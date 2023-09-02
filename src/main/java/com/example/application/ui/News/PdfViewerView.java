package com.example.application.ui.News;

import com.example.application.backend.entity.Case;
import com.example.application.backend.service.CaseService;
import com.example.application.ui.MainView;
import com.vaadin.componentfactory.pdfviewer.PdfViewer;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

@Route(value = "pdf-viewer", layout = MainView.class)
@PageTitle("PDF Viewer")
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
       /* if (id != null && !id.isEmpty()) {
            try {
                Integer idA = Integer.valueOf(id);
                Case caso = caseService.findCaseById(idA);

                // Verify that the caseId was passed correctly
                TextField title = new TextField("Case ID");

                if (caso != null) {
                    //  title of the case
                    title.setValue(caso.getTitle());

                    // Add the TextField to your UI
                    add(title);
                } else {
                    // Case not found
                    Notification.show("Case not found", 3000, Notification.Position.MIDDLE);
                }
            } catch (NumberFormatException e) {
                //  invalid case ID format
                Notification.show("Invalid Case ID format", 3000, Notification.Position.MIDDLE);
            }
        } else {
            // Handle the case where no case ID is provided, e.g., show an error message
            Notification.show("Case ID is missing", 3000, Notification.Position.MIDDLE);
        }
    }
*/


        if (id != null && !id.isEmpty()) {
            Integer idA = Integer.valueOf(id);
            Case caso = caseService.findCaseById(idA);
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
            Notification.show("Case ID is missing or invalid", 3000, Notification.Position.MIDDLE);
        }

    }
    @Autowired
    public PdfViewerView(CaseService caseService) {
        this.caseService = caseService;
    }
}

