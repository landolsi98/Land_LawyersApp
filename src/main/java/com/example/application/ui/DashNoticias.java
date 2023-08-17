package com.example.application.ui;

import com.example.application.backend.entity.Noticia;
import com.example.application.backend.service.NoticiaService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;


@Route(value = "news", layout = MainView.class)
@PageTitle("Land-Lawyers")
@RolesAllowed("ADMIN")
public class DashNoticias extends VerticalLayout {

    public DashNoticias(NoticiaService service) {

        var crud = new GridCrud<>(Noticia.class, service);
        crud.getGrid().setColumns("idNoticia","title","cuerpo","date","imagen");
        crud.getCrudFormFactory().setVisibleProperties("title","cuerpo","imagen");



        add(
                new H1("Dashboard News"),
                crud
        );

    }
}
