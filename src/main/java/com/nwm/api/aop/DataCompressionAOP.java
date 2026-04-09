package com.nwm.api.aop;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;


import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.entities.JsonResultEntity;

@Aspect
public class DataCompressionAOP {
	@Pointcut("@annotation(com.nwm.api.aop.customAnnotation.Compressed)")
	public void compressed() {}
	
	@AfterReturning(value = "compressed()", returning = "result")
    public void compressMethod(JsonResultEntity result) {
		try (
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    	GZIPOutputStream gzipOut = new GZIPOutputStream(baos);
		) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(gzipOut, result.getData());
			result.setData(baos.toByteArray());
		} catch (IOException e) {
		}
    }  
}
