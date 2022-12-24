package de.tekup.studentsabsence.controllers;

import de.tekup.studentsabsence.services.AbsenceService;
import de.tekup.studentsabsence.statistiques.AbsenceOfSubjectForEachGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboards")
public class DashboardController {
    private final AbsenceService absenceService;

    @Autowired
    public DashboardController(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    @GetMapping
    public String index(Model model) {
        List<AbsenceOfSubjectForEachGroup> max = this.absenceService.findMaxAbsenceOfSubjectForEachGroup();
        List<AbsenceOfSubjectForEachGroup> min = this.absenceService.findMinAbsenceOfSubjectForEachGroup();
        model.addAttribute("max", max);
        model.addAttribute("min", min);
        return "dashboards/index";
    }

}
