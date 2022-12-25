package de.tekup.studentsabsence.controllers;


import de.tekup.studentsabsence.entities.Group;
import de.tekup.studentsabsence.entities.GroupSubject;
import de.tekup.studentsabsence.entities.Subject;
import de.tekup.studentsabsence.services.AbsenceService;
import de.tekup.studentsabsence.services.GroupService;
import de.tekup.studentsabsence.services.GroupSubjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/statistiques")
@AllArgsConstructor
public class StatistiquesController {

    private GroupSubjectService groupSubjectService;
    private AbsenceService absenceService;

    private GroupService groupService;

    @GetMapping({"", "/"})
    public String index(Model model) {
        Map<String,Map<Float,Float>> groupabs = tauxAbsence();
        Set<String> groups =  new HashSet<>();
        Set<Float> max = new HashSet<>();
        Set<Float> min = new HashSet<>();
        for (String grpname:groupabs.keySet()){
            max.addAll(groupabs.get(grpname).keySet());
            min.addAll(groupabs.get(grpname).values());
            groups.add(grpname);
        }
        model.addAttribute("groups", groups);
        model.addAttribute("max", max);
        model.addAttribute("min", min);
        return "statistiques/statistiques";
    }
    public Map<String, Map<Float, Float>> tauxAbsence(){
        Map<String,Map<Float,Float>> groupabs = new HashMap<>();
        //chercher tous les groupes
        List<Group> groups = groupService.getAllGroups();
        for (Group group:groups){
            //chercher les subjects de chaque groupe
            List<GroupSubject> groupSubjects = groupSubjectService.getSubjectsByGroupId(group.getId());
            Map<Subject,Float> absenceHours = new HashMap<>();
            //Si le groupe a des subjects
            if (groupSubjects.size()!=0){
                for (GroupSubject groupSubject: groupSubjects){
                    //pour chaque groupe calculer le taux d'absences
                    absenceHours.put(groupSubject.getSubject(),absenceService.hoursCountByGroupAndSubject(group.getId(),groupSubject.getSubject().getId()));
                }
                Map<Float, Float> maxmin = new HashMap<>();
                //chercher le taux max et le taux min
                maxmin.put(Collections.max(absenceHours.values()),Collections.min(absenceHours.values()));
                String subjMax = "";
                String subjMin = "";
                //chercher le nom de chaque subject pour chaque taux
                for (Subject subject:absenceHours.keySet()){
                    if (absenceHours.get(subject)==Collections.max(absenceHours.values())){
                        subjMax = subject.getName();
                    }
                    if (absenceHours.get(subject)==Collections.min(absenceHours.values())){
                        subjMin = subject.getName();
                    }
                }
                groupabs.put(group.getName()+" (Max: "+subjMax+", Min: "+subjMin+")",maxmin);
            }
        }
        return groupabs;
    }
}
