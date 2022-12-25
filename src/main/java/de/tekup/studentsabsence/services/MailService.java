package de.tekup.studentsabsence.services;

import de.tekup.studentsabsence.entities.Student;
import de.tekup.studentsabsence.entities.Subject;
import org.springframework.stereotype.Service;

@Service
public interface MailService {
    void sendEliminationEmail(Student student, Subject subject);
}
