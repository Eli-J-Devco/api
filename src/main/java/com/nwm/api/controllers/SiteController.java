/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.AuditLog;
import com.nwm.api.entities.SiteAreaBuildingFloorRoomEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.SiteGasWaterElectricityRateScheduleEntity;
import com.nwm.api.services.AWSService;
import com.nwm.api.services.EmployeeService;
import com.nwm.api.services.SiteService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SendMail;

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
	public Object getSummaryTotalAlert(@RequestBody SiteEntity obj, @RequestHeader(name = "Authorization") String authz) {
		try {
			obj.setIsUserNW(Lib.isUserNW(authz));
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
	
	
	public static String getSHAHash(String input) {
	    try {
	      MessageDigest md = MessageDigest.getInstance("SHA-1");
	      byte[] messageDigest = md.digest(input.getBytes());
	      return convertByteToHex(messageDigest);
	    } catch (NoSuchAlgorithmException e) {
	      throw new RuntimeException(e);
	    }
  }
	
public static String convertByteToHex(byte[] data) {
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < data.length; i++) {
	      sb.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
	    }
	    return sb.toString();
  }


	/**
	 * @description save customer
	 * @author long.pham
	 * @since 2021-01-05
	 * @param  screen_mode = 0:add, 1:edit
	 */

	@PostMapping("/save")
	public Object saveRole(@Valid @RequestBody SiteEntity obj, @RequestHeader(name = "Authorization") String authz) {
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
				obj.setCreated_by(Lib.getUserId(authz));
				SiteEntity data = service.insertSite(obj);
				
				// Send mail to customer 
				if(data != null) {
					String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName,
							Constants.mailFromContact);
					
					String hashedText = getSHAHash(String.valueOf(data.getId()));

					String msgTemplate = Constants.getMailTempleteByState(23);
					String domain = "https://" + obj.getDomain() + "/management/sites/"+ hashedText +"/dashboard";
					String body = String.format(msgTemplate, domain, obj.getName());
					String mailTo = obj.getMail_to();
					String subject = obj.getName() + " has been added to your account.";

					String tags = "notify_add_site";
					String fromName = "NEXT WAVE ENERGY MONITORING INC";
					String mailToBCC = "";
					String mailToCC = obj.getMail_cc();
					SendMail.SendGmailTLS(mailFromContact, fromName, mailTo, mailToCC, mailToBCC, subject, body, tags);
				}
				
				return data != null ? this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1) : this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
			} else {
				obj.setUpdated_by(Lib.getUserId(authz));
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
			(new EmployeeService()).getTableSort(obj);
			SiteService service = new SiteService();
			List data = service.getList(obj);
			int totalRecord = service.getTotalRecord(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get site detail
	 * @author Duy.Phan
	 * @since 2024-08-12
	 * @param id_site
	 * @return data (status, message, array
	 */
	@PostMapping("/site-detail")
	public Object getSiteDetail(@RequestBody SiteEntity obj) {
		try {

			SiteService service = new SiteService();

			SiteEntity siteDetail = service.getSiteDetail(obj);
			
			if (siteDetail != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, siteDetail, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
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
	public Object delete(@Valid @RequestBody SiteEntity obj, @RequestHeader(name = "Authorization") String authz) {
		SiteService service = new SiteService();
		try {
			String mailCC = service.getEmailCC(obj);
			
			obj.setUpdated_by(Lib.getUserId(authz));
			boolean result = service.deleteEmployee(obj);
			if (result) {
				if(obj.getMail_to() != null) {
					// send mail
					String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName,
							Constants.mailFromContact);
					String msgTemplate = Constants.getMailTempleteByState(24);
					String body = String.format(msgTemplate, obj.getName());
					String mailTo = obj.getMail_to();
					String subject = obj.getName() + " has been deleted from your account.";

					String tags = "notify_add_site";
					String fromName = "NEXT WAVE ENERGY MONITORING INC";
					String mailToBCC = "";
					String mailToCC = mailCC;
					SendMail.SendGmailTLS(mailFromContact, fromName, mailTo, mailToCC, mailToBCC, subject, body, tags);
					
				}
				
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
	
	/**
	 * @description Get list filter alert by id_employeee
	 * @author duy.phan
	 * @since 2023-07-18
	 * @return data (status, message, object, total_row
	 */
	@PostMapping("/get-site-per-page")
	public Object getSitePerPage(@RequestBody SiteEntity obj) {
		try {
			SiteService service = new SiteService();
			Object detailObj = service.getSitePerPage(obj);
			if (detailObj != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, detailObj, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get site logs
	 * @author Hung.Bui
	 * @since 2025-09-05
	 * @param id
	 * @return obj
	 */
	@PostMapping("/logs")
	public Object getLogs(@Valid @RequestBody SiteEntity obj) {
		try {
			SiteService service = new SiteService();
			List<AuditLog> data = service.getLogs(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
		}
	}
	
	/**
	 * @description delete area
	 * @author Duy.Phan
	 * @since 2024-06-03
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete-site-area")
	public Object deleteSiteArea(@Valid @RequestBody SiteAreaBuildingFloorRoomEntity obj) {
		SiteService service = new SiteService();
		try {
			boolean result = service.deleteSiteArea(obj);
			if (result) {
				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description delete building
	 * @author Duy.Phan
	 * @since 2024-06-03
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete-site-area-building")
	public Object deleteSiteAreaBuilding(@Valid @RequestBody SiteAreaBuildingFloorRoomEntity obj) {
		SiteService service = new SiteService();
		try {
			boolean result = service.deleteSiteAreaBuilding(obj);
			if (result) {
				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description delete unit 
	 * @author Duy.Phan
	 * @since 2024-06-03
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete-site-area-building-floor")
	public Object deleteSiteAreaBuildingFloor(@Valid @RequestBody SiteAreaBuildingFloorRoomEntity obj) {
		SiteService service = new SiteService();
		try {
			boolean result = service.deleteSiteAreaBuildingFloor(obj);
			if (result) {
				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description delete floor
	 * @author Duy.Phan
	 * @since 2024-06-03
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete-site-area-building-floor-room")
	public Object deleteSiteAreaBuildingFloorRoom(@Valid @RequestBody SiteAreaBuildingFloorRoomEntity obj) {
		SiteService service = new SiteService();
		try {
			boolean result = service.deleteSiteAreaBuildingFloorRoom(obj);
			if (result) {
				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description delete water rate schedule
	 * @author Duy.Phan
	 * @since 2024-06-03
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete-site-water-rate-schedule")
	public Object deleteSiteWaterRateSchedule(@Valid @RequestBody SiteGasWaterElectricityRateScheduleEntity obj) {
		SiteService service = new SiteService();
		try {
			boolean result = service.deleteSiteWaterRateSchedule(obj);
			if (result) {
				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description delete gas rate schedule
	 * @author Duy.Phan
	 * @since 2024-06-03
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete-site-gas-rate-schedule")
	public Object deleteSiteGasrRateSchedule(@Valid @RequestBody SiteGasWaterElectricityRateScheduleEntity obj) {
		SiteService service = new SiteService();
		try {
			boolean result = service.deleteSiteGasRateSchedule(obj);
			if (result) {
				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description delete electricity rate schedule
	 * @author Duy.Phan
	 * @since 2024-06-03
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete-site-electricity-rate-schedule")
	public Object deleteSiteElectricityRateSchedule(@Valid @RequestBody SiteGasWaterElectricityRateScheduleEntity obj) {
		SiteService service = new SiteService();
		try {
			boolean result = service.deleteSiteElectricityRateSchedule(obj);
			if (result) {
				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description update bem overview tab
	 * @author duy.phan
	 * @since 2022-12-22
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-bems-overview-tab")
	public Object updateBemsOverviewTab(@RequestBody SiteEntity obj) {
		try {
			SiteService service = new SiteService();
			boolean data = service.updateBemsOverviewTab(obj);
			return this.jsonResult(true, "Updated BEMS Overview Tab", data, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
}
