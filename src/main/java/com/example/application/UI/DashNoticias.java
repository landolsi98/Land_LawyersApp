package com.example.application.UI;

import com.example.application.UI.Principals.MainView;
import com.example.application.backend.entity.Noticia;
import com.example.application.backend.service.NoticiaService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


@AnonymousAllowed
@Route(value = "dashN", layout = MainView.class)
@PageTitle("Land-Lawyers")
//@RolesAllowed({"ADMIN" , "LAWYER"})
public class DashNoticias extends VerticalLayout {

    private NoticiaService noticiaService;

    public DashNoticias(@Autowired NoticiaService noticiaService) {
        this.noticiaService = noticiaService;

        var crud = new GridCrud<>(Noticia.class, noticiaService);
        crud.getGrid().setColumns("idNoticia", "title", "cuerpo", "date", "imagen");
        crud.getCrudFormFactory().setVisibleProperties("title", "cuerpo", "imagen");
        add(
                new H1("Dashboard News"),
                crud
        );


        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);

        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);

            // Convert the InputStream to a byte array
            byte[] imageData = readInputStream(inputStream);

            // Create a new Noticia instance and set the properties
            Noticia noticia = new Noticia();
            noticia.setTitle("News Title");
            noticia.setCuerpo("News Body");
            noticia.setImage(imageData); // Set the image data

            // Save the Noticia instance using your NoticiaService
            noticiaService.add(noticia);

            // Refresh the CRUD grid to reflect the newly added entity
            crud.refreshGrid();
        });


        add(upload);
    }

    private byte[] readInputStream(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }
}