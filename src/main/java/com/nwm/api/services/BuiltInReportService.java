/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;
import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ViewReportEntity;

public class BuiltInReportService extends DB {
	
	/**
	 * @description get list site in report 
	 * @author long.pham
	 * @since 2023-08-25
	 */
	
	public List getListSiteInReport(ViewReportEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BuiltInReport.getListSiteInReport", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get annually production trend report 
	 * @author long.pham
	 * @since 2022-08-23
	 * @param id_site, date_from, data_to
	 */
	
	public Object getAnnuallyBuitInReport(ViewReportEntity obj) {
		ViewReportEntity dataObj = new ViewReportEntity();
		try {
			
			List data = queryForList("BuiltInReport.getDataAnnualTrendReport", obj);
			
			if (data.size() > 0) {
				dataObj.setDataReports(data);
			}
			
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}
	
	
	/**
	 * @description get monthly  report 
	 * @author long.pham
	 * @since 2022-08-23
	 * @param id_site, date_from, data_to
	 */
	
	public Object getMonthlyTrendBuitInReport(ViewReportEntity obj) {
		ViewReportEntity dataObj = new ViewReportEntity();
		try {
			List dataListDeviceMeter = queryForList("BuiltInReport.getListDeviceTypeMeter", obj);
			
			if(dataListDeviceMeter.size() > 0 ) {
				obj.setGroupDevices(dataListDeviceMeter);
				List data = queryForList("BuiltInReport.getMonthlyTrendBuitInReport", obj);
				if (data.size() > 0) {
					dataObj.setDataReports(data);
				}
			} else {
				List dataListInverter = queryForList("BuiltInReport.getListDeviceTypeInverter", obj);
				if(dataListInverter.size() > 0) {
					obj.setGroupDevices(dataListInverter);
					List dataPower = queryForList("BuiltInReport.getMonthlyTrendBuitInReport", obj);
					if (dataPower.size() > 0) {
						dataObj.setDataReports(dataPower);
					}
				} 
			}
			
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}
}
