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
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.DeviceEntity;


public class DeviceService extends DB {

	/**
	 * @description get list site for page employee manage site
	 * @author long.pham
	 * @since 2021-01-12
	 */

	public List getListDeviceBySite(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Device.getListDeviceBySite", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}

	/**
	 * @description get total device by id_site
	 * @author long.pham
	 * @since 2021-01-12
	 */
	public int getDeviceBySiteTotalRecord(DeviceEntity obj) {
		try {
			return (int) queryForObject("Device.getDeviceBySiteTotalRecord", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	
	
	/**
	 * @description get device list by serial_number
	 * @author long.pham
	 * @since 2020-10-07
	 * @param serial_number
	 */
	
	public List getDeviceListBySerialNumber(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Device.getListBySerialNumber", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	/**
	 * @description get list device by id device type
	 * @author long.pham
	 * @since 2020-11-06
	 * @param id_site, id_customer, id_type_device
	 * @return array
	 */
	
	public List getListByDeviceType(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Device.getListByDeviceType", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	/**
	 * @description get list device by id device type
	 * @author long.pham
	 * @since 2020-11-12
	 * @param id_site, id_customer, id_type_device
	 * @return array
	 */
	
	public List getListDeviceByGroup(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Device.getListDeviceByGroup", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description update device status
	 * @author long.pham
	 * @since 2021-01-12
	 * @param id
	 */
	public boolean updateStatus(DeviceEntity obj) {
		try {
			return update("Device.updateStatus", obj) > 0;
		} catch (Exception ex) {
			log.error("Device.updateStatus", ex);
			return false;
		}
	}
	
	
	/**
	 * @description update time last_updated
	 * @author long.pham
	 * @since 2022-02-09
	 * @param id, last_updated
	 */
	public boolean updateLastUpdated(DeviceEntity obj) {
		try {
			return update("Device.updateLastUpdated", obj) > 0;
		} catch (Exception ex) {
			log.error("Device.updateLastUpdated", ex);
			return false;
		}
	}
	
	
	
	
	/**
	 * @description update ssh status
	 * @author long.pham
	 * @since 2022-02-09
	 * @param {}
	 */
	public boolean updateSshStatus(DeviceEntity obj) {
		try {
			return update("Device.updateSshStatus", obj) > 0;
		} catch (Exception ex) {
			log.error("Device.updateSshStatus", ex);
			return false;
		}
	}
	
	
	/**
	 * @description delete site
	 * @author long.pham
	 * @since 2021-01-11
	 * @param id
	 */
	public boolean deleteDevice(DeviceEntity obj) {
		try {
			return update("Device.deleteDevice", obj) > 0;
		} catch (Exception ex) {
			log.error("Device.deleteDevice", ex);
			return false;
		}
	}
	
	
	/**
	 * @description insert device
	 * @author long.pham
	 * @since 2021-01-12
	 */
	public DeviceEntity insertDevice(DeviceEntity obj) 
	{
		SqlSession session = this.beginTransaction();
		try {
			Object insertId =  session.insert("Device.insertDevice", obj);
			if(insertId != null && insertId instanceof Integer && obj.getId() > 0) {
//				 Create table, view, BJob
				session.insert("Device.createTableDevice", obj);
				session.insert("Device.createViewThreeMonthData", obj);
				session.insert("Device.createBJobData", obj);
				obj.setDatatablename("data" + obj.getId() + "_"+ obj.getDevice_group_table());
				obj.setView_tablename("View" + obj.getId() + "_"+ obj.getDevice_group_table());
				obj.setJob_tablename("BJob" + obj.getId() + "_"+ obj.getDevice_group_table());
				session.update("Device.updateTableDevice", obj);
				session.update("Device.updateFTPSite", obj);
			} else {
				throw new Exception();
			}

			session.commit();
			return obj;
		} catch (Exception ex) {
			session.rollback();
			log.error("Device.insertDevice", ex);
			obj.setId(0);
			return obj;
		} finally {
			session.close();
		}	
	}
	
	/**
	 * @description update device
	 * @author long.pham
	 * @since 2021-01-12
	 */
	public boolean updateDevice(DeviceEntity obj){
		SqlSession session = this.beginTransaction();
		try {
			session.update("Device.updateDevice", obj);
			session.update("Device.updateFTPSite", obj);
			session.commit();
			return true;
		} catch (Exception ex) {
			session.rollback();
			log.error("Device.updateDevice", ex);
			return false;
		} finally {
			session.close();
		}
	}
	
	
	/**
	 * @description get list site for page employee manage site
	 * @author long.pham
	 * @since 2021-01-12
	 */

	public List getListSshDataloggerCellModem(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Device.getListSshDataloggerCellModem", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}

	/**
	 * @description get total device by id_site
	 * @author long.pham
	 * @since 2021-01-12
	 */
	public int getTotalSshDataloggerCellModem(DeviceEntity obj) {
		try {
			return (int) queryForObject("Device.getTotalSshDataloggerCellModem", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/**
	 * @description Get list hidden data by device
	 * @author Hung.Bui
	 * @since 2023-08-03
	 * @param id_device
	 * @return array
	 */
	
	public List getListHiddenDataByDevice(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Device.getListHiddenDataByDevice", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description delete hidden data
	 * @author Hung.Bui
	 * @since 2023-08-03
	 * @param id
	 */
	public boolean deleteHiddenData(DeviceEntity obj) {
		try {
			return update("Device.deleteHiddenData", obj) > 0;
		} catch (Exception ex) {
			log.error("Device.deleteHiddenData", ex);
			return false;
		}
	}
	
	
	/**
	 * @description add hidden data
	 * @author Hung.Bui
	 * @since 2023-08-03
	 */
	public DeviceEntity insertHiddenData(DeviceEntity obj) 
	{
		try
	    {
	       Object insertId = insert("Device.insertHiddenData", obj);
	       if(insertId != null && insertId instanceof Integer) {
	    	   return obj;
	       }else {
	    	   return null;
	       }
	    }
	    catch(Exception ex)
	    {
	        log.error("Device.insertHiddenData", ex);
	        return null;
	    }	
	}
	
	/**
	 * @description check low production
	 * @author Hung.Bui
	 * @since 2023-08-17
	 */
	public void checkLowProduction(DeviceEntity obj, List<DeviceEntity> devicesList) {
		try {
			Integer lowProduction = (Integer) queryForObject("Device.getLowProductionErrorId", obj);
			if (lowProduction == null) return;
			List<HashMap<String, Object>> latest4HoursComparisonRatioDataList = new ArrayList<HashMap<String, Object>>(); 
			List poaDevicesList = devicesList.stream().filter(item -> item.getId_device_type() == 4).collect(Collectors.toList());
			if (!poaDevicesList.isEmpty()) {
				obj.setGroupWeather(poaDevicesList);
				latest4HoursComparisonRatioDataList = queryForList("Device.getComparisonRatioHavingPOA", obj);
			} else {
				List powerDevicesList = devicesList.stream().filter(item -> item.getId_device_type() == obj.getId_device_type()).collect(Collectors.toList());
				if (powerDevicesList.size() <= 1) return;
				obj.setGroupInverter(powerDevicesList);
				latest4HoursComparisonRatioDataList = queryForList("Device.getComparisonRatioNoPOA", obj);
			}
			if (latest4HoursComparisonRatioDataList == null || latest4HoursComparisonRatioDataList.isEmpty()) return;
			
			/** 
			 * low production alarm condition:
			 *  - all comparison ratio in latest 4 hours are less than or equal 70%.
			 *  - time difference between latest and oldest in latest 4 hours is larger or equal 4 hours (for case there are not enough data in latest 4 hours).
			 */
			LocalDateTime startTime = LocalDateTime.parse(latest4HoursComparisonRatioDataList.get(0).get("time_full").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			LocalDateTime endTime = LocalDateTime.parse(latest4HoursComparisonRatioDataList.get(latest4HoursComparisonRatioDataList.size() - 1).get("time_full").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			long hours = ChronoUnit.HOURS.between(startTime, endTime);
			boolean isComparisonRatioLessThanOrEqual70 = latest4HoursComparisonRatioDataList.stream().allMatch(item -> item.get("comparison_ratio") != null ? Double.parseDouble(item.get("comparison_ratio").toString()) <= 70 : false);
			
			AlertEntity alertDeviceItem = new AlertEntity();
			alertDeviceItem.setId_device(obj.getId());
			alertDeviceItem.setStart_date(obj.getLast_updated());
			alertDeviceItem.setId_error(lowProduction);
			
			if (hours >= 4 && isComparisonRatioLessThanOrEqual70) {
				boolean checkAlertExist = (int) queryForObject("BatchJob.checkAlertlExist", alertDeviceItem) > 0;
				boolean errorExits = (int) queryForObject("BatchJob.checkErrorExist", alertDeviceItem) > 0;
				if (!checkAlertExist && errorExits) {
					insert("BatchJob.insertAlert", alertDeviceItem);
				}
			} else {
				// Close alert
				AlertEntity checkAlertExist = (AlertEntity) queryForObject("BatchJob.getAlertDetail", alertDeviceItem);
				if (checkAlertExist != null && checkAlertExist.getId() > 0) {
					alertDeviceItem.setEnd_date(obj.getLast_updated());
					alertDeviceItem.setId(checkAlertExist.getId());
					update("BatchJob.updateCloseAlert", alertDeviceItem);
				}
			}
		} catch (Exception ex) {
			log.error("Device.checkLowProduction", ex);
		}
	}
	
	/**
	 * @description Get list device parameter
	 * @author Hung.Bui
	 * @since 2023-08-28
	 * @param id_device
	 * @return array
	 */
	
	public List getListDeviceParameter(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Device.getListDeviceParameter", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description Get list device filter parameter
	 * @author Hung.Bui
	 * @since 2024-03-06
	 * @param id_device
	 * @return array
	 */
	
	public List getListDeviceFilterParameter(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Device.getListDeviceFilterParameter", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description Get list device parameter
	 * @author duy.phan
	 * @since 2024-01-15
	 * @param id_device
	 * @return array
	 */
	
	public List getListScaledParameterByDeviceGroup(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Device.getListScaledParameterByDeviceGroup", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description Get list device parameter having scale setting
	 * @author Hung.Bui
	 * @since 2023-08-28
	 * @param id_device
	 * @return array
	 */
	
	public List getListScaledDeviceParameter(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Device.getListScaledDeviceParameter", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description update device parameter scale
	 * @author Hung.Bui
	 * @since 2023-08-28
	 * @param id_device, id_device_parameter, slope, offset
	 */
	public boolean saveDeviceParameterScale(DeviceEntity obj) {
		try {
			if (obj.getId() == 0 || obj.getId_device_parameter() == 0) return false;
			Object insertId = insert("Device.saveDeviceParameterScale", obj);
	        if(insertId == null) {
	        	return false;
	        }
	        return true;
		} catch (Exception ex) {
			log.error("Device.saveDeviceParameterScale", ex);
			return false;
		}
	}
	
	/**
	 * @description update device filter parameter
	 * @author Hung.Bui
	 * @since 2024-03-06
	 * @param id_device, id_device_parameter, min_value, max_value
	 */
	public boolean saveDeviceFilterParameter(DeviceEntity obj) {
		try {
			if (obj.getId() == 0 || obj.getId_device_parameter() == 0) return false;
			Object insertId = insert("Device.saveDeviceFilterParameter", obj);
			if(insertId == null) {
				return false;
			}
			return true;
		} catch (Exception ex) {
			log.error("Device.saveDeviceFilterParameter", ex);
			return false;
		}
	}

}
