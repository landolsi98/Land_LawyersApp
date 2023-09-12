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
    private Long idCita;


    @Column
    private String object;


    @Column
    private LocalDate date;

    @Column
    private LocalTime time;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "id_user", nullable = false)
    private User client;


    /*
        @NotNull
        @ManyToOne
        @JoinColumn(name = "id_abogado")

        private Abogado abogado;
    */
    public Long getIdCita() {
        return idCita;
    }

    public void IdCita(Long idCita) {
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

    public User getUser() {
        return client;
    }

    public void setUser(User user) {
        this.client = user;
    }

    /*
        public Abogado getAbogado() {
            return abogado;
        }

        public void setAbogado(Abogado abogado) {
            this.abogado = abogado;
        }

    */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cita cita = (Cita) o;
        return Objects.equals(idCita, cita.idCita) && Objects.equals(object, cita.object) && Objects.equals(date, cita.date) && Objects.equals(time, cita.time) && Objects.equals(client, cita.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCita, object, date, time, client);
    }
}