/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.BatchJobTableEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ErrorEntity;
import com.nwm.api.entities.ModelDataloggerEntity;
import com.nwm.api.entities.MonthlyDateEntity;
import com.nwm.api.entities.ReportsEntity;
import com.nwm.api.entities.SiteDataReportEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.entities.WeatherEntity;

public class BatchJobService extends DB {
	
	
	/**
	 * @description get device datalogger
	 * @author long.pham
	 * @since 2022-08-11
	 * @param id_site
	 * @return Object
	 */

	public DeviceEntity getDeviceDatalogger(int id_site) {
		DeviceEntity obj = new DeviceEntity();
		try {
			obj = (DeviceEntity) queryForObject("BatchJob.getDeviceDatalogger", id_site);
			if (obj == null)
				return new DeviceEntity();
		} catch (Exception ex) {
			log.error("BatchJob.getDeviceDatalogger", ex);
			return new DeviceEntity();
		}
		return obj;
	}
	
	
	
	/**
	 * @description get data from datalogger
	 * @author long.pham
	 * @since 2022-08-11
	 * @param id_site
	 * @return Object
	 */

	public BatchJobTableEntity getDataloggerItem(BatchJobTableEntity obj) {
		BatchJobTableEntity rowItem = new BatchJobTableEntity();
		try {
			rowItem = (BatchJobTableEntity) queryForObject("BatchJob.getDataloggerItem", obj);
			if (rowItem == null)
				return new BatchJobTableEntity();
		} catch (Exception ex) {
			log.error("BatchJob.getDataloggerItem", ex);
			return new BatchJobTableEntity();
		}
		return rowItem;
	}

	
	/**
	 * @description get list device
	 * @author long.pham
	 * @since 2021-02-17
	 */
	
	public List getListSiteSentMailAlert(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BatchJob.getListSiteSentMailAlert", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	/**
	 * @description get list alert by site
	 * @author long.pham
	 * @since 2021-07-28
	 */
	
	public List getListAlertOpenBySite(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BatchJob.getListAlertOpenBySite", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	/**
	 * @description get list alert by site
	 * @author long.pham
	 * @since 2021-07-28
	 */
	
	public List getListAlertCloseBySite(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BatchJob.getListAlertCloseBySite", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	/**
	 * @description update open sent alert 
	 * @author long.pham
	 * @since 2022-07-29
	 */
	public boolean updateOpenSentAlert(AlertEntity obj){
		try{
			return update("BatchJob.updateOpenSentAlert", obj)>0;
		}catch (Exception ex) {
			log.error("BatchJob.updateOpenSentAlert", ex);
			return false;
		}
	}
	
	
	/**
	 * @description update close sent alert 
	 * @author long.pham
	 * @since 2022-07-29
	 */
	public boolean updateCloseSentAlert(AlertEntity obj){
		try{
			return update("BatchJob.updateCloseSentAlert", obj)>0;
		}catch (Exception ex) {
			log.error("BatchJob.updateCloseSentAlert", ex);
			return false;
		}
	}
	
	
	/**
	 * @description get list error
	 * @author long.pham
	 * @since 2021-07-22
	 */
	
	public List getListErrorByType(ErrorEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BatchJob.getListErrorByType", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	/**
	 * @description get list all device
	 * @author long.pham
	 * @since 2022-07-22
	 */
	
	public List getListAllDevice(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BatchJob.getListAllDevice", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	/**
	 * @description get list device
	 * @author long.pham
	 * @since 2021-02-17
	 */
	
	public List getListSite(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BatchJob.getListSite", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	/**
	 * @description get list device solar open weather
	 * @author long.pham
	 * @since 2021-02-17
	 */
	
	public List getListDeviceSolarOpenWeather(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BatchJob.getListDeviceSolarOpenWeather", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	

	/**
	 * @description get list device
	 * @author long.pham
	 * @since 2021-02-17
	 */
	
	public List getListDeviceBySite(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BatchJob.getListDeviceBySite", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	/**
	 * @description get list device
	 * @author long.pham
	 * @since 2021-02-17
	 */
	
	public List getListMeterAndInverter(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BatchJob.getListMeterAndInverter", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	/**
	 * @description get list device by site
	 * @author long.pham
	 * @since 2022-08-11
	 */
	
	public List getListMeterAndInverterBySite(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BatchJob.getListMeterAndInverterBySite", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get list device
	 * @author long.pham
	 * @since 2021-02-17
	 */
	
	public List getListSiteCheckNoCom(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BatchJob.getListSiteCheckNoCom", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	/**
	 * @description get data device energy lifetime
	 * @author long.pham
	 * @since 2022-06-20
	 * @param {}
	 */

	public DeviceEntity getDataDeviceUpdateLifetime(DeviceEntity obj) {
		DeviceEntity rowItem = new DeviceEntity();
		try {
			rowItem = (DeviceEntity) queryForObject("BatchJob.getLastValueLifetime", obj);
			if (rowItem == null)
				return new DeviceEntity();
		} catch (Exception ex) {
			log.error("BatchJob.getLastValueLifetime", ex);
			return new DeviceEntity();
		}
		return rowItem;
	}

	
	/**
	 * @description update time last_updated
	 * @author long.pham
	 * @since 2022-02-09
	 * @param id, last_updated
	 */
	public boolean  updateDataDeviceLifetime(DeviceEntity obj) {
		try {
			return update("BatchJob.updateDataDeviceLifetime", obj) > 0;
		} catch (Exception ex) {
			log.error("Device.updateDataDeviceLifetime", ex);
			return false;
		}
	}
	
	
	/** 
	 * @description get data device energy today
	 * @author long.pham
	 * @since 2022-06-20
	 * @param {}
	 */

	public DeviceEntity getDataDeviceUpdateEnergyToday(DeviceEntity obj) {
		DeviceEntity rowItem = new DeviceEntity();
		try {
			rowItem = (DeviceEntity) queryForObject("BatchJob.getLastValueEnergyToday", obj);
			if (rowItem == null)
				return new DeviceEntity();
		} catch (Exception ex) {
			log.error("BatchJob.getLastValueEnergyToday", ex);
			return new DeviceEntity();
		}
		return rowItem;
	}
	
	
	
	/** 
	 * @description get data device energy this month
	 * @author long.pham
	 * @since 2022-06-20
	 * @param {}
	 */

	public DeviceEntity getDataDeviceEnergyThisMonth(DeviceEntity obj) {
		DeviceEntity rowItem = new DeviceEntity();
		try {
			rowItem = (DeviceEntity) queryForObject("BatchJob.getDataDeviceEnergyThisMonth", obj);
			if (rowItem == null)
				return new DeviceEntity();
		} catch (Exception ex) {
			log.error("BatchJob.getDataDeviceEnergyThisMonth", ex);
			return new DeviceEntity();
		}
		return rowItem;
	}
	
	/**
	 * @description update time last_updated
	 * @author long.pham
	 * @since 2022-02-09
	 * @param id, last_updated
	 */
	public boolean  updateDataDeviceEnergyToday(DeviceEntity obj) {
		try {
			return update("BatchJob.updateDataDeviceEnergyToday", obj) > 0;
		} catch (Exception ex) {
			log.error("Device.updateDataDeviceEnergyToday", ex);
			return false;
		}
	}
	
	
	
	/**
	 * @description update time last_updated
	 * @author long.pham
	 * @since 2022-02-09
	 * @param id, last_updated
	 */
	public boolean  updateDataDeviceEnergyThisMonth(DeviceEntity obj) {
		try {
			return update("BatchJob.updateDataDeviceEnergyThisMonth", obj) > 0;
		} catch (Exception ex) {
			log.error("Device.updateDataDeviceEnergyThisMonth", ex);
			return false;
		}
	}
	
	
	/**
	 * @description get last row "data table name" by device
	 * @author long.pham
	 * @since 2021-05-18
	 * @param datatablename
	 */

	public BatchJobTableEntity getRowItemAlert(BatchJobTableEntity obj) {
		BatchJobTableEntity rowItem = new BatchJobTableEntity();
		try {
			rowItem = (BatchJobTableEntity) queryForObject("BatchJob.getRowItemAlert", obj);
			if (rowItem == null)
				return new BatchJobTableEntity();
		} catch (Exception ex) {
			log.error("BatchJob.getRowItemAlert", ex);
			return new BatchJobTableEntity();
		}
		return rowItem;
	}
	
	
	/**
	 * @description delete alert 
	 * @author long.pham
	 * @since 2021-10-12
	 * @param id, id_device, id_error_generate
	 */
	public boolean updateCloseAlert(BatchJobTableEntity obj) {
		try {
			return update("BatchJob.updateCloseAlert", obj) > 0;
		} catch (Exception ex) {
			log.error("BatchJob.updateCloseAlert", ex);
			return false;
		}
	}
	
	
	
	/**
	 * @description update error multi row 
	 * @author long.pham
	 * @since 2022-07-22
	 * @param id, id_device, id_error_generate
	 */
	public boolean UpdateErrorMultiRow(AlertEntity obj) {
		try {
			return update("BatchJob.UpdateErrorMultiRow", obj) > 0;
		} catch (Exception ex) {
			log.error("BatchJob.UpdateErrorMultiRow", ex);
			return false;
		}
	}
	
	
	/**
	 * @description get alert detail
	 * @author long.pham
	 * @since 2022-01-21
	 * @param obj
	 */

	public Object getAlertDetail(AlertEntity obj) {
		Object dataObj = null;
		try {
			dataObj = queryForObject("BatchJob.getAlertDetail", obj);
			if (dataObj == null)
				return new AlertEntity();
		} catch (Exception ex) {
			return new AlertEntity();
		}
		return dataObj;
	}
	
	
	
	/**
	 * @description get list device
	 * @author long.pham
	 * @since 2021-02-17
	 */
	
	public List getListDevice(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BatchJob.getListDevice", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	
	
	
	/**
	 * @description get last row "data table name" by device
	 * @author long.pham
	 * @since 2021-02-17
	 * @param datatablename
	 */

	public BatchJobTableEntity getLastRowItem(BatchJobTableEntity obj) {
		BatchJobTableEntity rowItem = new BatchJobTableEntity();
		try {
			rowItem = (BatchJobTableEntity) queryForObject("BatchJob.getLastRowItem", obj);
			if (rowItem == null)
				return new BatchJobTableEntity();
		} catch (Exception ex) {
			log.error("BatchJob.getLastRowItem", ex);
			return new BatchJobTableEntity();
		}
		return rowItem;
	}
	
	
	
	/**
	 * @description get last row item, reset last value device
	 * @author long.pham
	 * @since 2022-07-25
	 * @param datatablename, id_device, utc time
	 */

	public BatchJobTableEntity getLastRowItemResetLastValue(BatchJobTableEntity obj) {
		BatchJobTableEntity rowItem = new BatchJobTableEntity();
		try {
			rowItem = (BatchJobTableEntity) queryForObject("BatchJob.getLastRowItemResetLastValue", obj);
			if (rowItem == null)
				return new BatchJobTableEntity();
		} catch (Exception ex) {
			log.error("BatchJob.getLastRowItemResetLastValue", ex);
			return new BatchJobTableEntity();
		}
		return rowItem;
	}
	
	
	/**
	 * @description update last value device 
	 * @author long.pham
	 * @since 2022-07-25
	 * @param id, id_device, last_value
	 */
	public boolean updateLastValueDevice (DeviceEntity obj) {
		try {
			return update("BatchJob.updateLastValueDevice", obj) > 0;
		} catch (Exception ex) {
			log.error("BatchJob.updateLastValueDevice", ex);
			return false;
		}
	}
	
	/**
	 * @description get last row "data table name" by device
	 * @author long.pham
	 * @since 2021-02-17
	 * @param datatablename
	 */

	public ErrorEntity getErrorItem(ErrorEntity obj) {
		ErrorEntity rowItem = new ErrorEntity();
		try {
			rowItem = (ErrorEntity) queryForObject("BatchJob.getErrorItem", obj);
			if (rowItem == null)
				return new ErrorEntity();
		} catch (Exception ex) {
			log.error("BatchJob.getErrorItem", ex);
			return new ErrorEntity();
		}
		return rowItem;
	}
	
	
	/**
	 * @description get alert Exists
	 * @author long.pham
	 * @since 2021-02-18
	 * @param error_code, time
	 */
	public boolean checkAlertExist(AlertEntity dataE) {
		try {
			return (int) queryForObject("BatchJob.checkAlertlExist", dataE) > 0;
		}catch (Exception e) {
			
		}
		return true;
	}
	
	/**
	 * @description insert alert
	 * @author long.pham
	 * @since 2021-02-18
	 * @param {}
	 */
	public AlertEntity insertAlert(AlertEntity obj) 
	{
		try
	    {
	       Object insertId = insert("BatchJob.insertAlert", obj);
	       if(insertId != null && insertId instanceof Integer) {
	    	   return obj;
	       }else {
	    	   return null;
	       }
	    }
	    catch(Exception ex)
	    {
	        log.error("Alert.insertAlert", ex);
	        return null;
	    }	
	}
	
	
	
	/**
	 * @description get list device
	 * @author long.pham
	 * @since 2021-05-17
	 */
	
	public List getListAlertCronJob(AlertEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BatchJob.getListAlertCronJob", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	/**
	 * @description get last row "data table name" by device
	 * @author long.pham
	 * @since 2021-05-18
	 * @param datatablename
	 */

	public BatchJobTableEntity getLastRowItemAutoCloseAlert(BatchJobTableEntity obj) {
		BatchJobTableEntity rowItem = new BatchJobTableEntity();
		try {
			rowItem = (BatchJobTableEntity) queryForObject("BatchJob.getLastRowItemAutoCloseAlert", obj);
			if (rowItem == null)
				return new BatchJobTableEntity();
		} catch (Exception ex) {
			log.error("BatchJob.getLastRowItemAutoCloseAlert", ex);
			return new BatchJobTableEntity();
		}
		return rowItem;
	}
	
	
	
	
	/**
	 * @description update auto close alert 
	 * @author long.pham
	 * @since 2021-05-18
	 */
	public boolean updateWeather(WeatherEntity obj){
		try{
			return update("BatchJob.updateWeather", obj)>0;
		}catch (Exception ex) {
			log.error("BatchJob.updateWeather", ex);
			return false;
		}
	}
	
	
	/**
	 * @description get last row "data table name" by device
	 * @author long.pham
	 * @since 2021-05-18
	 * @param datatablename
	 */

	public BatchJobTableEntity getLastRowItemCheckNoProduction(BatchJobTableEntity obj) {
		BatchJobTableEntity rowItem = new BatchJobTableEntity();
		try {
			rowItem = (BatchJobTableEntity) queryForObject("BatchJob.getLastRowItemCheckNoProduction", obj);
			if (rowItem == null)
				return new BatchJobTableEntity();
		} catch (Exception ex) {
			log.error("BatchJob.getLastRowItemCheckNoProduction", ex);
			return new BatchJobTableEntity();
		}
		return rowItem;
	}
	
	
	
	
	/**
	 * @description get last row "data table name" by device
	 * @author long.pham
	 * @since 2021-05-18
	 * @param datatablename
	 */

	public BatchJobTableEntity getLastRowItemCheckNoCommunication(BatchJobTableEntity obj) {
		BatchJobTableEntity rowItem = new BatchJobTableEntity();
		try {
			rowItem = (BatchJobTableEntity) queryForObject("BatchJob.getLastRowItemCheckNoCommunication", obj);
			if (rowItem == null)
				return new BatchJobTableEntity();
		} catch (Exception ex) {
			log.error("BatchJob.getLastRowItemCheckNoCommunication", ex);
			return new BatchJobTableEntity();
		}
		return rowItem;
	}
	
	
	
	
	
	/**
	 * @description delete alert 
	 * @author long.pham
	 * @since 2021-10-12
	 * @param id, id_device, id_error_generate
	 */
	public boolean deleteAlert(BatchJobTableEntity obj) {
		try {
			return update("BatchJob.deleteAlert", obj) > 0;
		} catch (Exception ex) {
			log.error("BatchJob.deleteAlert", ex);
			return false;
		}
	}
	
	
	
	/**
	 * @description insert alert
	 * @author long.pham
	 * @since 2021-02-18
	 * @param {}
	 */
	public SiteEntity insertDataGenerateReport(SiteEntity obj) 
	{
		try
	    {
			List dataListInverter = queryForList("BatchJob.getListDeviceInverterBySite", obj);
			List dataListMeter = queryForList("BatchJob.getListDeviceMeterBySite", obj);
			List dataListWeather = queryForList("BatchJob.getListDeviceWeather", obj);
			
			Date now = new Date();
			TimeZone.setDefault(TimeZone.getTimeZone(obj.getTime_zone_value()));
			SimpleDateFormat format = new SimpleDateFormat("yyyy", Locale.US);
			String year = format.format(now);
			// Case 1: inverter, meter, weather
			if(dataListInverter.size() > 0 && dataListMeter.size() > 0 && dataListWeather.size() > 0) {
				// Create list date 
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
				Date startDate = dateFormat.parse("2022-01-01 00:00:00");
				Calendar cal = Calendar.getInstance();
				
				
				for(int t = 0; t < 365; t++) {
					cal.setTime(startDate);
					cal.add(Calendar.DATE, t);
					System.out.println(dateFormat.format(cal.getTime()));
					// inverter 
					for (int i = 0; i < dataListInverter.size(); i++) {
						SiteDataReportEntity dataReportInverter = new SiteDataReportEntity();
						DeviceEntity deviceItem = (DeviceEntity) dataListInverter.get(i);
						System.out.println("deviceItem1: " + deviceItem);
						
						deviceItem.setYear( Integer.parseInt(year) );
						deviceItem.setGroupMeter(dataListMeter);
						deviceItem.setGroupInverter(dataListInverter);
						deviceItem.setGroupWeather(dataListWeather);
						deviceItem.setStart_date(dateFormat.format(cal.getTime()) + " 08:00:00");
						deviceItem.setEnd_date(dateFormat.format(cal.getTime())+ " 17:59:59");
						
						
						dataReportInverter = (SiteDataReportEntity) queryForObject("BatchJob.getSiteDataReportIIMW", deviceItem);
						if (dataReportInverter != null && dataReportInverter.getId_device() > 0) {
							insert("BatchJob.insertSiteDataReport", dataReportInverter);
						}
					}
					
					// meter 
					for (int j = 0; j < dataListMeter.size(); j++) {
						SiteDataReportEntity dataReportMeter = new SiteDataReportEntity();
						DeviceEntity deviceItemMeter = (DeviceEntity) dataListMeter.get(j);
						System.out.println("deviceItemMeter1: " + deviceItemMeter);
						deviceItemMeter.setYear( Integer.parseInt(year) );
						deviceItemMeter.setGroupMeter(dataListMeter);
						deviceItemMeter.setGroupInverter(dataListInverter);
						deviceItemMeter.setGroupWeather(dataListWeather);
						deviceItemMeter.setStart_date(dateFormat.format(cal.getTime()) + " 08:00:00");
						deviceItemMeter.setEnd_date(dateFormat.format(cal.getTime())+ " 17:59:59");
						
						dataReportMeter = (SiteDataReportEntity) queryForObject("BatchJob.getSiteDataReportMIMW", deviceItemMeter);
						if (dataReportMeter != null && dataReportMeter.getId_device() > 0) {
							insert("BatchJob.insertSiteDataReport", dataReportMeter);
						}
					}
					
					// weather station
					for (int j = 0; j < dataListWeather.size(); j++) {
						SiteDataReportEntity dataReportMeter = new SiteDataReportEntity();
						DeviceEntity deviceItemWeather = (DeviceEntity) dataListWeather.get(j);
						System.out.println("deviceItemMeter3: " + deviceItemWeather);
						deviceItemWeather.setYear( Integer.parseInt(year) );
						deviceItemWeather.setStart_date(dateFormat.format(cal.getTime()) + " 08:00:00");
						deviceItemWeather.setEnd_date(dateFormat.format(cal.getTime())+ " 17:59:59");
						
						dataReportMeter = (SiteDataReportEntity) queryForObject("BatchJob.getSiteDataReportWeather", deviceItemWeather);
						if (dataReportMeter != null && dataReportMeter.getId_device() > 0) {
							insert("BatchJob.insertSiteDataReport", dataReportMeter);
						}
					}
					
				}
			}
			
			// Case 2: inverter, meter
			if(dataListInverter.size() > 0 && dataListMeter.size() > 0 && dataListWeather.size() <= 0) {
				// Create list date 
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
				Date startDate = dateFormat.parse("2022-01-01 00:00:00");
				Calendar cal = Calendar.getInstance();
				
				for(int t = 0; t < 365; t++) {
					cal.setTime(startDate);
					cal.add(Calendar.DATE, t);
					System.out.println(dateFormat.format(cal.getTime()));

					// inverter 
					for (int k = 0; k < dataListInverter.size(); k++) {
						SiteDataReportEntity dataReportInverter = new SiteDataReportEntity();
						
						DeviceEntity deviceItem = (DeviceEntity) dataListInverter.get(k);
						
						System.out.println("deviceItemMeter4: " + deviceItem);
						
						deviceItem.setYear( Integer.parseInt(year) );
						deviceItem.setGroupMeter(dataListMeter);
						deviceItem.setGroupInverter(dataListInverter);
						deviceItem.setStart_date(dateFormat.format(cal.getTime()) + " 08:00:00");
						deviceItem.setEnd_date(dateFormat.format(cal.getTime())+ " 17:59:59");
						
						dataReportInverter = (SiteDataReportEntity) queryForObject("BatchJob.getSiteDataReportIIM", deviceItem);
						if (dataReportInverter != null && dataReportInverter.getId_device() > 0) {
							insert("BatchJob.insertSiteDataReport", dataReportInverter);
						}
					}
					
					// meter 
					for (int j = 0; j < dataListMeter.size(); j++) {
						SiteDataReportEntity dataReportMeter = new SiteDataReportEntity();
						DeviceEntity deviceItemMeter = (DeviceEntity) dataListMeter.get(j);
						System.out.println("deviceItemMeter5: " + deviceItemMeter);
						
						deviceItemMeter.setYear( Integer.parseInt(year) );
						deviceItemMeter.setGroupMeter(dataListMeter);
						deviceItemMeter.setStart_date(dateFormat.format(cal.getTime()) + " 08:00:00");
						deviceItemMeter.setEnd_date(dateFormat.format(cal.getTime())+ " 17:59:59");

						dataReportMeter = (SiteDataReportEntity) queryForObject("BatchJob.getSiteDataReportMIM", deviceItemMeter);
						if (dataReportMeter != null && dataReportMeter.getId_device() > 0) {
							insert("BatchJob.insertSiteDataReport", dataReportMeter);
						}
					}
				}
			}
			
			// Case 3: inverter, weather
			if(dataListInverter.size() > 0  && dataListMeter.size() <= 0 && dataListWeather.size() > 0 ) {
				// Create list date 
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
				Date startDate = dateFormat.parse("2022-01-01 00:00:00");
				Calendar cal = Calendar.getInstance();
				
				for(int t = 0; t < 365; t++) {
					cal.setTime(startDate);
					cal.add(Calendar.DATE, t);
					System.out.println(dateFormat.format(cal.getTime()));
					//inverter
					
					for (int i = 0; i < dataListInverter.size(); i++) {
						SiteDataReportEntity dataReportInverter = new SiteDataReportEntity();
						DeviceEntity deviceItem = (DeviceEntity) dataListInverter.get(i);
						System.out.println("deviceItemMeter6: " + deviceItem);
						
						deviceItem.setYear( Integer.parseInt(year) );
						deviceItem.setGroupMeter(dataListMeter);
						deviceItem.setGroupInverter(dataListInverter);
						deviceItem.setGroupWeather(dataListWeather);
						deviceItem.setStart_date(dateFormat.format(cal.getTime()) + " 08:00:00");
						deviceItem.setEnd_date(dateFormat.format(cal.getTime())+ " 17:59:59");
						
						
						dataReportInverter = (SiteDataReportEntity) queryForObject("BatchJob.getSiteDataReportIIMW", deviceItem);
						if (dataReportInverter != null && dataReportInverter.getId_device() > 0) {
							insert("BatchJob.insertSiteDataReport", dataReportInverter);
						}
					}
					// weather station
					for (int j = 0; j < dataListWeather.size(); j++) {
						SiteDataReportEntity dataReportMeter = new SiteDataReportEntity();
						DeviceEntity deviceItemWeather = (DeviceEntity) dataListWeather.get(j);
						System.out.println("deviceItemMeter7: " + deviceItemWeather);
						deviceItemWeather.setYear( Integer.parseInt(year) );
						deviceItemWeather.setStart_date(dateFormat.format(cal.getTime()) + " 08:00:00");
						deviceItemWeather.setEnd_date(dateFormat.format(cal.getTime())+ " 17:59:59");
						
						dataReportMeter = (SiteDataReportEntity) queryForObject("BatchJob.getSiteDataReportWeather", deviceItemWeather);
						if (dataReportMeter != null && dataReportMeter.getId_device() > 0) {
							insert("BatchJob.insertSiteDataReport", dataReportMeter);
						}
					}
				}
			}
			
			// Case 4: meter, weather
			if(dataListInverter.size() <= 0 && dataListMeter.size() > 0 && dataListWeather.size() > 0 ) {
				// Create list date 
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
				Date startDate = dateFormat.parse("2022-01-01 00:00:00");
				Calendar cal = Calendar.getInstance();
				
				for(int t = 0; t < 365; t++) {
					cal.setTime(startDate);
					cal.add(Calendar.DATE, t);
					System.out.println(dateFormat.format(cal.getTime()));
					// meter 
					for (int j = 0; j < dataListMeter.size(); j++) {
						SiteDataReportEntity dataReportMeter = new SiteDataReportEntity();
						DeviceEntity deviceItemMeter = (DeviceEntity) dataListMeter.get(j);
						System.out.println("deviceItemMeter8: " + deviceItemMeter);
						
						deviceItemMeter.setYear( Integer.parseInt(year) );
						deviceItemMeter.setGroupMeter(dataListMeter);
						deviceItemMeter.setStart_date(dateFormat.format(cal.getTime()) + " 08:00:00");
						deviceItemMeter.setEnd_date(dateFormat.format(cal.getTime())+ " 17:59:59");

						dataReportMeter = (SiteDataReportEntity) queryForObject("BatchJob.getSiteDataReportMM", deviceItemMeter);
						if (dataReportMeter != null && dataReportMeter.getId_device() > 0) {
							insert("BatchJob.insertSiteDataReport", dataReportMeter);
						}
					}
					
					// weather station
					for (int j = 0; j < dataListWeather.size(); j++) {
						SiteDataReportEntity dataReportMeter = new SiteDataReportEntity();
						DeviceEntity deviceItemWeather = (DeviceEntity) dataListWeather.get(j);
						System.out.println("deviceItemMeter9: " + deviceItemWeather);
						deviceItemWeather.setYear( Integer.parseInt(year) );
						deviceItemWeather.setStart_date(dateFormat.format(cal.getTime()) + " 08:00:00");
						deviceItemWeather.setEnd_date(dateFormat.format(cal.getTime())+ " 17:59:59");
						
						dataReportMeter = (SiteDataReportEntity) queryForObject("BatchJob.getSiteDataReportWeather", deviceItemWeather);
						if (dataReportMeter != null && dataReportMeter.getId_device() > 0) {
							insert("BatchJob.insertSiteDataReport", dataReportMeter);
						}
					}
				}
				
				
				
				
			}
			// Case 5: meter
			if(dataListInverter.size() <= 0 && dataListMeter.size() > 0 && dataListWeather.size() <= 0 ) {
				// Create list date 
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
				Date startDate = dateFormat.parse("2022-01-01 00:00:00");
				Calendar cal = Calendar.getInstance();
				
				for(int t = 0; t < 365; t++) {
					cal.setTime(startDate);
					cal.add(Calendar.DATE, t);
					System.out.println(dateFormat.format(cal.getTime()));
					// meter 
					for (int j = 0; j < dataListMeter.size(); j++) {
						SiteDataReportEntity dataReportMeter = new SiteDataReportEntity();
						DeviceEntity deviceItemMeter = (DeviceEntity) dataListMeter.get(j);
						System.out.println("deviceItemMeter10: " + deviceItemMeter);
						
						deviceItemMeter.setYear( Integer.parseInt(year) );
						deviceItemMeter.setGroupMeter(dataListMeter);
						deviceItemMeter.setStart_date(dateFormat.format(cal.getTime()) + " 08:00:00");
						deviceItemMeter.setEnd_date(dateFormat.format(cal.getTime())+ " 17:59:59");

						dataReportMeter = (SiteDataReportEntity) queryForObject("BatchJob.getSiteDataReportMM", deviceItemMeter);
						if (dataReportMeter != null && dataReportMeter.getId_device() > 0) {
							insert("BatchJob.insertSiteDataReport", dataReportMeter);
						}
					}
				}
			}
			// Case 6: inverter
			if(dataListInverter.size() > 0 && dataListMeter.size() <= 0 && dataListWeather.size() <= 0 ) {
				// Create list date 
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
				Date startDate = dateFormat.parse("2022-01-01 00:00:00");
				Calendar cal = Calendar.getInstance();
				
				for(int t = 0; t < 365; t++) {
					cal.setTime(startDate);
					cal.add(Calendar.DATE, t);
					System.out.println(dateFormat.format(cal.getTime()));

					// inverter 
					for (int k = 0; k < dataListInverter.size(); k++) {
						SiteDataReportEntity dataReportInverter = new SiteDataReportEntity();
						
						DeviceEntity deviceItem = (DeviceEntity) dataListInverter.get(k);
						
						System.out.println("deviceItemMeter11: " + deviceItem);
						
						deviceItem.setYear( Integer.parseInt(year) );
						deviceItem.setGroupInverter(dataListInverter);
						deviceItem.setGroupWeather(dataListWeather);
						
						deviceItem.setStart_date(dateFormat.format(cal.getTime()) + " 08:00:00");
						deviceItem.setEnd_date(dateFormat.format(cal.getTime())+ " 17:59:59");
						
						dataReportInverter = (SiteDataReportEntity) queryForObject("BatchJob.getSiteDataReportIIW", deviceItem);
						if (dataReportInverter != null && dataReportInverter.getId_device() > 0) {
							insert("BatchJob.insertSiteDataReport", dataReportInverter);
						}
					}
					
				}
			}
			
			return null;
	    }
	    catch(Exception ex)
	    {
	        log.error("insertDataGenerateReport", ex);
	        return null;
	    }	
	}
	
	
	
	
	/**
	 * @description insert alert
	 * @author long.pham
	 * @since 2021-02-18
	 * @param {}
	 */
	public SiteEntity updateDataGeneratePerformanceRatio(SiteEntity obj) 
	{
		try
	    {
			List dataListWeather = queryForList("BatchJob.getListDeviceWeather", obj);
			Double sytemSite = obj.getDc_capacity();
			Double PVModuleTemperature = -0.0047;
			Double globaSolarIrradiance = 1000.0;
			Double STCTemperature = 25.0;
			Double actualPower = 0.0;
			Double POA = 0.0;
			Double temperature = 0.0;
			Double PerformanceRatioYesterday = 0.0;
			
			if(dataListWeather.size() > 0 && sytemSite > 0) {
				List dataListInverter = queryForList("BatchJob.getListDeviceInverterBySite", obj);
				List dataListMeter = queryForList("BatchJob.getListDeviceMeterBySite", obj);
				// get POA
				obj.setWeatherStation(dataListWeather);
				SiteEntity dataWeatherStatiton = new SiteEntity();
				dataWeatherStatiton =  (SiteEntity) queryForObject("BatchJob.getDataIrradianceYesterday", obj);
				if(dataWeatherStatiton != null) {
					POA = dataWeatherStatiton.getNvm_irradiance();
					temperature = dataWeatherStatiton.getNvm_temperature();
				}
				
				
				// get actual power
				if(dataListMeter.size() > 0) {
					SiteEntity dataMeter = new SiteEntity();
					obj.setGroupMeter(dataListMeter);
					dataMeter = (SiteEntity) queryForObject("BatchJob.getDataPowerMeterYesterday", obj);
					if(dataMeter != null) {
						actualPower = dataMeter.getActualPower();	
					}
					
				} else if(dataListInverter.size() > 0) {
					obj.setGroupMeter(dataListInverter);
					SiteEntity dataInverter = new SiteEntity();
					dataInverter = (SiteEntity) queryForObject("BatchJob.getDataPowerInverterYesterday", obj);
					if(dataInverter != null) {
						actualPower = dataInverter.getAc_power();
					}
				}
				
				System.out.println("---------------------------------------------------------");
				System.out.println("actualPower: " + actualPower);
				System.out.println("POA: " + POA);
				System.out.println("sytemSite: " + sytemSite);
				System.out.println("PVModuleTemperature: " + PVModuleTemperature);
				System.out.println("temperature: " + temperature);
				System.out.println("STCTemperature: " + STCTemperature);
				
				if(POA != 0) {
					PerformanceRatioYesterday = (actualPower / ((POA / globaSolarIrradiance) * sytemSite * (1 + PVModuleTemperature * (temperature - STCTemperature)))) * 100;
				}
				
				System.out.println("PerformanceRatioYesterday: " + PerformanceRatioYesterday);
			}
			
			
			// Update Performance Ratio Yesterday
			DecimalFormat df = new DecimalFormat("##.0");
			obj.setPerformanceRatioYesterday(Double.parseDouble(df.format(PerformanceRatioYesterday)));
			update("BatchJob.updatPerformanceRatioYesterday", obj);
			return null;
	    }
	    catch(Exception ex)
	    {
	        log.error("insertDataGenerateReport", ex);
	        return null;
	    }	
	}
	
	
	/**
	 * @description get list device
	 * @author long.pham
	 * @since 2021-02-17
	 */
	
	public List getListReportsByCadence(ViewReportEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BatchJob.getListReportsByCadence", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
}
