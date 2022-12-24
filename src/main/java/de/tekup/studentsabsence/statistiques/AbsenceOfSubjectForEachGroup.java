package de.tekup.studentsabsence.statistiques;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AbsenceOfSubjectForEachGroup {
    int groupId;
    String groupName;
    int subjectId;
    String subjectName;
    float totalHours;
}
