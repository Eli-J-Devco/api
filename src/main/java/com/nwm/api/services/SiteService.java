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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.EmployeeRoleMapEntity;
import com.nwm.api.entities.EmployeeSiteMapEntity;
import com.nwm.api.entities.SiteAreaBuildingFloorRoomEntity;
import com.nwm.api.entities.SiteCustomerMapEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.TablePreferenceEntity;

public class SiteService extends DB {
	/**
	 * @description get site detail
	 * @author long.pham
	 * @since 2020-10-22
	 * @param id_customer, id_site
	 * @return Object
	 */

	public SiteEntity getSummaryTotalAlert(SiteEntity obj) {
		SiteEntity dataObj = new SiteEntity();
		try {
			dataObj = (SiteEntity) queryForObject("Site.getSummaryTotalAlert", obj);
			if (dataObj == null)
				return new SiteEntity();
		} catch (Exception ex) {
			return new SiteEntity();
		}
		return dataObj;
	}
	
	/**
	 * @description get all site by id employee
	 * @author long.pham
	 * @since 2022-01-29
	 * @param id_employee
	 */
	

	public List getSiteByEmployee(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Site.getSiteByEmployee", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}

	/**
	 * @description get all site by id employee
	 * @author long.pham
	 * @since 2022-01-29
	 * @param id_employee
	 */
	

	public List getSiteByEmployeeREC(SiteEntity obj) {
		try {
			List dataList = (List<Map<String, Object>>) queryForList("Site.getSiteByEmployeeREC", obj);
			if (dataList == null) return new ArrayList();
			ObjectMapper mapper = new ObjectMapper();
			for (int i = 0; i < dataList.size(); i++) {
				Map<String, Object> item = (Map<String, Object>) dataList.get(i);
				
				try {
					item.put("options", mapper.readValue(item.get("devicesJSON").toString(), new TypeReference<List<Map<String, Object>>>(){}));
				} catch (JsonProcessingException e) {
					item.put("options", new ArrayList<Map<String, Object>>());
				}
				
				item.put("devicesJSON", null);
			}
			return dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	/**
	 * @description Get all site group by id employee
	 * @author Hung.Bui
	 * @since 2023-07-21
	 * @param id_employee
	 */
	public List getSiteGroupByEmployee(SiteEntity obj) {
		List dataList = new ArrayList();
		List newDataList = new ArrayList<>();
		try {
			dataList = queryForList("Site.getSiteGroupByEmployee", obj);
			if (dataList == null)
				return new ArrayList();
			
			for (int i = 0; i < dataList.size(); i++) {
				Map<String, Object> dataItem = (Map<String, Object>) dataList.get(i);
				List subGroupList = queryForList("Site.getSubGroupByGroup", dataItem);
				dataItem.put("sub_group_list", subGroupList);
				newDataList.add(dataItem);
			}
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get list site for page employee manage site
	 * @author long.pham
	 * @since 2021-01-07
	 */

	public List getListEmployeeManageSite(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Site.getListEmployeeManageSite", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}

	/**
	 * @description get total site for page employee manage site
	 * @author long.pham
	 * @since 2021-01-07
	 */
	public int getManageSiteTotalRecord(SiteEntity obj) {
		try {
			return (int) queryForObject("Site.getManageSiteTotalRecord", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	
	
	/**
	 * @description get total record Employee
	 * @author long.pham
	 * @since 2021-01-06
	 */
	public int checkExitsManageSite(SiteEntity obj) {
		try {
			return (int) queryForObject("Site.checkExitsManageSite", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	
	
	/**
	 * @description insert site employee map
	 * @author long.pham
	 * @since 2021-01-08
	 * @param id_employee, id_site
	 */
	public SiteEntity insertSiteEmployeeMap(SiteEntity obj) 
	{
		try
	    {
	       Object insertId = insert("Site.insertSiteEmployeeMap", obj);
	       if(insertId != null && insertId instanceof Integer) {
	    	   return obj;
	       }else {
	    	   return null;
	       }
	    }
	    catch(Exception ex)
	    {
	        log.error("insert.insertSiteEmployeeMap", ex);
	        return null;
	    }	
	}
	
	
	 /** @description delete site employee map
	 * @author long.pham
	 * @since 2021-01-08
	 * @param id
	 */
	public boolean deleteSiteEmployeeMap(SiteEntity obj) {
		try {
			return delete("Site.deleteSiteEmployeeMap", obj) > 0;
		} catch (Exception ex) {
			log.error("Site.deleteSiteEmployeeMap", ex);
			return false;
		}
	}
	
	
	
	/**
	 * @description insert site
	 * @author long.pham
	 * @since 2021-01-08
	 */
	public SiteEntity insertSite(SiteEntity obj) 
	{
		SqlSession session = this.beginTransaction();
		try {
			List dataEmployee = obj.getDataEmployee();
			if (dataEmployee.size() <= 0) {
				throw new Exception();
			}

			session.insert("Site.insertSite", obj);
			int insertLastId = obj.getId();

			if (insertLastId > 0) {
				// Update table virtual and table report
				obj.setTable_data_report("site" + insertLastId + "_data_report");
				obj.setTable_data_virtual("model"+ insertLastId + "_virtual_meter_or_inverter");
				
				// Create table site data report and table virtual meter
				session.insert("Site.createTableReportSite", obj);
				session.insert("Site.createTableVirtualDeviceSite", obj);
				session.insert("Site.updateTableVirtualAndReport", obj);
				
				
				for (int i = 0; i < dataEmployee.size(); i++) {
					Map<String, Object> employee = (Map<String, Object>) dataEmployee.get(i);
					int id_employee = (int) employee.get("id");
					EmployeeSiteMapEntity siteEmployeeMaptItem = this._buildSiteEmployeeMapItem(insertLastId, id_employee);
					session.insert("Site.insertSiteEmployeeMap", siteEmployeeMaptItem);
				}
				
				if (obj.getSite_type() == 2) {
					List areaList = obj.getAreaList();
					obj.setId_site(insertLastId);
					if (areaList != null) {
						if (areaList.size() > 0) {
							session.insert("Site.insertSiteArea", obj);
						}
					}
					obj.setId(insertLastId);
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
	 * build order product item
	 * 
	 * @param productItem
	 * @param productId
	 * @param insertOrderLastId
	 * @return
	 */
	@SuppressWarnings("unused")
	private EmployeeSiteMapEntity _buildSiteEmployeeMapItem(int id_site, int id_employee ) {
		try {
			EmployeeSiteMapEntity item = new EmployeeSiteMapEntity();
			item.setId_employee(id_employee);
			item.setId_site(id_site);
			return item;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	/**
	 * @description update role
	 * @author long.pham
	 * @since 2021-01-08
	 * @param id
	 */
	public boolean updateSite(SiteEntity obj){
		
		SqlSession session = this.beginTransaction();
		try {
			List dataEmployee = obj.getDataEmployee();
			if (dataEmployee.size() <= 0) {
				throw new Exception();
			}

			session.delete("Site.deleteSiteEmployeeMapEdit", obj);
			session.update("Site.updateSite", obj);

			for (int i = 0; i < dataEmployee.size(); i++) {
				Map<String, Object> customer = (Map<String, Object>) dataEmployee.get(i);
				int id_employee = (int) customer.get("id");
				EmployeeSiteMapEntity siteCustomerMaptItem = this._buildSiteEmployeeMapItem(obj.getId(), id_employee);
				session.insert("Site.insertSiteEmployeeMap", siteCustomerMaptItem);
			}
			
			if (obj.getSite_type() == 2) {
				// add Area
				List areaList = obj.getAreaList();
				if (areaList != null) {
					if (areaList.size() > 0) {
						session.insert("Site.insertSiteArea", obj);
					}
				}
				
				// add Building
				List buildingList = obj.getBuildingList();
				if (buildingList != null) {
					if (buildingList.size() > 0) {
						session.insert("Site.insertSiteAreaBuilding", obj);
					}
				}
				
				// add Floor
				List floorList = obj.getFloorList();
				if (floorList != null) {
					if (floorList.size() > 0) {
						session.insert("Site.insertSiteAreaBuildingFloor", obj);
					}
				}
							
				// add Room
				List roomList = obj.getRoomList();
				if (roomList != null) {
					if (roomList.size() > 0) {
						session.insert("Site.insertSiteAreaBuildingFloorRoom", obj);
					}
				}
			}
					
			
			session.update("Site.updateHidingSite", obj);

			session.commit();
			return true;
		} catch (Exception ex) {
			session.rollback();
			log.error("Site.updateSite", ex);
			return false;
		} finally {
			session.close();
		}
	}
	
	
	/**
	 * @description get list site by id customer
	 * @author long.pham
	 * @since 2020-10-09
	 * @param id_customer
	 */
	
	
	public List getList(SiteEntity obj) {
		List dataList = new ArrayList();
		try {		
			dataList = queryForList("Site.getList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	public int getTotalRecord(SiteEntity obj) {
		try {
			return (int)queryForObject("Site.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/**
	 * @description get list site building floor
	 * @author Duy.Phan
	 * @since 2024-08-12
	 * @param id_site
	 */
	
	
	public SiteEntity getSiteDetail(SiteEntity obj) {
		SiteEntity dataObj = null;
		try {
			 dataObj = (SiteEntity) queryForObject("Site.getSiteDetail", obj);
			if (dataObj == null)
				return new SiteEntity();
		} catch (Exception ex) {
			return new SiteEntity();
		}
		return dataObj;
	}
	
	
	/**
	 * @description update site status
	 * @author long.pham
	 * @since 2021-01-11
	 * @param id
	 */
	public boolean updateStatus(SiteEntity obj) {
		try {
			return update("Site.updateStatus", obj) > 0;
		} catch (Exception ex) {
			log.error("Site.updateStatus", ex);
			return false;
		}
	}
	
	
	/**
	 * @description delete site
	 * @author long.pham
	 * @since 2021-01-11
	 * @param id
	 */
	public boolean deleteEmployee(SiteEntity obj) {
		try {
			return update("Site.deleteSite", obj) > 0;
		} catch (Exception ex) {
			log.error("Site.deleteSite", ex);
			return false;
		}
	}
	
	
	/**
	 * @description get all site by id employee
	 * @author long.pham
	 * @since 2021-01-14
	 * @param id_employee, id_sites
	 */
	

	public List getAllSiteByEmployee(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Site.getAllSiteByEmployee", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get all site 
	 * @author long.pham
	 * @since 2020-10-08
	 * @param id_customer
	 */
	

	public List getAllSite(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Site.getAllSite", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	/**
	 * @description get all site group
	 * @author Hung.Bui
	 * @since 2023-08-23
	 */

	public List getAllSiteGroup(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Site.getAllSiteGroup", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @description get all site by id customer
	 * @author long.pham
	 * @since 2020-10-08
	 * @param id_customer
	 */
	

	public List getAllSiteByIdCustomer(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Site.getAllSiteByIdCustomer", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	/**
	 * @description get summary site by customer id
	 * @author long.pham
	 * @since 2020-10-21
	 * @param id_customer
	 * @return Object
	 */

	public SiteEntity getSiteCustomerById(int id_customer) {
		SiteEntity siteCustomerEn = new SiteEntity();
		try {
			siteCustomerEn = (SiteEntity) queryForObject("Site.getSiteCustomerById", id_customer);
			if (siteCustomerEn == null)
				return new SiteEntity();
		} catch (Exception ex) {
			log.error("Site.SiteCustomer", ex);
			return new SiteEntity();
		}
		return siteCustomerEn;
	}
	
	
	/**
	 * @description get site detail
	 * @author long.pham
	 * @since 2020-10-22
	 * @param id_customer, id_site
	 * @return Object
	 */

	public SiteEntity getDetailSite(SiteEntity obj) {
		SiteEntity dataObj = new SiteEntity();
		try {
			dataObj = (SiteEntity) queryForObject("Site.getDetailSite", obj);
			if (dataObj == null)
				return new SiteEntity();
		} catch (Exception ex) {
			return new SiteEntity();
		}
		return dataObj;
	}
	
	
	/**
	 * @description get Irradiance kpi by day 
	 * @author long.pham
	 * @since 2020-10-23
	 * @param id_site, id_customer
	 */
	

	public List getChartKPIDayIrradiance(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Site.getChartKPIDayIrradiance", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get Power kpi by day 
	 * @author long.pham
	 * @since 2020-10-23
	 * @param id_site, id_customer
	 */
	

	public List getChartKPIDayPower(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Site.getChartKPIDayPower", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	/**
	 * @description get energy kpi by day 
	 * @author long.pham
	 * @since 2020-10-23
	 * @param id_site, id_customer
	 */
	

	public List getChartKPIDayEnergy(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Site.getChartKPIDayEnergy", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	/**
	 * @description get Power kpi by month
	 * @author long.pham
	 * @since 2020-10-26
	 * @param id_site, id_customer
	 */
	

	public List getChartKPIMonthPower(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Site.getChartKPIMonthPower", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	/**
	 * @description get insolation kpi by month
	 * @author long.pham
	 * @since 2020-10-26
	 * @param id_site, id_customer
	 */
	

	public List getChartKPIMonthInsolation(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Site.getChartKPIMonthInsolation", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get kpi by month
	 * @author long.pham
	 * @since 2020-10-28
	 * @param id_site, id_customer
	 */
	

	public List getChartKPIMonth(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Site.getChartKPIMonth", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get kpi by year
	 * @author long.pham
	 * @since 2020-10-28
	 * @param id_site, id_customer
	 */
	

	public List getChartKPIYear(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Site.getChartKPIYear", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get active alarm
	 * @author long.pham
	 * @since 2020-10-29
	 * @param id_site, id_customer
	 */
	

	public List getActiveAlarm(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Site.getActiveAlarm", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	/**
     * @description  Update site information 
     * @author long.pham
     * @since 2020-10-30
     * @param 
     */
	public boolean updateSiteInformation(SiteEntity obj){
		try{
			return update("Site.updateSite", obj) > 0;
		}catch (Exception ex) {
			log.error("Site.updateSite", ex);
			return false;
		}
	}
	
	
	/**
	 * @description get report quick query model shark 100
	 * @author long.pham
	 * @since 2020-11-09
	 * @param id_site, id_customer, id_device
	 */
	

	public List reportQuickQuery(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Site.reportQuickQuery", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	/**
	 * @description get list data specific yield month
	 * @author long.pham
	 * @since 2020-11-10
	 * @param id_site, id_customer
	 */
	

	public List getSpecificYieldMonth(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Site.getSpecificYieldMonth", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get list data specific yield year
	 * @author long.pham
	 * @since 2020-11-10
	 * @param id_site, id_customer
	 */
	

	public List getSpecificYieldYear(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Site.getSpecificYieldYear", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	/**
	 * @description get daily report summary
	 * @author long.pham
	 * @since 2020-11-11
	 * @param id_customer, id_site
	 * @return Object
	 */

	public Object getDailyReportSumary(SiteEntity obj) {
		Object dataObj = null;
		try {
			dataObj = queryForObject("Site.getDailyReportSumary", obj);
			if (dataObj == null)
				return new SiteEntity();
		} catch (Exception ex) {
			return new SiteEntity();
		}
		return dataObj;
	}
	
	
	/**
	 * @description get list data daily report to chart
	 * @author long.pham
	 * @since 2020-11-10
	 * @param id_site, id_customer, start_date,end_date
	 */
	

	public List getDailyReportChart(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Site.getDailyReportChart", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	/**
	 * @description get list data report visualization device
	 * @author long.pham
	 * @since 2020-11-12
	 * @param id_site, id_customer,id_device, start_date, end_date
	 */
	

	public List getReportVisualizationDevice(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Site.getReportVisualizationDevice", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get list data report visualization device by day
	 * @author long.pham
	 * @since 2020-11-13
	 * @param id_site, id_customer,id_device, start_date, end_date
	 */
	

	public List getReportVisualizationDeviceDay(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Site.getReportVisualizationDeviceDay", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get list data annual comparison
	 * @author long.pham
	 * @since 2020-11-13
	 * @param id_site, id_customer, current_time
	 */
	

	public List getAnnualComparison(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Site.getAnnualComparison", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get user preference for table sorting column
	 * @author Hung.Bui
	 * @since 2023-02-27
	 * @param id_customer, id_site
	 */
	public TablePreferenceEntity getPreference(SiteEntity obj) {
		try {
			// get user preference for table sorting column
			TablePreferenceEntity tablePreference = new TablePreferenceEntity();
			tablePreference.setId_employee(obj.getId_employee());
			tablePreference.setTable("Site");
			tablePreference = (TablePreferenceEntity) queryForObject("TablePreference.getPreference", tablePreference);
			
			if ((obj.getOrder_by() != null) && (obj.getSort_column() != null)) {
				if (tablePreference != null) {
					tablePreference.setOrder_by(obj.getOrder_by());
					tablePreference.setSort_column(obj.getSort_column());
					update("TablePreference.updatePreference", tablePreference);
				} else {
					tablePreference = new TablePreferenceEntity();
					tablePreference.setId_employee(obj.getId_employee());
					tablePreference.setTable("Site");
					tablePreference.setOrder_by(obj.getOrder_by());
					tablePreference.setSort_column(obj.getSort_column());
					insert("TablePreference.insertPreference", tablePreference);
				}
			} else {
				if (tablePreference != null) {
					obj.setOrder_by(tablePreference.getOrder_by());
					obj.setSort_column(tablePreference.getSort_column());
				}
			}
			
			if (tablePreference == null) {
				return new TablePreferenceEntity();
			}
			return tablePreference;
		} catch (Exception ex) {
			return null;
		}
	}
	

	/**
	 * @description get site per page
	 * @author long.pham
	 * @since 2020-11-24
	 * @param id_site, id_alert, id_customer, current_time
	 * @return Object
	 */

	public Object getSitePerPage(SiteEntity obj) {
		Object dataObj = null;
		try {
			dataObj = queryForObject("Site.getSitePerPage", obj);
			if (dataObj == null)
				return new SiteEntity();
		} catch (Exception ex) {
			return new SiteEntity();
		}
		return dataObj;

	}
	
	/**
	  * @description delete area 
	 * @author Duy.Phan
	 * @since 2024-06-03
	 * @param id
	 */
	public boolean deleteSiteArea(SiteAreaBuildingFloorRoomEntity obj) {
		try {		
			return delete("Site.deleteSiteArea", obj) > 0;
		} catch (Exception ex) {
			log.error("Site.deleteSiteArea", ex);
			return false;
		}
	}
	
	/**
	  * @description delete building
	 * @author Duy.Phan
	 * @since 2024-06-03
	 * @param id
	 */
	public boolean deleteSiteAreaBuilding(SiteAreaBuildingFloorRoomEntity obj) {
		try {		
			return delete("Site.deleteSiteAreaBuilding", obj) > 0;
		} catch (Exception ex) {
			log.error("Site.deleteSiteAreaBuilding", ex);
			return false;
		}
	}
	
	/**
	  * @description delete floor
	 * @author Duy.Phan
	 * @since 2024-06-03
	 * @param id
	 */
	public boolean deleteSiteAreaBuildingFloor(SiteAreaBuildingFloorRoomEntity obj) {
		try {		
			return delete("Site.deleteSiteAreaBuildingFloor", obj) > 0;
		} catch (Exception ex) {
			log.error("Site.deleteSiteAreaBuildingFloor", ex);
			return false;
		}
	}
	
	/**
	  * @description delete unit
	 * @author Duy.Phan
	 * @since 2024-06-03
	 * @param id
	 */
	public boolean deleteSiteAreaBuildingFloorRoom(SiteAreaBuildingFloorRoomEntity obj) {
		try {		
			return delete("Site.deleteSiteAreaBuildingFloorRoom", obj) > 0;
		} catch (Exception ex) {
			log.error("Site.deleteSiteAreaBuildingFloorRoom", ex);
			return false;
		}
	}
}
