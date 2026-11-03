package com.nwm.api.services;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.apache.ibatis.session.SqlSession;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.TroubleshootingConfigEntity;
import com.nwm.api.entities.TroubleshootingConfigEntity.SubMeterConfig;
import com.nwm.api.entities.TroubleshootingConfigEntity.Panel;
import com.nwm.api.entities.TroubleshootingConfigEntity.Submeter;
import com.nwm.api.entities.TroubleshootingConfigEntity.MeterGroup;

public class TroubleshootingConfigService extends DB {

	public enum MeterType {
		MAIN("MainMeter"), PQ("PqMeter"), SUB("SubMeter");

		private final String statement;

		MeterType(String statement) {
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
		if (config == null || config.getId_site() <= 0) return false;
		SqlSession session = beginTransaction();
		if (session == null) return false;
		try {
			// Serialize saves for the site, including creation of its first configuration.
			if (session.selectOne("TroubleshootingConfig.lockSite", config.getId_site()) == null) return false;
			Map<String, Object> header = new HashMap<>();
			header.put("id_site", config.getId_site());
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
			if (type == MeterType.SUB) savePanels(session, (SubMeterConfig) config);
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

	private void savePanels(SqlSession session, SubMeterConfig config) {
		if (config.getPanels() == null) throw new IllegalArgumentException("Panels are required");
		List<Panel> existing = session.selectList("TroubleshootingConfig.getPanels", config.getId_config());
		Set<Integer> panelOrders = new HashSet<>();
		for (Panel panel : config.getPanels()) {
			if (panel == null || panel.getId() < 0 || panel.getSubmeters() == null || panel.getGroups() == null
					|| panel.getSort_order() < 0 || panel.getSort_order() > 1
					|| !panelOrders.add(panel.getSort_order()))
				throw new IllegalArgumentException("Invalid panel");
			Panel previous = existing.stream().filter(row -> row.getId() == panel.getId()).findFirst().orElse(null);
			if (panel.getId() > 0 && (previous == null || previous.getSort_order() != panel.getSort_order()))
				throw new IllegalArgumentException("Panel does not belong to this configuration");
			if (panel.getId() == 0) {
				if (existing.stream().anyMatch(row -> row.getSort_order() == panel.getSort_order()))
					throw new IllegalArgumentException("Panel already exists; reload the configuration");
				panel.setId_config(config.getId_config());
				session.insert("TroubleshootingConfig.insertPanel", panel);
			}
			final Panel oldPanel = previous == null ? new Panel() : previous;
			Map<Integer, Integer> savedGroupIds = new HashMap<>();
			int groupOrder = 0;
			for (MeterGroup group : panel.getGroups()) {
				if (group == null || group.getId() == 0 || savedGroupIds.containsKey(group.getId()))
					throw new IllegalArgumentException("Invalid meter group");
				int clientId = group.getId();
				if (clientId > 0 && oldPanel.getGroups().stream().noneMatch(row -> row.getId() == clientId))
					throw new IllegalArgumentException("Group must belong to its panel");
				group.setId_panel(panel.getId());
				group.setSort_order(groupOrder++);
				if (clientId > 0) session.update("TroubleshootingConfig.updateMeterGroup", group);
				else session.insert("TroubleshootingConfig.insertMeterGroup", group);
				savedGroupIds.put(clientId, group.getId());
			}

			Set<Integer> clientSubmeterIds = new HashSet<>();
			List<Integer> savedSubmeterIds = new ArrayList<>();
			for (Submeter meter : panel.getSubmeters()) {
				if (meter == null || meter.getId() == 0 || !clientSubmeterIds.add(meter.getId()))
					throw new IllegalArgumentException("Invalid submeter");
				int clientId = meter.getId();
				if (clientId > 0 && oldPanel.getSubmeters().stream().noneMatch(row -> row.getId() == clientId))
					throw new IllegalArgumentException("Submeter must belong to its panel");
				if (meter.getId_group() != null) {
					Integer savedGroupId = savedGroupIds.get(meter.getId_group());
					if (savedGroupId == null) throw new IllegalArgumentException("Group must belong to the submeter panel");
					meter.setId_group(savedGroupId);
				}
				if (meter.getId_device() != null) {
					Map<String, Object> device = new HashMap<>();
					device.put("id_site", config.getId_site());
					device.put("id_device", meter.getId_device());
					Integer count = session.selectOne("TroubleshootingConfig.countDevice", device);
					if (count == null || count != 1) throw new IllegalArgumentException("Device must belong to the site");
				}
				meter.setId_panel(panel.getId());
				if (clientId > 0) session.update("TroubleshootingConfig.updateSubmeter", meter);
				else session.insert("TroubleshootingConfig.insertSubmeter", meter);
				savedSubmeterIds.add(meter.getId());
			}
			Map<String, Object> keys = new HashMap<>();
			keys.put("id_panel", panel.getId());
			keys.put("ids", savedSubmeterIds);
			session.delete("TroubleshootingConfig.deleteSubmetersByPanel", keys);
			keys.put("ids", new ArrayList<>(savedGroupIds.values()));
			session.delete("TroubleshootingConfig.deleteGroupsByPanel", keys);
		}
	}

}
