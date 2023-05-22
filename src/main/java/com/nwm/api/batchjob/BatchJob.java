/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.batchjob;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.nwm.api.controllers.ReportsController;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.BatchJobTableEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ErrorEntity;
import com.nwm.api.entities.ModelCellModemEntity;
import com.nwm.api.entities.ModelDataloggerEntity;
import com.nwm.api.entities.ModelSolarOpenWeatherEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.entities.WeatherEntity;
import com.nwm.api.services.BatchJobService;
import com.nwm.api.services.DeviceService;
import com.nwm.api.services.ModelCellModemService;
import com.nwm.api.services.ModelDataloggerService;
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
											case "model_campell_scientific_meter1": noProduction = 764; break;
											case "model_campell_scientific_meter2": noProduction = 765; break;
											case "model_campell_scientific_meter3": noProduction = 766; break;
											case "model_campell_scientific_meter4": noProduction = 767; break;
											case "model_xantrex_gt100_250_500": noProduction = 762; break;
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
										if(obj.getView_tablename() != null) {
											bathJobEntity.setDatatablename(obj.getView_tablename());
										} else {
											bathJobEntity.setDatatablename(obj.getDatatablename());
										}
										
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
											case "model_xantrex_gt100_250_500": noCommunication = 763; break;
											case "model_campell_scientific_meter1": noCommunication = 771; break;
											case "model_campell_scientific_meter2": noCommunication = 770; break;
											case "model_campell_scientific_meter3": noCommunication = 769; break;
											case "model_campell_scientific_meter4": noCommunication = 768; break;
										}
										
										BatchJobTableEntity lastRowItem = service.getLastRowItemCheckNoCommunication(bathJobEntity);
										AlertEntity alertItem = new AlertEntity();
										alertItem.setId_device(obj.getId());
										alertItem.setId_error(noCommunication);
										alertItem.setStart_date( !Lib.isBlank(obj.getLast_updated()) ? obj.getLast_updated() : sDateUTC);
										
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
		        if(obj.getView_tablename() != null) {
		        	bathJobEntity.setDatatablename(obj.getView_tablename());
		        } else {
		        	bathJobEntity.setView_tablename(obj.getDatatablename());
		        	
		        }
		        
		        
		        
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
					
					log.infoSuffixes("MailReportOnSchedule", "Cadence: " + cadenceRange + "\nTimeSchedule: " + timeSchedule + "\nNow: " + timeScheduleFormat.format(calQ.getTime()) + "\nEqual: " + timeSchedule.equals(timeScheduleFormat.format(calQ.getTime())));
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
	
	public static SiteEntity fetchFromJSONSunriseSunset(double lat, double lng, String time_zone_value)
			throws FileNotFoundException, IOException, org.json.simple.parser.ParseException {
		try {
			String inline = "";
			SiteEntity item = new SiteEntity();
			String APIURL = Constants.sunriseSunsetAPI + "?lat=" + lat + "&lng=" + lng + "&formatted=0";
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
				JSONObject jsonobj_1 = (JSONObject) jobj.get("results");
				
				String sunrise = (String) jsonobj_1.get("sunrise");
				String sunset = (String) jsonobj_1.get("sunset");
				Instant timestampSunrise = Instant.parse(sunrise);
				Instant timestampSunset = Instant.parse(sunset);
				
				ZonedDateTime zdtSunrise = timestampSunrise.atZone(ZoneId.of(time_zone_value));
				int hourOfSunrise = zdtSunrise.getHour();
				item.setStart_date_time(hourOfSunrise);
				ZonedDateTime zdtSunset = timestampSunset.atZone(ZoneId.of(time_zone_value));
				int hourOfSunset = zdtSunset.getHour();
				item.setEnd_date_time(hourOfSunset);
				
			}
			return item;
		} catch (Exception e) {
			return null;
		}
	}
	
	public void runCronJobGetSunriseSunset() {
		try {
			BatchJobService service = new BatchJobService();
			
			// Get list site 
			List listSite = service.getListSite(new SiteEntity());
			if (listSite == null || listSite.size() == 0) {
				return;
			}
			
			for (int i = 0; i < listSite.size(); i++) {
				SiteEntity siteItem = (SiteEntity) listSite.get(i);
				
				// Get sunrise sunset API
				double lat = (double) siteItem.getLat();
				double lng = (double) siteItem.getLng();
				String time_zone_value = (String) siteItem.getTime_zone_value();
				
				if (lat == 0 && lng == 0) {
					siteItem.setStart_date_time(8);
					siteItem.setEnd_date_time(18);
				} else {
					SiteEntity res = fetchFromJSONSunriseSunset(lat, lng, time_zone_value);
					siteItem.setStart_date_time(res.getStart_date_time());
					siteItem.setEnd_date_time(res.getEnd_date_time());
				}
				
				// Update site sunrise and sunset time
				service.updateSunriseSunset(siteItem);				
			}
			
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	
	public void runCronJobSSHCellModem() throws Exception {
		// Get list device and id_device_type = 10
		BatchJobService service = new BatchJobService();
		List<?> listDevice = service.getListDeviceCelModem(new DeviceEntity());
		if (listDevice == null || listDevice.size() == 0) { return; }		
		for (int i = 0; i < listDevice.size(); i++) {
			DeviceEntity deviceItem = (DeviceEntity) listDevice.get(i);
			listFolderStructure(deviceItem.getSsh_user(), deviceItem.getSsh_pass(), deviceItem.getSsh_host(), Integer.parseInt(deviceItem.getSsh_port()), deviceItem.getId());
		}
	}
	
	public static void listFolderStructure(String username, String password, String host, int port, int id_device)
			throws Exception {
		if(host != null && username != null && password != null && port > 0) {
			// Save device cell modem 
			ModelCellModemService serviceCellModem = new ModelCellModemService();
			ModelCellModemEntity celModemEntity = new ModelCellModemEntity();
			
			DeviceEntity deviceUpdateE = new DeviceEntity();
			DeviceService serviceD = new DeviceService();
			
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		    final String utcTime = sdf.format(new Date());
		    celModemEntity.setTime(utcTime);
		    
		    // Reading SSH info
		    Session session = null;
			ChannelExec channel = null;
			String command = "get wan 2";
			
			ChannelExec channelcpu = null;
			String commandcpu = "get cpuload";
			
			ChannelExec channelbt = null;
			String commandbt = "get bandwidth transferred";
			
			ChannelExec channelsystem = null;
			String commandsystem = "get system";
			
			ChannelExec channeluptime = null;
			String commanduptime = "get uptime";
			
			ChannelExec channelscm = null;
			String commandscm = "support cellular-mdstatus 1";
			
			ChannelExec channelscs = null;
			String commandscs = "support cellular-simstatus 1";
			
			try {
				session = new JSch().getSession(username, host, port );
				session.setPassword(password);
				session.setConfig("StrictHostKeyChecking", "no");
				session.connect();

				channel = (ChannelExec) session.openChannel("exec");
				channel.setCommand(command);
				ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
				channel.setOutputStream(responseStream);
				InputStream is = channel.getInputStream();
				channel.connect();
				while (channel.isConnected()) { Thread.sleep(100); }
				
				
				channelcpu = (ChannelExec) session.openChannel("exec");
				channelcpu.setCommand(commandcpu);
				ByteArrayOutputStream responseStreamcpu = new ByteArrayOutputStream();
				channelcpu.setOutputStream(responseStreamcpu);
				InputStream iscpu = channelcpu.getInputStream();
				channelcpu.connect();
				while (channelcpu.isConnected()) { Thread.sleep(100); }
				
				channelbt = (ChannelExec) session.openChannel("exec");
				channelbt.setCommand(commandbt);
				ByteArrayOutputStream responseStreambt = new ByteArrayOutputStream();
				channelbt.setOutputStream(responseStreambt);
				InputStream isbt = channelbt.getInputStream();
				channelbt.connect();
				while (channelbt.isConnected()) { Thread.sleep(100); }
				
				
				channelsystem = (ChannelExec) session.openChannel("exec");
				channelsystem.setCommand(commandsystem);
				ByteArrayOutputStream responseStreamsystem = new ByteArrayOutputStream();
				channelsystem.setOutputStream(responseStreamsystem);
				InputStream issystem = channelsystem.getInputStream();
				channelsystem.connect();
				while (channelsystem.isConnected()) { Thread.sleep(100); }
				
				
				channeluptime = (ChannelExec) session.openChannel("exec");
				channeluptime.setCommand(commanduptime);
				ByteArrayOutputStream responseStreamuptime = new ByteArrayOutputStream();
				channeluptime.setOutputStream(responseStreamuptime);
				InputStream isuptime = channeluptime.getInputStream();
				channeluptime.connect();
				while (channeluptime.isConnected()) { Thread.sleep(100); }
				
				
				channelscm = (ChannelExec) session.openChannel("exec");
				channelscm.setCommand(commandscm);
				ByteArrayOutputStream responseStreamscm = new ByteArrayOutputStream();
				channelscm.setOutputStream(responseStreamscm);
				InputStream isscm = channelscm.getInputStream();
				channelscm.connect();
				while (channelscm.isConnected()) { Thread.sleep(100); }
				
				channelscs = (ChannelExec) session.openChannel("exec");
				channelscs.setCommand(commandscs);
				ByteArrayOutputStream responseStreamscs = new ByteArrayOutputStream();
				channelscs.setOutputStream(responseStreamscs);
				InputStream isscs = channelscs.getInputStream();
				channelscs.connect();
				while (channelscs.isConnected()) { Thread.sleep(100); }
				
				
				
				String CPULoad = null;
				String WANConnection = null;
				String ConnectionName = null;
				String ConnectionStatus = null;
				String ConnectionType = null;
				String ConnectionMethod = null;
				String IPAddress = null;
				String DefaultGateway = null;
				String DNSServers = null;
				String MTU = null;
				String IMEI = null;
				String IMSI = null;
				String ICCID = null;
				String SIMSLOT = null;
				String Band = null;
				String RSSI4 = null;
				String SINR4 = null;
				String RSRP4 = null;
				String RSRQ4 = null;
				String Channel4 = null;

				String RSSI3 = null;
				String SINR3 = null;
				String RSRP3 = null;
				String RSRQ3 = null;
				String Channel3 = null;
				
				String ALLDownload = null;
				String ALLUpload = null;
				String ALLTotal = null;
				String CellularDownload = null;
				String CellularUpload = null;
				String CellularTotal = null;
				String DeviceName = null;
				String ProductModel = null;
				String HardwareRevision = null;
				String SerialNumber = null;
				String FirmwareVersion = null;
				String uptime = null;
				String mode = null;
				String SystemMode = null;
				String Temperature = null;
				String SlotA = null;
				String SlotB = null;

				try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
					for (String line = br.readLine(); line != null; line = br.readLine()) {
						
						if (line.contains("WAN Connection [2]") && !line.contains("awk ")) {
							WANConnection = line.trim();
						}
						

						if (WANConnection != null && WANConnection.contains("WAN Connection [2]") && line.contains("MTU")
								&& !line.contains("awk ")) {
							MTU = line.split(":")[1];
						}

						if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
								&& line.contains("IP Address") && !line.contains("awk ")) {
							IPAddress = line.split(":")[1];
						}

						if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
								&& line.contains("Default Gateway") && !line.contains("awk ")) {
							DefaultGateway = line.split(":")[1];
						}

						if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
								&& line.contains("DNS Servers") && !line.contains("awk ")) {
							DNSServers = line.split(":")[1];
						}

						if (WANConnection != null && WANConnection.contains("WAN Connection [2]") && line.contains("IMEI")
								&& !line.contains("awk ")) {
							IMEI = line.split(":")[1];
						}


						if (WANConnection != null && WANConnection.contains("WAN Connection [2]") && line.contains("IMSI")
								&& !line.contains("awk ")) {
							IMSI = line.split(":")[1];
						}

						if (WANConnection != null && WANConnection.contains("WAN Connection [2]") && line.contains("ICCID")
								&& !line.contains("awk ")) {
							ICCID = line.split(":")[1];
						}

						if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
								&& line.contains("SIM SLOT") && !line.contains("awk ")) {
							SIMSLOT = line.split(":")[1];
						}

						if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
								&& line.contains("Connection Name") && !line.contains("awk ")) {
							ConnectionName = line.split(":")[1];
						}

						if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
								&& line.contains("Connection Status") && !line.contains("awk ")) {
							ConnectionStatus = line.split(":")[1];
						}

						if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
								&& line.contains("Connection Type") && !line.contains("awk ")) {
							ConnectionType = line.split(":")[1];
						}

						if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
								&& line.contains("Connection Method") && !line.contains("awk ")) {
							ConnectionMethod = line.split(":")[1];
						}

						if (WANConnection != null && WANConnection.contains("WAN Connection [2]") && line.contains("Band")
								&& !line.contains("awk ")) {
							Band = line.split(":")[1];
							Band = Band.trim();
						}

						if (Band != null && Band.equals("LTE Band 4 (AWS 1700/2100 MHz)")) {
							if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
									&& line.contains("RSSI") && !line.contains("awk ")) {
								RSSI4 = line.split(":")[1];
							}

							if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
									&& line.contains("SINR") && !line.contains("awk ")) {
								SINR4 = line.split(":")[1];
							}

							if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
									&& line.contains("RSRP") && !line.contains("awk ")) {
								RSRP4 = line.split(":")[1];
							}

							if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
									&& line.contains("RSRQ") && !line.contains("awk ")) {
								RSRQ4 = line.split(":")[1];
							}

							if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
									&& line.contains("Channel") && !line.contains("awk ")) {
								Channel4 = line.split(":")[1];
							}

						} else if (Band != null && Band.equals("LTE Band 13 (700 MHz)")) {
							if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
									&& line.contains("RSSI") && !line.contains("awk ")) {
								RSSI3 = line.split(":")[1];
							}

							if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
									&& line.contains("SINR") && !line.contains("awk ")) {
								SINR3 = line.split(":")[1];
							}

							if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
									&& line.contains("RSRP") && !line.contains("awk ")) {
								RSRP3 = line.split(":")[1];
							}

							if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
									&& line.contains("RSRQ") && !line.contains("awk ")) {
								RSRQ3 = line.split(":")[1];
							}

							if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
									&& line.contains("Channel") && !line.contains("awk ")) {
								Channel3 = line.split(":")[1];
							}
						}
					}

					celModemEntity.setIPAddress( IPAddress != null ? IPAddress.trim(): null);
					celModemEntity.setDefaultGateway(DefaultGateway !=null ? DefaultGateway.trim(): null);
					celModemEntity.setDNSServers(DNSServers != null ? DNSServers.trim(): null);
					celModemEntity.setMTU(MTU != null ? MTU.trim(): null);
					celModemEntity.setIMEI(IMEI != null ? IMEI.trim(): null);
					celModemEntity.setIMSI(IMSI != null ? IMSI.trim(): null);
					celModemEntity.setICCID(ICCID != null ? ICCID.trim(): null);
					celModemEntity.setSIMSLOT(SIMSLOT != null ? SIMSLOT.trim(): null);
					celModemEntity.setConnectionName(ConnectionName != null ? ConnectionName.trim(): null);
					celModemEntity.setConnectionStatus(ConnectionStatus != null ? ConnectionStatus.trim(): null);
					celModemEntity.setConnectionType(ConnectionType != null ? ConnectionType.trim(): null);
					celModemEntity.setConnectionMethod(ConnectionMethod != null ? ConnectionMethod.trim(): null);
					
					RSSI3 = RSSI3.replaceAll("dBm", "");
					SINR3 = SINR3.replaceAll("dB", "");
					RSRP3 = RSRP3.replaceAll("dBm", "");
					RSRQ3 = RSRQ3.replaceAll("dB", "");
					celModemEntity.setRSSI3(RSSI3 != null ? Double.parseDouble(RSSI3.trim()): null);
					celModemEntity.setSINR3(SINR3 != null ? Double.parseDouble(SINR3.trim()): null);
					celModemEntity.setRSRP3(RSRP3 != null ? Double.parseDouble(RSRP3.trim()): null);
					celModemEntity.setRSRQ3(RSRQ3 != null ? Double.parseDouble(RSRQ3.trim()): null);
					celModemEntity.setChannel3(Channel3 != null ? Double.parseDouble(Channel3.trim()): null);
					RSSI4 = RSSI4.replaceAll("dBm", "");
					SINR4 = SINR4.replaceAll("dB", "");
					RSRP4 = RSRP4.replaceAll("dBm", "");
					RSRQ4 = RSRQ4.replaceAll("dB", "");
					celModemEntity.setRSSI4(RSSI4 != null ? Double.parseDouble(RSSI4.trim()): null);
					celModemEntity.setSINR4(SINR4 != null ? Double.parseDouble(SINR4.trim()): null);
					celModemEntity.setRSRP4(RSRP4 != null ? Double.parseDouble(RSRP4.trim()): null);
					celModemEntity.setRSRQ4(RSRQ4 != null ? Double.parseDouble(RSRQ4.trim()): null);
					celModemEntity.setChannel4(Channel4 != null ? Double.parseDouble(Channel4.trim()): null);
					celModemEntity.setCPULoad(0);
					
					celModemEntity.setId_device(id_device);	
					serviceCellModem.insertModelCellModem(celModemEntity);
				}
				
				
				try (BufferedReader br = new BufferedReader(new InputStreamReader(iscpu))) {
					for (String line = br.readLine(); line != null; line = br.readLine()) {
						
						if (line.contains("get wan 2 WAN Connection [2]") && !line.contains("awk ")) {
							WANConnection = line.trim();
						}
						

						if (WANConnection != null && WANConnection.contains("WAN Connection [2]") && line.contains("cpuload CPU Load")
								&& !line.contains("awk ")) {
							CPULoad = line.split(":")[1];
							CPULoad = CPULoad.replace("%", "");
						}
					}
					
					celModemEntity.setCPULoad( CPULoad != null ? Double.parseDouble(CPULoad): 0);
					serviceCellModem.insertModelCellModem(celModemEntity);
					
					// CPU load
					if(CPULoad != null) {
						deviceUpdateE.setLast_updated(celModemEntity.getTime());
						deviceUpdateE.setLast_value(Double.parseDouble(CPULoad));
						deviceUpdateE.setField_value1(Double.parseDouble(CPULoad));
					} else {
						deviceUpdateE.setLast_updated(null);
						deviceUpdateE.setLast_value(null);
						deviceUpdateE.setField_value1(null);
					}
					
				}
				
				
				
				try (BufferedReader br = new BufferedReader(new InputStreamReader(isbt))) {
					for (String line = br.readLine(); line != null; line = br.readLine()) {
						
						if (line != null && line.contains("ALL WAN Connections") && !line.contains("awk ")) {
							String lineText = line.split("ALL WAN Connections")[1];
							
							String[] ary = lineText.split(" ");
							ary = Arrays.stream(ary).filter(x -> !StringUtils.isBlank(x)).toArray(String[]::new);
							ALLDownload = ary[0] + " " + ary[1];
							ALLUpload = ary[2] + " " + ary[3];
							ALLTotal = ary[4] + " " + ary[5];
						}
						
						if (line != null && line.contains("Cellular") && !line.contains("awk ")) {
							String lineText = line.split("Cellular")[1];
							
							String[] ary = lineText.split(" ");
							ary = Arrays.stream(ary).filter(x -> !StringUtils.isBlank(x)).toArray(String[]::new);
							CellularDownload = ary[0] + " " + ary[1];
							CellularUpload = ary[2] + " " + ary[3];
							CellularTotal = ary[4] + " " + ary[5];
						}
					}
					
					celModemEntity.setALLDownload(ALLDownload != null ? ALLDownload : null);
					celModemEntity.setALLUpload(ALLUpload != null ? ALLUpload : null);
					celModemEntity.setALLTotal(ALLTotal != null ? ALLTotal : null);
					celModemEntity.setCellularDownload(CellularDownload != null ? CellularDownload : null);
					celModemEntity.setCellularUpload(CellularUpload != null ? CellularUpload : null);
					celModemEntity.setCellularTotal(CellularTotal != null ? CellularTotal : null);
					
					
					serviceCellModem.insertModelCellModem(celModemEntity);
				}
				
				
				
				try (BufferedReader br = new BufferedReader(new InputStreamReader(issystem))) {
					for (String line = br.readLine(); line != null; line = br.readLine()) {
						
						if (line != null && line.contains("Device Name") && !line.contains("awk ")) {
							DeviceName = line.split(":")[1];
						}
						
						if (line != null && line.contains("Product Model") && !line.contains("awk ")) {
							ProductModel = line.split(":")[1];
						}
						
						if (line != null && line.contains("Hardware Revision") && !line.contains("awk ")) {
							HardwareRevision = line.split(":")[1];
						}
						
						if (line != null && line.contains("Serial Number") && !line.contains("awk ")) {
							SerialNumber = line.split(":")[1];
						}
						
						if (line != null && line.contains("Firmware Version") && !line.contains("awk ")) {
							FirmwareVersion = line.split(":")[1];
						}
						
					}
					
					celModemEntity.setDeviceName(DeviceName != null ? DeviceName : null);
					celModemEntity.setProductModel(ProductModel != null ? ProductModel : null);
					celModemEntity.setHardwareRevision(HardwareRevision != null ? HardwareRevision : null);
					celModemEntity.setSerialnumber(SerialNumber != null ? SerialNumber : null);
					celModemEntity.setFirmwareVersion(FirmwareVersion != null ? FirmwareVersion : null);
					serviceCellModem.insertModelCellModem(celModemEntity);
				}
				
				
				try (BufferedReader br = new BufferedReader(new InputStreamReader(isuptime))) {
					for (String line = br.readLine(); line != null; line = br.readLine()) {
						
						if (line != null && line.contains("uptime Uptime") && !line.contains("awk ")) {
							uptime = line.split(":")[1];
						}
					}
					
					celModemEntity.setUptime(uptime != null ? uptime : null);
					serviceCellModem.insertModelCellModem(celModemEntity);
				}
				
				
				
				try (BufferedReader br = new BufferedReader(new InputStreamReader(isscm))) {
					for (String line = br.readLine(); line != null; line = br.readLine()) {
						
						if (line != null && line.contains("Mode:") && !line.contains("awk ")) {
							mode = line.split("Mode:")[1];
						}
						
						if (line != null && line.contains("Temperature:") && !line.contains("awk ")) {
							Temperature = line.split("Temperature:")[1];
						}
						
						if (line != null && line.contains("System mode:") && !line.contains("awk ")) {
							String lineText = line.split("System mode:")[1];
							lineText = lineText.trim();
							SystemMode = lineText.split("     ")[0];
						}
						
						// getTemperature
						if(Temperature != null) {
							deviceUpdateE.setField_value2(Double.parseDouble(Temperature));
						} else {
							deviceUpdateE.setField_value2(null);
						}
					}
					
					celModemEntity.setMode(mode != null ? mode : null);
					celModemEntity.setSystemMode(SystemMode != null ? SystemMode : null);

					celModemEntity.setTemperature( Temperature != null ? Double.parseDouble(Temperature): 0);
					serviceCellModem.insertModelCellModem(celModemEntity);
				}
				
				
				
				try (BufferedReader br = new BufferedReader(new InputStreamReader(isscs))) {
					for (String line = br.readLine(); line != null; line = br.readLine()) {
						
						if (line != null && line.contains("Slot A -") && !line.contains("awk ")) {
							SlotA = line.split("Slot A -")[1];
						}
						
						if (line != null && line.contains("Slot B -") && !line.contains("awk ")) {
							SlotB = line.split("Slot B -")[1];
						}
					}
					
					celModemEntity.setSlotA(SlotA != null ? SlotA : null);
					celModemEntity.setSlotB(SlotB != null ? SlotB : null);
					serviceCellModem.insertModelCellModem(celModemEntity);
				}
				
				// value 3
				deviceUpdateE.setField_value3(null);
				
				deviceUpdateE.setId(celModemEntity.getId_device());
				serviceD.updateLastUpdated(deviceUpdateE);

			} finally {
				if (session != null) {
					session.disconnect();
				}
				if (channel != null) {
					channel.disconnect();
				}
				
				if (channelcpu != null) {
					channelcpu.disconnect();
				}

				if (channelbt != null) {
					channelbt.disconnect();
				}

				if (channelsystem != null) {
					channelsystem.disconnect();
				}
				
				if (channeluptime != null) {
					channeluptime.disconnect();
				}

				if (channelscm != null) {
					channelscm.disconnect();
				}

				if (channelscs != null) {
					channelscs.disconnect();
				}
			}
		}
	}
	
	public void runCronJobSSHDatalogger() throws Exception {
		// Get list device and id_device_type = 5
		BatchJobService service = new BatchJobService();
		List<?> listDevice = service.getListDeviceDatalogger(new DeviceEntity());
		if (listDevice == null || listDevice.size() == 0) { return; }		
		for (int i = 0; i < listDevice.size(); i++) {
			DeviceEntity deviceItem = (DeviceEntity) listDevice.get(i);
			listDataloggerStructure(deviceItem.getSsh_user(), deviceItem.getSsh_pass(), deviceItem.getSsh_host(), Integer.parseInt(deviceItem.getSsh_port()), deviceItem.getId());
		}
	}
	
	public static void listDataloggerStructure(String username, String password, String host, int port, int id_device)
			throws Exception {
		if(host != null && username != null && password != null && port > 0) {
			// datalogger
			ModelDataloggerService dataloggerModem = new ModelDataloggerService();
			ModelDataloggerEntity dataloggerEntity = new ModelDataloggerEntity();
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		    final String utcTime = sdf.format(new Date());
		    
			dataloggerEntity.setTime(utcTime);
			
			DeviceEntity deviceUpdateE = new DeviceEntity();
			DeviceService serviceD = new DeviceService();
			
			 // Reading SSH info
		    Session session = null;
			ChannelExec channel = null;
			String command = "cat /proc/meminfo";
			
			try {
				session = new JSch().getSession( username, host, port );
				session.setPassword(password);
				session.setConfig("StrictHostKeyChecking", "no");
				session.connect();

				channel = (ChannelExec) session.openChannel("exec");
				channel.setCommand(command);
				ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
				channel.setOutputStream(responseStream);
				InputStream is = channel.getInputStream();
				channel.connect();
				while (channel.isConnected()) { Thread.sleep(100); }
				
				String MemTotal = null;
				String MemFree = null;

				try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
					for (String line = br.readLine(); line != null; line = br.readLine()) {
						
						if (line.contains("MemTotal") && !line.contains("awk ")) {
							MemTotal = line.split(":")[1];
						}
						
						if (line.contains("MemFree") && !line.contains("awk ")) {
							MemFree = line.split(":")[1];
						}

					}
					String regex = "[^0-9]";
					MemTotal = MemTotal.replaceAll(regex, "");
					MemFree = MemFree.replaceAll(regex, "");  
					
					System.out.println("MemFree: " + MemFree);
					
					dataloggerEntity.setMemTotal(MemTotal != null ? Double.parseDouble(MemTotal.trim()): null);
					dataloggerEntity.setMemFree(MemFree != null ? Double.parseDouble(MemFree.trim()): null);
					dataloggerEntity.setId_device(id_device);	
					dataloggerModem.insertModelDatalogger(dataloggerEntity);
					
					// CPU load
					if(MemFree != null) {
						deviceUpdateE.setLast_updated(dataloggerEntity.getTime());
						deviceUpdateE.setLast_value(Double.parseDouble(MemFree));
						deviceUpdateE.setField_value1(Double.parseDouble(MemFree));
					} else {
						deviceUpdateE.setLast_updated(null);
						deviceUpdateE.setLast_value(null);
						deviceUpdateE.setField_value1(null);
					}
				}
				
				
				
				// value 3
				deviceUpdateE.setField_value2(null);
				deviceUpdateE.setField_value3(null);
				deviceUpdateE.setId(dataloggerEntity.getId_device());
				serviceD.updateLastUpdated(deviceUpdateE);	
			} finally {
				if (session != null) {
					session.disconnect();
				}
				if (channel != null) {
					channel.disconnect();
				}
			}
		}
	}
}
