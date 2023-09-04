package com.example.application.UI.Features;

import com.example.application.backend.entity.Case;
import com.example.application.backend.service.CaseService;
import com.example.application.UI.Principals.MainView;
import com.vaadin.componentfactory.pdfviewer.PdfViewer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

@Route(value = "pdf-viewer", layout = MainView.class)
@PageTitle("PDF Viewer | Land Lawyers")
@AnonymousAllowed
public class PdfViewerView extends VerticalLayout implements HasUrlParameter<String> {
    private String id = "0";

    private final CaseService caseService;

    @Override
    public void setParameter(BeforeEvent event, @WildcardParameter String parameter) {

        id = "0";
        Location location = event.getLocation();
        QueryParameters queryParameters = location.getQueryParameters();

        Map<String, List<String>> parametersMap = queryParameters
                .getParameters();


        id = parametersMap.keySet().iterator().next();
        System.out.println("!!!!!!" + id);



        if (id != null && !id.isEmpty()) {
            Integer idA = Integer.valueOf(id);
            Case caso = caseService.findCaseById(idA);
            System.out.println("id zaab " + id);
            if (caso != null && caso.getDocument() != null) {
                //  PDF viewer component and display the PDF
                PdfViewer pdfViewer = new PdfViewer();
                pdfViewer.setHeight("800px");
                pdfViewer.setWidth("100%");
                StreamResource resource = new StreamResource("Land Lawyers Documents", () -> {
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
                UI.getCurrent().getPage().setLocation("/inicio"); // Redirect if case or document is not found
            }
        } else {
            Notification.show("Case ID is missing or invalid", 3000, Notification.Position.MIDDLE);
        }

    }
    @Autowired
    public PdfViewerView(CaseService caseService) {
        this.caseService = caseService;
    }
}

