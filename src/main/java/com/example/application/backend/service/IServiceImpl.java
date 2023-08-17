package com.example.application.backend.service;

import com.example.application.backend.entity.Service;

import java.util.List;

public interface IServiceImpl {
    List<Service> findAll();

    Service add (Service service);
    Service update(Service service);

    void delete(Service service);

}
