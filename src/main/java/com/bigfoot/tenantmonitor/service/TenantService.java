package com.bigfoot.tenantmonitor.service;

import com.bigfoot.tenantmonitor.dto.TenantDTO;
import com.bigfoot.tenantmonitor.model.Tenant;
import com.bigfoot.tenantmonitor.repository.TenantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TenantService {
    private final TenantRepository tenantRepository;
    private final ModelMapper modelMapper;

    public TenantService(TenantRepository tenantRepository, ModelMapper modelMapper) {
        this.tenantRepository = tenantRepository;
        this.modelMapper = modelMapper;
    }

    public List<TenantDTO> fetchAllTenants() {
        return tenantRepository
                .findAll()
                .stream()
                .map(tenant -> modelMapper.map(tenant, TenantDTO.class))
                .toList();
    }

    public TenantDTO fetchTenantById(UUID tenantId) {
        return modelMapper.map(tenantRepository
                .findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant has not been found!")), TenantDTO.class);
    }

    public void createTenant(TenantDTO tenant) {
        tenantRepository.save(modelMapper.map(tenant, Tenant.class));
    }

    public TenantDTO updateTenant(UUID tenantId, TenantDTO updatedTenant) {
        Optional<Tenant> existingTenant = tenantRepository.findById(tenantId);

        if(existingTenant.isEmpty()){
            throw new RuntimeException("Tenant has not been found!");
        }

        updatedTenant.setId(tenantId);
        tenantRepository.save(modelMapper.map(updatedTenant, Tenant.class));
        return updatedTenant;
    }

    public void deleteTenant(UUID tenantId) {
        Optional<Tenant> existingTenant = tenantRepository.findById(tenantId);

        if(existingTenant.isEmpty()){
            throw new RuntimeException("Tenant has not been found!");
        }

        tenantRepository.delete(modelMapper.map(existingTenant, Tenant.class));
    }
}
