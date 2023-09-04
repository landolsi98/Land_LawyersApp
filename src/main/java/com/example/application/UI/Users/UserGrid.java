package com.example.application.UI.Users;

import com.example.application.backend.entity.User;
import com.example.application.backend.service.UserService;
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
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;


@SpringComponent
@Scope("prototype")
@RolesAllowed({"ADMIN","LAWYER"})
@Route(value = "dashboard_users", layout = MainView.class)
@PageTitle("Dashboard Users | Land Lawyers")
public class UserGrid extends VerticalLayout {
    private Grid<User> grid = new Grid<>(User.class);
    TextField filterUser = new TextField();
    private Span status;
    private UserForm form;

    UserForm form1;
    @Autowired
    private UserService userService;


    private void saveUser(UserForm.SaveEvent event) {
        userService.add(event.getUser());
        updateList();
        closeEditor();
        Notification notification = Notification
                .show("User Added Successfully");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        notification.setPosition(Notification.Position.BOTTOM_START);
    }

    private void deleteUser(UserForm.DeleteEvent event) {
        status = new Span();
        status.setVisible(false);

        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Delete This User ?");
        dialog.setText("Are you sure you want to delete it?");

        dialog.setCancelable(true);

        dialog.setConfirmText("Delete");
        dialog.setConfirmButtonTheme("error primary");

        dialog.addConfirmListener(e -> {
            try {
                userService.delete(event.getUser());
                updateList();
                closeEditor();

                Notification successNotification = Notification
                        .show("User Deleted Successfully");
                successNotification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
                successNotification.setPosition(Notification.Position.BOTTOM_START);
            } catch (Exception ex) {
                ex.printStackTrace();

                Notification errorNotification = Notification
                        .show("You can't delete a user/client while they still have an appointment");
                errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                errorNotification.setPosition(Notification.Position.BOTTOM_START);
            }
        });

        UI.getCurrent().add(dialog);
        dialog.open();
    }

    private UserGrid(@Autowired UserService userService) {
        this.userService = userService;
        this.form1 = new UserForm(userService.findAllRoles());

        grid.setColumns("idUser", "firstName", "lastName", "username", "email", "password", "enable", "phoneNumber");
        grid.setItems(userService.findAll());
        grid.addColumn(rol -> rol.getAuthority().getRol()).setHeader("Rol");

//grid.getColumnByKey("password").setAutoWidth(false);
        grid.asSingleSelect().addValueChangeListener(event ->
                editUser(event.getValue()));

        form1 = new UserForm(userService.findAllRoles());
        form1.addSaveListener(UserForm.SaveEvent.class, this::saveUser);
        form1.addDeleteListener(UserForm.DeleteEvent.class, this::deleteUser);
        form1.addCloseListener(UserForm.CloseEvent.class, e -> closeEditor());


        Div content = new Div(grid, form1);
        content.setSizeFull();
        content.getStyle().set("display", "flex");
        content.getStyle().set("flex", "row");

        add(getToolbar(), grid, content);
        closeEditor();

    }


    private Component getToolbar() {
        filterUser.setPlaceholder("Filter by name...");
        filterUser.setClearButtonVisible(true);
        filterUser.setValueChangeMode(ValueChangeMode.LAZY);
        filterUser.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add user");
        addContactButton.addClickListener(click -> addUser());

        var toolbar = new HorizontalLayout(filterUser, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editUser(User user) {
        if (user == null) {
            closeEditor();
        } else {
            form1.setUser(user);
            form1.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form1.setUser(null);
        form1.setVisible(false);
        removeClassName("editing");
    }

    private void addUser() {
        grid.asSingleSelect().clear();
        editUser(new User());
    }


    private void updateList() {
        grid.setItems(userService.findAllUsers(filterUser.getValue()));
    }
}