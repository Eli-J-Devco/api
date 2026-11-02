package com.nwm.api.services;

import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.TroubleshootingConfigEntity;

public class TroubleshootingConfigService extends DB {

	public enum MeterType {
		MAIN(1, "MainMeter"), PQ(2, "PqMeter");

		private final int id;
		private final String statement;

		MeterType(int id, String statement) {
			this.id = id;
			this.statement = statement;
		}
	}

	public TroubleshootingConfigEntity getBySite(int idSite, MeterType type) {
		try {
			return (TroubleshootingConfigEntity) queryForObject("TroubleshootingConfig.get" + type.statement, idSite);
		} catch (Exception ex) {
			log.error("TroubleshootingConfig.get" + type.statement, ex);
			throw new IllegalStateException("Unable to load meter configuration", ex);
		}
	}

	public boolean save(TroubleshootingConfigEntity config, MeterType type) {
		SqlSession session = beginTransaction();
		if (session == null) return false;
		try {
			// Serialize saves for the site, including creation of its first configuration.
			if (session.selectOne("TroubleshootingConfig.lockSite", config.getId_site()) == null) return false;
			Map<String, Object> header = new HashMap<>();
			header.put("id_site", config.getId_site());
			header.put("id_config_type", type.id);
			TroubleshootingConfigEntity active = session.selectOne("TroubleshootingConfig.getActiveConfig", header);
			if (active == null) {
				header.put("version", session.selectOne("TroubleshootingConfig.getNextVersion", header));
				session.insert("TroubleshootingConfig.insertConfig", header);
				config.setId_config(((Number) header.get("id_config")).intValue());
				config.setVersion(((Number) header.get("version")).intValue());
			} else {
				config.setId_config(active.getId_config());
				config.setVersion(active.getVersion());
			}
			Integer count = session.selectOne("TroubleshootingConfig.count" + type.statement, config.getId_config());
			if (count == null || count == 0) session.insert("TroubleshootingConfig.insert" + type.statement, config);
			else session.update("TroubleshootingConfig.update" + type.statement, config);
			session.commit();
			return true;
		} catch (Exception ex) {
			session.rollback();
			log.error("TroubleshootingConfig.save" + type.statement, ex);
			return false;
		} finally {
			session.close();
		}
	}
}
