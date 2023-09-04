package com.example.application.UI.Features;



import com.example.application.UI.Principals.MainView;
import com.example.application.backend.entity.User;
import com.example.application.backend.service.ServiceService;
import com.example.application.backend.service.UserService;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.charts.model.style.SolidColor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.spreadsheet.Spreadsheet;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.List;


@AnonymousAllowed
@PageTitle("Workspace | Land Lawyers")
@Route(value = "spreadsheet-basic" , layout = MainView.class)
public class WorkSpace extends Div {
private final ServiceService service;
private final UserService userService;
    public WorkSpace(ServiceService service, UserService userService) {
        this.service = service;
        this.userService = userService;
        // tag::snippet[]
        Spreadsheet spreadsheet = new Spreadsheet();
        spreadsheet.setHeight("400px");
        add(spreadsheet);
        // end::snippet[]

        // Spline Chart
        Chart chart = new Chart(ChartType.SPLINE);

        Configuration configuration = chart.getConfiguration();
        configuration.getTitle().setText("Land Lawyer Financial Performance and Key Dates");
        configuration.getxAxis().setType(AxisType.DATETIME);

        DataSeries dataSeries = new DataSeries();
        dataSeries.setId("dataseries");
        dataSeries.addData(new Number[][]{
                {1434499200000L, 0.8821},
                {1434585600000L, 0.8802},
                {1434672000000L, 0.8808},
                {1434844800000L, 0.8794},
                {1434931200000L, 0.8818},
                {1435017600000L, 0.8952},
                {1435104000000L, 0.8924},
                {1435190400000L, 0.8925},
                {1435276800000L, 0.8955}
        });

        DataSeries flagsOnSeries = new DataSeries();
        flagsOnSeries.setPlotOptions(new PlotOptionsFlags());
        flagsOnSeries.setName("Important Events");
        flagsOnSeries.add(new FlagItem(1434585600000L, "Client Meeting", "Client Meeting Tooltip Text"));
        flagsOnSeries.add(new FlagItem(1435017600000L, "Legal Conference"));

        DataSeries flagsOnAxis = new DataSeries();
        flagsOnAxis.setPlotOptions(new PlotOptionsFlags());
        flagsOnAxis.setName("Key Dates");
        flagsOnAxis.add(new FlagItem(1434844800000L, "Court Hearing", "Court Hearing Tooltip Text"));
        flagsOnAxis.add(new FlagItem(1435190400000L, "Filing Deadline"));

        configuration.setSeries(dataSeries, flagsOnSeries, flagsOnAxis);

        // Pie Chart (3D)
        Chart chart3 = new Chart(ChartType.PIE);
        Configuration conf3 = chart3.getConfiguration();
conf3.getTitle().setText("Users Status Overview Charts");
        PlotOptionsPie options = new PlotOptionsPie();
        options.setInnerSize("0");
        options.setSize("95%");
        options.setCenter("50%", "50%");
        Options3d options3d = new Options3d();
        options3d.setEnabled(true);
        options3d.setAlpha(7);
        options3d.setBeta(30);
        options3d.setDepth(135); // Default is 100
        options3d.setViewDistance(100); // Default
        conf3.getChart().setOptions3d(options3d);
        conf3.setPlotOptions(options);
        Frame frame = new Frame();
        Back back=new Back();
        back.setColor(SolidColor.BEIGE);
        back.setSize(1);
        frame.setBack(back);
        options3d.setFrame(frame);
        DataSeries series = new DataSeries();

// Fetch all users with role "USER" and "CLIENT"
        List<User> usersWithRoleUser = userService.findUserByIdRol(3);
        List<User> usersWithRoleClient = userService.findUserByIdRol(4);

        int totalUsers = usersWithRoleUser.size();
        int totalClients = usersWithRoleClient.size();
System.out.print("****"+totalClients);
        int total = totalUsers + totalClients;

        double percentageUsers = ((double) totalUsers / total) * 100;
        double percentageClients = ((double) totalClients / total) * 100;

        DataSeriesItem userSlice = new DataSeriesItem("Non-Client Users ", percentageUsers);
        DataSeriesItem clientSlice = new DataSeriesItem("Client Users", percentageClients);

        series.add(userSlice);
        series.add(clientSlice);
        conf3.addSeries(series);


            VerticalLayout layout = new VerticalLayout();
            layout.add(chart, chart3);
            add(layout);
        }
    }




