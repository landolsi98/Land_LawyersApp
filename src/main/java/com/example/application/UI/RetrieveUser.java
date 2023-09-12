package com.example.application.UI;

import com.example.application.backend.entity.Abogado;
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

        List<Abogado> usersWithRole2 = (List<Abogado>) avocatService.findAll();

        if (!usersWithRole2.isEmpty()) {
            for (Abogado abogado : usersWithRole2) {
              //  Integer userIdValue = abogado.getIdUser();
               // String username = abogado.getUsername();
               // String speciality = user.getLawyer().getSpeciality();
                //Integer rol = user.getAuthority().getIdRol();
                String name = abogado.getUsername();
                String position = abogado.getPosition();
                Text userLabel = new Text("User ID: " +   ", Username: " +  name + "position :"  + position+ "Name : " );
                add(userLabel);
            }
        } else {
            Text notFoundLabel = new Text("No users with Role ID 2 found");
            add(notFoundLabel);
        }
    }
}
