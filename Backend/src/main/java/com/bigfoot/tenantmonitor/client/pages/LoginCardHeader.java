package com.bigfoot.tenantmonitor.client.pages;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class LoginCardHeader extends HorizontalLayout {
    public LoginCardHeader(String title, ComponentEventListener<ClickEvent<Button>> event) {
        H1 titleH1 = new H1(title);
        titleH1.getStyle().setColor("var(--lumo-primary-contrast-color)");
        // Icon button using a tooltip to provide textual description
        // of the action that it triggers
        Button closeButton = new Button(new Icon(VaadinIcon.CLOSE_SMALL), event);
        closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_ICON);
        closeButton.setAriaLabel("Close");
        closeButton.setTooltipText("Close the dialog");

        add(titleH1, closeButton);
    }
}
