package com.example.application.ui.News;

import com.example.application.backend.entity.Cita;
import com.example.application.backend.service.CitaService;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.reports.PrintPreviewReport;

import java.util.Collections;
import java.util.List;


@AnonymousAllowed
@Route(value = "cita_confirmation")
public class ReportView extends VerticalLayout implements BeforeEnterObserver {

    private final CitaService citaService;

    public ReportView(CitaService citaService) {
        this.citaService = citaService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        QueryParameters queryParameters = event.getLocation().getQueryParameters();
        String newCitaIdStr = queryParameters.getParameters().get("newCita").get(0); // Get the first value

        try {
            Long newCitaId = Long.parseLong(newCitaIdStr);
            Cita newCita = citaService.findCitaById(newCitaId); // Replace with your actual method

            var report = new PrintPreviewReport<>(Cita.class, "object", "date", "time");

            List<Cita> items = newCita != null ? Collections.singletonList(newCita) : Collections.emptyList();
            report.setItems(items);

            report.getReportBuilder().setTitle("Appointment");
            StreamResource pdf = report.getStreamResource("books.pdf", citaService::findAll, PrintPreviewReport.Format.PDF);
            add(new HorizontalLayout(new Anchor(pdf, "Download Pdf")), report);
        } catch (NumberFormatException e) {
            System.out.println("tahcheeee"); // Redirect to a different route if no parameter is provided
        }
    }
}







