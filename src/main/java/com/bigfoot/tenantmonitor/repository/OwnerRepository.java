package com.bigfoot.tenantmonitor.repository;

import com.bigfoot.tenantmonitor.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OwnerRepository extends JpaRepository<Owner, UUID> {
}
