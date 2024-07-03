/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.BatchJobTableEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.EmployeeEntity;
import com.nwm.api.entities.ErrorEntity;
import com.nwm.api.entities.SiteEntity;

public class CronJobAlertService extends DB {
	
	
	/**
	 * @description get device datalogger
	 * @author long.pham
	 * @since 2023-07-20
	 * @param id_site
	 * @return Object
	 */

	public DeviceEntity getDeviceDatalogger(int id_site) {
		DeviceEntity obj = new DeviceEntity();
		try {
			obj = (DeviceEntity) queryForObject("CronJobAlert.getDeviceDatalogger", id_site);
			if (obj == null)
				return new DeviceEntity();
		} catch (Exception ex) {
			log.error("CronJobAlert.getDeviceDatalogger", ex);
			return new DeviceEntity();
		}
		return obj;
	}
	
	
	
	/**
	 * @description get data from datalogger
	 * @author long.pham
	 * @since 2023-07-20
	 * @param id_site
	 * @return Object
	 */

	public BatchJobTableEntity getDataloggerItem(BatchJobTableEntity obj) {
		BatchJobTableEntity rowItem = new BatchJobTableEntity();
		try {
			rowItem = (BatchJobTableEntity) queryForObject("CronJobAlert.getDataloggerItem", obj);
			if (rowItem == null)
				return new BatchJobTableEntity();
		} catch (Exception ex) {
			log.error("CronJobAlert.getDataloggerItem", ex);
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
			dataList = queryForList("CronJobAlert.getListSiteSentMailAlert", obj);
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
			List errorLevel = queryForList("CronJobAlert.getListErrorLevel", obj);
			
			obj.setErrorLevel(errorLevel);
			dataList = queryForList("CronJobAlert.getListAlertOpenBySite", obj);
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
			dataList = queryForList("CronJobAlert.getListAlertCloseBySite", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get list alert by site to clients
	 * @author long.pham
	 * @since 2021-07-28
	 */
	
	public List getListAlertOpenBySiteToClients(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			List errorLevel = queryForList("CronJobAlert.getListErrorLevel", obj);
			
			obj.setErrorLevel(errorLevel);
			dataList = queryForList("CronJobAlert.getListAlertOpenBySiteToClients", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	/**
	 * @description get list alert by site to clients
	 * @author long.pham
	 * @since 2021-07-28
	 */
	
	public List getListAlertCloseBySiteToClients(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("CronJobAlert.getListAlertCloseBySiteToClients", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
//	
	/**
	 * @description update open sent alert 
	 * @author long.pham
	 * @since 2022-07-29
	 */
	public boolean updateOpenSentAlert(AlertEntity obj){
		try{
			return update("CronJobAlert.updateOpenSentAlert", obj)>0;
		}catch (Exception ex) {
			log.error("CronJobAlert.updateOpenSentAlert", ex);
			return false;
		}
	}
	
	/**
	 * @description update open sent alert  to clients
	 * @author long.pham
	 * @since 2022-07-29
	 */
	public boolean updateOpenSentAlertToClients(AlertEntity obj){
		try{
			return update("CronJobAlert.updateOpenSentAlertToClients", obj)>0;
		}catch (Exception ex) {
			log.error("CronJobAlert.updateOpenSentAlertToClients", ex);
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
			return update("CronJobAlert.updateCloseSentAlert", obj)>0;
		}catch (Exception ex) {
			log.error("CronJobAlert.updateCloseSentAlert", ex);
			return false;
		}
	}
	
	/**
	 * @description update close sent alert  to clients
	 * @author long.pham
	 * @since 2022-07-29
	 */
	public boolean updateCloseSentAlertToClients(AlertEntity obj){
		try{
			return update("CronJobAlert.updateCloseSentAlertToClients", obj)>0;
		}catch (Exception ex) {
			log.error("CronJobAlert.updateCloseSentAlertToClients", ex);
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
			dataList = queryForList("CronJobAlert.getListErrorByType", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
//	
	/**
	 * @description get list all device
	 * @author long.pham
	 * @since 2022-07-22
	 */
	
	public List getListAllDevice(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("CronJobAlert.getListAllDevice", obj);
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
	 * @since 2023-07-20
	 */
	
	public List getListMeterAndInverterBySite(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("CronJobAlert.getListMeterAndInverterBySite", obj);
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
	 * @since 2023-07-20
	 */
	
	public List getListWeatherStation(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("CronJobAlert.getListWeatherStation", obj);
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
	 * @since 2023-07-20
	 */
	
	public List getListDeviceCheckNoCom(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("CronJobAlert.getListDeviceCheckNoCom", obj);
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
	 * @since 2023-07-20
	 */
	
	public List getListSiteCheckNoCom(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("CronJobAlert.getListSiteCheckNoCom", obj);
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
	 * @since 2023-07-20
	 * @param datatablename
	 */

	public BatchJobTableEntity getRowItemAlert(BatchJobTableEntity obj) {
		BatchJobTableEntity rowItem = new BatchJobTableEntity();
		try {
			rowItem = (BatchJobTableEntity) queryForObject("CronJobAlert.getRowItemAlert", obj);
			if (rowItem == null)
				return new BatchJobTableEntity();
		} catch (Exception ex) {
			log.error("CronJobAlert.getRowItemAlert", ex);
			return new BatchJobTableEntity();
		}
		return rowItem;
	}
	
	
	/**
	 * @description delete alert 
	 * @author long.pham
	 * @since 2023-07-20
	 * @param id, id_device, id_error_generate
	 */
	public boolean updateCloseAlert(BatchJobTableEntity obj) {
		try {
			return update("CronJobAlert.updateCloseAlert", obj) > 0;
		} catch (Exception ex) {
			log.error("CronJobAlert.updateCloseAlert", ex);
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
			return update("CronJobAlert.UpdateErrorMultiRow", obj) > 0;
		} catch (Exception ex) {
			log.error("CronJobAlert.UpdateErrorMultiRow", ex);
			return false;
		}
	}
	
//	
	/**
	 * @description get alert detail
	 * @author long.pham
	 * @since 2023-07-20
	 * @param obj
	 */

	public Object getAlertDetail(AlertEntity obj) {
		Object dataObj = null;
		try {
			dataObj = queryForObject("CronJobAlert.getAlertDetail", obj);
			if (dataObj == null)
				return new AlertEntity();
		} catch (Exception ex) {
			return new AlertEntity();
		}
		return dataObj;
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
			rowItem = (BatchJobTableEntity) queryForObject("CronJobAlert.getLastRowItem", obj);
			if (rowItem == null)
				return new BatchJobTableEntity();
		} catch (Exception ex) {
			log.error("CronJobAlert.getLastRowItem", ex);
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
			rowItem = (BatchJobTableEntity) queryForObject("CronJobAlert.getLastRowItemResetLastValue", obj);
			if (rowItem == null)
				return new BatchJobTableEntity();
		} catch (Exception ex) {
			log.error("CronJobAlert.getLastRowItemResetLastValue", ex);
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
			return update("CronJobAlert.updateLastValueDevice", obj) > 0;
		} catch (Exception ex) {
			log.error("CronJobAlert.updateLastValueDevice", ex);
			return false;
		}
	}
	
	/**
	 * @description get alert Exists
	 * @author long.pham
	 * @since 2023-07-20
	 * @param error_code, time
	 */
	public boolean checkAlertExist(AlertEntity dataE) {
		try {
			return (int) queryForObject("CronJobAlert.checkAlertlExist", dataE) > 0;
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
	       Object insertId = insert("CronJobAlert.insertAlert", obj);
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
	 * @description get last row "data table name" by device
	 * @author long.pham
	 * @since 2021-05-18
	 * @param datatablename
	 */

	public BatchJobTableEntity getLastRowItemCheckNoProduction(BatchJobTableEntity obj) {
		BatchJobTableEntity rowItem = new BatchJobTableEntity();
		try {
			rowItem = (BatchJobTableEntity) queryForObject("CronJobAlert.getLastRowItemCheckNoProduction", obj);
			if (rowItem == null)
				return new BatchJobTableEntity();
		} catch (Exception ex) {
			log.error("CronJobAlert.getLastRowItemCheckNoProduction", ex);
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

	public BatchJobTableEntity getLastRowItemNoCommWeather(BatchJobTableEntity obj) {
		BatchJobTableEntity rowItem = new BatchJobTableEntity();
		try {
			rowItem = (BatchJobTableEntity) queryForObject("CronJobAlert.getLastRowItemNoCommWeather", obj);
			if (rowItem == null)
				return new BatchJobTableEntity();
		} catch (Exception ex) {
			log.error("CronJobAlert.getLastRowItemNoCommWeather", ex);
			return new BatchJobTableEntity();
		}
		return rowItem;
	}
	
//	
//	
//	
	
	/**
	 * @description get last row "data table name" by device
	 * @author long.pham
	 * @since 2021-05-18
	 * @param datatablename
	 */

	public BatchJobTableEntity getLastRowItemCheckNoCommunication(BatchJobTableEntity obj) {
		BatchJobTableEntity rowItem = new BatchJobTableEntity();
		try {
			rowItem = (BatchJobTableEntity) queryForObject("CronJobAlert.getLastRowItemCheckNoCommunication", obj);
			if (rowItem == null)
				return new BatchJobTableEntity();
		} catch (Exception ex) {
			log.error("CronJobAlert.getLastRowItemCheckNoCommunication", ex);
			return new BatchJobTableEntity();
		}
		return rowItem;
	}
	
	

	/**
	 * @description get list employees hiding a site
	 * @author duy.phan
	 * @since 2023-06-21
	 * @param id
	 */

	public List getEmployeeHidingSite(SiteEntity obj) {
		try {
			List rs = queryForList("CronJobAlert.getEmployeeHidingSite", obj);
			if (rs == null) {
				return new ArrayList<>();
			}
			return rs;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * @description get list site (id, eer_last_month)
	 * @author duy.phan
	 * @since 2023-10-13
	 */
	
	public List getListSiteEERLastMonth(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("CronJobAlert.getListSiteEERLastMonth", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description update err last month
	 * @author duy.phan
	 * @since 2023-10-13
	 */
	
	public List updateSiteEERLastMonth(SiteEntity obj) {
		try {
			update("CronJobAlert.updateSiteEERLastMonth", obj);			
			return null;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * @description get list site (id, eer_this_month)
	 * @author duy.phan
	 * @since 2023-10-13
	 */
	
	public List getListSiteEERThisMonth(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("CronJobAlert.getListSiteEERThisMonth", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description update err this month
	 * @author duy.phan
	 * @since 2023-10-13
	 */
	
	public List updateSiteEERThisMonth(SiteEntity obj) {
		try {
			update("CronJobAlert.updateSiteEERThisMonth", obj);			
			return null;
		} catch (Exception ex) {
			return null;
		}
	}

	
	/**
	 * @description get list device
	 * @author long.pham
	 * @since 2023-07-20
	 */
	
	public List getListSiteLowProduction(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("CronJobAlert.getListSiteLowProduction", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	/**
	 * @description get list device by group
	 * @author long.pham
	 * @since 2024-03-04
	 */
	
	public List getListDeviceByGroup(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("CronJobAlert.getListDeviceByGroup", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	/**
	 * @description get list device by group
	 * @author long.pham
	 * @since 2024-03-04
	 */
	
	public List getListAlertByGroupDevice(AlertEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("CronJobAlert.getListAlertByGroupDevice", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	/**
	 * @description update multi alert
	 * @author long.pham
	 * @since 2024-03-06
	 */
	
	
	public List closeMultiAlert(AlertEntity obj) {
		try {
			update("CronJobAlert.closeMultiAlert", obj);			
			return null;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * @description get list employees hiding a site
	 * @author duy.phan
	 * @since 2023-06-21
	 * @param id
	 */

	public List getListEmployeeOnSiteMailMap(SiteEntity obj) {
		try {
			List rs = queryForList("CronJobAlert.getListEmployeeOnSiteMailMap", obj);
			if (rs == null) {
				return new ArrayList<>();
			}
			return rs;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * @description check nw internal
	 * @author duy.phan
	 * @since 2024-06-25
	 * @param id
	 */
	public int checkNwInternal(EmployeeEntity obj) {
		try {
			return (int)queryForObject("CronJobAlert.checkNwInternal", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/**
	 * @description check nw internal
	 * @author duy.phan
	 * @since 2024-06-25
	 * @param id
	 */
	public int checkSiteFTPNoDatalogger(int id_site) {
		try {
			return (int)queryForObject("CronJobAlert.checkSiteFTPNoDatalogger", id_site);
		} catch (Exception ex) {
			return 0;
		}
	}
	
}
