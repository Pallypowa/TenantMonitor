package com.bigfoot.tenantmonitor.rest;

import com.bigfoot.tenantmonitor.dto.PropertyDTO;
import com.bigfoot.tenantmonitor.service.PropertyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(PropertyController.API_ENDPOINT)
public class PropertyController {
    public static final String API_ENDPOINT = "/api/v1/property";
    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping
    public ResponseEntity<List<PropertyDTO>> fetchAll(){
        return ResponseEntity.ok(propertyService.fetchAllProperties());
    }

    @GetMapping("/{propertyId}")
    public ResponseEntity<PropertyDTO> fetchById(@PathVariable UUID propertyId){
        return ResponseEntity.ok(propertyService.fetchPropertyById(propertyId));
    }

    @PostMapping
    public ResponseEntity<Void> createProperty(@RequestBody PropertyDTO property){
        propertyService.createProperty(property);
        return ResponseEntity.created(URI.create(API_ENDPOINT)).build();
    }

    @PatchMapping("/{propertyId}")
    public ResponseEntity<PropertyDTO> updateProperty(@PathVariable UUID propertyId, @RequestBody PropertyDTO property){
        PropertyDTO updatedProperty = propertyService.updateProperty(propertyId, property);
        return ResponseEntity.ok(updatedProperty);
    }

    @DeleteMapping("{propertyId}")
    public ResponseEntity<Void> deleteProperty(@PathVariable UUID propertyId){
        propertyService.deleteProperty(propertyId);
        return ResponseEntity.noContent().build();
    }

}
