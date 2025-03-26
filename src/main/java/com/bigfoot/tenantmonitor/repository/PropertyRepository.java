package com.bigfoot.tenantmonitor.repository;

import com.bigfoot.tenantmonitor.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID> {
}
