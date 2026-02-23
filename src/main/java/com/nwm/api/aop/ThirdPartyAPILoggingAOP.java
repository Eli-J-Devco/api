package com.nwm.api.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nwm.api.entities.APIAccessLoggingDTO;
import com.nwm.api.services.ApiAccessService;

@Aspect
public class ThirdPartyAPILoggingAOP {
	private final ApiAccessService apiAccessService = new ApiAccessService();
	
	@Pointcut("within(com.nwm.api.controllers.ThirdPartyAPIController) || within(com.nwm.api.controllers.SiteExternalAPIController) " +
            "|| within(com.nwm.api.controllers.DeviceController) || within(com.nwm.api.controllers.AlertController)")


	public void thirdPartyAPIMethod() {}
	
	@AfterReturning(value = "thirdPartyAPIMethod()", returning = "result")
    public void afterSiteDetailMethod(Object result) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		if (result instanceof ResponseEntity) {
			if (((ResponseEntity<?>) result).getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0) return;
		}

		String route = request.getRequestURI().substring(request.getContextPath().length());
		String method = request.getMethod();
		String security_key = request.getHeader("X-NWM-API-KEY");
		
		apiAccessService.insertAPIUsage(new APIAccessLoggingDTO(route, method, security_key));
    }  
	
}
