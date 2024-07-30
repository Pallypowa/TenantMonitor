package com.bigfoot.tenantmonitor.client.layout;

import com.bigfoot.tenantmonitor.client.NavBar;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;

public class BaseLayout extends AppLayout implements RouterLayout {
    public BaseLayout() {
        NavBar navBar = new NavBar();
        addToNavbar(navBar);
    }

    public void setViewContent(VerticalLayout content) {
        setContent(content);
    }
}
