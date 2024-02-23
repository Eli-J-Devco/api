/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.EmployeeFilterFavoritesEntity;
import com.nwm.api.entities.EmployeeFilterRecentlyEntity;


public class SitesAnalyticsService extends DB {

	/**
	 * @description get list device by id_site
	 * @author long.pham
	 * @since 2022-02-22
	 * @param id_site
	 */
	public List getListDeviceBySite(DeviceEntity obj) {
		try {
			List dataListNew = new ArrayList();
			List<Map<String, Object>> dataList = queryForList("SitesAnalytics.getListDeviceBySite", obj);
			
			if(dataList.size() > 0) {
				Map<String, List> map = new HashMap<String, List>();
				map.put("list", dataList);
				List<Map<String, Object>> dataListParameter = queryForList("SitesAnalytics.getListDeviceParameter", map);
				dataList.forEach(item -> {
					item.put("dataParameter", dataListParameter.stream().filter(p -> p.get("id_device").equals(item.get("id"))).collect(Collectors.toList()));
					dataListNew.add(item);
				});
			}
			
			return dataListNew;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	/**
	 * @description get list device parameter
	 * @author long.pham
	 * @since 2022-02-22
	 * @param arr
	 */
	

	public List getChartParameterDevice(DeviceEntity obj) {
		try {
			List dataList = new ArrayList();
			List dataDevice = obj.getDataDevice();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			List<Map<String, String>> deviceGroupsList = queryForList("SitesAnalytics.getDeviceGroupsList", obj);
						
			if(dataDevice.size() > 0 && deviceGroupsList.size() > 0) {
				ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
				List<ListenableFuture<Map<String, Object>>> list = new ArrayList<ListenableFuture<Map<String, Object>>>();
				
				for(int i = 0; i < dataDevice.size(); i++) {
					int k = i;
					
					ListenableFuture<Map<String, Object>> future = executor.submit(new Callable<Map<String, Object>>() {
			            public Map<String, Object> call() throws Exception {
							Map<String, Object> map = (Map<String, Object>) dataDevice.get(k);
							Map<String, Object> maps = new HashMap<>();
							
							maps.put("filterBy", obj.getFilterBy());
							maps.put("start_date", obj.getStart_date());
							maps.put("end_date", obj.getEnd_date());
							int diff5Days = (int) ((dateFormat.parse(obj.getEnd_date()).getTime() - dateFormat.parse(obj.getStart_date()).getTime()) / (1000 * 60 * 60 * 24) + 1);
							maps.put("diff5Days", diff5Days <= 5 && diff5Days > 0);
							maps.put("data_send_time", obj.getData_send_time());
							Date dt = new Date();
							Calendar c = Calendar.getInstance(); 
							c.setTime(dt); 
							c.add(Calendar.MONTH, -3);
							SimpleDateFormat dateFor = new SimpleDateFormat("yyyy-MM-dd");
							Date d1 = dateFor.parse(obj.getStart_date());
							Date d2 = dateFor.parse(dateFor.format(c.getTime()));
							if(d1.compareTo(d2) < 0) {
								maps.put("datatablename", map.get("datatablename"));
							} else {
								maps.put("datatablename", map.get("view_tablename"));
							}
							maps.put("id", map.get("id"));
							maps.put("device_name", map.get("devicename"));
							maps.put("id_device_group", map.get("id_device_group"));
							maps.put("id_device_type", map.get("id_device_type"));
							
							// get list of time to exclude data from
							List hiddenDataList = queryForList("SitesAnalytics.getHiddenDataListByDevice", map);
							maps.put("hidden_data_list", hiddenDataList);
							
							// get device's common model table
							Map<String, String> modelTable = deviceGroupsList.stream().filter(deviceGroup -> map.get("datatablename").toString().endsWith(deviceGroup.get("table_name"))).findFirst().get();
							maps.put("table_name", modelTable.get("table_name"));
							
							if ((int) map.get("id_device_type") == 12) maps.put("datatablename", map.get("table_data_virtual"));
							
							List getDataChartParameter = queryForList("SitesAnalytics.getDataChartParameter", maps);
							
							maps.put("data", getDataChartParameter);
							return maps;
			            }
			        });
					
					list.add(future);
				}
				
			    ListenableFuture<List<Map<String, Object>>> combinedFutures = Futures.allAsList(list);
			    List<Map<String, Object>> deviceDataList = combinedFutures.get();
			    deviceDataList.forEach(data -> dataList.add(data));
			}
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
		
	}
	
	
	
	
	/**
	 * @description insert error level
	 * @author long.pham
	 * @since 2021-02-26
	 */
	public EmployeeFilterFavoritesEntity saveEmployeeFilterFavorites(EmployeeFilterFavoritesEntity obj) {
		try {
			// Save
			Object insertId = insert("EmployeeFilterFavorites.saveFilterFavorites", obj);
			if (insertId != null && insertId instanceof Integer) {
				Object total = queryForObject("EmployeeFilterFavorites.getListCount", obj);
				if((int)total > 10) {
					// Delete one row
					delete("EmployeeFilterFavorites.deleteFilterFavorites", obj);
				}
				return obj;
			} else {
				return null;
			}

		} catch (Exception ex) {
			log.error("insert", ex);
			return null;
		}
	}
	
	
	/**
	 * @description insert error level
	 * @author long.pham
	 * @since 2022-05-03
	 */
	public EmployeeFilterRecentlyEntity saveRecentlyUsedFilter(EmployeeFilterRecentlyEntity obj) {
		try {
			// Save
			Object insertId = insert("EmployeeFilterRecently.saveRecentlyUsedFilter", obj);
			if (insertId != null && insertId instanceof Integer) {
				Object total = queryForObject("EmployeeFilterRecently.getListCount", obj);
				
				if((int)total > 10) {
					// Delete one row
					delete("EmployeeFilterRecently.deleteRecentlyUsedFilter", obj);
				}
				
				return obj;
			} else {
				return null;
			}

		} catch (Exception ex) {
			log.error("insert", ex);
			return null;
		}
	}
	
	
	
	/**
	 * @description get list device by id_site
	 * @author long.pham
	 * @since 2021-03-16
	 * @param id_site, id_device
	 */
	

	public List getListEmployeeFilter(EmployeeFilterFavoritesEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("EmployeeFilterCharting.getListEmployeeFilter", obj);
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	/**
	 * @description get list device by id_site
	 * @author long.pham
	 * @since 2021-03-16
	 * @param id_site, id_device
	 */
	

	public List getListRecently(EmployeeFilterRecentlyEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("EmployeeFilterRecently.getListRecently", obj);
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	
	/**
	 * @description get list favorites by id_site
	 * @author long.pham
	 * @since 2022-05-03
	 * @param id_site
	 */
	

	public List getListFavorites(EmployeeFilterFavoritesEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("EmployeeFilterFavorites.getListFavorites", obj);
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
}
