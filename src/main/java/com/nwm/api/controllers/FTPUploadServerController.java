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
import java.lang.reflect.InvocationTargetException;
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
import java.util.stream.StreamSupport;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import net.objecthunter.exp4j.ExpressionBuilder;


import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.nwm.api.entities.BatchJobTableEntity;
import com.nwm.api.entities.DeviceEntity;
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
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/upload-ftp")
public class FTPUploadServerController extends BaseController {

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
					TimeZone tzInAmerica = TimeZone.getTimeZone(siteItem.getDisplay_timezone());
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
								StreamSupport.stream(stream.spliterator(), false)
							    .sorted(Comparator.comparing(Path::toString))
							    .forEach(path -> { 
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
													
													List<DeviceEntity> scaledDeviceParameters = serviceD.getListScaledDeviceParameter(deviceItem);

													
													String[] itemXML = {"MeanPublic", "CurrentPublic"};
													ModelSmaInverterStp3000ktlus10Entity entitySMA3000 = new ModelSmaInverterStp3000ktlus10Entity();
													entitySMA3000.setId_device(deviceItem.getId());
													entitySMA3000.setView_tablename(deviceItem.getView_tablename());
													entitySMA3000.setJob_tablename(deviceItem.getJob_tablename());
													entitySMA3000.setDatatablename(deviceItem.getDatatablename());
													
													ModelSmaInverterStp24000ktlus10Entity entitySMA24000 = new ModelSmaInverterStp24000ktlus10Entity();
													entitySMA24000.setId_device(deviceItem.getId());
													entitySMA24000.setView_tablename(deviceItem.getView_tablename());
													entitySMA24000.setJob_tablename(deviceItem.getJob_tablename());
													entitySMA24000.setDatatablename(deviceItem.getDatatablename());
													
													ModelSmaInverterStp62us41Entity entitySMA62 = new ModelSmaInverterStp62us41Entity();
													entitySMA62.setId_device(deviceItem.getId());
													entitySMA62.setView_tablename(deviceItem.getView_tablename());
													entitySMA62.setJob_tablename(deviceItem.getJob_tablename());
													entitySMA62.setDatatablename(deviceItem.getDatatablename());
													
													ModelSmaInverterStp24ktlus10Entity entitySMA24k = new ModelSmaInverterStp24ktlus10Entity();
													entitySMA24k.setId_device(deviceItem.getId());
													entitySMA24k.setView_tablename(deviceItem.getView_tablename());
													entitySMA24k.setJob_tablename(deviceItem.getJob_tablename());
													entitySMA24k.setDatatablename(deviceItem.getDatatablename());
													
													ModelSmaInverterStp1200tlus10Entity entitySMA12k = new ModelSmaInverterStp1200tlus10Entity();
													entitySMA12k.setId_device(deviceItem.getId());
													entitySMA12k.setView_tablename(deviceItem.getView_tablename());
													entitySMA12k.setJob_tablename(deviceItem.getJob_tablename());
													entitySMA12k.setDatatablename(deviceItem.getDatatablename());
													
													ModelSmaClusterControllerEntity entityCluster = new ModelSmaClusterControllerEntity();
													entityCluster.setId_device(deviceItem.getId());
													entityCluster.setView_tablename(deviceItem.getView_tablename());
													entityCluster.setJob_tablename(deviceItem.getJob_tablename());
													entityCluster.setDatatablename(deviceItem.getDatatablename());
													
													
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
																
																

																if (deviceItem.getId() > 0) {
																	// Insert to datatable
																	switch (deviceItem.getDevice_group_table()) {
																	case "model_sma_cluster_controller":
																		entityCluster.setTime(formatterUtcDateTime);
																		if(field.equals("Metering.TotWhOut") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { 
																			entityCluster.setMetering_TotWhOut(mean != null  ? Double.parseDouble(mean) : 0.001); 
																			entityCluster.setNvmActiveEnergy(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		else if(field.equals("Operation.GriSwCnt") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setOperation_GriSwCnt(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("Metering.TotFeedTms") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setMetering_TotOpTms(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.TotW") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { 
																			entityCluster.setGridMs_TotW(mean != null  ? Double.parseDouble(mean)/1000 : 0.001);
																			entityCluster.setNvmActivePower(mean != null  ? Double.parseDouble(mean) / 1000 : 0.001); 
																		}
																		
																		else if(field.equals("GridMs.Hz") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setGridMs_Hz(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("Isolation.FltA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setIsolation_FltA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("Isolation.LeakRis") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setIsolation_LeakRis(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Vol[A]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setDcMs_VolA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Vol[B]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setDcMs_VolB(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Amp[A]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setDcMs_AmpA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Amp[B]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setDcMs_AmpB(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.PhV.phsA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setGridMs_PhV_phsA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.PhV.phsB") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setGridMs_PhV_phsB(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.PhV.phsC") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setGridMs_PhV_phsC(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.A.phsA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setGridMs_A_phsA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.A.phsB") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setGridMs_A_phsB(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.A.phsC") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setGridMs_A_phsC(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Watt[A]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setDcMs_WattA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Watt[B]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setDcMs_WattB(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("Operation.Health") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setOperation_Health(mean != null ? mean : null); }
																		else if(field.equals("Operation.Evt.Prio") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setOperation_Evt_Prio(mean != null ? mean : null); }
																		else if(field.equals( "Operation.Evt.Msg") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setOperation_Evt_Msg(mean != null ? mean : null); }
																		else if(field.equals("Operation.Evt.Dsc") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setOperation_Evt_Dsc(mean != null ? mean : null); }
																		else if(field.equals("InOut.AnInA1") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setInOut_AnInA1(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("InOut.AnInA2") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setInOut_AnInA2(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("InOut.AnInA3") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setInOut_AnInA3(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("InOut.AnInVol4") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setInOut_AnInVol4(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("Env.ExInsol") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entityCluster.setEnv_ExInsol(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		break;
																
																	case "model_sma_inverter_stp1200tlus10":
																		entitySMA12k.setTime(formatterUtcDateTime);
																		if(field.equals("Metering.TotWhOut") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { 
																			entitySMA12k.setMetering_TotWhOut(mean != null  ? Double.parseDouble(mean) : 0.001);
																			entitySMA12k.setNvmActiveEnergy(mean != null ? Double.parseDouble(mean) : 0.001);
																		}
																		else if(field.equals("Operation.GriSwCnt") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entitySMA12k.setOperation_GriSwCnt(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("Metering.TotFeedTms") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entitySMA12k.setMetering_TotOpTms(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("Metering.GridMs.TotWhOut") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entitySMA12k.setMetering_GridMs_TotWhOut(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.TotW") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA12k.setGridMs_TotW(mean != null  ? Double.parseDouble(mean)/1000 : 0.001);
																			entitySMA12k.setNvmActivePower(mean != null  ? Double.parseDouble(mean) / 1000 : 0.001); 
																		}
																		else if(field.equals("GridMs.Hz") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entitySMA12k.setGridMs_Hz(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("Isolation.FltA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entitySMA12k.setIsolation_FltA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("Isolation.LeakRis") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entitySMA12k.setIsolation_LeakRis(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Vol[A]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entitySMA12k.setDcMs_VolA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Vol[B]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entitySMA12k.setDcMs_VolB(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Amp[A]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entitySMA12k.setDcMs_AmpA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Amp[B]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entitySMA12k.setDcMs_AmpB(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.PhV.phsA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entitySMA12k.setGridMs_PhV_phsA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.PhV.phsB") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entitySMA12k.setGridMs_PhV_phsB(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.PhV.phsC") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entitySMA12k.setGridMs_PhV_phsC(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.A.phsA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entitySMA12k.setGridMs_A_phsA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.A.phsB") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entitySMA12k.setGridMs_A_phsB(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("GridMs.A.phsC") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entitySMA12k.setGridMs_A_phsC(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Watt[A]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entitySMA12k.setDcMs_WattA(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("DcMs.Watt[B]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entitySMA12k.setDcMs_WattB(mean != null  ? Double.parseDouble(mean) : 0.001); }
																		else if(field.equals("Operation.Health") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entitySMA12k.setOperation_Health(mean != null ? mean : null); }
																		else if(field.equals("Operation.Evt.Prio") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entitySMA12k.setOperation_Evt_Prio(mean != null ? mean : null); }
																		else if(field.equals( "Operation.Evt.Msg") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { entitySMA12k.setOperation_Evt_Msg(mean != null ? mean : null); }
																		else if(field.equals("Operation.Evt.Dsc") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) { 
																			entitySMA12k.setOperation_Evt_Dsc(mean != null ? mean : null); 
																		}
																		
																		break;
																	case "model_sma_inverter_stp24ktlus10":
																		entitySMA24k.setTime(formatterUtcDateTime);
																		// Put data to entity
																		if (field.equals("Metering.TotWhOut") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setMetering_TotWhOut(mean != null  ? Double.parseDouble(mean) : 0.001);
																			entitySMA24k.setNvmActiveEnergy(mean != null ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("Operation.GriSwCnt") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setOperation_GriSwCnt(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("Metering.TotOpTms") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setMetering_TotOpTms(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("Metering.TotFeedTms") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setMetering_TotFeedTms(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("Metering.GridMs.TotWhOut") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setMetering_GridMs_TotWhOut(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("GridMs.TotW") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setGridMs_TotW(mean != null  ? Double.parseDouble(mean)/100 : 0.001);
																			entitySMA24k.setNvmActivePower(mean != null  ? Double.parseDouble(mean) / 100 : 0.001);
																		}
																		  
																		if (field.equals("GridMs.Hz") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setGridMs_Hz(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("Isolation.FltA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setIsolation_FltA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("Isolation.LeakRis") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setIsolation_LeakRis(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("DcMs.Vol[A]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setDcMs_VolA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("DcMs.Vol[B]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setDcMs_VolB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("DcMs.Amp[A]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setDcMs_AmpA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("DcMs.Amp[B]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setDcMs_AmpB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("GridMs.PhV.phsA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setGridMs_PhV_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("GridMs.PhV.phsB") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setGridMs_PhV_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("GridMs.PhV.phsC") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setGridMs_PhV_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("GridMs.A.phsA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setGridMs_A_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("GridMs.A.phsB") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setGridMs_A_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("GridMs.A.phsC") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setGridMs_A_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("DcMs.Watt[A]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setDcMs_WattA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("DcMs.Watt[B]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setDcMs_WattB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		  
																		if (field.equals("Operation.Health") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setOperation_Health(mean != null ? mean : null);
																		}
																		  
																		if (field.equals("Operation.Evt.Prio") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setOperation_Evt_Prio(mean != null ? mean : null);
																		}
																		  
																		if (field.equals("Operation.Evt.Msg") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setOperation_Evt_Msg(mean != null ? mean : null);
																		}
																		 
																		if (field.equals("Operation.Evt.Dsc") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24k.setOperation_Evt_Dsc(mean != null ? mean : null);
																		}
																		
																		break;
																	case "model_sma_inverter_stp30000tlus10":
																		entitySMA3000.setTime(formatterUtcDateTime);
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
																			entitySMA3000.setGridMs_TotW(mean != null  ? Double.parseDouble(mean) / 1000 : 0.001);
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
																			entitySMA3000.setMetering_TotWhOut(mean != null  ? Double.parseDouble(mean)/1000 : 0.001);
																			entitySMA3000.setNvmActiveEnergy(mean != null ? Double.parseDouble(mean)/1000 : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Metering.TotWhOut.Pv") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA3000.setTotWhOut_Pv(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}

																		
																		break;
																		
																	case "model_sma_inverter_stp24000tlus10":
																		entitySMA24000.setTime(formatterUtcDateTime);
																		// Put data to entity
																		if (field.equals("Measurement.GridMs.TotVAr") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setGridMs_TotVAr(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.DcMs.Watt[0]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setDcMs_Watt0(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Watt[1]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setDcMs_Watt1(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.GridMs.W.phsA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setW_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.W.phsB") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setW_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.A.phsB") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setA_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.W.phsC") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setW_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.TotW") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setGridMs_TotW(mean != null  ? Double.parseDouble(mean) / 1000 : 0.001);
																			entitySMA24000.setNvmActivePower(mean != null  ? Double.parseDouble(mean) / 1000 : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.TotVA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setGridMs_TotVA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.A.phsC") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setA_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.Hz") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setGridMs_Hz(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.A.phsA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setA_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Isolation.LeakRis") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setIsolation_LeakRis(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.DcMs.Vol[0]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setDcMs_Vol0(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Vol[1]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setDcMs_Vol1(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.PhV.phsC") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setPhV_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.PhV.phsB") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setPhV_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VAr.phsA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setVAr_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.DcMs.Amp[0]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setDcMs_Amp0(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																
																		else if (field.equals("Measurement.DcMs.Amp[1]") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setDcMs_Amp1(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.TotVAr.Pv") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setTotVAr_Pv(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VAr.phsB") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setVAr_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.PhV.phsA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setPhV_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VAr.phsC") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setVAr_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VA.phsA") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setVA_phsA(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VA.phsB") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setVA_phsB(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.VA.phsC") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setVA_phsC(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.GridMs.TotW.Pv") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setTotW_Pv(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Metering.TotFeedTms") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setMetering_TotFeedTms(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Operation.GriSwCnt") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setOperation_GriSwCnt(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Metering.TotOpTms") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setMetering_TotOpTms(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Operation.Health") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setOperation_Health(mean != null ? mean : null);
																		}
																		
																		else if (field.equals("Measurement.Metering.TotWhOut") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setMetering_TotWhOut(mean != null  ? Double.parseDouble(mean)/1000 : 0.001);
																			entitySMA24000.setNvmActiveEnergy(mean != null ? Double.parseDouble(mean)/1000 : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Metering.TotWhOut.Pv") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setTotWhOut_Pv(mean != null  ? Double.parseDouble(mean) : 0.001);
																		}
																		
																		else if (field.equals("Measurement.Operation.Evt.EvtNoShrt") && modbusdevicenumber.equals(deviceItem.getModbusdevicenumber())) {
																			entitySMA24000.setEvt_EvtNoShrt(mean != null  ? Double.parseDouble(mean) : 0.001);
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
																			entitySMA62.setGridMs_TotW(mean != null  ? Double.parseDouble(mean)/1000 : 0.001);
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
																			entitySMA62.setMetering_TotWhOut(mean != null  ? Double.parseDouble(mean) /1000 : 0.001);
																			entitySMA62.setNvmActiveEnergy(mean != null ? Double.parseDouble(mean) / 1000 : 0.001);
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
																		break;
																	}
																}
															}
														}

													}
													
													switch (deviceItem.getDevice_group_table()) {
													case "model_sma_cluster_controller":
														// scaling device parameter
														if (scaledDeviceParameters.size() > 0) {
															for (int j = 0; j < scaledDeviceParameters.size(); j++) {
																DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
																String slug = scaledDeviceParameter.getParameter_slug();
																String scaleExpressions = scaledDeviceParameter.getParameter_scale();
																String variableName = scaledDeviceParameter.getVariable_name();
																PropertyDescriptor pd = new PropertyDescriptor(slug, ModelSmaClusterControllerEntity.class);
																Double initialValue = (Double) pd.getReadMethod().invoke(entityCluster);
																if (initialValue == 0.001) continue;
																Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
																pd.getWriteMethod().invoke(entityCluster, scaledValue);
																if (slug.equals("GridMs.TotW")) entityCluster.setNvmActivePower(scaledValue);
																if (slug.equals("Metering.TotWhOut")) entityCluster.setNvmActiveEnergy(scaledValue);
															}
														}
														
														serviceUmg604.insertModelSmaClusterController(entityCluster);
														// Update last value
														if(entityCluster.getNvmActivePower() >= 0) {
															deviceUpdateE.setLast_updated(entityCluster.getTime());
															deviceUpdateE.setLast_value(entityCluster.getNvmActivePower() >= 0 ? entityCluster.getNvmActivePower() : null);
															deviceUpdateE.setField_value1(entityCluster.getNvmActivePower() >= 0 ? entityCluster.getNvmActivePower() : null);
														} else {
															deviceUpdateE.setLast_updated(null);
															deviceUpdateE.setLast_value(null);
														}
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														deviceUpdateE.setId(entityCluster.getId_device());
														serviceD.updateLastUpdated(deviceUpdateE);
														break;
													
													case "model_sma_inverter_stp1200tlus10":
														// scaling device parameter
														if (scaledDeviceParameters.size() > 0) {
															for (int j = 0; j < scaledDeviceParameters.size(); j++) {
																DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
																String slug = scaledDeviceParameter.getParameter_slug();
																String scaleExpressions = scaledDeviceParameter.getParameter_scale();
																String variableName = scaledDeviceParameter.getVariable_name();
																PropertyDescriptor pd = new PropertyDescriptor(slug, ModelSmaInverterStp1200tlus10Entity.class);
																Double initialValue = (Double) pd.getReadMethod().invoke(entitySMA12k);
																if (initialValue == 0.001) continue;
																Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
																pd.getWriteMethod().invoke(entitySMA12k, scaledValue);
																if (slug.equals("GridMs.TotW")) entitySMA12k.setNvmActivePower(scaledValue);
																if (slug.equals("Metering.TotWhOut")) entitySMA12k.setNvmActiveEnergy(scaledValue);
															}
														}
														
														serviceSMA12k.insertModelSmaInverterStp1200tlus10(entitySMA12k);
														// Update last value
														if(entitySMA12k.getNvmActivePower() >= 0) {
															deviceUpdateE.setLast_updated(entitySMA12k.getTime());
															deviceUpdateE.setLast_value(entitySMA12k.getNvmActivePower() >= 0 ? entitySMA12k.getNvmActivePower() : null);
															deviceUpdateE.setField_value1(entitySMA12k.getNvmActivePower() >= 0 ? entitySMA12k.getNvmActivePower() : null);
														} else {
															deviceUpdateE.setLast_updated(null);
															deviceUpdateE.setLast_value(null);
														}
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														deviceUpdateE.setId(entitySMA12k.getId_device());
														serviceD.updateLastUpdated(deviceUpdateE);
														break;
													case "model_sma_inverter_stp24ktlus10":
														// scaling device parameter
														if (scaledDeviceParameters.size() > 0) {
															for (int j = 0; j < scaledDeviceParameters.size(); j++) {
																DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
																String slug = scaledDeviceParameter.getParameter_slug();
																String scaleExpressions = scaledDeviceParameter.getParameter_scale();
																String variableName = scaledDeviceParameter.getVariable_name();
																PropertyDescriptor pd = new PropertyDescriptor(slug, ModelSmaInverterStp24ktlus10Entity.class);
																Double initialValue = (Double) pd.getReadMethod().invoke(entitySMA24k);
																if (initialValue == 0.001) continue;
																Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
																pd.getWriteMethod().invoke(entitySMA24k, scaledValue);
																if (slug.equals("GridMs.TotW")) entitySMA24k.setNvmActivePower(scaledValue);
																if (slug.equals("Metering.TotWhOut")) entitySMA24k.setNvmActiveEnergy(scaledValue);
															}
														}
														
														serviceSMA24k.insertModelSmaInverterStp24ktlus10(entitySMA24k);
														if (entitySMA24k.getGridMs_TotW() > 0) {
															deviceUpdateE.setLast_updated(entitySMA24k.getTime());
															deviceUpdateE.setLast_value(entitySMA24k.getGridMs_TotW()  > 0 ? entitySMA24k.getGridMs_TotW() : null);
															deviceUpdateE.setField_value1(entitySMA24k.getGridMs_TotW()  > 0 ? entitySMA24k.getGridMs_TotW() : null);
														} else {
															deviceUpdateE.setLast_updated(null);
															deviceUpdateE.setLast_value(null);
															deviceUpdateE.setField_value1(null);
														}
													
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														deviceUpdateE.setId(entitySMA24k.getId_device());
														serviceD.updateLastUpdated(deviceUpdateE);
														break;
														
													case "model_sma_inverter_stp30000tlus10":
														// scaling device parameter
														if (scaledDeviceParameters.size() > 0) {
															for (int j = 0; j < scaledDeviceParameters.size(); j++) {
																DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
																String slug = scaledDeviceParameter.getParameter_slug();
																String scaleExpressions = scaledDeviceParameter.getParameter_scale();
																String variableName = scaledDeviceParameter.getVariable_name();
																PropertyDescriptor pd = new PropertyDescriptor(slug, ModelSmaInverterStp3000ktlus10Entity.class);
																Double initialValue = (Double) pd.getReadMethod().invoke(entitySMA3000);
																if (initialValue == 0.001) continue;
																Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
																pd.getWriteMethod().invoke(entitySMA3000, scaledValue);
																if (slug.equals("Measurement.GridMs.TotW")) entitySMA3000.setNvmActivePower(scaledValue);
																if (slug.equals("Measurement.Metering.TotWhOut")) entitySMA3000.setNvmActiveEnergy(scaledValue);
															}
														}
														
														serviceSMA3000.insertModelSmaInverterStp3000ktlus10(entitySMA3000);
														if (entitySMA3000.getGridMs_TotW() > 0) {
															deviceUpdateE.setLast_updated(entitySMA3000.getTime());
															deviceUpdateE.setLast_value(entitySMA3000.getGridMs_TotW()  > 0 ? entitySMA3000.getGridMs_TotW() : null);
															deviceUpdateE.setField_value1(entitySMA3000.getGridMs_TotW()  > 0 ? entitySMA3000.getGridMs_TotW() : null);
														} else {
															deviceUpdateE.setLast_updated(null);
															deviceUpdateE.setLast_value(null);
															deviceUpdateE.setField_value1(null);
														}

														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														deviceUpdateE.setId(entitySMA3000.getId_device());
														serviceD.updateLastUpdated(deviceUpdateE);
														break;
														
													case "model_sma_inverter_stp24000tlus10":
														// scaling device parameter
														if (scaledDeviceParameters.size() > 0) {
															for (int j = 0; j < scaledDeviceParameters.size(); j++) {
																DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
																String slug = scaledDeviceParameter.getParameter_slug();
																String scaleExpressions = scaledDeviceParameter.getParameter_scale();
																String variableName = scaledDeviceParameter.getVariable_name();
																PropertyDescriptor pd = new PropertyDescriptor(slug, ModelSmaInverterStp24000ktlus10Entity.class);
																Double initialValue = (Double) pd.getReadMethod().invoke(entitySMA24000);
																if (initialValue == 0.001) continue;
																Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
																pd.getWriteMethod().invoke(entitySMA3000, scaledValue);
																if (slug.equals("Measurement.GridMs.TotW")) entitySMA24000.setNvmActivePower(scaledValue);
																if (slug.equals("Measurement.Metering.TotWhOut")) entitySMA24000.setNvmActiveEnergy(scaledValue);
															}
														}
														
														serviceSMA24000.insertModelSmaInverterStp24000ktlus10(entitySMA24000);
														if (entitySMA24000.getGridMs_TotW() > 0) {
															deviceUpdateE.setLast_updated(entitySMA24000.getTime());
															deviceUpdateE.setLast_value(entitySMA24000.getGridMs_TotW()  > 0 ? entitySMA24000.getGridMs_TotW() : null);
															deviceUpdateE.setField_value1(entitySMA24000.getGridMs_TotW()  > 0 ? entitySMA24000.getGridMs_TotW() : null);
														} else {
															deviceUpdateE.setLast_updated(null);
															deviceUpdateE.setLast_value(null);
															deviceUpdateE.setField_value1(null);
														}

														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														deviceUpdateE.setId(entitySMA24000.getId_device());
														serviceD.updateLastUpdated(deviceUpdateE);
														break;
														
													case "model_sma_inverter_stp62us41":
														// scaling device parameter
														if (scaledDeviceParameters.size() > 0) {
															for (int j = 0; j < scaledDeviceParameters.size(); j++) {
																DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
																String slug = scaledDeviceParameter.getParameter_slug();
																String scaleExpressions = scaledDeviceParameter.getParameter_scale();
																String variableName = scaledDeviceParameter.getVariable_name();
																PropertyDescriptor pd = new PropertyDescriptor(slug, ModelSmaInverterStp62us41Entity.class);
																Double initialValue = (Double) pd.getReadMethod().invoke(entitySMA62);
																if (initialValue == 0.001) continue;
																Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
																pd.getWriteMethod().invoke(entitySMA62, scaledValue);
																if (slug.equals("Measurement.GridMs.TotW")) entitySMA62.setNvmActivePower(scaledValue);
																if (slug.equals("Measurement.Metering.TotWhOut")) entitySMA62.setNvmActiveEnergy(scaledValue);
															}
														}
														
														serviceSMA62.insertModelSmaInverterStp62us41(entitySMA62);
														
														if (entitySMA62.getGridMs_TotW() > 0) {
															deviceUpdateE.setLast_updated(entitySMA62.getTime());
															deviceUpdateE.setLast_value(entitySMA62.getGridMs_TotW()  > 0 ? entitySMA62.getGridMs_TotW() : null);
															deviceUpdateE.setField_value1(entitySMA62.getGridMs_TotW()  > 0 ? entitySMA62.getGridMs_TotW() : null);
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
												
												
											} catch (ParserConfigurationException | SAXException | IOException | IntrospectionException | IllegalAccessException | InvocationTargetException e) {
												e.printStackTrace();
											}
											
											// Delete file from server
											
											File logFile = new File(fileXML);
											logFile.delete();
										}
									}
								});
							}
						}
						

						

					} catch (IOException ex) {
						ex.printStackTrace();
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
				if(lastRow.getNvmActivePower() >= 0) {
					deviceUpdateE.setId(deviceItem.getId());
					deviceUpdateE.setLast_updated(lastRow.getTime());
					// check lastRow if lastRow not in View Table 
					if(lastRow.getTime() == null) {
						obj.setDatatablename(datatablename);
						BatchJobTableEntity lastRowDatatablename = service.getLastRowItemUpdateDate(obj);
						deviceUpdateE.setLast_updated(lastRowDatatablename.getTime());
					}
				} else {
					deviceUpdateE.setLast_updated(null);
				}
				service.updateLastUpdatedCronJob(deviceUpdateE);
				
			}
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
}
