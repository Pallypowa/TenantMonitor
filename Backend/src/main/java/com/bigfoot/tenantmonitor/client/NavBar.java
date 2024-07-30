package com.bigfoot.tenantmonitor.client;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class NavBar extends HorizontalLayout {
    public NavBar() {
        Button home = new Button("Home", e -> UI.getCurrent().navigate(""));
        Button about = new Button("About", e -> UI.getCurrent().navigate("about"));
        Button contact = new Button("Contact", e -> UI.getCurrent().navigate("contact"));
        Button signUp = new Button("Sign Up", e -> UI.getCurrent().navigate("signup"));
        Button login = new Button("Login", e -> UI.getCurrent().navigate("login"));

        add(home, about, contact, signUp, login);
    }
}
