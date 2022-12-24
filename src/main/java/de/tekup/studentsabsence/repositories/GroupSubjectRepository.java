package de.tekup.studentsabsence.repositories;

import de.tekup.studentsabsence.entities.Group;
import de.tekup.studentsabsence.entities.GroupSubject;
import de.tekup.studentsabsence.entities.GroupSubjectKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupSubjectRepository extends CrudRepository<GroupSubject, GroupSubjectKey> {
    List<GroupSubject> findAllByGroup(Group id);

    GroupSubject findByGroup_IdAndSubject_Id(Long group_id, Long subject_id);
}
