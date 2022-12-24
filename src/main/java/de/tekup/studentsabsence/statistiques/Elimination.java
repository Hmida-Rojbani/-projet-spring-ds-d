package de.tekup.studentsabsence.statistiques;

import de.tekup.studentsabsence.entities.Group;
import de.tekup.studentsabsence.entities.Student;
import de.tekup.studentsabsence.entities.Subject;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Elimination {
    private Student student;
    private Subject subject;
    private Group group;
}
