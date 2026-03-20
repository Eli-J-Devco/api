package com.nwm.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.*;
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
            Object employeeId = obj.get("employee_id");
            if (company == null || site == null || endPoint == null || employeeId == null) {
                session.rollback();
                return false;
            }
            if (employeeId instanceof Integer && ((Integer) employeeId) <= 0) {
                session.rollback();
                return false;
            }
            if (employeeId instanceof String) {
                if (Lib.isBlank((String) employeeId)) {
                    session.rollback();
                    return false;
                }
                try {
                    int emp = Integer.parseInt(Lib.safeTrim((String) employeeId));
                    if (emp <= 0) {
                        session.rollback();
                        return false;
                    }

                } catch (NumberFormatException e) {
                    session.rollback();
                    return false;
                }
            }
            if (endPoint.isEmpty() || site.isEmpty() || company.isEmpty()) {
                session.rollback();
                return false;
            }
            ApiAccessEntity entity = (ApiAccessEntity) queryForObject("ApiAccess.checkUserHaveConfig", obj);
            UserService userService = new UserService();
            AccountEntity accountEntity = userService.getUserById((Integer) obj.get("author"));
            String author = accountEntity != null ? accountEntity.getEmail() : null;
            obj.put("author", author);
            if (entity == null) {
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
//                obj.put("status", 1);
                session.update("ApiAccess.updateConfig", obj);
            }
            session.commit();
            return true;
        } catch (Exception ex) {
            session.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public ApiAccessEntity checkApiAccessConfig(Map<String, Object> obj) {
        try {
//            obj.put("checkStatus", 1);
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
            params.put("checkStatus", 1);
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
            Integer isAdmin = (Integer) params.get("is_admin");
            if (employeeId == null) {
                return new ArrayList();
            }
            if (isAdmin == null || isAdmin != 1) {
                // not admin, check api access
                params.put("checkStatus", 1);
                if (checkApiAccessConfig(params) == null) {
                    return new ArrayList();
                }
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
            params.put("checkStatus", 1);
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
            params.put("checkStatus", 1);
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
            params.put("checkStatus", 1);
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

    public List adminGetSummary() {
        try{
            List data = queryForList("ApiAccess.adminGetSummary");
            return data;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList();
        }
    }

    /**
	 * @description insert API usage
	 * @author Hung.Bui
	 * @since 2026-02-10
	 */
    public boolean insertAPIUsage(APIAccessLoggingDTO apiAccessLogging) {
    	SqlSession session = this.beginTransaction();
    	
    	try {
    		boolean isInserted = session.insert("ApiAccess.insertAPIUsage", apiAccessLogging) > 0;
    		if (isInserted) session.update("ApiAccess.updateAPIAccessLastUsed", apiAccessLogging);
    		session.commit();
			return isInserted;
		} catch (Exception ex) {
			log.error("ApiAccessService.insertAPIUsage", ex);
			session.rollback();
			return false;
		} finally {
			session.close();
		}
    }

    /**
	 * @description insert API usage (overload method for convenience)
	 * @author duc.pham
	 * @since 2026-02-12
	 * @param endpoint The API endpoint route
	 * @param method The HTTP method
	 * @param apiKey The API security key
	 * @return true if successful, false otherwise
	 */
    public boolean insertAPIUsage(String endpoint, String method, String apiKey) {
    	return insertAPIUsage(new APIAccessLoggingDTO(endpoint, method, apiKey));
    }

    /**
     * Validate API key for external API access
     * @param apiKey The API key to validate
     * @return true if valid and active, false otherwise
     */
    public boolean validateApiKey(String apiKey) {
        if (Lib.isBlank(apiKey)) {
            return false;
        }
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("security_key", apiKey);
            ApiAccessEntity entity = (ApiAccessEntity) queryForObject("ApiAccess.validateApiKey", params);
            return entity != null && entity.getStatus() == 1;
        } catch (Exception ex) {
            log.error("ApiAccessService.validateApiKey", ex);
            return false;
        }
    }

    /**
     * Get API access entity by security key
     * @param apiKey The API key
     * @return ApiAccessEntity if found, null otherwise
     */
    public ApiAccessEntity getByApiKey(String apiKey) {
        if (Lib.isBlank(apiKey)) {
            return null;
        }
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("security_key", apiKey);
            return (ApiAccessEntity) queryForObject("ApiAccess.validateApiKey", params);
        } catch (Exception ex) {
            log.error("ApiAccessService.getByApiKey", ex);
            return null;
        }
    }

    public List adminGetListUserAccessApi(String keyword) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("keyword", keyword);
            List dataList = queryForList("ApiAccess.adminGetListUserAccessApi", params);
            return dataList;
        } catch (Exception ex) {
            log.error("ApiAccessService.adminGetListUserAccessApi", ex);
            return new ArrayList<>();
        }
    }
}
