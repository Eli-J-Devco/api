package com.nwm.api.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DeviceGroupEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.Lib;
import org.springframework.http.HttpMethod;

import java.util.*;

public class SolarEdgeService extends DB  {

    private final RestApiService restApiService = new RestApiService();
    private final ObjectMapper mapper = new ObjectMapper();

    private final DeviceService  deviceService = new DeviceService();

    private final String DOMAIN_URL = "https://monitoringapi.solaredge.com";

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

            List<DeviceEntity> deviceList = (List<DeviceEntity>) queryForList("SolarEdge.getSolarEdgeDeviceBySide", obj);
            List<DeviceGroupEntity> deviceGroupList = (List<DeviceGroupEntity>) queryForList("SolarEdge.getDeviceGroupModelName");

            Set<String> existedKeys = new HashSet<>();
            List<Map<String, Object>> result = new ArrayList<>();

            if (deviceList != null) {
                for (DeviceEntity device : deviceList) {
                    if (device.getModbusdevicenumber() != null) {
                        existedKeys.add(device.getModbusdevicenumber());
                    }
                }
            }

            if (inverters != null) {
                String manufacture = "";
                for (Map<String, Object> inverter : inverters) {

                    String sn = (String) inverter.get("SN");

                    if (sn == null || existedKeys.contains(sn)) {
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
                    newDevice.setDevice_group_table("model_SolarEdge_API_inverter");
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

                    existedKeys.add(sn);
                }
            }

            if (meters != null) {
                String manufacture = "";
                for (Map<String, Object> meter : meters) {

                    String sn = (String) meter.get("SN");

                    if (sn == null || existedKeys.contains(sn)) {
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
                    newDevice.setDevice_group_table("model_SolarEdge_API_meter");
                    if (deviceGroupList != null && !deviceGroupList.isEmpty()) {
                        DeviceGroupEntity entity = deviceGroupList.stream().filter(item -> item.getId() == 180).findFirst().orElse(null);
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
                        item.put("model", Lib.isBlank((String) meter.get("model")) ? "" : (String) meter.get("model"));
                        item.put("data_table_name", insertedDevice.getDatatablename());
                        result.add(item);
                    }

                    existedKeys.add(sn);
                }
            }

            if (sensors != null) {
                int index = 1;
                String manufacture = "";
                for (Map<String, Object> sensor : sensors) {

                    String id = (String) sensor.get("id");

                    if (id == null || existedKeys.contains(id)) {
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
                    newDevice.setDevice_group_table("model_SolarEdge_API_weather");
                    if (deviceGroupList != null && !deviceGroupList.isEmpty()) {
                        DeviceGroupEntity entity = deviceGroupList.stream().filter(item -> item.getId() == 181).findFirst().orElse(null);
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
                        item.put("model", Lib.isBlank((String) sensor.get("model")) ? "" : (String) sensor.get("model"));
                        item.put("data_table_name", insertedDevice.getDatatablename());
                        result.add(item);
                    }

                    existedKeys.add(id);

                    index++;
                }
            }

            return result;
        } catch (Exception e) {
            this.log.error("SolarEdgeService.syncInventory", e);
        }
        return null;
    }


}
