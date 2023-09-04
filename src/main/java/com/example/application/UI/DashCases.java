package com.example.application.UI;

import com.example.application.UI.Principals.MainView;
import com.example.application.backend.entity.*;
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
import java.util.stream.Collectors;

@PageTitle("Dashboard Cases")
@AnonymousAllowed
@Route(value = "Cases" , layout = MainView.class)
public class DashCases extends VerticalLayout {

    public DashCases(CaseService service){

var crud = new GridCrud<>(Case.class, service);
crud.getGrid().setColumns("idCase","title", "description" , "state","creationDate","document");
crud.getGrid().addColumn(avocat -> avocat.getAvocat().getFirstName()).setHeader("Charged Lawyer");
crud.getGrid().addColumn(serv -> serv.getService().getService()).setHeader("Service");
crud.getGrid().addColumn(user -> user.getUser().getFirstName()).setHeader("Client");


crud.getCrudFormFactory().setUseBeanValidation(true); // Activate Bean validations
crud.getCrudFormFactory().setVisibleProperties("title", "description" , "state","creation_date","document", "avocat","service","client");

crud.getCrudFormFactory().setFieldProvider("avocat", entity -> {
            ComboBox<Avocat> abogadoComboBox = new ComboBox<>();
            List<Avocat> abogadoList = service.findAllAbogados();

            abogadoComboBox.setItems(abogadoList);
            abogadoComboBox.setItemLabelGenerator(Avocat ::getFirstName);

            Binder<Case> binder = new Binder<>(Case.class);
            binder.forField(abogadoComboBox)
                    .bind(Case::getAvocat, Case::setAvocat);
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
            List<User> allUsers = service.findAllUsers();

            List<User> filteredUsers = allUsers.stream()
                    .filter(user -> user.getAuthority().getIdRol() == 3 || user.getAuthority().getIdRol() ==4 )
                    .collect(Collectors.toList());

            userComboBox.setItems(filteredUsers);
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
