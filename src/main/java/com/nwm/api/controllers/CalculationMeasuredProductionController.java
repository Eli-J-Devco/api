/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.CalculationMeasuredProductionEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.WidgetGroupParameterEntity;
import com.nwm.api.services.CalculationMeasureProductionService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/calculations")
public class CalculationMeasuredProductionController extends BaseController {

	/**
	 * @description Import table virtual device
	 * @author long.pham
	 * @since 2023-06-16
	 * @return {}
	 */
	@GetMapping("/measured-production-device")
	@ResponseBody
	public Object renderCalculationMeasuredProduction(@RequestParam Map<String, Object> params) {
		try {
			String privateKey = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.privateKey);
			
			String token = (String) params.get("token");
			if(token == null || token == "" || !token.equals(privateKey)) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
			int day = 1;
			String totalDay = (String) params.get("day");
			if(totalDay != null && Integer.parseInt(totalDay) > 0 ) {
				day = Integer.parseInt(totalDay);
			}
		    
			String idSite = (String) params.get("id_site");
			int id_site = 0;
			
			if(idSite != null && Integer.parseInt(idSite) > 0 ) {
				id_site = Integer.parseInt(idSite);
			}
			
			CalculationMeasuredProductionEntity entity = new CalculationMeasuredProductionEntity();
			entity.setId_site(id_site);
			entity.setTotalDay(day);
		    
			CalculationMeasureProductionService service = new CalculationMeasureProductionService();
			List<?> listDevices = service.getListDevice(entity);
			
			if (listDevices == null || listDevices.size() == 0) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}

			for (int i = 0; i < listDevices.size(); i++) {
				CalculationMeasuredProductionEntity deviceItem = (CalculationMeasuredProductionEntity) listDevices.get(i);
				if(day <= 60) {
					deviceItem.setDatatablename(deviceItem.getView_tablename());
				}
				
				TimeZone.setDefault(TimeZone.getTimeZone(deviceItem.getTime_zone_value()));
				SimpleDateFormat startDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
				SimpleDateFormat endDateFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
				
				Calendar cal = Calendar.getInstance();
				deviceItem.setEnd_date(endDateFormat.format(cal.getTime()));
				cal.add(Calendar.DATE, -day);
				deviceItem.setStart_date(startDateFormat.format(cal.getTime()));
				
				service.updateMeasuredProduction(deviceItem);
			}

			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	/**
	 * @description update measured production device upload FTP
	 * @author long.pham
	 * @since 2023-09-12
	 * @return {}
	 */
	@GetMapping("/update-measured-production-device-upload-ftp")
	@ResponseBody
	public Object updateMeasuredProductionDeviceUploadFTP(@RequestParam Map<String, Object> params) {
		try {
			String privateKey = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.privateKey);
			
			String token = (String) params.get("token");
			if(token == null || token == "" || !token.equals(privateKey)) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
			int day = 4;
			String totalDay = (String) params.get("day");
			if(totalDay != null && Integer.parseInt(totalDay) > 0 ) {
				day = Integer.parseInt(totalDay);
			}
		    
			String idSite = (String) params.get("id_site");
			int id_site = 0;
			
			if(idSite != null && Integer.parseInt(idSite) > 0 ) {
				id_site = Integer.parseInt(idSite);
			}
			
			CalculationMeasuredProductionEntity entity = new CalculationMeasuredProductionEntity();
			entity.setId_site(id_site);
			entity.setTotalDay(day);
		    
			CalculationMeasureProductionService service = new CalculationMeasureProductionService();
			List<?> listDevices = service.getListDeviceUpdateMeasuredProductionFTP(entity);
			
			if (listDevices == null || listDevices.size() == 0) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}

			
			for (int i = 0; i < listDevices.size(); i++) {
				CalculationMeasuredProductionEntity deviceItem = (CalculationMeasuredProductionEntity) listDevices.get(i);
				
				TimeZone.setDefault(TimeZone.getTimeZone(deviceItem.getTime_zone_value()));
				SimpleDateFormat startDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
				SimpleDateFormat endDateFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
				
				Calendar cal = Calendar.getInstance();
				deviceItem.setEnd_date(endDateFormat.format(cal.getTime()));
				cal.add(Calendar.DATE, -day);
				deviceItem.setStart_date(startDateFormat.format(cal.getTime()));

				service.updateMeasuredProduction(deviceItem);
			}
			
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Import table virtual device
	 * @author long.pham
	 * @since 2023-06-16
	 * @return {}
	 */
	@GetMapping("/move-data")
	@ResponseBody
	public Object renderMoveData(@RequestParam Map<String, Object> params) {
		try {
			String privateKey = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.privateKey);
			
			String token = (String) params.get("token");
			if(token == null || token == "" || !token.equals(privateKey)) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
			
		    
			String idSite = (String) params.get("id_site");
			int id_site = 0;
			
			if(idSite != null && Integer.parseInt(idSite) > 0 ) {
				id_site = Integer.parseInt(idSite);
			}
			
			CalculationMeasuredProductionEntity entity = new CalculationMeasuredProductionEntity();
			entity.setId_site(id_site);
		    
			CalculationMeasureProductionService service = new CalculationMeasureProductionService();
			
			// Create site data report 
			SiteEntity siteItem = new SiteEntity();
			siteItem.setId(id_site);
			
			SiteEntity getDetailSite = service.getDetailSite(siteItem);
			if(getDetailSite.getId() > 0) {
				service.insertTableReport(getDetailSite);
			}
			
			
			List<?> listDevices = service.getListDeviceMoveData(entity);
			if (listDevices == null || listDevices.size() == 0) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}

			for (int i = 0; i < listDevices.size(); i++) {
				CalculationMeasuredProductionEntity deviceItem = (CalculationMeasuredProductionEntity) listDevices.get(i);
				deviceItem.setNewtablename("data"+deviceItem.getId() +"_"+ deviceItem.getDevice_group_table());
				
				// Create new table 
				service.insertTable(deviceItem);
			}

			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	/**
	 * @description Import table virtual device
	 * @author long.pham
	 * @since 2023-06-16
	 * @return {}
	 */
	@GetMapping("/widget-fields")
	@ResponseBody
	public Object renderCalculationWidgetFields(@RequestParam Map<String, Object> params) {
		try {
			String privateKey = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.privateKey);
			
			String token = (String) params.get("token");
			if(token == null || token == "" || !token.equals(privateKey)) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}

		    
			String idSite = (String) params.get("id_site");
			int id_site = 0;
			
			if(idSite != null && Integer.parseInt(idSite) > 0 ) {
				id_site = Integer.parseInt(idSite);
			}
			
			CalculationMeasuredProductionEntity entity = new CalculationMeasuredProductionEntity();
			entity.setId_site(id_site);
		    
			CalculationMeasureProductionService service = new CalculationMeasureProductionService();
			List<?> listFields = service.getListWidgetGroupParameter(entity);
			
			if (listFields == null || listFields.size() == 0) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
			
			for (int i = 0; i < listFields.size(); i++) {
				@SuppressWarnings("unchecked")
				Map<String, Object> deviceItem = (Map<String, Object>) listFields.get(i);
				String idDevices = deviceItem.get("id_device").toString();
				if(idDevices.equals(null) || idDevices == "") { continue; }
				String[] arrDevices = idDevices.split(",");
				ArrayList<String> strList = new ArrayList<String>( Arrays.asList(arrDevices)); 
				if(strList.size() < 0) { continue; }
				String tablename = "data"+strList.get(0)+"_"+deviceItem.get("table_group");
				WidgetGroupParameterEntity item = new WidgetGroupParameterEntity();
				item.setTablename(tablename);
				item.setDataDevices(strList);
				item.setFieldname(deviceItem.get("fieldname").toString());
				item.setFormula(Integer.parseInt(deviceItem.get("formula").toString()));
				item.setType(Integer.parseInt(deviceItem.get("type").toString()));
				
				
				long totalRecord = service.getValueField(item);
				item.setId(Integer.parseInt(deviceItem.get("id").toString()));
				item.setTotalRecord(totalRecord);
				service.updateValueField(item);
				
			}

			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
}
