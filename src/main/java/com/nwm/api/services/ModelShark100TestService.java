/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelShark100TestEntity;
import com.nwm.api.entities.SiteCustomerMapEntity;
import com.nwm.api.entities.SiteEntity;

public class ModelShark100TestService extends DB {

	/**
	 * @description insert data from datalogger to model shark 100
	 * @author long.pham
	 * @since 2020-10-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelShark100Test(ModelShark100TestEntity obj) {
		try {
			 Object insertId = insert("ModelShark100Test.insertModelShark100Test", obj);
		        if(insertId == null ) {
		        	return false;
		        }
		        return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}
	
//	public boolean insertModelShark100Test(ModelShark100TestEntity obj) 
//	{
//		SqlSession session = this.beginTransaction();
//		try {
//			Object insertId = session.insert("ModelShark100Test.insertModelShark100Test", obj);
//			if(insertId == null ) {
//	        	return false;
//	        }
//
//			session.commit();
//			return true;
//		} catch (Exception ex) {
//			session.rollback();
//			log.error("Site.insertSite", ex);
//			return false;
//		} finally {
//			session.close();
//		}
//			
//	}
	

}
