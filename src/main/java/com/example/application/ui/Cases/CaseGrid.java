package com.example.application.ui.Cases;

import com.example.application.backend.entity.Case;
import com.example.application.backend.service.CaseService;
import com.example.application.ui.MainView;
import com.vaadin.componentfactory.pdfviewer.PdfViewer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemClickEvent;
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
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "Case-grid", layout = MainView.class)
@PageTitle("Cases | Vaadin CRM")
public class CaseGrid extends VerticalLayout {


    Grid<Case> grid = new Grid<>(Case.class);
    TextField filterText = new TextField();
    CaseForm form;
    CaseService service;

    public CaseGrid(CaseService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
        pdfView();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new CaseForm(service.findAllServices(), service.findAllUsers(),service.findAllAbogados());
        form.setWidth("25em");
        form.addSaveListener(this::saveCase); // <1>
        form.addDeleteListener(this::deleteCase); // <2>
        form.addCloseListener(e -> closeEditor()); // <3>
    }

        private void saveCase(CaseForm.SaveEvent event) {
        service.add(event.getCase());
        updateList();
        closeEditor();
    }

    private void deleteCase(CaseForm.DeleteEvent event) {
        service.delete(event.getCase());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("title", "description", "state","creation_date");
        grid.addColumn(service -> service.getService().getService()).setHeader("Service");
        grid.addColumn(user -> user.getUser().getFirstName()).setHeader("User");
        grid.addColumn(avocat -> avocat.getAvocat().getFirstName()).setHeader("Lawyer");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.addComponentColumn(this::createDocumentLinkRenderer)
                .setHeader("Document")
                .setAutoWidth(true);
        grid.asSingleSelect().addValueChangeListener(event ->
                editCase(event.getValue()));
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
        filterText.setPlaceholder("Filter by name...");
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
            form.setCase(caso);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setCase(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addCase() {
        grid.asSingleSelect().clear();
        editCase(new Case());
    }


    private void updateList() {
        grid.setItems(service.findAll());
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