package com.example.application.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Noticia {
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long idNoticia;
    @Column
    private String title;
    @Column

    private String imagen;
    @Column

    private String cuerpo;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;


    public Noticia () {
        date = new Date();
    }

}
