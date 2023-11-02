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
import com.nwm.api.entities.ModelAE1000NXClass9644Entity;
import com.nwm.api.entities.ModelAbbTrioClass6210Entity;
import com.nwm.api.entities.ModelAdam4017WSClass8110Nelis190Entity;
import com.nwm.api.entities.ModelAdvancedEnergySolaronEntity;
import com.nwm.api.entities.ModelAesTxInverterEntity;
import com.nwm.api.entities.ModelCampellScientificMeter1Entity;
import com.nwm.api.entities.ModelCampellScientificMeter2Entity;
import com.nwm.api.entities.ModelCampellScientificMeter3Entity;
import com.nwm.api.entities.ModelCampellScientificMeter4Entity;
import com.nwm.api.entities.ModelChintSolectriaInverterClass9725Entity;
import com.nwm.api.entities.ModelDataloggerEntity;
import com.nwm.api.entities.ModelERIWeatherICPClass8050Entity;
import com.nwm.api.entities.ModelElkorProductionMeterEntity;
import com.nwm.api.entities.ModelElkorWattsonPVMeterEntity;
import com.nwm.api.entities.ModelHukselfluxSr30d1DeviceclassV0Entity;
import com.nwm.api.entities.ModelIMTSolarClass8000Entity;
import com.nwm.api.entities.ModelIMTSolarTmodulClass8006Entity;
import com.nwm.api.entities.ModelIVTSolaronEXTEntity;
import com.nwm.api.entities.ModelKippZonenRT1Class8009Entity;
import com.nwm.api.entities.ModelLufftClass8020Entity;
import com.nwm.api.entities.ModelLufftWS501UMBWeatherEntity;
import com.nwm.api.entities.ModelMeterIon8600Entity;
import com.nwm.api.entities.ModelPVMet100Entity;
import com.nwm.api.entities.ModelPVPInverterEntity;
import com.nwm.api.entities.ModelPVPowered3550260500kwInverterEntity;
import com.nwm.api.entities.ModelPoaTempEntity;
import com.nwm.api.entities.ModelPowerMeasurementIon7650Entity;
import com.nwm.api.entities.ModelPyranometerPoaEntity;
import com.nwm.api.entities.ModelRT1Class30000Entity;
import com.nwm.api.entities.ModelSatconPowergate225InverterEntity;
import com.nwm.api.entities.ModelSatconPvs357InverterEntity;
import com.nwm.api.entities.ModelShark100Entity;
import com.nwm.api.entities.ModelShark100TestEntity;
import com.nwm.api.entities.ModelSolarEdgeInverterEntity;
import com.nwm.api.entities.ModelSolectriaSGI226IVTEntity;
import com.nwm.api.entities.ModelSunnyCentralClass9775InverterEntity;
import com.nwm.api.entities.ModelTTiTrackerEntity;
import com.nwm.api.entities.ModelVerisIndustriesE50c2aEntity;
import com.nwm.api.entities.ModelVerisIndustriesE51c2PowerMeterEntity;
import com.nwm.api.entities.ModelWKippZonenRT1Entity;
import com.nwm.api.entities.ModelWattsunTcuEntity;
import com.nwm.api.entities.ModelWattsunTrackerEntity;
import com.nwm.api.entities.ModelXantrexGT100250500Entity;
import com.nwm.api.entities.ModelXantrexGT500EEntity;
import com.nwm.api.entities.ModelXantrexInverterEntity;
import com.nwm.api.services.BatchJobService;
import com.nwm.api.services.DeviceService;
import com.nwm.api.services.ModelAE1000NXClass9644Service;
import com.nwm.api.services.ModelAbbTrioClass6210Service;
import com.nwm.api.services.ModelAdam4017WSClass8110Nelis190Service;
import com.nwm.api.services.ModelAdvancedEnergySolaronService;
import com.nwm.api.services.ModelAesTxInverterService;
import com.nwm.api.services.ModelCampellScientificMeter1Service;
import com.nwm.api.services.ModelCampellScientificMeter2Service;
import com.nwm.api.services.ModelCampellScientificMeter3Service;
import com.nwm.api.services.ModelCampellScientificMeter4Service;
import com.nwm.api.services.ModelChintSolectriaInverterClass9725Service;
import com.nwm.api.services.ModelDataloggerService;
import com.nwm.api.services.ModelERIWeatherICPClass8050Service;
import com.nwm.api.services.ModelElkorProductionMeterService;
import com.nwm.api.services.ModelElkorWattsonPVMeterService;
import com.nwm.api.services.ModelHukselfluxSr30d1DeviceclassV0Service;
import com.nwm.api.services.ModelIMTSolarClass8000Service;
import com.nwm.api.services.ModelIMTSolarTmodulClass8006Service;
import com.nwm.api.services.ModelIVTSolaronEXTService;
import com.nwm.api.services.ModelKippZonenRT1Class8009Service;
import com.nwm.api.services.ModelLufftClass8020Service;
import com.nwm.api.services.ModelLufftWS501UMBWeatherService;
import com.nwm.api.services.ModelMeterIon8600Service;
import com.nwm.api.services.ModelPVMet100Service;
import com.nwm.api.services.ModelPVPInverterService;
import com.nwm.api.services.ModelPVPowered3550260500kwInverterService;
import com.nwm.api.services.ModelPoaTempService;
import com.nwm.api.services.ModelPowerMeasurementIon7650Service;
import com.nwm.api.services.ModelPyranometerPoaService;
import com.nwm.api.services.ModelRT1Class30000Service;
import com.nwm.api.services.ModelSatconPowergate225InverterService;
import com.nwm.api.services.ModelSatconPvs357InverterService;
import com.nwm.api.services.ModelShark100Service;
import com.nwm.api.services.ModelShark100TestService;
import com.nwm.api.services.ModelSolarEdgeInverterService;
import com.nwm.api.services.ModelSolectriaSGI226IVTService;
import com.nwm.api.services.ModelSunnyCentralClass9775InverterService;
import com.nwm.api.services.ModelTTiTrackerService;
import com.nwm.api.services.ModelVerisIndustriesE50c2aService;
import com.nwm.api.services.ModelVerisIndustriesE51c2PowerMeterService;
import com.nwm.api.services.ModelWKippZonenRT1Service;
import com.nwm.api.services.ModelWattsunTcuService;
import com.nwm.api.services.ModelWattsunTrackerService;
import com.nwm.api.services.ModelXantrexGT100250500Service;
import com.nwm.api.services.ModelXantrexGT500EService;
import com.nwm.api.services.ModelXantrexInverterService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.beans.PropertyDescriptor;
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
		
		if(serialnumber.equals("001EC605699B")) {
			System.out.println("---------------------------------start------------------------------");
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
			System.out.println("-------------------------------end--------------------------------");
		}
//		
		
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
							        
									List<DeviceEntity> scaledDeviceParameters = serviceD.getListScaledDeviceParameter(item);

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
													ModelPVPowered3550260500kwInverterEntity dataModelPVPowered = serviceModelPVPowered.setModelPVPowered3550260KWInverter(line);
													dataModelPVPowered.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelPVPowered3550260500kwInverterEntity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelPVPowered);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelPVPowered, scaledValue);
															if (slug.equals("OutputGeneration")) dataModelPVPowered.setNvmActivePower(scaledValue);
															if (slug.equals("TotalEnergyGeneration")) dataModelPVPowered.setNvmActiveEnergy(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													
													// OutputGeneration
													deviceUpdateE.setLast_updated(dataModelPVPowered.getTime());
													deviceUpdateE.setLast_value(dataModelPVPowered.getOutputGeneration() != 0.001 ? dataModelPVPowered.getOutputGeneration() : null);
													deviceUpdateE.setField_value1(dataModelPVPowered.getOutputGeneration() != 0.001 ? dataModelPVPowered.getOutputGeneration() : null);
													
													// DCInputVoltage
													deviceUpdateE.setField_value2(dataModelPVPowered.getDCInputVoltage() != 0.001 ? dataModelPVPowered.getDCInputVoltage() : null);
													
													// DCInputCurrent
													deviceUpdateE.setLast_value(dataModelPVPowered.getDCInputCurrent() != 0.001 ? dataModelPVPowered.getDCInputCurrent() : null);
													
													
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
													
													serviceModelPVPowered.insertModelPVPowered3550260KWInverter(dataModelPVPowered);
													
													// low production alert
													if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
														item.setLast_updated(deviceUpdateE.getLast_updated());
														serviceD.checkLowProduction(item);
													}
													
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
													
													ModelShark100Entity dataModelShark100 = serviceModelShark100.setModelShark100(line);
													dataModelShark100.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelShark100Entity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelShark100);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelShark100, scaledValue);
															if (slug.equals("watts_3ph_total")) dataModelShark100.setNvmActivePower(scaledValue);
															if (slug.equals("w_hours_total")) dataModelShark100.setNvmActiveEnergy(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													
													// watts_3ph_total
													deviceUpdateE.setLast_updated(dataModelShark100.getTime());
													deviceUpdateE.setLast_value(dataModelShark100.getWatts_3ph_total() != 0.001 ? dataModelShark100.getWatts_3ph_total() : null);
													deviceUpdateE.setField_value1(dataModelShark100.getWatts_3ph_total() != 0.001 ? dataModelShark100.getWatts_3ph_total() : null);
													
													// vars_3ph_total
													deviceUpdateE.setField_value2(dataModelShark100.getVars_3ph_total() != 0.001 ? dataModelShark100.getVars_3ph_total() : null);
													
													// vas_3ph_total
													deviceUpdateE.setField_value3(dataModelShark100.getVas_3ph_total() != 0.001 ? dataModelShark100.getVas_3ph_total() : null);
													
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
													
													serviceModelShark100.insertModelShark100(dataModelShark100);

													// low production alert
													if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
														item.setLast_updated(deviceUpdateE.getLast_updated());
														serviceD.checkLowProduction(item);
													}
													
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
													ModelRT1Class30000Entity dataModelRTC30000 = serviceModelRT1Class30000.setModelRT1Class30000(line);
													dataModelRTC30000.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelRT1Class30000Entity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelRTC30000);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelRTC30000, scaledValue);
															if (slug.equals("sensor1_data")) dataModelRTC30000.setNvm_irradiance(scaledValue);
															if (slug.equals("panel_temperature")) dataModelRTC30000.setNvm_temperature(scaledValue);
															if (slug.equals("panel_temperature")) dataModelRTC30000.setNvm_panel_temperature(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// sensor1_data
													deviceUpdateE.setLast_updated(dataModelRTC30000.getTime());
													deviceUpdateE.setLast_value(dataModelRTC30000.getSensor1_data() != 0.001 ? dataModelRTC30000.getSensor1_data() : null);
													deviceUpdateE.setField_value1(dataModelRTC30000.getSensor1_data() != 0.001 ? dataModelRTC30000.getSensor1_data() : null);
													
													// panel_temperature
													deviceUpdateE.setField_value2(dataModelRTC30000.getPanel_temperature() != 0.001 ? dataModelRTC30000.getPanel_temperature() : null);
													
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
													
													ModelKippZonenRT1Class8009Entity dataKippZonen = serviceModelKippzonen.setModelKippZonenRT1Class8009(line);
													dataKippZonen.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelKippZonenRT1Class8009Entity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataKippZonen);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataKippZonen, scaledValue);
															if (slug.equals("sensor1_data")) dataKippZonen.setNvm_irradiance(scaledValue);
															if (slug.equals("panel_temperature")) dataKippZonen.setNvm_temperature(scaledValue);
															if (slug.equals("panel_temperature")) dataKippZonen.setNvm_panel_temperature(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													
													// Sensor1_data
													deviceUpdateE.setLast_updated(dataKippZonen.getTime());
													deviceUpdateE.setLast_value(dataKippZonen.getSensor1_data() != 0.001 ? dataKippZonen.getSensor1_data() : null);
													deviceUpdateE.setField_value1(dataKippZonen.getSensor1_data() != 0.001 ? dataKippZonen.getSensor1_data() : null);
													
													// panel_temperature
													deviceUpdateE.setField_value2(dataKippZonen.getPanel_temperature() != 0.001 ? dataKippZonen.getPanel_temperature() : null);
													
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
													
													ModelIVTSolaronEXTEntity dataModelIVTSolaronEXT = serviceModelIVTSolaronEXT.setModelIVTSolaronEXT(line);
													dataModelIVTSolaronEXT.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelIVTSolaronEXTEntity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelIVTSolaronEXT);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelIVTSolaronEXT, scaledValue);
															if (slug.equals("ac_power")) dataModelIVTSolaronEXT.setNvmActivePower(scaledValue);
															if (slug.equals("ytd_kwh_total")) dataModelIVTSolaronEXT.setNvmActiveEnergy(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// ac_power
													deviceUpdateE.setLast_updated(dataModelIVTSolaronEXT.getTime());
													deviceUpdateE.setLast_value(dataModelIVTSolaronEXT.getAc_power() != 0.001 ? dataModelIVTSolaronEXT.getAc_power() : null);
													deviceUpdateE.setField_value1(dataModelIVTSolaronEXT.getAc_power() != 0.001 ? dataModelIVTSolaronEXT.getAc_power() : null);
													
													// ac_frequency
													deviceUpdateE.setField_value2(dataModelIVTSolaronEXT.getAc_frequency() != 0.001 ? dataModelIVTSolaronEXT.getAc_frequency() : null);
													
													// pv_voltage
													deviceUpdateE.setField_value3(dataModelIVTSolaronEXT.getPv_voltage() != 0.001 ? dataModelIVTSolaronEXT.getPv_voltage() : null);
													
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
													
													serviceModelIVTSolaronEXT.insertModelIVTSolaronEXT(dataModelIVTSolaronEXT);
													
													// low production alert
													if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
														item.setLast_updated(deviceUpdateE.getLast_updated());
														serviceD.checkLowProduction(item);
													}
													
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
													ModelHukselfluxSr30d1DeviceclassV0Entity dataModelHukselfluxSr30d1DeviceclassV0 = serviceModelHukselfluxSr30d1DeviceclassV0.setModelHukselfluxSr30d1DeviceclassV0(line);
													dataModelHukselfluxSr30d1DeviceclassV0.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelHukselfluxSr30d1DeviceclassV0Entity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelHukselfluxSr30d1DeviceclassV0);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelHukselfluxSr30d1DeviceclassV0, scaledValue);
															if (slug.equals("IrradianceTcs")) dataModelHukselfluxSr30d1DeviceclassV0.setNvm_irradiance(scaledValue);
															if (slug.equals("SensorBodyTemperature")) dataModelHukselfluxSr30d1DeviceclassV0.setNvm_temperature(scaledValue);
															if (slug.equals("SensorBodyTemperature")) dataModelHukselfluxSr30d1DeviceclassV0.setNvm_panel_temperature(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// IrradianceTcs
													deviceUpdateE.setLast_updated(dataModelHukselfluxSr30d1DeviceclassV0.getTime());
													deviceUpdateE.setLast_value(dataModelHukselfluxSr30d1DeviceclassV0.getIrradianceTcs() != 0.001 ? dataModelHukselfluxSr30d1DeviceclassV0.getIrradianceTcs() : null);
													deviceUpdateE.setField_value1(dataModelHukselfluxSr30d1DeviceclassV0.getIrradianceTcs() != 0.001 ? dataModelHukselfluxSr30d1DeviceclassV0.getIrradianceTcs() : null);
													
													// SensorBodyTemperature
													deviceUpdateE.setField_value2(dataModelHukselfluxSr30d1DeviceclassV0.getSensorBodyTemperature() != 0.001 ? dataModelHukselfluxSr30d1DeviceclassV0.getSensorBodyTemperature() : null);
													
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
													ModelIMTSolarClass8000Entity dataModelIMTSolarClass = serviceModelIMTSolarClass8000.setModelIMTSolarClass8000(line);
													dataModelIMTSolarClass.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelIMTSolarClass8000Entity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelIMTSolarClass);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelIMTSolarClass, scaledValue);
															if (slug.equals("irradiance")) dataModelIMTSolarClass.setNvm_irradiance(scaledValue);
															if (slug.equals("tcell")) dataModelIMTSolarClass.setNvm_temperature(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													
													// irradiance
													deviceUpdateE.setLast_updated(dataModelIMTSolarClass.getTime());
													deviceUpdateE.setLast_value(dataModelIMTSolarClass.getIrradiance() != 0.001 ? dataModelIMTSolarClass.getIrradiance() : null);
													deviceUpdateE.setField_value1(dataModelIMTSolarClass.getIrradiance() != 0.001 ? dataModelIMTSolarClass.getIrradiance() : null);
													
													// tcell
													deviceUpdateE.setField_value2(dataModelIMTSolarClass.getTcell() != 0.001 ? dataModelIMTSolarClass.getTcell() : null);
													
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
													
													ModelIMTSolarTmodulClass8006Entity dataModelIMTSolarTmodulClass8006 = serviceModelIMTSolarTmodulClass8006.setDataModelIMTSolarTmodulClass8006(line);
													dataModelIMTSolarTmodulClass8006.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelIMTSolarTmodulClass8006Entity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelIMTSolarTmodulClass8006);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelIMTSolarTmodulClass8006, scaledValue);
															if (slug.equals("ModuleTemperature")) dataModelIMTSolarTmodulClass8006.setNvm_temperature(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// ModuleTemperature
													deviceUpdateE.setLast_updated(dataModelIMTSolarTmodulClass8006.getTime());
													deviceUpdateE.setLast_value(dataModelIMTSolarTmodulClass8006.getModuleTemperature() != 0.001 ? dataModelIMTSolarTmodulClass8006.getModuleTemperature() : null);
													deviceUpdateE.setField_value1(dataModelIMTSolarTmodulClass8006.getModuleTemperature() != 0.001 ? dataModelIMTSolarTmodulClass8006.getModuleTemperature() : null);
													
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
													ModelAdvancedEnergySolaronEntity dataModelAdvancedEnergySolaron = serviceModelAdvancedEnergySolaron.setModelAdvancedEnergySolaron(line);
													dataModelAdvancedEnergySolaron.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelAdvancedEnergySolaronEntity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelAdvancedEnergySolaron);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelAdvancedEnergySolaron, scaledValue);
															if (slug.equals("ac_power")) dataModelAdvancedEnergySolaron.setNvmActivePower(scaledValue);
															if (slug.equals("ytd_kwh_total")) dataModelAdvancedEnergySolaron.setNvmActiveEnergy(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													
													// ac_power
													deviceUpdateE.setLast_updated(dataModelAdvancedEnergySolaron.getTime());
													deviceUpdateE.setLast_value(dataModelAdvancedEnergySolaron.getAc_power() != 0.001 ? dataModelAdvancedEnergySolaron.getAc_power() : null);
													deviceUpdateE.setField_value1(dataModelAdvancedEnergySolaron.getAc_power() != 0.001 ? dataModelAdvancedEnergySolaron.getAc_power() : null);
													
													// ac_frequency
													deviceUpdateE.setField_value2(dataModelAdvancedEnergySolaron.getAc_frequency() != 0.001 ? dataModelAdvancedEnergySolaron.getAc_frequency() : null);
													
													// pv_voltage
													deviceUpdateE.setField_value3(dataModelAdvancedEnergySolaron.getPv_voltage() != 0.001 ? dataModelAdvancedEnergySolaron.getPv_voltage() : null);
													
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
													
													serviceModelAdvancedEnergySolaron.insertModelAdvancedEnergySolaron(dataModelAdvancedEnergySolaron);

													// low production alert
													if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
														item.setLast_updated(deviceUpdateE.getLast_updated());
														serviceD.checkLowProduction(item);
													}
													
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
											
										case "model_pvmet_100":
											ModelPVMet100Service servicePVMet100 = new ModelPVMet100Service();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													
													ModelPVMet100Entity dataPVMet100 = servicePVMet100.setModelPVMet100(line);
													dataPVMet100.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelPVMet100Entity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataPVMet100);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataPVMet100, scaledValue);
															if (slug.equals("TransientHorizontalIrradiance")) dataPVMet100.setNvm_irradiance(scaledValue);
															if (slug.equals("AmbientTemperature")) dataPVMet100.setNvm_temperature(scaledValue);
															if (slug.equals("Temperature")) dataPVMet100.setNvm_panel_temperature(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													
													// Irradiance
													deviceUpdateE.setLast_updated(dataPVMet100.getTime());
													deviceUpdateE.setLast_value(dataPVMet100.getTransientHorizontalIrradiance() != 0.001 ? dataPVMet100.getTransientHorizontalIrradiance() : null);
													deviceUpdateE.setField_value1(dataPVMet100.getTransientHorizontalIrradiance() != 0.001 ? dataPVMet100.getTransientHorizontalIrradiance() : null);
													
													// Ambient Temperature
													deviceUpdateE.setField_value2(dataPVMet100.getAmbientTemperature() != 0.001 ? dataPVMet100.getAmbientTemperature() : null);
													
													// PV Temperature Module
													deviceUpdateE.setField_value3(dataPVMet100.getTemperature() != 0.001 ? dataPVMet100.getTemperature() : null);
													
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
													
													servicePVMet100.insertModelPVMet100(dataPVMet100);
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
											
										case "model_pvp_inverter":
											ModelPVPInverterService serviceModelPVPInverter = new ModelPVPInverterService();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													ModelPVPInverterEntity dataModelPVPInverter = serviceModelPVPInverter.setModelPVPInverter(line);
													dataModelPVPInverter.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelPVPInverterEntity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelPVPInverter);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelPVPInverter, scaledValue);
															if (slug.equals("line_kw")) dataModelPVPInverter.setNvmActivePower(scaledValue);
															if (slug.equals("total_kwh_delivered")) dataModelPVPInverter.setNvmActiveEnergy(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// line_kw
													deviceUpdateE.setLast_updated(dataModelPVPInverter.getTime());
													deviceUpdateE.setLast_value(dataModelPVPInverter.getLine_kw() != 0.001 ? dataModelPVPInverter.getLine_kw() : null);
													deviceUpdateE.setField_value1(dataModelPVPInverter.getLine_kw() != 0.001 ? dataModelPVPInverter.getLine_kw() : null);
													
													// dc_output_voltage
													deviceUpdateE.setField_value2(dataModelPVPInverter.getDc_output_voltage() != 0.001 ? dataModelPVPInverter.getDc_output_voltage() : null);
													
													// dc_output_current
													deviceUpdateE.setField_value3(dataModelPVPInverter.getDc_output_current() != 0.001 ? dataModelPVPInverter.getDc_output_current() : null);
													
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
													
													serviceModelPVPInverter.insertModelPVPInverter(dataModelPVPInverter);

													// low production alert
													if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
														item.setLast_updated(deviceUpdateE.getLast_updated());
														serviceD.checkLowProduction(item);
													}
													
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
													ModelChintSolectriaInverterClass9725Entity dataModelChint = serviceModelChintSolectria.setModelChintSolectriaInverterClass9725(line);
													dataModelChint.setId_device(item.getId());
													
													System.out.println("id device: " + dataModelChint.getId_device() + " - word1: "+ dataModelChint.getTime() + "\n");
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelChintSolectriaInverterClass9725Entity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelChint);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelChint, scaledValue);
															if (slug.equals("AC_ActivePower")) dataModelChint.setNvmActivePower(scaledValue);
															if (slug.equals("TotalEnergyToEnergy")) dataModelChint.setNvmActiveEnergy(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													
													// AC_ActivePower
													deviceUpdateE.setLast_updated(dataModelChint.getTime());
													deviceUpdateE.setLast_value(dataModelChint.getAC_ActivePower() != 0.001 ? dataModelChint.getAC_ActivePower() : null);
													deviceUpdateE.setField_value1(dataModelChint.getAC_ActivePower() != 0.001 ? dataModelChint.getAC_ActivePower() : null);
													
													// AC_ApparentPower
													deviceUpdateE.setField_value2(dataModelChint.getAC_ApparentPower() != 0.001 ? dataModelChint.getAC_ApparentPower() : null);
													
													// PV1_Voltage
													deviceUpdateE.setField_value3(dataModelChint.getPV1_Voltage() != 0.001 ? dataModelChint.getPV1_Voltage() : null);
													
													
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
													
													
													serviceModelChintSolectria.insertModelChintSolectriaInverterClass9725(dataModelChint);

													// low production alert
													if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
														item.setLast_updated(deviceUpdateE.getLast_updated());
														serviceD.checkLowProduction(item);
													}
													
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
													ModelVerisIndustriesE51c2PowerMeterEntity dataModelVeris = serviceModelVeris.setModelChintSolectriaInverterClass9725(line);
													dataModelVeris.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelVerisIndustriesE51c2PowerMeterEntity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelVeris);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelVeris, scaledValue);
															if (slug.equals("TotalNetInstantaneousRealPower")) dataModelVeris.setNvmActivePower(scaledValue);
															if (slug.equals("AccumulatedRealEnergyNet")) dataModelVeris.setNvmActiveEnergy(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// TotalNetInstantaneousRealPower
													deviceUpdateE.setLast_updated(dataModelVeris.getTime());
													deviceUpdateE.setLast_value(dataModelVeris.getTotalNetInstantaneousRealPower() != 0.001 ? dataModelVeris.getTotalNetInstantaneousRealPower() : null);
													deviceUpdateE.setField_value1(dataModelVeris.getTotalNetInstantaneousRealPower() != 0.001 ? dataModelVeris.getTotalNetInstantaneousRealPower() : null);
													
													// RealPowerPhaseA
													deviceUpdateE.setField_value2(dataModelVeris.getRealPowerPhaseA() != 0.001 ? dataModelVeris.getRealPowerPhaseA() : null);
													
													// RealPowerPhaseB
													deviceUpdateE.setField_value3(dataModelVeris.getRealPowerPhaseB() != 0.001 ? dataModelVeris.getRealPowerPhaseB() : null);
													
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
													
													serviceModelVeris.insertModelVerisIndustriesE51c2PowerMeter(dataModelVeris);

													// low production alert
													if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
														item.setLast_updated(deviceUpdateE.getLast_updated());
														serviceD.checkLowProduction(item);
													}
													
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
													ModelSatconPvs357InverterEntity dataModelSatcon = serviceModelSatcon.setModelSatconPvs357Inverter(line);
													dataModelSatcon.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelSatconPvs357InverterEntity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelSatcon);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelSatcon, scaledValue);
															if (slug.equals("Output_kw")) dataModelSatcon.setNvmActivePower(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													//Output_kw
													deviceUpdateE.setLast_updated(dataModelSatcon.getTime());
													deviceUpdateE.setLast_value(dataModelSatcon.getOutput_kw() != 0.001 ? dataModelSatcon.getOutput_kw() : null);
													deviceUpdateE.setField_value1(dataModelSatcon.getOutput_kw() != 0.001 ? dataModelSatcon.getOutput_kw() : null);
													
													// Input_kW
													deviceUpdateE.setField_value2(dataModelSatcon.getInput_kW() != 0.001 ? dataModelSatcon.getInput_kW() : null);
													
													// DC_Input_Volts
													deviceUpdateE.setField_value3(dataModelSatcon.getDC_Input_Volts() != 0.001 ? dataModelSatcon.getDC_Input_Volts() : null);
													
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
													
													serviceModelSatcon.insertModelSatconPvs357Inverter(dataModelSatcon);

													// low production alert
													if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
														item.setLast_updated(deviceUpdateE.getLast_updated());
														serviceD.checkLowProduction(item);
													}
													
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
													ModelElkorWattsonPVMeterEntity dataModelElkor = serviceModelElkor.setModelElkorWattsonPVMeter(line);
													dataModelElkor.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelElkorWattsonPVMeterEntity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelElkor);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelElkor, scaledValue);
															if (slug.equals("TotalRealPower")) dataModelElkor.setNvmActivePower(scaledValue);
															if (slug.equals("TotalEnergyConsumption")) dataModelElkor.setNvmActiveEnergy(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// TotalRealPower
													deviceUpdateE.setLast_updated(dataModelElkor.getTime());
													deviceUpdateE.setLast_value(dataModelElkor.getTotalRealPower() != 0.001 ? dataModelElkor.getTotalRealPower() : null);
													deviceUpdateE.setField_value1(dataModelElkor.getTotalRealPower() != 0.001 ? dataModelElkor.getTotalRealPower() : null);
													
													// TotalReactivePower
													deviceUpdateE.setField_value2(dataModelElkor.getTotalReactivePower() != 0.001 ? dataModelElkor.getTotalReactivePower() : null);
													
													// TotalApparentPower
													deviceUpdateE.setField_value3(dataModelElkor.getTotalApparentPower() != 0.001 ? dataModelElkor.getTotalApparentPower() : null);
													
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
													
													serviceModelElkor.insertModelElkorWattsonPVMeter(dataModelElkor);

													// low production alert
													if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
														item.setLast_updated(deviceUpdateE.getLast_updated());
														serviceD.checkLowProduction(item);
													}
													
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
													ModelWKippZonenRT1Entity dataModelWkipp = serviceModelWkipp.setModelWKippZonenRT1(line);
													dataModelWkipp.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelWKippZonenRT1Entity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelWkipp);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelWkipp, scaledValue);
															if (slug.equals("SunPOATempComp")) dataModelWkipp.setNvm_irradiance(scaledValue);
															if (slug.equals("PanelTemperature")) dataModelWkipp.setNvm_temperature(scaledValue);
															if (slug.equals("PanelTemperature")) dataModelWkipp.setNvm_panel_temperature(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// SunPOATempComp
													deviceUpdateE.setLast_updated(dataModelWkipp.getTime());
													deviceUpdateE.setLast_value(dataModelWkipp.getSunPOATempComp() != 0.001 ? dataModelWkipp.getSunPOATempComp() : null);
													deviceUpdateE.setField_value1(dataModelWkipp.getSunPOATempComp() != 0.001 ? dataModelWkipp.getSunPOATempComp() : null);

													// PanelTemperature
													deviceUpdateE.setField_value2(dataModelWkipp.getPanelTemperature() != 0.001 ? dataModelWkipp.getPanelTemperature() : null);
													
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
													ModelElkorProductionMeterEntity dataModelElkorP = serviceModelElkorP.setModelElkorProductionMeter(line);
													dataModelElkorP.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelElkorProductionMeterEntity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelElkorP);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelElkorP, scaledValue);
															if (slug.equals("ActivePowerTotal")) dataModelElkorP.setNvmActivePower(scaledValue);
															if (slug.equals("TotalImportEnergy")) dataModelElkorP.setNvmActiveEnergy(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// ActivePowerTotal
													deviceUpdateE.setLast_updated(dataModelElkorP.getTime());
													deviceUpdateE.setLast_value(dataModelElkorP.getActivePowerTotal() != 0.001 ? dataModelElkorP.getActivePowerTotal() : null);
													deviceUpdateE.setField_value1(dataModelElkorP.getActivePowerTotal() != 0.001 ? dataModelElkorP.getActivePowerTotal() : null);
													
													// VoltageA
													deviceUpdateE.setField_value2(dataModelElkorP.getVoltageA() != 0.001 ? dataModelElkorP.getVoltageA() : null);
													
													// VoltageB
													deviceUpdateE.setField_value3(dataModelElkorP.getVoltageB() != 0.001 ? dataModelElkorP.getVoltageB() : null);
													
													
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
													
													serviceModelElkorP.insertModelElkorProductionMeter(dataModelElkorP);

													// low production alert
													if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
														item.setLast_updated(deviceUpdateE.getLast_updated());
														serviceD.checkLowProduction(item);
													}
													
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
													ModelAbbTrioClass6210Entity dataModelABB = serviceModelABB.setModelAbbTrioClass6210(line);
													dataModelABB.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelAbbTrioClass6210Entity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelABB);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelABB, scaledValue);
															if (slug.equals("GridPower")) dataModelABB.setNvmActivePower(scaledValue);
															if (slug.equals("TotalEnergy")) dataModelABB.setNvmActiveEnergy(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													
													// GridPower
													deviceUpdateE.setLast_updated(dataModelABB.getTime());
													deviceUpdateE.setLast_value(dataModelABB.getGridPower() != 0.001 ? dataModelABB.getGridPower() : null);
													deviceUpdateE.setField_value1(dataModelABB.getGridPower() != 0.001 ? dataModelABB.getGridPower() : null);
													
													// Input1Voltage
													deviceUpdateE.setField_value2(dataModelABB.getInput1Voltage() != 0.001 ? dataModelABB.getInput1Voltage() : null);
													
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
													
													serviceModelABB.insertModelAbbTrioClass6210(dataModelABB);

													// low production alert
													if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
														item.setLast_updated(deviceUpdateE.getLast_updated());
														serviceD.checkLowProduction(item);
													}
													
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
													ModelLufftClass8020Entity dataModelLufft = serviceModelLufft.setModelLufftClass8020(line);
													dataModelLufft.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelLufftClass8020Entity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelLufft);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelLufft, scaledValue);
															if (slug.equals("IrradianceActual")) dataModelLufft.setNvm_irradiance(scaledValue);
															if (slug.equals("AirTemperatureActual")) dataModelLufft.setNvm_temperature(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// AirTemperatureActual
													deviceUpdateE.setLast_updated(dataModelLufft.getTime());
													deviceUpdateE.setLast_value(dataModelLufft.getAirTemperatureActual() != 0.001 ? dataModelLufft.getAirTemperatureActual() : null);
													deviceUpdateE.setField_value1(dataModelLufft.getAirTemperatureActual() != 0.001 ? dataModelLufft.getAirTemperatureActual() : null);
													
													// IrradianceActual
													deviceUpdateE.setField_value2(dataModelLufft.getIrradianceActual() != 0.001 ? dataModelLufft.getIrradianceActual() : null);
													
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
													ModelLufftWS501UMBWeatherEntity dataModelLufft = serviceModelLufftWS501.setModelLufftWS501UMBWeather(line);
													dataModelLufft.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelLufftWS501UMBWeatherEntity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelLufft);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelLufft, scaledValue);
															if (slug.equals("GlobalRadiation")) dataModelLufft.setNvm_irradiance(scaledValue);
															if (slug.equals("AirTemperatureCActual")) dataModelLufft.setNvm_temperature(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// AirTemperatureActual
													deviceUpdateE.setLast_updated(dataModelLufft.getTime());
													deviceUpdateE.setLast_value(dataModelLufft.getAirTemperatureCActual() != 0.001 ? dataModelLufft.getAirTemperatureCActual() : null);
													deviceUpdateE.setField_value1(dataModelLufft.getAirTemperatureCActual() != 0.001 ? dataModelLufft.getAirTemperatureCActual() : null);
													
													// GlobalRadiation
													deviceUpdateE.setField_value2(dataModelLufft.getGlobalRadiation() != 0.001 ? dataModelLufft.getGlobalRadiation() : null);
													
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
													
													serviceModelLufftWS501.insertModelLufftWS501UMBWeather(dataModelLufft);
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
													ModelSolectriaSGI226IVTEntity dataModelSolectria226 = serviceModelSolectriaSGI226IVT.setModelSolectriaSGI226IVT(line);
													dataModelSolectria226.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelSolectriaSGI226IVTEntity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelSolectria226);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelSolectria226, scaledValue);
															if (slug.equals("ACPowerOutput")) dataModelSolectria226.setNvmActivePower(scaledValue);
															if (slug.equals("CumulativeACEnergy")) dataModelSolectria226.setNvmActiveEnergy(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													
													// ACPowerOutput
													deviceUpdateE.setLast_updated(dataModelSolectria226.getTime());
													deviceUpdateE.setLast_value(dataModelSolectria226.getACPowerOutput() != 0.001 ? dataModelSolectria226.getACPowerOutput() : null);
													deviceUpdateE.setField_value1(dataModelSolectria226.getACPowerOutput() != 0.001 ? dataModelSolectria226.getACPowerOutput() : null);
													
													// DCVoltage
													deviceUpdateE.setField_value2(dataModelSolectria226.getDCVoltage() != 0.001 ? dataModelSolectria226.getDCVoltage() : null);
													
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
													
													serviceModelSolectriaSGI226IVT.insertModelSolectriaSGI226IVT(dataModelSolectria226);

													// low production alert
													if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
														item.setLast_updated(deviceUpdateE.getLast_updated());
														serviceD.checkLowProduction(item);
													}
													
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
													ModelSolarEdgeInverterEntity dataModelSET = serviceModelSET.setModelSolarEdgeInverter(line);
													dataModelSET.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelSolarEdgeInverterEntity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelSET);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelSET, scaledValue);
															if (slug.equals("I_AC_Power")) dataModelSET.setNvmActivePower(scaledValue);
															if (slug.equals("I_AC_Energy_WH")) dataModelSET.setNvmActiveEnergy(scaledValue / 1000);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													
													// I_AC_Power
													deviceUpdateE.setLast_updated(dataModelSET.getTime());
													deviceUpdateE.setLast_value(dataModelSET.getI_AC_Power() != 0.001 ? dataModelSET.getI_AC_Power() : null);
													deviceUpdateE.setField_value1(dataModelSET.getI_AC_Power() != 0.001 ? dataModelSET.getI_AC_Power() : null);
													
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
													
													serviceModelSET.insertModelSolarEdgeInverter(dataModelSET);

													// low production alert
													if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
														item.setLast_updated(deviceUpdateE.getLast_updated());
														serviceD.checkLowProduction(item);
													}
													
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
													ModelXantrexGT100250500Entity dataModelXantrex = serviceModelXantrex.setModelXantrexGT100250500(line);
													dataModelXantrex.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelXantrexGT100250500Entity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelXantrex);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelXantrex, scaledValue);
															if (slug.equals("ReadPower")) dataModelXantrex.setNvmActivePower(scaledValue);
															if (slug.equals("AccumulatedEnergy")) dataModelXantrex.setNvmActiveEnergy(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// ReadPower
													deviceUpdateE.setLast_updated(dataModelXantrex.getTime());
													deviceUpdateE.setLast_value(dataModelXantrex.getReadPower() != 0.001 ? dataModelXantrex.getReadPower() : null);
													deviceUpdateE.setField_value1(dataModelXantrex.getReadPower() != 0.001 ? dataModelXantrex.getReadPower() : null);
													
													// PVVoltage
													deviceUpdateE.setField_value2(dataModelXantrex.getPVVoltage() != 0.001 ? dataModelXantrex.getPVVoltage() : null);
													
													// PVCurrent
													deviceUpdateE.setField_value3(dataModelXantrex.getPVCurrent() != 0.001 ? dataModelXantrex.getPVCurrent() : null);
													
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
													
													serviceModelXantrex.insertModelXantrexGT100250500(dataModelXantrex);

													// low production alert
													if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
														item.setLast_updated(deviceUpdateE.getLast_updated());
														serviceD.checkLowProduction(item);
													}
													
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
													ModelAdam4017WSClass8110Nelis190Entity dataModelAdam4017 = serviceModelAdam4017.setModelAdam4017WSClass8110Nelis190(line);
													dataModelAdam4017.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelAdam4017WSClass8110Nelis190Entity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelAdam4017);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelAdam4017, scaledValue);
															if (slug.equals("POACMP11")) dataModelAdam4017.setNvm_irradiance(scaledValue);
															if (slug.equals("AmbientTemp")) dataModelAdam4017.setNvm_temperature(scaledValue);
															if (slug.equals("PVPanelTemp")) dataModelAdam4017.setNvm_panel_temperature(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// POACMP11
													deviceUpdateE.setLast_updated(dataModelAdam4017.getTime());
													deviceUpdateE.setLast_value(dataModelAdam4017.getPOACMP11() != 0.001 ? dataModelAdam4017.getPOACMP11() : null);
													deviceUpdateE.setField_value1(dataModelAdam4017.getPOACMP11() != 0.001 ? dataModelAdam4017.getPOACMP11() : null);
													
													// AmbientTemp
													deviceUpdateE.setField_value2(dataModelAdam4017.getAmbientTemp() != 0.001 ? dataModelAdam4017.getAmbientTemp() : null);
													
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

											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													ModelCampellScientificMeter1Entity dataModelCSM1 = serviceModelCSM1.setModelCampellScientificMeter1(line);
													dataModelCSM1.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelCampellScientificMeter1Entity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelCSM1);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelCSM1, scaledValue);
															if (slug.equals("Meter1_ACPower")) dataModelCSM1.setNvmActivePower(scaledValue);
															if (slug.equals("Total_Energy")) dataModelCSM1.setNvmActiveEnergy(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// Meter1_ACPower
													deviceUpdateE.setLast_updated(dataModelCSM1.getTime());
													deviceUpdateE.setLast_value(dataModelCSM1.getMeter1_ACPower() != 0.001 ? dataModelCSM1.getMeter1_ACPower() : null);
													deviceUpdateE.setField_value1(dataModelCSM1.getMeter1_ACPower() != 0.001 ? dataModelCSM1.getMeter1_ACPower() : null);
													
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
													
													serviceModelCSM1.insertModelCampellScientificMeter1(dataModelCSM1);

													// low production alert
													if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
														item.setLast_updated(deviceUpdateE.getLast_updated());
														serviceD.checkLowProduction(item);
													}
													
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
													ModelCampellScientificMeter2Entity dataModelCSM2 = serviceModelCSM2.setModelCampellScientificMeter2(line);
													dataModelCSM2.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelCampellScientificMeter2Entity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelCSM2);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelCSM2, scaledValue);
															if (slug.equals("Meter2_ACPower")) dataModelCSM2.setNvmActivePower(scaledValue);
															if (slug.equals("Total_Energy")) dataModelCSM2.setNvmActiveEnergy(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// Meter2_ACPower
													deviceUpdateE.setLast_updated(dataModelCSM2.getTime());
													deviceUpdateE.setLast_value(dataModelCSM2.getMeter2_ACPower() != 0.001 ? dataModelCSM2.getMeter2_ACPower() : null);
													deviceUpdateE.setField_value1(dataModelCSM2.getMeter2_ACPower() != 0.001 ? dataModelCSM2.getMeter2_ACPower() : null);
													
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
													
													serviceModelCSM2.insertModelCampellScientificMeter2(dataModelCSM2);

													// low production alert
													if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
														item.setLast_updated(deviceUpdateE.getLast_updated());
														serviceD.checkLowProduction(item);
													}
													
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
													ModelCampellScientificMeter3Entity dataModelCSM3 = serviceModelCSM3.setModelCampellScientificMeter3(line);
													dataModelCSM3.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelCampellScientificMeter3Entity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelCSM3);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelCSM3, scaledValue);
															if (slug.equals("Meter3_ACPower")) dataModelCSM3.setNvmActivePower(scaledValue);
															if (slug.equals("Total_Energy")) dataModelCSM3.setNvmActiveEnergy(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// Meter3_ACPower
													deviceUpdateE.setLast_updated(dataModelCSM3.getTime());
													deviceUpdateE.setLast_value(dataModelCSM3.getMeter3_ACPower() != 0.001 ? dataModelCSM3.getMeter3_ACPower() : null);
													deviceUpdateE.setField_value1(dataModelCSM3.getMeter3_ACPower() != 0.001 ? dataModelCSM3.getMeter3_ACPower() : null);
													
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
													
													serviceModelCSM3.insertModelCampellScientificMeter3(dataModelCSM3);

													// low production alert
													if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
														item.setLast_updated(deviceUpdateE.getLast_updated());
														serviceD.checkLowProduction(item);
													}
													
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
													ModelCampellScientificMeter4Entity dataModelCSM4 = serviceModelCSM4.setModelCampellScientificMeter4(line);
													dataModelCSM4.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelCampellScientificMeter4Entity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelCSM4);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelCSM4, scaledValue);
															if (slug.equals("Meter4_ACPower")) dataModelCSM4.setNvmActivePower(scaledValue);
															if (slug.equals("Total_Energy")) dataModelCSM4.setNvmActiveEnergy(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// Meter4_ACPower
													deviceUpdateE.setLast_updated(dataModelCSM4.getTime());
													deviceUpdateE.setLast_value(dataModelCSM4.getMeter4_ACPower() != 0.001 ? dataModelCSM4.getMeter4_ACPower() : null);
													deviceUpdateE.setField_value1(dataModelCSM4.getMeter4_ACPower() != 0.001 ? dataModelCSM4.getMeter4_ACPower() : null);
													
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
													
													serviceModelCSM4.insertModelCampellScientificMeter4(dataModelCSM4);

													// low production alert
													if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
														item.setLast_updated(deviceUpdateE.getLast_updated());
														serviceD.checkLowProduction(item);
													}
													
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
											
											
										case "model_satcon_powergate_225_inverter": 
											ModelSatconPowergate225InverterService serviceModelSatcon225 = new ModelSatconPowergate225InverterService();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													ModelSatconPowergate225InverterEntity dataModelSatcon225 = serviceModelSatcon225.setModelSatconPowergate225Inverter(line);
													dataModelSatcon225.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelSatconPowergate225InverterEntity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelSatcon225);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelSatcon225, scaledValue);
															if (slug.equals("OutputKW")) dataModelSatcon225.setNvmActivePower(scaledValue);
															if (slug.equals("KWH")) dataModelSatcon225.setNvmActiveEnergy(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// OutputKW
														deviceUpdateE.setLast_updated(dataModelSatcon225.getTime());
														deviceUpdateE.setLast_value(dataModelSatcon225.getOutputKW() != 0.001 ? dataModelSatcon225.getOutputKW() : null);
														deviceUpdateE.setField_value1(dataModelSatcon225.getOutputKW() != 0.001 ? dataModelSatcon225.getOutputKW() : null);
													
													// Line Freq
													deviceUpdateE.setField_value2(dataModelSatcon225.getLineFreq() != 0.001 ? dataModelSatcon225.getLineFreq() : null);
													
													// DC Input Voltage
													deviceUpdateE.setField_value3(dataModelSatcon225.getDCInputVoltage() != 0.001 ? dataModelSatcon225.getDCInputVoltage() : null);
													
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
													
													serviceModelSatcon225.insertModelSatconPowergate225Inverter(dataModelSatcon225);

													// low production alert
													if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
														item.setLast_updated(deviceUpdateE.getLast_updated());
														serviceD.checkLowProduction(item);
													}
													
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



										case "model_sunny_central_class9775_inverter": 
											ModelSunnyCentralClass9775InverterService serviceModelSunnyClass9775 = new ModelSunnyCentralClass9775InverterService();
											// Check insert database status
											while ((line = br.readLine()) != null) {
												sb.append(line); // appends line to string buffer
												sb.append("\n"); // line feed
												// Convert string to array
												List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
												if (words.size() > 0) {
													ModelSunnyCentralClass9775InverterEntity dataModelSunnyClass9775 = serviceModelSunnyClass9775.setModelSunnyCentralClass9775Inverter(line);
													dataModelSunnyClass9775.setId_device(item.getId());
													
													// scaling device parameter
													if (scaledDeviceParameters.size() > 0) {
														for (int j = 0; j < scaledDeviceParameters.size(); j++) {
															DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
															String slug = scaledDeviceParameter.getParameter_slug();
															String scaleExpressions = scaledDeviceParameter.getParameter_scale();
															String variableName = scaledDeviceParameter.getVariable_name();
															PropertyDescriptor pd = new PropertyDescriptor(slug, ModelSunnyCentralClass9775InverterEntity.class);
															Double initialValue = (Double) pd.getReadMethod().invoke(dataModelSunnyClass9775);
															if (initialValue == 0.001) continue;
															Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
															pd.getWriteMethod().invoke(dataModelSunnyClass9775, scaledValue);
															if (slug.equals("ACPower")) dataModelSunnyClass9775.setNvmActivePower(scaledValue);
															if (slug.equals("LifekWhTotal")) dataModelSunnyClass9775.setNvmActiveEnergy(scaledValue);
														}
													}
													
													DeviceEntity deviceUpdateE = new DeviceEntity();
													// ACPower
													deviceUpdateE.setLast_updated(dataModelSunnyClass9775.getTime());
													deviceUpdateE.setLast_value(dataModelSunnyClass9775.getACPower() != 0.001 ? dataModelSunnyClass9775.getACPower() : null);
													deviceUpdateE.setField_value1(dataModelSunnyClass9775.getACPower() != 0.001 ? dataModelSunnyClass9775.getACPower() : null);
													
													// ACVoltage
													deviceUpdateE.setField_value2(dataModelSunnyClass9775.getACVoltage() != 0.001 ? dataModelSunnyClass9775.getACVoltage() : null);
													
													// InteriorTemperature
													deviceUpdateE.setField_value3(dataModelSunnyClass9775.getInteriorTemperature() != 0.001 ? dataModelSunnyClass9775.getInteriorTemperature() : null);
													
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
													
													serviceModelSunnyClass9775.insertModelSunnyCentralClass9775Inverter(dataModelSunnyClass9775);

													// low production alert
													if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
														item.setLast_updated(deviceUpdateE.getLast_updated());
														serviceD.checkLowProduction(item);
													}
													
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
										
											case "model_veris_industries_e50c2a":
												ModelVerisIndustriesE50c2aService serviceModelVeris50c2a = new ModelVerisIndustriesE50c2aService();
												// Check insert database status
												while ((line = br.readLine()) != null) {
													sb.append(line); // appends line to string buffer
													sb.append("\n"); // line feed
													// Convert string to array
													List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
													if (words.size() > 0) {
														ModelVerisIndustriesE50c2aEntity dataModelVeris = serviceModelVeris50c2a.setModelVerisIndustriesE50c2a(line);
														dataModelVeris.setId_device(item.getId());
														
														// scaling device parameter
														if (scaledDeviceParameters.size() > 0) {
															for (int j = 0; j < scaledDeviceParameters.size(); j++) {
																DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
																String slug = scaledDeviceParameter.getParameter_slug();
																String scaleExpressions = scaledDeviceParameter.getParameter_scale();
																String variableName = scaledDeviceParameter.getVariable_name();
																PropertyDescriptor pd = new PropertyDescriptor(slug, ModelVerisIndustriesE50c2aEntity.class);
																Double initialValue = (Double) pd.getReadMethod().invoke(dataModelVeris);
																if (initialValue == 0.001) continue;
																Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
																pd.getWriteMethod().invoke(dataModelVeris, scaledValue);
																if (slug.equals("TotalInstantaneousRealPower")) dataModelVeris.setNvmActivePower(scaledValue);
																if (slug.equals("RealEnergyConsumption")) dataModelVeris.setNvmActiveEnergy(scaledValue);
															}
														}
														
														DeviceEntity deviceUpdateE = new DeviceEntity();
														// TotalInstantaneousRealPower
														deviceUpdateE.setLast_updated(dataModelVeris.getTime());
														deviceUpdateE.setLast_value(dataModelVeris.getTotalInstantaneousRealPower() != 0.001 ? dataModelVeris.getTotalInstantaneousRealPower() : null);
														deviceUpdateE.setField_value1(dataModelVeris.getTotalInstantaneousRealPower() != 0.001 ? dataModelVeris.getTotalInstantaneousRealPower() : null);
														
														// RealPowerPhaseA
														deviceUpdateE.setField_value2(dataModelVeris.getRealPowerPhaseA() != 0.001 ? dataModelVeris.getRealPowerPhaseA() : null);
														
														// RealPowerPhaseB
														deviceUpdateE.setField_value3(dataModelVeris.getRealPowerPhaseB() != 0.001 ? dataModelVeris.getRealPowerPhaseB() : null);
														
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
														
														serviceModelVeris50c2a.insertModelVerisIndustriesE50c2a(dataModelVeris);

														// low production alert
														if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
															item.setLast_updated(deviceUpdateE.getLast_updated());
															serviceD.checkLowProduction(item);
														}
														
														try  
														{ 
															File logFile = new File(root.resolve(fileName).toString());
															if(logFile.delete()){  
//																System.out.println(logFile.getName() + " deleted .log");  
															}
															
															Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																	Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																	+ timeStamp + ".log.gz");
															File logGzFile = new File(path.toString());
															
															if(logGzFile.delete()) {  
//																System.out.println(logGzFile.getName() + " deleted .log.gz");   
															}		
														}  
														catch(Exception e){  
															e.printStackTrace();  
														}
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
														ModelAE1000NXClass9644Entity dataModelAE1000NX = serviceModelAE1000NX.setModelAE1000NXClass9644(line);
														dataModelAE1000NX.setId_device(item.getId());
														
														// scaling device parameter
														if (scaledDeviceParameters.size() > 0) {
															for (int j = 0; j < scaledDeviceParameters.size(); j++) {
																DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
																String slug = scaledDeviceParameter.getParameter_slug();
																String scaleExpressions = scaledDeviceParameter.getParameter_scale();
																String variableName = scaledDeviceParameter.getVariable_name();
																PropertyDescriptor pd = new PropertyDescriptor(slug, ModelAE1000NXClass9644Entity.class);
																Double initialValue = (Double) pd.getReadMethod().invoke(dataModelAE1000NX);
																if (initialValue == 0.001) continue;
																Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
																pd.getWriteMethod().invoke(dataModelAE1000NX, scaledValue);
																if (slug.equals("ACPower")) dataModelAE1000NX.setNvmActivePower(scaledValue);
																if (slug.equals("LifekWhTotal")) dataModelAE1000NX.setNvmActiveEnergy(scaledValue);
															}
														}
														
														DeviceEntity deviceUpdateE = new DeviceEntity();
														// AC Power
														deviceUpdateE.setLast_updated(dataModelAE1000NX.getTime());
														deviceUpdateE.setLast_value(dataModelAE1000NX.getACPower() != 0.001 ? dataModelAE1000NX.getACPower() : null);
														deviceUpdateE.setField_value1(dataModelAE1000NX.getACPower() != 0.001 ? dataModelAE1000NX.getACPower() : null);
														
														// AC Frequency
														deviceUpdateE.setField_value2(dataModelAE1000NX.getACFrequency() != 0.001 ? dataModelAE1000NX.getACFrequency() : null);
														
														// PV Voltage
														deviceUpdateE.setField_value3(dataModelAE1000NX.getPVVoltage() != 0.001 ? dataModelAE1000NX.getPVVoltage() : null);
														
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
														
														serviceModelAE1000NX.insertModelAE1000NXClass9644(dataModelAE1000NX);

														// low production alert
														if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
															item.setLast_updated(deviceUpdateE.getLast_updated());
															serviceD.checkLowProduction(item);
														}
														
														try  
														{ 
															File logFile = new File(root.resolve(fileName).toString());
															if(logFile.delete()){  
//																System.out.println(logFile.getName() + " deleted .log");  
															}
															
															Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																	Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																	+ timeStamp + ".log.gz");
															File logGzFile = new File(path.toString());
															
															if(logGzFile.delete()) {  
//																System.out.println(logGzFile.getName() + " deleted .log.gz");   
															}		
														}  
														catch(Exception e){  
															e.printStackTrace();  
														}
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
														ModelAesTxInverterEntity dataModelAesTx = serviceModelAesTx.setModelAesTxInverter(line);
														dataModelAesTx.setId_device(item.getId());
														
														// scaling device parameter
														if (scaledDeviceParameters.size() > 0) {
															for (int j = 0; j < scaledDeviceParameters.size(); j++) {
																DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
																String slug = scaledDeviceParameter.getParameter_slug();
																String scaleExpressions = scaledDeviceParameter.getParameter_scale();
																String variableName = scaledDeviceParameter.getVariable_name();
																PropertyDescriptor pd = new PropertyDescriptor(slug, ModelAesTxInverterEntity.class);
																Double initialValue = (Double) pd.getReadMethod().invoke(dataModelAesTx);
																if (initialValue == 0.001) continue;
																Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
																pd.getWriteMethod().invoke(dataModelAesTx, scaledValue);
																if (slug.equals("pt33")) dataModelAesTx.setNvmActivePower(scaledValue);
																if (slug.equals("pt34")) dataModelAesTx.setNvmActiveEnergy(scaledValue);
															}
														}
														
														DeviceEntity deviceUpdateE = new DeviceEntity();
														// pt33
														deviceUpdateE.setLast_updated(dataModelAesTx.getTime());
														deviceUpdateE.setLast_value(dataModelAesTx.getPt33() != 0.001 ? dataModelAesTx.getPt33() : null);
														deviceUpdateE.setField_value1(dataModelAesTx.getPt33() != 0.001 ? dataModelAesTx.getPt33() : null);
														
														deviceUpdateE.setField_value2(null);
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
														
														serviceModelAesTx.insertModelAesTxInverter(dataModelAesTx);

														// low production alert
														if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
															item.setLast_updated(deviceUpdateE.getLast_updated());
															serviceD.checkLowProduction(item);
														}
														
														try  
														{ 
															File logFile = new File(root.resolve(fileName).toString());
															if(logFile.delete()){  
//																System.out.println(logFile.getName() + " deleted .log");  
															}
															
															Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																	Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																	+ timeStamp + ".log.gz");
															File logGzFile = new File(path.toString());
															
															if(logGzFile.delete()) {  
//																System.out.println(logGzFile.getName() + " deleted .log.gz");   
															}		
														}  
														catch(Exception e){  
															e.printStackTrace();  
														}
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
														ModelMeterIon8600Entity dataModelIon = serviceModelIon.setModelMeterIon8600(line);
														dataModelIon.setId_device(item.getId());
														
														// scaling device parameter
														if (scaledDeviceParameters.size() > 0) {
															for (int j = 0; j < scaledDeviceParameters.size(); j++) {
																DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
																String slug = scaledDeviceParameter.getParameter_slug();
																String scaleExpressions = scaledDeviceParameter.getParameter_scale();
																String variableName = scaledDeviceParameter.getVariable_name();
																PropertyDescriptor pd = new PropertyDescriptor(slug, ModelMeterIon8600Entity.class);
																Double initialValue = (Double) pd.getReadMethod().invoke(dataModelIon);
																if (initialValue == 0.001) continue;
																Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
																pd.getWriteMethod().invoke(dataModelIon, scaledValue);
																if (slug.equals("kWTot")) dataModelIon.setNvmActivePower(scaledValue);
																if (slug.equals("kWhDelRec")) dataModelIon.setNvmActiveEnergy(scaledValue);
															}
														}
														
														DeviceEntity deviceUpdateE = new DeviceEntity();
														// kWTot
														deviceUpdateE.setLast_updated(dataModelIon.getTime());
														deviceUpdateE.setLast_value(dataModelIon.getKWTot() != 0.001 ? dataModelIon.getKWTot() : null);
														deviceUpdateE.setField_value1(dataModelIon.getKWTot() != 0.001 ? dataModelIon.getKWTot() : null);
														
														deviceUpdateE.setField_value2(null);
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
														
														serviceModelIon.insertModelMeterIon8600(dataModelIon);

														// low production alert
														if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
															item.setLast_updated(deviceUpdateE.getLast_updated());
															serviceD.checkLowProduction(item);
														}
														
														try  
														{ 
															File logFile = new File(root.resolve(fileName).toString());
															if(logFile.delete()){  
//																System.out.println(logFile.getName() + " deleted .log");  
															}
															
															Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																	Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																	+ timeStamp + ".log.gz");
															File logGzFile = new File(path.toString());
															
															if(logGzFile.delete()) {  
//																System.out.println(logGzFile.getName() + " deleted .log.gz");   
															}		
														}  
														catch(Exception e){  
															e.printStackTrace();  
														}
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
														ModelPowerMeasurementIon7650Entity dataModelPM7650 = serviceModelPM7650.setModelPowerMeasurementIon7650(line);
														dataModelPM7650.setId_device(item.getId());
														
														// scaling device parameter
														if (scaledDeviceParameters.size() > 0) {
															for (int j = 0; j < scaledDeviceParameters.size(); j++) {
																DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
																String slug = scaledDeviceParameter.getParameter_slug();
																String scaleExpressions = scaledDeviceParameter.getParameter_scale();
																String variableName = scaledDeviceParameter.getVariable_name();
																PropertyDescriptor pd = new PropertyDescriptor(slug, ModelMeterIon8600Entity.class);
																Double initialValue = (Double) pd.getReadMethod().invoke(dataModelPM7650);
																if (initialValue == 0.001) continue;
																Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
																pd.getWriteMethod().invoke(dataModelPM7650, scaledValue);
																if (slug.equals("kWTot")) dataModelPM7650.setNvmActivePower(scaledValue);
																if (slug.equals("kWhDel")) dataModelPM7650.setNvmActiveEnergy(scaledValue);
															}
														}
														
														DeviceEntity deviceUpdateE = new DeviceEntity();
														// kWTot
														deviceUpdateE.setLast_updated(dataModelPM7650.getTime());
														deviceUpdateE.setLast_value(dataModelPM7650.getkWTot() != 0.001 ? dataModelPM7650.getkWTot() : null);
														deviceUpdateE.setField_value1(dataModelPM7650.getkWTot() != 0.001 ? dataModelPM7650.getkWTot() : null);
														
														deviceUpdateE.setField_value2(null);
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
														
														serviceModelPM7650.insertModelPowerMeasurementIon7650(dataModelPM7650);

														// low production alert
														if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
															item.setLast_updated(deviceUpdateE.getLast_updated());
															serviceD.checkLowProduction(item);
														}
														
														try  
														{ 
															File logFile = new File(root.resolve(fileName).toString());
															if(logFile.delete()){  
//																System.out.println(logFile.getName() + " deleted .log");  
															}
															
															Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																	Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																	+ timeStamp + ".log.gz");
															File logGzFile = new File(path.toString());
															
															if(logGzFile.delete()) {  
//																System.out.println(logGzFile.getName() + " deleted .log.gz");   
															}		
														}  
														catch(Exception e){  
															e.printStackTrace();  
														}
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
														ModelXantrexInverterEntity dataModelXantrex = serviceModelXINV.setModelXantrexInverter(line);
														dataModelXantrex.setId_device(item.getId());
														
														// scaling device parameter
														if (scaledDeviceParameters.size() > 0) {
															for (int j = 0; j < scaledDeviceParameters.size(); j++) {
																DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
																String slug = scaledDeviceParameter.getParameter_slug();
																String scaleExpressions = scaledDeviceParameter.getParameter_scale();
																String variableName = scaledDeviceParameter.getVariable_name();
																PropertyDescriptor pd = new PropertyDescriptor(slug, ModelXantrexGT100250500Entity.class);
																Double initialValue = (Double) pd.getReadMethod().invoke(dataModelXantrex);
																if (initialValue == 0.001) continue;
																Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
																pd.getWriteMethod().invoke(dataModelXantrex, scaledValue);
																if (slug.equals("ReadPower")) dataModelXantrex.setNvmActivePower(scaledValue);
																if (slug.equals("kWh")) dataModelXantrex.setNvmActiveEnergy(scaledValue);
															}
														}
														
														DeviceEntity deviceUpdateE = new DeviceEntity();
														// ReadPower
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(dataModelXantrex.getReadPower() != 0.001 ? dataModelXantrex.getReadPower() : null);
														deviceUpdateE.setField_value1(dataModelXantrex.getReadPower() != 0.001 ? dataModelXantrex.getReadPower() : null);
														
														// PVVoltage
														deviceUpdateE.setField_value2(dataModelXantrex.getPVVoltage() != 0.001 ? dataModelXantrex.getPVVoltage() : null);
														
														// PVCurrent
														deviceUpdateE.setField_value3(dataModelXantrex.getPVCurrent() != 0.001 ? dataModelXantrex.getPVCurrent() : null);
														
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
														
														serviceModelXINV.insertModelXantrexInverter(dataModelXantrex);

														// low production alert
														if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
															item.setLast_updated(deviceUpdateE.getLast_updated());
															serviceD.checkLowProduction(item);
														}
														
														try  
														{ 
															File logFile = new File(root.resolve(fileName).toString());
															if(logFile.delete()){  
//																System.out.println(logFile.getName() + " deleted .log");  
															}
															
															Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																	Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																	+ timeStamp + ".log.gz");
															File logGzFile = new File(path.toString());
															
															if(logGzFile.delete()) {  
//																System.out.println(logGzFile.getName() + " deleted .log.gz");   
															}		
														}  
														catch(Exception e){  
//															System.out.println("e1: " + e);
															e.printStackTrace();  
														}
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
														
														ModelPoaTempEntity dataModel = serviceModelPoaTemp.setModelPoaTemp(line);
														dataModel.setId_device(item.getId());
														
														// scaling device parameter
														if (scaledDeviceParameters.size() > 0) {
															for (int j = 0; j < scaledDeviceParameters.size(); j++) {
																DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
																String slug = scaledDeviceParameter.getParameter_slug();
																String scaleExpressions = scaledDeviceParameter.getParameter_scale();
																String variableName = scaledDeviceParameter.getVariable_name();
																PropertyDescriptor pd = new PropertyDescriptor(slug, ModelPoaTempEntity.class);
																Double initialValue = (Double) pd.getReadMethod().invoke(dataModel);
																if (initialValue == 0.001) continue;
																Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
																pd.getWriteMethod().invoke(dataModel, scaledValue);
																if (slug.equals("T_AMB")) dataModel.setNvm_temperature(scaledValue);
																if (slug.equals("T_AMB")) dataModel.setNvm_panel_temperature(scaledValue);
															}
														}
														
														DeviceEntity deviceUpdateE = new DeviceEntity();
														
														// T_AMB
														deviceUpdateE.setLast_updated(dataModel.getTime());
														deviceUpdateE.setLast_value(dataModel.getT_AMB() != 0.001 ? dataModel.getT_AMB() : null);
														deviceUpdateE.setField_value1(dataModel.getT_AMB() != 0.001 ? dataModel.getT_AMB() : null);
														
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
														
														serviceModelPoaTemp.insertModelPoaTemp(dataModel);
														try  
														{ 
															File logFile = new File(root.resolve(fileName).toString());
															if(logFile.delete()){  
//																System.out.println(logFile.getName() + " deleted .log");  
															}
															
															Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																	Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																	+ timeStamp + ".log.gz");
															File logGzFile = new File(path.toString());
															
															if(logGzFile.delete()) {  
//																System.out.println(logGzFile.getName() + " deleted .log.gz");   
															}		
														}  
														catch(Exception e){  
															e.printStackTrace();  
														}
														
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
														ModelPyranometerPoaEntity dataModelPy = serviceModelPy.setModelPyranometer(line);
														dataModelPy.setId_device(item.getId());
														
														// scaling device parameter
														if (scaledDeviceParameters.size() > 0) {
															for (int j = 0; j < scaledDeviceParameters.size(); j++) {
																DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
																String slug = scaledDeviceParameter.getParameter_slug();
																String scaleExpressions = scaledDeviceParameter.getParameter_scale();
																String variableName = scaledDeviceParameter.getVariable_name();
																PropertyDescriptor pd = new PropertyDescriptor(slug, ModelPyranometerPoaEntity.class);
																Double initialValue = (Double) pd.getReadMethod().invoke(dataModelPy);
																if (initialValue == 0.001) continue;
																Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
																pd.getWriteMethod().invoke(dataModelPy, scaledValue);
//																if (slug.equals("ReadPower")) dataModelPy.setNvmActivePower(scaledValue);
//																if (slug.equals("kWh")) dataModelPy.setNvmActiveEnergy(scaledValue);
															}
														}
														
														DeviceEntity deviceUpdateE = new DeviceEntity();
														// ReadPower
														deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
														deviceUpdateE.setLast_value(dataModelPy.getPoa() != 0.001 ? dataModelPy.getPoa() : null);
														deviceUpdateE.setField_value1(dataModelPy.getPoa() != 0.001 ? dataModelPy.getPoa() : null);
														
														deviceUpdateE.setField_value2(null);
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
														
														serviceModelPy.insertModelPyranometer(dataModelPy);

														// low production alert
														if ((hours >= item.getStart_date_time()) && (hours <= item.getEnd_date_time())) {
															item.setLast_updated(deviceUpdateE.getLast_updated());
															serviceD.checkLowProduction(item);
														}
														
														try  
														{ 
															File logFile = new File(root.resolve(fileName).toString());
															if(logFile.delete()){  
//																System.out.println(logFile.getName() + " deleted .log");  
															}
															
															Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																	Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																	+ timeStamp + ".log.gz");
															File logGzFile = new File(path.toString());
															
															if(logGzFile.delete()) {  
//																System.out.println(logGzFile.getName() + " deleted .log.gz");   
															}		
														}  
														catch(Exception e){  
//															System.out.println("e1: " + e);
															e.printStackTrace();  
														}
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
														
														ModelERIWeatherICPClass8050Entity dataModel = serviceModelERIWeatherICPClass8050.setModelERIWeatherICPClass8050(line);
														dataModel.setId_device(item.getId());
														
														// scaling device parameter
														if (scaledDeviceParameters.size() > 0) {
															for (int j = 0; j < scaledDeviceParameters.size(); j++) {
																DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
																String slug = scaledDeviceParameter.getParameter_slug();
																String scaleExpressions = scaledDeviceParameter.getParameter_scale();
																String variableName = scaledDeviceParameter.getVariable_name();
																PropertyDescriptor pd = new PropertyDescriptor(slug, ModelPoaTempEntity.class);
																Double initialValue = (Double) pd.getReadMethod().invoke(dataModel);
																if (initialValue == 0.001) continue;
																Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
																pd.getWriteMethod().invoke(dataModel, scaledValue);
																if (slug.equals("solar_irradiation")) dataModel.setNvm_irradiance(scaledValue);
																if (slug.equals("ambient_temp")) dataModel.setNvm_temperature(scaledValue);
																if (slug.equals("panel_temp")) dataModel.setNvm_panel_temperature(scaledValue);
															}
														}
														
														DeviceEntity deviceUpdateE = new DeviceEntity();
														
														// solar_irradiation
														deviceUpdateE.setLast_updated(dataModel.getTime());
														deviceUpdateE.setLast_value(dataModel.getSolar_irradiation() != 0.001 ? dataModel.getSolar_irradiation() : null);
														deviceUpdateE.setField_value1(dataModel.getSolar_irradiation() != 0.001 ? dataModel.getSolar_irradiation() : null);
														
														// ambient_temp
														deviceUpdateE.setField_value2(dataModel.getAmbient_temp() != 0.001 ? dataModel.getAmbient_temp() : null);
														
														// panel_temp
														deviceUpdateE.setField_value3(dataModel.getPanel_temp() != 0.001 ? dataModel.getPanel_temp() : null);
														
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
														
														serviceModelERIWeatherICPClass8050.insertModelERIWeatherICPClass8050(dataModel);
														try  
														{ 
															File logFile = new File(root.resolve(fileName).toString());
															if(logFile.delete()){  
//																System.out.println(logFile.getName() + " deleted .log");  
															}
															
															Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																	Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																	+ timeStamp + ".log.gz");
															File logGzFile = new File(path.toString());
															
															if(logGzFile.delete()) {  
//																System.out.println(logGzFile.getName() + " deleted .log.gz");   
															}		
														}  
														catch(Exception e){  
															e.printStackTrace();  
														}
														
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
														
														ModelXantrexGT500EEntity dataModel = serviceModelgt500.setModelXantrexGT500E(line);
														dataModel.setId_device(item.getId());
														
														// scaling device parameter
														if (scaledDeviceParameters.size() > 0) {
															for (int j = 0; j < scaledDeviceParameters.size(); j++) {
																DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
																String slug = scaledDeviceParameter.getParameter_slug();
																String scaleExpressions = scaledDeviceParameter.getParameter_scale();
																String variableName = scaledDeviceParameter.getVariable_name();
																PropertyDescriptor pd = new PropertyDescriptor(slug, ModelPoaTempEntity.class);
																Double initialValue = (Double) pd.getReadMethod().invoke(dataModel);
																if (initialValue == 0.001) continue;
																Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
																pd.getWriteMethod().invoke(dataModel, scaledValue);
															}
														}
														
														DeviceEntity deviceUpdateE = new DeviceEntity();
														
														// solar_irradiation
														deviceUpdateE.setLast_updated(dataModel.getTime());
														deviceUpdateE.setLast_value(dataModel.getAC_POWER() != 0.001 ? dataModel.getAC_POWER() : null);
														deviceUpdateE.setField_value1(dataModel.getAC_POWER() != 0.001 ? dataModel.getAC_POWER() : null);
														
														// DC_POWER
														deviceUpdateE.setField_value2(null);
														deviceUpdateE.setField_value2(dataModel.getDC_POWER() != 0.001 ? dataModel.getDC_POWER() : null);
														
														//
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
														
														serviceModelgt500.insertModelXantrexGT500EService(dataModel);
														try  
														{ 
															File logFile = new File(root.resolve(fileName).toString());
															if(logFile.delete()){   }
															
															Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																	Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																	+ timeStamp + ".log.gz");
															File logGzFile = new File(path.toString());
															
															if(logGzFile.delete()) { }		
														}  
														catch(Exception e){  
															e.printStackTrace();  
														}
														
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
														DeviceEntity deviceUpdateE = new DeviceEntity();
														double setAngle = Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.0");
														
														// ReadAngle
														if(!Lib.isBlank(words.get(11))) {
															deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
															deviceUpdateE.setLast_value(!Lib.isBlank(words.get(11)) ? Double.parseDouble(String.valueOf(setAngle)) : null);
															deviceUpdateE.setField_value1(!Lib.isBlank(words.get(11)) ? Double.parseDouble(String.valueOf(setAngle)) : null);
														} else {
															deviceUpdateE.setLast_updated(null);
															deviceUpdateE.setLast_value(null);
															deviceUpdateE.setField_value1(null);
														}
														
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
														
														ModelWattsunTcuEntity dataModelTcu = serviceModelTcu.setModelWattsunTcu(line);
														dataModelTcu.setId_device(item.getId());
														serviceModelTcu.insertModelWattsunTcu(dataModelTcu);
														
														try  
														{ 
															File logFile = new File(root.resolve(fileName).toString());
															if(logFile.delete()){  }
															
															Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																	Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																	+ timeStamp + ".log.gz");
															File logGzFile = new File(path.toString());
															
															if(logGzFile.delete()) {  }		
														}  
														catch(Exception e){  
															e.printStackTrace();  
														}
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
														DeviceEntity deviceUpdateE = new DeviceEntity();
														double setAngle = Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.0");
														
														// ReadAngle
														if(!Lib.isBlank(words.get(8))) {
															deviceUpdateE.setLast_updated(words.get(0).replace("'", ""));
															deviceUpdateE.setLast_value(!Lib.isBlank(words.get(8)) ? Double.parseDouble(String.valueOf(setAngle)) : null);
															deviceUpdateE.setField_value1(!Lib.isBlank(words.get(8)) ? Double.parseDouble(String.valueOf(setAngle)) : null);
														} else {
															deviceUpdateE.setLast_updated(null);
															deviceUpdateE.setLast_value(null);
															deviceUpdateE.setField_value1(null);
														}
														
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
														
														ModelWattsunTrackerEntity dataModelWT = serviceModelWT.setModelWattsunTracker(line);
														dataModelWT.setId_device(item.getId());
														serviceModelWT.insertModelWattsunTracker(dataModelWT);
														
														try  
														{ 
															File logFile = new File(root.resolve(fileName).toString());
															if(logFile.delete()){  }
															
															Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
																	Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice  + "-" + unique + "."
																	+ timeStamp + ".log.gz");
															File logGzFile = new File(path.toString());
															
															if(logGzFile.delete()) {  }		
														}  
														catch(Exception e){  
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
						
					} catch (Exception e) {
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
			
//			System.out.println("message: " + message);
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