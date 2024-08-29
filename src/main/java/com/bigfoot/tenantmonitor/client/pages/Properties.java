package com.bigfoot.tenantmonitor.client.pages;

import com.bigfoot.tenantmonitor.client.layout.BaseLayout;
import com.bigfoot.tenantmonitor.dto.PropertyDTO;
import com.bigfoot.tenantmonitor.service.MainService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "properties", layout = BaseLayout.class)
public class Properties extends VerticalLayout {

    public Properties(MainService propertyService) {
        Grid<PropertyDTO> grid = new Grid<>(PropertyDTO.class, false);
        grid.addColumn(PropertyDTO::getId).setHeader("ID");
        grid.addColumn(PropertyDTO::getOwnerId).setHeader("Owner ID");
        grid.addColumn(PropertyDTO::getTenantId).setHeader("Tenant ID");
        grid.addColumn(PropertyDTO::getName).setHeader("Name");
        grid.addColumn(PropertyDTO::getPrice).setHeader("Price");

        grid.addComponentColumn(property -> createStatusIcon(property.isFree()))
                .setHeader("Available");

        grid.addComponentColumn(property -> createStatusIcon(property.isFurnished()))
                .setHeader("Furnished");

        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, property) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON);
                    button.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("property/" + property.getId())));
                    button.setIcon(new Icon(VaadinIcon.EDIT));
                })).setHeader("Details");

        List<PropertyDTO> properties = propertyService.fetchAllProperties();

        grid.setItems(properties);

        add(grid);
    }

    private Icon createStatusIcon(Boolean isFree) {
        Icon icon;
        if (isFree) {
            icon = VaadinIcon.CHECK.create();
            icon.getElement().getThemeList().add("badge success");
        } else {
            icon = VaadinIcon.CLOSE_SMALL.create();
            icon.getElement().getThemeList().add("badge error");
        }
        icon.getStyle().set("padding", "var(--lumo-space-xs");
        return icon;
    }
}