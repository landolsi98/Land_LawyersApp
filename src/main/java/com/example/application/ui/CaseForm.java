package com.example.application.ui;

import com.example.application.backend.entity.*;
import com.example.application.backend.service.CaseService;
import com.example.application.backend.service.NoticiaService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.shared.Registration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@AnonymousAllowed

@Route(value = "caseForm")
public class CaseForm extends FormLayout {
    private byte[] documentData; // Define imageData as an instance variable

    ComboBox<Avocat> abogados = new ComboBox<>("Status");
    ComboBox<User> users = new ComboBox<>("Company");
    ComboBox<Service> services = new ComboBox<>("Company");

    private CaseService caseService;

    TextField title = new TextField("Title", "News Title");
    //DatePicker date = new DatePicker("Creation Date");

    TextArea description = new TextArea("News Description");

    TextField state = new TextField("state");

    DatePicker date = new DatePicker("date");



    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    // Other fields omitted
    Binder<Case> binder = new BeanValidationBinder<>(Case.class);

    public CaseForm(List<Avocat> avocatList, List<User> usuarios) {
        addClassName("contact-form");
        binder.bindInstanceFields(this);


        abogados.setItems(avocatList);
        abogados.setItemLabelGenerator(Avocat::getFirstName);
        users.setItems(usuarios);
        users.setItemLabelGenerator(User::getUsername);
        add(title,
                description,
                abogados,
                state,
                users,
                date,
                createButtonsLayout());


        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);

        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);

            // Convert the InputStream to a byte array
            documentData = readInputStream(inputStream); // Set the instance variable
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
        delete.addClickListener(event -> fireEvent(new com.example.application.ui.CaseForm.DeleteEvent(this, binder.getBean()))); // <2>
        close.addClickListener(event -> fireEvent(new com.example.application.ui.CaseForm.CloseEvent(this))); // <3>

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid())); // <4>
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if (binder.isValid() && documentData != null) {
            Case caso = binder.getBean();
            caso.setDocument(documentData); // Set the image data
            fireEvent(new com.example.application.ui.CaseForm.SaveEvent(this, caso)); // Fire the SaveEvent with the complete data
        }
    }



    public void setCase(Case caso) {
        binder.setBean(caso); // <1>
    }

    // Events
    public static abstract class ContactFormEvent extends ComponentEvent<com.example.application.ui.CaseForm> {
        private Case caso;

        protected ContactFormEvent(com.example.application.ui.CaseForm source, Case caso) {
            super(source, false);
            this.caso = caso;
        }

        public Case getCase() {
            return caso;
        }
    }

    public static class SaveEvent extends com.example.application.ui.CaseForm.ContactFormEvent {
        SaveEvent(com.example.application.ui.CaseForm source, Case caso) {
            super(source, caso);
        }
    }

    public static class DeleteEvent extends com.example.application.ui.CaseForm.ContactFormEvent {
        DeleteEvent(com.example.application.ui.CaseForm source, Case caso){
            super(source, caso);
        }

    }

    public static class CloseEvent extends com.example.application.ui.CaseForm.ContactFormEvent {
        CloseEvent(com.example.application.ui.CaseForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(Class<com.example.application.ui.CaseForm.DeleteEvent> deleteEventClass, ComponentEventListener<com.example.application.ui.CaseForm.DeleteEvent> listener) {
        return addListener(com.example.application.ui.CaseForm.DeleteEvent.class, listener);
    }

    public Registration addSaveListener(Class<com.example.application.ui.CaseForm.SaveEvent> saveEventClass, ComponentEventListener<com.example.application.ui.CaseForm.SaveEvent> listener) {
        return addListener(com.example.application.ui.CaseForm.SaveEvent.class, listener);
    }
    public Registration addCloseListener(Class<com.example.application.ui.CaseForm.CloseEvent> closeEventClass, ComponentEventListener<com.example.application.ui.CaseForm.CloseEvent> listener) {
        return addListener(com.example.application.ui.CaseForm.CloseEvent.class, listener);
    }


}