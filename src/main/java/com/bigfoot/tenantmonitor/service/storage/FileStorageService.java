package com.bigfoot.tenantmonitor.service.storage;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileStorageService {
    void uploadFile(MultipartFile file, String objectId);
    FileResponse getFile(UUID id) ;
    void deleteFile(UUID id) ;
}
