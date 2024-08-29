package com.bigfoot.tenantmonitor.repository;

import com.bigfoot.tenantmonitor.model.FileMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileMappingRepository extends JpaRepository<FileMapping, UUID> {
}
