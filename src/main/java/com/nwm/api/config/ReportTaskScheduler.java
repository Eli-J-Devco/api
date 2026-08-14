package com.nwm.api.config;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.nwm.api.batchjob.BatchJob;
import com.nwm.api.entities.AnalyticalReportTrackerEntity;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.services.AnalyticalReportTrackerService;
import com.nwm.api.services.BatchJobService;

@Component
public class ReportTaskScheduler {
	private final String dateTimeFormatString = "yyyy-MM-dd HH:mm:ss";
	private final SimpleDateFormat sdf;
	
	// tracking scheduled tasks and cancel if having any update
	private final Map<Integer, List<ScheduledFuture<?>>> scheduledReportTaskById = new HashMap<Integer, List<ScheduledFuture<?>>>();
	private final Map<Integer, List<ScheduledFuture<?>>> scheduledAnalyticalReportTrackerTaskById = new HashMap<Integer, List<ScheduledFuture<?>>>();
	
	private int reportId = 0;
	private int reportTrackerId = 0;
	
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;
    @Autowired
    private BatchJobService service;
    @Autowired
    private BatchJob batchJob;
    @Autowired
    private AnalyticalReportTrackerService analyticalReportTrackerService;
    
    public ReportTaskScheduler() {
    	sdf = new SimpleDateFormat(dateTimeFormatString);
    	sdf.setTimeZone(TimeZone.getTimeZone(ZoneOffset.UTC));
	}

	private List<String> timeScheduleToCronExpConverter(int periodicity, String timeSchedule, String daysWeek, String offset_timezone) {
		try {
			List<String> cronsList = new ArrayList<String>();
			if (timeSchedule.isEmpty()) return new ArrayList<String>();
			ZonedDateTime zonedDateTime = ZonedDateTime.parse(timeSchedule, DateTimeFormatter.ofPattern(dateTimeFormatString).withZone(ZoneId.of(offset_timezone)));
			ZonedDateTime utcDateTime = zonedDateTime.withZoneSameInstant(ZoneOffset.UTC);
			
			switch (periodicity) {
				case 1: { // daily
					String cron = String.format("0 %d %d * * *", utcDateTime.getMinute(), utcDateTime.getHour());
					cronsList.add(cron);
					break;
				}
				case 2: { // weekly
					if (daysWeek.isEmpty()) return new ArrayList<String>();
					List<String> days = new ArrayList<>();
					for (int i = 0; i < daysWeek.length(); i++) {
						if (Character.compare((char) daysWeek.charAt(i), '0') == 0) continue;
						days.add(String.valueOf(i + 1));
					}
					String cron = String.format("0 %d %d * * %s", utcDateTime.getMinute(), utcDateTime.getHour(), days.stream().collect(Collectors.joining(",")));
					cronsList.add(cron);
					break;
				}
				case 3: { // monthly
					// last day of month
					if (utcDateTime.getDayOfMonth() == utcDateTime.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth()) {
						for (int i = 1; i <= 12; i++) {
							String cron = String.format("0 %d %d %d %d *", utcDateTime.getMinute(), utcDateTime.getHour(), utcDateTime.withMonth(i).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth(), i);
							cronsList.add(cron);
						}
					} else {
						// day out of range in February
						if (utcDateTime.getDayOfMonth() > 28) {
							String cron = String.format("0 %d %d %d 1,3-12 *", utcDateTime.getMinute(), utcDateTime.getHour(), utcDateTime.getDayOfMonth());
							cronsList.add(cron);
							
							cron = String.format("0 %d %d 28 2 *", utcDateTime.getMinute(), utcDateTime.getHour());
							cronsList.add(cron);
						} else {
							String cron = String.format("0 %d %d %d * *", utcDateTime.getMinute(), utcDateTime.getHour(), utcDateTime.getDayOfMonth());
							cronsList.add(cron);
						}
					}
					break;
				}
				case 4: { // quarterly
					// last day of month
					if (utcDateTime.getDayOfMonth() == utcDateTime.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth()) {
						for (int i = 0; i < 4; i++) {
							int month = (utcDateTime.getMonthValue() + 3 * i) > 12 ? utcDateTime.getMonthValue() + 3 * i - 12 : utcDateTime.getMonthValue() + 3 * i;
							String cron = String.format("0 %d %d %d %d *", utcDateTime.getMinute(), utcDateTime.getHour(), utcDateTime.withMonth(month).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth(), month);
							cronsList.add(cron);
						}
					} else {
						for (int i = 0; i < 4; i++) {
							int month = (utcDateTime.getMonthValue() + 3 * i) > 12 ? utcDateTime.getMonthValue() + 3 * i - 12 : utcDateTime.getMonthValue() + 3 * i;
							String cron = String.format("0 %d %d %d %d *", utcDateTime.getMinute(), utcDateTime.getHour(), utcDateTime.getDayOfMonth() > 28 && month == 2 ? 28 : utcDateTime.getDayOfMonth(), month);
							cronsList.add(cron);
						}
					}
					break;
				}
				case 5: { // annually
					String cron = String.format("0 %d %d %d %d *", utcDateTime.getMinute(), utcDateTime.getHour(), utcDateTime.getDayOfMonth(), utcDateTime.getMonthValue());
					cronsList.add(cron);
					break;
				}
				default:
					break;
			}
			return cronsList;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	private String getDayInWeekString(String dateTimeString, String timeZoneString) {
    	StringBuilder sb = new StringBuilder("0000000");
    	
    	try {
    		LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern(dateTimeFormatString));
			int index = localDateTime.getDayOfWeek().getValue() - 1;
			sb.setCharAt(index, '1');
    		
    		return sb.toString();
    	} catch (Exception e) {
    		return sb.toString();
		}
    }
    
    private List<ScheduledFuture<?>> schedulingTask(Runnable task, List<String> cronExps) {
    	try {
    		return cronExps.stream()
    				.map(cronExp -> taskScheduler.schedule(task, new CronTrigger(cronExp, TimeZone.getTimeZone(ZoneOffset.UTC))))
    				.filter(Objects::nonNull)
    				.collect(Collectors.toList());
    	} catch (Exception e) {
			return new ArrayList<>();
		}
    }
    
    /*
     * cancel scheduled task before create new one
     * application start-up won't run this, only affected when user change schedule
     */
    private void cancelScheduledTask(Map<Integer, List<ScheduledFuture<?>>> scheduledTasks, int taskId) {
    	scheduledTasks.computeIfPresent(taskId, (key, value) -> {
			value.forEach(item -> item.cancel(false));
			return null;
		});
	}
    
    /*
     * get next run time for updating to know if task is already ran or not
     */
    private void runTaskIfNotRunYet(List<String> cronExps, int taskId, Function<Map<String, Object>, Boolean> checkAndUpdateNextRunTime, Runnable taskToRun) {
    	cronExps.stream()
				.map(cronExp -> {
					CronSequenceGenerator generator = new CronSequenceGenerator(cronExp, TimeZone.getTimeZone(ZoneOffset.UTC));
					return generator.next(new Date());
				})
				.sorted()
				.findFirst()
				.ifPresent(nextRunTime -> {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", taskId);
					map.put("time", sdf.format(nextRunTime));
					
					boolean notRunYet = checkAndUpdateNextRunTime.apply(map);
					if (!notRunYet) return;
					
					taskToRun.run();
				});
    }

    @PostConstruct
    public void scheduleWithCronTrigger() {
		try {
			List<ViewReportEntity> reports = service.getListReports(reportId);
			
			cancelScheduledTask(scheduledReportTaskById, reportId);
			
			for (ViewReportEntity report : reports) {
				if (report.getSchedule_enable() == 0) continue;
				List<String> cronExps = timeScheduleToCronExpConverter(report.getPeriodicity(), report.getTime_schedule(), report.getDays_week(), report.getOffset_timezone());
				ScheduledReportRunnable task = new ScheduledReportRunnable(report);
				scheduledReportTaskById.put(report.getId(), schedulingTask(task, cronExps));
			}
		} catch (Exception e) {
		}
    }
    
    public void changeReportSchedule(int id) {
		this.reportId = id;
		this.scheduleWithCronTrigger();
	}

	public String getNextAnalyticalReportTrackerRunTime(AnalyticalReportTrackerEntity reportTracker) {
		try {
			List<String> cronExps = timeScheduleToCronExpConverter(reportTracker.getCadence(), reportTracker.getStart_date(), getDayInWeekString(reportTracker.getStart_date(), reportTracker.getTimezone()), reportTracker.getTimezone());
			Date upcomingRunTime = cronExps.stream()
					.map(cronExp -> new CronSequenceGenerator(cronExp, TimeZone.getTimeZone(ZoneOffset.UTC)).next(new Date()))
					.sorted()
					.findFirst()
					.orElse(null);
			if (upcomingRunTime == null) return null;

			return cronExps.stream()
					.map(cronExp -> new CronSequenceGenerator(cronExp, TimeZone.getTimeZone(ZoneOffset.UTC)).next(upcomingRunTime))
					.sorted()
					.findFirst()
					.map(sdf::format)
					.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}
    
    private class ScheduledReportRunnable implements Runnable {
    	ViewReportEntity prevReport;
    	
		@Override
		public void run() {
			try {
				ViewReportEntity currentReport = service.getReportDetail(prevReport);
				if (
					currentReport == null ||
					(currentReport.getPeriodicity() == 2 && !currentReport.getDays_week().equals(prevReport.getDays_week())) ||
					!currentReport.getTime_schedule().equals(prevReport.getTime_schedule()) ||
					currentReport.getPeriodicity() != prevReport.getPeriodicity()
				) {
					changeReportSchedule(prevReport.getId());
					return;
				}
				
				List<String> cronExps = timeScheduleToCronExpConverter(currentReport.getPeriodicity(), currentReport.getTime_schedule(), currentReport.getDays_week(), currentReport.getOffset_timezone());
				runTaskIfNotRunYet(cronExps, currentReport.getId(), req -> service.updateReportScheduleNextRunTime(req), () -> batchJob.sentMailReportOnSchedule(currentReport));
			} catch (Exception e) {
			}
		}
		
		public ScheduledReportRunnable(ViewReportEntity report) {
			this.prevReport = report;
		}
	}
    
    @PostConstruct
    public void analyticalReportTrackerScheduleWithCronTrigger() {
		try {
			List<AnalyticalReportTrackerEntity> reportTrackers = reportTrackerId > 0 ? 
					Arrays.asList(analyticalReportTrackerService.getSubmittedAnalyticalReportTrackerById(reportTrackerId))
					:
					analyticalReportTrackerService.getAllSubmittedAnalyticalReportTrackers();
			
			cancelScheduledTask(scheduledAnalyticalReportTrackerTaskById, reportTrackerId);
			
			for (AnalyticalReportTrackerEntity reportTracker : reportTrackers) {
				if (Objects.isNull(reportTracker.getId())) continue;
				List<String> cronExps = timeScheduleToCronExpConverter(reportTracker.getCadence(), reportTracker.getStart_date(), getDayInWeekString(reportTracker.getStart_date(), reportTracker.getTimezone()), reportTracker.getTimezone());
				AnalyticalReportTrackerTask task = new AnalyticalReportTrackerTask(reportTracker);
				scheduledAnalyticalReportTrackerTaskById.put(reportTracker.getId(), schedulingTask(task, cronExps));
			}
		} catch (Exception e) {
		}
    }
    
    public void changeAnalyticalReportTrackerSchedule(int id) {
    	this.reportTrackerId = id;
		this.analyticalReportTrackerScheduleWithCronTrigger();
	}
    
    private class AnalyticalReportTrackerTask implements Runnable {
    	AnalyticalReportTrackerEntity prev;
    	
		@Override
		public void run() {
			try {
				AnalyticalReportTrackerEntity curr = analyticalReportTrackerService.getSubmittedAnalyticalReportTrackerById(prev.getId());
				if (
					curr == null ||
					!curr.getStart_date().equals(prev.getStart_date()) ||
					curr.getStatus() != prev.getStatus() ||
					curr.getCadence() != prev.getCadence()
				) {
					changeAnalyticalReportTrackerSchedule(prev.getId());
					return;
				}
				
				List<String> cronExps = timeScheduleToCronExpConverter(curr.getCadence(), curr.getStart_date(), getDayInWeekString(curr.getStart_date(), curr.getTimezone()), curr.getTimezone());
				runTaskIfNotRunYet(cronExps, curr.getId(), req -> analyticalReportTrackerService.updateNextRunTime(req), () -> {});
			} catch (Exception e) {
			}
		}
		
		public AnalyticalReportTrackerTask(AnalyticalReportTrackerEntity item) {
			this.prev = item;
		}
	}
}
