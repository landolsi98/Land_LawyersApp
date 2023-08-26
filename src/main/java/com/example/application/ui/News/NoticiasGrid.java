package com.example.application.ui.News;

import java.util.Base64;

import com.example.application.backend.entity.Noticia;
import com.example.application.backend.service.NoticiaService;
import com.example.application.ui.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@UIScope
@PageTitle("News LandLawyers")
@AnonymousAllowed
@Route(value = "Dashboard_News" , layout = MainView.class)
public class NoticiasGrid extends VerticalLayout {
    private final NewsForm form1;
    private Span status;

    private Grid<Noticia> grid = new Grid<>(Noticia.class);

    NewsForm form;
    @Autowired
    private NoticiaService noticiaService;

    private void saveNoticia(NewsForm.SaveEvent evt) {
        noticiaService.add(evt.getNoticia());
        closeEditor();
        updateList();

        Notification notification = Notification
                .show("News Added");
        notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        notification.setPosition(Notification.Position.BOTTOM_START);

    }
    private void deleteNoticia(NewsForm.DeleteEvent event) {
        status = new Span();
        status.setVisible(false);

        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Delete This item ?");
        dialog.setText("Are you sure you want to delete this item?");

        dialog.setCancelable(true);

        dialog.setConfirmText("Delete");
        dialog.setConfirmButtonTheme("error primary");

        dialog.addConfirmListener(e -> {
            // Delete the news item after user confirms
            noticiaService.delete(event.getNoticia());
            closeEditor();
            updateList();
        });

        UI.getCurrent().add(dialog);
        dialog.open();
    }


    public NoticiasGrid(@Autowired NoticiaService noticiaService) {
        this.noticiaService = noticiaService;
        this.form = new NewsForm();

        // Setup  grid with columns for title, date, and body
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