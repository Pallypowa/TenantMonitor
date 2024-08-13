package com.bigfoot.tenantmonitor.client;

import com.bigfoot.tenantmonitor.client.pages.LoginCard;
import com.bigfoot.tenantmonitor.client.pages.SignUpCard;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class NavBar extends HorizontalLayout {
    public NavBar() {
        // Centralized LoginOverlay instances
        LoginOverlay loginOverlay = new LoginOverlay();
        LoginOverlay signUpOverlay = new LoginOverlay();

        // Initialize LoginCard and SignUpCard with these overlays
        SignUpCard signUpCard = new SignUpCard(signUpOverlay, loginOverlay);
        LoginCard loginCard = new LoginCard(loginOverlay);

        Button home = new Button("Home", e -> UI.getCurrent().navigate(""));
        Button about = new Button("About", e -> UI.getCurrent().navigate("about"));
        Button contact = new Button("Contact", e -> UI.getCurrent().navigate("contact"));
        Button properties = new Button("Properties", e -> UI.getCurrent().navigate("properties"));

        setWidthFull();
        setPadding(true);
        setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);
        add(home, about, contact, properties, signUpCard, loginCard);
    }
}
