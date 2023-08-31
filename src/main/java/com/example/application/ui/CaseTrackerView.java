package com.example.application.ui;

import com.example.application.backend.entity.Case;
import com.example.application.backend.entity.User;
import com.example.application.backend.service.CaseService;
import com.example.application.security.AuthenticatedUser;
import com.lowagie.text.pdf.codec.Base64;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.crud.CrudI18n;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.RolesAllowed;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@UIScope
@PageTitle("Case monitoring")
@Route(value = "Case-tracking",layout = MainView.class)
@RolesAllowed("CLIENT")
public class CaseTrackerView extends VerticalLayout {

    private final CaseService caseService;
   private final  AuthenticatedUser authenticatedUser;

    public CaseTrackerView(CaseService caseService, AuthenticatedUser authenticatedUser) {
        this.caseService = caseService;
        this.authenticatedUser = authenticatedUser;

        Image backgroundImage = new Image("images/TrackingImage.jpg", "Background Image");
        backgroundImage.setHeight("300px");
        backgroundImage.getStyle().set("margin-bottom", "30px");
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

        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();

        Upload upload = new Upload(buffer);

        VerticalLayout layout = new VerticalLayout();
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

        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);

            // Convert the InputStream to a byte array
            byte[] document = readInputStream(inputStream);

            User currentUser = authenticatedUser.get().orElse(null);
            if (currentUser != null) {
                // Find or create a case for the user
                Case userCase = caseService.findCaseByUser(currentUser);
                if (userCase == null) {
                    userCase = new Case();
                    userCase.setClient(currentUser);
                    userCase = caseService.add(userCase);
                }


                // Set the document to the case
                userCase.setDocument(document);
                caseService.update(userCase); // Update the case
            }
        });

        User currentUser = authenticatedUser.get().orElse(null);
        if (currentUser != null) {
            Case userCase = caseService.findCaseByUser(currentUser);
            grid.setItems(userCase);

        }

        layout.add(grid,upload);
        add(layout);


    }

    private Component createDocumentLinkRenderer(Case caso) {
        if (caso.getDocument() != null) {
            Button linkButton = new Button("View Document");
            linkButton.addClickListener(event -> {
                // Redirect to the PDF viewer page
                String pdfViewerLink = "/pdf-viewer?caseId=" + caso.getIdCase();
                UI.getCurrent().getPage().setLocation(pdfViewerLink);
            });
            return linkButton;
        } else {
            return new PasswordField("No Document Available");
        }
    }

        private byte[] readInputStream(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
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



     /*
        PdfViewer pdfViewer = new PdfViewer();
        if (userCase != null && userCase.getDocument() != null) {
            //File pdfFile = new File("/Users/macbookair/Downloads/landlawyersapp23/src/main/resources/META-INF/resources/pdf/CV_Jawher2023EN.pdf");
            StreamResource resource = new StreamResource("CV_Jawher2023EN.pdf", () -> {
                try {
                    return new ByteArrayInputStream(userCase.getDocument());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });
            pdfViewer.setSrc(resource);



// Set the layout as the content for your UI
            add(layout);

        }
    }

         */
