package com.bigfoot.tenantmonitor.service;

import com.bigfoot.tenantmonitor.dto.PropertyDTO;
import com.bigfoot.tenantmonitor.dto.TenantDTO;
import com.bigfoot.tenantmonitor.exception.ErrorCode;
import com.bigfoot.tenantmonitor.exception.PropertyException;
import com.bigfoot.tenantmonitor.model.FileMapping;
import com.bigfoot.tenantmonitor.model.Property;
import com.bigfoot.tenantmonitor.model.User;
import com.bigfoot.tenantmonitor.repository.FileMappingRepository;
import com.bigfoot.tenantmonitor.repository.PropertyRepository;
import com.bigfoot.tenantmonitor.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MainService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final FileMappingRepository fileMappingRepository;

    public MainService(PropertyRepository propertyRepository,
                       UserRepository userRepository,
                       ModelMapper modelMapper,
                       FileMappingRepository fileMappingRepository) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.fileMappingRepository = fileMappingRepository;
    }

    public List<PropertyDTO> fetchAllProperties() {
        return propertyRepository
                .findAll()
                .stream()
                .map(property -> modelMapper.map(property, PropertyDTO.class))
                //.peek(propertyDTO -> propertyDTO.setFiles(getFilesForProperty(propertyDTO.getId())))
                .toList();
    }

    public PropertyDTO fetchPropertyById(UUID propertyId) {
        Property property = getProperty(propertyId);
        return modelMapper.map(property, PropertyDTO.class);
    }

    public void createProperty(PropertyDTO property) {
        propertyRepository.save(modelMapper.map(property, Property.class));
    }


    public PropertyDTO updateProperty(UUID propertyId, PropertyDTO updatedProperty) {
        Optional<Property> property = propertyRepository.findById(propertyId);

        if(property.isEmpty()){
            throw new PropertyException(ErrorCode.PropertyDoesNotExist, "Property does not exist!");
        }

        updatedProperty.setId(propertyId);
        Property updatedProp = modelMapper.map(updatedProperty, Property.class);
        propertyRepository.save(updatedProp);
        return updatedProperty;
    }

    public void deleteProperty(UUID propertyId) {
        Property property = getProperty(propertyId);
        propertyRepository.delete(property);
    }

    public void createTenant(UUID propertyId, TenantDTO tenant) {
        Property property = getProperty(propertyId);

        if(!isPropertyFree(property)){
            throw new PropertyException(ErrorCode.PropertyAlreadyTaken, "Property is already taken!");
        }

        User user = modelMapper.map(tenant, User.class);
        userRepository.save(user);
        property.setTenant(user);
        propertyRepository.save(property);
    }

    private boolean isPropertyFree(Property property){
        return property.getTenant() == null;
    }

    private Property getProperty(UUID propertyId){
        Optional<Property> property = propertyRepository.findById(propertyId);

        if(property.isEmpty()){
            throw new PropertyException(ErrorCode.PropertyDoesNotExist, "Property does not exist!");
        }
        return property.get();
    }

    private List<FileMapping> getFilesForProperty(UUID id){
        return fileMappingRepository.findAll().stream().filter(fileMapping -> fileMapping.getObject_id().equals(id)).toList();
    }
}
