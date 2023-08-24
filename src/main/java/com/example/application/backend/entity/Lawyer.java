package com.example.application.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Arrays;
import java.util.Objects;


@NoArgsConstructor
@AllArgsConstructor

@Data
@Entity
@PrimaryKeyJoinColumn(name = "id_user")

public class Lawyer  extends User  {
    @Column(name = "id_lawyers", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "speciality", nullable = false, length = 45)
    private String speciality;

    @Size(max = 45)
    @NotNull
    @Column(name = "bar_number", nullable = false, length = 45)
    private String barNumber;

    @Size(max = 45)
    @NotNull
    @Column(name = "position", nullable = false, length = 45)
    private String position;

    @Column(name = "image")
    private byte[] image;

    @Size(max = 45)
    @NotNull
    @Column(name = "description", nullable = false, length = 45)
    private String description;

    @OneToOne(cascade = CascadeType.PERSIST) // Cascade only the persist operation
    @JoinColumn(name = "id_user")
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lawyer lawyer = (Lawyer) o;
        return Objects.equals(id, lawyer.id) && Objects.equals(speciality, lawyer.speciality) && Objects.equals(barNumber, lawyer.barNumber) && Objects.equals(position, lawyer.position) && Arrays.equals(image, lawyer.image) && Objects.equals(description, lawyer.description) && Objects.equals(user, lawyer.user);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, speciality, barNumber, position, description, user);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}
