/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.nwm.api.entities.SitesAnalyticsReportEntity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SendMail;
import com.nwm.api.utils.Translator;
import org.springframework.web.bind.annotation.*;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.EmployeeChartFilterEntity;
import com.nwm.api.services.SitesAnalyticsService;
import com.nwm.api.utils.Constants;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/sites-analytics")
public class SitesAnalyticsController extends BaseController {
	
	/**
	 * @description Get list device by site
	 * @author long.pham
	 * @since 2022-02-09
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-device-by-site")
	public Object getListDeviceBySite(@RequestBody DeviceEntity obj) {
		try {
			SitesAnalyticsService service = new SitesAnalyticsService();
			List data = service.getListDeviceBySite(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get list device parameter
	 * @author long.pham
	 * @since 2022-02-09
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/chart-parameter-device")
	public Object getChartParameterDevice(@RequestBody DeviceEntity obj) {
		try {
			SitesAnalyticsService service = new SitesAnalyticsService();
			List data = service.getChartParameterDevice(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description get filter list
	 * @author Hung.Bui
	 * @since 2024-06-07
	 * @param obj { id_employee, hash_id_site }
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/get-list-filter")
	public Object getListFilter(@RequestBody EmployeeChartFilterEntity obj) {
		try {
			SitesAnalyticsService service = new SitesAnalyticsService();
			List<EmployeeChartFilterEntity> data = service.getListFilter(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description save filter
	 * @author Hung.Bui
	 * @since 2024-06-07
	 * @param obj { id_employee, hash_id_site, params, created_date, name, is_favorite }
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/save-filter")
	public Object saveFilter(@RequestBody EmployeeChartFilterEntity obj) {
		try {
			SitesAnalyticsService service = new SitesAnalyticsService();
			EmployeeChartFilterEntity data = service.saveFilter(obj);
			
			if (data != null) {
				return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
			} else {
				return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Send PDF Report
	 * @author nhan.tran
	 * @since 2024-05-28
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/send-pdf-report")
	public @ResponseBody Object sendPDFReport(@RequestBody SitesAnalyticsReportEntity obj) {
		try {
			String fileName = URLEncoder.encode(obj.getFileName() + "." + obj.getFileType(), "UTF-8");
			String htmlData = Lib.unzip(org.apache.commons.codec.binary.Base64.decodeBase64(obj.getHtml()));
			String email = obj.getEmail();
			String title = obj.getTitle() != null ? obj.getTitle() : "Custom Reports";

			if (!email.matches("^(|([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5}){1,25})+(,(\\s*)(([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5}){1,25})+)*$"))
				throw new IllegalArgumentException(Constants.VALIDATE_EMAIL);

			if (Lib.isBlank(fileName) || Lib.isBlank(htmlData) || Lib.isBlank(email)) {
                throw new Exception(Translator.toLocale(Constants.SENT_EMAIL_ERROR));
            }

			File file = new File(fileName);
			PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file));
			pdfDocument.setDefaultPageSize(PageSize.A3);
			HtmlConverter.convertToPdf(htmlData, pdfDocument, new ConverterProperties());

			String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailFromContact);
			String msgTemplate = Constants.getMailTempleteByState(16);
			String body = String.format(msgTemplate,
					obj.getSiteName(),
					obj.getId(),
					obj.getCustomerName(), "Custom ", "", "");

            String tags = "customReport";
			String fromName = "NEXT WAVE ENERGY MONITORING INC";
			boolean flagSent = SendMail.SendGmailTLSAttachment(mailFromContact, fromName, email, title, body, tags, fileName);
			if (!flagSent) {
				throw new Exception(Translator.toLocale(Constants.SENT_EMAIL_ERROR));
			}

			pdfDocument.close();
			if (file.delete()){
				log.info("Deleted file " + fileName);
			} else {
				log.error("Failed to delete file " + fileName);
			}

            return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
		} catch (IllegalArgumentException e) {
			log.error(e);
            return this.jsonResult(false, e.getMessage(), e, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
}
