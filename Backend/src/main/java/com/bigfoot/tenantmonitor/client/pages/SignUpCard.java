package com.bigfoot.tenantmonitor.client.pages;

import com.bigfoot.tenantmonitor.dto.RegistrationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SignUpCard extends Div {
    // The ide will complain about not being able to autowire the LoginOverlay, but it's fine
    public SignUpCard(LoginOverlay signUpOverlay, LoginOverlay loginOverlay) {
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

            if (register(registrationDTO)) {
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

    // Registration method
    private boolean register(RegistrationDTO registrationDTO) {
        try {
            // Create URL
            URL url = new URL("http://localhost:8080/api/v1/register");

            // Create connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Create JSON body
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonInputString = objectMapper.writeValueAsString(registrationDTO);

            // Write JSON body to request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Read the response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                // Registration successful (204 No Content)
                return true;
            } else {
                // Handle error response
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
