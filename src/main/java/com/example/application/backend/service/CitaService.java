package com.example.application.backend.service;

import com.example.application.backend.entity.Cita;
import com.example.application.backend.entity.User;
import com.example.application.backend.repository.CitaRepository;
import com.example.application.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CitaService implements CrudListener<Cita> {

    @Autowired
    private UserRepository userRepository;
    @Autowired

    private final CitaRepository citaRepository;
    public CitaService(UserRepository userRepository, CitaRepository citaRepository) {
        this.userRepository = userRepository ;
        this.citaRepository = citaRepository;
    }


    @Override
    public List<Cita> findAll() {
        return citaRepository.findAll();
    }

    @Override
    public Cita add(Cita cita) {
        return citaRepository.save(cita);
    }
    public Cita findCitaByDateAndTime(LocalDate date, LocalTime time) {
        // Assuming you have an entity manager or repository for Cita entities
        // Replace "CitaRepository" with the actual class representing your repository
        return citaRepository.findByDateAndTime(date, time);
    }

    public Cita findCitaById(Long idCita) {
        return citaRepository.findCitaByIdCita(idCita);
    }

    public List<Cita> findCitasByDate(LocalDate date){
        return citaRepository.findAllCitasByDate(date);
    }
    public List<Cita> findCitasByTime(LocalTime time){
        return citaRepository.findAllCitasByTime(time);
    }
    public Cita findCitaByDate(LocalDate date) {
        return citaRepository.findByDate(date);
    }

    public Cita findCitaByTime(LocalTime time){
        return citaRepository.findByTime(time);
    }

    @Override
    public Cita update(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    public void delete(Cita cita) {
        citaRepository.delete(cita);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }



}
