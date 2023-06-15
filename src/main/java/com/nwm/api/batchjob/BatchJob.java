/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.batchjob;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
import com.nwm.api.entities.ModelSmaInverterStp3000ktlus10Entity;
import com.nwm.api.entities.ModelSmaInverterStp62us41Entity;
import com.nwm.api.entities.ModelSolarOpenWeatherEntity;
import com.nwm.api.entities.ModelSungrowSg110cxEntity;
import com.nwm.api.entities.ModelSungrowSg50cxEntity;
import com.nwm.api.entities.ModelSungrowUmg604Entity;
import com.nwm.api.entities.ModelSungrowWeatherPvmet75200Entity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.entities.WeatherEntity;
import com.nwm.api.services.BatchJobService;
import com.nwm.api.services.DeviceService;
import com.nwm.api.services.ModelCellModemService;
import com.nwm.api.services.ModelDataloggerService;
import com.nwm.api.services.ModelSmaInverterStp3000ktlus10Service;
import com.nwm.api.services.ModelSmaInverterStp62us41Service;
import com.nwm.api.services.ModelSungrowSg110cxService;
import com.nwm.api.services.ModelSungrowSg50cxService;
import com.nwm.api.services.ModelSungrowUmg604Service;
import com.nwm.api.services.ModelSungrowWeatherPvmet75200Service;
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

				if (weather.getWeather_icon() == null || weather.getWeather_description() == null) {
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
			if (listDevice == null || listDevice.size() == 0) {
				return;
			}
			for (int i = 0; i < listDevice.size(); i++) {
				DeviceEntity deviceItem = (DeviceEntity) listDevice.get(i);
				DeviceEntity rowItem = service.getDataDeviceUpdateLifetime(deviceItem);
				if (rowItem != null) {
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
			if (listDevice == null || listDevice.size() == 0) {
				return;
			}
			for (int i = 0; i < listDevice.size(); i++) {
				DeviceEntity deviceItem = (DeviceEntity) listDevice.get(i);

				/// converting date format for US
				Date date = new Date();
				SimpleDateFormat sdfAmerica = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				TimeZone tzInAmerica = TimeZone.getTimeZone("America/Los_Angeles");
				sdfAmerica.setTimeZone(tzInAmerica);

				deviceItem.setCurrent_time(sdfAmerica.format(date));
				DeviceEntity rowItem = service.getDataDeviceUpdateEnergyToday(deviceItem);
				if (rowItem.getId() <= 0) {
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
			if (listDevice == null || listDevice.size() == 0) {
				return;
			}
			for (int i = 0; i < listDevice.size(); i++) {
				DeviceEntity deviceItem = (DeviceEntity) listDevice.get(i);

				// converting date format for US
				Date date = new Date();
				SimpleDateFormat sdfAmerica = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				TimeZone tzInAmerica = TimeZone.getTimeZone("America/Los_Angeles");
				sdfAmerica.setTimeZone(tzInAmerica);
				deviceItem.setCurrent_time(sdfAmerica.format(date));

				DeviceEntity rowItem = service.getDataDeviceEnergyThisMonth(deviceItem);
				if (rowItem.getId() <= 0) {
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
				String idSite = String.valueOf(siteObj.getId());
				String hash_id = secretCard.encrypt(idSite).toLowerCase();
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
//					String mailTo = "vanlong200880@gmail.com,lpham@phoenixrs.com";
					String mailTo = siteObj.getCf_email_subscribers();
					String subject = " Next Wave Alert - ".concat(siteObj.getName());
					String tags = "run_cron_job";
					String fromName = "NEXT WAVE ENERGY MONITORING INC";
					if (mailTo != null && !mailTo.isEmpty()) {
						boolean flagSent = SendMail.SendGmailTLS(mailFromContact, fromName, mailTo, subject,
								bodyHtml.toString(), tags);

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
				if (errorCode > 0) {
					Date now = new Date();
					TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
					String CurrentDate = format.format(now);
					obj.setEnd_date(CurrentDate);

					switch (errorCode) {
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
						if (lastRow.getNvmActivePower() > 0) {
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

					if (hourOfDay >= (objSite.getStart_date_time() + 1)
							&& hourOfDay <= (objSite.getEnd_date_time() - 1)) {
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

										int noProduction = 0;
										switch (obj.getDatatablename()) {
										case "model_shark100":
											noProduction = 48;
											break;
										case "model_ivt_solaron_ext":
											noProduction = 53;
											break;
										case "model_pvp_inverter":
											noProduction = 54;
											break;
										case "model_advanced_energy_solaron":
											noProduction = 55;
											break;
										case "model_chint_solectria_inverter_class9725":
											noProduction = 56;
											break;
										case "model_veris_industries_e51c2_power_meter":
											noProduction = 57;
											break;
										case "model_satcon_pvs357_inverter":
											noProduction = 58;
											break;
										case "model_elkor_wattson_pv_meter":
											noProduction = 59;
											break;
										case "model_elkor_production_meter":
											noProduction = 60;
											break;
										case "model_abb_trio_class6210":
											noProduction = 61;
											break;
										case "model_solectria_sgi_226ivt":
											noProduction = 133;
											break;
										case "model_pv_powered_35_50_260_500kw_inverter":
											noProduction = 135;
											break;
										case "model_campell_scientific_meter1":
											noProduction = 764;
											break;
										case "model_campell_scientific_meter2":
											noProduction = 765;
											break;
										case "model_campell_scientific_meter3":
											noProduction = 766;
											break;
										case "model_campell_scientific_meter4":
											noProduction = 767;
											break;
										case "model_xantrex_gt100_250_500":
											noProduction = 762;
											break;
										case "model_veris_industries_e50c2a":
											noProduction = 920;
											break;
										case "model_sunny_central_class9775_inverter":
											noProduction = 922;
											break;
										case "model_satcon_powergate_225_inverter":
											noProduction = 924;
											break;
										case "model_solaredge_inverter":
											noProduction = 926;
											break;
										case "model_sma_inverter_stp1200tlus10":
											noProduction = 928;
											break;
										case "model_sma_inverter_stp24ktlus10":
											noProduction = 930;
											break;
										case "model_sungrow_sg50cx":
											noProduction = 932;
											break;
										case "model_sungrow_sg110cx":
											noProduction = 934;
											break;

										}

										BatchJobTableEntity rowItem = service
												.getLastRowItemCheckNoProduction(bathJobEntity);
										if (rowItem.getNvmActivePower() != 0.001) {
											if (rowItem.getId_device() > 0 && rowItem.getNvmActivePower() <= 0) {
												AlertEntity alertItem = new AlertEntity();
												alertItem.setId_device(obj.getId());
												alertItem.setId_error(noProduction);
												alertItem.setStart_date(
														!Lib.isBlank(obj.getLast_updated()) ? obj.getLast_updated()
																: sDateUTC);
												// Check error exits
												boolean checkAlertExist = service.checkAlertExist(alertItem);
												if (!checkAlertExist && alertItem.getId_device() > 0
														&& alertItem.getId_error() > 0) {
													// Insert error
													service.insertAlert(alertItem);
												}
											} else {
												// Close alert no production
												bathJobEntity.setId_error(noProduction);
												BatchJobTableEntity rowItemRemove = service
														.getRowItemAlert(bathJobEntity);
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

					if (hourOfDay >= (objSite.getStart_date_time() + 1)
							&& hourOfDay <= (objSite.getEnd_date_time() - 1)) {
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
							List<?> listDeviceBySite = service.getListMeterAndInverterBySite(dEntity);
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

										int noCommunication = 0;
										switch (obj.getDatatablename()) {
										case "model_shark100":
											noCommunication = 49;
											break;
										case "model_ivt_solaron_ext":
											noCommunication = 62;
											break;
										case "model_pvp_inverter":
											noCommunication = 63;
											break;
										case "model_advanced_energy_solaron":
											noCommunication = 64;
											break;
										case "model_chint_solectria_inverter_class9725":
											noCommunication = 65;
											break;
										case "model_veris_industries_e51c2_power_meter":
											noCommunication = 66;
											break;
										case "model_satcon_pvs357_inverter":
											noCommunication = 67;
											break;
										case "model_elkor_wattson_pv_meter":
											noCommunication = 68;
											break;
										case "model_elkor_production_meter":
											noCommunication = 69;
											break;
										case "model_abb_trio_class6210":
											noCommunication = 70;
											break;
										case "model_solectria_sgi_226ivt":
											noCommunication = 132;
											break;
										case "model_pv_powered_35_50_260_500kw_inverter":
											noCommunication = 134;
											break;
										case "model_xantrex_gt100_250_500":
											noCommunication = 763;
											break;
										case "model_campell_scientific_meter1":
											noCommunication = 771;
											break;
										case "model_campell_scientific_meter2":
											noCommunication = 770;
											break;
										case "model_campell_scientific_meter3":
											noCommunication = 769;
											break;
										case "model_campell_scientific_meter4":
											noCommunication = 768;
											break;
										case "model_veris_industries_e50c2a":
											noCommunication = 919;
											break;
										case "model_sunny_central_class9775_inverter":
											noCommunication = 921;
											break;
										case "model_satcon_powergate_225_inverter":
											noCommunication = 924;
											break;
										case "model_solaredge_inverter":
											noCommunication = 925;
											break;
										case "model_sma_inverter_stp1200tlus10":
											noCommunication = 927;
											break;
										case "model_sma_inverter_stp24ktlus10":
											noCommunication = 929;
											break;
										case "model_sungrow_sg50cx":
											noCommunication = 931;
											break;
										case "model_sungrow_sg110cx":
											noCommunication = 933;
											break;

										}

										BatchJobTableEntity lastRowItem = service
												.getLastRowItemCheckNoCommunication(bathJobEntity);
										AlertEntity alertItem = new AlertEntity();
										alertItem.setId_device(obj.getId());
										alertItem.setId_error(noCommunication);
										alertItem.setStart_date(
												!Lib.isBlank(obj.getLast_updated()) ? obj.getLast_updated() : sDateUTC);

										if (lastRowItem.getId_device() <= 0
												|| lastRowItem.getNvmActivePower() == 0.001) {
											// Check error exits
											boolean checkAlertExist = service.checkAlertExist(alertItem);
											if (!checkAlertExist && alertItem.getId_device() > 0
													&& alertItem.getId_error() > 0) {
												// Insert error
												service.insertAlert(alertItem);
											}

										} else {
											// Close no communication
											AlertEntity checkAlertExist = (AlertEntity) service
													.getAlertDetail(alertItem);
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

				if (lastRowItem.getId_device() <= 0 || lastRowItem.getNvmActivePower() == -1) {
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
				if (obj.getJob_tablename() != null) {
					bathJobEntity.setDatatablename(obj.getJob_tablename());
				} else {
					bathJobEntity.setDatatablename(obj.getView_tablename());
				}

				BatchJobTableEntity lastRowItem = service.getLastRowItem(bathJobEntity);
				System.out.println("Run batchJob close alert from datalogger: " + obj.getId());
				if (lastRowItem.getError() == 0) {
//		        	Close all alert by datalogger
//		    	    get all error by id_device_group, type datalogger alert

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
			if (listSites.size() > 0) {
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
			if (listSites.size() > 0) {
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

			if (listReports.size() > 0) {
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
						timeSchedule = calQ.get(Calendar.YEAR) + "-" + (calQ.get(Calendar.MONTH) + 1) + "-"
								+ calQ.getActualMaximum(Calendar.DATE) + " 20";
						timeScheduleFormat = new SimpleDateFormat("yyyy-MM-dd HH");
						startDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
						endDateFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
					}

					timeSchedule = timeScheduleFormat.format(timeScheduleFormat.parse(timeSchedule));
					timeScheduleFormat.setTimeZone(timeZone);
					startDateFormat.setTimeZone(timeZone);
					endDateFormat.setTimeZone(timeZone);

					log.infoSuffixes("MailReportOnSchedule",
							"Cadence: " + cadenceRange + "\nTimeSchedule: " + timeSchedule + "\nNow: "
									+ timeScheduleFormat.format(calQ.getTime()) + "\nEqual: "
									+ timeSchedule.equals(timeScheduleFormat.format(calQ.getTime())));
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
		if (listDevice == null || listDevice.size() == 0) {
			return;
		}
		for (int i = 0; i < listDevice.size(); i++) {
			DeviceEntity deviceItem = (DeviceEntity) listDevice.get(i);
			listFolderStructure(deviceItem.getSsh_user(), deviceItem.getSsh_pass(), deviceItem.getSsh_host(),
					Integer.parseInt(deviceItem.getSsh_port()), deviceItem.getId());
		}
	}

	public static void listFolderStructure(String username, String password, String host, int port, int id_device)
			throws Exception {
		if (host != null && username != null && password != null && port > 0) {
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
				session = new JSch().getSession(username, host, port);
				session.setPassword(password);
				session.setConfig("StrictHostKeyChecking", "no");
				session.connect();

				channel = (ChannelExec) session.openChannel("exec");
				channel.setCommand(command);
				ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
				channel.setOutputStream(responseStream);
				InputStream is = channel.getInputStream();
				channel.connect();
				while (channel.isConnected()) {
					Thread.sleep(100);
				}

				channelcpu = (ChannelExec) session.openChannel("exec");
				channelcpu.setCommand(commandcpu);
				ByteArrayOutputStream responseStreamcpu = new ByteArrayOutputStream();
				channelcpu.setOutputStream(responseStreamcpu);
				InputStream iscpu = channelcpu.getInputStream();
				channelcpu.connect();
				while (channelcpu.isConnected()) {
					Thread.sleep(100);
				}

				channelbt = (ChannelExec) session.openChannel("exec");
				channelbt.setCommand(commandbt);
				ByteArrayOutputStream responseStreambt = new ByteArrayOutputStream();
				channelbt.setOutputStream(responseStreambt);
				InputStream isbt = channelbt.getInputStream();
				channelbt.connect();
				while (channelbt.isConnected()) {
					Thread.sleep(100);
				}

				channelsystem = (ChannelExec) session.openChannel("exec");
				channelsystem.setCommand(commandsystem);
				ByteArrayOutputStream responseStreamsystem = new ByteArrayOutputStream();
				channelsystem.setOutputStream(responseStreamsystem);
				InputStream issystem = channelsystem.getInputStream();
				channelsystem.connect();
				while (channelsystem.isConnected()) {
					Thread.sleep(100);
				}

				channeluptime = (ChannelExec) session.openChannel("exec");
				channeluptime.setCommand(commanduptime);
				ByteArrayOutputStream responseStreamuptime = new ByteArrayOutputStream();
				channeluptime.setOutputStream(responseStreamuptime);
				InputStream isuptime = channeluptime.getInputStream();
				channeluptime.connect();
				while (channeluptime.isConnected()) {
					Thread.sleep(100);
				}

				channelscm = (ChannelExec) session.openChannel("exec");
				channelscm.setCommand(commandscm);
				ByteArrayOutputStream responseStreamscm = new ByteArrayOutputStream();
				channelscm.setOutputStream(responseStreamscm);
				InputStream isscm = channelscm.getInputStream();
				channelscm.connect();
				while (channelscm.isConnected()) {
					Thread.sleep(100);
				}

				channelscs = (ChannelExec) session.openChannel("exec");
				channelscs.setCommand(commandscs);
				ByteArrayOutputStream responseStreamscs = new ByteArrayOutputStream();
				channelscs.setOutputStream(responseStreamscs);
				InputStream isscs = channelscs.getInputStream();
				channelscs.connect();
				while (channelscs.isConnected()) {
					Thread.sleep(100);
				}

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

						if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
								&& line.contains("MTU") && !line.contains("awk ")) {
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

						if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
								&& line.contains("IMEI") && !line.contains("awk ")) {
							IMEI = line.split(":")[1];
						}

						if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
								&& line.contains("IMSI") && !line.contains("awk ")) {
							IMSI = line.split(":")[1];
						}

						if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
								&& line.contains("ICCID") && !line.contains("awk ")) {
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

						if (WANConnection != null && WANConnection.contains("WAN Connection [2]")
								&& line.contains("Band") && !line.contains("awk ")) {
							Band = line.split(":")[1];
							Band = Band.trim();
						}

						if (Band != null && (Band.equals("LTE Band 4 (AWS 1700/2100 MHz)")
								|| Band.equals("LTE Band 2 (1900 MHz)"))) {
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

					celModemEntity.setIPAddress(IPAddress != null ? IPAddress.trim() : null);
					celModemEntity.setDefaultGateway(DefaultGateway != null ? DefaultGateway.trim() : null);
					celModemEntity.setDNSServers(DNSServers != null ? DNSServers.trim() : null);
					celModemEntity.setMTU(MTU != null ? MTU.trim() : null);
					celModemEntity.setIMEI(IMEI != null ? IMEI.trim() : null);
					celModemEntity.setIMSI(IMSI != null ? IMSI.trim() : null);
					celModemEntity.setICCID(ICCID != null ? ICCID.trim() : null);
					celModemEntity.setSIMSLOT(SIMSLOT != null ? SIMSLOT.trim() : null);
					celModemEntity.setConnectionName(ConnectionName != null ? ConnectionName.trim() : null);
					celModemEntity.setConnectionStatus(ConnectionStatus != null ? ConnectionStatus.trim() : null);
					celModemEntity.setConnectionType(ConnectionType != null ? ConnectionType.trim() : null);
					celModemEntity.setConnectionMethod(ConnectionMethod != null ? ConnectionMethod.trim() : null);

					RSSI3 = RSSI3 != null ? RSSI3.replaceAll("dBm", "") : null;
					SINR3 = SINR3 != null ? SINR3.replaceAll("dB", "") : null;
					RSRP3 = RSRP3 != null ? RSRP3.replaceAll("dBm", "") : null;
					RSRQ3 = RSRQ3 != null ? RSRQ3.replaceAll("dB", "") : null;

					celModemEntity.setRSSI3(RSSI3 != null ? Double.parseDouble(RSSI3.trim()) : 0);
					celModemEntity.setSINR3(SINR3 != null ? Double.parseDouble(SINR3.trim()) : 0);
					celModemEntity.setRSRP3(RSRP3 != null ? Double.parseDouble(RSRP3.trim()) : 0);
					celModemEntity.setRSRQ3(RSRQ3 != null ? Double.parseDouble(RSRQ3.trim()) : 0);
					celModemEntity.setChannel3(Channel3 != null ? Double.parseDouble(Channel3.trim()) : 0);
					RSSI4 = RSSI4 != null ? RSSI4.replaceAll("dBm", "") : null;
					SINR4 = SINR4 != null ? SINR4.replaceAll("dB", "") : null;
					RSRP4 = RSRP4 != null ? RSRP4.replaceAll("dBm", "") : null;
					RSRQ4 = RSSI3 != null ? RSRQ4.replaceAll("dB", "") : null;
					celModemEntity.setRSSI4(RSSI4 != null ? Double.parseDouble(RSSI4.trim()) : 0);
					celModemEntity.setSINR4(SINR4 != null ? Double.parseDouble(SINR4.trim()) : 0);
					celModemEntity.setRSRP4(RSRP4 != null ? Double.parseDouble(RSRP4.trim()) : 0);
					celModemEntity.setRSRQ4(RSRQ4 != null ? Double.parseDouble(RSRQ4.trim()) : 0);
					celModemEntity.setChannel4(Channel4 != null ? Double.parseDouble(Channel4.trim()) : 0);
					celModemEntity.setCPULoad(0);

					// RSSI4
					if (RSSI4 != null) {
						deviceUpdateE.setField_value3(RSSI4 != null ? Double.parseDouble(RSSI4.trim()) : 0);
					} else {
						deviceUpdateE.setField_value3(null);
					}

					celModemEntity.setId_device(id_device);
					serviceCellModem.insertModelCellModem(celModemEntity);
				}

				try (BufferedReader br = new BufferedReader(new InputStreamReader(iscpu))) {
					for (String line = br.readLine(); line != null; line = br.readLine()) {

						if (line.contains("cpuload CPU Load") && !line.contains("awk ")) {
							CPULoad = line.split(":")[1];
							CPULoad = CPULoad.replace("%", "");
						}
					}

					celModemEntity.setCPULoad(CPULoad != null ? Double.parseDouble(CPULoad) : 0);
					serviceCellModem.insertModelCellModem(celModemEntity);

					// CPU load
					if (CPULoad != null) {
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
						if (Temperature != null) {
							deviceUpdateE.setField_value2(Double.parseDouble(Temperature));
						} else {
							deviceUpdateE.setField_value2(null);
						}
					}

					celModemEntity.setMode(mode != null ? mode : null);
					celModemEntity.setSystemMode(SystemMode != null ? SystemMode : null);

					celModemEntity.setTemperature(Temperature != null ? Double.parseDouble(Temperature) : 0);
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
		if (listDevice == null || listDevice.size() == 0) {
			return;
		}
		for (int i = 0; i < listDevice.size(); i++) {
			DeviceEntity deviceItem = (DeviceEntity) listDevice.get(i);
			listDataloggerStructure(deviceItem.getSsh_user(), deviceItem.getSsh_pass(), deviceItem.getSsh_host(),
					Integer.parseInt(deviceItem.getSsh_port()), deviceItem.getId());
		}
	}

	@SuppressWarnings("null")
	public static void listDataloggerStructure(String username, String password, String host, int port, int id_device)
			throws Exception {
		if (host != null && username != null && password != null && port > 0) {
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

			ChannelExec channeldns = null;
			String commanddns = "cat /mnt/main/sysconfig/loggerconfig.ini";

			try {
				session = new JSch().getSession(username, host, port);
				session.setPassword(password);
				session.setConfig("StrictHostKeyChecking", "no");
				session.connect();

				channel = (ChannelExec) session.openChannel("exec");
				channel.setCommand(command);
				ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
				channel.setOutputStream(responseStream);
				InputStream is = channel.getInputStream();
				channel.connect();
				while (channel.isConnected()) {
					Thread.sleep(100);
				}

				// DNS
				channeldns = (ChannelExec) session.openChannel("exec");
				channeldns.setCommand(commanddns);
				ByteArrayOutputStream responseStreamdns = new ByteArrayOutputStream();
				channeldns.setOutputStream(responseStreamdns);
				InputStream isdns = channeldns.getInputStream();
				channeldns.connect();
				while (channeldns.isConnected()) {
					Thread.sleep(100);
				}

				String MemTotal = null;
				String MemFree = null;

				String IPADDR = null;
				String DNS1 = null;
				String DNS2 = null;
				String GATEWAY = null;
				String NETMASK = null;
				String NETWORK = null;
				String LOOPNAME = null;
				String SERIALNUMBER = null;

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

					dataloggerEntity.setMemTotal(MemTotal != null ? Double.parseDouble(MemTotal.trim()) : 0);
					dataloggerEntity.setMemFree(MemFree != null ? Double.parseDouble(MemFree.trim()) : 0);
					dataloggerEntity.setId_device(id_device);
					dataloggerModem.insertModelDatalogger(dataloggerEntity);

					// MemFree
					if (MemFree != null) {
						deviceUpdateE.setLast_updated(dataloggerEntity.getTime());
						deviceUpdateE.setLast_value(Double.parseDouble(MemFree));
						deviceUpdateE.setField_value1(Double.parseDouble(MemFree));
					} else {
						deviceUpdateE.setLast_updated(null);
						deviceUpdateE.setLast_value(null);
						deviceUpdateE.setField_value1(null);
					}
				}

				try (BufferedReader br = new BufferedReader(new InputStreamReader(isdns))) {
					for (String line = br.readLine(); line != null; line = br.readLine()) {
						if (line.contains("IPADDR") && !line.contains("awk ")) {
							IPADDR = line.split("=")[1];
						}

						if (line.contains("DNS1") && !line.contains("awk ")) {
							DNS1 = line.split("=")[1];
						}

						if (line.contains("DNS2") && !line.contains("awk ")) {
							DNS2 = line.split("=")[1];
						}

						if (line.contains("GATEWAY") && !line.contains("awk ")) {
							GATEWAY = line.split("=")[1];
						}

						if (line.contains("NETMASK") && !line.contains("awk ")) {
							NETMASK = line.split("=")[1];
						}

						if (line.contains("NETWORK") && !line.contains("awk ")) {
							NETWORK = line.split("=")[1];
						}

						if (line.contains("LOOPNAME") && !line.contains("awk ")) {
							LOOPNAME = line.split("=")[1];
						}

						if (line.contains("SERIALNUMBER") && !line.contains("awk ")) {
							SERIALNUMBER = line.split("=")[1];
						}

					}

					dataloggerEntity.setIpaddr(IPADDR != null ? IPADDR.trim() : null);
					dataloggerEntity.setDns1(DNS1 != null ? DNS1.trim() : null);
					dataloggerEntity.setDns2(DNS2 != null ? DNS2.trim() : null);
					dataloggerEntity.setGateway(GATEWAY != null ? GATEWAY.trim() : null);
					dataloggerEntity.setNetwork(NETMASK != null ? NETMASK.trim() : null);
					dataloggerEntity.setNetmask(NETWORK != null ? NETWORK.trim() : null);
					dataloggerEntity.setSerialnumber(SERIALNUMBER != null ? SERIALNUMBER.trim() : null);
					dataloggerEntity.setLoopname(LOOPNAME != null ? LOOPNAME.trim() : null);

					dataloggerEntity.setId_device(id_device);
					dataloggerModem.insertModelDatalogger(dataloggerEntity);
				}

				// value 2,3
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
				if (channeldns != null) {
					channeldns.disconnect();
				}
			}
		}
	}

	
	public void runCronJobSMADataManager() throws Exception {

		// Get list device and id_device_type = 10
		BatchJobService service = new BatchJobService();
		ModelSmaInverterStp3000ktlus10Service serviceSMA3000 = new ModelSmaInverterStp3000ktlus10Service();
		DeviceService serviceD = new DeviceService();

		List<?> listSites = service.getListSiteByDataloggerType(new SiteEntity());
		if (listSites == null || listSites.size() == 0) {
			return;
		}
		for (int i = 0; i < listSites.size(); i++) {
			SiteEntity siteItem = (SiteEntity) listSites.get(i);
			if (siteItem.getFtp_server() != null && siteItem.getFtp_user() != null && siteItem.getFtp_pass() != null && siteItem.getFtp_folder() != null) {
				String server = siteItem.getFtp_server();
				String user = siteItem.getFtp_user();
				String pass = siteItem.getFtp_pass();
				int port = Integer.parseInt(siteItem.getFtp_port());
				String remoteDirPath = siteItem.getFtp_folder();
				
				
				/// converting date format for US
				Date date = new Date();
				SimpleDateFormat sdfAmerica = new SimpleDateFormat("yyyyMMdd");
				TimeZone tzInAmerica = TimeZone.getTimeZone(siteItem.getTime_zone_value());
				sdfAmerica.setTimeZone(tzInAmerica);
				Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(siteItem.getTime_zone_value()));
				
				remoteDirPath = remoteDirPath + "/"+ calendar.get(Calendar.YEAR)+ "/" + 
				((calendar.get(Calendar.MONTH) + 1) < 10 ? ("0"+(calendar.get(Calendar.MONTH) + 1)): (calendar.get(Calendar.MONTH) + 1)); 
				remoteDirPath = remoteDirPath + "/"+ sdfAmerica.format(date);

				String saveDirPath = Lib.getReourcePropValue(Constants.appConfigFileName,
						Constants.uploadRootPathConfigKey) + "/" + siteItem.getId();
				
				remoteDirPath = "/SMAFTP/OneillVintners/XML/2023/06/20230614";

				System.out.println(Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey));
				FTPClient ftpClient = new FTPClient();

				try {
					ftpClient.connect(server, port);
					int replyCode = ftpClient.getReplyCode();
					if (!FTPReply.isPositiveCompletion(replyCode)) {
						return;
					}
					boolean success = ftpClient.login(user, pass);
					if (!success) {
						return;
					}
		            downloadDirectory(ftpClient, remoteDirPath, "", saveDirPath, siteItem.getId());
				} catch (IOException ex) {
					System.out.println("Oops! Something wrong happened");
					ex.printStackTrace();
				} finally {
					// logs out and disconnects from server
					try {
						if (ftpClient.isConnected()) {
							ftpClient.logout();
							ftpClient.disconnect();
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Download a whole directory from a FTP server.
	 * 
	 * @param ftpClient  an instance of org.apache.commons.net.ftp.FTPClient class.
	 * @param parentDir  Path of the parent directory of the current directory being
	 *                   downloaded.
	 * @param currentDir Path of the current directory being downloaded.
	 * @param saveDir    path of directory where the whole remote directory will be
	 *                   downloaded and saved.
	 * @throws IOException if any network or IO error occurred.
	 */
	public static void downloadDirectory(FTPClient ftpClient, String parentDir, String currentDir, String saveDir, int id_site)
			throws IOException {
		String dirToList = parentDir;
		if (!currentDir.equals("")) {
			dirToList += "/" + currentDir;
		}

		ftpClient.enterLocalPassiveMode();

		FTPClientConfig config = new FTPClientConfig();
		config.setUnparseableEntries(true);
		ftpClient.configure(config);

		FTPFile[] subFiles = ftpClient.listFiles(dirToList);

		if (subFiles != null && subFiles.length > 0) {
			for (FTPFile aFile : subFiles) {
				String currentFileName = aFile.getName();
				if (currentFileName.equals(".") || currentFileName.equals("..")) {
					// skip parent directory and the directory itself
					continue;
				}
				String filePath = parentDir + "/" + currentDir + "/" + currentFileName;
				if (currentDir.equals("")) {
					filePath = parentDir + "/" + currentFileName;
				}

				String newDirPath = saveDir + parentDir + File.separator + currentDir + File.separator
						+ currentFileName;
				if (currentDir.equals("")) {
					newDirPath = saveDir + parentDir + File.separator + currentFileName;
				}

				if (aFile.isDirectory()) {
					// create the directory in saveDir
					File newDir = new File(newDirPath);
					boolean created = newDir.mkdirs();
					if (created) {
						System.out.println("CREATED the directory: " + newDirPath);
					} else {
						System.out.println("COULD NOT create the directory: " + newDirPath);
					}

					// download the sub directory
					downloadDirectory(ftpClient, dirToList, currentFileName, saveDir, id_site);
				} else {
					// download the file
					
					File f = new File(newDirPath);
					// create the directory in saveDir
					File fDir = new File(saveDir + parentDir);
					if(fDir.mkdirs()) { System.out.println("Full directory structure created ... ");
					} else { System.out.println("Oops!!! Folder exits."); }

					if (!f.exists()) {
						// do something
						boolean success = downloadSingleFile(ftpClient, filePath, newDirPath);

						if (success) {

							// Unzip file
							String fileZip = newDirPath;
							File destDir = new File(Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey) + "/"+ id_site +"/data");

							byte[] buffer = new byte[1024];
							ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
							ZipEntry zipEntry = zis.getNextEntry();
							while (zipEntry != null) {
								File newFile = newFile(destDir, zipEntry);
								if (zipEntry.isDirectory()) {
									if (!newFile.isDirectory() && !newFile.mkdirs()) {
										throw new IOException("Failed to create directory " + newFile);
									}
								} else {
									// fix for Windows-created archives
									File parent = newFile.getParentFile();
									if (!parent.isDirectory() && !parent.mkdirs()) {
										throw new IOException("Failed to create directory " + parent);
									}

									// write file content
									FileOutputStream fos = new FileOutputStream(newFile);
									int len;
									while ((len = zis.read(buffer)) > 0) {
										fos.write(buffer, 0, len);
									}
									fos.close();
									
									File logFile = new File(fileZip);
									if(logFile.delete()){  
										System.out.println("Delete file zip: " + fileZip);  
									}
									
								}
								zipEntry = zis.getNextEntry();
							}

							zis.closeEntry();
							zis.close();

						} else {
							 System.out.println("COULD NOT download the file: " + filePath);
						}
					} else {
						 System.out.println("File not exits.");
					}

				}
			}
		} else {
			System.out.println("Error.");
		}
	}

	public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
		File destFile = new File(destinationDir, zipEntry.getName());

		String destDirPath = destinationDir.getCanonicalPath();
		String destFilePath = destFile.getCanonicalPath();

		if (!destFilePath.startsWith(destDirPath + File.separator)) {
			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
		}

		return destFile;
	}

	public static boolean downloadSingleFile(FTPClient ftpClient, String remoteFilePath, String savePath)
			throws IOException {
		File downloadFile = new File(savePath);

		File parentDir = downloadFile.getParentFile();
		if (!parentDir.exists()) {
			parentDir.mkdir();
		}

		OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
		try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			return ftpClient.retrieveFile(remoteFilePath, outputStream);
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}
	
	
	

	public void runCronJobReadXMLDataManager() throws Exception {

		// Get list device and id_device_type = 10
		BatchJobService service = new BatchJobService();
		ModelSmaInverterStp3000ktlus10Service serviceSMA3000 = new ModelSmaInverterStp3000ktlus10Service();
		ModelSmaInverterStp62us41Service serviceSMA62 = new ModelSmaInverterStp62us41Service();
		
		DeviceService serviceD = new DeviceService();

		List<?> listSites = service.getListSiteByDataloggerType(new SiteEntity());
		if (listSites == null || listSites.size() == 0) {
			return;
		}
		for (int i = 0; i < listSites.size(); i++) {
			SiteEntity siteItem = (SiteEntity) listSites.get(i);
			if (siteItem.getFtp_server() != null && siteItem.getFtp_user() != null && siteItem.getFtp_pass() != null && siteItem.getFtp_folder() != null) {
				try {
					
					// Get list device by id_site
					DeviceEntity objDevice = new DeviceEntity();
					objDevice.setId_site(siteItem.getId());
					List<?> listDevice = service.getListDeviceSMABySite(objDevice);
					
					if(listDevice.size() > 0) {
						// Read file XML
						String dirFolderXML = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey) + "/"+siteItem.getId()+"/data";
						Set<String> fileSet = new HashSet<>();
						try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dirFolderXML))) {
							for (Path path : stream) {
								if (!Files.isDirectory(path)) {
									fileSet.add(path.getFileName().toString());
									String fileXML = dirFolderXML + "/" + path.getFileName().toString();

									if (fileXML.indexOf("xml") != -1) {
										// Read file XML
										// Instantiate the Factory
										DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
										try {

											// optional, but recommended
											// process XML securely, avoid attacks like XML External Entities (XXE)
											dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

											// parse XML file
											DocumentBuilder db = dbf.newDocumentBuilder();

											Document doc = db.parse(new File(fileXML));
											doc.getDocumentElement().normalize();
											
											
											for (int v = 0; v < listDevice.size(); v++) {
												DeviceEntity deviceItem = (DeviceEntity) listDevice.get(v);
												DeviceEntity deviceUpdateE = new DeviceEntity();
												
												String[] itemXML = {"MeanPublic", "CurrentPublic"};
												ModelSmaInverterStp3000ktlus10Entity entitySMA3000 = new ModelSmaInverterStp3000ktlus10Entity();
												entitySMA3000.setId_device(deviceItem.getId());
												
												ModelSmaInverterStp62us41Entity entitySMA62 = new ModelSmaInverterStp62us41Entity();
												entitySMA62.setId_device(deviceItem.getId());
												
												for (int k = 0; k < itemXML.length; k++) {
												  NodeList list = doc.getElementsByTagName(itemXML[k]);
													for (int temp = 0; temp < list.getLength(); temp++) {
														Node node = list.item(temp);

														if (node.getNodeType() == Node.ELEMENT_NODE) {
															String modbusdevicenumber = null;
															String field = null;

															Element element = (Element) node;
															// get text
															String key = element.getElementsByTagName("Key").item(0).getTextContent();
															
															key = key.replace("SN: ", "").trim();
															key = key.replace("SN ", "").trim();
															
															String[] keyArr = key.split("\\:", 0);
															if (keyArr.length > 0) {
																modbusdevicenumber = keyArr[0];
															}
															if (keyArr.length > 0) {
																field = keyArr[keyArr.length - 1];
															}
															
															if(field != null) {field = field.trim(); }
															String mean = element.getElementsByTagName("Mean").item(0).getTextContent();
															String timestamp = element.getElementsByTagName("Timestamp").item(0).getTextContent();

															ZoneId utc = ZoneId.of("Etc/UTC");
															DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
															// ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
															ZoneId zId = ZoneId.of(siteItem.getTime_zone_value());
															ZonedDateTime utcDateTime = LocalDateTime.parse(timestamp).atZone(zId).withZoneSameInstant(utc);
															String formatterUtcDateTime = utcDateTime.format(targetFormatter);
															entitySMA3000.setTime(formatterUtcDateTime);
															
															System.out.println("modbusdevicenumber: "+ modbusdevicenumber + "-----" + deviceItem.getModbusdevicenumber());

//															DeviceEntity device = new DeviceEntity();
//															for(int j = 0; j < listDevice.size(); j++ ) {
//																DeviceEntity itemDevice = (DeviceEntity) listDevice.get(j);
//																if(modbusdevicenumber.equals(itemDevice.getModbusdevicenumber())) {
//																	device = itemDevice;
//																}
//															}
															

															if (deviceItem.getId() > 0) {
//																DeviceEntity deviceUpdateE = new DeviceEntity();
																// Insert to datatable
																switch (deviceItem.getDatatablename()) {
																case "model_sma_inverter_stp30000tlus10":
																	// Put data to entity
																	if (field.equals("Measurement.GridMs.TotVAr") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setGridMs_TotVAr(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.DcMs.Watt[0]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setDcMs_Watt0(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
															
																	else if (field.equals("Measurement.DcMs.Watt[1]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setDcMs_Watt1(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
															
																	else if (field.equals("Measurement.GridMs.W.phsA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setW_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.W.phsB") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setW_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.A.phsB") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setA_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.W.phsC") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setW_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.TotW") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setGridMs_TotW(mean != null  ? Double.parseDouble(mean) : 0.001);
																		entitySMA3000.setNvmActivePower(mean != null  ? Double.parseDouble(mean) / 1000 : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.TotVA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setGridMs_TotVA(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.A.phsC") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setA_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.Hz") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setGridMs_Hz(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.A.phsA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setA_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.Isolation.LeakRis") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setIsolation_LeakRis(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.DcMs.Vol[0]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setDcMs_Vol0(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
															
																	else if (field.equals("Measurement.DcMs.Vol[1]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setDcMs_Vol1(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.PhV.phsC") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setPhV_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.PhV.phsB") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setPhV_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.VAr.phsA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setVAr_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.DcMs.Amp[0]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setDcMs_Amp0(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
															
																	else if (field.equals("Measurement.DcMs.Amp[1]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setDcMs_Amp1(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.TotVAr.Pv") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setTotVAr_Pv(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.VAr.phsB") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setVAr_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.PhV.phsA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setPhV_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.VAr.phsC") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setVAr_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.VA.phsA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setVA_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.VA.phsB") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setVA_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.VA.phsC") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setVA_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.TotW.Pv") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setTotW_Pv(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.Metering.TotFeedTms") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setMetering_TotFeedTms(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.Operation.GriSwCnt") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setOperation_GriSwCnt(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.Metering.TotOpTms") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setMetering_TotOpTms(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.Operation.Health") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setOperation_Health(mean != null ? mean : null);
																	}
																	
																	else if (field.equals("Measurement.Metering.TotWhOut") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setMetering_TotWhOut(mean != null  ? Double.parseDouble(mean) : 0.001);
																		entitySMA3000.setNvmActiveEnergy(mean != null ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.Metering.TotWhOut.Pv") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA3000.setTotWhOut_Pv(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}

																	
																	break;
																	
																case "model_sma_inverter_stp62us41":
																	entitySMA62.setTime(formatterUtcDateTime);
																	// Insert data
																	if (field.equals("Measurement.GridMs.VA.phsA")&& modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setVA_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.VA.phsB")&& modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setVA_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.DcMs.Vol[0]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setDcMs_Vol0(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
															
																	else if (field.equals("Measurement.DcMs.Vol[1]")  && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setDcMs_Vol1(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
															
																	else if (field.equals("Measurement.DcMs.Vol[2]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setDcMs_Vol2(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
															
																	else if (field.equals("Measurement.DcMs.Vol[3]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setDcMs_Vol3(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
															
																	else if (field.equals("Measurement.DcMs.Vol[4]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setDcMs_Vol4(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
															
																	else if (field.equals("Measurement.DcMs.Vol[5]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setDcMs_Vol5(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.TotW.Pv") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setTotW_Pv(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.Isolation.LeakRis") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setIsolation_LeakRis(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.PhV.phsC") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setPhV_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.Hz") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setGridMs_Hz(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.W.phsB") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setW_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.TotW") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setGridMs_TotW(mean != null  ? Double.parseDouble(mean) : 0.001);
																		entitySMA62.setNvmActivePower(mean != null  ? Double.parseDouble(mean) / 1000 : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.W.phsC") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setW_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.VAr.phsC") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setVAr_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.DcMs.Watt[0]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setDcMs_Watt0(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
															
																	else if (field.equals("Measurement.DcMs.Watt[1]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setDcMs_Watt1(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
															
																	else if (field.equals("Measurement.DcMs.Watt[2]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setDcMs_Watt2(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
															
																	else if (field.equals("Measurement.DcMs.Watt[3]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setDcMs_Watt3(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
															
																	else if (field.equals("Measurement.DcMs.Watt[4]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setDcMs_Watt4(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
															
																	else if (field.equals("Measurement.DcMs.Watt[5]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setDcMs_Watt5(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.W.phsA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setW_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.VAr.phsB") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setVAr_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.TotVAr.Pv") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setTotVAr_Pv(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.PhV.phsA2B") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setPhV_phsA2B(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.VAr.phsA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setVAr_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.TotVA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setGridMs_TotVA(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.TotVAr") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setGridMs_TotVAr(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.DcMs.Amp[0]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setDcMs_Amp0(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
															
																	else if (field.equals("Measurement.DcMs.Amp[1]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setDcMs_Amp1(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
															
																	else if (field.equals("Measurement.DcMs.Amp[2]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setDcMs_Amp2(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
															
																	else if (field.equals("Measurement.DcMs.Amp[3]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setDcMs_Amp3(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
															
																	else if (field.equals("Measurement.DcMs.Amp[4]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setDcMs_Amp4(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
															
																	else if (field.equals("Measurement.DcMs.Amp[5]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setDcMs_Amp5(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.PhV.phsB2C") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setPhV_phsB2C(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.PhV.phsB") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setPhV_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.A.phsA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setA_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.PhV.phsC2A") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setPhV_phsC2A(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.A.phsB") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setA_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.PhV.phsA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setPhV_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.VA.phsC") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setVA_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.GridMs.A.phsC") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setA_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.Metering.TotWhOut") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setMetering_TotWhOut(mean != null  ? Double.parseDouble(mean) : 0.001);
																		entitySMA62.setNvmActiveEnergy(mean != null ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.Operation.Health") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setOperation_Health(mean != null  ? mean : null);
																	}
																	
																	else if (field.equals("Measurement.Operation.GriSwCnt") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setOperation_GriSwCnt(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.Metering.TotWhOut.Pv") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setTotWhOut_Pv(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.Metering.TotFeedTms") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setMetering_TotFeedTms(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}
																	
																	else if (field.equals("Measurement.Metering.TotOpTms") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																		entitySMA62.setMetering_TotOpTms(mean != null  ? Double.parseDouble(mean) : 0.001);
																	}

																	break;
																default:
																	
																}
															}
														}
													}

												}
												
												switch (deviceItem.getDatatablename()) {
												case "model_sma_inverter_stp30000tlus10":
													serviceSMA3000.insertModelSmaInverterStp3000ktlus10(entitySMA3000);
													if (entitySMA3000.getGridMs_TotW() > 0) {
														deviceUpdateE.setLast_updated(entitySMA3000.getTime());
														deviceUpdateE.setLast_value(entitySMA3000.getGridMs_TotW()  > 0 ? entitySMA3000.getGridMs_TotW() / 1000 : null);
														deviceUpdateE.setField_value1(entitySMA3000.getGridMs_TotW()  > 0 ? entitySMA3000.getGridMs_TotW()/ 1000 : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
//												
//													
													deviceUpdateE.setField_value2(null);
													deviceUpdateE.setField_value3(null);
//													
													deviceUpdateE.setId(entitySMA3000.getId_device());
													serviceD.updateLastUpdated(deviceUpdateE);
													break;
												case "model_sma_inverter_stp62us41":
													
													serviceSMA62.insertModelSmaInverterStp62us41(entitySMA62);
													
													if (entitySMA62.getGridMs_TotW() > 0) {
														deviceUpdateE.setLast_updated(entitySMA62.getTime());
														deviceUpdateE.setLast_value(entitySMA62.getGridMs_TotW()  > 0 ? entitySMA62.getGridMs_TotW() / 1000 : null);
														deviceUpdateE.setField_value1(entitySMA62.getGridMs_TotW()  > 0 ? entitySMA62.getGridMs_TotW()/ 1000 : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													deviceUpdateE.setField_value2(null);
													deviceUpdateE.setField_value3(null);
													
													deviceUpdateE.setId(entitySMA62.getId_device());
													serviceD.updateLastUpdated(deviceUpdateE);
													break;
												}
												
											}
											
											
										} catch (ParserConfigurationException | SAXException | IOException e) {
											e.printStackTrace();
										}
										
										// Delete file from server
										File logFile = new File(fileXML);
										if(logFile.delete()){  
											System.out.println("Delete file: " + fileXML);  
										}
									}
								}
							}
						}
					}
					

					

				} catch (IOException ex) {
					System.out.println("Oops! Something wrong happened");
					ex.printStackTrace();
				}
			}
		}
	}

}
