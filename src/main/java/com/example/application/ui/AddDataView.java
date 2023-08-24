package com.example.application.ui;

import com.example.application.backend.entity.Authority;
import com.example.application.backend.entity.Avocat;
import com.example.application.backend.entity.Lawyer;
import com.example.application.backend.entity.User;
import com.example.application.backend.service.AvocatService;
import com.example.application.backend.service.LawyerService;
import com.example.application.backend.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@AnonymousAllowed
@Route("add-data")
public class AddDataView extends VerticalLayout {

    @Autowired
    private final AvocatService avocatService;

    @Autowired
    private final UserService userService;

    public AddDataView( AvocatService avocatService, UserService userService) {
        this.avocatService = avocatService;
        this.userService = userService;

        // Créez les composants de formulaire ici (TextField, Button, etc.)
        TextField firstName = new TextField("First Name");
        TextField lastName = new TextField("Last Name");
        TextField username = new TextField("username");
        TextField email = new TextField("email");
        TextField password = new TextField("password");
        TextField phoneNumber = new TextField("phoneNumber");
        TextField numberBar = new TextField("barnumber");
        TextField position  = new TextField("position");
        TextField description  = new TextField("description");


// ...

        ComboBox<Authority> rolComboBox = new ComboBox<>();
        Collection<Authority> rolesList = userService.findAllRoles();

        rolComboBox.setItems(rolesList);
        rolComboBox.setItemLabelGenerator(Authority::getRol);
        Binder<User> binder = new Binder<>(User.class);
        binder.forField(rolComboBox)
                .bind(User::getAuthority, User::setAuthority);


        Button saveButton = new Button("Save");
        saveButton.addClickListener(event -> {

            // Créez un nouvel utilisateur
            Avocat newUser = new Avocat();
            newUser.setFirstName(firstName.getValue());
            newUser.setLastName(lastName.getValue());
            newUser.setUsername(username.getValue());
            newUser.setEmail(email.getValue());
            newUser.setPassword(password.getValue());
            newUser.setPhoneNumber(phoneNumber.getValue());

            if (binder.writeBeanIfValid(newUser)) {



            // Créez un nouvel avocat lié à l'utilisateur créé

            newUser.setNumberBar(numberBar.getValue());
            newUser.setPosition(position.getValue());
            newUser.setDescription(description.getValue());

                Authority selectedRole = rolComboBox.getValue();

                newUser.setAuthority(selectedRole);


                //newUser.setUser(newUser); // Lie l'avocat à l'utilisateur


           // userService.add(newUser);
            avocatService.add(newUser);


            } else {
System.out.println("error");
            }


        });

// ...


        add(firstName, lastName,username,email,password,phoneNumber,position,numberBar,description,rolComboBox,saveButton);
    }
}
