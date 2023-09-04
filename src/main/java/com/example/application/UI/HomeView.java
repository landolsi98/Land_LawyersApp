package com.example.application.UI;

import com.example.application.UI.Principals.MainView;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;


@Route(value = "home",  layout = MainView.class )
@AnonymousAllowed
@PageTitle("Land Lawyers")
public class HomeView extends VerticalLayout {

    public HomeView() {
        // Set background image styling
        Html styleTag = new Html("<style>body { background-image: url('images/background.jpg'); background-size: 100% 500px; background-repeat: no-repeat; }</style>");
        add(styleTag);

        // Create the text overlay
        Div textOverlay = createTextOverlay();

        // Add text overlay
        add(textOverlay);

        // Set container styling
        addClassNames("image-list-view", LumoUtility.MaxWidth.SCREEN_LARGE, LumoUtility.Margin.Horizontal.AUTO, LumoUtility.Padding.Bottom.LARGE, LumoUtility.Padding.Horizontal.LARGE);

        // Create a HorizontalLayout for the image sets
        HorizontalLayout imageSetsLayout = createImageSetsLayout();
        imageSetsLayout.getElement().getStyle().set("margin-top","500px");
        imageSetsLayout.getElement().getStyle().set("position","fixed");

        // Add the image sets layout below the text overlay
        add(imageSetsLayout);


    }

    private Div createTextOverlay() {
        Div textOverlay = new Div();
        //textOverlay.getStyle().set("position", "absolute");
        textOverlay.getStyle().set("top", "30%");
        textOverlay.getStyle().set("left", "50%");
        textOverlay.getStyle().set("transform", "translate(-50%, -50%)");
        textOverlay.getStyle().set("text-align", "center");

        Div textDiv = new Div();
        textDiv.setText("Land Lawyers");
        textOverlay.getStyle().set("font-size", "66px");
        textOverlay.getStyle().set("color", "white");

        Button button = new Button("Book an Appointment");
        button.getStyle().set("font-size", "18px");
        button.getStyle().set("height", "48px");
        button.getStyle().set("color", "rgba(0, 19, 86, 1)");
        button.getStyle().set("background-color", "grey");

        Button button1 = new Button("test");
        Button button2 = new Button("NJareb");
        textOverlay.add(textDiv, button,button2);
        add(button1);

        return textOverlay;
    }

    private HorizontalLayout createImageSetsLayout() {
        HorizontalLayout imageSetsLayout = new HorizontalLayout();
        imageSetsLayout.setSpacing(true);

        for (int i = 0; i < 3; i++) {
            Div imageLayerContainer = createImageLayerContainer();
            imageSetsLayout.add(imageLayerContainer);
        }

        return imageSetsLayout;
    }

    private Div createImageLayerContainer() {
        Div imageLayerContainer = new Div();
        imageLayerContainer.addClassName("image-layer-container");

        Image foregroundImage = new Image("https://via.placeholder.com/150", "Foreground Image");
        foregroundImage.setWidth("150px");
        foregroundImage.addClassName("foreground-image");

        Image backgroundImage = new Image("https://via.placeholder.com/150/CCCCCC/FFFFFF?text=Background", "Background Image");
        backgroundImage.setWidth("150px");
        backgroundImage.addClassName("background-image");
        backgroundImage.getStyle().set("margin", "5px");

        imageLayerContainer.addClassNames(
                LumoUtility.BoxShadow.LARGE,
                LumoUtility.Display.FLEX,
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.CENTER,
                LumoUtility.Margin.Bottom.MEDIUM,
                LumoUtility.Overflow.HIDDEN,
                LumoUtility.BorderRadius.MEDIUM,
                LumoUtility.Width.FULL,
                LumoUtility.BorderColor.CONTRAST_80,

                LumoUtility.Margin.MEDIUM
        );
        imageLayerContainer.setHeight("230px");
        imageLayerContainer.setWidth("320px");

        imageLayerContainer.add(backgroundImage, foregroundImage);

        return imageLayerContainer;

    }



    }
