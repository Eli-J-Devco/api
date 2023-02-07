/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.batchjob;


import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelSungrowSg110cxEntity;
import com.nwm.api.entities.ModelSungrowSg50cxEntity;
import com.nwm.api.entities.ModelSungrowUmg604Entity;
import com.nwm.api.entities.ModelSungrowWeatherPvmet75200Entity;
import com.nwm.api.services.BatchJobService;
import com.nwm.api.services.DeviceService;
import com.nwm.api.services.ModelSungrowSg110cxService;
import com.nwm.api.services.ModelSungrowSg50cxService;
import com.nwm.api.services.ModelSungrowUmg604Service;
import com.nwm.api.services.ModelSungrowWeatherPvmet75200Service;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.FLLogger;
import com.nwm.api.utils.Lib;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;
import java.io.File;

public class BatchJobFTP {
	protected final FLLogger log = FLLogger.getLogger("batchjob/" + this.getClass().getSimpleName());
	
	public static boolean downloadSingleFile(FTPClient ftpClient,
	        String remoteFilePath, String savePath) throws IOException {
	    File downloadFile = new File(savePath);
	     
	    File parentDir = downloadFile.getParentFile();
	    if (!parentDir.exists()) {
	        parentDir.mkdir();
	    }
	         
	    OutputStream outputStream = new BufferedOutputStream(
	            new FileOutputStream(downloadFile));
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
	
	
	public void readFolderFTP() {
		try {
	        String server = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.FTPSGServer);
	        String user = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.FTPSGUser);
	        String pass = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.FTPSGPass);
	        int port = 21;
	        String remoteDirPath = "/SGFTP"; 
	        
			String saveDirPath = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey) + "/" + Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyFTP);
	        
	        FTPClient ftpClient = new FTPClient();
	        
	        try {
	            ftpClient.connect(server, port);
	            int replyCode = ftpClient.getReplyCode();
	            if (!FTPReply.isPositiveCompletion(replyCode)) {
	                System.out.println("Connect failed");
	                return;
	            }
	            boolean success = ftpClient.login(user, pass);
	            if (!success) {
	                System.out.println("Could not login to the server");
	                return;
	            }
	            downloadDirectory(ftpClient, remoteDirPath, "", saveDirPath);
	            
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
		} catch (Exception e) {
			log.error(e);
		}

	}

	
	/**
	 * Download a whole directory from a FTP server.
	 * @param ftpClient an instance of org.apache.commons.net.ftp.FTPClient class.
	 * @param parentDir Path of the parent directory of the current directory being
	 * downloaded.
	 * @param currentDir Path of the current directory being downloaded.
	 * @param saveDir path of directory where the whole remote directory will be
	 * downloaded and saved.
	 * @throws IOException if any network or IO error occurred.
	 */
	public static void downloadDirectory(FTPClient ftpClient, String parentDir,
	        String currentDir, String saveDir) throws IOException {
	    String dirToList = parentDir;
	    if (!currentDir.equals("")) {
	        dirToList += "/" + currentDir;
	    }
	    
	 
	    FTPFile[] subFiles = ftpClient.listFiles(dirToList);
	 
	    if (subFiles != null && subFiles.length > 0) {
	        for (FTPFile aFile : subFiles) {
	            String currentFileName = aFile.getName();
	            if (currentFileName.equals(".") || currentFileName.equals("..")) {
	                // skip parent directory and the directory itself
	                continue;
	            }
	            String filePath = parentDir + "/" + currentDir + "/"
	                    + currentFileName;
	            if (currentDir.equals("")) {
	                filePath = parentDir + "/" + currentFileName;
	            }
	 
	            String newDirPath = saveDir + parentDir + File.separator
	                    + currentDir + File.separator + currentFileName;
	            if (currentDir.equals("")) {
	                newDirPath = saveDir + parentDir + File.separator
	                          + currentFileName;
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
	                downloadDirectory(ftpClient, dirToList, currentFileName, saveDir);
	            } else {
	                // download the file
	            	File f = new File(newDirPath);
	            	if(!f.exists()) { 
	            	    // do something
	            		boolean success = downloadSingleFile(ftpClient, filePath, newDirPath);
	            		if (success) {
		                    // Read file xml
		                    // Instantiate the Factory
		                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		                    try {

		                        // optional, but recommended
		                        // process XML securely, avoid attacks like XML External Entities (XXE)
		                        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

		                        // parse XML file
		                        DocumentBuilder db = dbf.newDocumentBuilder();

		                        Document doc = db.parse(new File(newDirPath));

		                        // optional, but recommended
		                        doc.getDocumentElement().normalize();
		                        
		                        String vendor = "";
		                        String serial = "";
		                        String timestamp = "";

		                        // get datalogger serial number
	                        	NodeList nodeDatalogger = doc.getElementsByTagName("uuid");
	                        	for (int temp = 0; temp < nodeDatalogger.getLength(); temp++) {
	                        		Node nodeUUI = nodeDatalogger.item(temp);
	                        		if (nodeUUI.getNodeType() == Node.ELEMENT_NODE) {
	                        			Element elementDatalogger = (Element) nodeUUI;
	                        			vendor = elementDatalogger.getElementsByTagName("vendor").item(0).getTextContent();
	                        			serial = elementDatalogger.getElementsByTagName("serial").item(0).getTextContent();
	                        		}
	                        	}
	                        	
	                        	// Get data point timestamp
	                        	NodeList listDatapoint = doc.getElementsByTagName("datapoint");
	                        	for (int temp = 0; temp < listDatapoint.getLength(); temp++) {
	                        		Node node = listDatapoint.item(temp);
	                        		if (node.getNodeType() == Node.ELEMENT_NODE) {
	                        			 Element element = (Element) node;
	                        			 timestamp = element.getAttribute("timestamp");
	                        		}
	                        	}
	                        	
	                        	// Get data parameter device 
	                        	BatchJobService service = new BatchJobService();
	                        	DeviceEntity entity = new DeviceEntity();
	                        	DeviceEntity deviceUpdateE = new DeviceEntity();
	                        	
	                        	Element rootElement = doc.getDocumentElement();
	                        	NodeList resourceList = rootElement.getElementsByTagName("device");
	                        	
	                        	for (int i = 0; i < resourceList.getLength(); i++) {
	                        		
	                        	    Element resource = (Element) resourceList.item(i);
	                        	    String id = resource.getAttribute("id");
	                        	    String type = resource.getAttribute("type");
	                        	    
	                        	    if(serial != "" && timestamp != "" && id != "" && type == "") {
     	                        	    
	                        	    	entity.setSerial_number(serial);
     	                        		entity.setModbusdevicenumber(id.replaceAll("[^0-9]", ""));
     	                        		DeviceEntity rowItem = service.checkExitsDeviceUploadSungrow(entity);
     	                        		ModelSungrowUmg604Service serviceUmg604 = new ModelSungrowUmg604Service();
     	                        		ModelSungrowSg110cxService serviceUmgSg110 = new ModelSungrowSg110cxService();
     	                        		ModelSungrowSg50cxService serviceUmgSg50 = new ModelSungrowSg50cxService();
     	                        		ModelSungrowWeatherPvmet75200Service serviceSW = new ModelSungrowWeatherPvmet75200Service();
     	                        		DeviceService serviceD = new DeviceService();
     	                        		timestamp = timestamp.replace("Z", "");
     	                        		
     	                        		ZoneId utc = ZoneId.of("Etc/UTC");
	     	                   	        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
	
	     	                   	        //ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
	     	                   	        ZoneId zId = ZoneId.of(rowItem.getTimezone_value());
	     	                   	    
	     	                   	        ZonedDateTime utcDateTime = LocalDateTime.parse(timestamp).atZone(zId).withZoneSameInstant(utc);
	     	                   	        String formatterUtcDateTime = utcDateTime.format(targetFormatter);
	     	                   	    
     	                        		if(rowItem != null && rowItem.getId() > 0) {
     	                        			NodeList aceList = resource.getElementsByTagName("mv");
											switch (rowItem.getDatatablename()) {
											
											case "model_sungrow_weather_pvmet75200":
												ModelSungrowWeatherPvmet75200Entity entitySW = new ModelSungrowWeatherPvmet75200Entity();
												entitySW.setId_device(rowItem.getId());
												entitySW.setError(0);
												entitySW.setHigh_alarm(0);
												entitySW.setLow_alarm(0);
												entitySW.setTime(formatterUtcDateTime);
												
												for (int j = 0; j < aceList.getLength(); j++) {
													Element mv = (Element) aceList.item(j);
													if((mv.getAttribute("t")).equals("E_DAY")) { entitySW.setSRAD_D_H(Double.parseDouble(mv.getAttribute("v"))); }
												}
												
												serviceSW.insertModelSungrowWeatherPvmet75200(entitySW);
												
												if(entitySW.getSRAD_D_H() >= 0) {
													deviceUpdateE.setLast_updated(formatterUtcDateTime);
													deviceUpdateE.setLast_value(entitySW.getSRAD_D_H() >= 0 ? entitySW.getSRAD_D_H() : null);
												} else {
													deviceUpdateE.setLast_updated(null);
													deviceUpdateE.setLast_value(null);
												}
												
												deviceUpdateE.setId(rowItem.getId());
												serviceD.updateLastUpdated(deviceUpdateE);
												
												break;
												
												
												
												case "model_sungrow_sg110cx":
													ModelSungrowSg110cxEntity entitySg110 = new ModelSungrowSg110cxEntity();
													entitySg110.setId_device(rowItem.getId());
													entitySg110.setError(0);
													entitySg110.setHigh_alarm(0);
													entitySg110.setLow_alarm(0);
													entitySg110.setTime(formatterUtcDateTime);
													
													for (int j = 0; j < aceList.getLength(); j++) {
														Element mv = (Element) aceList.item(j);
														if((mv.getAttribute("t")).equals("E_DAY")) { entitySg110.setE_DAY(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("E_TOTAL")) { 
															entitySg110.setE_TOTAL(Double.parseDouble(mv.getAttribute("v"))); 
															entitySg110.setNvmActiveEnergy(Double.parseDouble(mv.getAttribute("v")));	
														}
														if((mv.getAttribute("t")).equals("E_YEAR")) { entitySg110.setE_YEAR(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("OT_AC_TOTAL")) { entitySg110.setOT_AC_TOTAL(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("T_WR")) { entitySg110.setT_WR(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC1")) { entitySg110.setU_DC1(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC1")) { entitySg110.setI_DC1(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC2")) { entitySg110.setU_DC2(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC2")) { entitySg110.setI_DC2(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC3")) { entitySg110.setU_DC3(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC3")) { entitySg110.setI_DC3(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC4")) { entitySg110.setU_DC4(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC4")) { entitySg110.setI_DC4(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC5")) { entitySg110.setU_DC5(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC5")) { entitySg110.setI_DC5(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC6")) { entitySg110.setU_DC6(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC6")) { entitySg110.setI_DC6(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC7")) { entitySg110.setU_DC7(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC7")) { entitySg110.setI_DC7(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC8")) { entitySg110.setU_DC8(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC8")) { entitySg110.setI_DC8(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC9")) { entitySg110.setU_DC9(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC9")) { entitySg110.setI_DC9(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR1")) { entitySg110.setU_STR1(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR1")) { entitySg110.setI_STR1(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR2")) { entitySg110.setU_STR2(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR2")) { entitySg110.setI_STR2(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR3")) { entitySg110.setU_STR3(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR3")) { entitySg110.setI_STR3(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR4")) { entitySg110.setU_STR4(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR4")) { entitySg110.setI_STR4(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR5")) { entitySg110.setU_STR5(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR5")) { entitySg110.setI_STR5(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR6")) { entitySg110.setU_STR6(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR6")) { entitySg110.setI_STR6(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR7")) { entitySg110.setU_STR7(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR7")) { entitySg110.setI_STR7(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR8")) { entitySg110.setU_STR8(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR8")) { entitySg110.setI_STR8(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR9")) { entitySg110.setU_STR9(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR9")) { entitySg110.setI_STR9(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR10")) { entitySg110.setU_STR10(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR10")) { entitySg110.setI_STR10(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR11")) { entitySg110.setU_STR11(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR12")) { entitySg110.setU_STR12(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR12")) { entitySg110.setI_STR12(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR13")) { entitySg110.setU_STR13(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR13")) { entitySg110.setI_STR13(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR14")) { entitySg110.setU_STR14(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR14")) { entitySg110.setI_STR14(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR15")) { entitySg110.setU_STR15(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR15")) { entitySg110.setI_STR15(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR16")) { entitySg110.setU_STR16(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR16")) { entitySg110.setI_STR16(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR17")) { entitySg110.setU_STR17(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR17")) { entitySg110.setI_STR17(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR18")) { entitySg110.setU_STR18(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR18")) { entitySg110.setI_STR18(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("P_DC")) { 
															entitySg110.setP_DC(Double.parseDouble(mv.getAttribute("v"))); 
															entitySg110.setNvmActivePower(Double.parseDouble(mv.getAttribute("v")));
														}
														if((mv.getAttribute("t")).equals("U_AC1")) { entitySg110.setU_AC1(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_AC2")) { entitySg110.setU_AC2(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_AC3")) { entitySg110.setU_AC3(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_AC1")) { entitySg110.setI_AC1(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_AC2")) { entitySg110.setI_AC2(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_AC3")) { entitySg110.setI_AC3(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("S_AC")) { entitySg110.setS_AC(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("P_AC")) { entitySg110.setP_AC(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("Q_AC")) { entitySg110.setQ_AC(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("COS_PHI")) { entitySg110.setCOS_PHI(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("F_AC")) { entitySg110.setF_AC(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("FT_AC_DAY")) { entitySg110.setFT_AC_DAY(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("R_ISO")) { entitySg110.setR_ISO(Double.parseDouble(mv.getAttribute("v"))); }
													}
													
													serviceUmgSg110.insertModelSungrowSg110cx(entitySg110);
													
													if(entitySg110.getP_DC() >= 0) {
														deviceUpdateE.setLast_updated(formatterUtcDateTime);
														deviceUpdateE.setLast_value(entitySg110.getP_DC() >= 0 ? entitySg110.getP_DC() : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
													}
													
													deviceUpdateE.setId(rowItem.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													break;
												case "model_sungrow_umg604":
													ModelSungrowUmg604Entity entityUmg604 = new ModelSungrowUmg604Entity();
													entityUmg604.setId_device(rowItem.getId());
													entityUmg604.setError(0);
													entityUmg604.setHigh_alarm(0);
													entityUmg604.setLow_alarm(0);
													entityUmg604.setTime(formatterUtcDateTime);
													for (int j = 0; j < aceList.getLength(); j++) {
														Element mv = (Element) aceList.item(j);
														if((mv.getAttribute("t")).equals("M_AC_U1")) { entityUmg604.setM_AC_U1(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_U2")) { entityUmg604.setM_AC_U2(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_U3")) { entityUmg604.setM_AC_U3(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_U_L1L2")) { entityUmg604.setM_AC_U_L1L2(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_U_L2L3")) { entityUmg604.setM_AC_U_L2L3(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_U_L3L1")) { entityUmg604.setM_AC_U_L3L1(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_I1")) { entityUmg604.setM_AC_I1(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_I2")) { entityUmg604.setM_AC_I2(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_I3")) { entityUmg604.setM_AC_I3(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_P1")) { entityUmg604.setM_AC_P1(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_P2")) { entityUmg604.setM_AC_P2(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_P3")) { entityUmg604.setM_AC_P3(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_P")) { 
	    													entityUmg604.setM_AC_P(Double.parseDouble(mv.getAttribute("v"))); 
	    													entityUmg604.setNvmActivePower(Double.parseDouble(mv.getAttribute("v")));
	    												}
	    												if((mv.getAttribute("t")).equals("M_AC_E_M1")) { entityUmg604.setM_AC_E_M1(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_Q1")) { entityUmg604.setM_AC_Q1(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_Q2")) { entityUmg604.setM_AC_Q2(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_Q3")) { entityUmg604.setM_AC_Q3(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_Q")) { entityUmg604.setM_AC_Q(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_S1")) { entityUmg604.setM_AC_S1(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_S2")) { entityUmg604.setM_AC_S2(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_S3")) { entityUmg604.setM_AC_S3(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_S")) { entityUmg604.setM_AC_S(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_PF")) { entityUmg604.setM_AC_PF(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_F")) { entityUmg604.setM_AC_F(Double.parseDouble(mv.getAttribute("v"))); }
	    												if((mv.getAttribute("t")).equals("M_AC_E_EXP")) { 
	    													entityUmg604.setM_AC_E_EXP(Double.parseDouble(mv.getAttribute("v")));
	    													entityUmg604.setNvmActiveEnergy(Double.parseDouble(mv.getAttribute("v")));
														}
	    												if(mv.getAttribute("t") == "M_AC_E_IMP") { entityUmg604.setM_AC_E_IMP(Double.parseDouble(mv.getAttribute("v"))); }
													}
													serviceUmg604.insertModelSungrowUmg604(entityUmg604);
													
													if(entityUmg604.getM_AC_P() >= 0) {
														deviceUpdateE.setLast_updated(formatterUtcDateTime);
														deviceUpdateE.setLast_value(entityUmg604.getM_AC_P() >= 0 ? entityUmg604.getM_AC_P() : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
													}
													
													deviceUpdateE.setId(rowItem.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													break;
												case "model_sungrow_sg50cx":
													ModelSungrowSg50cxEntity entitySg50 = new ModelSungrowSg50cxEntity();
													entitySg50.setId_device(rowItem.getId());
													entitySg50.setError(0);
													entitySg50.setHigh_alarm(0);
													entitySg50.setLow_alarm(0);
													entitySg50.setTime(formatterUtcDateTime);
													for (int j = 0; j < aceList.getLength(); j++) {
														Element mv = (Element) aceList.item(j);
														if((mv.getAttribute("t")).equals("E_DAY")) { entitySg50.setE_DAY(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("E_TOTAL")) { 
															entitySg50.setE_TOTAL(Double.parseDouble(mv.getAttribute("v"))); 
															entitySg50.setNvmActiveEnergy(Double.parseDouble(mv.getAttribute("v")));
														}
														if((mv.getAttribute("t")).equals("E_YEAR")) { entitySg50.setE_YEAR(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("OT_AC_TOTAL")) { entitySg50.setOT_AC_TOTAL(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("T_WR")) { entitySg50.setT_WR(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC1")) { entitySg50.setU_DC1(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC1")) { entitySg50.setI_DC1(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC2")) { entitySg50.setU_DC2(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC2")) { entitySg50.setI_DC2(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC3")) { entitySg50.setU_DC3(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC3")) { entitySg50.setI_DC3(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC4")) { entitySg50.setU_DC4(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC4")) { entitySg50.setI_DC4(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC5")) { entitySg50.setU_DC5(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC5")) { entitySg50.setI_DC5(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC6")) { entitySg50.setU_DC6(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC6")) { entitySg50.setI_DC6(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC7")) { entitySg50.setU_DC7(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC7")) { entitySg50.setI_DC7(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC8")) { entitySg50.setU_DC8(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC8")) { entitySg50.setI_DC8(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC9")) { entitySg50.setU_DC9(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC9")) { entitySg50.setI_DC9(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC10")) { entitySg50.setU_DC10(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC10")) { entitySg50.setI_DC10(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC11")) { entitySg50.setU_DC11(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC11")) { entitySg50.setI_DC11(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC12")) { entitySg50.setU_DC12(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC12")) { entitySg50.setI_DC12(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC13")) { entitySg50.setU_DC13(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC13")) { entitySg50.setI_DC13(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC14")) { entitySg50.setU_DC14(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC14")) { entitySg50.setI_DC14(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC15")) { entitySg50.setU_DC15(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC15")) { entitySg50.setI_DC15(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC16")) { entitySg50.setU_DC16(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC16")) { entitySg50.setI_DC16(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC17")) { entitySg50.setU_DC17(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC17")) { entitySg50.setI_DC17(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC18")) { entitySg50.setU_DC18(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC18")) { entitySg50.setI_DC18(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC19")) { entitySg50.setU_DC19(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC19")) { entitySg50.setI_DC19(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_DC20")) { entitySg50.setU_DC20(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_DC20")) { entitySg50.setI_DC20(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR1")) { entitySg50.setU_STR1(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR1")) { entitySg50.setI_STR1(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR2")) { entitySg50.setU_STR2(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR2")) { entitySg50.setI_STR2(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR3")) { entitySg50.setU_STR3(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR3")) { entitySg50.setI_STR3(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR4")) { entitySg50.setU_STR4(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR4")) { entitySg50.setI_STR4(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR5")) { entitySg50.setU_STR5(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR5")) { entitySg50.setI_STR5(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR6")) { entitySg50.setU_STR6(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR6")) { entitySg50.setI_STR6(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR7")) { entitySg50.setU_STR7(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR7")) { entitySg50.setI_STR7(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR8")) { entitySg50.setU_STR8(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR8")) { entitySg50.setI_STR8(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR9")) { entitySg50.setU_STR9(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR9")) { entitySg50.setI_STR9(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_STR10")) { entitySg50.setU_STR10(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR10")) { entitySg50.setI_STR10(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR11")) { entitySg50.setI_STR11(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR12")) { entitySg50.setI_STR12(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR13")) { entitySg50.setI_STR13(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR14")) { entitySg50.setI_STR14(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR15")) { entitySg50.setI_STR15(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR16")) { entitySg50.setI_STR16(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR17")) { entitySg50.setI_STR17(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR18")) { entitySg50.setI_STR18(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR19")) { entitySg50.setI_STR19(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR20")) { entitySg50.setI_STR20(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR21")) { entitySg50.setI_STR21(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_STR22")) { entitySg50.setI_STR22(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("P_DC")) { 
															entitySg50.setP_DC(Double.parseDouble(mv.getAttribute("v"))); 
															entitySg50.setNvmActivePower(Double.parseDouble(mv.getAttribute("v")));
														}
														if((mv.getAttribute("t")).equals("U_AC1")) { entitySg50.setU_AC1(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_AC2")) { entitySg50.setU_AC2(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("U_AC3")) { entitySg50.setU_AC3(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_AC1")) { entitySg50.setI_AC1(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_AC2")) { entitySg50.setI_AC2(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("I_AC3")) { entitySg50.setI_AC3(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("S_AC")) { entitySg50.setS_AC(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("P_AC")) { entitySg50.setP_AC(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("Q_AC")) { entitySg50.setQ_AC(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("COS_PHI")) { entitySg50.setCOS_PHI(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("F_AC")) { entitySg50.setF_AC(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("FT_AC_DAY")) { entitySg50.setFT_AC_DAY(Double.parseDouble(mv.getAttribute("v"))); }
														if((mv.getAttribute("t")).equals("R_ISO")) { entitySg50.setR_ISO(Double.parseDouble(mv.getAttribute("v"))); }
													}
													serviceUmgSg50.insertModelSungrowSg50cx(entitySg50);
													if(entitySg50.getP_DC() >= 0) {
														deviceUpdateE.setLast_updated(formatterUtcDateTime);
														deviceUpdateE.setLast_value(entitySg50.getP_DC() >= 0 ? entitySg50.getP_DC() : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
													}
													
													deviceUpdateE.setId(rowItem.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													break;
											}
     	                        		}
     	                        		
	                        	    }
	                        	  
	                        	}

		                    } catch (ParserConfigurationException | SAXException | IOException e) {
		                        e.printStackTrace();
		                    }
		                    // Delete file upload
		                    File logFile = new File(newDirPath);
							if(logFile.delete()){  
								System.out.println("Deleted file: " + newDirPath);  
							}
							
							// Delete file for FTP
							boolean deleted = ftpClient.deleteFile(filePath);
							if (deleted) {
				                System.out.println("The file was deleted successfully.");
				            } else {
				                System.out.println("Could not delete the  file, it may not exist.");
				            }
		                    
		                } else {
		                    System.out.println("COULD NOT download the file: " + filePath);
		                }
	            	}
	                
	                
	            }
	        }
	    }
	}
	
	
}
