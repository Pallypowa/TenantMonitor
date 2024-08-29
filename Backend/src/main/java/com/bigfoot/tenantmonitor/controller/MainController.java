package com.bigfoot.tenantmonitor.controller;

import com.bigfoot.tenantmonitor.dto.FileResponse;
import com.bigfoot.tenantmonitor.dto.PropertyDTO;
import com.bigfoot.tenantmonitor.dto.TenantDTO;
import com.bigfoot.tenantmonitor.service.MainService;
import com.bigfoot.tenantmonitor.service.MinioService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/property")
public class MainController {
    private final MainService propertyService;

    public MainController(MainService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping
    public ResponseEntity<List<PropertyDTO>> fetchAll(){
        return ResponseEntity.ok(propertyService.fetchAllProperties());
    }

    @PostMapping
    public ResponseEntity<Void> createProperty(@RequestBody PropertyDTO property){
        propertyService.createProperty(property);
        return ResponseEntity.created(URI.create("/api/v1/property")).build();
    }

    @PatchMapping("/{propertyId}")
    public ResponseEntity<PropertyDTO> updateProperty(@PathVariable UUID propertyId, @RequestBody PropertyDTO property){
        PropertyDTO updatedProperty = propertyService.updateProperty(propertyId, property);
        return ResponseEntity.ok(updatedProperty);
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<Void> deleteProperty(@PathVariable UUID propertyId){
        propertyService.deleteProperty(propertyId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{propertyId}/tenant")
    public ResponseEntity<Void> createTenant(@PathVariable UUID propertyId, @RequestBody TenantDTO tenant){
        propertyService.createTenant(propertyId, tenant);
        return ResponseEntity.created(URI.create("/api/v1/property")).build();
    }

}
