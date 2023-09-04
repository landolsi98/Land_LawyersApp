package com.example.application.UI.Principals;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.stereotype.Component;
/*
public class Footer extends VerticalLayout{
    public Footer() {
        HorizontalLayout footLayout = createFoot();
add(footLayout);
    }

    public static HorizontalLayout createFoot() {
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

    public static VerticalLayout createLinksColumn1() {
        VerticalLayout linksLayout = new VerticalLayout();
        linksLayout.addClassNames(LumoUtility.Margin.Left.XLARGE,LumoUtility.Margin.Top.LARGE);

        linksLayout.add(createFooterLink("Home", "http://localhost:8080/inicio"));
        linksLayout.add(createFooterLink("About", "http://localhost:8080/services"));
        linksLayout.add(createFooterLink("Our Services", "#team"));
        // Add more links as needed

        return linksLayout;
    }

    public static VerticalLayout createLinksColumn2() {
        VerticalLayout linksLayout = new VerticalLayout();
        linksLayout.addClassNames(LumoUtility.Margin.Left.NONE,LumoUtility.Margin.Top.LARGE);

        linksLayout.add(createFooterLink("Our Team", "#book-now"));
        linksLayout.add(createFooterLink("News", "#contact"));
        linksLayout.add(createFooterLink("Book an Appointment", "#see-more"));
        // Add more links as needed

        return linksLayout;
    }
    public static VerticalLayout createLinksColumn3() {
        VerticalLayout linksLayout = new VerticalLayout();
        linksLayout.addClassNames(LumoUtility.Margin.Left.NONE,LumoUtility.Margin.Top.LARGE);

        linksLayout.add(createFooterLink("Contact", "#book-now"));
        linksLayout.add(createFooterLink("Email : LandLawyers@gmail.com", "#contact"));
        linksLayout.add(createFooterLink("Tel Number : +216 21 997 664", "#see-more"));

        return linksLayout;
    }

    public static Anchor createFooterLink(String label, String targetUrl) {
        Anchor link = new Anchor(targetUrl);
        link.setText(label);
        link.getStyle().set("color", "white");
        link.getStyle().set("text-decoration", "none");
        link.getStyle().set("margin-bottom", "8px");

        return link;
    }
}

 */
