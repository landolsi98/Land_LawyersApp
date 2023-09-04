package com.example.application.UI.Principals;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.map.Map;
import com.vaadin.flow.component.map.configuration.style.TextStyle;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.map.configuration.Coordinate;
import com.vaadin.flow.component.map.configuration.feature.MarkerFeature;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle("Contact Us | Land Lawyers")
@Route(value = "Contact-Us" , layout = MainView.class)
@AnonymousAllowed
public class ContactView extends Div {

    public ContactView() {
        Map map = new Map();
        add(map);
        HorizontalLayout footer = HomePage.createFoot();
        TextStyle textStyle = new TextStyle();
           textStyle.setFont("bold 16px sans-serif");
        textStyle.setStroke("#fdf4ff", 3);
        textStyle.setFill("#701a75");
        // Position text to the right of the icon
        textStyle.setTextAlign(TextStyle.TextAlign.LEFT);
        textStyle.setOffset(22, -18);

        MarkerFeature customizedMarker = new MarkerFeature(new Coordinate(  10.64431827626331, 35.656913226747434   ));
        customizedMarker.setText("Land Lawyer Office");
        customizedMarker.setTextStyle(textStyle);
        customizedMarker.setDraggable(true);


        map.getFeatureLayer().addFeature(customizedMarker);

        // Listen to marker drop event
        map.addFeatureDropListener(event -> {
            MarkerFeature droppedMarker = (MarkerFeature) event.getFeature();
            Coordinate startCoordinates = event.getStartCoordinate();
            Coordinate endCoordinates = event.getCoordinate();

            Notification.show("Marker \"" + droppedMarker.getId() + "\" dragged from " + startCoordinates + " to " + endCoordinates);
        });

        HorizontalLayout layout = new HorizontalLayout();
        layout.addClassNames(LumoUtility.Margin.Horizontal.AUTO, LumoUtility.MaxWidth.SCREEN_LARGE, LumoUtility.Margin.Bottom.XLARGE);

        layout.setWidthFull();
        layout.add(contactForm());
        layout.add(contactText());
        layout.add(BookNow());


        add(layout,footer);
    }

    private HorizontalLayout contactForm() {
        HorizontalLayout layout = new HorizontalLayout();
        VerticalLayout verticallayout = new VerticalLayout();
        H3 title = new H3("Address & Infos");

        Accordion accordion = new Accordion();
        accordion.getElement().setProperty("opened", true);
        Span name = new Span("Land Lawyers");
        Span email = new Span("Land.Lawyers@company.com");
        Span phone = new Span("(+34) 655-912-871");

        VerticalLayout personalInformationLayout = new VerticalLayout(name,
                email, phone);
        personalInformationLayout.setSpacing(false);
        personalInformationLayout.setPadding(false);

        AccordionPanel personalInfoPanel = accordion.add("Company Information",
                personalInformationLayout);
        personalInfoPanel.addThemeVariants(DetailsVariant.FILLED);

        Accordion accordion2 = new Accordion();

        accordion2.getElement().setProperty("opened", true);
        Span street = new Span("Law Office, Calle Almeria 4");
        Span zipCode = new Span("28028");
        Span city = new Span("Madrid, Spain");

        VerticalLayout personalInformationLayout1 = new VerticalLayout(street,
                zipCode, city);
        personalInformationLayout1.setSpacing(false);
        personalInformationLayout1.setPadding(false);

        AccordionPanel personalInfoPanel1 = accordion.add("Company Address",
                personalInformationLayout1);
        personalInfoPanel1.addThemeVariants(DetailsVariant.FILLED);

        verticallayout.add(title, accordion, accordion2);
        //verticallayout.getElement().getStyle().set("margin-left", "100px");
        verticallayout.setMargin(true);
        //verticallayout.getElement().getStyle().set("margin-right","100px");
        verticallayout.getElement().getStyle().set("width", "300px");


        layout.add(verticallayout);
        return layout;
    }

  /*  private HorizontalLayout contactText() {
        HorizontalLayout horizLayout = new HorizontalLayout();
        VerticalLayout verticalLayout = new VerticalLayout();
        H3 title = new H3("Let's get started");
        Paragraph parafo = new Paragraph("if you are facing serious charges.it is imperative that you seek assistance from a knoledgeable criminal defense lawyer as as possible.An experienced attorney can guide you through the legal process.explain your options. and work 10 craft the strongest possible defense on your behalf");
        parafo.getElement().getStyle().set("background-color", "gainsboro");
        parafo.getElement().getStyle().setOpacity("0.5");
        parafo.getElement().getStyle().setColor("black");

        parafo.getElement().getStyle().set("margin-top", "-5px");

        parafo.getElement().getStyle().set("text-align", "justify");

        verticalLayout.add(title, parafo);
        verticalLayout.getElement().getStyle().set("margin-left", "100px");
        verticalLayout.getElement().getStyle().set("width", "60%");
        verticalLayout.setMargin(true);
        verticalLayout.setSpacing(true);
        horizLayout.add(verticalLayout);
        return horizLayout;
    }
    */

    private HorizontalLayout contactText() {
HorizontalLayout horizontalLayout = new HorizontalLayout();
VerticalLayout verticalLayout = new VerticalLayout();
    Div div = new Div();
                div.addClassNames(
    LumoUtility.BoxShadow.LARGE,
    LumoUtility.Display.FLEX,
    LumoUtility.FlexDirection.COLUMN,
    LumoUtility.AlignItems.CENTER,
    LumoUtility.JustifyContent.CENTER,
    LumoUtility.Margin.Bottom.MEDIUM,
    LumoUtility.Overflow.HIDDEN,
    LumoUtility.BorderRadius.MEDIUM,
    LumoUtility.Width.FULL,
    LumoUtility.Background.CONTRAST_5,
    LumoUtility.BorderColor.CONTRAST_60,
    LumoUtility.Margin.MEDIUM,
    LumoUtility.Height.XLARGE,
                        LumoUtility.Margin.Top.XSMALL


                );
                div.setHeight("260px");
                div.setWidth("320px");
    com.vaadin.flow.component.icon.Icon vaadinIcon = new Icon(VaadinIcon.CALENDAR_BRIEFCASE);
    vaadinIcon.getElement().getStyle().setColor("darkblue");

                vaadinIcon.setSize("30px");



        Paragraph description = new Paragraph("if you are facing serious charges.it is imperative that you seek assistance from a knowledgeable criminal defense lawyer as as possible. An experienced attorney can guide you through the legal process. Contact us for a better assistance.");
                description.addClassNames(LumoUtility.Margin.Vertical.MEDIUM,
    LumoUtility.FontSize.MEDIUM,
    LumoUtility.FlexDirection.COLUMN,
    LumoUtility.TextAlignment.JUSTIFY,
    LumoUtility.Margin.Horizontal.LARGE,
    LumoUtility.Margin.Left.XLARGE
                );
        H3 title = new H3("Let's get started");
        title.getElement().getStyle().set("margin-left","80px");
        title.getElement().getStyle().set("margin-top","-6px");

        div.add(vaadinIcon,description);
        verticalLayout.add(title,div);
        verticalLayout.setMargin(true);
        horizontalLayout.add(verticalLayout);
        horizontalLayout.getElement().getStyle().set("margin-top","7px");
        horizontalLayout.getElement().getStyle().set("margin-right","37px");

        return horizontalLayout;
    }

    private HorizontalLayout BookNow() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        VerticalLayout verticalLayout = new VerticalLayout();
        H3 title = new H3("Reach Us");
        title.getElement().getStyle().set("margin-left","32px");
        Button bookButton2 = new Button();
        bookButton2.setText("Book now");
        bookButton2.addClassNames(LumoUtility.Margin.Horizontal.AUTO, LumoUtility.MaxWidth.SCREEN_LARGE, LumoUtility.Margin.Bottom.XLARGE);
        bookButton2.getStyle().set("margin-top", "5px");
        bookButton2.getStyle().set("font-size", "18px");
        bookButton2.getStyle().set("height", "60px");
        bookButton2.getStyle().set("width", "200px");

        bookButton2.getStyle().set("color", "white");
        bookButton2.getStyle().set("background-color", "darkblue");

        bookButton2.addClassName(LumoUtility.Display.INLINE);
        verticalLayout.add(title,bookButton2);
        verticalLayout.setMargin(true);
        verticalLayout.getElement().getStyle().set("margin-right","10px");

        horizontalLayout.add(verticalLayout);
        return horizontalLayout;
    }
    }