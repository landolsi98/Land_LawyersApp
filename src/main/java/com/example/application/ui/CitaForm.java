package com.example.application.ui;

import com.example.application.ui.News.ReportView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.example.application.backend.entity.*;
import com.example.application.backend.service.CitaService;
import com.example.application.backend.service.UserService;
import com.vaadin.flow.component.Composite;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;


@PageTitle("Appointment Land Lawyers")
@AnonymousAllowed
@Route(value = "formCita", layout = MainView.class)
public class CitaForm extends Composite<Div> {

    private CitaService citaService;
    private UserService userService;


    public CitaForm(UserService userService,CitaService citaService){
        this.citaService = citaService;
        this.userService = userService;
        Div content = getContent();
        content.getStyle().set("width", "100%");
        content.getStyle().set("margin-bottom", "60px");
        //  the background image component
        Image backgroundImage = new Image("images/meeting.jpg", "Background Image");
        backgroundImage.setHeight("320px");
        backgroundImage.getStyle().set("margin-bottom","30px");
        // backgroundImage.setWidth("1425px");
        //backgroundImage.getStyle().set("margin-top", "800px");
        getElement().getStyle().set("width", "100%");
        getElement().getStyle().set("margin-bottom", "60px");

        backgroundImage.setWidth("100%");
        backgroundImage.getElement().getStyle().set("border", "none");
        backgroundImage.addClassName(LumoUtility.Margin.NONE);

H3 title = new H3("Fill in the Form to Request an Appointment with the Experts of law");
title.getStyle().set("text-align","center");
 title.getStyle().set("margin-bottom","10px");

               content.add(backgroundImage, title);

        FormLayout formLayout = new FormLayout();

        TextField firstName = new TextField("Your name here");

        TextField lastName = new TextField("last name");

        EmailField email = new EmailField("email");

        NumberField phoneNumber = new NumberField("phone number");

        TextArea object = new TextArea("object");


        DatePicker date = new DatePicker("Appointment date");
        date.setHelperText("Mondays – Fridays only");

        Binder<Cita> binder = new Binder<>(Cita.class);
        binder.forField(date).withValidator(localDate -> {
            int dayOfWeek = localDate.getDayOfWeek().getValue();
            boolean validWeekDay = dayOfWeek >= 1 && dayOfWeek <= 5;
            return validWeekDay;
        }, "Select a weekday").bind(Cita::getDate,
                Cita::setDate);
        Button saveButton = new Button("Send");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
        ButtonVariant.LUMO_CONTRAST);

        TimePicker time = new TimePicker();
        time.setLabel("Appointment time");
        time.setHelperText("Open 8:00-12:00, 13:00-16:00");
        time.setStep(Duration.ofMinutes(30));
        time.setMin(LocalTime.of(8, 0));
        time.setMax(LocalTime.of(16, 0));


        saveButton.addClickListener(event -> {
                    saveButton.setEnabled(true);


                    LocalDate dateCita = date.getValue();
                    LocalTime timeCita = time.getValue();
                    Cita existingAppointment = citaService.findCitaByDateAndTime(dateCita, timeCita);


                    if(existingAppointment != null ) {
                        Notification notification2 = new Notification(
                                "No available appointment in this time",
                                5000,
                                Notification.Position.BOTTOM_CENTER
                        );
                        notification2.addThemeVariants(NotificationVariant.LUMO_ERROR);
                        notification2.open();

                    } else {
                        String userEmail = email.getValue();
                        User existingUser = userService.findUserByEmail(userEmail);


                        if (existingUser == null) {
                            User newUser = new User();
                            newUser.setFirstName(firstName.getValue());
                            newUser.setLastName(lastName.getValue());
                            newUser.setEmail(userEmail);
                            newUser.setPhoneNumber(phoneNumber.getValue());
                            Authority authority = new Authority();
                            authority.setIdRol(4);
                            newUser.setAuthority(authority);

                            existingUser = userService.add(newUser);
                        }
                        Cita newCita = new Cita();
                        newCita.setDate(date.getValue());
                        newCita.setTime(time.getValue());
                        newCita.setObject(object.getValue());
                        newCita.setUser(existingUser);

                        citaService.add(newCita);

                        Notification notification = Notification
                                .show("Appointment Booked successfully !");
                        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                        notification.setPosition(Notification.Position.BOTTOM_CENTER);
                        UI.getCurrent().navigate("cita_confirmation?newCita=" + newCita.getIdCita());

                    }
                }
                );


        formLayout.add(firstName,lastName,email,phoneNumber,object,time,date,saveButton);
        formLayout.addClassNames(LumoUtility.Margin.Horizontal.AUTO, LumoUtility.MaxWidth.SCREEN_LARGE, LumoUtility.Margin.Bottom.XLARGE);

        content.add(formLayout);


    }
}





