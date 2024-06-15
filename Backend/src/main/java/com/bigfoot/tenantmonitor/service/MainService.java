package com.bigfoot.tenantmonitor.service;

import com.bigfoot.tenantmonitor.dto.PropertyDTO;
import com.bigfoot.tenantmonitor.dto.TenantDTO;
import com.bigfoot.tenantmonitor.model.Property;
import com.bigfoot.tenantmonitor.model.User;
import com.bigfoot.tenantmonitor.repository.PropertyRepository;
import com.bigfoot.tenantmonitor.repository.UserRepository;
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

    public MainService(PropertyRepository propertyRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<PropertyDTO> fetchAllProperties() {
        return propertyRepository
                .findAll()
                .stream()
                .map(property -> modelMapper.map(property, PropertyDTO.class))
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
            throw new RuntimeException("Property does not exist!");
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
            throw new RuntimeException("Property is already taken");
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
            throw new RuntimeException("Property does not exist!");
        }
        return property.get();
    }
}
