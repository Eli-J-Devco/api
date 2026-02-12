/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.AlertFilterEntity;
import com.nwm.api.entities.AlertHistoryEntity;
import com.nwm.api.entities.AlertNoteEntity;
import com.nwm.api.entities.ChartAlertDateEntity;
import com.nwm.api.entities.ExternalAlertEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.services.AlertService;
import com.nwm.api.services.ApiAccessService;
import com.nwm.api.services.EmployeeService;
import com.nwm.api.services.ThirdPartyAPIService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;
import com.nwm.api.utils.SendMail;
import com.nwm.api.utils.Translator;
import com.nwm.api.entities.CustomAlertEntity;
import com.nwm.api.entities.CustomAlertMetricEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DeviceGroupEntity;

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
    public Object getList(@RequestBody AlertEntity obj, @RequestHeader(name = "Authorization") String authz){
		try {
			obj.setIsUserNW(Lib.isUserNW(authz));
			(new EmployeeService()).getTableSort(obj);
			AlertService service = new AlertService();
			List data = service.getList(obj);
			int totalRecord = service.getListTotalCount(obj);
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

	@PostMapping("/list-group-by-site")
    public Object getListAlertGroupBySite(@RequestBody AlertEntity obj, @RequestHeader(name = "Authorization") String authz){
		try {
			obj.setIsUserNW(Lib.isUserNW(authz));
			(new EmployeeService()).getTableSort(obj);
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
    public Object getAllAlertBySite(@RequestBody AlertEntity obj, @RequestHeader(name = "Authorization") String authz){
		try {
			obj.setIsUserNW(Lib.isUserNW(authz));
			obj.setId_employee(Lib.getUserId(authz));
			AlertService service = new AlertService();
			List data = service.getAllAlertBySite(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
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
	 * @description update alert note
	 * @author long.pham
	 * @since 2025-08-24
	 * @param id
	 * @return object
	 */
	@PostMapping("/update-alert-note")
	public Object updateAlertNote(@RequestBody AlertNoteEntity obj) {
		try {
			AlertService service = new AlertService();
			service.updateAlertNote(obj);
			return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, obj, 1);
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
								String mailToBCC = detailObj.getAlert_mail_bcc();
								String mailToCC = detailObj.getAlert_mail_cc();
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
									boolean flagSent = SendMail.SendGmailTLS(mailFromContact, fromName, mailTo, mailToCC, mailToBCC, subject, bodyHtml.toString(), tags);
									
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
	public Object getAlertSummary(@RequestBody AlertEntity obj, @RequestHeader(name = "Authorization") String authz) {
		try {
			obj.setIsUserNW(Lib.isUserNW(authz));
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
			AlertService service = new AlertService();
			List<ChartAlertDateEntity> data = service.getDataChart(obj);
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
	 * @description Get list filter alert by id_employeee
	 * @author duy.phan
	 * @since 2023-07-18
	 * @return data (status, message, object, total_row
	 */
	@PostMapping("/get-alert-per-page")
	public Object getAlertPerPage(@RequestBody AlertFilterEntity obj) {
		try {
			AlertService service = new AlertService();
			Object detailObj = service.getAlertPerPage(obj);
			if (detailObj != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, detailObj, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list filter alert by id_employeee
	 * @author duy.phan
	 * @since 2023-07-18
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-alert-filter-default")
	public Object getAlertFilterDefault(@RequestBody AlertFilterEntity obj) {
		try {
			AlertService service = new AlertService();
			Object detailObj = service.getAlertFilterDefault(obj);
			if (detailObj != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, detailObj, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
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

    @PostMapping("/get-list-device-group")
    public Object getDeviceGroupList(@RequestBody DeviceGroupEntity obj) {
        AlertService service = new AlertService();
        try {
            List data = service.getDeviceGroupList(obj);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
        } catch (Exception e) {
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    @PostMapping("/get-device-list")
    public Object getDeviceList(@RequestBody DeviceEntity obj) {
        AlertService service = new AlertService();
        try {
            List data = service.getDeviceList(obj);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
        } catch (Exception e) {
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    @PostMapping("/get-metric-list")
    public Object getMetricList(@RequestBody CustomAlertMetricEntity obj) {
        AlertService service = new AlertService();
        try {
            List data = service.getMetricList(obj);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
        } catch (Exception e) {
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    @PostMapping("/save-custom-alert")
    public Object saveCustomAlert(@Valid @RequestBody CustomAlertEntity obj) {
        try {
            if (obj.getIds_site() == null || obj.getIds_site().isEmpty()) {
                return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
            }
            AlertService service = new AlertService();
            CustomAlertEntity data = service.saveCustomAlert(obj);
            if (data != null) {
                return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
            }
            return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
        } catch (Exception e) {
            // log error
            return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
        }
    }

    @PostMapping("/delete-custom-alert")
    public Object deleteCustomAlert(@Valid @RequestBody CustomAlertEntity obj) {
        try {
            if (obj.getId() == 0) {
                return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
            }
            AlertService service = new AlertService();
            boolean res = service.deleteCustomAlert(obj);
            if (res) {
                return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, obj, 1);
            }
            return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
        } catch (Exception e) {
            // log error
            return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
        }
    }

    @PostMapping("/disable-custom-alert")
    public Object disableCustomAlert(@Valid @RequestBody CustomAlertEntity obj) {
        try {
            if (obj.getId() == 0) {
                return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
            }
            AlertService service = new AlertService();
            boolean res = service.disableCustomAlert(obj);
            if (res) {
                return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, obj, 1);
            }
            return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
        } catch (Exception e) {
            // log error
            return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
        }
    }

    @PostMapping("/customize-list")
    public Object getListCustomize(@RequestBody CustomAlertEntity obj){
        try {
            AlertService service = new AlertService();
            List data = service.getListCustomize(obj);
            int totalRecord = service.getListCustomizeTotalCount(obj);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    /**
	 * @description Get all alerts with basic information for external API
	 * @author duc.pham
	 * @since 2026-02-09
	 * @param start_date, end_date, limit, offset
	 * @return data with Alert Name, Source, Message, Code, Status, Acknowledgment
	 */
	@GetMapping("/external/get-all-alerts")
	public Object getAllAlertsExternal(
			@ApiParam(value = "Filter by Alert Code (optional)")
			@RequestParam(required = false) String code,

			@ApiParam(value = "Filter by Source (optional)")
			@RequestParam(required = false) String source,

			@ApiParam(value = "Filter by Message (optional)")
			@RequestParam(required = false) String message,

			@ApiParam(value = "Filter by Alert Name (optional)")
			@RequestParam(required = false) String alert_name,

			@ApiParam(value = "Filter by Status (optional)")
			@RequestParam(required = false) String status,

			@ApiParam(value = "Filter by Acknowledgment (optional)")
			@RequestParam(required = false) Boolean acknowledgment,

			@ApiParam(value = "Start date (format: YYYY-MM-DD) (optional)")
			@RequestParam(required = false) String start_date,

			@ApiParam(value = "End date (format: YYYY-MM-DD) (optional)")
			@RequestParam(required = false) String end_date,

			@RequestHeader(name = "X-NWM-API-KEY", required = false) String apiKey,
			HttpServletRequest request) {
		try {
			// Validate API key using same method as 3rd-party
			if (Lib.isBlank(apiKey)) {
				return this.thirdPartyJsonResult(false, "Key is required.", null, 0);
			}

			ApiAccessService apiAccessService = new ApiAccessService();
			if (!apiAccessService.validateApiKey(apiKey)) {
				return this.thirdPartyJsonResult(false, "Key is invalid.", null, 0);
			}

			// Check endpoint access - same as 3rd-party
			ThirdPartyAPIService thirdPartyService = new ThirdPartyAPIService();
			String endpoint = request.getRequestURI().substring(request.getContextPath().length());
			String method = request.getMethod();

			if (!thirdPartyService.checkUserCanAccessEndPoint(apiKey, endpoint, method)) {
				return this.thirdPartyJsonResult(false, "Can not access this endpoint", null, 0);
			}

			if (!thirdPartyService.checkRateLimit(apiKey)) {
				return this.thirdPartyJsonResult(false, "Rate limit is full this month", null, 0);
			}

			// Log API usage
			apiAccessService.insertAPIUsage(endpoint, method, apiKey);

			// Build entity from params with all filters
			AlertEntity obj = new AlertEntity();
			obj.setSecurity_key(apiKey);

			// Set filter parameters
			if (!Lib.isBlank(code)) {
				obj.setError_code(code);
			}
			if (!Lib.isBlank(source)) {
				obj.setDevicename(source); // Source is device name
			}
			if (!Lib.isBlank(message)) {
				obj.setMessage(message);
			}
			// alert_name is returned in response, no need to filter
			if (!Lib.isBlank(status)) {
				// Map status string to int (Open=1, Closed=0)
				if ("Open".equalsIgnoreCase(status)) {
					obj.setStatus(1);
				} else if ("Closed".equalsIgnoreCase(status)) {
					obj.setStatus(0);
				}
			}
			if (acknowledgment != null) {
				// Map to alert_acknowledged field in database
				obj.setAlert_acknowledged(acknowledgment ? 1 : 0);
			}
			if (!Lib.isBlank(start_date)) {
				obj.setStart_date(start_date);
			}
			if (!Lib.isBlank(end_date)) {
				obj.setEnd_date(end_date);
			}

			AlertService service = new AlertService();


			List data = service.getAllAlertsForExternalAPI(obj);
			int totalRecord = 0;

			if (data != null && !data.isEmpty()) {
				totalRecord = service.getAllAlertsForExternalAPICount(obj);
			}

			return this.thirdPartyJsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error("Error in getAllAlertsExternal: " + e.getMessage(), e);
			return this.thirdPartyJsonResult(false, "Error retrieving alerts: " + e.getMessage(), null, 0);
		}
	}
}
