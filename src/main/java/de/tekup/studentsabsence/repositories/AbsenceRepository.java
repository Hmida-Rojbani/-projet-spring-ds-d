package de.tekup.studentsabsence.repositories;

import de.tekup.studentsabsence.entities.Absence;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbsenceRepository extends CrudRepository<Absence, Long> {
    List<Absence> findAllByStudent_Group_Id(Long id);

    List<Absence> findAllByStudent_Sid(Long sid);

    List<Absence> findAllByStudent_SidAndSubject_Id(Long sid, Long id);

    List<Absence> findAllByStudent_Group_IdAndSubject_Id(Long gid, Long id);

    @Query(nativeQuery = true, value = "SELECT t.group_id, t.group_name, t.subject_id, t.subject_name, t.total " +
            "FROM ( " +
            "    SELECT  gr.group_id, " +
            "    sub.subject_id, " +
            "    gr.name group_name,  " +
            "    sub.name subject_name, " +
            "    sum(abs.hours) as total, " +
            "    ROW_NUMBER() OVER (PARTITION BY gr.group_id ORDER BY sum(abs.hours) DESC) as rn " +
            "    FROM `absences` abs, `subjects` sub, `students` st, `groups` gr " +
            "    where abs.subject_id = sub.subject_id " +
            "    AND st.student_id = abs.student_id " +
            "    AND gr.group_id = st.group_id " +
            "    GROUP BY gr.group_id, sub.subject_id " +
            ") as t " +
            " WHERE rn = 1; ")
    List<Object[]> findMaxAbsenceOfSubjectForEachGroup();

    @Query(nativeQuery = true, value = "SELECT t.group_id, t.group_name, t.subject_id, t.subject_name, t.total " +
            "FROM ( " +
            "    SELECT  gr.group_id, " +
            "    sub.subject_id, " +
            "    gr.name group_name,  " +
            "    sub.name subject_name, " +
            "    sum(abs.hours) as total, " +
            "    ROW_NUMBER() OVER (PARTITION BY gr.group_id ORDER BY sum(abs.hours) ASC) as rn " +
            "    FROM `absences` abs, `subjects` sub, `students` st, `groups` gr " +
            "    where abs.subject_id = sub.subject_id " +
            "    AND st.student_id = abs.student_id " +
            "    AND gr.group_id = st.group_id " +
            "    GROUP BY gr.group_id, sub.subject_id " +
            ") as t " +
            " WHERE rn = 1; ")
    List<Object[]> findMinAbsenceOfSubjectForEachGroup();
}
