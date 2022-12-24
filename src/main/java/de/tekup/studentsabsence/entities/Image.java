package de.tekup.studentsabsence.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "images")
public class Image implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false)
    private Long id;


    @Column(name = "file_name", nullable = false, unique = true)
    private String fileName;

  
    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Lob
 
    @Column(name = "data", nullable = false)
    private byte[] data;


}
