package com.example.application.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

import java.util.Objects;

@Data
@Entity
@Table(name = "Citas", schema = "firstDb")
public class  Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCita;


    @Column
    private String object;


    @Column
    private LocalDate date;

    @Column
    private LocalTime time;
/*
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_abogado")

    private Avocat abogado;
*/
    public Integer getIdCita() {
        return idCita;
    }

    public void IdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
/*
    public Avocat getAbogado() {
        return abogado;
    }

    public void setAbogado(Avocat abogado) {
        this.abogado = abogado;
    }
*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cita cita = (Cita) o;
        return Objects.equals(idCita, cita.idCita) && Objects.equals(object, cita.object) && Objects.equals(date, cita.date) && Objects.equals(time, cita.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCita, object, date, time);
    }
}