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
@Table(name = "avocat", schema = "landb")
public class Abogado extends User {
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


    @Column
    private String image64;


    @Size(max = 45)
    @NotNull
    @Column(name = "speciality", nullable = false, length = 45)
    private String speciality;


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
/*
    public void setIdAbogado(Integer idAbogado) {
        this.idAbogado = idAbogado;
    }
*/

    public String getImage64() {
        return image64;
    }

    public void setImage64(String image64) {
        this.image64 = image64;
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
        Abogado abogado = (Abogado) o;
        return  Objects.equals(numberBar, abogado.numberBar) && Objects.equals(description, abogado.description) && Objects.equals(position, abogado.position) && Arrays.equals(image, abogado.image) && Objects.equals(speciality, abogado.speciality);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(),  numberBar, description, position, speciality);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}