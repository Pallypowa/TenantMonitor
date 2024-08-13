package com.bigfoot.tenantmonitor.client.pages;

import com.bigfoot.tenantmonitor.dto.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LoginCard extends Div {
    // The ide will complain about not being able to autowire the LoginOverlay, but it's fine
    public LoginCard(LoginOverlay loginOverlay) {
        loginOverlay.addLoginListener(e -> {
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setUserName(e.getUsername());
            loginDTO.setPassword(e.getPassword());

            if (authenticate(loginDTO)) {
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

    // Raw dogging sending a request to with both the sender and receiver being the same
    // Basic authentication method
    private boolean authenticate(LoginDTO loginDTO) {
        try {
            // Create URL
            URL url = new URL("http://localhost:8080/api/v1/login");

            // Create connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Create JSON body
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonInputString = objectMapper.writeValueAsString(loginDTO);

            // Write JSON body to request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Read the response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Parse response (if necessary)
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    // Assuming the response contains an AccessTokenDTO in JSON format
                    //AccessTokenDTO uses builder, so you can't use the default constructor
                    //AccessTokenDTO accessTokenDTO = objectMapper.readValue(response.toString(), AccessTokenDTO.class);
                    // You can save the token or use it as needed
                    return true;
                }
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
