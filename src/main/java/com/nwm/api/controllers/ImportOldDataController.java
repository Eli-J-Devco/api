/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import javax.validation.Valid;
import org.apache.commons.lang3.time.StopWatch;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.ImportOldDataEntity;
import com.nwm.api.services.ImportOldDataService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/import-old-data")
public class ImportOldDataController extends BaseController {

	/**
	 * @description Get all site by id_employee
	 * @author long.pham
	 * @since 2022-12-21
	 * @return data 
	 */
	@PostMapping("/get-list-all-site-by-employee")
	public Object getDropdownList(@RequestBody ImportOldDataEntity obj) {
		try {
			ImportOldDataService service = new ImportOldDataService();
			List data = service.getAllSiteByEmployeeId(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	
	/**
	 * @description Get list site by id_customer
	 * @author long.pham
	 * @since 2020-10-09
	 * @param id_customer
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-device-by-site-id")
	public Object getList(@RequestBody ImportOldDataEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			ImportOldDataService service = new ImportOldDataService();
			List data = service.getAllDeviceBySiteId(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	

	/**
	 * @description save error level
	 * @author long.pham
	 * @since 2021-02-26
	 * @param  screen_mode = 0:add, 1:edit
	 */

	@PostMapping("/save")
	public Object save(@Valid @RequestBody ImportOldDataEntity obj) {
		try {
			ImportOldDataService service = new ImportOldDataService();
			ImportOldDataEntity data = service.insertImportOldData(obj);
			if (data != null) {
				if (data.getRow() > 0) {
					return this.jsonResult(false, "Error date format at row " + data.getRow(), null, 0);
				}
				return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
			} else {
				return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description save error level
	 * @author long.pham
	 * @since 2023-05-31
	 * @param screen_mode = 0:add, 1:edit
	 */

	@PostMapping("/save-upload")
	public Object saveUpload(@Valid @RequestBody ImportOldDataEntity obj) {
		try {
			ImportOldDataService service = new ImportOldDataService();
			String fileName = "";
			String saveDir = "";

			if (!Lib.isBlank(obj.getFile_upload())) {
				saveDir = uploadRootPath() + "/" + Lib.getReourcePropValue(Constants.appConfigFileName,
						Constants.uploadFilePathConfigKeyOlddata);
				fileName = randomAlphabetic(16);
				String saveFileName = Lib.uploadFromBase64(obj.getFile_upload(), fileName, saveDir);

				String fileUrl = saveDir + "/" + saveFileName;
				List<Object> result = new ArrayList<Object>();
				
				try (InputStream is = new FileInputStream(fileUrl); ReadableWorkbook wb = new ReadableWorkbook(is)) {
					StopWatch watch = new StopWatch();
					watch.start();
					
					wb.getSheets().forEach(sheet -> {
						try (Stream<Row> rows = sheet.openStream()) {
							rows.skip(1).forEach(r -> {
								
								HashMap<String, String> rowItem = new HashMap<String, String>();
								try {
									String dateData = r.getCell(0).asDate().toString().replace("T", " ") + ":00Z";
									SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									Date date = formatter.parse(dateData);

									switch (obj.getTable_name()) {
									case "model_hukseflux_sr30d1_deviceclass_v0":
										rowItem.put("time", formatter.format(date));
										rowItem.put("local_time", formatter.format(date));
										rowItem.put("id_device", r.getCellText(1).toString());
										rowItem.put("error", r.getCellText(2).toString());
										rowItem.put("low_alarm", r.getCellText(3).toString());
										rowItem.put("high_alarm", r.getCellText(4).toString());
										rowItem.put("IrradianceTcs", r.getCellText(5).toString());
										rowItem.put("IrradianceUs", r.getCellText(6).toString());
										rowItem.put("SensorBodyTemperature", r.getCellText(7).toString());
										rowItem.put("SensorElectricalResistance", r.getCellText(8).toString());
										rowItem.put("ScalingFactorIrradiance", r.getCellText(9).toString());
										rowItem.put("ScalingFactorTemperature", r.getCellText(10).toString());
										rowItem.put("SensorSerialNumber", r.getCellText(11).toString());
										rowItem.put("SensorSensitivity", r.getCellText(12).toString());
										rowItem.put("SensorCalibrationDate", r.getCellText(13).toString());
										rowItem.put("InternalHumidity", r.getCellText(14).toString());
										rowItem.put("TiltAngle", r.getCellText(15).toString());
										rowItem.put("TiltAngleaverage", r.getCellText(16).toString());
										rowItem.put("FanSpeedRPM", r.getCellText(17).toString());
										rowItem.put("HeaterCurrent", r.getCellText(18).toString());
										rowItem.put("FanCurrent", r.getCellText(19).toString());
										rowItem.put("nvm_irradiance", r.getCellText(20).toString());
										rowItem.put("nvm_temperature", r.getCellText(21).toString());
										
										break;
									case "model_imtsolar_tmodul_class8006":
										rowItem.put("time", formatter.format(date));
										rowItem.put("local_time", formatter.format(date));
										rowItem.put("id_device", r.getCellText(1).toString());
										rowItem.put("error", r.getCellText(2).toString());
										rowItem.put("low_alarm", r.getCellText(3).toString());
										rowItem.put("high_alarm", r.getCellText(4).toString());
										rowItem.put("ModuleTemperature", r.getCellText(5).toString());
										rowItem.put("nvm_irradiance", r.getCellText(6).toString());
										rowItem.put("nvm_temperature", r.getCellText(7).toString());
										
										break;
									case "model_xantrex_gt100_250_500":
										rowItem.put("time", formatter.format(date));
										rowItem.put("local_time", formatter.format(date));
										rowItem.put("id_device", r.getCellText(1).toString());
										rowItem.put("error", r.getCellText(2).toString());
										rowItem.put("low_alarm", r.getCellText(3).toString());
										rowItem.put("high_alarm", r.getCellText(4).toString());
										rowItem.put("VAB", r.getCellText(5).toString());
										rowItem.put("VBC", r.getCellText(6).toString());
										rowItem.put("VCA", r.getCellText(7).toString());
										rowItem.put("CurrentA", r.getCellText(8).toString());
										rowItem.put("CurrentB", r.getCellText(9).toString());
										rowItem.put("CurrentC", r.getCellText(10).toString());
										rowItem.put("ReadPower", r.getCellText(11).toString());
										rowItem.put("PVVoltage", r.getCellText(12).toString());
										rowItem.put("PVCurrent", r.getCellText(13).toString());
										rowItem.put("PVPower", r.getCellText(14).toString());
										rowItem.put("GridFrequency", r.getCellText(15).toString());
										rowItem.put("SystemState", r.getCellText(16).toString());
										rowItem.put("GoalState", r.getCellText(17).toString());
										rowItem.put("FaultCode", r.getCellText(18).toString());
										rowItem.put("AccumulatedEnergy", r.getCellText(19).toString());
										rowItem.put("RMatrixTemp", r.getCellText(20).toString());
										rowItem.put("LMatrixTemp", r.getCellText(21).toString());
										rowItem.put("IntakeAirTemperature", r.getCellText(22).toString());
										rowItem.put("nvmActivePower", r.getCellText(23).toString());
										rowItem.put("nvmActiveEnergy", r.getCellText(24).toString());
										
										break;
										
									case "model_tti_tracker":
										rowItem.put("time", formatter.format(date));
										rowItem.put("local_time", formatter.format(date));
										rowItem.put("id_device", r.getCellText(1).toString());
										rowItem.put("error", r.getCellText(2).toString());
										rowItem.put("low_alarm", r.getCellText(3).toString());
										rowItem.put("high_alarm", r.getCellText(4).toString());
										rowItem.put("Mode", r.getCellText(5).toString());
										rowItem.put("SubMode", r.getCellText(6).toString());
										rowItem.put("MotorStatus", r.getCellText(7).toString());
										rowItem.put("ReadAngle", r.getCellText(8).toString());
										rowItem.put("SetAngle", r.getCellText(9).toString());
										rowItem.put("OptimalAngle", r.getCellText(10).toString());
										rowItem.put("WindSpeed", r.getCellText(11).toString());
										rowItem.put("TTiTime", r.getCellText(12).toString());
										rowItem.put("MotorFault", r.getCellText(13).toString());
										rowItem.put("RemoteInterfaceFault", r.getCellText(14).toString());
										rowItem.put("InclinometerFault", r.getCellText(15).toString());
										rowItem.put("ModbusAddress", r.getCellText(16).toString());
										rowItem.put("FirmwareVersion", r.getCellText(17).toString());
										rowItem.put("Units", r.getCellText(18).toString());
										rowItem.put("InclinometerOffset", r.getCellText(19).toString());
										rowItem.put("MotorStopDelay", r.getCellText(20).toString());
										rowItem.put("CoastAngle", r.getCellText(21).toString());
										rowItem.put("MaxRotationWest", r.getCellText(22).toString());
										rowItem.put("MaxRotationEast", r.getCellText(23).toString());
										rowItem.put("SoftAngleLimitsEnabled", r.getCellText(24).toString());
										rowItem.put("MotorMonitorSampleTime", r.getCellText(25).toString());
										rowItem.put("MotorMonitorMinAngle", r.getCellText(26).toString());
										rowItem.put("EnableMotorMonitor", r.getCellText(27).toString());
										rowItem.put("DeadBand", r.getCellText(28).toString());
										rowItem.put("NightTimeStowAltitude", r.getCellText(29).toString());
										rowItem.put("NightTimeStowAngle", r.getCellText(30).toString());
										rowItem.put("PoleSpacing", r.getCellText(31).toString());
										rowItem.put("ModuleWidth", r.getCellText(32).toString());
										rowItem.put("MotorPolarity", r.getCellText(33).toString());
										rowItem.put("InclinometerPolarity", r.getCellText(34).toString());
										rowItem.put("Latitude", r.getCellText(35).toString());
										rowItem.put("Longitude", r.getCellText(36).toString());
										rowItem.put("LoggingInterval", r.getCellText(37).toString());
										rowItem.put("HelicalVarationAngle", r.getCellText(38).toString());
										rowItem.put("DriveArmSlope", r.getCellText(39).toString());
										rowItem.put("WindConstant", r.getCellText(40).toString());
										rowItem.put("WindStowSpeed", r.getCellText(41).toString());
										rowItem.put("WindStowTime", r.getCellText(42).toString());
										
										break;
									case "model_solectria_sgi_226ivt":
										rowItem.put("time", formatter.format(date));
										rowItem.put("local_time", formatter.format(date));
										rowItem.put("id_device", r.getCellText(1).toString());
										rowItem.put("error", r.getCellText(2).toString());
										rowItem.put("low_alarm", r.getCellText(3).toString());
										rowItem.put("high_alarm", r.getCellText(4).toString());
										rowItem.put("DCVoltage", r.getCellText(5).toString());
										rowItem.put("ACPowerOutput", r.getCellText(6).toString());
										rowItem.put("ACGridFrequency", r.getCellText(7).toString());
										rowItem.put("ACPowerStageCurrent", r.getCellText(8).toString());
										rowItem.put("L1toL2ACVoltage", r.getCellText(9).toString());
										rowItem.put("L2toL3ACVoltage", r.getCellText(10).toString());
										rowItem.put("L1toL3ACVoltage", r.getCellText(11).toString());
										rowItem.put("PhaseSequence", r.getCellText(12).toString());
										rowItem.put("CumulativeACEnergy", r.getCellText(13).toString());
										rowItem.put("CumulativeOngridHours", r.getCellText(14).toString());
										rowItem.put("FanOntimeHours", r.getCellText(15).toString());
										rowItem.put("ACContactorCycles", r.getCellText(16).toString());
										rowItem.put("SlaveID", r.getCellText(17).toString());
										rowItem.put("CriticalAlarms", r.getCellText(18).toString());
										rowItem.put("InformativeAlarms", r.getCellText(19).toString());
										rowItem.put("nvmActivePower", r.getCellText(20).toString());
										rowItem.put("nvmActiveEnergy", r.getCellText(21).toString());
										
										break;
									case "model_pv_powered_35_50_260_500kw_inverter":
										rowItem.put("time", formatter.format(date));
										rowItem.put("local_time", formatter.format(date));
										rowItem.put("id_device", r.getCellText(1).toString());
										rowItem.put("error", r.getCellText(2).toString());
										rowItem.put("low_alarm", r.getCellText(3).toString());
										rowItem.put("high_alarm", r.getCellText(4).toString());
										rowItem.put("InverterIDASCIICHAR0001", r.getCellText(5).toString());
										rowItem.put("InverterIDASCIICHAR0203", r.getCellText(6).toString());
										rowItem.put("InverterIDASCIICHAR0405", r.getCellText(7).toString());
										rowItem.put("InverterIDASCIICHAR0607", r.getCellText(8).toString());
										rowItem.put("InverterIDASCIICHAR0809", r.getCellText(9).toString());
										rowItem.put("InverterIDASCIICHAR1011", r.getCellText(10).toString());
										rowItem.put("InverterIDASCIICHAR1213", r.getCellText(11).toString());
										rowItem.put("InverterIDASCIICHAR1415", r.getCellText(12).toString());
										rowItem.put("FirmwareVersionASCIICHAR0001", r.getCellText(13).toString());
										rowItem.put("FirmwareVersionASCIICHAR0203", r.getCellText(14).toString());
										rowItem.put("FirmwareVersionASCIICHAR0405", r.getCellText(15).toString());
										rowItem.put("FirmwareVersionASCIICHAR0607", r.getCellText(16).toString());
										rowItem.put("MapVersion", r.getCellText(17).toString());
										rowItem.put("InverterConfiguration", r.getCellText(18).toString());
										rowItem.put("InverterSerialASCIICHAR0001", r.getCellText(19).toString());
										rowItem.put("InverterSerialASCIICHAR0203", r.getCellText(20).toString());
										rowItem.put("InverterSerialASCIICHAR0405", r.getCellText(21).toString());
										rowItem.put("InverterSerialASCIICHAR0607", r.getCellText(22).toString());
										rowItem.put("InverterSerialASCIICHAR0809", r.getCellText(23).toString());
										rowItem.put("InverterSerialASCIICHAR1011", r.getCellText(24).toString());
										rowItem.put("InverterSerialASCIICHAR1213", r.getCellText(25).toString());
										rowItem.put("InverterSerialASCIICHAR1415", r.getCellText(26).toString());
										rowItem.put("InverterSerialASCIICHAR1617", r.getCellText(27).toString());
										rowItem.put("InverterSerialASCIICHAR1819", r.getCellText(28).toString());
										rowItem.put("VoltageAN", r.getCellText(29).toString());
										rowItem.put("VoltageBN", r.getCellText(30).toString());
										rowItem.put("VoltageCN", r.getCellText(31).toString());
										rowItem.put("CurrentA", r.getCellText(32).toString());
										rowItem.put("CurrentB", r.getCellText(33).toString());
										rowItem.put("CurrentC", r.getCellText(34).toString());
										rowItem.put("DCInputVoltage", r.getCellText(35).toString());
										rowItem.put("DCInputCurrent", r.getCellText(36).toString());
										rowItem.put("LineFrequency", r.getCellText(37).toString());
										rowItem.put("OutputGeneration", r.getCellText(38).toString());
										rowItem.put("TotalEnergyGeneration", r.getCellText(39).toString());
										rowItem.put("PVInputVoltage", r.getCellText(40).toString());
										rowItem.put("InputGenerationCalculated", r.getCellText(41).toString());
										rowItem.put("InverterOperatingStatus", r.getCellText(42).toString());
										rowItem.put("MainFault", r.getCellText(43).toString());
										rowItem.put("DriveFault", r.getCellText(44).toString());
										rowItem.put("VoltageFault", r.getCellText(45).toString());
										rowItem.put("GridFault", r.getCellText(46).toString());
										rowItem.put("TemperatureFault", r.getCellText(47).toString());
										rowItem.put("SystemFault", r.getCellText(48).toString());
										rowItem.put("SystemWarnings", r.getCellText(49).toString());
										rowItem.put("PVMStatusCodes", r.getCellText(50).toString());
										rowItem.put("nvmActivePower", r.getCellText(51).toString());
										rowItem.put("nvmActiveEnergy", r.getCellText(52).toString());
										
										break;
									case "model_lufft_class8020":
										rowItem.put("time", formatter.format(date));
										rowItem.put("local_time", formatter.format(date));
										rowItem.put("id_device", r.getCellText(1).toString());
										rowItem.put("error", r.getCellText(2).toString());
										rowItem.put("low_alarm", r.getCellText(3).toString());
										rowItem.put("high_alarm", r.getCellText(4).toString());
										rowItem.put("RelativeHumidityActual", r.getCellText(5).toString());
										rowItem.put("RelativeHumidityMin", r.getCellText(6).toString());
										rowItem.put("RelativeHumidityMax", r.getCellText(7).toString());
										rowItem.put("RelativeHumidityAvg", r.getCellText(8).toString());
										rowItem.put("RelativeAirPressureActual", r.getCellText(9).toString());
										rowItem.put("RelativeAirPressureMin", r.getCellText(10).toString());
										rowItem.put("RelativeAirPressureMax", r.getCellText(11).toString());
										rowItem.put("RelativeAirPressureAvg", r.getCellText(12).toString());
										rowItem.put("WindDirectionActual", r.getCellText(13).toString());
										rowItem.put("WindDirectionMin", r.getCellText(14).toString());
										rowItem.put("WindDirectionMax", r.getCellText(15).toString());
										rowItem.put("WindDirectionVct", r.getCellText(16).toString());
										rowItem.put("WindDirectionFast", r.getCellText(17).toString());
										rowItem.put("WindDirectionCompassCorrected", r.getCellText(18).toString());
										rowItem.put("Compass", r.getCellText(19).toString());
										rowItem.put("PrecipitationType", r.getCellText(20).toString());
										rowItem.put("WindMeasurementQuality", r.getCellText(21).toString());
										rowItem.put("IrradianceActual", r.getCellText(22).toString());
										rowItem.put("IrradianceMin", r.getCellText(23).toString());
										rowItem.put("IrradianceMax", r.getCellText(24).toString());
										rowItem.put("IrradianceAvg", r.getCellText(25).toString());
										rowItem.put("AirTemperatureActual", r.getCellText(26).toString());
										rowItem.put("AirTemperatureMin", r.getCellText(27).toString());
										rowItem.put("AirTemperatureMax", r.getCellText(28).toString());
										rowItem.put("AirTemperatureAvg", r.getCellText(29).toString());
										rowItem.put("DewPointActual", r.getCellText(30).toString());
										rowItem.put("DewPointMin", r.getCellText(31).toString());
										rowItem.put("DewPointMax", r.getCellText(32).toString());
										rowItem.put("DewPointAvg", r.getCellText(33).toString());
										rowItem.put("WindChillTemperature", r.getCellText(34).toString());
										rowItem.put("HeatingTemperatureWind", r.getCellText(35).toString());
										rowItem.put("HeatingTemperatureR2S", r.getCellText(36).toString());
										rowItem.put("WindSpeedActual", r.getCellText(37).toString());
										rowItem.put("WindSpeedMin", r.getCellText(38).toString());
										rowItem.put("WindSpeedMax", r.getCellText(39).toString());
										rowItem.put("WindSpeedAvg", r.getCellText(40).toString());
										rowItem.put("WindSpeedVct", r.getCellText(41).toString());
										rowItem.put("WindSpeedFast", r.getCellText(42).toString());
										rowItem.put("PrecipitationQuantityAbsolute", r.getCellText(43).toString());
										rowItem.put("PrecipitationQuantityDifferential", r.getCellText(44).toString());
										rowItem.put("PrecipitationIntensity", r.getCellText(45).toString());
										rowItem.put("AbsoluteHumidityActual", r.getCellText(46).toString());
										rowItem.put("AbsoluteHumidityMin", r.getCellText(47).toString());
										rowItem.put("AbsoluteHumidityMax", r.getCellText(48).toString());
										rowItem.put("AbsoluteHumidityAvg", r.getCellText(49).toString());
										rowItem.put("MixingRatioActual", r.getCellText(50).toString());
										rowItem.put("MixingRatioMin", r.getCellText(51).toString());
										rowItem.put("MixingRatioMax", r.getCellText(52).toString());
										rowItem.put("MixingRatioAvg", r.getCellText(53).toString());
										rowItem.put("AbsoluteAirPressureActual", r.getCellText(54).toString());
										rowItem.put("AbsoluteAirPressureMin", r.getCellText(55).toString());
										rowItem.put("AbsoluteAirPressureMax", r.getCellText(56).toString());
										rowItem.put("AbsoluteAirPressureAvg", r.getCellText(57).toString());
										rowItem.put("WindSpeedStandardDeviation", r.getCellText(58).toString());
										rowItem.put("WindDirectionStandardDeviation", r.getCellText(59).toString());
										rowItem.put("WetBulbTemperature", r.getCellText(60).toString());
										rowItem.put("SpecificEnthalpy", r.getCellText(61).toString());
										rowItem.put("AirDensityActual", r.getCellText(62).toString());
										rowItem.put("LeafWetnessActual", r.getCellText(63).toString());
										rowItem.put("LeafWetnessMin", r.getCellText(64).toString());
										rowItem.put("LeafWetnessMax", r.getCellText(65).toString());
										rowItem.put("LeafWetnessAvg", r.getCellText(66).toString());
										rowItem.put("LeafWetnessState", r.getCellText(67).toString());
										rowItem.put("ExternalTemperature", r.getCellText(68).toString());
										rowItem.put("WindValueQualityFast", r.getCellText(69).toString());
										rowItem.put("nvm_irradiance", r.getCellText(70).toString());
										rowItem.put("nvm_temperature", r.getCellText(71).toString());
										
										break;
									case "model_abb_trio_class6210":
										rowItem.put("time", formatter.format(date));
										rowItem.put("local_time", formatter.format(date));
										rowItem.put("id_device", r.getCellText(1).toString());
										rowItem.put("error", r.getCellText(2).toString());
										rowItem.put("low_alarm", r.getCellText(3).toString());
										rowItem.put("high_alarm", r.getCellText(4).toString());
										rowItem.put("AuroraType", r.getCellText(5).toString());
										rowItem.put("GridType", r.getCellText(6).toString());
										rowItem.put("TransformerType", r.getCellText(7).toString());
										rowItem.put("StatesByte0", r.getCellText(8).toString());
										rowItem.put("StatesByte1", r.getCellText(9).toString());
										rowItem.put("StatesByte2", r.getCellText(10).toString());
										rowItem.put("StatesByte3", r.getCellText(11).toString());
										rowItem.put("StatesByte4", r.getCellText(12).toString());
										rowItem.put("TotalEnergy", r.getCellText(13).toString());
										rowItem.put("GridVoltage", r.getCellText(14).toString());
										rowItem.put("GridCurrent", r.getCellText(15).toString());
										rowItem.put("GridPower", r.getCellText(16).toString());
										rowItem.put("Frequency", r.getCellText(17).toString());
										rowItem.put("Input1Power", r.getCellText(18).toString());
										rowItem.put("Input1Voltage", r.getCellText(19).toString());
										rowItem.put("Input1Current", r.getCellText(20).toString());
										rowItem.put("Input2Power", r.getCellText(21).toString());
										rowItem.put("Input2Voltage", r.getCellText(22).toString());
										rowItem.put("Input2Current", r.getCellText(23).toString());
										rowItem.put("InverterTemperature", r.getCellText(24).toString());
										rowItem.put("BooseterTemperature", r.getCellText(25).toString());
										rowItem.put("IslolationResistance", r.getCellText(26).toString());
										rowItem.put("nvmActivePower", r.getCellText(27).toString());
										rowItem.put("nvmActiveEnergy", r.getCellText(28).toString());
										
										break;
									case "model_elkor_production_meter":
										rowItem.put("time", formatter.format(date));
										rowItem.put("local_time", formatter.format(date));
										rowItem.put("id_device", r.getCellText(1).toString());
										rowItem.put("error", r.getCellText(2).toString());
										rowItem.put("low_alarm", r.getCellText(3).toString());
										rowItem.put("high_alarm", r.getCellText(4).toString());
										rowItem.put("ActivePowerTotal", r.getCellText(5).toString());
										rowItem.put("ReactivePowerTotal", r.getCellText(6).toString());
										rowItem.put("ApparentPowerTotal", r.getCellText(7).toString());
										rowItem.put("VoltageAverage", r.getCellText(8).toString());
										rowItem.put("VoltageLLAverage", r.getCellText(9).toString());
										rowItem.put("CurrentAverage", r.getCellText(10).toString());
										rowItem.put("SystemPowerFactor", r.getCellText(11).toString());
										rowItem.put("SystemFrequency", r.getCellText(12).toString());
										rowItem.put("VoltageAverageAngle", r.getCellText(13).toString());
										rowItem.put("SystemQuadrant", r.getCellText(14).toString());
										rowItem.put("VoltageA", r.getCellText(15).toString());
										rowItem.put("VoltageB", r.getCellText(16).toString());
										rowItem.put("VoltageC", r.getCellText(17).toString());
										rowItem.put("VoltageAB", r.getCellText(18).toString());
										rowItem.put("VoltageBC", r.getCellText(19).toString());
										rowItem.put("VoltageAC", r.getCellText(20).toString());
										rowItem.put("CurrentA", r.getCellText(21).toString());
										rowItem.put("CurrentB", r.getCellText(22).toString());
										rowItem.put("CurrentC", r.getCellText(23).toString());
										rowItem.put("ActivePowerA", r.getCellText(24).toString());
										rowItem.put("ActivePowerB", r.getCellText(25).toString());
										rowItem.put("ActivePowerC", r.getCellText(26).toString());
										rowItem.put("ReactivePowerA", r.getCellText(27).toString());
										rowItem.put("ReactivePowerB", r.getCellText(28).toString());
										rowItem.put("ReactivePowerC", r.getCellText(29).toString());
										rowItem.put("ApparentPowerA", r.getCellText(30).toString());
										rowItem.put("ApparentPowerB", r.getCellText(31).toString());
										rowItem.put("ApparentPowerC", r.getCellText(32).toString());
										rowItem.put("PowerFactorA", r.getCellText(33).toString());
										rowItem.put("PowerFactorB", r.getCellText(34).toString());
										rowItem.put("PowerFactorC", r.getCellText(35).toString());
										rowItem.put("VoltageAngleAB", r.getCellText(36).toString());
										rowItem.put("VoltageAngleBC", r.getCellText(37).toString());
										rowItem.put("VoltageAngleCA", r.getCellText(38).toString());
										rowItem.put("QuadrantA", r.getCellText(39).toString());
										rowItem.put("QuadrantB", r.getCellText(40).toString());
										rowItem.put("QuadrantC", r.getCellText(41).toString());
										rowItem.put("SlidingWindowPower", r.getCellText(42).toString());
										rowItem.put("NetTotalEnergy", r.getCellText(43).toString());
										rowItem.put("TotalNetApparentEnergy", r.getCellText(44).toString());
										rowItem.put("TotalImportEnergy", r.getCellText(45).toString());
										rowItem.put("TotalExportEnergy", r.getCellText(46).toString());
										rowItem.put("TotalImportApparentEnergy", r.getCellText(47).toString());
										rowItem.put("TotalExportApparentEnergy", r.getCellText(48).toString());
										rowItem.put("Q1TotalReactiveEnergy", r.getCellText(49).toString());
										rowItem.put("Q2TotalReactiveEnergy", r.getCellText(50).toString());
										rowItem.put("Q3TotalReactiveEnergy", r.getCellText(51).toString());
										rowItem.put("Q4TotalReactiveEnergy", r.getCellText(52).toString());
										rowItem.put("Q1Q2TotalInductiveReactiveEnergy", r.getCellText(53).toString());
										rowItem.put("Q3Q4TotalCapacitiveReactiveEnergy", r.getCellText(54).toString());
										rowItem.put("NetEnergyA", r.getCellText(55).toString());
										rowItem.put("NetEnergyB", r.getCellText(56).toString());
										rowItem.put("NetEnergyC", r.getCellText(57).toString());
										rowItem.put("NetApparentEnergyA", r.getCellText(58).toString());
										rowItem.put("NetApparentEnergyB", r.getCellText(59).toString());
										rowItem.put("NetApparentEnergyC", r.getCellText(60).toString());
										rowItem.put("ImportEnergyA", r.getCellText(61).toString());
										rowItem.put("ImportEnergyB", r.getCellText(62).toString());
										rowItem.put("ImportEnergyC", r.getCellText(63).toString());
										rowItem.put("ExportEnergyA", r.getCellText(64).toString());
										rowItem.put("ExportEnergyB", r.getCellText(65).toString());
										rowItem.put("ExportEnergyC", r.getCellText(66).toString());
										rowItem.put("ImportApparentEnergyA", r.getCellText(67).toString());
										rowItem.put("ImportApparentEnergyB", r.getCellText(68).toString());
										rowItem.put("ImportApparentEnergyC", r.getCellText(69).toString());
										rowItem.put("ExportApparentEnergyA", r.getCellText(70).toString());
										rowItem.put("ExportApparentEnergyB", r.getCellText(71).toString());
										rowItem.put("ExportApparentEnergyC", r.getCellText(72).toString());
										rowItem.put("Q1ReactiveEnergyA", r.getCellText(73).toString());
										rowItem.put("Q1ReactiveEnergyB", r.getCellText(74).toString());
										rowItem.put("Q1ReactiveEnergyC", r.getCellText(75).toString());
										rowItem.put("Q2ReactiveEnergyA", r.getCellText(76).toString());
										rowItem.put("Q2ReactiveEnergyB", r.getCellText(77).toString());
										rowItem.put("Q2ReactiveEnergyC", r.getCellText(78).toString());
										
										rowItem.put("Q3ReactiveEnergyA", r.getCellText(79).toString());
										rowItem.put("Q3ReactiveEnergyB", r.getCellText(80).toString());
										rowItem.put("Q3ReactiveEnergyC", r.getCellText(81).toString());
										rowItem.put("Q4ReactiveEnergyA", r.getCellText(82).toString());
										rowItem.put("Q4ReactiveEnergyB", r.getCellText(83).toString());
										rowItem.put("Q4ReactiveEnergyC", r.getCellText(84).toString());
										rowItem.put("nvmActivePower", r.getCellText(85).toString());
										rowItem.put("nvmActiveEnergy", r.getCellText(86).toString());
										
										break;
									case "model_w_kipp_zonen_rt1":
										rowItem.put("time", formatter.format(date));
										rowItem.put("local_time", formatter.format(date));
										rowItem.put("id_device", r.getCellText(1).toString());
										rowItem.put("error", r.getCellText(2).toString());
										rowItem.put("low_alarm", r.getCellText(3).toString());
										rowItem.put("high_alarm", r.getCellText(4).toString());
										rowItem.put("DeviceType", r.getCellText(5).toString());
										rowItem.put("DataModelVersion", r.getCellText(6).toString());
										rowItem.put("OperationalMode", r.getCellText(7).toString());
										rowItem.put("StatusFlags", r.getCellText(8).toString());
										rowItem.put("SunPOATempComp", r.getCellText(9).toString());
										rowItem.put("PanelTemperature", r.getCellText(10).toString());
										rowItem.put("ExtPowerSensor", r.getCellText(11).toString());
										rowItem.put("BatchNumber", r.getCellText(12).toString());
										rowItem.put("SerialNumber", r.getCellText(13).toString());
										rowItem.put("CalibrationDateYYMMDD", r.getCellText(14).toString());
										rowItem.put("nvm_irradiance", r.getCellText(15).toString());
										rowItem.put("nvm_temperature", r.getCellText(16).toString());
										
										break;
									case "model_elkor_wattson_pv_meter":
										rowItem.put("time", formatter.format(date));
										rowItem.put("local_time", formatter.format(date));
										rowItem.put("id_device", r.getCellText(1).toString());
										rowItem.put("error", r.getCellText(2).toString());
										rowItem.put("low_alarm", r.getCellText(3).toString());
										rowItem.put("high_alarm", r.getCellText(4).toString());
										rowItem.put("TotalEnergyConsumption", r.getCellText(5).toString());
										rowItem.put("TotalRealPower", r.getCellText(6).toString());
										rowItem.put("TotalReactivePower", r.getCellText(7).toString());
										rowItem.put("TotalApparentPower", r.getCellText(8).toString());
										rowItem.put("AverageVoltageLN", r.getCellText(9).toString());
										rowItem.put("AverageVoltageLL", r.getCellText(10).toString());
										rowItem.put("AverageCurrent", r.getCellText(11).toString());
										rowItem.put("TotalSystemPowerFactor", r.getCellText(12).toString());
										rowItem.put("Frequency", r.getCellText(13).toString());
										rowItem.put("SlidingWindowRealPowerDemand", r.getCellText(14).toString());
										rowItem.put("VoltageAN", r.getCellText(15).toString());
										rowItem.put("VoltageBN", r.getCellText(16).toString());
										rowItem.put("VoltageCN", r.getCellText(17).toString());
										rowItem.put("VoltageAB", r.getCellText(18).toString());
										rowItem.put("VoltageBC", r.getCellText(19).toString());
										rowItem.put("VoltageAC", r.getCellText(20).toString());
										rowItem.put("CurrentA", r.getCellText(21).toString());
										rowItem.put("CurrentB", r.getCellText(22).toString());
										rowItem.put("CurrentC", r.getCellText(23).toString());
										rowItem.put("RealPowerA", r.getCellText(24).toString());
										rowItem.put("RealPowerB", r.getCellText(25).toString());
										rowItem.put("RealPowerC", r.getCellText(26).toString());
										rowItem.put("ReactivePowerA", r.getCellText(27).toString());
										rowItem.put("ReactivePowerB", r.getCellText(28).toString());
										rowItem.put("ReactivePowerC", r.getCellText(29).toString());
										rowItem.put("ApparentPowerA", r.getCellText(30).toString());
										rowItem.put("ApparentPowerB", r.getCellText(31).toString());
										rowItem.put("ApparentPowerC", r.getCellText(32).toString());
										rowItem.put("PowerFactorA", r.getCellText(33).toString());
										rowItem.put("PowerFactorB", r.getCellText(34).toString());
										rowItem.put("PowerFactorC", r.getCellText(35).toString());
										rowItem.put("nvmActivePower", r.getCellText(36).toString());
										rowItem.put("nvmActiveEnergy", r.getCellText(37).toString());
										
										break;
									case "model_satcon_pvs357_inverter":
										rowItem.put("time", formatter.format(date));
										rowItem.put("local_time", formatter.format(date));
										rowItem.put("id_device", r.getCellText(1).toString());
										rowItem.put("error", r.getCellText(2).toString());
										rowItem.put("low_alarm", r.getCellText(3).toString());
										rowItem.put("high_alarm", r.getCellText(4).toString());
										rowItem.put("Software_Identification_Number", r.getCellText(5).toString());
										rowItem.put("Fault_Word1", r.getCellText(6).toString());
										rowItem.put("Fault_Word2", r.getCellText(7).toString());
										rowItem.put("Fault_Word3", r.getCellText(8).toString());
										rowItem.put("Fault_Word4", r.getCellText(9).toString());
										rowItem.put("Fault_Word5", r.getCellText(10).toString());
										rowItem.put("Fault_Word6", r.getCellText(11).toString());
										rowItem.put("Fault_Word7", r.getCellText(12).toString());
										rowItem.put("Number_of_Faults", r.getCellText(13).toString());
										rowItem.put("Program_Checksum", r.getCellText(14).toString());
										rowItem.put("Parameter_Checksum", r.getCellText(15).toString());
										rowItem.put("DC_Input_Volts", r.getCellText(16).toString());
										rowItem.put("DC_Link_Volts", r.getCellText(17).toString());
										rowItem.put("DC_Link_Amps", r.getCellText(18).toString());
										rowItem.put("DC_Ground_Current", r.getCellText(19).toString());
										rowItem.put("Line_Amps_A", r.getCellText(20).toString());
										rowItem.put("Line_Amps_B", r.getCellText(21).toString());
										rowItem.put("Line_Amps_C", r.getCellText(22).toString());
										rowItem.put("Line_Amps_Average", r.getCellText(23).toString());
										rowItem.put("Neutral_Current", r.getCellText(24).toString());
										rowItem.put("Line_Volts_A", r.getCellText(25).toString());
										rowItem.put("Line_Volts_B", r.getCellText(26).toString());
										rowItem.put("Line_Volts_C", r.getCellText(27).toString());
										rowItem.put("Line_Volts_Average", r.getCellText(28).toString());
										rowItem.put("Line_Voltage_Unbalance", r.getCellText(29).toString());
										rowItem.put("Line_Current_Unbalance", r.getCellText(30).toString());
										rowItem.put("Input_kW", r.getCellText(31).toString());
										rowItem.put("Output_kw", r.getCellText(32).toString());
										rowItem.put("Output_kvar", r.getCellText(33).toString());
										rowItem.put("Output_kva", r.getCellText(34).toString());
										rowItem.put("Power_Factor", r.getCellText(35).toString());
										rowItem.put("Ground_Impedance", r.getCellText(36).toString());
										rowItem.put("String_Amps1", r.getCellText(37).toString());
										rowItem.put("String_Amps2", r.getCellText(38).toString());
										rowItem.put("String_Amps3", r.getCellText(39).toString());
										rowItem.put("String_Amps4", r.getCellText(40).toString());
										rowItem.put("String_Amps5", r.getCellText(41).toString());
										rowItem.put("String_Amps6", r.getCellText(42).toString());
										rowItem.put("String_Amps7", r.getCellText(43).toString());
										rowItem.put("String_Amps8", r.getCellText(44).toString());
										rowItem.put("String_Amps9", r.getCellText(45).toString());
										rowItem.put("String_Amps10", r.getCellText(46).toString());
										rowItem.put("String_Amps11", r.getCellText(47).toString());
										rowItem.put("String_Amps12", r.getCellText(48).toString());
										rowItem.put("String_Amps13", r.getCellText(49).toString());
										rowItem.put("String_Amps14", r.getCellText(50).toString());
										rowItem.put("String_Amps15", r.getCellText(51).toString());
										rowItem.put("String_Amps16", r.getCellText(52).toString());
										rowItem.put("String_Amps17", r.getCellText(53).toString());
										rowItem.put("String_Amps18", r.getCellText(54).toString());
										rowItem.put("String_Amps19", r.getCellText(55).toString());
										rowItem.put("String_Amps20", r.getCellText(56).toString());
										rowItem.put("String_Amps21", r.getCellText(57).toString());
										rowItem.put("String_Amps22", r.getCellText(58).toString());
										rowItem.put("String_Amps23", r.getCellText(59).toString());
										rowItem.put("String_Amps24", r.getCellText(60).toString());
										rowItem.put("String_Amps25", r.getCellText(61).toString());
										rowItem.put("String_Amps26", r.getCellText(62).toString());
										rowItem.put("String_Amps27", r.getCellText(63).toString());
										rowItem.put("String_Amps28", r.getCellText(64).toString());
										rowItem.put("String_Amps29", r.getCellText(65).toString());
										rowItem.put("String_Amps30", r.getCellText(66).toString());
										rowItem.put("String_Amps31", r.getCellText(67).toString());
										rowItem.put("String_Amps32", r.getCellText(68).toString());
										rowItem.put("String_Amps_Average", r.getCellText(69).toString());
										rowItem.put("String_kwh1", r.getCellText(70).toString());
										rowItem.put("String_kwh2", r.getCellText(71).toString());
										rowItem.put("String_kwh3", r.getCellText(72).toString());
										rowItem.put("String_kwh4", r.getCellText(73).toString());
										rowItem.put("String_kwh5", r.getCellText(74).toString());
										rowItem.put("String_kwh6", r.getCellText(75).toString());
										rowItem.put("String_kwh7", r.getCellText(76).toString());
										rowItem.put("String_kwh8", r.getCellText(77).toString());
										rowItem.put("String_kwh9", r.getCellText(78).toString());
										
										rowItem.put("String_kwh10", r.getCellText(79).toString());
										rowItem.put("String_kwh11", r.getCellText(80).toString());
										rowItem.put("String_kwh12", r.getCellText(81).toString());
										rowItem.put("String_kwh13", r.getCellText(82).toString());
										rowItem.put("String_kwh14", r.getCellText(83).toString());
										rowItem.put("String_kwh15", r.getCellText(84).toString());
										rowItem.put("String_kwh16", r.getCellText(85).toString());
										rowItem.put("String_kwh17", r.getCellText(86).toString());
										rowItem.put("String_kwh18", r.getCellText(87).toString());
										rowItem.put("String_kwh19", r.getCellText(88).toString());
										rowItem.put("String_kwh20", r.getCellText(89).toString());
										rowItem.put("String_kwh21", r.getCellText(90).toString());
										rowItem.put("String_kwh22", r.getCellText(91).toString());
										rowItem.put("String_kwh23", r.getCellText(92).toString());
										rowItem.put("String_kwh24", r.getCellText(93).toString());
										rowItem.put("String_kwh25", r.getCellText(94).toString());
										rowItem.put("String_kwh26", r.getCellText(95).toString());
										rowItem.put("String_kwh27", r.getCellText(96).toString());
										rowItem.put("String_kwh28", r.getCellText(97).toString());
										rowItem.put("String_kwh29", r.getCellText(98).toString());
										rowItem.put("String_kwh30", r.getCellText(99).toString());
										rowItem.put("String_kwh31", r.getCellText(100).toString());
										rowItem.put("String_kwh32", r.getCellText(101).toString());
										rowItem.put("String_kwh_Average", r.getCellText(102).toString());
										rowItem.put("Total_kwh", r.getCellText(103).toString());
										rowItem.put("Total_mwh", r.getCellText(104).toString());
										rowItem.put("kwh_Today", r.getCellText(105).toString());
										rowItem.put("kwh_Yesterday", r.getCellText(106).toString());
										rowItem.put("Total_kwh7_days", r.getCellText(107).toString());
										rowItem.put("Total_kwh30_days", r.getCellText(108).toString());
										rowItem.put("Average_kwh7_days", r.getCellText(109).toString());
										rowItem.put("Average_kwh30_Days", r.getCellText(110).toString());
										rowItem.put("Average_Line_Frequency", r.getCellText(111).toString());
										rowItem.put("Average_Line_Frequency_Error", r.getCellText(112).toString());
										rowItem.put("FPGA_Identification_Number", r.getCellText(113).toString());
										rowItem.put("DC_Input_Voltage_Timer", r.getCellText(114).toString());
										rowItem.put("AC_Line_Voltage_Timer", r.getCellText(115).toString());
										rowItem.put("Operating_State", r.getCellText(116).toString());
										rowItem.put("Internal_Air_Temperature", r.getCellText(117).toString());
										rowItem.put("Inverter_Air_Temperature", r.getCellText(118).toString());
										rowItem.put("Heatsink_Temperature1", r.getCellText(119).toString());
										rowItem.put("Heatsink_Temperature2", r.getCellText(120).toString());
										rowItem.put("Heatsink_Temperature3", r.getCellText(121).toString());
										rowItem.put("Heatsink_Temperature4", r.getCellText(122).toString());
										rowItem.put("Heatsink_Temperature5", r.getCellText(123).toString());
										rowItem.put("Heatsink_Temperature6", r.getCellText(124).toString());
										rowItem.put("Heatsink_Maximum_Temparature1", r.getCellText(125).toString());
										rowItem.put("Fan_Speed_Command1", r.getCellText(126).toString());
										rowItem.put("Heatsink_Maximum_Temperature2", r.getCellText(127).toString());
										rowItem.put("Fan_Speed_Command2", r.getCellText(128).toString());
										rowItem.put("Number_of_Temperature_Feedbacks", r.getCellText(129).toString());
										rowItem.put("Serial_number_word1", r.getCellText(130).toString());
										rowItem.put("Serial_number_word2", r.getCellText(131).toString());
										rowItem.put("Serial_number_word3", r.getCellText(132).toString());
										rowItem.put("Serial_number_word4", r.getCellText(133).toString());
										rowItem.put("Number_of_Strings", r.getCellText(134).toString());
										rowItem.put("nvmActivePower", r.getCellText(135).toString());
										rowItem.put("nvmActiveEnergy", r.getCellText(136).toString());
										
										break;
									case "model_veris_industries_e51c2_power_meter":
										rowItem.put("time", formatter.format(date));
										rowItem.put("local_time", formatter.format(date));
										rowItem.put("id_device", r.getCellText(1).toString());
										rowItem.put("error", r.getCellText(2).toString());
										rowItem.put("low_alarm", r.getCellText(3).toString());
										rowItem.put("high_alarm", r.getCellText(4).toString());
										rowItem.put("AccumulatedRealEnergyNet", r.getCellText(5).toString());
										rowItem.put("RealEnergyQuadrants14Import", r.getCellText(6).toString());
										rowItem.put("RealEnergyQuadrants23Export", r.getCellText(7).toString());
										rowItem.put("ReactiveEnergyQuadrant1", r.getCellText(8).toString());
										rowItem.put("ReactiveEnergyQuadrant2", r.getCellText(9).toString());
										rowItem.put("ReactiveEnergyQuadrant3", r.getCellText(10).toString());
										rowItem.put("ReactiveEnergyQuadrant4", r.getCellText(11).toString());
										rowItem.put("ApparentEnergyNet", r.getCellText(12).toString());
										rowItem.put("ApparentEnergyQuadrants14", r.getCellText(13).toString());
										rowItem.put("ApparentEnergyQuadrants23", r.getCellText(14).toString());
										rowItem.put("TotalNetInstantaneousRealPower", r.getCellText(15).toString());
										rowItem.put("TotalNetInstantaneousReactivePower", r.getCellText(16).toString());
										rowItem.put("TotalNetInstantaneousApparentPower", r.getCellText(17).toString());
										rowItem.put("TotalPowerFactor", r.getCellText(18).toString());
										rowItem.put("VoltageLL3pAve", r.getCellText(19).toString());
										rowItem.put("VoltageLN3pAve", r.getCellText(20).toString());
										rowItem.put("Current3pAve", r.getCellText(21).toString());
										rowItem.put("Frequency", r.getCellText(22).toString());
										rowItem.put("TotalRealPowerPresentDemand", r.getCellText(23).toString());
										rowItem.put("TotalReactivePowerPresentDemand", r.getCellText(24).toString());
										rowItem.put("TotalApparentPowerPresentDemand", r.getCellText(25).toString());
										rowItem.put("TotalRealPowerMaxDemandImport", r.getCellText(26).toString());
										rowItem.put("TotalReactivePowerMaxDemandImport", r.getCellText(27).toString());
										rowItem.put("TotalApparentPowerMaxDemandImport", r.getCellText(28).toString());
										rowItem.put("TotalRealPowerMaxDemandExport", r.getCellText(29).toString());
										rowItem.put("TotalReactivePowerMaxDemandExport", r.getCellText(30).toString());
										rowItem.put("TotalApparentPowerMaxDemandExport", r.getCellText(31).toString());
										rowItem.put("AccumulatedRealEnergyPhaseAImport", r.getCellText(32).toString());
										rowItem.put("AccumulatedRealEnergyPhaseBImport", r.getCellText(33).toString());
										rowItem.put("AccumulatedRealEnergyPhaseCImport", r.getCellText(34).toString());
										rowItem.put("AccumulatedRealEnergyPhaseAExport", r.getCellText(35).toString());
										rowItem.put("AccumulatedRealEnergyPhaseBExport", r.getCellText(36).toString());
										rowItem.put("AccumulatedRealEnergyPhaseCExport", r.getCellText(37).toString());
										rowItem.put("AccumulatedQ1ReactiveEnergyPhaseAImport", r.getCellText(38).toString());
										rowItem.put("AccumulatedQ1ReactiveEnergyPhaseBImport", r.getCellText(39).toString());
										rowItem.put("AccumulatedQ1ReactiveEnergyPhaseCImport", r.getCellText(40).toString());
										rowItem.put("AccumulatedQ2ReactiveEnergyPhaseAImport", r.getCellText(41).toString());
										rowItem.put("AccumulatedQ2ReactiveEnergyPhaseBImport", r.getCellText(42).toString());
										rowItem.put("AccumulatedQ2ReactiveEnergyPhaseCImport", r.getCellText(43).toString());
										rowItem.put("AccumulatedQ3ReactiveEnergyPhaseAExport", r.getCellText(44).toString());
										rowItem.put("AccumulatedQ3ReactiveEnergyPhaseBExport", r.getCellText(45).toString());
										rowItem.put("AccumulatedQ3ReactiveEnergyPhaseCExport", r.getCellText(46).toString());
										rowItem.put("AccumulatedQ4ReactiveEnergyPhaseAExport", r.getCellText(47).toString());
										rowItem.put("AccumulatedQ4ReactiveEnergyPhaseBExport", r.getCellText(48).toString());
										rowItem.put("AccumulatedQ4ReactiveEnergyPhaseCExport", r.getCellText(49).toString());
										rowItem.put("AccumulatedApparentEnergyPhaseAImport", r.getCellText(50).toString());
										rowItem.put("AccumulatedApparentEnergyPhaseBImport", r.getCellText(51).toString());
										rowItem.put("AccumulatedApparentEnergyPhaseCImport", r.getCellText(52).toString());
										rowItem.put("AccumulatedApparentEnergyPhaseAExport", r.getCellText(53).toString());
										rowItem.put("AccumulatedApparentEnergyPhaseBExport", r.getCellText(54).toString());
										rowItem.put("AccumulatedApparentEnergyPhaseCExport", r.getCellText(55).toString());
										rowItem.put("RealPowerPhaseA", r.getCellText(56).toString());
										rowItem.put("RealPowerPhaseB", r.getCellText(57).toString());
										rowItem.put("RealPowerPhaseC", r.getCellText(58).toString());
										rowItem.put("ReactivePowerPhaseA", r.getCellText(59).toString());
										rowItem.put("ReactivePowerPhaseB", r.getCellText(60).toString());
										rowItem.put("ReactivePowerPhaseC", r.getCellText(61).toString());
										rowItem.put("ApparentPowerPhaseA", r.getCellText(62).toString());
										rowItem.put("ApparentPowerPhaseB", r.getCellText(63).toString());
										rowItem.put("ApparentPowerPhaseC", r.getCellText(64).toString());
										rowItem.put("PowerFactorPhaseA", r.getCellText(65).toString());
										rowItem.put("PowerFactorPhaseB", r.getCellText(66).toString());
										rowItem.put("PowerFactorPhaseC", r.getCellText(67).toString());
										rowItem.put("VoltagePhaseAB", r.getCellText(68).toString());
										rowItem.put("VoltagePhaseBC", r.getCellText(69).toString());
										rowItem.put("VoltagePhaseAC", r.getCellText(70).toString());
										rowItem.put("VoltagePhaseAN", r.getCellText(71).toString());
										rowItem.put("VoltagePhaseBN", r.getCellText(72).toString());
										rowItem.put("VoltagePhaseCN", r.getCellText(73).toString());
										rowItem.put("CurrentPhaseA", r.getCellText(74).toString());
										rowItem.put("CurrentPhaseB", r.getCellText(75).toString());
										rowItem.put("CurrentPhaseC", r.getCellText(76).toString());
										rowItem.put("nvmActivePower", r.getCellText(77).toString());
										rowItem.put("nvmActiveEnergy", r.getCellText(78).toString());
										
										break;
									case "model_chint_solectria_inverter_class9725":
										rowItem.put("time", formatter.format(date));
										rowItem.put("local_time", formatter.format(date));
										rowItem.put("id_device", r.getCellText(1).toString());
										rowItem.put("error", r.getCellText(2).toString());
										rowItem.put("low_alarm", r.getCellText(3).toString());
										rowItem.put("high_alarm", r.getCellText(4).toString());
										rowItem.put("PowerOnOff", r.getCellText(5).toString());
										rowItem.put("PActiveSet", r.getCellText(6).toString());
										rowItem.put("PFactorSet", r.getCellText(7).toString());
										rowItem.put("PReactiveSet", r.getCellText(8).toString());
										rowItem.put("GridVMax", r.getCellText(9).toString());
										rowItem.put("GridVmaxTripT", r.getCellText(10).toString());
										rowItem.put("GridVMin", r.getCellText(11).toString());
										rowItem.put("GridVminTripT", r.getCellText(12).toString());
										rowItem.put("GridFMax", r.getCellText(13).toString());
										rowItem.put("GridFMin", r.getCellText(14).toString());
										rowItem.put("GridFTripT", r.getCellText(15).toString());
										rowItem.put("ActivePower", r.getCellText(16).toString());
										rowItem.put("PowerFactor", r.getCellText(17).toString());
										rowItem.put("StartDelayTime", r.getCellText(18).toString());
										rowItem.put("Risomin", r.getCellText(19).toString());
										rowItem.put("PVStartVol", r.getCellText(20).toString());
										rowItem.put("DCIMax", r.getCellText(21).toString());
										rowItem.put("TambientMax", r.getCellText(22).toString());
										rowItem.put("TmoduleMax", r.getCellText(23).toString());
										rowItem.put("OffsetDiffMax", r.getCellText(24).toString());
										rowItem.put("GridVolUnbalance", r.getCellText(25).toString());
										rowItem.put("SoftPowerStep", r.getCellText(26).toString());
										rowItem.put("TotalEnergyToEnergy", r.getCellText(27).toString());
										rowItem.put("TotalEnergyToday", r.getCellText(28).toString());
										rowItem.put("InverterEfficiency", r.getCellText(29).toString());
										rowItem.put("PowerFactor1", r.getCellText(30).toString());
										rowItem.put("MaxActivePowerToday", r.getCellText(31).toString());
										rowItem.put("RunTimeToGrid", r.getCellText(32).toString());
										rowItem.put("AC_ActivePower", r.getCellText(33).toString());
										rowItem.put("AC_ApparentPower", r.getCellText(34).toString());
										rowItem.put("GridVoltageUab", r.getCellText(35).toString());
										rowItem.put("GridVoltageUbc", r.getCellText(36).toString());
										rowItem.put("GridVoltageUca", r.getCellText(37).toString());
										rowItem.put("GridA_PhaseCurrent", r.getCellText(38).toString());
										rowItem.put("GridB_PhaseCurrent", r.getCellText(39).toString());
										rowItem.put("GridC_PhaseCurrent", r.getCellText(40).toString());
										rowItem.put("PV1_Voltage", r.getCellText(41).toString());
										rowItem.put("PV1_Current", r.getCellText(42).toString());
										rowItem.put("PV2_Voltage", r.getCellText(43).toString());
										rowItem.put("PV2_Current", r.getCellText(44).toString());
										rowItem.put("PV3_Voltage", r.getCellText(45).toString());
										rowItem.put("PV3_Current", r.getCellText(46).toString());
										rowItem.put("Grid_Frequency", r.getCellText(47).toString());
										rowItem.put("ModuleTemp", r.getCellText(48).toString());
										rowItem.put("InternalTemp", r.getCellText(49).toString());
										rowItem.put("TransformerTemp", r.getCellText(50).toString());
										rowItem.put("InverterModeCode", r.getCellText(51).toString());
										rowItem.put("PermanentFaultCode", r.getCellText(52).toString());
										rowItem.put("WarnCode", r.getCellText(53).toString());
										rowItem.put("FaultCode0", r.getCellText(54).toString());
										rowItem.put("FaultCode1", r.getCellText(55).toString());
										rowItem.put("FaultCode2", r.getCellText(56).toString());
										rowItem.put("nvmActivePower", r.getCellText(57).toString());
										rowItem.put("nvmActiveEnergy", r.getCellText(58).toString());
										break;
									case "model_rt1_class30000":
										rowItem.put("time", formatter.format(date));
										rowItem.put("local_time", formatter.format(date));
										rowItem.put("id_device", r.getCellText(1).toString());
										rowItem.put("error", r.getCellText(2).toString());
										rowItem.put("low_alarm", r.getCellText(3).toString());
										rowItem.put("high_alarm", r.getCellText(4).toString());
										rowItem.put("device_type", r.getCellText(5).toString());
										rowItem.put("data_model_version", r.getCellText(6).toString());
										rowItem.put("operational_mode", r.getCellText(7).toString());
										rowItem.put("status_flags", r.getCellText(8).toString());
										rowItem.put("sensor1_data", r.getCellText(9).toString());
										rowItem.put("panel_temperature", r.getCellText(10).toString());
										rowItem.put("external_power_sensor", r.getCellText(11).toString());
										rowItem.put("calibration_date", r.getCellText(12).toString());
										rowItem.put("error_code", r.getCellText(13).toString());
										rowItem.put("protocol_error", r.getCellText(14).toString());
										rowItem.put("batch_number", r.getCellText(15).toString());
										rowItem.put("serial_number", r.getCellText(16).toString());
										rowItem.put("software_version", r.getCellText(17).toString());
										rowItem.put("hardware_version", r.getCellText(18).toString());
										rowItem.put("node_id", r.getCellText(19).toString());
										rowItem.put("nvm_irradiance", r.getCellText(20).toString());
										rowItem.put("nvm_temperature", r.getCellText(21).toString());
										
										break;
									case "model_advanced_energy_solaron":
										rowItem.put("time", formatter.format(date));
										rowItem.put("local_time", formatter.format(date));
										rowItem.put("id_device", r.getCellText(1).toString());
										rowItem.put("error", r.getCellText(2).toString());
										rowItem.put("low_alarm", r.getCellText(3).toString());
										rowItem.put("high_alarm", r.getCellText(4).toString());
										rowItem.put("today_kwh", r.getCellText(5).toString());
										rowItem.put("ytd_kwh_total", r.getCellText(6).toString());
										rowItem.put("life_kwh_total", r.getCellText(7).toString());
										rowItem.put("ytd_kwh", r.getCellText(8).toString());
										rowItem.put("life_kwh", r.getCellText(9).toString());
										rowItem.put("last_15min_kwh", r.getCellText(10).toString());
										rowItem.put("timestamp_15minutes", r.getCellText(11).toString());
										rowItem.put("last_restart", r.getCellText(12).toString());
										rowItem.put("uptime", r.getCellText(13).toString());
										rowItem.put("ac_power", r.getCellText(14).toString());
										rowItem.put("ac_frequency", r.getCellText(15).toString());
										rowItem.put("pv_voltage", r.getCellText(16).toString());
										rowItem.put("pv_current", r.getCellText(17).toString());
										rowItem.put("common_mode", r.getCellText(18).toString());
										rowItem.put("ambient_temperature", r.getCellText(19).toString());
										rowItem.put("coolant_temperature", r.getCellText(20).toString());
										rowItem.put("reactor_temperature", r.getCellText(21).toString());
										rowItem.put("cabinet_temperature", r.getCellText(22).toString());
										rowItem.put("bus_voltage", r.getCellText(23).toString());
										rowItem.put("ground_current", r.getCellText(24).toString());
										rowItem.put("reactive_power", r.getCellText(25).toString());
										rowItem.put("active_faults1", r.getCellText(26).toString());
										rowItem.put("active_faults2", r.getCellText(27).toString());
										rowItem.put("active_faults3", r.getCellText(28).toString());
										rowItem.put("status", r.getCellText(29).toString());
										rowItem.put("warnings1", r.getCellText(30).toString());
										rowItem.put("warnings2_reserved", r.getCellText(31).toString());
										rowItem.put("warnings3_reserved", r.getCellText(32).toString());
										rowItem.put("limits", r.getCellText(33).toString());
										rowItem.put("year", r.getCellText(34).toString());
										rowItem.put("month", r.getCellText(35).toString());
										rowItem.put("day", r.getCellText(36).toString());
										rowItem.put("hour", r.getCellText(37).toString());
										rowItem.put("minutes", r.getCellText(38).toString());
										rowItem.put("seconds", r.getCellText(39).toString());
										rowItem.put("current_time", r.getCellText(40).toString());
										rowItem.put("nvmActivePower", r.getCellText(41).toString());
										rowItem.put("nvmActiveEnergy", r.getCellText(42).toString());
										
										break;
									case "model_imtsolar_class8000":
										rowItem.put("time", formatter.format(date));
										rowItem.put("local_time", formatter.format(date));
										rowItem.put("id_device", r.getCellText(1).toString());
										rowItem.put("error", r.getCellText(2).toString());
										rowItem.put("low_alarm", r.getCellText(3).toString());
										rowItem.put("high_alarm", r.getCellText(4).toString());
										rowItem.put("irradiance", r.getCellText(5).toString());
										rowItem.put("tcell", r.getCellText(6).toString());
										rowItem.put("nvm_irradiance", r.getCellText(7).toString());
										rowItem.put("nvm_temperature", r.getCellText(8).toString());
										
										break;
									case "model_pvp_inverter":
										rowItem.put("time", formatter.format(date));
										rowItem.put("local_time", formatter.format(date));
										rowItem.put("id_device", r.getCellText(1).toString());
										rowItem.put("error", r.getCellText(2).toString());
										rowItem.put("low_alarm", r.getCellText(3).toString());
										rowItem.put("high_alarm", r.getCellText(4).toString());
										rowItem.put("total_kwh_delivered", r.getCellText(5).toString());
										rowItem.put("volts_a_l_n", r.getCellText(6).toString());
										rowItem.put("volts_b_l_n", r.getCellText(7).toString());
										rowItem.put("volts_c_l_n", r.getCellText(8).toString());
										rowItem.put("current_a", r.getCellText(9).toString());
										rowItem.put("current_b", r.getCellText(10).toString());
										rowItem.put("current_c", r.getCellText(11).toString());
										rowItem.put("dc_output_voltage", r.getCellText(12).toString());
										rowItem.put("dc_output_current", r.getCellText(13).toString());
										rowItem.put("line_frenquency", r.getCellText(14).toString());
										rowItem.put("line_kw", r.getCellText(15).toString());
										rowItem.put("inverter_operating_status", r.getCellText(16).toString());
										rowItem.put("inverter_fault_word0", r.getCellText(17).toString());
										rowItem.put("inverter_fault_word1", r.getCellText(18).toString());
										rowItem.put("inverter_fault_word2", r.getCellText(19).toString());
										rowItem.put("data_comm_status", r.getCellText(20).toString());
										rowItem.put("nvmActivePower", r.getCellText(21).toString());
										rowItem.put("nvmActiveEnergy", r.getCellText(22).toString());

										break;
									case "model_ivt_solaron_ext":
										rowItem.put("time", formatter.format(date));
										rowItem.put("local_time", formatter.format(date));
										rowItem.put("id_device", r.getCellText(1).toString());
										rowItem.put("error", r.getCellText(2).toString());
										rowItem.put("low_alarm", r.getCellText(3).toString());
										rowItem.put("high_alarm", r.getCellText(4).toString());
										rowItem.put("today_kwh", r.getCellText(5).toString());
										rowItem.put("ytd_kwh_total", r.getCellText(6).toString());
										rowItem.put("life_kwh_total", r.getCellText(7).toString());
										rowItem.put("ytd_kwh", r.getCellText(8).toString());
										rowItem.put("life_kwh", r.getCellText(9).toString());
										rowItem.put("last_15min_kwh", r.getCellText(10).toString());
										rowItem.put("timestamp_15minutes", r.getCellText(11).toString());
										rowItem.put("last_restart", r.getCellText(12).toString());
										rowItem.put("uptime", r.getCellText(13).toString());
										rowItem.put("ac_power", r.getCellText(14).toString());
										rowItem.put("ac_frequency", r.getCellText(15).toString());
										rowItem.put("pv_voltage", r.getCellText(16).toString());
										rowItem.put("pv_current", r.getCellText(17).toString());
										rowItem.put("common_mode", r.getCellText(18).toString());
										rowItem.put("ambient_temperature", r.getCellText(19).toString());
										rowItem.put("coolant_temperature", r.getCellText(20).toString());
										rowItem.put("reactor_temperature", r.getCellText(21).toString());
										rowItem.put("cabinet_temperature", r.getCellText(22).toString());
										rowItem.put("bus_voltage", r.getCellText(23).toString());
										rowItem.put("ground_current", r.getCellText(24).toString());
										rowItem.put("reactive_power", r.getCellText(25).toString());
										rowItem.put("active_faults1", r.getCellText(26).toString());
										rowItem.put("active_faults2", r.getCellText(27).toString());
										rowItem.put("active_faults3", r.getCellText(28).toString());
										rowItem.put("status", r.getCellText(29).toString());
										rowItem.put("warnings1", r.getCellText(30).toString());
										rowItem.put("warnings2_reserved", r.getCellText(31).toString());
										rowItem.put("warnings3_reserved", r.getCellText(32).toString());
										rowItem.put("limits", r.getCellText(33).toString());
										rowItem.put("year", r.getCellText(34).toString());
										rowItem.put("month", r.getCellText(35).toString());
										rowItem.put("day", r.getCellText(36).toString());
										rowItem.put("hour", r.getCellText(37).toString());
										rowItem.put("minutes", r.getCellText(38).toString());
										rowItem.put("seconds", r.getCellText(39).toString());
										rowItem.put("current_time", r.getCellText(40).toString());
										rowItem.put("ac_current", r.getCellText(41).toString());
										rowItem.put("requset_set_ac_power_limit", r.getCellText(42).toString());
										rowItem.put("request_set_instantaneous_reactive_power_set_point", r.getCellText(43).toString());
										rowItem.put("autostart_status", r.getCellText(44).toString());
										rowItem.put("set_read_reactive_power_mode", r.getCellText(45).toString());
										rowItem.put("set_read_p_ac_limit", r.getCellText(46).toString());
										rowItem.put("set_read_instantaneous_reactive_power_set_point", r.getCellText(47).toString());
										rowItem.put("set_read_power_factor_set_point", r.getCellText(48).toString());
										rowItem.put("ac_power_ramp_rate", r.getCellText(49).toString());
										rowItem.put("reactive_power_ramp_rate", r.getCellText(50).toString());
										rowItem.put("power_factor_ramp_rate", r.getCellText(51).toString());
										rowItem.put("nvmActivePower", r.getCellText(52).toString());
										rowItem.put("nvmActiveEnergy", r.getCellText(53).toString());
										
										break;
										
									case "model_kippzonen_rt1_class8009":
										rowItem.put("time", formatter.format(date));
										rowItem.put("local_time", formatter.format(date));
										rowItem.put("id_device", r.getCellText(1).toString());
										rowItem.put("error", r.getCellText(2).toString());
										rowItem.put("low_alarm", r.getCellText(3).toString());
										rowItem.put("high_alarm", r.getCellText(4).toString());
										rowItem.put("device_type", r.getCellText(5).toString());
										rowItem.put("data_model_version", r.getCellText(6).toString());
										rowItem.put("operational_mode", r.getCellText(7).toString());
										rowItem.put("status_flags", r.getCellText(8).toString());
										rowItem.put("sensor1_data", r.getCellText(9).toString());
										rowItem.put("panel_temperature", r.getCellText(10).toString());
										rowItem.put("external_power_sensor", r.getCellText(11).toString());
										rowItem.put("calibration_date", r.getCellText(12).toString());
										rowItem.put("error_code", r.getCellText(13).toString());
										rowItem.put("protocol_error", r.getCellText(14).toString());
										rowItem.put("batch_number", r.getCellText(15).toString());
										rowItem.put("serial_number", r.getCellText(16).toString());
										rowItem.put("software_version", r.getCellText(17).toString());
										rowItem.put("hardware_version", r.getCellText(18).toString());
										rowItem.put("node_id", r.getCellText(19).toString());
										rowItem.put("nvm_irradiance", r.getCellText(20).toString());
										rowItem.put("nvm_temperature", r.getCellText(21).toString());
										
										break;
									case "model_shark100":
										rowItem.put("time", formatter.format(date));
										rowItem.put("local_time", formatter.format(date));
										rowItem.put("id_device", r.getCellText(1).toString());
										rowItem.put("error", r.getCellText(2).toString());
										rowItem.put("low_alarm", r.getCellText(3).toString());
										rowItem.put("high_alarm", r.getCellText(4).toString());
										rowItem.put("volts_a_n", r.getCellText(5).toString());
										rowItem.put("volts_b_n", r.getCellText(6).toString());
										rowItem.put("volts_c_n", r.getCellText(7).toString());
										rowItem.put("volts_a_b", r.getCellText(8).toString());
										rowItem.put("volts_b_c", r.getCellText(9).toString());
										rowItem.put("volts_c_a", r.getCellText(10).toString());
										rowItem.put("amps_a", r.getCellText(11).toString());
										rowItem.put("amps_b", r.getCellText(12).toString());
										rowItem.put("amps_c", r.getCellText(13).toString());
										rowItem.put("watts_3ph_total", r.getCellText(14).toString());
										rowItem.put("vars_3ph_total", r.getCellText(15).toString());
										rowItem.put("vas_3ph_total", r.getCellText(16).toString());
										rowItem.put("power_factor_3ph_total", r.getCellText(17).toString());
										rowItem.put("frequency", r.getCellText(18).toString());
										rowItem.put("neutral_current", r.getCellText(19).toString());
										rowItem.put("w_hours_received", r.getCellText(20).toString());
										rowItem.put("w_hours_delivered", r.getCellText(21).toString());
										rowItem.put("w_hours_net", r.getCellText(22).toString());
										rowItem.put("w_hours_total", r.getCellText(23).toString());
										rowItem.put("var_hours_positive", r.getCellText(24).toString());
										rowItem.put("var_hours_negative", r.getCellText(25).toString());
										rowItem.put("var_hours_net", r.getCellText(26).toString());
										rowItem.put("var_hours_total", r.getCellText(27).toString());
										rowItem.put("va_hours_total", r.getCellText(28).toString());
										rowItem.put("amps_a_average", r.getCellText(29).toString());
										rowItem.put("amps_b_average", r.getCellText(30).toString());
										rowItem.put("amps_c_average", r.getCellText(31).toString());
										rowItem.put("positive_watts_3ph_average", r.getCellText(32).toString());
										rowItem.put("positive_vars_3ph_average", r.getCellText(33).toString());
										rowItem.put("negative_watts_3ph_average", r.getCellText(34).toString());
										rowItem.put("negative_vars_3ph_average", r.getCellText(35).toString());
										rowItem.put("vas_3ph_average", r.getCellText(36).toString());
										rowItem.put("positive_pf_3ph_average", r.getCellText(37).toString());
										rowItem.put("negative_pf_3ph_average", r.getCellText(38).toString());
										rowItem.put("volts_a_n_min", r.getCellText(39).toString());
										rowItem.put("volts_b_n_min", r.getCellText(40).toString());
										rowItem.put("volts_c_n_min", r.getCellText(41).toString());
										rowItem.put("volts_a_b_min", r.getCellText(42).toString());
										rowItem.put("volts_b_c_min", r.getCellText(43).toString());
										rowItem.put("volts_c_a_min", r.getCellText(44).toString());
										rowItem.put("amps_a_min_avg_demand", r.getCellText(45).toString());
										rowItem.put("amps_b_min_avg_demand", r.getCellText(46).toString());
										rowItem.put("amps_c_min_avg_demand", r.getCellText(47).toString());
										rowItem.put("positive_watts_3ph_min_avg_demand", r.getCellText(48).toString());
										rowItem.put("positive_vars_3ph_min_avg_demand", r.getCellText(49).toString());
										rowItem.put("negative_watts_3ph_min_avg_demand", r.getCellText(50).toString());
										rowItem.put("negative_vars_3ph_min_avg_demand", r.getCellText(51).toString());
										rowItem.put("vas_3ph_min_avg_demand", r.getCellText(52).toString());
										rowItem.put("positive_pf_3ph_min_avg_demand", r.getCellText(53).toString());
										rowItem.put("negative_pf_3ph_min_avg_demand", r.getCellText(54).toString());
										rowItem.put("frequency_min", r.getCellText(55).toString());
										rowItem.put("volts_a_n_max", r.getCellText(56).toString());
										rowItem.put("volts_b_n_max", r.getCellText(57).toString());
										rowItem.put("volts_c_n_max", r.getCellText(58).toString());
										rowItem.put("volts_a_b_max", r.getCellText(59).toString());
										rowItem.put("volts_b_c_max", r.getCellText(60).toString());
										rowItem.put("volts_c_a_max", r.getCellText(61).toString());
										rowItem.put("amps_a_max_avg_demand", r.getCellText(62).toString());
										rowItem.put("amps_b_max_avg_demand", r.getCellText(63).toString());
										rowItem.put("amps_c_max_avg_demand", r.getCellText(64).toString());
										rowItem.put("positive_watts_3ph_max_avg_demand", r.getCellText(65).toString());
										rowItem.put("positive_vars_3ph_max_avg_demand", r.getCellText(66).toString());
										rowItem.put("negative_watts_3ph_max_avg_demand", r.getCellText(67).toString());
										rowItem.put("negative_vars_3ph_max_avg_demand", r.getCellText(68).toString());
										rowItem.put("vas_3ph_max_avg_demand", r.getCellText(69).toString());
										rowItem.put("positive_pf_3ph_max_avg_demand", r.getCellText(70).toString());
										rowItem.put("negative_pf_3ph_max_avg_demand", r.getCellText(71).toString());
										rowItem.put("frequency_max", r.getCellText(72).toString());
										rowItem.put("volts_a_n_thd", r.getCellText(73).toString());
										rowItem.put("volts_b_n_thd", r.getCellText(74).toString());
										rowItem.put("volts_c_n_thd", r.getCellText(75).toString());
										rowItem.put("amps_a_thd", r.getCellText(76).toString());
										rowItem.put("amps_b_thd", r.getCellText(77).toString());
										rowItem.put("amps_c_thd", r.getCellText(78).toString());
										rowItem.put("phase_a_current_0th", r.getCellText(79).toString());
										rowItem.put("phase_a_current_1st", r.getCellText(80).toString());
										rowItem.put("phase_a_current_2nd", r.getCellText(81).toString());
										rowItem.put("phase_a_current_3rd", r.getCellText(82).toString());
										rowItem.put("phase_a_current_4th", r.getCellText(83).toString());
										rowItem.put("phase_a_current_5th", r.getCellText(84).toString());
										rowItem.put("phase_a_current_6th", r.getCellText(85).toString());
										rowItem.put("phase_a_current_7th", r.getCellText(86).toString());
										rowItem.put("phase_a_voltage_0th", r.getCellText(87).toString());
										rowItem.put("phase_a_voltage_1st", r.getCellText(88).toString());
										rowItem.put("phase_a_voltage_2nd", r.getCellText(89).toString());
										rowItem.put("phase_a_voltage_3rd", r.getCellText(90).toString());
										rowItem.put("phase_b_current_0th", r.getCellText(91).toString());
										rowItem.put("phase_b_current_1st", r.getCellText(92).toString());
										rowItem.put("phase_b_current_2nd", r.getCellText(93).toString());
										rowItem.put("phase_b_current_3rd", r.getCellText(94).toString());
										rowItem.put("phase_b_current_4th", r.getCellText(95).toString());
										rowItem.put("phase_b_current_5th", r.getCellText(96).toString());
										rowItem.put("phase_b_current_6th", r.getCellText(97).toString());
										rowItem.put("phase_b_current_7th", r.getCellText(98).toString());
										rowItem.put("phase_b_voltage_0th", r.getCellText(99).toString());
										rowItem.put("phase_b_voltage_1st", r.getCellText(100).toString());
										rowItem.put("phase_b_voltage_2nd", r.getCellText(101).toString());
										rowItem.put("phase_b_voltage_3rd", r.getCellText(102).toString());
										rowItem.put("phase_c_current_0th", r.getCellText(103).toString());
										rowItem.put("phase_c_current_1st", r.getCellText(104).toString());
										rowItem.put("phase_c_current_2nd", r.getCellText(105).toString());
										rowItem.put("phase_c_current_3rd", r.getCellText(106).toString());
										rowItem.put("phase_c_current_4th", r.getCellText(107).toString());
										rowItem.put("phase_c_current_5th", r.getCellText(108).toString());
										rowItem.put("phase_c_current_6th", r.getCellText(109).toString());
										rowItem.put("phase_c_current_7th", r.getCellText(110).toString());
										rowItem.put("phase_c_voltage_0th", r.getCellText(111).toString());
										rowItem.put("phase_c_voltage_1st", r.getCellText(112).toString());
										rowItem.put("phase_c_voltage_2nd", r.getCellText(113).toString());
										rowItem.put("phase_c_voltage_3rd", r.getCellText(114).toString());
										rowItem.put("angle_phase_a_current", r.getCellText(115).toString());
										rowItem.put("angle_phase_b_current", r.getCellText(116).toString());
										rowItem.put("angle_phase_c_current", r.getCellText(117).toString());
										rowItem.put("angle_volts_a_b", r.getCellText(118).toString());
										rowItem.put("angle_volts_b_c", r.getCellText(119).toString());
										rowItem.put("angle_volts_c_a", r.getCellText(120).toString());
										rowItem.put("nvmActivePower", r.getCellText(121).toString());
										rowItem.put("nvmActiveEnergy", r.getCellText(122).toString());
										break;
									}

								} catch (ParseException e) {
									e.printStackTrace();
								}
								result.add(rowItem);
//								obj.setDataList(result);
//								ImportOldDataEntity data = service.insertImportOldData(obj);
								
							});

						} catch (Exception e) {
							e.printStackTrace();
						}
						watch.stop();
						System.out.println("Processing time: " + watch.getTime(TimeUnit.MILLISECONDS));
					});

					obj.setDataList(result);
					ImportOldDataEntity data = service.insertImportOldData(obj);
					if (data != null) {
						if (data.getRow() > 0) {
							return this.jsonResult(false, "Error date format at row " + data.getRow(), null, 0);
						}
						File logGzFile = new File(fileUrl);
						if(logGzFile.delete()) {  System.out.println(fileUrl); }	
						return this.jsonResult(true, "Import data successfully", data, 1);
					} else {
						return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
					}
					
				}catch (Exception e) {
					return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
				}

			} else {
				return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
			}

		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
	
}
