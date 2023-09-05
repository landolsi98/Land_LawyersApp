package com.example.application.UI.Citas;
import com.example.application.security.AuthenticatedUser;
import com.example.application.UI.Principals.HomePage;
import com.example.application.UI.Principals.MainView;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.example.application.backend.entity.*;
import com.example.application.backend.service.CitaService;
import com.example.application.backend.service.UserService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;


@PageTitle("Appointment booking | Land Lawyers")
@AnonymousAllowed
@Route(value = "formCita", layout = MainView.class)
public class CitaForm extends Composite<Div> {

    private final AuthenticatedUser authenticatedUser;
    private CitaService citaService;
    private UserService userService;

    private HasValue.ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<EmailField, String>> listener ;
 private HasValue.ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<IntegerField, Integer>> listener2;

    public CitaForm(AuthenticatedUser authenticatedUser, UserService userService, CitaService citaService) {
        this.authenticatedUser = authenticatedUser;
        this.citaService = citaService;
        this.userService = userService;
        Div content = getContent();
        content.getStyle().set("width", "100%");
        content.getStyle().set("margin-bottom", "60px");
        //  the background image component
        Image backgroundImage = new Image("images/meeting.jpg", "Background Image");
        backgroundImage.setHeight("320px");
        backgroundImage.getStyle().set("margin-bottom", "30px");
        // backgroundImage.setWidth("1425px");
        //backgroundImage.getStyle().set("margin-top", "800px");
        getElement().getStyle().set("width", "100%");
        getElement().getStyle().set("margin-bottom", "60px");

        backgroundImage.setWidth("100%");
        backgroundImage.getElement().getStyle().set("border", "none");
        backgroundImage.addClassName(LumoUtility.Margin.NONE);

        H3 title = new H3("Fill in the Form to Request an Appointment with the Experts of law");
        title.getStyle().set("text-align", "center");
        title.getStyle().set("margin-bottom", "10px");

        content.add(backgroundImage, title);

        FormLayout formLayout = new FormLayout();

        TextField firstName;
        TextField lastName;
        EmailField email;
        IntegerField phoneNumber;

        Optional<User> userOptional = authenticatedUser.get();

        if (userOptional.isPresent()) {
            User connectedUser = userOptional.get();
            System.out.println("*****" + connectedUser);
            firstName = new TextField("Your name here", connectedUser.getFirstName(), connectedUser.getFirstName());
            lastName = new TextField("last name", connectedUser.getLastName(), connectedUser.getLastName());
            email = new EmailField("email", connectedUser.getEmail(), listener);
            phoneNumber = new IntegerField("phone number", connectedUser.getPhoneNumber(), listener2);
        } else {
            firstName = new TextField("Your name here");
            lastName = new TextField("last name");
            email = new EmailField("email");
            phoneNumber = new IntegerField("phone number");
        }


        TextArea object = new TextArea("object");

        DatePicker date = new DatePicker("Appointment date");
        LocalDate now = LocalDate.now(ZoneId.systemDefault());

        date.setMin(now);
        date.setMax(now.plusDays(120));
        date.setHelperText("Must be within 60 days from today");
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

        binder.forField(time).withValidator(startTime -> {
            return !(LocalTime.of(8, 0).isAfter(startTime)
                    || (LocalTime.of(12, 0).isBefore(startTime)
                    && LocalTime.of(13, 0).isAfter(startTime))
                    || LocalTime.of(16, 0).isBefore(startTime));
        }, "The selected time is not available, please select a working time").bind(Cita::getTime,
                Cita::setTime);
        date.addValueChangeListener(event -> {
            boolean isDateValid = binder.isValid();
            // Enable the "Save" button only if  date and time are valid
            saveButton.setEnabled(isDateValid);
        });
        time.addValueChangeListener(event -> {
            boolean isTimeValid = binder.isValid();
            saveButton.setEnabled(isTimeValid);
        }
);

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
                        UI.getCurrent().navigate("cita_confirmation", QueryParameters.fromString(newCita.getIdCita().toString()));


                    }
                }

                );
        formLayout.add(firstName,lastName,email,phoneNumber,object,time,date,saveButton);
        formLayout.addClassNames(LumoUtility.Margin.Horizontal.AUTO, LumoUtility.MaxWidth.SCREEN_LARGE, LumoUtility.Margin.Bottom.XLARGE);
        HorizontalLayout  footerLayout = createFoot() ;

        content.add(formLayout,footerLayout);

    }
    private HorizontalLayout createFoot() {
        Div div = new Div();
        div.addClassNames(
                LumoUtility.Display.FLEX,
                LumoUtility.Margin.Bottom.MEDIUM,
                LumoUtility.BorderRadius.MEDIUM
        );
        div.setHeight("420px");

        HorizontalLayout footLayout = new HorizontalLayout();
        footLayout.addClassNames(
                LumoUtility.Background.CONTRAST
        );
        footLayout.getStyle().setWidth("100%");
        footLayout.getStyle().setHeight("250px");
        footLayout.getStyle().set("margin-bottom", "-65px");
        footLayout.getStyle().set("margin-top", "23px");


        // Content on the left side
        Div footContent = new Div();
        Image logoImage = new Image("images/LogoLandL23.jpg", "Land Lawyer Logo");
        logoImage.setWidth("180px");
        logoImage.setHeight("200px");
        footContent.add(logoImage);

        Div footerContent1 = new Div();
        footerContent1.getStyle().set("color", "white");
        footerContent1.setText("© 2023 LandLawyers");
        footerContent1.getStyle().set("margin-top","10px");
        footContent.add(footerContent1);

        // Links on the right side
        VerticalLayout linksColumn1 = createLinksColumn1();
        VerticalLayout linksColumn2 = createLinksColumn2();
        VerticalLayout linksColumn3 = createLinksColumn3();

        Icon fbIcon = new Icon(VaadinIcon.FACEBOOK_SQUARE);
        Icon twitterIcon = new Icon(VaadinIcon.TWITTER);
        fbIcon.setColor("white");
        twitterIcon.setColor("white");

        //  icons
        VerticalLayout socialIconsLayout = new VerticalLayout(fbIcon, twitterIcon);
        socialIconsLayout.setSpacing(true);
        socialIconsLayout.getStyle().set("margin-top", "20px");
        footLayout.add(div, footContent, linksColumn1, linksColumn2,linksColumn3,socialIconsLayout);

        return footLayout;
    }

    private VerticalLayout createLinksColumn1() {
        VerticalLayout linksLayout = new VerticalLayout();
        linksLayout.addClassNames(LumoUtility.Margin.Left.XLARGE,LumoUtility.Margin.Top.LARGE);

        linksLayout.add(createFooterLink("Home", "http://localhost:8080/inicio"));
        linksLayout.add(createFooterLink("About", "http://localhost:8080/services"));
        linksLayout.add(createFooterLink("Our Services", "#team"));
        // Add more links as needed

        return linksLayout;
    }

    private VerticalLayout createLinksColumn2() {
        VerticalLayout linksLayout = new VerticalLayout();
        linksLayout.addClassNames(LumoUtility.Margin.Left.NONE,LumoUtility.Margin.Top.LARGE);

        linksLayout.add(createFooterLink("Our Team", "#book-now"));
        linksLayout.add(createFooterLink("News", "#contact"));
        linksLayout.add(createFooterLink("Book an Appointment", "#see-more"));

        return linksLayout;
    }
    private VerticalLayout createLinksColumn3() {
        VerticalLayout linksLayout = new VerticalLayout();
        linksLayout.addClassNames(LumoUtility.Margin.Left.NONE,LumoUtility.Margin.Top.LARGE);

        linksLayout.add(createFooterLink("Contact", "#book-now"));
        linksLayout.add(createFooterLink("Email : LandLawyers@gmail.com", "#contact"));
        linksLayout.add(createFooterLink("Tel Number : +216 21 997 664", "#see-more"));

        return linksLayout;
    }

    private Anchor createFooterLink(String label, String targetUrl) {
        Anchor link = new Anchor(targetUrl);
        link.setText(label);
        link.getStyle().set("color", "white");
        link.getStyle().set("text-decoration", "none");
        link.getStyle().set("margin-bottom", "8px");

        return link;
    }
}





