package com.example.application.UI.News;

import com.example.application.backend.entity.Noticia;
import com.example.application.backend.service.NoticiaService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.shared.Registration;


import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@AnonymousAllowed
@Route(value = "newsForm")

public class NewsForm extends FormLayout {
    private byte[] imageData; // Define imageData as an instance variable
    private String image64; // Define base64EncodedImage as an instance variable

    private NoticiaService noticiaService;

    TextField title = new TextField("Title", "News Title");
     //DatePicker date = new DatePicker("Creation Date");

     TextArea cuerpo = new TextArea("News Description");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Close");
    // Other fields omitted
    Binder<Noticia> binder = new BeanValidationBinder<>(Noticia.class);

    public NewsForm() {
        addClassName("contact-form");
        binder.bindInstanceFields(this);


        add(title,
                //date,
                cuerpo,

                createButtonsLayout());


        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);

        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);

            // Convert the InputStream to a byte array
            byte[] imageData = readInputStream(inputStream);

            // Encode the byte array to Base64 and store it as an instance variable
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
        if (binder.isValid() && image64 != null) {
            Noticia noticia = binder.getBean();
            noticia.setImage64(image64); // Set the image data
            fireEvent(new SaveEvent(this, noticia)); // Fire the SaveEvent with the complete data
        }
    }



    public void setNoticia(Noticia noticia) {
        binder.setBean(noticia); // <1>
    }

    // Events
    public static abstract class ContactFormEvent extends ComponentEvent<NewsForm> {
        private Noticia noticia;

        protected ContactFormEvent(NewsForm source, Noticia noticia) {
            super(source, false);
            this.noticia = noticia;
        }

        public Noticia getNoticia() {
            return noticia;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(NewsForm source, Noticia noticia) {
            super(source, noticia);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(NewsForm source, Noticia noticia) {
            super(source, noticia);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(NewsForm source) {
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


