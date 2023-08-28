package com.example.application.ui;

import com.example.application.security.AuthenticatedUser;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.server.auth.AnonymousAllowed;

import com.vaadin.flow.component.html.Div;

import com.vaadin.flow.router.Route;

@Route("login")
@AnonymousAllowed
@PageTitle("Login")
public class LoginView extends Div implements BeforeEnterObserver {
    private final LoginForm login = new LoginForm();

    public LoginView() {
        // Background image
        Image backgroundImage = new Image("images/Login10.jpg", "Background Image");
        backgroundImage.setWidth("100%");
        backgroundImage.setHeight("100%");
        backgroundImage.getStyle().set("object-fit", "cover");
        backgroundImage.getStyle().set("position", "fixed");

        //  main layout
        setWidth("100%");
        setHeight("100%");
        getElement().getStyle().set("display", "flex");
        getElement().getStyle().set("flexDirection", "column");
        getElement().getStyle().set("alignItems", "center");
        getElement().getStyle().set("justifyContent", "center");
        getElement().getStyle().set("position", "relative");

        // Login form
        login.getElement().getThemeList().add("dark");
        login.setAction("login");
        login.getStyle().set("position", "relative");
        login.getStyle().set("margin-top","70px");

        login.setForgotPasswordButtonVisible(true);
        // components to the layout
        add(backgroundImage, login);
    }





    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}
