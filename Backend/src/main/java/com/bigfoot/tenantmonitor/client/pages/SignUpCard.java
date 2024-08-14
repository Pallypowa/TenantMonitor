package com.bigfoot.tenantmonitor.client.pages;

import com.bigfoot.tenantmonitor.client.BackendService;
import com.bigfoot.tenantmonitor.client.layout.BaseLayout;
import com.bigfoot.tenantmonitor.dto.RegistrationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import io.micrometer.common.util.StringUtils;

@Route(value = "signup", layout = BaseLayout.class)
public class SignUpCard extends VerticalLayout {
    private final ObjectMapper objectMapper;
    private final BackendService backendService;
    public SignUpCard(ObjectMapper objectMapper, BackendService backendService) {
        this.objectMapper = objectMapper;
        this.backendService = backendService;
        // Add a header
        H1 header = new H1("Sign Up");

        // Create input fields
        TextField firstNameField = new TextField("First Name");
        TextField lastNameField = new TextField("Last Name");
        EmailField emailField = new EmailField("Email");
        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");

        // Create sign-up button
        Button signUpButton = new Button("Sign Up");
        signUpButton.addClickListener(e -> {
            RegistrationDTO registrationDTO = new RegistrationDTO();
            registrationDTO.setFirstName(firstNameField.getValue());
            registrationDTO.setLastName(lastNameField.getValue());
            registrationDTO.setEmail(emailField.getValue());
            registrationDTO.setUserName(usernameField.getValue());
            registrationDTO.setPassword(passwordField.getValue());


            if (backendService.register(registrationDTO)) {
                Notification.show("Registration successful! Please log in.");
                // Navigate to login view or another view after successful registration
                UI.getCurrent().navigate("login");
            } else {
                Notification.show("Registration failed. Please try again.", 3000, Notification.Position.TOP_END);
            }
        });

        // Add components to the layout
        add(header, firstNameField, lastNameField, emailField, usernameField, passwordField, signUpButton);
    }

    // Registration method
//    private boolean register(RegistrationDTO registrationDTO) {
//        try {
//            // Create URL
//            URL url = new URL("http://localhost:8080/api/v1/register");
//
//            // Create connection
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type", "application/json");
//            connection.setDoOutput(true);
//
//            // Create JSON body
//            String jsonInputString = objectMapper.writeValueAsString(registrationDTO);
//
//            // Write JSON body to request
//            try (OutputStream os = connection.getOutputStream()) {
//                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
//                os.write(input, 0, input.length);
//            }
//
//            // Read the response
//            int responseCode = connection.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
//                // Registration successful (204 No Content)
//                return true;
//            } else {
//                // Handle error response
//                return false;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}
