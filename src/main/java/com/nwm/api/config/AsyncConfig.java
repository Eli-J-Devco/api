package com.nwm.api.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {
	@Value("${executor.device-data.core-pool-size:36}")
	private int deviceDataCorePoolSize;
	@Value("${executor.device-data.max-pool-size:72}")
	private int deviceDataMaxPoolSize;
	@Value("${executor.device-data.queue-capacity:200}")
	private int deviceDataQueueCapacity;
	
	@Value("${executor.site.core-pool-size:36}")
	private int siteCorePoolSize;
	@Value("${executor.site.max-pool-size:72}")
	private int siteMaxPoolSize;
	@Value("${executor.site.queue-capacity:200}")
	private int siteQueueCapacity;
	
	@Bean(name = "deviceDataExecutor")
	public Executor deviceDataExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(deviceDataCorePoolSize);
		executor.setMaxPoolSize(deviceDataMaxPoolSize);
		executor.setQueueCapacity(deviceDataQueueCapacity);
		executor.setThreadNamePrefix("AsyncDeviceDataThread-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
		executor.initialize();
		
		return executor;
    }
	
	@Bean(name = "siteExecutor")
	public Executor siteExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(siteCorePoolSize);
		executor.setMaxPoolSize(siteMaxPoolSize);
		executor.setQueueCapacity(siteQueueCapacity);
		executor.setThreadNamePrefix("AsyncSiteThread-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
		executor.initialize();
		
		return executor;
    }
}
