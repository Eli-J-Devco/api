package com.nwm.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.AuditingLogsEntity;
import com.nwm.api.services.AuditingLogsService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/auditing-logs")
public class AuditingLogsController extends BaseController {
	/**
	 * @description get list auditing logs by employee
	 * @author Hung.Bui
	 * @since 2023-04-25
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-by-employee")
	public Object getList(@RequestBody AuditingLogsEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			AuditingLogsService service = new AuditingLogsService();
			List data = service.getList(obj);
			int totalRecord = service.getTotalRecord(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description get list auditing logs by site
	 * @author duy.phan
	 * @since 2023-05-10
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-by-site")
	public Object getListBySite(@RequestBody AuditingLogsEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			AuditingLogsService service = new AuditingLogsService();
			List data = service.getListBySite(obj);
			int totalRecord = service.getTotalRecordBySite(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description save auditing log
	 * @author Hung.Bui
	 * @since 2023-04-25
	 * @param
	 */
	@PostMapping("/save")
	public Object save(@Valid @RequestBody AuditingLogsEntity obj) {
		try {
			AuditingLogsService service = new AuditingLogsService();
			AuditingLogsEntity data = service.insertAccountStatus(obj);
			if (data != null) {
				// delete old records, only keep specific number of latest records
				List<Integer> latestRecordList = service.getLatestRecordsByEmployee(obj);
				if (latestRecordList != null) {
					String latestRecordListString = latestRecordList.toString();
					latestRecordListString = latestRecordListString.substring(1, latestRecordListString.length() - 1);
					obj.setLatest_records(latestRecordListString);
					service.deleteOldRecordsByEmployee(obj);
				}
				
				return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
			} else {
				return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description get list all auditing logs 
	 * @author duy.phan
	 * @since 2023-07-31
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list")
	public Object getListAll(@RequestBody AuditingLogsEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			AuditingLogsService service = new AuditingLogsService();
			List data = service.getListAll(obj);
			int totalRecord = service.getTotalAllRecord(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
}
