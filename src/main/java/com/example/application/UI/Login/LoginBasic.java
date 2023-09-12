package com.example.application.UI.Login;

import com.example.application.security.AuthenticatedUser;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@PageTitle("Login")
@Route(value = "login-lawyer")
public class LoginBasic extends Div implements BeforeEnterObserver {
    LoginForm loginForm = new LoginForm();

   private final  AuthenticatedUser authenticatedUser;
    public LoginBasic(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout titleLogo = new HorizontalLayout();
        H1 title = new H1();
        title.getElement().getStyle().setColor("darkblue");

        Icon icon = new Icon(VaadinIcon.ACADEMY_CAP);
        icon.setSize("32px");
        icon.getStyle().set("margin-top", "12px");


        titleLogo.add(title,icon);
        titleLogo.getElement().getStyle().setColor("darkblue");
        titleLogo.setAlignItems(FlexComponent.Alignment.CENTER);
        title.setText("Welcome Lawyer");
        getStyle().set("background-color", "var(--lumo-contrast-5pct)")
                .set("display", "flex").set("justify-content", "center")
                .set("padding", "var(--lumo-space-l)");
        //loginForm.getStyle().set("margin-top","150px");
        loginForm.setAction("login");
        verticalLayout.add(titleLogo,loginForm);
        verticalLayout.getStyle().set("justify-content", "center");
        verticalLayout.getElement().getStyle().set("margin-left", "auto");
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        add(verticalLayout);
        loginForm.getElement().setAttribute("no-autofocus", "");
    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginForm.setError(true);
        }
    }
}
