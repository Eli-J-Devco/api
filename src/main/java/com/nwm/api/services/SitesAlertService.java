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
import com.nwm.api.entities.AlertHistoryEntity;
import com.nwm.api.entities.TablePreferenceEntity;

public class SitesAlertService extends DB {
	/**
	 * @description get list alert by site
	 * @author long.pham
	 * @since 2020-11-16
	 * @param id_customer, id_site, start_date, end_date
	 */

	public List getListBySiteId(AlertEntity obj) {
		try {
			// get user preference for table sorting column
			TablePreferenceEntity tablePreference = new TablePreferenceEntity();
			tablePreference.setId_employee(obj.getId_employee());
			tablePreference.setTable("SiteAlerts");
			tablePreference = (TablePreferenceEntity) queryForObject("TablePreference.getPreference", tablePreference);
			
			if ((obj.getOrder_by() != null) && (obj.getSort_column() != null)) {
				if (tablePreference != null) {
					tablePreference.setOrder_by(obj.getOrder_by());
					tablePreference.setSort_column(obj.getSort_column());
					update("TablePreference.updatePreference", tablePreference);
				} else {
					tablePreference = new TablePreferenceEntity();
					tablePreference.setId_employee(obj.getId_employee());
					tablePreference.setTable("SiteAlerts");
					tablePreference.setOrder_by(obj.getOrder_by());
					tablePreference.setSort_column(obj.getSort_column());
					insert("TablePreference.insertPreference", tablePreference);
				}
			} else {
				if (tablePreference != null) {
					obj.setOrder_by(tablePreference.getOrder_by());
					obj.setSort_column(tablePreference.getSort_column());
				}
			}
			
			List rs = queryForList("SitesAlert.getListBySiteId", obj);
			if (rs == null) {
				return new ArrayList<>();
			}
			return rs;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * @description count total alert by site
	 * @author long.pham
	 * @since 2020-11-16
	 * @param id_customer, id_site, start_date, end_date
	 */

	public int getListBySiteIdTotalCount(AlertEntity obj) {
		try {
			AlertEntity totalRecord = (AlertEntity) queryForObject("SitesAlert.getListBySiteIdTotalCount", obj);
			return totalRecord.getTotalRecord();
		} catch (Exception ex) {
			return 0;
		}
	}
	
	
	/**
	 * @description update error level status
	 * @author long.pham
	 * @since 2021-05-18
	 * @param id
	 */
	public boolean updateStatus(AlertEntity obj) {
		try {
			return update("SitesAlert.updateStatus", obj) > 0;
		} catch (Exception ex) {
			log.error("SitesAlert.updateStatus", ex);
			return false;
		}
	}

	
	/**
	 * @description update ack
	 * @author long.pham
	 * @since 2021-11-04
	 * @param id
	 */
	public boolean updateACK(AlertHistoryEntity obj) {
		try {
			AlertHistoryEntity dataObj = new AlertHistoryEntity();
			dataObj = (AlertHistoryEntity) queryForObject("SitesAlert.getACKByEmplyee", obj);
			if (dataObj == null) {
				Object insertId = insert("SitesAlert.insertAlertHistory", obj);
				if (insertId != null && insertId instanceof Integer) {
					return true;
				} else {
					return false;
				}
			} else {
				// update time
				return update("SitesAlert.updateAlertHistory", obj) > 0;
			}

		} catch (Exception ex) {
			log.error("SitesAlert.updateStatus", ex);
			return false;
		}
	}
	
	
	/**
	 * @description update alert
	 * @author long.pham
	 * @since 2021-11-05
	 * @param id
	 */
	public boolean updateAlert(AlertEntity obj){
		try{
			return update("SitesAlert.updateAlert", obj)>0;
		}catch (Exception ex) {
			log.error("SitesAlert.updateAlert", ex);
			return false;
		}
	}
	
	
	/**
	 * @description get detail alert
	 * @author long.pham
	 * @since 2021-03-18
	 * @param id_site
	 * @return Object
	 */

	public AlertEntity getAlertSummary(AlertEntity obj) {
		AlertEntity dataObj = new AlertEntity();
		try {
			dataObj = (AlertEntity) queryForObject("SitesAlert.getAlertSummary", obj);
			if (dataObj == null)
				return new AlertEntity();
		} catch (Exception ex) {
			return new AlertEntity();
		}
		return dataObj;

	}
	
	
	/**
	 * @description get site detail
	 * @author long.pham
	 * @since 2021-03-12
	 * @param id_site
	 * @return Object
	 */

	public AlertEntity getDetailSendMail(AlertEntity obj) {
		AlertEntity dataObj = new AlertEntity();
		try {
			dataObj = (AlertEntity) queryForObject("Alert.getDetailSendMail", obj);
			if (dataObj == null)
				return new AlertEntity();
		} catch (Exception ex) {
			return new AlertEntity();
		}
		return dataObj;
	}
	
	/**
	 * @description get user preference for table sorting column
	 * @author Hung.Bui
	 * @since 2023-02-27
	 * @param id_customer, id_site
	 */
	public TablePreferenceEntity getPreference(AlertEntity obj) {
		try {
			// get user preference for table sorting column
			TablePreferenceEntity tablePreference = new TablePreferenceEntity();
			tablePreference.setId_employee(obj.getId_employee());
			tablePreference.setTable("SiteAlerts");
			tablePreference = (TablePreferenceEntity) queryForObject("TablePreference.getPreference", tablePreference);
			
			if (tablePreference == null) {
				return new TablePreferenceEntity();
			}
			return tablePreference;
		} catch (Exception ex) {
			return null;
		}
	}

}
