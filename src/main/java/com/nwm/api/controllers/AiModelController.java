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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.nwm.api.utils.Lib;
import com.opencsv.CSVWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelOpenMeteoWeatherEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.services.AiModelService;
import com.nwm.api.utils.Constants;
import springfox.documentation.annotations.ApiIgnore;
import com.nwm.api.services.AWSService;

//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.IOException;
import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

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
			// Get device list 
			List dataList = service.getListDevices(obj);
			if(dataList.size() > 0) {
				// Build csv file and upload to s3 aws
				 for(int i = 0; i < dataList.size(); i++) {
					 DeviceEntity item = (DeviceEntity) dataList.get(i);
					 List<Map<String, Object>> parameterLists = service.getParameterByDeviceGroup(item);
					 item.setDataParameters(parameterLists);
					 item.setYear(obj.getYear());
					 List<Map<String, Object>> dataFieldList = service.getDataByDevice(item);
					 
					 
					 if(dataFieldList.size() > 0) {
						 String saveDir = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey) +"/"+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathAiModel) + "/"+ item.getId_site();
						 File theDir = new File(saveDir);
						 if (!theDir.exists()){ theDir.mkdirs(); }
						 
						String fileURL = saveDir +"/"+ item.getDatatablename() + "_type_"+ item.getId_device_type() + "_" + obj.getYear() + ".csv";
						String fileName = item.getId_site() + "/"+ item.getDatatablename() + "_type_" + item.getId_device_type()+ "_" + obj.getYear() + ".csv";
						
					    File file = new File(fileURL);
					    try {
					        FileWriter outputfile = new FileWriter(file);
					        CSVWriter writer = new CSVWriter(outputfile);
					        String[] header = {};
					        Map<String, Object> itemParam = (Map<String, Object>) dataFieldList.get(0);
					        for (String key : itemParam.keySet()) {
					        	header = push(header, key);
					        }
					        writer.writeNext(header);
					        for(int k = 0; k < dataFieldList.size(); k++) {
					        	String[] dataField = {};
					        	Map<String, Object> itemData = (Map<String, Object>) dataFieldList.get(k);
					        	 for (String key : itemData.keySet()) {
					        		 String valueItem = itemData.get(key).toString();
					        		 if(valueItem == null || valueItem.isEmpty() || valueItem == "" || valueItem.equals("")) {
					        			 valueItem = "10000";
					        			 System.out.println("valueItem: "+ valueItem);
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
					    		String postData = "{\"obj_key\": \"value\"}"; 
								
								URL url = new URL(APIURL);
								HttpURLConnection conn = (HttpURLConnection) url.openConnection();
								conn.setRequestMethod("POST");
								conn.setRequestProperty("Content-Type", "application/json");
								conn.setDoOutput(true);
								
								try (DataOutputStream os = new DataOutputStream(conn.getOutputStream())) {
					                os.writeBytes(postData);
					                os.flush();
					            }
								
								conn.connect();
								int responseCode = conn.getResponseCode();
								if (responseCode == HttpURLConnection.HTTP_OK) {
									
									 // HTTP_OK or 200 response code generally means that the server ran successfully without any errors
					                StringBuilder response = new StringBuilder();

					                // Read response content
					                // connection.getInputStream() purpose is to obtain an input stream for reading the server's response.
					                try (
					                    BufferedReader reader = new BufferedReader( new InputStreamReader( conn.getInputStream()))) {
					                    String line;
					                    while ((line = reader.readLine()) != null) {
					                        response.append(line); // Adds every line to response till the end of file.
					                    }
					                }
					                System.out.println("Response: " + response.toString());
					                
//								if (responseCode == 200) {
//									Scanner sc = new Scanner(url.openStream());
//									while (sc.hasNext()) {
//										inline += sc.nextLine();
//									}
//									sc.close();
//									
//									ModelOpenMeteoWeatherEntity itemMeteo = new ModelOpenMeteoWeatherEntity();
//									itemMeteo.setId_device(deviceItem.getId());
//									JSONParser parse = new JSONParser();
//									JSONObject jobj = (JSONObject) parse.parse(inline);
//									JSONObject current = (JSONObject) jobj.get("current");
//									JSONObject daily = (JSONObject) jobj.get("daily");
//									JSONArray sunriseArr = (JSONArray) daily.get("sunrise");
//									JSONArray sunsetArr = (JSONArray) daily.get("sunset");
//									JSONArray uvIndexMax = (JSONArray) daily.get("uv_index_max");
//									
//									String sunrise = sunriseArr.get(0).toString() + ":00+00:00";
//									String sunset = sunsetArr.get(0).toString()+ ":00+00:00";
//									double uv_index_max = Double.parseDouble(uvIndexMax.get(0).toString());
//									itemMeteo.setUv_index(uv_index_max);
//									
//									double irradiance = 0;
//									irradiance =  Double.parseDouble(current.get("global_tilted_irradiance").toString());
//									itemMeteo.setIrradiance(irradiance);
//									itemMeteo.setNvm_irradiance(irradiance);
//									
//									double temperature = 0;
//									temperature = Double.parseDouble( current.get("temperature_2m").toString());
//									itemMeteo.setTemperature(temperature);
//									itemMeteo.setNvm_temperature(temperature);
//									itemMeteo.setNvm_panel_temperature(temperature);
//									
//									double humid = 0;
//									humid =  Double.parseDouble(current.get("relative_humidity_2m").toString());
//									itemMeteo.setHumid(humid);
//									
//									double wind = 0;
//									wind = Double.parseDouble( current.get("wind_speed_10m").toString());
//									itemMeteo.setWind_speed(wind);
//									
//									double wind_direction = 0;
//									wind_direction = Double.parseDouble( current.get("wind_direction_10m").toString());
//									itemMeteo.setWind_direction(wind_direction);
//									
//									
//									double surface_pressure;
//									surface_pressure = Double.parseDouble( current.get("surface_pressure").toString());
//									itemMeteo.setSurface_pressure(surface_pressure);
//									
//									
//									double precipitation = 0;
//									precipitation = Double.parseDouble( current.get("precipitation").toString());
//									itemMeteo.setTotal_precipitation(precipitation);
//									
//									double rain = 0;
//									rain =  Double.parseDouble(current.get("rain").toString());
//									itemMeteo.setRain(rain);
//									
//									
//									double snowfall = 0;
//									snowfall =  Double.parseDouble(current.get("snowfall").toString());
//									itemMeteo.setSnowfall(snowfall);
//									
//									
//									double cloud_cover;
//									cloud_cover =  Double.parseDouble(current.get("cloud_cover").toString());
//									itemMeteo.setCloud_cover(cloud_cover);
//									
//									
//									itemMeteo.setSunrise(sunrise);
//									itemMeteo.setSunset(sunset);
//									
//									String date = (String) current.get("time"); 
//									SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
//									SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//									Date parsedDate = inputFormat.parse(date);
//									String formattedDate = outputFormat.format(parsedDate);
//									
//									itemMeteo.setTime(formattedDate);
//									itemMeteo.setHigh_alarm(0);
//									itemMeteo.setLow_alarm(0);
//									itemMeteo.setError(0);
//									itemMeteo.setDatatablename(deviceItem.getDatatablename());
//									service.insertOpenMeteoWeather(itemMeteo);
								}else {
					                System.out.println("Error: HTTP Response code - " + responseCode);
					            }
					            conn.disconnect();
					    	}
					    }  
					 }
				 }
				 
			}
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
}
