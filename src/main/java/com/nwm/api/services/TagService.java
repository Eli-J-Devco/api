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
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.TablePreferenceEntity;
import com.nwm.api.entities.TagEntity;

public class TagService extends DB {
	
	/**
	 * @description get total record Employee
	 * @author Duy.Phan
	 * @since 2024-03-06
	 */
	public int checkExitsTagName(TagEntity obj) {
		try {
			return (int) queryForObject("Tag.checkExitsTagName", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/**
	 * @description insert site
	 * @author long.pham
	 * @since 2021-01-08
	 */
	public TagEntity insertTag(TagEntity obj) 
	{
		try {
			insert("Tag.insertTag", obj);
			
			if (obj.getId() >= 0) {
				if (obj.getType_tag().equals("site")) {				
					insert("Tag.insertTagSiteMap", obj);				
				} else {
					insert("Tag.insertTagDeviceMap", obj);
				}
			} else {
				return null;
			}
			return obj;
		} catch (Exception ex) {
			log.error("Tag.insertTag", ex);
			return null;
		}
			
	}
	
	/**
	 * @description get list tag
	 * @author Duy.Phan
	 * @since 2024-06-03
	 */	
	public List getList(TagEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Tag.getList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get list tag By Site
	 * @author Duy.Phan
	 * @since 2024-06-03
	 * @param id_site
	 */	
	public List getListBySiteOrDevice(TagEntity obj) {
		List dataList = new ArrayList();
		try {
			if (obj.getType_tag().equals("site")) {			
				dataList = queryForList("Tag.getListBySite", obj);	
			} else {
				dataList = queryForList("Tag.getListByDevice", obj);
			}
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description delete Tag
	 * @author long.pham
	 * @since 2021-01-11
	 * @param id
	 */
	public boolean deleteTag(TagEntity obj) {
		try {
			return delete("Tag.deleteTag", obj) > 0;
		} catch (Exception ex) {
			log.error("Tag.deleteTag", ex);
			return false;
		}
	}
	
	/**
	 * @description insert site
	 * @author long.pham
	 * @since 2021-01-08
	 */
	public TagEntity insertTagtoSiteOrDevice(TagEntity obj) 
	{
		try {
			if (obj.getType_tag().equals("site")) {			
				insert("Tag.insertTagSiteMap", obj);		
			} else {
				insert("Tag.insertTagDeviceMap", obj);
			}
			
			return obj;
		} catch (Exception ex) {
			log.error("Tag.insertTagSiteMap", ex);
			return null;
		}			
	}
	
	/**
	 * @description delete Tag
	 * @author long.pham
	 * @since 2021-01-11
	 * @param id
	 */
	public boolean deleteTagMapToSiteOrDevice(TagEntity obj) {
		try {
			if (obj.getType_tag().equals("site")) {
				return delete("Tag.deleteTagMapToSite", obj) > 0;
			} else {
				return delete("Tag.deleteTagMapToDevice", obj) > 0;
			}
		} catch (Exception ex) {
			log.error("Tag.deleteTagMapToSite", ex);
			return false;
		}
	}

}
