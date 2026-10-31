/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.services;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.TenantBillingEntity;
import com.nwm.api.utils.Lib;
import com.opencsv.CSVWriter;

/**
 * Tenant Billing (WEBDEV-5686).
 * Status, daily kWh, peak day and average are calculated here from the daily rows of TenantBilling.getDailyData.
 */
@Service
@SuppressWarnings("unchecked")
public class TenantBillingService extends DB {

	public static final String READY = "ready";
	public static final String INCOMPLETE = "incomplete";
	private static final int RECORDS_PER_DAY = 288;
	private static final int DEFAULT_LIMIT = 15;
	private static final DateTimeFormatter LABEL_FORMAT = DateTimeFormatter.ofPattern("MMM d", Locale.ENGLISH);
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
		Period period = preparePeriod(obj);
		List<Map<String, Object>> meters = calculateMeters(obj, period, period.start, period.end);

		// Ready first, then the selected column. serial and tenant_name ascending, kwh highest first.
		String sortColumn = obj.getSort_column();
		Comparator<Map<String, Object>> sort = Comparator.comparing(m -> READY.equals(m.get("status")) ? 0 : 1);
		if ("kwh".equals(sortColumn)) {
			sort = sort.thenComparing(m -> (Double) m.get("kwh"), Comparator.nullsLast(Comparator.reverseOrder()));
		} else if ("tenant_name".equals(sortColumn)) {
			sort = sort.thenComparing(m -> Lib.str(m.get("tenant_name")), String.CASE_INSENSITIVE_ORDER);
		} else {
			// TODO: serial is compared as text; switch to numeric if every S/N is a number.
			sort = sort.thenComparing(m -> Lib.str(m.get("serial")));
		}

		String keyword = obj.getKeyword() == null ? "" : obj.getKeyword().trim().toLowerCase();
		String status = obj.getStatus() == null ? "all" : obj.getStatus();
		List<Map<String, Object>> filtered = meters.stream()
				.filter(m -> keyword.isEmpty()
						|| Lib.str(m.get("tenant_name")).toLowerCase().contains(keyword)
						|| Lib.str(m.get("serial")).toLowerCase().contains(keyword))
				.filter(m -> "all".equals(status) || status.equals(m.get("status")))
				.sorted(sort)
				.collect(Collectors.toList());

		int limit = obj.getLimit() > 0 ? obj.getLimit() : DEFAULT_LIMIT;
		int from = Math.min(Math.max(obj.getOffset(), 0), filtered.size());
		int to = Math.min(from + limit, filtered.size());

		List<Map<String, Object>> data = new ArrayList<>();
		for (Map<String, Object> m : filtered.subList(from, to)) {
			Map<String, Object> row = new LinkedHashMap<>();
			row.put("id_device", m.get("id_device"));
			row.put("device_name", m.get("device_name"));
			row.put("serial", m.get("serial"));
			row.put("tenant_name", m.get("tenant_name"));
			row.put("location", m.get("location"));
			row.put("kwh", m.get("kwh"));
			row.put("status", m.get("status"));
			data.add(row);
		}

		Map<String, Object> result = new LinkedHashMap<>();
		result.put("data", data);
		result.put("total_row", filtered.size());
		return result;
	}

	/**
	 * @description API 2: Ready, Incomplete, Period usage
	 * TODO: counts ignore the search keyword until PO confirms.
	 */
	public Map<String, Object> getSummary(TenantBillingEntity obj) throws Exception {
		Period period = preparePeriod(obj);
		List<Map<String, Object>> meters = calculateMeters(obj, period, period.start, period.end);

		int ready = 0;
		double periodKwh = 0;
		for (Map<String, Object> m : meters) {
			if (READY.equals(m.get("status"))) ready++;
			if (m.get("kwh") != null) periodKwh += (Double) m.get("kwh");
		}

		// "vs same days last month"
		// TODO: confirm the comparison period for a custom range; it is shifted back one month here.
		List<Map<String, Object>> previous = calculateMeters(obj, period, period.start.minusMonths(1), period.end.minusMonths(1));
		Double previousKwh = null;
		for (Map<String, Object> m : previous) {
			if (m.get("kwh") != null) previousKwh = (previousKwh == null ? 0 : previousKwh) + (Double) m.get("kwh");
		}

		Map<String, Object> result = new LinkedHashMap<>();
		result.put("ready", ready);
		result.put("incomplete", meters.size() - ready);
		result.put("period_kwh", Math.round(periodKwh * 100.0) / 100.0);
		result.put("compare_percent", previousKwh == null || previousKwh == 0 ? null
				: Math.round((periodKwh - previousKwh) / previousKwh * 1000.0) / 10.0);
		return result;
	}

	/**
	 * @description API 3: CSV with one row per meter
	 * @return { file_name, content (byte[]) }
	 */
	public Map<String, Object> exportAll(TenantBillingEntity obj) throws Exception {
		Period period = preparePeriod(obj);
		if (!period.end.isBefore(period.today)) {
			throw new IllegalStateException(period.end.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH)
					+ " can be exported after the month closes");
		}
		List<Map<String, Object>> meters = calculateMeters(obj, period, period.start, period.end);
		meters.sort(Comparator.comparing((Map<String, Object> m) -> READY.equals(m.get("status")) ? 0 : 1)
				.thenComparing(m -> Lib.str(m.get("serial"))));

		String siteCode = Lib.str(period.site.get("site_code"));
		List<String[]> rows = new ArrayList<>();
		rows.add(CSV_ALL_HEADER);
		for (Map<String, Object> m : meters) {
			rows.add(new String[] {
				siteCode,
				Lib.str(m.get("serial")),
				Lib.str(m.get("tenant_name")),
				Lib.str(m.get("location")),
				period.start.toString(),
				period.end.toString(),
				m.get("opening_reading") == null ? "" : Lib.format((Double) m.get("opening_reading"), "#.####"),
				Lib.str(m.get("opening_time")),
				m.get("closing_reading") == null ? "" : Lib.format((Double) m.get("closing_reading"), "#.####"),
				Lib.str(m.get("closing_time")),
				Lib.format(((Number) m.get("multiplier")).doubleValue(), "#.####"),
				m.get("kwh") == null ? "" : Lib.format((Double) m.get("kwh"), "#.##"),
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
	 * @description API 4: daily kWh for the chart
	 */
	public List<Map<String, Object>> getChart(TenantBillingEntity obj) throws Exception {
		Map<String, Object> meter = calculateMeter(obj);
		List<Map<String, Object>> data = new ArrayList<>();
		for (Map<String, Object> day : (List<Map<String, Object>>) meter.get("days")) {
			Map<String, Object> row = new LinkedHashMap<>();
			row.put("date", day.get("date"));
			row.put("kwh", day.get("kwh"));
			row.put("is_peak", day.get("is_peak"));
			row.put("missing", day.get("missing"));
			data.add(row);
		}
		return data;
	}

	/**
	 * @description API 5: CSV with the daily kWh of one meter
	 * TODO: spec only blocks Download all for an open period; single meter is allowed here.
	 * TODO: Note text is not defined yet.
	 */
	public Map<String, Object> exportDevice(TenantBillingEntity obj) throws Exception {
		Map<String, Object> meter = calculateMeter(obj);
		List<String[]> rows = new ArrayList<>();
		rows.add(CSV_DEVICE_HEADER);
		for (Map<String, Object> day : (List<Map<String, Object>>) meter.get("days")) {
			int records = (Integer) day.get("record_count");
			String note = (Boolean) day.get("missing") ? "No data"
					: (Boolean) day.get("partial") ? "Partial data (" + records + "/" + RECORDS_PER_DAY + " readings)" : "";
			rows.add(new String[] { Lib.str(day.get("date")), day.get("kwh") == null ? "" : Lib.format((Double) day.get("kwh"), "#.##"), note });
		}

		StringWriter out = new StringWriter();
		// BOM so Excel opens UTF-8 text correctly
		out.write('﻿');
		try (CSVWriter writer = new CSVWriter(out)) {
			writer.writeAll(rows, false);
		}
		// Tenant_Billing_<serial>_2026-09.csv for a whole month, Tenant_Billing_<serial>_2026-09-05_2026-10-04.csv otherwise
		Period period = (Period) meter.get("period");
		boolean wholeMonth = period.start.getDayOfMonth() == 1
				&& period.end.equals(period.start.withDayOfMonth(period.start.lengthOfMonth()));
		String range = wholeMonth ? period.start.toString().substring(0, 7) : period.start + "_" + period.end;

		Map<String, Object> file = new LinkedHashMap<>();
		file.put("file_name", "Tenant_Billing_" + Lib.str(meter.get("serial")).replaceAll("[^A-Za-z0-9_-]", "") + "_" + range + ".csv");
		file.put("content", out.toString().getBytes(StandardCharsets.UTF_8));
		return file;
	}

	/**
	 * @description API 6: header, banner, This period and Peak day of the side panel
	 */
	public Map<String, Object> getSummaryDevice(TenantBillingEntity obj) throws Exception {
		Map<String, Object> meter = calculateMeter(obj);
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("id_device", meter.get("id_device"));
		result.put("device_name", meter.get("device_name"));
		result.put("serial", meter.get("serial"));
		result.put("tenant_name", meter.get("tenant_name"));
		result.put("location", meter.get("location"));
		result.put("status", meter.get("status"));
		result.put("missing_message", meter.get("missing_message"));
		result.put("period_kwh", meter.get("kwh"));
		result.put("peak_day", meter.get("peak_day"));
		return result;
	}

	/**
	 * @description API 7: average daily kWh = period kWh / days with data
	 */
	public Map<String, Object> getAvgDaily(TenantBillingEntity obj) throws Exception {
		Map<String, Object> meter = calculateMeter(obj);
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("avg_daily", meter.get("avg_daily"));
		result.put("period_kwh", meter.get("kwh"));
		result.put("days_with_data", meter.get("days_with_data"));
		return result;
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

	private Period preparePeriod(TenantBillingEntity obj) throws Exception {
		if (obj.getId_site() <= 0) throw new IllegalArgumentException("id_site is required");
		Map<String, Object> site = (Map<String, Object>) queryForObject("TenantBilling.getSiteInfo", obj);
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

		obj.setTimezone_offset(Lib.str(site.get("timezone_offset")));
		return period;
	}


	// ---------------------------------------------------------------- calculation

	/**
	 * The one meter in obj.id_device, used by API 4-7.
	 */
	private Map<String, Object> calculateMeter(TenantBillingEntity obj) throws Exception {
		if (obj.getId_device() <= 0) throw new IllegalArgumentException("id_device is required");
		Period period = preparePeriod(obj);
		List<Map<String, Object>> meters = calculateMeters(obj, period, period.start, period.end);
		if (meters.isEmpty()) throw new IllegalArgumentException("Meter not found");
		Map<String, Object> meter = meters.get(0);
		meter.put("period", period);
		return meter;
	}

	/**
	 * Tenant meters of the site (or the one in obj.id_device) with their daily kWh and totals for [start, end].
	 * Daily kWh = last reading of the day - last reading of the previous day with data (first reading for the first day),
	 * so the daily values add up to (closing - opening) x multiplier.
	 * TODO: after missing days the next day with data gets the energy of the missing days too.
	 * TODO: readings are multiplied by the multiplier; confirm nvmActiveEnergy is not already scaled.
	 * TODO: a register reset or a meter swap gives a negative kWh; not handled.
	 */
	private List<Map<String, Object>> calculateMeters(TenantBillingEntity obj, Period period, LocalDate start, LocalDate end) throws Exception {
		List<Map<String, Object>> meters = queryForList("TenantBilling.getListTenantMeter", obj);
		if (meters.isEmpty()) return meters;

		TenantBillingEntity query = new TenantBillingEntity();
		query.setTimezone_offset(obj.getTimezone_offset());
		query.setStart_date(start.toString());
		query.setEnd_date(end.toString());
		query.setDevices(meters);
		List<Map<String, Object>> rows = queryForList("TenantBilling.getDailyData", query);

		Map<Integer, Map<String, Map<String, Object>>> byDevice = new HashMap<>();
		for (Map<String, Object> row : rows) {
			int idDevice = ((Number) row.get("id_device")).intValue();
			byDevice.computeIfAbsent(idDevice, k -> new HashMap<>()).put(Lib.str(row.get("date")), row);
		}

		// 5-minute slots from the start of the period until the end of the period or now, whichever is first
		LocalDateTime slotFrom = start.atStartOfDay();
		LocalDateTime slotTo = end.plusDays(1).atStartOfDay();
		if (slotTo.isAfter(period.now)) slotTo = period.now;
		// TODO: the slot that is still running counts too, so a live period is Incomplete for up to 5 minutes.
		long expected = slotTo.isAfter(slotFrom) ? (Duration.between(slotFrom, slotTo).toMinutes() + 4) / 5 : 0;

		// TODO: days after today are left out of the chart.
		LocalDate lastDay = end.isAfter(period.today) ? period.today : end;

		List<Map<String, Object>> result = new ArrayList<>();
		for (Map<String, Object> meter : meters) {
			Map<String, Object> m = new LinkedHashMap<>(meter);
			m.remove("datatablename");
			Map<String, Map<String, Object>> daily = byDevice.getOrDefault(((Number) meter.get("id_device")).intValue(), Collections.emptyMap());
			double multiplier = ((Number) m.get("multiplier")).doubleValue();

			List<Map<String, Object>> days = new ArrayList<>();
			Map<String, Object> firstRow = null;
			Map<String, Object> lastRow = null;
			Double previousLast = null;
			long records = 0;
			int daysWithData = 0;
			Map<String, Object> peak = null;
			List<LocalDate> missingDays = new ArrayList<>();
			List<LocalDate> partialDays = new ArrayList<>();

			for (LocalDate date = start; !date.isAfter(lastDay); date = date.plusDays(1)) {
				Map<String, Object> row = daily.get(date.toString());
				Map<String, Object> day = new LinkedHashMap<>();
				day.put("date", date.toString());
				if (row == null) {
					day.put("kwh", null);
					day.put("record_count", 0);
					day.put("missing", true);
					day.put("partial", false);
					missingDays.add(date);
				} else {
					int count = ((Number) row.get("record_count")).intValue();
					double firstRead = ((Number) row.get("first_read")).doubleValue();
					double lastRead = ((Number) row.get("last_read")).doubleValue();
					double base = previousLast != null ? previousLast : firstRead;
					previousLast = lastRead;
					if (firstRow == null) firstRow = row;
					lastRow = row;
					records += count;
					daysWithData++;

					// TODO: a day with some readings counts as a day with data.
					boolean partial = count < RECORDS_PER_DAY && date.isBefore(period.today);
					if (partial) partialDays.add(date);
					day.put("kwh", Math.round((lastRead - base) * multiplier * 100.0) / 100.0);
					day.put("record_count", count);
					day.put("missing", false);
					day.put("partial", partial);
					// TODO: on a tie the earliest day is the peak.
					if (peak == null || (Double) day.get("kwh") > (Double) peak.get("kwh")) peak = day;
				}
				day.put("is_peak", false);
				days.add(day);
			}
			if (peak != null) peak.put("is_peak", true);

			boolean ready = expected > 0 && records >= expected;
			Double kwh = firstRow == null ? null
					: Math.round((((Number) lastRow.get("last_read")).doubleValue() - ((Number) firstRow.get("first_read")).doubleValue()) * multiplier * 100.0) / 100.0;

			// Banner, e.g. "Missing data on Sep 14–15; partial data on Sep 20."
			// TODO: banner wording is only defined for "no data received this period."
			String missingMessage = null;
			if (!ready && daysWithData == 0) {
				missingMessage = "No data received this period.";
			} else if (!ready) {
				List<String> parts = new ArrayList<>();
				for (List<LocalDate> dates : new List[] { missingDays, partialDays }) {
					if (dates.isEmpty()) continue;
					// [Sep 14, Sep 15, Sep 20] -> "Sep 14–15, Sep 20"
					List<String> ranges = new ArrayList<>();
					LocalDate from = dates.get(0);
					LocalDate to = from;
					for (int i = 1; i <= dates.size(); i++) {
						LocalDate next = i < dates.size() ? dates.get(i) : null;
						if (next != null && next.equals(to.plusDays(1))) {
							to = next;
							continue;
						}
						String label = from.format(LABEL_FORMAT);
						if (!to.equals(from)) {
							label += "–" + (to.getMonth() == from.getMonth() ? String.valueOf(to.getDayOfMonth()) : to.format(LABEL_FORMAT));
						}
						ranges.add(label);
						if (next != null) {
							from = next;
							to = next;
						}
					}
					parts.add((dates == missingDays ? "Missing data on " : "partial data on ") + String.join(", ", ranges));
				}
				String text = parts.isEmpty() ? "Some readings are missing." : String.join("; ", parts) + ".";
				missingMessage = Character.toUpperCase(text.charAt(0)) + text.substring(1);
			}

			m.put("days", days);
			m.put("record_count", records);
			m.put("expected_records", expected);
			m.put("status", ready ? READY : INCOMPLETE);
			m.put("kwh", kwh);
			m.put("opening_reading", firstRow == null ? null : Math.round(((Number) firstRow.get("first_read")).doubleValue() * 10000.0) / 10000.0);
			m.put("opening_time", firstRow == null ? null : firstRow.get("first_time"));
			m.put("closing_reading", lastRow == null ? null : Math.round(((Number) lastRow.get("last_read")).doubleValue() * 10000.0) / 10000.0);
			m.put("closing_time", lastRow == null ? null : lastRow.get("last_time"));
			m.put("days_with_data", daysWithData);
			m.put("avg_daily", daysWithData == 0 ? null : Math.round(kwh / daysWithData * 100.0) / 100.0);
			if (peak == null) {
				m.put("peak_day", null);
			} else {
				Map<String, Object> peakDay = new LinkedHashMap<>();
				peakDay.put("date", peak.get("date"));
				peakDay.put("kwh", peak.get("kwh"));
				m.put("peak_day", peakDay);
			}
			m.put("missing_message", missingMessage);
			result.add(m);
		}
		return result;
	}
}
