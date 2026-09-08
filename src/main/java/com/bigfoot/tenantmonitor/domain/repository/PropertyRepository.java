package com.bigfoot.tenantmonitor.domain.repository;

import com.bigfoot.tenantmonitor.domain.model.Property;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID> {
}
