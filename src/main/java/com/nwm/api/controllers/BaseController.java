/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nwm.api.entities.JsonResultEntity;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.FLLogger;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SecretCards;
import com.nwm.api.utils.Translator;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

public abstract class BaseController {
	protected FLLogger log = FLLogger.getLogger("controller/"+this.getClass().getSimpleName());
	@Autowired
	protected SecretCards secretCard;
//	@Autowired
//	private JwtTokenStore tokenstore;
	/**
	 * respone json data 
	 * @param status
	 * @param mess
	 * @param data
	 * @param totalRow
	 * @param totalRecord;
	 * @return
	 */
	protected JsonResultEntity jsonResult (boolean status, String mess, Object data, int totalRow) {
		JsonResultEntity result = new JsonResultEntity();
		result.setStatus(status);
		result.setMess(mess);
		result.setData(data);
		result.setTotal_row(totalRow);
		return result;
	}
	protected JsonResultEntity jsonResult (boolean status, String mess, Object data) {
		JsonResultEntity result = new JsonResultEntity();
		result.setStatus(status);
		result.setMess(mess);
		result.setData(data);
		return result;
	}
	
	/**
	 * handle validation message 
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public JsonResultEntity handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        try {
		        Object[] ar = error.getArguments();
		        if(ar.length>1) {
			        Object param = ar[1];
			        String val = param.toString();
			        errorMessage = String.format(errorMessage,val);
		        }
	        }catch (Exception e) {
				// TODO: handle exception
			}
	        errors.put(fieldName, errorMessage);
	    });
	    JsonResultEntity result = new JsonResultEntity();
		result.setStatus(false);
		result.setMess(Translator.toLocale(Constants.VALIDATE_ERROR_MSG));
		result.setData(errors);
		result.setTotal_row(0);
	    return result;
	}
	/**
	 * bean root upload path
	 * @return
	 */
	@Bean
	public String uploadRootPath() {
		return Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey);
	}
	
}
