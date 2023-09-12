package com.example.application.UI.Principals;

import com.example.application.UI.Lawyers.AbogadoGrid;
import com.example.application.backend.entity.Authority;
import com.example.application.backend.entity.User;
import com.example.application.security.AuthenticatedUser;
import com.example.application.UI.Citas.CitaForm;
import com.example.application.UI.Features.CaseTrackerView;
import com.example.application.UI.Features.ChatView;
import com.example.application.UI.Lawyers.TeamView;
import com.example.application.UI.News.NewsView;
import com.example.application.UI.Services.ServiceListView;
import com.example.application.UI.Features.WorkSpace;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.Optional;

@AnonymousAllowed
public class MainView extends AppLayout {
    private final Tabs menu;
    private H4 viewTitle;
    private AuthenticatedUser authenticatedUser;

    public MainView(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
        //  drawer for the menu
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        //  the menu in the drawer
        menu = createMenu();
        addToDrawer(createDrawerContent(menu));

    }

    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        //  styling for the drawer
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.setMargin(false);
        layout.getThemeList().remove("spacing-s");
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        layout.getStyle().set("margin-bottom","150px");

        //  logo layout
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");

        H2 titulo = new H2("Land Lawyers");
        titulo.getStyle().setColor("dark Blue");
        titulo.getStyle().set("margin-top","15px");
        titulo.getStyle().set("margin-left","22px");
        titulo.getStyle().set("font-weight","bold");

        layout.add(titulo,menu);

        return layout;
    }

    private Details createDetails(String summary, Anchor... anchors) {
        Details details = new Details(summary, createContent(anchors));
        details.setOpened(true);
        return details;
    }

    private VerticalLayout createContent(Anchor... anchors) {
        VerticalLayout content = new VerticalLayout();
        content.setPadding(false);
        content.setSpacing(false);
        content.add(anchors);
        return content;
    }

    private Anchor createStyledAnchor(String href, String text) {
        Anchor anchor = new Anchor(href, text);
        anchor.getStyle().set("color", "var(--lumo-primary-text-color)");
        anchor.getStyle().set("text-decoration", "none");

        return anchor;
    }

    private Tabs createMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");

            Tab homeTab = createTab("Home", HomePage.class, new Icon(VaadinIcon.HOME));
            Tab ServicesTab = createTab(" Services", ServiceListView.class, new Icon(VaadinIcon.ACADEMY_CAP));
            Tab EquipoTab = createTab("Our Team", TeamView.class, new Icon(VaadinIcon.GAVEL));
            Tab CitaTab = createTab(" Book an appointment", CitaForm.class, new Icon(VaadinIcon.CALENDAR_BRIEFCASE));
            Tab Noticia = createTab("News", NewsView.class, new Icon(VaadinIcon.NEWSPAPER));
            Tab ContactTab = createTab("Contact Us", ContactView.class, new Icon(VaadinIcon.CHAT));
            tabs.add(homeTab, ServicesTab, EquipoTab, CitaTab, Noticia, ContactTab);

            Optional<User> authenticatedUserOptional = authenticatedUser.get();

        if (authenticatedUserOptional.isPresent()) {
            User authenticatedUser = authenticatedUserOptional.get();
            Authority authority = authenticatedUser.getAuthority();
            if(authority != null &&  "LAWYER".equals(authority.getRol()) ) {
                Tab WorkingTab = createTab("Work Space", WorkSpace.class, new Icon(VaadinIcon.WORKPLACE));
                tabs.add(WorkingTab);
            }
            if(authority != null && "CLIENT".equals(authority.getRol()) || "LAWYER".equals(authority.getRol()) ) {
                Tab ChatRoom = createTab("Chat Room", ChatView.class, new Icon(VaadinIcon.COMMENTS));
                tabs.add(ChatRoom);
      }
            if(authority != null && "CLIENT".equals(authority.getRol()) ) {
                Tab CaseTracker = createTab("Case Monitoring", CaseTrackerView.class, new Icon(VaadinIcon.BAR_CHART_H));
                tabs.add(CaseTracker);
            }
            if (authority != null && "ADMIN".equals(authority.getRol())) {
                //  component only for the admin user
                Details analyticsDetails = createDetails("Dashboards",
                        createStyledAnchor("http://localhost:8080/Dashboard_abogados", " Lawyers"),
                        createStyledAnchor("http://localhost:8080/dashServices", " Services"),
                        createStyledAnchor("http://localhost:8080/Case-grid", " Cases"),
                        createStyledAnchor("http://localhost:8080/Dashboard_News", " News"),
                        createStyledAnchor("http://localhost:8080/Dashboard_appointments", " Citas"),
                        createStyledAnchor("http://localhost:8080/dashClients", " Clients"),
                        createStyledAnchor("http://localhost:8080/dashboard_users", " Users"));

                Tab analyticsTab = createTab("", AbogadoGrid.class, new Icon(VaadinIcon.BAR_CHART_H));
                analyticsTab.addComponentAsFirst(analyticsDetails);
                tabs.add(analyticsTab);

            } else if ( "LAWYER".equals(authority.getRol())) {
                Details analyticsDetails = createDetails("Dashboards",
                        createStyledAnchor("http://localhost:8080/Case-grid", " Cases"),
                        createStyledAnchor("http://localhost:8080/Dashboard_appointments", " Citas"),
                        createStyledAnchor("http://localhost:8080/dashClients", " Clients"),
                        createStyledAnchor("http://localhost:8080/dashboard_users", " Users"));

                Tab analyticsTab = createTab("", AbogadoGrid.class, new Icon(VaadinIcon.BAR_CHART_H));
                analyticsTab.addComponentAsFirst(analyticsDetails);
                tabs.add(analyticsTab);
            }
        }
                return tabs;

            }

        private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();
        //  styling  the header
        layout.setId("header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setHeightFull();
        layout.setHeight("70px");
        layout.setSpacing(false);
        layout.setMargin(false);
        layout.setPadding(false);
        layout.getThemeList().remove("spacing-s");
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        // drawer toggle button on the left
        layout.add(new DrawerToggle());
        //  logo layout
        viewTitle = new H4();
        layout.add(viewTitle);
        var header = new HorizontalLayout( createAvatar() );
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        //header.expand(createAvatar());
         header.getStyle().set("margin-left","auto");
        // Add the avatar
        layout.add(header);
        return layout;
    }

    private Footer createAvatar() {
        Footer layout = new Footer();
        int colorIndex = 3;
        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            String name = user.getFirstName() + " " + user.getLastName();

            Avatar avatar = new Avatar(name);

            avatar.setThemeName("small");
            avatar.getElement().setAttribute("tabindex", "-1");
            avatar.setColorIndex(colorIndex);

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(avatar);
            div.add(user.getUsername());
            div.add(new Icon("lumo", "dropdown"));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");

            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);

            userName.getSubMenu().addItem("Sign out", e -> {
                authenticatedUser.logout();

            });


            layout.add(userMenu);
        } else {
            Button clientButton = new Button("Client Access", new Icon(VaadinIcon.USER), e -> getUI().ifPresent(ui -> ui.navigate("login")));
            clientButton.getStyle().set("background-color","darkgoldenrod");
            clientButton.getStyle().set("margin","8px");
            clientButton.getStyle().set("color","whitesmoke");
            clientButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

            Button lawyerButton = new Button("Lawyer Access", new Icon(VaadinIcon.ACADEMY_CAP), e -> getUI().ifPresent(ui -> ui.navigate("login-lawyer")));
            lawyerButton.getStyle().set("background-color","darkgrey");
            lawyerButton.getStyle().set("margin-right","5px");
            lawyerButton.getStyle().set("color","whitesmoke");
            lawyerButton.addThemeVariants(ButtonVariant.LUMO_SMALL);


            layout.add(clientButton, lawyerButton);
        }


        return layout;
    }

    private static Tab createTab(String text,
                                 Class<? extends Component> navigationTarget,Icon icon) {
        final Tab tab = new Tab();
        tab.add(icon, new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();

        // Select the tab corresponding to currently shown view
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);

        // Set the view title in the header
        viewTitle.setText(getCurrentPageTitle());
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren()
                .filter(tab -> ComponentUtil.getData(tab, Class.class)
                        .equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }


    private String getCurrentPageTitle() {
        return getContent().getClass().getAnnotation(PageTitle.class).value();
    }


}