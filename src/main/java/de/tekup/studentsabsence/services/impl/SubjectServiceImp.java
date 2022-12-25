package de.tekup.studentsabsence.services.impl;

import de.tekup.studentsabsence.entities.GroupSubject;
import de.tekup.studentsabsence.entities.Student;
import de.tekup.studentsabsence.entities.Subject;
import de.tekup.studentsabsence.repositories.GroupSubjectRepository;
import de.tekup.studentsabsence.repositories.SubjectRepository;
import de.tekup.studentsabsence.services.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class SubjectServiceImp implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final GroupSubjectService groupSubjectService;
    private final AbsenceService absenceService;
    private final StudentService studentService;
    private final GroupService groupService;
    private final GroupSubjectRepository groupSubjectRepository;

    //TODO Complete this method
    @Override
    public List<Subject> getAllSubjects() {
        List<Subject> subjects= new ArrayList<>();
        subjectRepository.findAll().forEach(subjects::add);
        return subjects;
    }

    @Override
    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id).
                orElseThrow(() -> new NoSuchElementException("No Subject with ID: " + id));

    }

    @Override
    public Subject addSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public Subject updateSubject(Subject subject) {
        if (!subjectRepository.existsById(subject.getId())) {
            throw new NoSuchElementException("No Subject with ID : " + subject.getId());
        }
        return subjectRepository.save(subject);
    }

    @Override
    public Subject deleteSubject(Long id) {
        Subject subject = getSubjectById(id);
        subjectRepository.delete(subject);
        return subject;
    }

    //Question 2

    @Override
    public boolean eliminatedStudentByGroup(Long id ,Long gid,Long sid){
        GroupSubject groupSubject=groupSubjectService.getSubjectsGroupBySubjectIdAndGroupId(id,gid);
        Student student=studentService.getStudentBySid(sid);
        float hour=0;
        hour=groupSubject.getHours();
        float res=absenceService.hoursCountByStudentAndSubject(student.getSid(),id);
        if(hour<res*4){
            return true;
        }else {
            return false;
        }
    }
}
