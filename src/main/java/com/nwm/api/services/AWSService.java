package com.nwm.api.services;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.sts.auth.StsAssumeRoleCredentialsProvider;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.FileUpload;
import software.amazon.awssdk.transfer.s3.model.UploadFileRequest;

@Service
public class AWSService {
	
	@Value("${aws.bucketName}")
	private String bucketName;
	@Value("${aws.rootFolder}")
	private String rootFolder;
	@Value("${aws.regionName}")
	private String regionName;
	
	@Autowired
	StsAssumeRoleCredentialsProvider credentialsProvider;

	/**
	 * @description upload file to amazon s3 bucket
	 * @author Hung.Bui
	 * @since 2024-01-15
	 */
	
	public String uploadFile(String localFilePath, String awsFilePath) throws Exception {
		try (
			S3AsyncClient s3AsyncClient = S3AsyncClient.builder()
				.credentialsProvider(credentialsProvider)
				.region(Region.of(this.regionName))
				.build();
		) {
			
			S3TransferManager transferManager = S3TransferManager.builder().s3Client(s3AsyncClient).build();
			UploadFileRequest uploadFileRequest = UploadFileRequest.builder()
				.putObjectRequest(b -> b.bucket(this.bucketName).key(this.rootFolder + "/" + awsFilePath))
				.source(Paths.get(localFilePath))
				.build();
			
			
			// upload file to s3
			FileUpload fileUpload = transferManager.uploadFile(uploadFileRequest);
			fileUpload.completionFuture().join();
			
			return this.rootFolder + "/" + awsFilePath;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
}
