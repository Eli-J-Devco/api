package com.nwm.api.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.nwm.api.batchjob.BatchJob;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.services.BatchJobService;
import com.nwm.api.utils.Lib;

@Component
public class ReportTaskScheduler {
	
	private final List<ScheduledFuture<?>> scheduledTasks = new ArrayList<>();
	
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @PostConstruct
    public void scheduleWithCronTrigger() {
    	BatchJobService service = new BatchJobService();
    	BatchJob batchJob = new BatchJob();
    	List<ViewReportEntity> listReports = service.getListReports(new ViewReportEntity());
    	
		try {
			// cancel existed schedules before create all new schedules
			if (scheduledTasks.size() > 0) scheduledTasks.forEach(task -> task.cancel(true));
			
			for (ViewReportEntity report : listReports) {
				if (report.getSchedule_enable() == 0) continue;
				List<String> cronExps = Lib.datetimeToCronExp(report.getPeriodicity(), report.getTime_schedule(), report.getDays_week(), report.getOffset_timezone());
				if (cronExps.size() == 0) throw new Exception();
				
				for (String cronExp: cronExps) {
					ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(new Runnable() {
						@Override
						public void run() {
							batchJob.sentMailReportOnSchedule(report);
						}
					}, new CronTrigger(cronExp));
				
					if (scheduledFuture != null) scheduledTasks.add(scheduledFuture);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
