/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;
import java.util.ArrayList;
import java.util.List;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ViewReportEntity;

public class LevitonReportsService extends DB {
	
	
	
	/**
	 * @description get data leviton report 
	 * @author long.pham
	 * @since 2024-06-16
	 * @param id_site, date_from, data_to
	 */
	
	public Object getDataReport(ViewReportEntity obj) {
		ViewReportEntity dataObj = new ViewReportEntity();
		try {
			dataObj = (ViewReportEntity) queryForObject("Reports.getDetailReport", obj);
			if (dataObj == null) {
				return null;
			}
			
			List data = new ArrayList();
			List dataListDeviceMeter = queryForList("Reports.getListDeviceTypeMeter", obj);
			if(dataListDeviceMeter.size() > 0 ) {
				obj.setGroupDevices(dataListDeviceMeter);
				data = queryForList("LevitonReports.getDataLevitonReport", obj);
			}
			
			DeviceEntity virtualObj = (DeviceEntity) queryForObject("LevitonReports.getVirtualDevice", obj);
			if(virtualObj.getId() > 0) {
				List listFields = queryForList("LevitonReports.getListFieldVirtualLeviton", virtualObj);
				if(listFields.size() > 0) {
					obj.setDataFields(listFields);
					List dataVirtualMeter = queryForList("LevitonReports.getDataVirtualMeter", obj);
 					if(dataVirtualMeter.size() > 0) {
 						data.addAll(dataVirtualMeter);
					}
				}
			}
			
			dataObj.setDataReports(data);
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}
	
}
