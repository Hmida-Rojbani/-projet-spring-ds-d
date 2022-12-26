package de.tekup.studentsabsence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"image","group","absences"})
public class Student implements Serializable {
    //TODO Complete Validations of fields


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;
    @Column(length = 50,nullable = false)
    @NotBlank
    @Size(min = 3, max = 50)
    private String firstName;
    @Size(max = 50)
    private String lastName;
    @Size(max = 50)
    private String email;
    @Column(unique = true,length = 8)
    @Pattern(regexp = "^[0-9]{8}$",message = "phone number must contains only digits")
    @Size(min = 8,max = 8, message = "phone number must be exactly 8 digits")
    private String phone;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;

    //TODO Complete Relations with other entities
    @OneToMany(mappedBy = "student")
    List<Absence> absences;
    @ManyToOne
    private Group group;
    @OneToOne(mappedBy = "student")
    private Image image;
    

}
