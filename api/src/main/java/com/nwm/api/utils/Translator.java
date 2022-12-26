package com.nwm.api.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class Translator {
	private static ResourceBundleMessageSource translateMessageSource;

	   @Autowired
	   Translator(ResourceBundleMessageSource messageSource) {
	      Translator.translateMessageSource = messageSource;
	   }

	   public static String toLocale(String msgCode) {
	      Locale locale = LocaleContextHolder.getLocale();
	      return translateMessageSource.getMessage(msgCode, null, locale);
	   }
}
