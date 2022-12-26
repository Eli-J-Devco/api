/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.CustomerEntity;

public class CustomerService extends DB {

	/**
	 * @description get list role
	 * @author long.pham
	 * @since 2021-01-05
	 */
	
	public List getList(CustomerEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Customer.getList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get total record customer
	 * @author long.pham
	 * @since 2021-01-05
	 */
	public int getTotalRecord(CustomerEntity obj) {
		try {
			return (int)queryForObject("Customer.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/**
	 * @description update role status
	 * @author long.pham
	 * @since 2021-01-05
	 * @param id
	 */
	public boolean updateStatus(CustomerEntity obj){
		try{
			return update("Customer.updateStatus", obj)>0;
		}catch (Exception ex) {
			log.error("Customer.updateStatus", ex);
			return false;
		}
	}
	
	/**
	 * @description insert customer
	 * @author long.pham
	 * @since 2021-01-05
	 * @param id
	 */
	public CustomerEntity insertCustomer(CustomerEntity obj) 
	{
		try
	    {
	       Object insertId = insert("Customer.insertCustomer", obj);
	       if(insertId != null && insertId instanceof Integer) {
	    	   return obj;
	       }else {
	    	   return null;
	       }
	    }
	    catch(Exception ex)
	    {
	        log.error("insert.insertCustomer", ex);
	        return null;
	    }	
	}
	
	/**
	 * @description update customer
	 * @author long.pham
	 * @since 2021-01-05
	 * @param id
	 */
	public boolean updateCustomer(CustomerEntity obj){
		try{
			return update("Customer.updateCustomer", obj)>0;
		}catch (Exception ex) {
			log.error("Customer.updateCustomer", ex);
			return false;
		}
	}
	
	/**
	 * @description delete customer
	 * @author long.pham
	 * @since 2021-01-05
	 * @param id
	 */
	public boolean deleteCustomer(CustomerEntity obj){
		try{
			return update("Customer.deleteCustomer", obj)>0;
		}catch (Exception ex) {
			log.error("Customer.deleteCustomer", ex);
			return false;
		}
	}

	/**
	 * @description get customer by email
	 * @author long.pham
	 * @since 2021-01-05
	 * @param email
	 */

	public CustomerEntity getCustomerByEmail(String email) {
		CustomerEntity customer = new CustomerEntity();
		try {
			customer = (CustomerEntity) queryForObject("Customer.getCustomerByEmail", email);
			if (customer == null)
				return new CustomerEntity();
		} catch (Exception ex) {
			log.error("Customer.getCustomerByEmail", ex);
			return new CustomerEntity();
		}
		return customer;
	}
	
	/**
	 * @description get customer Exist email and id
	 * @author long.pham
	 * @since 2021-01-05
	 * @param email, id
	 */
	public boolean checkCustomerEmailExist(CustomerEntity dataE) {
		try {
			return (int) queryForObject("Customer.checkCustomerEmailExist", dataE) > 0;
		}catch (Exception e) {
			
		}
		return true;
	}
	
	/**
	 * @description get customer by email
	 * @author long.pham
	 * @since 2021-01-05
	 * @param email
	 */

	public CustomerEntity getCustomerById(int id) {
		CustomerEntity customer = new CustomerEntity();
		try {
			customer = (CustomerEntity) queryForObject("Customer.getCustomerById", id);
			if (customer == null)
				return new CustomerEntity();
		} catch (Exception ex) {
			log.error("Customer.getCustomerById", ex);
			return new CustomerEntity();
		}
		return customer;
	}
	
	/**
	 * @description get list role
	 * @author long.pham
	 * @since 2021-01-05
	 */
	
	public List getAll(CustomerEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Customer.getAll", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description update customer
	 * @author long.pham
	 * @since 2021-01-05
	 * @param id
	 */
	public boolean customerUpdateToken(CustomerEntity obj){
		try{
			return update("Customer.customerUpdateToken", obj)>0;
		}catch (Exception ex) {
			log.error("Customer.customerUpdateToken", ex);
			return false;
		}
	}
	
}
