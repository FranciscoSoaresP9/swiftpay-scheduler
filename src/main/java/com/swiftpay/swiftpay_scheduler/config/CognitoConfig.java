package com.swiftpay.swiftpay_scheduler.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CognitoConfig {
    @Value(value = "${aws.accessKey}")
    private String accessKey;

    @Value(value = "${aws.secretAccessKey}")
    private String secretKey;

    @Value(value = "${aws.cognito.region}")
    private String region;

    @Bean
    public AWSCognitoIdentityProvider cognitoClient() {

        var awsCreds = new BasicAWSCredentials(accessKey, secretKey);

        return AWSCognitoIdentityProviderClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(region)
                .build();
    }
}
