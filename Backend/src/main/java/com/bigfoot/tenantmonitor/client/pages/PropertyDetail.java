package com.bigfoot.tenantmonitor.client.pages;

import com.bigfoot.tenantmonitor.client.BackendService;
import com.bigfoot.tenantmonitor.client.layout.BaseLayout;
import com.bigfoot.tenantmonitor.dto.PropertyDTO;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "property/:propertyId", layout = BaseLayout.class)
public class PropertyDetail extends Div implements BeforeEnterObserver {

    @Autowired
    private final BackendService propertyService;
    private String propertyId;

    private H1 title = new H1("Property Detail");
    private H1 name = new H1("Property Name");

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getRouteParameters().get("propertyId").isEmpty()){
            throw new IllegalArgumentException("Property ID is required");
        }
        propertyId = event.getRouteParameters().get("propertyId").get();

        PropertyDTO currentProperty = propertyService.getProperty(propertyId);

        title.setText("Property Detail, the property ID is: " + propertyId);
        name.setText(currentProperty.getName());
    }

    public PropertyDetail(BackendService propertyService) {
        this.propertyService = propertyService;
        add(title, name);
    }
}
