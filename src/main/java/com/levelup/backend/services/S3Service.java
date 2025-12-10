package com.levelup.backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.region}")
    private String awsRegion;

    public S3Service(
            @Value("${aws.region}") String region,
            @Value("${aws.access-key:}") String accessKey,
            @Value("${aws.secret-key:}") String secretKey,
            @Value("${aws.session-token:}") String sessionToken) {

        Region sdkRegion = Region.of(region);
        S3Client s3;

        // AWS Academy / Temporary credentials
        if (sessionToken != null && !sessionToken.isBlank()) {
            AwsSessionCredentials creds = AwsSessionCredentials.create(accessKey, secretKey, sessionToken);
            s3 = S3Client.builder()
                    .region(sdkRegion)
                    .credentialsProvider(StaticCredentialsProvider.create(creds))
                    .build();
        }
        // Permanent credentials from properties
        else if (accessKey != null && !accessKey.isBlank() && secretKey != null && !secretKey.isBlank()) {
            AwsBasicCredentials creds = AwsBasicCredentials.create(accessKey, secretKey);
            s3 = S3Client.builder()
                    .region(sdkRegion)
                    .credentialsProvider(StaticCredentialsProvider.create(creds))
                    .build();
        }
        // Default provider (IAM role, etc.)
        else {
            s3 = S3Client.builder()
                    .region(sdkRegion)
                    .credentialsProvider(DefaultCredentialsProvider.create())
                    .build();
        }

        this.s3Client = s3;
    }

    public String subirArchivo(MultipartFile file) throws IOException {
        String original = Objects.requireNonNull(file.getOriginalFilename(), "Nombre de archivo nulo");
        String extension = "";
        int idx = original.lastIndexOf('.');
        if (idx > 0) extension = original.substring(idx);

        String filename = UUID.randomUUID() + "-" + System.currentTimeMillis() + extension;

        PutObjectRequest putReq = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putReq, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, awsRegion, filename);
    }
}
