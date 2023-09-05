package com.example.application.UI.Appointement;

import com.example.application.backend.entity.Cita;
import com.example.application.backend.service.CitaService;
import com.example.application.UI.Principals.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;


@PageTitle("Appointments | Land Lawyers")
@RolesAllowed({"ADMIN", "LAWYER"})
@Route(value = "Dashboard_appointments" , layout = MainView.class)
public class AppointmentGrid extends VerticalLayout {

    private AppointmentForm form;
    private Span status;

    private Grid<Cita> grid = new Grid<>(Cita.class);
    TextField filterCita = new TextField();
    AppointmentForm form1;

    @Autowired
    private CitaService citaService;


    private void saveCita(AppointmentForm.SaveEvent evt) {
        citaService.add(evt.getCita());
        closeEditor();
        updateList();

        Notification notification = Notification
                .show("Appointment Added Successfully");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        notification.setPosition(Notification.Position.BOTTOM_START);

    }

    private void deleteCita(AppointmentForm.DeleteEvent event) {
        status = new Span();
        status.setVisible(false);

        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Delete This Appointment ?");
        dialog.setText("Are you sure you want to delete it?");

        dialog.setCancelable(true);

        dialog.setConfirmText("Delete");
        dialog.setConfirmButtonTheme("error primary");

        dialog.addConfirmListener(e -> {
            // Delete the news item after user confirms
            citaService.delete(event.getCita());
            closeEditor();
            updateList();
            Notification notification = Notification
                    .show("Appointment deleted");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setPosition(Notification.Position.BOTTOM_START);
        });

        UI.getCurrent().add(dialog);
        dialog.open();
    }


    public AppointmentGrid(@Autowired CitaService citaService) {
        this.citaService = citaService;
        this.form = new AppointmentForm(citaService.findAllUsers());

        grid.setColumns("idCita", "object", "date", "time");
        grid.setItems(citaService.findAll());
        grid.addColumn(cita -> cita.getUser().getFirstName()).setHeader("client");

        grid.asSingleSelect().addValueChangeListener(evt -> editCita(evt.getValue()));


        form1 = new AppointmentForm(citaService.findAllUsers());
        form1.addSaveListener(AppointmentForm.SaveEvent.class, this::saveCita);
        form1.addDeleteListener(AppointmentForm.DeleteEvent.class, this::deleteCita);
        form1.addCloseListener(AppointmentForm.CloseEvent.class, e -> closeEditor());


        Div content = new Div(grid, form1);
        content.setSizeFull();
        content.getStyle().set("display", "flex");
        content.getStyle().set("flex", "row");

        add(getToolbar(), grid, content);
        closeEditor();

    }

    private Component getToolbar() {
        filterCita.setPlaceholder("By object or client name...");
        filterCita.setClearButtonVisible(true);
        filterCita.setValueChangeMode(ValueChangeMode.LAZY);
        filterCita.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add Appointment");
        addContactButton.addClickListener(click -> addCita());

        var toolbar = new HorizontalLayout(filterCita, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addCita() {
        grid.asSingleSelect().clear();
        editCita(new Cita());


    }

    private void editCita(Cita cita) {

        if (cita == null) {
            closeEditor();
        } else {
            form1.setCita(cita);
            form1.setVisible(true);
            addClassName("editing");
        }

    }

    private void closeEditor() {
        form1.setCita(null);
        form1.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(citaService.findAllCitas(filterCita.getValue()));
    }
}
