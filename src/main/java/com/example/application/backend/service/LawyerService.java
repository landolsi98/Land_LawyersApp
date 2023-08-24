package com.example.application.backend.service;

import com.example.application.backend.entity.Abogado;
import com.example.application.backend.entity.Lawyer;
import com.example.application.backend.repository.AbogadoRepository;
import com.example.application.backend.repository.LawyerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;

@Service
public class LawyerService implements CrudListener<Lawyer> {

    private final LawyerRepository lawyerRepository;

    public LawyerService(LawyerRepository lawyerRepository) {
        this.lawyerRepository = lawyerRepository;
    }

    @Override
    public Collection<Lawyer> findAll() {
        return lawyerRepository.findAll();
    }

    @Transactional
    @Override
    public Lawyer add(Lawyer lawyer) {
        return lawyerRepository.save(lawyer);
    }

    @Override
    public Lawyer update(Lawyer lawyer) {
        return lawyerRepository.save(lawyer);
    }

    @Override
    public void delete(Lawyer lawyer) {
        lawyerRepository.delete(lawyer);

    }
}

