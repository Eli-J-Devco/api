/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceZoneVirtualEntity;
import com.nwm.api.entities.EmployeeSiteMapEntity;
import com.nwm.api.entities.ZoneEntity;

public class ZoneService extends DB {

	/**
	 * @description get error level
	 * @author long.pham
	 * @since 2021-01-28
	 * @returns array
	 */
	
	public List getList(ZoneEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Zone.getListManage", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get list site by id customer
	 * @author long.pham
	 * @since 2021-02-25
	 * @param {}
	 */
	
	
	public List getListManage(ZoneEntity obj) {
		List dataList = new ArrayList();
		try {
			List data = queryForList("Zone.getListManage", obj);
			if (data == null)
				return new ArrayList();
			for(int i =0; i < data.size(); i ++) {
				ZoneEntity item = (ZoneEntity) data.get(i);
				List dataZoneVirtual = queryForList("Zone.getDataZoneVirtual", item);
				List zoneVirtualMap = new ArrayList();
				if(dataZoneVirtual.size() > 0) {
					for(int j = 0; j < dataZoneVirtual.size(); j++) {
						DeviceZoneVirtualEntity itemVirtual = (DeviceZoneVirtualEntity) dataZoneVirtual.get(j);
						List listZoneVirtualMap = queryForList("Zone.getDataZoneVirtualMap", itemVirtual);
						itemVirtual.setListDataMaps(listZoneVirtualMap);
						zoneVirtualMap.add(itemVirtual);
					}
				}
				
				item.setDataZoneVirtual(zoneVirtualMap);
				dataList.add(item);
			}
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	public int getTotalRecordManage(ZoneEntity obj) {
		try {
			return (int)queryForObject("Zone.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	
	/** @description delete breaker
	 * @author long.pham
	 * @since 2025-03-24
	 * @param id
	 */
	public boolean delete(ZoneEntity obj) {
		try {
			return delete("Zone.delete", obj) > 0;
		} catch (Exception ex) {
			log.error("Zone.delete", ex);
			return false;
		}
	}
	
	
	
	/**
	 * @description insert zone
	 * @author long.pham
	 * @since 2025-02-24
	 */
	public ZoneEntity insertZone(ZoneEntity obj) 
	{
		SqlSession session = this.beginTransaction();
		try {
			session.insert("Zone.insertZone", obj);
			List dataZoneVirtual = obj.getDataZoneVirtual();
			if (obj.getId() > 0 && dataZoneVirtual.size() > 0) {
				for(int i = 0; i < dataZoneVirtual.size(); i++) {
					Map<String, Object> item = (Map<String, Object>) dataZoneVirtual.get(i);
					String title0l = item.get("title0l").toString();
					String title0r = item.get("title0r").toString();
					String field_0L = item.get("field_0L").toString(); 
					String field_0R = item.get("field_0R").toString();
					String field_0L_verify = item.get("field_0L_verify").toString();
					String field_0R_verify = item.get("field_0R_verify").toString();
					DeviceZoneVirtualEntity zoneMaptItem = this._buildZoneVirtualMapItem(obj.getId(), title0l, title0r, field_0L, field_0R, field_0L_verify, field_0R_verify );
					session.insert("Zone.insertZoneVirtual", zoneMaptItem);
					item.put("id", zoneMaptItem.getId());
					session.insert("Zone.insertZoneVirtualMap", item);
				}
			} else {
				return null;
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
	 * build zone virtual item
	 * 
	 * @param productItem
	 * @param productId
	 * @param insertOrderLastId
	 * @return
	 */
	@SuppressWarnings("unused")
	private DeviceZoneVirtualEntity _buildZoneVirtualMapItem(int id_zone, String title0l, String title0r, String field_0L, String field_0R, String field_0L_verify, String field_0R_verify ) {
		try {
			DeviceZoneVirtualEntity item = new DeviceZoneVirtualEntity();
			item.setId_zone(id_zone);
			item.setTitle0l(title0l);
			item.setTitle0r(title0r);
			item.setField_0L(field_0L);
			item.setField_0R(field_0R);
			item.setField_0L_verify(field_0L_verify);
			item.setField_0R_verify(field_0R_verify);
			return item;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	/**
	 * @description update breaker
	 * @author long.pham
	 * @since 2025-03-24
	 * @param id
	 */
	public boolean updateZone(ZoneEntity obj){
		SqlSession session = this.beginTransaction();
		try {
			
			session.update("Zone.updateZone", obj);
			session.delete("Zone.deleteZoneVirtual", obj);
			List dataZoneVirtual = obj.getDataZoneVirtual();
			
			if (obj.getId() > 0 && dataZoneVirtual.size() > 0) {
				for(int i = 0; i < dataZoneVirtual.size(); i++) {
					Map<String, Object> item = (Map<String, Object>) dataZoneVirtual.get(i);
					String title0l = item.get("title0l").toString();
					String title0r = item.get("title0r").toString();
					String field_0L = item.get("field_0L").toString(); 
					String field_0R = item.get("field_0R").toString();
					String field_0L_verify = item.get("field_0L_verify").toString();
					String field_0R_verify = item.get("field_0R_verify").toString();
					DeviceZoneVirtualEntity zoneMaptItem = this._buildZoneVirtualMapItem(obj.getId(), title0l, title0r, field_0L, field_0R, field_0L_verify, field_0R_verify );
					session.insert("Zone.insertZoneVirtual", zoneMaptItem);
					item.put("id", zoneMaptItem.getId());
					session.insert("Zone.insertZoneVirtualMap", item);
				}
			} else {
				return false;
			}
			
			session.commit();
			return true;
		} catch (Exception ex) {
			session.rollback();
			log.error("Zone.updateZone", ex);
			return false;
		} finally {
			session.close();
		}
		
	}
	
	

}
