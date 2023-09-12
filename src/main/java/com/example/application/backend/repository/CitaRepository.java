package com.example.application.backend.repository;

import com.example.application.backend.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CitaRepository extends JpaRepository<Cita,Long> {

    List<Cita> findCitasByIdCita(Long idCita);
    Cita findByDateAndTime(LocalDate date, LocalTime time);

    Cita findCitaByIdCita(Long idCita);
    Cita findByDate(LocalDate date);
    Cita findByTime(LocalTime time);

    @Query("SELECT c FROM Cita c " +
            "where lower(c.client.firstName) like lower(concat('%', :searchTerm,'%')) " +
            " or lower(c.object) like lower(concat('%', :searchTerm,'%'))"
    )
    List<Cita> search(@Param("searchTerm") String filter);
}
