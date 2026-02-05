package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ApiAccessEntity;
import com.nwm.api.entities.CompanyEntity;
import com.nwm.api.entities.EmployeeEntity;
import com.nwm.api.entities.EmployeeManageEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiAccessService extends DB {

    public List getApiAccessConfig(Map<String, Object> params) {
        try {
            List dataList = queryForList("AccountStatus.getListByEmployee", params);
            return dataList == null ? new ArrayList<>() : dataList;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public List getListUser() {
        try {
            EmployeeService service = new EmployeeService();
            List data = service.getAll(new EmployeeEntity());
            List<Map<String, Object>> dataList = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                EmployeeManageEntity item = (EmployeeManageEntity) data.get(i);
                Map<String, Object> obj = new HashMap<>();
                obj.put("id", item.getId());
                obj.put("fullname", item.getFullname());
                obj.put("text", item.getText());
                obj.put("label", item.getLabel());
                obj.put("value", item.getId());
                dataList.add(obj);
            }
            return dataList;
        } catch (Exception ex) {
            return new ArrayList<>();
        }

    }

    public List getListCompany() {
        try {
            CompanyService service = new CompanyService();
            List data = service.getDropdownList(new CompanyEntity());
            List<Map<String, Object>> dataList = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                CompanyEntity item = (CompanyEntity) data.get(i);
                Map<String, Object> obj = new HashMap<>();
                obj.put("id", item.getId());
                obj.put("text", item.getText());
                obj.put("label", item.getText());
                obj.put("value", item.getId());
                dataList.add(obj);
            }
            return dataList;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public List getListSite(Map<String, Object> params) {
        try {
            List<Integer> employeeList = (List<Integer>) params.get("employee_ids");
            if (employeeList == null || employeeList.size() == 0) {
                return new ArrayList();
            }
            List data = queryForList("ApiAccess.getListSite", params);
            if (data == null) {
                return new ArrayList();
            }
            return data;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public boolean saveConfig(ApiAccessEntity obj) {
        try {
            List<ApiAccessEntity> dataList = queryForList("ApiAccess.checkSiteBelongToUser", obj);
            if (dataList == null || dataList.size() == 0) {
                return false;
            }
            Map<String, Object> params = new HashMap<>();
            params.put("dataList", dataList);
            insert("ApiAccess.saveConfig", params);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
