package de.tekup.studentsabsence.services;

import de.tekup.studentsabsence.entities.Subject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface SubjectService {

    List<Subject> getAllSubjects();

    Subject getSubjectById(Long id);

    Subject addSubject(Subject subject);

    Subject deleteSubject(Long id);

    Subject updateSubject(Subject subject);


}
