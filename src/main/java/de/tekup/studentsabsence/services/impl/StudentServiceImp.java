package de.tekup.studentsabsence.services.impl;

import de.tekup.studentsabsence.entities.GroupSubject;
import de.tekup.studentsabsence.entities.Student;
import de.tekup.studentsabsence.repositories.GroupSubjectRepository;
import de.tekup.studentsabsence.repositories.StudentRepository;
import de.tekup.studentsabsence.services.StudentService;
import de.tekup.studentsabsence.statistiques.Elimination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImp implements StudentService {
    private final StudentRepository studentRepository;
    private final GroupSubjectRepository groupSubjectRepository;

    private final EmailService emailService;

    @Autowired
    public StudentServiceImp(StudentRepository studentRepository,
                             GroupSubjectRepository groupSubjectRepository,
                             EmailService emailService) {
        this.studentRepository = studentRepository;
        this.groupSubjectRepository = groupSubjectRepository;
        this.emailService = emailService;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        this.studentRepository.findAll().forEach(students::add);

        return students;
    }

    @Override
    public Student getStudentBySid(Long sid) {
        // Check if the student exist by id
        Optional<Student> student = this.getStudentById(sid);
        if (student.isEmpty())
            throw new NoSuchElementException("No Student With SID: " + sid);

        // Get Student object
        return student.get();
    }

    @Override
    public Student addStudent(Student newStudent) {
        Long studentId = newStudent.getSid();

        // Check if the student exist by id
        Optional<Student> student = this.getStudentById(studentId);
        if (student.isPresent())
            throw new NoSuchElementException("Student With SID: " + studentId + " already exist");

        // Persist data to the database
        return studentRepository.save(newStudent);
    }

    @Override
    public Student updateStudent(Student updatedStudent) {
        // Get student id
        Long studentId = updatedStudent.getSid();

        // Check if the student exist by id
        Optional<Student> student = this.getStudentById(studentId);
        if (student.isEmpty())
            throw new NoSuchElementException("No Student With SID: " + studentId);

        // Persist to database
        return this.studentRepository.save(updatedStudent);
    }

    @Override
    public void deleteStudent(Long sid) {
        // Throw an exception when student id does not exist
        this.getStudentById(sid);

        // Delete student object
        this.studentRepository.deleteById(sid);
    }

    @Override

    public List<List<Elimination>> getListOfEliminatedStudent() {
        List<GroupSubject> GroupSubjectList = new ArrayList<>();
        this.groupSubjectRepository.findAll().forEach(GroupSubjectList::add);

        return GroupSubjectList
                .stream()
                .map(e -> this.studentRepository.findEliminatedStudents(e.getGroup().getId(), e.getSubject().getId()))
                .filter(item -> !item.isEmpty())
                .collect(Collectors.toList());
    }

    public void sendEmail(Long studentId) {
        // Check if the student exist by id
        Optional<Student> student = this.getStudentById(studentId);
        if (student.isEmpty())
            throw new NoSuchElementException("No Student With SID: " + studentId);

        Student existingStudent = student.get();
        String emailSubject = "Elimination";
        String emailBody = "You are eliminated";
        this.emailService.sendEmail(existingStudent.getEmail(),emailSubject, emailBody);
    }

    private Optional<Student> getStudentById(Long studentId) {
        return studentRepository.findById(studentId);
    }


}
