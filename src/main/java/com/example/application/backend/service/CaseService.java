package com.example.application.backend.service;


import com.example.application.backend.entity.*;
import com.example.application.backend.repository.*;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;
import java.util.List;

@org.springframework.stereotype.Service
public class CaseService implements CrudListener<Case> {

private final CaseRepository caseRepository;
private final AvocatRepository avocatRepository;
private final ServiceRepository serviceRepository;
private final UserRepository userRepository;

    public CaseService(CaseRepository caseRepository , AvocatRepository avocatRepository, ServiceRepository serviceRepository, UserRepository userRepository) {
        this.caseRepository = caseRepository;
        this.avocatRepository = avocatRepository;
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
    }

    public Collection<Case> findAllCases(String caseFilter) {
        if(caseFilter == null || caseFilter.isEmpty()){
            return caseRepository.findAll();
        }else {
            return caseRepository.search(caseFilter);
        }
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
    public Case findCaseById(Integer idCase) {
        return caseRepository.findCaseByIdCase(idCase);
    }
    public Case findCaseByUser(User client) {
        return caseRepository.findCaseByClient(client);
    }


    public List<Abogado> findAllAbogados() {
        return avocatRepository.findAll();
    }
    public List<Service> findAllServices(){
        return serviceRepository.findAll();
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

}
