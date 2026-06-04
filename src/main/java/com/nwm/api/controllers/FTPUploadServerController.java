/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.nwm.api.entities.BatchJobTableEntity;
import com.nwm.api.entities.CameraImageEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelBaseEntity;
import com.nwm.api.entities.ModelSmaClusterControllerEntity;
import com.nwm.api.entities.ModelSmaInverterStp1200tlus10Entity;
import com.nwm.api.entities.ModelSmaInverterStp24000ktlus10Entity;
import com.nwm.api.entities.ModelSmaInverterStp24ktlus10Entity;
import com.nwm.api.entities.ModelSmaInverterStp3000ktlus10Entity;
import com.nwm.api.entities.ModelSmaInverterStp62us41Entity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.services.BatchJobService;
import com.nwm.api.services.DeviceService;
import com.nwm.api.services.ModelSmaClusterControllerService;
import com.nwm.api.services.ModelSmaInverterStp1200tlus10Service;
import com.nwm.api.services.ModelSmaInverterStp24000ktlus10Service;
import com.nwm.api.services.ModelSmaInverterStp24ktlus10Service;
import com.nwm.api.services.ModelSmaInverterStp3000ktlus10Service;
import com.nwm.api.services.ModelSmaInverterStp62us41Service;
import com.nwm.api.services.UploadFilesService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;
import com.nwm.api.services.AWSService;

@RestController
@ApiIgnore
@RequestMapping("/upload-ftp")
public class FTPUploadServerController extends BaseController {
	
	@Autowired
	private AWSService awsService;
	@Autowired
	private UploadFilesService uploadFilesService;
	
	/**
	 * @description Get list file from FTP server
	 * @author long.pham
	 * @since 2023-06-16
	 * @return {}
	 */
	@GetMapping("/download-data-from-ftp")
	public Object downloadFileFromFTP() {
		try {
			BatchJobService service = new BatchJobService();
			List<?> listSites = service.getListSiteByDataloggerType(new SiteEntity());
			if (listSites == null || listSites.size() == 0) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
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
					
					
//					remoteDirPath = "/SMAFTP/LagunaWoods_Laundry24/XML/2023/12/20231206";
//					if(siteItem.getId() == 147) {
//						remoteDirPath = "/SMAFTP/PeninsulaPlastics/XML/2023/06/20230615";
//					}

					FTPClient ftpClient = new FTPClient();

					try {
						ftpClient.connect(server, port);
						int replyCode = ftpClient.getReplyCode();
						if (!FTPReply.isPositiveCompletion(replyCode)) {
							return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
						}
						boolean success = ftpClient.login(user, pass);
						if (!success) {
							return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
						}
						downloadDirectory(ftpClient, remoteDirPath, "", saveDirPath, siteItem.getId());
						
						// download file from yesterday because data maybe upload late 
						if (calendar.get(Calendar.HOUR_OF_DAY) < 1) {
							String remoteDirPathYesterDay = siteItem.getFtp_folder();
							calendar.add(Calendar.DATE, -1);
							String yesterdayString = sdfAmerica.format(calendar.getTime());
							remoteDirPathYesterDay = remoteDirPathYesterDay + "/"+ calendar.get(Calendar.YEAR)+ "/" + 
									((calendar.get(Calendar.MONTH) + 1) < 10 ? ("0"+(calendar.get(Calendar.MONTH) + 1)): (calendar.get(Calendar.MONTH) + 1)); 
							remoteDirPathYesterDay = remoteDirPathYesterDay + "/"+ yesterdayString;
							
							downloadDirectory(ftpClient, remoteDirPathYesterDay, "", saveDirPath, siteItem.getId());
						}
					} catch (IOException ex) {
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
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	
	/**
	 * @description Get list file from FTP server
	 * @author long.pham
	 * @since 2023-06-16
	 * @return {}
	 */
	@GetMapping("/read-data-from-file-xml")
	public Object readDataFromFileXML() {
		try {
			BatchJobService service = new BatchJobService();
			ModelSmaInverterStp3000ktlus10Service serviceSMA3000 = new ModelSmaInverterStp3000ktlus10Service();
			ModelSmaInverterStp24000ktlus10Service serviceSMA24000 = new ModelSmaInverterStp24000ktlus10Service();
			ModelSmaInverterStp62us41Service serviceSMA62 = new ModelSmaInverterStp62us41Service();
			ModelSmaInverterStp24ktlus10Service serviceSMA24k = new ModelSmaInverterStp24ktlus10Service();
			ModelSmaInverterStp1200tlus10Service serviceSMA12k = new ModelSmaInverterStp1200tlus10Service();
			ModelSmaClusterControllerService serviceUmg604 = new ModelSmaClusterControllerService();
			
			DeviceService serviceD = new DeviceService();

			List<?> listSites = service.getListSiteByDataloggerType(new SiteEntity());
			if (listSites == null || listSites.size() == 0) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
			for (int i = 0; i < listSites.size(); i++) {
				SiteEntity siteItem = (SiteEntity) listSites.get(i);
				if (!(siteItem.getFtp_server() != null && siteItem.getFtp_user() != null && siteItem.getFtp_pass() != null && siteItem.getFtp_folder() != null)) continue;
				
				Path dirFolderXML = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey) + "/" + siteItem.getId() + "/data");
				if (Files.notExists(dirFolderXML)) continue;
					
					try {
						
						// Get list device by id_site
						DeviceEntity objDevice = new DeviceEntity();
						objDevice.setId_site(siteItem.getId());
						List<?> listDevice = service.getListDeviceSMABySite(objDevice);
						
						if(listDevice.size() > 0) {
							// Read file XML
							try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirFolderXML)) {
								StreamSupport.stream(stream.spliterator(), false)
							    .sorted(Comparator.comparing(Path::toString))
							    .forEach(path -> { 
									if (!Files.isDirectory(path)) {
										String fileXML = path.toString();

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
													ModelBaseEntity baseEntity = null;
													
													List<DeviceEntity> scaledDeviceParameters = serviceD.getListScaledDeviceParameter(deviceItem);

													
													String[] itemXML = {"MeanPublic", "CurrentPublic"};
													ModelSmaInverterStp3000ktlus10Entity entitySMA3000 = new ModelSmaInverterStp3000ktlus10Entity();
													entitySMA3000.setDeviceDetail(deviceItem.getId(), deviceItem.getDatatablename(), deviceItem.getView_tablename(), deviceItem.getJob_tablename(), 0, 0, null);
													
													ModelSmaInverterStp24000ktlus10Entity entitySMA24000 = new ModelSmaInverterStp24000ktlus10Entity();
													entitySMA24000.setDeviceDetail(deviceItem.getId(), deviceItem.getDatatablename(), deviceItem.getView_tablename(), deviceItem.getJob_tablename(), 0, 0, null);
													
													ModelSmaInverterStp62us41Entity entitySMA62 = new ModelSmaInverterStp62us41Entity();
													entitySMA62.setDeviceDetail(deviceItem.getId(), deviceItem.getDatatablename(), deviceItem.getView_tablename(), deviceItem.getJob_tablename(), 0, 0, null);
													
													ModelSmaInverterStp24ktlus10Entity entitySMA24k = new ModelSmaInverterStp24ktlus10Entity();
													entitySMA24k.setDeviceDetail(deviceItem.getId(), deviceItem.getDatatablename(), deviceItem.getView_tablename(), deviceItem.getJob_tablename(), 0, 0, null);
													
													ModelSmaInverterStp1200tlus10Entity entitySMA12k = new ModelSmaInverterStp1200tlus10Entity();
													entitySMA12k.setDeviceDetail(deviceItem.getId(), deviceItem.getDatatablename(), deviceItem.getView_tablename(), deviceItem.getJob_tablename(), 0, 0, null);
													
													ModelSmaClusterControllerEntity entityCluster = new ModelSmaClusterControllerEntity();
													entityCluster.setDeviceDetail(deviceItem.getId(), deviceItem.getDatatablename(), deviceItem.getView_tablename(), deviceItem.getJob_tablename(), 0, 0, null);
													
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
																	modbusdevicenumber = keyArr[1];
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
																
																

																if (deviceItem.getId() > 0 && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																	// Insert to datatable
																	switch (deviceItem.getDevice_group_table()) {
																	case "model_sma_cluster_controller":
																		entityCluster.setTime(formatterUtcDateTime);
																		if(field.equals("Metering.TotWhOut")) { 
																			entityCluster.setMetering_TotWhOut(mean != null  ? Double.parseDouble(mean) : 0.001); 
																			entityCluster.setNvmActiveEnergy(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		else if(field.equals("Operation.GriSwCnt")) { entityCluster.setOperation_GriSwCnt(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("Metering.TotFeedTms")) { entityCluster.setMetering_TotOpTms(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.TotW")) { 
																			entityCluster.setGridMs_TotW(mean != null  ? Double.parseDouble(mean)/1000 : 0.001);
																			entityCluster.setNvmActivePower(mean != null  ? Double.parseDouble(mean) / 1000 : 0.001); 
																		}
																		
																		else if(field.equals("GridMs.Hz")) { entityCluster.setGridMs_Hz(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("Isolation.FltA")) { entityCluster.setIsolation_FltA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("Isolation.LeakRis")) { entityCluster.setIsolation_LeakRis(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Vol[A]")) { entityCluster.setDcMs_VolA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Vol[B]")) { entityCluster.setDcMs_VolB(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Amp[A]")) { entityCluster.setDcMs_AmpA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Amp[B]")) { entityCluster.setDcMs_AmpB(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.PhV.phsA")) { entityCluster.setGridMs_PhV_phsA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.PhV.phsB")) { entityCluster.setGridMs_PhV_phsB(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.PhV.phsC")) { entityCluster.setGridMs_PhV_phsC(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.A.phsA")) { entityCluster.setGridMs_A_phsA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.A.phsB")) { entityCluster.setGridMs_A_phsB(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.A.phsC")) { entityCluster.setGridMs_A_phsC(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Watt[A]")) { entityCluster.setDcMs_WattA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Watt[B]")) { entityCluster.setDcMs_WattB(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("Operation.Health")) { entityCluster.setOperation_Health(mean != null ? mean : null); }
																		else if(field.equals("Operation.Evt.Prio")) { entityCluster.setOperation_Evt_Prio(mean != null ? mean : null); }
																		else if(field.equals( "Operation.Evt.Msg")) { entityCluster.setOperation_Evt_Msg(mean != null ? mean : null); }
																		else if(field.equals("Operation.Evt.Dsc")) { entityCluster.setOperation_Evt_Dsc(mean != null ? mean : null); }
																		else if(field.equals("InOut.AnInA1")) { entityCluster.setInOut_AnInA1(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("InOut.AnInA2")) { entityCluster.setInOut_AnInA2(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("InOut.AnInA3")) { entityCluster.setInOut_AnInA3(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("InOut.AnInVol4")) { entityCluster.setInOut_AnInVol4(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("Env.ExInsol")) { entityCluster.setEnv_ExInsol(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		break;
																
																	case "model_sma_inverter_stp1200tlus10":
																		entitySMA12k.setTime(formatterUtcDateTime);
																		if(field.equals("Metering.TotWhOut")) { 
																			entitySMA12k.setMetering_TotWhOut(mean != null  ? Double.parseDouble(mean) : 0.001);
																			entitySMA12k.setNvmActiveEnergy(mean != null ? Double.parseDouble(mean) : 0.001);
																		}
																		else if(field.equals("Operation.GriSwCnt")) { entitySMA12k.setOperation_GriSwCnt(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("Metering.TotFeedTms")) { entitySMA12k.setMetering_TotOpTms(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("Metering.GridMs.TotWhOut")) { entitySMA12k.setMetering_GridMs_TotWhOut(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.TotW")) {
																			entitySMA12k.setGridMs_TotW(mean != null  ? Double.parseDouble(mean)/1000 : 0.001);
																			entitySMA12k.setNvmActivePower(mean != null  ? Double.parseDouble(mean) / 1000 : 0.001); 
																		}
																		else if(field.equals("GridMs.Hz")) { entitySMA12k.setGridMs_Hz(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("Isolation.FltA")) { entitySMA12k.setIsolation_FltA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("Isolation.LeakRis")) { entitySMA12k.setIsolation_LeakRis(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Vol[A]")) { entitySMA12k.setDcMs_VolA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Vol[B]")) { entitySMA12k.setDcMs_VolB(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Amp[A]")) { entitySMA12k.setDcMs_AmpA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Amp[B]")) { entitySMA12k.setDcMs_AmpB(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.PhV.phsA")) { entitySMA12k.setGridMs_PhV_phsA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.PhV.phsB")) { entitySMA12k.setGridMs_PhV_phsB(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.PhV.phsC")) { entitySMA12k.setGridMs_PhV_phsC(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.A.phsA")) { entitySMA12k.setGridMs_A_phsA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.A.phsB")) { entitySMA12k.setGridMs_A_phsB(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.A.phsC")) { entitySMA12k.setGridMs_A_phsC(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Watt[A]")) { entitySMA12k.setDcMs_WattA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Watt[B]")) { entitySMA12k.setDcMs_WattB(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("Operation.Health")) { entitySMA12k.setOperation_Health(mean != null ? mean : null); }
																		else if(field.equals("Operation.Evt.Prio")) { entitySMA12k.setOperation_Evt_Prio(mean != null ? mean : null); }
																		else if(field.equals( "Operation.Evt.Msg")) { entitySMA12k.setOperation_Evt_Msg(mean != null ? mean : null); }
																		else if(field.equals("Operation.Evt.Dsc")) { 
																			entitySMA12k.setOperation_Evt_Dsc(mean != null ? mean : null); 
																		}
																		
																		break;
																	case "model_sma_inverter_stp24ktlus10":
																		entitySMA24k.setTime(formatterUtcDateTime);
																		// Put data to entity
																		if (field.equals("Metering.TotWhOut")) {
																			entitySMA24k.setMetering_TotWhOut(mean != null  ? Double.parseDouble(mean) : 0.001);
																			entitySMA24k.setNvmActiveEnergy(mean != null ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("Operation.GriSwCnt")) {
																			entitySMA24k.setOperation_GriSwCnt(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("Metering.TotOpTms")) {
																			entitySMA24k.setMetering_TotOpTms(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("Metering.TotFeedTms")) {
																			entitySMA24k.setMetering_TotFeedTms(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("Metering.GridMs.TotWhOut")) {
																			entitySMA24k.setMetering_GridMs_TotWhOut(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("GridMs.TotW")) {
																			entitySMA24k.setGridMs_TotW(mean != null  ? Double.parseDouble(mean)/100 : 0.001);
																			entitySMA24k.setNvmActivePower(mean != null  ? Double.parseDouble(mean) / 100 : 0.001);
																		}
																		  
																		if (field.equals("GridMs.Hz")) {
																			entitySMA24k.setGridMs_Hz(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("Isolation.FltA")) {
																			entitySMA24k.setIsolation_FltA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("Isolation.LeakRis")) {
																			entitySMA24k.setIsolation_LeakRis(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("DcMs.Vol[A]")) {
																			entitySMA24k.setDcMs_VolA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("DcMs.Vol[B]")) {
																			entitySMA24k.setDcMs_VolB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("DcMs.Amp[A]")) {
																			entitySMA24k.setDcMs_AmpA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("DcMs.Amp[B]")) {
																			entitySMA24k.setDcMs_AmpB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("GridMs.PhV.phsA")) {
																			entitySMA24k.setGridMs_PhV_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("GridMs.PhV.phsB")) {
																			entitySMA24k.setGridMs_PhV_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("GridMs.PhV.phsC")) {
																			entitySMA24k.setGridMs_PhV_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("GridMs.A.phsA")) {
																			entitySMA24k.setGridMs_A_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("GridMs.A.phsB")) {
																			entitySMA24k.setGridMs_A_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("GridMs.A.phsC")) {
																			entitySMA24k.setGridMs_A_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("DcMs.Watt[A]")) {
																			entitySMA24k.setDcMs_WattA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("DcMs.Watt[B]")) {
																			entitySMA24k.setDcMs_WattB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("Operation.Health")) {
																			entitySMA24k.setOperation_Health(mean != null ? mean : null);
																		}
																		  
																		if (field.equals("Operation.Evt.Prio")) {
																			entitySMA24k.setOperation_Evt_Prio(mean != null ? mean : null);
																		}
																		  
																		if (field.equals("Operation.Evt.Msg")) {
																			entitySMA24k.setOperation_Evt_Msg(mean != null ? mean : null);
																		}
																		 
																		if (field.equals("Operation.Evt.Dsc")) {
																			entitySMA24k.setOperation_Evt_Dsc(mean != null ? mean : null);
																		}
																		
																		break;
																	case "model_sma_inverter_stp30000tlus10":
																		entitySMA3000.setTime(formatterUtcDateTime);
																		// Put data to entity
																		if (field.equals("Measurement.GridMs.TotVAr")) {
																			entitySMA3000.setGridMs_TotVAr(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.DcMs.Watt[0]")) {
																			entitySMA3000.setDcMs_Watt0(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Watt[1]")) {
																			entitySMA3000.setDcMs_Watt1(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.GridMs.W.phsA")) {
																			entitySMA3000.setW_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.W.phsB")) {
																			entitySMA3000.setW_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.A.phsB")) {
																			entitySMA3000.setA_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.W.phsC")) {
																			entitySMA3000.setW_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.TotW")) {
																			entitySMA3000.setGridMs_TotW(mean != null  ? Double.parseDouble(mean) / 1000 : 0.001);
																			entitySMA3000.setNvmActivePower(mean != null  ? Double.parseDouble(mean) / 1000 : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.TotVA")) {
																			entitySMA3000.setGridMs_TotVA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.A.phsC")) {
																			entitySMA3000.setA_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.Hz")) {
																			entitySMA3000.setGridMs_Hz(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.A.phsA")) {
																			entitySMA3000.setA_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Isolation.LeakRis")) {
																			entitySMA3000.setIsolation_LeakRis(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.DcMs.Vol[0]")) {
																			entitySMA3000.setDcMs_Vol0(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Vol[1]")) {
																			entitySMA3000.setDcMs_Vol1(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.PhV.phsC")) {
																			entitySMA3000.setPhV_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.PhV.phsB")) {
																			entitySMA3000.setPhV_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VAr.phsA")) {
																			entitySMA3000.setVAr_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.DcMs.Amp[0]")) {
																			entitySMA3000.setDcMs_Amp0(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Amp[1]")) {
																			entitySMA3000.setDcMs_Amp1(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.TotVAr.Pv")) {
																			entitySMA3000.setTotVAr_Pv(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VAr.phsB")) {
																			entitySMA3000.setVAr_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.PhV.phsA")) {
																			entitySMA3000.setPhV_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VAr.phsC")) {
																			entitySMA3000.setVAr_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VA.phsA")) {
																			entitySMA3000.setVA_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VA.phsB")) {
																			entitySMA3000.setVA_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VA.phsC")) {
																			entitySMA3000.setVA_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.TotW.Pv")) {
																			entitySMA3000.setTotW_Pv(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Metering.TotFeedTms")) {
																			entitySMA3000.setMetering_TotFeedTms(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Operation.GriSwCnt")) {
																			entitySMA3000.setOperation_GriSwCnt(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Metering.TotOpTms")) {
																			entitySMA3000.setMetering_TotOpTms(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Operation.Health")) {
																			entitySMA3000.setOperation_Health(mean != null ? mean : null);
																		}
																		
																		else if (field.equals("Measurement.Metering.TotWhOut")) {
																			entitySMA3000.setMetering_TotWhOut(mean != null  ? Double.parseDouble(mean)/1000 : 0.001);
																			entitySMA3000.setNvmActiveEnergy(mean != null ? Double.parseDouble(mean)/1000 : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Metering.TotWhOut.Pv")) {
																			entitySMA3000.setTotWhOut_Pv(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}

																		
																		break;
																		
																	case "model_sma_inverter_stp24000tlus10":
																		entitySMA24000.setTime(formatterUtcDateTime);
																		// Put data to entity
																		if (field.equals("Measurement.GridMs.TotVAr")) {
																			entitySMA24000.setGridMs_TotVAr(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.DcMs.Watt[0]")) {
																			entitySMA24000.setDcMs_Watt0(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Watt[1]")) {
																			entitySMA24000.setDcMs_Watt1(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.GridMs.W.phsA")) {
																			entitySMA24000.setW_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.W.phsB")) {
																			entitySMA24000.setW_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.A.phsB")) {
																			entitySMA24000.setA_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.W.phsC")) {
																			entitySMA24000.setW_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.TotW")) {
																			entitySMA24000.setGridMs_TotW(mean != null  ? Double.parseDouble(mean) / 1000 : 0.001);
																			entitySMA24000.setNvmActivePower(mean != null  ? Double.parseDouble(mean) / 1000 : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.TotVA")) {
																			entitySMA24000.setGridMs_TotVA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.A.phsC")) {
																			entitySMA24000.setA_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.Hz")) {
																			entitySMA24000.setGridMs_Hz(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.A.phsA")) {
																			entitySMA24000.setA_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Isolation.LeakRis")) {
																			entitySMA24000.setIsolation_LeakRis(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.DcMs.Vol[0]")) {
																			entitySMA24000.setDcMs_Vol0(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Vol[1]")) {
																			entitySMA24000.setDcMs_Vol1(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.PhV.phsC")) {
																			entitySMA24000.setPhV_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.PhV.phsB")) {
																			entitySMA24000.setPhV_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VAr.phsA")) {
																			entitySMA24000.setVAr_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.DcMs.Amp[0]")) {
																			entitySMA24000.setDcMs_Amp0(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Amp[1]")) {
																			entitySMA24000.setDcMs_Amp1(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.TotVAr.Pv")) {
																			entitySMA24000.setTotVAr_Pv(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VAr.phsB")) {
																			entitySMA24000.setVAr_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.PhV.phsA")) {
																			entitySMA24000.setPhV_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VAr.phsC")) {
																			entitySMA24000.setVAr_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VA.phsA")) {
																			entitySMA24000.setVA_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VA.phsB")) {
																			entitySMA24000.setVA_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VA.phsC")) {
																			entitySMA24000.setVA_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.TotW.Pv")) {
																			entitySMA24000.setTotW_Pv(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Metering.TotFeedTms")) {
																			entitySMA24000.setMetering_TotFeedTms(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Operation.GriSwCnt")) {
																			entitySMA24000.setOperation_GriSwCnt(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Metering.TotOpTms")) {
																			entitySMA24000.setMetering_TotOpTms(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Operation.Health")) {
																			entitySMA24000.setOperation_Health(mean != null ? mean : null);
																		}
																		
																		else if (field.equals("Measurement.Metering.TotWhOut")) {
																			entitySMA24000.setMetering_TotWhOut(mean != null  ? Double.parseDouble(mean)/1000 : 0.001);
																			entitySMA24000.setNvmActiveEnergy(mean != null ? Double.parseDouble(mean)/1000 : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Metering.TotWhOut.Pv")) {
																			entitySMA24000.setTotWhOut_Pv(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Operation.Evt.EvtNoShrt")) {
																			entitySMA24000.setEvt_EvtNoShrt(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}

																		
																		break;
																		
																	case "model_sma_inverter_stp62us41":
																		entitySMA62.setTime(formatterUtcDateTime);
																		// Insert data
																		if (field.equals("Measurement.GridMs.VA.phsA")) {
																			entitySMA62.setVA_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VA.phsB")) {
																			entitySMA62.setVA_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.DcMs.Vol[0]")) {
																			entitySMA62.setDcMs_Vol0(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Vol[1]") ) {
																			entitySMA62.setDcMs_Vol1(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Vol[2]")) {
																			entitySMA62.setDcMs_Vol2(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Vol[3]")) {
																			entitySMA62.setDcMs_Vol3(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Vol[4]")) {
																			entitySMA62.setDcMs_Vol4(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Vol[5]")) {
																			entitySMA62.setDcMs_Vol5(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.TotW.Pv")) {
																			entitySMA62.setTotW_Pv(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Isolation.LeakRis")) {
																			entitySMA62.setIsolation_LeakRis(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.PhV.phsC")) {
																			entitySMA62.setPhV_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.Hz")) {
																			entitySMA62.setGridMs_Hz(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.W.phsB")) {
																			entitySMA62.setW_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.TotW")) {
																			entitySMA62.setGridMs_TotW(mean != null  ? Double.parseDouble(mean)/1000 : 0.001);
																			entitySMA62.setNvmActivePower(mean != null  ? Double.parseDouble(mean) / 1000 : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.W.phsC")) {
																			entitySMA62.setW_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VAr.phsC")) {
																			entitySMA62.setVAr_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.DcMs.Watt[0]")) {
																			entitySMA62.setDcMs_Watt0(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Watt[1]")) {
																			entitySMA62.setDcMs_Watt1(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Watt[2]")) {
																			entitySMA62.setDcMs_Watt2(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Watt[3]")) {
																			entitySMA62.setDcMs_Watt3(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Watt[4]")) {
																			entitySMA62.setDcMs_Watt4(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Watt[5]")) {
																			entitySMA62.setDcMs_Watt5(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.W.phsA")) {
																			entitySMA62.setW_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VAr.phsB")) {
																			entitySMA62.setVAr_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.TotVAr.Pv")) {
																			entitySMA62.setTotVAr_Pv(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.PhV.phsA2B")) {
																			entitySMA62.setPhV_phsA2B(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VAr.phsA")) {
																			entitySMA62.setVAr_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.TotVA")) {
																			entitySMA62.setGridMs_TotVA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.TotVAr")) {
																			entitySMA62.setGridMs_TotVAr(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.DcMs.Amp[0]")) {
																			entitySMA62.setDcMs_Amp0(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Amp[1]")) {
																			entitySMA62.setDcMs_Amp1(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Amp[2]")) {
																			entitySMA62.setDcMs_Amp2(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Amp[3]")) {
																			entitySMA62.setDcMs_Amp3(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Amp[4]")) {
																			entitySMA62.setDcMs_Amp4(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Amp[5]")) {
																			entitySMA62.setDcMs_Amp5(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.PhV.phsB2C")) {
																			entitySMA62.setPhV_phsB2C(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.PhV.phsB")) {
																			entitySMA62.setPhV_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.A.phsA")) {
																			entitySMA62.setA_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.PhV.phsC2A")) {
																			entitySMA62.setPhV_phsC2A(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.A.phsB")) {
																			entitySMA62.setA_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.PhV.phsA")) {
																			entitySMA62.setPhV_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VA.phsC")) {
																			entitySMA62.setVA_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.A.phsC")) {
																			entitySMA62.setA_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Metering.TotWhOut")) {
																			entitySMA62.setMetering_TotWhOut(mean != null  ? Double.parseDouble(mean) /1000 : 0.001);
																			entitySMA62.setNvmActiveEnergy(mean != null ? Double.parseDouble(mean) / 1000 : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Operation.Health")) {
																			entitySMA62.setOperation_Health(mean != null  ? mean : null);
																		}
																		
																		else if (field.equals("Measurement.Operation.GriSwCnt")) {
																			entitySMA62.setOperation_GriSwCnt(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Metering.TotWhOut.Pv")) {
																			entitySMA62.setTotWhOut_Pv(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Metering.TotFeedTms")) {
																			entitySMA62.setMetering_TotFeedTms(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Metering.TotOpTms")) {
																			entitySMA62.setMetering_TotOpTms(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}

																		break;
																	default:
																		break;
																	}
																}
															}
														}

													}
													
													switch (deviceItem.getDevice_group_table()) {
													case "model_sma_cluster_controller":
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, entityCluster);
														
														deviceItem.setLast_value(entityCluster.getGridMs_TotW() >= 0 ? entityCluster.getGridMs_TotW() : null);
														deviceItem.setField_value1(entityCluster.getGridMs_TotW() >= 0 ? entityCluster.getGridMs_TotW() : null);
														
														deviceItem.setField_value2(null);
														deviceItem.setField_value3(null);
														
														uploadFilesService.handleEnergyField(deviceItem, entityCluster, "Metering_TotWhOut");
														
														serviceUmg604.insertModelSmaClusterController(entityCluster);
														
														baseEntity = entityCluster;
														break;
													
													case "model_sma_inverter_stp1200tlus10":
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, entitySMA12k);
														
														deviceItem.setLast_value(entitySMA12k.getGridMs_TotW() >= 0 ? entitySMA12k.getGridMs_TotW() : null);
														deviceItem.setField_value1(entitySMA12k.getGridMs_TotW() >= 0 ? entitySMA12k.getGridMs_TotW() : null);
														
														deviceItem.setField_value2(null);
														deviceItem.setField_value3(null);
														
														uploadFilesService.handleEnergyField(deviceItem, entitySMA12k, "Metering_TotWhOut");
														
														serviceSMA12k.insertModelSmaInverterStp1200tlus10(entitySMA12k);
														
														baseEntity = entitySMA12k;
														break;
														
													case "model_sma_inverter_stp24ktlus10":
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, entitySMA24k);
														
														deviceItem.setLast_value(entitySMA24k.getGridMs_TotW()  >= 0 ? entitySMA24k.getGridMs_TotW() : null);
														deviceItem.setField_value1(entitySMA24k.getGridMs_TotW()  >= 0 ? entitySMA24k.getGridMs_TotW() : null);
														
														deviceItem.setField_value2(null);
														deviceItem.setField_value3(null);
														
														uploadFilesService.handleEnergyField(deviceItem, entitySMA24k, "Metering_TotWhOut");
														
														serviceSMA24k.insertModelSmaInverterStp24ktlus10(entitySMA24k);
														
														baseEntity = entitySMA24k;
														break;
														
													case "model_sma_inverter_stp30000tlus10":
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, entitySMA3000);
														
														deviceItem.setLast_value(entitySMA3000.getGridMs_TotW()  >= 0 ? entitySMA3000.getGridMs_TotW() : null);
														deviceItem.setField_value1(entitySMA3000.getGridMs_TotW()  >= 0 ? entitySMA3000.getGridMs_TotW() : null);

														deviceItem.setField_value2(null);
														deviceItem.setField_value3(null);
														
														uploadFilesService.handleEnergyField(deviceItem, entitySMA3000, "Metering_TotWhOut");
														
														serviceSMA3000.insertModelSmaInverterStp3000ktlus10(entitySMA3000);
														
														baseEntity = entitySMA3000;
														break;
														
													case "model_sma_inverter_stp24000tlus10":
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, entitySMA24000);
														
														deviceItem.setLast_value(entitySMA24000.getGridMs_TotW()  >= 0 ? entitySMA24000.getGridMs_TotW() : null);
														deviceItem.setField_value1(entitySMA24000.getGridMs_TotW()  >= 0 ? entitySMA24000.getGridMs_TotW() : null);

														deviceItem.setField_value2(null);
														deviceItem.setField_value3(null);
														
														uploadFilesService.handleEnergyField(deviceItem, entitySMA24000, "Metering_TotWhOut");
														
														serviceSMA24000.insertModelSmaInverterStp24000ktlus10(entitySMA24000);
														
														baseEntity = entitySMA24000;
														break;
														
													case "model_sma_inverter_stp62us41":
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, entitySMA62);
														
														deviceItem.setLast_value(entitySMA62.getGridMs_TotW()  >= 0 ? entitySMA62.getGridMs_TotW() : null);
														deviceItem.setField_value1(entitySMA62.getGridMs_TotW()  >= 0 ? entitySMA62.getGridMs_TotW() : null);
														
														deviceItem.setField_value2(null);
														deviceItem.setField_value3(null);
														
														uploadFilesService.handleEnergyField(deviceItem, entitySMA62, "Metering_TotWhOut");
														
														serviceSMA62.insertModelSmaInverterStp62us41(entitySMA62);
														
														baseEntity = entitySMA62;
														break;
													}
													
													uploadFilesService.deviceLastUpdated(deviceItem, baseEntity);
												}
												
												
											} catch (ParserConfigurationException | SAXException | IOException e) {
												e.printStackTrace();
											}
											
											uploadFilesService.deletingFile(dirFolderXML, path.getFileName().toString());
										}
									}
								});
							}
						}
						

						

					} catch (IOException ex) {
						ex.printStackTrace();
					}
			}
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
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

					// download the sub directory
					downloadDirectory(ftpClient, dirToList, currentFileName, saveDir, id_site);
				} else {
					// download the file
					
					File f = new File(newDirPath);
					// create the directory in saveDir
					File fDir = new File(saveDir + parentDir);

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
									
									// check newFile is existed and rename that file existed
									if (newFile.exists()) {
										String fileName = newFile.getName();
										
										Date date = new Date();
										SimpleDateFormat sdfAmerica = new SimpleDateFormat("yyyyMMddHHmmss");
										String dateDownload = sdfAmerica.format(date);										
										fileName = dateDownload + "_"+ fileName;
										
										newFile.renameTo(new File(destDir, fileName));						
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
									}
									
								}
								zipEntry = zis.getNextEntry();
							}

							zis.closeEntry();
							zis.close();
							
							
							ftpClient.deleteFile(filePath);

						}
					}

				}
			}
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
			parentDir.mkdirs();
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
	
	
	
	/**
	 * @description get last_updated device
	 * @author long.pham
	 * @since 2023-06-16
	 * @return {}
	 */
	@GetMapping("/update-last-time-device")
	public Object updateLastTimeDevice() {
		try {
			BatchJobService service = new BatchJobService();
			List<?> listDevice = service.getListDeviceUpdateLastUpdate(new DeviceEntity());
			if (listDevice == null || listDevice.size() == 0) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
			for (int i = 0; i < listDevice.size(); i++) {
				DeviceEntity deviceItem = (DeviceEntity) listDevice.get(i);
				BatchJobTableEntity obj = new BatchJobTableEntity();
				obj.setId_device(deviceItem.getId());
				String datatablename = deviceItem.getDatatablename();
				obj.setDatatablename(deviceItem.getView_tablename());
				
				BatchJobTableEntity lastRow = service.getLastRowItemUpdateDate(obj);
				DeviceEntity deviceUpdateE = new DeviceEntity();
				if(lastRow.getNvmActivePower() >= 0 && Constants.ModbusError.fromValue(lastRow.getError()) != Constants.ModbusError.DEVICE_FAILED_TO_RESPOND) {
					deviceUpdateE.setId(deviceItem.getId());
					deviceUpdateE.setLast_updated(lastRow.getTime());
					// check lastRow if lastRow not in View Table
					if(lastRow.getTime() == null) {
						obj.setDatatablename(datatablename);
						BatchJobTableEntity lastRowDatatablename = service.getLastRowItemUpdateDate(obj);
						deviceUpdateE.setLast_updated(lastRowDatatablename.getTime());
					}
                    service.updateLastUpdatedCronJob(deviceUpdateE);
				} else {
					// deviceUpdateE.setLast_updated(null);
				}
				// service.updateLastUpdatedCronJob(deviceUpdateE);
				
			}
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
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
	 * @throws Exception 
	 */
	public void downloadImageDirectory(FTPClient ftpClient, String parentDir, String currentDir, String saveDir, int id_site, int id_device, String datatablename)
			throws Exception {
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

					// download the sub directory
					downloadImageDirectory(ftpClient, dirToList, currentFileName, saveDir, id_site, id_device, datatablename);
				} else {
					// download the file
					
					File f = new File(newDirPath);
					// create the directory in saveDir
					File fDir = new File(saveDir + parentDir);

					if (!f.exists()) {
						// do something
						boolean success = downloadSingleFile(ftpClient, filePath, newDirPath);

						if (success) {
							String created_date = "";
							String month = "";
							String year = "";
							
							String regex = ".*(\\d{2}-\\d{2}-\\d{2}_\\d{2}-\\d{2}-\\d{2}).*";
							Pattern pattern = Pattern.compile(regex);
						    Matcher m = pattern.matcher(currentFileName);
						    
						    //yyyymmddhhmmssMMM_
						    String regex2 = ".*(\\d{17}_).*";
						    Pattern pattern2 = Pattern.compile(regex2);
						    Matcher m2 = pattern2.matcher(currentFileName);
						    
						    						    
						    // date by file name
						    if (m.find()){
						    	String unformatDate = m.group(1);
						    	
						    	String yyyyMMdd = "20" + unformatDate.split("_")[0].replace("-", ":");
						        month = yyyyMMdd.split(":", 3)[1];
						        year = yyyyMMdd.split(":", 3)[0];
						        String HHmmss = unformatDate.split("_")[1].replace("-", ":");
						        created_date = yyyyMMdd + " " + HHmmss;
						    } else if (m2.find()) {
						    	String unformatDate = m2.group(1);
						    	unformatDate = unformatDate.substring(0, unformatDate.length() - 4);
						    	
						    	String yyyyString = unformatDate.substring(0, 4);
						    	String MMString = unformatDate.substring(4, 6);
						    	String ddString = unformatDate.substring(6, 8);
						    	String HHString = unformatDate.substring(8, 10);
						    	String mmString = unformatDate.substring(10, 12);
						    	String ssString = unformatDate.substring(12, 14);
						    	
						    	month = MMString;
						    	year = yyyyString;
						    	created_date = yyyyString + ":" + MMString + ":" + ddString + " " + HHString + ":" + mmString + ":" + ssString;
						    }
						    
						    
						    
						    // if date by filename is empty ->  date by file on ftp server
						    Calendar currrentDateCalendar = aFile.getTimestamp();	
						    
					    	created_date =  created_date != "" ? created_date : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currrentDateCalendar.getTime());
					    	
					    	month = month != "" ? month : ((currrentDateCalendar.get(Calendar.MONTH) + 1) < 10 ? ("0"+(currrentDateCalendar.get(Calendar.MONTH) + 1)): String.valueOf(currrentDateCalendar.get(Calendar.MONTH) + 1));
					   
					    	year = year != "" ? year : String.valueOf(currrentDateCalendar.get(Calendar.YEAR));
																				
							String filePathImage = awsService.uploadFile(newDirPath, "camera" + "/" + id_site  + parentDir + "/"+ year + "/" + month + "/" + currentFileName);
							
							if (filePathImage != null || filePathImage != "") {
								CameraImageEntity cameraImage = new CameraImageEntity();
								cameraImage.setId_device(id_device);
								cameraImage.setTime(created_date);
								cameraImage.setImage_url(filePathImage);
								cameraImage.setDatatablename(datatablename);
								
								BatchJobService service = new BatchJobService();
								CameraImageEntity image = service.insertCameraImage(cameraImage);
								
								// Update last updated
								DeviceService serviceD = new DeviceService();
								DeviceEntity deviceUpdateE = new DeviceEntity();						
								deviceUpdateE.setLast_updated(created_date);
								deviceUpdateE.setLast_value(null);							
								deviceUpdateE.setId(id_device);
								serviceD.updateLastUpdated(deviceUpdateE);
								
								if (image != null) {
									new File(newDirPath).delete();
								}
							}
							
							ftpClient.deleteFile(filePath);

						}
					}

				}
			}
		}
	}
	
	/**
	 * @description Get list file from FTP server
	 * @author long.pham
	 * @since 2023-06-16
	 * @return {}
	 */
	@GetMapping("/download-image-camera-from-ftp")
	public Object downloadImageCameraFromFTP() {
		try {
			BatchJobService service = new BatchJobService();
			List<?> listDevices = service.getListCameraDevice(new DeviceEntity());
			if (listDevices == null || listDevices.size() == 0) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
			for (int i = 0; i < listDevices.size(); i++) {
				DeviceEntity deviceItem = (DeviceEntity) listDevices.get(i);
				if (deviceItem.getDevice_ftp_server() != null && deviceItem.getDevice_ftp_user() != null && deviceItem.getDevice_ftp_pass() != null && deviceItem.getDevice_ftp_folder() != null) {
					String server = deviceItem.getDevice_ftp_server();
					String user = deviceItem.getDevice_ftp_user();
					String pass = deviceItem.getDevice_ftp_pass();
					int port = Integer.parseInt(deviceItem.getDevice_ftp_port());
					String remoteDirPath = deviceItem.getDevice_ftp_folder();

					String saveDirPath = Lib.getReourcePropValue(Constants.appConfigFileName,
							Constants.uploadRootPathConfigKey) + "/" + deviceItem.getId_site();
					
					FTPClient ftpClient = new FTPClient();

					try {
						ftpClient.connect(server, port);
						int replyCode = ftpClient.getReplyCode();
						if (!FTPReply.isPositiveCompletion(replyCode)) {
							return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
						}
						boolean success = ftpClient.login(user, pass);
						if (!success) {
							return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
						}
					
						downloadImageDirectory(ftpClient, remoteDirPath, "", saveDirPath, deviceItem.getId_site(), deviceItem.getId(), deviceItem.getDatatablename());
//						String filePath = awsService.uploadFile(saveDirPath + "/" + currentFileName, Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyGallery) + "/" + currentFileName);
						
					} catch (IOException ex) {
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
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
}
