package com.example.application.ui;

import java.util.Collection;
import java.util.Base64;

import com.example.application.backend.entity.Noticia;
import com.example.application.backend.service.NoticiaService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;
@PageTitle("News LandLawyers")
@Component
@AnonymousAllowed
@Route(value = "noticias-grid" , layout = MainView.class)
public class NoticiasGrid extends VerticalLayout {
    private final NewsForm form1;
    private Grid<Noticia> grid = new Grid<>(Noticia.class);

    NewsForm form;
    @Autowired
    private NoticiaService noticiaService;

    private void saveNoticia(NewsForm.SaveEvent evt){
        noticiaService.add(evt.getNoticia());
        closeEditor();
        updateList();
    }
    private void deleteNoticia(NewsForm.DeleteEvent event) {
        noticiaService.delete(event.getNoticia());
        closeEditor();
        updateList();

    }

    public NoticiasGrid(@Autowired NoticiaService noticiaService) {
        this.noticiaService = noticiaService;
this.form = new NewsForm();

        // Setup a grid with columns for title, date, and body
        grid.setColumns("title", "date", "cuerpo");
        grid.setItems(noticiaService.findAll());


grid.asSingleSelect().addValueChangeListener(evt -> editNoticia(evt.getValue()));


        form1 = new NewsForm();
        form1.addSaveListener(NewsForm.SaveEvent.class, this::saveNoticia);
        form1.addDeleteListener(NewsForm.DeleteEvent.class,this::deleteNoticia);
        form1.addCloseListener(NewsForm.CloseEvent.class,e -> closeEditor());




Div content = new Div(grid,form1);
content.setSizeFull();
content.getStyle().set("display","flex");
content.getStyle().set("flex","row");

Button addNews= new Button ( "Add News" , click -> addNoticia());

        add(addNews,grid,content);
        closeEditor();

    }

    private void addNoticia() {
        grid.asSingleSelect().clear();
        editNoticia(new Noticia());

    }

    private void editNoticia(Noticia noticia) {

        if(noticia == null) {
            closeEditor();
        }else {
            form1.setNoticia(noticia);
            form1.setVisible(true);
            addClassName("editing");
        }

    }

    private void closeEditor() {
        form1.setNoticia(null);
        form1.setVisible(false);
        removeClassName("editing");
    }
    private void updateList() {
        grid.setItems(noticiaService.findAll());
    }

    private String getImageAsBase64(String base64Image) {
        String mimeType = "image/jpg"; // Change the mimeType as needed
        if (base64Image == null) {
            return TRANSPARENT_GIF_1PX;
        } else {
            return "data:" + mimeType + ";base64," + Base64.getEncoder().encodeToString(base64Image.getBytes());
        }

    }


    private static final String TRANSPARENT_GIF_1PX =
            "data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACwAAAAAAQABAAACAkQBADs=";
}