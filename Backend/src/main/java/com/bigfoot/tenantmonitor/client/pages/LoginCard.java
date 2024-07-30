package com.bigfoot.tenantmonitor.client.pages;

import com.bigfoot.tenantmonitor.client.layout.BaseLayout;
import com.bigfoot.tenantmonitor.dto.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Route(value = "login", layout = BaseLayout.class)
public class LoginCard extends VerticalLayout {
    public LoginCard() {
        // Add a header
        H1 header = new H1("Login");

        // Create input fields
        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");

        // Create login button
        Button loginButton = new Button("Login");
        loginButton.addClickListener(e -> {
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setUserName(usernameField.getValue());
            loginDTO.setPassword(passwordField.getValue());

            if (authenticate(loginDTO)) {
                Notification.show("Login successful!");
                // Navigate to main view or another view after successful login
                UI.getCurrent().navigate("");
            } else {
                Notification.show("Invalid username or password", 3000, Notification.Position.TOP_END);
            }
        });

        // Add components to the layout
        add(header, usernameField, passwordField, loginButton);
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
