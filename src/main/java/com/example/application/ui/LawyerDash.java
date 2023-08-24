package com.example.application.ui;

import com.example.application.backend.entity.Abogado;
import com.example.application.backend.entity.Lawyer;
import com.example.application.backend.service.AbogadoService;
import com.example.application.backend.service.LawyerService;
import com.example.application.backend.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Base64;

/*
@Route("dash lawyers")
public class LawyerDash extends VerticalLayout {
private LawyersForm formL;
    private Span status;

    private Grid<Lawyer> grid = new Grid<>(Lawyer.class);

    LawyersForm form2;


    @Autowired
    private LawyerService lawyerService;
    @Autowired

    private UserService userService;
    private void saveLawyer(LawyersForm.SaveEvent evt) {
        lawyerService.add(evt.getLawyer());
        closeEditor();
        updateList();

        Notification notification = Notification
                .show("Lawyer Added");
        notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        notification.setPosition(Notification.Position.BOTTOM_START);

    }
    private void deleteLawyer(LawyersForm.DeleteEvent event) {
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
            lawyerService.delete(event.getLawyer());
            closeEditor();
            updateList();
        });

        UI.getCurrent().add(dialog);
        dialog.open();
    }


    public LawyerDash(@Autowired LawyerService lawyerService ,@Autowired UserService userService){
        this.lawyerService = lawyerService;
        this.formL = new LawyersForm(user,lawyer);

        // Setup  grid with columns for title, date, and body
        grid.setColumns("id_lawyers", "speciality","specialty", "bar_number", "position","description");
        grid.setItems(lawyerService.findAll());


        grid.asSingleSelect().addValueChangeListener(evt -> editAbogado(evt.getValue()));


        form2 = new LawyersForm();
        form2.addSaveListener(LawyersForm.SaveEvent.class, this::saveLawyer);
        form2.addDeleteListener(LawyersForm.DeleteEvent.class,this::deleteLawyer);
        form2.addCloseListener(LawyersForm.CloseEvent.class,e -> closeEditor());




        Div content = new Div(grid,form2);
        content.setSizeFull();
        content.getStyle().set("display","flex");
        content.getStyle().set("flex","row");

        Button addNews= new Button ( "Add Abogado" , click -> addAbogado());



        add(addNews,grid,content);
        closeEditor();

    }

    private void addAbogado() {
        grid.asSingleSelect().clear();
        editAbogado(new Lawyer());


    }

    private void editAbogado(Lawyer lawyer) {

        if(lawyer == null) {
            closeEditor();
        }else {
            form2.setLawyer(lawyer);
            form2.setVisible(true);
            addClassName("editing");
        }

    }

    private void closeEditor() {
        form2.setLawyer(null);
        form2.setVisible(false);
        removeClassName("editing");
    }
    private void updateList() {
        grid.setItems(lawyerService.findAll());
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

*/
