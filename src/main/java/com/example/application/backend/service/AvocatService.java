package com.example.application.backend.service;

import com.example.application.backend.entity.Abogado;
import com.example.application.backend.repository.AvocatRepository;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;

@Service
public class AvocatService implements CrudListener<Abogado> {

    private final AvocatRepository avocatRepository;

    public AvocatService(AvocatRepository avocatRepository) {
        this.avocatRepository = avocatRepository;
    }

    @Override
    public Collection<Abogado> findAll() {
        return avocatRepository.findAll();
    }

    @Override
    public Abogado add(Abogado abogado) {
        return avocatRepository.save(abogado);
    }

    @Override
    public Abogado update(Abogado abogado) {
        return avocatRepository.save(abogado);
    }

    @Override
    public void delete(Abogado abogado) {
        avocatRepository.delete(abogado);
    }
    public Collection<Abogado> findAllLawyers(String filter) {
        if(filter == null || filter.isEmpty()){
            return avocatRepository.findAll();
        }else {
            return avocatRepository.search(filter);
        }
    }

}

