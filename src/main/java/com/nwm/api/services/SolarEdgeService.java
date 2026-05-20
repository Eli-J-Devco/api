package com.nwm.api.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.*;
import com.nwm.api.utils.Lib;
import org.springframework.http.HttpMethod;

import java.time.LocalDate;
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
                    newDevice.setDatatablename("");
                    if (tableName != null) {
                        newDevice.setDevice_group_table(tableName);
                    } else {
                        newDevice.setDevice_group_table("model_SolarEdge_API_inverter");
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
                    newDevice.setDatatablename("");
                    if (tableName != null) {
                        newDevice.setDevice_group_table(tableName);
                    } else {
                        newDevice.setDevice_group_table("model_SolarEdge_API_meter");
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
                    newDevice.setDatatablename("");
                    if (tableName != null) {
                        newDevice.setDevice_group_table(tableName);
                    } else {
                        newDevice.setDevice_group_table("model_SolarEdge_API_weather");
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
            LocalDate startDate = LocalDate.parse(startTime);
            LocalDate endDate = LocalDate.parse(endTime);
            startTime = startDate.atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            endTime = endDate.atTime(23, 59, 59).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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
            List<DeviceEntity> deviceList = (List<DeviceEntity>) queryForList("SolarEdge.getSolarEdgeDeviceBySite", obj);
            Map<String, Object> params = new HashMap<>();
            params.put("solar_edge_id", solarEdgeId);
            params.put("solar_edge_api_key", solarEdgeApiKey);
            params.put("start_time", startTime);
            params.put("end_time", endTime);
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
                for(int i = 0; i < energyDatalist.size(); i++) {
                    Map<String, Object> item = energyDatalist.get(i);
                    String energyTime = (String) item.get("date");
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
                                .filter(power -> energyTime.equals((String) power.get("date")))
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
        } catch (Exception e) {
            this.log.error("SolarEdgeService.fillDataInverter", e);
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
                for (Map<String, Object> item : telemetries) {
                    ModelSolarEdgeAPIWeatherEntity entity = new ModelSolarEdgeAPIWeatherEntity();
                    entity.setTime((String) item.get("date"));
                    entity.setId_device(device.getId());
                    if (Lib.isBlank(entity.getTime())) {
                        continue;
                    }
                    entity.setAmbientTemperature((double) item.get("ambientTemperature"));
                    entity.setPlaneOfArrayIrradiance((double) item.get("planeOfArrayIrradiance"));
                    entity.setNvm_temperature((double) item.get("ambientTemperature"));
                    entity.setNvm_irradiance((double) item.get("planeOfArrayIrradiance"));
                    dataList.add(entity);
                }
            }

            if (!dataList.isEmpty()) {
                Map<String, Object> param = new HashMap<>();
                param.put("datatablename", device.getDatatablename());
                param.put("list", dataList);
                insert("ModelSolarEdgeAPIWeather.batchInsertModelSolarEdgeAPIWeather", param);
            }

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
            for (int i = 0; i < telemetries.size(); i++) {
                Map<String, Object> item = telemetries.get(i);
                Map<String, Object> L1Data = (Map<String, Object>) item.get("L1Data");
                Map<String, Object> L2Data = (Map<String, Object>) item.get("L2Data");
                Map<String, Object> L3Data = (Map<String, Object>) item.get("L3Data");

                ModelSolarEdgeAPIInverterEntity entity = new ModelSolarEdgeAPIInverterEntity();
                entity.setTime((String) item.get("date"));
                entity.setId_device(device.getId());
                if (Lib.isBlank(entity.getTime())) {
                    continue;
                }
                double currentEnergy = ((double) item.get("totalEnergy")) / 1000;
                double measure = 0;
                if (i < telemetries.size() - 1) {
                    Map<String, Object> nextItem = telemetries.get(i + 1);
                    double nextEnergy = ((double) nextItem.get("totalEnergy")) / 1000;
                    measure = nextEnergy - currentEnergy;
                }

                entity.setTotalActivePower(((double) item.get("totalActivePower")) / 1000);
                entity.setDcVoltage((double) item.get("dcVoltage"));
                entity.setPowerLimit((double) item.get("powerLimit"));
                entity.setTotalEnergy(currentEnergy);
                entity.setTemperature((double) item.get("temperature"));
                entity.setInverterMode((String) item.get("inverterMode"));
                entity.setOperationMode(Double.parseDouble(item.get("operationMode").toString()));
                entity.setNvmActiveEnergy(currentEnergy);
                entity.setNvmActivePower((double) item.get("totalActivePower"));
                entity.setvL1To2((double) item.get("vL1To2"));
                entity.setvL2To3((double) item.get("vL2To3"));
                entity.setvL3To1((double) item.get("vL3To1"));
                entity.setMeasuredProduction(measure > 0 ? measure : 0);
                if (L1Data != null) {
                    entity.setL1_acCurrent((double) L1Data.get("acCurrent"));
                    entity.setL1_acVoltage((double) L1Data.get("acVoltage"));
                    entity.setL1_acFrequency((double) L1Data.get("acFrequency"));
                    entity.setL1_apparentPower(((double) L1Data.get("apparentPower")) / 1000);
                    entity.setL1_activePower(((double) L1Data.get("activePower")) / 1000);
                    entity.setL1_reactivePower(((double) L1Data.get("reactivePower")) / 1000);
                }
                if (L2Data != null) {
                    entity.setL2_acCurrent((double) L2Data.get("acCurrent"));
                    entity.setL2_acVoltage((double) L2Data.get("acVoltage"));
                    entity.setL2_acFrequency((double) L2Data.get("acFrequency"));
                    entity.setL2_apparentPower(((double) L2Data.get("apparentPower")) / 1000);
                    entity.setL2_activePower(((double) L2Data.get("activePower")) / 1000);
                    entity.setL2_reactivePower(((double) L2Data.get("reactivePower")) / 1000);
                }
                if (L3Data != null) {
                    entity.setL3_acCurrent((double) L3Data.get("acCurrent"));
                    entity.setL3_acVoltage((double) L3Data.get("acVoltage"));
                    entity.setL3_acFrequency((double) L3Data.get("acFrequency"));
                    entity.setL3_apparentPower(((double) L3Data.get("apparentPower")) / 1000);
                    entity.setL3_activePower(((double) L3Data.get("activePower")) / 1000);
                    entity.setL3_reactivePower(((double) L3Data.get("reactivePower")) / 1000);
                }
                dataList.add(entity);
            }
            if (!dataList.isEmpty()) {
                Map<String, Object> param = new HashMap<>();
                param.put("datatablename", device.getDatatablename());
                param.put("list", dataList);
                insert("ModelSolarEdgeAPIInverter.batchInsertModelSolarEdgeAPIInverter", param);
            }
        } catch (Exception e) {
            this.log.error("SolarEdgeService.fillDataInverter", e);
        }
    }
}
