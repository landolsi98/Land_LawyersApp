package com.example.application.backend.service;

import com.example.application.backend.entity.Authority;
import com.example.application.backend.entity.Lawyer;
import com.example.application.backend.entity.User;
import com.example.application.backend.repository.AuthorityRepository;
import com.example.application.backend.repository.LawyerRepository;
import com.example.application.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;
import java.util.List;

@Service
public class UserService implements CrudListener<User> {
    private final UserRepository userRepository;
private  final AuthorityRepository authorityRepository;
private final LawyerRepository lawyerRepository;
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, LawyerRepository lawyerRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.lawyerRepository = lawyerRepository;
    }

    public Collection<User> findAll() {
        return userRepository.findAll();
    }
    public User findUserById(Long idUser) {
        return userRepository.findById(idUser).orElse(null);
    }
    public Collection <Lawyer> findLawyers(){
        return lawyerRepository.findAll();
    }

    public List<User> findUserByIdRol(Integer idRol){
        return userRepository.findUserByIdRol(idRol);
    }
    @Transactional
    public User add(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void delete(User user) {
userRepository.delete(user);
    }

    public List<Authority> findAllRoles(){
        return authorityRepository.findAll();
    }
}
