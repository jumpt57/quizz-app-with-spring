package com.jumpt57.quizz.services;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileArchiveService {

    private static final String USERNAME_METADATA = "username";

    private final AmazonS3Client s3;

    private final Logger logger;

    @Value("${aws.bucket}")
    private String bucket;

    public String createFile(@NotNull InputStream file, @NotNull String username) {

        logger.info("Creating new file for S3 storage");

        final UUID key = UUID.randomUUID();

        logger.info("New file name is {}", key.toString());

        final ObjectMetadata om = new ObjectMetadata();
        om.addUserMetadata(USERNAME_METADATA, Base64.encodeAsString(username.getBytes(Charset.defaultCharset())));

        s3.putObject(new PutObjectRequest(bucket, key.toString(), file, om)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return s3.getResourceUrl(bucket, key.toString());
    }

}
