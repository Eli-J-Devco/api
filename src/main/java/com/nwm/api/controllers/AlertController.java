/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.AlertFilterEntity;
import com.nwm.api.entities.AlertHistoryEntity;
import com.nwm.api.entities.ConfigurationEntity;
import com.nwm.api.entities.EmployeeFilterFavoritesEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.TablePreferenceEntity;
import com.nwm.api.services.AlertService;
import com.nwm.api.services.ConfigurationService;
import com.nwm.api.services.SitesAnalyticsService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SendMail;
import com.nwm.api.utils.Translator;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/alert")
public class AlertController extends BaseController {

	/**
	 * @description Get list alert by site
	 * @author long.pham
	 * @since 2020-11-16
	 * @param id_customer, id_site, start_date, end_date
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/list")
    public Object getList(@RequestBody AlertEntity obj){
		try {
			if(obj.getLimit() == 0) {
				obj.setLimit(1000);
			}
			
			AlertService service = new AlertService();
			List data = service.getList(obj);
			int totalRecord = service.getListTotalCount(obj);
			TablePreferenceEntity preference = service.getPreference(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord, preference);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0, null);
		}
    }
	
	
	
	
	/**
	 * @description Get list alert by site
	 * @author long.pham
	 * @since 2020-11-16
	 * @param id_customer, id_site, start_date, end_date
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/list-group-by-site")
    public Object getListAlertGroupBySite(@RequestBody AlertEntity obj){
		try {
			if(obj.getLimit() == 0) {
				obj.setLimit(1000);
			}
			
			AlertService service = new AlertService();
			List data = service.getListAlertGroupBySite(obj);
			int totalRecord = service.getTotalGroupAlertSite(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
    }
	
	
	
	/**
	 * @description Get list alert by site
	 * @author long.pham
	 * @since 2020-11-16
	 * @param id_customer, id_site, start_date, end_date
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/get-all-alert-by-site")
    public Object getAllAlertBySite(@RequestBody AlertEntity obj){
		try {
			if(obj.getLimit() == 0) {
				obj.setLimit(1000);
			}
			
			AlertService service = new AlertService();
			List data = service.getAllAlertBySite(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
    }
	
	/**
	 * @description Get list alert by site
	 * @author long.pham
	 * @since 2020-11-16
	 * @param id_customer, id_site, start_date, end_date
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/list-by-id-sites")
    public Object getListSiteByEmployee(@RequestBody AlertEntity obj){
		try {
			if(obj.getLimit() == 0) {
				obj.setLimit(1000);
			}
			
			AlertService service = new AlertService();
			List data = service.getListIdSites(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
    }
	
	
	/**
	 * @description Get detail alert
	 * @author long.pham
	 * @since 2020-11-24
	 * @param id_site, id_customer, id_alert, current_time
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/alert-detail")
	public Object getAlertDetail(@RequestBody AlertEntity obj) {
		try {
			AlertService service = new AlertService();
			Object detailObj = service.getDetailAlert(obj);
			if (detailObj != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, detailObj, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	/**
	 * @description Get list alert by site
	 * @author long.pham
	 * @since 2021-03-18
	 * @param id_customer, id_site, start_date, end_date
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/list-by-site-admin")
    public Object getListBySiteAdmin(@RequestBody AlertEntity obj){
		try {
			
			if(obj.getHash_id() == null) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
			obj.setId_site(Integer.parseInt(secretCard.decrypt(obj.getHash_id())));
			
			AlertService service = new AlertService();
			List data = service.getListBySiteAdmin(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
    }
	
	/**
	 * @description Get detail alert
	 * @author long.pham
	 * @since 2020-11-24
	 * @param id_site, id_customer, id_alert, current_time
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/site-detail")
	public Object getSiteDetail(@RequestBody AlertEntity obj) {
		try {
			if(obj.getHash_id() == null) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
			obj.setId_site(Integer.parseInt(secretCard.decrypt(obj.getHash_id())));
			
			AlertService service = new AlertService();
			SiteEntity detailObj = service.getSiteDetail(obj);
			if (detailObj != null) {
				detailObj.setHash_id(secretCard.encrypt(Integer.toString(detailObj.getId())).toLowerCase());
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, detailObj, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description update error status
	 * @author long.pham
	 * @since 2021-02-26
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-status")
	public Object updateStatus(@RequestBody AlertEntity obj) {
		try {
			AlertService service = new AlertService();
			if(obj.getStatus() == 1) {
				obj.setEnd_date(null);
			} else if(obj.getStatus() == 0) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar cal = Calendar.getInstance(TimeZone.getDefault());
				Date now = cal.getTime();
				String CurrentDate = format.format(now);
				obj.setEnd_date(CurrentDate);
			}
			service.updateStatus(obj);
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description update alert ack
	 * @author long.pham
	 * @since 2021-11-04
	 * @param id
	 * @return object
	 */
	@PostMapping("/update-ack")
	public Object updateACK(@RequestBody AlertHistoryEntity obj) {
		try {
			AlertService service = new AlertService();
			service.updateACK(obj);
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description update alert
	 * @author long.pham
	 * @since 2021-11-05
	 * @param  obj
	 */

	@PostMapping("/update-alert")
	public Object save(@Valid @RequestBody AlertEntity obj) {
		try {
			AlertService service = new AlertService();
			if (obj.getScreen_mode() == 2) {
				boolean update = service.updateAlert(obj);
				if (update == true) {
					// Get alert info send email
					AlertEntity detailObj = service.getDetailSendMail(obj);
					if(detailObj.getId() > 0) {
						String idSite =  String.valueOf(detailObj.getId_site());
						String hash_id = secretCard.encrypt(idSite).toLowerCase();
						String domain = Lib.getDomain();
						
						StringBuilder bodyHtml = new StringBuilder();
						bodyHtml.append("<div style=\"max-width: 1000px;\" class=\"main-body\">"
								+ "<p>Your Next Wave Energy Monitoring system detected an alert.</p>"
								+ "<table style=\"border-collapse: collapse; border: 1px solid #DDD; width: 100%; \">\n"
						+ "                <thead>\n"
						+ "                    <tr>\n"
						+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Fault Code</th>\n"
						+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Site Name</th>\n"
						+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Device Name</th>\n"
						+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Message</th>\n"
						+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Open Date</th>\n"
						+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Close Date</th>\n"
						+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: center;\">Status</th>\n"
						+ "                    </tr>\n"
						+ "                </thead>\n"
						+ "                <tbody>\n");
						
						StringBuilder tBody = new StringBuilder();
						
						if(detailObj.getOpen_send_mail() == 0 && detailObj.getStatus() == 1) {
							tBody.append("<tr>\n");
							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getError_code()).append("</td>");
							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getSite_name()).append("</td>");
							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getDevicename()).append("</td>");
							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getMessage()).append("</td>");
							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getStart_date()).append("</td>");
							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getEnd_date()).append("</td>");
							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD; text-align: center;\">").append(detailObj.getStatus_name()).append("</td>");
							tBody .append("</tr>");
						} else if(detailObj.getStatus() == 0 && detailObj.getClose_send_mail() == 0) {
							tBody.append("<tr>\n");
							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getError_code()).append("</td>");
							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getSite_name()).append("</td>");
							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getDevicename()).append("</td>");
							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getMessage()).append("</td>");
							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getStart_date()).append("</td>");
							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getEnd_date()).append("</td>");
							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD; text-align: center;\">").append(detailObj.getStatus_name()).append("</td>");
							tBody .append("</tr>");
						}
						
						if(tBody != null) {

							bodyHtml.append(tBody);
							bodyHtml.append("</tbody>\n"
									+ "            </table>"
									+ "<br/><p>For more details on the alert, visit the Next Wave Energy Monitoring login portal below. If you wish to change any of your notification settings, do not hesitate to contact us at <a href=\"mailto:support@nwemon.com\">support@nwemon.com</a> or (800) 644-0839. </p>"
									+ "<div style=\"text-align: center; \" class=\"login-portal\"><a style=\"display: inline-block; background: #ffda00; padding: 5px 30px; color: #000; margin-top: 30px; border-radius: 4px; text-decoration: none; \" href=\""+domain+"/management/sites/"+hash_id+"/dashboard\" target=\"_blank\">Site Overview</a></div>"
									+ "<div class=\"regards\"><br><p>Regards,</p><p>Next Wave Team</p><p><a href=\"https://nwemon.com\" target=\"_blank\"><img width=\"100px\" src=\"https://nwemon.com/public/uploads/system_setting_images/logo-colored-1642026858.png\"></a></p></div>"
									+ "</div>");
							
							if(tBody.length() > 0) {
								// Sent mail
								String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailFromContact);
//								String mailTo = "vanlong200880@gmail.com";
								String mailTo = detailObj.getCf_email_subscribers();
								// Remove email employees who hide a site
								List emails = service.getEmployeeHidingSite(obj);
								if(emails != null && emails.size() > 0) {
									for(int i = 0; i < emails.size(); i++) {
										Map<String, Object> itemT = (Map<String, Object>) emails.get(i);
										String email = itemT.get("email").toString();

										mailTo = mailTo.replaceAll("\\b(" + email + "(,)|(,)" + email + ")?", "");
									}
								}
								
								String subject = " Next Wave Alert - ".concat(detailObj.getSite_name());
								String tags = "run_cron_job";
								String fromName = "NEXT WAVE ENERGY MONITORING INC";
								if(mailTo != null && !mailTo.trim().isEmpty() ) {
									boolean flagSent = SendMail.SendGmailTLS(mailFromContact, fromName, mailTo, subject, bodyHtml.toString(), tags);
									
									if (!flagSent) {
										throw new Exception(Translator.toLocale(Constants.SEND_MAIL_ERROR_MSG));
									}
								}
								
							}
						}
					}
					
					return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
				} else {
					return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
				}
			} else {
				return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get detail alert
	 * @author long.pham
	 * @since 2020-11-24
	 * @param id_site, id_customer, id_alert, current_time
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/alert-summary")
	public Object getAlertSummary(@RequestBody AlertEntity obj) {
		try {
			AlertService service = new AlertService();
			Object detailObj = service.getAlertSummary(obj);
			if (detailObj != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, detailObj, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get list alert by site
	 * @author long.pham
	 * @since 2020-11-16
	 * @param id_customer, id_site, start_date, end_date
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/get-data-chart")
    public Object getDataChart(@RequestBody AlertEntity obj){
		try {
			if(obj.getLimit() == 0) {
				obj.setLimit(10000);
			}
			
			AlertService service = new AlertService();
			List data = service.getDataChart(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
    }
	
	/**
	 * @description update isRead 
	 * @author duy.phan
	 * @since 2023-05-08
	 * @param id
	 * @return object
	 */
	@PostMapping("/update-is-read")
	public Object updateIsRead(@RequestBody AlertEntity obj) {
		try {
			AlertService service = new AlertService();
			service.updateIsRead(obj);
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description update isRead 
	 * @author duy.phan
	 * @since 2023-05-08
	 * @param id
	 * @return object
	 */
	@PostMapping("/update-is-notification")
	public Object updateIsNotification(@RequestBody AlertEntity obj) {
		try {
			AlertService service = new AlertService();
			service.updateIsNotification(obj);
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get save name alert filter
	 * @author duy.phan
	 * @since 2023-07-17
	 * @param id_employee
	 * @return data (status, message, object, total_row
	 */	
	@PostMapping("/save-alert-filter")
	public Object insertAlertFilter(@Valid @RequestBody AlertFilterEntity obj) {
		try {
			AlertService service = new AlertService();
			AlertFilterEntity data = service.saveAlertFilter(obj);
			if (data != null) {
				
				return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
			} else {
				return this.jsonResult(false, "Please Add a Name for This Alert Filter", null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list filter alert by id_employeee
	 * @author duy.phan
	 * @since 2023-07-18
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-alert-filter")
	public Object getListAlertFilter(@RequestBody AlertFilterEntity obj) {
		try {
			AlertService service = new AlertService();
			List data = service.getListAlertFilter(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description delete a alert filter
	 * @author duy.phan
	 * @since 2023-07-19
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete-alert-filter")
	public Object deleteAlertFilter(@Valid @RequestBody AlertFilterEntity obj) {
		AlertService service = new AlertService();
		try {
			boolean result = service.deleteAlertFilter(obj);
			if (result) {
				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description delete a alert filter
	 * @author duy.phan
	 * @since 2023-07-19
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete-all-alert-filter")
	public Object deleteAllAlertFilter(@Valid @RequestBody AlertFilterEntity obj) {
		AlertService service = new AlertService();
		try {
			boolean result = service.deleteAllAlertFilter(obj);
			if (result) {
				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
}
