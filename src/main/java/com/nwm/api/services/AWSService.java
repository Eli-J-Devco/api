package com.nwm.api.services;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nwm.api.utils.FLLogger;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.FileUpload;
import software.amazon.awssdk.transfer.s3.model.UploadFileRequest;

@Service
public class AWSService {
	
	protected final FLLogger logger = FLLogger.getLogger("AWSService");

	@Value("${aws.bucketName}")
	private String bucketName;
	@Value("${aws.rootFolder}")
	private String rootFolder;
	@Value("${aws.regionName}")
	private String regionName;
	@Value("${aws.storage.accessKey}")
	private String storageAccessKey;
	@Value("${aws.storage.secretKey}")
	private String storageSecretKey;

	/**
	 * @description upload file to amazon s3 bucket
	 * @author Hung.Bui
	 * @since 2024-01-15
	 */
	
	public String uploadFile(String localFilePath, String awsFilePath) throws Exception {
		S3AsyncClient s3AsyncClient = null;
		S3TransferManager transferManager = null;

		try {
			// Create S3 client with optimized configuration
			s3AsyncClient = S3AsyncClient.builder()
				.credentialsProvider(StaticCredentialsProvider.create(
					AwsBasicCredentials.create(this.storageAccessKey, this.storageSecretKey)
				))
				.region(Region.of(this.regionName))
				.build();

			transferManager = S3TransferManager.builder()
				.s3Client(s3AsyncClient)
				.build();

			String s3Key = this.rootFolder + "/" + awsFilePath;

			UploadFileRequest uploadFileRequest = UploadFileRequest.builder()
				.putObjectRequest(b -> b.bucket(this.bucketName).key(s3Key))
				.source(Paths.get(localFilePath))
				.build();
			
			// upload file to s3 with proper handling
			FileUpload fileUpload = transferManager.uploadFile(uploadFileRequest);

			// Wait for completion
			fileUpload.completionFuture().join();

			String result = "/" + this.rootFolder + "/" + awsFilePath;

			return result;
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// Clean up resources
			if (transferManager != null) {
				try {
					transferManager.close();
				} catch (Exception e) {
					logger.warn("Error closing transfer manager: " + e.getMessage());
				}
			}
			if (s3AsyncClient != null) {
				try {
					s3AsyncClient.close();
				} catch (Exception e) {
					logger.warn("Error closing S3 client: " + e.getMessage());
				}
			}
		}
	}
	
}