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
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.AlertHistoryEntity;
import com.nwm.api.entities.ChartAlertDateEntity;
import com.nwm.api.entities.DailyDateEntity;
import com.nwm.api.entities.ErrorLevelEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.entities.TablePreferenceEntity;

public class AlertService extends DB {
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
			tablePreference.setTable("Alert");
			tablePreference = (TablePreferenceEntity) queryForObject("TablePreference.getPreference", tablePreference);
			
			if ((obj.getOrder_by() != null) && (obj.getSort_column() != null)) {
				if (tablePreference != null) {
					tablePreference.setOrder_by(obj.getOrder_by());
					tablePreference.setSort_column(obj.getSort_column());
					update("TablePreference.updatePreference", tablePreference);
				} else {
					tablePreference = new TablePreferenceEntity();
					tablePreference.setId_employee(obj.getId_employee());
					tablePreference.setTable("Alert");
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
			
			List rs = queryForList("Alert.getList", obj);
			if (rs == null) {
				return new ArrayList<>();
			}
			return rs;
		} catch (Exception ex) {
			return null;
		}
	}

	
	/**
	 * @description get list group by site
	 * @author long.pham
	 * @since 2022-11-24
	 * @param id_employee
	 */

	public List getAllAlertBySite(AlertEntity obj) {
		try {
			List rs = queryForList("Alert.getAllAlertBySite", obj);
			if (rs == null) {
				return new ArrayList<>();
			}
			return rs;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * @description get list group by site
	 * @author long.pham
	 * @since 2022-11-24
	 * @param id_employee
	 */

	public List getListAlertGroupBySite(AlertEntity obj) {
		try {
			List rs = queryForList("Alert.getListAlertGroupBySite", obj);
			if (rs == null) {
				return new ArrayList<>();
			}
			return rs;
		} catch (Exception ex) {
			return null;
		}
	}
	
	
	
	/**
	 * @description count total group by site
	 * @author long.pham
	 * @since 2022-11-24
	 * @param id_employee
	 */

	public int getTotalGroupAlertSite(AlertEntity obj) {
		try {
			AlertEntity totalRecord = (AlertEntity) queryForObject("Alert.getTotalGroupAlertSite", obj);
			return totalRecord.getTotalRecord();
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/**
	 * @description get list site by id_sites
	 * @author long.pham
	 * @since 2021-02-02
	 * @param arr id_sites
	 */

	public List getListIdSites(AlertEntity obj) {
		try {
			List rs = queryForList("Alert.getListIdSites", obj);
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

	public int getListTotalCount(AlertEntity obj) {
		try {
			AlertEntity totalRecord = (AlertEntity) queryForObject("Alert.getTotal", obj);
			return totalRecord.getTotalRecord();
		} catch (Exception ex) {
			return 0;
		}
	}

	/**
	 * @description get detail alert
	 * @author long.pham
	 * @since 2020-11-24
	 * @param id_site, id_alert, id_customer, current_time
	 * @return Object
	 */

	public Object getDetailAlert(AlertEntity obj) {
		Object dataObj = null;
		try {
			dataObj = queryForObject("Alert.getDetailAlert", obj);
			if (dataObj == null)
				return new AlertEntity();
		} catch (Exception ex) {
			return new AlertEntity();
		}
		return dataObj;

	}

	/**
	 * @description get alert Exists
	 * @author long.pham
	 * @since 2021-01-29
	 * @param error_code, time
	 */
	public boolean checkAlertExist(AlertEntity dataE) {
		try {
			return (int) queryForObject("Alert.checkAlertlExist", dataE) > 0;
		} catch (Exception e) {

		}
		return true;
	}

	/**
	 * @description insert alert
	 * @author long.pham
	 * @since 2021-01-29
	 * @param id
	 */
	public AlertEntity insertAlert(AlertEntity obj) {
		try {
			Object insertId = insert("Alert.insertAlert", obj);
			if (insertId != null && insertId instanceof Integer) {
				return obj;
			} else {
				return null;
			}
		} catch (Exception ex) {
			log.error("Alert.insertAlert", ex);
			return null;
		}
	}

	/**
	 * @description get list alert by site
	 * @author long.pham
	 * @since 2021-03-18
	 * @param id_customer, id_site, start_date, end_date
	 */

	public List getListBySiteAdmin(AlertEntity obj) {
		try {
			List rs = queryForList("Alert.getListBySiteAdmin", obj);
			if (rs == null) {
				return new ArrayList<>();
			}
			return rs;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * @description get detail alert
	 * @author long.pham
	 * @since 2021-03-18
	 * @param id_site
	 * @return Object
	 */

	public SiteEntity getSiteDetail(AlertEntity obj) {
		SiteEntity dataObj = new SiteEntity();
		try {
			dataObj = (SiteEntity) queryForObject("Alert.getSiteDetail", obj);
			if (dataObj == null)
				return new SiteEntity();
		} catch (Exception ex) {
			return new SiteEntity();
		}
		return dataObj;

	}

	/**
	 * @description update error level status
	 * @author long.pham
	 * @since 2021-05-18
	 * @param id
	 */
	public boolean updateStatus(AlertEntity obj) {
		try {
			return update("Alert.updateStatus", obj) > 0;
		} catch (Exception ex) {
			log.error("Alert.updateStatus", ex);
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
			dataObj = (AlertHistoryEntity) queryForObject("Alert.getACKByEmplyee", obj);
			if (dataObj == null) {
				Object insertId = insert("Alert.insertAlertHistory", obj);
				if (insertId != null && insertId instanceof Integer) {
					return true;
				} else {
					return false;
				}
			} else {
				// update time
				return update("Alert.updateAlertHistory", obj) > 0;
			}

		} catch (Exception ex) {
			log.error("Alert.updateStatus", ex);
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
			return update("Alert.updateAlert", obj)>0;
		}catch (Exception ex) {
			log.error("Alert.updateAlert", ex);
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
			dataObj = (AlertEntity) queryForObject("Alert.getAlertSummary", obj);
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
			tablePreference.setTable("Alert");
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
	 * @description get list site by id_sites
	 * @author long.pham
	 * @since 2021-02-02
	 * @param arr id_sites
	 */

	public List getDataChart(AlertEntity obj) {
		try {
			
			// Create list date 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
			SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm"); 
			SimpleDateFormat catFormat = new SimpleDateFormat("MM-dd-yyyy");
			
//			SimpleDateFormat dateFormatHour = new SimpleDateFormat("HH:00");
			Date startDate = dateFormat.parse(obj.getStart_date() + " AM");
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			List<ChartAlertDateEntity> categories = new ArrayList<ChartAlertDateEntity> ();
			int minute = 15;
			int forCount = 96 * 3;
//			if(obj.getData_intervals() == 1) {
//				minute = 5;
//				forCount = 288 * 3;
//			} else if(obj.getData_intervals() == 2) {
//				minute = 15;
//				forCount = 96*3;
//			} else if(obj.getData_intervals() == 3) {
//				minute = 60;
//				forCount = 24*3;
//			}
			for(int t = 0; t < forCount; t++) {
				cal.setTime(startDate);
				ChartAlertDateEntity headerDate = new ChartAlertDateEntity();
				cal.add(Calendar.MINUTE, t * minute);
				
				headerDate.setTime_format(timeFormat.format(cal.getTime()));
//				String hours = dateFormatHour.format(cal.getTime());
				headerDate.setCategories_time(catFormat.format(cal.getTime()));
				headerDate.setEnergy(0.001);
				headerDate.setPower(0.001);
				headerDate.setIrradiance(0.001);
				categories.add(headerDate);
			}
			
			
			List dataPower = queryForList("Alert.getDataChart", obj);
			List<ChartAlertDateEntity> dataNewPower = new ArrayList<ChartAlertDateEntity> ();
			if(categories.size() > 0) {
				for (ChartAlertDateEntity item : categories) {
					boolean flag = false;
					ChartAlertDateEntity mapItemObj = new ChartAlertDateEntity();
					if(dataPower != null && dataPower.size() > 0) {
						for( int v = 0; v < dataPower.size(); v++){
							Map<String, Object> itemT = (Map<String, Object>) dataPower.get(v);
							String categoriesTime = item.getTime_format();
							String powerTime = itemT.get("time_format").toString();
					        if (categoriesTime.equals(powerTime)) {
					        	flag = true;
					        	mapItemObj.setCategories_time(itemT.get("categories_time").toString());
					        	Double power = Double.parseDouble(itemT.get("power").toString());
					        	power = (power == -0.0) ? 0 : power;
					        	
					        	mapItemObj.setPower( power );
					        	mapItemObj.setIrradiance(item.getIrradiance());
					        	mapItemObj.setTime_format(itemT.get("time_format").toString());
					        	
					        	
					        	if(itemT.get("power") == null || Double.parseDouble(itemT.get("power").toString()) == 0.001) {
						        	mapItemObj.setEnergy( 0.001 );
					        	} else {
					        		Double energy = (double)Math.round(Double.parseDouble(itemT.get("power").toString()) * 15/60);
						        	mapItemObj.setEnergy( energy > 0 ? energy : 0 );
					        	}
					        	
					        	
					        	break;
					        }
					    }
					}
					
					
					
					if(flag == false) {
						ChartAlertDateEntity mapItem = new ChartAlertDateEntity();
						mapItem.setCategories_time(item.getCategories_time());
						mapItem.setTime_format(item.getTime_format());
						mapItem.setIrradiance(item.getIrradiance());
						mapItem.setEnergy(item.getEnergy());
						mapItem.setPower(item.getPower());
						
						dataNewPower.add(mapItem);
					} else {
						dataNewPower.add(mapItemObj);
					}
				}
			}
//			
//			dataObj.setDataReports(dataNewPower);
//			
						
//			List rs = queryForList("Alert.getDataChart", obj);
//			if (rs == null) {
//				return new ArrayList<>();
//			}
			return dataNewPower;
		} catch (Exception ex) {
			return null;
		}
	}
	
}
