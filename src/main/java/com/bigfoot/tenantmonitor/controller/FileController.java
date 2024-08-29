package com.bigfoot.tenantmonitor.controller;

import com.bigfoot.tenantmonitor.dto.FileResponse;
import com.bigfoot.tenantmonitor.service.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class FileController {
    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/file")
    public ResponseEntity<Void> createFile(@RequestParam("file") MultipartFile file, @RequestParam("objectId") String objectId){
        //TODO add some business logic to check if the property belongs to the user...
        fileStorageService.uploadFile(file, objectId);
        return ResponseEntity.created(URI.create("/api/v1/file")).build();
    }

    @GetMapping("/file/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable UUID fileId){
        FileResponse file = fileStorageService.getFile(fileId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("file-name", file.getFileName());
        headers.add("file-size", file.getFileSize().toString());
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(file.getStream());
    }

    @DeleteMapping("/file/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable UUID fileId){
        fileStorageService.deleteFile(fileId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
