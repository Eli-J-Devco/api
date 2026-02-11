package com.nwm.api.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AOPConfig {

	@Bean
	AccountStatusAOP accountStatusAOP() {
		return new AccountStatusAOP();
	}
	
	@Bean
	ThirdPartyAPILoggingAOP thirdPartyAPILoggingAOP() {
		return new ThirdPartyAPILoggingAOP();
	}
	   
}
