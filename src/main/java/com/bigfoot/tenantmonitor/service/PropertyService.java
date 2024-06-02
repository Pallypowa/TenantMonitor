package com.bigfoot.tenantmonitor.service;

import com.bigfoot.tenantmonitor.dto.PropertyDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PropertyService {

    private final List<PropertyDTO> properties = new ArrayList<>();

    public List<PropertyDTO> fetchAllProperties() {
        return properties;
    }

    public PropertyDTO fetchPropertyById(UUID propertyId) {
        return properties.stream()
                .filter(property -> property.getId().equals(propertyId))
                .findFirst()
                .orElse(null);
    }

    public void createProperty(PropertyDTO property) {
        properties.add(property);
    }


    public PropertyDTO updateProperty(UUID propertyId, PropertyDTO updatedProperty) {
        for (int i = 0; i < properties.size(); i++) {
            if (properties.get(i).getId().equals(propertyId)) {
                properties.set(i, updatedProperty);
                return updatedProperty;
            }
        }
        return null;
    }

    public void deleteProperty(UUID propertyId) {
        properties.removeIf(property -> property.getId().equals(propertyId));
    }
}
