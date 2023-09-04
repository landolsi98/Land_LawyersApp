package com.example.application.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDate;


import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.Formula;

import java.util.Objects;


@Data
@Entity
@Table(name = "`Case`", schema = "firstDb")
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private Integer idCase;

    @Column
    private String title;


    @Column
    private String description;

    @Column
    private String state;


    @Column
    private LocalDate creation_date;

    @Column(name = "Document")
    private byte[] document;



    @Column(name = "Comment")
    private String comment;



/*
    @ManyToOne
    @JoinColumn(name = "id_abogado", nullable = false)

    private Abogado abogado;
*/



    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_service", nullable = false)
    private Service service;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User client;

    @ManyToOne
    private Avocat avocat;
/*
    @Formula("(select count(*) from `Case` where id_user = id_case)")
    private int usersCount;

    public int getUsersCount(){
        return usersCount;
    }
    public Integer getIdCase() {
        return idCase;
    }
    */


    public void setIdCase(Integer idCase) {
        this.idCase = idCase;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDate getCreationDate() {
        return creation_date;
    }

    public void setCreationDate(LocalDate creation_date) {
        this.creation_date = creation_date;
    }
/*
    public Abogado getAbogado() {
        return abogado;
    }

    public void setAbogado(Abogado abogado) {
        this.abogado = abogado;
    }
*/

    public User getUser() {
        return client;
    }

    public void setUser(User client) {
        this.client = client;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Avocat getAvocat() {
        return avocat;
    }

    public void setAvocat(Avocat avocat) {
        this.avocat = avocat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Case aCase = (Case) o;
        return Objects.equals(idCase, aCase.idCase) && Objects.equals(title, aCase.title) && Objects.equals(description, aCase.description) && Objects.equals(state, aCase.state) && Objects.equals(creation_date, aCase.creation_date)  && Objects.equals(service, aCase.service);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCase, title, description, state, creation_date,  service,client);
    }
}