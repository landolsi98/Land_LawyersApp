package com.example.application.backend.service;

import com.example.application.backend.entity.Avocat;
import com.example.application.backend.entity.Lawyer;
import com.example.application.backend.repository.AvocatRepository;
import com.example.application.backend.repository.LawyerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;
import java.util.List;

@Service
public class AvocatService implements CrudListener<Avocat> {

    private final AvocatRepository avocatRepository;

    public AvocatService(AvocatRepository avocatRepository) {
        this.avocatRepository = avocatRepository;
    }

    @Override
    public Collection<Avocat> findAll() {
        return avocatRepository.findAll();
    }

    @Override
    public Avocat add(Avocat avocat) {
        return avocatRepository.save(avocat);
    }

    @Override
    public Avocat update(Avocat avocat) {
        return avocatRepository.save(avocat);
    }

    @Override
    public void delete(Avocat avocat) {
avocatRepository.delete(avocat);
    }
}

