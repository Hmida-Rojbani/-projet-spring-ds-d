package de.tekup.studentsabsence.entities;

import de.tekup.studentsabsence.converters.CapitalizeCharConverter;
import de.tekup.studentsabsence.enums.LevelEnum;
import de.tekup.studentsabsence.enums.SpecialityEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@ToString(exclude = {"students", "subjects"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", nullable = false)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(name = "name", nullable = false, unique = true)
    @Convert(converter = CapitalizeCharConverter.class)
    private String name;

    @NotBlank(message = "Label is required")
    @Column(name = "label", nullable = false)
    @Convert(converter = CapitalizeCharConverter.class)
    private String label;

    @NotNull(message = "Level is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    @Convert(converter = CapitalizeCharConverter.class)
    private LevelEnum level;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Speciality is required")
    @Column(name = "speciality", nullable = false)
    @Convert(converter = CapitalizeCharConverter.class)
    private SpecialityEnum speciality;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<Student> students;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<GroupSubject> subjects;
}
