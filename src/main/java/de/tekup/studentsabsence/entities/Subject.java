package de.tekup.studentsabsence.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@ToString(exclude = "absences")
public class Subject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long id;
    @NotBlank(message = "Name is required")
    @NonNull
    private String name;

    @OneToMany(mappedBy = "subject")
    private List<Absence> absences;
}
