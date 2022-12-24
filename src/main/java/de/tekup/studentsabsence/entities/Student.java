package de.tekup.studentsabsence.entities;

import de.tekup.studentsabsence.converters.CapitalizeCharConverter;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"image", "group", "absences"})
@Table(name = "students")
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", nullable = false)
    private Long sid;

    @NotNull
    @NotBlank
    @Column(name = "first_name", nullable = false)
    @Convert(converter = CapitalizeCharConverter.class)
    private String firstName;

    @NotNull
    @NotBlank
    @Column(name = "last_name", nullable = false)
    @Convert(converter = CapitalizeCharConverter.class)
    private String lastName;

    @NotNull
    @NotBlank
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @NotBlank
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    // Student can have only one image
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "image_id", referencedColumnName = "image_id")
    private Image image;

    // Student belongs to one group
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", referencedColumnName = "group_id", nullable = false)
    private Group group;

    // Student can have multiple absences
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<Absence> absences;

}
