package com.nwm.api.services;

import java.io.File;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nwm.api.utils.FLLogger;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Service
public class S3BasicService {
	
	@Value("${aws.bucketName:}")
	private String bucketName;
	
	@Value("${aws.regionName:}")
	private String region;
	
	@Autowired
	@Qualifier("s3BasicClient")
	private S3Client s3Client;
	
	protected final FLLogger logger = FLLogger.getLogger("S3BasicService");

	/**
	 * @description upload file to amazon s3 bucket using basic credentials
	 * @author duc.van
	 * @since 2025-11-06
	 */
	public String uploadFile(String localFilePath, String s3Key) throws Exception {
		try {
			if (s3Client == null) {
				throw new Exception("S3 Client is not configured. Please check AWS credentials.");
			}
			
			if (bucketName.isEmpty()) {
				throw new Exception("S3 Bucket name is not configured.");
			}
			
			File file = new File(localFilePath);
			if (!file.exists()) {
				throw new Exception("Local file does not exist: " + localFilePath);
			}
			
			logger.info("Uploading file to S3: " + s3Key);
			logger.info("Bucket: " + bucketName);
			logger.info("Local file: " + localFilePath);
			
			PutObjectRequest putObjectRequest = PutObjectRequest.builder()
					.bucket(bucketName)
					.key(s3Key)
					.contentType(getContentType(file.getName()))
					.build();
			
			PutObjectResponse response = s3Client.putObject(putObjectRequest, 
					RequestBody.fromFile(Paths.get(localFilePath)));
			
			String s3Url = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + s3Key;
			logger.info("File uploaded successfully. S3 URL: " + s3Url);
			logger.info("ETag: " + response.eTag());
			
			return s3Url;
			
		} catch (Exception e) {
			logger.error("Failed to upload file to S3: " + e.getMessage());
			throw new Exception("S3 upload failed: " + e.getMessage(), e);
		}
	}
	
	/**
	 * Get content type based on file extension
	 */
	private String getContentType(String filename) {
		String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
		
		switch (extension) {
			case "txt":
				return "text/plain";
			case "log":
				return "text/plain";
			case "xml":
				return "application/xml";
			case "json":
				return "application/json";
			case "csv":
				return "text/csv";
			case "pdf":
				return "application/pdf";
			case "jpg":
			case "jpeg":
				return "image/jpeg";
			case "png":
				return "image/png";
			case "gif":
				return "image/gif";
			case "zip":
				return "application/zip";
			default:
				return "application/octet-stream";
		}
	}
}