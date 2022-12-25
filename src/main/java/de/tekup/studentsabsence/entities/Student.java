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
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"image","group","absences"})
public class Student implements Serializable {
    //TODO Complete Validations of fields


    @Id
    private Long sid;
    @Column(length = 50,nullable = false)
    @NotEmpty
    @Size(min=3,max = 50)
    private String firstName;
    @Column(length = 50, nullable = false)
    @NotEmpty
    @Size(min = 3, max = 50)
    private String lastName;
    @Email(message = "Email is not valid" )
    private String email;
    @Column(unique = true,length = 8)
    @Pattern(regexp = "^[0-9]{8}$",message = "phone must only contains digits")
    @Size (min = 8,max = 8,message = "phone must exactly contains digits")
    private String phone;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Past(message = "Date input is valid for a birth date.")    private LocalDate dob;

    //TODO Complete Relations with other entities

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
    @OneToMany(mappedBy = "student",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Absence> absences = new ArrayList<>();



}
