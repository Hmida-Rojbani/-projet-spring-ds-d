package de.tekup.studentsabsence.services;

import de.tekup.studentsabsence.entities.Student;
import de.tekup.studentsabsence.statistiques.Elimination;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    List<Student> getAllStudents();

    Student getStudentBySid(Long sid);

    Student addStudent(Student student);

    Student updateStudent(Student student);

    void deleteStudent(Long sid);

    List<List<Elimination>> getListOfEliminatedStudent();

    void sendEmail(Long sid);
}

