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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.TimeZone;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelSmaClusterControllerEntity;
import com.nwm.api.entities.ModelSmaInverterStp1200tlus10Entity;
import com.nwm.api.entities.ModelSmaInverterStp24ktlus10Entity;
import com.nwm.api.services.BatchJobService;
import com.nwm.api.services.DeviceService;
import com.nwm.api.services.ModelSmaClusterControllerService;
import com.nwm.api.services.ModelSmaInverterStp1200tlus10Service;
import com.nwm.api.services.ModelSmaInverterStp24ktlus10Service;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.FLLogger;
import com.nwm.api.utils.Lib;
import java.io.File;

public class BatchJobSMAFTP {
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
	
	
	public void readFolderSMAFTP() {
		try {
	        String server = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.FTPSMAServer);
	        String user = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.FTPSMAUser);
	        String pass = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.FTPSMAPass);
	        int port = 21;
	        String remoteDirPath = "/SMAFTP/CSV"; 
	        
			String saveDirPath = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey) + "/" + Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeySMAFTP);
	        
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
	 * 
	 * @param ftpClient  an instance of org.apache.commons.net.ftp.FTPClient class.
	 * @param parentDir  Path of the parent directory of the current directory being
	 *                   downloaded.
	 * @param currentDir Path of the current directory being downloaded.
	 * @param saveDir    path of directory where the whole remote directory will be
	 *                   downloaded and saved.
	 * @throws IOException if any network or IO error occurred.
	 */
	public static void downloadDirectory(FTPClient ftpClient, String parentDir, String currentDir, String saveDir)
			throws IOException {
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
					downloadDirectory(ftpClient, dirToList, currentFileName, saveDir);
				} else {
					// download the file
					Calendar cal = Calendar.getInstance(TimeZone.getDefault());
					java.util.Date d = new Date();
					cal.setTime(d);
					int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH) + 1;
					int day = cal.get(Calendar.DATE);
					String mString = (month > 9) ? ""+ month : "0"+ month;
					String dString = (day > 9) ? ""+ day : "0"+ day;
					String dFileName = year + mString + dString + ".csv";
					
					cal.add(Calendar.DATE, -1);
					int yearSub = cal.get(Calendar.YEAR);
					int monthSub = cal.get(Calendar.MONTH) + 1;
					int daySub = cal.get(Calendar.DATE);
					String mSubString = (monthSub > 9) ? ""+ monthSub : "0"+ monthSub;
					String dSubString = (daySub > 9) ? ""+ daySub : "0"+ daySub;
					String dSubFileName = yearSub + mSubString + dSubString + ".csv";
			        
					File f = new File(newDirPath);
					if (!f.exists() && (dFileName.equals(currentFileName) || dSubFileName.equals(currentFileName))) {
						boolean success = downloadSingleFile(ftpClient, filePath, newDirPath);
						if (success) {
							// Read file CSV
							List<String> valuesIdDevice = new ArrayList<String>();
							List<String> fields = new ArrayList<String>();
							try (Scanner scanner = new Scanner(new File(newDirPath));) {
								int i = 0;
								while (scanner.hasNextLine()) {

									List<String> values = new ArrayList<String>();
									try (Scanner rowScanner = new Scanner(scanner.nextLine())) {
										rowScanner.useDelimiter(";");
										while (rowScanner.hasNext()) {
											values.add(rowScanner.next());
										}
									}

									if (i == 4) {
										try (Scanner rowScanner = new Scanner(scanner.nextLine())) {
											rowScanner.useDelimiter(";");
											while (rowScanner.hasNext()) {
												valuesIdDevice.add(rowScanner.next());
											}
										}
									}
									
									// Add data field to array
									if (i == 4) {
										try (Scanner rowScanner = new Scanner(scanner.nextLine())) {
											rowScanner.useDelimiter(";");
											while (rowScanner.hasNext()) {
												fields.add(rowScanner.next());
											}
										}
									}
									

									if (valuesIdDevice.size() > 0 && values.size() > 0 && i > 6) {
										String modbusdevicenumber = "";
										String tablename = "";
										String timestamp = "";
										String timezone = "";
										DeviceEntity entity = new DeviceEntity();
										BatchJobService service = new BatchJobService();
										ModelSmaClusterControllerEntity entityCluster = new ModelSmaClusterControllerEntity();
										entityCluster.setError(0);
										entityCluster.setHigh_alarm(0);
										entityCluster.setLow_alarm(0);
										
										ModelSmaInverterStp1200tlus10Entity entityStp12 = new ModelSmaInverterStp1200tlus10Entity();
										entityStp12.setError(0);
										entityStp12.setHigh_alarm(0);
										entityStp12.setLow_alarm(0);
										
										ModelSmaInverterStp24ktlus10Entity entityStp24 = new ModelSmaInverterStp24ktlus10Entity();
										entityStp24.setError(0);
										entityStp24.setHigh_alarm(0);
										entityStp24.setLow_alarm(0);
										
										int idDevice = 0;
										
										DeviceEntity deviceUpdateE = new DeviceEntity();
										DeviceService serviceD = new DeviceService();
										
										DeviceEntity rowItem = new DeviceEntity();
										for (int j = 0; j < valuesIdDevice.size(); j++) {
											if (!modbusdevicenumber.equals(valuesIdDevice.get(j))) {
												entity.setModbusdevicenumber(valuesIdDevice.get(j));
												rowItem = service.checkExitsDeviceSMA(entity);
												if (rowItem != null && rowItem.getId() > 0) {
													modbusdevicenumber = rowItem.getModbusdevicenumber();
													tablename = rowItem.getDatatablename();
													timezone = rowItem.getTimezone_value();
													idDevice = rowItem.getId();
												}
											}
											
											timestamp = values.get(0);
											Date date = new Date(timestamp);
											SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
											String stringDate = DateFor.format(date);
											timestamp = stringDate.replace( " " , "T" );
	     	                        		ZoneId utc = ZoneId.of("Etc/UTC");
		     	                   	        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		     	                   	        //ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
		     	                   	        ZoneId zId = ZoneId.of(timezone);
		     	                   	        ZonedDateTime utcDateTime = LocalDateTime.parse(timestamp).atZone(zId).withZoneSameInstant(utc);
		     	                   	        String formatterUtcDateTime = utcDateTime.format(targetFormatter);
		     	                   	        String fieldName = fields.get(j);
											switch (tablename) {
												case "model_sma_cluster_controller":
													entityCluster.setId_device(idDevice);
				     	                   	        entityCluster.setTime(formatterUtcDateTime);
													if(fieldName.equals("Metering.TotWhOut")) { 
														entityCluster.setMetering_TotWhOut(Double.parseDouble(values.get(j + 1))); 
														entityCluster.setNvmActiveEnergy(Double.parseDouble(values.get(j + 1)));
													}
													else if(fieldName.equals("Operation.GriSwCnt")) { entityCluster.setOperation_GriSwCnt(Double.parseDouble(values.get(j + 1))); }
													else if(fieldName.equals("Metering.TotFeedTms")) { entityCluster.setMetering_TotOpTms(Double.parseDouble(values.get(j + 1))); }
													else if(fieldName.equals("GridMs.TotW")) { 
														DecimalFormat df = new DecimalFormat("#.0");
														double power = Double.parseDouble((!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0") ) / 1000;
														entityCluster.setGridMs_TotW(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? df.format(power) : "0.001"));
														entityCluster.setNvmActivePower(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? df.format(power) : "0.001"));
													}
													else if(fieldName.equals("GridMs.Hz")) { entityCluster.setGridMs_Hz(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("Isolation.FltA")) { entityCluster.setIsolation_FltA(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("Isolation.LeakRis")) { entityCluster.setIsolation_LeakRis(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("DcMs.Vol[A]")) { entityCluster.setDcMs_VolA(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("DcMs.Vol[B]")) { entityCluster.setDcMs_VolB(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("DcMs.Amp[A]")) { entityCluster.setDcMs_AmpA(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("DcMs.Amp[B]")) { entityCluster.setDcMs_AmpB(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("GridMs.PhV.phsA")) { entityCluster.setGridMs_PhV_phsA(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("GridMs.PhV.phsB")) { entityCluster.setGridMs_PhV_phsB(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("GridMs.PhV.phsC")) { entityCluster.setGridMs_PhV_phsC(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("GridMs.A.phsA")) { entityCluster.setGridMs_A_phsA(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("GridMs.A.phsB")) { entityCluster.setGridMs_A_phsB(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("GridMs.A.phsC")) { entityCluster.setGridMs_A_phsC(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("DcMs.Watt[A]")) { entityCluster.setDcMs_WattA(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("DcMs.Watt[B]")) { entityCluster.setDcMs_WattB(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("Operation.Health")) { entityCluster.setOperation_Health(values.get(j + 1)); }
													else if(fieldName.equals("Operation.Evt.Prio")) { entityCluster.setOperation_Evt_Prio(values.get(j + 1)); }
													else if(fieldName.equals( "Operation.Evt.Msg")) { entityCluster.setOperation_Evt_Msg(values.get(j + 1)); }
													else if(fieldName.equals("Operation.Evt.Dsc")) { entityCluster.setOperation_Evt_Dsc(values.get(j + 1)); }
													else if(fieldName.equals("InOut.AnInA1")) { entityCluster.setInOut_AnInA1(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("InOut.AnInA2")) { entityCluster.setInOut_AnInA2(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("InOut.AnInA3")) { entityCluster.setInOut_AnInA3(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("InOut.AnInVol4")) { entityCluster.setInOut_AnInVol4(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("Env.ExInsol")) { 
														entityCluster.setEnv_ExInsol(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001"));
														ModelSmaClusterControllerService serviceUmg604 = new ModelSmaClusterControllerService();
														serviceUmg604.insertModelSmaClusterController(entityCluster);
														
														// Update last value
														if(entityCluster.getNvmActivePower() >= 0) {
															deviceUpdateE.setLast_updated(formatterUtcDateTime);
															deviceUpdateE.setLast_value(entityCluster.getNvmActivePower() >= 0 ? entityCluster.getNvmActivePower() : null);
														} else {
															deviceUpdateE.setLast_updated(null);
															deviceUpdateE.setLast_value(null);
														}
														deviceUpdateE.setId(rowItem.getId());
														serviceD.updateLastUpdated(deviceUpdateE);
														
													}
													break;
													
												case "model_sma_inverter_stp1200tlus10":
													entityStp12.setId_device(idDevice);
													entityStp12.setTime(formatterUtcDateTime);
													if(fieldName.equals("Metering.TotWhOut")) { 
														entityStp12.setMetering_TotWhOut(Double.parseDouble(values.get(j + 1))); 
														entityStp12.setNvmActiveEnergy(Double.parseDouble(values.get(j + 1)));
													}
													else if(fieldName.equals("Operation.GriSwCnt")) { entityStp12.setOperation_GriSwCnt(Double.parseDouble(values.get(j + 1))); }
													else if(fieldName.equals("Metering.TotFeedTms")) { entityStp12.setMetering_TotOpTms(Double.parseDouble(values.get(j + 1))); }
													else if(fieldName.equals("Metering.GridMs.TotWhOut")) { entityStp12.setMetering_GridMs_TotWhOut(Double.parseDouble(values.get(j + 1))); }
													else if(fieldName.equals("GridMs.TotW")) { 
														DecimalFormat df = new DecimalFormat("#.0");
														double power = Double.parseDouble((!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0") ) / 1000;
														entityStp12.setGridMs_TotW(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? df.format(power) : "0.001"));
														entityStp12.setNvmActivePower(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? df.format(power) : "0.001"));
													}
													else if(fieldName.equals("GridMs.Hz")) { entityStp12.setGridMs_Hz(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("Isolation.FltA")) { entityStp12.setIsolation_FltA(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("Isolation.LeakRis")) { entityStp12.setIsolation_LeakRis(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("DcMs.Vol[A]")) { entityStp12.setDcMs_VolA(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("DcMs.Vol[B]")) { entityStp12.setDcMs_VolB(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("DcMs.Amp[A]")) { entityStp12.setDcMs_AmpA(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("DcMs.Amp[B]")) { entityStp12.setDcMs_AmpB(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("GridMs.PhV.phsA")) { entityStp12.setGridMs_PhV_phsA(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("GridMs.PhV.phsB")) { entityStp12.setGridMs_PhV_phsB(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("GridMs.PhV.phsC")) { entityStp12.setGridMs_PhV_phsC(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("GridMs.A.phsA")) { entityStp12.setGridMs_A_phsA(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("GridMs.A.phsB")) { entityStp12.setGridMs_A_phsB(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("GridMs.A.phsC")) { entityStp12.setGridMs_A_phsC(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("DcMs.Watt[A]")) { entityStp12.setDcMs_WattA(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("DcMs.Watt[B]")) { entityStp12.setDcMs_WattB(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("Operation.Health")) { entityStp12.setOperation_Health(values.get(j + 1)); }
													else if(fieldName.equals("Operation.Evt.Prio")) { entityStp12.setOperation_Evt_Prio(values.get(j + 1)); }
													else if(fieldName.equals( "Operation.Evt.Msg")) { entityStp12.setOperation_Evt_Msg(values.get(j + 1)); }
													else if(fieldName.equals("Operation.Evt.Dsc")) { 
														entityStp12.setOperation_Evt_Dsc(values.get(j + 1)); 
														ModelSmaInverterStp1200tlus10Service servicestp12 = new ModelSmaInverterStp1200tlus10Service();
														servicestp12.insertModelSmaInverterStp1200tlus10(entityStp12);
														
														// Update last value
														if(entityStp12.getNvmActivePower() >= 0) {
															deviceUpdateE.setLast_updated(formatterUtcDateTime);
															deviceUpdateE.setLast_value(entityStp12.getNvmActivePower() >= 0 ? entityStp12.getNvmActivePower() : null);
														} else {
															deviceUpdateE.setLast_updated(null);
															deviceUpdateE.setLast_value(null);
														}
														deviceUpdateE.setId(rowItem.getId());
														serviceD.updateLastUpdated(deviceUpdateE);
													}
													break;
													
												case "model_sma_inverter_stp24ktlus10":
													boolean statusSetEntity = false;
													entityStp24.setId_device(rowItem.getId());
													entityStp24.setTime(formatterUtcDateTime);
													if(fieldName.equals("Metering.TotWhOut")) { 
														entityStp24.setMetering_TotWhOut(Double.parseDouble(values.get(j + 1))); 
														entityStp24.setNvmActiveEnergy(Double.parseDouble(values.get(j + 1)));
													}
													else if(fieldName.equals("Operation.GriSwCnt")) { entityStp24.setOperation_GriSwCnt(Double.parseDouble(values.get(j + 1))); }
													else if(fieldName.equals("Metering.TotOpTms")) { entityStp24.setMetering_TotOpTms(Double.parseDouble(values.get(j + 1))); }
													else if(fieldName.equals("Metering.TotFeedTms")) { entityStp24.setMetering_TotFeedTms(Double.parseDouble(values.get(j + 1))); }
													else if(fieldName.equals("Metering.GridMs.TotWhOut")) { entityStp24.setMetering_GridMs_TotWhOut(Double.parseDouble(values.get(j + 1))); }
													else if(fieldName.equals("GridMs.TotW")) { 
														DecimalFormat df = new DecimalFormat("#.0");
														double power = Double.parseDouble((!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0") ) / 1000;
														entityStp24.setGridMs_TotW(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? df.format(power) : "0.001"));
														entityStp24.setNvmActivePower(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? df.format(power) : "0.001"));
													}
													else if(fieldName.equals("GridMs.Hz")) { entityStp24.setGridMs_Hz(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("Isolation.FltA")) { entityStp24.setIsolation_FltA(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("Isolation.LeakRis")) { entityStp24.setIsolation_LeakRis(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("DcMs.Vol[A]")) { entityStp24.setDcMs_VolA(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("DcMs.Vol[B]")) { entityStp24.setDcMs_VolB(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("DcMs.Amp[A]")) { entityStp24.setDcMs_AmpA(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("DcMs.Amp[B]")) { entityStp24.setDcMs_AmpB(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("DcMs.Amp[A1]")) { entityStp24.setDcMs_AmpA1(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("GridMs.PhV.phsA")) { entityStp24.setGridMs_PhV_phsA(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("GridMs.PhV.phsB")) { entityStp24.setGridMs_PhV_phsB(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("GridMs.PhV.phsC")) { entityStp24.setGridMs_PhV_phsC(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("GridMs.A.phsA")) { entityStp24.setGridMs_A_phsA(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("GridMs.A.phsB")) { entityStp24.setGridMs_A_phsB(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("GridMs.A.phsC")) { entityStp24.setGridMs_A_phsC(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("DcMs.Watt[A]")) { entityStp24.setDcMs_WattA(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("DcMs.Watt[B]")) { entityStp24.setDcMs_WattB(Double.parseDouble(!Lib.isBlank(values.get(j + 1)) ? values.get(j + 1) : "0.001")); }
													else if(fieldName.equals("Operation.Health")) { entityStp24.setOperation_Health(values.get(j + 1)); }
													else if(fieldName.equals("Operation.Evt.Prio")) { entityStp24.setOperation_Evt_Prio(values.get(j + 1)); }
													else if(fieldName.equals( "Operation.Evt.Msg")) { entityStp24.setOperation_Evt_Msg(values.get(j + 1)); }
													else if(fieldName.equals("Operation.Evt.Dsc")) { 
														entityStp24.setOperation_Evt_Dsc(values.get(j + 1)); 
														ModelSmaInverterStp24ktlus10Service servicestp24 = new ModelSmaInverterStp24ktlus10Service();
														servicestp24.insertModelSmaInverterStp24ktlus10(entityStp24);
														
														// Update last value
														if(entityStp24.getNvmActivePower() >= 0) {
															deviceUpdateE.setLast_updated(formatterUtcDateTime);
															deviceUpdateE.setLast_value(entityStp24.getNvmActivePower() >= 0 ? entityStp24.getNvmActivePower() : null);
														} else {
															deviceUpdateE.setLast_updated(null);
															deviceUpdateE.setLast_value(null);
														}
														deviceUpdateE.setId(rowItem.getId());
														serviceD.updateLastUpdated(deviceUpdateE);
													}
													break;
											}
										}
									}
									i++;
								}
							} catch(Exception e) {
								System.out.print(e); 
							}

							// Delete file upload
		                    File logFile = new File(newDirPath);
							if(logFile.delete()){  
								System.out.println("Deleted file: " + newDirPath);  
							}

							// Delete file for FTP
//							boolean deleted = ftpClient.deleteFile(filePath);
//							if (deleted) {
//				                System.out.println("The file was deleted successfully.");
//				            } else {
//				                System.out.println("Could not delete the  file, it may not exist.");
//				            }

						} else {
							System.out.println("COULD NOT download the file: " + filePath);
						}
					}

				}
			}
		}
	}
	
	private static List<String> getRecordFromLine(String line) {
	    List<String> values = new ArrayList<String>();
	    try (Scanner rowScanner = new Scanner(line)) {
	        rowScanner.useDelimiter(",");
	        while (rowScanner.hasNext()) {
	            values.add(rowScanner.next());
	        }
	    }
	    System.out.println("line: " + values);
	    return values;
	}
	
	
}
