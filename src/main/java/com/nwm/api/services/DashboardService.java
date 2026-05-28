/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.*;

public class DashboardService extends DB {
	/**
	 * @description get list alert by site
	 * @author long.pham
	 * @since 2020-11-16
	 * @param id_customer, id_site, start_date, end_date
	 */

	public List getList(AlertEntity obj) {
		try {
			List rs = queryForList("Dashboard.getList", obj);
			if (rs == null) {
				return new ArrayList<>();
			}
			return rs;
		} catch (Exception ex) {
			return null;
		}
	}
	
	
	
	
	/**
	 * @description get list alert by site
	 * @author long.pham
	 * @since 2020-11-16
	 * @param id_customer, id_site, start_date, end_date
	 */

	public List getListActualvsExpected(DashboardEntity obj) {
		try {
			List rs = queryForList("Dashboard.getListActualvsExpected", obj);
			if (rs == null) {
				return new ArrayList<>();
			}
			return rs;
		} catch (Exception ex) {
			return null;
		}
	}
	
//
//	
//	
//	/**
//	 * @description get list site by id_sites
//	 * @author long.pham
//	 * @since 2021-02-02
//	 * @param arr id_sites
//	 */
//
//	public List getListIdSites(AlertEntity obj) {
//		try {
//			List rs = queryForList("Alert.getListIdSites", obj);
//			if (rs == null) {
//				return new ArrayList<>();
//			}
//			return rs;
//		} catch (Exception ex) {
//			return null;
//		}
//	}
//
//	/**
//	 * @description count total alert by site
//	 * @author long.pham
//	 * @since 2020-11-16
//	 * @param id_customer, id_site, start_date, end_date
//	 */
//
//	public int getListTotalCount(AlertEntity obj) {
//		try {
//			AlertEntity totalRecord = (AlertEntity) queryForObject("Alert.getTotal", obj);
//			return totalRecord.getTotalRecord();
//		} catch (Exception ex) {
//			return 0;
//		}
//	}
//
//	/**
//	 * @description get detail alert
//	 * @author long.pham
//	 * @since 2020-11-24
//	 * @param id_site, id_alert, id_customer, current_time
//	 * @return Object
//	 */
//
//	public Object getDetailAlert(AlertEntity obj) {
//		Object dataObj = null;
//		try {
//			dataObj = queryForObject("Alert.getDetailAlert", obj);
//			if (dataObj == null)
//				return new AlertEntity();
//		} catch (Exception ex) {
//			return new AlertEntity();
//		}
//		return dataObj;
//
//	}
//
//	/**
//	 * @description get alert Exists
//	 * @author long.pham
//	 * @since 2021-01-29
//	 * @param error_code, time
//	 */
//	public boolean checkAlertExist(AlertEntity dataE) {
//		try {
//			return (int) queryForObject("Alert.checkAlertlExist", dataE) > 0;
//		} catch (Exception e) {
//
//		}
//		return true;
//	}
//
//	/**
//	 * @description insert alert
//	 * @author long.pham
//	 * @since 2021-01-29
//	 * @param id
//	 */
//	public AlertEntity insertAlert(AlertEntity obj) {
//		try {
//			Object insertId = insert("Alert.insertAlert", obj);
//			if (insertId != null && insertId instanceof Integer) {
//				return obj;
//			} else {
//				return null;
//			}
//		} catch (Exception ex) {
//			log.error("Alert.insertAlert", ex);
//			return null;
//		}
//	}
//
//	/**
//	 * @description get list alert by site
//	 * @author long.pham
//	 * @since 2021-03-18
//	 * @param id_customer, id_site, start_date, end_date
//	 */
//
//	public List getListBySiteAdmin(AlertEntity obj) {
//		try {
//			List rs = queryForList("Alert.getListBySiteAdmin", obj);
//			if (rs == null) {
//				return new ArrayList<>();
//			}
//			return rs;
//		} catch (Exception ex) {
//			return null;
//		}
//	}
//
//	/**
//	 * @description get detail alert
//	 * @author long.pham
//	 * @since 2021-03-18
//	 * @param id_site
//	 * @return Object
//	 */
//
//	public SiteEntity getSiteDetail(AlertEntity obj) {
//		SiteEntity dataObj = new SiteEntity();
//		try {
//			dataObj = (SiteEntity) queryForObject("Alert.getSiteDetail", obj);
//			if (dataObj == null)
//				return new SiteEntity();
//		} catch (Exception ex) {
//			return new SiteEntity();
//		}
//		return dataObj;
//
//	}
//
//	/**
//	 * @description update error level status
//	 * @author long.pham
//	 * @since 2021-05-18
//	 * @param id
//	 */
//	public boolean updateStatus(AlertEntity obj) {
//		try {
//			return update("Alert.updateStatus", obj) > 0;
//		} catch (Exception ex) {
//			log.error("Alert.updateStatus", ex);
//			return false;
//		}
//	}
//
//	/**
//	 * @description update ack
//	 * @author long.pham
//	 * @since 2021-11-04
//	 * @param id
//	 */
//	public boolean updateACK(AlertHistoryEntity obj) {
//		try {
//			AlertHistoryEntity dataObj = new AlertHistoryEntity();
//			dataObj = (AlertHistoryEntity) queryForObject("Alert.getACKByEmplyee", obj);
//			if (dataObj == null) {
//				Object insertId = insert("Alert.insertAlertHistory", obj);
//				if (insertId != null && insertId instanceof Integer) {
//					return true;
//				} else {
//					return false;
//				}
//			} else {
//				// update time
//				return update("Alert.updateAlertHistory", obj) > 0;
//			}
//
//		} catch (Exception ex) {
//			log.error("Alert.updateStatus", ex);
//			return false;
//		}
//	}
//	
//	
//	/**
//	 * @description update alert
//	 * @author long.pham
//	 * @since 2021-11-05
//	 * @param id
//	 */
//	public boolean updateAlert(AlertEntity obj){
//		try{
//			return update("Alert.updateAlert", obj)>0;
//		}catch (Exception ex) {
//			log.error("Alert.updateAlert", ex);
//			return false;
//		}
//	}
//	
	
	/**
	 * @description get detail alert
	 * @author long.pham
	 * @since 2021-03-18
	 * @param id_site
	 * @return Object
	 */

	public AlertEntity getAlertSummary(AlertEntity obj) {
		AlertEntity dataObj = new AlertEntity();
		try {
			dataObj = (AlertEntity) queryForObject("Dashboard.getAlertSummary", obj);
			if (dataObj == null)
				return new AlertEntity();
		} catch (Exception ex) {
			return new AlertEntity();
		}
		return dataObj;

	}

    public List<EnergyEntity> getTotalEnergyToday(PortfolioEntity obj) {
        try {
            PortfolioService service = new PortfolioService();
            List<SiteEntity> sites = service.getSites(obj);
            if (sites.size() == 0) return new ArrayList<>();

            List<CompletableFuture<EnergyEntity>> futureList = new ArrayList<CompletableFuture<EnergyEntity>>();
            for (SiteEntity site : sites) {
                site.setDomain(obj.getDomain());

                CompletableFuture<EnergyEntity> future = CompletableFuture.supplyAsync(() -> {
                    EnergyEntity data = new EnergyEntity(site.getId_site(), site.getHash_id(), site.getName());

                    try {
                        if (site.getEnable_virtual_device() == 1) {
                            EnergyEntity energy = (EnergyEntity) queryForObject("Dashboard.getTotalEnergyTodayByVirtualDevice", site);
                            if (energy == null) return data;

                            data.setActual(energy.getActual());
                            data.setExpected(energy.getExpected());
                        } else {
                            CustomerViewService customerViewService = new CustomerViewService();
                            DevicesByTypeEntity devices = customerViewService.getDevicesBySite(site);
                            List<DeviceEntity> meters = devices.getMeter();
                            List<DeviceEntity> inverters = devices.getInverter();
                            List<DeviceEntity> irradiances = devices.getIrradiance();
                            List<DeviceEntity> powerDevices = meters.size() > 0 ? meters : inverters;

                            if (powerDevices != null && !powerDevices.isEmpty()) {
                                Double actual = (Double) queryForObject("Dashboard.getTotalEnergyTodayActualByDevice", powerDevices);
                                data.setActual(actual);
                            }
                            if (irradiances != null && !irradiances.isEmpty()) {
                                Double expected = (Double) queryForObject("Dashboard.getTotalEnergyTodayExpectedByDevice", irradiances.get(0));
                                data.setExpected(expected);
                            }
                        }

                        if (Objects.nonNull(data.getActual()) && Objects.nonNull(data.getExpected()) && data.getExpected() > 0)
                            data.setLoss((data.getExpected() - data.getActual()) / data.getExpected());

                    } catch (Exception e) {
                    }

                    return data;
                });
                futureList.add(future);
            }

            return futureList.stream().map(future -> future.join()).collect(Collectors.toList());
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public Map<String, Object> getTotalPowerAndCapacity(PortfolioEntity obj) {
        try {
            SitesMetricsSummaryEntity data = (SitesMetricsSummaryEntity) queryForObject("Dashboard.getTotalPowerAndCapacity", obj);
            if (data == null) {
                return null;
            }
            Map<String, Object> res = new HashMap<>();
            res.put("ac_capacity", data.getCapacity());
            res.put("active_power", data.getActivePower());
            return res;
        } catch (Exception e) {
            log.error("DashboardService.getTotalPowerAndCapacity", e);
        }
        return null;
    }

    public List<SiteEntity> getSiteMapData(Map<String, Object> obj) {
        try {
            List<SiteEntity> dataList = (List<SiteEntity>) queryForList("Dashboard.getSiteMapData", obj);
            if (dataList == null || dataList.isEmpty()) {
                return null;
            }
            return dataList;
        } catch (Exception e) {
            log.error("DashboardService.getSiteMapData", e);
        }
        return null;

    }

}
