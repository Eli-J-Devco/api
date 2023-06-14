/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.config;

import java.util.ResourceBundle;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.nwm.api.batchjob.BatchJob;
import com.nwm.api.batchjob.BatchJobFTP;
import com.nwm.api.batchjob.BatchJobSMAFTP;
import com.nwm.api.utils.Constants;
@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfig {
	
	/**
	 * @description batch job get weather
	 * @author long.pham
	 * @since 2021-02-17
	 */
//	@Scheduled(cron = "* * * * * *")
//	@Scheduled(cron = "0 */1 * * * *")
	@Scheduled(cron = "0 */1 * * * *")
	public void startBatchJobSolarOpenWeather() throws Exception {
		BatchJob job =new BatchJob(); 
//		job.runCronJobSolarOpenWeather();
	}
	
	
	/**
	 * @description batch job update data device energy lifetime
	 * @author long.pham
	 * @since 2022-06-20
	 */
//	@Scheduled(cron = "* * * * * *")
//	@Scheduled(cron = "0 */1 * * * *")
	@Scheduled(cron = "0 */5 * * * *")
	public void startBatchJobUpdateEnergyLifetime() throws Exception {
		BatchJob job =new BatchJob(); 
		job.runCronJobUpdateEnergyLifetime();
	}
	
	
	
	/**
	 * @description batch job update data device energy today
	 * @author long.pham
	 * @since 2022-06-20
	 */
//	@Scheduled(cron = "* * * * * *")
//	@Scheduled(cron = "0 */1 * * * *")
	@Scheduled(cron = "0 */5 * * * *")
	public void startBatchJobUpdateEnergyToday() throws Exception {
		BatchJob job =new BatchJob(); 
		job.runCronJobUpdateEnergyToday();
	}
	
	
	/**
	 * @description batch job update data device energy today
	 * @author long.pham
	 * @since 2022-06-20
	 */
//	@Scheduled(cron = "* * * * * *")
//	@Scheduled(cron = "0 */1 * * * *")
	@Scheduled(cron = "0 */5 * * * *")
	public void startBatchJobUpdateEnergyThisMonth() throws Exception {
		BatchJob job =new BatchJob(); 
		job.runCronJobUpdateEnergyThisMonth();
	}
	
	
	
	
	
	/**
	 * @description batch job get weather
	 * @author long.pham
	 * @since 2021-02-17
	 */
//	@Scheduled(cron = "* * * * * *")
//	@Scheduled(cron = "0 */1 * * * *")
	@Scheduled(cron = "0 * */60 * * *")
	public void startBatchJobGetWeather() throws Exception {
		BatchJob job =new BatchJob(); 
		job.runCronJobGetWeather();
	}
	
	/**
	 * @description batch job get alert for all device No Communication
	 * @author long.pham
	 * @since 2021-02-17
	 */
//	@Scheduled(cron = "* * * * * *")
//	@Scheduled(cron = "0 */1 * * * *")
//	@Scheduled(cron = "0 */1 * * * *")
//	public void startBatchJobGetAlert() throws Exception {
//		BatchJob job =new BatchJob(); 
////		job.runCronJobGetAlert();
//	}

	
	
	/**
	 * @description batch job get alert for all device No Production
	 * @author long.pham
	 * @since 2021-05-20
	 */
//	@Scheduled(cron = "* * * * * *")
//	@Scheduled(cron = "0 */1 * * * *")
	@Scheduled(cron = "0 */30 * * * *")
	public void startBatchJobGetNoProduction() throws Exception {
		ResourceBundle resourceAppBundle = ResourceBundle.getBundle(Constants.appConfigFileName);
		String env = readProperty(resourceAppBundle, "spring.profiles.active", "dev");
		if (!env.equals("staging")) {
			BatchJob job =new BatchJob(); 
			job.runCronJobGetNoProduction();
		}
	}
	
	/**
	 * @description batch job get alert for device no communication
	 * @author long.pham
	 * @since 2021-05-20
	 */
//	@Scheduled(cron = "* * * * * *")
//	@Scheduled(cron = "0 */1 * * * *")
	@Scheduled(cron = "0 */20 * * * *")
	public void startBatchJobGetNoCommunication() throws Exception {
		ResourceBundle resourceAppBundle = ResourceBundle.getBundle(Constants.appConfigFileName);
		String env = readProperty(resourceAppBundle, "spring.profiles.active", "dev");
		if (!env.equals("staging")) {
			BatchJob job =new BatchJob(); 
			job.runCronJobGetNoCommunication();
		}
	}
	
	
	/**
	 * @description batch job auto close alert from datalogger
	 * @author long.pham
	 * @since 2021-05-18
	 */
//	@Scheduled(cron = "* * * * * *")
//	@Scheduled(cron = "0 */1 * * * *")
	@Scheduled(cron = "0 */5 * * * *")
	public void startBatchJobAutoCloseAlertFromDatalogger() throws Exception {
		BatchJob job =new BatchJob(); 
		job.runCronJobCloseAlertFromDatalogger();
	}
	
	
	/**
	 * @description batch job auto close alert from datalogger
	 * @author long.pham
	 * @since 2022-07-25
	 */
//	@Scheduled(cron = "* * * * * *")
//	@Scheduled(cron = "0 */1 * * * *")
	@Scheduled(cron = "0 */10 * * * *")
	public void startBatchJobResetLastValue() throws Exception {
		BatchJob job =new BatchJob(); 
		job.runCronJobResetLastValue();
	}
	
	
	/**
	 * @description batch job auto close alert when system fix alert
	 * @author long.pham
	 * @since 2021-05-18
	 */
//	@Scheduled(cron = "* * * * * *")
//	@Scheduled(cron = "0 */1 * * * *")
	@Scheduled(cron = "0 */30 * * * *")
	public void startBatchJobAutoSenmailAlert() throws Exception {
		BatchJob job =new BatchJob(); 
		job.runCronJobAutoSentMailAlert();
	}
	
	
	/**
	 * @description batch job generate data report
	 * @author long.pham
	 * @since 2021-05-18
	 */
//	@Scheduled(cron = "* * * * * *")
//	@Scheduled(cron = "0 */1 * * * *")
	@Scheduled(cron = "0 */60 * * * *")
	public void startBatchJobGenerateDataReport() throws Exception {
		BatchJob job =new BatchJob(); 
		job.runCronJobGenerateDataReport();
	}
	
	
	/**
	 * @description batch job generate data report
	 * @author long.pham
	 * @since 2021-05-18
	 */
//	@Scheduled(cron = "* * * * * *")
//	@Scheduled(cron = "0 */1 * * * *")
	@Scheduled(cron = "0 */20 * * * *")
	
	// Start every day 2 PM.
//	@Scheduled(cron = "0 0 0/20 ? * *")
	public void startBatchJobGeneratePerformanceRatio() throws Exception {
		BatchJob job =new BatchJob(); 
		job.startBatchJobGeneratePerformanceRatio();
	}
	
	
	/**
	 * @description sent mail daily report on schedule
	 * @author Hung.Bui
	 * @since 2022-12-22
	 */
	@Scheduled(cron = "0 0 */1 * * *")
	public void sentMailDailyReportOnSchedule() throws Exception {
		BatchJob job = new BatchJob(); 
		job.sentMailReportOnSchedule(1);
	}
	
	/**
	 * @description sent mail monthly report on schedule
	 * @author Hung.Bui
	 * @since 2022-12-22
	 */
	@Scheduled(cron = "0 0 */1 28-31 * *") // cron expression don't support last day of month L for this running spring version
	@Scheduled(cron = "0 0 */1 1 * *") // case: local time (server) is after time at site
	public void sentMailMonthlyReportOnSchedule() throws Exception {
		BatchJob job = new BatchJob(); 
		job.sentMailReportOnSchedule(2);
	}
	
	/**
	 * @description sent mail quarterly report on schedule
	 * @author Hung.Bui
	 * @since 2022-12-22
	 */
	@Scheduled(cron = "0 0 */1 31 3 *")
	@Scheduled(cron = "0 0 */1 30 6 *")
	@Scheduled(cron = "0 0 */1 30 9 *")
	@Scheduled(cron = "0 0 */1 31 12 *")
	@Scheduled(cron = "0 0 */1 1 1,4,7,10 *") // case: local time (server) is after time at site
	public void sentMailQuarterlyReportOnSchedule() throws Exception {
		BatchJob job = new BatchJob(); 
		job.sentMailReportOnSchedule(3);
	}
	
	/**
	 * @description sent mail annually report on schedule
	 * @author Hung.Bui
	 * @since 2022-12-22
	 */
	@Scheduled(cron = "0 0 */1 31 12 *")
	@Scheduled(cron = "0 0 */1 1 1 *") // case: local time (server) is after time at site
	public void sentMailAnnuallyReportOnSchedule() throws Exception {
		BatchJob job = new BatchJob(); 
		job.sentMailReportOnSchedule(4);
	}
	
	
	/**
	 * @description read folder from FTP account
	 * @author Long.Pham
	 * @since 2023-01-04
	 */
	@Scheduled(cron = "0 */5 * * * *")
	public void readFolderFTP() throws Exception {
		ResourceBundle resourceAppBundle = ResourceBundle.getBundle(Constants.appConfigFileName);
		String env = readProperty(resourceAppBundle, "spring.profiles.active", "dev");
		BatchJobFTP job = new BatchJobFTP(); 
		switch (env) {
		case "test":
//			job.readFolderFTP();
			break;
		case "staging":
		case "prod":
			System.out.println("FTP Sungrow start upload");
//			job.readFolderFTP();
			break;
		}
		
		
	}
	
	
	private static String readProperty(ResourceBundle resourceBundle, String key, String defaultValue) {
		String value = defaultValue;
		try {
			value = resourceBundle.getString(key);
		} catch (Exception e) {}
		return value;
	}
	
	/**
	 * @description SMA read folder from FTP account
	 * @author Long.Pham
	 * @since 2023-01-30
	 */
	@Scheduled(cron = "0 */5 * * * *")
	public void readFolderSMAFTP() throws Exception {
		ResourceBundle resourceAppBundle = ResourceBundle.getBundle(Constants.appConfigFileName);
		String env = readProperty(resourceAppBundle, "spring.profiles.active", "dev");
		BatchJobSMAFTP job = new BatchJobSMAFTP();
		switch (env) {
		case "test":
//			job.readFolderSMAFTP();
			break;
		case "staging":
		case "prod":
//			job.readFolderSMAFTP();
			break;
		}
	}
	
	
	/**
	 * @description get sunrise sunset
	 * @author Duy.Phan
	 * @since 2023-02-02
	 */
	@Scheduled(cron = "0 0 0 * * 0")
	@Scheduled(cron = "0 0 0 * * 1")
	public void startBatchJobGetSunriseSunset() throws Exception {
		BatchJob job = new BatchJob(); 
		job.runCronJobGetSunriseSunset();
	}
	
	/**
	 * @description batch job get run ssh cell modem and datalogger
	 * @author long.pham
	 * @since 2023-05-08
	 */
//	@Scheduled(cron = "* * * * * *")
//	@Scheduled(cron = "0 */1 * * * *")
	@Scheduled(cron = "0 */3 * * * *")
	public void startBatchJobSSHCellModem() throws Exception {
		BatchJob job =new BatchJob(); 
		job.runCronJobSSHCellModem();
		job.runCronJobSSHDatalogger();
	}
	
	
	/**
	 * @description batch job get run ftp get data from datalogger SMA DATA MANAGER
	 * @author long.pham
	 * @since 2023-05-08
	 */
//	@Scheduled(cron = "* * * * * *")
//	@Scheduled(cron = "0 */1 * * * *")
	@Scheduled(cron = "0 */10 * * * *")
	public void startBatchJobSMADataManager() throws Exception {
		ResourceBundle resourceAppBundle = ResourceBundle.getBundle(Constants.appConfigFileName);
		String env = readProperty(resourceAppBundle, "spring.profiles.active", "dev");
		if (env.equals("staging")) {
			BatchJob job =new BatchJob();
			job.runCronJobSMADataManager();
		}
		
		
	}
	
	
	/**
	 * @description batch job read file xml from datalogger SMA DATA MANAGER
	 * @author long.pham
	 * @since 2023-05-08
	 */
//	@Scheduled(cron = "* * * * * *")
//	@Scheduled(cron = "0 */1 * * * *")
	@Scheduled(cron = "0 */5 * * * *")
	public void startBatchJobReadXMLDataManager() throws Exception {
		ResourceBundle resourceAppBundle = ResourceBundle.getBundle(Constants.appConfigFileName);
		String env = readProperty(resourceAppBundle, "spring.profiles.active", "dev");
		if (env.equals("staging")) {
			BatchJob job =new BatchJob(); 
			job.runCronJobReadXMLDataManager();
		}
		
	}

}
