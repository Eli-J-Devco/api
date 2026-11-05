/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.entities;

/**
 * Tenant Billing (WEBDEV-5686): one row of bems_tenant_billing_setting, how a site receives the CSV
 */
public class TenantBillingSettingEntity {
	private int id;
	private int id_site;
	// 0: none, 1: FTP, 2: email
	private int type;
	private String ftp_host;
	private Integer ftp_port;
	private String ftp_user_name;
	// encrypted by the FE (CryptoJS AES with the tenant billing key), decrypted only to connect to the FTP
	private String ftp_password;
	private String receive_email;
	// day of month to send (1-31), the last day in the months without it
	private Integer receive_date;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getFtp_host() {
		return ftp_host;
	}
	public void setFtp_host(String ftp_host) {
		this.ftp_host = ftp_host;
	}
	public Integer getFtp_port() {
		return ftp_port;
	}
	public void setFtp_port(Integer ftp_port) {
		this.ftp_port = ftp_port;
	}
	public String getFtp_user_name() {
		return ftp_user_name;
	}
	public void setFtp_user_name(String ftp_user_name) {
		this.ftp_user_name = ftp_user_name;
	}
	public String getFtp_password() {
		return ftp_password;
	}
	public void setFtp_password(String ftp_password) {
		this.ftp_password = ftp_password;
	}
	public String getReceive_email() {
		return receive_email;
	}
	public void setReceive_email(String receive_email) {
		this.receive_email = receive_email;
	}
	public Integer getReceive_date() {
		return receive_date;
	}
	public void setReceive_date(Integer receive_date) {
		this.receive_date = receive_date;
	}
}
