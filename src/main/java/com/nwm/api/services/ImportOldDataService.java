/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ImportOldDataEntity;

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
			switch (obj.getTable_name()) {
			case "model_shark100":
				for (int i = 0; i < dataList.size(); i++) {
					session.insert("ModelShark100.insertModelShark100", dataList.get(i));
				}
				break;
			case "model_kippzonen_rt1_class8009":
				for (int i = 0; i < dataList.size(); i++) {
					session.insert("ModelKippZonenRT1Class8009.insertModelKippZonenRT1Class8009", dataList.get(i));
				}
				break;
			case "model_ivt_solaron_ext":
				for (int i = 0; i < dataList.size(); i++) {
					session.insert("ModelIVTSolaronEXT.insertModelIVTSolaronEXT", dataList.get(i));
				}
				break;
			case "model_pvp_inverter":
				for (int i = 0; i < dataList.size(); i++) {
					session.insert("ModelPVPInverter.insertModelPVPInverter", dataList.get(i));
				}
				break;
			case "model_imtsolar_class8000":
				for (int i = 0; i < dataList.size(); i++) {
					session.insert("ModelIMTSolarClass8000.insertModelIMTSolarClass8000", dataList.get(i));
				}
				break;
			case "model_advanced_energy_solaron":
				for (int i = 0; i < dataList.size(); i++) {
					session.insert("ModelAdvancedEnergySolaron.insertModelAdvancedEnergySolaron", dataList.get(i));
				}
				break;
			case "model_rt1_class30000":
				for (int i = 0; i < dataList.size(); i++) {
					session.insert("ModelRT1Class30000.insertModelRT1Class30000", dataList.get(i));
				}
				break;
			case "model_chint_solectria_inverter_class9725":
				for (int i = 0; i < dataList.size(); i++) {
					session.insert("ModelChintSolectriaInverterClass9725.insertModelChintSolectriaInverterClass9725", dataList.get(i));
				}
				break;
			case "model_veris_industries_e51c2_power_meter":
				for (int i = 0; i < dataList.size(); i++) {
					session.insert("ModelVerisIndustriesE51c2PowerMeter.insertModelVerisIndustriesE51c2PowerMeter", dataList.get(i));
				}
				break;
			case "model_satcon_pvs357_inverter":
				for (int i = 0; i < dataList.size(); i++) {
					session.insert("ModelSatconPvs357Inverter.insertModelSatconPvs357Inverter", dataList.get(i));
				}
				break;
			case "model_elkor_wattson_pv_meter":
				for (int i = 0; i < dataList.size(); i++) {
					session.insert("ModelElkorWattsonPVMeter.insertModelElkorWattsonPVMeter", dataList.get(i));
				}
				break;
			case "model_w_kipp_zonen_rt1":
				for (int i = 0; i < dataList.size(); i++) {
					session.insert("ModelWKippZonenRT1.insertModelWKippZonenRT1", dataList.get(i));
				}
				break;
			case "model_elkor_production_meter":
				for (int i = 0; i < dataList.size(); i++) {
					session.insert("ModelElkorProductionMeter.insertModelElkorProductionMeter", dataList.get(i));
				}
				break;
			case "model_abb_trio_class6210":
				for (int i = 0; i < dataList.size(); i++) {
					session.insert("ModelAbbTrioClass6210.insertModelAbbTrioClass6210", dataList.get(i));
				}
				break;
			case "model_lufft_class8020":
				for (int i = 0; i < dataList.size(); i++) {
					session.insert("ModelLufftClass8020.insertModelLufftClass8020", dataList.get(i));
				}
				break;
			case "model_pv_powered_35_50_260_500kw_inverter":
				for (int i = 0; i < dataList.size(); i++) {
					session.insert("ModelPVPowered3550260500kwInverter.insertModelPVPowered3550260KWInverter", dataList.get(i));
				}
				break;
			case "model_solectria_sgi_226ivt":
				for (int i = 0; i < dataList.size(); i++) {
					session.insert("ModelSolectriaSGI226IVT.insertModelSolectriaSGI226IVT", dataList.get(i));
				}
				break;
			case "model_tti_tracker":
				for (int i = 0; i < dataList.size(); i++) {
					session.insert("ModelTTiTracker.insertModelTTiTracker", dataList.get(i));
				}
				break;
			case "model_xantrex_gt100_250_500":
				for (int i = 0; i < dataList.size(); i++) {
					session.insert("ModelXantrexGT100250500.insertModelXantrexGT100250500", dataList.get(i));
				}
				break;
			case "model_imtsolar_tmodul_class8006":
				for (int i = 0; i < dataList.size(); i++) {
					session.insert("ModelIMTSolarTmodulClass8006.insertModelIMTSolarTmodulClass8006", dataList.get(i));
				}
				break;
			case "model_hukseflux_sr30d1_deviceclass_v0":
				for (int i = 0; i < dataList.size(); i++) {
					session.insert("ModelHukselfluxSr30d1DeviceclassV0.insertModelHukselfluxSr30d1DeviceclassV0", dataList.get(i));
				}
				break;
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
}
