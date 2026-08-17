/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Locale;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.config.ReportTaskScheduler;
import com.nwm.api.entities.AnalyticalReportTrackerDTO;
import com.nwm.api.entities.AnalyticalReportTrackerEntity;
import com.nwm.api.entities.AnalyticalReportTrackerGlobalConfigActionFlagEntity;
import com.nwm.api.entities.AnalyticalReportTrackerGlobalConfigDTO;
import com.nwm.api.entities.AnalyticalReportTrackerGlobalConfigCurrentStatusEntity;
import com.nwm.api.entities.AnalyticalReportTrackerGlobalConfigPathForwardUpdateEntity;
import com.nwm.api.entities.AnalyticalReportTrackerGlobalConfigRuleEntity;
import com.nwm.api.entities.AnalyticalReportTrackerLogs;
import com.nwm.api.entities.AuditLog;
import com.nwm.api.entities.ClientMonthlyDateEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DevicesByTypeEntity;
import com.nwm.api.entities.PerformanceDataChartItemEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.DocumentHelper;
import com.nwm.api.utils.Constants.ChartingFilter;
import com.nwm.api.utils.Constants.ChartingGranularity;
import com.nwm.api.utils.Constants.UploadingDataIntervals;

@Service
public class AnalyticalReportTrackerService extends DB {
	private static final int MAX_PAUSE_REASON_LENGTH = 100;
	private static final int MAX_NOTES_LENGTH = 500;
	
	public enum Status {
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

			AnalyticalReportTrackerEntity data = Optional.ofNullable(getSiteGenerationSummary(new AnalyticalReportTrackerDTO(reportTracker))).orElse(new AnalyticalReportTrackerEntity());
			
			String filePath = createPdfFile(data);
			if (filePath == null) return false;
			
			reportsService.sentReportByMail(filePath, reportTracker.getRecipient_to(), "analytical_report_tracker", 30);
			
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

			data.setActionFlags(actionFlags);
			data.setCurrentStatuses(currentStatuses);
			data.setPathForwardUpdates(pathForwardUpdates);
			data.setPerformanceRules(performanceRules);
			if (!actionFlags.isEmpty()) data.setModified_by(actionFlags.get(0).getModified_by());
			else if (!currentStatuses.isEmpty()) data.setModified_by(currentStatuses.get(0).getModified_by());
			else if (!pathForwardUpdates.isEmpty()) data.setModified_by(pathForwardUpdates.get(0).getModified_by());
			else if (!performanceRules.isEmpty()) data.setModified_by(performanceRules.get(0).getModified_by());
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

		SqlSession session = this.beginTransaction();
		try {
			if (obj.getActionFlags() != null) {
				if (obj.getDeletedActionFlagIds() != null && obj.getDeletedActionFlagIds().size() > 0) session.delete("AnalyticalReportTracker.deleteGlobalConfigActionFlags", obj.getDeletedActionFlagIds());
				if (obj.getActionFlags().size() > 0) session.insert("AnalyticalReportTracker.insertGlobalConfigActionFlags", obj);
			}
			if (obj.getCurrentStatuses() != null) {
				if (obj.getDeletedCurrentStatusIds() != null && obj.getDeletedCurrentStatusIds().size() > 0) session.delete("AnalyticalReportTracker.deleteGlobalConfigCurrentStatuses", obj.getDeletedCurrentStatusIds());
				if (obj.getCurrentStatuses().size() > 0) session.insert("AnalyticalReportTracker.insertGlobalConfigCurrentStatuses", obj);
			}
			if (obj.getPathForwardUpdates() != null) {
				if (obj.getDeletedPathForwardUpdateIds() != null && obj.getDeletedPathForwardUpdateIds().size() > 0) session.delete("AnalyticalReportTracker.deleteGlobalConfigPathForwardUpdates", obj.getDeletedPathForwardUpdateIds());
				if (obj.getPathForwardUpdates().size() > 0) session.insert("AnalyticalReportTracker.insertGlobalConfigPathForwardUpdates", obj);
			}
			if (obj.getPerformanceRules() != null) {
				if (obj.getDeletedPerformanceRuleIds() != null && obj.getDeletedPerformanceRuleIds().size() > 0) session.delete("AnalyticalReportTracker.deleteGlobalConfigRules", obj.getDeletedPerformanceRuleIds());
				if (obj.getPerformanceRules().size() > 0) session.insert("AnalyticalReportTracker.insertGlobalConfigRules", obj);
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
	
	public List<Map<String, Object>> getListAlertInverterBySiteId(int id_site, LocalDateTime end_date) {

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
	public AnalyticalReportTrackerEntity getSiteGenerationSummary(AnalyticalReportTrackerDTO obj) {
		try {
			
			AnalyticalReportTrackerEntity dataObj = new AnalyticalReportTrackerEntity(obj);
			Optional<SiteEntity> siteOptional = siteService.getSiteById(obj.getId_site());
			SiteEntity site = siteOptional.get();

	        if (site != null) {
	            dataObj.setData_send_time(site.getData_send_time());
	            dataObj.setTimezone_value(site.getTime_zone_value());
	            dataObj.setSunrise(site.getSunrise());
	            dataObj.setSunset(site.getSunset());
	        }
				
			LocalDateTime startDate = getReportDate("first_day_last_month", dataObj.getTimezone_value());	
			LocalDateTime endDate = getReportDate("yesterday_end", dataObj.getTimezone_value());		
			
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
			List<PerformanceDataChartItemEntity> inverterDataList = new ArrayList<>();

			if (!inverterDevices.isEmpty()) {
				Map<Integer, List<ClientMonthlyDateEntity>> dataByDevices = customerViewService.getEnergyByDevice(startDate, endDate, devices.getInverter(), granularity, filter, false);
				
				if (!dataByDevices.isEmpty()) {
					dataByDevices.forEach((deviceId, data) -> {
						String deviceName = inverterDevices.stream().filter(device -> device.getId() == deviceId).findFirst().map(DeviceEntity::getDevicename).orElse("");
						
						List<ClientMonthlyDateEntity> dataByDevice = data.stream().map(item -> {
							ClientMonthlyDateEntity entityItem = new ClientMonthlyDateEntity();
							entityItem.setTime_full(item.getTime_full());
							entityItem.setCategories_time(item.getCategories_time());
							entityItem.setDownload_time(LocalDate.parse(item.getTime_full(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
							entityItem.setChart_energy_kwh(Objects.nonNull(item.getChart_energy_kwh()) ? BigDecimal.valueOf(item.getChart_energy_kwh()).setScale(0, RoundingMode.HALF_UP).doubleValue() : null);
							
							return entityItem;
						}).collect(Collectors.toList());
						
						PerformanceDataChartItemEntity inverterData = new PerformanceDataChartItemEntity(dataByDevice, deviceId, "Inverter", "kWh", deviceName);
						inverterDataList.add(inverterData);
					});
				}
			}
			dataObj.setInverterDataList(inverterDataList);
			
			//Alerts - Portfolio Tracker
			List<Map<String, Object>> inverterAlerts = getListAlertInverterBySiteId(dataObj.getId_site(), endDate);
			
			Set<Integer> noProductionDeviceIds = new HashSet<>();
			Set<Integer> noCommDeviceIds = new HashSet<>();
			Map<Integer, String> noProductionStartDateMap = new HashMap<>();
			Map<Integer, String> noCommStartDateMap = new HashMap<>();
			Map<Integer, String> lowProductionStartDateMap = new HashMap<>();

			for (Map<String, Object> alert : inverterAlerts) {
			    Object deviceIdObj = alert.get("id_device");
			    Object errorCodeObj = alert.get("error_code");

			    if (deviceIdObj == null || errorCodeObj == null) {
			        continue;
			    }

			    Integer deviceId = Integer.valueOf(String.valueOf(deviceIdObj));
			    String errorCode = String.valueOf(errorCodeObj);
			    String startDateAlert = alert.get("start_date") != null ? String.valueOf(alert.get("start_date")) : null;

			    if ("1000".equals(errorCode)) {
			        noProductionDeviceIds.add(deviceId);
			        noProductionStartDateMap.put(deviceId, startDateAlert);
			    }

			    if ("1001".equals(errorCode)) {
			        noCommDeviceIds.add(deviceId);
			        noCommStartDateMap.put(deviceId, startDateAlert);
			    }
			}

			Set<Integer> excludedDeviceIds = new HashSet<>();

			excludedDeviceIds.addAll(noProductionDeviceIds);
			excludedDeviceIds.addAll(noCommDeviceIds);

			double maxDcRating = inverterDevices.stream().map(DeviceEntity::getRating_ac_power).filter(Objects::nonNull).mapToDouble(Double::doubleValue).max().orElse(0.0);

			Map<Integer, DeviceEntity> inverterDeviceMap = inverterDevices.stream().collect(Collectors.toMap(DeviceEntity::getId,device -> device,(first, second) -> first));
			Map<Integer, Double> normalizedProductionMap =new HashMap<>();
			double maxNormalizedProduction = 0.0;

			if (maxDcRating > 0) {
			    for (PerformanceDataChartItemEntity inverterData : inverterDataList) {
			        Integer deviceId = inverterData.getId_device();
			        if (deviceId == null) {
			            continue;
			        }
			        if (excludedDeviceIds.contains(deviceId)) {
			            continue;
			        }

			        DeviceEntity device = inverterDeviceMap.get(deviceId);
			        if (device == null) {
			            continue;
			        }

			        Double ratingAcPower = device.getRating_ac_power();
			        if (ratingAcPower == null || ratingAcPower <= 0) {
			            continue;
			        }
			        
			        String lowProductionStartDate = null;
			        List<ClientMonthlyDateEntity> dataEnergy = inverterData.getData_energy();
			        if (dataEnergy == null || dataEnergy.isEmpty()) {
			            continue;
			        }
			        
			        ClientMonthlyDateEntity lastItem = dataEnergy.get(dataEnergy.size() - 1);
			        Double production = lastItem.getChart_energy_kwh();
			        if (production == null) {
			            continue;
			        }

			        double normalizedProduction = production * maxDcRating / ratingAcPower;
			        normalizedProductionMap.put(deviceId, normalizedProduction);
			        maxNormalizedProduction = Math.max(maxNormalizedProduction, normalizedProduction);
			        lowProductionStartDateMap.put(deviceId, lastItem.getDownload_time());
			    }
			}

			List<Map<String, Object>> portfolioTrackerList = new ArrayList<>();
			int noProductionCount = 0;
			int noCommCount = 0;
			int lowProductionCount = 0;
			int normalCount = 0;

			for (PerformanceDataChartItemEntity inverterData : inverterDataList) {
			    Integer deviceId = inverterData.getId_device();
			    if (deviceId == null) {
			        continue;
			    }

			    Map<String, Object> item = new HashMap<>();
			    item.put("id_device", deviceId);
			    item.put("devicename", inverterData.getDevicename());

			    if (noCommDeviceIds.contains(deviceId)) {
			        item.put("status", "No Comm");
			        item.put("status_key", "no-comm");
			        item.put("issue_started", noCommStartDateMap.get(deviceId));
			        item.put("low_production_threshold", null);
			        noCommCount++;
			        portfolioTrackerList.add(item);
			        continue;
			    }

			    if (noProductionDeviceIds.contains(deviceId)) {
			        item.put("status", "No Production");
			        item.put("status_key", "no-production");
			        item.put("issue_started", noProductionStartDateMap.get(deviceId));
			        item.put("low_production_threshold", null);
			        noProductionCount++;
			        portfolioTrackerList.add(item);
			        continue;
			    }

			    Double normalizedProduction = normalizedProductionMap.get(deviceId);
			    if (normalizedProduction == null || maxNormalizedProduction <= 0) {
			        item.put("status", "No Data");
			        item.put("status_key", "no-data");
			        item.put("low_production_threshold", null);
			        portfolioTrackerList.add(item);
			        continue;
			    }

			    double lowProductionThreshold = -(1 - (normalizedProduction / maxNormalizedProduction)) * 100;
			    lowProductionThreshold = BigDecimal.valueOf(lowProductionThreshold).setScale(1, RoundingMode.HALF_UP).doubleValue();
			    if (lowProductionThreshold <= -10.0) {
			        item.put("status", "Low Production");
			        item.put("status_key", "low-production");	        
			        item.put("low_production_threshold", lowProductionThreshold);
			        item.put("issue_started", lowProductionStartDateMap.get(deviceId));
			        lowProductionCount++;
			    } else {
			        item.put("status", "Normal");
			        item.put("status_key", "normal");
			        item.put("low_production_threshold", null);
			        item.put( "issue_started",null);
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
	public String createPdfFile(AnalyticalReportTrackerEntity obj) {
		try {
			if (Objects.isNull(obj)) return null;
			File file = reportsService.writeToPdfFile("Tracker Summary Report");
			
			try (
				PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file));
				Document document = new Document(pdfDocument, PageSize.A3.rotate());
			) {
				AnalyticalReportTrackerGlobalConfigDTO globalConfigDetail = getGlobalConfigDetail();
				Image logoImage = DocumentHelper.readLogoImageFile();
				DeviceRgb textBlueColor = new DeviceRgb(74, 123, 167);
				DeviceRgb borderGrayColor = new DeviceRgb(220, 221, 224);
				DeviceRgb bgGrayColor = new DeviceRgb(236, 237, 238);
				
//				document.add(new AreaBreak());
				
				// Analytical Report Glossary
				document.add(new Paragraph("Analytical Report Glossary").setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN)).setFontSize(24));
				
				// Final Score % – Performance Grades
				document.add(new Paragraph("Final Score % – Performance Grades").setMarginTop(20).setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN)).setFontSize(16).setFontColor(textBlueColor));
				
				final float[] finalScoreTableColumnWidths = {1, 1, 1, 4};
				Table finalScoreTable = new Table(UnitValue.createPercentArray(finalScoreTableColumnWidths)).useAllAvailableWidth();
				finalScoreTable.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN));
				finalScoreTable.setFontSize(13);
				
				// header
				finalScoreTable.addCell(new Cell().add(new Paragraph("FINAL SCORE %"))
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBorder(Border.NO_BORDER)
						.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 2))
						.setFontSize(16)
						.setBold()
				);
				finalScoreTable.addCell(new Cell().add(new Paragraph("GRADE"))
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBorder(Border.NO_BORDER)
						.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 2))
						.setFontSize(16)
						.setBold()
				);
				finalScoreTable.addCell(new Cell().add(new Paragraph("LABEL"))
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBorder(Border.NO_BORDER)
						.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 2))
						.setFontSize(16)
						.setBold()
				);
				finalScoreTable.addCell(new Cell().add(new Paragraph("DESCRIPTION"))
						.setVerticalAlignment(VerticalAlignment.MIDDLE)
						.setPaddings(5, 10, 5, 10)
						.setBorder(Border.NO_BORDER)
						.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 2))
						.setFontSize(16)
						.setBold()
				);
				
				List<AnalyticalReportTrackerGlobalConfigRuleEntity> performanceRules = Optional.ofNullable(globalConfigDetail.getPerformanceRules()).orElse(new ArrayList<>());
				for (int i = 0; i < performanceRules.size(); i++) {
					AnalyticalReportTrackerGlobalConfigRuleEntity rule = performanceRules.get(i);
					
					finalScoreTable.addCell(new Cell().add(new Paragraph(rule.getOperator().concat(" ").concat(rule.getThreshold())))
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
				document.add(new Paragraph("Site Generation Performance (A/E)").setMarginTop(20).setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN)).setFontSize(16).setFontColor(textBlueColor));
				
				Table performanceTable = new Table(3).useAllAvailableWidth();
				performanceTable.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN));
				performanceTable.setFontSize(13);
				document.add(performanceTable);
				
				return file.getAbsolutePath();
			}
		} catch (Exception ex) {
			log.error("AnalyticalReportTracker.createPdfFile", ex);
			return null;
		}
	}
}
