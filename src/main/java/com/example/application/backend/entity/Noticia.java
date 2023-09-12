package com.example.application.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.w3c.dom.Text;

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

    @Lob //  large object data
    @Basic(fetch = FetchType.LAZY) //
     private byte[] imagen; // Storing the image data as a byte array

@Column
 private String image64;

    @Column

    private String cuerpo;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;


    public Noticia () {
        date = new Date();
    }

    public byte[] getImage() {
        return imagen;
    }

    public void setImage(byte[] image) {
        this.imagen = image;
    }

    public String getImage64() {
        return image64;
    }

    public void setImage64(String image64) {
        this.image64 = image64;
    }
}
