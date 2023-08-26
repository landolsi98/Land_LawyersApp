package com.example.application.ui.News;

import com.example.application.backend.entity.Noticia;
import com.example.application.backend.service.NoticiaService;
import com.example.application.ui.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
@PageTitle("Newspage")
@Route(value = "news" , layout = MainView.class)
@AnonymousAllowed
public class NewsView extends VerticalLayout {

    private NoticiaService noticiaService;
    public NewsView(NoticiaService noticiaService) {

        this.noticiaService = noticiaService;
        //  the background image component
        Image backgroundImage = new Image("images/backNews.jpg", "Background Image");
        backgroundImage.setHeight("300px");
        backgroundImage.getStyle().set("margin-bottom","30px");
        // backgroundImage.setWidth("1425px");
        //backgroundImage.getStyle().set("margin-top", "800px");
        getElement().getStyle().set("width", "100%");
        getElement().getStyle().set("margin-bottom", "60px");

        backgroundImage.setWidth("100%");
        backgroundImage.getElement().getStyle().set("border", "none");
        backgroundImage.addClassName(LumoUtility.Margin.NONE);


        add(backgroundImage

        );
        setMargin(false);
        setPadding(false);
        // Adjusting spacing and alignment
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setSpacing(true);
        setWidthFull();
        displayImages();

        HorizontalLayout footLayout = createFoot();
        add(footLayout);

    }



    private void displayImages() {
        Collection<Noticia> news = noticiaService.findAll();

        for (Noticia noticia : news) {
            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.addClassNames(LumoUtility.Margin.Horizontal.AUTO, LumoUtility.MaxWidth.SCREEN_LARGE, LumoUtility.Margin.Bottom.NONE);

            H1 title = new H1(noticia.getTitle());
            title.getStyle().set("font-size", "36px");

            Paragraph body = new Paragraph(noticia.getCuerpo());
            body.getStyle().set("width", "90%");

            Div dateDiv = new Div(new Text(String.valueOf(noticia.getDate())));
            dateDiv.getStyle().set("display", "inline-block");
            dateDiv.getStyle().set("margin-bottom", "10px");
            dateDiv.getStyle().set("margin-top", "20px");
            dateDiv.getStyle().set("width", "80%");

            // Apply border as padding-bottom for width control
            dateDiv.getStyle().set("padding-bottom", "50px");
            dateDiv.getStyle().set("border-bottom", "2px solid grey");

            verticalLayout.add(title, body, dateDiv);

            HorizontalLayout horizontalLayout = new HorizontalLayout();
            Image image = new Image(getImageResource(noticia), "Image not found");
            image.setHeight("350px");
            image.setWidth("600px");

            image.getStyle().set("border-radius","5px");
            horizontalLayout.add(verticalLayout, image);
            horizontalLayout.getStyle().setMargin("20px");
            horizontalLayout.getStyle().set("margin-bottom","-8px");
            add(horizontalLayout);
        }
    }




    private StreamResource getImageResource(Noticia noticia) {
        return new StreamResource(noticia.getTitle() + ".jpg", () -> {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(noticia.getImage());
            return inputStream;
        });
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

