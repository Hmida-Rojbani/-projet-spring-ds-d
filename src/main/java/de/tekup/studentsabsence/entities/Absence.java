package de.tekup.studentsabsence.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "absences")
public class Absence implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "absence_id")
    private Long id;

    @NotNull(message = "Start date is required")
    @Past(message = "Should be a date in the past")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @NotNull(message = "Hours is required")
    @Positive(message = "Should be positive")
    @Column(name = "hours", nullable = false)
    private float hours;

    // An Absence belongs to one student
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", referencedColumnName = "student_id", nullable = false)
    private Student student;

    // An absence belongs to a subject
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id", nullable = false)
    private Subject subject;

}
