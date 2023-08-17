package com.example.application.backend.service;


import com.example.application.backend.entity.Abogado;
import com.example.application.backend.entity.Case;
import com.example.application.backend.entity.Service;
import com.example.application.backend.entity.User;
import com.example.application.backend.repository.AbogadoRepository;
import com.example.application.backend.repository.CaseRepository;
import com.example.application.backend.repository.ServiceRepository;
import com.example.application.backend.repository.UserRepository;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;
import java.util.List;

@org.springframework.stereotype.Service
public class CaseService implements CrudListener<Case> {

private final CaseRepository caseRepository;
private final AbogadoRepository abogadoRepository;
private final ServiceRepository serviceRepository;
private final UserRepository userRepository;

    public CaseService(CaseRepository caseRepository, AbogadoRepository abogadoRepository, ServiceRepository serviceRepository, UserRepository userRepository) {
        this.caseRepository = caseRepository;
        this.abogadoRepository = abogadoRepository;
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Collection<Case> findAll() {
        return caseRepository.findAll();
    }

    @Override
    public Case add(Case caso) {
        return caseRepository.save(caso);
    }

    @Override
    public Case update(Case caso) {
        return caseRepository.save(caso);
    }

    @Override
    public void delete(Case caso) {
        caseRepository.delete(caso);

    }

    public List<Abogado> findAllAbogados() {
        return abogadoRepository.findAll();
    }
    public List<Service> findAllServices(){
        return serviceRepository.findAll();
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

}
