/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.SiteGroupManageEntity;

public class SiteGroupService extends DB {

	/**
	 * @description get list site group 
	 * @author Hung.Bui
	 * @since 2022-12-13
	 * @param {}
	 */
	public List getList(SiteGroupManageEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("SiteGroup.getList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	public int getTotalRecord(SiteGroupManageEntity obj) {
		try {
			return (int)queryForObject("SiteGroup.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	
	/** @description delete site group
	 * @author Hung.Bui
	 * @since 2022-12-13
	 * @param id
	 */
	public boolean delete(SiteGroupManageEntity obj) {
		try {
			return update("SiteGroup.deleteSiteGroup", obj) > 0 && update("SiteGroup.deleteAllSiteInSiteGroup", obj) > 0;
		} catch (Exception ex) {
			log.error("SiteGroup.deleteSiteGroup", ex);
			return false;
		}
	}
	
	
	
	/**
	 * @description insert site group
	 * @author Hung.Bui
	 * @since 2022-12-13
	 */
	public SiteGroupManageEntity insertSiteGroup(SiteGroupManageEntity obj) 
	{
		try
	    {
	       Object insertId = insert("SiteGroup.insertSiteGroup", obj);
	       if(insertId != null && insertId instanceof Integer) {
	    	   if (update("SiteGroup.updateSiteGroupInSite", obj) > 0) {
	    		   return obj;
	    	   } else {
	    		   return null;
	    	   }
	       }else {
	    	   return null;
	       }
	    }
	    catch(Exception ex)
	    {
	        log.error("SiteGroup.insertSiteGroup", ex);
	        return null;
	    }	
	}
	
	
	/**
	 * @description update site group
	 * @author Hung.Bui
	 * @since 2022-12-13
	 * @param id
	 */
	public boolean updateSiteGroup(SiteGroupManageEntity obj){
		try{
			// find sites that have been remove from group
			SiteGroupManageEntity siteSiteGroupMaptItem = this._buildSiteSiteGroupMapItem(obj.getId());
			List<Integer> prevList = queryForList("SiteGroup.getPrevList", siteSiteGroupMaptItem);
			Iterator<Integer> prevListIterator = prevList.iterator();
			
			while (prevListIterator.hasNext()) {
				int id = prevListIterator.next();
				int[] currList = obj.getSite_id();
				if (IntStream.of(currList).anyMatch(i -> i == id)) {
					prevListIterator.remove();
				}
			}
			
			boolean result = true;
			if (prevList.size() > 0) {
				obj.setPrev_site_id(prevList.stream().mapToInt(i -> i).toArray());
				result = update("SiteGroup.deletePrevSiteInSiteGroup", obj) > 0;
			}
			result = result && update("SiteGroup.updateSiteGroupInName", obj) > 0;
			result = result && update("SiteGroup.updateSiteGroupInSite", obj) > 0;
			
			return result;
		}catch (Exception ex) {
			log.error("SiteGroup.updateSiteGroup", ex);
			return false;
		}
	}
	
	/**
	 * build site - site group map item
	 * 
	 * @param siteGroupId
	 * @return
	 */
	private SiteGroupManageEntity _buildSiteSiteGroupMapItem(int siteGroupId) {
		try {
			SiteGroupManageEntity siteSiteGroupMaptItem = new SiteGroupManageEntity();
			siteSiteGroupMaptItem.setId(siteGroupId);
			return siteSiteGroupMaptItem;
		} catch (Exception e) {
			return null;
		}
	}

}
