/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.CronJobSchedulerEntity;
import com.nwm.api.entities.DeviceAlertDetectEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.FLLogger;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SendMail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CronJobAlertEmailNotifyService extends DB {

	private static final FLLogger log = FLLogger.getLogger("batchjob/"+CronJobAlertEmailNotifyService.class);
	private final AtomicBoolean isRunning = new AtomicBoolean(false);

	public enum ErrorLevel {
		INFO(2),
		FATAL(11),
		ERROR(12),
		WARNING(13),
		DEBUG(14),
		NO_PRODUCTION(32),
		NO_COMM(33),
		POWER_FACTOR(36),
		GRID_FREQUENCY(37),
		ZONE(38),
		BREAKER(39),
		HVAC_ALERT(40);

		private final int id;

		ErrorLevel(int id) {
			this.id = id;
		}
		public int getId() {
			return id;
		}
	}


	@Value ("${cron.device.alert.email.notify.maxthread:1}")
	private int MAX_SITE_THREADS;

	private ThreadPoolExecutor siteExecutor;

	private ThreadPoolExecutor createSiteExecutor() {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(
				MAX_SITE_THREADS, MAX_SITE_THREADS,
				60L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<>());
		executor.allowCoreThreadTimeOut(true);
		return executor;
	}

	private Instant nowInstant;

	@PostConstruct
	public void init() {
		siteExecutor = createSiteExecutor();
		nowInstant = Instant.from(LocalDateTime.now(ZoneId.of("UTC")).toInstant(ZoneOffset.UTC));
	}

	/**
	 * @description execute the email notification for alerts
	 */
	public void execute() {
		if (!isRunning.compareAndSet(false, true)) {
			log.info("Email Notification check is already running. Skipping this execution.");
			return;
		}
		try {
			Map<String, Object> params = new HashMap<>();
			// get all sites that have email subscribers and bcc client emails
			List<Map<String, Object>> listSites = queryForList("CronJobAlertEmailNotify.getListSiteSendMailAlert", params);
			if (listSites == null || listSites.isEmpty()) {
				return;
			}

			List<Future<?>> futures = new ArrayList<>();
			for (Map site : listSites) {
				futures.add(siteExecutor.submit(() -> {
					Thread t = Thread.currentThread();
					String tName = t.getName();
					t.setName(tName + "-" + site.get("id"));
					processForEachSite(site, nowInstant);
				}));
			}

			for (Future<?> f : futures) {
				try {
					f.get();
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
					log.error("Site task interrupted: " + ie.getMessage(), ie);
					break;
				} catch (ExecutionException ee) {
					log.error("Error executing site task: " + ee.getMessage(), ee);
				}catch(Exception e) {
					log.error("Unexpected error executing site task: " + e.getMessage(), e);
				}
			}
		} catch (Exception e) {
			log.error("Error in runEmailNotification: " + e.getMessage());
			e.printStackTrace();
		} finally {
			isRunning.set(false);
		}
	}

	/**
	 * @description process for each site
	 * @since 2026-09-30
	 * @param {Map<String, Object>} site
	 */
	private void processForEachSite(Map<String, Object> site, Instant jobStartInstant) {
		try {
			log.info("Start processing site: " + site.get("id") + ", site name: " + site.get("name"));
			String domain = Lib.getDomain();
			int siteId = (int) site.get("id");
			String siteName = (String) site.get("name");
			String siteHashId = (String) site.get("hash_id");
			String cfEmailSubscribers = (String) site.get("cf_email_subscribers");
			String bccClientEmails = (String) site.get("bcc_client_emails");
			String hidingEmails = (String) site.get("hiding_emails");
			String adminUsers = (String) site.get("nw_emails");
			
			Map<String, Object> params = new HashMap<>();
			params.put("id_site", site.get("id"));
			params.put("error_levels", Arrays.asList(ErrorLevel.NO_COMM.getId(), ErrorLevel.NO_PRODUCTION.getId()));
			params.put("is_closed", 0);
			
			// Get the list alert status is open by site and error type
			List<Map<String, Object>> listOpenAlerts = (List<Map<String, Object>>)queryForList("CronJobAlertEmailNotify.getListAlert", params);
			if(listOpenAlerts == null || listOpenAlerts.isEmpty()) {
				log.info("No open alerts found for site name: " + siteName + ", site id: " + siteId + ", site hash id: " + siteHashId);
			} 
			// Get the list alert status is closed by site and error type
			params.put("is_closed", 1);
			List<Map<String, Object>> listClosedAlerts = (List<Map<String, Object>>)queryForList("CronJobAlertEmailNotify.getListAlert", params);
			if(listClosedAlerts == null || listClosedAlerts.isEmpty()) {
				log.info("No closed alerts found for site name: " + siteName + ", site id: " + siteId + ", site hash id: " + siteHashId);
			}

			// If there are no open or closed alerts, log and return
			if((listOpenAlerts == null || listOpenAlerts.isEmpty()) && (listClosedAlerts == null || listClosedAlerts.isEmpty())) {
				log.info("No alerts to send for site name: " + siteName + ", site id: " + siteId + ", site hash id: " + siteHashId);
				return;
			}

			List<String> listOpenAlertIds = listOpenAlerts != null ? listOpenAlerts.stream()
					.map(alert -> alert.get("id").toString())
					.collect(Collectors.toList()) : new ArrayList<>();
			List<String> listClosedAlertIds = listClosedAlerts != null ? listClosedAlerts.stream()
					.map(alert -> alert.get("id").toString())
					.collect(Collectors.toList()) : new ArrayList<>();

			// check if there are any open alerts to update the alert sent status fro field open_send_mail 
			if(!listOpenAlertIds.isEmpty()) {
				log.info("List of open alert IDs for site name: " + siteName + ", site id: " + siteId + ", site hash id: " + siteHashId + ": " + String.join(",", listOpenAlertIds));
				Map<String, Object> updateParams = new HashMap<>();
				updateParams.put("list_alert_ids", listOpenAlertIds);
				updateParams.put("is_closed", 0);
				update("CronJobAlertEmailNotify.updateAlertSentStatus", updateParams);
			}

			// check if there are any closed alerts to update the alert sent status for field close_send_mail
			if(!listClosedAlertIds.isEmpty()) {
				log.info("List of closed alert IDs for site name: " + siteName + ", site id: " + siteId + ", site hash id: " + siteHashId + ": " + String.join(",", listClosedAlertIds));
				Map<String, Object> updateParams = new HashMap<>();
				updateParams.put("list_alert_ids", listClosedAlertIds);
				updateParams.put("is_closed", 1);
				update("CronJobAlertEmailNotify.updateAlertSentStatus", updateParams);
			}

			// Debug skip send to client
			// process send email to client users
			boolean isToClient = true;
			// boolean clientEmailSent = processSendEmailToUsers(siteName, cfEmailSubscribers, bccClientEmails, hidingEmails, adminUsers, domain, siteHashId, listOpenAlerts, listClosedAlerts, isToClient);
			boolean clientEmailSent = false;
			// process send email to nw admin users
			isToClient = false;
			String finalCfEmailSubscribers = cfEmailSubscribers;
			if(clientEmailSent) {
				finalCfEmailSubscribers = "";
			}
			processSendEmailToUsers(siteName, finalCfEmailSubscribers, bccClientEmails, hidingEmails, adminUsers, domain, siteHashId, listOpenAlerts, listClosedAlerts, isToClient);
		} catch (Exception e) {
			log.error("Error processing site: " + site.get("id") + ", site name: " + site.get("name"), e);
		}
	}

	/**
	 * @description process send email to users
	 * @author chuong.ma
	 * @since 2026-09-30
	 * @param siteName
	 * @param cfEmailSubscribers
	 * @param bccClientEmails
	 * @param hidingEmails
	 * @param emailContent
	 */
	private boolean processSendEmailToUsers(String siteName, String cfEmailSubscribers, String bccClientEmails, String hidingEmails, 
			String adminUsers, String domain, String siteHashId, List<Map<String, Object>> listOpenAlerts, List<Map<String, Object>> listClosedAlerts, boolean isToClient) {
		try{
			// build email content
			StringBuilder emailContent = new StringBuilder();
			// Build email header
			emailContent.append(buildEmailHeader());
			// Build email content for open alerts
			emailContent.append(buildEmailContent(listOpenAlerts, isToClient));
			// Build email content for closed alerts
			emailContent.append(buildEmailContent(listClosedAlerts, isToClient));
			// Build email footer
			emailContent.append(buildEmailFooter(domain, siteHashId));
			// list of emails from un subscribers list
			ArrayList<String> hidingEmailList = hidingEmails != null && !hidingEmails.isEmpty() ? new ArrayList<>(Arrays.asList(hidingEmails.split(","))) : new ArrayList<>();
			// fileter the email subscribers list to remove any emails that are in the hidingEmailList
			// Stream<String> clientStream = Arrays.stream(bccClientEmails.split(","));
			String finalBccEmails = "";
			if(isToClient) {
				// clientStream = Arrays.stream(cfEmailSubscribers.split(","));
				finalBccEmails =  Arrays.stream(bccClientEmails.split(","))
						.distinct() // remove duplicates
						.filter(email -> !hidingEmailList.contains(email))
						.collect(Collectors.joining(","));
			}else{
				finalBccEmails =  
						Arrays.stream(adminUsers.split(","))
						.distinct() // remove duplicates
						.filter(email -> !hidingEmailList.contains(email))
						.collect(Collectors.joining(","));
			}
			if((cfEmailSubscribers == null || cfEmailSubscribers.isEmpty()) && (finalBccEmails == null || finalBccEmails.isEmpty())) {
				log.info("No email subscribers found for site: " + siteName);
				return false;
			}

			String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName,
								Constants.mailFromContact);
			String mailToCC = "";
			// String mailToBCC = String.join(",", mailToBCCArr);
			String subject = " [DEV_TEST]Next Wave Alert - ".concat(siteName);
			String tags = "run_cron_job";
			String fromName = "NEXT WAVE ENERGY MONITORING INC";	
			boolean flagSent = SendMail.SendGmailTLS(mailFromContact, fromName, cfEmailSubscribers, mailToCC, finalBccEmails,
									subject, emailContent.toString(), tags);
			if(flagSent) {
				log.info("Email sent successfully to users for site: " + siteName);
				return true;
			} else {
				log.error("Failed to send email to users for site: " + siteName);
				return false;
			}
		}catch(Exception e){
			log.error("Error sending email to users for site: " + siteName, e);
			return false;
		}
	}

	/**
	 * @description build email table thead
	 * @author chuong.ma
	 * @since 2026-09-30
	 * @return String
	 */
	private String buildEmailHeader() {
			StringBuffer header = new StringBuffer();
			header.append("<html><body>");
			header.append("<h3>Your Next Wave Energy Monitoring system detected an alert</h3>");
			header.append("<div style=\"max-width: 1000px;\" class=\"main-body\">");
			header.append("<p>Your Next Wave Energy Monitoring system detected an alert.</p>");
			header.append("<table style=\"border-collapse: collapse; border: 1px solid #DDD; width: 100%; \">\n");
			header.append("<thead>\n" + "                    <tr>\n"
							+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Fault Code</th>\n"
							+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Site Name</th>\n"
							+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Device Name</th>\n"
							+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Message</th>\n"
							+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Open Date</th>\n"
							+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Close Date</th>\n"
							+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: center;\">Status</th>\n"
							+ "                    </tr>\n" + "                </thead>\n");
			header.append("<tbody>\n");
			return header.toString();
	}

	/**
	 * @description build email table row
	 * @author chuong.ma
	 * @since 2026-09-30
	 * @param alert
	 * @return String
	 */
	private String buildEmailItemRow(Map<String, Object> alert) {
		StringBuilder row = new StringBuilder();
		row.append("<tr>\n");
		row.append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">")
				.append(alert.get("error_code")).append("</td>");
		row.append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">")
				.append(alert.get("site_name")).append("</td>");
		row.append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">")
				.append(alert.get("device_name")).append("</td>");
		row.append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">")
				.append(alert.get("message")).append("</td>");
		row.append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">")
				.append(alert.get("start_date")).append("</td>");
		row.append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">")
				.append(alert.get("end_date")).append("</td>");
		row.append("<td style=\"padding: 5px 10px; border: 1px solid #DDD; text-align: center;\">")
				.append(alert.get("status")).append("</td>");
		row.append("</tr>");
		return row.toString();
	}

	/**
	 * @description build email footer
	 * @param domain
	 * @param hash_id
	 * @return
	 */
	private String buildEmailFooter(String domain, String hash_id) {
		StringBuilder footer = new StringBuilder();
		footer.append("</tbody>\n");
		footer.append("</table>");
		footer.append("<br/><p>For more details on the alert, visit the Next Wave Energy Monitoring login portal below. If you wish to change any of your notification settings, do not hesitate to contact us at <a href=\"mailto:support@nwemon.com\">support@nwemon.com</a> or (800) 644-0839. </p>");
		footer.append("<div style=\"text-align: center; \" class=\"login-portal\"><a style=\"display: inline-block; background: #ffda00; padding: 5px 30px; color: #000; margin-top: 30px; border-radius: 4px; text-decoration: none; \" href=\"");
		footer.append(domain + "/management/sites/" + hash_id + "/dashboard");
		footer.append("/dashboard\" target=\"_blank\">Site Overview</a></div>");
		footer.append("<div class=\"regards\"><br><p>Regards,</p><p>Next Wave Team</p><p><a href=\"https://nwemon.com\" target=\"_blank\"><img width=\"100px\" src=\"https://nwemon.com/public/uploads/system_setting_images/logo-colored-1642026858.png\"></a></p></div>");
		footer.append("</div>");
		footer.append("</body></html>");
		return footer.toString();
	}

	/**
	 * @description build email content
	 * @author chuong.ma
	 * @since 2026-09-30
	 * @param alerts
	 * @return String
	 */
	private String buildEmailContent(List<Map<String, Object>> alerts, boolean isToClient) {
		if(alerts == null || alerts.isEmpty()) {
			return "";
		}
		StringBuilder emailContent = new StringBuilder();
		for (Map<String, Object> alert : alerts) {
			int isNotifyNw = (int) alert.get("is_notity_nw");
			int isNotifyClient = (int) alert.get("is_notity_client");
			if(isToClient && isNotifyClient == 0) {
				continue;
			}
			if(!isToClient && isNotifyNw == 0) {
				continue;
			}
			emailContent.append(buildEmailItemRow(alert));
		}
		return emailContent.toString();
	}

	/**
	 * @description update job scheduler status
	 * @author chuong.ma
	 * @since 2026-09-25
	 * @param jobCode
	 * @param trigger, trigger can be "START" or "END"
	 */
	public void updateJobSchedulerStatus(String jobCode, String trigger) {
		try {
			String status = "START".equals(trigger) ? "RUNNING" : "COMPLETED";
			CronJobSchedulerEntity jobEntity = (CronJobSchedulerEntity) queryForObject("CronJobScheduler.getJobScheduler", jobCode);
			if(jobEntity == null) {
				jobEntity = new CronJobSchedulerEntity();
				jobEntity.setJob_code(jobCode);
				jobEntity.setJob_name("Alert Email Notify");
				jobEntity.setLast_start_time(Date.from(Instant.now()));
				jobEntity.setLast_status(status);
				jobEntity.setDeseription("This job is responsible for sending alert email notifications to subscribers based on the alert status and error levels.");
				insert("CronJobScheduler.insertJobScheduler", jobEntity);
			}else{
				if ("START".equals(trigger)) {
					jobEntity.setLast_start_time(Date.from(Instant.now()));
				}
				if ("END".equals(trigger)) {
					jobEntity.setLast_end_time(Date.from(Instant.now()));
				}
				jobEntity.setLast_status(status);
				update("CronJobScheduler.updateJobScheduler", jobEntity);
			}
		} catch (Exception e) {
			log.error("Error updating job scheduler status: " + e.getMessage(), e);
		}
	}


}
