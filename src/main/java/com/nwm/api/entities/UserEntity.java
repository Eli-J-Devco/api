/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.ArrayList;
import java.util.Collection;
//import java.util.Date;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEntity implements UserDetails, Authentication{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String user_name;
	private String password;
	private String first_name;
	private String last_name;
	private String address;
	private String phone;
	private String email;
	private String birthday;
	private String avatar;
	private String status;
	private String is_delete;
	private String created_date;
	private String updated_date;
	private String roles;
	private String name;
	private int limit;
	private int offset;
	private int totalRecord;
	private String full_name;
	private int id_role;
	private String hash_id_user;
	private String logo;
	private List permissions;
	private String id_sites;
	private String customer_type;
	private String token_setpassword;
	private int is_technical;
	private int id_user;
	private String table_column;
	
	
	

	public String getTable_column() {
		return table_column;
	}
	public void setTable_column(String table_column) {
		this.table_column = table_column;
	}
	public int getId_user() {
		return id_user;
	}
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	public int getIs_technical() {
		return is_technical;
	}
	public void setIs_technical(int is_technical) {
		this.is_technical = is_technical;
	}
	public String getHash_id_user() {
		return hash_id_user;
	}
	public void setHash_id_user(String hash_id_user) {
		this.hash_id_user = hash_id_user;
	}
	private Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
	/**
	 * @return the user_name
	 */
	public String getUser_name() {
		return user_name;
	}
	/**
	 * @param user_name the user_name to set
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	/**
	 * @return the first_name
	 */
	public String getFirst_name() {
		return first_name;
	}
	/**
	 * @param first_name the first_name to set
	 */
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	/**
	 * @return the last_name
	 */
	public String getLast_name() {
		return last_name;
	}
	/**
	 * @param last_name the last_name to set
	 */
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}
	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the is_delete
	 */
	public String getIs_delete() {
		return is_delete;
	}
	/**
	 * @param is_delete the is_delete to set
	 */
	public void setIs_delete(String is_delete) {
		this.is_delete = is_delete;
	}
	
	/**
	 * @return the created_date
	 */
	public String getCreated_date() {
		return created_date;
	}
	/**
	 * @param created_date the created_date to set
	 */
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	/**
	 * @return the updated_date
	 */
	public String getUpdated_date() {
		return updated_date;
	}
	/**
	 * @param updated_date the updated_date to set
	 */
	public void setUpdated_date(String updated_date) {
		this.updated_date = updated_date;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return this;
	}
	@Override
	public Object getDetails() {
		// TODO Auto-generated method stub
		return this;
	}
	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return this;
	}
	@Override
	public boolean isAuthenticated() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.getUser_name();
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public String getName() {
		return this.getUser_name();
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String role) {
		this.roles = role;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public int getId_role() {
		return id_role;
	}
	public void setId_role(int id_role) {
		this.id_role = id_role;
	}
	/**
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}
	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
	/**
	 * @return the permissions
	 */
	public List getPermissions() {
		return permissions;
	}
	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(List permissions) {
		this.permissions = permissions;
	}
	/**
	 * @return the id_sites
	 */
	public String getId_sites() {
		return id_sites;
	}
	/**
	 * @param id_sites the id_sites to set
	 */
	public void setId_sites(String id_sites) {
		this.id_sites = id_sites;
	}
	/**
	 * @return the customer_type
	 */
	public String getCustomer_type() {
		return customer_type;
	}
	/**
	 * @param customer_type the customer_type to set
	 */
	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}
	/**
	 * @return the token_setpassword
	 */
	public String getToken_setpassword() {
		return token_setpassword;
	}
	/**
	 * @param token_setpassword the token_setpassword to set
	 */
	public void setToken_setpassword(String token_setpassword) {
		this.token_setpassword = token_setpassword;
	}
	
	
}
