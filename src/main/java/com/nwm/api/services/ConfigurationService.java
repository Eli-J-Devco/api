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

import org.apache.ibatis.session.SqlSession;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ConfigurationEntity;

public class ConfigurationService extends DB {
	
	/**
	 * @description get configuration site detail
	 * @author long.pham
	 * @since 2020-10-22
	 * @param id_customer, id_site
	 * @return Object
	 */

	public ConfigurationEntity getDetail(ConfigurationEntity obj) {
		ConfigurationEntity dataObj = new ConfigurationEntity();
		try {
			dataObj = (ConfigurationEntity) queryForObject("Configuration.getDetail", obj);
			
			if (dataObj == null)
				return new ConfigurationEntity();
		} catch (Exception ex) {
			return new ConfigurationEntity();
		}
		return dataObj;
	}
	
	
	/**
	 * @description get total record Employee
	 * @author long.pham
	 * @since 2021-01-06
	 */
	public ConfigurationEntity getConfigEstimated(ConfigurationEntity obj) {
		ConfigurationEntity dataObj = new ConfigurationEntity();
		try {
			dataObj = (ConfigurationEntity) queryForObject("Configuration.getConfigEstimated", obj);
			if (dataObj == null)
				return new ConfigurationEntity();
		} catch (Exception ex) {
			return new ConfigurationEntity();
		}
		return dataObj;
	}
	
	
	/**
	 * @description insert Configuration
	 * @author long.pham
	 * @since 2021-05-17
	 */
	
	public ConfigurationEntity insertConfiguration(ConfigurationEntity obj) 
	{
		SqlSession session = this.beginTransaction();
		try {
			List dataExpec = obj.getDataExpec();
			session.delete("Configuration.deleteConfigExpectation", obj);
			if(dataExpec.size() > 0) {
				session.insert("Configuration.insertConfiguration", obj);
			}
			
			session.commit();
			return obj;
		} catch (Exception ex) {
			session.rollback();
			log.error("Site.insertSite", ex);
			return null;
		} finally {
			session.close();
		}
			
	}
	
	/**
	 * @description insert Configuration
	 * @author long.pham
	 * @since 2021-05-17
	 */
	
	public ConfigurationEntity insertConfigurationIrradianceExpectation(ConfigurationEntity obj) 
	{
		SqlSession session = this.beginTransaction();
		try {
			List dataIrradianceExpec = obj.getDataIrradianceExpec();
			session.delete("Configuration.deleteConfigIrradianceExpectation", obj);
			if(dataIrradianceExpec.size() > 0) {
				session.insert("Configuration.insertIrradianceConfiguration", obj);
			}
			
			session.commit();
			return obj;
		} catch (Exception ex) {
			session.rollback();
			log.error("Site.insertSite", ex);
			return null;
		} finally {
			session.close();
		}
			
	}
	
	 /** @description delete config expectation
	 * @author long.pham
	 * @since 2021-11-24
	 * @param id
	 */
	public boolean deleteConfigExpectation(ConfigurationEntity obj) {
		try {
			return delete("Configuration.deleteConfigExpectation", obj) > 0;
		} catch (Exception ex) {
			log.error("Configuration.deleteConfigExpectation", ex);
			return false;
		}
	}
	
	/** @description delete config expectation
	 * @author long.pham
	 * @since 2021-11-24
	 * @param id
	 */
	public boolean deleteIrradianceConfigExpectation(ConfigurationEntity obj) {
		try {
			return delete("Configuration.deleteIrradianceConfigExpectation", obj) > 0;
		} catch (Exception ex) {
			log.error("Configuration.deleteIrradianceConfigExpectation", ex);
			return false;
		}
	}
	
	
	/** @description delete row config expectation
	 * @author long.pham
	 * @since 2021-11-24
	 * @param id
	 */
	public boolean deleteRowConfigExpectation(ConfigurationEntity obj) {
		try {
			return delete("Configuration.deleteRowConfigExpectation", obj) > 0;
		} catch (Exception ex) {
			log.error("Configuration.deleteRowConfigExpectation", ex);
			return false;
		}
	}
	
	
	
	
	/**
	 * @description get chart data energy
	 * @author long.pham
	 * @since 2020-12-04
	 * @param id_site, id_customer
	 */

	public List getDataRunReport(ConfigurationEntity obj) {
		List dataGenerated = new ArrayList<>();
		List dataListDeviceMeter = new ArrayList<>();
		List dataListDeviceIrr = new ArrayList<>();
		
		
		try {
			dataListDeviceMeter = queryForList("Configuration.getListDeviceTypeMeter", obj);
			if (dataListDeviceMeter.size() > 0) {
				obj.setDataDevices(dataListDeviceMeter);
				List dataEnergyDevice = queryForList("Configuration.getDataMeterRunReport", obj);
				Map<String, Object> deviceItem = new HashMap<String, Object>();
				if (dataEnergyDevice.size() > 0) {
					deviceItem.put("data_energy", dataEnergyDevice);
					deviceItem.put("type", "energy");
					if (dataListDeviceMeter.size() == 1) {
						deviceItem.put("devicename", "Energy output");
					}
					dataGenerated.add(deviceItem);
				}
			} else {
				List dataListDeviceInverter = queryForList("Configuration.getListDeviceTypeInverter", obj);
				if(dataListDeviceInverter.size() > 0 ) {
					obj.setDataDevices(dataListDeviceInverter);
					List dataEnergyDevice = queryForList("Configuration.getDataInverterRunReport", obj);
					Map<String, Object> deviceItem = new HashMap<String, Object>();
					if (dataEnergyDevice.size() > 0) {
						deviceItem.put("data_energy", dataEnergyDevice);
						deviceItem.put("type", "energy");
						if (dataListDeviceMeter.size() == 1) {
							deviceItem.put("devicename", "Energy output");
						}
						dataGenerated.add(deviceItem);
					}
				}
			}
			return dataGenerated;
		} catch (Exception ex) {
			return new ArrayList();
		}

	}
		
	/**
	 * @description update Configuration
	 * @author long.pham
	 * @since 2021-03-31
	 * @param id
	 */
	public boolean updateConfiguration(ConfigurationEntity obj){
		try{
			return update("Configuration.updateConfiguration", obj)>0;
		}catch (Exception ex) {
			log.error("Configuration.updateConfiguration", ex);
			return false;
		}
	}
	
	
	
	/**
	 * @description update row Configuration
	 * @author long.pham
	 * @since 2021-03-31
	 * @param id
	 */
	public boolean updateRowConfiguration(ConfigurationEntity obj){
		try{
			return update("Configuration.updateRowConfiguration", obj)>0;
		}catch (Exception ex) {
			log.error("Configuration.updateRowConfiguration", ex);
			return false;
		}
	}
	
	/**
	 * @description update row Configuration
	 * @author long.pham
	 * @since 2021-03-31
	 * @param id
	 */
	public boolean updateRowIrradianceConfiguration(ConfigurationEntity obj){
		try{
			return update("Configuration.updateRowIrradianceConfiguration", obj)>0;
		}catch (Exception ex) {
			log.error("Configuration.updateRowIrradianceConfiguration", ex);
			return false;
		}
	}
}
