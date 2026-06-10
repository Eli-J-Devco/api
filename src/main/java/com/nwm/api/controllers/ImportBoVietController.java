/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.util.List;
import java.io.File;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.*;
import com.nwm.api.services.*;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/import-old-data-boviet")
public class ImportBoVietController extends BaseController {


	
	/**
	 * @description Get list role
	 * @author long.pham
	 * @since 2020-12-30
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/restore-data")
	@ResponseBody
	public Object restoreDataCSV(HttpServletRequest request,
			@RequestParam(name = "id", required = true) String id) {
		try {
			if (id == null || id.trim().isEmpty()) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
			}
			SiteEntity obj = new SiteEntity();
			obj.setId(Integer.parseInt(id));
			ImportOldDataBoVietService service = new ImportOldDataBoVietService();
			List<DeviceEntity> devices = service.getListDeviceBySite(obj);
			if(devices.size() <= 0) { return this.jsonResult(true, Constants.GET_ERROR_MSG, null, 0); }
			
			File folder = new File("/Volumes/Data/restores_data/Gaskins/001EC6100A96");
			File[] files = folder.listFiles(new FilenameFilter() {
	            @Override
	            public boolean accept(File dir, String name) {
	                return name.toLowerCase().endsWith(".csv");
	            }
	        });
			
			if (files == null) { return this.jsonResult(true, Constants.GET_ERROR_MSG, null, 0); }
			
			
			ModelHuaweiSun200028ktlService serviceHuaweiSun200028ktl = new ModelHuaweiSun200028ktlService();
			ModelIMTSolarTvClass8004Service serviceModelIMT = new ModelIMTSolarTvClass8004Service();
			ModelSmartLogger3000Service serviceSmartLogger3000 = new ModelSmartLogger3000Service();
			ModelQuint4UPSService serviceModelQUPS = new ModelQuint4UPSService();
			ModelGinlongSolisInverterClass6007Service serviceModelGinlong = new ModelGinlongSolisInverterClass6007Service();
			ModelVerisIndustriesE51c2PowerMeterService serviceModelVeris = new ModelVerisIndustriesE51c2PowerMeterService();
			
			for (File file : files) {
				if (file.isFile()) { // only files, not sub-directories
					String filePath = folder + "/" + file.getName(); // your CSV file path
					System.out.println(filePath);
					
					String fileName = file.getName();
					
					String modbusdevicenumber = fileName.substring(0, fileName.indexOf('.')).replace("mb-", "");
					
					DeviceEntity deviceItem = new DeviceEntity();
					if(Integer.parseInt(modbusdevicenumber) > 0){
                		for (DeviceEntity device : devices) {
                			if(Integer.parseInt(device.getModbusdevicenumber()) == Integer.parseInt(modbusdevicenumber) ) {
                				deviceItem = device;
                			}
                        }
                	}

					long lineIndex = 0;
					try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
						String line;
                        while ((line = br.readLine()) != null) {
                        	if(lineIndex > 0) {
                        		// save data 
                				switch(deviceItem.getDevice_group_table()) {
                    				case "model_huawei_sun2000_28ktl":
                    					ModelHuaweiSun200028ktlEntity dataEntity = serviceHuaweiSun200028ktl.setModelHuaweiSun200028ktl(line.replace("\"", "'"));
                    					
                    					dataEntity.setId_device(deviceItem.getId());
										dataEntity.setDatatablename(deviceItem.getDatatablename());
										dataEntity.setView_tablename(deviceItem.getView_tablename());
										dataEntity.setJob_tablename(deviceItem.getJob_tablename());
                    					serviceHuaweiSun200028ktl.insertModelHuaweiSun200028ktl(dataEntity);
                    					break;
                    				case "model_imtsolar_tv_class8004":
                    					ModelIMTSolarTvClass8004Entity dataIMTEntity = serviceModelIMT.setModelIMTSolarTvClass8004(line.replace("\"", "'"));
                    					dataIMTEntity.setId_device(deviceItem.getId());
                    					dataIMTEntity.setDatatablename(deviceItem.getDatatablename());
                    					dataIMTEntity.setView_tablename(deviceItem.getView_tablename());
                    					dataIMTEntity.setJob_tablename(deviceItem.getJob_tablename());
                    					serviceModelIMT.insertModelIMTSolarTvClass8004(dataIMTEntity);
                    					break;
                    					
                    				case "model_smartlogger3000":
                    					ModelSmartLogger3000Entity dataSMEntity = serviceSmartLogger3000.setModelSmartLogger3000(line.replace("\"", "'"));
                    					dataSMEntity.setId_device(deviceItem.getId());
                    					dataSMEntity.setDatatablename(deviceItem.getDatatablename());
                    					dataSMEntity.setView_tablename(deviceItem.getView_tablename());
                    					dataSMEntity.setJob_tablename(deviceItem.getJob_tablename());
                    					serviceSmartLogger3000.insertModelSmartLogger3000(dataSMEntity);
                    					break;
                    				
                    				case "model_QUINT4_UPS":
                    					ModelQuint4UPSEntity dataUPSEntity = serviceModelQUPS.setModelQuint4UPS(line.replace("\"", "'"));
                    					dataUPSEntity.setId_device(deviceItem.getId());
                    					dataUPSEntity.setDatatablename(deviceItem.getDatatablename());
                    					dataUPSEntity.setView_tablename(deviceItem.getView_tablename());
                    					dataUPSEntity.setJob_tablename(deviceItem.getJob_tablename());
                    					serviceModelQUPS.insertModelQuint4UPS(dataUPSEntity);
                    					break;
                    					
                    					
                    				case "model_veris_industries_e51c2_power_meter":
                    					ModelVerisIndustriesE51c2PowerMeterEntity dataVerEntity = serviceModelVeris.setModelChintSolectriaInverterClass9725(line.replace("\"", "'"));
                    					dataVerEntity.setId_device(deviceItem.getId());
                    					dataVerEntity.setDatatablename(deviceItem.getDatatablename());
                    					dataVerEntity.setView_tablename(deviceItem.getView_tablename());
                    					dataVerEntity.setJob_tablename(deviceItem.getJob_tablename());
                    					serviceModelVeris.insertModelVerisIndustriesE51c2PowerMeter(dataVerEntity);
                    					break;
                    				case "model_ginlong_solis_inverter_class6007":
                    					ModelGinlongSolisInverterClass6007Entity dataGinEntity = serviceModelGinlong.setModelGinlongSolisInverterClass6007(line.replace("\"", "'"));
                    					dataGinEntity.setId_device(deviceItem.getId());
                    					dataGinEntity.setDatatablename(deviceItem.getDatatablename());
                    					dataGinEntity.setView_tablename(deviceItem.getView_tablename());
                    					dataGinEntity.setJob_tablename(deviceItem.getJob_tablename());
                    					serviceModelGinlong.insertGinlongSolisInverterClass6007(dataGinEntity);
                    					break;
                    					
                				}
                        		
                        	}

                            lineIndex++;

                        }
					} catch (IOException e) {
                      e.printStackTrace();
	                } finally {
	                      // move file to new folder
	                  	try {
	                          Path source = Paths.get(filePath);  
	                          Path targetDir =   Paths.get(folder + "/done");
	
	                          // Create target folder if it doesn't exist
	                          if (!Files.exists(targetDir)) {
	                              Files.createDirectories(targetDir);
	                          }
	
	                          // Move the file
	                          Files.move(source, targetDir.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
	
	                          System.out.println(filePath + " File moved successfully!");
	                      } catch (Exception e) {
	                          e.printStackTrace();
	                      }
	                  	
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
	

	/**
	 * @description Get list role
	 * @author long.pham
	 * @since 2020-12-30
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/render-data")
	@ResponseBody
	public Object uploadFiles(HttpServletRequest request,
			@RequestParam(name = "id", required = true) String id) {
		try {
			if (id == null || id.trim().isEmpty()) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
			}
			SiteEntity obj = new SiteEntity();
			obj.setId(Integer.parseInt(id));
			ImportOldDataBoVietService service = new ImportOldDataBoVietService();
			List<DeviceEntity> devices = service.getListDeviceBySite(obj);
			if(devices.size() <= 0) { return this.jsonResult(true, Constants.GET_ERROR_MSG, null, 0); }
			
			File folder = new File("/Volumes/Data/Godbee/test_file");
			File[] files = folder.listFiles(new FilenameFilter() {
	            @Override
	            public boolean accept(File dir, String name) {
	                return name.toLowerCase().endsWith(".csv");
	            }
	        });
			
			if (files == null) { return this.jsonResult(true, Constants.GET_ERROR_MSG, null, 0); }
			
			for (File file : files) {
				if (file.isFile()) { // only files, not sub-directories
					String filePath = folder + "/" + file.getName(); // your CSV file path
					System.out.println(filePath);
					long lineIndex = 0;
					long totalLine = 0;
					try (BufferedReader brLine = new BufferedReader(new FileReader(filePath))) {
						while (brLine.readLine() != null) {
							totalLine++;
			            }
					} catch (IOException e) {
						e.printStackTrace();
	                }
					
					try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
						String line;
						String serialNumber = "", serialNumberTmp = "";
						List dataInserts = new ArrayList();	
                        DeviceEntity deviceItem = new DeviceEntity();
                        while ((line = br.readLine()) != null) {
                            lineIndex++;
                        	int indexFindSerialNumber = line.indexOf("ESN:");
                        	if (indexFindSerialNumber != -1) {
                            	String[] stringArray = line.split(":");
                            	if(stringArray.length > 1) {
                            		serialNumberTmp = stringArray[1];
                            	}
                            } else {
                            	serialNumber = serialNumberTmp;
                            }
                        	
                        	if(totalLine - 1 == lineIndex) {
                        		serialNumber = "end";
                        	}
                        	
                        	int indexTitle = line.indexOf("#Time");
                        	if(indexTitle != -1) { continue; }
                        	
                        	if(serialNumber.equals(serialNumberTmp) || "end".equals(serialNumber)){
                        		for (DeviceEntity device : devices) {
                                    if ((device.getSerialnumber()).equals(serialNumber) || ((device.getSerialnumber()).equals(serialNumberTmp) && "end".equals(serialNumber)  ) ) {
                                    	deviceItem = device;
                                        break;
                                    }
                                }
                        	}
                        	
                        	if((serialNumber.equals(serialNumberTmp) || "end".equals(serialNumber)) && indexTitle == -1 && deviceItem.getId() > 0) {
                        		String[] stringValue = line.split(";");
                        		String dateStr ="20"+stringValue[0] + " EDT";
                            	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
                                DateTimeFormatter formatterSql = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
                                ZonedDateTime newYorkTime = localDateTime.atZone(ZoneId.of("America/New_York"));
                                // Convert to UTC
                                ZonedDateTime utcTime = newYorkTime.withZoneSameInstant(ZoneId.of("UTC"));
                                
                            	switch(deviceItem.getDevice_group_table()) {
                                	case "model_huawei_sun2000_28ktl":
                                    	ModelHuaweiSun200028ktlEntity item = new ModelHuaweiSun200028ktlEntity();
                                    	if(stringValue.length > 0) {
                                    		item.setDatatablename(deviceItem.getDatatablename());
                                    		item.setView_tablename(deviceItem.getView_tablename());
                                    		item.setTime(utcTime.format(formatterSql));
                                    		item.setId_device(deviceItem.getId()) ;
                                    		item.setError(0);
                                    		item.setLow_alarm(0);
                                    		item.setHigh_alarm(0);
                                    		item.setActivePower(stringValue.length > 24 && !Lib.isBlank(stringValue[24]) ? Double.parseDouble(stringValue[24]) * 10: 0);
                                    		item.setReactivePower(stringValue.length > 25 && !Lib.isBlank(stringValue[25]) ? Double.parseDouble(stringValue[25]): 0);
                                    		Double totalDC = 
                                    				stringValue.length > 7 && !Lib.isBlank(stringValue[7]) ? Double.parseDouble(stringValue[7]): 0 + 
                                    						stringValue.length > 8 && !Lib.isBlank(stringValue[8]) ? Double.parseDouble(stringValue[8]): 0 + 
                                    								stringValue.length > 9 && !Lib.isBlank(stringValue[9]) ? Double.parseDouble(stringValue[9]): 0 + 
                                    										stringValue.length > 10 && !Lib.isBlank(stringValue[10]) ? Double.parseDouble(stringValue[10]): 0 + 
                                    												stringValue.length > 11 && !Lib.isBlank(stringValue[11]) ? Double.parseDouble(stringValue[11]): 0 + 
                                    														stringValue.length > 12 && !Lib.isBlank(stringValue[12]) ? Double.parseDouble(stringValue[12]): 0;
                                    		item.setTotalDCInputCurrent(totalDC);
                                    		item.setTotalInputPower(0);
                                    		item.setInsulationResistance(0);
                                    		item.setPowerFactor(0);
                                    		item.setInverterStatus(stringValue.length > 19 && !Lib.isBlank(stringValue[19]) ? Double.parseDouble(stringValue[19]): 0);
                                    		item.setCabinetTemperature(stringValue.length > 21 && !Lib.isBlank(stringValue[21]) ? Double.parseDouble(stringValue[21]): 0);
                                    		item.setMajorFaultCode(stringValue.length > 20 && !Lib.isBlank(stringValue[20]) ? Double.parseDouble(stringValue[20]): 0);
                                    		item.setMinorFaultCode(0);
                                    		item.setWarningCode(0);
                                    		item.setNvmActivePower(stringValue.length > 24 && !Lib.isBlank(stringValue[24]) ? Double.parseDouble(stringValue[24]) * 10: 0 );
                                    		item.setNvmActiveEnergy(0);
                                    		item.setMeasuredProduction(stringValue.length > 26 && !Lib.isBlank(stringValue[26]) ? Double.parseDouble(stringValue[26]) * 100: 0 );
                                    		dataInserts.add(item);
                                    	}
                                    	
                                		break;
                                	case "model_imtsolar_tv_class8004":
                        				if(stringValue.length > 0) {
                        					ModelIMTSolarTvClass8004Entity dataEntity = new ModelIMTSolarTvClass8004Entity();
	                                		dataEntity.setDatatablename(deviceItem.getDatatablename());
	                                		dataEntity.setView_tablename(deviceItem.getView_tablename());
	                                		dataEntity.setTime(utcTime.format(formatterSql));
	                                		dataEntity.setId_device(deviceItem.getId()) ;
	                                		dataEntity.setError(0);
	                                		dataEntity.setLow_alarm(0);
	                                		dataEntity.setHigh_alarm(0);
	                                		dataEntity.setIrradiance(stringValue.length > 5 && !Lib.isBlank(stringValue[5]) ? Double.parseDouble(stringValue[5]): 0);
	                                		dataEntity.setTcell(stringValue.length > 3 && !Lib.isBlank(stringValue[3]) ? Double.parseDouble(stringValue[3]): 0);
	                                		dataEntity.setText(stringValue.length > 4 && !Lib.isBlank(stringValue[4]) ? Double.parseDouble(stringValue[4]): 0);
	                                		dataEntity.setWspeed(stringValue.length > 1 && !Lib.isBlank(stringValue[1]) ? Double.parseDouble(stringValue[1]): 0);
	                                		dataEntity.setNvm_irradiance(stringValue.length > 5 && !Lib.isBlank(stringValue[5]) ? Double.parseDouble(stringValue[5]): 0);
	                                		dataEntity.setNvm_temperature(stringValue.length > 3 && !Lib.isBlank(stringValue[3]) ? Double.parseDouble(stringValue[3]): 0);
	                                		dataEntity.setNvm_panel_temperature(0);
	                                		dataInserts.add(dataEntity);
                        				}
                                		
                                		break;
                            	}
                        	}
                        	
                        	
                        	
                        	
                        	if(!serialNumber.equals(serialNumberTmp) && deviceItem.getId() > 0 && dataInserts.size() > 0) {
                        		deviceItem.setParameters(dataInserts);
                				// save data 
                				switch(deviceItem.getDevice_group_table()) {
                    				case "model_huawei_sun2000_28ktl":
                    					service.insertModelHuaweiSun200028ktl(deviceItem);
                    					dataInserts = new ArrayList();	
                    					break;
                    				case "model_imtsolar_tv_class8004":
                    					service.insertModelImtsolarTvClass8004(deviceItem);
                    					dataInserts = new ArrayList();	
                    					break;
                				}
                        	}
                        }
					} catch (IOException e) {
                      e.printStackTrace();
	                } finally {
	                      // move file to new folder
	                  	try {
	                          Path source = Paths.get(filePath);
	                          Path targetDir = Paths.get("/Volumes/Data/Godbee/done");
	
	                          // Create target folder if it doesn't exist
	                          if (!Files.exists(targetDir)) {
	                              Files.createDirectories(targetDir);
	                          }
	
	                          // Move the file
	                          Files.move(source, targetDir.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
	
	                          System.out.println(filePath + " File moved successfully!");
	                      } catch (Exception e) {
	                          e.printStackTrace();
	                      }
	                  	
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
