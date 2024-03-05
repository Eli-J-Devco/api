/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.EmployeeEntity;
import com.nwm.api.entities.EmployeeManageEntity;
import com.nwm.api.services.EmployeeService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SendMail;
import com.nwm.api.utils.Translator;

import springfox.documentation.annotations.ApiIgnore;
import javax.validation.Valid;
import com.nwm.api.services.AWSService;

@RestController
@ApiIgnore
@RequestMapping("/employee")
public class EmployeeController extends BaseController {
	@Autowired
	private AWSService awsService;
	/**
	 * @description Get list role
	 * @author long.pham
	 * @since 2021-01-06
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list")
	public Object getList(@RequestBody EmployeeManageEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			EmployeeService service = new EmployeeService();
			List data = service.getList(obj);
			int totalRecord = service.getTotalRecord(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description update Employee status
	 * @author long.pham
	 * @since 2021-01-06
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-status")
	public Object updateOwnAccountstatus(@RequestBody EmployeeManageEntity obj) {
		try {
			EmployeeService service = new EmployeeService();
			service.updateStatus(obj);
			return this.jsonResult(true, "Update status complate.", obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description save Employee
	 * @author long.pham
	 * @since 2021-01-06
	 * @param screen_mode = 0:add, 1:edit
	 */

	@PostMapping("/save")
	public Object saveRole(@Valid @RequestBody EmployeeManageEntity obj) {
		try {
			EmployeeService service = new EmployeeService();
			String fileName = "";
			String saveDir = "";
			String stringPassword = Lib.randomString(10);
			obj.setPassword(Lib.toMd5String(stringPassword));

			if (obj.getScreen_mode() == 1) {
				EmployeeManageEntity getEmployeeByEmail = service.getEmployeeByEmail(obj.getEmail());
				if (getEmployeeByEmail.getId() > 0) {
					return this.jsonResult(false, "Email already exists", null, 0);
				}

				if (!Lib.isBlank(obj.getFile_upload())) {
					saveDir = uploadRootPath() + "/" + Lib.getReourcePropValue(Constants.appConfigFileName,
							Constants.uploadFilePathConfigKeyAvatar);
					fileName = randomAlphabetic(16);
					String saveFileName = Lib.uploadFromBase64(obj.getFile_upload(), fileName, saveDir);
					String filePath = awsService.uploadFile(saveDir + "/" + saveFileName, Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyAvatar) + "/" + saveFileName);
					obj.setAvatar(filePath);
					
//					obj.setAvatar(Lib.getReourcePropValue(Constants.appConfigFileName,
//							Constants.uploadFilePathConfigKeyAvatar) + "/" + saveFileName);
				}

				EmployeeManageEntity data = service.insertEmployee(obj);
				if (data != null) {
					// Sent account info to email
					
					Calendar cal = Calendar.getInstance(TimeZone.getDefault());
					int expiredTime = Lib.strToInteger(Lib.getReourcePropValue(Constants.appConfigFileName, Constants.RESETPASSW_EXPIRED_TIME_KEY));
					cal.add(Calendar.MINUTE, expiredTime);
					Date now = cal.getTime();
					String strExpired = Lib.DateToString(now);
//					String link = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailResetPassword);
//					String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailFromContact);
//					link += "" + secretCard.encrypt(Integer.toString(getUserByEmail.getId())) + "." + secretCard.encrypt(strExpired);
					
					String hashId = secretCard.encrypt(obj.getEmail()).toLowerCase();
					String linkSetPassword = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailSetPassword);
//					String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName,
//							Constants.mailFromContact);
//
//					String msgTemplate = Constants.getMailTempleteByState(12);
//					String fullname = data.getFirst_name() + " " + data.getLast_name();
					String tokenSetpassword = Lib.hashString(UUID.randomUUID().toString());
					linkSetPassword += "" + hashId + "." + secretCard.encrypt(strExpired).toLowerCase() + "." + tokenSetpassword;
//					
					
					String link = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailSentPassword);
					String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName,
							Constants.mailFromContact);

					String msgTemplate = Constants.getMailTempleteByState(9);
					String username = obj.getEmail();
					String fullname = obj.getFirst_name() + " " + obj.getLast_name();
					String body = String.format(msgTemplate, fullname, username, stringPassword, link, linkSetPassword, "");
					String mailTo = obj.getEmail();
					String subject = Constants.getMailSubjectByState(9);

					String tags = "sent_password";
					String fromName = "NEXT WAVE ENERGY MONITORING INC";
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
					if (!Lib.isBlank(obj.getFile_upload())) {
						saveDir = uploadRootPath() + "/" + Lib.getReourcePropValue(Constants.appConfigFileName,
								Constants.uploadFilePathConfigKeyAvatar);
						fileName = randomAlphabetic(16);
						String saveFileName = Lib.uploadFromBase64(obj.getFile_upload(), fileName, saveDir);
						String filePath = awsService.uploadFile(saveDir + "/" + saveFileName, Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyAvatar) + "/" + saveFileName);
						obj.setAvatar(filePath);
						
//						obj.setAvatar(Lib.getReourcePropValue(Constants.appConfigFileName,
//								Constants.uploadFilePathConfigKeyAvatar) + "/" + saveFileName);
					}

					boolean checkEmployeeEmailExist = service.checkEmployeeEmailExist(obj);
					if (checkEmployeeEmailExist) {
						return this.jsonResult(false, "Email already exists", null, 0);
					}

					if (Lib.isBlank(obj.getPassword())) {
						EmployeeManageEntity getEmployeeById = service.getEmployeeById(obj.getId());
						if (getEmployeeById.getId() > 0) {
							obj.setPassword(getEmployeeById.getPassword());
						} else {
							return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
						}
					}

					boolean insert = service.updateEmployee(obj);
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
	 * @description save Employee
	 * @author long.pham
	 * @since 2021-04-06
	 */

	@PostMapping("/admin-reset-password")
	public Object adminSaveResetPassword(@Valid @RequestBody EmployeeManageEntity obj) {
		try {
			EmployeeService service = new EmployeeService();
			String stringPassword = Lib.randomString(10);
			obj.setPassword(Lib.toMd5String(stringPassword));

			EmployeeManageEntity getEmployeeByEmail = service.getEmployeeByEmail(obj.getEmail());
			if (getEmployeeByEmail.getId() <= 0) {
				return this.jsonResult(false, "Email does not exist", null, 0);
			}

			boolean data = service.updatePasswordEmployee(obj);
			if (data) {
				// Sent account info to email
				String link = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailSentPassword);
				String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName,
						Constants.mailFromContact);

				String msgTemplate = Constants.getMailTempleteByState(8);
				String username = obj.getEmail();
				String fullname = obj.getFirst_name() + " " + obj.getLast_name();
				
				Calendar cal = Calendar.getInstance(TimeZone.getDefault());
				int expiredTime = Lib.strToInteger(Lib.getReourcePropValue(Constants.appConfigFileName, Constants.RESETPASSW_EXPIRED_TIME_KEY));
				cal.add(Calendar.MINUTE, expiredTime);
				Date now = cal.getTime();
				String strExpired = Lib.DateToString(now);
				String hashId = secretCard.encrypt(obj.getEmail()).toLowerCase();
				String linkSetPassword = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailSetPassword);
				String tokenSetpassword = Lib.hashString(UUID.randomUUID().toString());
				linkSetPassword += "" + hashId + "." + secretCard.encrypt(strExpired).toLowerCase() + "." + tokenSetpassword;
				
				
				String body = String.format(msgTemplate, fullname, username, stringPassword, link, linkSetPassword, "");
				String mailTo = obj.getEmail();
				String subject = Constants.getMailSubjectByState(8);

				String tags = "sent_password";
				String fromName = "NEXT WAVE ENERGY MONITORING INC";
				String mailToBCC = "";
				String mailToCC = "";
//				boolean flagSent = SendMail.mailSMTPAmazon(mailFromContact, fromName, mailTo, subject, body, tags);
				boolean flagSent = SendMail.SendGmailTLS(mailFromContact, fromName, mailTo, mailToCC, mailToBCC, subject, body, tags);

				if (!flagSent) {
					throw new Exception(Translator.toLocale(Constants.SEND_MAIL_ERROR_MSG));
				}

				return this.jsonResult(true, Constants.ADMIN_RESET_PASSWORD, data, 1);
			} else {
				return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
			}

		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description delete Employee
	 * @author long.pham
	 * @since 2021-01-06
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete")
	public Object delete(@Valid @RequestBody EmployeeManageEntity obj) {
		EmployeeService service = new EmployeeService();
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
	 * @description Get all employee to dropdown
	 * @author long.pham
	 * @since 2021-01-08
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/all")
	public Object getAll(@RequestBody EmployeeEntity obj) {
		try {
			EmployeeService service = new EmployeeService();
			List data = service.getAll(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description update table columns in Portfolio
	 * @author duy.phan
	 * @since 2022-12-22
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-table-column")
	public Object updateTableColumn(@RequestBody EmployeeManageEntity obj) {
		try {
			EmployeeService service = new EmployeeService();
			boolean data = service.updateTableColumn(obj);
			return this.jsonResult(true, "Updated table column", data, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description get table columns in Portfolio
	 * @author duy.phan
	 * @since 2022-12-22
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-table-column")
	public Object getTableColumn(@RequestBody EmployeeManageEntity obj) {
		try {
			EmployeeService service = new EmployeeService();
			EmployeeManageEntity data = service.getTableColumn(obj.getId());
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description update display alert per page
	 * @author duy.phan
	 * @since 2023-07-24
	 * @param id, alert_per_page
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-alert-per-page")
	public Object updateAlertPerPage(@RequestBody EmployeeManageEntity obj) {
		try {
			EmployeeService service = new EmployeeService();
			service.updateAlertPerPage(obj);
			return this.jsonResult(true, "Update alert per page complete.", obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description get alert filter
	 * @author duy.phan
	 * @since 2022-12-22
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-alert-filter")
	public Object getAlertFilter(@RequestBody EmployeeManageEntity obj) {
		try {
			EmployeeService service = new EmployeeService();
			EmployeeManageEntity data = service.getAlertFilter(obj.getId());
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description update alert filter in alert
	 * @author duy.phan
	 * @since 2023-08-22
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-alert-filter")
	public Object updateAlertFilter(@RequestBody EmployeeManageEntity obj) {
		try {
			EmployeeService service = new EmployeeService();
			boolean data = service.updateAlertFilter(obj);
			return this.jsonResult(true, "Updated table column", data, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description update lock account
	 * @author duy.phan
	 * @since 2023-08-22
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-locked-account")
	public Object updateLockedAccount(@RequestBody EmployeeManageEntity obj) {
		try {
			EmployeeService service = new EmployeeService();
			boolean data = service.updateLockedAccount(obj);
			return this.jsonResult(true, "This Account is locked", data, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description update unlock account
	 * @author duy.phan
	 * @since 2023-08-22
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-unlocked-account")
	public Object updateUnlockedAccount(@RequestBody EmployeeManageEntity obj) {
		try {
			EmployeeService service = new EmployeeService();
			boolean data = service.updateUnlockedAccount(obj);
			return this.jsonResult(true, "This Account is unlocked", data, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description update unlock account
	 * @author duy.phan
	 * @since 2023-08-22
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	
	@PostMapping("/unlock-account-by-email")
	public Object updateUnlockAccountByEmail(@RequestBody EmployeeManageEntity obj) {
		try {
			EmployeeService service = new EmployeeService();

			if (!Lib.isBlank(obj.getHash_id_user())) {
				String hashId = obj.getHash_id_user();
				if (Lib.isBlank(hashId)) {
					return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
				}
				
				
				boolean data = service.updateUnlockedAccount(obj);
				return this.jsonResult(true, "This Account is unlocked", data, 1);
			}
				
			return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, e, 0);
		}
	}

}
