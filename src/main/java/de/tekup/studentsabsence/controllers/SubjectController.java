package de.tekup.studentsabsence.controllers;

import de.tekup.studentsabsence.entities.Absence;
import de.tekup.studentsabsence.entities.Student;
import de.tekup.studentsabsence.entities.Subject;
import de.tekup.studentsabsence.services.AbsenceService;
import de.tekup.studentsabsence.services.StudentService;
import de.tekup.studentsabsence.services.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/subjects")
@AllArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    private final AbsenceService absenceService;
    private final StudentService studentService;
    private final JavaMailSender javaMailSender;
    @GetMapping({"", "/"})
    public String index(Model model) {
        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "subjects/index";
    }

    @GetMapping("/add")
    public String addView(Model model) {
        model.addAttribute("subject", new Subject());
        return "subjects/add";
    }

    @PostMapping("/add")
    public String add(@Valid Subject subject, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "subjects/add";
        }

        subjectService.addSubject(subject);
        return "redirect:/subjects";
    }

    @GetMapping("/{id}/update")
    public String updateView(@PathVariable Long id, Model model) {
        model.addAttribute("subject", subjectService.getSubjectById(id));
        return "subjects/update";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @Valid Subject subject, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "subjects/update";
        }

        subjectService.updateSubject(subject);
        return "redirect:/subjects";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {

        subjectService.deleteSubject(id);
        return "redirect:/subjects";
    }

    @GetMapping("/{id}/show")

    public String show(@PathVariable Long id, Model model) {
        List<Student> students = studentService.getAllStudents();
        Set<Student> studentAbs = new HashSet<>();
        for (Student studentes_x : students) {
            for (Absence absence : studentes_x.getAbsences()) {
                if (absence.getSubject().getId() == id) {
                    if (absenceService.hoursCountByStudentAndSubject(studentes_x.getSid(), id) >= 6) {
                        studentAbs.add(studentes_x);
                    }
                }
            }
        }
        model.addAttribute("studentabs", studentAbs);
        model.addAttribute("subject", subjectService.getSubjectById(id));
        return "subjects/show";
    }


    @GetMapping("/{idSubject}/sendMail/{idStudent}")
    public String sendMails(@PathVariable("idSubject") Long idSubject,@PathVariable("idStudent") Long idStudent) {

        Student student = studentService.getStudentBySid(idStudent);
        Subject subject = subjectService.getSubjectById(idSubject);
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setFrom("spring.boot.123456789@gmail.com");
        sm.setTo(student.getEmail());
        sm.setText(" Attontion vous etes eliminer dans le subject "+subject.getName());
        sm.setSubject("eliminations");
        javaMailSender.send(sm);
        return "redirect:/subjects/"+idSubject+"/show";
    }


}
