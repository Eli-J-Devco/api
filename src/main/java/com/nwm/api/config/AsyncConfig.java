package com.nwm.api.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {
	@Value("${executor.device-data.core-pool-size:10}")
	private int corePoolSize;
	@Value("${executor.device-data.max-pool-size:20}")
	private int maxPoolSize;
	@Value("${executor.device-data.queue-capacity:500}")
	private int queueCapacity;
	
	@Bean(name = "deviceDataExecutor")
	public Executor deviceDataExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix("AsyncDeviceDataThread-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
		executor.initialize();
		
		return executor;
    }
}
