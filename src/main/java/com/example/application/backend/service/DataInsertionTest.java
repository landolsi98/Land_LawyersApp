package com.example.application.backend.service;

import com.example.application.backend.entity.Lawyer;
import com.example.application.backend.entity.User;
import com.example.application.backend.repository.LawyerRepository;
import com.example.application.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DataInsertionTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LawyerRepository lawyerRepository;

    @Transactional
    public void testInsertData() {

        Lawyer lawyer = new Lawyer();
        lawyer.setFirstName("Jane");
        lawyer.setLastName("Smith");
        lawyer.setPassword("xxxx");
        lawyer.setUsername("zab");
        lawyer.setEnable(true);
        lawyer.setDescription("description");
        lawyer.setPosition("ceo");
        lawyer.setBarNumber("589Y");
        lawyer.setEmail("jane@example.com");
        lawyer.setSpeciality("Criminal Law");
        System.out.println("ahawha" + lawyer);
        lawyerRepository.save(lawyer);
    }
}