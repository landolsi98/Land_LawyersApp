package com.example.application.UI.Citas;

import com.example.application.backend.entity.*;
import com.example.application.backend.service.CitaService;
import com.example.application.UI.Principals.HomePage;
import com.example.application.UI.Principals.MainView;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.util.List;
@PageTitle("Dashboard Appointments | Land Lawyers")

@Route(value = "dashboard-citas" , layout = MainView.class)
@RolesAllowed({"ADMIN","LAWYER"})
public class CitaGrid extends VerticalLayout {
    HorizontalLayout footer = HomePage.createFoot();

    public CitaGrid(CitaService service) {

        var crud = new GridCrud<>(Cita.class, service);
        crud.getGrid().setColumns("idCita", "object", "date", "time");
        crud.getGrid().addColumn(cita -> cita.getUser().getFirstName()).setHeader("Client");

        crud.getCrudFormFactory().setUseBeanValidation(true); // Activate Bean validations
        crud.getCrudFormFactory().setVisibleProperties("object", "date", "time","client");

        crud.getCrudFormFactory().setFieldProvider("time", entity -> {
            TimePicker timePicker = new TimePicker();
            timePicker.setLabel("Select Time");

            Binder<Cita> binder = new Binder<>(Cita.class);
            binder.forField(timePicker)
                    .bind(Cita::getTime, Cita::setTime);

            return timePicker;
        });

        crud.getCrudFormFactory().setFieldProvider("client", entity -> {
            ComboBox<User> userComboBox = new ComboBox<>();
            List<User> usersList = service.findAllUsers();

            userComboBox.setItems(usersList);
            userComboBox.setItemLabelGenerator(User::getFirstName);

            Binder<Case> binder = new Binder<>(Case.class);
            binder.forField(userComboBox)
                    .bind(Case::getUser, Case::setUser);
            return userComboBox;
        });


/*
        crud.getCrudFormFactory().setFieldProvider("abogado", entity -> {
            ComboBox<Abogado> abogadoComboBox = new ComboBox<>();
            abogadoComboBox.setLabel("Select Abogado");
            List<Abogado> abogadoList = service.findAllAbogados();

            abogadoComboBox.setItems(abogadoList);
            abogadoComboBox.setItemLabelGenerator(Abogado::getFirstName);

            Binder<Cita> binder = new Binder<>(Cita.class);
            binder.forField(abogadoComboBox)
                    .bind(Cita::getAbogado, Cita::setAbogado);

            return abogadoComboBox;
        });

*/
        add(
                new H1("Dashboard Cita"),
                crud
        );
    }
}



