/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.CompanyEntity;
import com.nwm.api.entities.ContactInfoCompanyEntity;
import com.nwm.api.entities.ContactInfoCompanyPhoneEntity;
import com.nwm.api.entities.RoleEntity;
import com.nwm.api.entities.RoleScreenMapEntity;
import com.nwm.api.utils.Lib;

public class CompanyService extends DB {

	/**
	 * @description get list dropdown company
	 * @author long.pham
	 * @since 2022-03-10
	 * @returns array
	 */
	
	public List getDropdownList(CompanyEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Company.getDropdownList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description Get detail company by id
	 * @author duy.phan
	 * @since 2025-08-29	
	 * @param id
	 * @return Object
	 */

	public CompanyEntity getDetailCompanyById(CompanyEntity obj) {
		CompanyEntity dataObj = new CompanyEntity();
		try {
			dataObj = (CompanyEntity) queryForObject("Company.getDetailCompanyById", obj);
			
			if (dataObj == null)
				return new CompanyEntity();
		} catch (Exception ex) {
			return new CompanyEntity();
		}
		return dataObj;
	}
	
	/**
	 * @description get list company 
	 * @author long.pham
	 * @since 2022-03-10
	 * @param {}
	 */
	
	
	public List<Map<String, Object>> getList(CompanyEntity obj) {
		try {
			List<Map<String, Object>> dataList = queryForList("Company.getList", obj);
			if (dataList == null) return new ArrayList<Map<String, Object>>();
			ObjectMapper mapper = new ObjectMapper();
			dataList.forEach(item -> {
				try {
					List<Map<String, Object>> contact = mapper.readValue(item.get("contactInformation").toString(), new TypeReference<List<Map<String, Object>>>(){});
					item.put("contactInformation", contact);
				} catch (JsonProcessingException e) {
					item.put("contactInformation", new ArrayList<Map<String, Object>>());
				}
			});
			return dataList;
		} catch (Exception ex) {
			return new ArrayList<Map<String, Object>>();
		}
	}
	
	public int getTotalRecord(CompanyEntity obj) {
		try {
			return (int)queryForObject("Company.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	
	/** @description delete company
	 * @author long.pham
	 * @since 2022-03-10
	 * @param id
	 */
	public boolean delete(CompanyEntity obj) {
		try {
			return delete("Company.delete", obj) > 0;
		} catch (Exception ex) {
			log.error("Company.delete", ex);
			return false;
		}
	}
	
	
	
	/**
	 * @description insert company
	 * @author long.pham
	 * @since 2022-03-10
	 */
	public CompanyEntity insertCompany(CompanyEntity obj) 
	{
		SqlSession session = this.beginTransaction();
		try {
			List contactInformation = obj.getContactInformation();
			session.insert("Company.insertCompany", obj);
			int insertLastId = obj.getId();
			
			List screens = obj.getScreens();
			if(insertLastId > 0 && screens.size() > 0) {
				// Create role 
				RoleEntity roleItem = new RoleEntity();
				roleItem.setName("Admin");
				roleItem.setDescription("");
				roleItem.setId_company(insertLastId);
				roleItem.setIs_admin_role(1);
				
				
				session.insert("Role.insertRole", roleItem);
				if(roleItem.getId() > 0) {
					for (int i = 0; i < screens.size(); i++) {
						Map<String, Object> item = (Map<String, Object>) screens.get(i);
						Integer id_screen = Integer.parseInt(item.get("id").toString()) ;
						RoleScreenMapEntity mapItem = this._buildRoleScreenMap(roleItem.getId(), id_screen, 511 );
						session.insert("Role.insertRoleScreenMap", mapItem);
					}
				} else { return null; }
			} else {return null; }  
			
			if(insertLastId > 0 && contactInformation.size() > 0) {
				for (int i = 0; i < contactInformation.size(); i++) {
					Map<String, Object> item = (Map<String, Object>) contactInformation.get(i);
					int primary_contact = item.get("primary_contact") == null ? 0 : Integer.parseInt(item.get("primary_contact").toString());
					String first_name = item.get("first_name") == null ? "" : item.get("first_name").toString();
					String last_name = item.get("last_name") == null ? "": item.get("last_name").toString();
					String email = item.get("email") == null ? "": item.get("email").toString();
					String title_position = item.get("title_position") == null ? "": item.get("title_position").toString();
					List dataPhones = (List) item.get("dataPhones");
					
					ContactInfoCompanyEntity mapItem = this._buildContactInfoComanyMap(insertLastId, first_name, last_name, email, title_position, primary_contact );
					session.insert("Company.insertContactInfoCompany", mapItem);
					int insertLastIdIC = mapItem.getId();
					
					if(insertLastIdIC > 0 && dataPhones.size() > 0) {
						for (int j = 0; j < dataPhones.size(); j++) {
							Map<String, Object> itemPhone = (Map<String, Object>) dataPhones.get(j);
							
							Integer phone_type = itemPhone.get("phone_type") == null || Lib.isBlank(itemPhone.get("phone_type"))  ? 0 : Integer.parseInt(itemPhone.get("phone_type").toString()) ;
							String phone_number = itemPhone.get("phone_number") == null || Lib.isBlank(itemPhone.get("phone_number")) ? "" : itemPhone.get("phone_number").toString();
							String phone_ext = itemPhone.get("phone_ext") == null || Lib.isBlank(itemPhone.get("phone_ext")) ? "": itemPhone.get("phone_ext").toString();
							
							ContactInfoCompanyPhoneEntity mapItemPhone = this._buildContactInfoComanyPhoneMap(insertLastIdIC, phone_type, phone_number, phone_ext);
							session.insert("Company.insertContactInfoCompanyPhone", mapItemPhone);
						}
					}
				}
			} else { return null; }
			
			session.commit();
			return obj;
		} catch (Exception ex) {
			session.rollback();
			return null;
		} finally {
			session.close();
		}
	}
	
	
	/**
	 * build contact info company
	 * @author long.pham
	 * @since 2024-07-16
	 * @param idCompany
	 * @param first_name, last_name, email, title_position, primary_contact
	 * @return
	 */
	private ContactInfoCompanyEntity _buildContactInfoComanyMap(int idCompany, String first_name, String last_name, String email, String title_position, int primary_contact) {
		try {
			ContactInfoCompanyEntity item = new ContactInfoCompanyEntity();
			item.setId_company(idCompany);
			item.setFirst_name(first_name);
			item.setLast_name(last_name);
			item.setEmail(email);
			item.setTitle_position(title_position);
			item.setPrimary_contact(primary_contact);
			return item;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	
	/**
	 * build contact info company
	 * @author long.pham
	 * @since 2024-07-16
	 * @param idCompany
	 * @param first_name, last_name, email, title_position, primary_contact
	 * @return
	 */
	private RoleScreenMapEntity _buildRoleScreenMap(int id_role, int id_screen, int auths) {
		try {
			RoleScreenMapEntity item = new RoleScreenMapEntity();
			item.setId_role(id_role);
			item.setId_screen(id_screen);
			item.setAuths(auths);
			return item;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	
	/**
	 * build contact info company
	 * @author long.pham
	 * @since 2024-07-16
	 * @param idCompany
	 * @param first_name, last_name, email, title_position, primary_contact
	 * @return
	 */
	private ContactInfoCompanyPhoneEntity _buildContactInfoComanyPhoneMap(int id_conact_info_company, int phone_type, String phone_number, String phone_ext) {
		try {
			ContactInfoCompanyPhoneEntity item = new ContactInfoCompanyPhoneEntity();
			item.setId_conact_info_company(id_conact_info_company);
			item.setPhone_type(phone_type);
			item.setPhone_number(phone_number);
			item.setPhone_ext(phone_ext);
			return item;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @description update company
	 * @author long.pham
	 * @since 2022-03-10
	 * @param id
	 */
	public boolean updateCompany(CompanyEntity obj){
		
		SqlSession session = this.beginTransaction();
		try {
			session.update("Company.updateCompany", obj);
			session.delete("Company.deleteContactInfoCompany", obj);
			List screens = obj.getScreens();
			if(screens.size() > 0 && obj.getId() > 0 && obj.getId_admin_role() > 0) {
				
				ArrayList<RoleScreenMapEntity> dataScreenMap = new ArrayList<RoleScreenMapEntity>(10);
				
				// Get all role by company 
				List<Map<String, Object>> dataRolesByCompany = queryForList("Role.getListRoleByCompanyNotAdminRole", obj);
				if(dataRolesByCompany.size() > 0) {
					
					for (int i = 0; i < dataRolesByCompany.size(); i++) {
						
						Map<String, Object> itemRole = (Map<String, Object>) dataRolesByCompany.get(i);
						List<Map<String, Object>> dataScreenByRole = queryForList("Role.getDataScreenByRole", itemRole);
						if(dataScreenByRole.size() > 0) {
							
							for (int j = 0; j < screens.size(); j++) {
								
								Map<String, Object> itemScreen = (Map<String, Object>) screens.get(j);
								RoleScreenMapEntity itemScreenMap = new RoleScreenMapEntity();
								itemScreenMap.setId_role(Integer.parseInt(itemRole.get("id").toString()));
								itemScreenMap.setId_screen(Integer.parseInt(itemScreen.get("id").toString()));
								itemScreenMap.setAuths(0);
								
								for (int v = 0; v < dataScreenByRole.size(); v++) {
									Map<String, Object> itemScreenOld = (Map<String, Object>) dataScreenByRole.get(v);
									if( Integer.parseInt(itemScreen.get("id").toString()) == Integer.parseInt(itemScreenOld.get("id_screen").toString()) &&  Integer.parseInt(itemRole.get("id").toString()) == Integer.parseInt(itemScreenOld.get("id_role").toString()) ) {
										itemScreenMap.setId_role(Integer.parseInt(itemScreenOld.get("id_role").toString()));
										itemScreenMap.setId_screen(Integer.parseInt(itemScreenOld.get("id_screen").toString()));
										itemScreenMap.setAuths(Integer.parseInt(itemScreenOld.get("auths").toString()));
									}
								}
								
								dataScreenMap.add(itemScreenMap);
							}
							
						}
					}
					
				}
				
				if(dataScreenMap.size() > 0) {
					obj.setDataScreenMap(dataScreenMap);
					session.delete("Role.deleteMultiRole", obj);
					session.insert("Role.insertMultiRoleScreenMap", obj);
					
					
				}
				
				// Update permission for admin role
				session.delete("Role.deleteRoleScreenMap", obj);
				for (int i = 0; i < screens.size(); i++) {
					Map<String, Object> item = (Map<String, Object>) screens.get(i);
					Integer id_screen = Integer.parseInt(item.get("id").toString()) ;
					RoleScreenMapEntity mapItem = this._buildRoleScreenMap(obj.getId_admin_role(), id_screen, 511 );
					session.insert("Role.insertRoleScreenMap", mapItem);
				}
			}
			
			
			
			List contactInformation = obj.getContactInformation();
			if(obj.getId() > 0 && contactInformation.size() > 0) {
				for (int i = 0; i < contactInformation.size(); i++) {
					Map<String, Object> item = (Map<String, Object>) contactInformation.get(i);
					int primary_contact = item.get("primary_contact") == null ? 0 : Integer.parseInt(item.get("primary_contact").toString());
					String first_name = item.get("first_name") == null ? "" : item.get("first_name").toString();
					String last_name = item.get("last_name") == null ? "": item.get("last_name").toString();
					String email = item.get("email") == null ? "": item.get("email").toString();
					String title_position = item.get("title_position") == null ? "": item.get("title_position").toString();
					List dataPhones = (List) item.get("dataPhones");
					
					ContactInfoCompanyEntity mapItem = this._buildContactInfoComanyMap(obj.getId(), first_name, last_name, email, title_position, primary_contact );
					session.insert("Company.insertContactInfoCompany", mapItem);
					int insertLastIdIC = mapItem.getId();
					
					if(insertLastIdIC > 0 && dataPhones.size() > 0) {
						for (int j = 0; j < dataPhones.size(); j++) {
							Map<String, Object> itemPhone = (Map<String, Object>) dataPhones.get(j);
							
							int phone_type = itemPhone.get("phone_type") == null || Lib.isBlank(itemPhone.get("phone_type")) ? 0 :  Integer.parseInt(itemPhone.get("phone_type").toString());
							String phone_number = itemPhone.get("phone_number") == null || Lib.isBlank(itemPhone.get("phone_number")) ? "" : itemPhone.get("phone_number").toString();
							String phone_ext = itemPhone.get("phone_ext") == null || Lib.isBlank(itemPhone.get("phone_ext")) ? "": itemPhone.get("phone_ext").toString();
							
							ContactInfoCompanyPhoneEntity mapItemPhone = this._buildContactInfoComanyPhoneMap(insertLastIdIC, phone_type, phone_number, phone_ext);
							session.insert("Company.insertContactInfoCompanyPhone", mapItemPhone);
						}
					}
				}
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
	
	
	/**
	 * @description update company status
	 * @author long.pham
	 * @since 2022-03-10
	 * @param id
	 */
	public boolean updateStatus(CompanyEntity obj){
		try{
			return update("Company.updateStatus", obj)>0;
		}catch (Exception ex) {
			log.error("Company.updateStatus", ex);
			return false;
		}
	}
	
	/**
	 * @description update company performance on Actual Expected
	 * @author duy.phan
	 * @since 2025-08-29
	 * @param id
	 */
	public boolean updatePerformanceThresholdsActualExpected(CompanyEntity obj){
		try{
			return update("Company.updatePerformanceThresholdsActualExpected", obj)>0;
		}catch (Exception ex) {
			log.error("Company.updatePerformanceThresholdsActualExpected", ex);
			return false;
		}
	}
	
	/**
	 * @description update company performance availability
	 * @author duy.phan
	 * @since 2025-08-29
	 * @param id
	 */
	public boolean updateAvailabilityPerformanceThresholds(CompanyEntity obj){
		try{
			return update("Company.updateAvailabilityPerformanceThresholds", obj)>0;
		}catch (Exception ex) {
			log.error("Company.updateAvailabilityPerformanceThresholds", ex);
			return false;
		}
	}

}
