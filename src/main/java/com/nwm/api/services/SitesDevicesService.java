/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.CameraImageEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DeviceParameterEntity;
import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SendMail;
import com.nwm.api.utils.TOTP;

public class SitesDevicesService extends DB {

	
	
	/**
	 * @description get site detail
	 * @author long.pham
	 * @since 2021-03-12
	 * @param id_site
	 * @return Object
	 */

	public SitesDevicesEntity getDetail(SitesDevicesEntity obj) {
		SitesDevicesEntity dataObj = new SitesDevicesEntity();
		try {
			dataObj = (SitesDevicesEntity) queryForObject("SitesDashboard.getDetail", obj);
			List listDeviceDisableAlert = new ArrayList();
			listDeviceDisableAlert = queryForList("SitesDashboard.getListDeviceIsDisableAlert", obj);	
			dataObj.setDeviceDisableAlerts(listDeviceDisableAlert);
			// Get list error level
			
			List errorLevel = new ArrayList();
			errorLevel = queryForList("SitesDashboard.getListErrorLevel", obj);
			dataObj.setErrorLevel(errorLevel);
			
			
			if (dataObj == null)
				return new SitesDevicesEntity();
		} catch (Exception ex) {
			return new SitesDevicesEntity();
		}
		return dataObj;
	}
	
	/**
	 * @description get list device by id_site
	 * @author long.pham
	 * @since 2021-03-12
	 * @param id_site
	 */
	

	public List getListDeviceByIdSite(SitesDevicesEntity obj) {
		try {
			List dataList = queryForList("SitesDevices.getListDeviceByIdSite", obj);
			if (dataList == null) return new ArrayList();
			
			return dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	/**
	 * @description Get device yield list
	 * @author Hung.Bui
	 * @since 2024-07-24
	 * @param list_device
	 */
	
	public List getListYieldByDevice(SitesDevicesEntity obj) {
		try {
			if (obj.getList_device() == null || obj.getList_device().size() == 0) return new ArrayList();
			List dataList = queryForList("SitesDevices.getListYieldByDevice", obj);
			if (dataList == null) return new ArrayList();
			
			return dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	/**
	 * @description get device detail by id
	 * @author long.pham
	 * @since 2021-03-16
	 * @param id_site, id_device
	 * @return Object
	 */

	public DeviceEntity getDeviceDetail(DeviceEntity obj) {
		DeviceEntity data = new DeviceEntity();
		try {
			data = (DeviceEntity) queryForObject("SitesDevices.getDeviceDetail", obj);
			if(data == null) {
				return new DeviceEntity();
			}
			obj.setDatatablename(data.getDatatablename());
		} catch (Exception ex) {
			return new DeviceEntity();
		}
		return data;
	}
	
	
	
	/**
	 * @description get list device by id_site
	 * @author long.pham
	 * @since 2021-03-16
	 * @param id_site, id_device
	 */
	

	public List getListParameters(DeviceEntity obj) {
		List<Object> dataList, dataListNew = new ArrayList<Object>();
		try {
			dataList = queryForList("SitesDevices.getListDeviceParameter", obj);
			if(dataList.size() > 0) {
				Object dataListRowItem = queryForObject("SitesDevices.getModelLastRowItem", obj);
				ObjectMapper oMapper = new ObjectMapper();
				Map<String, Object> map = oMapper.convertValue(dataListRowItem, Map.class);
				for(int i =0; i< dataList.size(); i++) {
					DeviceParameterEntity item = (DeviceParameterEntity)dataList.get(i);
					if(dataListRowItem != null) {
						String valueField = map.get(item.getSlug()) != null ? map.get(item.getSlug()).toString() : "";
						item.setValue(valueField);
					} else {
						item.setValue("");
					}
					
					dataListNew.add(item);
				}
			} else {
				return new ArrayList<Object>();
			}
				
		} catch (Exception ex) {
			return new ArrayList<Object>();
		}
		return dataListNew;
	}
	
	/**
	 * @description get list summary device by id_site
	 * @author long.pham
	 * @since 2023-06-20
	 * @param id_site
	 */
	

	public List getListSummaryDevice(SitesDevicesEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("SitesDevices.getListSummaryDevice", obj);
			return dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
		
	}
	
	/**
	 * @description Send OTP
	 * @author Hung.Bui
	 * @since 2024-05-28
	 * @param id_site
	 */
	
	public boolean sendOTP(String user_name) {
		try {
			String OTP = TOTP.generateTOTP(user_name);
			if (OTP == null) return false;
			String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailFromContact);
		    String msgTemplate = Constants.getMailTempleteByState(20);
		    String body = String.format(msgTemplate, "Customer", OTP);
		    String mailTo = user_name;
		    String subject = Constants.getMailSubjectByState(20);
		    
		    String tags = "OTP_code";
		    String fromName = "NEXT WAVE ENERGY MONITORING INC";
		    boolean flagSent = SendMail.SendGmailTLS(mailFromContact, fromName, mailTo, null, null, subject, body, tags);
			return flagSent;
		} catch (Exception ex) {
			return false;
		}
	}
	
	/**
	 * @description Validate OTP
	 * @author Hung.Bui
	 * @since 2024-05-28
	 * @param id_site
	 */
	
	public boolean validateOTP(String verifyCode, String user_name) {
		try {
			return TOTP.validateTOTP(user_name, verifyCode);
		} catch (Exception ex) {
			return false;
		}
	}
	
	/**
	 * @description get list image camera
	 * @author duy.phan
	 * @since 2025-01-24
	 * @param id_site
	 */
	

	public List getListCameraImage(CameraImageEntity obj) {

		try {
			List dataList = queryForList("SitesDevices.getListCameraImage", obj);
			if (dataList == null) return new ArrayList();
			
			return dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	/**
	 * @description get list image camera
	 * @author duy.phan
	 * @since 2025-01-24
	 * @param id_site
	 */
	

	public List getListCameraDevices(SitesDevicesEntity obj) {

		try {
			List dataList = queryForList("SitesDevices.getListCameraDevices", obj);
			if (dataList == null) return new ArrayList();
			
			return dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	/**
	 * @description get first  image camera by id_device
	 * @author duy.phan 
	 * @since 2025-01-24
	 * @param id_site
	 */
	
	public CameraImageEntity getFirstTimeImageCamera(CameraImageEntity obj) {
		CameraImageEntity rowItem = new CameraImageEntity();
		try {
			rowItem = (CameraImageEntity) queryForObject("SitesDevices.getFirstTimeImageCamera", obj);
			if (rowItem == null) {
				return new CameraImageEntity();
			}
			
			return rowItem;
		} catch (Exception ex) {
			return new CameraImageEntity();
		}
	}
	
}
