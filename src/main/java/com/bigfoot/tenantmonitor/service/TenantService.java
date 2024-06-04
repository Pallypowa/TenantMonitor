package com.bigfoot.tenantmonitor.service;

import com.bigfoot.tenantmonitor.dto.TenantDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TenantService {

    private final List<TenantDTO> tenants = new ArrayList<>();

    public List<TenantDTO> fetchAllTenants() {
        return tenants;
    }

    public TenantDTO fetchTenantById(UUID tenantId) {
        return tenants.stream()
                .filter(tenant -> tenant.getId().equals(tenantId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tenant not found"));
    }

    public void createTenant(TenantDTO tenant) {
        UUID tenantId = UUID.randomUUID();
        tenant.setId(tenantId);
        tenants.add(tenant);
    }

    public TenantDTO updateTenant(UUID tenantId, TenantDTO updatedTenant) {
        for (int i = 0; i < tenants.size(); i++) {
            if (tenants.get(i).getId().equals(tenantId)) {
                updatedTenant.setId(tenantId);
                tenants.set(i, updatedTenant);
                return updatedTenant;
            }
        }
        throw new RuntimeException("Tenant not found");
    }

    public void deleteTenant(UUID tenantId) {
        boolean removed = tenants.removeIf(tenant -> tenant.getId().equals(tenantId));
        if (!removed) {
            throw new RuntimeException("Tenant not found");
        }
    }
}
