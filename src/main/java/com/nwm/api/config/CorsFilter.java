package com.nwm.api.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.nwm.api.utils.EventSendHelper;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//        response.setHeader("Access-Control-Allow-Credentials", "true");
        
        System.out.println("CORSFilter HTTP Request: " + request.getMethod());
        
        response.setHeader("Access-Control-Allow-Credentials", "false");
        
        //response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, grant_type, customer_type, client_id, lang, x-access-token");
        response.setHeader("Access-Control-Max-Age", "3600");
        
        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
        	System.out.println("Core method: " + request.getMethod());
        	System.out.println("Core OK: " + HttpServletResponse.SC_OK);
        	
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
        	System.out.println("Core method: " + request.getMethod());
        	if("client_credentials".equals(req.getParameter("grant_type"))) {
        		System.out.print(req.getParameter("grant_type"));
        	}
        	EventSendHelper.requestLogEvent((HttpServletRequest) req);
        	
    		chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }
}