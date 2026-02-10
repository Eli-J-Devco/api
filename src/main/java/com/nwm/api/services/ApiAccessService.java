package com.nwm.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.APIAccessLoggingDTO;
import com.nwm.api.entities.ApiAccessEntity;
import com.nwm.api.entities.CompanyEntity;
import com.nwm.api.entities.EmployeeEntity;
import com.nwm.api.entities.EmployeeManageEntity;
import com.nwm.api.utils.Lib;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiAccessService extends DB {

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
//            CompanyService service = new CompanyService();
//            CompanyEntity entity = new CompanyEntity();
//            String keyword = (String) params.get("keyword");
//            if (!Lib.isBlank(keyword)) {
//                entity.setKeyword(keyword);
//            }
//            List data = service.getDropdownList(entity);
            List data = queryForList("ApiAccess.getListCompany", params);
//            List<Map<String, Object>> dataList = new ArrayList<>();
//            for (int i = 0; i < data.size(); i++) {
//                CompanyEntity item = (CompanyEntity) data.get(i);
//                Map<String, Object> obj = new HashMap<>();
//                obj.put("id", item.getId());
//                obj.put("text", item.getText());
//                obj.put("label", item.getText());
//                obj.put("value", item.getId());
//                dataList.add(obj);
//            }
            return data;
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
            List<Map<String, Object>> company = (List<Map<String, Object>>) obj.get("companies");
            List<Map<String, Object>> site = (List<Map<String, Object>>) obj.get("sites");
            List<Map<String, Object>> endPoint = (List<Map<String, Object>>) obj.get("end_points");
            if (company == null) {
                company = new ArrayList<>();
            }
            if (site == null) {
                site = new ArrayList<>();
            }
            if (endPoint == null) {
                endPoint = new ArrayList<>();
            }
            ApiAccessEntity entity = (ApiAccessEntity) queryForObject("ApiAccess.checkUserHaveConfig", obj);
            if (entity == null) {
                if (company.isEmpty() && site.isEmpty() && endPoint.isEmpty()) {
                    session.rollback();
                    return false;
                }
                // create
                int row = session.insert("ApiAccess.saveConfig", obj);
                if (row == 0) {
                    session.rollback();
                    return false;
                }
                if (!company.isEmpty()) {
                    row = session.insert("ApiAccess.saveCompanyConfig", obj);
                    if (row == 0) {
                        session.rollback();
                        return false;
                    }
                }
                if (!site.isEmpty()) {
                    row = session.insert("ApiAccess.saveSiteConfig", obj);
                    if (row == 0) {
                        session.rollback();
                        return false;
                    }
                }
                if (!endPoint.isEmpty()) {
                    row = session.insert("ApiAccess.saveEndPointConfig", obj);
                    if (row == 0) {
                        session.rollback();
                        return false;
                    }
                }
            } else {
                obj.put("id_api_access", entity.getId());
                obj.put("id", entity.getId());
                obj.put("table", "api_access_site_map");
                session.delete("ApiAccess.deleteConfig", obj);
                obj.replace("table", "api_access_site_map", "api_access_company_map");
                session.delete("ApiAccess.deleteConfig", obj);
                obj.replace("table", "api_access_company_map", "api_access_endpoint_map");
                session.delete("ApiAccess.deleteConfig", obj);
                if (company.isEmpty() && site.isEmpty() && endPoint.isEmpty()) {
                    //obj.put("security_key", null);
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
                if (!endPoint.isEmpty()) {
                    int row = session.insert("ApiAccess.saveEndPointConfig", obj);
                    if (row == 0) {
                        session.rollback();
                        return false;
                    }
                }
                //obj.put("security_key", entity.getSecurity_key());
                obj.put("status", 1);
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

    public Map<String, Object> getApiAccessConfig(Map<String, Object> obj) {
        try{
            Map<String, Object> data = (Map<String, Object>) queryForObject("ApiAccess.getConfigOfUser", obj);
            ObjectMapper mapper = new ObjectMapper();
            String sitesStr = (String) data.get("sites");
            String companiesStr = (String) data.get("companies");
            String endPointStr = (String) data.get("end_points");
            if (!Lib.isBlank(sitesStr)) {
                List<Map<String, Object>> sites = mapper.readValue(sitesStr, List.class);
                data.put("sites", sites);
            }
            if (!Lib.isBlank(companiesStr)) {
                List<Map<String, Object>> companies = mapper.readValue(companiesStr, List.class);
                data.put("companies", companies);
            }
            if (!Lib.isBlank(endPointStr)) {
                List<Map<String, Object>> endPoint = mapper.readValue(endPointStr, List.class);
                data.put("end_points", endPoint);
            }
            return data;
        }catch (Exception ex) {
            return null;
        }
    }

    public List getListEndPoint(Map<String, Object> params) {
        try{
            List data = queryForList("ApiEndPoint.getList", params);
            if (data != null) {
                return data;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList();
    }

    public int getTotalEndPoint(Map<String, Object> params) {
        try{
            Integer total = (Integer) queryForObject("ApiEndPoint.getTotal", params);
            return total;
        } catch (Exception ex) {
            return 0;
        }
    }

    public Map<String, Object> getListEndPointOfUser(Map<String, Object> params) {
        try{
            Integer employeeId = (Integer) params.get("employee_id");
            if (employeeId == null) {
                return new HashMap<>();
            }
            if (checkApiAccessConfig(params) == null) {
                return new HashMap<>();
            }
            List data = queryForList("ApiEndPoint.getListOfUser", params);
            Map<String, Object> res = new HashMap<>();
            if (data != null) {
                Integer total = (Integer) queryForObject("ApiEndPoint.getTotalListOfUser", params);
                res.put("data", data);
                res.put("total_row", total);
                return res;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new HashMap<>();
    }

    public List getChartData(Map<String, Object> params) {
        try{
            Integer employeeId = (Integer) params.get("employee_id");
            if (employeeId == null) {
                return new ArrayList();
            }
            if (checkApiAccessConfig(params) == null) {
                return new ArrayList();
            }
            String filterBy = (String) params.get("filter_by");
            if (Lib.isBlank(filterBy)) {
                filterBy = "daily";
            }
            String query = "";
            switch (filterBy) {
                case "monthly":
                    query = "ApiAccess.getChartDataMonthly";
                    break;
                case "quarterly":
                    query = "ApiAccess.getChartDataQuarterly";
                    break;
                case "yearly":
                    query = "ApiAccess.getChartDataYearly";
                    break;
                default:
                    query = "ApiAccess.getChartDataDaily";
                    break;
            }
            List data = queryForList(query, params);
            if (data != null) {
                return data;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList();
    }

    public Map<String, Object> createSecurityKey(Map<String, Object> params) {
        try {
            Integer employeeId = (Integer) params.get("employee_id");
            if (employeeId == null) {
                return null;
            }
            ApiAccessEntity entity = checkApiAccessConfig(params);
            if (entity == null) {
                return null;
            }
            if (!Lib.isBlank(entity.getSecurity_key())) {
                return null;
            }
            String ramdomStr = Lib.randomString(10);;
            params.put("security_key_str", ramdomStr + employeeId);
            update("ApiAccess.updateSecurityKey", params);
            return getUserSecurityKey(params);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> getUserSecurityKey(Map<String, Object> obj) {
        try{
            Map<String, Object> data = (Map<String, Object>) queryForObject("ApiAccess.getUserSecurityKey", obj);
            return data;
        }catch (Exception ex) {
            return null;
        }
    }

    public boolean deleteUserSecurityKey(Map<String, Object> params) {
        try {
            Integer employeeId = (Integer) params.get("employee_id");
            if (employeeId == null) {
                return false;
            }
            ApiAccessEntity entity = checkApiAccessConfig(params);
            if (entity == null) {
                return false;
            }
            params.put("security_key_str", null);
            params.put("security_key_name", null);
            update("ApiAccess.updateSecurityKey", params);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List getSummary(Map<String, Object> params) {
        try {
            Integer employeeId = (Integer) params.get("employee_id");
            if (employeeId == null) {
                return new ArrayList();
            }
            ApiAccessEntity entity = checkApiAccessConfig(params);
            if (entity == null) {
                return new ArrayList();
            }
            List data = queryForList("ApiAccess.getSummary", params);
            return data;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList();
        }
    }
    
    public boolean insertAPIUsage(APIAccessLoggingDTO apiAccessLogging) {
    	try {
			return (int) insert("insertAPIUsage", apiAccessLogging) > 0;
		} catch (SQLException ex) {
			log.error("ApiAccessService.insertAPIUsage", ex);
			return false;
		}
    }
}
