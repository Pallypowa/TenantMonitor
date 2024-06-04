package com.bigfoot.tenantmonitor.service;

import com.bigfoot.tenantmonitor.dto.PropertyDTO;
import com.bigfoot.tenantmonitor.model.Property;
import com.bigfoot.tenantmonitor.repository.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final ModelMapper modelMapper;

    public PropertyService(PropertyRepository propertyRepository, ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
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
        Optional<Property> property = propertyRepository.findById(propertyId);

        if(property.isEmpty()){
            throw new RuntimeException("Property does not exist!");
        }

        return modelMapper.map(property.get(), PropertyDTO.class);
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
        Optional<Property> property = propertyRepository.findById(propertyId);

        if(property.isEmpty()){
            throw new RuntimeException("Property does not exist!");
        }

        propertyRepository.delete(property.get());

    }
}
