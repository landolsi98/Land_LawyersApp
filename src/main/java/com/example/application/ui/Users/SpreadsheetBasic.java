package com.example.application.ui.Users;



import com.example.application.ui.MainView;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.spreadsheet.Spreadsheet;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@AnonymousAllowed
@PageTitle("Working space")
@Route(value = "spreadsheet-basic" , layout = MainView.class)
public class SpreadsheetBasic extends Div {

    public SpreadsheetBasic() {
        // tag::snippet[]
        Spreadsheet spreadsheet = new Spreadsheet();
        spreadsheet.setHeight("400px");
        add(spreadsheet);
        // end::snippet[]
    }

}


