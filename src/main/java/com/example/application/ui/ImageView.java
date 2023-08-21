package com.example.application.ui;

import com.example.application.backend.entity.Noticia;
import com.example.application.backend.service.NoticiaService;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
@AnonymousAllowed
@Route("image-view")
public class ImageView extends Div {

    private NoticiaService noticiaService;

    public ImageView(NoticiaService noticiaService) {
        this.noticiaService = noticiaService;
        displayImages();
    }

    private void displayImages() {
        Collection<Noticia> news = noticiaService.findAll();

        for (Noticia noticia : news) {
            if (noticia.getImage() != null && noticia.getImage().length > 0) {
                Image image = new Image(getImageResource(noticia), "Image not found");
                image.setHeight("300px"); // Adjust the size as needed
                add(image);
            }
        }
    }

    private StreamResource getImageResource(Noticia noticia) {
        return new StreamResource(noticia.getTitle() + ".jpg", () -> {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(noticia.getImage());
            return inputStream;
        });
    }
}

