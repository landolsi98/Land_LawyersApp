package com.example.application.ui;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle("Image List")
@Route(value = "services")
@AnonymousAllowed
public class ServicesView extends Main implements HasComponents, HasStyle {



    public ServicesView() {
        addClassNames(LumoUtility.Background.CONTRAST_5, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN, LumoUtility.AlignItems.START, LumoUtility.Padding.MEDIUM,
                LumoUtility.BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(LumoUtility.Background.CONTRAST, LumoUtility.Display.FLEX, LumoUtility.AlignItems.CENTER, LumoUtility.JustifyContent.CENTER,
                LumoUtility.Margin.Bottom.MEDIUM, LumoUtility.Overflow.HIDDEN, LumoUtility.BorderRadius.MEDIUM, LumoUtility.Width.FULL);
        div.setHeight("160px");

        Image image = new Image();
        image.setWidth("100%");


        div.add(image);

        Span header = new Span();
        header.addClassNames(LumoUtility.FontSize.XLARGE, LumoUtility.FontWeight.SEMIBOLD);
        header.setText("Title");

        Span subtitle = new Span();
        subtitle.addClassNames(LumoUtility.FontSize.SMALL, LumoUtility.TextColor.SECONDARY);
        subtitle.setText("Card subtitle");

        Paragraph description = new Paragraph(
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut.");
        description.addClassName(LumoUtility.Margin.Vertical.MEDIUM);

        Span badge = new Span();
        badge.getElement().setAttribute("theme", "badge");
        badge.setText("Label");

        add(div, header, subtitle, description, badge);

    }
}
