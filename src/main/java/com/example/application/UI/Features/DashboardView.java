package com.example.application.UI.Features;


import com.example.application.UI.Principals.MainView;
import com.example.application.backend.service.CaseService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
/*
@AnonymousAllowed
@Route(value = "dashboard", layout = MainView.class)
@PageTitle("Dashboard | Vaadin ")
public class DashboardView extends VerticalLayout {
    private final CaseService caseService;

    public DashboardView(CaseService caseService) {
        this.caseService = caseService;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(getContactStats(), getCompaniesChart());
    }

    private Component getContactStats() {
        Span stats = new Span(caseService.countUsers() + " users");
        stats.addClassNames(
                LumoUtility.FontSize.XLARGE,
                LumoUtility.Margin.Top.MEDIUM);
        return stats;
    }

    private Chart getCompaniesChart() {
        Chart chart = new Chart(ChartType.PIE);
        DataSeries dataSeries = new DataSeries();
        caseService.findAll().forEach(company ->
                dataSeries.add(new DataSeriesItem(company.getTitle(), company.getUsersCount())));
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }

 */
