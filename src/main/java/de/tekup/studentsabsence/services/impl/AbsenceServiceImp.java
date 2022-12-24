package de.tekup.studentsabsence.services.impl;

import de.tekup.studentsabsence.entities.Absence;
import de.tekup.studentsabsence.entities.Student;
import de.tekup.studentsabsence.entities.Subject;
import de.tekup.studentsabsence.repositories.AbsenceRepository;
import de.tekup.studentsabsence.repositories.StudentRepository;
import de.tekup.studentsabsence.services.AbsenceService;
import de.tekup.studentsabsence.statistiques.AbsenceOfSubjectForEachGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AbsenceServiceImp implements AbsenceService {
    private final AbsenceRepository absenceRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public AbsenceServiceImp(AbsenceRepository absenceRepository,
                             StudentRepository studentRepository) {
        this.absenceRepository = absenceRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Absence> getAllAbsences() {
        List<Absence> absences = new ArrayList<>();
        absenceRepository.findAll().forEach(absences::add);
        return absences;
    }

    @Override
    public List<Absence> getAllAbsencesByGroupId(Long id) {
        return new ArrayList<>(absenceRepository.findAllByStudent_Group_Id(id));
    }

    @Override
    public List<Absence> getAllAbsencesByStudentId(Long sid) {
        //TODO complete the missing instructions
        return new ArrayList<>(absenceRepository.findAllByStudent_Sid(sid));
    }

    @Override
    public List<Absence> getAllAbsencesByStudentIdAndSubjectId(Long sid, Long id) {
        //TODO complete the missing instructions
        return new ArrayList<>(absenceRepository.findAllByStudent_SidAndSubject_Id(sid, id));
    }

    @Override
    public List<Absence> getAllAbsencesByGroupIdAndSubjectId(Long gid, Long id) {
        return new ArrayList<>(absenceRepository.findAllByStudent_Group_IdAndSubject_Id(gid, id));
    }

    @Override
    public Absence getAbsenceById(Long id) {
        return absenceRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No Absence With ID: " + id));
    }

    @Override
    public void addAbsence(Absence newAbsence) {

        Student student = newAbsence.getStudent();
        Subject subject = newAbsence.getSubject();

        List<Absence> absenceListPerSubject = absenceRepository.findAllByStudent_SidAndSubject_Id(student.getSid(), subject.getId());
        int absencePerSubjectListLength = absenceListPerSubject.size();

        if (absencePerSubjectListLength == 0) {
            absenceRepository.save(newAbsence);
            return;
        }

        for (Absence absence : absenceListPerSubject) {
            if (absence.getSubject() == newAbsence.getSubject()) {
                float totalAbsenceHours = absence.getHours() + newAbsence.getHours();
                absence.setHours(totalAbsenceHours > 0 ? totalAbsenceHours : 0);
            }

        }

        student.setAbsences(new HashSet<>(absenceListPerSubject));
    }

    @Override
    public Absence deleteAbsence(Long id) {
        Absence absence = getAbsenceById(id);
        absenceRepository.delete(absence);
        return absence;
    }

    @Override
    public float hoursCountByStudent(Long sid) {
        List<Absence> absences = getAllAbsencesByStudentId(sid);
        return countHours(absences);
    }

    @Override
    public float hoursCountByGroupAndSubject(Long gid, Long id) {
        List<Absence> absences = getAllAbsencesByGroupIdAndSubjectId(gid, id);
        return countHours(absences);
    }

    @Override
    public float hoursCountByStudentAndSubject(Long sid, Long id) {
        List<Absence> absences = getAllAbsencesByStudentIdAndSubjectId(sid, id);
        return countHours(absences);
    }

    //TODO Complete the countHours method
    public float countHours(List<Absence> absences) {
        return (float) absences.stream().mapToDouble(Absence::getHours).sum();
    }

    @Override
    public List<AbsenceOfSubjectForEachGroup> findMaxAbsenceOfSubjectForEachGroup() {
        return this.absenceRepository.findMaxAbsenceOfSubjectForEachGroup()
                .stream()
                .map(col -> new AbsenceOfSubjectForEachGroup(((BigInteger) col[0]).intValue(),
                        (String) col[1],
                        ((BigInteger) col[2]).intValue(),
                        (String) col[3],
                        ((Double) col[4]).floatValue())).collect(Collectors.toList());
    }

    @Override
    public List<AbsenceOfSubjectForEachGroup> findMinAbsenceOfSubjectForEachGroup() {
        return this.absenceRepository.findMinAbsenceOfSubjectForEachGroup()
                .stream()
                .map(col -> new AbsenceOfSubjectForEachGroup(((BigInteger) col[0]).intValue(),
                        (String) col[1],
                        ((BigInteger) col[2]).intValue(),
                        (String) col[3],
                        ((Double) col[4]).floatValue())).collect(Collectors.toList());
    }
}
