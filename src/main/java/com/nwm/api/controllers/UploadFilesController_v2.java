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
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.nwm.api.entities.*;
import com.nwm.api.services.*;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.DataloggerUpdateHelper;
import com.nwm.api.utils.Lib;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.nio.file.Path;



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
		if (serialnumber == null 
			|| serialnumber.trim().isEmpty()
			|| mode == null || mode.trim().isEmpty()
			|| files == null || files.length == 0) {
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

					Path root = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey));
					String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
					String unique = UUID.randomUUID().toString();

					byte[] bytes;
					try {
						bytes = file.getBytes();
						
						fileName = "bm-" + serialnumber + "-" + modbusport + "-" + modbusdevice + "-" + unique + "." + timeStamp + ".log";
						
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
							FileReader fr = new FileReader(readFile);
							BufferedReader br = new BufferedReader(fr);
							String line;

							UploadFilesService uploadFilesService = new UploadFilesService();
							DeviceService serviceD = new DeviceService();
						DeviceEntity deviceE = new DeviceEntity();
						deviceE.setSerial_number(serialnumber);
						deviceE.setModbusdevicenumber(modbusdevice);
		
						// Update datalogger info use dataloger helper()
						List<DeviceEntity> dataloggers = serviceD.getDataloggerBySerialNumber(deviceE);
						if (dataloggers.size() > 0) {
							for (DeviceEntity dataloggerItem : dataloggers) {
								DataloggerUpdateHelper.updateDataloggerInfo(
									dataloggerItem,
									serialnumber,
									loopname,
									modbusip,
									modbusport,
									modbusdevice,
									modbusdevicename,
									modbusdevicetype,
									modbusdevicetypenumber,
									modbusdeviceclass
								);
							}
						}
						
						// Get device and scaled parameters
						DeviceEntity item = serviceD.getDeviceBySerialNumber(deviceE);
						if (item == null) {
							fr.close();
							message = "\nFAILURE\n";
							return;
						}
						ModelHuaweiSun200028ktlService_v2 serviceHuaweiSun200028ktl = new ModelHuaweiSun200028ktlService_v2();
						List dataArrList = new ArrayList<>();
						// Read all lines from file (no parsing yet)
						List<String> lines = new ArrayList<>();
						while ((line = br.readLine()) != null) {
							lines.add(line);
							ModelHuaweiSun200028ktlEntity dataModel = serviceHuaweiSun200028ktl.setModelHuaweiSun200028ktl(line);
							dataArrList.add(dataModel);
						}
						
						item.setDatas(dataArrList);
						// Process file based on device model
						switch (item.getDevice_group_table()) {
							case "model_huawei_sun2000_28ktl": {
								serviceHuaweiSun200028ktl.insertModelHuaweiSun200028ktl_v2(item);
								break;
							}
						}
						message = "\nSUCCESS\n";
						fr.close();
						} else {
							message = "\nSUCCESS\n";
						}

					} catch (Exception e) {
						message = "\nFAILURE\n";
					}finally{}

				});
			} else {
				message = "\nFAILURE\n";
				
			}
			
			return message;

		} catch (Exception e) {
			message = "\nFAILURE!\n";
			return message;
		}
	}
	
}