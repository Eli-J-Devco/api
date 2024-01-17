package com.nwm.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nwm.api.utils.FLLogger;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.auth.StsAssumeRoleCredentialsProvider;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;

@Configuration
public class AWSCredentialConfig {
	
	@Value("${aws.roleARN}")
	private String roleARN;
	@Value("${aws.roleSessionName}")
	private String roleSessionName;
	@Value("${aws.regionName}")
	private String regionName;
	@Value("${aws.accessKey}")
	private String accessKey;
	@Value("${aws.secretKey}")
	private String secretKey;
	
	protected final FLLogger logger = FLLogger.getLogger("AWSCredentialConfig");
	
	@Bean
	public StsAssumeRoleCredentialsProvider credentialsProvider() {
        try {
        	StsClient stsClient = StsClient.builder()
    			.region(Region.of(regionName))
    			.credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
    			.build();
        	
        	AssumeRoleRequest roleRequest = AssumeRoleRequest.builder()
        		.roleArn(this.roleARN)
        		.roleSessionName(this.roleSessionName)
        		.durationSeconds(900)
        		.build();
        	
        	StsAssumeRoleCredentialsProvider credentialsProvider = StsAssumeRoleCredentialsProvider
        		.builder()
        		.stsClient(stsClient)
        		.refreshRequest(roleRequest)
        		.build();
            
            return credentialsProvider;
        } catch (Exception e) {
        	logger.error(e);
        	return null;
        }
    }
	
}
