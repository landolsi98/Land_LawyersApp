package com.example.application.UI;

import com.example.application.backend.entity.*;
import com.example.application.backend.service.CaseService;
import com.example.application.backend.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


@AnonymousAllowed
@Route(value = "value")
public class TestCaseForm extends FormLayout {
    private byte[] document; // Define imageData as an instance variable
 private final UserService userService;
    private final CaseService caseService;

    TextField title = new TextField("Title", "News Title");
    //DatePicker date = new DatePicker("Creation Date");

    TextArea description = new TextArea("News Description");
    TextField state = new TextField("state");

    DatePicker date = new DatePicker("date");


    // Other fields omitted
    Binder<Case> binder = new BeanValidationBinder<>(Case.class);

    public TestCaseForm(UserService userService, CaseService caseService) {
        this.userService = userService;
        this.caseService = caseService;
        addClassName("contact-form");
        binder.bindInstanceFields(this);
        Button save = new Button("Save");
     save.addClickListener(event -> {


             save.setEnabled(false);

         User avocatUser = this.userService.findUserById(375L);
         if (avocatUser instanceof Abogado) {
             Case newCase = new Case();
             newCase.setTitle(title.getValue());
             newCase.setCreation_date(date.getValue());
             newCase.setState(state.getValue());
             newCase.setDescription(description.getValue());

             User user = new User();
             user.setIdUser(420);
             newCase.setUser(user);

             Service service = new Service();
             service.setIdService(9);
             newCase.setService(service);

             newCase.setAbogado((Abogado) avocatUser);

             caseService.add(newCase);


         }


     }   )  ;



        add(title,
                //date,
                description,

                state,
                date
        ,save);


        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);

        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);

            // Convert the InputStream to a byte array
            document = readInputStream(inputStream); // Set the instance variable
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

}
