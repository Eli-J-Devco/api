/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.text.SimpleDateFormat;
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

import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelVirtualMeterOrInverterEntity;
import com.nwm.api.entities.VirtualDeviceEntity;
import com.nwm.api.services.VirtualDeviceService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/virtual-device")
public class VirtualDeviceController extends BaseController {

	/**
	 * @description Import table virtual device
	 * @author long.pham
	 * @since 2023-06-16
	 * @return {}
	 */
	@GetMapping("/render-data")
	@ResponseBody
	public Object renderDataVirtualDevice(@RequestParam Map<String, Object> params) {
		try {
			String privateKey = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.privateKey);
			
			String token = (String) params.get("token");
			if(token == null || token == "" || !token.equals(privateKey)) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
			int setTime = 2;
			String totalDay = (String) params.get("total_day");
			if(totalDay != null && Integer.parseInt(totalDay) > 0 ) {
				setTime = Integer.parseInt(totalDay);
			}
		    
			String idSite = (String) params.get("id_site");
			int id_site = 0;
			
			if(idSite != null && Integer.parseInt(idSite) > 0 ) {
				id_site = Integer.parseInt(idSite);
			}
			
			VirtualDeviceEntity entity = new VirtualDeviceEntity();
			entity.setId_site(id_site);
		    
			VirtualDeviceService service = new VirtualDeviceService();
			List<?> listSites = service.getListSiteVirtualDevice(entity);

			if (listSites == null || listSites.size() == 0) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}

			for (int i = 0; i < listSites.size(); i++) {
				VirtualDeviceEntity siteItem = (VirtualDeviceEntity) listSites.get(i);
				
				// Get list device by virtual device
				List<?> listDevice = service.getListDevice(siteItem);
				List<?> listWeather = service.getListDeviceWeather(siteItem);
				siteItem.setDevices(listDevice);
				siteItem.setWeathers(listWeather);
				
				if(listDevice.size() > 0) {
					Date now = new Date();
					TimeZone.setDefault(TimeZone.getTimeZone(siteItem.getTime_zone_value()));
					SimpleDateFormat dateFormatCurrent = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
					Calendar calCurrent = Calendar.getInstance();
					calCurrent.setTime(dateFormatCurrent.parse(dateFormatCurrent.format(now)));
					calCurrent.add(Calendar.DATE, -setTime);
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Calendar cal = Calendar.getInstance();
					Date currentDate = calCurrent.getTime();
					
					for(int t = 0; t <= setTime; t++) {
						cal.setTime(currentDate);
						cal.add(Calendar.DATE, t);
						System.out.println("Log virtual device date: "+ dateFormat.format(cal.getTime()) + " 00:00:00");
						
						siteItem.setStart_date(dateFormat.format(cal.getTime()) + " 00:00:00");
						siteItem.setEnd_date(dateFormat.format(cal.getTime()) + " 23:59:59");
						
						List<?> dataPower = service.getDataPower(siteItem);
						if(dataPower.size() > 0){
							ModelVirtualMeterOrInverterEntity deviceItem = new ModelVirtualMeterOrInverterEntity();
							deviceItem.setId_device(siteItem.getId_device());
							deviceItem.setData(dataPower);
							service.insertVirtualDevice(deviceItem);
						}
					}
					
					// Updated last data
					VirtualDeviceEntity lastItem = new VirtualDeviceEntity();
					lastItem.setId_device(siteItem.getId_device());
					lastItem = service.getLastRowVirtualDevice(lastItem);
					
					DeviceEntity deviceEntity = new DeviceEntity();
					deviceEntity.setId(siteItem.getId_device());
					
					if(lastItem.getId_device() > 0) {
						deviceEntity.setLast_updated(lastItem.getTime());
						deviceEntity.setLast_value(lastItem.getNvmActivePower());
						deviceEntity.setField_value1(lastItem.getNvmActivePower());
						deviceEntity.setField_value2(lastItem.getNvmActiveEnergy());
						deviceEntity.setField_value3(lastItem.getNvm_irradiance());
					} else {
						deviceEntity.setLast_updated(null);
						deviceEntity.setLast_value(null);
						deviceEntity.setField_value1(null);
						deviceEntity.setField_value2(null);
						deviceEntity.setField_value3(null);
					}
					
					service.updateDeviceVirtualDevice(deviceEntity);
					
					
				}
			}

			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
}
