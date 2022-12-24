package de.tekup.studentsabsence.services;

import de.tekup.studentsabsence.entities.Absence;
import de.tekup.studentsabsence.statistiques.AbsenceOfSubjectForEachGroup;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface AbsenceService {
    List<Absence> getAllAbsences();

    List<Absence> getAllAbsencesByGroupId(Long id);

    List<Absence> getAllAbsencesByStudentId(Long sid);

    List<Absence> getAllAbsencesByStudentIdAndSubjectId(Long sid, Long id);

    List<Absence> getAllAbsencesByGroupIdAndSubjectId(Long gid, Long id);

    Absence getAbsenceById(Long id);

    void addAbsence(Absence absence);

    Absence deleteAbsence(Long id);

    float hoursCountByStudent(Long sid);

    float hoursCountByGroupAndSubject(Long gid, Long id);

    float hoursCountByStudentAndSubject(Long sid, Long id);

    float countHours(List<Absence> absences);

    List<AbsenceOfSubjectForEachGroup> findMaxAbsenceOfSubjectForEachGroup();

    List<AbsenceOfSubjectForEachGroup> findMinAbsenceOfSubjectForEachGroup();
}
