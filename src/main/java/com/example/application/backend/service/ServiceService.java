package com.example.application.backend.service;

import com.example.application.backend.entity.Service;
import com.example.application.backend.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService implements CrudListener<Service> {

    @Autowired
    private final ServiceRepository serviceRepository;
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    public Service add(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public Service update(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public void delete(Service service) {
        serviceRepository.delete(service);

    }
}
