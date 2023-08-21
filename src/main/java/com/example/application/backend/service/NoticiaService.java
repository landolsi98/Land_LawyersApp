package com.example.application.backend.service;

import com.example.application.backend.entity.Noticia;
import com.example.application.backend.repository.NoticiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoticiaService implements CrudListener<Noticia> {

    @Value("${path.to.directory.uploads}")
    private String pathToDirectoryUploads;
 @Autowired
private final NoticiaRepository repository;


    @Override
    public Collection<Noticia> findAll() {
        return repository.findAll();
    }

    @Override
    public Noticia add(Noticia noticia) {
/*
        if (noticia.getImage() != null) {
            byte[] imageData = Base64.getDecoder().decode(noticia.getImage()); // Decode base64 image data
            String fileName = UUID.randomUUID().toString() + ".jpg";
            String filePath = pathToDirectoryUploads + "/" + fileName;
            saveImageToFile(imageData, filePath); // Save the binary image data
            noticia.setImage(filePath);
        }
        */

        return repository.save(noticia);
    }

    /*private void saveImageToFile(byte[] imageData, String filePath) {
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            outputStream.write(imageData);
        } catch (IOException e) {
            // Handle exceptions appropriately
            e.printStackTrace();
        }
    }
*/
    public Noticia addNoticiaWithImage(String title, byte[] imageData, String cuerpo,Noticia noticia) {
        noticia.setDate(new Date());

        noticia.setTitle(title);
        noticia.setImage(imageData);
        noticia.setCuerpo(cuerpo);
        // Set other properties as needed
        return repository.save(noticia);
    }
    @Override
    public Noticia update(Noticia noticia) {
        return repository.save(noticia);
    }

    @Override
    public void delete(Noticia noticia) {
        repository.delete(noticia);

    }
}
