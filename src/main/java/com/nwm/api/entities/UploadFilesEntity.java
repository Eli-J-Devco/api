/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;


public class UploadFilesEntity {
	private String file_upload;
	private String name;

	public String getFile_upload() {
		return file_upload;
	}

	public void setFile_upload(String file_upload) {
		this.file_upload = file_upload;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
