package de.tekup.studentsabsence.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
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

    @NotBlank(message = "FirstName is required")
    private String firstName;

    @NotBlank(message = "LastName is required")
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotNull(message = "Start date is required")
    private String phone;

    @NotNull(message = "date of birth is required")
    @Past(message = "Should be a date in the past")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;

    //TODO Complete Relations with other entities
    @ManyToOne
    private Group group;

    @OneToOne
    private Image image;

    @OneToMany(mappedBy = "student")
    private List<Absence> absences;



}