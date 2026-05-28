package com.nwm.api.controllers.mobile;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.controllers.BaseController;
import com.nwm.api.entities.mobile.GetSiteBodyEntity;
import com.nwm.api.entities.mobile.SiteMobileEntity;
import com.nwm.api.entities.mobile.SiteSummaryEntity;
import com.nwm.api.entities.mobile.site.GetDevicesBysiteDto;
import com.nwm.api.entities.mobile.site.GetSiteGenerationDto;
import com.nwm.api.entities.mobile.site.SiteDeviceEntity;
import com.nwm.api.entities.mobile.site.SiteGenerationMobileEntity;
import com.nwm.api.services.mobile.SiteService;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/mobile-site")
public class SiteControllers extends BaseController {
	private final SiteService service;

	public SiteControllers() {
		this.service = new SiteService();
	}

	@PostMapping("/get-sites-summary-by-user")
	public Object getSiteSummaryByUser(@RequestBody GetSiteBodyEntity body) {
		try {
			SiteSummaryEntity data = this.service.getSiteSummaryByUser(body);

			return this.jsonResult(true, "Get Site Summary Success", data, 1);

		} catch (Exception ex) {
			log.error(ex);

			return this.jsonResult(false, "Get Site Summary By User faild", ex, 0);
		}
	}

	@PostMapping("/get-sites-by-user")
	public Object getSitesByUser(@RequestBody GetSiteBodyEntity body) {
		try {
			List<SiteMobileEntity> data = this.service.getSiteByUser(body);

			return this.jsonResult(true, "Get Sites By User Success", data, data.size());
		} catch (Exception ex) {

			log.error(ex);

			return this.jsonResult(false, "Get Sites By User faild", ex, 0);
		}
	}

	@PostMapping("/get-site-generation")
	public Object getSiteGeneration(@RequestBody GetSiteGenerationDto body) {
		try {
			SiteGenerationMobileEntity data = this.service.getSiteGeneration(body);

			return this.jsonResult(true, "Get Site Generation Success", data, 1);
		} catch (Exception ex) {

			log.error(ex);

			return this.jsonResult(false, "Get Site Generation failed", ex, 0);
		}
	}

	@PostMapping("/get-devices-by-site")
	public Object getDevicesBySite(@RequestBody GetDevicesBysiteDto body) {
		try {
			List<SiteDeviceEntity> data = this.service.getDevicesBySite(body);

			return this.jsonResult(true, "Get Devices By Site Success", data, data.size());
		} catch (Exception ex) {

			log.error(ex);

			return this.jsonResult(false, "Get Devices By Site failed", ex, 0);
		}
	}

}
