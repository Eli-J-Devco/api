package com.nwm.api.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.*;
import com.nwm.api.utils.Lib;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpStatusCodeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SolarEdgeService extends DB  {

    private final RestApiService restApiService = new RestApiService();
    private final ObjectMapper mapper = new ObjectMapper();

    private final DeviceService  deviceService = new DeviceService();

    private final String DOMAIN_URL = "https://monitoringapi.solaredge.com";

    private enum DeviceType {
        INVERTER(1),
        METER(3),
        SENSOR(4);

        private final int type;

        DeviceType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }

    /**
     * @description get solar edge site id & api key by nw site id
     * @author quan.nguyen
     * @since 2026-05-12
     * @params SiteEntity
     */
    public Map<String, Object> getSolarEdgeInfo(SiteEntity obj) {
        try {
            SiteEntity siteEntity = (SiteEntity) queryForObject("SolarEdge.getSolarEdgeInfo", obj);
            if (siteEntity == null) {
                return null;
            }
            Map<String, Object> data = new HashMap<>();
            data.put("solar_edge_id", siteEntity.getSolar_edge_id());
            data.put("solar_edge_api_key", siteEntity.getSolar_edge_api_key());
            data.put("id", siteEntity.getId());
            data.put("time_zone_value", siteEntity.getTime_zone_value());
            return data;
        } catch (Exception e) {
            this.log.error("SolarEdgeService.getSolarEdgeInfo ", e);
        }
        return null;
    }

    /**
     * @description check solar edge site id and api key valid
     * @author minh.le
     * @since 2026-05-13
     * @params SiteEntity
     */
    public Map<String, Object> getSolarEdgeSiteInfo(SiteEntity obj) {
        try {
            Map<String, Object> info = getSolarEdgeInfo(obj);
            if (info == null) {
                return null;
            }
            Long solarEdgeId = (Long) info.get("solar_edge_id");
            String solarEdgeApiKey = (String) info.get("solar_edge_api_key");
            if (Lib.isBlank(solarEdgeApiKey) || solarEdgeId == null || solarEdgeId == 0) {
                return null;
            }

            StringBuilder url = new StringBuilder();
            url.append(DOMAIN_URL);
            url.append("/site");
            url.append("/").append(solarEdgeId);
            url.append("/details?api_key=").append(solarEdgeApiKey);

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");

            String response = restApiService.callApi(
                    url.toString(),
                    HttpMethod.GET,
                    headers,
                    null
            );

            return mapper.readValue(
                    response,
                    new TypeReference<Map<String, Object>>() {}
            );

        } catch (Exception e) {
            this.log.error("SolarEdgeService.getSolarEdgeSiteInfo ", e);
        }
        return null;
    }

    /**
     * @description get device from solar site and add to db
     * @author minh.le
     * @since 2026-05-13
     * @params SiteEntity
     */
    public List<Map<String, Object>> syncInventory(SiteEntity obj) {
        try {
            Map<String, Object> info = getSolarEdgeInfo(obj);
            if (info == null) {
                return null;
            }
            Long solarEdgeId = (Long) info.get("solar_edge_id");
            String solarEdgeApiKey = (String) info.get("solar_edge_api_key");
            if (Lib.isBlank(solarEdgeApiKey) || solarEdgeId == null || solarEdgeId == 0) {
                return null;
            }

            StringBuilder url = new StringBuilder();
            url.append(DOMAIN_URL);
            url.append("/site");
            url.append("/").append(solarEdgeId);
            url.append("/inventory?api_key=").append(solarEdgeApiKey);

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");

            String response = restApiService.callApi(
                    url.toString(),
                    HttpMethod.GET,
                    headers,
                    null
            );

            Map<String, Object> data = mapper.readValue(
                    response,
                    new TypeReference<Map<String, Object>>() {}
            );

            if (data == null) {
                return null;
            }

            Map<String, Object> inventory =
                    (Map<String, Object>) data.get("Inventory");

            if (inventory == null) {
                return null;
            }

            List<Map<String, Object>> inverters =
                    (List<Map<String, Object>>) inventory.get("inverters");
            List<Map<String, Object>> sensors =
                    (List<Map<String, Object>>) inventory.get("sensors");
            List<Map<String, Object>> meters =
                    (List<Map<String, Object>>) inventory.get("meters");

            List<DeviceEntity> deviceList = (List<DeviceEntity>) queryForList("SolarEdge.getSolarEdgeDeviceBySite", obj);
            List<DeviceGroupEntity> deviceGroupList = (List<DeviceGroupEntity>) queryForList("SolarEdge.getDeviceGroupModelName");

//            Set<String> existedKeys = new HashSet<>();

            Map<String, DeviceEntity> existedDevices = new HashMap<>();
            List<Map<String, Object>> result = new ArrayList<>();

            if (deviceList != null) {
                for (DeviceEntity device : deviceList) {
                    if (device.getModbusdevicenumber() != null) {
                        existedDevices.put(device.getModbusdevicenumber(), device);
                    }
                }
            }

            if (inverters != null) {
                String manufacture = "";
                for (Map<String, Object> inverter : inverters) {
                    String tableName = "";

                    if (deviceGroupList != null && !deviceGroupList.isEmpty()) {
                        DeviceGroupEntity entity = deviceGroupList.stream().filter(item -> item.getId() == 179).findFirst().orElse(null);
                        if (entity != null) {
                            tableName = entity.getTable_name();
                            manufacture = entity.getManufacture();
                        }
                    }

                    String sn = (String) inverter.get("SN");

                    DeviceEntity existedDevice = existedDevices.get(sn);

                    if (existedDevice != null) {
                        if (existedDevice.getIs_delete() == 1) {
                            Map<String, Object> updateItem = new HashMap<>();
                            updateItem.put("id", existedDevice.getId());
                            update("SolarEdge.updateDevice", updateItem);
                        }

                        Map<String, Object> item = new HashMap<>();
                        item.put("device_name", existedDevice.getDevicename());
                        item.put("serial_number", existedDevice.getSerial_number());
                        item.put("manufacturer", manufacture);
                        item.put("model", Lib.isBlank((String) inverter.get("model")) ? "" : (String) inverter.get("model"));
                        item.put("data_table_name", existedDevice.getDatatablename());
                        result.add(item);
                        continue;
                    }

                    DeviceEntity newDevice = new DeviceEntity();

                    newDevice.setId_site(obj.getId());
                    newDevice.setId_vendor(1);
                    newDevice.setId_device_type(1);
                    newDevice.setId_device_group(179);
                    newDevice.setSerial_number(solarEdgeId.toString());
                    newDevice.setModbusdevicenumber(sn);
                    newDevice.setDevicename((String) inverter.get("name"));
                    newDevice.setCreate_total_device(1);
                    newDevice.setDatatablename("model_SolarEdge_API_inverter");
                    newDevice.setCommunication_method(3);
                    if (!Lib.isBlank(tableName)) {
                        newDevice.setDevice_group_table(tableName);
                    }
                    if (deviceGroupList != null && !deviceGroupList.isEmpty()) {
                        DeviceGroupEntity entity = deviceGroupList.stream().filter(item -> item.getId() == 179).findFirst().orElse(null);
                        if (entity != null) {
                            newDevice.setDevice_group_table(entity.getTable_name());
                            manufacture = entity.getManufacture();
                        }
                    }

                    DeviceEntity insertedDevice = deviceService.insertDevice(newDevice);

                    if (insertedDevice.getId() > 0) {
                        Map<String, Object> item = new HashMap<>();
                        item.put("device_name", newDevice.getDevicename());
                        item.put("serial_number", newDevice.getSerial_number());
                        item.put("manufacturer", manufacture);
                        item.put("model", Lib.isBlank((String) inverter.get("model")) ? "" : (String) inverter.get("model"));
                        item.put("data_table_name", insertedDevice.getDatatablename());
                        result.add(item);
                    }

                    existedDevices.put(sn, insertedDevice);
                }
            }

            if (meters != null) {
                String manufacture = "";
                for (Map<String, Object> meter : meters) {
                    String tableName = "";

                    if (deviceGroupList != null && !deviceGroupList.isEmpty()) {
                        DeviceGroupEntity entity = deviceGroupList.stream().filter(item -> item.getId() == 180).findFirst().orElse(null);
                        if (entity != null) {
                            tableName = entity.getTable_name();
                            manufacture = entity.getManufacture();
                        }
                    }

                    String sn = (String) meter.get("SN");

                    DeviceEntity existedDevice = existedDevices.get(sn);

                    if (existedDevice != null) {
                        if (existedDevice.getIs_delete() == 1) {
                            Map<String, Object> updateItem = new HashMap<>();
                            updateItem.put("id", existedDevice.getId());
                            update("SolarEdge.updateDevice", updateItem);
                        }
                        Map<String, Object> item = new HashMap<>();
                        item.put("device_name", existedDevice.getDevicename());
                        item.put("serial_number", existedDevice.getSerial_number());
                        item.put("manufacturer", manufacture);
                        item.put("model", Lib.isBlank((String) meter.get("model")) ? "" : (String) meter.get("model"));
                        item.put("data_table_name", existedDevice.getDatatablename());
                        result.add(item);
                        continue;
                    }

                    DeviceEntity newDevice = new DeviceEntity();

                    newDevice.setId_site(obj.getId());
                    newDevice.setId_vendor(1);
                    newDevice.setId_device_type(3);
                    newDevice.setId_device_group(180);
                    newDevice.setSerial_number(solarEdgeId.toString());
                    newDevice.setModbusdevicenumber(sn);
                    newDevice.setDevicename((String) meter.get("name"));
                    newDevice.setCreate_total_device(1);
                    newDevice.setDatatablename("model_SolarEdge_API_meter");
                    newDevice.setCommunication_method(3);
                    if (!Lib.isBlank(tableName)) {
                        newDevice.setDevice_group_table(tableName);
                    }

                    DeviceEntity insertedDevice = deviceService.insertDevice(newDevice);
                    if (insertedDevice.getId() > 0) {
                        Map<String, Object> item = new HashMap<>();
                        item.put("device_name", newDevice.getDevicename());
                        item.put("serial_number", newDevice.getSerial_number());
                        item.put("manufacturer", manufacture);
                        item.put("model", Lib.isBlank((String) meter.get("model")) ? "" : (String) meter.get("model"));
                        item.put("data_table_name", insertedDevice.getDatatablename());
                        result.add(item);
                    }

                    existedDevices.put(sn, insertedDevice);
                }
            }

            if (sensors != null) {
                int index = 1;
                String manufacture = "";
                for (Map<String, Object> sensor : sensors) {
                    String tableName = "";

                    if (deviceGroupList != null && !deviceGroupList.isEmpty()) {
                        DeviceGroupEntity entity = deviceGroupList.stream().filter(item -> item.getId() == 181).findFirst().orElse(null);
                        if (entity != null) {
                            tableName = entity.getTable_name();
                            manufacture = entity.getManufacture();
                        }
                    }

                    String id = (String) sensor.get("id");

                    DeviceEntity existedDevice = existedDevices.get(id);

                    if (existedDevice != null) {
                        if (existedDevice.getIs_delete() == 1) {
                            Map<String, Object> updateItem = new HashMap<>();
                            updateItem.put("id", existedDevice.getId());
                            update("SolarEdge.updateDevice", updateItem);
                        }
                        Map<String, Object> item = new HashMap<>();
                        item.put("device_name", existedDevice.getDevicename());
                        item.put("serial_number", existedDevice.getSerial_number());
                        item.put("manufacturer", manufacture);
                        item.put("model", Lib.isBlank((String) sensor.get("model")) ? "" : (String) sensor.get("model"));
                        item.put("data_table_name", existedDevice.getDatatablename());
                        result.add(item);
                        continue;
                    }

                    DeviceEntity newDevice = new DeviceEntity();

                    newDevice.setId_site(obj.getId());
                    newDevice.setId_vendor(1);
                    newDevice.setId_device_type(4);
                    newDevice.setId_device_group(181);
                    newDevice.setSerial_number(solarEdgeId.toString());
                    newDevice.setModbusdevicenumber((String) sensor.get("id"));
                    newDevice.setDevicename("Sensor " + index);
                    newDevice.setCreate_total_device(1);
                    newDevice.setDatatablename("model_SolarEdge_API_weather");
                    newDevice.setCommunication_method(3);
                    if (!Lib.isBlank(tableName)) {
                        newDevice.setDevice_group_table(tableName);
                    }

                    DeviceEntity insertedDevice = deviceService.insertDevice(newDevice);
                    if (insertedDevice.getId() > 0) {
                        Map<String, Object> item = new HashMap<>();
                        item.put("device_name", newDevice.getDevicename());
                        item.put("serial_number", newDevice.getSerial_number());
                        item.put("manufacturer", manufacture);
                        item.put("model", Lib.isBlank((String) sensor.get("model")) ? "" : (String) sensor.get("model"));
                        item.put("data_table_name", insertedDevice.getDatatablename());
                        result.add(item);
                    }

                    existedDevices.put(id, insertedDevice);

                    index++;
                }
            }

            return result;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            this.log.error("SolarEdgeService.syncInventory", e);
        }
        return null;
    }

    /**
     * @description get solar edge device data and save to nw db
     * @author quan.nguyen
     * @since 2026-05-18
     * @params Map<String, Object>
     * @params String start_time, String end_time, int nw site id
     */
    public boolean fillBackData(Map<String, Object> obj) {
        try {
            String startTime = (String) obj.get("start_time");
            String endTime = (String) obj.get("end_time");
            if (Lib.isBlank(startTime) || Lib.isBlank(endTime)) {
                return false;
            }
            SiteEntity siteEntity = new SiteEntity();
            siteEntity.setId((int) obj.get("id"));
            Map<String, Object> info = getSolarEdgeInfo(siteEntity);
            if (info == null) {
                return false;
            }
            Long solarEdgeId = (Long) info.get("solar_edge_id");
            String solarEdgeApiKey = (String) info.get("solar_edge_api_key");
            if (Lib.isBlank(solarEdgeApiKey) || solarEdgeId == null || solarEdgeId == 0) {
                return false;
            }
            String timeZone = (String) info.get("time_zone_value");
//            String startUtc = startTime;
//            String endUtc = endTime;
//            if (!Lib.isBlank(timeZone)) {
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                ZoneId sourceZone = ZoneId.of(timeZone);
//                LocalDateTime startLocal = LocalDateTime.parse(startTime, formatter);
//                LocalDateTime endLocal = LocalDateTime.parse(endTime, formatter);
//                startUtc = startLocal.atZone(sourceZone).withZoneSameInstant(ZoneId.of("UTC")).format(formatter);
//                endUtc = endLocal.atZone(sourceZone).withZoneSameInstant(ZoneId.of("UTC")).format(formatter);
//            }
            obj.put("solar_edge_get_working_device", true);

            List<DeviceEntity> deviceList = (List<DeviceEntity>) queryForList("SolarEdge.getSolarEdgeDeviceBySite", obj);
            Map<String, Object> params = new HashMap<>();
            params.put("solar_edge_id", solarEdgeId);
            params.put("solar_edge_api_key", solarEdgeApiKey);
            params.put("start_time", startTime);
            params.put("end_time", endTime);
            params.put("time_zone", timeZone);
            for (DeviceEntity device : deviceList) {
                params.put("device_serial_no", device.getModbusdevicenumber());
                if (device.getId_device_type() == DeviceType.INVERTER.getType()) {
                    fillDataInverter(params, device);
                    continue;
                }
                if (device.getId_device_type() == DeviceType.SENSOR.getType()) {
                    fillDataSensor(params, device);
                    continue;
                }
                fillDataMeter(params, device);
            }

            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            this.log.error("SolarEdgeService.fillBackData", e);

        }
        return false;
    }

    /**
     * @description get solar edge API url by id device type
     * @author quan.nguyen
     * @since 2026-05-18
     * @params Map<String, Object>
     * @params String start_time, String end_time, String device_serial_no, String solar_edge_api_key,int nw solar_edge_id
     */
    private String getUrl(Map<String, Object> obj, int deviceType) {
        try {
            StringBuilder url = new StringBuilder();
            url.append(DOMAIN_URL);
            if (deviceType == DeviceType.INVERTER.getType()) {
                url.append("/equipment");
                url.append("/").append(obj.get("solar_edge_id"));
                url.append("/").append(obj.get("device_serial_no"));
                url.append("/data?api_key=").append(obj.get("solar_edge_api_key"));
                url.append("&startTime=").append(obj.get("start_time"));
                url.append("&endTime=").append(obj.get("end_time"));
                return url.toString();
            }
            if (deviceType == DeviceType.SENSOR.getType() || deviceType == DeviceType.METER.getType()) {
                url.append("/site");
                url.append("/").append(obj.get("solar_edge_id"));
                url.append(deviceType == DeviceType.SENSOR.getType() ? "/sensors" : "/meters");
                url.append("?api_key=").append(obj.get("solar_edge_api_key"));
                if (deviceType == DeviceType.SENSOR.getType()) {
                    url.append("&startDate=").append(obj.get("start_time"));
                    url.append("&endDate=").append(obj.get("end_time"));
                } else {
                    url.append("&startTime=").append(obj.get("start_time"));
                    url.append("&endTime=").append(obj.get("end_time"));
                    url.append("&timeUnit=QUARTER_OF_AN_HOUR");
                }
                return url.toString();
            }
        } catch (Exception e) {
            this.log.error("SolarEdgeService.getUrl", e);
        }
        return null;
    }

    /**
     * @description save data of meter to db
     * @author quan.nguyen
     * @since 2026-05-20
     * @params DeviceEntity
     * @params String start_time, String end_time, String device_serial_no, String solar_edge_api_key,int nw solar_edge_id
     */
    private void fillDataMeter(Map<String, Object> obj, DeviceEntity device) {
        try {
            if (device == null) {
                return;
            }
            String url = getUrl(obj, device.getId_device_type());
            if (Lib.isBlank(url)) {
                return;
            }

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");

            String response = restApiService.callApi(url, HttpMethod.GET, headers,null);

            Map<String, Object> res = mapper.readValue(response, new TypeReference<Map<String, Object>>() {});
            if (res == null) {
                return;
            }
            Map<String, Object> meterEnergyDetails = (Map<String, Object>) res.get("meterEnergyDetails");
            if (meterEnergyDetails == null) {
                return;
            }
            List<Map<String, Object>> meters = (List<Map<String, Object>>) meterEnergyDetails.get("meters");
            if (meters == null || meters.isEmpty()) {
                return;
            }
            Map<String, Object> meter = meters.stream()
                    .filter(item -> device.getModbusdevicenumber().equalsIgnoreCase((String) item.get("meterSerialNumber")))
                    .findFirst().orElse(null);
            if (meter == null) {
                return;
            }
            String meterType = (String) meter.get("meterType");

            StringBuilder energyUrl = new StringBuilder();
            energyUrl.append(DOMAIN_URL);
            energyUrl.append("/site");
            energyUrl.append("/").append(obj.get("solar_edge_id"));
            energyUrl.append("/energyDetails");
            energyUrl.append("?api_key=").append(obj.get("solar_edge_api_key"));
            energyUrl.append("&startTime=").append(obj.get("start_time"));
            energyUrl.append("&endTime=").append(obj.get("end_time"));
            energyUrl.append("&meters=").append(!Lib.isBlank(meterType) ? meterType.toUpperCase() : "");
            energyUrl.append("&timeUnit=QUARTER_OF_AN_HOUR");

            String powerUrl = energyUrl.toString().replace("energyDetails", "powerDetails");

            Map<String, Object> energyData = getMeterData(energyUrl.toString(), !Lib.isBlank(meterType) ? meterType.toUpperCase() : "", "energyDetails");
            Map<String, Object> powerData = getMeterData(powerUrl, !Lib.isBlank(meterType) ? meterType.toUpperCase() : "", "powerDetails");
            List<ModelSolarEdgeAPIMeterEntity> meterDataList = new ArrayList<>();
            if (energyData != null) {
                String energyUnit = (String) energyData.get("unit");
                if (Lib.isBlank(energyUnit)) {
                    energyUnit = "Wh";
                }
                List<Map<String, Object>> energyDatalist = (List<Map<String, Object>>) energyData.get("values");
                String timeZone = (String) obj.get("time_zone");
                for(int i = 0; i < energyDatalist.size(); i++) {
                    Map<String, Object> item = energyDatalist.get(i);
                    String energyTime = convertTimeToUTC(timeZone, (String) item.get("date"));
                    if (Lib.isBlank(energyTime)) {
                        continue;
                    }
                    ModelSolarEdgeAPIMeterEntity entity = new ModelSolarEdgeAPIMeterEntity();
                    entity.setTime(energyTime);
                    entity.setId_device(device.getId());
                    Double totalEnergy = (Double) item.get("value");
                    if (totalEnergy == null) {
                        totalEnergy = Double.parseDouble("0");
                    }
                    if (energyUnit.equalsIgnoreCase("Wh") && totalEnergy != 0) {
                        totalEnergy /= 1000;
                    }
                    entity.setTotalEnergy(totalEnergy);
                    entity.setNvmActiveEnergy(totalEnergy);
                    double measure = 0;
                    if (i < energyDatalist.size() - 1) {
                        Map<String, Object> nextItem = energyDatalist.get(i + 1);
                        Double nextEnergy = (Double) nextItem.get("value");
                        if (nextEnergy != null) {
                            if (energyUnit.equalsIgnoreCase("Wh") && nextEnergy != 0) {
                                nextEnergy /=  1000;
                            }
                            measure =  nextEnergy - totalEnergy;
                        }
                    }
                    entity.setMeasuredProduction(measure > 0 ? measure : 0);
                    if (powerData != null) {
                        String powerUnit = (String) powerData.get("unit");
                        if (Lib.isBlank(powerUnit)) {
                            powerUnit = "W";
                        }
                        List<Map<String, Object>> powerDatalist = (List<Map<String, Object>>) powerData.get("values");
                        Map<String, Object> powerItem = powerDatalist.stream()
                                .filter(power -> energyTime.equals(convertTimeToUTC(timeZone, (String) power.get("date"))))
                                .findFirst().orElse(null);
                        if (powerItem != null) {
                            Double totalPower = (Double) powerItem.get("value");
                            if (totalPower == null) {
                                totalPower = Double.parseDouble("0");
                            }
                            if (powerUnit.equalsIgnoreCase("W") && totalPower != 0) {
                                totalPower /= 1000;
                            }

                            entity.setActivePower(totalPower);
                            entity.setNvmActivePower(totalPower);
                        }
                    }
                    meterDataList.add(entity);
                }
            }
            if (meterDataList.isEmpty()) {
                return;
            }
            Map<String, Object> param = new HashMap<>();
            param.put("datatablename", device.getDatatablename());
            param.put("list", meterDataList);
            insert("ModelSolarEdgeAPIMeter.batchInsertModelSolarEdgeAPIMeter", param);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            this.log.error("SolarEdgeService.fillDataMeter", e);
        }
    }

    /**
     * @description call solar edge api to get data of power & energy of meter device
     * @author quan.nguyen
     * @since 2026-05-20
     * @params DeviceEntity
     * @params String url, String meterType, String key
     */
    private Map<String, Object> getMeterData(String url, String meterType, String key) {
        try {
            if (Lib.isBlank(url)) {
                return null;
            }
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            String response = restApiService.callApi(url, HttpMethod.GET, headers,null);

            Map<String, Object> res = mapper.readValue(response, new TypeReference<Map<String, Object>>() {});
            if (res == null) {
                return null;
            }
            Map<String, Object> data = (Map<String, Object>) res.get(key);
            if (data == null) {
                return null;
            }
            String unit = (String) data.get("unit");
            List<Map<String, Object>> meters = (List<Map<String, Object>>) data.get("meters");
            if (meters == null || meters.isEmpty()) {
                return null;
            }
            Map<String, Object> meter = meters.stream()
                    .filter(item -> meterType.equalsIgnoreCase((String) item.get("type")))
                    .findFirst()
                    .orElse(null);
            if (meter == null) {
                return null;
            }
            List<Map<String, Object>> values = (List<Map<String, Object>>) meter.get("values");
            if (values == null || values.isEmpty()) {
                return null;
            }
            Map<String, Object> meterData = new HashMap<>();
            meterData.put("unit", unit);
            meterData.put("values", values);
            return meterData;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            log.error("SolarEdgeService.getMeterData", e);
        }
        return null;
    }

    /**
     * @description save data of sensor to db
     * @author quan.nguyen
     * @since 2026-05-18
     * @params DeviceEntity
     * @params String start_time, String end_time, String device_serial_no, String solar_edge_api_key,int nw solar_edge_id
     */
    private void fillDataSensor(Map<String, Object> obj, DeviceEntity device) {
        try {
            if (device == null) {
                return;
            }
            String url = getUrl(obj, device.getId_device_type());
            if (Lib.isBlank(url)) {
                return;
            }

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");

            String response = restApiService.callApi(url, HttpMethod.GET, headers,null);

            Map<String, Object> res = mapper.readValue(response, new TypeReference<Map<String, Object>>() {});
            if (res == null) {
                return;
            }
            Map<String, Object> siteSensors = (Map<String, Object>) res.get("siteSensors");
            if (siteSensors == null) {
                return;
            }
            List<Map<String, Object>> listData = (List<Map<String, Object>>) siteSensors.get("data");
            if (listData == null || listData.isEmpty()) {
                return;
            }
            List<ModelSolarEdgeAPIWeatherEntity> dataList = new ArrayList<>();
            for (Map<String, Object> data : listData) {
                List<Map<String, Object>> telemetries = (List<Map<String, Object>>) data.get("telemetries");

                if (telemetries == null || telemetries.isEmpty()) {
                    continue;
                }
                String timeZone = (String) obj.get("time_zone");
                for (Map<String, Object> item : telemetries) {
                    ModelSolarEdgeAPIWeatherEntity entity = new ModelSolarEdgeAPIWeatherEntity();
                    String date = convertTimeToUTC(timeZone, (String) item.get("date"));
                    if (Lib.isBlank(date)) {
                        continue;
                    }

                    entity.setTime(date);
                    entity.setId_device(device.getId());
                    if (Lib.isBlank(entity.getTime())) {
                        continue;
                    }
                    entity.setAmbientTemperature(
                            item.get("ambientTemperature") != null
                                    ? ((Number) item.get("ambientTemperature")).doubleValue()
                                    : 0D
                    );

                    entity.setPlaneOfArrayIrradiance(
                            item.get("planeOfArrayIrradiance") != null
                                    ? ((Number) item.get("planeOfArrayIrradiance")).doubleValue()
                                    : 0D
                    );

                    entity.setNvm_temperature(
                            item.get("ambientTemperature") != null
                                    ? ((Number) item.get("ambientTemperature")).doubleValue()
                                    : 0D
                    );

                    entity.setNvm_irradiance(
                            item.get("planeOfArrayIrradiance") != null
                                    ? ((Number) item.get("planeOfArrayIrradiance")).doubleValue()
                                    : 0D
                    );

                    entity.setNvm_panel_temperature(entity.getNvm_temperature());
                    dataList.add(entity);
                }
            }

            if (!dataList.isEmpty()) {
                Map<String, Object> param = new HashMap<>();
                param.put("datatablename", device.getDatatablename());
                param.put("list", dataList);
                insert("ModelSolarEdgeAPIWeather.batchInsertModelSolarEdgeAPIWeather", param);
            }

        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            this.log.error("SolarEdgeService.fillDataInverter", e);
        }
    }

    /**
     * @description save data of inverter to db
     * @author quan.nguyen
     * @since 2026-05-18
     * @params DeviceEntity
     * @params String start_time, String end_time, String device_serial_no, String solar_edge_api_key,int nw solar_edge_id
     */
    private void fillDataInverter(Map<String, Object> obj, DeviceEntity device) {
        try {
            if (device == null) {
                return;
            }
            String url = getUrl(obj, device.getId_device_type());
            if (Lib.isBlank(url)) {
                return;
            }

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");

            String response = restApiService.callApi(url, HttpMethod.GET, headers,null);

            Map<String, Object> res = mapper.readValue(response, new TypeReference<Map<String, Object>>() {});
            if (res == null) {
                return;
            }
            Map<String, Object> data = (Map<String, Object>) res.get("data");
            if (data == null) {
                return;
            }
            List<Map<String, Object>> telemetries = (List<Map<String, Object>>) data.get("telemetries");

            if (telemetries == null || telemetries.isEmpty()) {
                return;
            }
            List<ModelSolarEdgeAPIInverterEntity> dataList = new ArrayList<>();
            String timeZone = (String) obj.get("time_zone");
            for (int i = 0; i < telemetries.size(); i++) {
                Map<String, Object> item = telemetries.get(i);
                Map<String, Object> L1Data = (Map<String, Object>) item.get("L1Data");
                Map<String, Object> L2Data = (Map<String, Object>) item.get("L2Data");
                Map<String, Object> L3Data = (Map<String, Object>) item.get("L3Data");

                ModelSolarEdgeAPIInverterEntity entity = new ModelSolarEdgeAPIInverterEntity();
                String date = convertTimeToUTC(timeZone, (String) item.get("date"));
                if (Lib.isBlank(date)) {
                    continue;
                }
                entity.setTime(date);
                entity.setId_device(device.getId());
                double currentEnergy = item.get("totalEnergy") != null ? ((Number) item.get("totalEnergy")).doubleValue() / 1000 : 0D;

                double measure = 0D;

                if (i < telemetries.size() - 1) {
                    Map<String, Object> nextItem = telemetries.get(i + 1);
                    double nextEnergy = nextItem.get("totalEnergy") != null ? ((Number) nextItem.get("totalEnergy")).doubleValue() / 1000 : 0D;
                    measure = nextEnergy - currentEnergy;
                }

                entity.setTotalActivePower(
                        item.get("totalActivePower") != null
                                ? ((Number) item.get("totalActivePower")).doubleValue() / 1000
                                : 0D
                );

                entity.setDcVoltage(
                        item.get("dcVoltage") != null
                                ? ((Number) item.get("dcVoltage")).doubleValue()
                                : 0D
                );

                entity.setPowerLimit(
                        item.get("powerLimit") != null
                                ? ((Number) item.get("powerLimit")).doubleValue()
                                : 0D
                );

                entity.setTotalEnergy(currentEnergy);

                entity.setTemperature(
                        item.get("temperature") != null
                                ? ((Number) item.get("temperature")).doubleValue()
                                : 0D
                );

                entity.setInverterMode(
                        item.get("inverterMode") != null
                                ? item.get("inverterMode").toString()
                                : null
                );

                entity.setOperationMode(
                        item.get("operationMode") != null
                                ? Double.parseDouble(item.get("operationMode").toString())
                                : 0D
                );

                entity.setNvmActiveEnergy(currentEnergy);

                entity.setNvmActivePower(
                        item.get("totalActivePower") != null
                                ? ((Number) item.get("totalActivePower")).doubleValue()
                                : 0D
                );

                entity.setvL1To2(
                        item.get("vL1To2") != null
                                ? ((Number) item.get("vL1To2")).doubleValue()
                                : 0D
                );

                entity.setvL2To3(
                        item.get("vL2To3") != null
                                ? ((Number) item.get("vL2To3")).doubleValue()
                                : 0D
                );

                entity.setvL3To1(
                        item.get("vL3To1") != null
                                ? ((Number) item.get("vL3To1")).doubleValue()
                                : 0D
                );

                entity.setMeasuredProduction(measure > 0 ? measure : 0D);

                if (L1Data != null) {

                    entity.setL1_acCurrent(
                            L1Data.get("acCurrent") != null
                                    ? ((Number) L1Data.get("acCurrent")).doubleValue()
                                    : 0D
                    );

                    entity.setL1_acVoltage(
                            L1Data.get("acVoltage") != null
                                    ? ((Number) L1Data.get("acVoltage")).doubleValue()
                                    : 0D
                    );

                    entity.setL1_acFrequency(
                            L1Data.get("acFrequency") != null
                                    ? ((Number) L1Data.get("acFrequency")).doubleValue()
                                    : 0D
                    );

                    entity.setL1_apparentPower(
                            L1Data.get("apparentPower") != null
                                    ? ((Number) L1Data.get("apparentPower")).doubleValue() / 1000
                                    : 0D
                    );

                    entity.setL1_activePower(
                            L1Data.get("activePower") != null
                                    ? ((Number) L1Data.get("activePower")).doubleValue() / 1000
                                    : 0D
                    );

                    entity.setL1_reactivePower(
                            L1Data.get("reactivePower") != null
                                    ? ((Number) L1Data.get("reactivePower")).doubleValue() / 1000
                                    : 0D
                    );
                }

                if (L2Data != null) {

                    entity.setL2_acCurrent(
                            L2Data.get("acCurrent") != null
                                    ? ((Number) L2Data.get("acCurrent")).doubleValue()
                                    : 0D
                    );

                    entity.setL2_acVoltage(
                            L2Data.get("acVoltage") != null
                                    ? ((Number) L2Data.get("acVoltage")).doubleValue()
                                    : 0D
                    );

                    entity.setL2_acFrequency(
                            L2Data.get("acFrequency") != null
                                    ? ((Number) L2Data.get("acFrequency")).doubleValue()
                                    : 0D
                    );

                    entity.setL2_apparentPower(
                            L2Data.get("apparentPower") != null
                                    ? ((Number) L2Data.get("apparentPower")).doubleValue() / 1000
                                    : 0D
                    );

                    entity.setL2_activePower(
                            L2Data.get("activePower") != null
                                    ? ((Number) L2Data.get("activePower")).doubleValue() / 1000
                                    : 0D
                    );

                    entity.setL2_reactivePower(
                            L2Data.get("reactivePower") != null
                                    ? ((Number) L2Data.get("reactivePower")).doubleValue() / 1000
                                    : 0D
                    );
                }

                if (L3Data != null) {

                    entity.setL3_acCurrent(
                            L3Data.get("acCurrent") != null
                                    ? ((Number) L3Data.get("acCurrent")).doubleValue()
                                    : 0D
                    );

                    entity.setL3_acVoltage(
                            L3Data.get("acVoltage") != null
                                    ? ((Number) L3Data.get("acVoltage")).doubleValue()
                                    : 0D
                    );

                    entity.setL3_acFrequency(
                            L3Data.get("acFrequency") != null
                                    ? ((Number) L3Data.get("acFrequency")).doubleValue()
                                    : 0D
                    );

                    entity.setL3_apparentPower(
                            L3Data.get("apparentPower") != null
                                    ? ((Number) L3Data.get("apparentPower")).doubleValue() / 1000
                                    : 0D
                    );

                    entity.setL3_activePower(
                            L3Data.get("activePower") != null
                                    ? ((Number) L3Data.get("activePower")).doubleValue() / 1000
                                    : 0D
                    );

                    entity.setL3_reactivePower(
                            L3Data.get("reactivePower") != null
                                    ? ((Number) L3Data.get("reactivePower")).doubleValue() / 1000
                                    : 0D
                    );
                }
                dataList.add(entity);
            }
            if (!dataList.isEmpty()) {
                Map<String, Object> param = new HashMap<>();
                param.put("datatablename", device.getDatatablename());
                param.put("list", dataList);
                insert("ModelSolarEdgeAPIInverter.batchInsertModelSolarEdgeAPIInverter", param);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            this.log.error("SolarEdgeService.fillDataInverter", e);
        }
    }

    private String convertTimeToUTC(String timeZone, String localTime) {
        if (Lib.isBlank(timeZone) || Lib.isBlank(localTime)) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZoneId sourceZone = ZoneId.of(timeZone);
        LocalDateTime dateLocal = LocalDateTime.parse(localTime, formatter);
        return dateLocal.atZone(sourceZone).withZoneSameInstant(ZoneId.of("UTC")).format(formatter);
    }
}
