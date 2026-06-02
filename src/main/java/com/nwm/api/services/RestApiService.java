package com.nwm.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
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
        try {
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
        } catch (HttpStatusCodeException e) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(e.getResponseBodyAsString());

                String detail = node.path("detail").asText();
                int status = node.path("status").asInt();
                String mess = status == 429 ? "HTTP/1.1 429 Too Many Requests. Backfill not successful. Try again later." : "";

                throw new RuntimeException(mess, e);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(e.getResponseBodyAsString());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
