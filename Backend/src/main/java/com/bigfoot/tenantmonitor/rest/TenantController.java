package com.bigfoot.tenantmonitor.rest;

import com.bigfoot.tenantmonitor.dto.TenantDTO;
import com.bigfoot.tenantmonitor.service.TenantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(TenantController.API_ENDPOINT)
public class TenantController {
    public static final String API_ENDPOINT = "/api/v1/tenant";
    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping
    public ResponseEntity<List<TenantDTO>> fetchAll(){
        return ResponseEntity.ok(tenantService.fetchAllTenants());
    }

    @GetMapping("/{tenantId}")
    public ResponseEntity<TenantDTO> fetchById(@PathVariable UUID tenantId){
        return ResponseEntity.ok(tenantService.fetchTenantById(tenantId));
    }

    @PostMapping
    public ResponseEntity<Void> createProperty(@RequestBody TenantDTO tenant){
        tenantService.createTenant(tenant);
        return ResponseEntity.created(URI.create(API_ENDPOINT)).build();
    }

    @PatchMapping("/{tenantId}")
    public ResponseEntity<TenantDTO> updateProperty(@PathVariable UUID tenantId, @RequestBody TenantDTO tenant){
        TenantDTO updatedTenant = tenantService.updateTenant(tenantId, tenant);
        return ResponseEntity.ok(updatedTenant);
    }

    @DeleteMapping("/{tenantId}")
    public ResponseEntity<Void> deleteTenant(@PathVariable UUID tenantId){
        tenantService.deleteTenant(tenantId);
        return ResponseEntity.noContent().build();
    }
}
