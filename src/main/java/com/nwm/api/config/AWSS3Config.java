package com.nwm.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.nwm.api.utils.FLLogger;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AWSS3Config {
	
	@Value("${aws.storage.accessKey:}")
	private String storageAccessKey;
	
	@Value("${aws.storage.secretKey:}")
	private String storageSecretKey;
	
	@Value("${aws.regionName:}")
	private String storageRegion;
	
	@Value("${aws.bucketName:}")
	private String storageBucketName;
	
	protected final FLLogger logger = FLLogger.getLogger("AWSS3Config");
	
	@Bean("s3BasicClient")
	@Primary
	public S3Client s3BasicClient() {
		try {
			if (storageAccessKey.isEmpty() || storageSecretKey.isEmpty() || storageRegion.isEmpty()) {
				logger.info("AWS basic credentials not configured, using default credentials provider");
				return null;
			}
			
			AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(storageAccessKey, storageSecretKey);
			
			S3Client s3Client = S3Client.builder()
					.region(Region.of(storageRegion))
					.credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
					.build();
			
			logger.info("AWS S3 Basic Client configured successfully");
			return s3Client;
			
		} catch (Exception e) {
			logger.error("Failed to configure AWS S3 Basic Client: " + e.getMessage());
			return null;
		}
	}
}