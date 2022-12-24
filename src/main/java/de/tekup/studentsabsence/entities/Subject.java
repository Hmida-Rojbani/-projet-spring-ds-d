package de.tekup.studentsabsence.entities;

import de.tekup.studentsabsence.converters.CapitalizeCharConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subjects")
public class Subject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id", nullable = false)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(name = "name", nullable = false, unique = true)
    @Convert(converter = CapitalizeCharConverter.class)
    private String name;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private Set<GroupSubject> groups;

}
