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
			
//			System.out.println("SENDDATATRACE: " + senddatatrace);
//			System.out.println("MODE: " + mode);
//			System.out.println("SERIALNUMBER: " + serialnumber);
//			System.out.println("PASSWORD: " + password);
//			System.out.println("LOOPNAME: " + loopname);
//			System.out.println("MODBUSIP: " + modbusip);
//			System.out.println("MODBUSPORT: " + modbusport);
//			System.out.println("MODBUSDEVICE: " + modbusdevice);
//			System.out.println("MODBUSDEVICENAME: " + modbusdevicename);
//			System.out.println("MODBUSDEVICETYPE: " + modbusdevicetype);
//			System.out.println("MODBUSDEVICETYPENUMBER: " + modbusdevicetypenumber);
//			System.out.println("MODBUSDEVICECLASS: " + modbusdeviceclass);
//			System.out.println("MD5CHECKSUM: " + md5checksum);
//			System.out.println("FILESIZE: " + filesize);
//			System.out.println("FILETIME: " + filetime);


			if (LOGFILEUPLOAD.equalsIgnoreCase(mode) && files != null && files.length > 0) {
				Arrays.asList(files).stream().forEach(file -> {
					String fileName = file.getOriginalFilename();
					
					String ext = "";
					if (fileName != null) {
						int lastDot = fileName.lastIndexOf('.');
						if (lastDot >= 0 && lastDot < fileName.length() - 1) {
							ext = fileName.substring(lastDot + 1);
						} else {
							ext = "log";
						}
					}
					
					fileNames.add(file.getOriginalFilename());
					

					Path root = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey));
					String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
					String unique = UUID.randomUUID().toString();
					byte[] bytes;
					try {
						bytes = file.getBytes();
						fileName = "bm-" + modbusdevice  + "-" + unique + "." + timeStamp + ".log";
						switch (ext) {
							case "gz":
								Path path = root.resolve(fileName.concat(".gz"));
								Files.write(path, bytes);
	
								InputStream fis = file.getInputStream();
								GZIPInputStream gis = new GZIPInputStream(fis);
	
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
								Path pathLogUplad = root.resolve(fileName);
								Files.write(pathLogUplad, bytes);
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
								
								for (int i = 0; i < dataDevice.size(); i++) {
									DeviceEntity item = dataDevice.get(i);
									ZoneId zoneIdLosAngeles = ZoneId.of(item.getTimezone_value()); // "America/Los_Angeles"
							        ZonedDateTime zdtNowLosAngeles = ZonedDateTime.now(zoneIdLosAngeles);
							        int hours = zdtNowLosAngeles.getHour();
							        
									if( modbusdevice.equals(item.getModbusdevicenumber())) {
										List<DeviceEntity> scaledDeviceParameters = serviceD.getListScaledDeviceParameter(item);
										DeviceEntity deviceUpdateE = new DeviceEntity();
										deviceUpdateE.setId(item.getId());
										
										switch (item.getDevice_group_table()) {

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
													ModelPVPowered3550260500kwInverterEntity dataEntity = serviceModelPVPowered.setModelPVPowered3550260KWInverter(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// OutputGeneration
													deviceUpdateE.setLast_value(dataEntity.getOutputGeneration() != 0.001 ? dataEntity.getOutputGeneration() : null);
													deviceUpdateE.setField_value1(dataEntity.getOutputGeneration() != 0.001 ? dataEntity.getOutputGeneration() : null);
													
													// DCInputVoltage
													deviceUpdateE.setField_value2(dataEntity.getDCInputVoltage() != 0.001 ? dataEntity.getDCInputVoltage() : null);
													
													// DCInputCurrent
													deviceUpdateE.setField_value3(dataEntity.getDCInputCurrent() != 0.001 ? dataEntity.getDCInputCurrent() : null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelPVPowered.insertModelPVPowered3550260KWInverter(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
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
													
													ModelShark100Entity dataEntity = serviceModelShark100.setModelShark100(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													dataEntity.setOffset_data_old(item.getOffset_data_old());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// watts_3ph_total
													deviceUpdateE.setLast_value(dataEntity.getWatts_3ph_total() != 0.001 ? dataEntity.getWatts_3ph_total() : null);
													deviceUpdateE.setField_value1(dataEntity.getWatts_3ph_total() != 0.001 ? dataEntity.getWatts_3ph_total() : null);
													
													// vars_3ph_total
													deviceUpdateE.setField_value2(dataEntity.getVars_3ph_total() != 0.001 ? dataEntity.getVars_3ph_total() : null);
													
													// vas_3ph_total
													deviceUpdateE.setField_value3(dataEntity.getVas_3ph_total() != 0.001 ? dataEntity.getVas_3ph_total() : null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelShark100.insertModelShark100(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
												}
											}
											
											
											break;
											
										case "model_shark100v1":
											ModelShark100v1Service serviceModelShark100v1 = new ModelShark100v1Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													
													ModelShark100v1Entity dataEntity = serviceModelShark100v1.setModelShark100v1(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													dataEntity.setOffset_data_old(item.getOffset_data_old());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// watts_3ph_total
													deviceUpdateE.setLast_value(dataEntity.getWatts_3ph_total() != 0.001 ? dataEntity.getWatts_3ph_total() : null);
													deviceUpdateE.setField_value1(dataEntity.getWatts_3ph_total() != 0.001 ? dataEntity.getWatts_3ph_total() : null);
													
													// vars_3ph_total
													deviceUpdateE.setField_value2(dataEntity.getVars_3ph_total() != 0.001 ? dataEntity.getVars_3ph_total() : null);
													
													// vas_3ph_total
													deviceUpdateE.setField_value3(dataEntity.getVas_3ph_total() != 0.001 ? dataEntity.getVas_3ph_total() : null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelShark100v1.insertModelShark100v1(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
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
													ModelRT1Class30000Entity dataEntity = serviceModelRT1Class30000.setModelRT1Class30000(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// sensor1_data
													deviceUpdateE.setLast_value(dataEntity.getSensor1_data() != 0.001 ? dataEntity.getSensor1_data() : null);
													deviceUpdateE.setField_value1(dataEntity.getSensor1_data() != 0.001 ? dataEntity.getSensor1_data() : null);
													
													// panel_temperature
													deviceUpdateE.setField_value2(dataEntity.getPanel_temperature() != 0.001 ? dataEntity.getPanel_temperature() : null);
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelRT1Class30000.insertModelRT1Class30000(dataEntity);
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
													
													ModelKippZonenRT1Class8009Entity dataEntity = serviceModelKippzonen.setModelKippZonenRT1Class8009(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// Sensor1_data
													deviceUpdateE.setLast_value(dataEntity.getSensor1_data() != 0.001 ? dataEntity.getSensor1_data() : null);
													deviceUpdateE.setField_value1(dataEntity.getSensor1_data() != 0.001 ? dataEntity.getSensor1_data() : null);
													
													// panel_temperature
													deviceUpdateE.setField_value2(dataEntity.getPanel_temperature() != 0.001 ? dataEntity.getPanel_temperature() : null);
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelKippzonen.insertModelKippZonenRT1Class8009(dataEntity);
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
													
													ModelIVTSolaronEXTEntity dataEntity = serviceModelIVTSolaronEXT.setModelIVTSolaronEXT(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// ac_power
													deviceUpdateE.setLast_value(dataEntity.getAc_power() != 0.001 ? dataEntity.getAc_power() : null);
													deviceUpdateE.setField_value1(dataEntity.getAc_power() != 0.001 ? dataEntity.getAc_power() : null);
													
													// ac_frequency
													deviceUpdateE.setField_value2(dataEntity.getAc_frequency() != 0.001 ? dataEntity.getAc_frequency() : null);
													
													// pv_voltage
													deviceUpdateE.setField_value3(dataEntity.getPv_voltage() != 0.001 ? dataEntity.getPv_voltage() : null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelIVTSolaronEXT.insertModelIVTSolaronEXT(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
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
													ModelHukselfluxSr30d1DeviceclassV0Entity dataEntity = serviceModelHukselfluxSr30d1DeviceclassV0.setModelHukselfluxSr30d1DeviceclassV0(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// IrradianceTcs
													deviceUpdateE.setLast_value(dataEntity.getIrradianceTcs() != 0.001 ? dataEntity.getIrradianceTcs() : null);
													deviceUpdateE.setField_value1(dataEntity.getIrradianceTcs() != 0.001 ? dataEntity.getIrradianceTcs() : null);
													
													// SensorBodyTemperature
													deviceUpdateE.setField_value2(dataEntity.getSensorBodyTemperature() != 0.001 ? dataEntity.getSensorBodyTemperature() : null);
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelHukselfluxSr30d1DeviceclassV0.insertModelHukselfluxSr30d1DeviceclassV0(dataEntity);
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
													ModelIMTSolarClass8000Entity dataEntity = serviceModelIMTSolarClass8000.setModelIMTSolarClass8000(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

													// irradiance
													deviceUpdateE.setLast_value(dataEntity.getIrradiance() != 0.001 ? dataEntity.getIrradiance() : null);
													deviceUpdateE.setField_value1(dataEntity.getIrradiance() != 0.001 ? dataEntity.getIrradiance() : null);
													
													// tcell
													deviceUpdateE.setField_value2(dataEntity.getTcell() != 0.001 ? dataEntity.getTcell() : null);
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelIMTSolarClass8000.insertModelIMTSolarClass8000(dataEntity);
												}
											}
											
											break;
											
										case "model_imtsolar_tv_class8004":
											ModelIMTSolarTvClass8004Service serviceModelIMT = new ModelIMTSolarTvClass8004Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													ModelIMTSolarTvClass8004Entity dataEntity = serviceModelIMT.setModelIMTSolarTvClass8004(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

													// irradiance
													deviceUpdateE.setLast_value(dataEntity.getIrradiance() != 0.001 ? dataEntity.getIrradiance() : null);
													deviceUpdateE.setField_value1(dataEntity.getIrradiance() != 0.001 ? dataEntity.getIrradiance() : null);
													
													// tcell
													deviceUpdateE.setField_value2(dataEntity.getTcell() != 0.001 ? dataEntity.getTcell() : null);
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelIMT.insertModelIMTSolarTvClass8004(dataEntity);
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
													
													ModelIMTSolarTmodulClass8006Entity dataEntity = serviceModelIMTSolarTmodulClass8006.setDataModelIMTSolarTmodulClass8006(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// ModuleTemperature
													deviceUpdateE.setLast_value(dataEntity.getModuleTemperature() != 0.001 ? dataEntity.getModuleTemperature() : null);
													deviceUpdateE.setField_value1(dataEntity.getModuleTemperature() != 0.001 ? dataEntity.getModuleTemperature() : null);
													
													// value 2
													deviceUpdateE.setField_value2(null);
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelIMTSolarTmodulClass8006.insertModelIMTSolarTmodulClass8006(dataEntity);
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
													ModelAdvancedEnergySolaronEntity dataEntity = serviceModelAdvancedEnergySolaron.setModelAdvancedEnergySolaron(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													dataEntity.setOffset_data_old(item.getOffset_data_old());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// ac_power
													deviceUpdateE.setLast_value(dataEntity.getAc_power() != 0.001 ? dataEntity.getAc_power() : null);
													deviceUpdateE.setField_value1(dataEntity.getAc_power() != 0.001 ? dataEntity.getAc_power() : null);
													
													// ac_frequency
													deviceUpdateE.setField_value2(dataEntity.getAc_frequency() != 0.001 ? dataEntity.getAc_frequency() : null);
													
													// pv_voltage
													deviceUpdateE.setField_value3(dataEntity.getPv_voltage() != 0.001 ? dataEntity.getPv_voltage() : null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelAdvancedEnergySolaron.insertModelAdvancedEnergySolaron(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
												}
											}
											
											break;
											
										case "model_pvmet_100":
											ModelPVMet100Service servicePVMet100 = new ModelPVMet100Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													
													ModelPVMet100Entity dataEntity = servicePVMet100.setModelPVMet100(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// Irradiance
													deviceUpdateE.setLast_value(dataEntity.getTransientHorizontalIrradiance() != 0.001 ? dataEntity.getTransientHorizontalIrradiance() : null);
													deviceUpdateE.setField_value1(dataEntity.getTransientHorizontalIrradiance() != 0.001 ? dataEntity.getTransientHorizontalIrradiance() : null);
													
													// Ambient Temperature
													deviceUpdateE.setField_value2(dataEntity.getAmbientTemperature() != 0.001 ? dataEntity.getAmbientTemperature() : null);
													
													// PV Temperature Module
													deviceUpdateE.setField_value3(dataEntity.getTemperature() != 0.001 ? dataEntity.getTemperature() : null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													servicePVMet100.insertModelPVMet100(dataEntity);
												}
											}
											
											
											break;
											
										case "model_pvmet_200":
											ModelPVMet200Service servicePVMet200 = new ModelPVMet200Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													
													ModelPVMet200Entity dataEntity = servicePVMet200.setModelPVMet200(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// Irradiance
													deviceUpdateE.setLast_value(dataEntity.getPOA_Irradiance() != 0.001 ? dataEntity.getPOA_Irradiance() : null);
													deviceUpdateE.setField_value1(dataEntity.getPOA_Irradiance() != 0.001 ? dataEntity.getPOA_Irradiance() : null);
													
													// Ambient Temperature
													deviceUpdateE.setField_value2(dataEntity.getAmbient_Air_Temperature() != 0.001 ? dataEntity.getAmbient_Air_Temperature() : null);
													
													// PV Temperature Module
													deviceUpdateE.setField_value3(dataEntity.getBOM_Temp_1() != 0.001 ? dataEntity.getBOM_Temp_1() : null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													servicePVMet200.insertModelPVMet200(dataEntity);
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
													ModelPVPInverterEntity dataEntity = serviceModelPVPInverter.setModelPVPInverter(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

													// line_kw
													deviceUpdateE.setLast_value(dataEntity.getLine_kw() != 0.001 ? dataEntity.getLine_kw() : null);
													deviceUpdateE.setField_value1(dataEntity.getLine_kw() != 0.001 ? dataEntity.getLine_kw() : null);
													
													// dc_output_voltage
													deviceUpdateE.setField_value2(dataEntity.getDc_output_voltage() != 0.001 ? dataEntity.getDc_output_voltage() : null);
													
													// dc_output_current
													deviceUpdateE.setField_value3(dataEntity.getDc_output_current() != 0.001 ? dataEntity.getDc_output_current() : null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelPVPInverter.insertModelPVPInverter(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
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
													ModelChintSolectriaInverterClass9725Entity dataEntity = serviceModelChintSolectria.setModelChintSolectriaInverterClass9725(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// AC_ActivePower
													deviceUpdateE.setLast_value(dataEntity.getAC_ActivePower() != 0.001 ? dataEntity.getAC_ActivePower() : null);
													deviceUpdateE.setField_value1(dataEntity.getAC_ActivePower() != 0.001 ? dataEntity.getAC_ActivePower() : null);
													
													// AC_ApparentPower
													deviceUpdateE.setField_value2(dataEntity.getAC_ApparentPower() != 0.001 ? dataEntity.getAC_ApparentPower() : null);
													
													// PV1_Voltage
													deviceUpdateE.setField_value3(dataEntity.getPV1_Voltage() != 0.001 ? dataEntity.getPV1_Voltage() : null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelChintSolectria.insertModelChintSolectriaInverterClass9725(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
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
													ModelVerisIndustriesE51c2PowerMeterEntity dataEntity = serviceModelVeris.setModelChintSolectriaInverterClass9725(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													dataEntity.setOffset_data_old(item.getOffset_data_old());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// TotalNetInstantaneousRealPower
													deviceUpdateE.setLast_value(dataEntity.getTotalNetInstantaneousRealPower() != 0.001 ? dataEntity.getTotalNetInstantaneousRealPower() : null);
													deviceUpdateE.setField_value1(dataEntity.getTotalNetInstantaneousRealPower() != 0.001 ? dataEntity.getTotalNetInstantaneousRealPower() : null);
													
													// RealPowerPhaseA
													deviceUpdateE.setField_value2(dataEntity.getRealPowerPhaseA() != 0.001 ? dataEntity.getRealPowerPhaseA() : null);
													
													// RealPowerPhaseB
													deviceUpdateE.setField_value3(dataEntity.getRealPowerPhaseB() != 0.001 ? dataEntity.getRealPowerPhaseB() : null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelVeris.insertModelVerisIndustriesE51c2PowerMeter(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
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
													ModelSatconPvs357InverterEntity dataEntity = serviceModelSatcon.setModelSatconPvs357Inverter(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													//Output_kw
													deviceUpdateE.setLast_value(dataEntity.getOutput_kw() != 0.001 ? dataEntity.getOutput_kw() : null);
													deviceUpdateE.setField_value1(dataEntity.getOutput_kw() != 0.001 ? dataEntity.getOutput_kw() : null);
													
													// Input_kW
													deviceUpdateE.setField_value2(dataEntity.getInput_kW() != 0.001 ? dataEntity.getInput_kW() : null);
													
													// DC_Input_Volts
													deviceUpdateE.setField_value3(dataEntity.getDC_Input_Volts() != 0.001 ? dataEntity.getDC_Input_Volts() : null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelSatcon.insertModelSatconPvs357Inverter(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
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
													ModelElkorWattsonPVMeterEntity dataEntity = serviceModelElkor.setModelElkorWattsonPVMeter(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													dataEntity.setOffset_data_old(item.getOffset_data_old());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// TotalRealPower
													deviceUpdateE.setLast_value(dataEntity.getTotalRealPower() != 0.001 ? dataEntity.getTotalRealPower() : null);
													deviceUpdateE.setField_value1(dataEntity.getTotalRealPower() != 0.001 ? dataEntity.getTotalRealPower() : null);
													
													// TotalReactivePower
													deviceUpdateE.setField_value2(dataEntity.getTotalReactivePower() != 0.001 ? dataEntity.getTotalReactivePower() : null);
													
													// TotalApparentPower
													deviceUpdateE.setField_value3(dataEntity.getTotalApparentPower() != 0.001 ? dataEntity.getTotalApparentPower() : null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelElkor.insertModelElkorWattsonPVMeter(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
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
													ModelWKippZonenRT1Entity dataEntity = serviceModelWkipp.setModelWKippZonenRT1(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

													// SunPOATempComp
													deviceUpdateE.setLast_value(dataEntity.getSunPOATempComp() != 0.001 ? dataEntity.getSunPOATempComp() : null);
													deviceUpdateE.setField_value1(dataEntity.getSunPOATempComp() != 0.001 ? dataEntity.getSunPOATempComp() : null);

													// PanelTemperature
													deviceUpdateE.setField_value2(dataEntity.getPanelTemperature() != 0.001 ? dataEntity.getPanelTemperature() : null);
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelWkipp.insertModelWKippZonenRT1(dataEntity);
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
													ModelElkorProductionMeterEntity dataEntity = serviceModelElkorP.setModelElkorProductionMeter(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													dataEntity.setOffset_data_old(item.getOffset_data_old());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// ActivePowerTotal
													deviceUpdateE.setLast_value(dataEntity.getActivePowerTotal() != 0.001 ? dataEntity.getActivePowerTotal() : null);
													deviceUpdateE.setField_value1(dataEntity.getActivePowerTotal() != 0.001 ? dataEntity.getActivePowerTotal() : null);
													
													// VoltageA
													deviceUpdateE.setField_value2(dataEntity.getVoltageA() != 0.001 ? dataEntity.getVoltageA() : null);
													
													// VoltageB
													deviceUpdateE.setField_value3(dataEntity.getVoltageB() != 0.001 ? dataEntity.getVoltageB() : null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelElkorP.insertModelElkorProductionMeter(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
												}
											}
											
											break;
											
										case "model_elkor_production_meterv1":
											ModelElkorProductionMeterv1Service serviceModelElkorPv1 = new ModelElkorProductionMeterv1Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													ModelElkorProductionMeterv1Entity dataEntity = serviceModelElkorPv1.setModelElkorProductionMeterv1(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													dataEntity.setOffset_data_old(item.getOffset_data_old());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

													// ActivePowerTotal
													deviceUpdateE.setLast_value(dataEntity.getActivePowerTotal() != 0.001 ? dataEntity.getActivePowerTotal() : null);
													deviceUpdateE.setField_value1(dataEntity.getActivePowerTotal() != 0.001 ? dataEntity.getActivePowerTotal() : null);
													
													// VoltageA
													deviceUpdateE.setField_value2(dataEntity.getVoltageA() != 0.001 ? dataEntity.getVoltageA() : null);
													
													// VoltageB
													deviceUpdateE.setField_value3(dataEntity.getVoltageB() != 0.001 ? dataEntity.getVoltageB() : null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelElkorPv1.insertModelElkorProductionMeterv1(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
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
													ModelAbbTrioClass6210Entity dataEntity = serviceModelABB.setModelAbbTrioClass6210(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// GridPower
													deviceUpdateE.setLast_value(dataEntity.getGridPower() != 0.001 ? dataEntity.getGridPower() : null);
													deviceUpdateE.setField_value1(dataEntity.getGridPower() != 0.001 ? dataEntity.getGridPower() : null);
													
													// Input1Voltage
													deviceUpdateE.setField_value2(dataEntity.getInput1Voltage() != 0.001 ? dataEntity.getInput1Voltage() : null);
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													 
													serviceModelABB.insertModelAbbTrioClass6210(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
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
													ModelLufftClass8020Entity dataEntity = serviceModelLufft.setModelLufftClass8020(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

													// AirTemperatureActual
													deviceUpdateE.setLast_value(dataEntity.getAirTemperatureActual() != 0.001 ? dataEntity.getAirTemperatureActual() : null);
													deviceUpdateE.setField_value1(dataEntity.getAirTemperatureActual() != 0.001 ? dataEntity.getAirTemperatureActual() : null);
													
													// IrradianceActual
													deviceUpdateE.setField_value2(dataEntity.getIrradianceActual() != 0.001 ? dataEntity.getIrradianceActual() : null);
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelLufft.insertModelLufftClass8020(dataEntity);
												}
											}
											
											break;
											
											// Model sensor
											case "model_sth01_temp_sensor":
												ModelSth01TempSensorService serviceSth01TempSensor = new ModelSth01TempSensorService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelSth01TempSensorEntity dataEntity = serviceSth01TempSensor.setModelSth01TempSensor(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// TEMPRATURE
														deviceUpdateE.setLast_value(dataEntity.getTEMPRATURE() != 0.001 ? dataEntity.getTEMPRATURE() : null);
														deviceUpdateE.setField_value1(dataEntity.getTEMPRATURE() != 0.001 ? dataEntity.getTEMPRATURE() : null);
														
														// value 2
														deviceUpdateE.setField_value2(null);
														
														// value 3
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceSth01TempSensor.insertModelSth01TempSensor(dataEntity);
													}
												}
												
												break;
											
										// Model weather station
										case "model_lufft_ws501_umb_weather":
											ModelLufftWS501UMBWeatherService serviceModelLufftWS501 = new ModelLufftWS501UMBWeatherService();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													ModelLufftWS501UMBWeatherEntity dataEntity = serviceModelLufftWS501.setModelLufftWS501UMBWeather(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

													// AirTemperatureActual
													deviceUpdateE.setLast_value(dataEntity.getAirTemperatureCActual() != 0.001 ? dataEntity.getAirTemperatureCActual() : null);
													deviceUpdateE.setField_value1(dataEntity.getAirTemperatureCActual() != 0.001 ? dataEntity.getAirTemperatureCActual() : null);
													
													// GlobalRadiation
													deviceUpdateE.setField_value2(dataEntity.getGlobalRadiation() != 0.001 ? dataEntity.getGlobalRadiation() : null);
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelLufftWS501.insertModelLufftWS501UMBWeather(dataEntity);
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
													ModelSolectriaSGI226IVTEntity dataEntity = serviceModelSolectriaSGI226IVT.setModelSolectriaSGI226IVT(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// ACPowerOutput
													deviceUpdateE.setLast_value(dataEntity.getACPowerOutput() != 0.001 ? dataEntity.getACPowerOutput() : null);
													deviceUpdateE.setField_value1(dataEntity.getACPowerOutput() != 0.001 ? dataEntity.getACPowerOutput() : null);
													
													// DCVoltage
													deviceUpdateE.setField_value2(dataEntity.getDCVoltage() != 0.001 ? dataEntity.getDCVoltage() : null);
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelSolectriaSGI226IVT.insertModelSolectriaSGI226IVT(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
												}
											}
											
											
											break;
											
										case "model_solectria_inv_00_slc_3146":
											ModelSolectriaINV00SLC3146Service serviceModelSolectriaINV00SLC3146 = new ModelSolectriaINV00SLC3146Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													ModelSolectriaINV00SLC3146Entity dataEntity = serviceModelSolectriaINV00SLC3146.setModelSolectriaINV00SLC3146(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// ACPowerOutput
													deviceUpdateE.setLast_value(dataEntity.getRealACPower() != 0.001 ? dataEntity.getRealACPower() : null);
													deviceUpdateE.setField_value1(dataEntity.getRealACPower() != 0.001 ? dataEntity.getRealACPower() : null);
													
													// DCVoltage
													deviceUpdateE.setField_value2(dataEntity.getDCVoltage() != 0.001 ? dataEntity.getDCVoltage() : null);
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelSolectriaINV00SLC3146.insertModelSolectriaINV00SLC3146(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
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
													ModelTTiTrackerEntity dataEntity = serviceModelTTiTracker.setModelTTiTracker(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													dataEntity.setEnable_alert(item.getEnable_alert());
													
													// ReadAngle
													deviceUpdateE.setLast_value(dataEntity.getSetAngle() != 0.001 ? dataEntity.getSetAngle() : null);
													deviceUpdateE.setField_value1(dataEntity.getSetAngle() != 0.001 ? dataEntity.getSetAngle() : null);
													
													// WindSpeed
													deviceUpdateE.setField_value2(dataEntity.getWindSpeed() != 0.001 ? dataEntity.getWindSpeed() : null);
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelTTiTracker.insertModelTTiTracker(dataEntity);
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
													ModelSolarEdgeInverterEntity dataEntity = serviceModelSET.setModelSolarEdgeInverter(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// I_AC_Power
													deviceUpdateE.setLast_value(dataEntity.getI_AC_Power() != 0.001 ? dataEntity.getI_AC_Power() : null);
													deviceUpdateE.setField_value1(dataEntity.getI_AC_Power() != 0.001 ? dataEntity.getI_AC_Power() : null);
													
													// value 2
													deviceUpdateE.setField_value2(null);
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelSET.insertModelSolarEdgeInverter(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
												}
											}
											
											break;
											
										case "model_solaredge_inverter_v1":
											ModelSolarEdgeInverterV1Service serviceModelSETV1 = new ModelSolarEdgeInverterV1Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													ModelSolarEdgeInverterV1Entity dataEntity = serviceModelSETV1.setModelSolarEdgeInverterV1(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// I_AC_Power
													deviceUpdateE.setLast_value(dataEntity.getI_AC_Power() != 0.001 ? dataEntity.getI_AC_Power() : null);
													deviceUpdateE.setField_value1(dataEntity.getI_AC_Power() != 0.001 ? dataEntity.getI_AC_Power() : null);
													
													// value 2
													deviceUpdateE.setField_value2(null);
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelSETV1.insertModelSolarEdgeInverterV1(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
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
													ModelXantrexGT100250500Entity dataEntity = serviceModelXantrex.setModelXantrexGT100250500(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

													// ReadPower
													deviceUpdateE.setLast_value(dataEntity.getReadPower() != 0.001 ? dataEntity.getReadPower() : null);
													deviceUpdateE.setField_value1(dataEntity.getReadPower() != 0.001 ? dataEntity.getReadPower() : null);
													
													// PVVoltage
													deviceUpdateE.setField_value2(dataEntity.getPVVoltage() != 0.001 ? dataEntity.getPVVoltage() : null);
													
													// PVCurrent
													deviceUpdateE.setField_value3(dataEntity.getPVCurrent() != 0.001 ? dataEntity.getPVCurrent() : null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelXantrex.insertModelXantrexGT100250500(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
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
													ModelAdam4017WSClass8110Nelis190Entity dataEntity = serviceModelAdam4017.setModelAdam4017WSClass8110Nelis190(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

													// POACMP11
													deviceUpdateE.setLast_value(dataEntity.getPOACMP11() != 0.001 ? dataEntity.getPOACMP11() : null);
													deviceUpdateE.setField_value1(dataEntity.getPOACMP11() != 0.001 ? dataEntity.getPOACMP11() : null);
													
													// AmbientTemp
													deviceUpdateE.setField_value2(dataEntity.getAmbientTemp() != 0.001 ? dataEntity.getAmbientTemp() : null);
													
													// value 3
													deviceUpdateE.setField_value3(null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelAdam4017.inserModelAdam4017WSClass8110Nelis190(dataEntity);
												}
											}
											
											break;
											
											
										case "model_campell_scientific_meter1": 
											ModelCampellScientificMeter1Service serviceModelCSM1 = new ModelCampellScientificMeter1Service();

											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													ModelCampellScientificMeter1Entity dataEntity = serviceModelCSM1.setModelCampellScientificMeter1(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													dataEntity.setOffset_data_old(item.getOffset_data_old());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// Meter1_ACPower
													deviceUpdateE.setLast_value(dataEntity.getMeter1_ACPower() != 0.001 ? dataEntity.getMeter1_ACPower() : null);
													deviceUpdateE.setField_value1(dataEntity.getMeter1_ACPower() != 0.001 ? dataEntity.getMeter1_ACPower() : null);
													
													// value 2
													deviceUpdateE.setField_value2(null);
													// value 3
													deviceUpdateE.setField_value3(null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelCSM1.insertModelCampellScientificMeter1(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
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
													ModelCampellScientificMeter2Entity dataEntity = serviceModelCSM2.setModelCampellScientificMeter2(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													dataEntity.setOffset_data_old(item.getOffset_data_old());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

													// Meter2_ACPower
													deviceUpdateE.setLast_value(dataEntity.getMeter2_ACPower() != 0.001 ? dataEntity.getMeter2_ACPower() : null);
													deviceUpdateE.setField_value1(dataEntity.getMeter2_ACPower() != 0.001 ? dataEntity.getMeter2_ACPower() : null);
													
													// value 2
													deviceUpdateE.setField_value2(null);
													// value 3
													deviceUpdateE.setField_value3(null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelCSM2.insertModelCampellScientificMeter2(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
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
													ModelCampellScientificMeter3Entity dataEntity = serviceModelCSM3.setModelCampellScientificMeter3(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													dataEntity.setOffset_data_old(item.getOffset_data_old());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// Meter3_ACPower
													deviceUpdateE.setLast_value(dataEntity.getMeter3_ACPower() != 0.001 ? dataEntity.getMeter3_ACPower() : null);
													deviceUpdateE.setField_value1(dataEntity.getMeter3_ACPower() != 0.001 ? dataEntity.getMeter3_ACPower() : null);
													
													// value 2
													deviceUpdateE.setField_value2(null);
													// value 3
													deviceUpdateE.setField_value3(null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelCSM3.insertModelCampellScientificMeter3(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
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
													ModelCampellScientificMeter4Entity dataEntity = serviceModelCSM4.setModelCampellScientificMeter4(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													dataEntity.setOffset_data_old(item.getOffset_data_old());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
													
													// Meter4_ACPower
													deviceUpdateE.setLast_value(dataEntity.getMeter4_ACPower() != 0.001 ? dataEntity.getMeter4_ACPower() : null);
													deviceUpdateE.setField_value1(dataEntity.getMeter4_ACPower() != 0.001 ? dataEntity.getMeter4_ACPower() : null);
													
													// value 2
													deviceUpdateE.setField_value2(null);
													// value 3
													deviceUpdateE.setField_value3(null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelCSM4.insertModelCampellScientificMeter4(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
												}
											}
											
											break;
											
											
										case "model_satcon_powergate_225_inverter": 
											ModelSatconPowergate225InverterService serviceModelSatcon225 = new ModelSatconPowergate225InverterService();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													ModelSatconPowergate225InverterEntity dataEntity = serviceModelSatcon225.setModelSatconPowergate225Inverter(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

													// OutputKW
													deviceUpdateE.setLast_value(dataEntity.getOutputKW() != 0.001 ? dataEntity.getOutputKW() : null);
													deviceUpdateE.setField_value1(dataEntity.getOutputKW() != 0.001 ? dataEntity.getOutputKW() : null);
													
													// Line Freq
													deviceUpdateE.setField_value2(dataEntity.getLineFreq() != 0.001 ? dataEntity.getLineFreq() : null);
													
													// DC Input Voltage
													deviceUpdateE.setField_value3(dataEntity.getDCInputVoltage() != 0.001 ? dataEntity.getDCInputVoltage() : null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelSatcon225.insertModelSatconPowergate225Inverter(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
												}
											}
											
											break;



										case "model_sunny_central_class9775_inverter": 
											ModelSunnyCentralClass9775InverterService serviceModelSunnyClass9775 = new ModelSunnyCentralClass9775InverterService();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													ModelSunnyCentralClass9775InverterEntity dataEntity = serviceModelSunnyClass9775.setModelSunnyCentralClass9775Inverter(line);
													dataEntity.setId_device(item.getId());
													dataEntity.setDatatablename(item.getDatatablename());
													dataEntity.setView_tablename(item.getView_tablename());
													dataEntity.setJob_tablename(item.getJob_tablename());
													
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

													// ACPower
													deviceUpdateE.setLast_value(dataEntity.getACPower() != 0.001 ? dataEntity.getACPower() : null);
													deviceUpdateE.setField_value1(dataEntity.getACPower() != 0.001 ? dataEntity.getACPower() : null);
													
													// ACVoltage
													deviceUpdateE.setField_value2(dataEntity.getACVoltage() != 0.001 ? dataEntity.getACVoltage() : null);
													
													// InteriorTemperature
													deviceUpdateE.setField_value3(dataEntity.getInteriorTemperature() != 0.001 ? dataEntity.getInteriorTemperature() : null);
													
													uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
													
													serviceModelSunnyClass9775.insertModelSunnyCentralClass9775Inverter(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
												}
											}
											
											break;
										
											case "model_veris_industries_e50c2a":
												ModelVerisIndustriesE50c2aService serviceModelVeris50c2a = new ModelVerisIndustriesE50c2aService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelVerisIndustriesE50c2aEntity dataEntity = serviceModelVeris50c2a.setModelVerisIndustriesE50c2a(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// TotalInstantaneousRealPower
														deviceUpdateE.setLast_value(dataEntity.getTotalInstantaneousRealPower() != 0.001 ? dataEntity.getTotalInstantaneousRealPower() : null);
														deviceUpdateE.setField_value1(dataEntity.getTotalInstantaneousRealPower() != 0.001 ? dataEntity.getTotalInstantaneousRealPower() : null);
														
														// RealPowerPhaseA
														deviceUpdateE.setField_value2(dataEntity.getRealPowerPhaseA() != 0.001 ? dataEntity.getRealPowerPhaseA() : null);
														
														// RealPowerPhaseB
														deviceUpdateE.setField_value3(dataEntity.getRealPowerPhaseB() != 0.001 ? dataEntity.getRealPowerPhaseB() : null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelVeris50c2a.insertModelVerisIndustriesE50c2a(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
											case "model_ae1000nx_class9644":
												ModelAE1000NXClass9644Service serviceModelAE1000NX = new ModelAE1000NXClass9644Service();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelAE1000NXClass9644Entity dataEntity = serviceModelAE1000NX.setModelAE1000NXClass9644(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// AC Power
														deviceUpdateE.setLast_value(dataEntity.getACPower() != 0.001 ? dataEntity.getACPower() : null);
														deviceUpdateE.setField_value1(dataEntity.getACPower() != 0.001 ? dataEntity.getACPower() : null);
														
														// AC Frequency
														deviceUpdateE.setField_value2(dataEntity.getACFrequency() != 0.001 ? dataEntity.getACFrequency() : null);
														
														// PV Voltage
														deviceUpdateE.setField_value3(dataEntity.getPVVoltage() != 0.001 ? dataEntity.getPVVoltage() : null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelAE1000NX.insertModelAE1000NXClass9644(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
												
											case "model_aes_tx_inverter":
												ModelAesTxInverterService serviceModelAesTx = new ModelAesTxInverterService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelAesTxInverterEntity dataEntity = serviceModelAesTx.setModelAesTxInverter(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

														// pt33
														deviceUpdateE.setLast_value(dataEntity.getPt33() != 0.001 ? dataEntity.getPt33() : null);
														deviceUpdateE.setField_value1(dataEntity.getPt33() != 0.001 ? dataEntity.getPt33() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelAesTx.insertModelAesTxInverter(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
												
												
											case "model_meter_ion_8600":
												ModelMeterIon8600Service serviceModelIon = new ModelMeterIon8600Service();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelMeterIon8600Entity dataEntity = serviceModelIon.setModelMeterIon8600(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// kWTot
														deviceUpdateE.setLast_value(dataEntity.getKWTot() != 0.001 ? dataEntity.getKWTot() : null);
														deviceUpdateE.setField_value1(dataEntity.getKWTot() != 0.001 ? dataEntity.getKWTot() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelIon.insertModelMeterIon8600(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
											case "model_meter_ion_8600v1":
												ModelMeterIon8600V1Service serviceModelIonV1 = new ModelMeterIon8600V1Service();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelMeterIon8600V1Entity dataEntity = serviceModelIonV1.setModelMeterIon8600V1(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

														// kWTot
														deviceUpdateE.setLast_value(dataEntity.getKWTot() != 0.001 ? dataEntity.getKWTot() : null);
														deviceUpdateE.setField_value1(dataEntity.getKWTot() != 0.001 ? dataEntity.getKWTot() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelIonV1.insertModelMeterIon8600V1(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
											case "model_meter_ion_8600v2":
												ModelMeterIon8600V2Service serviceModelIonV2 = new ModelMeterIon8600V2Service();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelMeterIon8600V2Entity dataEntity = serviceModelIonV2.setModelMeterIon8600V2(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// kWTot
														deviceUpdateE.setLast_value(dataEntity.getKWTot() != 0.001 ? dataEntity.getKWTot() : null);
														deviceUpdateE.setField_value1(dataEntity.getKWTot() != 0.001 ? dataEntity.getKWTot() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelIonV2.insertModelMeterIon8600V2(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
											case "model_meter_ion_8600v3":
												ModelMeterIon8600V3Service serviceModelIonV3 = new ModelMeterIon8600V3Service();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelMeterIon8600V3Entity dataEntity = serviceModelIonV3.setModelMeterIon8600V3(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// kWTot
														deviceUpdateE.setLast_value(dataEntity.getKWTot() != 0.001 ? dataEntity.getKWTot() : null);
														deviceUpdateE.setField_value1(dataEntity.getKWTot() != 0.001 ? dataEntity.getKWTot() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelIonV3.insertModelMeterIon8600V3(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
											case "model_meter_ion_8600v4":
												ModelMeterIon8600V4Service serviceModelIonV4 = new ModelMeterIon8600V4Service();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelMeterIon8600V4Entity dataEntity = serviceModelIonV4.setModelMeterIon8600V4(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// kWTot
														deviceUpdateE.setLast_value(dataEntity.getKWTot() != 0.001 ? dataEntity.getKWTot() : null);
														deviceUpdateE.setField_value1(dataEntity.getKWTot() != 0.001 ? dataEntity.getKWTot() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelIonV4.insertModelMeterIon8600V4(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
																						
											case "model_power_measurement_ion_7650":
												ModelPowerMeasurementIon7650Service serviceModelPM7650 = new ModelPowerMeasurementIon7650Service();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelPowerMeasurementIon7650Entity dataEntity = serviceModelPM7650.setModelPowerMeasurementIon7650(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// kWTot
														deviceUpdateE.setLast_value(dataEntity.getkWTot() != 0.001 ? dataEntity.getkWTot() : null);
														deviceUpdateE.setField_value1(dataEntity.getkWTot() != 0.001 ? dataEntity.getkWTot() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelPM7650.insertModelPowerMeasurementIon7650(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
												
											case "model_xantrex_inverter":
												ModelXantrexInverterService serviceModelXINV = new ModelXantrexInverterService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelXantrexInverterEntity dataEntity = serviceModelXINV.setModelXantrexInverter(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

														// ReadPower
														deviceUpdateE.setLast_value(dataEntity.getReadPower() != 0.001 ? dataEntity.getReadPower() : null);
														deviceUpdateE.setField_value1(dataEntity.getReadPower() != 0.001 ? dataEntity.getReadPower() : null);
														
														// PVVoltage
														deviceUpdateE.setField_value2(dataEntity.getPVVoltage() != 0.001 ? dataEntity.getPVVoltage() : null);
														
														// PVCurrent
														deviceUpdateE.setField_value3(dataEntity.getPVCurrent() != 0.001 ? dataEntity.getPVCurrent() : null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelXINV.insertModelXantrexInverter(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
											case "model_poa_temp":
												ModelPoaTempService serviceModelPoaTemp = new ModelPoaTempService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelPoaTempEntity dataEntity = serviceModelPoaTemp.setModelPoaTemp(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// T_AMB
														deviceUpdateE.setLast_value(dataEntity.getT_AMB() != 0.001 ? dataEntity.getT_AMB() : null);
														deviceUpdateE.setField_value1(dataEntity.getT_AMB() != 0.001 ? dataEntity.getT_AMB() : null);
														
														// value 2
														deviceUpdateE.setField_value2(null);
														
														// value 3
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelPoaTemp.insertModelPoaTemp(dataEntity);
													}
												}
												
												
												break;
												
												
											case "model_pyranometer_poa":
												ModelPyranometerPoaService serviceModelPy = new ModelPyranometerPoaService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelPyranometerPoaEntity dataEntity = serviceModelPy.setModelPyranometer(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// POA
														deviceUpdateE.setLast_value(dataEntity.getPoa() != 0.001 ? dataEntity.getPoa() : null);
														deviceUpdateE.setField_value1(dataEntity.getPoa() != 0.001 ? dataEntity.getPoa() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelPy.insertModelPyranometer(dataEntity);
													}
												}
												
												break;
												
											case "model_eri_weather_icp_class8050":
												ModelERIWeatherICPClass8050Service serviceModelERIWeatherICPClass8050 = new ModelERIWeatherICPClass8050Service();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelERIWeatherICPClass8050Entity dataEntity = serviceModelERIWeatherICPClass8050.setModelERIWeatherICPClass8050(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// solar_irradiation
														deviceUpdateE.setLast_value(dataEntity.getSolar_irradiation() != 0.001 ? dataEntity.getSolar_irradiation() : null);
														deviceUpdateE.setField_value1(dataEntity.getSolar_irradiation() != 0.001 ? dataEntity.getSolar_irradiation() : null);
														
														// ambient_temp
														deviceUpdateE.setField_value2(dataEntity.getAmbient_temp() != 0.001 ? dataEntity.getAmbient_temp() : null);
														
														// panel_temp
														deviceUpdateE.setField_value3(dataEntity.getPanel_temp() != 0.001 ? dataEntity.getPanel_temp() : null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelERIWeatherICPClass8050.insertModelERIWeatherICPClass8050(dataEntity);
													}
												}
												
												break;
												
												
											case "model_xantrex_gt500e":
												ModelXantrexGT500EService serviceModelgt500 = new ModelXantrexGT500EService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelXantrexGT500EEntity dataEntity = serviceModelgt500.setModelXantrexGT500E(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

														// AC_POWER
														deviceUpdateE.setLast_value(dataEntity.getAC_POWER() != 0.001 ? dataEntity.getAC_POWER() : null);
														deviceUpdateE.setField_value1(dataEntity.getAC_POWER() != 0.001 ? dataEntity.getAC_POWER() : null);
														
														// DC_POWER
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value2(dataEntity.getDC_POWER() != 0.001 ? dataEntity.getDC_POWER() : null);
														
														//
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelgt500.insertModelXantrexGT500EService(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
												
											case "model_wattsun_tcu":
												ModelWattsunTcuService serviceModelTcu = new ModelWattsunTcuService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelWattsunTcuEntity dataEntity = serviceModelTcu.setModelWattsunTcu(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														
														// ReadAngle
														deviceUpdateE.setLast_value(dataEntity.getANGLE_CALC() != 0.001 ? dataEntity.getANGLE_CALC() : null);
														deviceUpdateE.setField_value1(dataEntity.getANGLE_CALC() != 0.001 ? dataEntity.getANGLE_CALC() : null);
														
														deviceUpdateE.setField_value2(null);
														// value 3
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelTcu.insertModelWattsunTcu(dataEntity);
													}
												}
												
												break;
												
											case "model_wattsun_tracker":
												ModelWattsunTrackerService serviceModelWT = new ModelWattsunTrackerService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelWattsunTrackerEntity dataEntity = serviceModelWT.setModelWattsunTracker(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														
														// ReadAngle
														deviceUpdateE.setLast_value(dataEntity.getTRACKER_ANGLE() != 0.001 ? dataEntity.getTRACKER_ANGLE() : null);
														deviceUpdateE.setField_value1(dataEntity.getTRACKER_ANGLE() != 0.001 ? dataEntity.getTRACKER_ANGLE() : null);
														
														deviceUpdateE.setField_value2(null);
														// value 3
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelWT.insertModelWattsunTracker(dataEntity);
													}
												}
												
												break;
											
											case "model_wattsun_tracker_master":
												ModelWattsunTrackerMasterService serviceModelWTMaster = new ModelWattsunTrackerMasterService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelWattsunTrackerMasterEntity dataEntity = serviceModelWTMaster.setModelWattsunTrackerMaster(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														
														// ReadAngle
														deviceUpdateE.setLast_value(dataEntity.getAngleCalc() != 0.001 ? dataEntity.getAngleCalc() : null);
														deviceUpdateE.setField_value1(dataEntity.getAngleCalc() != 0.001 ? dataEntity.getAngleCalc() : null);
														
														deviceUpdateE.setField_value2(null);
														// value 3
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelWTMaster.insertModelWattsunTrackerMaster(dataEntity);
													}
												}
												
												break;
											
											case "model_sun_track_tracker":
												ModelSunTrackTrackerService serviceModelSunTrackTracker = new ModelSunTrackTrackerService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelSunTrackTrackerEntity dataEntity = serviceModelSunTrackTracker.setModelSunTrackTracker(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														
														// Current Angle Calculated 
														deviceUpdateE.setLast_value(dataEntity.getCurrentAngleCalculated() != 0.001 ? dataEntity.getCurrentAngleCalculated() : null);
														deviceUpdateE.setField_value1(dataEntity.getCurrentAngleCalculated() != 0.001 ? dataEntity.getCurrentAngleCalculated() : null);
														
														deviceUpdateE.setField_value2(null);
														// value 3
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelSunTrackTracker.insertModelSunTrackTracker(dataEntity);
													}
												}
												
												break;
												
											case "model_sev_sg110cx":
												ModelSevSg110cxService serviceModel = new ModelSevSg110cxService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelSevSg110cxEntity dataEntity = serviceModel.setModelSevSg110cx(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setTimezone_value(item.getTimezone_value());
														
														// ac power
														deviceUpdateE.setLast_value(dataEntity.getTotalActivePower() != 0.001 ? dataEntity.getTotalActivePower() : null);
														deviceUpdateE.setField_value1(dataEntity.getTotalActivePower() != 0.001 ? dataEntity.getTotalActivePower() : null);
														
														deviceUpdateE.setField_value2(null);
														// value 3
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModel.insertModelSevSg110cx(dataEntity);
													}
												}
												
												break;
												
											case "model_elster_a1700":
												ModelElsterA1700Service serviceModelElsterA1700 = new ModelElsterA1700Service();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelElsterA1700Entity dataEntity = serviceModelElsterA1700.setModelElsterA1700(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// TotalActivePower
														deviceUpdateE.setLast_value(dataEntity.getTotalActivePower() != 0.001 ? dataEntity.getTotalActivePower() : null);
														deviceUpdateE.setField_value1(dataEntity.getTotalActivePower() != 0.001 ? dataEntity.getTotalActivePower() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelElsterA1700.insertModelElsterA1700(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
												
											case "model_ae_refusol":
												ModelAeRefusolService serviceModelAeR = new ModelAeRefusolService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelAeRefusolEntity dataEntity = serviceModelAeR.setModelAeRefusol(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

														// TotalActivePower
														deviceUpdateE.setLast_value(dataEntity.getACPower() != 0.001 ? dataEntity.getACPower() : null);
														deviceUpdateE.setField_value1(dataEntity.getACPower() != 0.001 ? dataEntity.getACPower() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelAeR.insertModelAeRefusol(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
											case "model_sungrow_logger1000":
												ModelSungrowLogger1000Service serviceModelSG1000 = new ModelSungrowLogger1000Service();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelSungrowLogger1000Entity dataEntity = serviceModelSG1000.setModelSungrowLogger1000(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// TotalActivePower
														deviceUpdateE.setLast_value(dataEntity.getTotalActivePower() != 0.001 ? dataEntity.getTotalActivePower() : null);
														deviceUpdateE.setField_value1(dataEntity.getTotalActivePower() != 0.001 ? dataEntity.getTotalActivePower() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelSG1000.insertModelSungrowLogger1000(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
											case "model_dts_measurelogic_demand_meter":
												ModelDTSMeasurelogicDemandMeterService serviceModelDTSMeter = new ModelDTSMeasurelogicDemandMeterService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelDTSMeasurelogicDemandMeterEntity dataEntity = serviceModelDTSMeter.setModelDTSMeasurelogicDemandMeter(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

														// PowerFactor_DTS_Overall
														deviceUpdateE.setLast_value(dataEntity.getPowerFactor_DTS_Overall() != 0.001 ? dataEntity.getPowerFactor_DTS_Overall() : null);
														deviceUpdateE.setField_value1(dataEntity.getPowerFactor_DTS_Overall() != 0.001 ? dataEntity.getPowerFactor_DTS_Overall() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelDTSMeter.insertModelDTSMeasurelogicDemandMeter(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
												
											case "model_janitza_umg604pro":
												ModelJanitzaUmg604proService serviceModelJan = new ModelJanitzaUmg604proService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelJanitzaUmg604proEntity dataEntity = serviceModelJan.setModelJanitzaUmg604pro(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

														// PowerFactor_DTS_Overall
														deviceUpdateE.setLast_value(dataEntity.getTotalPower() != 0.001 ? dataEntity.getTotalPower() : null);
														deviceUpdateE.setField_value1(dataEntity.getTotalPower() != 0.001 ? dataEntity.getTotalPower() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelJan.insertModelJanitzaUmg604pro(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
											case "model_leviton_70D48000":
												ModelLeviton70D48000Service serviceModel70D = new ModelLeviton70D48000Service();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelLeviton70D48000Entity dataEntity = serviceModel70D.setModelLeviton70D48000(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

														// PowerFactor_DTS_Overall
														deviceUpdateE.setLast_value(dataEntity.getPowerSum() != 0.001 ? dataEntity.getPowerSum() : null);
														deviceUpdateE.setField_value1(dataEntity.getPowerSum() != 0.001 ? dataEntity.getPowerSum() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModel70D.insertModelLeviton70D48000(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
											case "model_acu_rev_production_meter":
												ModelAcuRevProductionMeterService serviceModelAcuRevMeter = new ModelAcuRevProductionMeterService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelAcuRevProductionMeterEntity dataEntity = serviceModelAcuRevMeter.setModelAcuRevProductionMeter(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// TotalRealPower
														deviceUpdateE.setLast_value(dataEntity.getTotalRealPower() != 0.001 ? dataEntity.getTotalRealPower() : null);
														deviceUpdateE.setField_value1(dataEntity.getTotalRealPower() != 0.001 ? dataEntity.getTotalRealPower() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelAcuRevMeter.insertModelAcuRevProductionMeter(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;	
												
											case "model_phoenix_contact_quint_ups":
												ModelPhoenixContactQuintUPSService serviceModelPhoenixContactQuintUPS = new ModelPhoenixContactQuintUPSService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelPhoenixContactQuintUPSEntity dataEntity = serviceModelPhoenixContactQuintUPS.setModelPhoenixContactQuintUPS(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														deviceUpdateE.setLast_value(dataEntity.getStateofHealth() != 0.001 ? dataEntity.getStateofHealth() : null);
														deviceUpdateE.setField_value1(dataEntity.getStateofHealth() != 0.001 ? dataEntity.getStateofHealth() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelPhoenixContactQuintUPS.insertModelPhoenixContactQuintUPS(dataEntity);
													}
												}
												
												break;

											case "model_sma_inverter_12_15_20_24_30tlus10":
						                        ModelSmaInverterStp1215202430Tlus10Service serviceModelSma30Tlus10 = new ModelSmaInverterStp1215202430Tlus10Service();
						                        // Check insert database status
						                        while ((line = br.readLine()) != null) {
						                          sb.append(line); // appends line to string buffer
						                          sb.append("\n"); // line feed
						                          // Convert string to array
						                          List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
						                          if (words.size() > 0) {
						                            
						                            ModelSmaInverterStp1215202430Tlus10Entity dataEntity = serviceModelSma30Tlus10.setModelSmaInverterStp1215202430Tlus10(line);
						                            dataEntity.setId_device(item.getId());
						                            dataEntity.setDatatablename(item.getDatatablename());
						                            dataEntity.setView_tablename(item.getView_tablename());
						                            dataEntity.setJob_tablename(item.getJob_tablename());
						                            
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

						                            // lPower
						                            deviceUpdateE.setLast_value(dataEntity.getPower() != 0.001 ? dataEntity.getPower() : null);
						                            deviceUpdateE.setField_value1(dataEntity.getPower() != 0.001 ? dataEntity.getPower() : null);
						                            
						                            deviceUpdateE.setField_value2(null);
						                            deviceUpdateE.setField_value3(null);
						                            
						                            uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
						                            
						                            serviceModelSma30Tlus10.insertModelSmaInverterStp1215202430Tlus10(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
						                          }
						                        }
						                        
						                        break;	
						                    
											case "model_sma_stp_25_50_us_50":
												ModelSmaStp2550us50Service serviceModelSmaStp2550us50 = new ModelSmaStp2550us50Service();
						                        // Check insert database status
						                        while ((line = br.readLine()) != null) {
						                          sb.append(line); // appends line to string buffer
						                          sb.append("\n"); // line feed
						                          // Convert string to array
						                          List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
						                          if (words.size() > 0) {
						                            
						                        	ModelSmaStp2550us50Entity dataEntity = serviceModelSmaStp2550us50.setModelSmaStp2550us50(line);
						                            dataEntity.setId_device(item.getId());
						                            dataEntity.setDatatablename(item.getDatatablename());
						                            dataEntity.setView_tablename(item.getView_tablename());
						                            dataEntity.setJob_tablename(item.getJob_tablename());
						                            dataEntity.setOffset_data_old(item.getOffset_data_old());
						                            
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

						                            // Active_Power
						                            deviceUpdateE.setLast_value(dataEntity.getActive_Power() != 0.001 ? dataEntity.getActive_Power() : null);
						                            deviceUpdateE.setField_value1(dataEntity.getActive_Power() != 0.001 ? dataEntity.getActive_Power() : null);
						                            
						                            deviceUpdateE.setField_value2(null);
						                            deviceUpdateE.setField_value3(null);
						                            
						                            uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
						                            
						                            serviceModelSmaStp2550us50.insertModelSmaStp2550us50(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
						                          }
						                        }
						                        
						                        break;
						                        
						                        
											case "model_sma_core":
												ModelSmaCoreService serviceModelSmaCore = new ModelSmaCoreService();
						                        // Check insert database status
						                        while ((line = br.readLine()) != null) {
						                          sb.append(line); // appends line to string buffer
						                          sb.append("\n"); // line feed
						                          // Convert string to array
						                          List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
						                          if (words.size() > 0) {
						                            
						                        	ModelSmaCoreEntity dataEntity = serviceModelSmaCore.setModelSmaCore(line);
						                            dataEntity.setId_device(item.getId());
						                            dataEntity.setDatatablename(item.getDatatablename());
						                            dataEntity.setView_tablename(item.getView_tablename());
						                            dataEntity.setJob_tablename(item.getJob_tablename());
						                            dataEntity.setOffset_data_old(item.getOffset_data_old());
						                            
													uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

						                            // Active_Power
						                            deviceUpdateE.setLast_value(dataEntity.getActivePower() != 0.001 ? dataEntity.getActivePower() : null);
						                            deviceUpdateE.setField_value1(dataEntity.getActivePower() != 0.001 ? dataEntity.getActivePower() : null);
						                            
						                            deviceUpdateE.setField_value2(null);
						                            deviceUpdateE.setField_value3(null);
						                            
						                            uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
						                            
						                            serviceModelSmaCore.insertModelSmaCore(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
						                          }
						                        }
						                        
						                        break;
						                        
											case "model_abb_uno_dm_1250tp_plus":
												ModelAbbUnoDm1250tpPlusService serviceModelSma1250Tlus = new ModelAbbUnoDm1250tpPlusService();
						                        // Check insert database status
						                        while ((line = br.readLine()) != null) {
						                          sb.append(line); // appends line to string buffer
						                          sb.append("\n"); // line feed
						                          // Convert string to array
						                          List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
						                          if (words.size() > 0) {
						                        	  
						                        	  ModelAbbUnoDm1250tpPlusEntity dataEntity = serviceModelSma1250Tlus.setModelAbbUnoDm1250tpPlus(line);
						                        	  dataEntity.setId_device(item.getId());
						                        	  dataEntity.setDatatablename(item.getDatatablename());
						                        	  dataEntity.setView_tablename(item.getView_tablename());
						                        	  dataEntity.setJob_tablename(item.getJob_tablename());
						                            
						                        	uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
						                            
						                            // ACActivePower
						                            deviceUpdateE.setLast_value(dataEntity.getACActivePower() != 0.001 ? dataEntity.getACActivePower() : null);
						                            deviceUpdateE.setField_value1(dataEntity.getACActivePower() != 0.001 ? dataEntity.getACActivePower() : null);
						                            
						                            deviceUpdateE.setField_value2(null);
						                            deviceUpdateE.setField_value3(null);
						                            
						                            uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
						                            
						                            serviceModelSma1250Tlus.insertModelAbbUnoDm1250tpPlus(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
						                          }
						                        }
						                        
						                        break;	
						                        
						                        
											case "model_klea_220p":
												ModelKlea220pService serviceModelKlea = new ModelKlea220pService();
						                        // Check insert database status
						                        while ((line = br.readLine()) != null) {
						                          sb.append(line); // appends line to string buffer
						                          sb.append("\n"); // line feed
						                          // Convert string to array
						                          List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
						                          if (words.size() > 0) {
						                        	  
						                        	  ModelKlea220pEntity dataEntity = serviceModelKlea.setModelKlea220p(line);
						                        	  dataEntity.setId_device(item.getId());
						                        	  dataEntity.setDatatablename(item.getDatatablename());
						                        	  dataEntity.setView_tablename(item.getView_tablename());
						                        	  dataEntity.setJob_tablename(item.getJob_tablename());
						                        	  dataEntity.setOffset_data_old(item.getOffset_data_old());
						                            
						                        	uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

						                            // TotalActivePower
						                            deviceUpdateE.setLast_value(dataEntity.getTotalActivePower() != 0.001 ? dataEntity.getTotalActivePower() : null);
						                            deviceUpdateE.setField_value1(dataEntity.getTotalActivePower() != 0.001 ? dataEntity.getTotalActivePower() : null);
						                            
						                            deviceUpdateE.setField_value2(null);
						                            deviceUpdateE.setField_value3(null);
						                            
						                            uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
						                            
						                            serviceModelKlea.insertModelKlea220p(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
						                          }
						                        }
						                        
						                        break;	
						                        
											case "model_meter_ion_6200":
												ModelMeterIon6200Service serviceModelMeterIon6200 = new ModelMeterIon6200Service();
						                        // Check insert database status
						                        while ((line = br.readLine()) != null) {
						                          sb.append(line); // appends line to string buffer
						                          sb.append("\n"); // line feed
						                          // Convert string to array
						                          List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
						                          if (words.size() > 0) {
						                        	  
						                        	  ModelMeterIon6200Entity dataEntity = serviceModelMeterIon6200.setModelMeterIon6200(line);
						                        	  dataEntity.setId_device(item.getId());
						                        	  dataEntity.setDatatablename(item.getDatatablename());
						                        	  dataEntity.setView_tablename(item.getView_tablename());
						                        	  dataEntity.setJob_tablename(item.getJob_tablename());
						                        	  dataEntity.setOffset_data_old(item.getOffset_data_old());
						                            
						                        	uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

						                            // kWtotal
						                            deviceUpdateE.setLast_value(dataEntity.getkWtotal() != 0.001 ? dataEntity.getkWtotal() : null);
						                            deviceUpdateE.setField_value1(dataEntity.getkWtotal() != 0.001 ? dataEntity.getkWtotal() : null);
						                            
						                            deviceUpdateE.setField_value2(null);
						                            deviceUpdateE.setField_value3(null);
						                            
						                            uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
						                            
						                            serviceModelMeterIon6200.insertModelMeterIon6200(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
						                          }
						                        }
						                        
						                        break;
						                        
						                        
											case "model_leviton_s40000r_power_meter":
												ModelLevitonS40000rPowerMeterService serviceModelMeterS40000 = new ModelLevitonS40000rPowerMeterService();
						                        // Check insert database status
						                        while ((line = br.readLine()) != null) {
						                          sb.append(line); // appends line to string buffer
						                          sb.append("\n"); // line feed
						                          // Convert string to array
						                          List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
						                          if (words.size() > 0) {
						                        	  
						                        	  ModelLevitonS40000rPowerMeterEntity dataEntity = serviceModelMeterS40000.setModelLevitonS40000rPowerMeter(line);
						                        	  dataEntity.setId_device(item.getId());
						                        	  dataEntity.setDatatablename(item.getDatatablename());
						                        	  dataEntity.setView_tablename(item.getView_tablename());
						                        	  dataEntity.setJob_tablename(item.getJob_tablename());
						                        	  dataEntity.setOffset_data_old(item.getOffset_data_old());
						                        	  
						                        	uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
						                            
						                            // kWtotal
						                            deviceUpdateE.setLast_value(dataEntity.getTotalInstantaneousRealPower() != 0.001 ? dataEntity.getTotalInstantaneousRealPower() : null);
						                            deviceUpdateE.setField_value1(dataEntity.getTotalInstantaneousRealPower() != 0.001 ? dataEntity.getTotalInstantaneousRealPower() : null);
						                            
						                            deviceUpdateE.setField_value2(null);
						                            deviceUpdateE.setField_value3(null);
						                            
						                            uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
						                            
						                            serviceModelMeterS40000.insertModelLevitonS40000rPowerMeter(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
						                          }
						                        }
						                        
						                        break;
						                        
						                    
											case "model_leviton_abvius_a891123channel":
												ModelLevitonAbviusA891123ChannelService serviceModelA891123 = new ModelLevitonAbviusA891123ChannelService();
						                        // Check insert database status
						                        while ((line = br.readLine()) != null) {
						                          sb.append(line); // appends line to string buffer
						                          sb.append("\n"); // line feed
						                          // Convert string to array
						                          List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
						                          if (words.size() > 0) {
						                        	  
						                        	  ModelLevitonAbviusA891123ChannelEntity dataEntity = serviceModelA891123.setModelLevitonAbviusA891123Channel(line);
						                        	  dataEntity.setId_device(item.getId());
						                        	  dataEntity.setDatatablename(item.getDatatablename());
						                        	  dataEntity.setView_tablename(item.getView_tablename());
						                        	  dataEntity.setJob_tablename(item.getJob_tablename());
						                        	  dataEntity.setId_site(item.getId_site());
						                        	  
						                        	uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
						                            
						                            // kWtotal
						                            deviceUpdateE.setLast_value(null);
						                            deviceUpdateE.setField_value1(null);
						                            
						                            deviceUpdateE.setField_value2(null);
						                            deviceUpdateE.setField_value3(null);
						                            
						                            uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
						                            
						                            serviceModelA891123.insertModelLevitonAbviusA891123Channel(dataEntity);
													
													uploadFilesService.checkWrongEnergy(item, dataEntity);
						                          }
						                        }
						                        
						                        break;
						                        
											case "model_acuvim_IIR":
												ModelAcuvimIIRService serviceModelAcuvimIIR = new ModelAcuvimIIRService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelAcuvimIIREntity dataEntity = serviceModelAcuvimIIR.setModelAcuvimIIR(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// SystempowerPsum
														deviceUpdateE.setLast_value(dataEntity.getSystempowerPsum() != 0.001 ? dataEntity.getSystempowerPsum() : null);
														deviceUpdateE.setField_value1(dataEntity.getSystempowerPsum() != 0.001 ? dataEntity.getSystempowerPsum() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelAcuvimIIR.insertModelAcuvimIIR(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
												
											case "model_ky_pulse_meter":
												ModelKyPulseMeterService serviceModelKyPulse = new ModelKyPulseMeterService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelKyPulseMeterEntity dataEntity = serviceModelKyPulse.setModelKyPulseMeter(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

														// SystempowerPsum
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelKyPulse.insertModelKyPulseMeter(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
											case "model_water_meter_ky_pulse":
												ModelWaterMeterKyPulseService serviceModelKP = new ModelWaterMeterKyPulseService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelWaterMeterKyPulseEntity dataEntity = serviceModelKP.setModelWaterMeterKyPulse(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

														// SystempowerPsum
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelKP.insertModelWaterMeterKyPulse(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
												
											case "model_sun_spec_inverter":
												ModelSunSpecInverterService serviceModelSunSpec = new ModelSunSpecInverterService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelSunSpecInverterEntity dataEntity = serviceModelSunSpec.setModelSunSpecInverter(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

														// ACPower
														deviceUpdateE.setLast_value(dataEntity.getACPower() != 0.001 ? dataEntity.getACPower() : null);
														deviceUpdateE.setField_value1(dataEntity.getACPower() != 0.001 ? dataEntity.getACPower() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelSunSpec.insertModelSunSpecInverter(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
												
											case "model_dent_48pshd_meter":
												ModelDent48PSHDMeterService serviceModelDent = new ModelDent48PSHDMeterService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelDent48PSHDMeterEntity dataEntity = serviceModelDent.setModelDent48PSHDMeter(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

														// ACPower
														deviceUpdateE.setLast_value(dataEntity.getPowerSum() != 0.001 ? dataEntity.getPowerSum() : null);
														deviceUpdateE.setField_value1(dataEntity.getPowerSum() != 0.001 ? dataEntity.getPowerSum() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelDent.insertModelDent48PSHDMeter(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
											case "model_power_logic_pm8000_load_meter":
												ModelPowerLogicPM8000LoadMeterService serviceModelPM8000 = new ModelPowerLogicPM8000LoadMeterService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelPowerLogicPM8000LoadMeterEntity dataEntity = serviceModelPM8000.setModelPowerLogicPM8000LoadMeter(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// ACPower
														deviceUpdateE.setLast_value(dataEntity.getActivePowerTotal() != 0.001 ? dataEntity.getActivePowerTotal() : null);
														deviceUpdateE.setField_value1(dataEntity.getActivePowerTotal() != 0.001 ? dataEntity.getActivePowerTotal() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelPM8000.insertModelPowerLogicPM8000LoadMeter(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
											case "model_shark250":
												ModelShark250Service serviceModelShark250 = new ModelShark250Service();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelShark250Entity dataEntity = serviceModelShark250.setModelShark250(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);

														// active power
														deviceUpdateE.setLast_value(dataEntity.getActivePower() != 0.001 ? dataEntity.getActivePower() : null);
														deviceUpdateE.setField_value1(dataEntity.getActivePower() != 0.001 ? dataEntity.getActivePower() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelShark250.insertModelShark250(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												
												break;
												
											case "model_xgi150":
												ModelXGI1500Service serviceModelXGI1500 = new ModelXGI1500Service();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelXGI1500Entity dataEntity = serviceModelXGI1500.setModelXGI1500(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// active power
														deviceUpdateE.setLast_value(dataEntity.getActivePower() != 0.001 ? dataEntity.getActivePower() : null);
														deviceUpdateE.setField_value1(dataEntity.getActivePower() != 0.001 ? dataEntity.getActivePower() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelXGI1500.insertModelXGI1500(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												
												break;
												
												
											case "model_SEL651R":
												ModelSEL651RService serviceModelSEL651R = new ModelSEL651RService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelSEL651REntity dataEntity = serviceModelSEL651R.setModelSEL651R(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// active power
														deviceUpdateE.setLast_value(dataEntity.getBreakerStatus() != 0.001 ? dataEntity.getBreakerStatus() : null);
														deviceUpdateE.setField_value1(dataEntity.getBreakerStatus() != 0.001 ? dataEntity.getBreakerStatus() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelSEL651R.insertModelSEL651R(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												
												break;
												
											case "model_eaton_nova6_recloser":
												ModelEatonNova6RecloserService serviceModelEaton = new ModelEatonNova6RecloserService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelEatonNova6RecloserEntity dataEntity = serviceModelEaton.setModelEatonNova6Recloser(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// active power
//														deviceUpdateE.setLast_value(dataEntity.getRecloserClosed() != 0.001 ? dataEntity.getRecloserClosed() : null);
//														deviceUpdateE.setField_value1(dataEntity.getRecloserClosed() != 0.001 ? dataEntity.getRecloserClosed() : null);
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelEaton.insertModelEatonNova6Recloser(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												
												break;
												
											case "model_ATI_Tracker":
												ModelATiTrackerService serviceModelATiTracker = new ModelATiTrackerService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelATiTrackerEntity dataEntity = serviceModelATiTracker.setModelATiTracker(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setEnable_alert(item.getEnable_alert());
														
														// ReadAngle
														deviceUpdateE.setLast_value(dataEntity.getTracker1Setpoint() != 0.001 ? dataEntity.getTracker1Setpoint() : null);
														deviceUpdateE.setField_value1(dataEntity.getTracker1Setpoint() != 0.001 ? dataEntity.getTracker1Setpoint() : null);
														
														// value 2
														deviceUpdateE.setField_value2(null);
														
														// value 3
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelATiTracker.insertModelATiTracker(dataEntity);
													}
												}
												
												break;
												
											case "model_QUINT4_UPS":
												ModelQuint4UPSService serviceModelQUPS = new ModelQuint4UPSService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelQuint4UPSEntity dataEntity = serviceModelQUPS.setModelQuint4UPS(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setEnable_alert(item.getEnable_alert());
														
														// State of Charge Remaining Time
														deviceUpdateE.setLast_value(dataEntity.getBatteryModeTime() != 0.001 ? dataEntity.getBatteryModeTime() : null);
														deviceUpdateE.setField_value1(dataEntity.getBatteryModeTime() != 0.001 ? dataEntity.getBatteryModeTime() : null);
														
														// value 2
														deviceUpdateE.setField_value2(null);
														
														// value 3
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelQUPS.insertModelQuint4UPS(dataEntity);
													}
												}
												
												break;
												
											case "model_quint_ups_poso":
												ModelQuintUPSPosoService serviceModelQUPSPoso = new ModelQuintUPSPosoService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelQuintUPSPosoEntity dataEntity = serviceModelQUPSPoso.setModelQuintUPSPoso(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setEnable_alert(item.getEnable_alert());
														
														// State of Charge Remaining Time
														deviceUpdateE.setLast_value(dataEntity.getBatteryTemperature() != 0.001 ? dataEntity.getBatteryTemperature() : null);
														deviceUpdateE.setField_value1(dataEntity.getBatteryTemperature() != 0.001 ? dataEntity.getBatteryTemperature() : null);
														
														// value 2
														deviceUpdateE.setField_value2(null);
														
														// value 3
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelQUPSPoso.insertModelQuintUPSPoso(dataEntity);
													}
												}
												
												break;
												
												
											case "model_g3_light_controller":
												ModelG3LightControllerService serviceModelG3 = new ModelG3LightControllerService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelG3LightControllerEntity dataEntity = serviceModelG3.setModelG3LightController(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setEnable_alert(item.getEnable_alert());
														
														// WindSpeed
														deviceUpdateE.setLast_value(null);
														deviceUpdateE.setField_value1(null);
														
														// value 2
														deviceUpdateE.setField_value2(null);
														
														// value 3
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelG3.insertModelG3LightController(dataEntity);
													}
												}
												
												break;
												
												
											case "model_sol_ark_inverter":
												ModelSolArkInverterService serviceModelSol = new ModelSolArkInverterService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelSolArkInverterEntity dataEntity = serviceModelSol.setModelSolArkInverter(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// 
														deviceUpdateE.setLast_value(dataEntity.getTotalActivePower() != 0.001 ? dataEntity.getTotalActivePower() : null);
														deviceUpdateE.setField_value1(dataEntity.getTotalActivePower() != 0.001 ? dataEntity.getTotalActivePower() : null);
														
														// 
														deviceUpdateE.setField_value2(dataEntity.getTotalActivePower() != 0.001 ? dataEntity.getTotalActivePower() : null);
														
														// 
														deviceUpdateE.setField_value3(dataEntity.getTotalActivePower() != 0.001 ? dataEntity.getTotalActivePower() : null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelSol.insertModelSolArkInverter(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
											case "model_honeywell_emon_3200":
												ModelHoneywellEMON3200Service serviceModelModelHoneywellEMON3200 = new ModelHoneywellEMON3200Service();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelHoneywellEMON3200Entity dataEntity = serviceModelModelHoneywellEMON3200.setData(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// real power
														deviceUpdateE.setLast_value(dataEntity.getRealPower() != 0.001 ? dataEntity.getRealPower() : null);
														deviceUpdateE.setField_value1(dataEntity.getRealPower() != 0.001 ? dataEntity.getRealPower() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelModelHoneywellEMON3200.insertData(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
											case "model_huawei_sun2000_28ktl":
												ModelHuaweiSun200028ktlService serviceHuaweiSun200028ktl = new ModelHuaweiSun200028ktlService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelHuaweiSun200028ktlEntity dataEntity = serviceHuaweiSun200028ktl.setModelHuaweiSun200028ktl(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// real power
														deviceUpdateE.setLast_value(dataEntity.getActivePower() != 0.001 ? dataEntity.getActivePower() : null);
														deviceUpdateE.setField_value1(dataEntity.getActivePower() != 0.001 ? dataEntity.getActivePower() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceHuaweiSun200028ktl.insertModelHuaweiSun200028ktl(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
										
											case "model_sma_shp7510":
												ModelSmaShp7510Service serviceModelSmaShp7510 = new ModelSmaShp7510Service();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelSmaShp7510Entity dataEntity = serviceModelSmaShp7510.setModelSmaShp7510(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// real power
														deviceUpdateE.setLast_value(dataEntity.getActivepower() != 0.001 ? dataEntity.getActivepower() : null);
														deviceUpdateE.setField_value1(dataEntity.getActivepower() != 0.001 ? dataEntity.getActivepower() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelSmaShp7510.insertModelSmaShp7510(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
												
											case "model_lovato_dmg800":
												ModelLovatoDmg800Service serviceModelLovatoDmg800 = new ModelLovatoDmg800Service();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelLovatoDmg800Entity dataEntity = serviceModelLovatoDmg800.setModelLovatoDmg800(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// real power
														deviceUpdateE.setLast_value(dataEntity.getEqvActivepower() != 0.001 ? dataEntity.getEqvActivepower() : null);
														deviceUpdateE.setField_value1(dataEntity.getEqvActivepower() != 0.001 ? dataEntity.getEqvActivepower() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelLovatoDmg800.insertModelLovatoDmg800(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
											case "model_weather_station_bsp":
												ModelWeatherStationBSPService serviceModelWeatherStationBSP = new ModelWeatherStationBSPService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelWeatherStationBSPEntity dataEntity = serviceModelWeatherStationBSP.setModelWeatherStationBSP(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// real IRR
														deviceUpdateE.setLast_value(dataEntity.getTotalIrradiance() != 0.001 ? dataEntity.getTotalIrradiance() : null);
														deviceUpdateE.setField_value1(dataEntity.getTotalIrradiance() != 0.001 ? dataEntity.getTotalIrradiance() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelWeatherStationBSP.insertModelWeatherStationBSP(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
												
											case "model_abb_trio500tk_outd":
												ModelAbbTrio500tkOutdService serviceAbbTrio500tkOutd = new ModelAbbTrio500tkOutdService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelAbbTrio500tkOutdEntity dataEntity = serviceAbbTrio500tkOutd.setModelAbbTrio500tkOutd(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// real power
														deviceUpdateE.setLast_value(dataEntity.getOutputActivePower() != 0.001 ? dataEntity.getOutputActivePower() : null);
														deviceUpdateE.setField_value1(dataEntity.getOutputActivePower() != 0.001 ? dataEntity.getOutputActivePower() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceAbbTrio500tkOutd.insertModelAbbTrio500tkOutd(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
											case "model_smartlogger3000":
												ModelSmartLogger3000Service serviceSmartLogger3000 = new ModelSmartLogger3000Service();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelSmartLogger3000Entity dataEntity = serviceSmartLogger3000.setModelSmartLogger3000(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// real power
														deviceUpdateE.setLast_value(dataEntity.getActivePower() != 0.001 ? dataEntity.getActivePower() : null);
														deviceUpdateE.setField_value1(dataEntity.getActivePower() != 0.001 ? dataEntity.getActivePower() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceSmartLogger3000.insertModelSmartLogger3000(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
												
											case "model_gas_meter":
												ModelGasMeterService serviceModelModelGasMeter = new ModelGasMeterService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelGasMeterEntity dataEntity = serviceModelModelGasMeter.setModelGasMeter(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// real power
														deviceUpdateE.setLast_value(dataEntity.getProcessedValue() != 0.001 ? dataEntity.getProcessedValue() : null);
														deviceUpdateE.setField_value1(dataEntity.getProcessedValue() != 0.001 ? dataEntity.getProcessedValue() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelModelGasMeter.insertModelGasMeter(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
											case "model_ec350_gas_meter":
												ModelEC350GasMeterService serviceModelEC350GasMeter = new ModelEC350GasMeterService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelEC350GasMeterEntity dataEntity = serviceModelEC350GasMeter.setModelEC350GasMeter(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														dataEntity.setOffset_data_old(item.getOffset_data_old());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// real power
														deviceUpdateE.setLast_value(dataEntity.getEnergy() != 0.001 ? dataEntity.getEnergy() : null);
														deviceUpdateE.setField_value1(dataEntity.getEnergy() != 0.001 ? dataEntity.getEnergy() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelEC350GasMeter.insertModelEC350GasMeter(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
												// Model model_abb_emax_cb_ekip
											case "model_abb_emax_cb_ekip":
												ModelAbbEmaxCbEkipService serviceModelAbbEmaxCbEkip = new ModelAbbEmaxCbEkipService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelAbbEmaxCbEkipEntity dataEntity = serviceModelAbbEmaxCbEkip.setModelAbbEmaxCbEkip(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// ActivePower3PhaseTotal
														deviceUpdateE.setLast_value(dataEntity.getActivePower3PhaseTotal() != 0.001 ? dataEntity.getActivePower3PhaseTotal() : null);
														deviceUpdateE.setField_value1(dataEntity.getActivePower3PhaseTotal() != 0.001 ? dataEntity.getActivePower3PhaseTotal() : null);
														
														// ApparentPower3PhaseTotal
														deviceUpdateE.setField_value2(dataEntity.getApparentPower3PhaseTotal() != 0.001 ? dataEntity.getApparentPower3PhaseTotal() : null);
														
														// ReactivePower3PhaseTotal
														deviceUpdateE.setField_value3(dataEntity.getReactivePower3PhaseTotal() != 0.001 ? dataEntity.getReactivePower3PhaseTotal() : null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelAbbEmaxCbEkip.insertModelAbbEmaxCbEkip(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
												// Model model_pextron_urp6000
											case "model_pextron_urp6000":
												ModelPextronUrp6000Service serviceModelPextronUrp6000 = new ModelPextronUrp6000Service();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelPextronUrp6000Entity dataEntity = serviceModelPextronUrp6000.setModelPextronUrp6000(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// ActivePowerPhaseA
														deviceUpdateE.setLast_value(dataEntity.getActivePowerPhaseA() != 0.001 ? dataEntity.getActivePowerPhaseA() : null);
														deviceUpdateE.setField_value1(dataEntity.getActivePowerPhaseA() != 0.001 ? dataEntity.getActivePowerPhaseA() : null);
														
														// ActivePowerPhaseB
														deviceUpdateE.setField_value2(dataEntity.getActivePowerPhaseB() != 0.001 ? dataEntity.getActivePowerPhaseB() : null);
														
														// ActivePowerPhaseC
														deviceUpdateE.setField_value3(dataEntity.getActivePowerPhaseC() != 0.001 ? dataEntity.getActivePowerPhaseC() : null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelPextronUrp6000.insertModelPextronUrp6000(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
												// Model model_siemens_7sr11
											case "model_siemens_7sr11":
												ModelSiemens7Sr11Service serviceModelSiemens7Sr11 = new ModelSiemens7Sr11Service();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelSiemens7Sr11Entity dataEntity = serviceModelSiemens7Sr11.setModelSiemens7Sr11(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// ActivePower3PhaseTotal
														deviceUpdateE.setLast_value(dataEntity.getActivePower3PhaseTotal() != 0.001 ? dataEntity.getActivePower3PhaseTotal() : null);
														deviceUpdateE.setField_value1(dataEntity.getActivePower3PhaseTotal() != 0.001 ? dataEntity.getActivePower3PhaseTotal() : null);
														
														// Frequency
														deviceUpdateE.setField_value2(dataEntity.getFrequency() != 0.001 ? dataEntity.getFrequency() : null);
														
														// GroundCurrent
														deviceUpdateE.setField_value3(dataEntity.getGroundCurrent() != 0.001 ? dataEntity.getGroundCurrent() : null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelSiemens7Sr11.insertModelSiemens7Sr11(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
											case "model_ginlong_solis_inverter_class6007":
												ModelGinlongSolisInverterClass6007Service serviceModelGinlong = new ModelGinlongSolisInverterClass6007Service();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelGinlongSolisInverterClass6007Entity dataEntity = serviceModelGinlong.setModelGinlongSolisInverterClass6007(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// ActivePower3PhaseTotal
														deviceUpdateE.setLast_value(dataEntity.getActiveower() != 0.001 ? dataEntity.getActiveower() : null);
														deviceUpdateE.setField_value1(dataEntity.getActiveower() != 0.001 ? dataEntity.getActiveower() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelGinlong.insertGinlongSolisInverterClass6007(dataEntity);
														
														uploadFilesService.checkWrongEnergy(item, dataEntity);
													}
												}
												
												break;
												
												// Model model_thermtronic_th104bus
											case "model_thermtronic_th104bus":
												ModelThermtronicTh104BusService serviceModelThermtronicTh104Bus = new ModelThermtronicTh104BusService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelThermtronicTh104BusEntity dataEntity = serviceModelThermtronicTh104Bus.setModelThermtronicTh104Bus(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														// AmbientMeasuredMaximumTemperature
														deviceUpdateE.setLast_value(dataEntity.getAmbientMeasuredMaximumTemperature() != 0.001 ? dataEntity.getAmbientMeasuredMaximumTemperature() : null);
														deviceUpdateE.setField_value1(dataEntity.getAmbientMeasuredMaximumTemperature() != 0.001 ? dataEntity.getAmbientMeasuredMaximumTemperature() : null);
														
														// AmbientAirCurrentTemperature
														deviceUpdateE.setField_value2(dataEntity.getAmbientAirCurrentTemperature() != 0.001 ? dataEntity.getAmbientAirCurrentTemperature() : null);
														
														// AmbientAlarmTemperature
														deviceUpdateE.setField_value3(dataEntity.getAmbientAlarmTemperature() != 0.001 ? dataEntity.getAmbientAlarmTemperature() : null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelThermtronicTh104Bus.insertModelThermtronicTh104Bus(dataEntity);							
													}
												}
												
												break;		
												
											case "model_Kehua_SPI50_60K_inverter":
												ModelKehuaSPI5060KInverterService serviceModelKehua = new ModelKehuaSPI5060KInverterService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelKehuaSPI5060KInverterEntity dataEntity = serviceModelKehua.setModelKehuaSPI5060KInverter(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														deviceUpdateE.setLast_value(dataEntity.getOngridactivepower() != 0.001 ? dataEntity.getOngridactivepower() : null);
														deviceUpdateE.setField_value1(dataEntity.getOngridactivepower() != 0.001 ? dataEntity.getOngridactivepower() : null);
														
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelKehua.insertModelKehuaSPI5060KInverter(dataEntity);							
													}
												}
												
												break;	
											
											case "model_Hukseflux_HB500":
												ModelHuksefluxHB500Service serviceModelHuk = new ModelHuksefluxHB500Service();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														
														ModelHuksefluxHB500Entity dataEntity = serviceModelHuk.setModelHuksefluxHB500(line);
														dataEntity.setId_device(item.getId());
														dataEntity.setDatatablename(item.getDatatablename());
														dataEntity.setView_tablename(item.getView_tablename());
														dataEntity.setJob_tablename(item.getJob_tablename());
														
														uploadFilesService.scalingDeviceParameters(scaledDeviceParameters, dataEntity);
														
														deviceUpdateE.setLast_value(dataEntity.getPoa() != 0.001 ? dataEntity.getPoa() : null);
														deviceUpdateE.setField_value1(dataEntity.getPoa() != 0.001 ? dataEntity.getPoa() : null);
														
														// value 2
														deviceUpdateE.setField_value2(null);
														
														// value 3
														deviceUpdateE.setField_value3(null);
														
														uploadFilesService.deviceLastUpdated(deviceUpdateE, dataEntity);
														
														serviceModelHuk.insertModelHuksefluxHB500(dataEntity);
													}
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
										
										// Save to datalogger
//										ModelDataloggerEntity dataloggerEntity = new ModelDataloggerEntity();
//										dataloggerEntity.setId_device(item.getId());
//										dataloggerEntity.setDatatablename(item.getDatalogger_table());
//										dataloggerEntity.setView_tablename(item.getView_tablename());
//										dataloggerEntity.setJob_tablename(item.getJob_tablename());
//										
//										Date now = new Date();
//										TimeZone.setDefault(TimeZone.getTimeZone("America/Los_Angeles"));
//										SimpleDateFormat formatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//								        TimeZone tzUTC = TimeZone.getTimeZone("UTC");
//								        formatUTC.setTimeZone(tzUTC);
//								        String sDateUTC = formatUTC.format(now);
//								        dataloggerEntity.setTime(sDateUTC);
//								        dataloggerEntity.setSerialnumber(serialnumber);
//								        dataloggerEntity.setLoopname(loopname);
//								        dataloggerEntity.setModbusip(modbusip);
//								        dataloggerEntity.setModbusport(modbusport);
//								        dataloggerEntity.setModbusdevice(modbusdevice);
//								        dataloggerEntity.setModbusdevicename(modbusdevicename);
//								        dataloggerEntity.setModbusdevicetype(modbusdevicetype);
//								        dataloggerEntity.setModbusdevicetypenumber(modbusdevicetypenumber);
//								        dataloggerEntity.setModbusdeviceclass(modbusdeviceclass);
//										ModelDataloggerService dataloggerService = new ModelDataloggerService();
//										dataloggerService.insertModelDatalogger(dataloggerEntity);
										
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

				});
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
														}
														
														Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice + "-" + unique + "."
																+ timeStamp + ".log.gz");
														File logGzFile = new File(path.toString());
														
														if(logGzFile.delete()) {     
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
//				message = "\nSUCCESS\n";
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