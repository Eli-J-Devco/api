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
import com.nwm.api.entities.WidgetGroupEntity;
import com.nwm.api.entities.WidgetGroupParameterEntity;
import com.nwm.api.utils.Lib;

public class WidgetGroupService extends DB {
	
	
	/**
	 * @description get widget group
	 * @author long.pham
	 * @since 2021-01-28
	 * @returns array
	 */
	
	public List getAllDeviceBySite(WidgetGroupEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("WidgetGroup.getAllDeviceBySite", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get widget group
	 * @author long.pham
	 * @since 2021-01-28
	 * @returns array
	 */
	
	public List getListFieldBySite(WidgetGroupEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("WidgetGroup.getListFieldBySite", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	/**
	 * @description get widget group
	 * @author long.pham
	 * @since 2021-01-28
	 * @returns array
	 */
	
	public List getListDeviceGroupBySite(WidgetGroupEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("WidgetGroup.getListDeviceGroupBySite", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}

	/**
	 * @description get widget group
	 * @author long.pham
	 * @since 2021-01-28
	 * @returns array
	 */
	
	public List getList(WidgetGroupEntity obj) {
		List dataList = new ArrayList();
		List newDataList = new ArrayList<>();
		try {
			dataList = queryForList("WidgetGroup.getList", obj);
			
			if(dataList.size() > 0) {
				for(int i = 0; i < dataList.size(); i++) {
					Map<String, Object> dataItem = (Map<String, Object>) dataList.get(i);
					List subGroupList = queryForList("WidgetGroup.getSubGroup", dataItem);
					
					dataItem.put("dataFields", subGroupList);
					newDataList.add(dataItem);
				}
				
			}
			
			
			if (newDataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return newDataList;
	}
	
	
	/**
	 * @description get widget group
	 * @author long.pham
	 * @since 2021-01-28
	 * @returns array
	 */
	
	public List getListRoot(WidgetGroupEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("WidgetGroup.getListRoot", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}

	
	/** @description delete widget group
	 * @author long.pham
	 * @since 2024-03-25
	 * @param id
	 */
	public boolean delete(WidgetGroupEntity obj) {
		try {
			return delete("WidgetGroup.delete", obj) > 0;
		} catch (Exception ex) {
			log.error("WidgetGroup.delete", ex);
			return false;
		}
	}
	
	
	/**
	 * @description insert widget group
	 * @author long.pham
	 * @since 2024-03-25
	 */
	public WidgetGroupEntity insertWidgetGroup(WidgetGroupEntity obj) 
	{
		SqlSession session = this.beginTransaction();
		try {

			session.insert("WidgetGroup.insertWidgetGroup", obj);
			int insertLastId = obj.getId();

			if (insertLastId > 0) {
				List dataFields = obj.getDataFields();
				if(dataFields.size() > 0) {
					for (int i = 0; i < dataFields.size(); i++) {
						Map<String, Object> item = (Map<String, Object>) dataFields.get(i);
						WidgetGroupParameterEntity itemWP = new WidgetGroupParameterEntity();
						itemWP.setId_widget_group(insertLastId);
						itemWP.setId_group_parameter(Integer.parseInt(item.get("id_group_parameter").toString()));
						itemWP.setMenu_order(!Lib.isBlank(item.get("menu_order")) ? Integer.parseInt(item.get("menu_order").toString()) : 0);
						itemWP.setUnit(item.get("unit").toString());
						itemWP.setName(item.get("name").toString());
						itemWP.setId_device(item.get("id_device").toString());
						itemWP.setBg_color(!Lib.isBlank(item.get("bg_color")) ? item.get("bg_color").toString(): null);
						itemWP.setFormula(!Lib.isBlank(item.get("formula")) ? Integer.parseInt(item.get("formula").toString()) : 1);
						itemWP.setType(!Lib.isBlank(item.get("type")) ? Integer.parseInt(item.get("type").toString()) : 1);
						session.insert("WidgetGroup.insertWidgetGroupParameter", itemWP);
					}
				}
			} else {
				return null;
			}

			session.commit();
			return obj;
		} catch (Exception ex) {
			session.rollback();
			log.error("WidgetGroup.insertWidgetGroup", ex);
			return null;
		} finally {
			session.close();
		}
	}
	
	
	/**
	 * @description update widget group
	 * @author long.pham
	 * @since 2024-03-25
	 * @param id
	 */
	public boolean updateWidgetGroup(WidgetGroupEntity obj){
		SqlSession session = this.beginTransaction();
		try {
			session.delete("WidgetGroup.deleteWidgetGroupParameter", obj);
			session.update("WidgetGroup.updateWidgetGroup", obj);
			
			List dataFields = obj.getDataFields();
			if(dataFields.size() > 0) {
				for (int i = 0; i < dataFields.size(); i++) {
					Map<String, Object> item = (Map<String, Object>) dataFields.get(i);
					WidgetGroupParameterEntity itemWP = new WidgetGroupParameterEntity();
					itemWP.setId_widget_group(obj.getId());
					itemWP.setId_group_parameter(Integer.parseInt(item.get("id_group_parameter").toString()));
					itemWP.setMenu_order(item.get("menu_order") != null ? Integer.parseInt(item.get("menu_order").toString()) : 0);
					itemWP.setUnit(item.get("unit").toString());
					itemWP.setName(item.get("name").toString());
					itemWP.setBg_color(!Lib.isBlank(item.get("bg_color")) ? item.get("bg_color").toString(): null);
					itemWP.setFormula(item.get("formula") != null ? Integer.parseInt(item.get("formula").toString()) : 1);
					itemWP.setType(item.get("type") != null ? Integer.parseInt(item.get("type").toString()) : 1);
					itemWP.setId_device(item.get("id_device").toString());
					
					session.insert("WidgetGroup.insertWidgetGroupParameter", itemWP);
				}
			}

			session.commit();
			return true;
		} catch (Exception ex) {
			session.rollback();
			log.error("Site.updateSite", ex);
			return false;
		} finally {
			session.close();
		}
		
		
//		try{
//			return update("WidgetGroup.updateWidgetGroup", obj)>0;
//		}catch (Exception ex) {
//			log.error("Site.updateSite", ex);
//			return false;
//		}
	}
	
	

}
