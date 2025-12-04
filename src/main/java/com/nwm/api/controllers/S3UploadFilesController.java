package com.nwm.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.nwm.api.services.S3BasicService;
import com.nwm.api.services.S3AutoUploadLogService;


@RestController
@ApiIgnore
@RequestMapping("/s3-files")
public class S3UploadFilesController extends BaseController {
	
	@Autowired
	private S3BasicService s3BasicService;
	
	@Autowired
	private S3AutoUploadLogService s3AutoUploadLogService;
	
	/**
	 * @description Upload files to S3 with organized folder structure: dataloggers/serial_number/year/month/day/filename
	 * @author duc.van
	 * @since 2025-11-06
	 */
	@PostMapping("/upload/files-to-s3")
	@ResponseBody
	public String uploadFilesToS3(HttpServletRequest request,
			@RequestParam(name = "LOGFILE", required = false) MultipartFile files[],
			@RequestParam(name = "SERIALNUMBER", required = true) String serialnumber,
			@RequestParam(name = "MODBUSPORT", required = false) String modbusport) {
		if (files == null || files.length == 0) {
			return "\nFAILURE\n";
		}
		
		if (serialnumber == null || serialnumber.trim().isEmpty()) {
			return "\nFAILURE\n";
		}

		try {
			LocalDateTime now = LocalDateTime.now();
			String year = now.format(DateTimeFormatter.ofPattern("yyyy"));
			String month = now.format(DateTimeFormatter.ofPattern("MM"));
			String day = now.format(DateTimeFormatter.ofPattern("dd"));

			for (MultipartFile file : files) {
				String originalFileName = file.getOriginalFilename();
				if (originalFileName == null || originalFileName.trim().isEmpty()) {
					return "\nFAILURE\n";
				}
				
				Path tempDir = Files.createTempDirectory("upload");
				Path tempFile = tempDir.resolve(originalFileName);
				Files.write(tempFile, file.getBytes());
				
				String s3Path = "dataloggers/" + serialnumber + "/"+ modbusport + "/" + year + "/" + month + "/" + day + "/" + originalFileName;
				
				try {
					s3BasicService.uploadFile(tempFile.toString(), s3Path);
				} catch (Exception awsException) {
					return "\nFAILURE\n";
				}
				
				Files.deleteIfExists(tempFile);
				Files.deleteIfExists(tempDir);
			}
			
			return "\nSUCCESS\n";

		} catch (Exception e) {
			return "\nFAILURE\n";
		}
	}
	
	/**
	 * @description Trigger thủ công để upload tất cả file log từ thư mục uploads lên S3
	 * @author duc.van
	 * @since 2025-12-04
	 */
	@PostMapping("/upload/auto-upload-logs")
	@ResponseBody
	public Object autoUploadLogs() {
		try {
			int uploadedCount = s3AutoUploadLogService.uploadAllLogFiles();
			
			if (uploadedCount > 0) {
				return this.jsonResult(true, null, "Successfully uploaded " + uploadedCount + " log files to S3", 1);
			} else {
				return this.jsonResult(false, null, "No log files found to upload", 0);
			}
			
		} catch (Exception e) {
			return this.jsonResult(false, null, "Error uploading log files: " + e.getMessage(), 0);
		}
	}




}