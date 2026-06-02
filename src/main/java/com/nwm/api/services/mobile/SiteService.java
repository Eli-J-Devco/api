package com.nwm.api.services.mobile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.mobile.GetSiteBodyEntity;
import com.nwm.api.entities.mobile.SiteMobileEntity;
import com.nwm.api.entities.mobile.SiteSummaryEntity;
import com.nwm.api.entities.mobile.alert.AlertMobileEntity;
import com.nwm.api.entities.mobile.site.ChartDataEntity;
import com.nwm.api.entities.mobile.site.GetAlertBySiteDto;
import com.nwm.api.entities.mobile.site.GetDevicesBysiteDto;
import com.nwm.api.entities.mobile.site.GetSiteChartDto;
import com.nwm.api.entities.mobile.site.GetSiteGenerationDto;
import com.nwm.api.entities.mobile.site.SiteChartEntity;
import com.nwm.api.entities.mobile.site.SiteDeviceEntity;
import com.nwm.api.entities.mobile.site.SiteGenerationMobileEntity;

public class SiteService extends DB {

	public SiteSummaryEntity getSiteSummaryByUser(GetSiteBodyEntity body) {
		try {
			int totalSite = (int) queryForObject("SiteMobile.countSitesByUser", body);
			int sitesWithIssue = (int) queryForObject("SiteMobile.countSiteWithIssues", body);

			SiteSummaryEntity siteSummary = new SiteSummaryEntity();
			siteSummary.setTotalSite(totalSite);
			siteSummary.setSitesWithIssue(sitesWithIssue);

			return siteSummary;

		} catch (Exception ex) {

			System.out.println(ex);

			return new SiteSummaryEntity();
		}
	}

	public List<SiteMobileEntity> getSiteByUser(GetSiteBodyEntity body) {

		try {
			List<SiteMobileEntity> sites = queryForList("SiteMobile.getSitesByUser", body);

			return sites;

		} catch (Exception ex) {

			System.out.println(ex);

			return new ArrayList<SiteMobileEntity>();
		}
	}

	public int countByUser(GetSiteBodyEntity body) {

		try {
			int totalSite = (int) queryForObject("SiteMobile.countSitesByUser", body);

			return totalSite;
		} catch (Exception ex) {

			System.out.println(ex);

			return 0;
		}
	}

	public SiteGenerationMobileEntity getSiteGeneration(GetSiteGenerationDto body) {
		try {
			SiteGenerationMobileEntity siteGeneration = (SiteGenerationMobileEntity) queryForObject(
					"SiteOverviewMobile.getGeneration", body);

			System.out.println(siteGeneration.getPowerToday());

			return siteGeneration;
		} catch (Exception ex) {
			System.out.println(ex);
			return new SiteGenerationMobileEntity();
		}
	}

	public List<SiteDeviceEntity> getDevicesBySite(GetDevicesBysiteDto body) {
		try {
			List<SiteDeviceEntity> devices = queryForList("SiteOverviewMobile.getDevicesBySite", body);
			return devices;
		} catch (Exception ex) {
			System.out.println(ex);
			return new ArrayList<SiteDeviceEntity>();
		}
	}

	public List<AlertMobileEntity> getAlertsBySite(GetAlertBySiteDto body) {
		try {
			List<AlertMobileEntity> alerts = queryForList("SiteOverviewMobile.getAlertsBySite", body);

			for (AlertMobileEntity alert : alerts) {
				alert.setSiteName(body.getSiteName());
			}

			return alerts;
		} catch (Exception ex) {

			System.out.println(ex);

			return new ArrayList<AlertMobileEntity>();
		}
	}

	public Object getSiteChartData(GetSiteChartDto body) {
		try {
			SiteChartEntity chartData = new SiteChartEntity();

			// List<Map<String, Object>> data = (List<Map<String, Object>>) queryForObject(
			// 		"SiteOverviewMobile.getHiddenDataListBySite", body);
			// chartData.setChartData(data);

			// List<ChartDataEntity> data = (List<ChartDataEntity>) queryForList("SiteOverviewMobile.getSiteDataReport", body);
			// chartData.setChartData(data);

			return queryForList("SiteOverviewMobile.getDevicesBySite", body);
		} catch (Exception ex) {
			System.out.println(ex);
			return new SiteChartEntity();
		}
	}
}
