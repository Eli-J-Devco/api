/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.batchjob;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.TimeZone;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.nwm.api.controllers.ReportsController;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.BatchJobTableEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ErrorEntity;
import com.nwm.api.entities.ModelSolarOpenWeatherEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.entities.WeatherEntity;
import com.nwm.api.services.BatchJobService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.FLLogger;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SendMail;
import com.nwm.api.utils.Translator;
import com.nwm.api.utils.SecretCards;

public class BatchJob {
	protected final FLLogger log = FLLogger.getLogger("batchjob/" + this.getClass().getSimpleName());
	
	
	
	public void runCronJobSolarOpenWeather() {
		try {
			BatchJobService service = new BatchJobService();
			
			// Get list site 
			List listDevice = service.getListDeviceSolarOpenWeather(new DeviceEntity());
			if (listDevice == null || listDevice.size() == 0) {
				return;
			}
						
			for (int i = 0; i < listDevice.size(); i++) {
				DeviceEntity deviceItem = (DeviceEntity) listDevice.get(i);
				// Get weather API
				double lat = (double) deviceItem.getLat();
				double lon = (double) deviceItem.getLng();
				ModelSolarOpenWeatherEntity modelSolarOpenWeather = fetchFromJSONSolarOpenWeather(lat, lon);
				modelSolarOpenWeather.setId_device(deviceItem.getId());
//				weather.setId_site(siteItem.getId());
//				
//				if(weather.getWeather_icon() == null || weather.getWeather_description() == null) {
//					weather.setWeather_description(null);
//					weather.setWeather_icon(null);
//				}	
//				
//				// Update site weather
//				service.updateWeather(weather);
				
			}			
		} catch (Exception e) {
			log.error(e);
		}

	}
	
	
	public static ModelSolarOpenWeatherEntity fetchFromJSONSolarOpenWeather(double lat, double lon)
			throws FileNotFoundException, IOException, org.json.simple.parser.ParseException {

		try {
			String inline = "";
			ModelSolarOpenWeatherEntity item = new ModelSolarOpenWeatherEntity();
			String APIURL = Constants.OPEN_WEATHER_URL + "?lat=" + lat + "&lon=" + lon + "&api_key="
					+ Constants.OPEN_WEATHER_KEY;
			URL url = new URL(APIURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			if (responsecode == 200) {
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();
				JSONParser parse = new JSONParser();
				JSONObject jobj = (JSONObject) parse.parse(inline);
				JSONArray jsonarr_1 = (JSONArray) jobj.get("weather");
				for (int k = 0; k < jsonarr_1.size(); k++) {
					JSONObject jsonobj_1 = (JSONObject) jsonarr_1.get(k);
					String weatherIcon = (String) jsonobj_1.get("icon");
					String weatherDescription = (String) jsonobj_1.get("description");
//					item.setWeather_icon(weatherIcon);
//					item.setWeather_description(weatherDescription);
				}
			}
			return item;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public static WeatherEntity fetchFromJSONNext(double lat, double lon)
			throws FileNotFoundException, IOException, org.json.simple.parser.ParseException {

		try {
			String inline = "";
			WeatherEntity item = new WeatherEntity();
			String APIURL = Constants.weatherAPIURL + "?lat=" + lat + "&lon=" + lon + "&appid="
					+ Constants.weatherAPIKEY + "&units=imperial&lang=en";
			URL url = new URL(APIURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			if (responsecode == 200) {
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();
				JSONParser parse = new JSONParser();
				JSONObject jobj = (JSONObject) parse.parse(inline);
				JSONArray jsonarr_1 = (JSONArray) jobj.get("weather");
				for (int k = 0; k < jsonarr_1.size(); k++) {
					JSONObject jsonobj_1 = (JSONObject) jsonarr_1.get(k);
					String weatherIcon = (String) jsonobj_1.get("icon");
					String weatherDescription = (String) jsonobj_1.get("description");
					item.setWeather_icon(weatherIcon);
					item.setWeather_description(weatherDescription);
				}
			}
			return item;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public void runCronJobGetWeather() {
		try {
			BatchJobService service = new BatchJobService();
			
			// Get list site 
			List listSite = service.getListSite(new SiteEntity());
			if (listSite == null || listSite.size() == 0) {
				return;
			}
						
			for (int i = 0; i < listSite.size(); i++) {
				SiteEntity siteItem = (SiteEntity) listSite.get(i);
				// Get weather API
				double lat = (double) siteItem.getLat();
				double lon = (double) siteItem.getLng();
				WeatherEntity weather = fetchFromJSONNext(lat, lon);
				weather.setId_site(siteItem.getId());
				
				if(weather.getWeather_icon() == null || weather.getWeather_description() == null) {
					weather.setWeather_description(null);
					weather.setWeather_icon(null);
				}	
				
				// Update site weather
				service.updateWeather(weather);
			}			
		} catch (Exception e) {
			log.error(e);
		}

	}
	
	
	
	
	public void runCronJobUpdateEnergyLifetime() {
		try {
			BatchJobService service = new BatchJobService();
			
			// Get list site 
			List<?> listDevice = service.getListMeterAndInverter(new DeviceEntity());
			if (listDevice == null || listDevice.size() == 0) { return; }		
			for (int i = 0; i < listDevice.size(); i++) {
				DeviceEntity deviceItem = (DeviceEntity) listDevice.get(i);
				DeviceEntity rowItem = service.getDataDeviceUpdateLifetime(deviceItem);
				if(rowItem != null) {
					// Update energy lifetime
					service.updateDataDeviceLifetime(rowItem);
				}
			}			
		} catch (Exception e) {
			log.error(e);
		}

	}
	
	
	
	
	public void runCronJobUpdateEnergyToday() {
		try {
			BatchJobService service = new BatchJobService();
			
			// Get list site 
			List<?> listDevice = service.getListMeterAndInverter(new DeviceEntity());
			if (listDevice == null || listDevice.size() == 0) { return; }		
			for (int i = 0; i < listDevice.size(); i++) {
				DeviceEntity deviceItem = (DeviceEntity) listDevice.get(i);
				
				///converting date format for US
				Date date = new Date();
		        SimpleDateFormat sdfAmerica = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		        TimeZone tzInAmerica = TimeZone.getTimeZone("America/Los_Angeles");
		        sdfAmerica.setTimeZone(tzInAmerica);

				deviceItem.setCurrent_time(sdfAmerica.format(date));
				DeviceEntity rowItem = service.getDataDeviceUpdateEnergyToday(deviceItem);
				if(rowItem.getId() <= 0) {
					rowItem.setDatatablename(deviceItem.getDatatablename());
					rowItem.setId(deviceItem.getId());
					rowItem.setEnergy_today(null);
				}
				service.updateDataDeviceEnergyToday(rowItem);
			}			
		} catch (Exception e) {
			log.error(e);
		}

	}
	
	
	
	
	public void runCronJobUpdateEnergyThisMonth() {
		try {
			BatchJobService service = new BatchJobService();
			
			// Get list site 
			List<?> listDevice = service.getListMeterAndInverter(new DeviceEntity());
			if (listDevice == null || listDevice.size() == 0) { return; }		
			for (int i = 0; i < listDevice.size(); i++) {
				DeviceEntity deviceItem = (DeviceEntity) listDevice.get(i);
				
				// converting date format for US
				Date date = new Date();
		        SimpleDateFormat sdfAmerica = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		        TimeZone tzInAmerica = TimeZone.getTimeZone("America/Los_Angeles");
		        sdfAmerica.setTimeZone(tzInAmerica);
				deviceItem.setCurrent_time(sdfAmerica.format(date));
				
				DeviceEntity rowItem = service.getDataDeviceEnergyThisMonth(deviceItem);
				if(rowItem.getId() <= 0) {
					rowItem.setDatatablename(deviceItem.getDatatablename());
					rowItem.setId(deviceItem.getId());
					rowItem.setEnergy_this_month(null);
				}
				service.updateDataDeviceEnergyThisMonth(rowItem);
			}			
		} catch (Exception e) {
			log.error(e);
		}

	}
	
	




	public void runCronJobAutoSentMailAlert() {
		try {
			BatchJobService service = new BatchJobService();
			
			// Get list site 
			List listSite = service.getListSiteSentMailAlert(new SiteEntity());
			if (listSite == null || listSite.size() == 0) {
				return;
			}
			SecretCards secretCard = new SecretCards();
						
			for (int i = 0; i < listSite.size(); i++) {
				SiteEntity siteObj = (SiteEntity) listSite.get(i);
				String idSite =  String.valueOf(siteObj.getId());
				String hash_id = secretCard.encrypt(idSite).toLowerCase();
				String domain = Lib.getDomain();
				
				List listAlertOpenBySite = service.getListAlertOpenBySite(siteObj);
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
				if(listAlertOpenBySite.size() > 0) {
					// Get list alert open 
					for (int j = 0; j < listAlertOpenBySite.size(); j++) {
						Map<String, Object> rowItem = (Map<String, Object>) listAlertOpenBySite.get(j);
						tBody.append("<tr>\n");
						tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(rowItem.get("error_code")).append("</td>");
						tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(siteObj.getName()).append("</td>");
						tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(rowItem.get("devicename")).append("</td>");
						tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(rowItem.get("message")).append("</td>");
						tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(rowItem.get("start_date")).append("</td>");
						tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(rowItem.get("end_date")).append("</td>");
						tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD; text-align: center;\">").append(rowItem.get("status")).append("</td>");
						tBody .append("</tr>");
						
						// Close alert sent mail 
						AlertEntity alertItem = new AlertEntity();
						alertItem.setId(Integer.parseInt(rowItem.get("id").toString()));
						alertItem.setId_device(Integer.parseInt(rowItem.get("id_device").toString()));
						service.updateOpenSentAlert(alertItem);
					}
				}
				
				// get list alert close
				List listAlertCloseBySite = service.getListAlertCloseBySite(siteObj);
				if(listAlertCloseBySite.size() > 0) {
					// Get list alert open 
					for (int k = 0; k < listAlertCloseBySite.size(); k++) {
						Map<String, Object> rowItem = (Map<String, Object>) listAlertCloseBySite.get(k);
						tBody.append("<tr>\n");
						tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(rowItem.get("error_code")).append("</td>");
						tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(siteObj.getName()).append("</td>");
						tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(rowItem.get("devicename")).append("</td>");
						tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(rowItem.get("message")).append("</td>");
						tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(rowItem.get("start_date")).append("</td>");
						tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD;\">").append(rowItem.get("end_date")).append("</td>");
						tBody .append("<td style=\"padding: 5px 10px; border: 1px solid #DDD; text-align: center;\">").append(rowItem.get("status")).append("</td>");
						tBody .append("</tr>");
						
						// Close alert sent mail 
						AlertEntity alertItem = new AlertEntity();
						alertItem.setId(Integer.parseInt(rowItem.get("id").toString()));
						alertItem.setId_device(Integer.parseInt(rowItem.get("id_device").toString()));
						service.updateCloseSentAlert(alertItem);
					}
				}
				
				
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
//					String mailTo = "vanlong200880@gmail.com,lpham@phoenixrs.com";
					String mailTo = siteObj.getCf_email_subscribers();
					String subject = " Next Wave Alert - ".concat(siteObj.getName());
					String tags = "run_cron_job";
					String fromName = "NEXT WAVE ENERGY MONITORING INC";
					if(mailTo != null && !mailTo.isEmpty()) {
						boolean flagSent = SendMail.SendGmailTLS(mailFromContact, fromName, mailTo, subject, bodyHtml.toString(), tags);
						
						if (!flagSent) {
							throw new Exception(Translator.toLocale(Constants.SEND_MAIL_ERROR_MSG));
						}
					}
					
				}
				
			}			
		} catch (Exception e) {
			log.error(e);
		}

	}

	public void runCronJobCloseAlert() {
		try {
			BatchJobService service = new BatchJobService();
			AlertEntity entity = new AlertEntity();

			List listAlert = service.getListAlertCronJob(entity);
			if (listAlert == null || listAlert.size() == 0) {
				return;
			}

			for (int i = 0; i < listAlert.size(); i++) {
				BatchJobTableEntity obj = new BatchJobTableEntity();
				Map<String, Object> item = (Map<String, Object>) listAlert.get(i);
				String datatablename = (String) item.get("datatablename");
				int idDevice = (int) item.get("id_device");
				int id = Integer.parseInt(item.get("id").toString());
				obj.setDatatablename(datatablename);
				obj.setId_device(idDevice);
				obj.setId(id);
				
				int errorCode = (int) item.get("id_error");
				if(errorCode > 0) {
					Date now = new Date();
					TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
					String CurrentDate = format.format(now);
					obj.setEnd_date(CurrentDate);
					
					switch(errorCode) {
					// No production
					case 48:
					case 53:
					case 54:
					case 55:
					case 56:
					case 57:
					case 58:
					case 59:
					case 60:
					case 61:
					// No communication
					case 49:
					case 62:
					case 63:
					case 64:
					case 65:
					case 66:
					case 67:
					case 68:
					case 69:
					case 70:
						BatchJobTableEntity lastRow = service.getLastRowItem(obj);
						if(lastRow.getNvmActivePower() > 0) {
							service.updateCloseAlert(obj);
						}
						break;
					default:
						// Update close alert
						BatchJobTableEntity rowItem = service.getLastRowItemAutoCloseAlert(obj);
						if (rowItem.getId_device() > 0) {
							if (errorCode != rowItem.getError()) {
								service.updateCloseAlert(obj);
							}
						}
						break;
					}
				}
				
				
			}

		} catch (Exception e) {
			log.error(e);
		}

	}

	public void runCronJobGetNoProduction() {
		try {
			BatchJobService service = new BatchJobService();
			DeviceEntity entity = new DeviceEntity();

			// Get list site 
			List<?> listSites = service.getListSiteCheckNoCom(entity);
			if(listSites.size() > 0) {
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
			        
			        if (hourOfDay >= (objSite.getStart_date_time() + 1) && hourOfDay <= (objSite.getEnd_date_time() - 1)) {
			        	// Check alert datalogger no communication 
						DeviceEntity objDatalogger = service.getDeviceDatalogger(objSite.getId());
						if(objDatalogger.getId() > 0 ) {
							// Get list device meter and inverter
							DeviceEntity dEntity = new DeviceEntity();
							dEntity.setId_site(objSite.getId());
							List<?> listDeviceBySite = service.getListMeterAndInverterBySite(dEntity);
							if(listDeviceBySite.size() > 0 ) {
								for (int i = 0; i < listDeviceBySite.size(); i++) {
									DeviceEntity obj = (DeviceEntity) listDeviceBySite.get(i);
									if (obj.getTimezone_value() != null) {
										// No Production
										bathJobEntity.setId(obj.getId());
										bathJobEntity.setCurrent_time(CurrentDate);
										bathJobEntity.setStart_date_time(obj.getStart_date_time());
										bathJobEntity.setEnd_date_time(obj.getEnd_date_time());
										bathJobEntity.setDatatablename(obj.getDatatablename());
										bathJobEntity.setId_device(obj.getId());
										
										int noProduction = 0;
										switch(obj.getDatatablename()) {
											case "model_shark100": noProduction = 48; break;
											case "model_ivt_solaron_ext": noProduction = 53; break;
											case "model_pvp_inverter": noProduction = 54; break;
											case "model_advanced_energy_solaron": noProduction = 55; break;
											case "model_chint_solectria_inverter_class9725": noProduction = 56; break;
											case "model_veris_industries_e51c2_power_meter": noProduction = 57; break;
											case "model_satcon_pvs357_inverter": noProduction = 58; break;
											case "model_elkor_wattson_pv_meter": noProduction = 59; break;
											case "model_elkor_production_meter": noProduction = 60; break;
											case "model_abb_trio_class6210": noProduction = 61; break;
											case "model_solectria_sgi_226ivt": noProduction = 133; break;
											case "model_pv_powered_35_50_260_500kw_inverter": noProduction = 135; break;
										}
										
										
										BatchJobTableEntity rowItem = service.getLastRowItemCheckNoProduction(bathJobEntity);
										if(rowItem.getNvmActivePower() != 0.001) {
											if (rowItem.getId_device() > 0 && rowItem.getNvmActivePower() <= 0 ) {
												AlertEntity alertItem = new AlertEntity();
												alertItem.setId_device(obj.getId());
												alertItem.setId_error(noProduction);
												alertItem.setStart_date( !Lib.isBlank(obj.getLast_updated()) ? obj.getLast_updated() : sDateUTC);
												// Check error exits
												boolean checkAlertExist = service.checkAlertExist(alertItem);
												if (!checkAlertExist && alertItem.getId_device() > 0 && alertItem.getId_error() > 0 ) {
													// Insert error
													service.insertAlert(alertItem);
												}
											}else{
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
		} catch (Exception e) {
			System.out.println("Error check no production: " + e);
			log.error(e);
		}
	}
	
	
	public void runCronJobGetNoCommunication() {
		try {
			BatchJobService service = new BatchJobService();
			DeviceEntity entity = new DeviceEntity();
			
			// Get list site 
			List<?> listSites = service.getListSiteCheckNoCom(entity);
			if(listSites.size() > 0) {
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
			        
			        
			        if (hourOfDay >= (objSite.getStart_date_time() + 1) && hourOfDay <= (objSite.getEnd_date_time() - 1 )) {
			        	String flag = "off";
			        	// Check alert datalogger no communication 
						DeviceEntity objDatalogger = service.getDeviceDatalogger(objSite.getId());
						if(objDatalogger.getId() > 0 ) {
							bathJobEntity.setCurrent_time(sDateUTC);
							bathJobEntity.setId_device(objDatalogger.getId());
							
							AlertEntity alertItem = new AlertEntity();
							alertItem.setId_device(objDatalogger.getId());
							alertItem.setId_error(136);
							alertItem.setStart_date(sDateUTC);
							
							// Get last data from datalogger 
							BatchJobTableEntity dataDatalogger = service.getDataloggerItem(bathJobEntity);
							if(dataDatalogger.getId_device() <= 0) {
								// Check error exits
								boolean checkAlertExist = service.checkAlertExist(alertItem);
								if (!checkAlertExist && alertItem.getId_device() > 0 && alertItem.getId_error() > 0 ) {
									// Insert error
									service.insertAlert(alertItem);
									flag = "off";
								}
							} else {
								flag = "on";
								// Close alert no com datalogger
								// Close no communication 
								AlertEntity checkAlertExist = (AlertEntity) service.getAlertDetail(alertItem);
								if(checkAlertExist.getId() > 0){
									bathJobEntity.setEnd_date(sDateUTC);
									bathJobEntity.setId(checkAlertExist.getId());
									bathJobEntity.setId_device(checkAlertExist.getId_device());
									service.updateCloseAlert(bathJobEntity);
								}
								
							}
						} else {
							flag = "on";
						}
						
						if("on".equals(flag)) {
							// Check alert device 
							// Get list device meter and inverter
							DeviceEntity dEntity = new DeviceEntity();
							dEntity.setId_site(objSite.getId());
							List<?> listDeviceBySite = service.getListMeterAndInverterBySite(dEntity);
							if(listDeviceBySite.size() > 0) {
								for (int i = 0; i < listDeviceBySite.size(); i++) {
									DeviceEntity obj = (DeviceEntity) listDeviceBySite.get(i);
									if (obj.getTimezone_value() != null) {
										bathJobEntity.setId(obj.getId());
										bathJobEntity.setCurrent_time(CurrentDate);
										bathJobEntity.setStart_date_time(obj.getStart_date_time());
										bathJobEntity.setEnd_date_time(obj.getEnd_date_time());
										bathJobEntity.setDatatablename(obj.getDatatablename());
										bathJobEntity.setId_device(obj.getId());
										
										int noCommunication = 0;
										switch(obj.getDatatablename()) {
											case "model_shark100": noCommunication = 49; break;
											case "model_ivt_solaron_ext": noCommunication = 62; break;
											case "model_pvp_inverter": noCommunication = 63; break;
											case "model_advanced_energy_solaron": noCommunication = 64; break;
											case "model_chint_solectria_inverter_class9725": noCommunication = 65; break;
											case "model_veris_industries_e51c2_power_meter": noCommunication = 66; break;
											case "model_satcon_pvs357_inverter": noCommunication = 67; break;
											case "model_elkor_wattson_pv_meter": noCommunication = 68; break;
											case "model_elkor_production_meter": noCommunication = 69; break;
											case "model_abb_trio_class6210": noCommunication = 70; break;
											case "model_solectria_sgi_226ivt": noCommunication = 132; break;
											case "model_pv_powered_35_50_260_500kw_inverter": noCommunication = 134; break;
										}
										
										BatchJobTableEntity lastRowItem = service.getLastRowItemCheckNoCommunication(bathJobEntity);
										AlertEntity alertItem = new AlertEntity();
										alertItem.setId_device(obj.getId());
										alertItem.setId_error(noCommunication);
										alertItem.setStart_date( !Lib.isBlank(obj.getLast_updated()) ? obj.getLast_updated() : sDateUTC);
										System.out.println(lastRowItem.getId_device() + " - " + lastRowItem.getNvmActivePower());	
										
										System.out.println(Lib.isBlank(lastRowItem.getNvmActivePower()));	
										
										if (lastRowItem.getId_device() <= 0 || lastRowItem.getNvmActivePower() == 0.001) {
											// Check error exits
											boolean checkAlertExist = service.checkAlertExist(alertItem);
											if (!checkAlertExist && alertItem.getId_device() > 0 && alertItem.getId_error() > 0 ) {
												// Insert error
												service.insertAlert(alertItem);
											}
											
										} else {
											// Close no communication 
											AlertEntity checkAlertExist = (AlertEntity) service.getAlertDetail(alertItem);
											if(checkAlertExist.getId() > 0){
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
	
		} catch (Exception e) {
			System.out.println("Error: check no communication " + e);
			log.error(e);
		}
	}
	
	
	
	
	public void runCronJobResetLastValue() {
		try {
			BatchJobService service = new BatchJobService();
			DeviceEntity entityDevice = new DeviceEntity();
			BatchJobTableEntity bathJobEntity = new BatchJobTableEntity();
			// Get list device meter and inverter
			List<?> listDevice = service.getListAllDevice(entityDevice);
			if (listDevice == null || listDevice.size() == 0) {
				return;
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
		        System.out.println("Run batchJob reset last value device: "+ lastRowItem.getId_device());
		        
		        if(lastRowItem.getId_device() <= 0 || lastRowItem.getNvmActivePower() == -1 ) {
		        	DeviceEntity deviceObj = new DeviceEntity(); 
		        	deviceObj.setId(obj.getId());
		        	deviceObj.setLast_value(null);
		        	service.updateLastValueDevice(deviceObj);
		        }
			}

		} catch (Exception e) {
			System.out.println("Error: reset last value " + e);
			log.error(e);
		}

	}
	
	
	public void runCronJobCloseAlertFromDatalogger() {
		try {
			BatchJobService service = new BatchJobService();
			DeviceEntity entityDevice = new DeviceEntity();
			ErrorEntity entityError = new ErrorEntity();
			// Get list device meter and inverter
			List<?> listDevice = service.getListAllDevice(entityDevice);
			if (listDevice == null || listDevice.size() == 0) {
				return;
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
		        bathJobEntity.setDatatablename(obj.getDatatablename());
		        
		        BatchJobTableEntity lastRowItem = service.getLastRowItem(bathJobEntity);	
		        System.out.println("Run batchJob close alert from datalogger: "+ obj.getId());
		        if(lastRowItem.getError() == 0) {		    	   
//		        	Close all alert by datalogger
//		    	    get all error by id_device_group, type datalogger alert

		        	entityError.setId_device_group(obj.getId_device_group());
		        	entityError.setId_device(obj.getId());
		    	   List<?> listError = service.getListErrorByType(entityError);

		    	   if(listError.size() > 0) {
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
		} catch (Exception e) {
			System.out.println("Error: close alert from dataloggger " + e);
			log.error(e);
		}

	}
	
	
	public void runCronJobGenerateDataReport() {
		try {
			BatchJobService service = new BatchJobService();
			DeviceEntity entity = new DeviceEntity();
			
			// Get list site 
			List<?> listSites = service.getListSiteCheckNoCom(entity);
			if(listSites.size() > 0) {
				for (int s = 0; s < listSites.size(); s++) {
					SiteEntity objSite = (SiteEntity) listSites.get(s);
					service.insertDataGenerateReport(objSite);
				}
			}
	
		} catch (Exception e) {
			System.out.println("Error: check no communication " + e);
			log.error(e);
		}
	}
	
	
	
	
	public void startBatchJobGeneratePerformanceRatio() {
		try {
			BatchJobService service = new BatchJobService();
			DeviceEntity entity = new DeviceEntity();
			
			// Get list site 
			List<?> listSites = service.getListSiteCheckNoCom(entity);
			if(listSites.size() > 0) {
				for (int s = 0; s < listSites.size(); s++) {
					SiteEntity objSite = (SiteEntity) listSites.get(s);
					service.updateDataGeneratePerformanceRatio(objSite);
				}
			}
	
		} catch (Exception e) {
			System.out.println("Error: check no communication " + e);
			log.error(e);
		}
	}
	
	
	public void sentMailReportOnSchedule(int cadenceRange) {
		try {
			BatchJobService service = new BatchJobService();
			ViewReportEntity entity = new ViewReportEntity();
			entity.setCadence_range(cadenceRange);
			
			// Get list reports
			List<?> listReports = service.getListReportsByCadence(entity);
			
			if(listReports.size() > 0) {
				for (int s = 0; s < listReports.size(); s++) {
					ViewReportEntity objReport = (ViewReportEntity) listReports.get(s);
					TimeZone timeZone = TimeZone.getTimeZone(objReport.getTime_zone());
					Calendar calQ = Calendar.getInstance();
					calQ.setTimeZone(timeZone);
					String timeSchedule = null;
					SimpleDateFormat timeScheduleFormat = null;
					SimpleDateFormat startDateFormat = null;
					SimpleDateFormat endDateFormat = null;
					
					if (cadenceRange == 1) {
						timeSchedule = "20";
						timeScheduleFormat = new SimpleDateFormat("HH");
						startDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00");
						endDateFormat = new SimpleDateFormat("yyyy-MM-dd 23:59");
					} else {
						timeSchedule = calQ.get(Calendar.YEAR) + "-" + (calQ.get(Calendar.MONTH) + 1) + "-" + calQ.getActualMaximum(Calendar.DATE) + " 20";
						timeScheduleFormat = new SimpleDateFormat("yyyy-MM-dd HH");
						startDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
						endDateFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
					}
					
					timeSchedule = timeScheduleFormat.format(timeScheduleFormat.parse(timeSchedule));
					timeScheduleFormat.setTimeZone(timeZone);
					startDateFormat.setTimeZone(timeZone);
					endDateFormat.setTimeZone(timeZone);
					
					// convert local time (server) to time on site and compare to time schedule
					if (timeSchedule.equals(timeScheduleFormat.format(calQ.getTime()))) {
						ReportsController controller = new ReportsController();
						objReport.setEnd_date(endDateFormat.format(calQ.getTime()));
						
						switch (cadenceRange) {
						case 1:
							// daily
							calQ.add(Calendar.DATE, -2);
							objReport.setStart_date(startDateFormat.format(calQ.getTime()));
							
							// sent mail
							if (objReport.getFile_type() == 1) {
								controller.sentMailPdfDailyReport(objReport);
							} else if (objReport.getFile_type() == 2) {
								controller.sentMailDailyReport(objReport);
							}
							break;
							
						case 2:
							// monthly
							calQ.set(Calendar.DATE, 1);
							objReport.setStart_date(startDateFormat.format(calQ.getTime()));
							
							// sent mail
							if (objReport.getFile_type() == 1) {
							controller.sentMailPdfMonthlyReport(objReport);
							} else if (objReport.getFile_type() == 2) {
								controller.sentMailMonthlyReport(objReport);
							}
							break;
							
						case 3:
							// quarterly
							calQ.set(Calendar.DATE, 1);
							calQ.add(Calendar.MONTH, -2);
							objReport.setStart_date(startDateFormat.format(calQ.getTime()));
							
							// sent mail
							if (objReport.getFile_type() == 1) {
								controller.sentMailPdfQuarterlyReport(objReport);
							} else if (objReport.getFile_type() == 2) {
								controller.sentMailQuarterlyReport(objReport);
							}
							break;
							
						case 4:
							// annually
							calQ.set(Calendar.DATE, 1);
							calQ.set(Calendar.MONTH, 1);
							objReport.setStart_date(startDateFormat.format(calQ.getTime()));
							
							// sent mail
							if (objReport.getFile_type() == 1) {
								controller.sentMailPdfAnnuallyReport(objReport);
							} else if (objReport.getFile_type() == 2) {
								controller.sentMailAnnuallyReport(objReport);
							}
							break;

						default:
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error: sent mail report on schedule " + e);
			log.error(e);
		}
	}
	
}
