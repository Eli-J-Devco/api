/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.entities.WeeklyDateEntity;

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
	
	/**
	 * @description get weekly report 
	 * @author Hung.Bui
	 * @since 2023-07-31
	 * @param id_site, date_from, date_to
	 */
	
	public Object getWeeklyBuiltInReport(ViewReportEntity obj) {
		ViewReportEntity dataObj = new ViewReportEntity();
		try {
			// Create list date
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
			SimpleDateFormat catFormat = new SimpleDateFormat("MM/dd/yyyy");
			
			Date startDate = dateFormat.parse(obj.getStart_date());
			Calendar cal = Calendar.getInstance();
			
			List<WeeklyDateEntity> categories = new ArrayList<WeeklyDateEntity> ();
			int forCount = 7;
			
			for(int t = 0; t < forCount; t++) {
				cal.setTime(startDate);
				WeeklyDateEntity headerDate = new WeeklyDateEntity();
				cal.add(Calendar.DATE, t);
				headerDate.setTime_format(dateFormat.format(cal.getTime()));
				headerDate.setCategories_time(catFormat.format(cal.getTime()));
				headerDate.setActualGeneration(0.0);
				headerDate.setExpectedGeneration(0.0);
				headerDate.setModeledGeneration(0.0);
				headerDate.setPoa(0.0);
				headerDate.setExpectedGenerationIndex(0.0);
				headerDate.setModeledGenerationIndex(0.0);
				categories.add(headerDate);
			}
			
			List data = queryForList("BuiltInReport.getDataWeeklyTrendReport", obj);
			List<WeeklyDateEntity> dataNew = new ArrayList<WeeklyDateEntity>();
			
			if (data.size() > 0 && categories.size() > 0) {
				for (WeeklyDateEntity item : categories) {
					boolean flag = false;
					WeeklyDateEntity mapItem = new WeeklyDateEntity();
					
					for(int v = 0; v < data.size(); v++) {
						Map<String, Object> itemT = (Map<String, Object>) data.get(v);
						String categoriesTime = item.getTime_format();
						String powerTime = itemT.get("time_format").toString();
						
						if (categoriesTime.equals(powerTime)) {
							flag = true;
							mapItem.setTime_format(itemT.get("time_format").toString());
							mapItem.setCategories_time(itemT.get("categories_time").toString());
							mapItem.setSiteName(itemT.get("name").toString());
							mapItem.setActualGeneration(Double.parseDouble(itemT.get("ActualGeneration").toString()));
							mapItem.setExpectedGeneration(Double.parseDouble(itemT.get("ExpectedGeneration").toString()));
							mapItem.setModeledGeneration(Double.parseDouble(itemT.get("ModeledGeneration").toString()));
							mapItem.setPoa(Double.parseDouble(itemT.get("POA").toString()));
							mapItem.setExpectedGenerationIndex(Double.parseDouble(itemT.get("ExpectedGenerationIndex").toString()));
							mapItem.setModeledGenerationIndex(Double.parseDouble(itemT.get("ModeledGenerationIndex").toString()));
							break;
						}
					}
					
					if(flag == false) {
						mapItem.setTime_format(item.getTime_format());
						mapItem.setCategories_time(item.getCategories_time());
						mapItem.setActualGeneration(item.getActualGeneration());
						mapItem.setExpectedGeneration(item.getExpectedGeneration());
						mapItem.setModeledGeneration(item.getModeledGeneration());
						mapItem.setPoa(item.getPoa());
						mapItem.setExpectedGenerationIndex(item.getExpectedGenerationIndex());
						mapItem.setModeledGenerationIndex(item.getModeledGenerationIndex());
					}
					
					dataNew.add(mapItem);
				}
			}
			
			dataObj.setDataReports(dataNew);
			
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}
	
}
