package com.example.application.ui;

import com.example.application.backend.entity.Abogado;
import com.example.application.backend.entity.Avocat;
import com.example.application.backend.entity.Cita;
import com.example.application.backend.service.CitaService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.util.List;
@PageTitle("Dashboard News")

@Route(value = "rendez-vous" , layout = MainView.class)
@AnonymousAllowed
public class CitaView extends VerticalLayout {

    public CitaView(CitaService service) {

        var crud = new GridCrud<>(Cita.class, service);
        crud.getGrid().setColumns("idCita", "object", "date", "time");
       // crud.getGrid().addColumn(cita -> cita.getAbogado().getFirstName()).setHeader("Abogado");

        crud.getCrudFormFactory().setUseBeanValidation(true); // Activate Bean validations
        crud.getCrudFormFactory().setVisibleProperties("object", "date", "time");

        crud.getCrudFormFactory().setFieldProvider("time", entity -> {
            TimePicker timePicker = new TimePicker();
            timePicker.setLabel("Select Time");

            Binder<Cita> binder = new Binder<>(Cita.class);
            binder.forField(timePicker)
                    .bind(Cita::getTime, Cita::setTime);

            return timePicker;
        });
/*
        crud.getCrudFormFactory().setFieldProvider("abogado", entity -> {
            ComboBox<Avocat> abogadoComboBox = new ComboBox<>();
            abogadoComboBox.setLabel("Select Abogado");
            List<Avocat> abogadoList = service.findAllAbogados();

            abogadoComboBox.setItems(abogadoList);
            abogadoComboBox.setItemLabelGenerator(Avocat::getFirstName);

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



