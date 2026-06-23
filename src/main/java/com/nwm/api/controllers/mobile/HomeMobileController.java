package com.nwm.api.controllers.mobile;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.controllers.BaseController;
import com.nwm.api.entities.mobile.home.CriticalSiteEntity;
import com.nwm.api.entities.mobile.home.GetCriticalSiteDto;
import com.nwm.api.entities.mobile.home.GetCriticalSiteSummaryDto;
import com.nwm.api.entities.mobile.home.GetSummaryDto;
import com.nwm.api.entities.mobile.home.GetWhatChangeTodayDto;
import com.nwm.api.entities.mobile.home.SummaryAcrossSystemEntity;
import com.nwm.api.entities.mobile.site.ChartDataEntity;
import com.nwm.api.services.mobile.HomeMobileService;

import springfox.documentation.annotations.ApiIgnore;


@ApiIgnore
@RestController
@RequestMapping("/mobile/home")
public class HomeMobileController extends BaseController {
    private final HomeMobileService service;
   
    public HomeMobileController(){
        this.service = new HomeMobileService();
    }

    @PostMapping("/summary")
    public Object GetSummary(@RequestBody GetSummaryDto body) {
       	try {
			SummaryAcrossSystemEntity data = this.service.GetSummary(body);

			return this.jsonResult(true, "Get Home Summary Success", data, 1);

		} catch (Exception ex) {
			log.error(ex);

			return this.jsonResult(false, "Get Home Summary faild", ex, 0);
		}
    }

    @PostMapping("/generation-across-system")
    public Object GetGenerationAcrossSystem() {
       	try {
			Object data = this.service.GetGenerationAcrossSystem();

			return this.jsonResult(true, "Get Home generation Success", data, 1);

		} catch (Exception ex) {
			log.error(ex);

			return this.jsonResult(false, "Get Home generation faild", ex, 0);
		}
    }

    @PostMapping("/what-change-today")
    public Object GetWhatChangeTodayEntity(@RequestBody GetWhatChangeTodayDto body) {
       	try {
			Object data = this.service.GetWhatChangeToday(body);

			return this.jsonResult(true, "Get What Change Today Success", data, 1);

		} catch (Exception ex) {
			log.error(ex);

			return this.jsonResult(false, "Get What Change Today faild", ex, 0);
		}
    }

    @PostMapping("/critical-site")
    public Object GetCriticalSite(@RequestBody GetCriticalSiteDto entity) {
        try {
			List<CriticalSiteEntity> data = this.service.GetCriticalSite(entity);

			return this.jsonResult(true, "Get Critical Site Success", data, 1);

		} catch (Exception ex) {
			log.error(ex);

			return this.jsonResult(false, "Get Critical Site faild", ex, 0);
		}
    }

    @PostMapping("/summary-alert-by-site")
    public Object GetSummaryAlertBySite(@RequestBody GetCriticalSiteSummaryDto entity) {
          try {
			List<ChartDataEntity> data = this.service.GetSummaryAlertBySite(entity);

			return this.jsonResult(true, "Get Summary Alert By Site Success", data, data.size());

		} catch (Exception ex) {
			log.error(ex);

			return this.jsonResult(false, "Get Summary Alert By Site", ex, 0);
		}
    }
	
    @PostMapping("/summary-inverter-avail-by-site")
    public Object GetSummaryInverterAvailBySite(@RequestBody GetCriticalSiteSummaryDto entity) {
          try {
			List<ChartDataEntity> data = this.service.GetSummaryInverterAvailBySite(entity);

			return this.jsonResult(true, "Get Summary Inverter Avail By Site Success", data, data.size());

		} catch (Exception ex) {
			log.error(ex);

			return this.jsonResult(false, "Get Summary Inverter Avail faild", ex, 0);
		}
    }
    
}
