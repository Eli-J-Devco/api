/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.AlertFilterEntity;
import com.nwm.api.entities.AlertHistoryEntity;
import com.nwm.api.entities.AlertNoteEntity;
import com.nwm.api.entities.AlertsBySiteDeviceRequest;
import com.nwm.api.entities.AlertsBySiteDeviceResponse;
import com.nwm.api.entities.ChartAlertDateEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.Lib;
import com.nwm.api.entities.CustomAlertMetricEntity;
import com.nwm.api.entities.CustomAlertEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DeviceGroupEntity;

public class AlertService extends DB {
	/**
	 * @description get list alert by site
	 * @author long.pham
	 * @since 2020-11-16
	 * @param id_customer, id_site, start_date, end_date
	 */

	public List getList(AlertEntity obj) {
		try {
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
	 * @description insert alert note
	 * @author long.pham
	 * @since 2021-01-29
	 * @param id
	 */
	public AlertNoteEntity updateAlertNote(AlertNoteEntity obj) {
		SqlSession session = this.beginTransaction();
		try {
			session.update("Alert.updateAlertNote", obj);
			session.commit();
			return obj;
		} catch (Exception ex) {
			session.rollback();
			log.error("Alert.updateAlertNote", ex);
			return null;
		} finally {
			session.close();
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
	 * @description get list site by id_sites
	 * @author long.pham
	 * @since 2021-02-02
	 * @param arr id_sites
	 */

	public List<ChartAlertDateEntity> getDataChart(AlertEntity obj) {
		try {
			// ----- Create DateTime List ----- Begin
			DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			DateTimeFormatter fullTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
			DateTimeFormatter categoriesTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
			LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), inputFormat);
			LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), inputFormat);
			
			List<ChartAlertDateEntity> categories = new ArrayList<>();
			while (!start.isAfter(end)) {
				ChartAlertDateEntity dateTime = new ChartAlertDateEntity();
				dateTime.setTime_format(start.format(fullTimeFormat));
				dateTime.setCategories_time(start.format(categoriesTimeFormat));
				categories.add(dateTime);
				start = start.plus(15, ChronoUnit.MINUTES);
			}
			// ----- Create DateTime List ----- End
						
			List<ChartAlertDateEntity> dataPower = queryForList("Alert.getDataChart", obj);
			
			return Lib.fulfillData(categories, dataPower, "time_format");
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * @description update is_read
	 * @author duy.phan
	 * @since 2023-05-08
	 * @param id
	 */
	public boolean updateIsRead(AlertEntity obj) {
		try {
			return update("Alert.updateIsRead", obj) > 0;
		} catch (Exception ex) {
			log.error("Alert.updateIsRead", ex);
			return false;
		}
	}
	
	/**
	 * @description update is_notification
	 * @author duy.phan
	 * @since 2023-05-08
	 * @param id
	 */
	public boolean updateIsNotification(AlertEntity obj) {
		SqlSession session = this.beginTransaction();
		try {
			List dataList = obj.getAlerts();
			if (dataList.size() <= 0) {
				return true;
			}
			
			for (int i = 0; i < dataList.size(); i++) {
				session.update("Alert.updateIsNotification", dataList.get(i));
			}	
			
			session.commit();
			return true;
		} catch (Exception ex) {
			session.rollback();
			log.error("Alert.updateIsNotification", ex);
			return false;
		} finally {
			session.close();
		}
	}
	
	/**
	 * @description get list employees hiding a site
	 * @author duy.phan
	 * @since 2023-06-21
	 * @param id_site
	 */

	public List getEmployeeHidingSite(AlertEntity obj) {
		try {
			List rs = queryForList("Alert.getEmployeeHidingSite", obj);
			if (rs == null) {
				return new ArrayList<>();
			}
			return rs;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * @description insert error level
	 * @author duy.phan
	 * @since 2023-07-17
	 */
	public AlertFilterEntity saveAlertFilter(AlertFilterEntity obj) {
		try {
			// Check Filter is_default and delete all default filter
			if (obj.getIs_default() == 1) {
				Object total = queryForObject("AlertFilter.getListCountDefault", obj);
				if((int)total > 0) {
					delete("AlertFilter.deleteAlertFilterDefault", obj);
				}
			}
			
			// Save
			Object insertId = insert("AlertFilter.saveAlertFilter", obj);
			if (insertId != null && insertId instanceof Integer) {
				Object total = queryForObject("AlertFilter.getListCount", obj);
				if((int)total > 10) {
					// Delete one row
					delete("AlertFilter.deleteAlertFilter", obj);
				}
				return obj;
			} else {
				return null;
			}

		} catch (Exception ex) {
			log.error("insert", ex);
			return null;
		}
	}
	
	/**
	 * @description get list favorites by id_site
	 * @author duy.phan
	 * @since 2023-07-18
	 * @param id_site
	 */
	public List getListAlertFilter(AlertFilterEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("AlertFilter.getListAlertFilter", obj);
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	/**
	 * @description get detail alert
	 * @author long.pham
	 * @since 2020-11-24
	 * @param id_site, id_alert, id_customer, current_time
	 * @return Object
	 */

	public Object getAlertPerPage(AlertFilterEntity obj) {
		Object dataObj = null;
		try {
			dataObj = queryForObject("Alert.getAlertPerPage", obj);
			if (dataObj == null)
				return new AlertEntity();
		} catch (Exception ex) {
			return new AlertEntity();
		}
		return dataObj;

	}
	
	/**
	 * @description get list favorites by id_site
	 * @author duy.phan
	 * @since 2023-07-18
	 * @param id_site
	 */
	public Object getAlertFilterDefault(AlertFilterEntity obj) {
		Object dataObj = null;
		try {
			dataObj = queryForObject("AlertFilter.getAlertFilterDeafult", obj);
			return dataObj;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	 /** @description delete a alert filter
	 * @author duy.phan
	 * @since 2023-07-19
	 * @param id
	 */
	public boolean deleteAlertFilter(AlertFilterEntity obj) {
		try {
			return delete("AlertFilter.deleteAlertFilterById", obj) > 0;
		} catch (Exception ex) {
			log.error("AlertFilter.deleteAlertFilterById", ex);
			return false;
		}
	}
	
	/** @description delete all alert filter
	 * @author duy.phan
	 * @since 2023-07-19
	 * @param id
	 */
	public boolean deleteAllAlertFilter(AlertFilterEntity obj) {
		try {
			return delete("AlertFilter.deleteAllAlertFilterById", obj) > 0;
		} catch (Exception ex) {
			log.error("AlertFilter.deleteAllAlertFilterById", ex);
			return false;
		}
	}
	
	/**
	 * get site alert count list
	 * @author Hung.Bui
	 * @since 2025-06-27
	 * @return list of alert count
	 */
	public List<AlertEntity> getSiteAlertCountListInDuration(AlertEntity obj) {
		try {
			return queryForList("Alert.getSiteAlertCountListInDuration", obj);
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	/**
	 * get alerts by site's devices
	 * @author Hung.Bui
	 * @since 2025-09-19
	 * @param obj { id, date_from, date_to, device_list, data_send_time }
	 * @return list of alerts
	 */
	public List<AlertsBySiteDeviceResponse> getSiteDeviceAlerts(AlertsBySiteDeviceRequest obj) {
		try {
			return queryForList("Alert.getSiteDeviceAlerts", obj);
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

    public List<DeviceEntity> getDeviceList(DeviceEntity obj) {
        try {
            return queryForList("Device.getListByDeviceGroupAndSiteDropDown", obj);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<CustomAlertMetricEntity> getMetricList(CustomAlertMetricEntity obj) {
        try {
            return queryForList("CustomAlertMetric.getMetricListByDeviceTypeId", obj);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<DeviceGroupEntity> getDeviceGroupList(DeviceGroupEntity obj) {
        try {
            return queryForList("DeviceGroup.getListDeviceGroupBySite", obj);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private CustomAlertEntity getCustomAlertParam(int idSite, CustomAlertEntity obj) {
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("id_site", idSite);
            params.put("id_device_group", obj.getId_device_group());
            // check deviceType thuộc 1 site
            Object exist = queryForObject("CustomAlert.checkSiteHaveDeviceType", params);
            if (exist != null) {
                CustomAlertEntity entity = new CustomAlertEntity();
                entity.setId_site(idSite);
                entity.setId_device_group(obj.getId_device_group());
                entity.setId_metric(obj.getId_metric());
                entity.setCondition(obj.getCondition());
                entity.setThreshold(obj.getThreshold());
                entity.setCompare_to(obj.getCompare_to());
                entity.setTime_from(obj.getTime_from());
                entity.setTime_to(obj.getTime_to());
                entity.setLevel(obj.getLevel());
                entity.setNotify_email(obj.getNotify_email());
                entity.setNotify_web(obj.getNotify_web());
                entity.setAlert_email(obj.getAlert_email());
                entity.setStatus(obj.getStatus());
                entity.setIs_delete(obj.getIs_delete());
                return entity;
            }
        } catch (Exception ex) {
            log.error("saveCustomAlert", ex);
        }
        return null;
    }

    public CustomAlertEntity saveCustomAlert(CustomAlertEntity obj) {
        try {
            for (int i = 0; i < obj.getIds_site().size(); i++) {
                CustomAlertEntity entity = getCustomAlertParam(obj.getIds_site().get(i), obj);
                if (entity == null) {
                    continue;
                }
                List<Integer> ids_device = obj.getIds_device();
                if (!ids_device.isEmpty()) {
                    for (Integer id : ids_device) {
                        entity.setId_device(id);
                        insert("CustomAlert.insertCustomAlert", entity);
                    }
                } else {
                    insert("CustomAlert.insertCustomAlert", entity);
                }
            }
            return obj;
        } catch (Exception ex) {
            log.error("saveCustomAlert", ex);
        }
        return null;
    }
}
