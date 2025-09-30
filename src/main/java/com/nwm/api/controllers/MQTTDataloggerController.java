/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.services.MQTTDataloggerService;
import com.nwm.api.utils.Lib;

@RestController
@RequestMapping("/mqtt")
public class MQTTDataloggerController extends BaseController {

    private final MQTTDataloggerService mqttDataloggerService;

    public MQTTDataloggerController(MQTTDataloggerService mqttDataloggerService) {
        this.mqttDataloggerService = mqttDataloggerService;
    }

    /**
     * @description Receive MQTT Datalogger message and process it
     * @author Duc.Pham
     * @since 2025-09-30
     */
    @PostMapping("/datalogger")
    public ResponseEntity<MQTTDataloggerResponse> receiveMQTTDataloggerMessage(@RequestBody MQTTDataloggerRequest request) {
        try {
            if (Lib.isBlank(request.getTopic()) || Lib.isBlank(request.getPayload())) {
                MQTTDataloggerResponse response = new MQTTDataloggerResponse();
                response.setError(1);
                response.setMessage("Topic and payload are required");
                return ResponseEntity.badRequest().body(response);
            }

            // Process the MQTT message
            mqttDataloggerService.processMQTTDataloggerMessage(request.getTopic(), request.getPayload());

            MQTTDataloggerResponse response = new MQTTDataloggerResponse();
            response.setError(0);
            response.setMessage("MQTT Datalogger message processed successfully");
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            log.error("Error processing MQTT Datalogger message", ex);
            MQTTDataloggerResponse response = new MQTTDataloggerResponse();
            response.setError(1);
            response.setMessage("Error processing MQTT message: " + ex.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Request model for MQTT Datalogger
     */
    public static class MQTTDataloggerRequest {
        private String topic;
        private String payload;

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getPayload() {
            return payload;
        }

        public void setPayload(String payload) {
            this.payload = payload;
        }
    }

    /**
     * Response model for MQTT Datalogger
     */
    public static class MQTTDataloggerResponse {
        private int error;
        private String message;

        public int getError() {
            return error;
        }

        public void setError(int error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
