/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.BuildingReportEntity;
import com.nwm.api.services.BuildingReportService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;

@RestController
@ApiIgnore
@RequestMapping("/building-report")
public class BuildingReportController extends BaseController {
	
	
	/**
	 * @description Get data building report
	 * @author Long.Pham
	 * @since 2025-09-08
	 * @param id_site
	 * @return data (status, message, array
	 */
	@PostMapping("/get-data-report")
	public Object getDataBuildingReport(@RequestBody BuildingReportEntity obj) {
		try {

			BuildingReportService service = new BuildingReportService();

			BuildingReportEntity detail = service.getDataBuildingReport(obj);
			
			if (detail != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, detail, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	
	/**
	 * @description Get data building report
	 * @author Long.Pham
	 * @since 2025-09-08
	 * @param id_site
	 * @return data (status, message, array
	 */
	@PostMapping("/get-data-report-last-period")
	public Object getDataReportLastPeriod(@RequestBody BuildingReportEntity obj) {
		try {

			BuildingReportService service = new BuildingReportService();

			BuildingReportEntity detail = service.getDataReportLastPeriod(obj);
			
			if (detail != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, detail, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get data category statistic report
	 * @author Long.Pham
	 * @since 2025-09-08
	 * @param id_site
	 * @return data (status, message, array
	 */
	@PostMapping("/get-data-category-statistic-report")
	public Object getDataCategoryStatisticsReport(@RequestBody BuildingReportEntity obj) {
		try {

			BuildingReportService service = new BuildingReportService();

			BuildingReportEntity detail = service.getDataCategoryStatisticsReport(obj);
			
			if (detail != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, detail, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get data building report
	 * @author Long.Pham
	 * @since 2025-09-08
	 * @param id_site
	 * @return data (status, message, array
	 */
	@PostMapping("/get-data-weather-station-report")
	public Object getDataWeatherStationReport(@RequestBody BuildingReportEntity obj) {
		try {

			BuildingReportService service = new BuildingReportService();

			BuildingReportEntity detail = service.getDataWeatherStationReport(obj);
			
			if (detail != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, detail, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get data building report by type
	 * @author Long.Pham
	 * @since 2025-09-08
	 * @param id_site
	 * @return data (status, message, array
	 */
	@PostMapping("/get-data-report-by-type")
	public Object getDataBuildingReportByType(@RequestBody BuildingReportEntity obj) {
		try {

			BuildingReportService service = new BuildingReportService();

			BuildingReportEntity detail = service.getDataBuildingReportByType(obj);
			
			if (detail != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, detail, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description download report PDF file
	 * @author Minh Le
	 * @since 2025-11-11
	 * @return data (status, message, array
	 */
	@PostMapping("/download-pdf-file-report")
	public void getDataFileReport(@RequestBody BuildingReportEntity obj, HttpServletResponse response) {
		try {
			BuildingReportService service = new BuildingReportService();

            byte[] pdfBytes = service.getDataFileReport(obj);

            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;");
            response.setContentLength(pdfBytes.length);

            response.getOutputStream().write(pdfBytes);
            response.getOutputStream().flush();
        } catch (Exception e) {
			log.error(e);
            System.out.println("Download failed!");
		}
	}
	
}
