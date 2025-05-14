/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;


public class PortfolioAvailabilityVsPerformanceEntity extends SortEntity {
	private List id_sites;
	private String date_range;
	private String timezone;
	
	
	public List getId_sites() {
		return id_sites;
	}
	public void setId_sites(List id_sites) {
		this.id_sites = id_sites;
	}
	public String getDate_range() {
		return date_range;
	}
	public void setDate_range(String date_range) {
		this.date_range = date_range;
	}
	
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	
	public String getStart_date() {
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		ZoneId zone = ZoneId.of(getTimezone());
		ZoneOffset zoneOffset = zone.getRules().getOffset(Instant.now());
		
		switch(this.getDate_range()) {
			case "today":
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				break;
			case "this_month":
				calendar.set(Calendar.DATE, 1);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				break;
			case "last_month":
				calendar.add(Calendar.MONTH, -1);
				calendar.set(Calendar.DATE, 1);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				break;
			default:
		}
		OffsetDateTime odt = OffsetDateTime.ofInstant(calendar.getTime().toInstant(), zoneOffset);
		odt = odt.withOffsetSameInstant(ZoneOffset.UTC);
		return odt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
	
	public String getEnd_date() {
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		ZoneId zone = ZoneId.of(getTimezone());
		ZoneOffset zoneOffset = zone.getRules().getOffset(Instant.now());
		
		switch(this.getDate_range()) {
			case "today":
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 999);
				break;
			case "this_month":
				calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 999);
				break;
			case "last_month":
				calendar.add(Calendar.MONTH, -1);
				calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 999);
				break;
			default:
		}
		OffsetDateTime odt = OffsetDateTime.ofInstant(calendar.getTime().toInstant(), zoneOffset);
		odt = odt.withOffsetSameInstant(ZoneOffset.UTC);
		return odt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}