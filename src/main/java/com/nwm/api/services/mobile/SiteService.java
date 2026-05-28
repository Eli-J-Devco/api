package com.nwm.api.services.mobile;

import java.util.List;
import java.util.ArrayList;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.mobile.GetSiteBodyEntity;
import com.nwm.api.entities.mobile.SiteMobileEntity;
import com.nwm.api.entities.mobile.SiteSummaryEntity;
import com.nwm.api.entities.mobile.site.GetDevicesBysiteDto;
import com.nwm.api.entities.mobile.site.GetSiteGenerationDto;
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
			
		} catch(Exception ex) {
			
			System.out.println(ex);
			
			return new SiteSummaryEntity();
		}
	}

	public List<SiteMobileEntity> getSiteByUser(GetSiteBodyEntity body) {
		
		try {
			List<SiteMobileEntity> sites =  queryForList("SiteMobile.getSitesByUser", body);
			
			return sites;
			
		} catch(Exception ex) {
			
			System.out.println(ex);
			
			return new ArrayList<SiteMobileEntity>();
		}
		
		
	} 

	public SiteGenerationMobileEntity getSiteGeneration(GetSiteGenerationDto body) {
		try {
			SiteGenerationMobileEntity siteGeneration = (SiteGenerationMobileEntity) queryForObject("SiteOverviewMobile.getGeneration", body);
			
			System.out.println(siteGeneration.getPowerToday());
			
			return siteGeneration;
		} catch(Exception ex) {
			System.out.println(ex);
			return new SiteGenerationMobileEntity();
		}
	}

	public List<SiteDeviceEntity> getDevicesBySite(GetDevicesBysiteDto body) {
		try {
			List<SiteDeviceEntity> devices = queryForList("SiteOverviewMobile.getDevicesBySite", body);
			return devices;
		} catch(Exception ex) {
			System.out.println(ex);
			return new ArrayList<SiteDeviceEntity>();
		}
	}
}
