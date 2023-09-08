/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.GroupEntity;

public class GroupService extends DB {

	/**
	 * @description get list dropdown group
	 * @author long.pham
	 * @since 2022-12-16
	 * @returns array
	 */
	
	public List getDropdownList(GroupEntity obj) {
		List dataList = new ArrayList();
		List newDataList = new ArrayList<>();
		try {
			dataList = queryForList("Group.getDropdownList", obj);
			if (dataList == null)
				return new ArrayList();
			
			for (int i = 0; i < dataList.size(); i++) {
				GroupEntity dataItem = (GroupEntity) dataList.get(i);
				List subGroupList = queryForList("Group.getSubGroupByGroup", dataItem);
				dataItem.setSub_group_list(subGroupList);
				newDataList.add(dataItem);
			}
		} catch (Exception ex) {
			return new ArrayList();
		}
		return newDataList;
	}
	
	/**
	 * @description get list group 
	 * @author long.pham
	 * @since 2022-12-16
	 * @param {}
	 */
	
	
	public List getList(GroupEntity obj) {
		List dataList = new ArrayList();
		List newDataList = new ArrayList<>();
		try {
			dataList = queryForList("Group.getList", obj);
			if (dataList == null)
				return new ArrayList();
			
			for (int i = 0; i < dataList.size(); i++) {
				Map<String, Object> dataItem = (Map<String, Object>) dataList.get(i);
				List subGroupList = queryForList("Group.getSubGroupByGroup", dataItem);
				dataItem.put("sub_group_list", subGroupList);
				newDataList.add(dataItem);
			}
		} catch (Exception ex) {
			return new ArrayList();
		}
		return newDataList;
	}
	
	public int getTotalRecord(GroupEntity obj) {
		try {
			return (int)queryForObject("Group.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	public int getTotalSiteById(GroupEntity obj) {
		try {
			return (int)queryForObject("Group.getTotalSiteById", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	public int getTotalSiteInSubGroupById(GroupEntity obj) {
		try {
			return (int)queryForObject("Group.getTotalSiteInSubGroupById", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	
	public int getExitsCount(GroupEntity obj) {
		try {
			return (int)queryForObject("Group.getExitsCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	
	/** @description delete group
	 * @author long.pham
	 * @since 2022-12-16
	 * @param id
	 */
	public boolean delete(GroupEntity obj) {
		try {
			return delete("Group.delete", obj) > 0;
		} catch (Exception ex) {
			log.error("Group.delete", ex);
			return false;
		}
	}
	
	/** @description delete sub-group
	 * @author long.pham
	 * @since 2022-12-16
	 * @param id
	 */
	public boolean deleteSubGroup(GroupEntity obj) {
		try {
			return delete("Group.deleteSubGroup", obj) > 0;
		} catch (Exception ex) {
			log.error("Group.deleteSubGroup", ex);
			return false;
		}
	}
	
	
	/**
	 * @description insert group
	 * @author long.pham
	 * @since 2022-12-16
	 */
	public GroupEntity insertGroup(GroupEntity obj) 
	{
		try
	    {
	       Object insertId = insert("Group.insertGroup", obj);
	       if(insertId != null && insertId instanceof Integer) {
	    	   return obj;
	       }else {
	    	   return null;
	       }
	    }
	    catch(Exception ex)
	    {
	        log.error("insertGroup", ex);
	        return null;
	    }	
	}
	
	/**
	 * @description insert sub-group
	 * @author Hung.Bui
	 * @since 2023-07-21
	 */
	public GroupEntity insertSubGroup(GroupEntity obj) 
	{
		try
		{
			Object insertId = insert("Group.insertSubGroup", obj);
			if(insertId != null && insertId instanceof Integer) {
				return obj;
			}else {
				return null;
			}
		}
		catch(Exception ex)
		{
			log.error("insertSubGroup", ex);
			return null;
		}	
	}
	
	/**
	 * @description update group
	 * @author long.pham
	 * @since 2022-12-16
	 * @param id
	 */
	public boolean updateGroup(GroupEntity obj){
		try{
			return update("Group.updateGroup", obj)>0;
		}catch (Exception ex) {
			log.error("Group.updateGroup", ex);
			return false;
		}
	}
	
	/**
	 * @description update sub-group
	 * @author Hung.Bui
	 * @since 2023-07-21
	 * @param id
	 */
	public boolean updateSubGroup(GroupEntity obj){
		try{
			return update("Group.updateSubGroup", obj)>0;
		}catch (Exception ex) {
			log.error("Group.updateSubGroup", ex);
			return false;
		}
	}
	
	/**
	 * @description update group status
	 * @author long.pham
	 * @since 2022-12-16
	 * @param id
	 */
	public boolean updateStatus(GroupEntity obj){
		try{
			return update("Group.updateStatus", obj)>0;
		}catch (Exception ex) {
			log.error("Group.updateStatus", ex);
			return false;
		}
	}
	

}
