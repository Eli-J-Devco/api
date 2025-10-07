/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/

package com.nwm.api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.services.MQTTJsonService;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/mqtt")
public class MQTTJsonController extends BaseController {
	
	/**
	 * @description Process MQTT JSON data
	 * @author Duc.Pham
	 * @since 2025-01-06
	 * @param jsonData - JSON string từ MQTT
	 * @return Object
	 */
	@PostMapping("/process")
	public Object processMQTTJson(@RequestBody String jsonData) {
		try {
			log.info("Received MQTT JSON data: " + jsonData);
			
			// Validation at controller level
			if (jsonData == null || jsonData.trim().isEmpty()) {
				log.error("Empty JSON data received");
				return this.jsonResult(false, "JSON data cannot be empty", null, 0);
			}
			
			// Parse and validate JSON structure at controller level
			Map<String, Object> mqttDataMap = parseJsonData(jsonData);
			if (mqttDataMap.isEmpty()) {
				return this.jsonResult(false, "Invalid JSON format", null, 0);
			}
			
			// Basic field validation at controller level
			String validationError = validateRequiredFields(mqttDataMap);
			if (validationError != null) {
				log.error("Validation failed: " + validationError);
				return this.jsonResult(false, validationError, null, 0);
			}
			
			// Process business logic via service
			MQTTJsonService mqttService = new MQTTJsonService();
			boolean result = mqttService.processMQTTJsonData(jsonData);
			
			if (result) {
				return this.jsonResult(true, "MQTT JSON data processed successfully", null, 1);
			} else {
				return this.jsonResult(false, "Failed to process MQTT JSON data", null, 0);
			}
			
		} catch (Exception ex) {
			log.error("Error in processMQTTJson", ex);
			return this.jsonResult(false, "Error processing MQTT JSON data: " + ex.getMessage(), null, 0);
		}
	}
	
	/**
	 * @description Process MQTT JSON data from Map object
	 * @author Duc.Pham
	 * @since 2025-01-06
	 * @param jsonDataMap - JSON Map object từ MQTT
	 * @return Object
	 */
	@PostMapping("/process-map")
	public Object processMQTTJsonFromMap(@RequestBody Map<String, Object> jsonDataMap) {
		try {
			log.info("Received MQTT JSON Map: " + jsonDataMap);
			
			// Convert Map to JSON string
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(jsonDataMap);
			
			MQTTJsonService mqttService = new MQTTJsonService();
			boolean result = mqttService.processMQTTJsonData(jsonData);
			
			if (result) {
				return this.jsonResult(true, "MQTT JSON data processed successfully", jsonDataMap, 1);
			} else {
				return this.jsonResult(false, "Failed to process MQTT JSON data", jsonDataMap, 0);
			}
			
		} catch (Exception ex) {
			log.error("Error in processMQTTJsonFromMap", ex);
			return this.jsonResult(false, "Error processing MQTT JSON data: " + ex.getMessage(), null, 0);
		}
	}
	
	/**
	 * @description Test data conversion logic only
	 * @author Duc.Pham
	 * @since 2025-01-07
	 * @return Object
	 */
	@PostMapping("/test-conversion")
	public Object testConversion() {
		try {
			// Test MQTT data conversion
			List<String> testData = java.util.Arrays.asList(
				"2025-10-07,01:51:00","1","0","0","0","0","0","0","63744","164","49230","1",
				"0.000","0.000","108.129","0.000","-0.055","-0.055","0.000",
				"273.915","273.442","276","0.992","0.992","0.066","0.492","6","382","15490088","-0.999","59.900"
			);
			
			log.info("Test input: " + testData.size() + " fields - " + testData);
			
			MQTTJsonService service = new MQTTJsonService();
			// Access private method through reflection for testing
			java.lang.reflect.Method method = service.getClass().getDeclaredMethod("convertMQTTDataToCSV", List.class);
			method.setAccessible(true);
			String result = (String) method.invoke(service, testData);
			
			log.info("Conversion result: " + result);
			
			// Count fields in result
			String[] resultFields = result.split(",");
			log.info("Result has " + resultFields.length + " fields");
			
			return this.jsonResult(true, "Conversion test completed - check logs for details", 
				"Input: " + testData.size() + " fields, Output: " + resultFields.length + " fields. Expected: 31→31 with datetime conversion", 1);
			
		} catch (Exception ex) {
			log.error("Test conversion error: " + ex.getMessage(), ex);
			return this.jsonResult(false, "Test error: " + ex.getMessage(), null, 0);
		}
	}

	/**
	 * @description Test endpoint để kiểm tra MQTT JSON service với real data
	 * @author Duc.Pham
	 * @since 2025-01-07
	 * @return Object
	 */
	@PostMapping("/test")
	public Object testMQTTJson() {
		try {
			// Use the actual current MQTT format from user's log
			String testJson = "{"
					+ "\"LOGFILE\":\"mb-104.log\","
					+ "\"SERIALNUMBER\":\"001EC610021A1\","
					+ "\"MODBUSPORT\":\"104\","
					+ "\"MODBUSDEVICE\":\"104\","
					+ "\"MODE\":\"LOGFILEUPLOAD\","
					+ "\"DEVICENAME\":\"INV_Satcon_Powergate225\","
					+ "\"DATA\":["
						+ "\"2025-10-07,01:51:00\","  // Combined datetime format
						+ "\"1\",\"0\",\"0\","
						+ "\"0\",\"0\",\"0\",\"0\","
						+ "\"63744\","
						+ "\"164\","
						+ "\"49230\","
						+ "\"1\","
						+ "\"0.000\","
						+ "\"0.000\","
						+ "\"108.129\","
						+ "\"0.000\",\"-0.055\",\"-0.055\","
						+ "\"0.000\","
						+ "\"273.915\",\"273.442\","
						+ "\"276\","
						+ "\"0.992\","
						+ "\"0.992\","
						+ "\"0.066\","
						+ "\"0.492\","
						+ "\"6\","
						+ "\"382\","
						+ "\"15490088\","
						+ "\"-0.999\","
						+ "\"59.900\""
					+ "]"
				+ "}";
			
			log.info("Testing with current MQTT format: " + testJson);
			
			// Use the same validation process as main endpoint
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> mqttDataMap = mapper.readValue(testJson, new TypeReference<Map<String, Object>>(){});
			
			// Log the extracted DATA array for debugging
			@SuppressWarnings("unchecked")
			List<String> dataArray = (List<String>) mqttDataMap.get("DATA");
			log.info("Extracted DATA array: " + dataArray);
			log.info("First field (datetime): " + (!dataArray.isEmpty() ? dataArray.get(0) : "empty"));
			
			// Validate at controller level
			String validationError = validateRequiredFields(mqttDataMap);
			if (validationError != null) {
				return this.jsonResult(false, "Test validation failed: " + validationError, testJson, 0);
			}
			
			// Process via service (business logic only)
			MQTTJsonService mqttService = new MQTTJsonService();
			boolean result = mqttService.processMQTTJsonData(testJson);
			
			if (result) {
				return this.jsonResult(true, "Test MQTT JSON processed successfully", testJson, 1);
			} else {
				return this.jsonResult(false, "Test failed - unable to process MQTT JSON", testJson, 0);
			}
			
		} catch (Exception ex) {
			log.error("Error in testMQTTJson", ex);
			return this.jsonResult(false, "Test error: " + ex.getMessage(), null, 0);
		}
	}
	
	/**
	 * @description Handle MQTT message from MQTT broker - with controller validation
	 * @author Duc.Pham
	 * @since 2025-01-07
	 * @param message - MQTT message from Spring Integration
	 * @return boolean
	 */
	public boolean handleMQTTMessage(org.springframework.messaging.Message<?> message) {
		try {
			// Get the payload as string
			String jsonData = message.getPayload().toString();
			log.info("Handling MQTT message: " + jsonData);
			
			// Log the message details for debugging
			log.info("MQTT Message Headers: " + message.getHeaders());
			log.info("MQTT Message Payload Type: " + message.getPayload().getClass().getName());
			
			// Validation at controller level (same as REST endpoints)
			if (jsonData == null || jsonData.trim().isEmpty()) {
				log.error("Empty MQTT message payload");
				return false;
			}
			
			// Parse and validate JSON structure
			Map<String, Object> mqttDataMap = parseJsonData(jsonData);
			if (mqttDataMap.isEmpty()) {
				log.error("Invalid JSON format in MQTT message");
				return false;
			}
			
			// Validate required fields
			String validationError = validateRequiredFields(mqttDataMap);
			if (validationError != null) {
				log.error("MQTT message validation failed: " + validationError);
				return false;
			}
			
			// Process using MQTTJsonService (business logic only)
			MQTTJsonService mqttService = new MQTTJsonService();
			boolean result = mqttService.processMQTTJsonData(jsonData);
			
			if (result) {
				log.info("Successfully processed MQTT message");
				return true;
			} else {
				log.error("Failed to process MQTT message - check device exists and data format is correct");
				log.error("Failed JSON data: " + jsonData);
				return false;
			}
			
		} catch (Exception ex) {
			log.error("Error handling MQTT message: " + ex.getMessage(), ex);
			log.error("Message payload was: " + message.getPayload().toString());
			return false;
		}
	}
	
	/**
	 * @description Validate required fields in MQTT JSON - moved from service to controller
	 * @author Duc.Pham
	 * @since 2025-01-07
	 * @param mqttData - Map containing MQTT data
	 * @return String - error message if validation fails, null if success
	 */
	private String validateRequiredFields(Map<String, Object> mqttData) {
		if (mqttData == null) {
			return "MQTT data is null";
		}
		
		String[] requiredFields = {"LOGFILE", "SERIALNUMBER", "MODBUSPORT", "MODBUSDEVICE", "MODE", "DEVICENAME", "DATA"};
		
		for (String field : requiredFields) {
			Object value = mqttData.get(field);
			if (value == null) {
				return "Missing required field: " + field;
			}
			
			// Check string fields are not blank (except DATA)
			if (!"DATA".equals(field) && value instanceof String && Lib.isBlank((String) value)) {
				return "Invalid field value (blank): " + field;
			}
		}
		
		// Validate DATA field structure
		Object dataField = mqttData.get("DATA");
		if (!(dataField instanceof List) || ((List<?>) dataField).isEmpty()) {
			return "DATA field must be a non-empty array";
		}
		
		return null; // Validation passed
	}
	
	/**
	 * @description Parse JSON data with error handling - helper method
	 * @author Duc.Pham
	 * @since 2025-01-07
	 * @param jsonData - JSON string to parse
	 * @return Map<String, Object> - parsed data or null if invalid
	 */
	private Map<String, Object> parseJsonData(String jsonData) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(jsonData, new TypeReference<Map<String, Object>>(){});
		} catch (Exception ex) {
			log.error("JSON parsing error: " + ex.getMessage());
			return new java.util.HashMap<>(); // Return empty map instead of null
		}
	}
}
