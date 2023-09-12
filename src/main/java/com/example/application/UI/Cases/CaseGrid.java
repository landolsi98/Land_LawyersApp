package com.example.application.UI.Cases;

import com.example.application.backend.entity.Case;
import com.example.application.backend.service.CaseService;
import com.example.application.UI.Principals.MainView;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.componentfactory.pdfviewer.PdfViewer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Base64;



import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope("prototype")
@Route(value = "Case-grid", layout = MainView.class)
@PageTitle("Cases | Land Lawyers")
@RolesAllowed({"ADMIN", "LAWYER"})
public class CaseGrid extends VerticalLayout {


    Grid<Case> grid = new Grid<>(Case.class);
    TextField filterText = new TextField();
    CaseForm form1;
    private Span status;

UserInfo userInfo;
    @Autowired
    private CaseService caseService;

        private void saveCase(CaseForm.SaveEvent event) {
            caseService.add(event.getCase());
        updateList();
        closeEditor();
      Notification notification = Notification
            .show("Case Added Successfully");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        notification.setPosition(Notification.Position.BOTTOM_START);
}
    private void deleteCase(CaseForm.DeleteEvent event) {
        status = new Span();
        status.setVisible(false);

        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Delete This Case ?");
        dialog.setText("Are you sure you want to delete it?");

        dialog.setCancelable(true);

        dialog.setConfirmText("Delete");
        dialog.setConfirmButtonTheme("error primary");

        dialog.addConfirmListener(e -> {
            caseService.delete(event.getCase());
                    updateList();
                    closeEditor();
                    Notification successNotification = Notification
                            .show("Case Deleted Successfully");
                    successNotification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
                    successNotification.setPosition(Notification.Position.BOTTOM_START);
                }
        );
    }

    private CaseGrid(@Autowired CaseService caseService) {
        this.caseService = caseService;
        this.form1 = new CaseForm(caseService.findAllServices(),caseService.findAllUsers(),caseService.findAllAbogados());

        grid.setColumns("title", "description", "state","creation_date");
        grid.setItems(caseService.findAll());
        grid.addColumn(service -> service.getService().getService()).setHeader("Service");
        grid.addColumn(user -> user.getUser().getFirstName()).setHeader("User");
        grid.addColumn(avocat -> avocat.getAbogado().getFirstName()).setHeader("Lawyer");
        grid.getColumns().forEach(col -> col.setAutoWidth(false));
        grid.addComponentColumn(this::createDocumentLinkRenderer)
                .setHeader("Document");


        grid.asSingleSelect().addValueChangeListener(event ->
                editCase(event.getValue()));


        form1 = new CaseForm(caseService.findAllServices(),caseService.findAllUsers(),caseService.findAllAbogados());
        form1.addSaveListener(CaseForm.SaveEvent.class, this::saveCase);
        form1.addDeleteListener(CaseForm.DeleteEvent.class, this::deleteCase);
        form1.addCloseListener(CaseForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form1);
        content.setSizeFull();
        content.getStyle().set("display", "flex");
        content.getStyle().set("flex", "row");

        add(getToolbar(), grid, content);
        closeEditor();
        pdfView();

    }



    private Component createDocumentLinkRenderer(Case caso) {
        if (caso.getDocument() != null) {
            Button viewDocumentButton = new Button("View Document", event -> {
                Long caseId = Long.valueOf(caso.getIdCase()); // Get the case ID
                System.out.println("????????"  +  caseId.toString());
                 UI.getCurrent().navigate("pdf-viewer", QueryParameters.fromString(caseId.toString()));
            });

            return viewDocumentButton;
        } else {
            return new TextField("No Document Available");
        }
    }
    private void onViewDocumentButtonClick(ItemClickEvent<Case> event) {
        Case caso = event.getItem();
        String pdfViewerLink = "/pdf-viewer?caseId=" + caso.getIdCase();
        UI.getCurrent().getPage().setLocation(pdfViewerLink);
    }
    private Component getToolbar() {
        filterText.setPlaceholder("Filter by title/client/lawyer...");
        filterText.setWidth("240px");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addCaseButton = new Button("Add Case");
        addCaseButton.addClickListener(click -> addCase());

        var toolbar = new HorizontalLayout(filterText, addCaseButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }
    private PdfViewer pdfView(){
        PdfViewer pdfViewer = new PdfViewer();
        File pdfFile = new File("/Users/macbookair/Downloads/landlawyersapp23/src/main/resources/META-INF/resources/pdf/CV_Jawher2023EN.pdf");
        StreamResource resource = new StreamResource("CV_Jawher2023EN.pdf", () -> {
            try {
                return new FileInputStream(pdfFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        });
        pdfViewer.setSrc(resource);
        return pdfViewer;
    }

    public void editCase(Case caso) {
        if (caso == null) {
            closeEditor();
        } else {
            form1.setCase(caso);
            form1.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form1.setCase(null);
        form1.setVisible(false);
        removeClassName("editing");
    }

    private void addCase() {
        grid.asSingleSelect().clear();
        editCase(new Case());
    }


    private void updateList() {
        grid.setItems(caseService.findAllCases(filterText.getValue()));
    }


    private String getImageAsBase64(String base64Image) {
        String mimeType = "image/jpg"; // Change the mimeType as needed
        if (base64Image == null) {
            return TRANSPARENT_GIF_1PX;
        } else {
            return "data:" + mimeType + ";base64," + Base64.getEncoder().encodeToString(base64Image.getBytes());
        }

    }


    private static final String TRANSPARENT_GIF_1PX =
            "data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACwAAAAAAQABAAACAkQBADs=";
}