package com.nwm.api.services;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class RestApiService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String callApi(
            String url,
            HttpMethod method,
            Map<String, String> headers,
            Object body
    ) {

        HttpHeaders httpHeaders = new HttpHeaders();

        if (headers != null) {
            headers.forEach(httpHeaders::set);
        }

        HttpEntity<Object> entity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                method,
                entity,
                String.class
        );

        return response.getBody();
    }
}
