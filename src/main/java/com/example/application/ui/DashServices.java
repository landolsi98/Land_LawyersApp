package com.example.application.ui;


import com.example.application.backend.entity.Service;
import com.example.application.backend.service.ServiceService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route(value =  "dashServices", layout = MainView.class )
@PageTitle(" DashServices | Land-Lawyers ")
@AnonymousAllowed
public class DashServices extends VerticalLayout {

    public DashServices(ServiceService serviceService) {

        var crud = new GridCrud<>(Service.class,serviceService);
        crud.getGrid().setColumns("idService","service","description");
        crud.getCrudFormFactory().setVisibleProperties("service","description");

        add(
                new H1("Dashboard Services"),
                crud
        );


    }


}
