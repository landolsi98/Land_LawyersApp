package com.example.application.backend.repository;

import com.example.application.backend.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CitaRepository extends JpaRepository<Cita,Long> {

    List<Cita> findAllCitasByDate(LocalDate date);

    List<Cita> findAllCitasByTime(LocalTime time);

    Cita findByDateAndTime(LocalDate date, LocalTime time);

    Cita findCitaByIdCita(Long idCita);
    Cita findByDate(LocalDate date);
    Cita findByTime(LocalTime time);
}
