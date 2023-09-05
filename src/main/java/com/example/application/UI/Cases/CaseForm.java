package com.example.application.UI.Cases;

import com.example.application.backend.entity.*;
import com.vaadin.collaborationengine.CollaborationBinder;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
import java.util.List;

public class CaseForm extends FormLayout {

    private byte[] document;

    TextField title = new TextField("title");
    TextField description = new TextField("description");
    TextField state = new TextField("state","In Progress / On hold / Closed / Requires");
    DatePicker creation_date = new DatePicker("creation_date");
    TextArea comment = new TextArea("comment" ,"Write a remark or a comment to the client");
    ComboBox<Avocat> avocat = new ComboBox<>("Lawyers");
    ComboBox<User> client = new ComboBox<>("users");
    ComboBox<Service> service = new ComboBox<>("services");


    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    Binder<Case> binder = new BeanValidationBinder<>(Case.class);

    public CaseForm(List<Service> AllServices, List<User> allUsers, List<Avocat> allAvocats) {
        addClassName("case-form");
        binder.bindInstanceFields(this);
        client.setItems(allUsers);
        client.setItemLabelGenerator(User::getFirstName);
        service.setItems(AllServices);
        service.setItemLabelGenerator(Service::getService);
        avocat.setItems(allAvocats);
        avocat.setItemLabelGenerator(Avocat::getFirstName);


        add(title,
                description,
                state,
                creation_date,
                comment,
                service,
                avocat,
                client,
                createButtonsLayout());



    MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    Upload upload = new Upload(buffer);

        upload.addSucceededListener(event -> {
        String fileName = event.getFileName();
        InputStream inputStream = buffer.getInputStream(fileName);

        // Convert the InputStream to a byte array
            document = readInputStream(inputStream);
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
        Case caso = binder.getBean();
        if (binder.isValid() && (isNotEmpty(caso.getTitle()) || isNotEmpty(caso.getDescription()) || isNotEmpty(caso.getComment()))) {
            if (document != null) {
                caso.setDocument(document);
            }
            fireEvent(new CaseForm.SaveEvent(this, caso)); // Fire the SaveEvent with the complete data
        } else {
            // Show an error message or handle the case where required fields are missing
            Notification.show("Please fill in the required fields or upload a document",
                    3000, Notification.Position.MIDDLE);
        }
    }

    // Helper method to check if a string is not empty
    private boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }

    public void setCase(Case caso) {
        binder.setBean(caso); // <1>
    }

    // Events
    public static abstract class CaseFormEvent extends ComponentEvent<CaseForm> {
        private Case caso;

        protected CaseFormEvent(CaseForm source, Case caso) {
            super(source, false);
            this.caso = caso;
        }

        public Case getCase() {
            return caso;
        }
    }

    public static class SaveEvent extends CaseFormEvent {
        SaveEvent(CaseForm source, Case caso) {
            super(source, caso);
        }
    }

    public static class DeleteEvent extends CaseFormEvent {
        DeleteEvent(CaseForm source, Case caso) {
            super(source, caso);
        }

    }

    public static class CloseEvent extends CaseFormEvent {
        CloseEvent(CaseForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(Class<CaseForm.DeleteEvent> deleteEventClass, ComponentEventListener<CaseForm.DeleteEvent> listener) {
        return addListener(CaseForm.DeleteEvent.class, listener); }
    public Registration addSaveListener(Class<CaseForm.SaveEvent> saveEventClass, ComponentEventListener<CaseForm.SaveEvent> listener) {
        return addListener(CaseForm.SaveEvent.class, listener); }
    public Registration addCloseListener(Class<CaseForm.CloseEvent> closeEventClass, ComponentEventListener<CaseForm.CloseEvent> listener) {
        return addListener(CaseForm.CloseEvent.class, listener); }


}
