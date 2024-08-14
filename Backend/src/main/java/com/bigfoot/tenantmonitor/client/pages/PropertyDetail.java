package com.bigfoot.tenantmonitor.client.pages;

import com.bigfoot.tenantmonitor.client.layout.BaseLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "property/:propertyId", layout = BaseLayout.class)
public class PropertyDetail extends Div implements BeforeEnterObserver {

    private String propertyId;

    private H1 title = new H1("Property Detail");

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getRouteParameters().get("propertyId").isEmpty()){
            throw new IllegalArgumentException("Property ID is required");
        }
        propertyId = event.getRouteParameters().get("propertyId").get();
        title.setText("Property Detail, the property ID is: " + propertyId);
    }

    public PropertyDetail() {
        add(title);
    }
}
