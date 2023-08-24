package com.example.application.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Arrays;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id_user")

@Entity
@Table(name = "avocat", schema = "firstDb")
public class Avocat  extends User {

    @Column(name = "id_avocat", nullable = true)
    private Integer idAbogado;

    @NotNull
    @Column(name = "number_bar", nullable = false, length = 45)
    private String numberBar;

    @Size(max = 255)
    @NotNull
    @Column(name = "description", nullable = false, length = 45)
    private String description;

    @NotNull
    @Column(name = "position", nullable = false, length = 45)
    private String position;

    @Lob //large object data
    @Column(name = "image")
    private byte[] image;

    @Size(max = 45)
    @NotNull
    @Column(name = "speciality", nullable = false, length = 45)
    private String speciality;


    public Integer getIdAbogado() {
        return idAbogado;
    }

    public void setId(Integer idAbogado) {
        this.idAbogado = idAbogado;
    }

    public String getNumberBar() {
        return numberBar;
    }

    public void setNumberBar(String numberBar) {
        this.numberBar = numberBar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setIdAbogado(Integer idAbogado) {
        this.idAbogado = idAbogado;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Avocat avocat = (Avocat) o;
        return Objects.equals(idAbogado, avocat.idAbogado) && Objects.equals(numberBar, avocat.numberBar) && Objects.equals(description, avocat.description) && Objects.equals(position, avocat.position) && Arrays.equals(image, avocat.image) && Objects.equals(speciality, avocat.speciality);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), idAbogado, numberBar, description, position, speciality);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}