package com.example.application.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoIcon;

import java.util.Optional;

public class MainView extends AppLayout {
    private final Tabs menu;
    private H4 viewTitle;

    public MainView() {
        //  drawer for the menu
        setPrimarySection(Section.DRAWER);

        // Make the nav bar a header
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
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);




        //  logo layout
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        // Add the logo image or any other content you want
        // Image logoImage = new Image("images/logo_LL.jpg", "Logo");
        // logoImage.setHeight("50px");
        // logoImage.setWidth("150px");
        // logoLayout.add(logoImage);

        layout.add(logoLayout, menu);

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

        // Create the Details component
        Details analyticsDetails = createDetails("Dashboards",
                createStyledAnchor("http://localhost:8080/dashAbogados", "lawyers"),
                createStyledAnchor("http://localhost:8080/dashServices", "Services"),
                createStyledAnchor("http://localhost:8080/rendez-vous", "Cases"),
                createStyledAnchor("http://localhost:8080/news", "News"),
                createStyledAnchor("http://localhost:8080/Cases", "Citas"));

        // Create the Home tab
        Tab homeTab = createTab("Home", HomeView.class, new Icon(VaadinIcon.HOME));

        // homeTab.getElement().getStyle().set("margin-bottom", "-15px");

        /*  adding other tabs
        Tab dashboardLawyersTab = createTab("Dashboard lawyers", DashAbogados.class);
        Tab dashboardServicesTab = createTab("Dashboard Services", DashServices.class);
        Tab dashboardCasesTab = createTab("Dashboard Cases", DashCases.class);
        Tab dashboardNewsTab = createTab("Dashboard News", DashNoticias.class);
        Tab dashboardCitasTab = createTab("Dashboard Citas", CitaView.class);
*/

        // Create a custom tab for the Details component
        Tab detailsTab = createTab("", DashAbogados.class,new Icon(VaadinIcon.BAR_CHART_H) );
        detailsTab.addComponentAsFirst(analyticsDetails);

        Tab ServicesTab = createTab(" Services", ServiceListView.class,new Icon(VaadinIcon.ACADEMY_CAP));
        Tab EquipoTab = createTab("Our Team", ServiceListView.class, new Icon(VaadinIcon.GAVEL));
        Tab CitaTab = createTab(" Book an appointment", ServiceListView.class,new Icon(VaadinIcon.CALENDAR_BRIEFCASE));
        Tab Noticia = createTab("News" , CitaView.class, new Icon(VaadinIcon.NEWSPAPER));
        Tab ContactTab = createTab("Contact Us" , CitaView.class, new Icon(VaadinIcon.CHAT));



        // Add tabs in the desired order
        tabs.add(homeTab,ServicesTab,EquipoTab,CitaTab,Noticia,ContactTab,detailsTab);

        return tabs;
    }


    private static Tab createTab(String text,
                                 Class<? extends Component> navigationTarget,Icon icon) {
        final Tab tab = new Tab();
        tab.add(icon, new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }


    private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();

        // Configure styling for the header
        layout.setId("header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setHeight("70px");
        layout.setSpacing(false);
        layout.setMargin(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        // Have the drawer toggle button on the left
        layout.add(new DrawerToggle());

        // Placeholder for the title of the current view.
        viewTitle = new H4();
        layout.add(viewTitle);


        // A user icon
        //layout.add(new Image("/logo_LL.jpg", "Avatar"));

        return layout;
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