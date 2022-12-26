package com.nwm.api.config;

import java.io.IOException;
import java.util.Map;

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
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.nwm.api.entities.ShopEntity;
//import com.nwm.api.services.ShopService;
import com.nwm.api.utils.Constants;
//import com.nwm.api.utils.EventSendHelper;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, grant_type, client_id, lang, x-access-token");
        response.setHeader("Access-Control-Max-Age", "3600");
        
        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
        	if("client_credentials".equals(req.getParameter("grant_type"))) {
//	    		ShopService shopS = new ShopService();
//	    		ShopEntity shop = new ShopEntity();
//	    		shop.setApi_key(req.getParameter("api-key"));
//	    		shop.setApi_secret(req.getParameter("api-secret"));
//	    		if(shopS.findShopByAPI(shop)==null) {
//	    			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "hello");
//	    			return;
//	    		}
        	}
//        	EventSendHelper.requestLogEvent((HttpServletRequest) req);
        	
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