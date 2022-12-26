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
import com.nwm.api.entities.RoleEntity;
import com.nwm.api.entities.RoleScreenMapEntity;
import com.nwm.api.entities.ScreenEntity;

public class RoleService extends DB {

	/**
	 * @description get list role
	 * @author long.pham
	 * @since 2020-12-30
	 */
	
	public List getList(RoleEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Role.getList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get total record role
	 * @author long.pham
	 * @since 2020-12-30
	 */
	public int getTotalRecord(RoleEntity obj) {
		try {
			return (int)queryForObject("Role.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/**
	 * @description update role status
	 * @author long.pham
	 * @since 2020-12-30
	 * @param id
	 */
	public boolean updateStatus(RoleEntity obj){
		try{
			return update("Role.updateStatus", obj)>0;
		}catch (Exception ex) {
			log.error("Role.updateStatus", ex);
			return false;
		}
	}
	
	/**
	 * @description insert role
	 * @author long.pham
	 * @since 2020-12-30
	 * @param id
	 */
	public RoleEntity insertRole(RoleEntity obj) 
	{
		try
	    {
	       Object insertId = insert("Role.insertRole", obj);
	       if(insertId != null && insertId instanceof Integer) {
	    	   return obj;
	       }else {
	    	   return null;
	       }
	    }
	    catch(Exception ex)
	    {
	        log.error("insert", ex);
	        return null;
	    }	
	}
	
	/**
	 * @description update role
	 * @author long.pham
	 * @since 2020-12-30
	 * @param id
	 */
	public boolean updateRole(RoleEntity obj){
		try{
			return update("Role.updateRole", obj)>0;
		}catch (Exception ex) {
			log.error("Role.updateRole", ex);
			return false;
		}
	}
	
	/**
	 * @description delete role
	 * @author long.pham
	 * @since 2020-12-30
	 * @param id
	 */
	public boolean deleteRole(RoleEntity obj){
		try{
			return update("Role.deleteRole", obj)>0;
		}catch (Exception ex) {
			log.error("Role.deleteRole", ex);
			return false;
		}
	}

	
	/**
	 * @description get list screen permission by role
	 * @author long.pham
	 * @since 2020-12-31
	 */
	
	public List getListScreenPermission(RoleEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Role.getListScreenPermission", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	/**
	 * @description get list screen permission by role
	 * @author long.pham
	 * @since 2020-12-31
	 */
	
	public int updateRolePermissions(RoleEntity dataE) {
		SqlSession session = this.beginTransaction();
		try {
			List screens = dataE.getScreens();
			if (screens.size() <= 0) {
				throw new Exception();
			}
			
			for (int i = 0; i < screens.size(); i++) {
				Map<String, Object> screen = (Map<String, Object>) screens.get(i);
				int idRole = (int) screen.get("id_role");
				int idScreen = (int) screen.get("id_screen");
				int auths = (int) screen.get("auths");
				
				RoleScreenMapEntity roleScreenMapItem = this._buildRoleScreenMapItem(idScreen, idRole, auths);
				int exist = (int) queryForObject("Role.checkRoleScreenMapExist", roleScreenMapItem);
				// if exist => update
				if(exist > 0 ) {
					session.update("Role.updateRoleScreenMap", roleScreenMapItem);
				} else {
					session.insert("Role.insertRoleScreenMap", roleScreenMapItem);
				}
			}
			
			session.commit();
			return 0;
		}catch (Exception e) {
			log.error(e);
			session.rollback();
			return 1;
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
	private RoleScreenMapEntity _buildRoleScreenMapItem(int idScreen, int roleId, int auths) {
		try {
			RoleScreenMapEntity roleScreenMapItem = new RoleScreenMapEntity();
			roleScreenMapItem.setId_screen(idScreen);
			roleScreenMapItem.setId_role(roleId);
			roleScreenMapItem.setAuths(auths);
			return roleScreenMapItem;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @description get all role
	 * @author long.pham
	 * @since 2021-01-06
	 */
	
	public List getAllRole(RoleEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Role.getAllRole", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	/**
	 * @description update role
	 * @author long.pham
	 * @since 2020-12-30
	 * @param id
	 */
	public int updateAllPermission(RoleEntity obj){
		SqlSession session = this.beginTransaction();
		try {
			// Get all role
			List dataListRole  = queryForList("Role.getAllRole", obj);
			if(dataListRole.size() > 0) {
				for (int i = 0; i < dataListRole.size(); i++) {
					RoleEntity item =  (RoleEntity)dataListRole.get(i);
					// Get list screen 
					if(item.getId() > 0) {
						List dataListScreen  = queryForList("Role.getAllScreen", obj);
						if(dataListScreen.size() > 0) {
							for (int j = 0; j < dataListScreen.size(); j++) {
								// Check role is map screen
								ScreenEntity screen =  (ScreenEntity)dataListScreen.get(j);
								int auth = 0;
								if(screen.getPath().equals("/management")) {
									auth = 1;
								}
								RoleScreenMapEntity screenMapItem = this._buildRoleScreenMapItem(screen.getId(), item.getId(), auth);
								int checkRoleScreenMapExist = (int) queryForObject("Role.checkRoleScreenMapExist", screenMapItem);
								if(checkRoleScreenMapExist <= 0 ) {
									// Insert role screen map
									session.insert("Role.insertRoleScreenMap", screenMapItem);
								}
							}
						} else {
							return 1;
						}
					} else {
						return 1;
					}
				}
			} else {
				return 1;
			}
			
			session.commit();
			return 0;
		}catch (Exception e) {
			log.error(e);
			session.rollback();
			return 1;
		} finally {
			session.close();
		}
	}
}
