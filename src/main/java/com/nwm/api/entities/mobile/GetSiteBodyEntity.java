package com.nwm.api.entities.mobile;

import com.nwm.api.entities.mobile.common.BaseDto;

public class GetSiteBodyEntity extends BaseDto {
	private String siteStatus = "all";

	public String getSiteStatus() {
		return this.siteStatus;
	}

	public void setSiteStatus(String _siteStatus) {
		this.siteStatus = _siteStatus;
	}
}
