package com.bigfoot.tenantmonitor.client;

import com.bigfoot.tenantmonitor.client.pages.LoginCard;
import com.bigfoot.tenantmonitor.client.pages.SignUpCard;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class NavBar extends HorizontalLayout {
    private final LoginCard loginCard;
    private final SignUpCard signUpCard;

    public NavBar(LoginCard loginCard, SignUpCard signUpCard) {
        this.loginCard = loginCard;
        this.signUpCard = signUpCard;
        Button home = new Button("Home", e -> UI.getCurrent().navigate(""));
        Button about = new Button("About", e -> UI.getCurrent().navigate("about"));
        Button contact = new Button("Contact", e -> UI.getCurrent().navigate("contact"));
        Button properties = new Button("Properties", e -> UI.getCurrent().navigate("properties"));
        Button test = new Button("Test", e -> UI.getCurrent().navigate("endpointTest"));

        setWidthFull();
        setPadding(true);
        setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);
        add(home, about, contact, test, properties ,signUpCard, loginCard);
    }
}
