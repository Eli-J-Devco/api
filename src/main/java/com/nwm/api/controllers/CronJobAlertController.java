/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.BatchJobTableEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ErrorEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.services.CronJobAlertService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SecretCards;
import com.nwm.api.utils.SendMail;
import com.nwm.api.utils.Translator;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/cron-job")
public class CronJobAlertController extends BaseController {

	/**
	 * @description get no production
	 * @author long.pham
	 * @since 2023-07-20
	 * @return {}
	 */
	@GetMapping("/get-no-production")
	@ResponseBody
	public Object renderGetNoProduction(@RequestParam Map<String, Object> params) {
		try {
			String privateKey = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.privateKey);
			String token = (String) params.get("token");
			if (token == null || token == "" || !token.equals(privateKey)) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}

			String idSite = (String) params.get("id_site");
			int id_site = 0;

			if (idSite != null && Integer.parseInt(idSite) > 0) {
				id_site = Integer.parseInt(idSite);
			}

			CronJobAlertService service = new CronJobAlertService();
			DeviceEntity entity = new DeviceEntity();
			entity.setId_site(id_site);

			// Get list site
			List<?> listSites = service.getListSiteCheckNoCom(entity);
			if (listSites.size() > 0) {
				for (int s = 0; s < listSites.size(); s++) {
					SiteEntity objSite = (SiteEntity) listSites.get(s);
					BatchJobTableEntity bathJobEntity = new BatchJobTableEntity();

					ZoneId zoneIdLosAngeles = ZoneId.of(objSite.getTime_zone_value()); // "America/Los_Angeles"
					ZonedDateTime zdtNowLosAngeles = ZonedDateTime.now(zoneIdLosAngeles);
					int hourOfDay = zdtNowLosAngeles.getHour();

					Date now = new Date();
					// UTC
					TimeZone.setDefault(TimeZone.getTimeZone(objSite.getTime_zone_value()));
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
					String CurrentDate = format.format(now);

					SimpleDateFormat formatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					TimeZone tzUTC = TimeZone.getTimeZone("UTC");
					formatUTC.setTimeZone(tzUTC);
					String sDateUTC = formatUTC.format(now);
					

					if (hourOfDay >= (objSite.getStart_date_time() + 2) && hourOfDay <= (objSite.getEnd_date_time() - 2)) {
						// Check alert datalogger no communication
						DeviceEntity objDatalogger = service.getDeviceDatalogger(objSite.getId());
						if (objDatalogger.getId() > 0) {
							// Get list device meter and inverter
							DeviceEntity dEntity = new DeviceEntity();
							dEntity.setId_site(objSite.getId());
							List<?> listDeviceBySite = service.getListMeterAndInverterBySite(dEntity);
							if (listDeviceBySite.size() > 0) {
								for (int i = 0; i < listDeviceBySite.size(); i++) {
									DeviceEntity obj = (DeviceEntity) listDeviceBySite.get(i);
									if (obj.getTimezone_value() != null) {
										// No Production
										bathJobEntity.setId(obj.getId());
										bathJobEntity.setCurrent_time(CurrentDate);
										bathJobEntity.setStart_date_time(obj.getStart_date_time());
										bathJobEntity.setEnd_date_time(obj.getEnd_date_time());

										if (obj.getJob_tablename() != null) {
											bathJobEntity.setDatatablename(obj.getJob_tablename());
										} else {
											bathJobEntity.setDatatablename(obj.getDatatablename());
										}

										bathJobEntity.setId_device(obj.getId());
										int noProduction = obj.getId_error() > 0 ? obj.getId_error() : 0;

										BatchJobTableEntity rowItem = service.getLastRowItemCheckNoProduction(bathJobEntity);
										if (rowItem.getNvmActivePower() != 0.001) {
											if ((rowItem.getId_device() > 0 && (rowItem.getNvmActivePower() <= 0 || rowItem.getCount_item() == 5 )) ) {
												AlertEntity alertItem = new AlertEntity();
												alertItem.setId_device(obj.getId());
												alertItem.setId_error(noProduction);
												alertItem.setStart_date( !Lib.isBlank(rowItem.getTime()) ? rowItem.getTime() : sDateUTC);
												// Check error exits
												boolean checkAlertExist = service.checkAlertExist(alertItem);
												if (!checkAlertExist && alertItem.getId_device() > 0 && alertItem.getId_error() > 0) {
													// Insert error
													service.insertAlert(alertItem);
												}
											} else {
												// Close alert no production
												bathJobEntity.setId_error(noProduction);
												BatchJobTableEntity rowItemRemove = service.getRowItemAlert(bathJobEntity);
												rowItemRemove.setEnd_date(sDateUTC);
												if (rowItemRemove.getId() > 0) {
													service.updateCloseAlert(rowItemRemove);
												}
											}
										}

									}
								}
							}
						}
					}
				}
			}

			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description get no communication
	 * @author long.pham
	 * @since 2023-07-20
	 * @return {}
	 */
	@GetMapping("/get-no-communication")
	@ResponseBody
	public Object renderGetNoCommunication(@RequestParam Map<String, Object> params) {
		try {
			String privateKey = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.privateKey);
			String token = (String) params.get("token");
			if (token == null || token == "" || !token.equals(privateKey)) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}

			String idSite = (String) params.get("id_site");
			int id_site = 0;

			if (idSite != null && Integer.parseInt(idSite) > 0) {
				id_site = Integer.parseInt(idSite);
			}

			CronJobAlertService service = new CronJobAlertService();
			DeviceEntity entity = new DeviceEntity();
			entity.setId_site(id_site);

			// Get list site
			List<?> listSites = service.getListSiteCheckNoCom(entity);
			if (listSites.size() > 0) {
				for (int s = 0; s < listSites.size(); s++) {
					SiteEntity objSite = (SiteEntity) listSites.get(s);
					BatchJobTableEntity bathJobEntity = new BatchJobTableEntity();

					ZoneId zoneIdLosAngeles = ZoneId.of(objSite.getTime_zone_value()); // "America/Los_Angeles"
					ZonedDateTime zdtNowLosAngeles = ZonedDateTime.now(zoneIdLosAngeles);
					int hourOfDay = zdtNowLosAngeles.getHour();

					Date now = new Date();
					TimeZone.setDefault(TimeZone.getTimeZone(objSite.getTime_zone_value()));
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
					String CurrentDate = format.format(now);

					SimpleDateFormat formatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					TimeZone tzUTC = TimeZone.getTimeZone("UTC");
					formatUTC.setTimeZone(tzUTC);
					String sDateUTC = formatUTC.format(now);

					if (hourOfDay >= (objSite.getStart_date_time() + 2) && hourOfDay <= (objSite.getEnd_date_time() - 2)) {
						String flag = "off";
						// Check alert datalogger no communication
						DeviceEntity objDatalogger = service.getDeviceDatalogger(objSite.getId());
						if (objDatalogger.getId() > 0) {
							bathJobEntity.setCurrent_time(sDateUTC);
							bathJobEntity.setId_device(objDatalogger.getId());

							AlertEntity alertItem = new AlertEntity();
							alertItem.setId_device(objDatalogger.getId());
							alertItem.setId_error(136);
							alertItem.setStart_date(sDateUTC);

							// Get last data from datalogger
							BatchJobTableEntity dataDatalogger = service.getDataloggerItem(bathJobEntity);
							if (dataDatalogger.getId_device() <= 0) {
								// Check error exits
								boolean checkAlertExist = service.checkAlertExist(alertItem);
								if (!checkAlertExist && alertItem.getId_device() > 0 && alertItem.getId_error() > 0) {
									// Insert error
									service.insertAlert(alertItem);
									flag = "off";
								}
							} else {
								flag = "on";
								// Close alert no com datalogger
								// Close no communication
								AlertEntity checkAlertExist = (AlertEntity) service.getAlertDetail(alertItem);
								if (checkAlertExist.getId() > 0) {
									bathJobEntity.setEnd_date(sDateUTC);
									bathJobEntity.setId(checkAlertExist.getId());
									bathJobEntity.setId_device(checkAlertExist.getId_device());
									service.updateCloseAlert(bathJobEntity);
								}

							}
						} else {
							flag = "on";
						}

						if ("on".equals(flag)) {
							// Check alert device
							// Get list device meter and inverter
							DeviceEntity dEntity = new DeviceEntity();
							dEntity.setId_site(objSite.getId());
							List<?> listDeviceBySite = service.getListDeviceCheckNoCom(dEntity);
							if (listDeviceBySite.size() > 0) {
								for (int i = 0; i < listDeviceBySite.size(); i++) {
									DeviceEntity obj = (DeviceEntity) listDeviceBySite.get(i);
									if (obj.getTimezone_value() != null) {
										bathJobEntity.setId(obj.getId());
										bathJobEntity.setCurrent_time(CurrentDate);
										bathJobEntity.setStart_date_time(obj.getStart_date_time());
										bathJobEntity.setEnd_date_time(obj.getEnd_date_time());
										if (obj.getJob_tablename() != null) {
											bathJobEntity.setDatatablename(obj.getJob_tablename());
										} else {
											bathJobEntity.setDatatablename(obj.getDatatablename());
										}

										bathJobEntity.setId_device(obj.getId());

										int noCommunication = obj.getId_error() > 0 ? obj.getId_error() : 0;
										

										BatchJobTableEntity lastRowItem = service.getLastRowItemCheckNoCommunication(bathJobEntity);
										AlertEntity alertItem = new AlertEntity();
										alertItem.setId_device(obj.getId());
										alertItem.setId_error(noCommunication);
										alertItem.setStart_date(!Lib.isBlank(lastRowItem.getTime()) ? lastRowItem.getTime() : sDateUTC);
										

										if ((lastRowItem.getId_device() <= 0 || lastRowItem.getNvmActivePower() == 0.001) ) {
											// Check error exits
											boolean checkAlertExist = service.checkAlertExist(alertItem);
											if (!checkAlertExist && alertItem.getId_device() > 0 && alertItem.getId_error() > 0) {
												// Insert error
												service.insertAlert(alertItem);
											}

										} else {
											// Close no communication
											AlertEntity checkAlertExist = (AlertEntity) service.getAlertDetail(alertItem);
											if (checkAlertExist.getId() > 0) {
												bathJobEntity.setEnd_date(sDateUTC);
												bathJobEntity.setId(checkAlertExist.getId());
												bathJobEntity.setId_device(checkAlertExist.getId_device());
												service.updateCloseAlert(bathJobEntity);
											}
										}
									}
								}
							}
						}
					}
				}
			}

			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description get no communication
	 * @author long.pham
	 * @since 2023-07-20
	 * @return {}
	 */
	@GetMapping("/get-auto-close-alert-from-datalogger")
	@ResponseBody
	public Object renderAutoCloseAlertFromDatalogger(@RequestParam Map<String, Object> params) {
		try {
			String privateKey = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.privateKey);
			String token = (String) params.get("token");
			if (token == null || token == "" || !token.equals(privateKey)) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}

			String idSite = (String) params.get("id_site");
			int id_site = 0;

			if (idSite != null && Integer.parseInt(idSite) > 0) {
				id_site = Integer.parseInt(idSite);
			}

			CronJobAlertService service = new CronJobAlertService();
			DeviceEntity entityDevice = new DeviceEntity();
			entityDevice.setId_site(id_site);
			ErrorEntity entityError = new ErrorEntity();
			// Get list device meter and inverter
			List<?> listDevice = service.getListAllDevice(entityDevice);
			if (listDevice == null || listDevice.size() == 0) {
				return null;
			}

			for (int i = 0; i < listDevice.size(); i++) {
				DeviceEntity obj = (DeviceEntity) listDevice.get(i);

				Date now = new Date();
				TimeZone.setDefault(TimeZone.getTimeZone(obj.getTimezone_value()));
				SimpleDateFormat formatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				TimeZone tzUTC = TimeZone.getTimeZone("UTC");
				formatUTC.setTimeZone(tzUTC);
				String sDateUTC = formatUTC.format(now);
				BatchJobTableEntity bathJobEntity = new BatchJobTableEntity();
				bathJobEntity.setCurrent_time(sDateUTC);
				bathJobEntity.setId_device(obj.getId());
				if (obj.getJob_tablename() != null) {
					bathJobEntity.setDatatablename(obj.getJob_tablename());
				} else {
					bathJobEntity.setDatatablename(obj.getView_tablename());
				}

				BatchJobTableEntity lastRowItem = service.getLastRowItem(bathJobEntity);
				System.out.println("Run batchJob close alert from datalogger: " + obj.getId());
				if (lastRowItem.getError() == 0 && lastRowItem.getId_device() > 0) {
					// Close all alert by datalogger
					entityError.setId_device_group(obj.getId_device_group());
					entityError.setId_device(obj.getId());
					List<?> listError = service.getListErrorByType(entityError);

					if (listError.size() > 0) {
						AlertEntity alertItem = new AlertEntity();
						alertItem.setId_device(obj.getId());
						alertItem.setEnd_date(sDateUTC);
						for (int j = 0; j < listError.size(); j++) {
							ErrorEntity objError = (ErrorEntity) listError.get(j);
							alertItem.setId_error(objError.getId());
							service.UpdateErrorMultiRow(alertItem);
						}
					}
				}
			}

			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description get no communication
	 * @author long.pham
	 * @since 2023-07-20
	 * @return {}
	 */
	@GetMapping("/get-reset-last-value")
	@ResponseBody
	public Object renderResetLastValue(@RequestParam Map<String, Object> params) {
		try {
			String privateKey = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.privateKey);
			String token = (String) params.get("token");
			if (token == null || token == "" || !token.equals(privateKey)) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}

			String idSite = (String) params.get("id_site");
			int id_site = 0;

			if (idSite != null && Integer.parseInt(idSite) > 0) {
				id_site = Integer.parseInt(idSite);
			}

			CronJobAlertService service = new CronJobAlertService();
			DeviceEntity entityDevice = new DeviceEntity();
			entityDevice.setId_site(id_site);

			BatchJobTableEntity bathJobEntity = new BatchJobTableEntity();
			// Get list device meter and inverter
			List<?> listDevice = service.getListAllDevice(entityDevice);
			if (listDevice == null || listDevice.size() == 0) {
				return null;
			}

			for (int i = 0; i < listDevice.size(); i++) {
				DeviceEntity obj = (DeviceEntity) listDevice.get(i);
				Date now = new Date();
				TimeZone.setDefault(TimeZone.getTimeZone(obj.getTimezone_value()));
				SimpleDateFormat formatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				TimeZone tzUTC = TimeZone.getTimeZone("UTC");
				formatUTC.setTimeZone(tzUTC);
				String sDateUTC = formatUTC.format(now);

				bathJobEntity.setCurrent_time(sDateUTC);

				bathJobEntity.setId_device(obj.getId());
				bathJobEntity.setDatatablename(obj.getDatatablename());
				bathJobEntity.setId_device_type(obj.getId_device_type());
				BatchJobTableEntity lastRowItem = service.getLastRowItemResetLastValue(bathJobEntity);

				if (lastRowItem.getId_device() <= 0 || lastRowItem.getNvmActivePower() == -1) {
					DeviceEntity deviceObj = new DeviceEntity();
					deviceObj.setId(obj.getId());
					deviceObj.setLast_value(null);
					service.updateLastValueDevice(deviceObj);
				}
			}

			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description get auto sent mail alert
	 * @author long.pham
	 * @since 2023-07-20
	 * @return {}
	 */
	@GetMapping("/get-auto-sent-mail")
	@ResponseBody
	public Object renderAutoSentMailAlert(@RequestParam Map<String, Object> params) {
		try {
			String privateKey = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.privateKey);
			String token = (String) params.get("token");
			if (token == null || token == "" || !token.equals(privateKey)) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}

			String idSite = (String) params.get("id_site");
			int id_site = 0;

			if (idSite != null && Integer.parseInt(idSite) > 0) {
				id_site = Integer.parseInt(idSite);
			}

			CronJobAlertService service = new CronJobAlertService();

			// Get list site
			SiteEntity siteEntity = new SiteEntity();
			siteEntity.setId(id_site);
			List listSite = service.getListSiteSentMailAlert(siteEntity);
			if (listSite == null || listSite.size() == 0) {
				return null;
			}
			SecretCards secretCard = new SecretCards();

			for (int i = 0; i < listSite.size(); i++) {
				SiteEntity siteObj = (SiteEntity) listSite.get(i);
				String id = String.valueOf(siteObj.getId());
				String hash_id = secretCard.encrypt(id).toLowerCase();
				String domain = Lib.getDomain();

				List listAlertOpenBySite = service.getListAlertOpenBySite(siteObj);
				StringBuilder bodyHtml = new StringBuilder();
				bodyHtml.append("<div style=\"max-width: 1000px;\" class=\"main-body\">"
						+ "<p>Your Next Wave Energy Monitoring system detected an alert.</p>"
						+ "<table style=\"border-collapse: collapse; border: 1px solid #DDD; width: 100%; \">\n"
						+ "                <thead>\n" + "                    <tr>\n"
						+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Fault Code</th>\n"
						+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Site Name</th>\n"
						+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Device Name</th>\n"
						+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Message</th>\n"
						+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Open Date</th>\n"
						+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: left;\">Close Date</th>\n"
						+ "                        <th style=\"padding: 5px 10px; border: 1px solid #DDD; background: #f0f2f5; text-align: center;\">Status</th>\n"
						+ "                    </tr>\n" + "                </thead>\n"

						+ "                <tbody>\n");

				StringBuilder tBody = new StringBuilder();
				if (listAlertOpenBySite.size() > 0) {
					// Get list alert open
					for (int j = 0; j < listAlertOpenBySite.size(); j++) {
						Map<String, Object> rowItem = (Map<String, Object>) listAlertOpenBySite.get(j);
						tBody.append("<tr>\n");
						tBody.append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">")
								.append(rowItem.get("error_code")).append("</td>");
						tBody.append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">")
								.append(siteObj.getName()).append("</td>");
						tBody.append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">")
								.append(rowItem.get("devicename")).append("</td>");
						tBody.append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">")
								.append(rowItem.get("message")).append("</td>");
						tBody.append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">")
								.append(rowItem.get("start_date")).append("</td>");
						tBody.append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">")
								.append(rowItem.get("end_date")).append("</td>");
						tBody.append("<td style=\"padding: 5px 10px; border: 1px solid #DDD; text-align: center;\">")
								.append(rowItem.get("status")).append("</td>");
						tBody.append("</tr>");

						// Close alert sent mail
						AlertEntity alertItem = new AlertEntity();
						alertItem.setId(Integer.parseInt(rowItem.get("id").toString()));
						alertItem.setId_device(Integer.parseInt(rowItem.get("id_device").toString()));
						service.updateOpenSentAlert(alertItem);
					}
				}

				// get list alert close
				List listAlertCloseBySite = service.getListAlertCloseBySite(siteObj);
				if (listAlertCloseBySite.size() > 0) {
					// Get list alert open
					for (int k = 0; k < listAlertCloseBySite.size(); k++) {
						Map<String, Object> rowItem = (Map<String, Object>) listAlertCloseBySite.get(k);
						tBody.append("<tr>\n");
						tBody.append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">")
								.append(rowItem.get("error_code")).append("</td>");
						tBody.append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">")
								.append(siteObj.getName()).append("</td>");
						tBody.append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">")
								.append(rowItem.get("devicename")).append("</td>");
						tBody.append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">")
								.append(rowItem.get("message")).append("</td>");
						tBody.append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">")
								.append(rowItem.get("start_date")).append("</td>");
						tBody.append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">")
								.append(rowItem.get("end_date")).append("</td>");
						tBody.append("<td style=\"padding: 5px 10px; border: 1px solid #DDD; text-align: center;\">")
								.append(rowItem.get("status")).append("</td>");
						tBody.append("</tr>");

						// Close alert sent mail
						AlertEntity alertItem = new AlertEntity();
						alertItem.setId(Integer.parseInt(rowItem.get("id").toString()));
						alertItem.setId_device(Integer.parseInt(rowItem.get("id_device").toString()));
						service.updateCloseSentAlert(alertItem);
					}
				}

				bodyHtml.append(tBody);
				bodyHtml.append("</tbody>\n" + "            </table>"
						+ "<br/><p>For more details on the alert, visit the Next Wave Energy Monitoring login portal below. If you wish to change any of your notification settings, do not hesitate to contact us at <a href=\"mailto:support@nwemon.com\">support@nwemon.com</a> or (800) 644-0839. </p>"
						+ "<div style=\"text-align: center; \" class=\"login-portal\"><a style=\"display: inline-block; background: #ffda00; padding: 5px 30px; color: #000; margin-top: 30px; border-radius: 4px; text-decoration: none; \" href=\""
						+ domain + "/management/sites/" + hash_id
						+ "/dashboard\" target=\"_blank\">Site Overview</a></div>"
						+ "<div class=\"regards\"><br><p>Regards,</p><p>Next Wave Team</p><p><a href=\"https://nwemon.com\" target=\"_blank\"><img width=\"100px\" src=\"https://nwemon.com/public/uploads/system_setting_images/logo-colored-1642026858.png\"></a></p></div>"
						+ "</div>");

				if (tBody.length() > 0) {
					// Sent mail
					String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName,
							Constants.mailFromContact);
//								String mailTo = "vanlong200880@gmail.com";
					String mailTo = siteObj.getCf_email_subscribers();
					String mailToBCC = siteObj.getAlert_mail_bcc();
					String mailToCC = siteObj.getAlert_mail_cc();

					// Remove email employees who hide a site
					List emails = service.getEmployeeHidingSite(siteObj);
					if (emails != null && emails.size() > 0) {
						for (int j = 0; j < emails.size(); j++) {
							Map<String, Object> itemT = (Map<String, Object>) emails.get(j);
							String email = itemT.get("email").toString();

							mailTo = mailTo.replaceAll("\\b(" + email + "(,)|(,)" + email + ")?", "");
						}
					}

					String subject = " Next Wave Alert - ".concat(siteObj.getName());
					String tags = "run_cron_job";
					String fromName = "NEXT WAVE ENERGY MONITORING INC";
					if (mailTo != null && !mailTo.isEmpty()) {
						boolean flagSent = SendMail.SendGmailTLS(mailFromContact, fromName, mailTo, mailToCC, mailToBCC,
								subject, bodyHtml.toString(), tags);

						if (!flagSent) {
							throw new Exception(Translator.toLocale(Constants.SEND_MAIL_ERROR_MSG));
						}
					}

				}

			}

			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description get no communication
	 * @author long.pham
	 * @since 2023-07-20
	 * @return {}
	 */
	@GetMapping("/get-eer-last-month")
	@ResponseBody
	public Object renderGetEERLastMonth(@RequestParam Map<String, Object> params) {
		try {
			String privateKey = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.privateKey);
			String token = (String) params.get("token");
			if(token == null || token == "" || !token.equals(privateKey)) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}		
			
			CronJobAlertService service = new CronJobAlertService();
			SiteEntity entitySite = new SiteEntity();
			
			// Get list site of id and eer_last_month
			List<?> listSites = service.getListSiteEERLastMonth(entitySite);
			if (listSites.size() > 0) {
				for (int s = 0; s < listSites.size(); s++) {
					SiteEntity siteObj = (SiteEntity) listSites.get(s);

					service.updateSiteEERLastMonth(siteObj);
				}
			}
		
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description get no communication
	 * @author long.pham
	 * @since 2023-07-20
	 * @return {}
	 */
	@GetMapping("/get-eer-this-month")
	@ResponseBody
	public Object renderGetEERThisMonth(@RequestParam Map<String, Object> params) {
		try {
			String privateKey = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.privateKey);
			String token = (String) params.get("token");
			if(token == null || token == "" || !token.equals(privateKey)) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}		
			
			CronJobAlertService service = new CronJobAlertService();
			SiteEntity entitySite = new SiteEntity();
			
			// Get list site of id and eer_last_month
			List<?> listSites = service.getListSiteEERThisMonth(entitySite);
			if (listSites.size() > 0) {
				for (int s = 0; s < listSites.size(); s++) {
					SiteEntity siteObj = (SiteEntity) listSites.get(s);

					service.updateSiteEERThisMonth(siteObj);
				}
			}
		
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
}
