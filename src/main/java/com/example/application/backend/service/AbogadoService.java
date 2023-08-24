package com.example.application.backend.service;



import com.example.application.backend.entity.Abogado;
import com.example.application.backend.repository.AbogadoRepository;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;

@Service
public class AbogadoService implements CrudListener<Abogado> {

    private final AbogadoRepository abogadoRepository;

    public AbogadoService(AbogadoRepository repository) {
        this.abogadoRepository = repository;
    }

    @Override
    public Collection<Abogado> findAll() {
        return abogadoRepository.findAll();
    }

    @Override
    public Abogado add(Abogado abogado) {
        return abogadoRepository.save(abogado);
    }


    @Override
    public Abogado update(Abogado abogado) {
        return abogadoRepository.save(abogado);
    }

    @Override
    public void delete(Abogado abogado) {
        abogadoRepository.delete(abogado);

    }
}

