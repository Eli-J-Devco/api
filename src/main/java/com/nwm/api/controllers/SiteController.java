/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.TablePreferenceEntity;
import com.nwm.api.services.AWSService;
import com.nwm.api.services.SiteService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@RestController
@ApiIgnore
@RequestMapping("/site")
public class SiteController extends BaseController {
	
	@Autowired
	private AWSService awsService;
	
	/**
	 * @description Get detail site 
	 * @author long.pham
	 * @since 2020-10-22
	 * @param id_customer, id_site
	 * @return data (status, message, object, total_row
	 */

	@PostMapping("/get-summary-total-alert")
	public Object getSummaryTotalAlert(@RequestBody SiteEntity obj) {
		try {
			SiteService service = new SiteService();
			SiteEntity getDetailSite = service.getSummaryTotalAlert(obj);
			if (getDetailSite != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDetailSite, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	

	/**
	 * @description Get all site by id_customer
	 * @author long.pham
	 * @since 2022-01-29
	 * @param id_employee
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/site-by-employee")
	public Object getSiteByEmployee(@RequestBody SiteEntity site) {
		try {
			SiteService service = new SiteService();
			List data = service.getSiteByEmployee(site);
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get all site by id_customer
	 * @author long.pham
	 * @since 2022-01-29
	 * @param id_employee
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/site-by-employee-rec")
	public Object getSiteByEmployeeREC(@RequestBody SiteEntity site) {
		try {
			SiteService service = new SiteService();
			List data = service.getSiteByEmployeeREC(site);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get all site group by id_customer
	 * @author Hung.Bui
	 * @since 2023-07-21
	 * @param id_employee
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/site-group-by-employee")
	public Object getSiteGroupByEmployee(@RequestBody SiteEntity site) {
		try {
			SiteService service = new SiteService();
			List data = service.getSiteGroupByEmployee(site);
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get list site for page employee manage site
	 * @author long.pham
	 * @since 2021-01-07
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-employee-manage-list-site")
	public Object getListEmployeeManageSite(@RequestBody SiteEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			SiteService service = new SiteService();
			List data = service.getListEmployeeManageSite(obj);
			int totalRecord = service.getManageSiteTotalRecord(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	/**
	 * @description update Employee status
	 * @author long.pham
	 * @since 2021-01-08
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/add-manage-site-by-employee")
	public Object addEmployeeManageSite(@RequestBody SiteEntity obj) {
		try {
			SiteService service = new SiteService();
			if(obj.getId_site() > 0 && obj.getId_employee() > 0 ) {
				int checkExits = service.checkExitsManageSite(obj);
				if(checkExits <= 0) {
					// Insert
					SiteEntity data = service.insertSiteEmployeeMap(obj);
					return this.jsonResult(true,  Constants.SAVE_SUCCESS_MSG , data, 1);
				} else {
					// Delete
					service.deleteSiteEmployeeMap(obj);
					return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
				}
			} else {
				return this.jsonResult(false,  Constants.GET_ERROR_MSG , null, 0);
			}
			
			
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description save customer
	 * @author long.pham
	 * @since 2021-01-05
	 * @param  screen_mode = 0:add, 1:edit
	 */

	@PostMapping("/save")
	public Object saveRole(@Valid @RequestBody SiteEntity obj) {
		try {
			SiteService service = new SiteService();
			
			if(!Lib.isBlank(obj.getFile_upload())) {
				String saveDir = uploadRootPath() + "/" + Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyGallery);
				String fileName = randomAlphabetic(16);
				String saveFileName = Lib.uploadFromBase64(obj.getFile_upload(), fileName, saveDir);
				String filePath = awsService.uploadFile(saveDir + "/" + saveFileName, Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyGallery) + "/" + saveFileName);
				obj.setGallery(filePath);
			}
			
			if(!Lib.isBlank(obj.getFile_site_logo_upload())){
				String saveDir = uploadRootPath() + "/" + Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyGallery);
				String fileName = randomAlphabetic(16);
				String saveFileName = Lib.uploadFromBase64(obj.getFile_site_logo_upload(), fileName, saveDir);
				String filePath = awsService.uploadFile(saveDir + "/" + saveFileName, Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyGallery) + "/" + saveFileName);
				obj.setSite_logo(filePath);
			}
			
			if(!Lib.isBlank(obj.getFile_diagram_upload())) {
				String saveDir = uploadRootPath() + "/" + Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyDiagram);
				String fileName = randomAlphabetic(16);
				String saveFileName = Lib.uploadFromBase64(obj.getFile_diagram_upload(), fileName, saveDir);
				String filePath = awsService.uploadFile(saveDir + "/" + saveFileName, Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyDiagram) + "/" + saveFileName);
				obj.setDiagram(filePath);
			}
			
			if (obj.getScreen_mode() == 1) {
				SiteEntity data = service.insertSite(obj);
				return data != null ? this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1) : this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
			} else {
				boolean insert = service.updateSite(obj);
				return insert ? this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1) : this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
		}
	}
	
	
	/**
	 * @description Get list site by id_customer
	 * @author long.pham
	 * @since 2020-10-09
	 * @param id_customer
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list")
	public Object getList(@RequestBody SiteEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			SiteService service = new SiteService();
			List data = service.getList(obj);
			int totalRecord = service.getTotalRecord(obj);
			TablePreferenceEntity preference = service.getPreference(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord, preference);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0, null);
		}
	}
	
	
	/**
	 * @description update site status
	 * @author long.pham
	 * @since 2021-01-11
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-status")
	public Object updateStatus(@RequestBody SiteEntity obj) {
		try {
			SiteService service = new SiteService();
			service.updateStatus(obj);
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description delete site
	 * @author long.pham
	 * @since 2021-01-11
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete")
	public Object delete(@Valid @RequestBody SiteEntity obj) {
		SiteService service = new SiteService();
		try {
			boolean result = service.deleteEmployee(obj);
			if (result) {
				if (obj.getIs_delete() == 0) {
					return this.jsonResult(true, Constants.RESTORE_SUCCESS_MSG, obj, 1);
				}
				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get all site by id_customer
	 * @author long.pham
	 * @since 2020-10-08
	 * @param id_customer
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/all-by-employee")
	public Object getAllSiteByEmployee(@RequestBody SiteEntity site) {
		try {
			SiteService service = new SiteService();
			List data = service.getAllSiteByEmployee(site);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get all site by id_customer
	 * @author long.pham
	 * @since 2020-10-08
	 * @param id_customer
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/all")
	public Object getAllCompany(@RequestBody SiteEntity site) {
		try {
			SiteService service = new SiteService();
			List data = service.getAllSite(site);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	
	/**
	 * @description Get all site group
	 * @author Hung.Bui
	 * @since 2023-08-23
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/all-site-group")
	public Object getAllSiteGroup(@RequestBody SiteEntity site) {
		try {
			SiteService service = new SiteService();
			List data = service.getAllSiteGroup(site);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * @description Get list site by id_customer
	 * @author long.pham
	 * @since 2020-10-21
	 * @param id_customer
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/get-summary-site-by-customer")
	public Object getSummarySiteByCustomerId(@RequestBody SiteEntity obj) {
		try {
			SiteService service = new SiteService();
			SiteEntity getSiteCustomer = service.getSiteCustomerById(obj.getId_employee());
			if (getSiteCustomer != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getSiteCustomer, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get detail site 
	 * @author long.pham
	 * @since 2020-10-22
	 * @param id_customer, id_site
	 * @return data (status, message, object, total_row
	 */

	@PostMapping("/detail")
	public Object getDetailSite(@RequestBody SiteEntity obj) {
		try {
			SiteService service = new SiteService();
			SiteEntity getDetailSite = service.getDetailSite(obj);
			if (getDetailSite != null) {
				List dataActiveAlarm = service.getActiveAlarm(obj);
				getDetailSite.setActiveAlarm(dataActiveAlarm);
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDetailSite, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get chart kpi by site
	 * @author long.pham
	 * @since 2020-10-08
	 * @param id_customer
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-chart-kpi-day")
	public Object getChartKPIDay(@RequestBody SiteEntity obj) {
		try {

			SiteService service = new SiteService();
			String KPIType = obj.getKpi_type();
			switch(KPIType) {
			  case "month":
				  List dataMonth = service.getChartKPIMonth(obj);
				  obj.setEnergy(dataMonth);
				  
			    break;
			  case "year":
				  List dataYear = service.getChartKPIYear(obj);
				  obj.setEnergy(dataYear);
				  
			    break;
			  default:
				  List dataIrradiance = service.getChartKPIDayIrradiance(obj);
				  obj.setIrradiance(dataIrradiance);
				  
				  List dataPower = service.getChartKPIDayPower(obj);
				  obj.setPower(dataPower);
				  
				  List dataEnergy = service.getChartKPIDayEnergy(obj);
				  obj.setEnergy(dataEnergy);
				  
			}
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
     * @description  Update site information
     * @author long.pham
     * @since 2020-10-08
     * @Params id_site, id_customer
     */
	@PostMapping("/update")
	public Object save (HttpServletRequest request,@Valid @RequestBody SiteEntity site) {
		SiteService service = new SiteService();
		try {
			String fileName = "";
			String saveDir = "";
			if(!Lib.isBlank(site.getFile_upload())) {
				saveDir = uploadRootPath() +"/"+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKey);
				fileName = randomAlphabetic(16);
				String saveFileName = Lib.uploadFromBase64(site.getFile_upload(), fileName, saveDir);
				site.setGallery(Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKey)+"/"+saveFileName);
			}
			if(service.updateSiteInformation(site)) {
				return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, site, 1);
			}
			return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, e, 0);
		}
		
	}
	
	
	/**
	 * @description Get list data report quick query
	 * @author long.pham
	 * @since 2020-11-09
	 * @param id_site, id_customer, id_device
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/report-quick-query")
	public Object getReportQuickQuery(@RequestBody SiteEntity obj) {
		try {
			SiteService service = new SiteService();
			List data = service.reportQuickQuery(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	
	/**
	 * @description Get list data specific yield month
	 * @author long.pham
	 * @since 2020-11-10
	 * @param id_site, id_customer
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/specific-yield-month")
	public Object getSpecificYieldMonth(@RequestBody SiteEntity obj) {
		try {
			SiteService service = new SiteService();
			List data = service.getSpecificYieldMonth(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list data specific yield month
	 * @author long.pham
	 * @since 2020-11-10
	 * @param id_site, id_customer
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/specific-yield-year")
	public Object getSpecificYieldYear(@RequestBody SiteEntity obj) {
		try {
			SiteService service = new SiteService();
			List data = service.getSpecificYieldYear(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	/**
	 * @description Get daily report
	 * @author long.pham
	 * @since 2020-11-11
	 * @param id_customer, id_site, start_date, end_date
	 * @return data (status, message, object, total_row
	 */

	@PostMapping("/daily-report")
	public Object getDailyReportSumary(@RequestBody SiteEntity obj) {
		try {
			SiteService service = new SiteService();
			Object getDailyReport = service.getDailyReportSumary(obj);
			if (getDailyReport != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDailyReport, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list data specific yield month
	 * @author long.pham
	 * @since 2020-11-10
	 * @param id_site, id_customer, start_date, end_date
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/daily-report-chart")
	public Object getDailyReportChart(@RequestBody SiteEntity obj) {
		try {
			SiteService service = new SiteService();
			List data = service.getDailyReportChart(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	/**
	 * @description get list data report visualization device
	 * @author long.pham
	 * @since 2020-11-12
	 * @param id_site, id_customer, id_device, start_date, end_date
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/report-visualization-device")
	public Object getReportVisualizationDevice(@RequestBody SiteEntity obj) {
		try {
			SiteService service = new SiteService();
			List data = service.getReportVisualizationDevice(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description get list data report visualization device
	 * @author long.pham
	 * @since 2020-11-12
	 * @param id_site, id_customer, id_device, start_date, end_date
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/report-visualization-device-by-day")
	public Object getReportVisualizationDeviceDay(@RequestBody SiteEntity obj) {
		try {
			SiteService service = new SiteService();
			List data = service.getReportVisualizationDeviceDay(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description get list annual comparison
	 * @author long.pham
	 * @since 2020-11-12
	 * @param id_site, id_customer, id_device, start_date, end_date
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/annual-comparison")
	public Object getAnnualComparison(@RequestBody SiteEntity obj) {
		try {
			SiteService service = new SiteService();
			List data = service.getAnnualComparison(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	
}
