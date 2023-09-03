package com.example.application.ui.Services;

import com.example.application.backend.entity.Service;
import com.example.application.backend.service.ServiceService;
import com.example.application.ui.HomePage;
import com.example.application.ui.MainView;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
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

@PageTitle("Services")
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
HorizontalLayout footer = HomePage.createFoot();
add(footer);
    }


    private void constructUI() {
        addClassNames("services");
        //big container
        //addClassNames(LumoUtility.MaxWidth.SCREEN_LARGE, LumoUtility.Margin.Horizontal.AUTO, LumoUtility.Padding.Bottom.LARGE, LumoUtility.Padding.Horizontal.LARGE);
        //addClassNames( LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN, LumoUtility.AlignItems.CENTER, LumoUtility.Padding.MEDIUM,
               // LumoUtility.BorderRadius.LARGE);


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

        add(verticalLayout);
    }
}

