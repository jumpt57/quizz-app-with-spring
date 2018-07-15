package com.jumpt57.quizz.config;

import java.nio.charset.Charset;
import java.util.Base64;
import javax.annotation.PostConstruct;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.jumpt57.quizz.security.AwsKeys;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    private AmazonS3ClientBuilder builder;

    @Value("${aws.key}")
    private String key;

    private BasicAWSCredentials credentials;

    @PostConstruct
    public void initS3ClientBuilder() {

        builder = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(getCredentials()))
                .withRegion(Regions.EU_WEST_3);
    }

    @Bean
    public AmazonS3Client s3() {
        return (AmazonS3Client) builder.build();
    }


    private BasicAWSCredentials getCredentials() {

        if(credentials == null){
            final String decodedKey = new String(Base64.getDecoder().decode(key), Charset.defaultCharset());

            final AwsKeys awsKeys = new Gson().fromJson(decodedKey, AwsKeys.class);

            credentials = new BasicAWSCredentials(awsKeys.getAccessKey(), awsKeys.getSecretKey());
        }


        return credentials;
    }


}
