package com.example.application.UI.Services;

import com.example.application.backend.entity.Service;
import com.example.application.backend.service.ServiceService;
import com.example.application.UI.Principals.HomePage;
import com.example.application.UI.Principals.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@PageTitle("Services | Land Lawyers ")
@Route(value = "services" , layout = MainView.class )
@AnonymousAllowed
public class ServiceListView extends VerticalLayout {

    @Autowired
    private final ServiceService serviceService;

    public ServiceListView(ServiceService serviceService) {
        this.serviceService = serviceService;
        //  the background image component
        Image backgroundImage = new Image("images/servicesImage.jpg", "Background Image");
        backgroundImage.setHeight("370px");
        // backgroundImage.setWidth("1425px");
        //backgroundImage.getStyle().set("margin-top", "800px");
        getElement().getStyle().set("width", "100%");
        getElement().getStyle().set("margin-bottom", "60px");

        backgroundImage.setWidth("100%");
        backgroundImage.getElement().getStyle().set("border", "none");
        backgroundImage.addClassName(LumoUtility.Margin.NONE);

        H1 title = new H1("Our Services");
        title.getStyle().setMargin("auto");
        title.getStyle().setColor("brown");
        title.getStyle().set("margin-top","25px");
        Text text1 = new Text("\n" +
                "The Land Lawyers litigation capacities, comprehensive legal assistance.");
        add(backgroundImage,

                title,text1
        );
        setMargin(false);
        setPadding(false);
        // Adjusting spacing and alignment
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setSpacing(true);
        setWidthFull();
        constructUI();

    }


    private void constructUI() {
        addClassNames("services");

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSpacing(true);

        List<Service> services = serviceService.findAll();
        int cardsPerRow = 3;
        int totalRows = 10;

        for (int row = 0; row < totalRows; row++) {
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.setSpacing(true);

            for (int col = 0; col < cardsPerRow; col++) {
                int index = row * cardsPerRow + col;
                if (index >= services.size()) {
                    break;
                }

                Service service = services.get(index);
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
                        LumoUtility.BorderColor.CONTRAST_60,
                        LumoUtility.Margin.MEDIUM

                );
                div.setHeight("230px");
                div.setWidth("320px");

                Icon vaadinIcon = new Icon(VaadinIcon.ACADEMY_CAP);
                vaadinIcon.setSize("40px");


                Span header = new Span(service.getService());
                header.addClassNames(
                        LumoUtility.FontSize.XLARGE,
                        LumoUtility.FontWeight.SEMIBOLD,
                        LumoUtility.JustifyContent.CENTER,
                        LumoUtility.Margin.Top.SMALL,
                        LumoUtility.TextColor.ERROR


                );

                Paragraph description = new Paragraph(service.getDescription());
                description.addClassNames(LumoUtility.Margin.Vertical.MEDIUM,
                        LumoUtility.FontSize.XSMALL,
                        LumoUtility.FlexDirection.COLUMN,
                        LumoUtility.TextAlignment.JUSTIFY,
                        LumoUtility.Margin.Horizontal.LARGE,
                        LumoUtility.Margin.Left.XLARGE

                );

                div.add(vaadinIcon,header,description);

                horizontalLayout.add(div);
            }

            verticalLayout.add(horizontalLayout);
            verticalLayout.addClassNames(LumoUtility.MaxWidth.SCREEN_LARGE, LumoUtility.Margin.Horizontal.AUTO, LumoUtility.Padding.Bottom.LARGE, LumoUtility.Padding.Horizontal.LARGE);

        }
        HorizontalLayout  footerLayout = createFoot() ;

        add(verticalLayout,footerLayout);
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
        footLayout.getStyle().set("margin-bottom", "-65px");
        footLayout.getStyle().set("margin-top", "23px");


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

