/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.BuildingReportEntity;
import com.nwm.api.services.BuildingReportService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

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
	 * @author Duy.Phan
	 * @since 2024-08-12
	 * @param id_site
	 * @return data (status, message, array
	 */
	@PostMapping("/get-file-report")
	public Object getDataFileReport(@RequestBody BuildingReportEntity obj) {
		try {
			BuildingReportService service = new BuildingReportService();
			BuildingReportEntity siteDetail = service.getDataFileReport(obj);
			
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
	
}
