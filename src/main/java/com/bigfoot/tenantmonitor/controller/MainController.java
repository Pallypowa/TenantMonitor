package com.bigfoot.tenantmonitor.controller;

import com.bigfoot.tenantmonitor.dto.PropertyDTO;
import com.bigfoot.tenantmonitor.dto.TenantDTO;
import com.bigfoot.tenantmonitor.service.MainService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/property")
@SecurityRequirement(name = "jwt")
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

    @GetMapping("/{propertyId}")
    public ResponseEntity<PropertyDTO> fetchPropertyById(@PathVariable UUID propertyId){
        return ResponseEntity.ok(propertyService.fetchPropertyById(propertyId));
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
