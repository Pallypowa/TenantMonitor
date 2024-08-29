package com.bigfoot.tenantmonitor.client.pages;

import com.bigfoot.tenantmonitor.client.BackendService;
import com.bigfoot.tenantmonitor.dto.RegistrationDTO;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SignUpCard extends Div {
    private final BackendService backendService;
    private final LoginOverlay signUpOverlay;
    private final LoginOverlay loginOverlay;

    // The ide will complain about not being able to autowire the LoginOverlay, but it's fine
    public SignUpCard(BackendService backendService, @Qualifier("signUpOverlay") LoginOverlay signUpOverlay, @Qualifier("loginOverlay") LoginOverlay loginOverlay) {
        this.backendService = backendService;
        this.signUpOverlay = signUpOverlay;
        this.loginOverlay = loginOverlay;
        LoginI18n i18n = LoginI18n.createDefault();
        LoginI18n.Form i18nForm = i18n.getForm();
        i18nForm.setSubmit("Register");
        i18nForm.setForgotPassword("Already have an account? Log in");
        i18n.setForm(i18nForm);
        signUpOverlay.setI18n(i18n);

        // Custom input fields
        EmailField emailField = new EmailField("Email");
        emailField.getElement().setAttribute("name", "email");
        TextField firstNameField = new TextField("First Name");
        firstNameField.getElement().setAttribute("name", "firstName");
        TextField lastNameField = new TextField("Last Name");
        lastNameField.getElement().setAttribute("name", "lastName");

        signUpOverlay.getCustomFormArea().add(emailField, firstNameField, lastNameField);

        signUpOverlay.addLoginListener(e -> {
            RegistrationDTO registrationDTO = new RegistrationDTO();
            registrationDTO.setFirstName(firstNameField.getValue());
            registrationDTO.setLastName(lastNameField.getValue());
            registrationDTO.setEmail(emailField.getValue());
            registrationDTO.setUserName(e.getUsername());
            registrationDTO.setPassword(e.getPassword());


            if (backendService.register(registrationDTO)) {
                Notification.show("Registration successful! Please log in.");
                // Navigate to main view or another view after successful registration
                UI.getCurrent().navigate("");
                signUpOverlay.close();
            } else {
                Notification.show("Registration failed. Please try again.", 3000, Notification.Position.TOP_END);
            }
            signUpOverlay.setEnabled(true);
        });

        signUpOverlay.addForgotPasswordListener(e -> {
            signUpOverlay.close();
            loginOverlay.setOpened(true);
        });

        signUpOverlay.setTitle(new LoginCardHeader("Tenant Monitor", event -> signUpOverlay.setOpened(false)));
        add(signUpOverlay);

        Button signUp = new Button("Sign Up");
        signUp.addClickListener(event -> signUpOverlay.setOpened(true));
        signUp.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(signUp);
    }
}
