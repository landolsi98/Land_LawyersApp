package com.example.application.ui.Lawyers;

import com.example.application.backend.entity.Abogado;
import com.example.application.backend.entity.Avocat;
import com.example.application.backend.entity.User;
import com.example.application.backend.service.AbogadoService;
import com.example.application.backend.service.AvocatService;
import com.example.application.backend.service.LawyerService;
import com.example.application.backend.service.UserService;
import com.example.application.ui.HomePage;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@AnonymousAllowed
@PageTitle("Team List")
@Route(value = "team",layout = MainView.class)
public class TeamView extends VerticalLayout {

   private final  UserService userService;
private final AbogadoService abogadoService;

private AvocatService avocatService;
    public TeamView(UserService userService, AbogadoService abogadoService,AvocatService avocatService) {
        this.userService = userService;
        this.abogadoService = abogadoService;
        this.avocatService = avocatService;

        //  the background image component
        Image backgroundImage = new Image("images/lawOffice2.jpeg", "Background Image");
        backgroundImage.setHeight("370px");
        // backgroundImage.setWidth("1425px");
        //backgroundImage.getStyle().set("margin-top", "800px");
        getElement().getStyle().set("width", "100%");
        getElement().getStyle().set("margin-bottom", "60px");

        backgroundImage.setWidth("100%");
        backgroundImage.getElement().getStyle().set("border", "none");
        backgroundImage.addClassName(LumoUtility.Margin.NONE);

        H1 title = new H1("Our Team");
        title.getStyle().setMargin("auto");
        title.getStyle().setColor("darkblue");
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
        displayImages();
       HorizontalLayout  layout = createFoot() ;
add(layout);

    }






    private void displayImages() {



        List<Avocat> abogados = (List<Avocat>) avocatService.findAll();

        Avocat mainAbogado = null;
        List<Avocat> otherAbogados = new ArrayList<>();

        // Split abogados into mainAbogado and otherAbogados
        for (Avocat avocat : abogados) {
            if (Objects.equals(avocat.getNumberBar(),"Y88987K")) {
                mainAbogado = avocat;
            } else if (avocat.getImage() != null && avocat.getImage().length > 0) {
                otherAbogados.add(avocat);
            } else {
                Text error = new Text("no hay abogado 1");
                add(error);
            }
        }


            // Display mainAbogado vertically
        if (mainAbogado != null) {
            Image mainImage = new Image(getImageResource(mainAbogado), "Image not found");
            mainImage.setWidth("460px"); // Set an appropriate height
            mainImage.setHeight("450px");
            mainImage.getStyle().setMargin("auto");
            mainImage.getStyle().set("border-radius","8px");


            Paragraph LawyerName = new Paragraph(mainAbogado.getFirstName() );


            LawyerName.getStyle().set("font-size","28px");
            LawyerName.getStyle().set("font-weight" , "bold");
            LawyerName.getStyle().setMargin("auto");

            H4 position = new H4(mainAbogado.getPosition());
            position.getStyle().set("font-size","14px");
            position.getStyle().set("margin-top","-5px");
            position.getStyle().setColor("grey");
            position.getStyle().setMargin("auto");

            Paragraph description = new Paragraph(mainAbogado.getDescription());
            description.getStyle().set("font-size", "16px");
            description.setWidth("50%");
            description.getStyle().set("test-align","center");
            description.getStyle().setMargin("auto");

            add(mainImage, LawyerName, position, description);
        }


        // Display otherAbogados horizontally
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        for (Avocat avocat : otherAbogados) {
            Image image = new Image(getImageResource(avocat), "Image not found");
            image.setWidth("420px");
            image.setHeight("450px");
            image.setWidth("100%");
            image.getStyle().set("border-radius","8px");


            Paragraph info = new Paragraph(avocat.getFirstName());
            info.getStyle().set("font-size","28px");
            info.getStyle().set("font-weight" , "bold");
            info.getStyle().setMargin("auto");

            H4 position2 = new H4(avocat.getPosition());
            position2.getStyle().set("font-size","14px");
            position2.getStyle().set("margin-top","-5px");
            position2.getStyle().setColor("grey");
            position2.getStyle().setMargin("auto");

            Paragraph description2 = new Paragraph(avocat.getDescription());
            description2.getStyle().set("font-size", "16px");
            description2.setWidth("70%");
            description2.getStyle().set("test-align","center");
            description2.getStyle().setMargin("auto");


            VerticalLayout lawyerLayout = new VerticalLayout(image, info,position2,description2);
            horizontalLayout.add(lawyerLayout);
        }
        add(horizontalLayout);
    }

    private StreamResource getImageResource(Avocat avocat) {
        return new StreamResource(avocat.getFirstName() + ".jpg", () -> {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(avocat.getImage());
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
