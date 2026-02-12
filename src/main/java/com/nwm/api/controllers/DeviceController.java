/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.controllers;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.services.ApiAccessService;
import com.nwm.api.services.DeviceService;
import com.nwm.api.services.ThirdPartyAPIService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;
@RestController
@ApiIgnore
@RequestMapping("/device")
public class DeviceController extends BaseController {

	/**
	 * @description Get list device by id_site
	 * @author long.pham
	 * @since 2021-01-12
	 * @param id_site
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-device-by-site")
	public Object getListDeviceBySite(@RequestBody DeviceEntity obj) {
		try {
			DeviceService service = new DeviceService();
			List data = service.getListDeviceBySite(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description Get device by device type
	 * @author long.pham
	 * @since 2020-11-06
	 * @param array id_device
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/list-by-id-device-type")
	public Object getList(@RequestBody DeviceEntity obj) {
		try {
			DeviceService service = new DeviceService();
			List data = service.getListByDeviceType(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description Get list device by device group
	 * @author long.pham
	 * @since 2020-11-12
	 * @param id_site, id_device, id_device_group = 3
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/list-device-by-id-group")
	public Object getListDeviceByGroup(@RequestBody DeviceEntity obj) {
		try {
			DeviceService service = new DeviceService();
			List data = service.getListDeviceByGroup(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}


	/**
	 * @description update device status
	 * @author long.pham
	 * @since 2021-01-12
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-status")
	public Object updateStatus(@RequestBody DeviceEntity obj) {
		try {
			DeviceService service = new DeviceService();
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
	public Object delete(@RequestBody DeviceEntity obj) {
		DeviceService service = new DeviceService();
		try {
			boolean result = service.deleteDevice(obj);
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


	@PostMapping("/save")
	public Object saveDevice(@Valid @RequestBody DeviceEntity obj) {
		try {
			DeviceService service = new DeviceService();
			if (obj.getScreen_mode() == 1) {

				DeviceEntity data = service.insertDevice(obj);
				if (data != null) {
					return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
				} else {
					return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
				}
			} else {
				if (obj.getScreen_mode() == 2) {
					boolean update = service.updateDevice(obj);
					if (update == true) {
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
	 * @description Get list hidden data by device
	 * @author Hung.Bui
	 * @since 2023-08-03
	 * @param id_device
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/list-hidden-data-by-device")
	public Object getListHiddenDataByDevice(@RequestBody DeviceEntity obj) {
		try {
			DeviceService service = new DeviceService();
			List data = service.getListHiddenDataByDevice(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description add hidden data
	 * @author Hung.Bui
	 * @since 2023-08-03
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/add-hidden-data")
	public Object addHiddenData(@RequestBody DeviceEntity obj) {
		DeviceService service = new DeviceService();
		try {
			DeviceEntity data = service.insertHiddenData(obj);
			if (data != null) {
				return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
			} else {
				return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description delete hidden data
	 * @author Hung.Bui
	 * @since 2023-08-03
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete-hidden-data")
	public Object deleteHiddenData(@RequestBody DeviceEntity obj) {
		DeviceService service = new DeviceService();
		try {
			boolean result = service.deleteHiddenData(obj);
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
	 * @description Get list device parameter
	 * @author Hung.Bui
	 * @since 2023-08-28
	 * @param id_device
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/list-device-parameter")
	public Object getListDeviceParameter(@RequestBody DeviceEntity obj) {
		try {
			DeviceService service = new DeviceService();
			List data = service.getListDeviceParameter(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description Get list device parameter to scale old data
	 * @author Hung.Bui
	 * @since 2023-08-28
	 * @param id_device
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/list-device-parameter-scale-old-data")
	public Object getListDeviceParameterScaleOldData(@RequestBody DeviceEntity obj) {
		try {
			DeviceService service = new DeviceService();
			List data = service.getListDeviceParameterScaleOldData(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description Get list device filter parameter
	 * @author Hung.Bui
	 * @since 2024-03-06
	 * @param id_device
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/list-device-filter-parameter")
	public Object getListDeviceFilterParameter(@RequestBody DeviceEntity obj) {
		try {
			DeviceService service = new DeviceService();
			List data = service.getListDeviceFilterParameter(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description Get list device parameter by device group
	 * @author duy.phan
	 * @since 2024-01-15
	 * @param id_device
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/list-scaled-parameter-by-device-group")
	public Object getListScaledParameterByDeviceGroup(@RequestBody DeviceEntity obj) {
		try {
			DeviceService service = new DeviceService();
			List data = service.getListScaledParameterByDeviceGroup(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description update device parameter scale
	 * @author Hung.Bui
	 * @since 2023-08-28
	 * @param id_device, id_device_parameter, slope, offset
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/save-device-parameter-scale")
	public Object saveDeviceParameterScale(@RequestBody DeviceEntity obj) {
		try {
			// check whether user input correct scale or not
			ValidationResult expression = new ExpressionBuilder(obj.getParameter_scale()).variable(obj.getVariable_name()).build().setVariable(obj.getVariable_name(), 1).validate();
			if (!expression.isValid()) {
				return this.jsonResult(false, Constants.SAVE_SCALE_ERROR, null, 0);
			}
			if (obj.getParameter_scale().trim().equals(obj.getVariable_name())) obj.setParameter_scale(null);

			DeviceService service = new DeviceService();
			boolean result = service.saveDeviceParameterScale(obj);
			if (result) {
				return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description update device filter parameter
	 * @author Hung.Bui
	 * @since 2024-03-06
	 * @param id_device, id_device_parameter, min_value, max_value
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/save-device-filter-parameter")
	public Object saveDeviceFilterParameter(@RequestBody DeviceEntity obj) {
		try {
			DeviceService service = new DeviceService();
			return service.saveDeviceFilterParameter(obj) ?
					this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, obj, 1)
					:
					this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description update old date on device
	 * @author Duy.Phan
	 * @since 2024-07-15
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-scale-old-data")
	public Object updateScaleOldDate(@RequestBody DeviceEntity obj) {
		try {
			DeviceService service = new DeviceService();
			service.updateScaleOldDate(obj);
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	/**
	 * @description Get all devices with basic information for external API
	 * @author duc.pham
	 * @since 2026-02-09
	 * @param id_site, limit, offset
	 * @return data with id_device, id_site, site_name, device_name, make, model, serial_number
	 */
	@GetMapping("/external/get-all-devices")
	public Object getAllDevicesExternal(
			@ApiParam(value = "Filter by Device ID (optional)")
			@RequestParam(required = false) Integer id_device,

			@ApiParam(value = "Filter by Site ID (optional)")
			@RequestParam(required = false) Integer id_site,

			@ApiParam(value = "Filter by Site Name (optional)")
			@RequestParam(required = false) String site_name,

			@ApiParam(value = "Filter by Device Name (optional)")
			@RequestParam(required = false) String device_name,

			@ApiParam(value = "Filter by Make/Vendor (optional)")
			@RequestParam(required = false) String make,

			@ApiParam(value = "Filter by Model (optional)")
			@RequestParam(required = false) String model,

			@ApiParam(value = "Filter by Serial Number (optional)")
			@RequestParam(required = false) String serial_number,

			@RequestHeader(name = "X-NWM-API-KEY", required = false) String apiKey,
			HttpServletRequest request) {
		try {
			// Validate API key using same method as 3rd-party
			if (Lib.isBlank(apiKey)) {
				return this.thirdPartyJsonResult(false, "Key is required.", null, 0);
			}

			ApiAccessService apiAccessService = new ApiAccessService();
			if (!apiAccessService.validateApiKey(apiKey)) {
				return this.thirdPartyJsonResult(false, "Key is invalid.", null, 0);
			}

			// Check endpoint access - same as 3rd-party
			ThirdPartyAPIService thirdPartyService = new ThirdPartyAPIService();
			String endpoint = request.getRequestURI().substring(request.getContextPath().length());
			String method = request.getMethod();

			if (!thirdPartyService.checkUserCanAccessEndPoint(apiKey, endpoint, method)) {
				return this.thirdPartyJsonResult(false, "Can not access this endpoint", null, 0);
			}

			if (!thirdPartyService.checkRateLimit(apiKey)) {
				return this.thirdPartyJsonResult(false, "Rate limit is full this month", null, 0);
			}

			// Log API usage
			apiAccessService.insertAPIUsage(endpoint, method, apiKey);

			// Build entity from params with all filters
			DeviceEntity obj = new DeviceEntity();
			obj.setSecurity_key(apiKey);

			// Set filter parameters
			if (id_device != null) {
				obj.setId(id_device);
			}
			if (id_site != null) {
				obj.setId_site(id_site);
			}
			if (!Lib.isBlank(site_name)) {
				obj.setSite_name(site_name);
			}
			if (!Lib.isBlank(device_name)) {
				obj.setDevicename(device_name);
			}
			if (!Lib.isBlank(make)) {
				obj.setKeyword(make); // Store in keyword for vendor search
			}
			if (!Lib.isBlank(model)) {
				obj.setDevice_type_name(model);
			}
			if (!Lib.isBlank(serial_number)) {
				obj.setSerial_number(serial_number);
			}

			DeviceService service = new DeviceService();


			List data = service.getAllDevicesForExternalAPI(obj);
			int totalRecord = 0;

			if (data != null && !data.isEmpty()) {
				totalRecord = service.getAllDevicesForExternalAPICount(obj);
			}

			return this.thirdPartyJsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error("Error in getAllDevicesExternal: " + e.getMessage(), e);
			return this.thirdPartyJsonResult(false, "Error retrieving devices: " + e.getMessage(), null, 0);
		}
	}
}