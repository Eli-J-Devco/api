/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.entities.*;
import com.nwm.api.services.*;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.nio.file.Path;
import java.util.UUID;


@RestController
@ApiIgnore
@RequestMapping("/files")
public class UploadFilesController_v2 extends BaseController {
	public static String message = "";
	/**
	 * @description upload files datalogger and insert datalogger to database
	 * @author Duc.pham
	 * @since 2025-11-20
	 * @params RequestParam, files 
	 */

	@PostMapping("/upload_v2")
	@ResponseBody

	public String uploadFiles(HttpServletRequest request, @RequestParam(name = "LOGFILE", required = false) MultipartFile files[],
			@RequestParam(name = "SENDDATATRACE", required = false) String senddatatrace,
			@RequestParam(name = "MODE", required = false) String mode,
			@RequestParam(name = "SERIALNUMBER", required = true) String serialnumber,
			@RequestParam(name = "PASSWORD", required = false) String password,
			@RequestParam(name = "LOOPNAME", required = false) String loopname,
			@RequestParam(name = "MODBUSIP", required = false) String modbusip,
			@RequestParam(name = "MODBUSPORT", required = false) String modbusport,
			@RequestParam(name = "MODBUSDEVICE", required = false) String modbusdevice,
			@RequestParam(name = "MODBUSDEVICENAME", required = false) String modbusdevicename,
			@RequestParam(name = "MODBUSDEVICETYPE", required = false) String modbusdevicetype,
			@RequestParam(name = "MODBUSDEVICETYPENUMBER", required = false) String modbusdevicetypenumber,
			@RequestParam(name = "MODBUSDEVICECLASS", required = false) String modbusdeviceclass,
			@RequestParam(name = "MD5CHECKSUM", required = false) String md5checksum,
			@RequestParam(name = "FILESIZE", required = false) String filesize,
			@RequestParam(name = "FILETIME", required = false) String filetime,
			@RequestParam(name = "FILENAME", required = false) String filename) {

		// Basic validation to ensure data can be saved successfully
		if (serialnumber == null || serialnumber.trim().isEmpty()) {
			message = "\nFAILURE\n";
			return message;
		}
		
		if (mode == null || mode.trim().isEmpty()) {
			message = "\nFAILURE\n";
			return message;
		}
		
		if (files == null || files.length == 0) {
			message = "\nFAILURE\n";
			return message;
		}

		try {

			String LOGFILEUPLOAD = "LOGFILEUPLOAD";
			List<String> fileNames = new ArrayList<>();
			
			System.out.println("SENDDATATRACE: " + senddatatrace);
			System.out.println("MODE: " + mode);
			System.out.println("SERIALNUMBER: " + serialnumber);
			System.out.println("PASSWORD: " + password);
			System.out.println("LOOPNAME: " + loopname);
			System.out.println("MODBUSIP: " + modbusip);
			System.out.println("MODBUSPORT: " + modbusport);
			System.out.println("MODBUSDEVICE: " + modbusdevice);
			System.out.println("MODBUSDEVICENAME: " + modbusdevicename);
			System.out.println("MODBUSDEVICETYPE: " + modbusdevicetype);
			System.out.println("MODBUSDEVICETYPENUMBER: " + modbusdevicetypenumber);
			System.out.println("MODBUSDEVICECLASS: " + modbusdeviceclass);
			System.out.println("MD5CHECKSUM: " + md5checksum);
			System.out.println("FILESIZE: " + filesize);
			System.out.println("FILETIME: " + filetime);
			
			int fileCount = (files != null ? files.length : 0);
			
			String providedFilename = filename != null ? filename : (request != null ? request.getParameter("LOGFILE") : null);
			
			if (request != null) {
				try {
					request.getParameterMap().forEach((k, v) -> {
					});
				} catch (Exception ignore) {}
			}
			if (files != null && files.length > 0) {
				for (MultipartFile f : files) {
					if (f == null) continue;
				}
			}
			if (LOGFILEUPLOAD.equalsIgnoreCase(mode) && files != null && files.length > 0) {
			
				for (MultipartFile file : files) {
					String fileName = file.getOriginalFilename();
					String ext = "";
					if (fileName != null) {
						int lastDot = fileName.lastIndexOf('.');
						if (lastDot >= 0 && lastDot < fileName.length() - 1) {
							ext = fileName.substring(lastDot + 1);
						}
					}
					fileNames.add(file.getOriginalFilename());

					Path root = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey));
					String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
					String unique = UUID.randomUUID().toString();

					byte[] bytes;
					try {
						bytes = file.getBytes();
						
						// Debug: preview file contents (handles .gz and plain). Limit ~64KB
						try {
							String preview;
							if ("gz".equalsIgnoreCase(ext)) {
								try (InputStream fisP = file.getInputStream();
									 GZIPInputStream gisP = new GZIPInputStream(fisP)) {
									byte[] buf = new byte[8192];
									int read, total = 0;
									StringBuilder sbPrev = new StringBuilder();
									while ((read = gisP.read(buf)) != -1 && total < 64 * 1024) {
										sbPrev.append(new String(buf, 0, read, java.nio.charset.StandardCharsets.UTF_8));
										total += read;
									}
									preview = sbPrev.toString();
								}
							} else {
								preview = new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
							}
							// System.out.println("FILE PREVIEW (first 64KB):\n" + preview);
						} catch (Exception exPrev) {
							System.out.println("FILE PREVIEW ERROR: " + exPrev.getMessage());
						}
						fileName = "bm-" + modbusdevice  + "-" + unique + "." + timeStamp + ".log";
						// Process all data - no rate limiting, save everything received
						synchronized (System.out) {
						}
						
						switch (ext) {
							case "gz":
								Path path = root.resolve(fileName.concat(".gz"));
								Files.write(path, bytes);
								try (InputStream fis = file.getInputStream();
									 GZIPInputStream gis = new GZIPInputStream(fis);
									 FileOutputStream fos = new FileOutputStream(root.resolve(fileName).toString())) {
									byte[] buffer = new byte[1024 * 1024];
									int len = 0;
									while ((len = gis.read(buffer)) != -1) {
										fos.write(buffer, 0, len);
									}
								}
								break;
							case "log":
								Path pathLogUpload = root.resolve(fileName);
								Files.write(pathLogUpload, bytes);
								break;
							default:
								// Treat unknown or empty extension as plain log
								Path pathDefault = root.resolve(fileName);
								System.out.println("pathDefault:-------> " + pathDefault);
								Files.write(pathDefault, bytes);
								break;
						}

						boolean exists = new File(root.resolve(fileName).toString()).isFile(); 
						
						
						// Get list device by SERIALNUMBER
						if (!serialnumber.isEmpty() && exists) {
							File readFile = new File(root.resolve(fileName).toString());
							FileReader fr = new FileReader(readFile); // reads the file
							BufferedReader br = new BufferedReader(fr); // creates a buffering character input stream
							StringBuffer sb = new StringBuffer(); // constructs a string buffer with no characters
							String line;

							UploadFilesService uploadFilesService = new UploadFilesService();
							DeviceService serviceD = new DeviceService();
							DeviceEntity deviceE = new DeviceEntity();
							deviceE.setSerial_number(serialnumber);
							deviceE.setModbusdevicenumber(modbusdevice);
							
							// Update datalogger info 
							DeviceEntity dataloggerItem = serviceD.getDataloggerBySerialNumber(deviceE);
							if(dataloggerItem.getId() > 0) {
								// Set last update for datalogger 
								Date now = new Date();
								TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
								SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
								String CurrentDate = format.format(now);
								DeviceEntity deviceUpdateE = new DeviceEntity();
								deviceUpdateE.setLast_updated(CurrentDate);
								deviceUpdateE.setLast_value(null);
								deviceUpdateE.setId(dataloggerItem.getId());
								serviceD.updateLastUpdated(deviceUpdateE);
								
								// Save to datalogger
								ModelDataloggerEntity dataloggerEntity = new ModelDataloggerEntity();
								dataloggerEntity.setId_device(dataloggerItem.getId());
								dataloggerEntity.setDatatablename(dataloggerItem.getDatatablename());
								dataloggerEntity.setView_tablename(dataloggerItem.getView_tablename());
								dataloggerEntity.setJob_tablename(dataloggerItem.getJob_tablename());
								
						        String sDateUTC = format.format(now);
						        dataloggerEntity.setTime(sDateUTC);
						        dataloggerEntity.setSerialnumber(serialnumber);
						        dataloggerEntity.setLoopname(loopname);
						        dataloggerEntity.setModbusip(modbusip);
						        dataloggerEntity.setModbusport(modbusport);
						        dataloggerEntity.setModbusdevice(modbusdevice);
						        dataloggerEntity.setModbusdevicename(modbusdevicename);
						        dataloggerEntity.setModbusdevicetype(modbusdevicetype);
						        dataloggerEntity.setModbusdevicetypenumber(modbusdevicetypenumber);
						        dataloggerEntity.setModbusdeviceclass(modbusdeviceclass);
								ModelDataloggerService dataloggerService = new ModelDataloggerService();
								dataloggerService.insertModelDatalogger(dataloggerEntity);
							}
							
					List<DeviceEntity> dataDevice = serviceD.getDeviceListBySerialNumber(deviceE);
					if (dataDevice.size() > 0) {
						
						// NOTE: Do not pre-read file lines here; the model-processing blocks below
						// read from the same BufferedReader. Pre-reading would exhaust the stream
						// and result in no data being processed/inserted.
						
						// Query scaled parameters for all devices at once using single query with IN clause
						List<Integer> deviceIds = new ArrayList<>();
						for (DeviceEntity device : dataDevice) {
							deviceIds.add(device.getId());
						}
						Map<Integer, List<DeviceEntity>> scaledParametersCache = serviceD.getListScaledDeviceParameter(deviceIds);
						
						// Process each device with all file lines
						for (int i = 0; i < dataDevice.size(); i++) {
							DeviceEntity item = dataDevice.get(i);
							ZoneId zoneIdLosAngeles = ZoneId.of(item.getTimezone_value()); // "America/Los_Angeles"
					        ZonedDateTime zdtNowLosAngeles = ZonedDateTime.now(zoneIdLosAngeles);
					        int hours = zdtNowLosAngeles.getHour();								if( modbusdevice.equals(item.getModbusdevicenumber())) {
									// Get scaled parameters from cache
									List<DeviceEntity> scaledDeviceParameters = scaledParametersCache.get(item.getId());
									if (scaledDeviceParameters == null) {
										scaledDeviceParameters = new ArrayList<>();
									}
									DeviceEntity deviceUpdateE = new DeviceEntity();
									switch (item.getDevice_group_table()) {										
											case "model_huawei_sun2000_28ktl":
												ModelHuaweiSun200028ktlService_v2 serviceHuaweiSun200028ktl = new ModelHuaweiSun200028ktlService_v2();
												List<ModelHuaweiSun200028ktlEntity> dataList = new ArrayList<>();
												
												while ((line = br.readLine()) != null) {
													sb.append(line);
													sb.append("\n");
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelHuaweiSun200028ktlEntity dataEntity = serviceHuaweiSun200028ktl.setModelHuaweiSun200028ktl(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														dataList.add(dataEntity);
													}
												}
												
												// Batch insert and update device
												if (!dataList.isEmpty()) {
													serviceHuaweiSun200028ktl.insertModelHuaweiSun200028ktl_v2(dataList);
													
													// Update device with last row data
													ModelHuaweiSun200028ktlEntity dataEntity = dataList.get(dataList.size() - 1);
													if(dataEntity.getActivePower() != 0.001 && dataEntity.getActivePower() >= 0) {
														deviceUpdateE.setLast_updated(dataEntity.getTime());
													}
													deviceUpdateE.setLast_value(dataEntity.getActivePower() != 0.001 ? dataEntity.getActivePower() : null);
													deviceUpdateE.setField_value1(dataEntity.getActivePower() != 0.001 ? dataEntity.getActivePower() : null);
													deviceUpdateE.setField_value2(null);
													deviceUpdateE.setField_value3(null);
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
												}
												
												break;
												
															
											
										}
										
										// low production alert
										if (
											(item.getId_device_type() == 1 || ((item.getId_device_type() == 3 || item.getId_device_type() == 7 || item.getId_device_type() == 9) && !item.isIs_excluded_meter())) &&
											(hours >= item.getStart_date_time()) &&
											(hours <= item.getEnd_date_time())
										) {
											item.setLast_updated(deviceUpdateE.getLast_updated());
											serviceD.checkLowProduction(item, dataDevice);
										}
										
										uploadFilesService.deletingFile(root, fileName);
												
										message = "\nSUCCESS\n";
									}
									
								}
								
								fr.close(); // close
							}else {
								message = "\nFAILURE\n";
							}
						} else {
							// File not exits
							message = "\nSUCCESS\n";
						}

					} catch (Exception e) {
						message = "\nFAILURE\n";
						// TODO Auto-generated catch block
//						e.printStackTrace();
					}finally{}

				}
//				message = "\nSUCCESS\n";
			} else {
//				message = "Mode type test " + mode + " not supported by this sample script.";
				message = "\nFAILURE\n";
				
			}
			
			return message;

		} catch (Exception e) {
			message = "\nFAILURE!\n";
			// return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new
			// ResponseMessage(message));
			
			return message;
		}
	}
	
}