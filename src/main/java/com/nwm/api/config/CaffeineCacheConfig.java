package com.nwm.api.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
@ConditionalOnProperty(value = "cache.enabled", matchIfMissing = true)
public class CaffeineCacheConfig {
	@Value("${cache.site.ttl:60}")
	private int siteTTL;
	@Value("${cache.device.ttl:30}")
	private int deviceTTL;
	@Value("${cache.hidden-data-by-device.ttl:60}")
	private int hiddenDataBydeviceTTL;
	
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.registerCustomCache("sites", siteCache());
        cacheManager.registerCustomCache("devices", deviceCache());
        cacheManager.registerCustomCache("hiddenDataByDevice", hiddenDataByDeviceCache());
        
        return cacheManager;
    }
    
	private Cache<Object, Object> siteCache() {
    	return Caffeine.newBuilder().expireAfterWrite(siteTTL, TimeUnit.MINUTES).build();
	}
	
	private Cache<Object, Object> deviceCache() {
		return Caffeine.newBuilder().expireAfterWrite(deviceTTL, TimeUnit.MINUTES).build();
	}
	
	private Cache<Object, Object> hiddenDataByDeviceCache() {
		return Caffeine.newBuilder().expireAfterWrite(hiddenDataBydeviceTTL, TimeUnit.MINUTES).build();
	}
}
