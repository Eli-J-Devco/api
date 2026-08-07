/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.nwm.api.utils.Lib;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AuditLog;
import com.nwm.api.entities.SiteAreaBuildingFloorRoomEntity;
import com.nwm.api.entities.SiteDTO;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.SiteGasWaterElectricityRateScheduleEntity;
import com.nwm.api.entities.SiteGroupEntity;
import com.nwm.api.entities.SiteLogs;
import com.nwm.api.entities.SiteSubGroupEntity;

@Service
public class SiteService extends DB {
	@Autowired
	AuditingLogsService logsService;
	
	/**
	 * @description Get sites by user
	 * @author Hung.Bui
	 * @since 2026-08-04
	 * @return List
	 */
	public List<SiteGroupEntity> getSitesByUser(SiteEntity obj) {
		try {
			if (obj.getId_sites().isEmpty()) return new ArrayList<>();
			return Optional.ofNullable((List<SiteGroupEntity>) queryForList("Site.getSitesByUser", obj)).orElse(new ArrayList<>()).stream()
					.map(group -> {
						List<SiteDTO> sitesWithoutSubgroup = group.getSubGroups().stream()
								.filter(item -> Objects.isNull(item.getId()))
								.map(SiteSubGroupEntity::getSites)
								.flatMap(List::stream)
								.collect(Collectors.toList());
						
						SiteSubGroupEntity emptySubGroup = new SiteSubGroupEntity();
						emptySubGroup.setSites(sitesWithoutSubgroup);
						
						List<SiteSubGroupEntity> filterSubGroups = group.getSubGroups().stream()
								.filter(item -> Objects.nonNull(item.getId()))
								.collect(Collectors.toList());
						filterSubGroups.add(emptySubGroup);
						
						group.setSubGroups(filterSubGroups);
						
						return group;
					})
					.collect(Collectors.toList());
		} catch (Exception ex) {
			return new ArrayList<>();
		}
	}
	
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
	 * @description get site by id
	 * @author Hung.Bui
	 * @since 2026-07-01
	 * @param id
	 * @return Optional<SiteEntity>
	 */
	@Cacheable(value = "sites", key = "#id")
	public Optional<SiteEntity> getSiteById(int id) {
		try {
			return Optional.ofNullable((SiteEntity) queryForObject("Site.getSiteById", id));
		} catch (Exception e) {
			return Optional.empty();
		}
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
					item.put("options", mapper.readValue(item.get("options").toString(), new TypeReference<List<Map<String, Object>>>(){}));
				} catch (JsonProcessingException e) {
					item.put("options", new ArrayList<Map<String, Object>>());
				}
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
		try {
			List dataList = queryForList("Site.getSiteGroupByEmployee", obj);
			if (dataList == null) return new ArrayList();
			
			ObjectMapper mapper = new ObjectMapper();
			for (int i = 0; i < dataList.size(); i++) {
				Map<String, Object> item = (Map<String, Object>) dataList.get(i);
				
				try {
					item.put("sub_group_list", mapper.readValue(item.get("sub_group_list").toString(), new TypeReference<List<Map<String, Object>>>(){}));
				} catch (JsonProcessingException e) {
					item.put("sub_group_list", new ArrayList<Map<String, Object>>());
				}
			}
			
			return dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
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
	 * @description get Email CC
	 * @author long.pham
	 * @since 2021-01-06
	 */
	public String getEmailCC(SiteEntity obj) {
		try {
			return (String) queryForObject("Site.getEmailCC", obj);
		} catch (Exception ex) {
			return null;
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
			List<Map<String, Object>> dataEmployee = obj.getDataEmployee();
			if (dataEmployee.size() <= 0) {
				throw new Exception();
			}
            if (obj.getSolar_edge_site() == 1) {
                boolean invalidConfig = obj.getSolar_edge_id() == null || obj.getSolar_edge_id() <= 0 || Lib.isBlank(obj.getSolar_edge_api_key());

                if (invalidConfig) {
                    throw new Exception("Please input SolarEdge ID site or API key");
                }

                obj.setCommunication("SolarEdge API");
            }
			session.insert("Site.insertSite", obj);
			int insertLastId = obj.getId();

			if (insertLastId > 0) {
				dataEmployee.sort((a,b) -> ((Integer) a.get("id")).compareTo(((Integer) b.get("id"))));
				dataEmployee.forEach(item -> item.put("id_site", insertLastId));
				
				// Update table virtual and table report
				obj.setTable_data_report("site" + insertLastId + "_data_report");
				obj.setTable_data_virtual("model"+ insertLastId + "_virtual_meter_or_inverter");
				
				// Create table site data report and table virtual meter
				session.insert("Site.createTableReportSite", obj);
				session.insert("Site.createTableVirtualDeviceSite", obj);
				session.insert("Site.updateTableVirtualAndReport", obj);
				session.insert("Site.insertSiteEmployeeMap", obj);
				
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
	 * @description update role
	 * @author long.pham
	 * @since 2021-01-08
	 * @param id
	 */
	public boolean updateSite(SiteEntity obj){
		
		SqlSession session = this.beginTransaction();
		try {
			int insertLastId = obj.getId();
			
			switch (obj.getTab_menu()) {
			case 1:
				List<Map<String, Object>> dataEmployee = obj.getDataEmployee();
				if (dataEmployee.size() <= 0) {
					throw new Exception();
				}
				dataEmployee.sort((a,b) -> ((Integer) a.get("id")).compareTo(((Integer) b.get("id"))));
				dataEmployee.forEach(item -> item.put("id_site", insertLastId));

                if (obj.getSolar_edge_site() == 0) {
                    obj.setSolar_edge_id(null);
                    obj.setSolar_edge_api_key(null);
                    obj.setSolar_edge_auto_backfill(0);
                } else {
                    boolean invalidConfig = obj.getSolar_edge_id() == null || obj.getSolar_edge_id() <= 0 || Lib.isBlank(obj.getSolar_edge_api_key());

                    if (invalidConfig) {
                        throw new Exception("Please input SolarEdge ID site or API key");
                    }
                }
				session.update("Site.updateSite", obj);
				session.delete("Site.deleteSiteEmployeeMapEdit", obj);
				session.insert("Site.insertSiteEmployeeMap", obj);
				
				if (obj.getSite_type() == 2) {
					// add Area
					List areaList = obj.getAreaList();
					if (areaList != null) {
						if (areaList.size() > 0) {
							session.insert("Site.insertSiteArea", obj);
						}
					}
					obj.setId(insertLastId);
				}
				break;
			case 2:
				if (obj.getSite_type() == 2) {
					// add Building
					List buildingList = obj.getBuildingList();
					if (buildingList != null) {
						if (buildingList.size() > 0) {
							session.insert("Site.insertSiteAreaBuilding", obj);
						}
					}
					obj.setId(insertLastId);
				}
				break;
			case 3:
				if (obj.getSite_type() == 2) {
					// add Floor
					List floorList = obj.getFloorList();
					if (floorList != null) {
						if (floorList.size() > 0) {
							session.insert("Site.insertSiteAreaBuildingFloor", obj);
						}
					}
					obj.setId(insertLastId);
				}
				break;
			case 4:
				if (obj.getSite_type() == 2) {
					// add Room
					List roomList = obj.getRoomList();
					if (roomList != null) {
						if (roomList.size() > 0) {
							session.insert("Site.insertSiteAreaBuildingFloorRoom", obj);
						}
					}
					obj.setId(insertLastId);
				}
				break;
			case 5:
				session.update("Site.updateSite", obj);
				break;
			case 6:
				session.update("Site.insertSiteGas", obj);
				List gasRateSchedulesList = obj.getGasRateSchedulesList();
				if (gasRateSchedulesList != null) {
					if (gasRateSchedulesList.size() > 0) {
						session.insert("Site.insertSiteGasRateSchedules", obj);
					}
					obj.setId(insertLastId);
				}
				
				session.update("Site.insertSiteWater", obj);
				List waterRateSchedulesList = obj.getWaterRateSchedulesList();
				if (waterRateSchedulesList != null) {
					if (waterRateSchedulesList.size() > 0) {
						session.insert("Site.insertSiteWaterRateSchedules", obj);
					}
					obj.setId(insertLastId);
				}
				
				session.update("Site.insertSiteElectricity", obj);
				List electricityRateSchedulesList = obj.getElectricityRateSchedulesList();
				if (electricityRateSchedulesList != null) {
					if (electricityRateSchedulesList.size() > 0) {
						session.insert("Site.insertSiteElectricityRateSchedules", obj);
					}
					obj.setId(insertLastId);
				}
				
				break;

			default:
				break;
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
		try {
			List dataList = queryForList("Site.getAllSite", obj);
			if (dataList == null) return new ArrayList();
			
			ObjectMapper mapper = new ObjectMapper();
			for (int i = 0; i < dataList.size(); i++) {
				Map<String, Object> item = (Map<String, Object>) dataList.get(i);
				
				try {
					List<Map<String, Object>> sites = mapper.readValue(item.get("options").toString(), new TypeReference<List<Map<String, Object>>>(){});
					sites.sort((s1, s2) -> s1.get("text").toString().compareTo(s2.get("text").toString()));
					item.put("options", sites);
				} catch (JsonProcessingException e) {
					item.put("options", new ArrayList<Map<String, Object>>());
				}
			}
			return dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
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
	 * @description Get site logs
	 * @author Hung.Bui
	 * @since 2025-09-05
	 * @param id
	 */
	public List<AuditLog> getLogs(SiteEntity obj) {
		try {
			List<SiteLogs> logs = Optional.ofNullable(queryForList("Site.getLogs", obj)).orElse(new ArrayList<>());
			return logsService.getLogDifferences(logs, null);
		} catch (Exception ex) {
			return new ArrayList<>();
		}
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
	
	/**
	  * @description delete water rate schedule
	 * @author Duy.Phan
	 * @since 2024-06-03
	 * @param id
	 */
	public boolean deleteSiteWaterRateSchedule(SiteGasWaterElectricityRateScheduleEntity obj) {
		try {		
			return delete("Site.deleteSiteWaterRateSchedule", obj) > 0;
		} catch (Exception ex) {
			log.error("Site.deleteSiteWaterRateSchedule", ex);
			return false;
		}
	}
	
	/**
	  * @description delete gas rate schedule
	 * @author Duy.Phan
	 * @since 2024-06-03
	 * @param id
	 */
	public boolean deleteSiteGasRateSchedule(SiteGasWaterElectricityRateScheduleEntity obj) {
		try {		
			return delete("Site.deleteSiteGasRateSchedule", obj) > 0;
		} catch (Exception ex) {
			log.error("Site.deleteSiteGasRateSchedule", ex);
			return false;
		}
	}
	
	/**
	  * @description delete water rate schedule
	 * @author Duy.Phan
	 * @since 2024-06-03
	 * @param id
	 */
	public boolean deleteSiteElectricityRateSchedule(SiteGasWaterElectricityRateScheduleEntity obj) {
		try {		
			return delete("Site.deleteSiteElectricityRateSchedule", obj) > 0;
		} catch (Exception ex) {
			log.error("Site.deleteSiteElectricityRateSchedule", ex);
			return false;
		}
	}
	
	/**
	 * @description update bem overview tab
	 * @author duy.phan
	 * @since 2022-12-22
	 * @param id
	 */
	public boolean updateBemsOverviewTab(SiteEntity obj) {
		try {
			return update("Site.updateBemsOverviewTab", obj) > 0;
		} catch (Exception ex) {
			log.error("Site.updateBemsOverviewTab", ex);
			return false;
		}
	}

	public List<SiteEntity> getSiteByCondition(Map<String, Object> obj) {
		List<SiteEntity> dataList = new ArrayList();
		try {
			dataList = queryForList("Site.getSiteByCondition", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
}
