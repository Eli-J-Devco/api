/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.services;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.TenantBillingEntity;
import com.nwm.api.utils.Lib;
import com.opencsv.CSVWriter;

/**
 * Tenant Billing (WEBDEV-5686).
 * Data comes from bems_tenant_billing_daily_summary (one row per meter per local day),
 * except the chart (API 4) and the opening / closing readings of API 3, which read the raw meter table.
 */
@Service
@SuppressWarnings("unchecked")
public class TenantBillingService extends DB {

	public static final String READY = "ready";
	public static final String INCOMPLETE = "incomplete";
	private static final int DEFAULT_LIMIT = 15;
	private static final String[] CSV_ALL_HEADER = {
		"Site code", "Meter S/N", "Tenant name", "Location", "Period start", "Period end",
		"Opening reading", "Opening reading date/time", "Closing reading", "Closing reading date/time",
		"Multiplier", "Consumption (kWh)", "Status"
	};
	private static final String[] CSV_DEVICE_HEADER = { "Date", "kWh", "Note" };

	// replaced in tests to get a fixed "now"
	Clock clock = Clock.systemUTC();

	/**
	 * @description API 1: list of tenant meters with search, status filter, sort and paging
	 * @return { data, total_row }
	 */
	public Map<String, Object> getListDevice(TenantBillingEntity obj) throws Exception {
		preparePeriod(obj);
		if (obj.getLimit() <= 0) obj.setLimit(DEFAULT_LIMIT);
		if (obj.getOffset() < 0) obj.setOffset(0);

		Map<String, Object> result = new LinkedHashMap<>();
		result.put("data", queryForList("BemsTenantBilling.getListMeter", obj));
		result.put("total_row", (Integer) queryForObject("BemsTenantBilling.getListMeterTotal", obj));
		return result;
	}

	/**
	 * @description API 2: Ready, Incomplete, Period usage
	 */
	public Map<String, Object> getSummary(TenantBillingEntity obj) throws Exception {
		Period period = preparePeriod(obj);

		// start_date and end_date one month back, e.g. Dec 1-15 -> Nov 1-15, Sep 5 - Oct 4 -> Aug 5 - Sep 4
		obj.setPrevious_start_date(period.start.minusMonths(1).toString());
		obj.setPrevious_end_date(period.end.minusMonths(1).toString());

		Map<String, Object> summary = (Map<String, Object>) queryForObject("BemsTenantBilling.getSummary", obj);
		return summary;
	}

	/**
	 * @description API 3 (data): every meter with its readings, for the FE to build the Excel file
	 * @return { site_code, site_name, timezone, start_date, end_date, meters }
	 */
	public Map<String, Object> getExportAllData(TenantBillingEntity obj) throws Exception {
		Period period = preparePeriod(obj);
		// a period that is not over yet (e.g. This month) is exported after it closes
		if (!period.end.isBefore(period.today)) {
			throw new IllegalStateException(period.end.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH)
					+ " can be exported after the month closes");
		}

		// every meter, no search / filter / paging from the list
		obj.setId_device(0);
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("site_code", period.site.get("site_code"));
		data.put("site_name", period.site.get("site_name"));
		data.put("timezone", period.site.get("timezone"));
		data.put("start_date", obj.getStart_date());
		data.put("end_date", obj.getEnd_date());
		data.put("meters", exportMeters(obj));
		return data;
	}

	/**
	 * @description API 3: CSV with one row per meter
	 * @return { file_name, content (byte[]) }
	 */
	public Map<String, Object> exportAll(TenantBillingEntity obj) throws Exception {
		Map<String, Object> data = getExportAllData(obj);
		Period period = new Period();
		period.start = LocalDate.parse(obj.getStart_date());
		period.end = LocalDate.parse(obj.getEnd_date());

		String siteCode = Lib.str(data.get("site_code"));
		List<String[]> rows = new ArrayList<>();
		rows.add(CSV_ALL_HEADER);
		for (Map<String, Object> m : (List<Map<String, Object>>) data.get("meters")) {
			rows.add(new String[] {
				siteCode,
				Lib.str(m.get("serial")),
				Lib.str(m.get("tenant_name")),
				Lib.str(m.get("location")),
				period.start.toString(),
				period.end.toString(),
				m.get("opening_reading") == null ? "" : Lib.format(((Number) m.get("opening_reading")).doubleValue(), "#.####"),
				Lib.str(m.get("opening_time")),
				m.get("closing_reading") == null ? "" : Lib.format(((Number) m.get("closing_reading")).doubleValue(), "#.####"),
				Lib.str(m.get("closing_time")),
				Lib.str(m.get("multiplier")),
				m.get("kwh") == null ? "" : Lib.format(((Number) m.get("kwh")).doubleValue(), "#.##"),
				READY.equals(m.get("status")) ? "Ready" : "Incomplete"
			});
		}

		StringWriter out = new StringWriter();
		// BOM so Excel opens UTF-8 tenant names correctly
		out.write('﻿');
		try (CSVWriter writer = new CSVWriter(out)) {
			writer.writeAll(rows, false);
		}
		// Tenant_Billing_SMLU_2026-09.csv for a whole month, Tenant_Billing_SMLU_2026-09-05_2026-10-04.csv otherwise
		boolean wholeMonth = period.start.getDayOfMonth() == 1
				&& period.end.equals(period.start.withDayOfMonth(period.start.lengthOfMonth()));
		String range = wholeMonth ? period.start.toString().substring(0, 7) : period.start + "_" + period.end;

		Map<String, Object> file = new LinkedHashMap<>();
		file.put("file_name", "Tenant_Billing_" + siteCode.replaceAll("[^A-Za-z0-9_-]", "") + "_" + range + ".csv");
		file.put("content", out.toString().getBytes(StandardCharsets.UTF_8));
		return file;
	}

	/**
	 * @description API 4: daily kWh for the chart, from bems_tenant_billing_daily_summary (BemsTenantBilling.getChartData).
	 * TODO: days after today are left out of the chart.
	 */
	public List<Map<String, Object>> getChart(TenantBillingEntity obj) throws Exception {
		if (obj.getId_device() <= 0) throw new IllegalArgumentException("id_device is required");
		preparePeriod(obj);
		if ((Integer) queryForObject("BemsTenantBilling.getListMeterTotal", obj) == 0) throw new IllegalArgumentException("Meter not found");

		List<Map<String, Object>> data = new ArrayList<>();
		Map<String, Object> peak = null;
		for (Map<String, Object> row : (List<Map<String, Object>>) queryForList("BemsTenantBilling.getChartData", obj)) {
			Double kwh = row.get("kwh") == null ? null : ((Number) row.get("kwh")).doubleValue();
			Map<String, Object> day = new LinkedHashMap<>();
			day.put("date", row.get("date"));
			day.put("kwh", kwh);
			day.put("missing", ((Number) row.get("missing")).intValue() == 1);
			day.put("incomplete", ((Number) row.get("incomplete")).intValue() == 1);
			day.put("is_peak", false);
			// TODO: on a tie the earliest day is the peak.
			if (kwh != null && (peak == null || kwh > (Double) peak.get("kwh"))) peak = day;
			data.add(day);
		}
		// no data in the period (e.g. a meter that is not sending): null, so the chart draws no missing days
		if (peak == null) return null;
		peak.put("is_peak", true);
		return data;
	}

	/**
	 * @description API 5 (data): one meter with its readings and daily kWh, for the FE to build the Excel file
	 * TODO: spec only blocks Download all for an open period; single meter is allowed here.
	 * TODO: Note text is not defined yet.
	 * @return { site_code, site_name, timezone, start_date, end_date, meter, days: [{ date, kwh, note }] }
	 */
	public Map<String, Object> getExportDeviceData(TenantBillingEntity obj) throws Exception {
		if (obj.getId_device() <= 0) throw new IllegalArgumentException("id_device is required");
		Period period = preparePeriod(obj);
		List<Map<String, Object>> meters = exportMeters(obj);
		if (meters.isEmpty()) throw new IllegalArgumentException("Meter not found");

		Map<String, Object> data = new LinkedHashMap<>();
		data.put("site_code", period.site.get("site_code"));
		data.put("site_name", period.site.get("site_name"));
		data.put("timezone", period.site.get("timezone"));
		data.put("start_date", obj.getStart_date());
		data.put("end_date", obj.getEnd_date());
		data.put("meter", meters.get(0));
		data.put("days", queryForList("BemsTenantBilling.getDeviceDays", obj));
		return data;
	}

	/**
	 * @description API 5: CSV with the daily kWh of one meter
	 */
	public Map<String, Object> exportDevice(TenantBillingEntity obj) throws Exception {
		Map<String, Object> data = getExportDeviceData(obj);
		Period period = new Period();
		period.start = LocalDate.parse(obj.getStart_date());
		period.end = LocalDate.parse(obj.getEnd_date());

		List<String[]> rows = new ArrayList<>();
		rows.add(CSV_DEVICE_HEADER);
		for (Map<String, Object> day : (List<Map<String, Object>>) data.get("days")) {
			rows.add(new String[] {
				Lib.str(day.get("date")),
				day.get("kwh") == null ? "" : Lib.format(((Number) day.get("kwh")).doubleValue(), "#.##"),
				Lib.str(day.get("note"))
			});
		}

		StringWriter out = new StringWriter();
		// BOM so Excel opens UTF-8 text correctly
		out.write('﻿');
		try (CSVWriter writer = new CSVWriter(out)) {
			writer.writeAll(rows, false);
		}
		// Tenant_Billing_<serial>_2026-09.csv for a whole month, Tenant_Billing_<serial>_2026-09-05_2026-10-04.csv otherwise
		boolean wholeMonth = period.start.getDayOfMonth() == 1
				&& period.end.equals(period.start.withDayOfMonth(period.start.lengthOfMonth()));
		String range = wholeMonth ? period.start.toString().substring(0, 7) : period.start + "_" + period.end;

		Map<String, Object> file = new LinkedHashMap<>();
		file.put("file_name", "Tenant_Billing_" + Lib.str(((Map<String, Object>) data.get("meter")).get("serial")).replaceAll("[^A-Za-z0-9_-]", "") + "_" + range + ".csv");
		file.put("content", out.toString().getBytes(StandardCharsets.UTF_8));
		return file;
	}

	/**
	 * @description API 6: header, banner, This period and Peak day of the side panel
	 */
	public Map<String, Object> getSummaryDevice(TenantBillingEntity obj) throws Exception {
		if (obj.getId_device() <= 0) throw new IllegalArgumentException("id_device is required");
		preparePeriod(obj);
		Map<String, Object> summary = (Map<String, Object>) queryForObject("BemsTenantBilling.getSummaryDevice", obj);
		if (summary == null) throw new IllegalArgumentException("Meter not found");

		// peak_date, peak_kwh -> peak_day: { date, kwh }
		if (summary.containsKey("peak_date")) {
			Map<String, Object> peakDay = new LinkedHashMap<>();
			peakDay.put("date", summary.remove("peak_date"));
			peakDay.put("kwh", summary.remove("peak_kwh"));
			summary.put("peak_day", peakDay);
		}
		return summary;
	}

	/**
	 * @description API 7: This period, Peak day and Average daily tiles of the side panel
	 */
	public Map<String, Object> getAvgDaily(TenantBillingEntity obj) throws Exception {
		if (obj.getId_device() <= 0) throw new IllegalArgumentException("id_device is required");
		preparePeriod(obj);
		Map<String, Object> avg = (Map<String, Object>) queryForObject("BemsTenantBilling.getAvgDaily", obj);

		// peak_date, peak_kwh -> peak_day: { date, kwh }
		if (avg.containsKey("peak_date")) {
			Map<String, Object> peakDay = new LinkedHashMap<>();
			peakDay.put("date", avg.remove("peak_date"));
			peakDay.put("kwh", avg.remove("peak_kwh"));
			avg.put("peak_day", peakDay);
		}
		return avg;
	}


	/**
	 * Rows of API 3 / 5: every meter of the site (or obj.id_device) with its opening and closing readings.
	 * Needs preparePeriod(obj) first.
	 */
	private List<Map<String, Object>> exportMeters(TenantBillingEntity obj) throws Exception {
		TenantBillingEntity query = new TenantBillingEntity();
		query.setId_site(obj.getId_site());
		query.setId_device(obj.getId_device());
		query.setStart_date(obj.getStart_date());
		query.setEnd_date(obj.getEnd_date());
		query.setTimezone_offset(obj.getTimezone_offset());
		query.setExpected_days(obj.getExpected_days());
		query.setDevices(queryForList("BemsTenantBilling.getDataTables", query));
		if (query.getDevices().isEmpty()) return new ArrayList<>();

		List<Map<String, Object>> meters = queryForList("BemsTenantBilling.getExportAll", query);
		// TODO: multiplier source is not confirmed; 1 like the FE mock.
		for (Map<String, Object> m : meters) m.put("multiplier", 1);
		return meters;
	}


	// ---------------------------------------------------------------- period

	static class Period {
		Map<String, Object> site;
		ZoneOffset zone;
		LocalDate start;
		LocalDate end;
		LocalDate today;
		LocalDateTime now;
	}

	/**
	 * Validates the period and fills obj.timezone_offset, obj.expected_days and obj.today.
	 */
	private Period preparePeriod(TenantBillingEntity obj) throws Exception {
		if (obj.getId_site() <= 0) throw new IllegalArgumentException("id_site is required");
		Map<String, Object> site = (Map<String, Object>) queryForObject("BemsTenantBilling.getSiteInfo", obj);
		if (site == null) throw new IllegalArgumentException("Site not found");

		Period period = new Period();
		period.site = site;
		period.zone = ZoneOffset.of(Lib.str(site.get("timezone_offset")));
		period.now = LocalDateTime.now(clock.withZone(period.zone));
		period.today = period.now.toLocalDate();
		try {
			period.start = LocalDate.parse(obj.getStart_date());
			period.end = LocalDate.parse(obj.getEnd_date());
		} catch (DateTimeParseException | NullPointerException e) {
			throw new IllegalArgumentException("start_date and end_date must be YYYY-MM-DD");
		}

		if (period.end.isBefore(period.start)) throw new IllegalArgumentException("end_date must not be before start_date");
		// TODO: "within the past 6 months" is read as today minus 6 months.
		if (period.start.isBefore(period.today.minusMonths(6))) throw new IllegalArgumentException("start_date must be within the past 6 months");
		if (period.end.isAfter(period.start.plusMonths(3).minusDays(1))) throw new IllegalArgumentException("The range can be up to 3 months long");
		// This month is sent as the whole month, so the end may be in the future, but not past the current month.
		if (period.end.isAfter(period.today.withDayOfMonth(period.today.lengthOfMonth()))) throw new IllegalArgumentException("end_date must not be after the current month");

		// days that need a complete row to be Ready: start until the end of the period or today, whichever is first
		LocalDate lastDay = period.end.isAfter(period.today) ? period.today : period.end;
		obj.setExpected_days(lastDay.isBefore(period.start) ? 0 : (int) ChronoUnit.DAYS.between(period.start, lastDay) + 1);
		obj.setTimezone_offset(Lib.str(site.get("timezone_offset")));
		obj.setToday(period.today.toString());
		return period;
	}
}
