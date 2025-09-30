# MQTT Datalogger Service

Service này xử lý dữ liệu MQTT từ Datalogger và lưu vào database.

## Cấu trúc

### MQTTDataloggerService
- `processMQTTDataloggerMessage(String topic, String payload)`: Xử lý message MQTT chính
- `processDataRow()`: Xử lý từng dòng dữ liệu
- `processSatconPowergate225Data()`: Xử lý dữ liệu riêng cho Satcon Powergate 225
- `saveDataloggerMetadata()`: Lưu metadata của datalogger

### MQTTDataloggerController
- `POST /mqtt/datalogger`: API endpoint để nhận MQTT message

## Cách sử dụng

### 1. Gọi API trực tiếp
```bash
curl -X POST "http://localhost:8080/mqtt/datalogger" \
-H "Content-Type: application/json" \
-d '{
  "topic": "Datalogger/purs",
  "payload": "{\"LOGFILE\":\"mb-104.log\",\"SERIALNUMBER\":\"001EC610021A1\",\"MODBUSPORT\":\"104\",\"MODBUSDEVICE\":\"104\",\"MODE\":\"LOGFILEUPLOAD\",\"DEVICENAME\":\"INV_Satcon_Powergate225\",\"DATA\":[\"2025-09-30,09:48:00\",\"1\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"59648\",\"164\",\"49230\",\"1\",\"0.000\",\"0.000\",\"0.000\",\"0.000\",\"-0.055\",\"-0.055\",\"0.000\",\"276.214\",\"275.605\",\"278\",\"0.992\",\"0.992\",\"0.066\",\"0.492\",\"6\",\"156\",\"15475671\",\"-0.999\",\"59.919\"]}"
}'
```

### 2. Tích hợp với MQTT Client
```java
@Service
public class MQTTClientService {
    
    @Autowired
    private MQTTDataloggerService mqttDataloggerService;
    
    @EventListener
    public void handleMqttMessage(MqttMessageArrivedEvent event) {
        String topic = event.getTopic();
        String payload = new String(event.getMessage().getPayload());
        
        if (topic.startsWith("Datalogger/")) {
            mqttDataloggerService.processMQTTDataloggerMessage(topic, payload);
        }
    }
}
```

## Cấu trúc dữ liệu

### Payload JSON
```json
{
  "LOGFILE": "mb-104.log",
  "SERIALNUMBER": "001EC610021A1", 
  "MODBUSPORT": "104",
  "MODBUSDEVICE": "104",
  "MODE": "LOGFILEUPLOAD",
  "DEVICENAME": "INV_Satcon_Powergate225",
  "DATA": ["2025-09-30,09:48:00", "1", "0", ...]
}
```

### Mapping dữ liệu cho Satcon Powergate 225
- Index 0,1: DateTime (được parse riêng)
- Index 2-5: Fault1-4
- Index 6-8: GridStatus, Status6, Status7
- Index 9: PCSState
- Index 10-11: DCInputPower, DC_Link_Volts
- Index 12-13: DCInputVoltage, DCInputCurrent
- Index 14-16: OutputKVAR, OutputKW, OutputKVA
- Index 17-19: Line_Volts_A/B/C_TEST
- Index 20-22: Line_Amps_A/B/C_TEST
- Index 23-24: NeutralCurrent, StopCode
- Index 25-26: KWHlow, KWH
- Index 27-29: PowerFactor, LineFreq, OutputPowerLimit

## Database Tables

1. **data{device_id}_model_satcon_powergate_225_inverter**: Lưu dữ liệu inverter
2. **data{datalogger_device_id}_model_datalogger**: Lưu metadata datalogger

## Features

- ✅ Parse JSON payload từ MQTT
- ✅ Mapping dữ liệu theo device type
- ✅ Scaling device parameters
- ✅ Check wrong energy alerts
- ✅ Lưu datalogger metadata
- ✅ Error handling và logging
- ✅ RESTful API endpoint

## Lưu ý

- Service hỗ trợ mở rộng cho các device type khác
- Tự động detect device bằng serial number và modbus device
- Có validation và error handling đầy đủ
- Log chi tiết để debug
