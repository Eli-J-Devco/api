package com.nwm.api.services;

import java.net.URL;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
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
			
			System.out.println("localFilePath: " + localFilePath);
			System.out.println("awsFilePath: " + awsFilePath);
			System.out.println("rootFolder: " + this.rootFolder);
			
			System.out.println("regionName: " + this.regionName);
			
			
			// upload file to s3
			FileUpload fileUpload = transferManager.uploadFile(uploadFileRequest);
			fileUpload.completionFuture().join();
			
			// get file url from s3
			GetUrlRequest objectUrlRequest = GetUrlRequest.builder().key(this.rootFolder + "/" + awsFilePath).bucket(this.bucketName).build();
			URL objecUrl = s3AsyncClient.utilities().getUrl(objectUrlRequest);
			
			System.out.println("objecUrl.toString(): " + objecUrl.toString());
			
			return objecUrl.toString();
		} catch (Exception e) {
			System.out.println("e: " + e);
			throw new Exception(e);
		}
	}
	
}
