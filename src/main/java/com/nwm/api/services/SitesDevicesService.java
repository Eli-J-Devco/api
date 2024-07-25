/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DeviceParameterEntity;
import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.entities.TablePreferenceEntity;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SecretCards;
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
		List dataList, dataListNew = new ArrayList();
		SecretCards secretCard = new SecretCards();
		try {
			// get user preference for table sorting column
			TablePreferenceEntity tablePreference = new TablePreferenceEntity();
			tablePreference.setId_employee(obj.getId_employee());
			tablePreference.setTable("SiteDevices");
			tablePreference = (TablePreferenceEntity) queryForObject("TablePreference.getPreference", tablePreference);
			
			if ((obj.getOrder_by() != null) && (obj.getSort_column() != null)) {
				if (tablePreference != null) {
					tablePreference.setOrder_by(obj.getOrder_by());
					tablePreference.setSort_column(obj.getSort_column());
					update("TablePreference.updatePreference", tablePreference);
				} else {
					tablePreference = new TablePreferenceEntity();
					tablePreference.setId_employee(obj.getId_employee());
					tablePreference.setTable("SiteDevices");
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
			
			dataList = queryForList("SitesDevices.getListDeviceByIdSite", obj);
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
			
			
			Object dataListRowItem = queryForObject("SitesDevices.getModelLastRowItem", obj);
			if(dataListRowItem != null) {
				ObjectMapper oMapper = new ObjectMapper();
				Map<String, Object> map = oMapper.convertValue(dataListRowItem, Map.class);
//				data.setLast_communication(map.get("last_communication").toString());
			} else {
				// converting date format for US
				Date date = new Date();
		        SimpleDateFormat sdfAmerica = new SimpleDateFormat("MM/dd/yyyy, HH:mm:ss a");
		        TimeZone tzInAmerica = TimeZone.getTimeZone(data.getTimezone());
		        sdfAmerica.setTimeZone(tzInAmerica);
				
				data.setLast_communication(sdfAmerica.format(date) + " " + data.getAbbreviation_std());
			}
			
			
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
	 * @description get user preference for table sorting column
	 * @author Hung.Bui
	 * @since 2023-02-27
	 * @param id_customer, id_site
	 */
	public TablePreferenceEntity getPreference(SitesDevicesEntity obj) {
		try {
			// get user preference for table sorting column
			TablePreferenceEntity tablePreference = new TablePreferenceEntity();
			tablePreference.setId_employee(obj.getId_employee());
			tablePreference.setTable("SiteDevices");
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
	
}
