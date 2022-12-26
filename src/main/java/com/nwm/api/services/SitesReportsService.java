/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ReportsEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.SitesReportsEntity;
import com.nwm.api.entities.ViewReportEntity;

public class SitesReportsService extends DB {


	/**
	 * @description insert report
	 * @author long.pham
	 * @since 2021-12-27
	 */
	public SitesReportsEntity insertReports(SitesReportsEntity obj) {
		SqlSession session = this.beginTransaction();
		try {
			session.insert("SitesReports.insertReports", obj);
			session.commit();
			return obj;
		} catch (Exception ex) {
			session.rollback();
			log.error("SitesReports.insertReports", ex);
			return null;
		} finally {
			session.close();
		}

	}

	/**
	 * @description update role
	 * @author long.pham
	 * @since 2021-01-08
	 * @param id
	 */
	public boolean updateReports(SitesReportsEntity obj) {

		SqlSession session = this.beginTransaction();
		try {

			session.update("SitesReports.updateReports", obj);
			session.commit();
			return true;
		} catch (Exception ex) {
			session.rollback();
			log.error("SitesReports.updateReports", ex);
			return false;
		} finally {
			session.close();
		}
	}

	/**
	 * @description get list site by id customer
	 * @author long.pham
	 * @since 2021-12-27
	 * @param object
	 */

	public List getList(SitesReportsEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("SitesReports.getList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}

	public int getTotalRecord(SitesReportsEntity obj) {
		try {
			return (int) queryForObject("SitesReports.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}

	/**
	 * @description delete site
	 * @author long.pham
	 * @since 2021-01-11
	 * @param id
	 */
	public boolean deleteReports(SitesReportsEntity obj) {
		try {
			return update("SitesReports.deleteReports", obj) > 0;
		} catch (Exception ex) {
			log.error("SitesReports.deleteReports", ex);
			return false;
		}
	}
	
	

	public Object getSiteDetail(SitesReportsEntity obj) {
		ViewReportEntity dataObj = new ViewReportEntity();
		try {
			dataObj = (ViewReportEntity) queryForObject("SitesReports.getSiteDetail", obj);
			if (dataObj == null) {
				return null;
			}
			
			switch (dataObj.getId_site_type()) {
				case 1:
				case 6:
				case 2:
				case 4:
					obj.setTable_name("model_shark100");
					break;
				
				case 3:
					obj.setTable_name("");
					break;
				case 5:
				case 9:
					obj.setTable_name("model_veris_industries_e51c2_power_meter");
					break;
				case 7:
					obj.setTable_name("model_elkor_wattson_pv_meter");
					break;
				case 8:
					obj.setTable_name("");
					break;
			}
			
			List dataEnergy = queryForList("SitesReports.getReportYearDataEnergy", obj);
			dataObj.setDataReports(dataEnergy);
			
			List dataEnergyExpectations = queryForList("SitesReports.getReportEnergyExpectations", obj);
			dataObj.setDataExpectations(dataEnergyExpectations);
			
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}

//	/**
//	 * @description get total site for page employee manage site
//	 * @author long.pham
//	 * @since 2021-01-07
//	 */
//	public int getManageSiteTotalRecord(SiteEntity obj) {
//		try {
//			return (int) queryForObject("Site.getManageSiteTotalRecord", obj);
//		} catch (Exception ex) {
//			return 0;
//		}
//	}
//	
//	
//	
//	/**
//	 * @description get total record Employee
//	 * @author long.pham
//	 * @since 2021-01-06
//	 */
//	public int checkExitsManageSite(SiteEntity obj) {
//		try {
//			return (int) queryForObject("Site.checkExitsManageSite", obj);
//		} catch (Exception ex) {
//			return 0;
//		}
//	}
//	
//	
//	
//	/**
//	 * @description insert site employee map
//	 * @author long.pham
//	 * @since 2021-01-08
//	 * @param id_employee, id_site
//	 */
//	public SiteEntity insertSiteEmployeeMap(SiteEntity obj) 
//	{
//		try
//	    {
//	       Object insertId = insert("Site.insertSiteEmployeeMap", obj);
//	       if(insertId != null && insertId instanceof Integer) {
//	    	   return obj;
//	       }else {
//	    	   return null;
//	       }
//	    }
//	    catch(Exception ex)
//	    {
//	        log.error("insert.insertSiteEmployeeMap", ex);
//	        return null;
//	    }	
//	}
//	
//	
//	 /** @description delete site employee map
//	 * @author long.pham
//	 * @since 2021-01-08
//	 * @param id
//	 */
//	public boolean deleteSiteEmployeeMap(SiteEntity obj) {
//		try {
//			return delete("Site.deleteSiteEmployeeMap", obj) > 0;
//		} catch (Exception ex) {
//			log.error("Site.deleteSiteEmployeeMap", ex);
//			return false;
//		}
//	}
//	
//	
//	

//	
//	/**
//	 * build order product item
//	 * 
//	 * @param productItem
//	 * @param productId
//	 * @param insertOrderLastId
//	 * @return
//	 */
//	@SuppressWarnings("unused")
//	private SiteCustomerMapEntity _buildSiteCustomerMapItem(int id_site, int id_customer ) {
//		try {
//			SiteCustomerMapEntity item = new SiteCustomerMapEntity();
//			item.setId_customer(id_customer);
//			item.setId_site(id_site);
//			return item;
//		} catch (Exception e) {
//			return null;
//		}
//	}
//	
//	

//	
//	/**
//	 * @description update site status
//	 * @author long.pham
//	 * @since 2021-01-11
//	 * @param id
//	 */
//	public boolean updateStatus(SiteEntity obj) {
//		try {
//			return update("Site.updateStatus", obj) > 0;
//		} catch (Exception ex) {
//			log.error("Site.updateStatus", ex);
//			return false;
//		}
//	}
//	
//	

//	
//	
//	/**
//	 * @description get all site by id employee
//	 * @author long.pham
//	 * @since 2021-01-14
//	 * @param id_employee, id_sites
//	 */
//	
//
//	public List getAllSiteByEmployee(SiteEntity obj) {
//		List dataList = new ArrayList();
//		try {
//			dataList = queryForList("Site.getAllSiteByEmployee", obj);
//			if (dataList == null)
//				return new ArrayList();
//		} catch (Exception ex) {
//			return new ArrayList();
//		}
//		return dataList;
//	}
//	
//	/**
//	 * @description get all site 
//	 * @author long.pham
//	 * @since 2020-10-08
//	 * @param id_customer
//	 */
//	
//
//	public List getAllSite(SiteEntity obj) {
//		List dataList = new ArrayList();
//		try {
//			dataList = queryForList("Site.getAllSite", obj);
//			if (dataList == null)
//				return new ArrayList();
//		} catch (Exception ex) {
//			return new ArrayList();
//		}
//		return dataList;
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	/**
//	 * @description get all site by id customer
//	 * @author long.pham
//	 * @since 2020-10-08
//	 * @param id_customer
//	 */
//	
//
//	public List getAllSiteByIdCustomer(SiteEntity obj) {
//		List dataList = new ArrayList();
//		try {
//			dataList = queryForList("Site.getAllSiteByIdCustomer", obj);
//			if (dataList == null)
//				return new ArrayList();
//		} catch (Exception ex) {
//			return new ArrayList();
//		}
//		return dataList;
//	}
//	
//	
//	/**
//	 * @description get summary site by customer id
//	 * @author long.pham
//	 * @since 2020-10-21
//	 * @param id_customer
//	 * @return Object
//	 */
//
//	public SiteEntity getSiteCustomerById(int id_customer) {
//		SiteEntity siteCustomerEn = new SiteEntity();
//		try {
//			siteCustomerEn = (SiteEntity) queryForObject("Site.getSiteCustomerById", id_customer);
//			if (siteCustomerEn == null)
//				return new SiteEntity();
//		} catch (Exception ex) {
//			log.error("Site.SiteCustomer", ex);
//			return new SiteEntity();
//		}
//		return siteCustomerEn;
//	}
//	
//	
//	/**
//	 * @description get site detail
//	 * @author long.pham
//	 * @since 2020-10-22
//	 * @param id_customer, id_site
//	 * @return Object
//	 */
//
//	public SiteEntity getDetailSite(SiteEntity obj) {
//		SiteEntity dataObj = new SiteEntity();
//		try {
//			dataObj = (SiteEntity) queryForObject("Site.getDetailSite", obj);
//			if (dataObj == null)
//				return new SiteEntity();
//		} catch (Exception ex) {
//			return new SiteEntity();
//		}
//		return dataObj;
//	}
//	
//	
//	/**
//	 * @description get Irradiance kpi by day 
//	 * @author long.pham
//	 * @since 2020-10-23
//	 * @param id_site, id_customer
//	 */
//	
//
//	public List getChartKPIDayIrradiance(SiteEntity obj) {
//		List dataList = new ArrayList();
//		try {
//			dataList = queryForList("Site.getChartKPIDayIrradiance", obj);
//			if (dataList == null)
//				return new ArrayList();
//		} catch (Exception ex) {
//			return new ArrayList();
//		}
//		return dataList;
//	}
//	
//	/**
//	 * @description get Power kpi by day 
//	 * @author long.pham
//	 * @since 2020-10-23
//	 * @param id_site, id_customer
//	 */
//	
//
//	public List getChartKPIDayPower(SiteEntity obj) {
//		List dataList = new ArrayList();
//		try {
//			dataList = queryForList("Site.getChartKPIDayPower", obj);
//			if (dataList == null)
//				return new ArrayList();
//		} catch (Exception ex) {
//			return new ArrayList();
//		}
//		return dataList;
//	}
//	
//	
//	/**
//	 * @description get energy kpi by day 
//	 * @author long.pham
//	 * @since 2020-10-23
//	 * @param id_site, id_customer
//	 */
//	
//
//	public List getChartKPIDayEnergy(SiteEntity obj) {
//		List dataList = new ArrayList();
//		try {
//			dataList = queryForList("Site.getChartKPIDayEnergy", obj);
//			if (dataList == null)
//				return new ArrayList();
//		} catch (Exception ex) {
//			return new ArrayList();
//		}
//		return dataList;
//	}
//	
//	
//	/**
//	 * @description get Power kpi by month
//	 * @author long.pham
//	 * @since 2020-10-26
//	 * @param id_site, id_customer
//	 */
//	
//
//	public List getChartKPIMonthPower(SiteEntity obj) {
//		List dataList = new ArrayList();
//		try {
//			dataList = queryForList("Site.getChartKPIMonthPower", obj);
//			if (dataList == null)
//				return new ArrayList();
//		} catch (Exception ex) {
//			return new ArrayList();
//		}
//		return dataList;
//	}
//	
//	
//	/**
//	 * @description get insolation kpi by month
//	 * @author long.pham
//	 * @since 2020-10-26
//	 * @param id_site, id_customer
//	 */
//	
//
//	public List getChartKPIMonthInsolation(SiteEntity obj) {
//		List dataList = new ArrayList();
//		try {
//			dataList = queryForList("Site.getChartKPIMonthInsolation", obj);
//			if (dataList == null)
//				return new ArrayList();
//		} catch (Exception ex) {
//			return new ArrayList();
//		}
//		return dataList;
//	}
//	
//	/**
//	 * @description get kpi by month
//	 * @author long.pham
//	 * @since 2020-10-28
//	 * @param id_site, id_customer
//	 */
//	
//
//	public List getChartKPIMonth(SiteEntity obj) {
//		List dataList = new ArrayList();
//		try {
//			dataList = queryForList("Site.getChartKPIMonth", obj);
//			if (dataList == null)
//				return new ArrayList();
//		} catch (Exception ex) {
//			return new ArrayList();
//		}
//		return dataList;
//	}
//	
//	/**
//	 * @description get kpi by year
//	 * @author long.pham
//	 * @since 2020-10-28
//	 * @param id_site, id_customer
//	 */
//	
//
//	public List getChartKPIYear(SiteEntity obj) {
//		List dataList = new ArrayList();
//		try {
//			dataList = queryForList("Site.getChartKPIYear", obj);
//			if (dataList == null)
//				return new ArrayList();
//		} catch (Exception ex) {
//			return new ArrayList();
//		}
//		return dataList;
//	}
//	
//	/**
//	 * @description get active alarm
//	 * @author long.pham
//	 * @since 2020-10-29
//	 * @param id_site, id_customer
//	 */
//	
//
//	public List getActiveAlarm(SiteEntity obj) {
//		List dataList = new ArrayList();
//		try {
//			dataList = queryForList("Site.getActiveAlarm", obj);
//			if (dataList == null)
//				return new ArrayList();
//		} catch (Exception ex) {
//			return new ArrayList();
//		}
//		return dataList;
//	}
//	
//	
//	
//	/**
//     * @description  Update site information 
//     * @author long.pham
//     * @since 2020-10-30
//     * @param 
//     */
//	public boolean updateSiteInformation(SiteEntity obj){
//		try{
//			return update("Site.updateSite", obj) > 0;
//		}catch (Exception ex) {
//			log.error("Site.updateSite", ex);
//			return false;
//		}
//	}
//	
//	
//	/**
//	 * @description get report quick query model shark 100
//	 * @author long.pham
//	 * @since 2020-11-09
//	 * @param id_site, id_customer, id_device
//	 */
//	
//
//	public List reportQuickQuery(SiteEntity obj) {
//		List dataList = new ArrayList();
//		try {
//			dataList = queryForList("Site.reportQuickQuery", obj);
//			if (dataList == null)
//				return new ArrayList();
//		} catch (Exception ex) {
//			return new ArrayList();
//		}
//		return dataList;
//	}
//	
//	
//	
//	/**
//	 * @description get list data specific yield month
//	 * @author long.pham
//	 * @since 2020-11-10
//	 * @param id_site, id_customer
//	 */
//	
//
//	public List getSpecificYieldMonth(SiteEntity obj) {
//		List dataList = new ArrayList();
//		try {
//			dataList = queryForList("Site.getSpecificYieldMonth", obj);
//			if (dataList == null)
//				return new ArrayList();
//		} catch (Exception ex) {
//			return new ArrayList();
//		}
//		return dataList;
//	}
//	
//	/**
//	 * @description get list data specific yield year
//	 * @author long.pham
//	 * @since 2020-11-10
//	 * @param id_site, id_customer
//	 */
//	
//
//	public List getSpecificYieldYear(SiteEntity obj) {
//		List dataList = new ArrayList();
//		try {
//			dataList = queryForList("Site.getSpecificYieldYear", obj);
//			if (dataList == null)
//				return new ArrayList();
//		} catch (Exception ex) {
//			return new ArrayList();
//		}
//		return dataList;
//	}
//	
//	
//	
//	/**
//	 * @description get daily report summary
//	 * @author long.pham
//	 * @since 2020-11-11
//	 * @param id_customer, id_site
//	 * @return Object
//	 */
//
//	public Object getDailyReportSumary(SiteEntity obj) {
//		Object dataObj = null;
//		try {
//			dataObj = queryForObject("Site.getDailyReportSumary", obj);
//			if (dataObj == null)
//				return new SiteEntity();
//		} catch (Exception ex) {
//			return new SiteEntity();
//		}
//		return dataObj;
//	}
//	
//	
//	/**
//	 * @description get list data daily report to chart
//	 * @author long.pham
//	 * @since 2020-11-10
//	 * @param id_site, id_customer, start_date,end_date
//	 */
//	
//
//	public List getDailyReportChart(SiteEntity obj) {
//		List dataList = new ArrayList();
//		try {
//			dataList = queryForList("Site.getDailyReportChart", obj);
//			if (dataList == null)
//				return new ArrayList();
//		} catch (Exception ex) {
//			return new ArrayList();
//		}
//		return dataList;
//	}
//	
//	
//	
//	/**
//	 * @description get list data report visualization device
//	 * @author long.pham
//	 * @since 2020-11-12
//	 * @param id_site, id_customer,id_device, start_date, end_date
//	 */
//	
//
//	public List getReportVisualizationDevice(SiteEntity obj) {
//		List dataList = new ArrayList();
//		try {
//			dataList = queryForList("Site.getReportVisualizationDevice", obj);
//			if (dataList == null)
//				return new ArrayList();
//		} catch (Exception ex) {
//			return new ArrayList();
//		}
//		return dataList;
//	}
//	
//	/**
//	 * @description get list data report visualization device by day
//	 * @author long.pham
//	 * @since 2020-11-13
//	 * @param id_site, id_customer,id_device, start_date, end_date
//	 */
//	
//
//	public List getReportVisualizationDeviceDay(SiteEntity obj) {
//		List dataList = new ArrayList();
//		try {
//			dataList = queryForList("Site.getReportVisualizationDeviceDay", obj);
//			if (dataList == null)
//				return new ArrayList();
//		} catch (Exception ex) {
//			return new ArrayList();
//		}
//		return dataList;
//	}
//	
//	/**
//	 * @description get list data annual comparison
//	 * @author long.pham
//	 * @since 2020-11-13
//	 * @param id_site, id_customer, current_time
//	 */
//	
//
//	public List getAnnualComparison(SiteEntity obj) {
//		List dataList = new ArrayList();
//		try {
//			dataList = queryForList("Site.getAnnualComparison", obj);
//			if (dataList == null)
//				return new ArrayList();
//		} catch (Exception ex) {
//			return new ArrayList();
//		}
//		return dataList;
//	}
//	

}
