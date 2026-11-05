/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import com.nwm.api.entities.report.ReportDetail;
import com.nwm.api.entities.report.SiteByReport;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.util.Units;
import org.apache.poi.xddf.usermodel.XDDFColor;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.MarkerStyle;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChart;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData.Series;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DateTimeReportDataEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DevicesByTypeEntity;
import com.nwm.api.entities.MonthlyProductionTrendReportEntity;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.entities.WeeklyDateEntity;
import com.nwm.api.utils.Constants.ChartingFilter;
import com.nwm.api.utils.Constants.ChartingGranularity;
import com.nwm.api.utils.Constants.ReportIntervals;
import com.nwm.api.utils.Constants.ReportRange;
import com.nwm.api.utils.Constants.UploadingDataIntervals;
import com.nwm.api.utils.DocumentHelper;

@Service
public class BuiltInReportService extends DB {
	
	@Autowired
	ReportsService reportsService;
	@Autowired
	DeviceService deviceService;
	@Autowired
	CustomerViewService customerViewService;
	
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final DateTimeFormatter reportDateRangeDTF = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
	/**
	 * @description Get data list in multi threads
	 * @author Hung.Bui
	 * @since 2024-07-01
	 * @param id, ids, start_date, end_date
	 * @return data list
	 */
	public ReportDetail getReportDataList(ViewReportEntity req) throws Exception {
		switch (ReportRange.fromValue(req.getCadence_range())) {
			case LAST_MONTH:
			case MONTHLY:
			case CUSTOM:
				return this.getMonthlyTrendBuiltInReport(req);
			case LAST_WEEK:
			case WEEKLY:
				return this.getWeeklyBuiltInReport(req);
			case ANNUALLY:
				return this.getAnnuallyBuiltInReport(req);
			default:
				return null;
		}
	}
	
	public <K extends DateTimeReportDataEntity> void summarizeReport(ReportDetail reportDetail, Class<K> clazz) {
		try {
			List<SiteByReport> sites = reportDetail.getSites();
			if (sites.isEmpty()) return;

			List<K> summaryData = sites.stream()
					.filter(item -> !item.getDataReports().isEmpty())
					.findFirst()
					.map(site -> (List<K>) site.getDataReports())
					.map(list -> list.stream()
							.map(item -> {
								try {
									K target = clazz.getDeclaredConstructor().newInstance();
									target.setCategories_time(item.getCategories_time());
									return target;
								} catch (Exception e) {
									return null;
								}
							})
							.collect(Collectors.toList())
					)
					.orElse(new ArrayList<>());

			if (	reportDetail.getCadence_range() == ReportRange.ANNUALLY.getValue() ||
					reportDetail.getCadence_range() == ReportRange.WEEKLY.getValue() ||
					reportDetail.getCadence_range() == ReportRange.LAST_WEEK.getValue()) {
				for (int i = 0; i < summaryData.size(); i++) {
					WeeklyDateEntity sum = (WeeklyDateEntity) summaryData.get(i);
					List<Double> actualGeneration = new ArrayList<>();
					List<Double> expectedGeneration = new ArrayList<>();
					List<Double> modeledGeneration = new ArrayList<>();
					List<Double> poa = new ArrayList<>();

					for (int j = 0; j < sites.size(); j++) {
						List<WeeklyDateEntity> dataReports = (List<WeeklyDateEntity>) sites.get(j).getDataReports();
						if (dataReports.isEmpty()) continue;
						WeeklyDateEntity item = dataReports.get(i);

						if (item != null && sum.getCategories_time().equals(item.getCategories_time())) {
							actualGeneration.add(item.getActualGeneration());
							expectedGeneration.add(item.getExpectedGeneration());
							modeledGeneration.add(item.getModeledGeneration());
							poa.add(item.getPoa());
						}
					}
					
					sum.setActualGeneration(actualGeneration.stream().allMatch(Objects::isNull) ? null : actualGeneration.stream().filter(Objects::nonNull).mapToDouble(Double::doubleValue).sum());
					sum.setExpectedGeneration(expectedGeneration.stream().allMatch(Objects::isNull) ? null : expectedGeneration.stream().filter(Objects::nonNull).mapToDouble(Double::doubleValue).sum());
					sum.setModeledGeneration(modeledGeneration.stream().allMatch(Objects::isNull) ? null : modeledGeneration.stream().filter(Objects::nonNull).mapToDouble(Double::doubleValue).sum());
					sum.setPoa(poa.stream().allMatch(Objects::isNull) ? null : BigDecimal.valueOf(poa.stream().filter(Objects::nonNull).mapToDouble(Double::doubleValue).average().getAsDouble()).doubleValue());
					sum.setExpectedGenerationIndex(Optional.ofNullable(sum.getActualGeneration()).orElse(0.0) > 0 && Optional.ofNullable(sum.getExpectedGeneration()).orElse(0.0) > 0 ? BigDecimal.valueOf(sum.getActualGeneration() / sum.getExpectedGeneration() * 100).setScale(1, RoundingMode.HALF_UP).doubleValue() : null);
					sum.setModeledGenerationIndex(Optional.ofNullable(sum.getActualGeneration()).orElse(0.0) > 0 && Optional.ofNullable(sum.getModeledGeneration()).orElse(0.0) > 0 ? BigDecimal.valueOf(sum.getActualGeneration() / sum.getModeledGeneration() * 100).setScale(1, RoundingMode.HALF_UP).doubleValue() : null);
				}
			} else if ((reportDetail.getCadence_range() == ReportRange.MONTHLY.getValue() || reportDetail.getCadence_range() == ReportRange.LAST_MONTH.getValue()) &&
					reportDetail.getData_intervals() == ReportIntervals.MONTHLY.getValue()) {
				for (int i = 0; i < summaryData.size(); i++) {
					MonthlyProductionTrendReportEntity sum = (MonthlyProductionTrendReportEntity) summaryData.get(i);

					for (int j = 0; j < sites.size(); j++) {
						List<MonthlyProductionTrendReportEntity> dataReports = (List<MonthlyProductionTrendReportEntity>) sites.get(j).getDataReports();
						if (dataReports == null || dataReports.isEmpty()) continue;
						MonthlyProductionTrendReportEntity item = dataReports.get(i);

						if (item != null && sum.getCategories_time().equals(item.getCategories_time())) {
							if (item.getMonthlyProduction() != null) sum.setMonthlyProduction((sum.getMonthlyProduction() != null ? sum.getMonthlyProduction() : 0) + item.getMonthlyProduction());
						}
					}
				}
			} else {
				return;
			}
			
			SiteByReport summaryReport = new SiteByReport();
			summaryReport.setDataReports(summaryData);
			summaryReport.setName("Summary");
			summaryReport.setId(0);

			sites.add(0, summaryReport);
		} catch (Exception e) {
			log.error(e);
		}
		
		return;
	}
	
	/**
	 * @description get annually production trend report 
	 * @author long.pham
	 * @since 2022-08-23
	 * @param id_site, date_from, data_to
	 */
	
	public ReportDetail getAnnuallyBuiltInReport(ViewReportEntity req) throws Exception {
		return getWeeklyBuiltInReport(req);
	}
	
	
	/**
	 * @description get monthly  report 
	 * @author long.pham
	 * @since 2022-08-23
	 * @param id_site, date_from, data_to
	 */
	
	public ReportDetail getMonthlyTrendBuiltInReport(ViewReportEntity req) throws Exception {
		try {
			ReportDetail reportDetail = reportsService.getReportDetail(req);
			if (Objects.isNull(reportDetail)) throw new Exception("Report not found");

			LocalDateTime startDate = LocalDateTime.parse(req.getStart_date(), dateTimeFormatter);
			LocalDateTime endDate = LocalDateTime.parse(req.getEnd_date(), dateTimeFormatter);
			ReportIntervals interval = ReportIntervals.fromValue(reportDetail.getData_intervals());
			ReportRange range = ReportRange.fromValue(reportDetail.getCadence_range());
			ChartingGranularity granularity = ReportIntervals.toChartingGranularity(interval);
			ChartingFilter filter = ReportRange.toChartingFilter(range);

			reportsService.filterSiteOnRequest(reportDetail, req.getId_site());

			List<CompletableFuture<Void>> futures = Optional.ofNullable(reportDetail.getSites()).orElse(new ArrayList<>()).stream()
					.map(site -> CompletableFuture.runAsync(() -> {
						try {
							DevicesByTypeEntity devices = deviceService.getDevicesBySite(site.getId(), req.getDomain());

							List<MonthlyProductionTrendReportEntity> data = reportsService.getActualBySiteDevices(!devices.getMeter().isEmpty() ? devices.getMeter() : devices.getInverter(), startDate, endDate, granularity, filter)
									.stream()
									.map(item -> {
										MonthlyProductionTrendReportEntity entity = new MonthlyProductionTrendReportEntity();
										entity.setCategories_time(reportsService.dateTimeFormatConverter(granularity, item.getCategories_time(), DateTimeFormatter.ofPattern(interval == ReportIntervals._15_MINUTES ? "MM/dd/yyyy HH:mm:00" : "MMM-yy")));
										entity.setMonthlyProduction(Optional.ofNullable(item.getEnergy()).map(value -> BigDecimal.valueOf(value).setScale(0, RoundingMode.HALF_UP).doubleValue()).orElse(null));

										return entity;
									})
									.collect(Collectors.toList());

							site.setDataReports(data);
						} catch (Exception ex) {
							log.error(ex);
						}
					}))
					.collect(Collectors.toList());

			futures.forEach(CompletableFuture::join);

			summarizeReport(reportDetail, MonthlyProductionTrendReportEntity.class);

			return reportDetail;
		} catch (Exception ex) {
			log.error("BuiltInReport.getMonthlyTrendBuiltInReport", ex);
			throw ex;
		}
	}
	
	/**
	 * @description get weekly report 
	 * @author Hung.Bui
	 * @since 2023-07-31
	 * @param id_site, date_from, date_to
	 */
	
	public ReportDetail getWeeklyBuiltInReport(ViewReportEntity req) throws Exception {
		try {
			ReportDetail reportDetail = reportsService.getReportDetail(req);
			if (Objects.isNull(reportDetail)) throw new Exception("Report not found");

			LocalDateTime startDate = LocalDateTime.parse(req.getStart_date(), dateTimeFormatter);
			LocalDateTime endDate = LocalDateTime.parse(req.getEnd_date(), dateTimeFormatter);
			ReportIntervals interval = ReportIntervals.fromValue(reportDetail.getData_intervals());
			ReportRange range = ReportRange.fromValue(reportDetail.getCadence_range());
			ChartingGranularity granularity = ReportIntervals.toChartingGranularity(interval);
			ChartingFilter filter = ReportRange.toChartingFilter(range);

			reportsService.filterSiteOnRequest(reportDetail, req.getId_site());

			List<CompletableFuture<Void>> futures = Optional.ofNullable(reportDetail.getSites()).orElse(new ArrayList<>()).stream()
					.map(site -> CompletableFuture.runAsync(() -> {
						try {
							UploadingDataIntervals siteUploadingInterval = UploadingDataIntervals.fromValue(site.getData_send_time());
							DevicesByTypeEntity devices = deviceService.getDevicesBySite(site.getId(), req.getDomain());
							List<DeviceEntity> irradianceDevices = devices.getIrradiance();
							DateTimeFormatter outputDateTimeFormatter = DateTimeFormatter.ofPattern(interval == ReportIntervals.DAILY ? "MM/dd/yyyy" : "MMM-yy");

							List<WeeklyDateEntity> actualGeneration = reportsService.getActualBySiteDevices(!devices.getMeter().isEmpty() ? devices.getMeter() : devices.getInverter(), startDate, endDate, granularity, filter)
									.stream()
									.map(item -> {
										WeeklyDateEntity entity = new WeeklyDateEntity();
										entity.setCategories_time(reportsService.dateTimeFormatConverter(granularity, item.getCategories_time(), outputDateTimeFormatter));
										entity.setActualGeneration(item.getEnergy());

										return entity;
									})
									.collect(Collectors.toList());

							List<WeeklyDateEntity> mergedData = actualGeneration;

							if (irradianceDevices.size() == 1) {
								List<WeeklyDateEntity> irradiance = customerViewService.getIrradianceByDevice(startDate, endDate, irradianceDevices.get(0), granularity, filter, false, siteUploadingInterval)
										.stream()
										.map(item -> {
											WeeklyDateEntity entity = new WeeklyDateEntity();
											entity.setCategories_time(reportsService.dateTimeFormatConverter(granularity, item.getTime_full(), outputDateTimeFormatter));
											entity.setPoa(item.getNvm_irradiance());
											entity.setExpectedGeneration(item.getExpected_energy());

											return entity;
										})
										.collect(Collectors.toList());

								mergedData = Stream.concat(actualGeneration.stream(), irradiance.stream())
										.collect(Collectors.toMap(
												WeeklyDateEntity::getCategories_time,
												item -> item,
												(s1, s2) -> {
													s1.setPoa(s2.getPoa());
													s1.setExpectedGeneration(s2.getExpectedGeneration());

													return s1;
												},
												LinkedHashMap::new
										))
										.values()
										.stream()
										.map(item -> {
											return item;
										})
										.collect(Collectors.toList());
							} else if (irradianceDevices.size() > 1) {
								List<WeeklyDateEntity> poa = reportsService.getIrradianceBySiteDevices(irradianceDevices, startDate, endDate, granularity, filter)
										.stream()
										.map(item -> {
											WeeklyDateEntity entity = new WeeklyDateEntity();
											entity.setCategories_time(reportsService.dateTimeFormatConverter(granularity, item.getCategories_time(), outputDateTimeFormatter));
											entity.setPoa(item.getIrradiance());

											return entity;
										})
										.collect(Collectors.toList());

								List<WeeklyDateEntity> expectedGeneration = customerViewService.getExpectedBySelectedPOA(startDate, endDate, site.getId(), granularity, filter, irradianceDevices)
										.stream()
										.map(item -> {
											WeeklyDateEntity entity = new WeeklyDateEntity();
											entity.setCategories_time(reportsService.dateTimeFormatConverter(granularity, item.getTime_full(), outputDateTimeFormatter));
											entity.setExpectedGeneration(item.getExpected_energy());

											return entity;
										})
										.collect(Collectors.toList());

								mergedData = Stream.concat(
												Stream.concat(actualGeneration.stream(), poa.stream())
														.collect(Collectors.toMap(
																WeeklyDateEntity::getCategories_time,
																item -> item,
																(s1, s2) -> {
																	s1.setPoa(s2.getPoa());

																	return s1;
																},
																LinkedHashMap::new
														))
														.values()
														.stream(),
												expectedGeneration.stream()
										)
										.collect(Collectors.toMap(
												WeeklyDateEntity::getCategories_time,
												item -> item,
												(s1, s2) -> {
													s1.setExpectedGeneration(s2.getExpectedGeneration());

													return s1;
												},
												LinkedHashMap::new
										))
										.values()
										.stream()
										.map(item -> {
											return item;
										})
										.collect(Collectors.toList());
							}

							Map<String, Double> modeledGeneration = reportsService.getEnergyExpectation(startDate, site.getId());

							List<WeeklyDateEntity> data = mergedData.stream()
									.map(item -> {
										Double actualGenerationValue = item.getActualGeneration();
										Double expectedGenerationValue = item.getExpectedGeneration();
										Double poaValue = item.getPoa();
										Double modeledGenerationValue = modeledGeneration.get(interval == ReportIntervals.DAILY ? LocalDate.parse(item.getCategories_time(), outputDateTimeFormatter).getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toLowerCase() : YearMonth.parse(item.getCategories_time(), outputDateTimeFormatter).getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toLowerCase());

										Optional.ofNullable(actualGenerationValue).ifPresent(value -> item.setActualGeneration(BigDecimal.valueOf(value).setScale(0, RoundingMode.HALF_UP).doubleValue()));
										Optional.ofNullable(expectedGenerationValue).ifPresent(value -> item.setExpectedGeneration(BigDecimal.valueOf(value).setScale(0, RoundingMode.HALF_UP).doubleValue()));
										Optional.ofNullable(poaValue).ifPresent(value -> item.setPoa(BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue()));
										if (Objects.nonNull(actualGenerationValue) && Objects.nonNull(expectedGenerationValue) && expectedGenerationValue > 0) item.setExpectedGenerationIndex(BigDecimal.valueOf(actualGenerationValue / expectedGenerationValue * 100).setScale(1, RoundingMode.HALF_UP).doubleValue());
										if (Objects.nonNull(actualGenerationValue) && Objects.nonNull(modeledGenerationValue)) {
											modeledGenerationValue = interval == ReportIntervals.DAILY ? modeledGenerationValue / LocalDate.parse(item.getCategories_time(), outputDateTimeFormatter).lengthOfMonth() : modeledGenerationValue;
											item.setModeledGeneration(BigDecimal.valueOf(modeledGenerationValue).setScale(0, RoundingMode.HALF_UP).doubleValue());
											if (modeledGenerationValue > 0) item.setModeledGenerationIndex(BigDecimal.valueOf(actualGenerationValue / modeledGenerationValue * 100).setScale(1, RoundingMode.HALF_UP).doubleValue());
										}

										return item;
									})
									.collect(Collectors.toList());

							if (!data.isEmpty()) {
								WeeklyDateEntity totalRow = new WeeklyDateEntity();
								totalRow.setCategories_time("Total");
								Supplier<DoubleStream> actualGenerationStream = () -> data.stream().map(WeeklyDateEntity::getActualGeneration).filter(Objects::nonNull).mapToDouble(Double::doubleValue);
								actualGenerationStream.get().findAny().ifPresent(value -> totalRow.setActualGeneration(actualGenerationStream.get().sum()));
								Supplier<DoubleStream> expectedGenerationStream = () -> data.stream().map(WeeklyDateEntity::getExpectedGeneration).filter(Objects::nonNull).mapToDouble(Double::doubleValue);
								expectedGenerationStream.get().findAny().ifPresent(value -> totalRow.setExpectedGeneration(expectedGenerationStream.get().sum()));
								Supplier<DoubleStream> modeledGenerationStream = () -> data.stream().map(WeeklyDateEntity::getModeledGeneration).filter(Objects::nonNull).mapToDouble(Double::doubleValue);
								modeledGenerationStream.get().findAny().ifPresent(value -> totalRow.setModeledGeneration(modeledGenerationStream.get().sum()));
								if (Objects.nonNull(totalRow.getActualGeneration()) && Objects.nonNull(totalRow.getExpectedGeneration()) && totalRow.getExpectedGeneration() > 0) totalRow.setExpectedGenerationIndex(BigDecimal.valueOf(totalRow.getActualGeneration() / totalRow.getExpectedGeneration() * 100).setScale(1, RoundingMode.HALF_UP).doubleValue());
								if (Objects.nonNull(totalRow.getActualGeneration()) && Objects.nonNull(totalRow.getModeledGeneration()) && totalRow.getModeledGeneration() > 0) totalRow.setModeledGenerationIndex(BigDecimal.valueOf(totalRow.getActualGeneration() / totalRow.getModeledGeneration() * 100).setScale(1, RoundingMode.HALF_UP).doubleValue());

								data.add(totalRow);
							}

							site.setDataReports(data);
						} catch (Exception ex) {
							log.error(ex);
						}
					}))
					.collect(Collectors.toList());

			futures.forEach(CompletableFuture::join);

			summarizeReport(reportDetail, WeeklyDateEntity.class);

			return reportDetail;
		} catch (Exception ex) {
			log.error("BuiltInReport.getWeeklyBuiltInReport", ex);
			throw ex;
		}
	}
	
	/**
	 * @description send mail report
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 */
	public boolean sentMailReport(ViewReportEntity req) {
		try {
			ReportDetail reportDetail = getReportDataList(req);
			if (Objects.isNull(reportDetail)) return false;

			String filePath = null;
			String title = "";
			
			switch (ReportRange.fromValue(reportDetail.getCadence_range())) {
				case LAST_MONTH:
				case MONTHLY:
				case CUSTOM:
					title = "Monthly Production Trend Report ";
					if (reportDetail.getData_intervals() == ReportIntervals._15_MINUTES.getValue()) title = title.concat("(15-minute Interval)");
					else if (reportDetail.getData_intervals() == ReportIntervals.MONTHLY.getValue()) title = title.concat("(Monthly Interval)");
					filePath = createMonthlyTrendReportSheetFile(req, reportDetail);
					break;
					
				case LAST_WEEK:
				case WEEKLY:
				case ANNUALLY:
					title = ReportRange.fromValue(reportDetail.getCadence_range()) == ReportRange.ANNUALLY ? "Annual Production Trend Report (Monthly Interval)" : "Weekly Production Trend Report (Daily Interval)";
					filePath = createWeeklyTrendReportSheetFile(req, reportDetail);
					break;
					
				default:
					break;
			}
			
			if (filePath == null) return false;
			
			reportsService.sentReportByMail(filePath, reportDetail.getSubscribers(), title, 18, "Customer", title);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * @description download report
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 */
	public String downloadReport(ViewReportEntity req) {
		try {
			ReportDetail reportDetail = getReportDataList(req);
			if (Objects.isNull(reportDetail)) return null;

			switch (ReportRange.fromValue(reportDetail.getCadence_range())) {
				case LAST_MONTH:
				case MONTHLY:
				case CUSTOM:
					return createMonthlyTrendReportSheetFile(req, reportDetail);
					
				case LAST_WEEK:
				case WEEKLY:
				case ANNUALLY:
					return createWeeklyTrendReportSheetFile(req, reportDetail);
					
				default:
					return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @description create weekly production trend report sheet file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 * @return file path
	 */
	public String createWeeklyTrendReportSheetFile(ViewReportEntity req, ReportDetail reportDetail) {
		try (XSSFWorkbook document = new XSSFWorkbook()) {
			// insert logo image
			int pictureIdx = DocumentHelper.readLogoImageFile(document);
			ClientAnchor logoAnchor = new XSSFClientAnchor(10 * Units.EMU_PER_PIXEL, -10 * Units.EMU_PER_PIXEL, 0, 10 * Units.EMU_PER_PIXEL, 7, 1, 8, 4);

			reportDetail.getSites()
				.forEach(site -> {
					List<WeeklyDateEntity> dataExports = (List<WeeklyDateEntity>) site.getDataReports();
					int numOfPoints = dataExports != null ? dataExports.size() - 1 : 0; // exclude total row

					XSSFSheet sheet = document.createSheet(WorkbookUtil.createSafeSheetName(site.getName()));
					DocumentHelper.insertLogo(sheet, logoAnchor, pictureIdx);

					writeHeaderWeeklyReport(sheet, reportDetail, site, req.getStart_date(), req.getEnd_date());

					if (numOfPoints > 0) {
						// chart
						ClientAnchor chartAnchor = new XSSFClientAnchor(5 * Units.EMU_PER_PIXEL, 0, -5 * Units.EMU_PER_PIXEL, 0, 0, numOfPoints + 8, 8, numOfPoints + 8 + 24);
						XDDFChart chart = DocumentHelper.insertChart(sheet, chartAnchor, "Performance");
						chart.getCTChart().getTitle().getTx().getRich().getPArray(0).getRArray(0).getRPr().setSz(1400);

						// data sources
						XDDFDataSource<String> categoriesData = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(6, numOfPoints + 5, 0, 0));
						XDDFNumericalDataSource<Double> valuesData1 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(6, numOfPoints + 5, 1, 1));
						XDDFNumericalDataSource<Double> valuesData2 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(6, numOfPoints + 5, 2, 2));
						XDDFNumericalDataSource<Double> valuesData3 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(6, numOfPoints + 5, 3, 3));
						XDDFNumericalDataSource<Double> valuesData4 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(6, numOfPoints + 5, 5, 5));
						XDDFNumericalDataSource<Double> valuesData5 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(6, numOfPoints + 5, 6, 6));

						// category axis
						XDDFCategoryAxis bottomAxis = DocumentHelper.createCategoryAxis(chart);

						// left value axis
						XDDFValueAxis leftAxis = DocumentHelper.createLeftValueAxis(chart, "GENERATION (KWH)");

						XDDFChartData data = DocumentHelper.createChartData(chart, ChartTypes.BAR, bottomAxis, leftAxis);
						Supplier<Stream<WeeklyDateEntity>> filteredDataExports = () -> dataExports.stream().filter(item -> !item.getCategories_time().equals("Total"));
						DocumentHelper.addSeries(filteredDataExports.get().allMatch(item -> item.getActualGeneration() == null), data, categoriesData, valuesData1, "Actual Generation (kWh)", XDDFColor.from(new byte[] {(byte) 70, (byte) 130, (byte) 180}), null);
						DocumentHelper.addSeries(filteredDataExports.get().allMatch(item -> item.getExpectedGeneration() == null), data, categoriesData, valuesData2, "Expected Generation (kWh)", XDDFColor.from(new byte[] {(byte) 166, (byte) 166, (byte) 166}), null);
						DocumentHelper.addSeries(filteredDataExports.get().allMatch(item -> item.getModeledGeneration() == null), data, categoriesData, valuesData3, "Modeled Generation (kWh)", XDDFColor.from(new byte[] {(byte) 176, (byte) 196, (byte) 222}), null);

						chart.plot(data);

						// right value axis
						XDDFValueAxis rightAxis = DocumentHelper.createRightValueAxis(chart, bottomAxis, "PERFORMANCE INDEX (%)");

						data = DocumentHelper.createChartData(chart, ChartTypes.LINE, bottomAxis, rightAxis);
						Series lineSeries = DocumentHelper.addSeries(filteredDataExports.get().allMatch(item -> item.getExpectedGenerationIndex() == null), data, categoriesData, valuesData4, "Expected Generation Index (%)", XDDFColor.from(new byte[] {(byte) 112, (byte) 173, (byte) 71}), null);
						DocumentHelper.solidFillLineMarker(chart, lineSeries, 0, MarkerStyle.CIRCLE, XDDFColor.from(new byte[] {(byte) 112, (byte) 173, (byte) 71}));
						lineSeries = DocumentHelper.addSeries(filteredDataExports.get().allMatch(item -> item.getModeledGenerationIndex() == null), data, categoriesData, valuesData5, "Modeled Generation Index (%)", XDDFColor.from(new byte[] {(byte) 255, (byte) 192, (byte) 0}), null);
						DocumentHelper.solidFillLineMarker(chart, lineSeries, 1, MarkerStyle.CIRCLE, XDDFColor.from(new byte[] {(byte) 255, (byte) 192, (byte) 0}));

						chart.plot(data);
					}
				});

			return reportsService.writeToSheetFile(document, reportDetail.getName());
		} catch (Exception e) {
			return null;
		}
	}
	
	private static void writeHeaderWeeklyReport(Sheet sheet, ReportDetail reportDetail, SiteByReport site, String startDate, String endDate) {
		try {
			setPrintAreaToFitOnePage(sheet);
			
			// set column width
			sheet.setDefaultColumnWidth(16);
			sheet.setColumnWidth(0, 15 * 256);
			sheet.setColumnWidth(1, 30 * 256);
			sheet.setColumnWidth(2, 30 * 256);
			sheet.setColumnWidth(3, 30 * 256);
			sheet.setColumnWidth(4, 30 * 256);
			sheet.setColumnWidth(5, 30 * 256);
			sheet.setColumnWidth(6, 15 * 256);
			sheet.setColumnWidth(7, 15 * 256);
			sheet.setDefaultRowHeight((short) 315);
			sheet.setDisplayGridlines(false);
			
			CellStyle reportTitleCellStyle = DocumentHelper.createStyleForReportTitle(sheet, (short) 18, true);
			CellStyle reportSubTitleBoldCellStyle = DocumentHelper.createStyleForReportTitle(sheet, (short) 14, true);
			CellStyle reportSubTitleCelStyle = DocumentHelper.createStyleForReportTitle(sheet, (short) 12, false);
			CellStyle tableHeaderCellStyle = DocumentHelper.createStyleForTableHeader(sheet);
			CellStyle tableRowCellStyle = DocumentHelper.createStyleForTableRow(sheet, false);
			CellStyle tableRowBoldCellStyle = DocumentHelper.createStyleForTableRow(sheet, true);
			CellStyle tableRowNoDecimalCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, null);
			CellStyle tableRowNoDecimalBoldCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, true, null);
			CellStyle tableRowOneDecimalPlaceCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, DocumentHelper.oneDecimalPlaceDataFormat);
			CellStyle tableRowOneDecimalPlaceBoldCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, true, DocumentHelper.oneDecimalPlaceDataFormat);
			CellStyle tableRowTwoDecimalPlaceCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, DocumentHelper.twoDecimalPlaceDataFormat);

			Row row = sheet.createRow(1);
			row.setHeight((short) 500);
			Cell cell = row.createCell(1);
			cell.setCellStyle(reportTitleCellStyle);
			String title = reportDetail.getCadence_range() == ReportRange.ANNUALLY.getValue() ? "ANNUAL PRODUCTION TREND REPORT (MONTHLY INTERVAL)" : "WEEKLY PRODUCTION TREND REPORT (DAILY INTERVAL)";
			cell.setCellValue(title);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 6));
						
			row = sheet.createRow(2);
			row.setHeight((short) 400);
			cell = row.createCell(1);
			cell.setCellStyle(reportSubTitleBoldCellStyle);
			cell.setCellValue(site.getName().toUpperCase());
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 6));

			row = sheet.createRow(3);
			row.setHeight((short) 400);
			cell = row.createCell(1);
			cell.setCellStyle(reportSubTitleCelStyle);
			
			cell.setCellValue(reportDateRangeDTF.format(dateTimeFormatter.parse(startDate)) + " - " +  reportDateRangeDTF.format(dateTimeFormatter.parse(endDate)));
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 6));
			
			row = sheet.createRow(5);
			cell = row.createCell(0);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue(reportDetail.getCadence_range() == ReportRange.ANNUALLY.getValue() ? "" : "Date");

			cell = row.createCell(1);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Actual Generation (kWh)");
			
			cell = row.createCell(2);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Expected Generation (kWh)");
			
			cell = row.createCell(3);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Modeled Generation (kWh)");
			
			cell = row.createCell(4);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("POA (W/m2)");
			
			cell = row.createCell(5);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Expected Generation Index (%)");
			
			cell = row.createCell(6);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Modeled Generation Index (%)");
			cell = row.createCell(7);
			cell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 6, 7));
			
			List<WeeklyDateEntity> dataExports = (List<WeeklyDateEntity>) site.getDataReports();
			
			if(!dataExports.isEmpty()) {
				for (int i = 0; i < dataExports.size(); i++) {
					WeeklyDateEntity item = dataExports.get(i);
					boolean isTotalRow = item.getCategories_time().equals("Total");
					
					Row row5 = sheet.createRow(6 + i);
					Cell cell5 = row5.createCell(0);
					cell5.setCellStyle(isTotalRow ? tableRowBoldCellStyle : tableRowCellStyle);
					cell5.setCellValue(item.getCategories_time());
					
					Cell cell51 = row5.createCell(1);
					cell51.setCellStyle(isTotalRow ? tableRowNoDecimalBoldCellStyle : tableRowNoDecimalCellStyle);
					if (item.getActualGeneration() != null) cell51.setCellValue(item.getActualGeneration());
					
					Cell cell52 = row5.createCell(2);
					cell52.setCellStyle(isTotalRow ? tableRowNoDecimalBoldCellStyle : tableRowNoDecimalCellStyle);
					if (item.getExpectedGeneration() != null) cell52.setCellValue(item.getExpectedGeneration());
					
					Cell cell53 = row5.createCell(3);
					cell53.setCellStyle(isTotalRow ? tableRowNoDecimalBoldCellStyle : tableRowNoDecimalCellStyle);
					if (item.getModeledGeneration() != null) cell53.setCellValue(item.getModeledGeneration());
					
					Cell cell54 = row5.createCell(4);
					cell54.setCellStyle(isTotalRow ? tableRowOneDecimalPlaceBoldCellStyle : tableRowTwoDecimalPlaceCellStyle);
					if (item.getPoa() != null) cell54.setCellValue(item.getPoa());
					
					Cell cell55 = row5.createCell(5);
					cell55.setCellStyle(isTotalRow ? tableRowOneDecimalPlaceBoldCellStyle : tableRowOneDecimalPlaceCellStyle);
					if (item.getExpectedGenerationIndex() != null) cell55.setCellValue(item.getExpectedGenerationIndex());
					
					Cell cell56 = row5.createCell(6);
					cell56.setCellStyle(isTotalRow ? tableRowOneDecimalPlaceBoldCellStyle : tableRowOneDecimalPlaceCellStyle);
					if (item.getModeledGenerationIndex() != null) cell56.setCellValue(item.getModeledGenerationIndex());
					Cell cell57 = row5.createCell(7);
					cell57.setCellStyle(isTotalRow ? tableRowOneDecimalPlaceBoldCellStyle : tableRowOneDecimalPlaceCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(6 + i, 6 + i, 6, 7));
				}
			}
			
			// add Note
			Font fontRowBg = sheet.getWorkbook().createFont();
			fontRowBg.setFontName("Times New Roman");
			fontRowBg.setBold(true);
			fontRowBg.setFontHeightInPoints((short) 11); // font size
			fontRowBg.setColor(IndexedColors.WHITE.getIndex()); // text color
			// Create CellStyle
			CellStyle cellStyleBg = sheet.getWorkbook().createCellStyle();
			cellStyleBg.setFont(fontRowBg);
			cellStyleBg.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleBg.setAlignment(HorizontalAlignment.LEFT);
			cellStyleBg.setFillBackgroundColor(IndexedColors.GREY_40_PERCENT.index);
			cellStyleBg.setFillPattern(FillPatternType.BIG_SPOTS);
			cellStyleBg.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
			
			
			sheet.addMergedRegion(new CellRangeAddress(33 + dataExports.size(), 33 + dataExports.size(), 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(34 + dataExports.size(), 34 + dataExports.size(), 0, 5));
			sheet.addMergedRegion(new CellRangeAddress(35 + dataExports.size(), 35 + dataExports.size(), 0, 5));
			sheet.addMergedRegion(new CellRangeAddress(36 + dataExports.size(), 36 + dataExports.size(), 0, 5));
			
			Row row7 = sheet.createRow(33 + dataExports.size());
			Cell cell7 = row7.createCell(0);
			cell7.setCellStyle(cellStyleBg);
			cell7.setCellValue("Actual, Expected and Modeled Generation");
			
			
			Font fontRowNote = sheet.getWorkbook().createFont();
			fontRowNote.setFontName("Times New Roman");
			fontRowNote.setFontHeightInPoints((short) 11); // font size
			fontRowNote.setColor(IndexedColors.BLACK.getIndex()); // text color
			// Create CellStyle
			CellStyle cellStyleNote = sheet.getWorkbook().createCellStyle();
			cellStyleNote.setFont(fontRowNote);
			cellStyleNote.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleNote.setAlignment(HorizontalAlignment.LEFT);
			
			
			Row row8 = sheet.createRow(34 + dataExports.size());
			Cell cell8 = row8.createCell(0);
			cell8.setCellStyle(cellStyleNote);
			cell8.setCellValue("The Actual Generation is the energy reported by the production meters.");
			
			
			Row row9 = sheet.createRow(35 + dataExports.size());
			Cell cell9 = row9.createCell(0);
			cell9.setCellStyle(cellStyleNote);
			cell9.setCellValue("The Expected Generation is calculated based on measured irradiance and module temperature.");
			
			
			Row row10 = sheet.createRow(36 + dataExports.size());
			Cell cell10 = row10.createCell(0);
			cell10.setCellStyle(cellStyleNote);
			cell10.setCellValue("The Modeled Generation is predicted by PVWatts Calculator.");
			
		} catch (Exception e) {
		}
	}
	
	private static void setPrintAreaToFitOnePage(Sheet sheet) {
		sheet.setFitToPage(true);
		sheet.getPrintSetup().setFitWidth((short) 1);
		sheet.getPrintSetup().setFitHeight((short) 0);
	}
	
	/**
	 * @description create monthly production trend report sheet file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 * @return file path
	 */
	public String createMonthlyTrendReportSheetFile(ViewReportEntity req, ReportDetail reportDetail) {
		try (XSSFWorkbook document = new XSSFWorkbook()) {
			// insert logo image
			int pictureIdx = DocumentHelper.readLogoImageFile(document);
			
			if(reportDetail.getData_intervals() == ReportIntervals._15_MINUTES.getValue()) {
				ClientAnchor logoAnchor = new XSSFClientAnchor(-140 * Units.EMU_PER_PIXEL, -20 * Units.EMU_PER_PIXEL, -20 * Units.EMU_PER_PIXEL, 10 * Units.EMU_PER_PIXEL, 2, 1, 2, 4);

				reportDetail.getSites()
						.forEach(site -> {
							XSSFSheet sheet = document.createSheet(WorkbookUtil.createSafeSheetName(site.getName()));
							DocumentHelper.insertLogo(sheet, logoAnchor, pictureIdx);

							writeHeaderMonthTrendReport(sheet, site, req.getStart_date(), req.getEnd_date());
						});
			} else {
				XSSFSheet sheet = document.createSheet(WorkbookUtil.createSafeSheetName("Monthly Interval"));
				ClientAnchor logoAnchor = new XSSFClientAnchor(50 * Units.EMU_PER_PIXEL, -20 * Units.EMU_PER_PIXEL, 0, 20 * Units.EMU_PER_PIXEL, 4, 1, 5, 4);
				DocumentHelper.insertLogo(sheet, logoAnchor, pictureIdx);
				
				writeHeaderMonthTrendReportMonthlyInterval(sheet, reportDetail, req.getStart_date(), req.getEnd_date());
			}
					
			return reportsService.writeToSheetFile(document, reportDetail.getName());
		} catch (Exception e) {
			return null;
		}
	}
	
	private static void writeHeaderMonthTrendReportMonthlyInterval(Sheet sheet, ReportDetail reportDetail, String startDate, String endDate) {
		try {
			sheet.setDefaultColumnWidth(16);
			sheet.setColumnWidth(0, 25 * 256);
			sheet.setColumnWidth(1, 30 * 256);
			sheet.setColumnWidth(2, 30 * 256);
			sheet.setColumnWidth(3, 30 * 256);
			sheet.setColumnWidth(4, 25 * 256);
			sheet.setDefaultRowHeight((short) 500);
			sheet.setDisplayGridlines(false);
			
			CellStyle reportTitleCellStyle = DocumentHelper.createStyleForReportTitle(sheet, (short) 14, true);
			CellStyle tableHeaderCellStyle = DocumentHelper.createStyleForTableHeader(sheet);
			CellStyle tableRowCellStyle = DocumentHelper.createStyleForTableRow(sheet, false);
			CellStyle tableRowLeftAlignCellStyle = DocumentHelper.createStyleForTableRow(sheet, false);
			tableRowLeftAlignCellStyle.setAlignment(HorizontalAlignment.LEFT);
			CellStyle tableRowNoDecimalCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, null);
			
			Row row = sheet.createRow(0);
			row.setHeight((short) 600);
			Cell cell = row.createCell(1);
			cell.setCellStyle(reportTitleCellStyle);
			cell.setCellValue("MONTHLY PRODUCTION TREND REPORT (MONTHLY INTERVAL)");
			sheet.addMergedRegion(new CellRangeAddress(0, 4, 1, 3));
			
			row = sheet.createRow(5);
			cell = row.createCell(0);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Site Name");
			cell = row.createCell(1);
			cell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 1));
			
			cell = row.createCell(2);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Start Date");
			
			cell = row.createCell(3);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("End Date");
			
			cell = row.createCell(4);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Monthly Production (kWh)");

			List<SiteByReport> sites = reportDetail.getSites();
			if (sites.isEmpty()) return;

			for (int i = 0; i < sites.size(); i++) {
				SiteByReport site = sites.get(i);

				Row row6f = sheet.createRow(6+i);
				Cell cell60f = row6f.createCell(0);
				cell60f.setCellStyle(tableRowLeftAlignCellStyle);
				cell60f.setCellValue(site.getName());
				Cell cell61f = row6f.createCell(1);
				cell61f.setCellStyle(tableRowLeftAlignCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(6+i, 6+i, 0, 1));

				Cell cell62f = row6f.createCell(2);
				cell62f.setCellStyle(tableRowCellStyle);
				cell62f.setCellValue(reportDateRangeDTF.format(dateTimeFormatter.parse(startDate)));

				Cell cell63f = row6f.createCell(3);
				cell63f.setCellStyle(tableRowCellStyle);
				cell63f.setCellValue(reportDateRangeDTF.format(dateTimeFormatter.parse(endDate)));

				Cell cell64f = row6f.createCell(4);
				cell64f.setCellStyle(tableRowNoDecimalCellStyle);
				List<MonthlyProductionTrendReportEntity> dataReport = (List<MonthlyProductionTrendReportEntity>) site.getDataReports();
				dataReport.stream()
						.filter(item -> Objects.nonNull(item.getMonthlyProduction()))
						.findFirst()
						.map(MonthlyProductionTrendReportEntity::getMonthlyProduction)
						.ifPresent(cell64f::setCellValue);
			}
		} catch (Exception e) {
		}
	}
	
	private static void writeHeaderMonthTrendReport(Sheet sheet, SiteByReport site, String startDate, String endDate) {
		try {
			setPrintAreaToFitOnePage(sheet);
			
			// set column width
			sheet.setDefaultColumnWidth(16);
			sheet.setColumnWidth(0, 80 * 256);
			sheet.setColumnWidth(1, 30 * 256);
			sheet.setDefaultRowHeight((short) 400);
			sheet.setDisplayGridlines(false);
			
			CellStyle reportTitleCellStyle = DocumentHelper.createStyleForReportTitle(sheet, (short) 14, true);
			CellStyle reportSubTitleBoldCellStyle = DocumentHelper.createStyleForReportTitle(sheet, (short) 12, true);
			CellStyle reportSubTitleCelStyle = DocumentHelper.createStyleForReportTitle(sheet, (short) 12, false);
			CellStyle tableHeaderCellStyle = DocumentHelper.createStyleForTableHeader(sheet);
			CellStyle tableRowCellStyle = DocumentHelper.createStyleForTableRow(sheet, false);
			CellStyle tableRowNoDecimalCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, null);
			
			Row row = sheet.createRow(1);
			row.setHeight((short) 450);
			Cell cell = row.createCell(0);
			cell.setCellStyle(reportTitleCellStyle);
			cell.setCellValue("MONTHLY PRODUCTION TREND REPORT (15-MINUTE INTERVAL)");
						
			row = sheet.createRow(2);
			row.setHeight((short) 450);
			cell = row.createCell(0);
			cell.setCellStyle(reportSubTitleBoldCellStyle);
			cell.setCellValue(site.getName().toUpperCase());
			
			row = sheet.createRow(3);
			row.setHeight((short) 450);
			cell = row.createCell(0);
			cell.setCellStyle(reportSubTitleCelStyle);
			
			cell.setCellValue(reportDateRangeDTF.format(dateTimeFormatter.parse(startDate)) + " - " +  reportDateRangeDTF.format(dateTimeFormatter.parse(endDate)));
			
			row = sheet.createRow(5);
			row.setHeight((short) 400);
			cell = row.createCell(0);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Timestamp");

			cell = row.createCell(1);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Monthly Production (kWh)");
			
			List<MonthlyProductionTrendReportEntity> dataExports = (List<MonthlyProductionTrendReportEntity>) site.getDataReports();
			
			if (!dataExports.isEmpty()) {
				for (int i = 0; i < dataExports.size(); i++) {
					MonthlyProductionTrendReportEntity item = dataExports.get(i);
					
					Row row5 = sheet.createRow(6 + i);
					row5.setHeight((short) 315);
					Cell cell5 = row5.createCell(0);
					cell5.setCellStyle(tableRowCellStyle);
					cell5.setCellValue(item.getCategories_time());
					
					Cell cell51 = row5.createCell(1);
					cell51.setCellStyle(tableRowNoDecimalCellStyle);
					if (item.getMonthlyProduction() != null) cell51.setCellValue(item.getMonthlyProduction());
				}
			}
		} catch (Exception e) {
		}
	}
}
