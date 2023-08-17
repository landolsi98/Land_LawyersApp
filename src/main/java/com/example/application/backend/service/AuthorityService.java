package com.example.application.backend.service;

import com.example.application.backend.entity.Authority;
import com.example.application.backend.repository.AuthorityRepository;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;

@Service
public class AuthorityService implements CrudListener<Authority> {
    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Collection<Authority> findAll() {
        return authorityRepository.findAll() ;
    }

    @Override
    public Authority add(Authority authority) {
        return authorityRepository.save(authority);
    }

    @Override
    public Authority update(Authority authority) {
        return authorityRepository.save(authority);
    }

    @Override
    public void delete(Authority authority) {
        authorityRepository.delete(authority);
    }
}
