package com.bigfoot.tenantmonitor.client;

import com.bigfoot.tenantmonitor.client.layout.BaseLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = BaseLayout.class)
public class MainView extends VerticalLayout {
    public MainView() {
        VerticalLayout todosList = new VerticalLayout();
        TextField textField = new TextField();
        Button addButton = new Button("Click me");
        addButton.addClickListener(e -> {
            Checkbox checkbox = new Checkbox(textField.getValue());
            todosList.add(checkbox);
            textField.setValue("");
        });
        addButton.addClickShortcut(Key.ENTER);
        add(new H1("Todo List"), textField, addButton, todosList);
    }
}
