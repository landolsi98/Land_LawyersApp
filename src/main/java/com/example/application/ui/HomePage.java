package com.example.application.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

@PageTitle("inicio")
@AnonymousAllowed
@Route(value = "inicio" , layout = MainView.class)
public class HomePage extends VerticalLayout {

    public HomePage() {

        setSizeFull(); // Set the size of the layout to full screen

        //  the background image component
        Image backgroundImage = new Image("images/background.jpg", "Background Image");
        backgroundImage.setHeight("600px");
        backgroundImage.setWidth("1425px");
        backgroundImage.getStyle().set("margin-top", "800px");

        //the text overlay
        Div textOverlay = createTextOverlay();


        // Adding components to the layout
        add(backgroundImage, textOverlay);

        // Adjusting spacing and alignment
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setSpacing(true);

        HorizontalLayout imageSetsLayout = createImageSetsLayout();
        add(imageSetsLayout);

        HorizontalLayout aboutUsLayout = aboutUs();
        HorizontalLayout footerLayout = createFooter();

        add(aboutUsLayout,footerLayout);


    }

    //Creation of Button and Land Lawyers title
    private Div createTextOverlay() {
        Div textOverlay = new Div();
        //textOverlay.getStyle().set("position", "absolute");
        textOverlay.getStyle().set("top", "60%");
        textOverlay.getStyle().set("left", "20%");
        textOverlay.getStyle().set("transform", "translate(-50%, -50%)");
        textOverlay.getStyle().set("text-align", "center");
        textOverlay.getStyle().set("margin-top", "-300px");
        textOverlay.getStyle().set("margin-left", "300px");
        textOverlay.getStyle().set("margin-bottom", "50px");


        Div textDiv = new Div();
        textDiv.setText("Land Lawyers");
        textOverlay.getStyle().set("font-size", "66px");
        textOverlay.getStyle().set("color", "white");

        Button button = new Button("Book an Appointment");
        button.getStyle().set("font-size", "18px");
        button.getStyle().set("height", "48px");
        button.getStyle().set("color", "rgba(0, 19, 86, 1)");
        button.getStyle().set("background-color", "grey");


        textOverlay.add(textDiv, button);

        return textOverlay;
    }

    // Creation of 3 boxes under the image
    private HorizontalLayout createImageSetsLayout() {
        HorizontalLayout imageSetsLayout = new HorizontalLayout();
        imageSetsLayout.setSpacing(true);


        String[] titles = {"Our Office", "Expertise Areas", "Contact Us"};


        for (int i = 0; i < 3; i++) {
            Div imageLayerContainer = createImageLayerContainer(titles[i]);
            imageSetsLayout.add(imageLayerContainer);
        }

        return imageSetsLayout;
    }

    private Div createImageLayerContainer(String title) {
        Div imageLayerContainer = new Div();
        imageLayerContainer.addClassName("image-layer-container");

        Icon homeIcon = new Icon(VaadinIcon.GAVEL);
        homeIcon.setSize("60px");
        homeIcon.setColor("tan");
        homeIcon.getStyle().set("margin-bottom", "40px");

        imageLayerContainer.addClassNames(

                LumoUtility.Display.FLEX,
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.CENTER,
                LumoUtility.Margin.Bottom.MEDIUM,
                LumoUtility.Overflow.HIDDEN,
                LumoUtility.BorderRadius.MEDIUM,
                LumoUtility.Width.FULL,
                LumoUtility.Margin.Top.NONE,
                LumoUtility.BorderColor.WARNING,
                LumoUtility.Background.BASE,
                LumoUtility.Border.ALL,
                LumoUtility.Background.BASE

        );
        RouterLink titleLink = new RouterLink(title, getTargetViewForTitle(title));
        titleLink.addClassName("box-title");
        titleLink.getStyle().set("font-size", "32px");
        imageLayerContainer.add(titleLink);
        titleLink.getStyle().setColor("navy");
        titleLink.getStyle().set("margin-bottom", "40px");

        imageLayerContainer.add(homeIcon, titleLink);
        imageLayerContainer.setHeight("260px");
        imageLayerContainer.setWidth("350px");
        imageLayerContainer.getStyle().set("margin-right", "-18px");
        imageLayerContainer.getStyle().set("margin-top", "3px");

        RouterLink seeMoreLink = new RouterLink("See More...", getTargetViewForTitle(title));
        seeMoreLink.addClassName("see-more-link");
        seeMoreLink.getStyle().setColor("grey");
        seeMoreLink.getStyle().set("font-size", "14px");
        seeMoreLink.getStyle().set("margin-top", "-35px");
        imageLayerContainer.add(seeMoreLink);


        return imageLayerContainer;
    }

    // Navigation routes for the title into the boxes
    private Class<? extends Component> getTargetViewForTitle(String title) {
        switch (title) {
            case "Our Office":
                return CitaView.class;
            case "Expertise Areas":
                return ServiceListView.class;
            case "Contact Us":
                return DashNoticias.class;
            default:
                return HomePage.class;
        }
    }

    private HorizontalLayout aboutUs() {



        HorizontalLayout aboutUs = new HorizontalLayout();

        aboutUs.addClassNames(LumoUtility.Margin.Horizontal.AUTO,LumoUtility.MaxWidth.SCREEN_LARGE);

        Image aboutImage = new Image("images/lawOffice.jpg", "About us");
        aboutImage.setWidth("650px");
        aboutImage.setHeight("350px");
        aboutImage.getStyle().set("margin-right","20px");
        aboutImage.getStyle().set("margin-left","10px");
        aboutImage.addClassName(LumoUtility.BorderRadius.SMALL);

H1 aboutTitle = new H1(" Land Lawyers Office Presentation");
aboutTitle.getStyle().set("margin-top","-18px");

        Paragraph aboutParagraph = new Paragraph("Land Lawyers, a law firm with a focus on Business Law and others, was founded in 2008 by Jawher Landoulssi, a lawyer associated with the Tunisian bar. Since its inception, the firm has rapidly ascended to a prominent position within Tunisia, emerging as a top-tier player in its field of expertise.\n" +
                "\n" +
                "The firm's reputation is built upon its exceptional professional acumen, unwavering commitment, and adherence to European and Anglo-Saxon standards in its practice. This ensures that clients receive continual support for their endeavors, spanning both national and international realms.");

        aboutParagraph.getStyle().set("font-size" ,"14px");
        aboutParagraph.getStyle().set("margin-top","-10px");
        aboutUs.add(aboutImage, createAboutContentLayout(aboutTitle, aboutParagraph));


        return aboutUs;
    }

    private VerticalLayout createAboutContentLayout(H1 title, Paragraph paragraph) {
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.add(title, paragraph);
        return contentLayout;
    }

    private HorizontalLayout createFooter() {

        Div div = new Div();
        div.addClassNames(
                LumoUtility.Display.FLEX,
                LumoUtility.Margin.Bottom.MEDIUM,
                LumoUtility.BorderRadius.MEDIUM
        );
        div.setHeight("420px");

        HorizontalLayout footerLayout = new HorizontalLayout();
        footerLayout.addClassNames(
                LumoUtility.Background.WARNING
        );
        footerLayout.getStyle().setWidth("1425px");
        footerLayout.getStyle().setHeight("625px");




        Div footerContent = new Div();
        footerContent.setText("© 2023 Your Company. All rights reserved.");
        footerContent.getStyle().setColor("white");



        footerLayout.add(div,footerContent);

        return footerLayout;
    }



}
