package com.example.application.UI;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("app-layout-navbar")
@AnonymousAllowed

public class AppLayoutNavbar extends AppLayout {

    public AppLayoutNavbar() {

        H1 title = new H1("Land Lawyers");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0")
                .set("color","blue")
                .set("position", "absolute");

        Tabs tabs = getTabs();


        addToNavbar(title, tabs);
        //<theme-editor-local-classname>
        addClassName("app-layout-navbar-app-layout-1");

    }

    private Tabs getTabs() {

        Tabs tabs = new Tabs();
        tabs.getStyle().set("margin", "auto");

        tabs.add(createTab("Home", (Class<? extends Component>) HomeView.class));
        tabs.add(createTab("Noticias", DashNoticias.class));

        return tabs;
    }

    private Tab createTab(String tabLabel, Class<? extends Component> viewClass) {

        RouterLink link = new RouterLink(tabLabel, viewClass);
        link.setHighlightCondition(HighlightConditions.sameLocation());
        link.setTabIndex(-1);

        return new Tab(link);
    }
}
