package com.bigfoot.tenantmonitor.client.pages;

import com.bigfoot.tenantmonitor.client.BackendService;
import com.bigfoot.tenantmonitor.dto.LoginDTO;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.stereotype.Component;

@Component
public class LoginCard extends Div {
    private final BackendService backendService;

    // The ide will complain about not being able to autowire the LoginOverlay, but it's fine
    public LoginCard(LoginOverlay loginOverlay, BackendService backendService) {
        this.backendService = backendService;
        loginOverlay.addLoginListener(e -> {
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setUserName(e.getUsername());
            loginDTO.setPassword(e.getPassword());

            if (backendService.authenticate(loginDTO)) {
                Notification.show("Login successful!");
                // Navigate to main view or another view after successful login
                UI.getCurrent().navigate("");
                loginOverlay.close();
            } else {
                loginOverlay.showErrorMessage("Incorrect username or password", "Please try again");
            }

            loginOverlay.setEnabled(true);
        });

        loginOverlay.setTitle(new LoginCardHeader("Tenant Monitor", event -> loginOverlay.setOpened(false)));
        loginOverlay.setDescription("Please Log in to continue");

        loginOverlay.addForgotPasswordListener(e -> {
            Notification.show("Forgot password not implemented yet");
        });

        add(loginOverlay);

        Button login = new Button("Log in");
        login.addClickListener(event -> loginOverlay.setOpened(true));
        login.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(login);
    }
}
