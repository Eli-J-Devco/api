/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AccountEntity;
import com.nwm.api.entities.UserEntity;

public class UserService extends DB {

	/**
	 * @description get user by username
	 * @author long.pham
	 * @since 2020-09-03
	 * @param
	 */
	public UserEntity getUserByUserName(String userName) {
		UserEntity user = new UserEntity();
		try {
			user = (UserEntity) queryForObject("User.findByLoginId", userName);
			if (user == null)
				return new UserEntity();
		} catch (Exception ex) {
			return new UserEntity();
		}
		return user;
	}

	/**
	 * @description get user by email
	 * @author long.pham
	 * @since 2020-09-03
	 * @param
	 */

	public UserEntity getUserByEmail(String email) {
		UserEntity user = new UserEntity();
		try {
			user = (UserEntity) queryForObject("User.getUserByEmail", email);
			if (user == null)
				return new UserEntity();
		} catch (Exception ex) {
			log.error("User.getUserByEmail", ex);
			return new UserEntity();
		}
		return user;
	}

	
	/**
	 * @description get user by id
	 * @author long.pham
	 * @since 2020-09-03
	 * @param
	 */

	public AccountEntity getUserById(int id) {
		AccountEntity user = new AccountEntity();
		try {
			user = (AccountEntity) queryForObject("User.getUserById", id);
			if (user == null)
				return new AccountEntity();
		} catch (Exception ex) {
			log.error("User.getUserById", ex);
			return new AccountEntity();
		}
		return user;
	}

	/**
	 * @description reset password
	 * @author long.pham
	 * @since 2020-09-03
	 * @param
	 */
	public boolean resetPassword(AccountEntity obj) {
		try {
			return update("User.resetPassword", obj) > 0;
		} catch (Exception ex) {
			log.error("User.resetPassword", ex);
			return false;
		}
	}

	/**
	 * @description change password
	 * @author long.pham
	 * @since 2020-09-03
	 * @param
	 */

	public boolean updateChangePassword(AccountEntity obj) {
		try {
			return update("User.updateChangePassword", obj) > 0;
		} catch (Exception ex) {
			log.error("User.updateChangePassword", ex);
			return false;
		}
	}

	/**
	 * @description get user by id
	 * @author long.pham
	 * @since 2020-09-03
	 * @param
	 */

	public AccountEntity getUserAdminById(int id) {
		AccountEntity user = new AccountEntity();
		try {
			user = (AccountEntity) queryForObject("User.getUserAdminById", id);
			if (user == null)
				return new AccountEntity();
		} catch (Exception ex) {
			log.error("User.getUserAdminById", ex);
			return new AccountEntity();
		}
		return user;
	}
	
	/**
	 * @description admin change password
	 * @author long.pham
	 * @since 2020-09-03
	 * @param
	 */

	public boolean updateAdminChangePassword(AccountEntity obj) {
		try {
			return update("User.updateAdminChangePassword", obj) > 0;
		} catch (Exception ex) {
			log.error("User.updateAdminChangePassword", ex);
			return false;
		}
	}
	
	
	/**
	 * @description reset password
	 * @author long.pham
	 * @since 2020-02-28
	 * @param
	 */
	public boolean setNewPassword(AccountEntity obj) {
		try {
			return update("User.setNewPassword", obj) > 0;
		} catch (Exception ex) {
			log.error("User.setNewPassword", ex);
			return false;
		}
	}
	
	
	/**
	 * @description reset password admin
	 * @author long.pham
	 * @since 2020-02-28
	 * @param
	 */
	public boolean setNewPasswordAdmin(AccountEntity obj) {
		try {
			return update("User.setNewPasswordAdmin", obj) > 0;
		} catch (Exception ex) {
			log.error("User.setNewPasswordAdmin", ex);
			return false;
		}
	}
	
	/**
	 * @description get user by email
	 * @author long.pham
	 * @since 2020-09-03
	 * @param
	 */

	public AccountEntity getUserByEmailAndTokenSet(AccountEntity obj) {
		AccountEntity user = new AccountEntity();
		try {
			user = (AccountEntity) queryForObject("User.getUserByEmailAndTokenSet", obj);
			if (user == null)
				return new AccountEntity();
		} catch (Exception ex) {
			log.error("User.getUserByEmailAndTokenSet", ex);
			return new AccountEntity();
		}
		return user;
	}
	
	/**
	 * @description get admin by email
	 * @author long.pham
	 * @since 2021-05-17
	 * @param
	 */

	public AccountEntity getAdminByEmailAndTokenSet(AccountEntity obj) {
		AccountEntity user = new AccountEntity();
		try {
			user = (AccountEntity) queryForObject("User.getAdminByEmailAndTokenSet", obj);
			if (user == null)
				return new AccountEntity();
		} catch (Exception ex) {
			log.error("User.getAdminByEmailAndTokenSet", ex);
			return new AccountEntity();
		}
		return user;
	}
	
	
	
}
