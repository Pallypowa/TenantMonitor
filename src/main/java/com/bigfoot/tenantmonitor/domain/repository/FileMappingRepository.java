package com.bigfoot.tenantmonitor.domain.repository;

import com.bigfoot.tenantmonitor.domain.model.FileMapping;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileMappingRepository extends JpaRepository<FileMapping, UUID> {
}
