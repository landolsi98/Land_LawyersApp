package com.example.application.UI;

import com.example.application.UI.Principals.MainView;
import com.example.application.backend.entity.Authority;
import com.example.application.backend.entity.User;
import com.example.application.backend.service.UserService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Clients Dashboard ")
@Route(value = "dashClients" , layout = MainView.class)
@RolesAllowed({"ADMIN","LAWYER"})
public class DashClients extends VerticalLayout {


    public DashClients(UserService userService) {

        // Fetch all users
        List<User> allUsers = (List<User>) userService.findAll();

        // Filter users with the client role
        List<User> clientUsers = allUsers.stream()
                .filter(user -> "CLIENT".equals(user.getAuthority().getRol()))
                .collect(Collectors.toList());

        var crud = new GridCrud<>(User.class);
        ListDataProvider<User> dataProvider = new ListDataProvider<>(clientUsers);
        crud.setFindAllOperation(dataProvider);
        crud.setAddOperationVisible(false);
        crud.setDeleteOperationVisible(false);
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

        add(crud);
    }


}

