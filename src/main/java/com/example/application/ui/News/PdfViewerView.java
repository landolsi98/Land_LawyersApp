package com.example.application.ui.News;

import com.example.application.backend.entity.Case;
import com.example.application.backend.service.CaseService;
import com.example.application.ui.MainView;
import com.vaadin.componentfactory.pdfviewer.PdfViewer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;

@Route(value = "pdf-viewer", layout = MainView.class)
@PageTitle("PDF Viewer")
@AnonymousAllowed
public class PdfViewerView extends VerticalLayout implements HasUrlParameter<String> {

    private final CaseService caseService;

    @Autowired
    public PdfViewerView(CaseService caseService) {
        this.caseService = caseService;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String caseId) {

        if (caseId != null && !caseId.isEmpty()) {
            Integer id = Integer.valueOf(caseId);
            Case caso = caseService.findCaseById(id);
System.out.println("id zaab " + id);
            if (caso != null && caso.getDocument() != null) {
                // Create a PDF viewer component and display the PDF
                PdfViewer pdfViewer = new PdfViewer();
                pdfViewer.setHeight("800px");
                pdfViewer.setWidth("100%");
                StreamResource resource = new StreamResource("CV_Jawher2023EN.pdf", () -> {
                    try {
                        return new ByteArrayInputStream(caso.getDocument());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                });
                pdfViewer.setSrc(resource);

                add(pdfViewer);
            } else {
                UI.getCurrent().getPage().setLocation("/news"); // Redirect if case or document is not found
            }
        } else {
            UI.getCurrent().getPage().setLocation("/"); // Redirect if case ID is not provided
        }
    }

}
