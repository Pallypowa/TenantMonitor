package com.bigfoot.tenantmonitor.client.pages;

import com.bigfoot.tenantmonitor.client.BackendService;
import com.bigfoot.tenantmonitor.client.layout.BaseLayout;
import com.bigfoot.tenantmonitor.dto.PropertyDTO;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.List;

@Route(value = "endpointTest", layout = BaseLayout.class)
public class EndpointTestPage extends VerticalLayout {

    private final BackendService backendService;

    public EndpointTestPage(BackendService backendService){
        this.backendService = backendService;
        H1 title = new H1("Endpoint test buttons");
        VerticalLayout propertiesList = new VerticalLayout();
        Button getProperties = new Button("Get properties");
        getProperties.addClickListener(e -> {
            try {
                List<PropertyDTO> properties = backendService.getProperties();
                properties.forEach(p -> propertiesList.add(new H2(p.toString())));
            } catch (Exception exc){
                add(new H1(Arrays.toString(exc.getStackTrace())));
            }

        });


        add(title, getProperties, propertiesList);
    }
}
