package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ApiAccessEntity;
import com.nwm.api.entities.CompanyEntity;
import com.nwm.api.entities.EmployeeEntity;
import com.nwm.api.entities.EmployeeManageEntity;
import com.nwm.api.utils.Lib;
import org.apache.ibatis.session.SqlSession;

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

    public List getListUser(Map<String, Object> params) {
        try {
            EmployeeService service = new EmployeeService();
            EmployeeEntity entity = new EmployeeEntity();
            if (!Lib.isBlank(params.get("keyword"))) {
                String keyword = (String) params.get("keyword");
                entity.setKeyword(keyword);
            }
            List data = service.getAll(entity);
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

    public List getListCompany(Map<String, Object> params) {
        try {
            CompanyService service = new CompanyService();
            CompanyEntity entity = new CompanyEntity();
            String keyword = (String) params.get("keyword");
            if (!Lib.isBlank(keyword)) {
                entity.setKeyword(keyword);
            }
            List data = service.getDropdownList(entity);
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
            Integer employeeId = (Integer) params.get("employee_id");
            if (employeeId == null) {
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

    public boolean saveConfig(Map<String, Object> obj) {
        SqlSession session = this.beginTransaction();
        try {
            List<Map<String, Object>> company = (List<Map<String, Object>>) obj.get("company");
            List<Map<String, Object>> site = (List<Map<String, Object>>) obj.get("site");
            if (company == null) {
                company = new ArrayList<>();
            }
            if (site == null) {
                site = new ArrayList<>();
            }
            ApiAccessEntity entity = (ApiAccessEntity) queryForObject("ApiAccess.checkUserHaveConfig", obj);
            if (entity == null) {
                if (company.isEmpty() && site.isEmpty()) {
                    session.rollback();
                    return false;
                }
                // create
                int row = session.insert("ApiAccess.saveConfig", obj);
                if (row == 0) {
                    session.rollback();
                    return false;
                }
                row = session.insert("ApiAccess.saveCompanyConfig", obj);
                if (row == 0) {
                    session.rollback();
                    return false;
                }
                row = session.insert("ApiAccess.saveSiteConfig", obj);
                if (row == 0) {
                    session.rollback();
                    return false;
                }
            } else {
                obj.put("id_api_access", entity.getId());
                obj.put("id", entity.getId());
                obj.put("table", "api_access_site_map");
                session.delete("ApiAccess.deleteConfig", obj);
                obj.replace("table", "api_access_site_map", "api_access_company_map");
                session.delete("ApiAccess.deleteConfig", obj);
                if (company.isEmpty() && site.isEmpty()) {
                    obj.put("security_key", null);
                    obj.put("status", 0);
                    session.update("ApiAccess.updateConfig", obj);
                    session.commit();
                    return true;
                }
                if (!site.isEmpty()) {
                    int row = session.insert("ApiAccess.saveSiteConfig", obj);
                    if (row == 0) {
                        session.rollback();
                        return false;
                    }
                }
                if (!company.isEmpty()) {
                    int row = session.insert("ApiAccess.saveCompanyConfig", obj);
                    if (row == 0) {
                        session.rollback();
                        return false;
                    }
                }
                obj.put("security_key", entity.getSecurity_key());
                obj.put("status", entity.getStatus());
                session.update("ApiAccess.updateConfig", obj);
            }
            session.commit();
            return true;
        } catch (Exception ex) {
            session.rollback();
            return false;
        }
    }

    public ApiAccessEntity checkApiAccessConfig(Map<String, Object> obj) {
        try {
            obj.put("checkStatus", 1);
            ApiAccessEntity entity = (ApiAccessEntity) queryForObject("ApiAccess.checkUserHaveConfig", obj);
            return entity;
        } catch (Exception ex) {
            return null;
        }
    }
}
