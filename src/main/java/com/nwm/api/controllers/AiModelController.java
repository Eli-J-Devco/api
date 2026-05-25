/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.nwm.api.utils.Lib;
import com.opencsv.CSVWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.entities.AiDataMapEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.SiteAiModelEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.services.AiModelService;
import com.nwm.api.utils.Constants;
import springfox.documentation.annotations.ApiIgnore;
import com.nwm.api.services.AWSService;
import java.io.InputStreamReader;

@RestController
@ApiIgnore
@RequestMapping("/ai-model")
public class AiModelController extends BaseController {
	@Autowired
	private AWSService awsService;
	
	public static String[] push(String[] array, String element) {
        int originalLength = array.length;
        String[] newArray = new String[originalLength + 1];
        System.arraycopy(array, 0, newArray, 0, originalLength);
        newArray[originalLength] = element;
        return newArray;
    }
	
	/**
	 * @description update device data file to s3
	 * @author long.pham
	 * @since 2025-06-17
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/build-data-by-site")
	public Object buildDataBySite(@RequestBody SiteEntity obj) {
		try {

			AiModelService service = new AiModelService();
			List dataDevice = obj.getDataDevice();
			// Get device list 
			if(dataDevice.size() > 0) {
				// Build csv file and upload to s3 aws
				 for(int i = 0; i < dataDevice.size(); i++) {
					 Map<String, Object> item = (Map<String, Object>) dataDevice.get(i);
					 DeviceEntity itemDevice = new DeviceEntity();
					 itemDevice.setId(Integer.parseInt(item.get("id").toString()));
					 
					 itemDevice.setDatatablename(item.get("datatablename").toString());
					 itemDevice.setId_site(Integer.parseInt(item.get("id_site").toString()));
					 itemDevice.setId_device_type(Integer.parseInt(item.get("id_device_type").toString()));
					 itemDevice.setId_device_group(Integer.parseInt(item.get("id_device_group").toString()));
					 itemDevice.setFilterBy(obj.getFilterBy());
					 itemDevice.setDevicename(item.get("name").toString());
					 itemDevice.setId(Integer.parseInt(item.get("id").toString()));
					 itemDevice.setDatatablename(item.get("datatablename").toString());
					 itemDevice.setAi_train_type(obj.getAi_train_type());
					 itemDevice.setStart_date(obj.getStart_date());
					 itemDevice.setEnd_date(obj.getEnd_date());
					 itemDevice.setData_send_time(obj.getData_send_time());
					 
					 
					 List parameters = (List)item.get("parameters");
					 JSONArray outputPeatures = new JSONArray();
					 
					 if(parameters.size() > 0) {
						 for (int v = 0; v < parameters.size(); v++) {
							 Map<String, Object> itemParam = (Map<String, Object>) parameters.get(v);
							 outputPeatures.add(itemParam.get("slug").toString());
						 }
					 }
					 
					 
					 List<Map<String, Object>> parameterLists = service.getParameterByDeviceGroup(itemDevice);
					 if(parameterLists.size() <= 0) {
						 return this.jsonResult(false, "Upload data error", null, 0);
					 }
					 itemDevice.setDataParameters(parameterLists);
					 List<Map<String, Object>> dataFieldList = service.getDataByDevice(itemDevice);
					 String[] header = {};
					 JSONArray inputFeatures = new JSONArray();
					 String inputDatas = "";

					 if(dataFieldList.size() > 0) {
						 String saveDir = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey) +"/"+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathAiModel) + "/"+ itemDevice.getId_site();
						 File theDir = new File(saveDir);
						 if (!theDir.exists()){ theDir.mkdirs(); }
						 
						String fileURL = saveDir +"/"+ itemDevice.getDatatablename() + "_type_"+ itemDevice.getId_device_type() + "_" + itemDevice.getId() + ".csv";
						String fileName = itemDevice.getId_site() + "/"+ itemDevice.getDatatablename() + "_type_" + itemDevice.getId_device_type()+ "_" + itemDevice.getId() + ".csv";
						
					    File file = new File(fileURL);
					    try {
					        FileWriter outputfile = new FileWriter(file);
					        CSVWriter writer = new CSVWriter(outputfile);
					        Map<String, Object> itemParam = (Map<String, Object>) dataFieldList.get(0);
					        for (String key : itemParam.keySet()) {
					        	header = push(header, key);
					        	inputFeatures.add(key);
					        }
					        
					        ObjectMapper objectMapper = new ObjectMapper();
				            try {
				            	inputDatas = objectMapper.writeValueAsString(dataFieldList);
				            } catch (Exception e) {
				                e.printStackTrace();
				            }
				            
				            
					        writer.writeNext(header);
					        for(int k = 0; k < dataFieldList.size(); k++) {
					        	String[] dataField = {};
					        	Map<String, Object> itemData = (Map<String, Object>) dataFieldList.get(k);
					        	 for (String key : itemData.keySet()) {
					        		 String valueItem = itemData.get(key).toString();
					        		 if(valueItem == null || valueItem.isEmpty() || valueItem == "" || valueItem.equals("")) {
					        			 valueItem = "0";
					        		 }
					        		 
					        		 dataField = push(dataField, valueItem);
						        }
					        	writer.writeNext(dataField);
					        }
					        writer.close();
					    }
					    
					    
					    catch (IOException e) {
					        e.printStackTrace();
					    } finally {
							String awsFilePath = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathAiModel) + "/" + fileName;
					    	String awsPath = awsService.uploadFile(fileURL, awsFilePath);
					    	if(awsPath != null) {
					    		String APIURL = "https://ai-training.nextwavemonitoring.com/train";
//					    		String postData = "{\n"
//					    				+ "  \"obj_key\": \"uploads/ai_datasources/247/data1663_model_advanced_energy_solaron_type_1_2025.csv\",\n"
//					    				+ "  \"site_id\": \"247\",\n"
//					    				+ "  \"output_features\": [\n"
//					    				+ "    \"life_kwh\"\n"
//					    				+ "  ],\n"
//					    				+ "  \"categorical_features\": []\n"
//					    				+ "}"; 
//								
//					    		
					    		JSONArray categoricalFeatures = new JSONArray();
					    		categoricalFeatures.add("time_format");
					    		categoricalFeatures.add("categories_time");
					    		categoricalFeatures.add("time_full");
//					    		categoricalFeatures.add("time");
					    		
					    		
					    		
					    		JSONObject postDataJson = new JSONObject();
					    		postDataJson.put("obj_key", "uploads/"+awsFilePath);
					    		postDataJson.put("site_id", Integer.toString(itemDevice.getId_site()));
					    		postDataJson.put("input_features", inputFeatures);
					    		postDataJson.put("output_features", outputPeatures);
					    		postDataJson.put("categorical_features", categoricalFeatures);

								URL url = new URL(APIURL);
								HttpURLConnection conn = (HttpURLConnection) url.openConnection();
								conn.setRequestMethod("POST");
								conn.setRequestProperty("Content-Type", "application/json");
								conn.setDoOutput(true);
								
								try (DataOutputStream os = new DataOutputStream(conn.getOutputStream())) {
					                os.writeBytes(postDataJson.toString());
					                os.flush();
					            }

								conn.connect();
								int responseCode = conn.getResponseCode();
								if (responseCode == HttpURLConnection.HTTP_OK) {
					                StringBuilder response = new StringBuilder();
					                try (
					                    BufferedReader reader = new BufferedReader( new InputStreamReader( conn.getInputStream()))) {
					                    String line;
					                    while ((line = reader.readLine()) != null) {
					                        response.append(line); // Adds every line to response till the end of file.
					                    }
					                }
					                
					                String model_path = response.substring(15);
					                model_path = model_path.substring(0, model_path.length() - 2);
					                
					                // Deploy model
					                String APIURLDeployModel = "https://ai-training.nextwavemonitoring.com/deploy-model";
						    		JSONObject postDataJsonDelop = new JSONObject();
						    		postDataJsonDelop.put("model_path", model_path);
						    		LocalTime currentTime = LocalTime.now();
						    		String modelName = "ai_model_"+ currentTime;
						    		postDataJsonDelop.put("model_name", modelName);
						    		
									URL urlDeploy = new URL(APIURLDeployModel);
									HttpURLConnection connDelopy = (HttpURLConnection) urlDeploy.openConnection();
									connDelopy.setRequestMethod("POST");
									connDelopy.setRequestProperty("Content-Type", "application/json");
									connDelopy.setDoOutput(true);
									
									try (DataOutputStream osDelop = new DataOutputStream(connDelopy.getOutputStream())) {
										osDelop.writeBytes(postDataJsonDelop.toString());
										osDelop.flush();
						            }

									connDelopy.connect();
									int responseCodeDelop = connDelopy.getResponseCode();
									if (responseCodeDelop == HttpURLConnection.HTTP_OK) {
						                StringBuilder responseDelop = new StringBuilder();
						                try (
						                    BufferedReader readerDelop = new BufferedReader( new InputStreamReader( connDelopy.getInputStream()))) {
						                    String lineDelop;
						                    while ((lineDelop = readerDelop.readLine()) != null) {
						                    	responseDelop.append(lineDelop); // Adds every line to response till the end of file.
						                    }
						                }
						                
						                // Get data generation from AI 
						                String APIURLRepdict = "https://ai-training.nextwavemonitoring.com/predict";
							    		JSONObject postData = new JSONObject();
							    		postData.put("model_name", modelName);
							    		
							    		ObjectMapper mapper = new ObjectMapper();
							    		List<Map<String, Object>> list = mapper.readValue(inputDatas.toString(), new TypeReference<List<Map<String, Object>>>() {});
							    		
							    		postData.put("data", list);
										URL urlPredict = new URL(APIURLRepdict);
										HttpURLConnection connPredict = (HttpURLConnection) urlPredict.openConnection();
										connPredict.setRequestMethod("POST");
										connPredict.setRequestProperty("Content-Type", "application/json");
										connPredict.setDoOutput(true);
										
										try (DataOutputStream osPredict = new DataOutputStream(connPredict.getOutputStream())) {
											osPredict.writeBytes(postData.toString());
											osPredict.flush();
							            }
										

										connPredict.connect();
										int responseCodePredict = connPredict.getResponseCode();
										if (responseCodePredict == HttpURLConnection.HTTP_OK) {
							                StringBuilder responsePredict = new StringBuilder();
							                try (
							                    BufferedReader readerPredict = new BufferedReader( new InputStreamReader( connPredict.getInputStream()))) {
							                    String line;
							                    while ((line = readerPredict.readLine()) != null) {
							                    	responsePredict.append(line); // Adds every line to response till the end of file.
							                    }
							                }
							                
							                String dataReplace = (responsePredict.delete(responsePredict.length() - 2, responsePredict.length()) ).substring(16);
							                
							                if(dataReplace.length() > 0) {
							                	String[] dataArray = dataReplace.split(","); 
							                	List listDataMaps = new ArrayList<>();
							                	for (int k = 0; k < dataFieldList.size(); k++) {
							                		Map<String, Object> mapItemObj = dataFieldList.get(k);
							                		AiDataMapEntity mapItem = new AiDataMapEntity();
							                		
							                		
							                		if(obj.getAi_train_type() == 1) {
							                			mapItem.setAi_power(Double.parseDouble(dataArray[k]) > 0 ? Double.parseDouble(dataArray[k]) : 0 );
							                		}
							                		if(obj.getAi_train_type() == 2) {
							                			mapItem.setAi_energy(Double.parseDouble(dataArray[k]) > 0 ? Double.parseDouble(dataArray[k]) : 0 );
							                		}
							                		
							                		mapItem.setDatatablename(item.get("datatablename").toString());
							                		mapItem.setId_device(Integer.parseInt(item.get("id").toString()));
							                		mapItem.setTime(mapItemObj.get("time").toString());
							                		mapItem.setAi_train_type(obj.getAi_train_type());
//							                		service.updateDataDeviceAiGeneration(mapItem);
							                		listDataMaps.add(mapItem);
							                	}
							                	
							                	itemDevice.setListDataMaps(listDataMaps);
							                	service.updateDataDeviceAiGeneration(itemDevice);
							                }							                
										}else {
//							                System.out.println("Error: HTTP Response code - " + responseCodeDelop);
											return this.jsonResult(false, "Error: HTTP Response code - "+ responseCodePredict, null, 0);
							            }
										connPredict.disconnect();
						                
						                // save ai model to database 
//						                SiteAiModelEntity AiModel = new SiteAiModelEntity();
//						                AiModel.setId_site(itemDevice.getId_site());
//						                AiModel.setAi_model_name(modelName); 
//						                AiModel.setModel_name((obj.getModel_name() == "" || obj.getModel_name() == null ? itemDevice.getDevicename() : obj.getModel_name() ) + " - AI Generation ");
//						                AiModel.setData_send_time(obj.getFilterBy());
//						                AiModel.setYear(obj.getYear());
//						                
//						                AiModel.setOutput_fields(outputPeatures.toJSONString());
//						                AiModel.setInput_datas(inputDatas);
//						                service.insertSiteAiModel(AiModel);
						                
//						                

						                
									}else {
//						                System.out.println("Error: HTTP Response code - " + responseCodeDelop);
										return this.jsonResult(false, "Build Model for AI error", null, 0);
						            }
									connDelopy.disconnect();
						            
						            
					                
								}else {
//					                System.out.println("Error: HTTP Response code - " + responseCode);
									return this.jsonResult(false, "Training for AI Error", null, 0);
					            }
					            conn.disconnect();
					    	}
					    }  
					 }
				 }
				 
			}
			
			return this.jsonResult(true, "Training for AI completed", obj, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, "Training for AI Error", null, 0);
		}
	}
	
	
	
	/**
	 * @description Get predict AI model
	 * @author Long.Pham
	 * @since 2024-08-12
	 * @param id_model, id_site
	 * @return data (status, message, array
	 */
	@PostMapping("/get-ai-predict")
	public Object getSiteDetail(@RequestBody SiteAiModelEntity obj) {
		try {

			AiModelService service = new AiModelService();
			SiteAiModelEntity predict = service.getAiModelDetail(obj);
			
			if (predict != null) {
                String APIURLRepdict = "https://ai-training.nextwavemonitoring.com/predict";
	    		JSONObject postData = new JSONObject();
	    		postData.put("model_name", predict.getAi_model_name());
	    		
	    		ObjectMapper mapper = new ObjectMapper();
	    		List<Map<String, Object>> list = mapper.readValue(predict.getInput_datas(), new TypeReference<List<Map<String, Object>>>() {});
	    		
	    		postData.put("data", list);
				URL urlPredict = new URL(APIURLRepdict);
				HttpURLConnection connPredict = (HttpURLConnection) urlPredict.openConnection();
				connPredict.setRequestMethod("POST");
				connPredict.setRequestProperty("Content-Type", "application/json");
				connPredict.setDoOutput(true);
				
				try (DataOutputStream osPredict = new DataOutputStream(connPredict.getOutputStream())) {
					osPredict.writeBytes(postData.toString());
					osPredict.flush();
	            }

				connPredict.connect();
				int responseCodePredict = connPredict.getResponseCode();
				if (responseCodePredict == HttpURLConnection.HTTP_OK) {
	                StringBuilder responsePredict = new StringBuilder();
	                try (
	                    BufferedReader readerPredict = new BufferedReader( new InputStreamReader( connPredict.getInputStream()))) {
	                    String line;
	                    while ((line = readerPredict.readLine()) != null) {
	                    	responsePredict.append(line); // Adds every line to response till the end of file.
	                    }
	                }
	                
	                predict.setPredictions(responsePredict.toString());
				}else {
//	                System.out.println("Error: HTTP Response code - " + responseCodeDelop);
					return this.jsonResult(false, "Error: HTTP Response code - "+ responseCodePredict, null, 0);
	            }
				connPredict.disconnect();
	            
	            
	            
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, predict, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list model AI
	 * @author long.pham
	 * @since 2025-06-20
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-model-ai-by-site")
	public Object getListModelAiBySite(@RequestBody SiteAiModelEntity obj) {
		try {
			AiModelService service = new AiModelService();
			List data = service.getListModelAiBySite(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
}
