package com.example.application.ui;

import com.example.application.backend.entity.Abogado;
import com.example.application.backend.entity.Avocat;
import com.example.application.backend.service.AbogadoService;
import com.example.application.backend.service.AvocatService;
import com.example.application.backend.service.NoticiaService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Base64;

@PageTitle("Abogados LandLawyers")
@AnonymousAllowed
@Route(value = "Dashboard_abogados" , layout = MainView.class)
public class AbogadoGrid extends VerticalLayout {

    private TeamForm form;
    private Span status;

    private Grid<Avocat> grid = new Grid<>(Avocat.class);

    TeamForm form1;

    @Autowired
    private AvocatService avocatService;


    private void saveAbogado(TeamForm.SaveEvent evt) {
        avocatService.add(evt.getAbogado());
        closeEditor();
        updateList();

        Notification notification = Notification
                .show("Lawyer Added");
        notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        notification.setPosition(Notification.Position.BOTTOM_START);

    }
    private void deleteAbogado(TeamForm.DeleteEvent event) {
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
            avocatService.delete(event.getAbogado());
            closeEditor();
            updateList();
        });

        UI.getCurrent().add(dialog);
        dialog.open();
    }


    public AbogadoGrid(@Autowired AvocatService avocatService) {
        this.avocatService = avocatService;
        this.form = new TeamForm();

        // Setup  grid with columns for title, date, and body
        grid.setColumns("idAbogado", "firstName", "lastName", "email",  "phoneNumber", "speciality", "numberBar","password", "position","description");
        grid.setItems(avocatService.findAll());


        grid.asSingleSelect().addValueChangeListener(evt -> editAbogado(evt.getValue()));


        form1 = new TeamForm();
        form1.addSaveListener(TeamForm.SaveEvent.class, this::saveAbogado);
        form1.addDeleteListener(TeamForm.DeleteEvent.class,this::deleteAbogado);
        form1.addCloseListener(TeamForm.CloseEvent.class,e -> closeEditor());




        Div content = new Div(grid,form1);
        content.setSizeFull();
        content.getStyle().set("display","flex");
        content.getStyle().set("flex","row");

        Button addNews= new Button ( "Add Abogado" , click -> addAbogado());



        add(addNews,grid,content);
        closeEditor();

    }

    private void addAbogado() {
        grid.asSingleSelect().clear();
        editAbogado(new Avocat());


    }

    private void editAbogado(Avocat avocat) {

        if(avocat == null) {
            closeEditor();
        }else {
            form1.setAbogado(avocat);
            form1.setVisible(true);
            addClassName("editing");
        }

    }

    private void closeEditor() {
        form1.setAbogado(null);
        form1.setVisible(false);
        removeClassName("editing");
    }
    private void updateList() {
        grid.setItems(avocatService.findAll());
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