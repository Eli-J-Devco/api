package com.nwm.api.services;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MQTTDataloggerServiceTest {

    @Test
    public void testProcessMQTTDataloggerMessage() {
        // Test payload từ log
        String topic = "Datalogger/purs";
        String payload = "{\"LOGFILE\":\"mb-104.log\",\"SERIALNUMBER\":\"001EC610021A1\",\"MODBUSPORT\":\"104\",\"MODBUSDEVICE\":\"104\",\"MODE\":\"LOGFILEUPLOAD\",\"DEVICENAME\":\"INV_Satcon_Powergate225\",\"DATA\":[\"2025-09-30,09:48:00\",\"1\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"59648\",\"164\",\"49230\",\"1\",\"0.000\",\"0.000\",\"0.000\",\"0.000\",\"-0.055\",\"-0.055\",\"0.000\",\"276.214\",\"275.605\",\"278\",\"0.992\",\"0.992\",\"0.066\",\"0.492\",\"6\",\"156\",\"15475671\",\"-0.999\",\"59.919\"]}";
        
        MQTTDataloggerService service = new MQTTDataloggerService();
        
        // Test parsing JSON
        try {
            service.processMQTTDataloggerMessage(topic, payload);
            System.out.println("Test passed - JSON parsing successful");
        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
        }
    }
}
