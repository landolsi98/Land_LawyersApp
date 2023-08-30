package com.example.application.ui;

import com.example.application.backend.entity.Case;
import com.example.application.backend.entity.User;
import com.example.application.backend.service.CaseService;
import com.example.application.security.AuthenticatedUser;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@UIScope
@PageTitle("Case monitoring")
@Route(value = "Case-tracking",layout = MainView.class)
@AnonymousAllowed
public class CaseTrackerView extends VerticalLayout {

    private final CaseService caseService;
   private final  AuthenticatedUser authenticatedUser;

    public CaseTrackerView(CaseService caseService, AuthenticatedUser authenticatedUser) {
        this.caseService = caseService;
        this.authenticatedUser = authenticatedUser;

        Image backgroundImage = new Image("images/TrackingImage.jpg", "Background Image");
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




        User currentUser = authenticatedUser.get().orElse(null);
        if (currentUser != null) {
            List<Case> userCases = new ArrayList<>();
            for (Case caseItem : caseService.findAll()) {
                if (caseItem.getUser().equals(currentUser)) {
                    userCases.add(caseItem);
                }
            }
            Grid<Case> grid = new Grid<>(Case.class, false);
            grid.addColumn(createEmployeeRenderer()).setHeader("Name")
                    .setAutoWidth(true).setFlexGrow(0);
            grid.addColumn(Case::getTitle).setHeader("Case")
                    .setAutoWidth(true);
            grid.addColumn(Case::getCreation_date).setHeader("Creation Date")
                    .setAutoWidth(true);
            grid.addColumn(createStateComponentRenderer()).setHeader("state")
                    .setAutoWidth(true);
            grid.addColumn(createLawyerRenderer()).setHeader("Lawyer")
                    .setAutoWidth(true).setFlexGrow(0);


            List<Case> cases = (List<Case>) caseService.findAll();
            grid.setItems(userCases);
            add(grid);
        }
    }

    private Renderer<Case> createEmployeeRenderer() {
        return LitRenderer.<Case>of(
                        "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                                + "<vaadin-avatar img=\"${item.user.pictureUrl}\" name=\"${item.user.firstName}\" alt=\"User avatar\"></vaadin-avatar>"
                                + "  <vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">"
                                + "    <span>${item.user.firstName}</span>"
                                + "    <span style=\"font-size: var(--lumo-font-size-s); color: var(--lumo-secondary-text-color);\">"
                                + "      ${item.user.email}"
                                + "    </span>"
                                + "  </vaadin-vertical-layout>"
                                + "</vaadin-horizontal-layout>")
                .withProperty("user", Case::getUser);

    }
    private Renderer<Case> createLawyerRenderer() {
        return LitRenderer.<Case>of(
                        "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                                + "<vaadin-avatar img=\"${item.avocat.image}\" name=\"${item.avocat.firstName}\" alt=\"Lawyer avatar\"></vaadin-avatar>"
                                + "  <vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">"
                                + "    <span>${item.avocat.firstName}</span>"
                                + "    <span style=\"font-size: var(--lumo-font-size-s); color: var(--lumo-secondary-text-color);\">"
                                + "      ${item.avocat.email}"
                                + "    </span>"
                                + "    <span>${item.avocat.barNumber}</span>" // Access lawyer's bar number
                                + "  </vaadin-vertical-layout>"
                                + "</vaadin-horizontal-layout>")
                .withProperty("avocat", Case::getAvocat);
    }



    private static final SerializableBiConsumer<Span, Case> stateComponentUpdater = (
            span, caseObject) -> {
        String state = caseObject.getState();
        Span badge = new Span();

        switch (state) {
            case "In Progress":
                badge = new Span(new Span("In Progress"),
                        createIcon(VaadinIcon.CLOCK));
                badge.getElement().getThemeList().add("badge");
                break;
            case "On Hold":
                badge = new Span(new Span("On hold"),
                        createIcon(VaadinIcon.HAND));
                badge.getElement().getThemeList().add("badge contrast");
                break;
            case "Closed":
                badge = new Span(new Span("Closed"),
                        createIcon(VaadinIcon.CHECK));
                badge.getElement().getThemeList().add("badge success");
                break;
            case "Requires":
                badge = new Span(new Span("Requires"),
                        createIcon(VaadinIcon.EXCLAMATION_CIRCLE_O));
                badge.getElement().getThemeList().add("badge error");
                break;
            default:
                badge = new Span(createIcon(VaadinIcon.CLOCK),
                        new Span("Pending"));
                badge.getElement().getThemeList().add("badge");
                break;
        }

        span.removeAll();
        span.getElement().setAttribute("theme", badge.getElement().getThemeList().toString());
        span.add(badge);
    };

    private static ComponentRenderer<Span, Case> createStateComponentRenderer() {
        return new ComponentRenderer<>(Span::new, stateComponentUpdater);
    }

    private static Icon createIcon(VaadinIcon vaadinIcon) {
        Icon icon = vaadinIcon.create();
        icon.getStyle().set("padding", "var(--lumo-space-xs)");
        return icon;
    }

}




