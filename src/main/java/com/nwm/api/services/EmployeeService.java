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
import com.nwm.api.entities.CustomerEntity;
import com.nwm.api.entities.EmployeeEntity;
import com.nwm.api.entities.EmployeeManageEntity;
import com.nwm.api.entities.EmployeeRoleMapEntity;
import com.nwm.api.entities.EmployeeSiteMapEntity;
import com.nwm.api.entities.UserEntity;

public class EmployeeService extends DB {
	/**
	 * @description get list role
	 * @author long.pham
	 * @since 2021-01-05
	 */
	
	public List getAll(EmployeeEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Employee.getAll", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}

	/**
	 * @description get admin by (email)
	 * @author long.pham
	 * @since 2020-12-22
	 * @param username
	 * @return object
	 */

	public UserEntity getAdminByUserName(String userName) {
		UserEntity user = new UserEntity();
		try {
			user = (UserEntity) queryForObject("Employee.findByAdminLoginId", userName);
			if (user == null)
				return new UserEntity();

			// Get permission by user id
			List dataList = new ArrayList();
			if (user.getId() != 0 && user.getRoles() != null) {
				dataList = queryForList("Employee.getPermissionByUser", user);
			}

			user.setPermissions(dataList);

		} catch (Exception ex) {
			return new UserEntity();
		}
		return user;
	}

	/**
	 * @description get list Employee
	 * @author long.pham
	 * @since 2021-01-06
	 */

	public List getList(EmployeeManageEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Employee.getList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}

	/**
	 * @description get total record Employee
	 * @author long.pham
	 * @since 2021-01-06
	 */
	public int getTotalRecord(EmployeeManageEntity obj) {
		try {
			return (int) queryForObject("Employee.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}

	/**
	 * @description update role status
	 * @author long.pham
	 * @since 2021-01-06
	 * @param id
	 */
	public boolean updateStatus(EmployeeManageEntity obj) {
		try {
			return update("Employee.updateStatus", obj) > 0;
		} catch (Exception ex) {
			log.error("Employee.updateStatus", ex);
			return false;
		}
	}

	/**
	 * @description insert Employee
	 * @author long.pham
	 * @since 2021-01-06
	 * @param id
	 */
	public EmployeeManageEntity insertEmployee(EmployeeManageEntity obj) {
		SqlSession session = this.beginTransaction();
		try {
			List roles = obj.getRoles();
			if (roles.size() <= 0) {
				throw new Exception();
			}

			session.insert("Employee.insertEmployee", obj);
			int insertOrderLastId = obj.getId();

			if (insertOrderLastId > 0) {
				for (int i = 0; i < roles.size(); i++) {
					Map<String, Object> user = (Map<String, Object>) roles.get(i);
					int id = (int) user.get("id_role");
					EmployeeRoleMapEntity employeeRoleMaptItem = this._buildEmployeeRoleMapItem(insertOrderLastId, id);
					session.insert("Employee.insertEmployeeRoleMap", employeeRoleMaptItem);
				}
			} else {
				return null;
			}
			
			List dataSite = obj.getDataSite();
			if(dataSite.size() > 0) {
				for (int i = 0; i < dataSite.size(); i++) {
					Map<String, Object> objSite = (Map<String, Object>) dataSite.get(i);
					int id_site = (int) objSite.get("id");
					EmployeeSiteMapEntity employeeSiteMaptItem = this._buildEmployeeSiteMapItem(id_site, insertOrderLastId);
					session.insert("Employee.insertEmployeeSiteMap", employeeSiteMaptItem);
				}
			}

			session.commit();
			return obj;
		} catch (Exception ex) {
			session.rollback();
			log.error("Employee.insertEmployee", ex);
			return null;
		} finally {
			session.close();
		}

	}
	
	
	/**
	 * @description update password employee
	 * @author long.pham
	 * @since 2021-04-06
	 * @param id
	 */
	
	public boolean updatePasswordEmployee(EmployeeManageEntity obj) {
		try {
			return update("Employee.updatePasswordEmployee", obj) > 0;
		} catch (Exception ex) {
			log.error("Employee.updatePasswordEmployee", ex);
			return false;
		}
	}
	
	/**
	 * build order product item
	 * 
	 * @param productItem
	 * @param productId
	 * @param insertOrderLastId
	 * @return
	 */
	private EmployeeRoleMapEntity _buildEmployeeRoleMapItem(int employeeId, int roleId) {
		try {
			EmployeeRoleMapEntity employeeRoleMapItem = new EmployeeRoleMapEntity();
			employeeRoleMapItem.setId_employee(employeeId);
			employeeRoleMapItem.setId_role(roleId);
			return employeeRoleMapItem;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	/**
	 * build order product item
	 * 
	 * @param productItem
	 * @param productId
	 * @param insertOrderLastId
	 * @return
	 */
	private EmployeeSiteMapEntity _buildEmployeeSiteMapItem(int id_site, int id_employee) {
		try {
			EmployeeSiteMapEntity employeeSiteMapItem = new EmployeeSiteMapEntity();
			employeeSiteMapItem.setId_employee(id_employee);
			employeeSiteMapItem.setId_site(id_site);
			return employeeSiteMapItem;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @description update Employee
	 * @author long.pham
	 * @since 2021-01-06
	 * @param id
	 */
	public boolean updateEmployee(EmployeeManageEntity obj) {
		SqlSession session = this.beginTransaction();
		try {
			List roles = obj.getRoles();
			if (roles.size() <= 0) {
				throw new Exception();
			}

			session.delete("Employee.deleteEmpyeeRoleMap", obj);
			session.update("Employee.updateEmployee", obj);
			session.delete("Employee.deleteSiteEmployeeMap", obj);

			for (int i = 0; i < roles.size(); i++) {
				Map<String, Object> user = (Map<String, Object>) roles.get(i);
				int id = (int) user.get("id_role");
				EmployeeRoleMapEntity employeeRoleMaptItem = this._buildEmployeeRoleMapItem(obj.getId(), id);
				session.insert("Employee.insertEmployeeRoleMap", employeeRoleMaptItem);
			}
			
			List dataSite = obj.getDataSite();
			if(dataSite.size() > 0) {
				for (int i = 0; i < dataSite.size(); i++) {
					Map<String, Object> objSite = (Map<String, Object>) dataSite.get(i);
					int id_site = (int) objSite.get("id");
					EmployeeSiteMapEntity employeeSiteMaptItem = this._buildEmployeeSiteMapItem(id_site, obj.getId());
					session.insert("Employee.insertEmployeeSiteMap", employeeSiteMaptItem);
				}
			}

			session.commit();
			return true;
		} catch (Exception ex) {
			session.rollback();
			log.error("Employee.updateEmployee", ex);
			return false;
		} finally {
			session.close();
		}

	}

	/**
	 * @description delete Employee
	 * @author long.pham
	 * @since 2021-01-06
	 * @param id
	 */
	public boolean deleteEmployee(EmployeeManageEntity obj) {
		try {
			return update("Employee.deleteEmployee", obj) > 0;
		} catch (Exception ex) {
			log.error("Employee.deleteEmployee", ex);
			return false;
		}
	}

	/**
	 * @description get Employee by email
	 * @author long.pham
	 * @since 2021-01-06
	 * @param email
	 */

	public EmployeeManageEntity getEmployeeByEmail(String email) {
		EmployeeManageEntity employee = new EmployeeManageEntity();
		try {
			employee = (EmployeeManageEntity) queryForObject("Employee.getEmployeeByEmail", email);
			if (employee == null)
				return new EmployeeManageEntity();
		} catch (Exception ex) {
			log.error("Employee.getEmployeeByEmail", ex);
			return new EmployeeManageEntity();
		}
		return employee;
	}

	/**
	 * @description get Employee Exist email and id
	 * @author long.pham
	 * @since 2021-01-06
	 * @param email, id
	 */
	public boolean checkEmployeeEmailExist(EmployeeManageEntity dataE) {
		try {
			return (int) queryForObject("Employee.checkEmployeeEmailExist", dataE) > 0;
		} catch (Exception e) {

		}
		return true;
	}

	/**
	 * @description get Employee by email
	 * @author long.pham
	 * @since 2021-01-06
	 * @param email
	 */

	public EmployeeManageEntity getEmployeeById(int id) {
		EmployeeManageEntity employee = new EmployeeManageEntity();
		try {
			employee = (EmployeeManageEntity) queryForObject("Employee.getEmployeeById", id);
			if (employee == null)
				return new EmployeeManageEntity();
		} catch (Exception ex) {
			log.error("Employee.getEmployeeById", ex);
			return new EmployeeManageEntity();
		}
		return employee;
	}
	
	/**
	 * @description update table columns in Portfolio
	 * @author duy.phan
	 * @since 2022-12-22
	 * @param id
	 */
	public boolean updateTableColumn(EmployeeManageEntity obj) {
		try {
			return update("Employee.updateTableColumn", obj) > 0;
		} catch (Exception ex) {
			log.error("Employee.updateTableColumn", ex);
			return false;
		}
	}
	
	/**
	 * @description get table columns in Portfolio
	 * @author duy.phan
	 * @since 2023-04-18
	 * @param id
	 */
	public EmployeeManageEntity getTableColumn(int id) {
		EmployeeManageEntity employee = new EmployeeManageEntity();
		try {
			employee = (EmployeeManageEntity) queryForObject("Employee.getTableColumnEmployeeById", id);
			if (employee == null)
				return new EmployeeManageEntity();
		} catch (Exception ex) {
			log.error("Employee.getTableColumnEmployeeById", ex);
			return new EmployeeManageEntity();
		}
		return employee;
	}
	
	/**
	 * @description update display alert per page
	 * @author duy.phan
	 * @since 2023-07-24
	 * @param id, alert_per_page
	 */
	public boolean updateAlertPerPage(EmployeeManageEntity obj) {
		try {
			return update("Employee.updateAlertPerPage", obj) > 0;
		} catch (Exception ex) {
			log.error("Employee.updateAlertPerPage", ex);
			return false;
		}
	}
	
	/**
	 * @description update alert filter in Alert
	 * @author duy.phan
	 * @since 2022-12-22
	 * @param id
	 */
	public boolean updateAlertFilter(EmployeeManageEntity obj) {
		try {
			return update("Employee.updateAlertFilter", obj) > 0;
		} catch (Exception ex) {
			log.error("Employee.updateAlertFilter", ex);
			return false;
		}
	}
	
	/**
	 * @description update lock account
	 * @author duy.phan
	 * @since 2022-12-22
	 * @param id
	 */
	public boolean updateLockedAccount(EmployeeManageEntity obj) {
		try {
			return update("Employee.updateLockedAccount", obj) > 0;
		} catch (Exception ex) {
			log.error("Employee.updateLockedAccount", ex);
			return false;
		}
	}
	
	/**
	 * @description update unlock account
	 * @author duy.phan
	 * @since 2022-12-22
	 * @param id
	 */
	public boolean updateUnlockedAccount(EmployeeManageEntity obj) {
		try {
			return update("Employee.updateUnLockedAccount", obj) > 0;
		} catch (Exception ex) {
			log.error("Employee.updateUnLockedAccount", ex);
			return false;
		}
	}

}
