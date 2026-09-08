package com.bigfoot.tenantmonitor.web.controller;

import com.bigfoot.tenantmonitor.domain.model.Property;
import com.bigfoot.tenantmonitor.web.dto.property.PropertyDTO;
import com.bigfoot.tenantmonitor.web.dto.tenant.TenantDTO;
import com.bigfoot.tenantmonitor.service.PropertyService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/property")
@SecurityRequirement(name = "jwt")
public class PropertyController {
    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
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
