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
        noticia.setDate(new Date());

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
