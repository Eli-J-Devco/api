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
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ErrorEntity;
import com.nwm.api.entities.ModelAbbTrioClass6210Entity;
import com.nwm.api.entities.ModelAdam4017WSClass8110Nelis190Entity;
import com.nwm.api.entities.ModelAdvancedEnergySolaronEntity;
import com.nwm.api.entities.ModelCampellScientificMeter1Entity;
import com.nwm.api.entities.ModelCampellScientificMeter2Entity;
import com.nwm.api.entities.ModelCampellScientificMeter3Entity;
import com.nwm.api.entities.ModelCampellScientificMeter4Entity;
import com.nwm.api.entities.ModelChintSolectriaInverterClass9725Entity;
import com.nwm.api.entities.ModelDataloggerEntity;
import com.nwm.api.entities.ModelElkorProductionMeterEntity;
import com.nwm.api.entities.ModelElkorWattsonPVMeterEntity;
import com.nwm.api.entities.ModelHukselfluxSr30d1DeviceclassV0Entity;
import com.nwm.api.entities.ModelIMTSolarClass8000Entity;
import com.nwm.api.entities.ModelIMTSolarTmodulClass8006Entity;
import com.nwm.api.entities.ModelIVTSolaronEXTEntity;
import com.nwm.api.entities.ModelKippZonenRT1Class8009Entity;
import com.nwm.api.entities.ModelLufftClass8020Entity;
import com.nwm.api.entities.ModelPVPInverterEntity;
import com.nwm.api.entities.ModelPVPowered3550260500kwInverterEntity;
import com.nwm.api.entities.ModelRT1Class30000Entity;
import com.nwm.api.entities.ModelSatconPvs357InverterEntity;
import com.nwm.api.entities.ModelShark100Entity;
import com.nwm.api.entities.ModelShark100TestEntity;
import com.nwm.api.entities.ModelSolarEdgeInverterEntity;
import com.nwm.api.entities.ModelSolectriaSGI226IVTEntity;
import com.nwm.api.entities.ModelTTiTrackerEntity;
import com.nwm.api.entities.ModelVerisIndustriesE51c2PowerMeterEntity;
import com.nwm.api.entities.ModelWKippZonenRT1Entity;
import com.nwm.api.entities.ModelXantrexGT100250500Entity;
import com.nwm.api.services.BatchJobService;
import com.nwm.api.services.DeviceService;
import com.nwm.api.services.ModelAbbTrioClass6210Service;
import com.nwm.api.services.ModelAdam4017WSClass8110Nelis190Service;
import com.nwm.api.services.ModelAdvancedEnergySolaronService;
import com.nwm.api.services.ModelCampellScientificMeter1Service;
import com.nwm.api.services.ModelCampellScientificMeter2Service;
import com.nwm.api.services.ModelCampellScientificMeter3Service;
import com.nwm.api.services.ModelCampellScientificMeter4Service;
import com.nwm.api.services.ModelChintSolectriaInverterClass9725Service;
import com.nwm.api.services.ModelDataloggerService;
import com.nwm.api.services.ModelElkorProductionMeterService;
import com.nwm.api.services.ModelElkorWattsonPVMeterService;
import com.nwm.api.services.ModelHukselfluxSr30d1DeviceclassV0Service;
import com.nwm.api.services.ModelIMTSolarClass8000Service;
import com.nwm.api.services.ModelIMTSolarTmodulClass8006Service;
import com.nwm.api.services.ModelIVTSolaronEXTService;
import com.nwm.api.services.ModelKippZonenRT1Class8009Service;
import com.nwm.api.services.ModelLufftClass8020Service;
import com.nwm.api.services.ModelPVPInverterService;
import com.nwm.api.services.ModelPVPowered3550260500kwInverterService;
import com.nwm.api.services.ModelRT1Class30000Service;
import com.nwm.api.services.ModelSatconPvs357InverterService;
import com.nwm.api.services.ModelShark100Service;
import com.nwm.api.services.ModelShark100TestService;
import com.nwm.api.services.ModelSolarEdgeInverterService;
import com.nwm.api.services.ModelSolectriaSGI226IVTService;
import com.nwm.api.services.ModelTTiTrackerService;
import com.nwm.api.services.ModelVerisIndustriesE51c2PowerMeterService;
import com.nwm.api.services.ModelWKippZonenRT1Service;
import com.nwm.api.services.ModelXantrexGT100250500Service;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.nio.file.Path;
import java.util.UUID;


@RestController
@ApiIgnore
@RequestMapping("/files")
public class UploadFilesController extends BaseController {
	public static String message = "";
	/**
	 * @description upload files datalogger and insert datalogger to database
	 * @author long.pham
	 * @since 2020-08-19
	 * @params RequestParam, files
	 */

//	@SuppressWarnings("unchecked")
	@PostMapping("/upload")
	@ResponseBody
//	@RequestParam("LOGFILE") MultipartFile[] files,
//	@RequestParam("SENDDATATRACE") String senddatatrace, @RequestParam("MODE") String mode,
//	@RequestParam("SERIALNUMBER") String serialnumber, @RequestParam("PASSWORD") String password,
//	@RequestParam("LOOPNAME") String loopname, @RequestParam("MODBUSIP") String modbusip,
//	@RequestParam("MODBUSPORT") String modbusport, @RequestParam("MODBUSDEVICE") String modbusdevice,
//	@RequestParam("MODBUSDEVICENAME") String modbusdevicename,
//	@RequestParam("MODBUSDEVICETYPE") String modbusdevicetype,
//	@RequestParam("MODBUSDEVICETYPENUMBER") String modbusdevicetypenumber,
//	@RequestParam("MODBUSDEVICECLASS") String modbusdeviceclass,
//	@RequestParam("MD5CHECKSUM") String md5checksum, @RequestParam("FILESIZE") String filesize,
//	@RequestParam("FILETIME") String filetime

	public String uploadFiles(@RequestParam(name = "LOGFILE", required = false) MultipartFile files[],
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
			@RequestParam(name = "FILETIME", required = false) String filetime) {

//		public String message = " ";
//		System.out.println("---------------------------------start------------------------------");
//		System.out.println("SENDDATATRACE: " + senddatatrace);
//		System.out.println("MODE: " + mode);
//		System.out.println("SERIALNUMBER: " + serialnumber);
//		System.out.println("PASSWORD: " + password);
//		System.out.println("LOOPNAME: " + loopname);
//		System.out.println("MODBUSIP: " + modbusip);
//		System.out.println("MODBUSPORT: " + modbusport);
//		System.out.println("MODBUSDEVICE: " + modbusdevice);
//		System.out.println("MODBUSDEVICENAME: " + modbusdevicename);
//		System.out.println("MODBUSDEVICETYPE: " + modbusdevicetype);
//		System.out.println("MODBUSDEVICETYPENUMBER: " + modbusdevicetypenumber);
//		System.out.println("MODBUSDEVICECLASS: " + modbusdeviceclass);
//		System.out.println("-------------------------------end--------------------------------");
		
		try {

			String LOGFILEUPLOAD = "LOGFILEUPLOAD";
			List<String> fileNames = new ArrayList<>();

			if (mode.equals(LOGFILEUPLOAD) && files.length > 0) {
				Arrays.asList(files).stream().forEach(file -> {
					String fileName = file.getOriginalFilename();
					String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
					fileNames.add(file.getOriginalFilename());

					Path root = Paths.get(
							Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey));
					String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
					String unique = UUID.randomUUID().toString();
					byte[] bytes;
					try {
						bytes = file.getBytes();
						switch (ext) {
							case "gz":
	
								Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
										Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice + "-" + unique + "." + timeStamp
										+ ".log.gz");
								Files.write(path, bytes);
	
								InputStream fis = file.getInputStream();
								GZIPInputStream gis = new GZIPInputStream(fis);
	
								fileName = "bm-" + modbusdevice  + "-" + unique + "." + timeStamp + ".log";
								FileOutputStream fos = new FileOutputStream(root.resolve(fileName).toString());
								byte[] buffer = new byte[1024 * 1024];
								int len = 0;
								while ((len = gis.read(buffer)) != -1) {
									fos.write(buffer, 0, len);
								}
								// close resources
								fos.close();
								gis.close();
								break;
							case "log":
								// code block
								Path pathLogUplad = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
										Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "." + timeStamp
										+ ".log");
								Files.write(pathLogUplad, bytes);
								fileName = "bm-" + modbusdevice  + "-" + unique + "." + timeStamp + ".log";
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

							DeviceService serviceD = new DeviceService();
							DeviceEntity deviceE = new DeviceEntity();
							deviceE.setSerial_number(serialnumber);
							List<DeviceEntity> dataDevice = serviceD.getDeviceListBySerialNumber(deviceE);
							if (dataDevice.size() > 0) {
								
								for (int i = 0; i < dataDevice.size(); i++) {
									DeviceEntity item = dataDevice.get(i);
									ZoneId zoneIdLosAngeles = ZoneId.of(item.getTimezone_value()); // "America/Los_Angeles"
							        ZonedDateTime zdtNowLosAngeles = ZonedDateTime.now(zoneIdLosAngeles);
							        int hours = zdtNowLosAngeles.getHour();
							        
									if( modbusdevice.equals(item.getModbusdevicenumber())) {
										switch (item.getDatatablename()) {

										// Model model_pv_powered_35_50_260_500kw_inverter
										case "model_pv_powered_35_50_260_500kw_inverter":
											ModelPVPowered3550260500kwInverterService serviceModelPVPowered = new ModelPVPowered3550260500kwInverterService();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													DeviceEntity deviceUpdateE = new DeviceEntity();
													
													// OutputGeneration
													if(!Lib.isBlank(words.get(37))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(!Lib.isBlank(words.get(37)) ? Double.parseDouble(words.get(37)) : null);
														deviceUpdateE.setField_value1(!Lib.isBlank(words.get(37)) ? Double.parseDouble(words.get(37)) : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// DCInputVoltage
													if(!Lib.isBlank(words.get(34))) {
														deviceUpdateE.setField_value2(!Lib.isBlank(words.get(34)) ? Double.parseDouble(words.get(34)) : null);
													} else {
														deviceUpdateE.setField_value2(null);
													}
													
													// DCInputCurrent
													if(!Lib.isBlank(words.get(35))) {
														deviceUpdateE.setLast_value(!Lib.isBlank(words.get(35)) ? Double.parseDouble(words.get(35)) : null);
													} else {
														deviceUpdateE.setField_value3(null);
													}
													
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													ModelPVPowered3550260500kwInverterEntity dataModelPVPowered = serviceModelPVPowered.setModelPVPowered3550260KWInverter(line);
													dataModelPVPowered.setId_device(item.getId());
													serviceModelPVPowered.insertModelPVPowered3550260KWInverter(dataModelPVPowered);
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
														e.printStackTrace();  
													}
												}
											}
											
											break;
											
										case "model_shark100":
											ModelShark100Service serviceModelShark100 = new ModelShark100Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													
													// watts_3ph_total
													if(!Lib.isBlank(words.get(13))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(!Lib.isBlank(words.get(13)) ? Double.parseDouble(words.get(13)) : null);
														deviceUpdateE.setField_value1(!Lib.isBlank(words.get(13)) ? Double.parseDouble(words.get(13)) : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// vars_3ph_total
													if(!Lib.isBlank(words.get(14))) {
														deviceUpdateE.setField_value2(!Lib.isBlank(words.get(14)) ? Double.parseDouble(words.get(14)) : null);
													} else {
														deviceUpdateE.setField_value2(null);
													}
													
													// vas_3ph_total
													if(!Lib.isBlank(words.get(15))) {
														deviceUpdateE.setField_value3(!Lib.isBlank(words.get(15)) ? Double.parseDouble(words.get(15)) : null);
													} else {
														deviceUpdateE.setField_value3(null);
													}
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													
													ModelShark100Entity dataModelShark100 = serviceModelShark100.setModelShark100(line);
													dataModelShark100.setId_device(item.getId());
													serviceModelShark100.insertModelShark100(dataModelShark100);												
													
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){    
														e.printStackTrace();  
													}
													
												}
											}
											
											
											break;
										case "model_rt1_class30000":
											ModelRT1Class30000Service serviceModelRT1Class30000 = new ModelRT1Class30000Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// sensor1_data
													if(!Lib.isBlank(words.get(8))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(!Lib.isBlank(words.get(8)) ? Double.parseDouble(words.get(8)) : null);
														deviceUpdateE.setField_value1(!Lib.isBlank(words.get(8)) ? Double.parseDouble(words.get(8)) : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// panel_temperature
													if(!Lib.isBlank(words.get(9))) {
														deviceUpdateE.setField_value2(!Lib.isBlank(words.get(9)) ? Double.parseDouble(words.get(9)) : null);
													} else {
														deviceUpdateE.setField_value2(null);
													}
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													
													ModelRT1Class30000Entity dataModelRTC30000 = serviceModelRT1Class30000.setModelRT1Class30000(line);
													dataModelRTC30000.setId_device(item.getId());
													
													serviceModelRT1Class30000.insertModelRT1Class30000(dataModelRTC30000);
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
														e.printStackTrace();  
													}
													
												}
											}
											
											
											break;
											
										case "model_kippzonen_rt1_class8009":
											ModelKippZonenRT1Class8009Service serviceModelKippzonen = new ModelKippZonenRT1Class8009Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													
													// Sensor1_data
													if(!Lib.isBlank(words.get(8))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(!Lib.isBlank(words.get(8)) ? Double.parseDouble(words.get(8)) : null);
														deviceUpdateE.setField_value1(!Lib.isBlank(words.get(8)) ? Double.parseDouble(words.get(8)) : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// panel_temperature
													if(!Lib.isBlank(words.get(9))) {
														deviceUpdateE.setField_value2(!Lib.isBlank(words.get(9)) ? Double.parseDouble(words.get(9)) : null);
													} else {
														deviceUpdateE.setField_value2(null);
													}
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													
													ModelKippZonenRT1Class8009Entity dataKippZonen = serviceModelKippzonen.setModelKippZonenRT1Class8009(line);
													dataKippZonen.setId_device(item.getId());
													serviceModelKippzonen.insertModelKippZonenRT1Class8009(dataKippZonen);
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
														e.printStackTrace();  
													}
													
												}
											}
											
											
											break;
											
										case "model_ivt_solaron_ext":
											ModelIVTSolaronEXTService serviceModelIVTSolaronEXT = new ModelIVTSolaronEXTService();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// ac_power
													if(!Lib.isBlank(words.get(13))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(!Lib.isBlank(words.get(13)) ? Double.parseDouble(words.get(13)) : null);
														deviceUpdateE.setField_value1(!Lib.isBlank(words.get(13)) ? Double.parseDouble(words.get(13)) : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// ac_frequency
													if(!Lib.isBlank(words.get(15))) {
														deviceUpdateE.setField_value2(!Lib.isBlank(words.get(15)) ? Double.parseDouble(words.get(15)) : null);
													} else {
														deviceUpdateE.setField_value2(null);
													}
													
													// pv_voltage
													if(!Lib.isBlank(words.get(16))) {
														deviceUpdateE.setField_value3(!Lib.isBlank(words.get(16)) ? Double.parseDouble(words.get(16)) : null);
													} else {
														deviceUpdateE.setField_value3(null);
													}
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													
													ModelIVTSolaronEXTEntity dataModelIVTSolaronEXT = serviceModelIVTSolaronEXT.setModelIVTSolaronEXT(line);
													dataModelIVTSolaronEXT.setId_device(item.getId());
													serviceModelIVTSolaronEXT.insertModelIVTSolaronEXT(dataModelIVTSolaronEXT);
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
														e.printStackTrace();  
													}
													
												}
											}
											
											break;
											
										case "model_hukseflux_sr30d1_deviceclass_v0":
											ModelHukselfluxSr30d1DeviceclassV0Service serviceModelHukselfluxSr30d1DeviceclassV0 = new ModelHukselfluxSr30d1DeviceclassV0Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// IrradianceTcs
													if(!Lib.isBlank(words.get(4))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(!Lib.isBlank(words.get(4)) ? Double.parseDouble(words.get(4)) : null);
														deviceUpdateE.setField_value1(!Lib.isBlank(words.get(4)) ? Double.parseDouble(words.get(4)) : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// SensorBodyTemperature
													if(!Lib.isBlank(words.get(6))) {
														deviceUpdateE.setField_value2(!Lib.isBlank(words.get(6)) ? Double.parseDouble(words.get(6)) : null);
													} else {
														deviceUpdateE.setField_value2(null);
													}
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													
													ModelHukselfluxSr30d1DeviceclassV0Entity dataModelHukselfluxSr30d1DeviceclassV0 = serviceModelHukselfluxSr30d1DeviceclassV0.setModelHukselfluxSr30d1DeviceclassV0(line);
													dataModelHukselfluxSr30d1DeviceclassV0.setId_device(item.getId());
													serviceModelHukselfluxSr30d1DeviceclassV0.insertModelHukselfluxSr30d1DeviceclassV0(dataModelHukselfluxSr30d1DeviceclassV0);
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
														e.printStackTrace();  
													}
												}
											}
											
											break;
											
										case "model_imtsolar_class8000":
											ModelIMTSolarClass8000Service serviceModelIMTSolarClass8000 = new ModelIMTSolarClass8000Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													DeviceEntity deviceUpdateE = new DeviceEntity();
													
													// irradiance
													if(!Lib.isBlank(words.get(4))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(!Lib.isBlank(words.get(4)) ? Double.parseDouble(words.get(4)) : null);
														deviceUpdateE.setField_value1(!Lib.isBlank(words.get(4)) ? Double.parseDouble(words.get(4)) : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// tcell
													if(!Lib.isBlank(words.get(5))) {
														deviceUpdateE.setField_value2(!Lib.isBlank(words.get(5)) ? Double.parseDouble(words.get(5)) : null);
													} else {
														deviceUpdateE.setField_value2(null);
													}
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													
													ModelIMTSolarClass8000Entity dataModelIMTSolarClass = serviceModelIMTSolarClass8000.setModelIMTSolarClass8000(line);
													dataModelIMTSolarClass.setId_device(item.getId());
													
													serviceModelIMTSolarClass8000.insertModelIMTSolarClass8000(dataModelIMTSolarClass);
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
														e.printStackTrace();  
													}
												}
											}
											
											break;
											
										case "model_imtsolar_tmodul_class8006":
											ModelIMTSolarTmodulClass8006Service serviceModelIMTSolarTmodulClass8006 = new ModelIMTSolarTmodulClass8006Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													if(!Lib.isBlank(words.get(4))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(!Lib.isBlank(words.get(4)) ? Double.parseDouble(words.get(4)) : null);
														deviceUpdateE.setField_value1(!Lib.isBlank(words.get(4)) ? Double.parseDouble(words.get(4)) : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// value 2
													deviceUpdateE.setField_value2(null);
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													
													ModelIMTSolarTmodulClass8006Entity dataModelIMTSolarTmodulClass8006 = serviceModelIMTSolarTmodulClass8006.setDataModelIMTSolarTmodulClass8006(line);
													dataModelIMTSolarTmodulClass8006.setId_device(item.getId());
													
													serviceModelIMTSolarTmodulClass8006.insertModelIMTSolarTmodulClass8006(dataModelIMTSolarTmodulClass8006);
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
														e.printStackTrace();  
													}
												}
											}
											
											break;
											
										case "model_advanced_energy_solaron":
											ModelAdvancedEnergySolaronService serviceModelAdvancedEnergySolaron = new ModelAdvancedEnergySolaronService();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													DeviceEntity deviceUpdateE = new DeviceEntity();
													
													// ac_power
													if(!Lib.isBlank(words.get(13))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(!Lib.isBlank(words.get(13)) ? Double.parseDouble(words.get(13)) : null);
														deviceUpdateE.setField_value1(!Lib.isBlank(words.get(13)) ? Double.parseDouble(words.get(13)) : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// ac_frequency
													if(!Lib.isBlank(words.get(14))) {
														deviceUpdateE.setField_value2(!Lib.isBlank(words.get(14)) ? Double.parseDouble(words.get(14)) : null);
													} else {
														deviceUpdateE.setField_value2(null);
													}
													
													// pv_voltage
													if(!Lib.isBlank(words.get(15))) {
														deviceUpdateE.setField_value3(!Lib.isBlank(words.get(15)) ? Double.parseDouble(words.get(15)) : null);
													} else {
														deviceUpdateE.setField_value3(null);
													}
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														System.out.println("ID Device: " + item.getId()  + "Error_code: " + words.get(1) + " - Device group: " + item.getId_device_group() + "- Id error: " + rowItemError.getId() );
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													
													ModelAdvancedEnergySolaronEntity dataModelAdvancedEnergySolaron = serviceModelAdvancedEnergySolaron.setModelAdvancedEnergySolaron(line);
													dataModelAdvancedEnergySolaron.setId_device(item.getId());
													serviceModelAdvancedEnergySolaron.insertModelAdvancedEnergySolaron(dataModelAdvancedEnergySolaron);
													
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
//														System.out.println("e1: " + e);
														e.printStackTrace();  
													}
												}
											}
											
											break;
											
										case "model_pvp_inverter":
											ModelPVPInverterService serviceModelPVPInverter = new ModelPVPInverterService();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// line_kw
													if(!Lib.isBlank(words.get(14))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(!Lib.isBlank(words.get(14)) ? Double.parseDouble(words.get(14)) : null);
														deviceUpdateE.setField_value1(!Lib.isBlank(words.get(14)) ? Double.parseDouble(words.get(14)) : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// dc_output_voltage
													if(!Lib.isBlank(words.get(11))) {
														deviceUpdateE.setField_value2(!Lib.isBlank(words.get(11)) ? Double.parseDouble(words.get(11)) : null);
													} else {
														deviceUpdateE.setField_value2(null);
													}
													
													// dc_output_current
													if(!Lib.isBlank(words.get(12))) {
														deviceUpdateE.setField_value3(!Lib.isBlank(words.get(12)) ? Double.parseDouble(words.get(12)) : null);
													} else {
														deviceUpdateE.setField_value3(null);
													}
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													
													ModelPVPInverterEntity dataModelPVPInverter = serviceModelPVPInverter.setModelPVPInverter(line);
													dataModelPVPInverter.setId_device(item.getId());
													serviceModelPVPInverter.insertModelPVPInverter(dataModelPVPInverter);
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
														e.printStackTrace();  
													}
													
												}
											}
											
											break;
											
											
										case "model_chint_solectria_inverter_class9725":
											ModelChintSolectriaInverterClass9725Service serviceModelChintSolectria = new ModelChintSolectriaInverterClass9725Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													DeviceEntity deviceUpdateE = new DeviceEntity();
													
													// AC_ActivePower
													if(!Lib.isBlank(words.get(32))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0"));
														deviceUpdateE.setField_value1(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0"));
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// AC_ApparentPower
													if(!Lib.isBlank(words.get(33))) {
														deviceUpdateE.setField_value2(!Lib.isBlank(words.get(33)) ? Double.parseDouble(words.get(33)) : null);
													} else {
														deviceUpdateE.setField_value2(null);
													}
													
													// PV1_Voltage
													if(!Lib.isBlank(words.get(40))) {
														deviceUpdateE.setField_value3(!Lib.isBlank(words.get(40)) ? Double.parseDouble(words.get(40)) : null);
													} else {
														deviceUpdateE.setField_value3(null);
													}
													
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													
													ModelChintSolectriaInverterClass9725Entity dataModelChint = serviceModelChintSolectria.setModelChintSolectriaInverterClass9725(line);
													dataModelChint.setId_device(item.getId());
													
													System.out.println("id device: " + dataModelChint.getId_device() + " - word1: "+ dataModelChint.getTime() + "\n");
													
													serviceModelChintSolectria.insertModelChintSolectriaInverterClass9725(dataModelChint);
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
//														if(logFile.delete()){  
////															System.out.println(logFile.getName() + " deleted .log");  
//														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
//														if(logGzFile.delete()) {  
////															System.out.println(logGzFile.getName() + " deleted .log.gz");   
//														}		
													}  
													catch(Exception e){  
//														System.out.println("Error: " + e);
														e.printStackTrace();  
													}
												}
											}
											
											break;
											
											
										case "model_veris_industries_e51c2_power_meter":
											ModelVerisIndustriesE51c2PowerMeterService serviceModelVeris = new ModelVerisIndustriesE51c2PowerMeterService();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// TotalNetInstantaneousRealPower
													if(!Lib.isBlank(words.get(14))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(!Lib.isBlank(words.get(14)) ? Double.parseDouble(words.get(14)) : null);
														deviceUpdateE.setField_value1(!Lib.isBlank(words.get(14)) ? Double.parseDouble(words.get(14)) : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// RealPowerPhaseA
													if(!Lib.isBlank(words.get(55))) {
														deviceUpdateE.setField_value2(!Lib.isBlank(words.get(55)) ? Double.parseDouble(words.get(55)) : null);
													} else {
														deviceUpdateE.setField_value2(null);
													}
													
													// RealPowerPhaseB
													if(!Lib.isBlank(words.get(56))) {
														deviceUpdateE.setField_value3(!Lib.isBlank(words.get(56)) ? Double.parseDouble(words.get(56)) : null);
													} else {
														deviceUpdateE.setField_value3(null);
													}
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													
													ModelVerisIndustriesE51c2PowerMeterEntity dataModelVeris = serviceModelVeris.setModelChintSolectriaInverterClass9725(line);
													dataModelVeris.setId_device(item.getId());
													serviceModelVeris.insertModelVerisIndustriesE51c2PowerMeter(dataModelVeris);
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
														e.printStackTrace();  
													}
												}
											}
											
											break;
											
											
											
										case "model_satcon_pvs357_inverter":
											ModelSatconPvs357InverterService serviceModelSatcon = new ModelSatconPvs357InverterService();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													Double nvm103 = Double.parseDouble(!Lib.isBlank(words.get(103)) ? words.get(103) : "0");
													Double nvm102 = Double.parseDouble(!Lib.isBlank(words.get(102)) ? words.get(102) : "0");
													
													Double nvmEnergyTotal = nvm103 * 1000 + nvm102;
													DeviceEntity deviceUpdateE = new DeviceEntity();
													//Input_kW
													if(!Lib.isBlank(words.get(31))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(!Lib.isBlank(words.get(31)) ? Double.parseDouble(words.get(31)) : null);
														deviceUpdateE.setField_value1(!Lib.isBlank(words.get(31)) ? Double.parseDouble(words.get(31)) : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// Output_kw
													if(!Lib.isBlank(words.get(30))) {
														deviceUpdateE.setField_value2(!Lib.isBlank(words.get(30)) ? Double.parseDouble(words.get(30)) : null);
													} else {
														deviceUpdateE.setField_value2(null);
													}
													
													// DC_Input_Volts
													if(!Lib.isBlank(words.get(15))) {
														deviceUpdateE.setField_value3(!Lib.isBlank(words.get(15)) ? Double.parseDouble(words.get(15)) : null);
													} else {
														deviceUpdateE.setField_value3(null);
													}
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													
													ModelSatconPvs357InverterEntity dataModelSatcon = serviceModelSatcon.setModelSatconPvs357Inverter(line);
													dataModelSatcon.setId_device(item.getId());
													dataModelSatcon.setNvmActiveEnergy(!Lib.isBlank(words.get(31)) ? nvmEnergyTotal : Double.parseDouble("0.001"));
													serviceModelSatcon.insertModelSatconPvs357Inverter(dataModelSatcon);
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
														e.printStackTrace();  
													}
												}
											}
											
											break;
											
											

										case "model_elkor_wattson_pv_meter":
											ModelElkorWattsonPVMeterService serviceModelElkor = new ModelElkorWattsonPVMeterService();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													DeviceEntity deviceUpdateE = new DeviceEntity();
													if(!Lib.isBlank(words.get(5))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(!Lib.isBlank(words.get(5)) ? Double.parseDouble(words.get(5)) : null);
														deviceUpdateE.setField_value1(!Lib.isBlank(words.get(5)) ? Double.parseDouble(words.get(5)) : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// TotalReactivePower
													if(!Lib.isBlank(words.get(6))) {
														deviceUpdateE.setField_value2(!Lib.isBlank(words.get(6)) ? Double.parseDouble(words.get(6)) : null);
													} else {
														deviceUpdateE.setField_value2(null);
													}
													
													// TotalApparentPower
													if(!Lib.isBlank(words.get(7))) {
														deviceUpdateE.setField_value3(!Lib.isBlank(words.get(7)) ? Double.parseDouble(words.get(7)) : null);
													} else {
														deviceUpdateE.setField_value3(null);
													}
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													ModelElkorWattsonPVMeterEntity dataModelElkor = serviceModelElkor.setModelElkorWattsonPVMeter(line);
													dataModelElkor.setId_device(item.getId());
													serviceModelElkor.insertModelElkorWattsonPVMeter(dataModelElkor);
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
														e.printStackTrace();  
													}
												}
											}
											
											break;
											
											
											
										case "model_w_kipp_zonen_rt1":
											ModelWKippZonenRT1Service serviceModelWkipp = new ModelWKippZonenRT1Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// SunPOATempComp
													if(!Lib.isBlank(words.get(8))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(!Lib.isBlank(words.get(8)) ? Double.parseDouble(words.get(8)) : null);
														deviceUpdateE.setField_value1(!Lib.isBlank(words.get(8)) ? Double.parseDouble(words.get(8)) : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}

													// PanelTemperature
													if(!Lib.isBlank(words.get(9))) {
														deviceUpdateE.setField_value2(!Lib.isBlank(words.get(9)) ? Double.parseDouble(words.get(9)) : null);
													} else {
														deviceUpdateE.setField_value2(null);
													}
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													
													ModelWKippZonenRT1Entity dataModelWkipp = serviceModelWkipp.setModelWKippZonenRT1(line);
													dataModelWkipp.setId_device(item.getId());
													serviceModelWkipp.insertModelWKippZonenRT1(dataModelWkipp);
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
														e.printStackTrace();  
													}
													
												}
											}
											
											break;
											
											
										case "model_elkor_production_meter":
											ModelElkorProductionMeterService serviceModelElkorP = new ModelElkorProductionMeterService();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// ActivePowerTotal
													if(!Lib.isBlank(words.get(4))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(!Lib.isBlank(words.get(4)) ? Double.parseDouble(words.get(4)) : null);
														deviceUpdateE.setField_value1(!Lib.isBlank(words.get(4)) ? Double.parseDouble(words.get(4)) : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// VoltageA
													if(!Lib.isBlank(words.get(14))) {
														deviceUpdateE.setField_value2(!Lib.isBlank(words.get(14)) ? Double.parseDouble(words.get(14)) : null);
													} else {
														deviceUpdateE.setField_value2(null);
													}
													
													// VoltageB
													if(!Lib.isBlank(words.get(15))) {
														deviceUpdateE.setField_value3(!Lib.isBlank(words.get(15)) ? Double.parseDouble(words.get(15)) : null);
													} else {
														deviceUpdateE.setField_value3(null);
													}
													
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													ModelElkorProductionMeterEntity dataModelElkorP = serviceModelElkorP.setModelElkorProductionMeter(line);
													dataModelElkorP.setId_device(item.getId());
													serviceModelElkorP.insertModelElkorProductionMeter(dataModelElkorP);
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
														e.printStackTrace();  
													}
												}
											}
											
											break;
											
											
										// Model ABB Inverter
										case "model_abb_trio_class6210":
											ModelAbbTrioClass6210Service serviceModelABB = new ModelAbbTrioClass6210Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													DeviceEntity deviceUpdateE = new DeviceEntity();
													DecimalFormat df = new DecimalFormat("#.0");
													double nvmActivePowerABB = Double.parseDouble((!Lib.isBlank(words.get(15)) ? words.get(15) : "0") ) / 1000;
													
													if(nvmActivePowerABB < 0) { nvmActivePowerABB = 0.0; };
													
													// Input1Power
													if(!Lib.isBlank(words.get(15))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(!Lib.isBlank(words.get(15)) ? Double.parseDouble(df.format(nvmActivePowerABB)) : null);
														deviceUpdateE.setField_value1(!Lib.isBlank(words.get(15)) ? Double.parseDouble(df.format(nvmActivePowerABB)) : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// Input1Voltage
													if(!Lib.isBlank(words.get(18))) {
														deviceUpdateE.setField_value2(!Lib.isBlank(words.get(18)) ? Double.parseDouble(words.get(18)) : null);
													} else {
														deviceUpdateE.setField_value2(null);
													}
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													 
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													
													ModelAbbTrioClass6210Entity dataModelABB = serviceModelABB.setModelAbbTrioClass6210(line);
													dataModelABB.setId_device(item.getId());
													dataModelABB.setInput1Power(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
													dataModelABB.setNvmActivePower(Double.parseDouble(!Lib.isBlank(words.get(15)) ? df.format(nvmActivePowerABB) : "0.001"));

													serviceModelABB.insertModelAbbTrioClass6210(dataModelABB);
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
														e.printStackTrace();  
													}
												}
											}
											
											break;
											
											
										// Model weather station
										case "model_lufft_class8020":
											ModelLufftClass8020Service serviceModelLufft = new ModelLufftClass8020Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// AirTemperatureActual
													if(!Lib.isBlank(words.get(25))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(!Lib.isBlank(words.get(25)) ? Double.parseDouble(words.get(25)) : null);
														deviceUpdateE.setField_value1(!Lib.isBlank(words.get(25)) ? Double.parseDouble(words.get(25)) : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// IrradianceActual
													if(!Lib.isBlank(words.get(21))) {
														deviceUpdateE.setField_value2(!Lib.isBlank(words.get(21)) ? Double.parseDouble(words.get(21)) : null);
													} else {
														deviceUpdateE.setField_value2(null);
													}
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													
													ModelLufftClass8020Entity dataModelLufft = serviceModelLufft.setModelLufftClass8020(line);
													dataModelLufft.setId_device(item.getId());

													serviceModelLufft.insertModelLufftClass8020(dataModelLufft);
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
														e.printStackTrace();  
													}
												}
											}
											
											break;
											
										case "model_solectria_sgi_226ivt":
											ModelSolectriaSGI226IVTService serviceModelSolectriaSGI226IVT = new ModelSolectriaSGI226IVTService();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													DeviceEntity deviceUpdateE = new DeviceEntity();
													
													DecimalFormat df = new DecimalFormat("#.0");
													double nvmActivePower226 = Double.parseDouble((!Lib.isBlank(words.get(5)) ? words.get(5) : "0") ) / 1000;
													if(nvmActivePower226 < 0 ) {nvmActivePower226 = 0.0; }
													
													if(!Lib.isBlank(words.get(5))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(!Lib.isBlank(words.get(5)) ? Double.parseDouble(df.format(nvmActivePower226)) : null);
														deviceUpdateE.setField_value1(!Lib.isBlank(words.get(5)) ? Double.parseDouble(df.format(nvmActivePower226)) : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// IrradianceActual
													if(!Lib.isBlank(words.get(21))) {
														deviceUpdateE.setField_value2(!Lib.isBlank(words.get(21)) ? Double.parseDouble(words.get(21)) : null);
													} else {
														deviceUpdateE.setField_value2(null);
													}
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													ModelSolectriaSGI226IVTEntity dataModelSolectria226 = serviceModelSolectriaSGI226IVT.setModelSolectriaSGI226IVT(line);
													dataModelSolectria226.setId_device(item.getId());
													dataModelSolectria226.setACPowerOutput(Double.parseDouble(!Lib.isBlank(words.get(5)) ? df.format(nvmActivePower226) : "0.001"));
													dataModelSolectria226.setNvmActivePower(Double.parseDouble(!Lib.isBlank(words.get(5)) ? df.format(nvmActivePower226) : "0.001"));
													serviceModelSolectriaSGI226IVT.insertModelSolectriaSGI226IVT(dataModelSolectria226);
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
														e.printStackTrace();  
													}
													
												}
											}
											
											
											break;
											
											
										case "model_tti_tracker":
											ModelTTiTrackerService serviceModelTTiTracker = new ModelTTiTrackerService();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													DeviceEntity deviceUpdateE = new DeviceEntity();
													double setAngle = Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.0");
													setAngle = Math.round((setAngle * 180) / 3.14);
													
													// ReadAngle
													if(!Lib.isBlank(words.get(7))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(!Lib.isBlank(words.get(7)) ? Double.parseDouble(String.valueOf(setAngle)) : null);
														deviceUpdateE.setField_value1(!Lib.isBlank(words.get(7)) ? Double.parseDouble(String.valueOf(setAngle)) : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// WindSpeed
													if(!Lib.isBlank(words.get(10))) {
														deviceUpdateE.setField_value2(!Lib.isBlank(words.get(10)) ? Double.parseDouble(words.get(10)) : null);
													} else {
														deviceUpdateE.setField_value2(null);
													}
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														System.out.println("ID Device: " + item.getId()  + "Error_code: " + words.get(1) + " - Device group: " + item.getId_device_group() + "- Id error: " + rowItemError.getId() );
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													
													ModelTTiTrackerEntity dataModelTTiTracker = serviceModelTTiTracker.setModelTTiTracker(line);
													dataModelTTiTracker.setId_device(item.getId());
													serviceModelTTiTracker.insertModelTTiTracker(dataModelTTiTracker);
													
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
//														System.out.println("e1: " + e);
														e.printStackTrace();  
													}
												}
											}
											
											break;
											
											
											
										case "model_solaredge_inverter":
											ModelSolarEdgeInverterService serviceModelSET = new ModelSolarEdgeInverterService();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													DeviceEntity deviceUpdateE = new DeviceEntity();
													
													DecimalFormat df = new DecimalFormat("#.0");
													double nvmActivePowerSET = Double.parseDouble((!Lib.isBlank(words.get(19)) ? words.get(19) : "0") ) / 1000;
													double nvmActiveEnergySET = Double.parseDouble((!Lib.isBlank(words.get(30)) ? words.get(30) : "0") ) / 1000;
													
													
													if(!Lib.isBlank(words.get(19))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(!Lib.isBlank(words.get(19)) ? Double.parseDouble(df.format(nvmActivePowerSET)) : null);
														deviceUpdateE.setField_value1(!Lib.isBlank(words.get(19)) ? Double.parseDouble(df.format(nvmActivePowerSET)) : null);
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// value 2
													deviceUpdateE.setField_value2(null);
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													
													ModelSolarEdgeInverterEntity dataModelSET = serviceModelSET.setModelSolarEdgeInverter(line);
													dataModelSET.setId_device(item.getId());
													dataModelSET.setI_AC_Power(Double.parseDouble(!Lib.isBlank(words.get(19)) ? df.format(nvmActivePowerSET) : "0.001"));
													dataModelSET.setNvmActivePower(Double.parseDouble(!Lib.isBlank(words.get(19)) ? df.format(nvmActivePowerSET) : "0.001"));
													dataModelSET.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(30)) ? df.format(nvmActiveEnergySET) : "0.001"));
													serviceModelSET.insertModelSolarEdgeInverter(dataModelSET);
													
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
														e.printStackTrace();  
													}
													
												}
											}
											
											break;
											
											
											
											
										case "model_xantrex_gt100_250_500":
											ModelXantrexGT100250500Service serviceModelXantrex = new ModelXantrexGT100250500Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// ReadPower
													if(!Lib.isBlank(words.get(7))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0"));
														deviceUpdateE.setField_value1(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0"));
														
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// PVVoltage
													if(!Lib.isBlank(words.get(11))) {
														deviceUpdateE.setField_value2(!Lib.isBlank(words.get(11)) ? Double.parseDouble(words.get(11)) : null);
													} else {
														deviceUpdateE.setField_value2(null);
													}
													
													// PVCurrent
													if(!Lib.isBlank(words.get(12))) {
														deviceUpdateE.setField_value3(!Lib.isBlank(words.get(12)) ? Double.parseDouble(words.get(12)) : null);
													} else {
														deviceUpdateE.setField_value3(null);
													}
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														System.out.println("ID Device: " + item.getId()  + "Error_code: " + words.get(1) + " - Device group: " + item.getId_device_group() + "- Id error: " + rowItemError.getId() );
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													ModelXantrexGT100250500Entity dataModelXantrex = serviceModelXantrex.setModelXantrexGT100250500(line);
													dataModelXantrex.setId_device(item.getId());
													serviceModelXantrex.insertModelXantrexGT100250500(dataModelXantrex);
													
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
//														System.out.println("e1: " + e);
														e.printStackTrace();  
													}
												}
											}
											
											break;
											
											
										case "model_adam4017ws_class8110_nelis190": 
											ModelAdam4017WSClass8110Nelis190Service serviceModelAdam4017 = new ModelAdam4017WSClass8110Nelis190Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// ReadPower
													if(!Lib.isBlank(words.get(7))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0"));
														deviceUpdateE.setField_value1(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0"));
														
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// AmbientTemp
													if(!Lib.isBlank(words.get(4))) {
														deviceUpdateE.setField_value2(!Lib.isBlank(words.get(4)) ? Double.parseDouble(words.get(4)) : null);
													} else {
														deviceUpdateE.setField_value2(null);
													}
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														System.out.println("ID Device: " + item.getId()  + "Error_code: " + words.get(1) + " - Device group: " + item.getId_device_group() + "- Id error: " + rowItemError.getId() );
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													
													ModelAdam4017WSClass8110Nelis190Entity dataModelAdam4017 = serviceModelAdam4017.setModelAdam4017WSClass8110Nelis190(line);
													dataModelAdam4017.setId_device(item.getId());
													serviceModelAdam4017.inserModelAdam4017WSClass8110Nelis190(dataModelAdam4017);
													
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
//														System.out.println("e1: " + e);
														e.printStackTrace();  
													}
												}
											}
											
											break;
											
											
										case "model_campell_scientific_meter1": 
											ModelCampellScientificMeter1Service serviceModelCSM1 = new ModelCampellScientificMeter1Service();
											System.out.println("modbusport: " + modbusport + " - MODBUSDEVICE: " + modbusdevice);

											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// ReadPower
													if(!Lib.isBlank(words.get(4))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0"));
														deviceUpdateE.setField_value1(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0"));
														
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// value 2
													deviceUpdateE.setField_value2(null);
													// value 3
													deviceUpdateE.setField_value3(null);
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														System.out.println("ID Device: " + item.getId()  + "Error_code: " + words.get(1) + " - Device group: " + item.getId_device_group() + "- Id error: " + rowItemError.getId() );
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													
													ModelCampellScientificMeter1Entity dataModelCSM1 = serviceModelCSM1.setModelCampellScientificMeter1(line);
													dataModelCSM1.setId_device(item.getId());
													serviceModelCSM1.insertModelCampellScientificMeter1(dataModelCSM1);
													
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
//														System.out.println("e1: " + e);
														e.printStackTrace();  
													}
												}
											}
											
											break;
											
											
										case "model_campell_scientific_meter2": 
											ModelCampellScientificMeter2Service serviceModelCSM2 = new ModelCampellScientificMeter2Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// ReadPower
													if(!Lib.isBlank(words.get(4))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0"));
														deviceUpdateE.setField_value1(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0"));
														
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// value 2
													deviceUpdateE.setField_value2(null);
													// value 3
													deviceUpdateE.setField_value3(null);
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														System.out.println("ID Device: " + item.getId()  + "Error_code: " + words.get(1) + " - Device group: " + item.getId_device_group() + "- Id error: " + rowItemError.getId() );
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													
													ModelCampellScientificMeter2Entity dataModelCSM2 = serviceModelCSM2.setModelCampellScientificMeter2(line);
													dataModelCSM2.setId_device(item.getId());
													serviceModelCSM2.insertModelCampellScientificMeter2(dataModelCSM2);
													
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
//														System.out.println("e1: " + e);
														e.printStackTrace();  
													}
												}
											}
											
											break;
											
										case "model_campell_scientific_meter3": 
											ModelCampellScientificMeter3Service serviceModelCSM3 = new ModelCampellScientificMeter3Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// ReadPower
													if(!Lib.isBlank(words.get(4))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0"));
														deviceUpdateE.setField_value1(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0"));
														
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// value 2
													deviceUpdateE.setField_value2(null);
													// value 3
													deviceUpdateE.setField_value3(null);
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														System.out.println("ID Device: " + item.getId()  + "Error_code: " + words.get(1) + " - Device group: " + item.getId_device_group() + "- Id error: " + rowItemError.getId() );
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													
													ModelCampellScientificMeter3Entity dataModelCSM3 = serviceModelCSM3.setModelCampellScientificMeter3(line);
													dataModelCSM3.setId_device(item.getId());
													serviceModelCSM3.insertModelCampellScientificMeter3(dataModelCSM3);
													
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
//														System.out.println("e1: " + e);
														e.printStackTrace();  
													}
												}
											}
											
											break;
										
											
										case "model_campell_scientific_meter4": 
											ModelCampellScientificMeter4Service serviceModelCSM4 = new ModelCampellScientificMeter4Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// ReadPower
													if(!Lib.isBlank(words.get(4))) {
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0"));
														deviceUpdateE.setField_value1(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0"));
														
													} else {
														deviceUpdateE.setLast_updated(null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
													}
													
													// value 2
													deviceUpdateE.setField_value2(null);
													// value 3
													deviceUpdateE.setField_value3(null);
													
													deviceUpdateE.setId(item.getId());
													serviceD.updateLastUpdated(deviceUpdateE);
													
													// Insert alert
													if(Integer.parseInt(words.get(1)) > 0 && hours >= item.getStart_date_time() && hours <= item.getEnd_date_time() ){
														// Check error code
														BatchJobService service = new BatchJobService();
														ErrorEntity errorItem = new ErrorEntity();
														errorItem.setId_device_group(item.getId_device_group());
														errorItem.setError_code(words.get(1));
														ErrorEntity rowItemError = service.getErrorItem(errorItem);
														System.out.println("ID Device: " + item.getId()  + "Error_code: " + words.get(1) + " - Device group: " + item.getId_device_group() + "- Id error: " + rowItemError.getId() );
														if(rowItemError.getId() > 0) {
															AlertEntity alertItem = new AlertEntity();
															alertItem.setId_device(item.getId());
															alertItem.setStart_date(words.get(0).replace("'", ""));
															alertItem.setId_error(rowItemError.getId());
															boolean checkAlertExist = service.checkAlertExist(alertItem);
															if(!checkAlertExist && alertItem.getId_device() > 0) {
																// Insert alert
																service.insertAlert(alertItem);
															}
														}
													}
													
													ModelCampellScientificMeter4Entity dataModelCSM4 = serviceModelCSM4.setModelCampellScientificMeter4(line);
													dataModelCSM4.setId_device(item.getId());
													serviceModelCSM4.insertModelCampellScientificMeter4(dataModelCSM4);
													
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
//														System.out.println("e1: " + e);
														e.printStackTrace();  
													}
												}
											}
											
											break;
											
												
										}
										
										
										// Save to datalogger
										ModelDataloggerEntity dataloggerEntity = new ModelDataloggerEntity();
										dataloggerEntity.setId_device(item.getId());
										Date now = new Date();
										TimeZone.setDefault(TimeZone.getTimeZone("America/Los_Angeles"));
										SimpleDateFormat formatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								        TimeZone tzUTC = TimeZone.getTimeZone("UTC");
								        formatUTC.setTimeZone(tzUTC);
								        String sDateUTC = formatUTC.format(now);
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
										
									} else {
										// Set last update for datalogger 
										// DeviceEntity deviceObject = dataDevice.stream().filter(device -> "model_datalogger".equals(device.getDatatablename())).findAny().orElse(null);
										if(item != null && "model_datalogger".equals(item.getDatatablename()) ) {
											Date now = new Date();
											TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
											SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
											String CurrentDate = format.format(now);
											DeviceEntity deviceUpdateE = new DeviceEntity();
											deviceUpdateE.setLast_updated(CurrentDate);
											deviceUpdateE.setLast_value(null);
											deviceUpdateE.setId(item.getId());
											serviceD.updateLastUpdated(deviceUpdateE);
											
											// Save to datalogger
											ModelDataloggerEntity dataloggerEntity = new ModelDataloggerEntity();
											dataloggerEntity.setId_device(item.getId());
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
									}
									
								}
								
								fr.close(); // close
							}
							
							
						} else {
							// File not exits
							message = "\nSUCCESS\n";
						}
						
					} catch (IOException e) {
						message = "\nSUCCESS\n";
//						System.out.println("e2: " + e);
						// TODO Auto-generated catch block
//						System.out.println("e: " + e);
//						e.printStackTrace();
					}finally{}

				});
				message = "\nSUCCESS\n";
			} else {
//				message = "Mode type test " + mode + " not supported by this sample script.";
				message = "\nSUCCESS\n";
				
			}
			return message;

		} catch (Exception e) {
			message = "\nFAILURE!\n";
			// return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new
			// ResponseMessage(message));
			
			return message;
		}
	}
	
	
	@PostMapping("/test")
	@ResponseBody
	
	public String testJmeter(HttpServletRequest request, @RequestParam(name = "LOGFILE", required = false) MultipartFile files[],
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
			@RequestParam(name = "FILETIME", required = false) String filetime) {

		String message = " ";
		
		try {

//			System.out.println("-------------------------------start--------------------------------");
			String LOGFILEUPLOAD = "LOGFILEUPLOAD";
			List<String> fileNames = new ArrayList<>();

			if (mode.equals(LOGFILEUPLOAD) && files.length > 0) {
				Arrays.asList(files).stream().forEach(file -> {
					String fileName = file.getOriginalFilename();
					String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
					fileNames.add(file.getOriginalFilename());

					Path root = Paths.get(
							Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey));
					String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
					String unique = UUID.randomUUID().toString();
					
					byte[] bytes;
					try {
						bytes = file.getBytes();
						switch (ext) {
						case "gz":

							Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
									Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice + "-"+ unique + "." + timeStamp
									+ ".log.gz");
							Files.write(path, bytes);

							InputStream fis = file.getInputStream();
							GZIPInputStream gis = new GZIPInputStream(fis);

							fileName = "bm-" + modbusdevice + "-"+ unique + "." + timeStamp + ".log";
							FileOutputStream fos = new FileOutputStream(root.resolve(fileName).toString());
							byte[] buffer = new byte[100000];
							int len = 0;
							while ((len = gis.read(buffer)) != -1) {
								fos.write(buffer, 0, len);
							}
							// close resources
							fos.close();
							gis.close();
							break;
						case "log":
							// code block
							Path pathLogUplad = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
									Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice + "-"+ unique + "." + timeStamp
									+ ".log");
							Files.write(pathLogUplad, bytes);
							fileName = "bm-" + modbusdevice + "-"+ unique + "." + timeStamp + ".log";
							break;
						}

						boolean exists = new File(root.resolve(fileName).toString()).isFile();
						
						// Get list device by SERIALNUMBER
						if (!serialnumber.isEmpty() && exists) {
							File readFile = new File(root.resolve(fileName).toString());
							FileReader fr = new FileReader(readFile); // reads the file
							BufferedReader br = new BufferedReader(fr); // creates a buffering character input stream
							StringBuffer sb = new StringBuffer(); // constructs a string buffer with no characters
							

							DeviceService serviceD = new DeviceService();
							DeviceEntity deviceE = new DeviceEntity();
							deviceE.setSerial_number(serialnumber);
							List<DeviceEntity> dataDevice = serviceD.getDeviceListBySerialNumber(deviceE);
							
							String remoteAddr = null;
							String line;
					        if (request != null) {
					            remoteAddr = request.getHeader("X-FORWARDED-FOR");
					            if (remoteAddr == null || "".equals(remoteAddr)) {
					                remoteAddr = request.getRemoteAddr();
					            }
					        }
					        
							
							if (dataDevice.size() > 0) {
								ModelShark100TestService serviceModelShark100 = new ModelShark100TestService();
								for (int i = 0; i < dataDevice.size(); i++) {
									DeviceEntity item = dataDevice.get(i);
									if(item.getModbusdevicenumber() == modbusdevice) {
										
										switch (item.getDatatablename()) {
										case "model_shark100_test":
											ModelShark100TestEntity dataModelShark100 = new ModelShark100TestEntity();
											dataModelShark100.setId_device(item.getId());
											dataModelShark100.setIp_address(remoteAddr);
											// Check insert database status
											
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													dataModelShark100.setTime(words.get(0).replace("'", ""));
													dataModelShark100.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
													dataModelShark100.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
													dataModelShark100.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
													dataModelShark100.setVolts_a_n(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0"));
													dataModelShark100.setVolts_b_n(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0"));
													dataModelShark100.setVolts_c_n(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0"));
													dataModelShark100.setVolts_a_b(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0"));
													dataModelShark100.setVolts_b_c(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0"));
													dataModelShark100.setVolts_c_a(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0"));
													dataModelShark100.setAmps_a(Float.parseFloat(!Lib.isBlank(words.get(10)) ? words.get(10) : "0"));
													
													dataModelShark100.setAmps_b(Float.parseFloat(!Lib.isBlank(words.get(11)) ? words.get(11) : "0"));
													dataModelShark100.setAmps_c(Float.parseFloat(!Lib.isBlank(words.get(12)) ? words.get(12) : "0"));
													dataModelShark100.setWatts_3ph_total(Float.parseFloat(!Lib.isBlank(words.get(13)) ? words.get(13) : "0"));
													dataModelShark100.setVars_3ph_total(Float.parseFloat(!Lib.isBlank(words.get(14)) ? words.get(14) : "0"));
													dataModelShark100.setVas_3ph_total(Float.parseFloat(!Lib.isBlank(words.get(15)) ? words.get(15) : "0"));
													dataModelShark100.setPower_factor_3ph_total(Float.parseFloat(!Lib.isBlank(words.get(16)) ? words.get(16) : "0"));
													dataModelShark100.setFrequency(Float.parseFloat(!Lib.isBlank(words.get(17)) ? words.get(17) : "0"));
													dataModelShark100.setNeutral_current(Float.parseFloat(!Lib.isBlank(words.get(18)) ? words.get(18) : "0"));
													dataModelShark100.setW_hours_received(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0"));
													dataModelShark100.setW_hours_delivered(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0"));
													
													dataModelShark100.setW_hours_net(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0"));
													dataModelShark100.setW_hours_total(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0"));
													dataModelShark100.setVar_hours_positive(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0"));
													dataModelShark100.setVar_hours_negative(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0"));
													dataModelShark100.setVar_hours_net(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0"));
													dataModelShark100.setVar_hours_total(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0"));
													dataModelShark100.setVa_hours_total(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0"));
													dataModelShark100.setAmps_a_average(Float.parseFloat(!Lib.isBlank(words.get(28)) ? words.get(28) : "0"));
													dataModelShark100.setAmps_b_average(Float.parseFloat(!Lib.isBlank(words.get(29)) ? words.get(29) : "0"));
													dataModelShark100.setAmps_c_average(Float.parseFloat(!Lib.isBlank(words.get(30)) ? words.get(30) : "0"));
													
													dataModelShark100.setPositive_watts_3ph_average(Float.parseFloat(!Lib.isBlank(words.get(31)) ? words.get(31) : "0"));
													dataModelShark100.setPositive_vars_3ph_average(Float.parseFloat(!Lib.isBlank(words.get(32)) ? words.get(32) : "0"));
													dataModelShark100.setNegative_watts_3ph_average(Float.parseFloat(!Lib.isBlank(words.get(33)) ? words.get(33) : "0"));
													dataModelShark100.setNegative_vars_3ph_average(Float.parseFloat(!Lib.isBlank(words.get(34)) ? words.get(34) : "0"));
													dataModelShark100.setVas_3ph_average(Float.parseFloat(!Lib.isBlank(words.get(35)) ? words.get(35) : "0"));
													dataModelShark100.setPositive_pf_3ph_average(Float.parseFloat(!Lib.isBlank(words.get(36)) ? words.get(36) : "0"));
													dataModelShark100.setNegative_pf_3ph_average(Float.parseFloat(!Lib.isBlank(words.get(37)) ? words.get(37) : "0"));
													dataModelShark100.setVolts_a_n_min(Float.parseFloat(!Lib.isBlank(words.get(38)) ? words.get(38) : "0"));
													dataModelShark100.setVolts_b_n_min(Float.parseFloat(!Lib.isBlank(words.get(39)) ? words.get(39) : "0"));
													dataModelShark100.setVolts_c_n_min(Float.parseFloat(!Lib.isBlank(words.get(40)) ? words.get(40) : "0"));
													
													dataModelShark100.setVolts_a_b_min(Float.parseFloat(!Lib.isBlank(words.get(41)) ? words.get(41) : "0"));
													dataModelShark100.setVolts_b_c_min(Float.parseFloat(!Lib.isBlank(words.get(42)) ? words.get(42) : "0"));
													dataModelShark100.setVolts_c_a_min(Float.parseFloat(!Lib.isBlank(words.get(43)) ? words.get(43) : "0"));
													dataModelShark100.setAmps_a_min_avg_demand(Float.parseFloat(!Lib.isBlank(words.get(44)) ? words.get(44) : "0"));
													dataModelShark100.setAmps_b_min_avg_demand(Float.parseFloat(!Lib.isBlank(words.get(45)) ? words.get(45) : "0"));
													dataModelShark100.setAmps_c_min_avg_demand(Float.parseFloat(!Lib.isBlank(words.get(46)) ? words.get(46) : "0"));
													dataModelShark100.setPositive_watts_3ph_min_avg_demand(Float.parseFloat(!Lib.isBlank(words.get(47)) ? words.get(47) : "0"));
													dataModelShark100.setPositive_vars_3ph_min_avg_demand(Float.parseFloat(!Lib.isBlank(words.get(48)) ? words.get(48) : "0"));
													dataModelShark100.setNegative_watts_3ph_min_avg_demand(Float.parseFloat(!Lib.isBlank(words.get(49)) ? words.get(49) : "0"));
													dataModelShark100.setNegative_vars_3ph_min_avg_demand(Float.parseFloat(!Lib.isBlank(words.get(50)) ? words.get(50) : "0"));
													
													dataModelShark100.setVas_3ph_min_avg_demand(Float.parseFloat(!Lib.isBlank(words.get(51)) ? words.get(51) : "0"));
													dataModelShark100.setPositive_pf_3ph_min_avg_demand(Float.parseFloat(!Lib.isBlank(words.get(52)) ? words.get(52) : "0"));
													dataModelShark100.setNegative_pf_3ph_min_avg_demand(Float.parseFloat(!Lib.isBlank(words.get(53)) ? words.get(53) : "0"));
													dataModelShark100.setFrequency_min(Float.parseFloat(!Lib.isBlank(words.get(54)) ? words.get(54) : "0"));
													dataModelShark100.setVolts_a_n_max(Double.parseDouble(!Lib.isBlank(words.get(55)) ? words.get(55) : "0"));
													dataModelShark100.setVolts_b_n_max(Double.parseDouble(!Lib.isBlank(words.get(56)) ? words.get(56) : "0"));
													dataModelShark100.setVolts_c_n_max(Double.parseDouble(!Lib.isBlank(words.get(57)) ? words.get(57) : "0"));
													dataModelShark100.setVolts_a_b_max(Double.parseDouble(!Lib.isBlank(words.get(58)) ? words.get(58) : "0"));
													dataModelShark100.setVolts_b_c_max(Double.parseDouble(!Lib.isBlank(words.get(59)) ? words.get(59) : "0"));
													dataModelShark100.setVolts_c_a_max(Double.parseDouble(!Lib.isBlank(words.get(60)) ? words.get(60) : "0"));
													
													dataModelShark100.setAmps_a_max_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(61)) ? words.get(61) : "0"));
													dataModelShark100.setAmps_b_max_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(62)) ? words.get(62) : "0"));
													dataModelShark100.setAmps_c_max_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(63)) ? words.get(63) : "0"));
													dataModelShark100.setPositive_watts_3ph_max_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(64)) ? words.get(64) : "0"));
													dataModelShark100.setPositive_vars_3ph_max_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(65)) ? words.get(65) : "0"));
													dataModelShark100.setNegative_watts_3ph_max_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(66)) ? words.get(66) : "0"));
													dataModelShark100.setNegative_vars_3ph_max_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(67)) ? words.get(67) : "0"));
													dataModelShark100.setVas_3ph_max_avg_demand(Double.parseDouble(!Lib.isBlank(words.get(68)) ? words.get(68) : "0"));
													dataModelShark100.setPositive_pf_3ph_max_avg_demand(Float.parseFloat(!Lib.isBlank(words.get(69)) ? words.get(69) : "0"));
													dataModelShark100.setNegative_pf_3ph_max_avg_demand(Float.parseFloat(!Lib.isBlank(words.get(70)) ? words.get(70) : "0"));
													
													dataModelShark100.setFrequency_max(Float.parseFloat(!Lib.isBlank(words.get(71)) ? words.get(71) : "0"));
													dataModelShark100.setVolts_a_n_thd(Float.parseFloat(!Lib.isBlank(words.get(72)) ? words.get(72) : "0"));
													dataModelShark100.setVolts_b_n_thd(Float.parseFloat(!Lib.isBlank(words.get(73)) ? words.get(73) : "0"));
													dataModelShark100.setVolts_c_n_thd(Float.parseFloat(!Lib.isBlank(words.get(74)) ? words.get(74) : "0"));
													dataModelShark100.setAmps_a_thd(Float.parseFloat(!Lib.isBlank(words.get(75)) ? words.get(75) : "0"));
													dataModelShark100.setAmps_b_thd(Float.parseFloat(!Lib.isBlank(words.get(76)) ? words.get(76) : "0"));
													dataModelShark100.setAmps_c_thd(Float.parseFloat(!Lib.isBlank(words.get(77)) ? words.get(77) : "0"));
													dataModelShark100.setPhase_a_current_0th(Float.parseFloat(!Lib.isBlank(words.get(78)) ? words.get(78) : "0"));
													dataModelShark100.setPhase_a_current_1st(Float.parseFloat(!Lib.isBlank(words.get(79)) ? words.get(79) : "0"));
													dataModelShark100.setPhase_a_current_2nd(Float.parseFloat(!Lib.isBlank(words.get(80)) ? words.get(80) : "0"));
													
													dataModelShark100.setPhase_a_current_3rd(Float.parseFloat(!Lib.isBlank(words.get(81)) ? words.get(81) : "0"));
													dataModelShark100.setPhase_a_current_4th(Float.parseFloat(!Lib.isBlank(words.get(82)) ? words.get(82) : "0"));
													dataModelShark100.setPhase_a_current_5th(Float.parseFloat(!Lib.isBlank(words.get(83)) ? words.get(83) : "0"));
													dataModelShark100.setPhase_a_current_6th(Float.parseFloat(!Lib.isBlank(words.get(84)) ? words.get(84) : "0"));
													dataModelShark100.setPhase_a_current_7th(Float.parseFloat(!Lib.isBlank(words.get(85)) ? words.get(85) : "0"));
													dataModelShark100.setPhase_a_voltage_0th(Float.parseFloat(!Lib.isBlank(words.get(86)) ? words.get(86) : "0"));
													dataModelShark100.setPhase_a_voltage_1st(Float.parseFloat(!Lib.isBlank(words.get(87)) ? words.get(87) : "0"));
													dataModelShark100.setPhase_a_voltage_2nd(Float.parseFloat(!Lib.isBlank(words.get(88)) ? words.get(88) : "0"));
													dataModelShark100.setPhase_a_voltage_3rd(Float.parseFloat(!Lib.isBlank(words.get(89)) ? words.get(89) : "0"));
													dataModelShark100.setPhase_b_current_0th(Float.parseFloat(!Lib.isBlank(words.get(90)) ? words.get(90) : "0"));
													
													dataModelShark100.setPhase_b_current_1st(Float.parseFloat(!Lib.isBlank(words.get(91)) ? words.get(91) : "0"));
													dataModelShark100.setPhase_b_current_2nd(Float.parseFloat(!Lib.isBlank(words.get(92)) ? words.get(92) : "0"));
													dataModelShark100.setPhase_b_current_3rd(Float.parseFloat(!Lib.isBlank(words.get(93)) ? words.get(93) : "0"));
													dataModelShark100.setPhase_b_current_4th(Float.parseFloat(!Lib.isBlank(words.get(94)) ? words.get(94) : "0"));
													dataModelShark100.setPhase_b_current_5th(Float.parseFloat(!Lib.isBlank(words.get(95)) ? words.get(95) : "0"));
													dataModelShark100.setPhase_b_current_6th(Float.parseFloat(!Lib.isBlank(words.get(96)) ? words.get(96) : "0"));
													dataModelShark100.setPhase_b_current_7th(Float.parseFloat(!Lib.isBlank(words.get(97)) ? words.get(97) : "0"));
													dataModelShark100.setPhase_b_voltage_0th(Float.parseFloat(!Lib.isBlank(words.get(98)) ? words.get(98) : "0"));
													dataModelShark100.setPhase_b_voltage_1st(Float.parseFloat(!Lib.isBlank(words.get(99)) ? words.get(99) : "0"));
													dataModelShark100.setPhase_b_voltage_2nd(Float.parseFloat(!Lib.isBlank(words.get(100)) ? words.get(100) : "0"));
													
													dataModelShark100.setPhase_b_voltage_3rd(Float.parseFloat(!Lib.isBlank(words.get(101)) ? words.get(101) : "0"));
													dataModelShark100.setPhase_c_current_0th(Float.parseFloat(!Lib.isBlank(words.get(102)) ? words.get(102) : "0"));
													dataModelShark100.setPhase_c_current_1st(Float.parseFloat(!Lib.isBlank(words.get(103)) ? words.get(103) : "0"));
													dataModelShark100.setPhase_c_current_2nd(Float.parseFloat(!Lib.isBlank(words.get(104)) ? words.get(104) : "0"));
													dataModelShark100.setPhase_c_current_3rd(Float.parseFloat(!Lib.isBlank(words.get(105)) ? words.get(105) : "0"));
													dataModelShark100.setPhase_c_current_4th(Float.parseFloat(!Lib.isBlank(words.get(106)) ? words.get(106) : "0"));
													dataModelShark100.setPhase_c_current_5th(Float.parseFloat(!Lib.isBlank(words.get(107)) ? words.get(107) : "0"));
													dataModelShark100.setPhase_c_current_6th(Float.parseFloat(!Lib.isBlank(words.get(108)) ? words.get(108) : "0"));
													dataModelShark100.setPhase_c_current_7th(Float.parseFloat(!Lib.isBlank(words.get(109)) ? words.get(109) : "0"));
													dataModelShark100.setPhase_c_voltage_0th(Float.parseFloat(!Lib.isBlank(words.get(110)) ? words.get(110) : "0"));
													
													dataModelShark100.setPhase_c_voltage_1st(Float.parseFloat(!Lib.isBlank(words.get(111)) ? words.get(111) : "0"));
													dataModelShark100.setPhase_c_voltage_2nd(Float.parseFloat(!Lib.isBlank(words.get(112)) ? words.get(112) : "0"));
													dataModelShark100.setPhase_c_voltage_3rd(Float.parseFloat(!Lib.isBlank(words.get(113)) ? words.get(113) : "0"));
													dataModelShark100.setAngle_phase_a_current(Float.parseFloat(!Lib.isBlank(words.get(114)) ? words.get(114) : "0"));
													dataModelShark100.setAngle_phase_b_current(Float.parseFloat(!Lib.isBlank(words.get(115)) ? words.get(115) : "0"));
													dataModelShark100.setAngle_phase_c_current(Float.parseFloat(!Lib.isBlank(words.get(116)) ? words.get(116) : "0"));
													dataModelShark100.setAngle_volts_a_b(Float.parseFloat(!Lib.isBlank(words.get(117)) ? words.get(117) : "0"));
													dataModelShark100.setAngle_volts_b_c(Float.parseFloat(!Lib.isBlank(words.get(118)) ? words.get(118) : "0"));
													dataModelShark100.setAngle_volts_c_a(Float.parseFloat(!Lib.isBlank(words.get(119)) ? words.get(119) : "0"));
													
													serviceModelShark100.insertModelShark100Test(dataModelShark100);
													
													try  
													{ 
														File logFile = new File(root.resolve(fileName).toString());
														if(logFile.delete()){  
//															System.out.println(logFile.getName() + " deleted .log");  
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {  
//															System.out.println(logGzFile.getName() + " deleted .log.gz");   
														}		
													}  
													catch(Exception e){  
														e.printStackTrace();  
													}
													
												}
											}
											
											
											fr.close(); // closes the stream and release the resources
											br.close();
											
											break;
										
											
										}
									}
									
								}
							}
							
							
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				});
				message = "\nSUCCESS\n";
			} else {
				message = "Mode type test " + mode + " not supported by this sample script.";
				
			}
			
			return "true";

		} catch (Exception e) {
			message = "\nFAILURE!\n";
			// return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new
			// ResponseMessage(message));
			return message;
		}
	}
	
	

}