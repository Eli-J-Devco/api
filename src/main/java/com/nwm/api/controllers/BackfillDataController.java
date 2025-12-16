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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.Path;
import java.util.UUID;


@RestController
@ApiIgnore
@RequestMapping("/files")
public class BackfillDataController extends BaseController {
	public static String message = "";
	/**
	 * @description upload files datalogger and insert datalogger to database
	 * @author Duc.pham
	 * @since 2025-11-20
	 * @params RequestParam, files 
	 */

	@PostMapping("/backfill-data-site")
	public Object backFillDataSite(HttpServletRequest request,
			@RequestParam(name = "id", required = true) String id, @RequestParam(name = "folder_url",  required = true) String folder_url ) {
		try {
			if (id == null || id.trim().isEmpty() || folder_url == null || folder_url.trim().isEmpty() ) {
				return this.jsonResult(true, Constants.GET_ERROR_MSG, null, 0);
			}
			
			
			// Get list device 
			SiteEntity obj = new SiteEntity();
			obj.setId(Integer.parseInt(id));
			BackFillDataService service = new BackFillDataService();
			List<DeviceEntity> devices = service.getListDeviceBySite(obj);
			if(devices.size() <= 0) { return this.jsonResult(true, Constants.GET_ERROR_MSG, null, 0); }
			
			ModelXGI1500Service serviceModelXGI1500 = new ModelXGI1500Service();
			
			
		
			for(int i = 0; i< devices.size(); i++) {
				DeviceEntity itemDevice = devices.get(i);
				
				// Check folder file exits
				File folder = new File(folder_url + "/"+itemDevice.getModbusdevicenumber());

		        if (folder.exists() && folder.isDirectory()) {
		        	List dataInserts = new ArrayList();
		            // Get list file in folder
					File[] files = folder.listFiles();
					
					if(files.length > 0 ) {
						// Loop read data in file
						for (File file : files) {
							if (file.isFile()) {
								String filePath = folder + "/" + file.getName(); 
								String fileName = file.getName();
								String ext = "";
								if (fileName != null) {
									int lastDot = fileName.lastIndexOf('.');
									if (lastDot >= 0 && lastDot < fileName.length() - 1) { ext = fileName.substring(lastDot + 1); }
								}
								
								switch (ext) {
									case "gz":
								        try (GZIPInputStream gis = new GZIPInputStream(new FileInputStream(filePath));
								            BufferedReader br = new BufferedReader(new InputStreamReader(gis))) {
								            String line;
								            while ((line = br.readLine()) != null) {
								                DeviceEntity getDataItem = generationDataModel(itemDevice, line);
								                if(getDataItem.getDatas().size() > 0) {
								                	dataInserts.addAll(getDataItem.getDatas());
								                }
								            }

								        } catch (IOException e) {
								            e.printStackTrace();
								        }
								        
										break;
									case "log":
										try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
								            String line;
								            while ((line = br.readLine()) != null) {
								            	DeviceEntity getDataItem = generationDataModel(itemDevice, line);
								                if(getDataItem.getDatas().size() > 0) {
								                	dataInserts.addAll(getDataItem.getDatas());
								                }
								            }
								        } catch (IOException e) {
								            e.printStackTrace();
								        }
										break;
										
									case "csv":
										long lineIndex = 0;
										try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
								            String line;
							            	while ((line = br.readLine()) != null) {
							            		if(lineIndex > 0) {
							            			DeviceEntity getDataItem = generationDataModel(itemDevice, line.replace("\"", "'"));
									                if(getDataItem.getDatas().size() > 0) {
									                	dataInserts.addAll(getDataItem.getDatas());
									                }
							            		}
							            		
							            		lineIndex++;
								            	
								            }
								            
								        } catch (IOException e) {
								            e.printStackTrace();
								        }
										break;
								}
							}
						}
					}
					
					// insert data
					if(dataInserts.size() > 0) {
						itemDevice.setDatas(dataInserts);
						service.insertBackFillData(itemDevice);
					}
		        }
			}
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
        
	}
	
	public DeviceEntity generationDataModel(DeviceEntity item, String line) {
	
		List datas = new ArrayList();
		switch (item.getDevice_group_table()) {
	        case "model_xgi150":
	        	ModelXGI1500Service serviceModelXGI1500 = new ModelXGI1500Service();
	            ModelXGI1500Entity dataEntity = serviceModelXGI1500.setModelXGI1500(line);
				dataEntity.setId_device(item.getId());
				datas.add(dataEntity);
				break;
	        case "model_shark250":
	        	ModelShark250Service serviceModelShark250 = new ModelShark250Service();
	        	ModelShark250Entity dataEntityShard250 = serviceModelShark250.setModelShark250(line);
	        	dataEntityShard250.setId_device(item.getId());
				datas.add(dataEntityShard250);
	        	break;
	        case "model_hukseflux_sr30d1_deviceclass_v0":
	        	ModelHukselfluxSr30d1DeviceclassV0Service serviceModelHuk = new ModelHukselfluxSr30d1DeviceclassV0Service();
	        	ModelHukselfluxSr30d1DeviceclassV0Entity dataEntityHuk = serviceModelHuk.setModelHukselfluxSr30d1DeviceclassV0(line);
	        	dataEntityHuk.setId_device(item.getId());
				datas.add(dataEntityHuk);
				
	        	break;
	        case "model_lufft_class8020":
	        	ModelLufftClass8020Service serviceModelLufft = new ModelLufftClass8020Service();
	        	ModelLufftClass8020Entity dataEntityLufft = serviceModelLufft.setModelLufftClass8020(line);
	        	dataEntityLufft.setId_device(item.getId());
				datas.add(dataEntityLufft);
	        	break;
	        case "model_imtsolar_tmodul_class8006":
	        	ModelIMTSolarTmodulClass8006Service serviceModelIMTSolarTmodulClass8006 = new ModelIMTSolarTmodulClass8006Service();
	        	ModelIMTSolarTmodulClass8006Entity dataEntityTmodul = serviceModelIMTSolarTmodulClass8006.setDataModelIMTSolarTmodulClass8006(line);
	        	dataEntityTmodul.setId_device(item.getId());
				datas.add(dataEntityTmodul);
	        	break;
	        case "model_QUINT4_UPS":
	        	ModelQuint4UPSService serviceModelQUPS = new ModelQuint4UPSService();
	        	ModelQuint4UPSEntity dataEntityQuint = serviceModelQUPS.setModelQuint4UPS(line);
	        	dataEntityQuint.setId_device(item.getId());
				datas.add(dataEntityQuint);
	        	break;
	        	
	        case "model_SEL651R":
	        	ModelSEL651RService serviceModelSEL651R = new ModelSEL651RService();
	        	ModelSEL651REntity dataEntitySEL651 = serviceModelSEL651R.setModelSEL651R(line);
	        	dataEntitySEL651.setId_device(item.getId());
				datas.add(dataEntitySEL651);
	        	break;
	        	
	        case "model_ATI_Tracker":
	        	ModelATiTrackerService serviceModelATiTracker = new ModelATiTrackerService();
	        	ModelATiTrackerEntity dataEntityATi = serviceModelATiTracker.setModelATiTracker(line);
	        	dataEntityATi.setId_device(item.getId());
				datas.add(dataEntityATi);
	        	break;
	        	
	        case "model_smartlogger3000":
	        	ModelSmartLogger3000Service serviceModelSML3000 = new ModelSmartLogger3000Service();
	        	ModelSmartLogger3000Entity dataEntitySML300 = serviceModelSML3000.setModelSmartLogger3000(line);
	        	dataEntitySML300.setId_device(item.getId());
				datas.add(dataEntitySML300);
	        	break;
	        	
	        	
	        case "model_huawei_sun2000_28ktl":
	        	ModelHuaweiSun200028ktlService serviceModelSun2000 = new ModelHuaweiSun200028ktlService();
	        	ModelHuaweiSun200028ktlEntity dataEntitySun2000 = serviceModelSun2000.setModelHuaweiSun200028ktl(line);
	        	dataEntitySun2000.setId_device(item.getId());
				datas.add(dataEntitySun2000);
	        	break;
	        	
	        	
	        case "model_imtsolar_tv_class8004":
	        	ModelIMTSolarTvClass8004Service serviceModelSolarTV = new ModelIMTSolarTvClass8004Service();
	        	ModelIMTSolarTvClass8004Entity dataEntitySolarTV = serviceModelSolarTV.setModelIMTSolarTvClass8004(line);
	        	dataEntitySolarTV.setId_device(item.getId());
				datas.add(dataEntitySolarTV);
	        	break;
	        	
	        	
	        	
	        	
        }
		item.setDatas(datas);
		return item;
	}
	
	
	/**
	 * @description upload files datalogger and insert datalogger to database
	 * @author Duc.pham
	 * @since 2025-11-20
	 * @params RequestParam, files 
	 */

	@PostMapping("/backfill-data-csv-file")
	public Object backFillDataCSVFile(HttpServletRequest request,
			@RequestParam(name = "id", required = true) String id, @RequestParam(name = "folder_url",  required = true) String folder_url ) {
		try {
			if (id == null || id.trim().isEmpty() || folder_url == null || folder_url.trim().isEmpty() ) {
				return this.jsonResult(true, Constants.GET_ERROR_MSG, null, 0);
			}
			
			
			// Get list device 
			SiteEntity obj = new SiteEntity();
			obj.setId(Integer.parseInt(id));
			BackFillDataService service = new BackFillDataService();
			List<DeviceEntity> devices = service.getListDeviceBySite(obj);
			if(devices.size() <= 0) { return this.jsonResult(true, Constants.GET_ERROR_MSG, null, 0); }
			
		
			for(int i = 0; i< devices.size(); i++) {
				DeviceEntity itemDevice = devices.get(i);
				
				// Check folder file exits
				File folder = new File(folder_url + "/"+itemDevice.getModbusdevicenumber());

		        if (folder.exists() && folder.isDirectory()) {
		        	List dataInserts = new ArrayList();
		            // Get list file in folder
					File[] files = folder.listFiles();
					
					if(files.length > 0 ) {
						// Loop read data in file
						for (File file : files) {
							if (file.isFile()) {
								String filePath = folder + "/" + file.getName(); 
								String fileName = file.getName();
								String ext = "";
								if (fileName != null) {
									int lastDot = fileName.lastIndexOf('.');
									if (lastDot >= 0 && lastDot < fileName.length() - 1) { ext = fileName.substring(lastDot + 1); }
								}
								
								switch (ext) {
									case "gz":
								        try (GZIPInputStream gis = new GZIPInputStream(new FileInputStream(filePath));
								            BufferedReader br = new BufferedReader(new InputStreamReader(gis))) {
								            String line;
								            while ((line = br.readLine()) != null) {
								                DeviceEntity getDataItem = generationDataModel(itemDevice, line);
								                if(getDataItem.getDatas().size() > 0) {
								                	dataInserts.addAll(getDataItem.getDatas());
								                }
								            }

								        } catch (IOException e) {
								            e.printStackTrace();
								        }
								        
										break;
									case "log":
										try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
								            String line;
								            while ((line = br.readLine()) != null) {
								            	DeviceEntity getDataItem = generationDataModel(itemDevice, line);
								                if(getDataItem.getDatas().size() > 0) {
								                	dataInserts.addAll(getDataItem.getDatas());
								                }
								            }
								        } catch (IOException e) {
								            e.printStackTrace();
								        }
										break;
								}
							}
						}
					}
					
					// insert data
					if(dataInserts.size() > 0) {
						itemDevice.setDatas(dataInserts);
						service.insertBackFillData(itemDevice);
					}
		        }
			}
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
        
	}
	
	
	
}