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

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.AdvanceTechControlEntity;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.AlertHistoryEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.entities.TablePreferenceEntity;
import com.nwm.api.services.AlertService;
import com.nwm.api.services.SitesAlertService;
import com.nwm.api.services.SitesDevicesService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SendMail;
import com.nwm.api.utils.Translator;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/advance-tech-api")
public class AdvancetechAPIController extends BaseController {

	
	/**
	 * @description call API advanceTech 
	 * @author long.pham
	 * @since 2021-11-05
	 * @param  obj
	 */

	@PostMapping("/save-control")
	public Object saveControl(@Valid @RequestBody AdvanceTechControlEntity obj) {
		try {
			SitesDevicesService service = new SitesDevicesService();
			SitesDevicesEntity params = new SitesDevicesEntity();
			params.setHash_id(obj.getHash_id());
			params.setId_employee(obj.getId_employee());
			SitesDevicesEntity getDetail = service.getDetail(params);
			
//			if(getDetail.getId() > 0 && getDetail.getAdvance_tech_host() != null && getDetail.getAdvance_tech_pass() != null) {
				// Login
				String url = "http://192.168.80.189/sys/log_in";
				String json = "{\"password\":\"admin\"}";
				// Create HttpClient
		        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
		            
		            // Create HttpPut request
		            HttpPut request = new HttpPut(url);
		            
		            // Set JSON payload
		            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
		            request.setEntity(entity);
		            
		            // Set headers
		            request.setHeader("Accept", "application/json");
		            request.setHeader("Content-type", "application/json");
		            
		            // Execute the request
		            try (CloseableHttpResponse response = httpClient.execute(request)) {
		            	
		                
		                // Get HttpResponse Status
		                System.out.println("Response Code: " + response.getStatusLine());
		                
		                // Get HttpResponse Content
		                String content = EntityUtils.toString(response.getEntity());
		                System.out.println("Response Content: \n" + content);
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
//			}
			
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
			
			// Get site info
			
//			if (obj.getScreen_mode() == 2) {
//				boolean update = service.updateAlert(obj);
//				if (update == true) {
//					// Get alert info send email
//					AlertEntity detailObj = service.getDetailSendMail(obj);
//					if(detailObj.getId() > 0) {
//						String idSite =  String.valueOf(detailObj.getId_site());
//						String hash_id = secretCard.encrypt(idSite).toLowerCase();
//						String domain = Lib.getDomain();
//						
//						StringBuilder bodyHtml = new StringBuilder();
//						bodyHtml.append("<div style=\"max-width: 1000px;\" class=\"main-body\">"
//								+ "<p>Your Next Wave Energy Monitoring system detected an alert.</p>"
//								+ "<table style=\"border-collapse: collapse; border: 1px solid #DDD; width: 100%; \">\n"
//						+ "                <thead>\n"
//						+ "                    <tr>\n"
//						+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Fault Code</th>\n"
//						+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Site Name</th>\n"
//						+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Device Name</th>\n"
//						+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Message</th>\n"
//						+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Open Date</th>\n"
//						+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Close Date</th>\n"
//						+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: center;\">Status</th>\n"
//						+ "                    </tr>\n"
//						+ "                </thead>\n"
//						+ "                <tbody>\n");
//						
//						StringBuilder tBody = new StringBuilder();
//						
//						if(detailObj.getOpen_send_mail() == 0 && detailObj.getStatus() == 1) {
//							tBody.append("<tr>\n");
//							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getError_code()).append("</td>");
//							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getSite_name()).append("</td>");
//							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getDevicename()).append("</td>");
//							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getMessage()).append("</td>");
//							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getStart_date()).append("</td>");
//							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getEnd_date()).append("</td>");
//							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD; text-align: center;\">").append(detailObj.getStatus_name()).append("</td>");
//							tBody .append("</tr>");
//						} else if(detailObj.getStatus() == 0 && detailObj.getClose_send_mail() == 0) {
//							tBody.append("<tr>\n");
//							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getError_code()).append("</td>");
//							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getSite_name()).append("</td>");
//							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getDevicename()).append("</td>");
//							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getMessage()).append("</td>");
//							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getStart_date()).append("</td>");
//							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(detailObj.getEnd_date()).append("</td>");
//							tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD; text-align: center;\">").append(detailObj.getStatus_name()).append("</td>");
//							tBody .append("</tr>");
//						}
//						
//						if(tBody != null) {
//
//							bodyHtml.append(tBody);
//							bodyHtml.append("</tbody>\n"
//									+ "            </table>"
//									+ "<br/><p>For more details on the alert, visit the Next Wave Energy Monitoring login portal below. If you wish to change any of your notification settings, do not hesitate to contact us at <a href=\"mailto:support@nwemon.com\">support@nwemon.com</a> or (800) 644-0839. </p>"
//									+ "<div style=\"text-align: center; \" class=\"login-portal\"><a style=\"display: inline-block; background: #ffda00; padding: 5px 30px; color: #000; margin-top: 30px; border-radius: 4px; text-decoration: none; \" href=\""+domain+"/management/sites/"+hash_id+"/dashboard\" target=\"_blank\">Site Overview</a></div>"
//									+ "<div class=\"regards\"><br><p>Regards,</p><p>Next Wave Team</p><p><a href=\"https://nwemon.com\" target=\"_blank\"><img width=\"100px\" src=\"https://nwemon.com/public/uploads/system_setting_images/logo-colored-1642026858.png\"></a></p></div>"
//									+ "</div>");
//							
//							if(tBody.length() > 0) {
//								// Sent mail
//								String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailFromContact);
////								String mailTo = "vanlong200880@gmail.com";
//								String mailTo = detailObj.getCf_email_subscribers();
//								String mailToBCC = detailObj.getAlert_mail_bcc();
//								String mailToCC = detailObj.getAlert_mail_cc();
//								
//								// Remove email employees who hide a site
//								List emails = service.getEmployeeHidingSite(obj);
//								if(emails != null && emails.size() > 0) {
//									for(int i = 0; i < emails.size(); i++) {
//										Map<String, Object> itemT = (Map<String, Object>) emails.get(i);
//										String email = itemT.get("email").toString();
//
//										mailTo = mailTo.replaceAll("\\b(" + email + "(,)|(,)" + email + ")?", "");
//									}
//								}
//								
//								String subject = " Next Wave Alert - ".concat(detailObj.getSite_name());
//								String tags = "run_cron_job";
//								String fromName = "NEXT WAVE ENERGY MONITORING INC";
//								
//								if(mailTo != null && !mailTo.trim().isEmpty() ) {
//									boolean flagSent = SendMail.SendGmailTLS(mailFromContact, fromName, mailTo, mailToCC, mailToBCC, subject, bodyHtml.toString(), tags);
//									
//									if (!flagSent) {
//										throw new Exception(Translator.toLocale(Constants.SEND_MAIL_ERROR_MSG));
//									}
//								}
//								
//							}
//						}
//					}
//					
//					return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
//				} else {
//					return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
//				}
//			} else {
//				return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
//			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
}
