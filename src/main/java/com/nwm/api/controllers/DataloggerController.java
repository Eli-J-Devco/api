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
import java.util.Map;
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
import com.nwm.api.entities.ModelPVPInverterEntity;
import com.nwm.api.entities.ModelPVPowered3550260500kwInverterEntity;
import com.nwm.api.entities.ModelPowerMeasurementIon7650Entity;
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
import com.nwm.api.entities.ModelXantrexGT100250500Entity;
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
import com.nwm.api.services.ModelPVPInverterService;
import com.nwm.api.services.ModelPVPowered3550260500kwInverterService;
import com.nwm.api.services.ModelPowerMeasurementIon7650Service;
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
import com.nwm.api.services.ModelXantrexGT100250500Service;
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
@RequestMapping("/upload-data")
public class DataloggerController extends BaseController {
	public static String message = "";
	/**
	 * @description upload files datalogger and insert datalogger to database
	 * @author long.pham
	 * @since 2020-08-19
	 * @params RequestParam, files
	 */

//	@SuppressWarnings("unchecked")
	@PostMapping("/power-factor")
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

	public String uploadFiles(@RequestParam Map<String, String> params) {

		System.out.println("---------------------------------start power factor------------------------------");
		System.out.println("SENDDATATRACE: " + params);
		log.error(params);
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
		System.out.println("-------------------------------end--------------------------------");
		
		return "Done";
		
			
		
	}
	
	

}