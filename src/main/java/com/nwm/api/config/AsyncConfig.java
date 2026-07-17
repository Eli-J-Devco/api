package com.nwm.api.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {
	@Value("${executor.device-data.core-pool-size:8}")
	private int corePoolSize;
	@Value("${executor.device-data.max-pool-size:24}")
	private int maxPoolSize;
	@Value("${executor.device-data.queue-capacity:0}")
	private int queueCapacity;
	@Value("${executor.device-data.keep-alive-seconds:60}")
	private int keepAliveSeconds;

	@Bean(name = "deviceDataExecutor")
	public Executor deviceDataExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setKeepAliveSeconds(keepAliveSeconds);
		executor.setAllowCoreThreadTimeOut(true);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setAwaitTerminationSeconds(30);
		executor.setThreadNamePrefix("AsyncDeviceDataThread-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();

		return executor;
	}
}
