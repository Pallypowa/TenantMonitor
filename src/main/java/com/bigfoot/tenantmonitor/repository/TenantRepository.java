package com.bigfoot.tenantmonitor.repository;

import com.bigfoot.tenantmonitor.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {
}
