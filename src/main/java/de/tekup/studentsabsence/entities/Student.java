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
    @Id
    private Long sid;
    @Column(length = 50,nullable = false)
    @NotEmpty
    @Size(min = 3, max = 50)
    private String firstName;
    @Column(length = 50,nullable = false)
    @NotEmpty
    @Size(min = 3, max = 50)
    private String lastName;
    @Email(message = "Email is not valid", regexp = "[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @Column(unique = true,length = 8)
    @Pattern(regexp = "^[0-9]{8}$",message = "Phone must contains only digits")
    @Size(min = 8,max = 8, message = "Phone must be exactly 8 digits")
    private String phone;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Past(message = "Date input is invalid for a birth date.")
    private LocalDate dob;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;
    @ManyToOne
    @JoinColumn(name = "group_id")
    @NotNull(message = "l'etudiant doit etre assigné a un groupe")
    private Group group;
    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private List<Absence> absences = new ArrayList<>();



}
