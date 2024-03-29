/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.io.IOException;

import org.apache.ibatis.session.SqlSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ImportOldDataEntity;
import com.nwm.api.entities.SiteDataReportEntity;

public class ImportOldDataService extends DB {

	/**
	 * @description get all site by id_employee
	 * @author long.pham
	 * @since 2022-12-21
	 * @returns array
	 */
	
	public List getAllSiteByEmployeeId(ImportOldDataEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("ImportOldData.getAllSiteByEmployeeId", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get list device by id_site 
	 * @author long.pham
	 * @since 2022-12-21
	 * @param {}
	 */
	
	
	public List getAllDeviceBySiteId(ImportOldDataEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("ImportOldData.getAllDeviceBySiteId", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description insert old data
	 * @author long.pham
	 * @since 2022-12-26
	 */
	public ImportOldDataEntity insertImportOldData(ImportOldDataEntity obj) 
	{
		SqlSession session = this.beginTransaction();
		try {
			List dataList = obj.getDataList();
			if (dataList.size() <= 0) {
				throw new Exception();
			}
			
			List dataListInverter = queryForList("ImportOldData.getListDeviceInverterBySite", obj);
			List dataListMeter = queryForList("ImportOldData.getListDeviceMeterBySite", obj);
			List dataListWeather = queryForList("ImportOldData.getListDeviceWeather", obj);
			HashSet<String> set = new HashSet<String> ();
			
			switch (obj.getTable_name()) {
			case "model_shark100":
				obj.setId_device_type(3);
				for (int i = 0; i < dataList.size(); i++) {
					this.getTime(set, dataList.get(i));
					if(set.isEmpty()) {
						obj.setRow(i + 2);
						break;
					}
					session.insert("ModelShark100.insertModelShark100", dataList.get(i));					
				}				
				break;
			case "model_kippzonen_rt1_class8009":
				obj.setId_device_type(4);
				for (int i = 0; i < dataList.size(); i++) {
					this.getTime(set, dataList.get(i));
					if(set.isEmpty()) {
						obj.setRow(i + 2);
						break;
					}
					session.insert("ModelKippZonenRT1Class8009.insertModelKippZonenRT1Class8009", dataList.get(i));
				}
				break;
			case "model_ivt_solaron_ext":
				obj.setId_device_type(1);
				for (int i = 0; i < dataList.size(); i++) {
					this.getTime(set, dataList.get(i));
					if(set.isEmpty()) {
						obj.setRow(i + 2);
						break;
					}
					session.insert("ModelIVTSolaronEXT.insertModelIVTSolaronEXT", dataList.get(i));
				}
				break;
			case "model_pvp_inverter":
				obj.setId_device_type(1);
				for (int i = 0; i < dataList.size(); i++) {
					this.getTime(set, dataList.get(i));
					if(set.isEmpty()) {
						obj.setRow(i + 2);
						break;
					}
					session.insert("ModelPVPInverter.insertModelPVPInverter", dataList.get(i));
				}
				break;
			case "model_imtsolar_class8000":
				obj.setId_device_type(4);
				for (int i = 0; i < dataList.size(); i++) {
					this.getTime(set, dataList.get(i));
					if(set.isEmpty()) {
						obj.setRow(i + 2);
						break;
					}
					session.insert("ModelIMTSolarClass8000.insertModelIMTSolarClass8000", dataList.get(i));
				}
				break;
			case "model_advanced_energy_solaron":
				obj.setId_device_type(4);
				for (int i = 0; i < dataList.size(); i++) {
					this.getTime(set, dataList.get(i));
					if(set.isEmpty()) {
						obj.setRow(i + 2);
						break;
					}
					session.insert("ModelAdvancedEnergySolaron.insertModelAdvancedEnergySolaron", dataList.get(i));
				}
				break;
			case "model_rt1_class30000":
				obj.setId_device_type(4);
				for (int i = 0; i < dataList.size(); i++) {
					this.getTime(set, dataList.get(i));
					if(set.isEmpty()) {
						obj.setRow(i + 2);
						break;
					}
					session.insert("ModelRT1Class30000.insertModelRT1Class30000", dataList.get(i));
				}
				break;
			case "model_chint_solectria_inverter_class9725":
				obj.setId_device_type(1);
				for (int i = 0; i < dataList.size(); i++) {
					this.getTime(set, dataList.get(i));
					if(set.isEmpty()) {
						obj.setRow(i + 2);
						break;
					}
					session.insert("ModelChintSolectriaInverterClass9725.insertModelChintSolectriaInverterClass9725", dataList.get(i));
				}
				break;
			case "model_veris_industries_e51c2_power_meter":
				obj.setId_device_type(3);
				for (int i = 0; i < dataList.size(); i++) {
					this.getTime(set, dataList.get(i));
					if(set.isEmpty()) {
						obj.setRow(i + 2);
						break;
					}
					session.insert("ModelVerisIndustriesE51c2PowerMeter.insertModelVerisIndustriesE51c2PowerMeter", dataList.get(i));
				}
				break;
			case "model_satcon_pvs357_inverter":
				obj.setId_device_type(1);
				for (int i = 0; i < dataList.size(); i++) {
					this.getTime(set, dataList.get(i));
					if(set.isEmpty()) {
						obj.setRow(i + 2);
						break;
					}
					session.insert("ModelSatconPvs357Inverter.insertModelSatconPvs357Inverter", dataList.get(i));
				}
				break;
			case "model_elkor_wattson_pv_meter":
				obj.setId_device_type(3);
				for (int i = 0; i < dataList.size(); i++) {
					this.getTime(set, dataList.get(i));
					if(set.isEmpty()) {
						obj.setRow(i + 2);
						break;
					}
					session.insert("ModelElkorWattsonPVMeter.insertModelElkorWattsonPVMeter", dataList.get(i));
				}
				break;
			case "model_w_kipp_zonen_rt1":
				obj.setId_device_type(4);
				for (int i = 0; i < dataList.size(); i++) {
					this.getTime(set, dataList.get(i));
					session.insert("ModelWKippZonenRT1.insertModelWKippZonenRT1", dataList.get(i));
				}
				break;
			case "model_elkor_production_meter":
				obj.setId_device_type(3);
				for (int i = 0; i < dataList.size(); i++) {
					this.getTime(set, dataList.get(i));
					if(set.isEmpty()) {
						obj.setRow(i + 2);
						break;
					}
					session.insert("ModelElkorProductionMeter.insertModelElkorProductionMeter", dataList.get(i));
				}
				break;
			case "model_abb_trio_class6210":
				obj.setId_device_type(1);
				for (int i = 0; i < dataList.size(); i++) {
					this.getTime(set, dataList.get(i));
					if(set.isEmpty()) {
						obj.setRow(i + 2);
						break;
					}
					session.insert("ModelAbbTrioClass6210.insertModelAbbTrioClass6210", dataList.get(i));
				}
				break;
			case "model_lufft_class8020":
				obj.setId_device_type(4);
				for (int i = 0; i < dataList.size(); i++) {
					this.getTime(set, dataList.get(i));
					if(set.isEmpty()) {
						obj.setRow(i + 2);
						break;
					}
					session.insert("ModelLufftClass8020.insertModelLufftClass8020", dataList.get(i));
				}
				break;
			case "model_pv_powered_35_50_260_500kw_inverter":
				obj.setId_device_type(1);
				for (int i = 0; i < dataList.size(); i++) {
					this.getTime(set, dataList.get(i));
					if(set.isEmpty()) {
						obj.setRow(i + 2);
						break;
					}
					session.insert("ModelPVPowered3550260500kwInverter.insertModelPVPowered3550260KWInverter", dataList.get(i));
				}
				break;
			case "model_solectria_sgi_226ivt":
				obj.setId_device_type(1);
				for (int i = 0; i < dataList.size(); i++) {
					this.getTime(set, dataList.get(i));
					if(set.isEmpty()) {
						obj.setRow(i + 2);
						break;
					}
					session.insert("ModelSolectriaSGI226IVT.insertModelSolectriaSGI226IVT", dataList.get(i));
				}
				break;
			case "model_tti_tracker":
				obj.setId_device_type(2);
				for (int i = 0; i < dataList.size(); i++) {
					this.getTime(set, dataList.get(i));
					if(set.isEmpty()) {
						obj.setRow(i + 2);
						break;
					}
					session.insert("ModelTTiTracker.insertModelTTiTracker", dataList.get(i));
				}
				break;
			case "model_xantrex_gt100_250_500":
				obj.setId_device_type(1);
				for (int i = 0; i < dataList.size(); i++) {
					this.getTime(set, dataList.get(i));
					if(set.isEmpty()) {
						obj.setRow(i + 2);
						break;
					}
					session.insert("ModelXantrexGT100250500.insertModelXantrexGT100250500", dataList.get(i));
				}
				break;
			case "model_imtsolar_tmodul_class8006":
				obj.setId_device_type(4);
				for (int i = 0; i < dataList.size(); i++) {
					this.getTime(set, dataList.get(i));
					if(set.isEmpty()) {
						obj.setRow(i + 2);
						break;
					}
					session.insert("ModelIMTSolarTmodulClass8006.insertModelIMTSolarTmodulClass8006", dataList.get(i));
				}
				break;
			case "model_hukseflux_sr30d1_deviceclass_v0":
				obj.setId_device_type(4);
				for (int i = 0; i < dataList.size(); i++) {
					this.getTime(set, dataList.get(i));
					if(set.isEmpty()) {
						obj.setRow(i + 2);
						break;
					}
					session.insert("ModelHukselfluxSr30d1DeviceclassV0.insertModelHukselfluxSr30d1DeviceclassV0", dataList.get(i));
				}
				break;
			}

			session.commit();
			for (String s : set) {				
				String[] year = s.split("-");
				obj.setYear(year[0]);
				
				obj.setStart_date(s + " 08:00:00");
				obj.setEnd_date(s + " 17:59:59");
				obj.setDatatablename(obj.getTable_name());
				this.insertSiteDataReport(obj, dataListInverter, dataListMeter, dataListWeather);					
			}
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
	 * @description get date to insert for site_data_report
	 * @author duy.phan
	 * @since 2023-02-14
	 */
	public HashSet<String> getTime (HashSet<String> set, Object dataList) throws IOException {
		try {
			ObjectMapper oMapper = new ObjectMapper();
			Map<String, String> map = oMapper.convertValue(dataList, Map.class);			
			
			String time =  map.get("time");

	    	if (time == null || !time.matches("((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) ([2][0-3]|[0-1][0-9]|[1-9]):[0-5][0-9]:([0-5][0-9]|[6][0])$")) {
	    		set.clear();
	    		return null;
	    	}

			String[] parts = time.split(" ");
			String start = parts[0];
			set.add(start);
			return set;
		} catch (Exception ex) {			
			log.error("Error.dateFormat", ex);
			return null;
		}
	}
	
	/**
	 * @description insert data to site_data_report
	 * @author duy.phan
	 * @since 2023-02-14
	 */
	public void insertSiteDataReport(ImportOldDataEntity obj, List dataListInverter, List dataListMeter, List dataListWeather) {
		try {
			// Case 1: inverter, meter, weather
			if (dataListInverter.size() > 0 && dataListMeter.size() > 0 && dataListWeather.size() > 0) {
				switch (obj.getId_device_type()) {
					case 1:
						SiteDataReportEntity dataReportInverter = new SiteDataReportEntity();
						dataReportInverter = (SiteDataReportEntity) queryForObject("BatchJob.getSiteDataReportIIMW", obj);
						if (dataReportInverter != null && dataReportInverter.getId_device() > 0) {
							insert("BatchJob.insertSiteDataReport", dataReportInverter);
						}
						break;
					case 3:
						SiteDataReportEntity dataReportMeter = new SiteDataReportEntity();
						dataReportMeter = (SiteDataReportEntity) queryForObject("BatchJob.getSiteDataReportMIMW", obj);
						if (dataReportMeter != null && dataReportMeter.getId_device() > 0) {
							insert("BatchJob.insertSiteDataReport", dataReportMeter);
						}
						break;
					case 4:
						SiteDataReportEntity dataReportWeather = new SiteDataReportEntity();
						dataReportWeather = (SiteDataReportEntity) queryForObject("BatchJob.getSiteDataReportWeather", obj);
						if (dataReportWeather != null && dataReportWeather.getId_device() > 0) {
							insert("BatchJob.insertSiteDataReport", dataReportWeather);
						}
						break;
				}
			}
			
			// Case 2: inverter, meter
			else if(dataListInverter.size() > 0 && dataListMeter.size() > 0 && dataListWeather.size() <= 0) {
				switch (obj.getId_device_type()) {
					case 1:
						SiteDataReportEntity dataReportInverter = new SiteDataReportEntity();
						dataReportInverter = (SiteDataReportEntity) queryForObject("BatchJob.getSiteDataReportIIM", obj);
						if (dataReportInverter != null && dataReportInverter.getId_device() > 0) {
							insert("BatchJob.insertSiteDataReport", dataReportInverter);
						}
						break;
					case 3:
						SiteDataReportEntity dataReportMeter = new SiteDataReportEntity();
						dataReportMeter = (SiteDataReportEntity) queryForObject("BatchJob.getSiteDataReportMIM", obj);
						if (dataReportMeter != null && dataReportMeter.getId_device() > 0) {
							insert("BatchJob.insertSiteDataReport", dataReportMeter);
						}
						break;						
				}
			}
			
			// Case 3: inverter, weather
			else if(dataListInverter.size() > 0  && dataListMeter.size() <= 0 && dataListWeather.size() > 0 ) {
				switch (obj.getId_device_type()) {
					case 1:
						SiteDataReportEntity dataReportInverter = new SiteDataReportEntity();
						dataReportInverter = (SiteDataReportEntity) queryForObject("BatchJob.getSiteDataReportIIMW", obj);
						if (dataReportInverter != null && dataReportInverter.getId_device() > 0) {
							insert("BatchJob.insertSiteDataReport", dataReportInverter);
						}
						break;
					case 4:
						SiteDataReportEntity dataReportWeather = new SiteDataReportEntity();
						dataReportWeather = (SiteDataReportEntity) queryForObject("BatchJob.getSiteDataReportWeather", obj);
						if (dataReportWeather != null && dataReportWeather.getId_device() > 0) {
							insert("BatchJob.insertSiteDataReport", dataReportWeather);
						}
						break;
				}
			}
			
			// Case 4: meter, weather
			else if(dataListInverter.size() <= 0 && dataListMeter.size() > 0 && dataListWeather.size() > 0 ) {
				switch (obj.getId_device_type()) {
					case 3:
						SiteDataReportEntity dataReportMeter = new SiteDataReportEntity();
						dataReportMeter = (SiteDataReportEntity) queryForObject("BatchJob.getSiteDataReportMM", obj);
						if (dataReportMeter != null && dataReportMeter.getId_device() > 0) {
							insert("BatchJob.insertSiteDataReport", dataReportMeter);
						}
						break;
					case 4:
						SiteDataReportEntity dataReportWeather = new SiteDataReportEntity();
						dataReportWeather = (SiteDataReportEntity) queryForObject("BatchJob.getSiteDataReportWeather", obj);
						if (dataReportWeather != null && dataReportWeather.getId_device() > 0) {
							insert("BatchJob.insertSiteDataReport", dataReportWeather);
						}
						break;
				}
			}
			
			// Case 5: meter
			else if(dataListInverter.size() <= 0 && dataListMeter.size() > 0 && dataListWeather.size() <= 0 ) {
				if (obj.getId_device() == 3) {
					SiteDataReportEntity dataReportMeter = new SiteDataReportEntity();
					dataReportMeter = (SiteDataReportEntity) queryForObject("BatchJob.getSiteDataReportMM", obj);
					if (dataReportMeter != null && dataReportMeter.getId_device() > 0) {
						insert("BatchJob.insertSiteDataReport", dataReportMeter);
					}
				}					
			}
			
			// Case 6: inverter
			else if(dataListInverter.size() > 0 && dataListMeter.size() <= 0 && dataListWeather.size() <= 0 ) {
				if (obj.getId_device() == 1) {
					SiteDataReportEntity dataReportInverter = new SiteDataReportEntity();
					dataReportInverter = (SiteDataReportEntity) queryForObject("BatchJob.getSiteDataReportIIW", obj);
					if (dataReportInverter != null && dataReportInverter.getId_device() > 0) {
						insert("BatchJob.insertSiteDataReport", dataReportInverter);
					}
				}
			}
		} catch(Exception ex)
	    {
	        log.error("insertDataGenerateReport", ex);
	    }		
	}
}
