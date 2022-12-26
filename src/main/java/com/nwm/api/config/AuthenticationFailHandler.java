package com.nwm.api.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.nwm.api.entities.JsonResultEntity;
import com.nwm.api.utils.FLLogger;
@Component
public class AuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

	protected final FLLogger log = FLLogger.getLogger("AuthenticationFailHandler");

	/**
	 * custom response on fail login
	 */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

    	JsonResultEntity jsonE = new JsonResultEntity();
    	jsonE.setStatus(false);
    	jsonE.setMess(exception.getMessage());
    	jsonE.setData(null);
    	jsonE.setTotal_row(0);
    	log.info("Login fail");
            response.setStatus(200);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new Gson().toJson(jsonE));
    }
}
