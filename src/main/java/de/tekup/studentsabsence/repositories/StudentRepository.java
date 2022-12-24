package de.tekup.studentsabsence.repositories;

import de.tekup.studentsabsence.entities.Student;
import de.tekup.studentsabsence.statistiques.Elimination;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
    @Query(value = "SELECT new de.tekup.studentsabsence.statistiques.Elimination(ab.student, ab.subject, st.group)" +
            " FROM Absence ab, Subject sb, Student st, Group gr " +
            " WHERE st.sid = ab.student.sid " +
            " AND gr.id = st.group.id " +
            " AND sb.id = ab.subject.id " +
            " AND sb.id = ?2 " +
            " AND gr.id = ?1 " +
            " AND ab.hours > (SELECT gs.hours " +
            "                FROM GroupSubject gs " +
            "                WHERE gs.subject.id = ?2 " +
            "                AND gs.group.id = ?1 )")
    List<Elimination> findEliminatedStudents(Long GroupId, Long SubjectId);

}
