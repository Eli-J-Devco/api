package com.nwm.api.config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.nwm.api.batchjob.BatchJob;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.services.BatchJobService;
import com.nwm.api.utils.Lib;

@Component
public class ReportTaskScheduler {
	
	private final Map<Integer, List<ScheduledFuture<?>>> scheduledTasks = new HashMap<Integer, List<ScheduledFuture<?>>>();
	
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;
    BatchJobService service = new BatchJobService();
    BatchJob batchJob = new BatchJob();
    private int reportId = 0;

    @PostConstruct
    public void scheduleWithCronTrigger() {
    	List<ViewReportEntity> listReports = service.getListReports(reportId);
    	
		try {
			// cancel modified schedule before create new one
			// application start-up won't run this, only affected when user change schedule
			if (reportId > 0) {
				List<ScheduledFuture<?>> list = scheduledTasks.get(reportId);
				list.forEach(item -> item.cancel(false));
				scheduledTasks.remove(reportId);
			}
			
			for (ViewReportEntity report : listReports) {
				if (report.getSchedule_enable() == 0) continue;
				List<String> cronExps = Lib.datetimeToCronExp(report.getPeriodicity(), report.getTime_schedule(), report.getDays_week(), report.getOffset_timezone());
				if (cronExps.size() == 0) continue;
				ScheduledReportRunnable task = new ScheduledReportRunnable(report);
				List<ScheduledFuture<?>> scheduledFuturesList = new ArrayList<ScheduledFuture<?>>();
				
				for (String cronExp: cronExps) {
					ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(task, new CronTrigger(cronExp));
					if (scheduledFuture != null) scheduledFuturesList.add(scheduledFuture);
				}
				
				scheduledTasks.put(report.getId(), scheduledFuturesList);
			}
		} catch (Exception e) {
		}
    }
    
    public void changeReportSchedule(int reportId) {
		this.reportId = reportId;
		this.scheduleWithCronTrigger();
	}
    
    private class ScheduledReportRunnable implements Runnable {
    	ViewReportEntity prevReport;
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    	
		@Override
		public void run() {
			try {
				ViewReportEntity currentReport = service.getReportDetail(prevReport);
				if (
					currentReport == null ||
					!currentReport.getDays_week().equals(prevReport.getDays_week()) ||
					!currentReport.getTime_schedule().equals(prevReport.getTime_schedule()) ||
					currentReport.getPeriodicity() != prevReport.getPeriodicity()
				) {
					changeReportSchedule(prevReport.getId());
					return;
				}
				
				List<String> cronExps = Lib.datetimeToCronExp(currentReport.getPeriodicity(), currentReport.getTime_schedule(), currentReport.getDays_week(), currentReport.getOffset_timezone());
				List<Date> dateList = new ArrayList<Date>();
				cronExps.forEach(cron -> {
					CronSequenceGenerator generator = new CronSequenceGenerator(cron);
					Date nextRunTime = generator.next(new Date());
					dateList.add(nextRunTime);
				});
				if (dateList.size() == 0) return;
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", currentReport.getId());
				map.put("time", df.format(Collections.min(dateList)));
				boolean notRunYet = service.updateReportScheduleNextRunTime(map);
				if (!notRunYet) return;
				
				batchJob.sentMailReportOnSchedule(currentReport);
			} catch (Exception e) {
			}
		}
		
		public ScheduledReportRunnable(ViewReportEntity report) {
			this.prevReport = report;
		}
	}
}
