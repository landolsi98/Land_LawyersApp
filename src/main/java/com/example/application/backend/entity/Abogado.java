package com.example.application.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Abogado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAbogado;

    @Column
    private String firstName;
    @Column
    private String lastName;

    @Column
    @Email
    private String email;
    @Column

    private  String specialty;
    @Column

    private String phoneNumber;
    @Column

    private String barNumber;
    @Column

    private String position;



    public Integer getIdAbogado() {
        return idAbogado;
    }

    public void setIdAbogado(Integer idAbogado) {
        this.idAbogado = idAbogado;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBarNumber() {
        return barNumber;
    }

    public void setBarNumber(String barNumber) {
        this.barNumber = barNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Abogado abogado = (Abogado) o;
        return Objects.equals(idAbogado, abogado.idAbogado) && Objects.equals(firstName, abogado.firstName) && Objects.equals(lastName, abogado.lastName) && Objects.equals(email, abogado.email) && Objects.equals(specialty, abogado.specialty) && Objects.equals(phoneNumber, abogado.phoneNumber) && Objects.equals(barNumber, abogado.barNumber) && Objects.equals(position, abogado.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAbogado, firstName, lastName, email, specialty, phoneNumber, barNumber, position);
    }
}
