package com.example.application.backend.service;

import com.example.application.backend.entity.Noticia;
import com.example.application.backend.repository.NoticiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class NoticiaService implements CrudListener<Noticia> {

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
