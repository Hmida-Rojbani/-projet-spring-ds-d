package de.tekup.studentsabsence.repositories;

import de.tekup.studentsabsence.entities.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {
}
