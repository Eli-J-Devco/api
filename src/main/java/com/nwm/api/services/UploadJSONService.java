/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DeviceParameterN3uronEntity;
import com.nwm.api.entities.UploadN3uronEntity;

import net.objecthunter.exp4j.ExpressionBuilder;

public class UploadJSONService extends DB {
	
	/**
	 * @description upload data from N3uron
	 * @author Hung.Bui
	 * @since 2024-05-21
	 * @params obj { serial_number, id_device, parameters: List<DeviceParameterN3uronEntity> }
	 */
	public boolean n3euronUploads(UploadN3uronEntity obj) {
		try {
			DeviceEntity deviceDetail = (DeviceEntity) queryForObject("UploadJSON.getDeviceDetail", obj);
			if (deviceDetail == null) return false;
			ObjectMapper mapper = new ObjectMapper();
			List<Map<String, Object>> parameters = mapper.readValue(deviceDetail.getParameter_slug(), new TypeReference<List<Map<String, Object>>>(){});
			Set<Integer> inverterMeterType = new HashSet<Integer>(Arrays.asList(1,3,7,8,9));
			
			/* add user defined parameter */
			List<DeviceParameterN3uronEntity> data = obj.getParameters();
			
			if (inverterMeterType.contains(deviceDetail.getId_device_type())) {
				Map<String, Object> activePowerParameter = parameters.stream().filter(item -> Integer.parseInt(item.get("is_user_defined").toString()) == 0 && Integer.parseInt(item.get("is_active_power").toString()) == 1).findFirst().orElse(null);
				Double activePowerValue = activePowerParameter != null ? data.stream().filter(item -> item.getName().equals(activePowerParameter.get("slug").toString())).findFirst().orElse(new DeviceParameterN3uronEntity()).getValue() : null;
				DeviceParameterN3uronEntity nvmActivePower = new DeviceParameterN3uronEntity();
				nvmActivePower.setName("nvmActivePower");
				nvmActivePower.setValue(activePowerValue);
				data.add(nvmActivePower);
				
				Map<String, Object> energyParameter = parameters.stream().filter(item -> Integer.parseInt(item.get("is_user_defined").toString()) == 0 && Integer.parseInt(item.get("is_energy").toString()) == 1).findFirst().orElse(null);
				Double energyValue = energyParameter != null ? data.stream().filter(item -> item.getName().equals(energyParameter.get("slug").toString())).findFirst().orElse(new DeviceParameterN3uronEntity()).getValue() : null;
				DeviceParameterN3uronEntity nvmActiveEnergy = new DeviceParameterN3uronEntity();
				nvmActiveEnergy.setName("nvmActiveEnergy");
				nvmActiveEnergy.setValue(energyValue);
				data.add(nvmActiveEnergy);
				
				Map<String, Object> lastData = (Map<String, Object>) queryForObject("UploadJSON.getLastData", deviceDetail);
				double lastEnergyValue = lastData != null ? Double.parseDouble(lastData.get(energyParameter.get("slug").toString()).toString()) : 0;
				double measuredProductionValue = energyValue > 0 && lastEnergyValue > 0 && energyValue > lastEnergyValue ? (energyValue - lastEnergyValue) : 0;
				DeviceParameterN3uronEntity measuredProduction = new DeviceParameterN3uronEntity();
				measuredProduction.setName("MeasuredProduction");
				measuredProduction.setValue(measuredProductionValue);
				data.add(measuredProduction);
			}
			
			/******************************/
			
			/* parameter scale */
			List<Map<String, Object>> scaledParameters = parameters.stream().filter(item -> item.get("scale") != null).collect(Collectors.toList());
			if (scaledParameters.size() > 0) {
				for (int j = 0; j < scaledParameters.size(); j++) {
					Map<String, Object> scaledDeviceParameter = scaledParameters.get(j);
					String scaledSlug = scaledDeviceParameter.get("slug").toString();
					String scaleExpressions = scaledDeviceParameter.get("scale").toString();
					String variableName = scaledDeviceParameter.get("variable_name").toString();
					DeviceParameterN3uronEntity scaledParameter = data.stream().filter(item -> item.getName().equals(scaledSlug)).findFirst().orElse(null);
					Double initialValue = scaledParameter != null ? scaledParameter.getValue() : null;
					if (initialValue == null) continue;
					Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
					scaledParameter.setValue(scaledValue);
					
					// user defined parameter scale
					if (inverterMeterType.contains(deviceDetail.getId_device_type())) {
						Map<String, Object> activePowerParameter = parameters.stream().filter(item -> Integer.parseInt(item.get("is_user_defined").toString()) == 0 && Integer.parseInt(item.get("is_active_power").toString()) == 1).findFirst().orElse(null);
						if (activePowerParameter != null && scaledSlug.equals(activePowerParameter.get("slug"))) data.stream().filter(item -> item.getName().equals("nvmActivePower")).findFirst().orElse(new DeviceParameterN3uronEntity()).setValue(scaledValue);
						
						Map<String, Object> energyParameter = parameters.stream().filter(item -> Integer.parseInt(item.get("is_user_defined").toString()) == 0 && Integer.parseInt(item.get("is_energy").toString()) == 1).findFirst().orElse(null);
						if (energyParameter != null && scaledSlug.equals(energyParameter.get("slug"))) data.stream().filter(item -> item.getName().equals("nvmActiveEnergy")).findFirst().orElse(new DeviceParameterN3uronEntity()).setValue(scaledValue);
					}
				}
			}
			/****************************/
			
			/* insert data */
			deviceDetail.setList_parameters(data);
			Integer insert = (Integer) insert("UploadJSON.insertN3euronData", deviceDetail);
			if (insert == null || insert == 0) return false;
			/***************/
			
			/* update current device data */
			List<String> defaultParameters = mapper.readValue(deviceDetail.getField_value_default(), new TypeReference<List<String>>(){});
			
			if (defaultParameters.size() > 0) {
				DeviceService deviceService = new DeviceService();
				
				for (int i = 0; i < defaultParameters.size(); i++) {
					int innerI = i;
					DeviceParameterN3uronEntity parameter = data.stream().filter(item -> item.getName().equals(defaultParameters.get(innerI))).findFirst().orElse(null);
					Double value = parameter != null ? parameter.getValue() : null;
					
					switch (i) {
						case 0:
							if (value >= 0) deviceDetail.setLast_updated(deviceDetail.getCurrent_time());
							deviceDetail.setLast_value(value);
							deviceDetail.setField_value1(value);
							break;
						case 1:
							deviceDetail.setField_value2(value);
							break;
						case 2:
							deviceDetail.setField_value3(value);
							break;
						default:
							break;
					}
				}
				
				deviceService.updateLastUpdated(deviceDetail);
			}
			/******************************/
			
			/* alarm */
			/*********/
			
			return true;
		} catch (Exception e) {
			log.error("UploadJSON.n3euronUploads", e);
			return false;
		}
	}
}
