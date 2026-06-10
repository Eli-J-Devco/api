/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class FTPEntity {
	private int id;
	private String name;
	private String ftpServer;
	private String ftpUser;
	private String ftpPass;
	private String ftpPort;
	private String ftpFolder;
	
	// Constructors
	public FTPEntity() {}
	
	public FTPEntity(int id, String name, String ftpServer, String ftpUser, String ftpPass, String ftpPort, String ftpFolder) {
		this.id = id;
		this.name = name;
		this.ftpServer = ftpServer;
		this.ftpUser = ftpUser;
		this.ftpPass = ftpPass;
		this.ftpPort = ftpPort;
		this.ftpFolder = ftpFolder;
	}
	
	// Getters and Setters
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFtpServer() {
		return ftpServer;
	}
	
	public void setFtpServer(String ftpServer) {
		this.ftpServer = ftpServer;
	}
	
	public String getFtpUser() {
		return ftpUser;
	}
	
	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}
	
	public String getFtpPass() {
		return ftpPass;
	}
	
	public void setFtpPass(String ftpPass) {
		this.ftpPass = ftpPass;
	}
	
	public String getFtpPort() {
		return ftpPort;
	}
	
	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort;
	}
	
	public String getFtpFolder() {
		return ftpFolder;
	}
	
	public void setFtpFolder(String ftpFolder) {
		this.ftpFolder = ftpFolder;
	}
	
	@Override
	public String toString() {
		return "FTPEntity{" +
				"id=" + id +
				", name='" + name + '\'' +
				", ftpServer='" + ftpServer + '\'' +
				", ftpUser='" + ftpUser + '\'' +
				", ftpPass='" + ftpPass + '\'' +
				", ftpPort='" + ftpPort + '\'' +
				", ftpFolder='" + ftpFolder + '\'' +
				'}';
	}
}
