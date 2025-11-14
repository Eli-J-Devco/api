/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.CustomerSupportEntity;
import com.nwm.api.services.AWSService;
import com.nwm.api.services.CustomerSupportService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SendMail;
import com.nwm.api.utils.Translator;
import com.opencsv.CSVWriter;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/customer-support")
public class CustomerSupportController extends BaseController {
	
	@Autowired
	private AWSService awsService;

	/**
	 * @description save customer support
	 * @author long.pham
	 * @since 2021-12-20
	 * @param object
	 */

	@PostMapping("/save")
	public Object save(@Valid @RequestBody CustomerSupportEntity obj) {
		try {
			CustomerSupportService service = new CustomerSupportService();
			String fileName = "";
			String saveDir = "";
			List files = new ArrayList();

            if (obj.getScreen_mode() != 1) {
                return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
            }

            List fileUploads = obj.getFileUploads();
            if (fileUploads.size() > 0) {
                for (int i = 0; i < fileUploads.size(); i++) {
                    Map<String, Object> objFile = (Map<String, Object>) fileUploads.get(i);
                    saveDir = uploadRootPath() + "/" + Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeySupport);
                    fileName = randomAlphabetic(16) + "-" + new Date().getTime();
                    String saveFileName = Lib.uploadFromBase64(objFile.get("file_upload").toString(), fileName, saveDir);
                    String filePath = awsService.uploadFile(saveDir + "/" + saveFileName, Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeySupport) + "/" + saveFileName);
                    objFile.put("file_name", filePath);
                    files.add(saveDir + "/"+ saveFileName);
                }
            }
            obj.setFileUploads(fileUploads);
            CustomerSupportEntity data = service.insertCustomerSupport(obj);
            if (data == null) {
                return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
            }
            String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailFromContact);
            String msgTemplate = Constants.getMailTempleteByState(22);
            String body = "";
            if (!Lib.isBlank(msgTemplate)) {
                body = String.format(msgTemplate, obj.getWe_support(), obj.getSite_name(), obj.getIssue_name(), obj.getContact_person(), obj.getAccount_name(),obj.getPhone(), obj.getEmail(), obj.getSubject(), obj.getNote());
            }
            // for test
            String mailTo = "yphu@nwemon.com"; //"cases@nwemon.com";
            String subject = Constants.getMailSubjectByState(22);
            if (!Lib.isBlank(subject)) {
                subject = String.format(subject, obj.getSite_name(), obj.getIssue_name());
            }
            String tags = "support_ticket";
            String fromName = "NEXT WAVE ENERGY MONITORING INC";
            if(!Lib.isBlank(mailTo) && !Lib.isBlank(body)) {
                boolean flagSent = SendMail.SendGmailTLSAttachmentMultiFiles(mailFromContact, fromName, mailTo, subject, body, tags, files);
                if (!flagSent) {
                    throw new Exception(Translator.toLocale(Constants.SENT_EMAIL_ERROR));
                }
                // if send mail to admin successfully, send mail to notify customer
                msgTemplate = Constants.getMailTempleteByState(27);
                if (!Lib.isBlank(msgTemplate)) {
                    body = String.format(msgTemplate, obj.getContact_person());
                }
                // for test
                mailTo = "yphu@nwemon.com"; // obj.getEmail();
                subject = Constants.getMailSubjectByState(27);
                if (!Lib.isBlank(subject)) {
                    subject = String.format(subject, obj.getAccount_name(), obj.getSite_name());
                }
                SendMail.SendGmailTLSAttachmentMultiFiles(mailFromContact, fromName, mailTo, subject, body, tags, files);
            }

            return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list support tickets
	 * @author Duy.Phan
	 * @since 2024-08-14
	 * @param {}
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list")
	public Object getList(@RequestBody CustomerSupportEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			CustomerSupportService service = new CustomerSupportService();
			List data = service.getList(obj);
			int totalRecord = service.getTotalRecord(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description update icon status
	 * @author long.pham
	 * @since 2021-03-31
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-status")
	public Object updateStatus(@RequestBody CustomerSupportEntity obj) {
		try {
			CustomerSupportService service = new CustomerSupportService();
			service.updateStatus(obj);
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

}
