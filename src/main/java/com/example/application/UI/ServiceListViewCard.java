package com.example.application.UI;

import com.example.application.backend.entity.Service;
import com.example.application.backend.service.ServiceService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;

import java.util.Collection;

@SpringComponent
@Scope("prototype")

@Route(value = "test")
@AnonymousAllowed
public class ServiceListViewCard extends VerticalLayout {
private final ServiceService serviceService;


    Grid<Service> grid = new Grid<>(Service.class);

    public ServiceListViewCard(ServiceService serviceService) {
        this.serviceService = serviceService;

        setSizeFull();
        configureGrid();
        Collection<Service> services = serviceService.findAll();
        grid.setItems(services);
        add(grid

        );
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("idService", "service", "description");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

}
