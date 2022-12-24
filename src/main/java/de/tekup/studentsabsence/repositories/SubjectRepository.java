package de.tekup.studentsabsence.repositories;

import de.tekup.studentsabsence.entities.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, Long> {
}

