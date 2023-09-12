package com.example.application.UI.Lawyers;

import com.example.application.backend.entity.Abogado;
import com.example.application.backend.entity.Authority;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class TeamForm extends FormLayout {
    private String image64;
    private byte[] imageData; // Define imageData as an instance variable
    TextField firstName = new TextField("First Name", "Write your first name here ");
    TextField lastName = new TextField("Last Name");
    EmailField email = new EmailField("Email" , " write your email here @");
    PasswordField password = new PasswordField("Password");
    TextField phoneNumber = new TextField("Phone Number");
    TextField speciality = new TextField("Speciality");
    TextField numberBar = new TextField("Bar Number");
    TextField position = new TextField(" position");
    TextArea description = new TextArea(" description","write a litlle paragraph to describe the skills of the new lawyer");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Close");
    // Other fields omitted
    Binder<Abogado> binder = new BeanValidationBinder<>(Abogado.class);

    public TeamForm() {
        addClassName("form");
        binder.bindInstanceFields(this);


        add(firstName,
                lastName,
                email,
                password,
                speciality,
                phoneNumber,
                numberBar,
                position,
                description,

                createButtonsLayout());


        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);

        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);

            byte [] imageData = readInputStream(inputStream);
            // Convert the InputStream to a byte array
            image64 = Base64.getEncoder().encodeToString(imageData);

        });

        add(upload);
    }





    private byte[] readInputStream(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }


    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave()); // <1>
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean()))); // <2>
        close.addClickListener(event -> fireEvent(new CloseEvent(this))); // <3>

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid())); // <4>
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            Abogado abogado = binder.getBean();
            Authority authority = abogado.getAuthority();
            if (authority == null) {
                // Create a new Authority and set its idRol
                authority = new Authority();
                authority.setIdRol(2);
                abogado.setAuthority(authority);
            } else {
                // If the Authority already exists, set its idRol
                authority.setIdRol(2);
            }

            abogado.setImage64(image64); // Set the image data
            fireEvent(new SaveEvent(this, abogado));
        }
    }



    public void setAbogado(Abogado abogado) {
        binder.setBean(abogado); // <1>
    }

    // Events
    public static abstract class AbogadoFormEvent extends ComponentEvent<TeamForm> {
        private Abogado abogado;

        protected AbogadoFormEvent(TeamForm source, Abogado abogado) {
            super(source, false);
            this.abogado = abogado;
        }

        public Abogado getAbogado() {
            return abogado;
        }
    }

    public static class SaveEvent extends AbogadoFormEvent {
        SaveEvent(TeamForm source, Abogado abogado) {
            super(source, abogado);
        }
    }

    public static class DeleteEvent extends AbogadoFormEvent {
        DeleteEvent(TeamForm source, Abogado abogado) {
            super(source, abogado);
        }

    }

    public static class CloseEvent extends AbogadoFormEvent {
        CloseEvent(TeamForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(Class<DeleteEvent> deleteEventClass, ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(Class<SaveEvent> saveEventClass, ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
    public Registration addCloseListener(Class<CloseEvent> closeEventClass, ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }


}
