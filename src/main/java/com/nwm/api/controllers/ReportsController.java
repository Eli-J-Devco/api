/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.config.ReportTaskScheduler;
import com.nwm.api.entities.AssetManagementAndOperationPerformanceReportEntity;
import com.nwm.api.entities.AuditLog;
import com.nwm.api.entities.ReportDuplicateRequest;
import com.nwm.api.entities.ReportsEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.services.ReportsService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SendMail;
import com.nwm.api.utils.Translator;
import com.opencsv.CSVWriter;

import springfox.documentation.annotations.ApiIgnore;
//import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@ApiIgnore
@RequestMapping("/reports")
public class ReportsController extends BaseController {
				
	/**
	 * @description sent mail daily report
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-excel-daily-report")
	public Object sentMailDailyReport(@RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();
			return service.sentMailDailyReport(obj) ? this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1) : this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
		
	/**
	 * @description Get daily report
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/daily-report")
	public Object getDailyReport(@RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();
			ViewReportEntity dataObj = service.getDailyReport(obj);
			if (dataObj != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, dataObj, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description sent mail annually report
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-excel-annually-report")
	public Object sentMailAnnuallyReport(@RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();
			return service.sentMailAnnuallyReport(obj) ? this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1) : this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
		
	/**
	 * @description Get annually report
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/annually-report")
	public Object getAnnuallyReport(@RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();
			ViewReportEntity dataObj = service.getAnnuallyReport(obj);
			if (dataObj != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, dataObj, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
		
	/**
	 * @description sent mail monthly report
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-excel-quarterly-report")
	public Object sentMailQuarterlyReport(@RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();
			return service.sentMailQuarterlyReport(obj) ? this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1) : this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
	
	/**
	 * @description Get customer view chart data
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/quarterly-report")
	public Object getQuarterlyReport(@RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();

			ViewReportEntity dataObj = service.getQuarterlyReport(obj);

			if (dataObj != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, dataObj, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get asset management and performance report
	 * @author Hung.Bui
	 * @since 2024-06-10
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/asset-management-and-operation-performance-report")
	public Object getAssetManagementAndOperationPerformanceReport(@RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();

			AssetManagementAndOperationPerformanceReportEntity dataObj = service.getAssetManagementAndOperationPerformanceReport(obj);

			if (dataObj != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, dataObj, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description sent asset management and performance report
	 * @author Hung.Bui
	 * @since 2024-06-15
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-excel-asset-management-and-operation-performance-report")
	public Object sentMailAssetManagementAndOperationPerformanceReport(@RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();
			return service.sentMailAssetManagementAndOperationPerformanceReport(obj) ? this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1) : this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
	
	
	/**
	 * @description update site status
	 * @author long.pham
	 * @since 2021-01-11
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-site-rec-id")
	public Object updateStatus(@RequestBody SiteEntity obj) {
		try {
			ReportsService service = new ReportsService();
			service.updateRECID(obj);
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description update gu_id
	 * @author long.pham
	 * @since 2023-03-27
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-site-gu-id")
	public Object updateGUID(@RequestBody SiteEntity obj) {
		try {
			ReportsService service = new ReportsService();
			service.updateGUID(obj);
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get list site by employee
	 * @author long.pham
	 * @since 2021-01-20
	 * @param array id_site
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-site-rec")
	public Object getListREC(@RequestBody ReportsEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			ReportsService service = new ReportsService();
			List data = service.getListREC(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	@PostMapping("/render-excel-renewable-month")
	public Object excelRenewableMonth(@RequestBody ReportsEntity obj) {
		try {
			String[] header = {"REU ID", "GUID", "Vintage", "Begin Date", "End Date", "Generation (MWh)"};
	        List<String[]> list = new ArrayList<>();
//	        list.add(header);
	     
	        ReportsService service = new ReportsService();
			List data = service.getListREC(obj);
			if(data.size() > 0) {
				for (int i = 0; i < data.size(); i++) {
					Map<String, Object> item = (Map<String, Object>) data.get(i);
					String[] record = { 
							obj.getRecVersion() == 2 ? "" : (item.get("name") != null ? item.get("name").toString() : "") + " - " + (item.get("devicename") != null ? item.get("devicename").toString() : ""),
							" "+(item.get("ru_id") != null ? item.get("ru_id").toString() : ""),
							" "+(item.get("gu_id") != null ? item.get("gu_id").toString() : ""),
							" "+(item.get("vintage_date") != null ? item.get("vintage_date").toString() : ""),
							" "+(item.get("start_date") != null ? item.get("start_date").toString() : ""),
							" "+(item.get("end_date") != null ? item.get("end_date").toString() : ""),
							" "+(item.get("energy_this_month") != null ? item.get("energy_this_month").toString() : "")
							};
					list.add(record);
				}
				
				String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
				String dir = uploadRootPath() + "/"
						+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
				String fileName = dir + "/Renewable-energy-credits-" + timeStamp + ".csv";
				try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
		            writer.writeAll(list, false);
		            writer.flush();
		        }
				
				
				
				 

				String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName,
						Constants.mailFromContact);
				String msgTemplate = Constants.getMailTempleteByState(15);
				String body = String.format(msgTemplate, "Customer", "Renewable Energy Credits", "", "", "", "");
				String mailTo = obj.getSubscribers();
				String subject = Constants.getMailSubjectByState(15);
				String tags = "report_REC";
				String fromName = "NEXT WAVE ENERGY MONITORING INC";
				if(mailTo != null) {
					boolean flagSent = SendMail.SendGmailTLSAttachment(mailFromContact, fromName, mailTo, subject, body, tags, fileName);
					if (!flagSent) {
						throw new Exception(Translator.toLocale(Constants.SENT_EMAIL_ERROR));
					}
				}
				return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, data, 1);
			} else {
				return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
			}
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
	
	/**
	 * @description Get list site by employee
	 * @author long.pham
	 * @since 2021-12-27
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-site-by-customer")
	public Object getListSiteByEmployee(@RequestBody ReportsEntity obj, @RequestHeader(name = "Authorization") String authz) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			obj.setId_sites(Lib.sitesManagedByUser(authz).stream().map(item -> {
				Map<String, Integer> map = new HashMap<>();
				map.put("id", item);
				return map;
			}).collect(Collectors.toList()));
			ReportsService service = new ReportsService();
			List data = service.getListSiteByEmployee(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list site sub-group by employee
	 * @author Hung.Bui
	 * @since 2023-07-24
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-site-sub-group-by-customer")
	public Object getListSiteSubGroupByEmployee(@RequestBody ReportsEntity obj, @RequestHeader(name = "Authorization") String authz) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			obj.setId_sites(Lib.sitesManagedByUser(authz).stream().map(item -> {
				Map<String, Integer> map = new HashMap<>();
				map.put("id", item);
				return map;
			}).collect(Collectors.toList()));
			ReportsService service = new ReportsService();
			List data = service.getListSiteSubGroupByEmployee(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description save customer
	 * @author long.pham
	 * @since 2021-01-05
	 * @param screen_mode = 0:add, 1:edit
	 */
	@Autowired
	private ReportTaskScheduler reportTaskScheduler;

	@PostMapping("/save")
	public Object save(@Valid @RequestBody ReportsEntity obj, @RequestHeader(name = "Authorization") String authz) {
		try {
			ReportsService service = new ReportsService();
			if (obj.getScreen_mode() == 1) {
				obj.setCreated_by(Lib.getUserId(authz));
				ReportsEntity data = service.insertReports(obj);
				if (data != null) {
					// update scheduled task
					reportTaskScheduler.changeReportSchedule(obj.getId());
					return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
				} else {
					return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
				}
			} else {
				if (obj.getScreen_mode() == 2) {
					obj.setUpdated_by(Lib.getUserId(authz));
					boolean insert = service.updateReports(obj);
					if (insert == true) {
						// update scheduled task
						reportTaskScheduler.changeReportSchedule(obj.getId());
						return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
					} else {
						return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
					}
				} else {
					return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
				}
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description duplicate report
	 * @author Hung.Bui
	 * @since 2025-08-07
	 * @param obj { id }
	 */
	@PostMapping("/duplicate")
	public Object duplicate(@Valid @RequestBody ReportDuplicateRequest obj, @RequestHeader(name = "Authorization") String authz) {
		try {
			obj.setCreated_by(Lib.getUserId(authz));
			ReportsService service = new ReportsService();
			ReportDuplicateRequest data = service.duplicate(obj);
			return data != null ? this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data) : this.jsonResult(false, Constants.SAVE_ERROR_MSG, null);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null);
		}
	}
	
	/**
	 * @description download report
	 * @author Hung.Bui
	 * @since 2025-08-07
	 * @param obj { id }
	 */
	@PostMapping("/download")
	public ResponseEntity<Resource> download(@Valid @RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();
			Resource resource = service.download(obj);
			if (resource == null || !resource.exists()) return ResponseEntity.ok(null);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
		} catch (Exception e) {
			return ResponseEntity.ok(null);
		}
	}

	/**
	 * @description Get list report
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id_customer
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list")
	public Object getList(@RequestBody ReportsEntity obj) {
		try {
			ReportsService service = new ReportsService();
			List data = service.getList(obj);
			int totalRecord = service.getTotalRecord(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description delete site
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id
	 * @return obj
	 */
	@PostMapping("/delete")
	public Object delete(@Valid @RequestBody ReportsEntity obj, @RequestHeader(name = "Authorization") String authz) {
		ReportsService service = new ReportsService();
		try {
			obj.setUpdated_by(Lib.getUserId(authz));
			boolean result = service.deleteReports(obj);
			if (result) {
				// update scheduled task
				reportTaskScheduler.changeReportSchedule(obj.getId());
				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get report logs
	 * @author Hung.Bui
	 * @since 2025-08-19
	 * @param id
	 * @return obj
	 */
	@PostMapping("/logs")
	public Object getLogs(@Valid @RequestBody ReportsEntity obj, @RequestHeader(name = "Authorization") String authz) {
		try {
			ReportsService service = new ReportsService();
			List<AuditLog> data = service.getLogs(obj);
			
			if (Lib.isDemoUser(authz) || obj.getDomain().equals("demo.nextwavemonitoring.com")) {
				data.stream().forEach(item -> {
					item.setChanges(new ArrayList<>());
					item.setModifiedBy(null);
				});
			}
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
		}
	}

	/**
	 * @description Get customer view chart data
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/monthly-report")
	public Object getMonthlyReport(@RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();

			ViewReportEntity dataObj = service.getMonthlyReport(obj);

			if (dataObj != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, dataObj, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description sent mail monthly report
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-excel-monthly-report")
	public Object sentMailMonthlyReport(@RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();
			return service.sentMailMonthlyReport(obj) ? this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1) : this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
	
	/**
	 * @description Get custom report
	 * @author Hung.Bui
	 * @since 2022-12-15
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/custom-report")
	public Object getCustomReport(@RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();
			List<ViewReportEntity> dataObjList = service.getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, dataObjList, dataObjList.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
					
	/**
	 * @description sent mail custom report in excel
	 * @author Hung.Bui
	 * @since 2022-12-20
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-excel-custom-report")
	public Object sentMailCustomReport(@RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();
			return service.sentMailCustomReport(obj) ? this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1) : this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}

	/**
	 * Get sanity check report
	 * @author Hung.Bui
	 * @since 2025-06-25
	 * @param obj
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sanity-check-report")
	public Object getSanityCheckReport(@RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();
			ViewReportEntity dataObj = service.getSanityCheckReport(obj);
			
			if (dataObj == null) return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, dataObj, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		}
	}
	
	/**
	 * Sent mail sanity check report in excel
	 * @author Hung.Bui
	 * @since 2025-06-30
	 * @param obj
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-excel-sanity-check-report")
	public Object sentMailSanityCheckReport(@RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();
			return service.sentMailSanityCheckReport(obj) ? this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1) : this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
	
	/**
	 * Get meter level production irradiance temp report
	 * @author Duy.Phan
	 * @since 2025-09-10
	 * @param obj
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/meter-level-production-irradiance-temp-report")
	public Object getMeterLevelProductionIrradianceTempReport(@RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();
			ViewReportEntity dataObj = service.getMeterLevelProductionIrradianceTempReport(obj);
			
			if (dataObj == null) return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, dataObj, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		}
	}
	
	/**
	 * Sent mail meter level production irradiance temp report in excel
	 * @author Duy.Phan
	 * @since 2025-09-10
	 * @param obj
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-excel-meter-level-production-irradiance-temp-report")
	public Object sentMailMeterLevelProductionIrradianceTempReport(@RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();
			return service.sentMailMeterLevelProductionIrradianceTempReport(obj) ? this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1) : this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}

}
