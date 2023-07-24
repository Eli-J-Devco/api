/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.CustomerEntity;
import com.nwm.api.services.CustomerService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SendMail;
import com.nwm.api.utils.Translator;

import springfox.documentation.annotations.ApiIgnore;
import javax.validation.Valid;

@RestController
@ApiIgnore
@RequestMapping("/customer")
public class CustomerController extends BaseController {

	/**
	 * @description Get list role
	 * @author long.pham
	 * @since 2021-01-05
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list")
	public Object getList(@RequestBody CustomerEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			CustomerService service = new CustomerService();
			List data = service.getList(obj);
			int totalRecord = service.getTotalRecord(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description update customer status
	 * @author long.pham
	 * @since 2021-01-05
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-status")
	public Object updateOwnAccountstatus(@RequestBody CustomerEntity obj) {
		try {
			CustomerService service = new CustomerService();
			service.updateStatus(obj);
			return this.jsonResult(true, "Update status complate.", obj, 1);
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
	public Object saveRole(@Valid @RequestBody CustomerEntity obj) {
		try {
			CustomerService service = new CustomerService();
			String fileName = "";
			String saveDir = "";
			if (obj.getScreen_mode() == 1) {
				CustomerEntity getCustomerByEmail = service.getCustomerByEmail(obj.getEmail());
				
				if(getCustomerByEmail.getId() > 0) {
					return this.jsonResult(false, "Email already exists", null, 0);
				}
				
				if(!Lib.isBlank(obj.getFile_upload())) {
					saveDir = uploadRootPath() +"/"+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKey);
					fileName = randomAlphabetic(16);
					String saveFileName = Lib.uploadFromBase64(obj.getFile_upload(), fileName, saveDir);
					obj.setLogo(Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKey)+"/"+saveFileName);
				}
				
				String tokenSetpassword = Lib.hashString(UUID.randomUUID().toString());
				obj.setToken_setpassword(tokenSetpassword);
				CustomerEntity data = service.insertCustomer(obj);
				if (data != null) {
					// Create link customer set new password, sent to email
					String hashId = secretCard.encrypt(obj.getEmail()).toLowerCase();
					String link = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailSetPassword);
					String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName,
							Constants.mailFromContact);

					String msgTemplate = Constants.getMailTempleteByState(10);
					String fullname = data.getFirst_name() + " " + data.getLast_name();
					link += "" + hashId + "." + tokenSetpassword;
					String logoURL = Constants.logoURL;
					String body = String.format(msgTemplate, logoURL, fullname, link, "");
					String mailTo = obj.getEmail();
					String subject = Constants.getMailSubjectByState(10);
					String tags = "set_password";
					String fromName = "Set new password";
					String mailToBCC = "";
					String mailToCC = "";
//					boolean flagSent = SendMail.mailSMTPAmazon(mailFromContact, fromName, mailTo, subject, body, tags);
					boolean flagSent = SendMail.SendGmailTLS(mailFromContact, fromName, mailTo, mailToCC, mailToBCC, subject, body, tags);
					
					if (!flagSent) {
						throw new Exception(Translator.toLocale(Constants.SEND_MAIL_ERROR_MSG));
					}
					
					return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
				} else {
					return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
				}
			} else {
				if (obj.getScreen_mode() == 2) {
					if(!Lib.isBlank(obj.getFile_upload())) {
						saveDir = uploadRootPath() +"/"+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKey);
						fileName = randomAlphabetic(16);
						String saveFileName = Lib.uploadFromBase64(obj.getFile_upload(), fileName, saveDir);
						obj.setLogo(Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKey)+"/"+saveFileName);
					}
					
					boolean checkCustomerEmailExist = service.checkCustomerEmailExist(obj);
					if(checkCustomerEmailExist) {
						return this.jsonResult(false, "Email already exists", null, 0);
					}
					
					if(Lib.isBlank(obj.getPassword())) {
						CustomerEntity getCustomerById = service.getCustomerById(obj.getId());
						if(getCustomerById.getId() > 0) {
							obj.setPassword(getCustomerById.getPassword());
						} else {
							return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
						}
					}
					
					boolean insert = service.updateCustomer(obj);
					if (insert == true) {
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
	 * @description delete customer
	 * @author long.pham
	 * @since 2021-01-05
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete")
	public Object delete(@Valid @RequestBody CustomerEntity obj) {
		CustomerService service = new CustomerService();
		try {
			boolean result = service.deleteCustomer(obj);
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
	 * @description Get all customer to dropdown
	 * @author long.pham
	 * @since 2021-01-08
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/all")
	public Object getAll(@RequestBody CustomerEntity obj) {
		try {
			CustomerService service = new CustomerService();
			List data = service.getAll(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description admin sent reset link password
	 * @author long.pham
	 * @since 2021-01-05
	 * @param  screen_mode = 0:add, 1:edit
	 */

	@PostMapping("/admin-sent-reset-password")
	public Object adminSentLinkResetPassword(@Valid @RequestBody CustomerEntity obj) {
		try {
			CustomerService service = new CustomerService();
			CustomerEntity getCustomerByEmail = service.getCustomerByEmail(obj.getEmail());
			if(getCustomerByEmail == null) {
				return this.jsonResult(false, "Email does not exist", null, 0);
			}
			
			String tokenSetpassword = Lib.hashString(UUID.randomUUID().toString());
			obj.setToken_setpassword(tokenSetpassword);
			
			// Update token 
			boolean updateToken = service.customerUpdateToken(obj);
			if(!updateToken) {
				return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
			}
			
			// Create link customer set new password, sent to email
			String hashId = secretCard.encrypt(obj.getEmail()).toLowerCase();
			String link = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailSetPassword);
			String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName,
					Constants.mailFromContact);

			String msgTemplate = Constants.getMailTempleteByState(12);
			String fullname = obj.getFirst_name() + " " + obj.getLast_name();
			link += "" + hashId + "." + tokenSetpassword;
			String logoURL = Constants.logoURL;
			String body = String.format(msgTemplate, logoURL, fullname, link, "");
			String mailTo = obj.getEmail();
			String subject = Constants.getMailSubjectByState(12);
			String tags = "set_password";
			String fromName = "Set new password";
			String mailToBCC = "";
			String mailToCC = "";
//			boolean flagSent = SendMail.mailSMTPAmazon(mailFromContact, fromName, mailTo, subject, body, tags);
			boolean flagSent = SendMail.SendGmailTLS(mailFromContact, fromName, mailTo, mailToCC, mailToBCC, subject, body, tags);
			
			if (!flagSent) {
				throw new Exception(Translator.toLocale(Constants.SEND_MAIL_ERROR_MSG));
			}
			
			return this.jsonResult(true, "Submit request reset password successfully.", obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
	
}
