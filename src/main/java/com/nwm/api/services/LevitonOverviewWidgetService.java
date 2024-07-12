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
import com.nwm.api.entities.EmployeeSiteMapEntity;
import com.nwm.api.entities.LevitonOverviewWidgetEntity;
import com.nwm.api.entities.LevitonOverviewWidgetMapEntity;
import com.nwm.api.entities.RoleScreenMapEntity;
import com.nwm.api.entities.ScreenEntity;

public class LevitonOverviewWidgetService extends DB {

	/**
	 * @description get list icon
	 * @author long.pham
	 * @since 2021-03-31
	 * @param {}
	 */

	public List getList(LevitonOverviewWidgetEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("LevitonOverviewWidget.getList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	public int getTotalRecord(LevitonOverviewWidgetEntity obj) {
		try {
			return (int)queryForObject("LevitonOverviewWidget.getTotalRecord", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/** @description delete icon
	 * @author long.pham
	 * @since 2021-03-31
	 * @param id
	 */
	public boolean delete(LevitonOverviewWidgetEntity obj) {
		try {
			return delete("LevitonOverviewWidget.delete", obj) > 0;
		} catch (Exception ex) {
			log.error("LevitonOverviewWidget.delete", ex);
			return false;
		}
	}
	
	
	
	/**
	 * @description insert icon
	 * @author long.pham
	 * @since 2021-03-31
	 */
	public LevitonOverviewWidgetEntity insertIcon(LevitonOverviewWidgetEntity obj) 
	{
		SqlSession session = this.beginTransaction();
		try {
			List deviceMap = obj.getDeviceMap();
			if (deviceMap.size() <= 0) {
				throw new Exception();
			}

			if(obj.getIs_consumption() == 1) {
				session.update("LevitonOverviewWidget.updateIsConsumption", obj);
			}
			session.insert("LevitonOverviewWidget.insertIcon", obj);
			int insertLastId = obj.getId();

			if (insertLastId > 0) {
				for (int i = 0; i < deviceMap.size(); i++) {
					Map<String, Object> item = (Map<String, Object>) deviceMap.get(i);
					int id_device = (int) item.get("id_device");
					
					LevitonOverviewWidgetMapEntity mapItem = this._buildLevitonOverviewWidgetMap(id_device, insertLastId);
					session.insert("LevitonOverviewWidget.insertLevitonOverviewWidgetMap", mapItem);
				}
			} else {
				return null;
			}

			session.commit();
			return obj;
		} catch (Exception ex) {
			session.rollback();
			return null;
		} finally {
			session.close();
		}
	}
	
	
	/**
	 * build order product item
	 * 
	 * @param productItem
	 * @param productId
	 * @param insertOrderLastId
	 * @return
	 */
	private LevitonOverviewWidgetMapEntity _buildLevitonOverviewWidgetMap(int idDevice, int idWidget) {
		try {
			LevitonOverviewWidgetMapEntity item = new LevitonOverviewWidgetMapEntity();
			item.setId_device(idDevice);
			item.setId_leviton_overview_widget(idWidget);
			return item;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @description update icon
	 * @author long.pham
	 * @since 2021-03-31
	 * @param id
	 */
	public boolean updateIcon(LevitonOverviewWidgetEntity obj){
		SqlSession session = this.beginTransaction();
		try {
			List deviceMap = obj.getDeviceMap();
			if (deviceMap.size() <= 0) {
				throw new Exception();
			}
			
			if(obj.getIs_consumption() == 1) {
				session.update("LevitonOverviewWidget.updateIsConsumption", obj);
			}
			session.update("LevitonOverviewWidget.updateIcon", obj);
			session.delete("LevitonOverviewWidget.deleteLevitonOverviewWidgetMap", obj);

			for (int i = 0; i < deviceMap.size(); i++) {
				Map<String, Object> item = (Map<String, Object>) deviceMap.get(i);
				int id_device = (int) item.get("id_device");
				
				LevitonOverviewWidgetMapEntity mapItem = this._buildLevitonOverviewWidgetMap(id_device, obj.getId());
				session.insert("LevitonOverviewWidget.insertLevitonOverviewWidgetMap", mapItem);
			}

			session.commit();
			return true;
		} catch (Exception ex) {
			session.rollback();
			return false;
		} finally {
			session.close();
		}
	}
	
	
	/**
	 * @description update icon status
	 * @author long.pham
	 * @since 2021-03-31
	 * @param id
	 */
	public boolean updateStatus(LevitonOverviewWidgetEntity obj){
		try{
			return update("LevitonOverviewWidget.updateStatus", obj)>0;
		}catch (Exception ex) {
			log.error("LevitonOverviewWidget.updateStatus", ex);
			return false;
		}
	}

}
