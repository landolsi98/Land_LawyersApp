package com.example.application.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.GridVariant;
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

@PageTitle("Homepage | Land Lawyers ")
@AnonymousAllowed
@Route(value = "inicio" , layout = MainView.class)
public class HomePage extends VerticalLayout {

    public HomePage() {

        //setSizeFull();

        //  the background image component
        Image backgroundImage = new Image("images/background.jpg", "Background Image");
        backgroundImage.setHeight("500px");
        // backgroundImage.setWidth("1425px");
        //backgroundImage.getStyle().set("margin-top", "800px");
        getElement().getStyle().set("width", "100%");
        getElement().getStyle().set("margin-bottom", "30px");

        backgroundImage.setWidth("100%");
        backgroundImage.getElement().getStyle().set("border", "none");
        backgroundImage.addClassName(LumoUtility.Margin.NONE);
        //the text overlay
        Div textOverlay = createTextOverlay();


        // Adding components to the layout
        add(backgroundImage, textOverlay);
        setMargin(false);
        setPadding(false);
        // Adjusting spacing and alignment
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setSpacing(true);
        setWidthFull();

        //  additional styles to remove container spacing
        getStyle().set("border", "none");
        getStyle().set("padding", "0");
        getStyle().set("margin", "0");

        HorizontalLayout imageSetsLayout = createImageSetsLayout();
        add(imageSetsLayout);

        HorizontalLayout aboutUsLayout = aboutUs();
        HorizontalLayout footerLayout = createBand();
        HorizontalLayout serviceImages = VerticalImages();
        HorizontalLayout footLayout = createFoot();

        add(aboutUsLayout, footerLayout,serviceImages,footLayout);


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
        imageSetsLayout.setSpacing(false);


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
                LumoUtility.Margin.Top.LARGE,
                LumoUtility.BorderRadius.MEDIUM,
                LumoUtility.Width.FULL,
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
                return TeamView.class;
            case "Expertise Areas":
                return ServiceListView.class;
            case "Contact Us":
                return MapViewPort.class;
            default:
                return HomePage.class;
        }
    }

    private HorizontalLayout aboutUs() {


        HorizontalLayout aboutUs = new HorizontalLayout();

        aboutUs.addClassNames(LumoUtility.Margin.Horizontal.AUTO, LumoUtility.MaxWidth.SCREEN_LARGE, LumoUtility.Margin.Bottom.XLARGE);

        Image aboutImage = new Image("images/lawOffice.jpg", "About us");
        aboutImage.setWidth("650px");
        aboutImage.setHeight("350px");
        aboutImage.getStyle().set("margin-right", "20px");
        aboutImage.getStyle().set("margin-left", "0px");
        aboutImage.addClassName(LumoUtility.BorderRadius.SMALL);

        H1 aboutTitle = new H1(" Land Lawyers Office Presentation");
        aboutTitle.getStyle().set("margin-top", "-18px");

        Paragraph aboutParagraph = new Paragraph("Land Lawyers, a law firm with a focus on Business Law and others, was founded in 2008 by Jawher Landoulssi, a lawyer associated with the Tunisian bar. Since its inception, the firm has rapidly ascended to a prominent position within Tunisia, emerging as a top-tier player in its field of expertise.\n" +
                "\n" +
                "The firm's reputation is built upon its exceptional professional acumen, unwavering commitment, and adherence to European and Anglo-Saxon standards in its practice. This ensures that clients receive continual support for their endeavors, spanning both national and international realms.");

        aboutParagraph.getStyle().set("font-size", "14px");
        aboutParagraph.getStyle().set("margin-top", "-10px");
        aboutUs.add(aboutImage, createAboutContentLayout(aboutTitle, aboutParagraph));


        return aboutUs;
    }

    private VerticalLayout createAboutContentLayout(H1 title, Paragraph paragraph) {
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.add(title, paragraph);
        return contentLayout;
    }

    private HorizontalLayout createBand() {

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
        footerLayout.getStyle().setWidth("100%");
        footerLayout.getStyle().setHeight("150px");

        footerLayout.getStyle().set("background-image", "url('images/grey3.jpg')");
        footerLayout.getStyle().set("background-size", "cover");
        footerLayout.getStyle().set("background-repeat", "no-repeat");
        footerLayout.getStyle().set("background-position", "center center");
        footerLayout.getStyle().set("margin-bottom","-16px");


        Div footerContent = new Div();
        footerContent.setText("Legal Consultation");
        footerContent.getStyle().set("font-size", "36px");
        footerContent.getStyle().set("font-weight", "bold");
        footerContent.getStyle().set("margin-top", "40px");

        footerContent.addClassNames(LumoUtility.Margin.Horizontal.AUTO, LumoUtility.MaxWidth.SCREEN_LARGE, LumoUtility.Margin.Bottom.XLARGE);
        footerContent.getStyle().set("color", "darkblue");

        Button bookButton = new Button();
        bookButton.setText("Book now");
        bookButton.addClassNames(LumoUtility.Margin.Horizontal.AUTO, LumoUtility.MaxWidth.SCREEN_LARGE, LumoUtility.Margin.Bottom.XLARGE);
        bookButton.getStyle().set("margin-top", "45px");
        bookButton.getStyle().set("margin-bottom", "-18px");

        bookButton.getStyle().set("font-size", "18px");
        bookButton.getStyle().set("height", "50px");
        bookButton.getStyle().set("width", "150px");

        bookButton.getStyle().set("color", "white");
        bookButton.getStyle().set("background-color", "darkblue");

        bookButton.addClassName(LumoUtility.Display.INLINE);
        footerLayout.add(div, footerContent, bookButton);

        return footerLayout;
    }

    // Creation of vertical images
    public HorizontalLayout VerticalImages() {
        HorizontalLayout serviceImages = new HorizontalLayout();
        H1 services = new H1("Our Services ");
        services.getStyle().setColor("darkgoldenrod");
        services.getStyle().set("margin-top", "80px");
        services.getStyle().set("margin-right", "40px");

        Anchor link1 = createImageLink("images/lawV1.jpg", "verticalImage1", "Civil Rights");
        Anchor link2 = createImageLink("images/lawV2.jpg", "verticalImage2", "Buisness Law");
        Anchor link3 = createImageLink("images/lawV3.jpg", "verticalImage3", "Labour Law");
        Anchor link4 = createImageLink("images/lawV4.jpeg", "verticalImage4", "Private Equity");

        serviceImages.add(services, link1, link2, link3, link4);

        return serviceImages;
    }

    private Anchor createImageLink(String imageUrl, String imageAlt, String label) {
        Image image = new Image(imageUrl, imageAlt);
        image.setWidth("180px");
        image.setHeight("450px");

        Anchor link = new Anchor("http://localhost:8080/services");

        Div container = new Div();
        container.getStyle().set("position", "relative");
        container.add(image);

        Div labelDiv = new Div();
        labelDiv.setText(label);
        labelDiv.getStyle().set("position", "absolute");
        labelDiv.getStyle().set("top", "0");
        labelDiv.getStyle().set("left", "0");
        labelDiv.getStyle().set("width", "100%");
        labelDiv.getStyle().set("height", "40px");
        labelDiv.getStyle().set("font-size", "18px");
        labelDiv.getStyle().set("font-weight", "bold");
        labelDiv.getStyle().set("color", "midnightblue");


        labelDiv.getStyle().set("text-align", "center");
        labelDiv.getStyle().set("background-color", "rgba(255, 255, 255, 0.8)");

        container.add(labelDiv);
        link.add(container);

        return link;
    }

    private HorizontalLayout createFoot() {
        Div div = new Div();
        div.addClassNames(
                LumoUtility.Display.FLEX,
                LumoUtility.Margin.Bottom.MEDIUM,
                LumoUtility.BorderRadius.MEDIUM
        );
        div.setHeight("420px");

        HorizontalLayout footLayout = new HorizontalLayout();
        footLayout.addClassNames(
                LumoUtility.Background.CONTRAST
        );
        footLayout.getStyle().setWidth("100%");
        footLayout.getStyle().setHeight("250px");
        footLayout.getStyle().set("margin-bottom", "-38px");
        footLayout.getStyle().set("margin-top", "-23px");


        // Content on the left side
        Div footContent = new Div();
        Image logoImage = new Image("images/LogoLandL23.jpg", "Land Lawyer Logo");
        logoImage.setWidth("180px");
        logoImage.setHeight("200px");
        footContent.add(logoImage);

        Div footerContent1 = new Div();
        footerContent1.getStyle().set("color", "white");
        footerContent1.setText("© 2023 LandLawyers");
        footerContent1.getStyle().set("margin-top","10px");
        footContent.add(footerContent1);

        // Links on the right side
        VerticalLayout linksColumn1 = createLinksColumn1();
        VerticalLayout linksColumn2 = createLinksColumn2();
        VerticalLayout linksColumn3 = createLinksColumn3();

        Icon fbIcon = new Icon(VaadinIcon.FACEBOOK_SQUARE);
        Icon twitterIcon = new Icon(VaadinIcon.TWITTER);
        fbIcon.setColor("white");
        twitterIcon.setColor("white");

        //  icons
        VerticalLayout socialIconsLayout = new VerticalLayout(fbIcon, twitterIcon);
        socialIconsLayout.setSpacing(true);
        socialIconsLayout.getStyle().set("margin-top", "20px");
        footLayout.add(div, footContent, linksColumn1, linksColumn2,linksColumn3,socialIconsLayout);

        return footLayout;
    }

    private VerticalLayout createLinksColumn1() {
        VerticalLayout linksLayout = new VerticalLayout();
        linksLayout.addClassNames(LumoUtility.Margin.Left.XLARGE,LumoUtility.Margin.Top.LARGE);

        linksLayout.add(createFooterLink("Home", "http://localhost:8080/inicio"));
        linksLayout.add(createFooterLink("About", "http://localhost:8080/services"));
        linksLayout.add(createFooterLink("Our Services", "#team"));
        // Add more links as needed

        return linksLayout;
    }

    private VerticalLayout createLinksColumn2() {
        VerticalLayout linksLayout = new VerticalLayout();
        linksLayout.addClassNames(LumoUtility.Margin.Left.NONE,LumoUtility.Margin.Top.LARGE);

        linksLayout.add(createFooterLink("Our Team", "#book-now"));
        linksLayout.add(createFooterLink("News", "#contact"));
        linksLayout.add(createFooterLink("Book an Appointment", "#see-more"));
        // Add more links as needed

        return linksLayout;
    }
    private VerticalLayout createLinksColumn3() {
        VerticalLayout linksLayout = new VerticalLayout();
        linksLayout.addClassNames(LumoUtility.Margin.Left.NONE,LumoUtility.Margin.Top.LARGE);

        linksLayout.add(createFooterLink("Contact", "#book-now"));
        linksLayout.add(createFooterLink("Email : LandLawyers@gmail.com", "#contact"));
        linksLayout.add(createFooterLink("Tel Number : +216 21 997 664", "#see-more"));

        return linksLayout;
    }

    private Anchor createFooterLink(String label, String targetUrl) {
        Anchor link = new Anchor(targetUrl);
        link.setText(label);
        link.getStyle().set("color", "white");
        link.getStyle().set("text-decoration", "none");
        link.getStyle().set("margin-bottom", "8px");

        return link;
    }
}

