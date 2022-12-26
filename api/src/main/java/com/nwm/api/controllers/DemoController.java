package com.nwm.api.controllers;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nwm.api.services.DemoService;
import com.nwm.api.utils.Constants;


@RestController

@RequestMapping("/demo")
public class DemoController extends BaseController {

	/**
     * @description  Get all
     * @author long.pham
     * @since 2020-08-18
     */
	@PostMapping("/list")
    public Object getAll(HttpServletRequest request){
    	
		try {
			DemoService service = new DemoService();
			List data = service.getAll(null);
			if(data != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
			}else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
    }
}
