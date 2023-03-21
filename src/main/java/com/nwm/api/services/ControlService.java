/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;

import com.github.s7connector.api.DaveArea;
import com.github.s7connector.api.S7Connector;
import com.github.s7connector.api.factory.S7ConnectorFactory;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;

import lombok.experimental.var;

public class ControlService extends DB {

	
	
	/**
	 * @description get site detail
	 * @author long.pham
	 * @since 2021-03-12
	 * @param id_site
	 * @return Object
	 */

//	public SitesDevicesEntity getDetail(SitesDevicesEntity obj) {
//		SitesDevicesEntity dataObj = new SitesDevicesEntity();
//		try {
//			dataObj = (SitesDevicesEntity) queryForObject("SitesDashboard.getDetail", obj);
//			List listDeviceDisableAlert = new ArrayList();
//			listDeviceDisableAlert = queryForList("SitesDashboard.getListDeviceIsDisableAlert", obj);	
//			dataObj.setDeviceDisableAlerts(listDeviceDisableAlert);
//			
//			if (dataObj == null)
//				return new SitesDevicesEntity();
//		} catch (Exception ex) {
//			return new SitesDevicesEntity();
//		}
//		return dataObj;
//	}
	
	/**
	 * @description get list device inverter by id_site
	 * @author long.pham
	 * @since 2023-02-10
	 * @param id_site
	 */
	

	public List getListInverter(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Control.getListInverter", obj);
			List dataListNew = new ArrayList();
			S7Connector connector = S7ConnectorFactory
		            .buildTCPConnector()
		            .withHost("192.168.1.101")
		            .withRack(0) //optional
		            .withSlot(1) //optional
		            .build();
		    // read AC power 
		    byte[] bs = connector.read(DaveArea.DB, 3, 24, 0);
		    for (int i = 0; i < bs.length; i++) {
			    System.out.println(bs[i]);
			}
		    
		    // read setpoint
		    byte[] bsSetPoint = connector.read(DaveArea.DB, 3, 24, 24);
		    for (int i = 0; i < bsSetPoint.length; i++) {
			    System.out.println("setpoint: "+bsSetPoint[i]);
			}
//		    
		    connector.close();
		    int bsStep = 1;
		    for(int j = 0; j < dataList.size(); j++) {
		    	DeviceEntity item = (DeviceEntity)dataList.get(j);
		    	Integer power = 0;
		    	Integer setpoint = 0;
		    	if( bsStep <= bs.length){
		    		power = Byte.toUnsignedInt(bs[bsStep]);
		    	}
		    	
		    	if( bsStep <= bsSetPoint.length){
		    		setpoint = Byte.toUnsignedInt(bsSetPoint[bsStep]);
		    	}
		    	
		    		
		    		
//		    		System.out.println("aa: "+ power);
			    	item.setCon_power(power);
			    	item.setCon_setpoint(setpoint);
			    	bsStep = bsStep + 2;
//		    	}
		    	
		    	dataListNew.add(item);
		    }
		    
			return dataListNew;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
		
	}
	
	
	/**
	 * @description get device detail by id
	 * @author long.pham
	 * @since 2021-03-16
	 * @param id_site, id_device
	 * @return Object
	 */

//	public DeviceEntity getDeviceDetail(DeviceEntity obj) {
//		DeviceEntity data = new DeviceEntity();
//		try {
//			data = (DeviceEntity) queryForObject("SitesDevices.getDeviceDetail", obj);
//			if(data == null) {
//				return new DeviceEntity();
//			}
//			obj.setDatatablename(data.getDatatablename());
//			
//			
//			Object dataListRowItem = queryForObject("SitesDevices.getModelLastRowItem", obj);
//			if(dataListRowItem != null) {
//				ObjectMapper oMapper = new ObjectMapper();
//				Map<String, Object> map = oMapper.convertValue(dataListRowItem, Map.class);
////				data.setLast_communication(map.get("last_communication").toString());
//			} else {
//				// converting date format for US
//				Date date = new Date();
//		        SimpleDateFormat sdfAmerica = new SimpleDateFormat("MM/dd/yyyy, HH:mm:ss a");
//		        TimeZone tzInAmerica = TimeZone.getTimeZone(data.getTimezone());
//		        sdfAmerica.setTimeZone(tzInAmerica);
//				
//				data.setLast_communication(sdfAmerica.format(date) + " " + data.getAbbreviation_std());
//			}
//			
//			
//		} catch (Exception ex) {
//			return new DeviceEntity();
//		}
//		return data;
//	}
//	
	
	
	/**
	 * @description get list device by id_site
	 * @author long.pham
	 * @since 2021-03-16
	 * @param id_site, id_device
	 */
	

//	public List getListParameters(DeviceEntity obj) {
//		List<Object> dataList, dataListNew = new ArrayList<Object>();
//		try {
//			dataList = queryForList("SitesDevices.getListDeviceParameter", obj);
//			if(dataList.size() > 0) {
//				Object dataListRowItem = queryForObject("SitesDevices.getModelLastRowItem", obj);
//				ObjectMapper oMapper = new ObjectMapper();
//				Map<String, Object> map = oMapper.convertValue(dataListRowItem, Map.class);
//				for(int i =0; i< dataList.size(); i++) {
//					DeviceParameterEntity item = (DeviceParameterEntity)dataList.get(i);
//					if(dataListRowItem != null) {
//						String valueField = map.get(item.getSlug()) != null ? map.get(item.getSlug()).toString() : "";
//						item.setValue(valueField);
//					} else {
//						item.setValue("");
//					}
//					
//					dataListNew.add(item);
//				}
//			} else {
//				return new ArrayList<Object>();
//			}
//				
//		} catch (Exception ex) {
//			return new ArrayList<Object>();
//		}
//		return dataListNew;
//	}
	
}
