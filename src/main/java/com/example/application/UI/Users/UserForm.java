package com.example.application.UI.Users;

import com.example.application.backend.entity.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class UserForm extends FormLayout {


    TextField firstName = new TextField("firstName");
    TextField lastName = new TextField("LastName");
    TextField username = new TextField("username");
    EmailField email = new EmailField("email");
    TextField password = new TextField("password" ,"Write a remark or a comment to the client");
    Checkbox enable = new Checkbox();

    TextField phoneNumber = new TextField("phoneNumber" ,"Write a remark or a comment to the client");

    ComboBox<Authority> authority = new ComboBox<>("Rol");



    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Close");
    Binder<User> binder = new BeanValidationBinder<>(User.class);

    public UserForm(List<Authority> allRoles) {
        addClassName("user-form");
        binder.bindInstanceFields(this);

        authority.setItems(allRoles);
        authority.setItemLabelGenerator(Authority::getRol);


        add(firstName,
                lastName,
                username,
                email,
                password,
                enable,
                phoneNumber,
                authority,
                createButtonsLayout());

    }



    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave()); // <1>
        delete.addClickListener(event -> fireEvent(new com.example.application.UI.Users.UserForm.DeleteEvent(this, binder.getBean()))); // <2>
        close.addClickListener(event -> fireEvent(new com.example.application.UI.Users.UserForm.CloseEvent(this))); // <3>

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid())); // <4>
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if(binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean())); // <6>
            }
            }



    public void setUser(User user) {
        binder.setBean(user); // <1>
    }

    // Events
    public static abstract class UserFormEvent extends ComponentEvent<com.example.application.UI.Users.UserForm> {
        private User user;

        protected UserFormEvent(com.example.application.UI.Users.UserForm source, User user) {
            super(source, false);
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    public static class SaveEvent extends UserFormEvent {
        SaveEvent(UserForm source, User user) {
            super(source, user);
        }
    }

    public static class DeleteEvent extends UserFormEvent {
        DeleteEvent(com.example.application.UI.Users.UserForm source, User user) {
            super(source, user);
        }

    }

    public static class CloseEvent extends UserFormEvent {
        CloseEvent(com.example.application.UI.Users.UserForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(Class<UserForm.DeleteEvent> deleteEventClass, ComponentEventListener<UserForm.DeleteEvent> listener) {
        return addListener(UserForm.DeleteEvent.class, listener);
    }
    public Registration addSaveListener(Class<UserForm.SaveEvent> saveEventClass, ComponentEventListener<UserForm.SaveEvent> listener) {
        return addListener(UserForm.SaveEvent.class, listener);
    }
    public Registration addCloseListener(Class<UserForm.CloseEvent> closeEventClass, ComponentEventListener<UserForm.CloseEvent> listener) {
        return addListener(UserForm.CloseEvent.class, listener);
    }


}
