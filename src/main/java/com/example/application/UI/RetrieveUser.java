package com.example.application.UI;

import com.example.application.backend.entity.Avocat;
import com.example.application.backend.service.AvocatService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.List;

@AnonymousAllowed
@Route("retrieve")
public class RetrieveUser extends VerticalLayout {

    private  AvocatService avocatService;

    public RetrieveUser(AvocatService avocatService) {
        this.avocatService = avocatService;

        List<Avocat> usersWithRole2 = (List<Avocat>) avocatService.findAll();

        if (!usersWithRole2.isEmpty()) {
            for (Avocat avocat : usersWithRole2) {
              //  Integer userIdValue = avocat.getIdUser();
               // String username = avocat.getUsername();
               // String speciality = user.getLawyer().getSpeciality();
                //Integer rol = user.getAuthority().getIdRol();
                String name = avocat.getUsername();
                String position = avocat.getPosition();
                Text userLabel = new Text("User ID: " +   ", Username: " +  name + "position :"  + position+ "Name : " );
                add(userLabel);
            }
        } else {
            Text notFoundLabel = new Text("No users with Role ID 2 found");
            add(notFoundLabel);
        }
    }
}
