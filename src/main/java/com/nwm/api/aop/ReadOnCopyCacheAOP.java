package com.nwm.api.aop;

import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DevicesByTypeEntity;

@Aspect
@Order(1)
public class ReadOnCopyCacheAOP {
	@Pointcut("@annotation(org.springframework.cache.annotation.Cacheable)")
	public void cacheable() {}
	
	@Around(value = "cacheable()")
    public Object instantiateNewObject(ProceedingJoinPoint joinPoint) {
		try {
			Object result = joinPoint.proceed();
			
			if (result instanceof DevicesByTypeEntity) {
				DevicesByTypeEntity devices = (DevicesByTypeEntity) result;
				
				DevicesByTypeEntity copy = new DevicesByTypeEntity();
				copy.setAll(devices.getAll().stream().map(DeviceEntity::new).collect(Collectors.toList()));
				copy.setMeter(devices.getMeter().stream().map(DeviceEntity::new).collect(Collectors.toList()));
				copy.setInverter(devices.getInverter().stream().map(DeviceEntity::new).collect(Collectors.toList()));
				copy.setIrradiance(devices.getIrradiance().stream().map(DeviceEntity::new).collect(Collectors.toList()));
				
				return copy;
			}
			
			return result;
		} catch (Throwable e) {
			return null;
		}
    }  
}
