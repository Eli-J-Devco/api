package com.nwm.api.config;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
	
	private enum Periodicity {
		DAILY(1),
		WEEKLY(2),
		MONTHLY(3),
		QUARTERLY(4),
		ANNUALLY(5);
		
		private final int value;
		
		Periodicity(int value) {
			this.value = value;
		}
		
		private int getValue() {
			return this.value;
		}
		
		public static Periodicity fromValue(int value) {
			for (Periodicity range : Periodicity.values()) {
				if (range.getValue() == value) return range;
			}
			
			return Periodicity.DAILY;
		}
	}
	
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

	private List<String> timeScheduleToCronExpConverter(int periodicity, String timeSchedule, String daysWeek, String timezone) {
		try {
			List<String> cronsList = new ArrayList<String>();
			if (timeSchedule.isEmpty()) return new ArrayList<String>();
			ZonedDateTime zonedDateTime = ZonedDateTime.parse(timeSchedule, DateTimeFormatter.ofPattern(dateTimeFormatString).withZone(ZoneId.of(timezone)));
			ZonedDateTime utcDateTime = zonedDateTime.withZoneSameInstant(ZoneOffset.UTC);
			
			switch (Periodicity.fromValue(periodicity)) {
				case DAILY: {
					String cron = String.format("0 %d %d * * *", utcDateTime.getMinute(), utcDateTime.getHour());
					cronsList.add(cron);
					break;
				}
				case WEEKLY: {
					if (daysWeek.isEmpty()) return new ArrayList<String>();
					List<String> utcDaysWeek = new ArrayList<>();
					long dayOffset = ChronoUnit.DAYS.between(zonedDateTime.toLocalDate(), utcDateTime.toLocalDate());
					for (int i = 0; i < daysWeek.length(); i++) {
						if (daysWeek.charAt(i) == '0') continue;
						long day = i + 1 + dayOffset - ((i + 1 + dayOffset) > 7 ? 7 : 0);
						utcDaysWeek.add(String.valueOf(day));
					}
					String cron = String.format("0 %d %d * * %s", utcDateTime.getMinute(), utcDateTime.getHour(), utcDaysWeek.stream().collect(Collectors.joining(",")));
					cronsList.add(cron);
					break;
				}
				case MONTHLY:
				case QUARTERLY: {
					for (int i = 1; i <= (Periodicity.fromValue(periodicity) == Periodicity.MONTHLY ? 12 : 4); i++) {
						int month = Periodicity.fromValue(periodicity) == Periodicity.MONTHLY ? i : (zonedDateTime.getMonthValue() + 3 * (i - 1));
						ZonedDateTime lastDayOfMonth = zonedDateTime.withDayOfMonth(1).withMonth(month).with(TemporalAdjusters.lastDayOfMonth());
						String cron = zonedDateTime.getDayOfMonth() == zonedDateTime.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth() || zonedDateTime.getDayOfMonth() > lastDayOfMonth.getDayOfMonth() ? 
								// handle if day out of range of month
								String.format("0 %d %d %d %d *", utcDateTime.getMinute(), utcDateTime.getHour(), lastDayOfMonth.withZoneSameInstant(ZoneOffset.UTC).getDayOfMonth(), lastDayOfMonth.withZoneSameInstant(ZoneOffset.UTC).getMonthValue())
								:
								String.format("0 %d %d %d %d *", utcDateTime.getMinute(), utcDateTime.getHour(), zonedDateTime.withMonth(month).withZoneSameInstant(ZoneOffset.UTC).getDayOfMonth(), zonedDateTime.withMonth(month).withZoneSameInstant(ZoneOffset.UTC).getMonthValue());
						cronsList.add(cron);
					}
					break;
				}
				case ANNUALLY: {
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
	
	private String getDayInWeekString(String dateTimeString) {
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
				List<String> cronExps = timeScheduleToCronExpConverter(reportTracker.getCadence(), reportTracker.getStart_date(), getDayInWeekString(reportTracker.getStart_date()), reportTracker.getTimezone());
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
    
    /*
     * update next run time when manually send mail to prevent duplicating mail sending by schedule on the same day
     */
    public void updateNextRunTimeWhenManuallySendMail(AnalyticalReportTrackerEntity reportTracker) {
		try {
			List<String> cronExps = timeScheduleToCronExpConverter(reportTracker.getCadence(), reportTracker.getStart_date(), getDayInWeekString(reportTracker.getStart_date()), reportTracker.getTimezone());
			
			cronExps.stream()
					.map(cronExp -> new CronSequenceGenerator(cronExp, TimeZone.getTimeZone(ZoneOffset.UTC)).next(new Date()))
					.sorted()
					.findFirst()
					.map(nextRunTime -> nextRunTime.toInstant().atZone(ZoneOffset.UTC).toLocalDate().getDayOfMonth() == LocalDate.now().getDayOfMonth() ?
							cronExps.stream()
									.map(cronExp -> new CronSequenceGenerator(cronExp, TimeZone.getTimeZone(ZoneOffset.UTC)).next(nextRunTime))
									.sorted()
									.findFirst()
									.orElse(null)
							:
							nextRunTime
					)
					.ifPresent(nextRunTime -> {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", reportTracker.getId());
						map.put("time", sdf.format(nextRunTime));
						
						analyticalReportTrackerService.updateNextRunTime(map);
					});
		} catch (Exception e) {
		}
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
				
				List<String> cronExps = timeScheduleToCronExpConverter(curr.getCadence(), curr.getStart_date(), getDayInWeekString(curr.getStart_date()), curr.getTimezone());
				runTaskIfNotRunYet(cronExps, curr.getId(), req -> analyticalReportTrackerService.updateNextRunTime(req), () -> analyticalReportTrackerService.sendMail(curr));
			} catch (Exception e) {
			}
		}
		
		public AnalyticalReportTrackerTask(AnalyticalReportTrackerEntity item) {
			this.prev = item;
		}
	}
}
