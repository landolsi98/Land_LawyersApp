package com.example.application.backend.service;

import com.example.application.backend.entity.Authority;
import com.example.application.backend.entity.User;
import com.example.application.backend.repository.AuthorityRepository;
import com.example.application.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;
import java.util.List;

@Service
public class UserService implements CrudListener<User> {
    private final UserRepository userRepository;
private  final AuthorityRepository authorityRepository;
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
userRepository.delete(user);
    }

    public List<Authority> findAllRoles(){
        return authorityRepository.findAll();
    }
}
