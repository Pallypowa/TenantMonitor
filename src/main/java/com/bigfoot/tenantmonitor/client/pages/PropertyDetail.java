package com.bigfoot.tenantmonitor.client.pages;

import com.bigfoot.tenantmonitor.client.BackendService;
import com.bigfoot.tenantmonitor.client.layout.BaseLayout;
import com.bigfoot.tenantmonitor.dto.PropertyDTO;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Route(value = "property/:propertyId", layout = BaseLayout.class)
public class PropertyDetail extends Div implements BeforeEnterObserver {

    @Autowired
    private final BackendService propertyService;
    private String propertyId;

    private Button editButton = new Button("Edit");
    private Button saveButton = new Button("Save");
    private H1 nameLabel = new H1("Property Name");

    // Fields for view mode
    private Span nameText = new Span();
    private Span zipcodeText = new Span();
    private Span streetText = new Span();

    // Fields for edit mode
    private TextField nameField = new TextField("Name");
    private TextField zipcodeField = new TextField("Zipcode");
    private TextField streetField = new TextField("Street");

    private boolean editing = false;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        editButton.addClickListener(e -> toggleEditMode(true));
        saveButton.addClickListener(e -> saveProperty());

        event.getRouteParameters().get("propertyId")
                .ifPresentOrElse(
                        id -> {
                            this.propertyId = id;
                            loadPropertyDetails();
                        },
                        () -> {
                            throw new IllegalArgumentException("Property ID is required");
                        }
                );
    }

    public PropertyDetail(BackendService propertyService) {
        this.propertyService = propertyService;

        // Layout setup
        HorizontalLayout buttonLayout = new HorizontalLayout(editButton, saveButton);
        VerticalLayout viewLayout = new VerticalLayout(nameLabel, nameText, zipcodeText, streetText);
        VerticalLayout editLayout = new VerticalLayout(nameField, zipcodeField, streetField);

        saveButton.setVisible(false);
        add(buttonLayout, viewLayout, editLayout);

        // Initial state - view mode
        toggleEditMode(false);
    }

    private void loadPropertyDetails() {
        PropertyDTO currentProperty = propertyService.getProperty(propertyId);

        if (currentProperty == null) {
            nameLabel.setText("Property not found (or token expired)");
            return;
        }

        nameLabel.setText("Property: " + currentProperty.getName());

        // Populate fields in view mode
        nameText.setText(currentProperty.getName());
        zipcodeText.setText("Zipcode: " + currentProperty.getZipcode());
        streetText.setText("Street: " + currentProperty.getStreet());

        // Populate fields in edit mode
        nameField.setValue(currentProperty.getName());
        zipcodeField.setValue(String.valueOf(currentProperty.getZipcode()));
        streetField.setValue(currentProperty.getStreet());
    }

    private void toggleEditMode(boolean editMode) {
        this.editing = editMode;

        // Toggle visibility of components
        nameText.setVisible(!editMode);
        zipcodeText.setVisible(!editMode);
        streetText.setVisible(!editMode);

        nameField.setVisible(editMode);
        zipcodeField.setVisible(editMode);
        streetField.setVisible(editMode);

        saveButton.setVisible(editMode);
        editButton.setVisible(!editMode);
    }

    private void saveProperty() {
        PropertyDTO propertyToUpdate = new PropertyDTO();
        propertyToUpdate.setId(UUID.fromString(propertyId));
        propertyToUpdate.setName(nameField.getValue());
        propertyToUpdate.setZipcode(Integer.parseInt(zipcodeField.getValue()));
        propertyToUpdate.setStreet(streetField.getValue());

        // Call service to save the property
        propertyService.updateProperty(propertyToUpdate);

        // Refresh view with updated data
        loadPropertyDetails();
        toggleEditMode(false);
    }
}
