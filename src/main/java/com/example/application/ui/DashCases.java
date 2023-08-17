package com.example.application.ui;

import com.example.application.backend.entity.Abogado;
import com.example.application.backend.entity.Case;
import com.example.application.backend.entity.Service;
import com.example.application.backend.entity.User;
import com.example.application.backend.service.CaseService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.util.List;

@PageTitle("Dashboard Cases")
@AnonymousAllowed
@Route(value = "Cases" , layout = MainView.class)
public class DashCases extends VerticalLayout {


    public DashCases(CaseService service){

var crud = new GridCrud<>(Case.class, service);
crud.getGrid().setColumns("idCase","title", "description" , "state","creationDate");
crud.getGrid().addColumn(caso -> caso.getAbogado().getFirstName()).setHeader("Abogado Cargado");
crud.getGrid().addColumn(serv -> serv.getService().getService()).setHeader("Service");
crud.getGrid().addColumn(user -> user.getUser().getFirstName()).setHeader("Client");


crud.getCrudFormFactory().setUseBeanValidation(true); // Activate Bean validations
crud.getCrudFormFactory().setVisibleProperties("title", "description" , "state","creation_date","abogado","service","client");

crud.getCrudFormFactory().setFieldProvider("abogado", entity -> {
            ComboBox<Abogado> abogadoComboBox = new ComboBox<>();
            List<Abogado> abogadoList = service.findAllAbogados();

            abogadoComboBox.setItems(abogadoList);
            abogadoComboBox.setItemLabelGenerator(Abogado ::getFirstName);

            Binder<Case> binder = new Binder<>(Case.class);
            binder.forField(abogadoComboBox)
                    .bind(Case::getAbogado, Case::setAbogado);
            return abogadoComboBox;

});

        crud.getCrudFormFactory().setFieldProvider("service", entity -> {
            ComboBox<Service> servicioComboBox = new ComboBox<>();
            List<Service> serviceList = service.findAllServices();

            servicioComboBox.setItems(serviceList);
            servicioComboBox.setItemLabelGenerator(Service::getService);

            Binder<Case> binder = new Binder<>(Case.class);
            binder.forField(servicioComboBox)
                    .bind(Case::getService, Case::setService);
            return servicioComboBox;
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

add(
        new H1("Dashboard Cases"),
        crud
);





    }





}
