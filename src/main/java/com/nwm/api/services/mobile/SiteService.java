package com.nwm.api.services.mobile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.mobile.GetSiteBodyEntity;
import com.nwm.api.entities.mobile.SiteMobileEntity;
import com.nwm.api.entities.mobile.SiteSummaryEntity;
import com.nwm.api.entities.mobile.alert.AlertMobileEntity;
import com.nwm.api.entities.mobile.site.ChartDataEntity;
import com.nwm.api.entities.mobile.site.GetAlertBySiteDto;
import com.nwm.api.entities.mobile.site.GetChartParameterContext;
import com.nwm.api.entities.mobile.site.GetDevicesBysiteDto;
import com.nwm.api.entities.mobile.site.GetSiteChartDto;
import com.nwm.api.entities.mobile.site.GetSiteGenerationDto;
import com.nwm.api.entities.mobile.site.SeparateDevicesDto;
import com.nwm.api.entities.mobile.site.SiteChartEntity;
import com.nwm.api.entities.mobile.site.SiteDeviceDto;
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

	public SeparateDevicesDto getSeparateDevicesBySite(GetSiteChartDto body) {
		try {
			List<SiteDeviceDto> devices = queryForList("SiteOverviewMobile.getListDevicesBySite", body);
			List<SiteDeviceDto> meter = new ArrayList<SiteDeviceDto>();
			List<SiteDeviceDto> inverter = new ArrayList<SiteDeviceDto>();
			List<SiteDeviceDto> irradiance = new ArrayList<SiteDeviceDto>();

			for (SiteDeviceDto device : devices) {
				int type = device.getDeviceType();

				if ((type == 3 || type == 7 || type == 9) && !device.getIsExcludedMeter()) {
					meter.add(device);
					continue;
				}

				if ((type == 4 || type == 21) && device.getIsReversePoa() == 0) {
					irradiance.add(device);
					continue;
				}

				if (type == 1) {
					inverter.add(device);
					continue;
				}
			}

			return new SeparateDevicesDto(meter, inverter, irradiance);
		} catch (Exception ex) {

			return new SeparateDevicesDto(new ArrayList<SiteDeviceDto>(), new ArrayList<SiteDeviceDto>(),
					new ArrayList<SiteDeviceDto>());
		}
	}

	public Object getEnergyByDevice(GetChartParameterContext context) {
		try {
			List<Map<String, Object>> energyByDevice = queryForList("SiteOverviewMobile.getEnergyByDevice", context);
			return energyByDevice;
		} catch (Exception ex) {
			System.out.println(ex);
			return new ArrayList<Map<String, Object>>();
		}
	}

	public Object getSiteChartData(GetSiteChartDto body) {
		try {
			SiteChartEntity chartData = new SiteChartEntity();

			SeparateDevicesDto separateDevices = getSeparateDevicesBySite(body);
			List<SiteDeviceDto> meterDevices = separateDevices.getMeter();
			List<SiteDeviceDto> inverterDevices = separateDevices.getInverter();
			List<SiteDeviceDto> irradianceDevices = separateDevices.getIrradiance();
			List<SiteDeviceDto> powerDevices = meterDevices.size() > 0 ? meterDevices : inverterDevices;

			if (powerDevices.size() == 0)
				return new SiteChartEntity();

			GetChartParameterContext context = new GetChartParameterContext();
			context.setGetSiteChartDto(body);
			context.setListMeter(powerDevices);
			context.setTotalMeter(powerDevices.size());

			Object energyByDevice = getEnergyByDevice(context);

			return energyByDevice;
		} catch (Exception ex) {
			System.out.println(ex);
			return new SiteChartEntity();
		}
	}
}
