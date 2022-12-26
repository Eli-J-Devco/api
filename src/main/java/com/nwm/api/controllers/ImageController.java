/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import springfox.documentation.annotations.ApiIgnore;
@RestController
@ApiIgnore
@RequestMapping("/image")
public class ImageController  extends BaseController {
	/**
	 * show image action
	 * @param path
	 * @param fileName
	 * @return
	 */
	@GetMapping("/{path}/{filename}")
	public byte[] showImageAction (@PathVariable ("path") String path, @PathVariable ("filename") String fileName) {
		try {
		String imgPath = uploadRootPath() + "/"+path+"/"+fileName;
		InputStream in = new FileInputStream(imgPath);
			    return IOUtils.toByteArray(in);
		}catch (Exception e) {
			return null;
		}
		
	}
}