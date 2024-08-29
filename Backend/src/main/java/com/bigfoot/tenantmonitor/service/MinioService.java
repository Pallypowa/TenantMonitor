package com.bigfoot.tenantmonitor.service;

import com.bigfoot.tenantmonitor.dto.FileResponse;
import com.bigfoot.tenantmonitor.model.FileMapping;
import com.bigfoot.tenantmonitor.repository.FileMappingRepository;
import io.minio.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

//TODO handle exceptions in a normal manner...
@Service
public class MinioService implements FileStorageService {
    private final MinioClient minioClient;
    private final FileMappingRepository fileMappingRepository;
    private final String BUCKET_NAME = "tenantmonitor";

    public MinioService(MinioClient minioClient, FileMappingRepository fileMappingRepository) {
        this.minioClient = minioClient;
        this.fileMappingRepository = fileMappingRepository;
    }

    @Override
    public void uploadFile(MultipartFile file, String objectId){
        String id = generateNewId();
        try {
            minioClient.putObject(PutObjectArgs
                    .builder()
                    .bucket(BUCKET_NAME)
                    .object(id)
                    .contentType(file.getContentType())
                    .userMetadata(Map.of("file-name", Objects.requireNonNull(file.getOriginalFilename())))
                    .stream(file.getInputStream(), file.getInputStream().available(), -1)
                    .build());

            fileMappingRepository.save(FileMapping
                    .builder()
                    .file_id(UUID.fromString(id))
                    .object_id(UUID.fromString(objectId))
                    .file_name(file.getOriginalFilename())
                    .file_size(file.getSize())
                    .file_type(file.getContentType())
                    .created_at(LocalDateTime.now())
                    .build());

        } catch (Exception e){
            throw new RuntimeException(e);
        }


    }

    @Override
    public FileResponse getFile(UUID id) {
        try {
            GetObjectResponse file = minioClient.getObject(GetObjectArgs
                    .builder()
                    .bucket(BUCKET_NAME)
                    .object(id.toString())
                    .build());

            StatObjectResponse metadata = minioClient.statObject(StatObjectArgs
                    .builder()
                    .bucket(BUCKET_NAME)
                    .object(id.toString())
                    .build());

            return FileResponse
                    .builder()
                    .contentType(metadata.contentType())
                    .fileName(metadata.userMetadata().get("file-name"))
                    .fileSize(metadata.size())
                    .stream(new InputStreamResource(file))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFile(UUID id) {
        try {
            minioClient.removeObject(RemoveObjectArgs
                    .builder()
                    .bucket(BUCKET_NAME)
                    .object(id.toString())
                    .build());
            fileMappingRepository.deleteById(id);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    private String generateNewId(){
        return UUID.randomUUID().toString();
    }
}
