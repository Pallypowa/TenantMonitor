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
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MainService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final FileMappingRepository fileMappingRepository;

    public List<PropertyDTO> fetchAllProperties() {
        final User user = userRepository.findByUserName(getLoggedInUserName()).orElseThrow();

        return propertyRepository
                .findAll()
                .stream()
                .filter(property -> property.getOwner().getId().equals(user.getId()))
                .map(property -> modelMapper.map(property, PropertyDTO.class))
                .toList();
    }

    public PropertyDTO fetchPropertyById(UUID propertyId) {
        Property property = getProperty(propertyId);
        return modelMapper.map(property, PropertyDTO.class);
    }

    public void createProperty(PropertyDTO property) {
        final User user = userRepository.findByUserName(getLoggedInUserName()).orElseThrow();
        if(property.getOwnerId() != null) {
            throw new RuntimeException("Owner id has to be null");
        }
        property.setOwnerId(user.getId());
        propertyRepository.save(modelMapper.map(property, Property.class));
    }


    public PropertyDTO updateProperty(UUID propertyId, PropertyDTO updatedProperty) {
        final User user = userRepository.findByUserName(getLoggedInUserName()).orElseThrow();
        final Property property = getProperty(propertyId);

        if(!property.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("Can't update!");
        }

        updatedProperty.setId(propertyId);
        Property updatedProp = modelMapper.map(updatedProperty, Property.class);
        propertyRepository.save(updatedProp);
        return updatedProperty;
    }

    public void deleteProperty(UUID propertyId) {
        final User user = userRepository.findByUserName(getLoggedInUserName()).orElseThrow();
        final Property property = getProperty(propertyId);

        if(!property.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("Can't delete!");
        }

        propertyRepository.delete(property);
    }

    public void createTenant(UUID propertyId, TenantDTO tenant) {
        final User loggedInUser = userRepository.findByUserName(getLoggedInUserName()).orElseThrow();
        Property property = getProperty(propertyId);

        if(!property.getOwner().getId().equals(loggedInUser.getId())) {
            throw new RuntimeException("Can't update!");
        }

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

    private String getLoggedInUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
