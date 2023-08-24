package com.example.application.ui;

import com.example.application.backend.entity.Abogado;
import com.example.application.backend.entity.Noticia;
import com.example.application.backend.service.AbogadoService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@PageTitle("Dashboard Abogados")
@Route(value = "dashAbogados", layout = MainView.class )
@AnonymousAllowed
public class DashAbogados extends VerticalLayout {
    public DashAbogados(AbogadoService service) {


        var crud = new GridCrud<>(Abogado.class, service);
        crud.getGrid().setColumns("idAbogado", "firstName", "lastName", "email", "specialty", "phoneNumber", "barNumber", "position","image");
        crud.getCrudFormFactory().setVisibleProperties("firstName", "lastName", "email", "specialty", "phoneNumber", "barNumber", "position","image");


        add(
                new H1("Dashboard Lawyers"),
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
            service.add(new Abogado());

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

