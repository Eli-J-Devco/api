package com.nwm.api.aop;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nwm.api.entities.AccountStatusEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.JsonResultEntity;
import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.services.AccountStatusService;
import com.nwm.api.utils.Lib;

@Aspect
public class AccountStatusAOP {
	private Map<String, String> serviceUrl;
	private AccountStatusService accountStatusService = new AccountStatusService();
	
	public AccountStatusAOP() {
		Map<String, String> serviceUrl = new HashMap<String, String>();
		serviceUrl.put("dashboard/list", "management > dashboard");
		serviceUrl.put("portfolio/list", "management > portfolio");
		serviceUrl.put("portfolio/metrics/summary", "management > metrics");
		serviceUrl.put("alert/alert-summary", "management > alerts");
		serviceUrl.put("map/all-by-employee", "management > map");
		serviceUrl.put("reports/list", "management > reports");
		serviceUrl.put("site/list", "management > site");
		serviceUrl.put("email-subscribers/list", "management > email subscribers");
		serviceUrl.put("employee/list", "management > customers/accounts");
		serviceUrl.put("role/list", "management > permissions & roles");
		serviceUrl.put("group/list", "management > site groups");
		serviceUrl.put("icons/list", "management > icons");
		serviceUrl.put("avatar/list", "management > account icons");
		serviceUrl.put("error-level/list", "management > error level");
		serviceUrl.put("error/list", "management > errors");
		serviceUrl.put("company/list", "management > companies");
		serviceUrl.put("import-old-data/get-list-file-import", "management > import old data");
		serviceUrl.put("device-type/list-manage", "management > device type");
		serviceUrl.put("device-parameter/list-device-group", "management > hardware devices");
		serviceUrl.put("generic-parameters/list-paginated", "management > generic parameters");
		serviceUrl.put("categorize-data/list", "management > categorize data");
		serviceUrl.put("device-layout/list", "management > device layout");
		serviceUrl.put("monitor/list-ssh-cell-modem-status", "management > monitor");
		serviceUrl.put("system/system-setting", "management > system");
		serviceUrl.put("customer-support/list", "management > support tickets");
		serviceUrl.put("customer-view/get-customer-view-info", "client view");
//		serviceUrl.put("minisite/info", "kiosk view");
		
		this.serviceUrl = serviceUrl;
	}

	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void controllerClass() {}

	@Before("controllerClass()")
    public void beforeControllerMethod() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authz = request.getHeader("Authorization");
        String uri = request.getRequestURI();
        String[] uriArr = uri.split("/");
        String page = uriArr[2] + "/" + uriArr[3];
        if (!serviceUrl.keySet().contains(page)) return;
        
    	AccountStatusEntity obj = new AccountStatusEntity();
    	obj.setId_employee(Lib.getUserId(authz));
    	obj.setPage_login(serviceUrl.get(page));
    	accountStatusService.insertAccountStatus(obj);
    }
	
	@Pointcut("execution(* com.nwm.api.controllers.SitesDevicesController.getDetailSite(..))")
	public void siteDetailMethod() {}
	
	@AfterReturning(value = "siteDetailMethod()", returning = "result")
    public void afterSiteDetailMethod(JsonResultEntity result) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String queryString = request.getQueryString();
		if (!result.isStatus()) return;
        String authz = request.getHeader("Authorization");
        AccountStatusEntity obj = new AccountStatusEntity();
        obj.setId_employee(Lib.getUserId(authz));
        
        SitesDevicesEntity siteDetail = (SitesDevicesEntity) result.getData();
        String page = Objects.isNull(queryString) ? "" : " > " + queryString.split("=")[1];
    	String site = " > " + siteDetail.getName();
    	obj.setPage_login("management > sites" + site + page);
    	accountStatusService.insertAccountStatus(obj);
    }  
	
	@Pointcut("execution(* com.nwm.api.controllers.SitesDevicesController.getDeviceDetail(..))")
	public void deviceDetailMethod() {}
	
	@AfterReturning(value = "deviceDetailMethod()", returning = "result")
	public void afterDeviceDetailMethod(JsonResultEntity result) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		if (!result.isStatus()) return;
		String authz = request.getHeader("Authorization");
		AccountStatusEntity obj = new AccountStatusEntity();
		obj.setId_employee(Lib.getUserId(authz));
		
		DeviceEntity deviceDetail = (DeviceEntity) result.getData();
		String site = " > " + deviceDetail.getSite_name();
		String device = " > " + deviceDetail.getDevicename();
		obj.setPage_login("management > sites" + site + " > devices > hardware" + device);
		accountStatusService.insertAccountStatus(obj);
	}  
}
