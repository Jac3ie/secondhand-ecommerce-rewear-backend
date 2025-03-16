package io.muzoo.ssc.project.backend.service;

import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class StorageService {

    @Value("${gcp.bucket.name}")
    private String bucketName;

    private final Storage storage;

    public StorageService() {
        this.storage = StorageOptions.getDefaultInstance().getService();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        //steps to generate a unique file name
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        //upload file bytes to bucket
        storage.create(blobInfo, file.getBytes());

        //return the public url of the file
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }



}
