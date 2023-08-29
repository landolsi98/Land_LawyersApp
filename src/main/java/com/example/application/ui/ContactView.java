package com.example.application.ui;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.map.Map;
import com.vaadin.flow.component.map.configuration.style.Icon;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.map.configuration.Coordinate;
import com.vaadin.flow.component.map.configuration.feature.MarkerFeature;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("contact")
@Route(value = "Contact-Us" , layout = MainView.class)
@AnonymousAllowed
public class ContactView extends Div {

    public ContactView() {
        Map map = new Map();
        add(map);

        // Add draggable marker
        MarkerFeature marker = new MarkerFeature();
        marker.setId("draggable-marker");
        marker.setDraggable(true);
        marker.setText("Land lawyer Office");
        map.getFeatureLayer().addFeature(marker);

        // Listen to marker drop event
        map.addFeatureDropListener(event -> {
            MarkerFeature droppedMarker = (MarkerFeature) event.getFeature();
            Coordinate startCoordinates = event.getStartCoordinate();
            Coordinate endCoordinates = event.getCoordinate();

            Notification.show("Marker \"" + droppedMarker.getId() + "\" dragged from " + startCoordinates + " to " + endCoordinates);
        });
    }

}