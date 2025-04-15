package com.bitirmeproje.service.r2storage;

import com.bitirmeproje.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;

@Service
public class R2StorageService {
    private final S3Client s3Client;

    @Value("${cloudflare.r2.bucket}")
    private String bucketName;

    R2StorageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file, String filePath) {
        try {
            byte[] fileBytes = file.getBytes(); // inputStream yerine byte[]

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filePath)
                    .contentType(file.getContentType())
                    .contentLength((long) fileBytes.length)
                    .build();

            RequestBody requestBody = RequestBody.fromBytes(fileBytes);

            s3Client.putObject(putObjectRequest, requestBody);
            return filePath;

        } catch (IOException e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Dosya okuma hatası");
        } catch (S3Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, e.awsErrorDetails().errorMessage());
        }
    }



}

