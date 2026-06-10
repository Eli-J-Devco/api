package com.nwm.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.AccountStatusEntity;
import com.nwm.api.services.AccountStatusService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/account-status")
public class AccountStatusController extends BaseController {
	/**
	 * @description get list account status by employee
	 * @author Hung.Bui
	 * @since 2023-03-24
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-by-employee")
	public Object getList(@RequestBody AccountStatusEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			AccountStatusService service = new AccountStatusService();
			List data = service.getList(obj);
			int totalRecord = service.getTotalRecord(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
}
