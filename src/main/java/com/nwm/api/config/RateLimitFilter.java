package com.nwm.api.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nwm.api.entities.APIAccessLoggingDTO;
import com.nwm.api.entities.ThirdPartyJsonResultEntity;
import com.nwm.api.services.ApiAccessService;
import com.nwm.api.services.RateLimitService;
import com.nwm.api.services.ThirdPartyAPIService;
import com.nwm.api.utils.FLLogger;
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
    private final FLLogger log = FLLogger.getLogger(this.getClass().getSimpleName());

    public RateLimitFilter(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            String key = request.getHeader("X-NWM-API-KEY");
            String route = request.getRequestURI().substring(request.getContextPath().length());
            String method = request.getMethod();

            ThirdPartyAPIService thirdPartyAPIService = new ThirdPartyAPIService();
            if (thirdPartyAPIService.checkEndpointExist(route, method)) {
                String err = thirdPartyAPIService.checkKey(key, request);
                if (!Lib.isBlank(err)) {
                    log.info("RateLimitFilter.checkKeyFail: " + key + " - error: " + err);
                    failResponse(response, 401, err);
                    return;
                }
                ApiAccessService apiAccessService = new ApiAccessService();
                apiAccessService.insertAPIUsage(new APIAccessLoggingDTO(route, method, key));
                if (!rateLimitService.allowRequest(key, route, method)) {
                    log.info("RateLimitFilter.allowRequest: " + key + " - error: Too Many Requests");
                    failResponse(response, 429, "Too Many Requests. Your key was locked");
                    return;
                }
            }
            filterChain.doFilter(request, response);
        } catch (IOException e) {
            log.error("RateLimitFilter.IOException: " + e.getMessage());
        } catch (ServletException e) {
            log.error("RateLimitFilter.ServletException: " + e.getMessage());
        }
    }

    private void failResponse(HttpServletResponse response, int code, String msg) {
        try {
            ThirdPartyJsonResultEntity result = new ThirdPartyJsonResultEntity();
            result.setStatus(false);
            result.setMess(msg);
            result.setData(null);
            result.setTotal_row(0);

            response.setStatus(code);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(result));
            response.getWriter().flush();
        } catch (IOException e) {

        }
    }
}
