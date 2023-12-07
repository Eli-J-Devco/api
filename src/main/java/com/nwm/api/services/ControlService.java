/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.s7connector.api.DaveArea;
import com.github.s7connector.api.S7Connector;
import com.github.s7connector.api.factory.S7ConnectorFactory;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.SiteEntity;


public class ControlService extends DB {

	
	 
	/**
	 * @description read node meter PLC S7-1200
	 * @author long.pham
	 * @since 2023-03-22
	 * @param {}
	 * @return Object
	 */
	
	
	public Object readNodeMeterFromPLCS71200(SiteEntity obj) {
		SiteEntity dataObj = new SiteEntity();
		try {
			dataObj = (SiteEntity) queryForObject("Control.getSiteDetail", obj);
			
			if (dataObj == null)
				return new SiteEntity();
			
			S7Connector connector = S7ConnectorFactory
		            .buildTCPConnector()
		            .withHost("192.168.1.101")
		            .withRack(0) //optional
		            .withSlot(1) //optional
		            .build();
			
			// read AC power 
			List arrAcPower = new ArrayList();
		    byte[] bsAcPower = connector.read(DaveArea.DB, 3, 2, 48);
		    if(bsAcPower.length == 2) {
		    	String byteStart = "00000000"+ Integer.toBinaryString(bsAcPower[0] & 0xFF );
		    	String byteEnd = Integer.toBinaryString(bsAcPower[1]);
		    	
		    	if(byteEnd.length() < 8) { byteEnd = "00000000"+ byteEnd; }
		    	
		    	byteEnd = byteEnd.substring(byteEnd.length() - 8);
	    		int decimal = Integer.parseInt( (byteStart + byteEnd), 2); 
	    		
	    		dataObj.setAc_power(decimal);
		    }
		    
		    connector.close();
			
					    
			
		} catch (Exception ex) {
			return new SiteEntity();
		}
		return dataObj;

	}
	
	/**
	 * @description write PLC S7-1200
	 * @author long.pham
	 * @since 2023-03-22
	 * @param {}
	 * @return Object
	 */

	public boolean writePLCS71200(SiteEntity obj) {
		try {
			List devices = obj.getDevices();
//			byte[] arrBytes = new byte[devices.size() * 2];
			byte[] arrBytes = new byte[24];

			if (devices.size() > 0) {
				int step = 0;
				for (int i = 0; i < devices.size(); i++) {
					@SuppressWarnings("unchecked")
					Map<String, Object> item = (Map<String, Object>) devices.get(i);
					int setpoint = (int) item.get("con_setpoint");
					String binary = Integer.toBinaryString(setpoint);
					// add string 0 to binary;
					String mapByte = "";
					if (binary.length() < 16) {
						for (int j = binary.length(); j < 16; j++) {
							mapByte = mapByte + "0";
						}
					}

					binary = mapByte + binary;
					String byteStart = binary.substring(0, 8);
					String byteEnd = binary.substring(8, 16);

					if(i < 12) {
						arrBytes[step] = (byte) Integer.parseInt(byteStart, 2);
						arrBytes[step + 1] = (byte) Integer.parseInt(byteEnd, 2);
					}
					step = step + 2;
				}
			}


			try {
				// Open TCP Connection
				S7Connector connector = S7ConnectorFactory.buildTCPConnector().withHost("192.168.1.101").withRack(0) // optional
						.withSlot(1) // optional
						.build();

				connector.write(DaveArea.DB, 3, 24, arrBytes);
				connector.close();
				return true;
			} catch (Exception e) {
				return false;
			}

		} catch (Exception ex) {
			return false;
		}
	}
	

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
			List arrAcPower = new ArrayList();
		    byte[] bsAc = connector.read(DaveArea.DB, 3, 24, 0);
		    for (int i = 0; i < bsAc.length; i++) {
		    	if(i%2 != 0 && i != 0) {
		    		String byteStart = "00000000"+ Integer.toBinaryString(bsAc[i - 1] & 0xFF );
		    		String byteEnd = Integer.toBinaryString(bsAc[i]);
		    		if(byteEnd.length() < 8) { byteEnd = "00000000"+ byteEnd; }
		    		byteEnd = byteEnd.substring(byteEnd.length() - 8);
		    		int decimal = Integer.parseInt( (byteStart + byteEnd), 2); 
		    		arrAcPower.add(decimal);
		    	}
		    	
			}
		    
		    // read setpoint
		    List arrSetPoint = new ArrayList();
		    byte[] bsSetPoint = connector.read(DaveArea.DB, 3, 24, 24);
		    for (int i = 0; i < bsSetPoint.length; i++) {
		    	if(i%2 != 0 && i != 0) {
		    		String byteStart = "00000000"+ Integer.toBinaryString(bsSetPoint[i - 1] & 0xFF );
		    		String byteEnd = Integer.toBinaryString(bsSetPoint[i]);
		    		if(byteEnd.length() < 8) { byteEnd = "00000000"+ byteEnd; }
		    		byteEnd = byteEnd.substring(byteEnd.length() - 8);
		    		int decimal = Integer.parseInt( (byteStart + byteEnd), 2); 
		    		arrSetPoint.add(decimal);
		    	}
			}
		    
		    connector.close();
		    for(int j = 0; j < dataList.size(); j++) {
		    	DeviceEntity item = (DeviceEntity)dataList.get(j);
		    	Integer power = 0;
		    	Integer setpoint = 0;
		    	if( arrAcPower.size() > 0 && arrAcPower.size() > j){
		    		power = (Integer) arrAcPower.get(j);
		    	}
		    	if(arrSetPoint.size() > 0 && arrSetPoint.size() > j ) {
		    		setpoint = (Integer) arrSetPoint.get(j);
		    	}
			    item.setCon_power(power);
			    item.setCon_setpoint(setpoint);
		    	dataListNew.add(item);
		    }
		    
			return dataListNew;
				
		} catch (Exception ex) {
			return dataList;
		}
		
	}
	
}
