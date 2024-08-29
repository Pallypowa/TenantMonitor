package com.bigfoot.tenantmonitor.client.pages;

import com.bigfoot.tenantmonitor.client.layout.BaseLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "about", layout = BaseLayout.class)
public class About extends VerticalLayout {
    public About() {
        add(new H1("About"));
    }
}
