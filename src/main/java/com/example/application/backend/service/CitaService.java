package com.example.application.backend.service;

import com.example.application.backend.entity.Abogado;
import com.example.application.backend.entity.Avocat;
import com.example.application.backend.entity.Cita;
import com.example.application.backend.repository.AbogadoRepository;
import com.example.application.backend.repository.AvocatRepository;
import com.example.application.backend.repository.CitaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;
import java.util.List;

@Service
public class CitaService implements CrudListener<Cita> {

    private final AvocatRepository abogadoRepository;
private final CitaRepository citaRepository;
    public CitaService(AvocatRepository repository, CitaRepository citaRepository) {
        this.abogadoRepository = repository;
        this.citaRepository = citaRepository;
    }



    @Override
    public Collection<Cita> findAll() {
        return citaRepository.findAll();
    }

    @Override
    public Cita add(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    public Cita update(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    public void delete(Cita cita) {
        citaRepository.delete(cita);
    }
/*
    public List<Avocat> findAllAbogados() {
        return abogadoRepository.findAll();
    }
    */

}
