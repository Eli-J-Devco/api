/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.TablePreferenceEntity;
import com.nwm.api.entities.TagEntity;
import com.nwm.api.services.SiteService;
import com.nwm.api.services.TagService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/tag")
public class TagController extends BaseController {
	
	/**
	 * @description insert Tag
	 * @author Duy.Phan
	 * @since 2024-05-28
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/add-tag")
	public Object addTag(@RequestBody TagEntity obj) {
		try {
			TagService service = new TagService();
			int checkExits = service.checkExitsTagName(obj);
			if(checkExits <= 0) {
				// Insert
				TagEntity data = service.insertTag(obj);
				return this.jsonResult(true,  Constants.SAVE_SUCCESS_MSG , data, 1);
			} else {
				return this.jsonResult(false, "Tag is existed", obj, 1);
			}
			
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list tag
	 * @author Duy.Phan
	 * @since 2024-06-03
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list")
	public Object getList(@RequestBody TagEntity obj) {
		try {
			TagService service = new TagService();
			List data = service.getList(obj);
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0, null);
		}
	}
	
	/**
	 * @description Get list tag By site
	 * @author Duy.Phan
	 * @since 2024-06-03
	 * @param id_site
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-by-site-or-device")
	public Object getListBySite(@RequestBody TagEntity obj) {
		try {
			TagService service = new TagService();
			List data = service.getListBySiteOrDevice(obj);
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0, null);
		}
	}
	
	/**
	 * @description delete tag
	 * @author Duy.Phan
	 * @since 2024-06-03
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete-tag")
	public Object delete(@Valid @RequestBody TagEntity obj) {
		TagService service = new TagService();
		try {
			boolean result = service.deleteTag(obj);
			if (result) {
				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description insert Tag
	 * @author Duy.Phan
	 * @since 2024-05-28
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/apply-tag-to-site-or-device")
	public Object addEmployeeManageSite(@RequestBody TagEntity obj) {
		try {
			TagService service = new TagService();
			
			TagEntity data = service.insertTagtoSiteOrDevice(obj);
			if (data != null) {
				return this.jsonResult(true,  Constants.SAVE_SUCCESS_MSG , data, 1);

			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, data, 0);

			}
			
			
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description delete tag
	 * @author Duy.Phan
	 * @since 2024-06-03
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete-tag-map-to-site-or-device")
	public Object deleteTagMapToSiteOrDevice(@Valid @RequestBody TagEntity obj) {
		TagService service = new TagService();
		try {
			boolean result = service.deleteTagMapToSiteOrDevice(obj);
			if (result) {
				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
}
