package com.nwm.api.config;

import com.nwm.api.entities.ThirdPartyJsonResultEntity;
import com.nwm.api.services.RateLimitService;
import com.nwm.api.utils.Lib;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;

    public RateLimitFilter(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String key = request.getHeader("X-NWM-API-KEY");
        if (Lib.isBlank(key)) {
            filterChain.doFilter(request, response);
            return;
        }
        String route = request.getRequestURI().substring(request.getContextPath().length());
        String method = request.getMethod();

        if (!rateLimitService.allowRequest(key, route, method)) {
            ThirdPartyJsonResultEntity result = new ThirdPartyJsonResultEntity();
            result.setStatus(false);
            result.setMess("Too Many Requests. Your key was locked");
            result.setData(null);
            result.setTotal_row(0);

            response.setStatus(429);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(result));
            response.getWriter().flush();
            return;
        }

        filterChain.doFilter(request, response);
    }
}
