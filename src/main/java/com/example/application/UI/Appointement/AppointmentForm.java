package com.example.application.UI.Appointement;

import com.example.application.backend.entity.Cita;
import com.example.application.backend.entity.User;
import com.example.application.backend.service.CitaService;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class AppointmentForm extends FormLayout {

    private CitaService citaService;
    TextField object = new TextField("Object", "Write your first name here ");

    DatePicker date = new DatePicker("date");

    TimePicker time = new TimePicker("time");

    ComboBox<User> client = new ComboBox<>("Client");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Close");
    Binder<Cita> binder = new BeanValidationBinder<>(Cita.class);

    public AppointmentForm(List<User> allUsers) {
        addClassName("form");
        binder.bindInstanceFields(this);

        client.setItems(allUsers);
        client.setItemLabelGenerator(User::getFirstName);

        add(object,
                date,
                time,
                client,


                createButtonsLayout());

    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave()); // <1>
        delete.addClickListener(event -> fireEvent(new AppointmentForm.DeleteEvent(this, binder.getBean()))); // <2>
        close.addClickListener(event -> fireEvent(new AppointmentForm.CloseEvent(this))); // <3>

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid())); // <4>
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if(binder.isValid()) {
            fireEvent(new AppointmentForm.SaveEvent(this, binder.getBean())); // <6>
        }
    }
    public void setCita(Cita cita) {
        binder.setBean(cita); // <1>
    }

    // Events
    public static abstract class AppointmentFormEvent extends ComponentEvent<AppointmentForm> {
        private Cita cita;

        protected AppointmentFormEvent(AppointmentForm source, Cita cita) {
            super(source, false);
            this.cita = cita;
        }

        public Cita getCita() {
            return cita;
        }
    }

    public static class SaveEvent extends AppointmentFormEvent {
        SaveEvent(AppointmentForm source, Cita cita) {
            super(source, cita);
        }
    }

    public static class DeleteEvent extends AppointmentFormEvent {
        DeleteEvent(AppointmentForm source, Cita cita) {
            super(source, cita);
        }

    }

    public static class CloseEvent extends AppointmentFormEvent {
        CloseEvent(AppointmentForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(Class<AppointmentForm.DeleteEvent> deleteEventClass, ComponentEventListener<AppointmentForm.DeleteEvent> listener) {
        return addListener(AppointmentForm.DeleteEvent.class, listener);
    }

    public Registration addSaveListener(Class<AppointmentForm.SaveEvent> saveEventClass, ComponentEventListener<AppointmentForm.SaveEvent> listener) {
        return addListener(AppointmentForm.SaveEvent.class, listener);
    }
    public Registration addCloseListener(Class<AppointmentForm.CloseEvent> closeEventClass, ComponentEventListener<AppointmentForm.CloseEvent> listener) {
        return addListener(AppointmentForm.CloseEvent.class, listener);
    }


}
