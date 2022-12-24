package de.tekup.studentsabsence.controllers;

import de.tekup.studentsabsence.services.StudentService;
import de.tekup.studentsabsence.statistiques.Elimination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/eliminations")
public class EliminationController {
    private final StudentService studentService;

    @Autowired
    public EliminationController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String index(Model model) {
        List<List<Elimination>> eliminatedStudentsList = this.studentService.getListOfEliminatedStudent();
        model.addAttribute("eliminatedStudentsList", eliminatedStudentsList);
        return "eliminations/index";
    }

    @GetMapping("/{sid}/send-mail")
    public String updateView(@PathVariable Long sid) {
        this.studentService.sendEmail(sid);

        return "redirect:/eliminations";

    }
}
