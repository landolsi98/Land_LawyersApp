package com.example.application.ui;

import com.example.application.backend.entity.Abogado;
import com.example.application.backend.service.AbogadoService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;

@PageTitle("Dashboard Abogados")
@Route(value = "dashAbogados", layout = MainView.class )
@AnonymousAllowed
public class DashAbogados extends VerticalLayout {
    public DashAbogados(AbogadoService service) {


        var crud = new GridCrud<>(Abogado.class, service);
        crud.getGrid().setColumns("idAbogado", "firstName", "lastName", "email", "specialty", "phoneNumber", "barNumber", "position");
        crud.getCrudFormFactory().setVisibleProperties("firstName", "lastName", "email", "specialty", "phoneNumber", "barNumber", "position");


        add(
                new H1("Dashboard Lawyers"),
                crud
        );
    }
}
