package com.example.application.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Services", schema = "firstDb")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column
    private Integer idService;


    @Column
    private String service;


    @Column
    private String description;

    public Integer getIdService() {
        return idService;
    }

    public void setIdService(Integer idService) {
        this.idService = idService;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service1 = (Service) o;
        return Objects.equals(idService, service1.idService) && Objects.equals(service, service1.service) && Objects.equals(description, service1.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idService, service, description);
    }
}