/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.DashboardEntity;
import com.nwm.api.entities.TablePreferenceEntity;

public class DashboardService extends DB {
	/**
	 * @description get list alert by site
	 * @author long.pham
	 * @since 2020-11-16
	 * @param id_customer, id_site, start_date, end_date
	 */

	public List getList(AlertEntity obj) {
		try {
			// get user preference for table sorting column
			TablePreferenceEntity tablePreference = new TablePreferenceEntity();
			tablePreference.setId_employee(obj.getId_employee());
			tablePreference.setTable("DashboardLatestAlert");
			tablePreference = (TablePreferenceEntity) queryForObject("TablePreference.getPreference", tablePreference);
			
			if ((obj.getOrder_by() != null) && (obj.getSort_column() != null)) {
				if (tablePreference != null) {
					tablePreference.setOrder_by(obj.getOrder_by());
					tablePreference.setSort_column(obj.getSort_column());
					update("TablePreference.updatePreference", tablePreference);
				} else {
					tablePreference = new TablePreferenceEntity();
					tablePreference.setId_employee(obj.getId_employee());
					tablePreference.setTable("DashboardLatestAlert");
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
						
			List rs = queryForList("Dashboard.getList", obj);
			if (rs == null) {
				return new ArrayList<>();
			}
			return rs;
		} catch (Exception ex) {
			return null;
		}
	}
	
	
	
	
	/**
	 * @description get list alert by site
	 * @author long.pham
	 * @since 2020-11-16
	 * @param id_customer, id_site, start_date, end_date
	 */

	public List getListActualvsExpected(DashboardEntity obj) {
		try {
			// get user preference for table sorting column
			TablePreferenceEntity tablePreference = new TablePreferenceEntity();
			tablePreference.setId_employee(obj.getId_employee());
			tablePreference.setTable("DashboardActualvsExpected");
			tablePreference = (TablePreferenceEntity) queryForObject("TablePreference.getPreference", tablePreference);
			
			if ((obj.getOrder_by() != null) && (obj.getSort_column() != null)) {
				if (tablePreference != null) {
					tablePreference.setOrder_by(obj.getOrder_by());
					tablePreference.setSort_column(obj.getSort_column());
					update("TablePreference.updatePreference", tablePreference);
				} else {
					tablePreference = new TablePreferenceEntity();
					tablePreference.setId_employee(obj.getId_employee());
					tablePreference.setTable("DashboardActualvsExpected");
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

			List rs = queryForList("Dashboard.getListActualvsExpected", obj);
			if (rs == null) {
				return new ArrayList<>();
			}
			return rs;
		} catch (Exception ex) {
			return null;
		}
	}
	
//
//	
//	
//	/**
//	 * @description get list site by id_sites
//	 * @author long.pham
//	 * @since 2021-02-02
//	 * @param arr id_sites
//	 */
//
//	public List getListIdSites(AlertEntity obj) {
//		try {
//			List rs = queryForList("Alert.getListIdSites", obj);
//			if (rs == null) {
//				return new ArrayList<>();
//			}
//			return rs;
//		} catch (Exception ex) {
//			return null;
//		}
//	}
//
//	/**
//	 * @description count total alert by site
//	 * @author long.pham
//	 * @since 2020-11-16
//	 * @param id_customer, id_site, start_date, end_date
//	 */
//
//	public int getListTotalCount(AlertEntity obj) {
//		try {
//			AlertEntity totalRecord = (AlertEntity) queryForObject("Alert.getTotal", obj);
//			return totalRecord.getTotalRecord();
//		} catch (Exception ex) {
//			return 0;
//		}
//	}
//
//	/**
//	 * @description get detail alert
//	 * @author long.pham
//	 * @since 2020-11-24
//	 * @param id_site, id_alert, id_customer, current_time
//	 * @return Object
//	 */
//
//	public Object getDetailAlert(AlertEntity obj) {
//		Object dataObj = null;
//		try {
//			dataObj = queryForObject("Alert.getDetailAlert", obj);
//			if (dataObj == null)
//				return new AlertEntity();
//		} catch (Exception ex) {
//			return new AlertEntity();
//		}
//		return dataObj;
//
//	}
//
//	/**
//	 * @description get alert Exists
//	 * @author long.pham
//	 * @since 2021-01-29
//	 * @param error_code, time
//	 */
//	public boolean checkAlertExist(AlertEntity dataE) {
//		try {
//			return (int) queryForObject("Alert.checkAlertlExist", dataE) > 0;
//		} catch (Exception e) {
//
//		}
//		return true;
//	}
//
//	/**
//	 * @description insert alert
//	 * @author long.pham
//	 * @since 2021-01-29
//	 * @param id
//	 */
//	public AlertEntity insertAlert(AlertEntity obj) {
//		try {
//			Object insertId = insert("Alert.insertAlert", obj);
//			if (insertId != null && insertId instanceof Integer) {
//				return obj;
//			} else {
//				return null;
//			}
//		} catch (Exception ex) {
//			log.error("Alert.insertAlert", ex);
//			return null;
//		}
//	}
//
//	/**
//	 * @description get list alert by site
//	 * @author long.pham
//	 * @since 2021-03-18
//	 * @param id_customer, id_site, start_date, end_date
//	 */
//
//	public List getListBySiteAdmin(AlertEntity obj) {
//		try {
//			List rs = queryForList("Alert.getListBySiteAdmin", obj);
//			if (rs == null) {
//				return new ArrayList<>();
//			}
//			return rs;
//		} catch (Exception ex) {
//			return null;
//		}
//	}
//
//	/**
//	 * @description get detail alert
//	 * @author long.pham
//	 * @since 2021-03-18
//	 * @param id_site
//	 * @return Object
//	 */
//
//	public SiteEntity getSiteDetail(AlertEntity obj) {
//		SiteEntity dataObj = new SiteEntity();
//		try {
//			dataObj = (SiteEntity) queryForObject("Alert.getSiteDetail", obj);
//			if (dataObj == null)
//				return new SiteEntity();
//		} catch (Exception ex) {
//			return new SiteEntity();
//		}
//		return dataObj;
//
//	}
//
//	/**
//	 * @description update error level status
//	 * @author long.pham
//	 * @since 2021-05-18
//	 * @param id
//	 */
//	public boolean updateStatus(AlertEntity obj) {
//		try {
//			return update("Alert.updateStatus", obj) > 0;
//		} catch (Exception ex) {
//			log.error("Alert.updateStatus", ex);
//			return false;
//		}
//	}
//
//	/**
//	 * @description update ack
//	 * @author long.pham
//	 * @since 2021-11-04
//	 * @param id
//	 */
//	public boolean updateACK(AlertHistoryEntity obj) {
//		try {
//			AlertHistoryEntity dataObj = new AlertHistoryEntity();
//			dataObj = (AlertHistoryEntity) queryForObject("Alert.getACKByEmplyee", obj);
//			if (dataObj == null) {
//				Object insertId = insert("Alert.insertAlertHistory", obj);
//				if (insertId != null && insertId instanceof Integer) {
//					return true;
//				} else {
//					return false;
//				}
//			} else {
//				// update time
//				return update("Alert.updateAlertHistory", obj) > 0;
//			}
//
//		} catch (Exception ex) {
//			log.error("Alert.updateStatus", ex);
//			return false;
//		}
//	}
//	
//	
//	/**
//	 * @description update alert
//	 * @author long.pham
//	 * @since 2021-11-05
//	 * @param id
//	 */
//	public boolean updateAlert(AlertEntity obj){
//		try{
//			return update("Alert.updateAlert", obj)>0;
//		}catch (Exception ex) {
//			log.error("Alert.updateAlert", ex);
//			return false;
//		}
//	}
//	
	
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
			dataObj = (AlertEntity) queryForObject("Dashboard.getAlertSummary", obj);
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
			tablePreference.setTable("DashboardLatestAlert");
			tablePreference = (TablePreferenceEntity) queryForObject("TablePreference.getPreference", tablePreference);
			
			if (tablePreference == null) {
				return new TablePreferenceEntity();
			}
			return tablePreference;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * @description get user preference for table sorting column
	 * @author Hung.Bui
	 * @since 2023-02-27
	 * @param id_customer, id_site
	 */
	public TablePreferenceEntity getPreference(DashboardEntity obj) {
		try {
			// get user preference for table sorting column
			TablePreferenceEntity tablePreference = new TablePreferenceEntity();
			tablePreference.setId_employee(obj.getId_employee());
			tablePreference.setTable("DashboardActualvsExpected");
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
