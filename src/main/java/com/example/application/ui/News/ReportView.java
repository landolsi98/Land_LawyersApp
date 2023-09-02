package com.example.application.ui.News;

import com.example.application.backend.entity.Cita;
import com.example.application.backend.service.CitaService;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.reports.PrintPreviewReport;

import java.util.Collections;
import java.util.List;
import java.util.Map;


@AnonymousAllowed
@Route(value = "cita_confirmation")
public class ReportView extends VerticalLayout implements HasUrlParameter<String> {

    private String idNewCita;
    private final CitaService citaService;

    public ReportView(CitaService citaService) {
        this.citaService = citaService;
    }

    @Override
    public void setParameter(BeforeEvent event,  @WildcardParameter  String parameter) {

        Location location = event.getLocation();
        QueryParameters queryParameters = location.getQueryParameters();

        Map<String, List<String>> parametersMap = queryParameters
                .getParameters();
        idNewCita = parametersMap.keySet().iterator().next();

        try {
            Long newCitaId = Long.parseLong(idNewCita);
            Cita newCita = citaService.findCitaById(newCitaId);

                var report = new PrintPreviewReport<>(Cita.class, "object", "date", "time");
            System.out.println("*****" + newCita);
            List<Cita> items = Collections.singletonList(newCita);
            report.setItems(items);
            report.getReportBuilder().setTitle("Appointment");
            SerializableSupplier<List<? extends Cita>> listItems = new CitaListSupplier(items);
            StreamResource pdf = report.getStreamResource("books.pdf" , listItems, PrintPreviewReport.Format.PDF);
            add(new HorizontalLayout(new Anchor(pdf, "Download Pdf")), report);
           // add(report);
        } catch (NumberFormatException e) {
            System.out.println("tahcheeee"); // Redirect to a different route if no parameter is provided
        }
    }
}

        /*
        QueryParameters queryParameters = event.getLocation().getQueryParameters();
        String newCitaIdStr = queryParameters.getParameters().get("newCita").get(0);
*/






