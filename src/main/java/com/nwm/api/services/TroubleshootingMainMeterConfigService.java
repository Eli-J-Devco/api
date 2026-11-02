package com.nwm.api.services;

import org.apache.ibatis.session.SqlSession;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.TroubleshootingMainMeterConfigEntity;

public class TroubleshootingMainMeterConfigService extends DB {
	public TroubleshootingMainMeterConfigEntity getBySite(int idSite) {
		try {
			return (TroubleshootingMainMeterConfigEntity) queryForObject(
					"TroubleshootingMainMeterConfig.getBySite", idSite);
		} catch (Exception ex) {
			log.error("TroubleshootingMainMeterConfig.getBySite", ex);
			return null;
		}
	}

	public boolean save(TroubleshootingMainMeterConfigEntity config) {
		SqlSession session = beginTransaction();
		if (session == null) return false;
		try {
			Integer configId = (Integer) session.selectOne(
					"TroubleshootingMainMeterConfig.getActiveConfigId", config.getId_site());
			if (configId == null) {
				Integer version = (Integer) session.selectOne(
						"TroubleshootingMainMeterConfig.getNextVersion", config.getId_site());
				config.setVersion(version == null ? 1 : version);
				session.insert("TroubleshootingMainMeterConfig.insertConfig", config);
				configId = config.getId_config();
			} else {
				config.setId_config(configId);
			}

			Integer count = (Integer) session.selectOne(
					"TroubleshootingMainMeterConfig.countByConfig", configId);
			if (count == null || count == 0) {
				session.insert("TroubleshootingMainMeterConfig.insert", config);
			} else {
				session.update("TroubleshootingMainMeterConfig.update", config);
			}
			session.commit();
			return true;
		} catch (Exception ex) {
			session.rollback();
			log.error("TroubleshootingMainMeterConfig.save", ex);
			return false;
		} finally {
			session.close();
		}
	}
}
