package com.example.application.UI;

/*
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
            Abogado newUser = new Abogado();
            newUser.setFirstName(firstName.getValue());
            newUser.setLastName(lastName.getValue());
            newUser.setUsername(username.getValue());
            newUser.setEmail(email.getValue());
            newUser.setPassword(password.getValue());
            newUser.setPhoneNumber(Double.valueOf(Integer.valueOf(phoneNumber.getValue())));

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







        add(firstName, lastName,username,email,password,phoneNumber,position,numberBar,description,rolComboBox,saveButton);
    }
}
*/