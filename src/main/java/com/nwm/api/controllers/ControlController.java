/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.s7connector.api.DaveArea;
import com.github.s7connector.api.S7Connector;
import com.github.s7connector.api.factory.S7ConnectorFactory;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.services.ControlService;
import com.nwm.api.services.SitesDevicesService;
import com.nwm.api.utils.Constants;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/control")
public class ControlController extends BaseController {
	
	
    
    /**
	 * @description Get list device by site
	 * @author long.pham
	 * @since 2022-02-09
	 * @return data (status, message, array, total_row
	 */
//	@PostMapping("/readPLCS71200")
//	public Object readPLCS71200(@RequestBody DeviceEntity obj) {
//		try {
//			//Open TCP Connection
//		    S7Connector connector = S7ConnectorFactory
//		            .buildTCPConnector()
//		            .withHost("192.168.1.101")
//		            .withRack(0) //optional
//		            .withSlot(1) //optional
//		            .build();
//		    
//		    byte[] bs = connector.read(DaveArea.DB, 3, 12, 0);
//		    for (int i = 0; i < bs.length; i++) {
//			    System.out.println(bs[i]);
//			}
//		    
//		    byte[] test = new byte[] {(byte) 0, (byte) 11, (byte) 0, (byte) 12,(byte) 0, (byte) 13,(byte) 0, (byte) 14,(byte) 0, (byte) 15,(byte) 0, (byte) 16};
//		    connector.write(DaveArea.DB, 3, 12, test);
//			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, null, 10);
//		} catch (Exception e) {
//			log.error(e);
//			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
//		}
//	}
//	
	/**
	 * @description Get detail site 
	 * @author long.pham
	 * @since 2021-03-12
	 * @param id_site
	 * @return data (status, message, object, total_row
	 */

//	@PostMapping("/detail")
//	public Object getDetailSite(@RequestBody SitesDevicesEntity obj) {
//		try {			
//			SitesDevicesService service = new SitesDevicesService();
//			SitesDevicesEntity getDetail = service.getDetail(obj);
//			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDetail, 1);
//		} catch (Exception e) {
//			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
//		}
//	}
//	
//	
//	
//	
	/**
	 * @description Get list device by site
	 * @author long.pham
	 * @since 2022-02-09
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/getListInverter")
	public Object getListInverter(@RequestBody DeviceEntity obj) {
		try {
			ControlService service = new ControlService();
			List data = service.getListInverter(obj);
//			List dataListNew = new ArrayList();
//			if(data.size() > 0) {
//				try {
//					//Open TCP Connection
//				    S7Connector connector = S7ConnectorFactory
//				            .buildTCPConnector()
//				            .withHost("192.168.1.101")
//				            .withRack(0) //optional
//				            .withSlot(1) //optional
//				            .build();
//				    // read AC power 
//				    byte[] bs = connector.read(DaveArea.DB, 3, 12, 0);
//				    for (int i = 0; i < bs.length; i++) {
//					    System.out.println(bs[i]);
//					}
//				    connector.close();
//				    for(int j = 0; j < data.size(); j++) {
//				    	DeviceEntity item = (DeviceEntity)data.get(j);
//				    	Integer power = Byte.toUnsignedInt(bs[2*j]);
////				    	item.setCon_power(power);
//				    	dataListNew.add(item);
//				    }
//
//				    
//				    
//				    
//				    
////				    byte[] test = new byte[] {(byte) 0, (byte) 11, (byte) 0, (byte) 12,(byte) 0, (byte) 13,(byte) 0, (byte) 14,(byte) 0, (byte) 15,(byte) 0, (byte) 16};
////				    connector.write(DaveArea.DB, 3, 12, test);
//					
////					return this.jsonResult(true, Constants.GET_SUCCESS_MSG, dataListNew, data.size());
//				} catch (Exception e) {
//					log.error(e);
//					return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
//				}
//			} else {
//				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
//			}
			
			
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
//	
//	/**
//	 * @description Get device detail by id 
//	 * @author long.pham
//	 * @since 2021-03-16
//	 * @param id_site
//	 * @return data (status, message, object, total_row
//	 */
//
//	@PostMapping("/device-detail")
//	public Object getDeviceDetail(@RequestBody DeviceEntity obj) {
//		try {
//			
//			SitesDevicesService service = new SitesDevicesService();
//			DeviceEntity getDetail = service.getDeviceDetail(obj);
//			
//			
//			if (getDetail.getId() > 0) {
//				
//				// converting date format for US
//				Date date = new Date();
//		        SimpleDateFormat sdfAmerica = new SimpleDateFormat("MM/dd/yyyy, HH:mm:ss a");
//		        TimeZone tzInAmerica = TimeZone.getTimeZone(getDetail.getTimezone());
//		        sdfAmerica.setTimeZone(tzInAmerica);
//				
//				getDetail.setLast_attempt(sdfAmerica.format(date) + " " + getDetail.getAbbreviation_std());
//				List listParameters = service.getListParameters(obj);
//				getDetail.setList_parameters(listParameters);
//				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDetail, 1);
//			} else {
//				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
//			}
//		} catch (Exception e) {
//			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
//		}
//	}
	
	
}
