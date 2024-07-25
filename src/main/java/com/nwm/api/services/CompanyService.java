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

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.CompanyEntity;
import com.nwm.api.entities.ContactInfoCompanyEntity;
import com.nwm.api.entities.ContactInfoCompanyPhoneEntity;
import com.nwm.api.entities.LevitonOverviewWidgetMapEntity;
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
	 * @description get list company 
	 * @author long.pham
	 * @since 2022-03-10
	 * @param {}
	 */
	
	
	public List getList(CompanyEntity obj) {
		List dataList = new ArrayList();
		try {
			List data = queryForList("Company.getList", obj);
			if (data == null)
				return new ArrayList();
			
			for(int i = 0; i < data.size(); i++) {
				Map<String, Object> item = (Map<String, Object>) data.get(i);
				List contactInformation = queryForList("Company.getListContactInformation", item);
				
				List dataContact = new ArrayList();
				if(contactInformation.size() > 0) {
					for(int j = 0; j < contactInformation.size(); j++) {
						Map<String, Object> itemContact = (Map<String, Object>) contactInformation.get(j);
						List getDataPhones = queryForList("Company.getListDataPhones", itemContact);
						
						itemContact.put("dataPhones", getDataPhones);
						dataContact.add(itemContact);
					}
				}
				
				item.put("contactInformation", dataContact);
				
				dataList.add(item);
			}
			
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
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
			}
			
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
	

}
