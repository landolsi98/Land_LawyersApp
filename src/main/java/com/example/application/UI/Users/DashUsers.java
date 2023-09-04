package com.example.application.UI.Users;

import com.example.application.backend.entity.*;
import com.example.application.backend.service.UserService;
import com.example.application.UI.Principals.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.util.Collection;

@PageTitle("Dashboard Users")
     @AnonymousAllowed
     @Route(value = "dashUsers" , layout = MainView.class)
     public class DashUsers extends VerticalLayout {
    TextField filterUser = new TextField();

    public DashUsers(UserService userService) {

        var crud = new GridCrud<>(User.class, userService);
        crud.getGrid().setColumns("idUser", "firstName", "lastName", "username", "email", "password", "enable", "phoneNumber");
        crud.getGrid().addColumn(user -> user.getAuthority().getRol()).setHeader("rol");
        crud.getCrudFormFactory().setVisibleProperties("firstName", "lastName", "username", "email", "password", "enable", "phoneNumber", "authority");
        crud.getCrudFormFactory().setFieldProvider("authority", entity -> {
            ComboBox<Authority> rolComboBox = new ComboBox<>();
            Collection<Authority> rolesList = userService.findAllRoles();

            rolComboBox.setItems(rolesList);
            rolComboBox.setItemLabelGenerator(Authority::getRol);

            Binder<User> binder = new Binder<>(User.class);
            binder.forField(rolComboBox)
                    .bind(User::getAuthority, User::setAuthority);
            return rolComboBox;

        });
        add(
                crud
        );
    }
    private Component getToolbar() {
        filterUser.setPlaceholder("Filter by First Name...");
        filterUser.setClearButtonVisible(true);
        filterUser.setValueChangeMode(ValueChangeMode.LAZY);



        var toolbar = new HorizontalLayout(filterUser);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

}

