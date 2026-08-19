/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Objects;
import java.util.Locale;
import java.util.function.Function;

import com.nwm.api.entities.*;
import org.apache.ibatis.session.SqlSession;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.BorderCollapsePropertyValue;
import com.itextpdf.layout.properties.BorderRadius;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.config.ReportTaskScheduler;
import com.nwm.api.utils.DocumentHelper;
import com.nwm.api.utils.Constants.ChartingFilter;
import com.nwm.api.utils.Constants.ChartingGranularity;
import com.nwm.api.utils.Constants.UploadingDataIntervals;

@Service
public class AnalyticalReportTrackerService extends DB {
	private final static int MAX_PAUSE_REASON_LENGTH = 100;
	private final static int MAX_NOTES_LENGTH = 500;
	private final static DeviceRgb textBlueColor = new DeviceRgb(74, 123, 167);
	private final static DeviceRgb textGrayColor = new DeviceRgb(99, 105, 115);
	private final static DeviceRgb textYellowColor = new DeviceRgb(255, 192, 0);
	private final static DeviceRgb textRedColor = new DeviceRgb(245, 0, 0);
	private final static DeviceRgb borderGrayColor = new DeviceRgb(220, 221, 224);
	private final static DeviceRgb bgBlueColor = new DeviceRgb(0, 143, 210);
	private final static DeviceRgb bgRedColor = new DeviceRgb(245, 66, 34);
	private final static DeviceRgb bgYellowColor = textYellowColor;
	private final static DeviceRgb bgGreenColor = new DeviceRgb(146, 208, 80);
	private final static DeviceRgb bgGrayColor = new DeviceRgb(236, 237, 238);
	private final static DeviceRgb bgLightGrayColor = new DeviceRgb(250, 250, 250);
	private final static Color chartColumnSeriesBlueColor = new Color(0, 143, 210);
	private final static Color chartColumnSeriesGrayColor = new Color(195, 198, 203);
	private final static Color chartLineSeriesYellowColor = new Color(255, 192, 0);
	private final static int smallFontSize = 12;
	private final static int mediumFontSize = 16;
	private final static int largeFontSize = 24;
	private final static int borderRarius = 8; 
	
	private enum Status {
		DRAFT(1),
		SUBMITTED(2),
		SENT(3),
		PAUSED(4);
		
		private final int value;
		
		Status(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return this.value;
		}
		
		public static Status fromValue(int value) {
			for (Status range : Status.values()) {
				if (range.getValue() == value) return range;
			}
			
			return Status.DRAFT;
		}
	}
	
	private enum PortfolioTrackerStatus {
		NO_PRODUCTION("no-production", bgRedColor),
		LOW_PRODUCTION("low-production", bgYellowColor),
		NORMAL("normal", bgGreenColor),
		NO_COMMUNICATION("no-comm", bgGrayColor);
		
		private final String value;
		private final DeviceRgb color;
		
		PortfolioTrackerStatus(String value, DeviceRgb color) {
			this.value = value;
			this.color = color;
		}
		
		public String getValue() {
			return this.value;
		}
		
		public DeviceRgb getColor() {
			return this.color;
		}
		
		public static PortfolioTrackerStatus fromValue(String value) {
			for (PortfolioTrackerStatus status : PortfolioTrackerStatus.values()) {
				if (status.getValue() == value) return status;
			}
			
			return PortfolioTrackerStatus.NORMAL;
		}
	}
	
	@Autowired
	ReportTaskScheduler reportTaskScheduler;
	@Autowired
	AuditingLogsService logsService;
    @Autowired
    DeviceService deviceService;
    @Autowired
    CustomerViewService customerViewService;
    @Autowired
    ReportsService reportsService;
    @Autowired
    SiteService siteService;

	/**
	 * @description save analytical report tracker status
	 * @author Duc-Pham
	 * @since 2026-08-04
	 */
	public AnalyticalReportTrackerDTO saveStatus(AnalyticalReportTrackerDTO obj) {
		if (obj == null || obj.getId_site() <= 0) {
			return null;
		}
		
		AnalyticalReportTrackerEntity entity = new AnalyticalReportTrackerEntity(obj);

		Status status = Status.fromValue(entity.getStatus());
		String pauseReason = entity.getPause_reason() == null ? "" : entity.getPause_reason().trim();
		String notes = entity.getNotes() == null ? "" : entity.getNotes().trim();
		if (status != Status.PAUSED) {
			pauseReason = "";
			notes = "";
		} else if (pauseReason.length() == 0) {
			return null;
		}
		if (pauseReason.length() > MAX_PAUSE_REASON_LENGTH || notes.length() > MAX_NOTES_LENGTH) {
			return null;
		}

		entity.setStatus(status.getValue());
		entity.setPause_reason(pauseReason);
		entity.setNotes(notes);

		try {
			boolean hasReportId = entity.getId() != null && entity.getId().intValue() > 0;

			if (hasReportId) update("AnalyticalReportTracker.updateStatus", entity);
			else insert("AnalyticalReportTracker.insertStatus", entity);
			
			reportTaskScheduler.changeAnalyticalReportTrackerSchedule(entity.getId());

			return new AnalyticalReportTrackerDTO(entity);
		} catch (Exception ex) {
			log.error("AnalyticalReportTracker.saveStatus", ex);
			return null;
		}
	}
	
	/**
	 * @description update next run time for schedule
	 * @author Hung.Bui
	 * @since 2026-08-12
	 */
	
	public boolean updateNextRunTime(Map<String, Object> obj) {
		try {
			return update("AnalyticalReportTracker.updateNextRunTime", obj) > 0;
		} catch (Exception ex) {
			log.error("AnalyticalReportTracker.updateNextRunTime", ex);
			return false;
		}
	}
	
	/**
	 * @description reserve the next scheduled run after sending email now
	 * @author Duc-Pham
	 * @since 2026-08-14
	 */
	public boolean sendNow(int id) {
		try {
			AnalyticalReportTrackerEntity reportTracker = getSubmittedAnalyticalReportTrackerById(id);
			if (reportTracker.getId() == null) return false;

			AnalyticalReportTrackerResponseEntity data = Optional.ofNullable(getSiteGenerationSummary(new AnalyticalReportTrackerDTO(reportTracker))).orElse(new AnalyticalReportTrackerResponseEntity());
			
			String filePath = createPdfFile(data);
			if (filePath == null) return false;
			
//			reportsService.sentReportByMail(filePath, reportTracker.getRecipient_to(), "analytical_report_tracker", 30);
			
			String nextRunTime = reportTaskScheduler.getNextAnalyticalReportTrackerRunTime(reportTracker);
			if (nextRunTime == null) return false;

			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("id", id);
			obj.put("time", nextRunTime);
			return updateNextRunTime(obj);
		} catch (Exception ex) {
			log.error("AnalyticalReportTracker.sendNow", ex);
			return false;
		}
	}

	/**
	 * @description get analytical report tracker by id
	 * @author Hung.Bui
	 * @since 2026-08-12
	 */
	public AnalyticalReportTrackerEntity getSubmittedAnalyticalReportTrackerById(int id) {
		try {
			return Optional.ofNullable((AnalyticalReportTrackerEntity) queryForObject("AnalyticalReportTracker.getSubmittedAnalyticalReportTrackerById", id)).orElse(new AnalyticalReportTrackerEntity());
		} catch (Exception ex) {
			log.error("AnalyticalReportTracker.getAnalyticalReportTrackerById", ex);
			return new AnalyticalReportTrackerEntity();
		}
	}
	
	/**
	 * @description get all analytical report trackers
	 * @author Hung.Bui
	 * @since 2026-08-12
	 */
	public List<AnalyticalReportTrackerEntity> getAllSubmittedAnalyticalReportTrackers() {
		try {
			return Optional.ofNullable(queryForList("AnalyticalReportTracker.getAllSubmittedAnalyticalReportTrackers")).orElse(new ArrayList<AnalyticalReportTrackerEntity>());
		} catch (Exception ex) {
			log.error("AnalyticalReportTracker.getAllAnalyticalReportTrackers", ex);
			return new ArrayList<>();
		}
	}

	/**
	 * @description get tracker summary list
	 * @author Minh Le
	 * @since 2026-08-06
	 */
	public List<AnalyticalReportTrackerDTO> getTrackerSummaryList(Map<String, Object> params) {
		try {
			List<AnalyticalReportTrackerEntity> data = Optional.ofNullable(queryForList("AnalyticalReportTracker.getTrackerSummaryList", params)).orElse(new ArrayList<AnalyticalReportTrackerEntity>());
			Object count = queryForObject("AnalyticalReportTracker.countTrackerSummaryList", params);
			return data.stream().map(AnalyticalReportTrackerDTO::new).collect(Collectors.toList());
		} catch (Exception ex) {
			log.error("AnalyticalReportTracker.getTrackerSummaryList", ex);
			return null;
		}
	}

	/**
	 * @description count total tracker summary
	 * @author Minh Le
	 * @since 2026-08-06
	 */
	public Object countTotalTrackerSummary(Map<String, Object> params) {
		try {
			Object count = queryForObject("AnalyticalReportTracker.countTrackerSummaryList", params);
			return count;
		} catch (Exception ex) {
			log.error("AnalyticalReportTracker.getTrackerSummaryList", ex);
			return null;
		}
	}
	
	/**
	 * @description Get logs
	 * @author Hung.Bui
	 * @since 2026-08-07
	 * @param id
	 */
	public List<AuditLog> getLogs(int id) {
		try {
			List<AnalyticalReportTrackerLogs> logs = Optional.ofNullable(queryForList("AnalyticalReportTracker.getLogs", id)).orElse(new ArrayList<>());
			return logsService.getLogDifferences(logs, null);
		} catch (Exception ex) {
			return new ArrayList<>();
		}
	}

	/**
	 * @description Get global config detail
	 * @author Duc-Pham
	 * @since 2026-08-11
	 */
	public AnalyticalReportTrackerGlobalConfigDTO getGlobalConfigDetail() {
		try {
			AnalyticalReportTrackerGlobalConfigDTO data = new AnalyticalReportTrackerGlobalConfigDTO();
			List<AnalyticalReportTrackerGlobalConfigActionFlagEntity> actionFlags = Optional.ofNullable(queryForList("AnalyticalReportTracker.getGlobalConfigActionFlagList")).orElse(new ArrayList<>());
			List<AnalyticalReportTrackerGlobalConfigCurrentStatusEntity> currentStatuses = Optional.ofNullable(queryForList("AnalyticalReportTracker.getGlobalConfigCurrentStatusList")).orElse(new ArrayList<>());
			List<AnalyticalReportTrackerGlobalConfigPathForwardUpdateEntity> pathForwardUpdates = Optional.ofNullable(queryForList("AnalyticalReportTracker.getGlobalConfigPathForwardUpdateList")).orElse(new ArrayList<>());
			List<AnalyticalReportTrackerGlobalConfigRuleEntity> performanceRules = Optional.ofNullable(queryForList("AnalyticalReportTracker.getGlobalConfigRuleList")).orElse(new ArrayList<>());
			List<AnalyticalReportTrackerGlobalConfigPerformanceStatusMappingEntity> performanceStatusMappings = Optional.ofNullable(queryForList("AnalyticalReportTracker.getGlobalConfigPerformanceStatusMappingList")).orElse(new ArrayList<>());
			List<AnalyticalReportTrackerGlobalConfigDefinitionsGlossaryEntity> definitionsGlossary = Optional.ofNullable(queryForList("AnalyticalReportTracker.getGlobalConfigDefinitionsGlossaryList")).orElse(new ArrayList<>());
			setPerformanceRuleScores(performanceRules);
			data.setActionFlags(actionFlags);
			data.setCurrentStatuses(currentStatuses);
			data.setPathForwardUpdates(pathForwardUpdates);
			data.setPerformanceRules(performanceRules);
			data.setPerformanceStatusMappings(performanceStatusMappings);
			data.setDefinitionsGlossary(definitionsGlossary);
			if (!actionFlags.isEmpty()) data.setModified_by(actionFlags.get(0).getModified_by());
			else if (!currentStatuses.isEmpty()) data.setModified_by(currentStatuses.get(0).getModified_by());
			else if (!pathForwardUpdates.isEmpty()) data.setModified_by(pathForwardUpdates.get(0).getModified_by());
			else if (!performanceRules.isEmpty()) data.setModified_by(performanceRules.get(0).getModified_by());
			else if (!performanceStatusMappings.isEmpty()) data.setModified_by(performanceStatusMappings.get(0).getModified_by());
			else if (!definitionsGlossary.isEmpty()) data.setModified_by(definitionsGlossary.get(0).getModified_by());
			return data;
		} catch (Exception ex) {
			log.error("AnalyticalReportTracker.getGlobalConfigDetail", ex);
			return null;
		}
	}

	/**
	 * @description Save global config
	 * @author Duc-Pham
	 * @since 2026-08-11
	 */
	public AnalyticalReportTrackerGlobalConfigDTO saveGlobalConfig(AnalyticalReportTrackerGlobalConfigDTO obj) {
		if (obj == null || obj.getModified_by() == null || obj.getModified_by().intValue() <= 0) {
			return null;
		}
		if (!hasValidPerformanceRuleRanges(obj.getPerformanceRules())) {
			log.warn("AnalyticalReportTracker.saveGlobalConfig: performance rule ranges overlap");
			return null;
		}

		SqlSession session = this.beginTransaction();
		try {
			if (obj.getActionFlags() != null) {
				deleteMissingGlobalConfigItems(session, "AnalyticalReportTracker.deleteGlobalConfigActionFlags", obj.getActionFlags(), AnalyticalReportTrackerGlobalConfigActionFlagEntity::getId);
				if (obj.getActionFlags().size() > 0) session.insert("AnalyticalReportTracker.insertGlobalConfigActionFlags", obj);
			}
			if (obj.getCurrentStatuses() != null) {
				deleteMissingGlobalConfigItems(session, "AnalyticalReportTracker.deleteGlobalConfigCurrentStatuses", obj.getCurrentStatuses(), AnalyticalReportTrackerGlobalConfigCurrentStatusEntity::getId);
				if (obj.getCurrentStatuses().size() > 0) session.insert("AnalyticalReportTracker.insertGlobalConfigCurrentStatuses", obj);
			}
			if (obj.getPathForwardUpdates() != null) {
				deleteMissingGlobalConfigItems(session, "AnalyticalReportTracker.deleteGlobalConfigPathForwardUpdates", obj.getPathForwardUpdates(), AnalyticalReportTrackerGlobalConfigPathForwardUpdateEntity::getId);
				if (obj.getPathForwardUpdates().size() > 0) session.insert("AnalyticalReportTracker.insertGlobalConfigPathForwardUpdates", obj);
			}
			if (obj.getPerformanceRules() != null) {
				deleteMissingGlobalConfigItems(session, "AnalyticalReportTracker.deleteGlobalConfigRules", obj.getPerformanceRules(), AnalyticalReportTrackerGlobalConfigRuleEntity::getId);
				if (obj.getPerformanceRules().size() > 0) session.insert("AnalyticalReportTracker.insertGlobalConfigRules", obj);
			}
			if (obj.getPerformanceStatusMappings() != null) {
				deleteMissingGlobalConfigItems(session, "AnalyticalReportTracker.deleteGlobalConfigPerformanceStatusMapping", obj.getPerformanceStatusMappings(), AnalyticalReportTrackerGlobalConfigPerformanceStatusMappingEntity::getId);
				if (obj.getPerformanceStatusMappings().size() > 0) session.insert("AnalyticalReportTracker.insertGlobalConfigPerformanceStatusMapping", obj);
			}

			if (obj.getDefinitionsGlossary() != null) {
				deleteMissingGlobalConfigItems(session, "AnalyticalReportTracker.deleteGlobalConfigDefinitionsGlossary", obj.getDefinitionsGlossary(), AnalyticalReportTrackerGlobalConfigDefinitionsGlossaryEntity::getId);
				if (obj.getDefinitionsGlossary().size() > 0) session.insert("AnalyticalReportTracker.insertGlobalConfigDefinitionsGlossary", obj);
			}

			session.commit();
			return getGlobalConfigDetail();
		} catch (Exception ex) {
			session.rollback();
			log.error("AnalyticalReportTracker.saveGlobalConfig", ex);
			return null;
		} finally {
			session.close();
		}
	}

	private <T> void deleteMissingGlobalConfigItems(SqlSession session, String statement, List<T> items, Function<T, Integer> getId) {
		List<Integer> itemIds = items.stream()
				.map(getId)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		session.delete(statement, itemIds);
	}

	private void setPerformanceRuleScores(List<AnalyticalReportTrackerGlobalConfigRuleEntity> rules) {
        if (rules == null) return;

        final BigDecimal maxScore = BigDecimal.valueOf(10);
        BigDecimal upperBound = maxScore;
        for (AnalyticalReportTrackerGlobalConfigRuleEntity rule : rules) {
            if (rule == null) continue;

            String operator = rule.getOperator() == null ? "" : rule.getOperator().trim();
            BigDecimal threshold = rule.getThreshold();
            if (threshold == null) {
                rule.setScore("");
                upperBound = maxScore;
                continue;
            }
            String thresholdText = threshold.stripTrailingZeros().toPlainString() + "%";
            String condition = "=".equals(operator) ? thresholdText : (operator + " " + thresholdText).trim();
            if (!isLowerBoundOperator(operator)) {
                rule.setScore(condition);
                upperBound = maxScore;
                continue;
            }

            BigDecimal lowerBound = threshold;
            if (">".equals(operator)) lowerBound = lowerBound.add(BigDecimal.ONE);

            if (upperBound.compareTo(maxScore) == 0) {
                rule.setScore(operator + thresholdText);
            } else if (upperBound.compareTo(lowerBound) <= 0) {
                rule.setScore(condition);
            } else {
                rule.setScore(upperBound.stripTrailingZeros().toPlainString() + "% – " + lowerBound.stripTrailingZeros().toPlainString() + "%");
            }
            upperBound = lowerBound.subtract(BigDecimal.ONE);
        }
    }

	private boolean isLowerBoundOperator(String operator) {
        return ">=".equals(operator) || "≥".equals(operator) || ">".equals(operator);
    }

	private boolean hasValidPerformanceRuleRanges(List<AnalyticalReportTrackerGlobalConfigRuleEntity> rules) {
		if (rules == null) return true;

		BigDecimal upper = null;
		boolean upperInclusive = false;
		BigDecimal lowestLower = null;
		boolean lowerInclusiveAtUpper = false;
		for (AnalyticalReportTrackerGlobalConfigRuleEntity rule : rules) {
			if (rule == null || rule.getThreshold() == null) continue;

			BigDecimal threshold = rule.getThreshold();

			String operator = rule.getOperator() == null ? "" : rule.getOperator().trim();
			if ("<".equals(operator) || "<=".equals(operator) || "≤".equals(operator)) {
				if (upper != null) return false;
				upper = threshold;
				upperInclusive = !"<".equals(operator);
			} else if (isLowerBoundOperator(operator) && (lowestLower == null
					|| threshold.compareTo(lowestLower) < 0
					|| (threshold.compareTo(lowestLower) == 0 && !">".equals(operator)))) {
				lowestLower = threshold;
				lowerInclusiveAtUpper = !">".equals(operator);
			}
		}
		if (upper == null || lowestLower == null) return true;
		int comparison = lowestLower.compareTo(upper);
		return comparison > 0 || (comparison == 0 && (!lowerInclusiveAtUpper || !upperInclusive));
	}

	
	private LocalDateTime getReportDate(String type,String timezoneValue) {
	    ZoneId zoneId = ZoneId.of(timezoneValue);
	    LocalDate yesterday = ZonedDateTime.now(zoneId).toLocalDate().minusDays(1);

	    switch (type.toLowerCase()) {
	        case "yesterday":
	            return yesterday.atStartOfDay();
	        case "yesterday_end":
	            return yesterday.atTime(23, 59, 59);
	        case "yesterday_6_days":
	            return yesterday.minusDays(6).atStartOfDay();
	        case "first_day_last_month":
	            return yesterday.withDayOfMonth(1).minusMonths(1).atStartOfDay();
	        case "last_day_last_month":
	            return yesterday.withDayOfMonth(1).minusDays(1).atTime(23, 59, 59);
	        default:
	        	return yesterday.atStartOfDay();
	    }
	}
	
	public List<InverterAlertReportEntity> getListAlertInverterBySiteId(int id_site, LocalDateTime end_date) {

	    try {
	        Map<String, Object> params = new HashMap<>();

	        params.put("id_site", id_site);
	        params.put("end_date", end_date);

	        return Optional.ofNullable(queryForList("AnalyticalReportTracker.getListAlertInverterBySiteId", params)).orElse(new ArrayList<>());

	    } catch (Exception e) {
	        return new ArrayList<>();
	    }
	}
	
	/**
	 * @description Get site generation summary
	 * @author Duy.Phan
	 * @since 2026-08-07
	 * @param id
	 */
	public AnalyticalReportTrackerResponseEntity getSiteGenerationSummary(AnalyticalReportTrackerDTO obj) {
		try {
			AnalyticalReportTrackerResponseEntity dataObj = new AnalyticalReportTrackerResponseEntity(obj);
			Optional<SiteEntity> siteOptional = siteService.getSiteById(obj.getId_site());
			SiteEntity site = siteOptional.get();

	        if (site != null) {
	            dataObj.setData_send_time(site.getData_send_time());
	            dataObj.setTimezone_value(site.getTime_zone_value());
	            dataObj.setSite_name(site.getName());
	        }
				
			LocalDateTime startDate = getReportDate("first_day_last_month", dataObj.getTimezone_value());	
			LocalDateTime endDate = getReportDate("yesterday_end", dataObj.getTimezone_value());
			LocalDateTime startDateBaseOnCadence = obj.getCadence() == 1 ? getReportDate("yesterday", dataObj.getTimezone_value()) : getReportDate("yesterday_6_days", dataObj.getTimezone_value());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			dataObj.setStart_date(startDate.format(formatter));
			dataObj.setEnd_date(endDate.format(formatter));
			dataObj.setStart_date_base_on_cadence(startDateBaseOnCadence.format(formatter));
			
			ChartingGranularity granularity = ChartingGranularity._1_DAY;
			ChartingFilter filter = ChartingFilter.THIS_MONTH;
			UploadingDataIntervals siteUploadingInterval = UploadingDataIntervals.fromValue(dataObj.getData_send_time());
			DevicesByTypeEntity devices = deviceService.getDevicesBySite(dataObj);
			List<DeviceEntity> powerDevices = !devices.getMeter().isEmpty() ? devices.getMeter() : devices.getInverter();
			List<DeviceEntity> irradianceDevices = devices.getIrradiance();
			
			List<ClientMonthlyDateEntity> productionReportList = reportsService.getActualBySiteDevices(powerDevices, startDate, endDate, granularity, filter)
				.stream()
				.map(item -> {
					ClientMonthlyDateEntity entity = new ClientMonthlyDateEntity();
					entity.setCategories_time(reportsService.dateTimeFormatConverter(granularity, item.getCategories_time(), DateTimeFormatter.ofPattern("MM/dd")));
					entity.setDownload_time(reportsService.dateTimeFormatConverter(granularity, item.getCategories_time(), DateTimeFormatter.ofPattern("MM/dd/yyyy")));
					entity.setChart_energy_kwh(item.getEnergy());
					
					return entity;
				})
				.collect(Collectors.toList());
			
			Map<String, Double> estimatedData = irradianceDevices.isEmpty() ? reportsService.getEnergyExpectation(startDate, obj.getId_site()) : new HashMap<>();
			List<ClientMonthlyDateEntity> expectedData = irradianceDevices.isEmpty() ?
				new ArrayList<>()
				:
				irradianceDevices.size() == 1 ?
					customerViewService.getIrradianceByDevice(startDate, endDate, irradianceDevices.get(0), granularity, filter, false, siteUploadingInterval)
					:
					customerViewService.getExpectedBySelectedPOA(startDate, endDate, obj.getId_site(), granularity, filter, irradianceDevices);
			
			for (int i = 0; i < productionReportList.size(); i++) {
				ClientMonthlyDateEntity actualItem = productionReportList.get(i);
				LocalDate date = LocalDate.parse(actualItem.getDownload_time(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
				
				Double actual = actualItem.getChart_energy_kwh();
				
				Double estimated = irradianceDevices.isEmpty() ?
					Optional.ofNullable(estimatedData.get(date.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toLowerCase())).map(value ->  value / date.lengthOfMonth()).orElse(null)
					:
					!expectedData.isEmpty() ? expectedData.get(i).getExpected_energy() : null;
				
				Double nvm_irradiance = irradianceDevices.isEmpty() ?
						Optional.ofNullable(estimatedData.get(date.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toLowerCase())).map(value ->  value / date.lengthOfMonth()).orElse(null)
						:
						!expectedData.isEmpty() ? expectedData.get(i).getNvm_irradiance() : null;
				
				if (Objects.nonNull(actual)) {
					actualItem.setChart_energy_kwh(BigDecimal.valueOf(actual).setScale(0, RoundingMode.HALF_UP).doubleValue());
				}
				
				if (Objects.nonNull(actual) && Objects.nonNull(estimated) && Objects.nonNull(nvm_irradiance)) {
					actualItem.setNvm_irradiance(BigDecimal.valueOf(nvm_irradiance).setScale(0, RoundingMode.HALF_UP).doubleValue());
					actualItem.setExpected_energy(BigDecimal.valueOf(estimated).setScale(0, RoundingMode.HALF_UP).doubleValue());
				}
			}
			
			dataObj.setProductionReportList(productionReportList);
			
			// PRODUCTION REPORT
			double totalActualGeneration = productionReportList.stream().filter(item -> Objects.nonNull(item.getChart_energy_kwh())).mapToDouble(ClientMonthlyDateEntity::getChart_energy_kwh).sum();
			double totalExpectedGeneration = productionReportList.stream().filter(item -> Objects.nonNull(item.getExpected_energy())).mapToDouble(ClientMonthlyDateEntity::getExpected_energy).sum();
			double totalActualExpected = totalExpectedGeneration > 0 ? BigDecimal.valueOf(totalActualGeneration / totalExpectedGeneration * 100).setScale(0, RoundingMode.HALF_UP).doubleValue() : 0;
			double poaIrradiance = BigDecimal.valueOf(productionReportList.stream().filter(item -> Objects.nonNull(item.getNvm_irradiance())).mapToDouble(ClientMonthlyDateEntity::getNvm_irradiance).average()
			                		.orElse(0.0)).setScale(2, RoundingMode.HALF_UP).doubleValue();
			dataObj.setTotalActualGeneration(totalActualGeneration);
			dataObj.setTotalExpectedGeneration(totalExpectedGeneration);
			dataObj.setPoaIrradiance(poaIrradiance);
			dataObj.setTotalActualExpected(totalActualExpected);
			
			
			// SITE GENERATION SUMMARY
			int numberOfDays = obj.getCadence() == 1 ? 1 : 7;
			int startIndex = Math.max(0,productionReportList.size() - numberOfDays);
			List<ClientMonthlyDateEntity> generationSummaryList = new ArrayList<>(productionReportList.subList(startIndex, productionReportList.size()));
			
			double totalActual = generationSummaryList.stream().filter(item -> Objects.nonNull(item.getChart_energy_kwh())).mapToDouble(ClientMonthlyDateEntity::getChart_energy_kwh).sum();
			double totalExpected = generationSummaryList.stream().filter(item -> Objects.nonNull(item.getExpected_energy())).mapToDouble(ClientMonthlyDateEntity::getExpected_energy).sum();
			double actualExpected = totalExpected > 0 ? BigDecimal.valueOf(totalActual / totalExpected * 100).setScale(1, RoundingMode.HALF_UP).doubleValue() : 0.0;
			
			dataObj.setGenerationSummaryList(generationSummaryList);
			dataObj.setTotalActual(totalActual);
			dataObj.setTotalExpected(totalExpected);
			dataObj.setActualExpected(actualExpected);
			
			// Inverters
			List<DeviceEntity> inverterDevices = devices.getInverter();
			Map<Integer, DeviceEntity> inverterDeviceMap = inverterDevices.stream().collect(Collectors.toMap(DeviceEntity::getId,device -> device,(first, second) -> first));
			List<PerformanceDataChartItemEntity> inverterDataList = new ArrayList<>();

			if (!inverterDevices.isEmpty()) {
				Map<Integer, List<ClientMonthlyDateEntity>> dataByDevices = customerViewService.getEnergyByDevice(startDate, endDate, inverterDevices, granularity, filter, false);
				
				if (!dataByDevices.isEmpty()) {
					dataByDevices.forEach((deviceId, data) -> {
						DeviceEntity device = inverterDeviceMap.get(deviceId);
						String deviceName = device != null ? device.getDevicename() : "";
						
						List<ClientMonthlyDateEntity> dataByDevice = data.stream().map(item -> {
							ClientMonthlyDateEntity entityItem = new ClientMonthlyDateEntity();
							entityItem.setTime_full(item.getTime_full());
							entityItem.setCategories_time(item.getCategories_time());
							entityItem.setDownload_time(LocalDate.parse(item.getTime_full(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
							entityItem.setChart_energy_kwh(Objects.nonNull(item.getChart_energy_kwh()) ? BigDecimal.valueOf(item.getChart_energy_kwh()).setScale(0, RoundingMode.HALF_UP).doubleValue() : null);
							
							return entityItem;
						}).collect(Collectors.toList());
						
						inverterDataList.add(new PerformanceDataChartItemEntity(dataByDevice, deviceId, "Inverter", "kWh", deviceName));
					});
				}
			}
			dataObj.setInverterDataList(inverterDataList);
			
			//Alerts - Portfolio Tracker
			List<InverterAlertReportEntity> inverterAlerts = getListAlertInverterBySiteId(dataObj.getId_site(), endDate);
			Map<Integer, InverterAlertReportEntity> alertByDevice = inverterAlerts.stream()
			            .filter(alert -> alert.getId_device() != null && ("1000".equals(alert.getError_code()) || "1001".equals(alert.getError_code())))
			            .collect(Collectors.toMap(InverterAlertReportEntity::getId_device, Function.identity(),
			                    (first, second) -> {
			                        if ("1001".equals(first.getError_code())) {
			                            return first;
			                        }
			                        if ("1001".equals(second.getError_code())) {
			                            return second;
			                        }
			                        return first;
			                    }
			            ));

			double maxDcRating = inverterDevices.stream().map(DeviceEntity::getRating_ac_power).filter(Objects::nonNull).mapToDouble(Double::doubleValue).max().orElse(0.0);

			Map<Integer, Double> normalizedProductionMap = new HashMap<>();
			double maxNormalizedProduction = 0.0;

			if (maxDcRating > 0) {
			    for (PerformanceDataChartItemEntity inverterData : inverterDataList) {
			        Integer deviceId = inverterData.getId_device();
			        if (deviceId == null || alertByDevice.containsKey(deviceId)) {
			            continue;
			        }

			        DeviceEntity device = inverterDeviceMap.get(deviceId);
			        if (device == null ||
			            device.getRating_ac_power() == null ||
			            device.getRating_ac_power() <= 0) {
			            continue;
			        }

			        List<ClientMonthlyDateEntity> dataEnergy = inverterData.getData_energy();
			        if (dataEnergy == null || dataEnergy.isEmpty()) {
			            continue;
			        }

			        Double production = dataEnergy.get(dataEnergy.size() - 1).getChart_energy_kwh();

			        if (production == null) {
			            continue;
			        }

			        double normalizedProduction = production * maxDcRating / device.getRating_ac_power();
			        normalizedProductionMap.put(deviceId, normalizedProduction);
			        maxNormalizedProduction =Math.max(maxNormalizedProduction, normalizedProduction);
			    }
			}

			List<PortfolioAnalyticalReportTrackerEntity> portfolioTrackerList = new ArrayList<>();
			int noProductionCount = 0;
			int noCommCount = 0;
			int lowProductionCount = 0;
			int normalCount = 0;

			for (PerformanceDataChartItemEntity inverterData : inverterDataList) {
			    Integer deviceId = inverterData.getId_device();
			    if (deviceId == null) {
			        continue;
			    }

			    PortfolioAnalyticalReportTrackerEntity item = new PortfolioAnalyticalReportTrackerEntity(deviceId, inverterData.getDevicename());
			    InverterAlertReportEntity alert = alertByDevice.get(deviceId);
			    if (alert != null) {
			        if ("1001".equals(alert.getError_code())) {
			            item.setStatus("no-comm");
			            noCommCount++;
			        } else {
			            item.setStatus("no-production");
			            noProductionCount++;
			        }
			        item.setIssue_started(alert.getStart_date());
			        portfolioTrackerList.add(item);
			        continue;
			    }

			    Double normalizedProduction = normalizedProductionMap.get(deviceId);
			    if (normalizedProduction == null ||
			        maxNormalizedProduction <= 0) {
			        item.setStatus("no-data");
			        portfolioTrackerList.add(item);
			        continue;
			    }

			    double threshold = (normalizedProduction / maxNormalizedProduction - 1) * 100;
			    if (threshold <= -10.0) {
			        item.setStatus("low-production");
			        item.setLow_production_threshold(BigDecimal.valueOf(threshold).setScale(0, RoundingMode.HALF_UP).doubleValue());
			        List<ClientMonthlyDateEntity> dataEnergy = inverterData.getData_energy();
			        if (dataEnergy != null && !dataEnergy.isEmpty()) {
			        	item.setIssue_started(dataEnergy.get(dataEnergy.size() - 1).getDownload_time());
			        }
			        lowProductionCount++;

			    } else {
			        item.setStatus("normal");
			        normalCount++;
			    }

			    portfolioTrackerList.add(item);
			}

			dataObj.setPortfolioTrackerList(portfolioTrackerList);
			dataObj.setNoProductionCount(noProductionCount);
			dataObj.setNoCommCount(noCommCount);
			dataObj.setLowProductionCount(lowProductionCount);
			dataObj.setNormalCount(normalCount);
			
			
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * @description create pdf file
	 * @author Hung.Bui
	 * @since 2026-08-17
	 * @param obj
	 */
	public String createPdfFile(AnalyticalReportTrackerResponseEntity obj) {
		try {
			if (Objects.isNull(obj)) return null;

			File file = reportsService.writeToPdfFile("Tracker-Summary-Report");
			
			try (
				PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file));
				Document document = new Document(pdfDocument, PageSize.A3);
			) {
				// handle footer
				pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new ReportFooterHandler());
		        
				Date startDate = Date.from(getReportDate("first_day_last_month", obj.getTimezone_value()).atZone(ZoneId.of(obj.getTimezone_value())).toInstant());
				Date endDate = Date.from(getReportDate("yesterday_end", obj.getTimezone_value()).atZone(ZoneId.of(obj.getTimezone_value())).toInstant());
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				SimpleDateFormat categoriesFormat = new SimpleDateFormat("MM/dd");
				DecimalFormat noDecimalFormat = new DecimalFormat(DocumentHelper.noDecimalDataFormat);
				DecimalFormat noDecimalWithPercentageFormat = new DecimalFormat(DocumentHelper.noDecimalPlaceWithPercentageDataFormat);
				Image logoImage = DocumentHelper.readLogoImageFile();
				logoImage.scaleToFit(60, 60);
				logoImage.setFixedPosition(750, 1100);
				
				// Portfolio Tracker
				// page title
				document.add(new Paragraph(obj.getSite_name().toUpperCase().concat(" PORTFOLIO TRACKER"))
						.setFontSize(14)
						.setTextAlignment(TextAlignment.CENTER)
						.setBold()
				);
				document.add(logoImage);
				document.showTextAligned(
						new Paragraph("Report Date: ".concat(obj.getEnd_date()))
							.setMarginBottom(50)
							.setFontSize(smallFontSize)
							.setFontColor(textGrayColor)
						, 810, 1080, TextAlignment.RIGHT
				);
				
				List<PortfolioAnalyticalReportTrackerEntity> portfolioTracker = Optional.ofNullable(obj.getPortfolioTrackerList()).orElse(new ArrayList<>());
				
				Table portfolioTrackerTable = new Table(9).useAllAvailableWidth();
				portfolioTrackerTable.setFontSize(smallFontSize);
				portfolioTrackerTable.setMarginTop(100);
				portfolioTrackerTable.setBorderCollapse(BorderCollapsePropertyValue.SEPARATE);

				// table header
				portfolioTrackerTable.addCell(new Cell().add(new Paragraph("SITE NAME"))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgBlueColor)
						.setBorder(new SolidBorder(borderGrayColor, 1))
						.setBorderTopLeftRadius(new BorderRadius(borderRarius))
						.setFontSize(14)
						.setFontColor(ColorConstants.WHITE)
						.setBold()
				);
				portfolioTrackerTable.addCell(new Cell().add(new Paragraph("INV#"))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgBlueColor)
						.setBorder(new SolidBorder(borderGrayColor, 1))
						.setFontSize(14)
						.setFontColor(ColorConstants.WHITE)
						.setBold()
				);
				portfolioTrackerTable.addCell(new Cell().add(new Paragraph("STATUS"))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgBlueColor)
						.setBorder(new SolidBorder(borderGrayColor, 1))
						.setFontSize(14)
						.setFontColor(ColorConstants.WHITE)
						.setBold()
				);
				portfolioTrackerTable.addCell(new Cell().add(new Paragraph("ISSUE STARTED"))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgBlueColor)
						.setBorder(new SolidBorder(borderGrayColor, 1))
						.setFontSize(14)
						.setFontColor(ColorConstants.WHITE)
						.setBold()
				);
				portfolioTrackerTable.addCell(new Cell().add(new Paragraph("SITE VISIT"))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgBlueColor)
						.setBorder(new SolidBorder(borderGrayColor, 1))
						.setFontSize(14)
						.setFontColor(ColorConstants.WHITE)
						.setBold()
				);
				portfolioTrackerTable.addCell(new Cell().add(new Paragraph("LOSS %"))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgBlueColor)
						.setBorder(new SolidBorder(borderGrayColor, 1))
						.setFontSize(14)
						.setFontColor(ColorConstants.WHITE)
						.setBold()
				);
				portfolioTrackerTable.addCell(new Cell().add(new Paragraph("DESCRIPTION OF CURRENT STATUS"))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgBlueColor)
						.setBorder(new SolidBorder(borderGrayColor, 1))
						.setFontSize(14)
						.setFontColor(ColorConstants.WHITE)
						.setBold()
				);
				portfolioTrackerTable.addCell(new Cell().add(new Paragraph("PATH FORWARD UPDATE"))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgBlueColor)
						.setBorder(new SolidBorder(borderGrayColor, 1))
						.setFontSize(14)
						.setFontColor(ColorConstants.WHITE)
						.setBold()
				);
				portfolioTrackerTable.addCell(new Cell().add(new Paragraph("RESOLVED"))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgBlueColor)
						.setBorder(new SolidBorder(borderGrayColor, 1))
						.setBorderTopRightRadius(new BorderRadius(borderRarius))
						.setFontSize(14)
						.setFontColor(ColorConstants.WHITE)
						.setBold()
				);

				// table rows
				for (int i = 0; i < portfolioTracker.size(); i++) {
					PortfolioAnalyticalReportTrackerEntity item = portfolioTracker.get(i);
					
					portfolioTrackerTable.addCell(new Cell().add(new Paragraph(obj.getSite_name()))
							.setTextAlignment(TextAlignment.CENTER)
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBorder(new SolidBorder(bgLightGrayColor, 1))
							.setBorderBottomLeftRadius(new BorderRadius(i == portfolioTracker.size() - 1 ? borderRarius : 0))
					);
					portfolioTrackerTable.addCell(new Cell().add(new Paragraph(item.getDevicename()))
							.setTextAlignment(TextAlignment.CENTER)
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBorder(new SolidBorder(bgLightGrayColor, 1))
					);
					portfolioTrackerTable.addCell(new Cell().add(new Paragraph("").setPadding(10).setBackgroundColor(PortfolioTrackerStatus.fromValue(item.getStatus()).getColor()))
							.setTextAlignment(TextAlignment.CENTER)
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBorder(new SolidBorder(bgLightGrayColor, 1))
					);
					portfolioTrackerTable.addCell(new Cell().add(new Paragraph(Optional.ofNullable(item.getIssue_started()).orElse("-")))
							.setTextAlignment(TextAlignment.CENTER)
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBorder(new SolidBorder(bgLightGrayColor, 1))
					);
					portfolioTrackerTable.addCell(new Cell().add(new Paragraph("-"))
							.setTextAlignment(TextAlignment.CENTER)
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBorder(new SolidBorder(bgLightGrayColor, 1))
					);
					portfolioTrackerTable.addCell(new Cell().add(new Paragraph("-"))
							.setTextAlignment(TextAlignment.CENTER)
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBorder(new SolidBorder(bgLightGrayColor, 1))
							.setFontColor(textRedColor)
					);
					portfolioTrackerTable.addCell(new Cell().add(new Paragraph(""))
							.setTextAlignment(TextAlignment.CENTER)
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBorder(new SolidBorder(bgLightGrayColor, 1))
					);
					portfolioTrackerTable.addCell(new Cell().add(new Paragraph(""))
							.setTextAlignment(TextAlignment.CENTER)
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBorder(new SolidBorder(bgLightGrayColor, 1))
					);
					portfolioTrackerTable.addCell(new Cell().add(new Paragraph("-"))
							.setTextAlignment(TextAlignment.CENTER)
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBorder(new SolidBorder(bgLightGrayColor, 1))
							.setBorderBottomRightRadius(new BorderRadius(i == portfolioTracker.size() - 1 ? borderRarius : 0))
					);
				};
				
				Table summaryOfStatusTable = new Table(2);
				summaryOfStatusTable.setFontSize(smallFontSize);
				summaryOfStatusTable.setMarginTop(100);

				summaryOfStatusTable.addCell(new Cell().add(new Paragraph("Summary of Status"))
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBorder(Border.NO_BORDER)
						.setBold()
				);
				summaryOfStatusTable.addCell(new Cell().add(new Paragraph("Count"))
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBorder(Border.NO_BORDER)
						.setBold()
				);
				summaryOfStatusTable.addCell(new Cell().add(new Paragraph("No Power"))
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgRedColor)
						.setBorder(Border.NO_BORDER)
						.setFontColor(ColorConstants.WHITE)
						.setBold()
				);
				summaryOfStatusTable.addCell(new Cell().add(new Paragraph(Optional.ofNullable(obj.getNoProductionCount()).orElse(0).toString()))
						.setTextAlignment(TextAlignment.RIGHT)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgRedColor)
						.setBorder(Border.NO_BORDER)
						.setFontColor(ColorConstants.WHITE)
						.setBold()
				);
				summaryOfStatusTable.addCell(new Cell().add(new Paragraph("Low Power"))
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgYellowColor)
						.setBorder(Border.NO_BORDER)
						.setFontColor(ColorConstants.WHITE)
						.setBold()
				);
				summaryOfStatusTable.addCell(new Cell().add(new Paragraph(Optional.ofNullable(obj.getLowProductionCount()).orElse(0).toString()))
						.setTextAlignment(TextAlignment.RIGHT)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgYellowColor)
						.setBorder(Border.NO_BORDER)
						.setFontColor(ColorConstants.WHITE)
						.setBold()
				);
				summaryOfStatusTable.addCell(new Cell().add(new Paragraph("Nominal"))
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgGreenColor)
						.setBorder(Border.NO_BORDER)
						.setFontColor(ColorConstants.WHITE)
						.setBold()
				);
				summaryOfStatusTable.addCell(new Cell().add(new Paragraph(Optional.ofNullable(obj.getNormalCount()).orElse(0).toString()))
						.setTextAlignment(TextAlignment.RIGHT)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgGreenColor)
						.setBorder(Border.NO_BORDER)
						.setFontColor(ColorConstants.WHITE)
						.setBold()
				);
				summaryOfStatusTable.addCell(new Cell().add(new Paragraph("No Communication"))
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgGrayColor)
						.setBorder(Border.NO_BORDER)
						.setFontColor(ColorConstants.WHITE)
						.setBold()
				);
				summaryOfStatusTable.addCell(new Cell().add(new Paragraph(Optional.ofNullable(obj.getNoCommCount()).orElse(0).toString()))
						.setTextAlignment(TextAlignment.RIGHT)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgGrayColor)
						.setBorder(Border.NO_BORDER)
						.setFontColor(ColorConstants.WHITE)
						.setBold()
				);
				
				document.add(portfolioTrackerTable);
				document.add(summaryOfStatusTable);
				document.add(new AreaBreak());

				// Production Report
				// page title
				document.add(new Paragraph(obj.getSite_name().toUpperCase().concat(" PRODUCTION REPORT"))
						.setFontSize(mediumFontSize)
						.setBold()
				);
				document.add(new Paragraph(obj.getStart_date().concat(" - ").concat(obj.getEnd_date()))
						.setMarginBottom(50)
						.setFontSize(smallFontSize)
				);
				document.add(logoImage);

				Table totalProductionReportTable = new Table(3).useAllAvailableWidth();
				totalProductionReportTable.setMarginBottom(50);
				totalProductionReportTable.setFontSize(smallFontSize);

				totalProductionReportTable.addCell(new Cell().add(new Paragraph("TOTAL ACTUAL GENERATION"))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setBorder(Border.NO_BORDER)
						.setFontColor(textGrayColor)
						.setBold()
				);
				totalProductionReportTable.addCell(new Cell().add(new Paragraph("TOTAL EXPECTED GENERATION"))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setBorder(Border.NO_BORDER)
						.setFontColor(textGrayColor)
						.setBold()
				);
				totalProductionReportTable.addCell(new Cell().add(new Paragraph("POA IRRADIANCE"))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setBorder(Border.NO_BORDER)
						.setFontColor(textGrayColor)
						.setBold()
				);
				totalProductionReportTable.addCell(new Cell().add(new Paragraph(Optional.ofNullable(obj.getTotalActualGeneration()).map(noDecimalFormat::format).map(value -> value.concat(" kWh")).orElse("")))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setBorder(Border.NO_BORDER)
						.setFontSize(largeFontSize)
						.setBold()
				);
				totalProductionReportTable.addCell(new Cell().add(new Paragraph(Optional.ofNullable(obj.getTotalExpectedGeneration()).map(noDecimalFormat::format).map(value -> value.concat(" kWh")).orElse("")))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setBorder(Border.NO_BORDER)
						.setFontSize(largeFontSize)
						.setBold()
				);
				totalProductionReportTable.addCell(new Cell().add(new Paragraph(Optional.ofNullable(obj.getPoaIrradiance()).map(String::valueOf).map(value -> value.concat(" W/m²")).orElse("")))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setBorder(Border.NO_BORDER)
						.setFontSize(largeFontSize)
						.setFontColor(textYellowColor)
						.setBold()
				);

				document.add(totalProductionReportTable);

				List<ClientMonthlyDateEntity> productionReport = Optional.ofNullable(obj.getProductionReportList()).orElse(new ArrayList<>());

				//====== chart ============================================================
				JFreeChart productionReportChart = DocumentHelper.createJFreeChart("PERFORMANCE");
				XYPlot productionReportPlot = productionReportChart.getXYPlot();

				// data source
				TimeSeries actualSeries = new TimeSeries("Actual Generation (kWh)");
				TimeSeries expectedSeries = new TimeSeries("Expected Generation (kWh)");
				TimeSeries irradianceSeries = new TimeSeries("POA (W/m²)");

				for (ClientMonthlyDateEntity item : productionReport) {
					RegularTimePeriod period = new Day(dateFormat.parse(item.getDownload_time()));

					actualSeries.addOrUpdate(period, item.getChart_energy_kwh());
					expectedSeries.addOrUpdate(period, item.getExpected_energy());
					irradianceSeries.addOrUpdate(period, item.getNvm_irradiance());
				}

				TimeSeriesCollection barDataset = DocumentHelper.createJFreeChartBarDataset(0, productionReportPlot);
				barDataset.addSeries(actualSeries);
				productionReportPlot.getRendererForDataset(barDataset).setSeriesPaint(0, chartColumnSeriesBlueColor);
				barDataset.addSeries(expectedSeries);
				productionReportPlot.getRendererForDataset(barDataset).setSeriesPaint(1, chartColumnSeriesGrayColor);

				TimeSeriesCollection lineDataset = DocumentHelper.createJFreeChartLineDataset(2, productionReportPlot, null);
				lineDataset.addSeries(irradianceSeries);
				productionReportPlot.getRendererForDataset(lineDataset).setSeriesPaint(0, chartLineSeriesYellowColor);
				productionReportPlot.getRendererForDataset(lineDataset).setSeriesStroke(0, new BasicStroke(4f));


				// category axis
				DocumentHelper.createJFreeChartDomainAxis(productionReportPlot, new DateTickUnit(DateTickUnitType.DAY, 1, categoriesFormat), startDate, endDate);
				// left axis
				DocumentHelper.createJFreeChartNumberAxis("kWh", AxisLocation.BOTTOM_OR_LEFT, 0, 0, productionReportPlot);
				// right axis
				DocumentHelper.createJFreeChartNumberAxis("W/m²", AxisLocation.BOTTOM_OR_RIGHT, 1, 2, productionReportPlot);

				document.add(new Image(ImageDataFactory.create(productionReportChart.createBufferedImage(1800, 600), null)));

				Table productionReportTable = new Table(5).useAllAvailableWidth();
				productionReportTable.setFontSize(smallFontSize);
				productionReportTable.setMarginTop(50);
				productionReportTable.setBorderCollapse(BorderCollapsePropertyValue.SEPARATE);

				// table header
				productionReportTable.addCell(new Cell().add(new Paragraph("DATE"))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgLightGrayColor)
						.setBorder(Border.NO_BORDER)
						.setBorderTopLeftRadius(new BorderRadius(borderRarius))
						.setFontSize(mediumFontSize)
						.setBold()
				);
				productionReportTable.addCell(new Cell().add(new Paragraph("ACTUAL GENERATION (KWH)"))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgLightGrayColor)
						.setBorder(Border.NO_BORDER)
						.setFontSize(mediumFontSize)
						.setBold()
				);
				productionReportTable.addCell(new Cell().add(new Paragraph("EXPECTED GENERATION (KWH)"))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgLightGrayColor)
						.setBorder(Border.NO_BORDER)
						.setFontSize(mediumFontSize)
						.setBold()
				);
				productionReportTable.addCell(new Cell().add(new Paragraph("GENERATION INDEX (%)"))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgLightGrayColor)
						.setBorder(Border.NO_BORDER)
						.setFontSize(mediumFontSize)
						.setBold()
				);
				productionReportTable.addCell(new Cell().add(new Paragraph("POA (W/M²)"))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgLightGrayColor)
						.setBorder(Border.NO_BORDER)
						.setBorderTopRightRadius(new BorderRadius(borderRarius))
						.setFontSize(mediumFontSize)
						.setBold()
				);

				// table rows
				productionReport.stream().forEach(item -> {
					productionReportTable.addCell(new Cell().add(new Paragraph(item.getDownload_time()))
							.setTextAlignment(TextAlignment.CENTER)
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBorder(Border.NO_BORDER)
							.setBorderBottom(new SolidBorder(bgLightGrayColor, 2))
							.setBorderLeft(new SolidBorder(bgLightGrayColor, 2))
					);
					productionReportTable.addCell(new Cell().add(new Paragraph(Optional.ofNullable(item.getChart_energy_kwh()).map(noDecimalFormat::format).orElse("")))
							.setTextAlignment(TextAlignment.CENTER)
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBorder(Border.NO_BORDER)
							.setBorderBottom(new SolidBorder(bgLightGrayColor, 2))
					);
					productionReportTable.addCell(new Cell().add(new Paragraph(Optional.ofNullable(item.getExpected_energy()).map(noDecimalFormat::format).orElse("")))
							.setTextAlignment(TextAlignment.CENTER)
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBorder(Border.NO_BORDER)
							.setBorderBottom(new SolidBorder(bgLightGrayColor, 2))
					);
					productionReportTable.addCell(new Cell().add(new Paragraph(Optional.ofNullable(item.getChart_energy_kwh())
									.flatMap(energy -> Optional.ofNullable(item.getExpected_energy())
											.map(expected -> expected > 0 ? BigDecimal.valueOf(energy / expected).setScale(2, RoundingMode.HALF_UP).doubleValue() : null)
									)
									.map(noDecimalWithPercentageFormat::format)
									.orElse("")
							))
							.setTextAlignment(TextAlignment.CENTER)
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBorder(Border.NO_BORDER)
							.setBorderBottom(new SolidBorder(bgLightGrayColor, 2))
					);
					productionReportTable.addCell(new Cell().add(new Paragraph(Optional.ofNullable(item.getNvm_irradiance()).map(noDecimalFormat::format).orElse("")))
							.setTextAlignment(TextAlignment.CENTER)
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBorder(Border.NO_BORDER)
							.setBorderBottom(new SolidBorder(bgLightGrayColor, 2))
							.setBorderRight(new SolidBorder(bgLightGrayColor, 2))
					);
				});

				// total row
				productionReportTable.addCell(new Cell().add(new Paragraph("TOTAL"))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgLightGrayColor)
						.setBorder(Border.NO_BORDER)
						.setBorderBottomLeftRadius(new BorderRadius(borderRarius))
						.setBold()
				);
				productionReportTable.addCell(new Cell().add(new Paragraph(Optional.ofNullable(obj.getTotalActualGeneration()).map(noDecimalFormat::format).orElse("")))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgLightGrayColor)
						.setBorder(Border.NO_BORDER)
						.setBold()
				);
				productionReportTable.addCell(new Cell().add(new Paragraph(Optional.ofNullable(obj.getTotalExpectedGeneration()).map(noDecimalFormat::format).orElse("")))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgLightGrayColor)
						.setBorder(Border.NO_BORDER)
						.setBold()
				);
				productionReportTable.addCell(new Cell().add(new Paragraph("-"))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgLightGrayColor)
						.setBorder(Border.NO_BORDER)
						.setBold()
				);
				productionReportTable.addCell(new Cell().add(new Paragraph("-"))
						.setTextAlignment(TextAlignment.CENTER)
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBackgroundColor(bgLightGrayColor)
						.setBorder(Border.NO_BORDER)
						.setBorderBottomRightRadius(new BorderRadius(borderRarius))
						.setBold()
				);

				document.add(productionReportTable);
				document.add(new AreaBreak());

				// Inverters
				List<PerformanceDataChartItemEntity> inverters = Optional.ofNullable(obj.getInverterDataList()).orElse(new ArrayList<>());
				
				inverters.stream().forEach(inverter -> {
					try {
						// page title
						document.add(new Paragraph(inverter.getDevicename())
								.setMarginBottom(50)
								.setFontSize(largeFontSize)
								.setBold()
						);
						document.add(logoImage);
						
						Table inverterActualGenerationTable = new Table(4).useAllAvailableWidth();
						inverterActualGenerationTable.setFontSize(smallFontSize);
						inverterActualGenerationTable.setBorderCollapse(BorderCollapsePropertyValue.SEPARATE);
						
						// table header
						inverterActualGenerationTable.addCell(new Cell().add(new Paragraph("Date"))
								.setTextAlignment(TextAlignment.CENTER)
								.setVerticalAlignment(VerticalAlignment.MIDDLE)
								.setPaddings(5, 10, 5, 10)
								.setBackgroundColor(bgLightGrayColor)
								.setBorder(Border.NO_BORDER)
								.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 2))
								.setBorderTopLeftRadius(new BorderRadius(borderRarius))
								.setFontSize(mediumFontSize)
								.setBold()
						);
						inverterActualGenerationTable.addCell(new Cell().add(new Paragraph("Actual Generation (kWh)"))
								.setTextAlignment(TextAlignment.CENTER)
								.setVerticalAlignment(VerticalAlignment.MIDDLE)
								.setPaddings(5, 10, 5, 10)
								.setBackgroundColor(bgLightGrayColor)
								.setBorder(Border.NO_BORDER)
								.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 2))
								.setBorderTopRightRadius(new BorderRadius(borderRarius))
								.setFontSize(mediumFontSize)
								.setBold()
						);

						List<ClientMonthlyDateEntity> actualGeneration = Optional.ofNullable(inverter.getData_energy()).orElse(new ArrayList<>());
						
						// empty column: gap between table and chart
						inverterActualGenerationTable.addCell(new Cell(actualGeneration.size(), 1)
								.setBorder(Border.NO_BORDER)
						);
						
						Cell chartCell = new Cell(actualGeneration.size(), 1);
						inverterActualGenerationTable.addCell(chartCell
								.setHorizontalAlignment(HorizontalAlignment.CENTER)
								.setVerticalAlignment(VerticalAlignment.MIDDLE)
								.setBorder(Border.NO_BORDER)
						);
						
						// table rows
						for (int i = 0; i < actualGeneration.size(); i++) {
							ClientMonthlyDateEntity item = actualGeneration.get(i);
							
							inverterActualGenerationTable.addCell(new Cell().add(new Paragraph(item.getDownload_time()))
									.setTextAlignment(TextAlignment.CENTER)
									.setVerticalAlignment(VerticalAlignment.MIDDLE)
									.setPaddings(5, 10, 5, 10)
									.setBorder(new SolidBorder(bgLightGrayColor, 1))
									.setBorderBottomLeftRadius(new BorderRadius(i == actualGeneration.size() - 1 ? borderRarius : 0))
							);
							inverterActualGenerationTable.addCell(new Cell().add(new Paragraph(Optional.ofNullable(item.getChart_energy_kwh()).map(noDecimalFormat::format).orElse("")))
									.setTextAlignment(TextAlignment.CENTER)
									.setVerticalAlignment(VerticalAlignment.MIDDLE)
									.setPaddings(5, 10, 5, 10)
									.setBorder(new SolidBorder(bgLightGrayColor, 1))
									.setBorderBottomRightRadius(new BorderRadius(i == actualGeneration.size() - 1 ? borderRarius : 0))
							);
						};
						
						//====== chart ============================================================
						JFreeChart inverterChart = DocumentHelper.createJFreeChart("");
						XYPlot inverterPlot = inverterChart.getXYPlot();
						inverterChart.removeLegend();
						
						// data source
						TimeSeriesCollection actualInverterDataset = DocumentHelper.createJFreeChartBarDataset(0, inverterPlot);
						TimeSeries actualInverterSeries = new TimeSeries("");
						actualInverterDataset.addSeries(actualInverterSeries);
						inverterPlot.getRendererForDataset(actualInverterDataset).setSeriesPaint(0, chartColumnSeriesBlueColor);

						for (ClientMonthlyDateEntity item : actualGeneration) {
							RegularTimePeriod period = new Day(dateFormat.parse(item.getDownload_time()));
							
							actualInverterSeries.addOrUpdate(period, item.getChart_energy_kwh());
						}
						
						// category axis
						DocumentHelper.createJFreeChartDomainAxis(inverterPlot, new DateTickUnit(DateTickUnitType.DAY, 1, categoriesFormat), startDate, endDate);
						// left axis
						DocumentHelper.createJFreeChartNumberAxis("", AxisLocation.BOTTOM_OR_LEFT, 0, 0, inverterPlot);

						chartCell.add(new Image(ImageDataFactory.create(inverterChart.createBufferedImage(1800, 600), null))
								.setHorizontalAlignment(HorizontalAlignment.CENTER)
								.setMarginTop(400)
								.scaleToFit(550, 200)
						);
						
						document.add(inverterActualGenerationTable);
						document.add(new AreaBreak());
					} catch (Exception ex) {
						log.error("AnalyticalReportTracker.createPdfFile", ex);
					}
				});
				
				// Analytical Report Glossary
				AnalyticalReportTrackerGlobalConfigDTO globalConfigDetail = getGlobalConfigDetail();
				
				// page title
				document.add(new Paragraph("Analytical Report Glossary")
						.setFontSize(largeFontSize)
				);
				
				// Final Score % – Performance Grades
				document.add(new Paragraph("Final Score % – Performance Grades")
						.setMarginTop(50)
						.setFontSize(mediumFontSize)
						.setFontColor(textBlueColor)
				);
				
				final float[] finalScoreTableColumnWidths = {1, 1, 1, 4};
				Table finalScoreTable = new Table(UnitValue.createPercentArray(finalScoreTableColumnWidths)).useAllAvailableWidth();
				finalScoreTable.setFontSize(smallFontSize);
				
				// table header
				finalScoreTable.addCell(new Cell().add(new Paragraph("FINAL SCORE %"))
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBorder(Border.NO_BORDER)
						.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 2))
						.setFontSize(mediumFontSize)
						.setBold()
				);
				finalScoreTable.addCell(new Cell().add(new Paragraph("GRADE"))
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBorder(Border.NO_BORDER)
						.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 2))
						.setFontSize(mediumFontSize)
						.setBold()
				);
				finalScoreTable.addCell(new Cell().add(new Paragraph("LABEL"))
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBorder(Border.NO_BORDER)
						.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 2))
						.setFontSize(mediumFontSize)
						.setBold()
				);
				finalScoreTable.addCell(new Cell().add(new Paragraph("DESCRIPTION"))
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBorder(Border.NO_BORDER)
						.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 2))
						.setFontSize(mediumFontSize)
						.setBold()
				);
				
				// table rows
				List<AnalyticalReportTrackerGlobalConfigRuleEntity> performanceRules = Optional.ofNullable(globalConfigDetail.getPerformanceRules()).orElse(new ArrayList<>());
				for (int i = 0; i < performanceRules.size(); i++) {
					AnalyticalReportTrackerGlobalConfigRuleEntity rule = performanceRules.get(i);
					
					finalScoreTable.addCell(new Cell().add(new Paragraph(rule.getScore()))
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBackgroundColor(bgGrayColor, i % 2 == 0 ? 1 : 0)
							.setBorder(Border.NO_BORDER)
							.setBorderRight(new SolidBorder(borderGrayColor, 2))
							.setBold()
					);
					finalScoreTable.addCell(new Cell().add(new Paragraph(rule.getGrade()))
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBackgroundColor(bgGrayColor, i % 2 == 0 ? 1 : 0)
							.setBorder(Border.NO_BORDER)
							.setBorderRight(new SolidBorder(borderGrayColor, 2))
					);
					finalScoreTable.addCell(new Cell().add(new Paragraph(rule.getLabel()))
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBackgroundColor(bgGrayColor, i % 2 == 0 ? 1 : 0)
							.setBorder(Border.NO_BORDER)
							.setBorderRight(new SolidBorder(borderGrayColor, 2))
					);
					finalScoreTable.addCell(new Cell().add(new Paragraph(rule.getDescription()))
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBackgroundColor(bgGrayColor, i % 2 == 0 ? 1 : 0)
							.setBorder(Border.NO_BORDER)
					);
				}
				
				document.add(finalScoreTable);
				
				// Site Generation Performance (A/E)
				document.add(new Paragraph("Site Generation Performance (A/E)")
						.setMarginTop(20)
						.setFontSize(mediumFontSize)
						.setFontColor(textBlueColor)
				);
				
				final float[] performanceTableColumnWidths = {1, 1, 3};
				Table performanceTable = new Table(UnitValue.createPercentArray(performanceTableColumnWidths)).useAllAvailableWidth();
				performanceTable.setFontSize(smallFontSize);
				
				// table header
				performanceTable.addCell(new Cell().add(new Paragraph("A/E (%)"))
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBorder(Border.NO_BORDER)
						.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 2))
						.setFontSize(mediumFontSize)
						.setBold()
				);
				performanceTable.addCell(new Cell().add(new Paragraph("STATUS"))
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBorder(Border.NO_BORDER)
						.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 2))
						.setFontSize(mediumFontSize)
						.setBold()
				);
				performanceTable.addCell(new Cell().add(new Paragraph("DESCRIPTION"))
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBorder(Border.NO_BORDER)
						.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 2))
						.setFontSize(mediumFontSize)
						.setBold()
				);
				
				// table rows
				List<AnalyticalReportTrackerGlobalConfigPerformanceStatusMappingEntity> performanceStatusMappings = Optional.ofNullable(globalConfigDetail.getPerformanceStatusMappings()).orElse(new ArrayList<>());
				for (int i = 0; i < performanceStatusMappings.size(); i++) {
					AnalyticalReportTrackerGlobalConfigPerformanceStatusMappingEntity status = performanceStatusMappings.get(i);
					
					performanceTable.addCell(new Cell().add(new Paragraph(status.getOperator().concat(" ").concat(status.getThreshold())))
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBackgroundColor(bgGrayColor, i % 2 == 0 ? 1 : 0)
							.setBorder(Border.NO_BORDER)
							.setBorderRight(new SolidBorder(borderGrayColor, 2))
							.setBold()
					);
					performanceTable.addCell(new Cell().add(new Paragraph(status.getStatus_name()))
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBackgroundColor(bgGrayColor, i % 2 == 0 ? 1 : 0)
							.setBorder(Border.NO_BORDER)
							.setBorderRight(new SolidBorder(borderGrayColor, 2))
					);
					performanceTable.addCell(new Cell().add(new Paragraph(status.getDescription()))
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBackgroundColor(bgGrayColor, i % 2 == 0 ? 1 : 0)
							.setBorder(Border.NO_BORDER)
					);
				}
				
				document.add(performanceTable);
				
				// General Definitions
				document.add(new Paragraph("General Definitions")
						.setMarginTop(20)
						.setFontSize(mediumFontSize)
						.setFontColor(textBlueColor)
				);
				
				final float[] generalDefinitionsTableColumnWidths = {1, 2};
				Table generalDefinitionsTable = new Table(UnitValue.createPercentArray(generalDefinitionsTableColumnWidths)).useAllAvailableWidth();
				generalDefinitionsTable.setFontSize(smallFontSize);
				
				// table header
				generalDefinitionsTable.addCell(new Cell().add(new Paragraph("TERM"))
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBorder(Border.NO_BORDER)
						.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 2))
						.setFontSize(mediumFontSize)
						.setBold()
				);
				generalDefinitionsTable.addCell(new Cell().add(new Paragraph("DEFINITION"))
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBorder(Border.NO_BORDER)
						.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 2))
						.setFontSize(mediumFontSize)
						.setBold()
				);
				
				// table rows
				List<AnalyticalReportTrackerGlobalConfigDefinitionsGlossaryEntity> definitionsGlossary = Optional.ofNullable(globalConfigDetail.getDefinitionsGlossary()).orElse(new ArrayList<>());
				for (int i = 0; i < definitionsGlossary.size(); i++) {
					AnalyticalReportTrackerGlobalConfigDefinitionsGlossaryEntity definition = definitionsGlossary.get(i);
					
					generalDefinitionsTable.addCell(new Cell().add(new Paragraph(definition.getTerm()))
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBackgroundColor(bgGrayColor, i % 2 == 0 ? 1 : 0)
							.setBorder(Border.NO_BORDER)
							.setBorderRight(new SolidBorder(borderGrayColor, 2))
							.setBold()
					);
					generalDefinitionsTable.addCell(new Cell().add(new Paragraph(definition.getDescription()))
							.setVerticalAlignment(VerticalAlignment.MIDDLE)
							.setPaddings(5, 10, 5, 10)
							.setBackgroundColor(bgGrayColor, i % 2 == 0 ? 1 : 0)
							.setBorder(Border.NO_BORDER)
					);
				}
				
				document.add(generalDefinitionsTable);
				
				return file.getAbsolutePath();
			}
		} catch (Exception ex) {
			log.error("AnalyticalReportTracker.createPdfFile", ex);
			return null;
		}
	}
	
	private class ReportFooterHandler implements IEventHandler {
		@Override
		public void handleEvent(Event event) {
			DeviceRgb footerTextColor = new DeviceRgb(99, 105, 115);
			PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
			PdfPage page = docEvent.getPage();
			String pageNumber = String.valueOf(docEvent.getDocument().getPageNumber(page));
			
			PdfCanvas pdfCanvas = new PdfCanvas(page);
			
			Canvas canvas = new Canvas(pdfCanvas, page.getPageSize());
			canvas.showTextAligned(new Paragraph("CONFIDENTIAL - DO NOT SHARE AS PER NDA").setFontSize(smallFontSize).setFontColor(footerTextColor).setBold(), 40, 10, TextAlignment.LEFT);
			canvas.showTextAligned(new Paragraph(pageNumber).setFontSize(smallFontSize).setFontColor(footerTextColor).setBold(), page.getPageSize().getWidth() - 40, 10, TextAlignment.RIGHT);
			canvas.close();
		}
	}
}
