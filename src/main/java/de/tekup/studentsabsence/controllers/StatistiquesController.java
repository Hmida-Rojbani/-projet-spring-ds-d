package de.tekup.studentsabsence.controllers;

import de.tekup.studentsabsence.entities.Group;
import de.tekup.studentsabsence.entities.GroupSubject;
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
@RequestMapping("/statistique")
@AllArgsConstructor
public class StatistiquesController {
    public final GroupService groupService;
    public final AbsenceService absenceService;
    public final GroupSubjectService groupSubjectService;


    @GetMapping({"", "/"})
    public String index(Model model) {
        Map<String, Map<Float, Float>> grpAbs = getGrpAndAbsMaxMin();
        Set<String> groups = grpAbs.keySet();
        List<Float> max = new ArrayList<>();
        List<Float> min = new ArrayList<>();
        for (String grp:groups){
            for (Float mx: grpAbs.get(grp).keySet()){
                max.add(mx);
            }

            for (Float mn: grpAbs.get(grp).values()){
                min.add(mn);
            }
        }
        model.addAttribute("groups", groups);
        model.addAttribute("max", max);
        model.addAttribute("min", min);
        return "statis";
    }



    public Map<String,Map<Float, Float>> getGrpAndAbsMaxMin (){
        Map<String,Map<Float, Float>> grpAbs = new HashMap<>();
        for (Group group: groupService.getAllGroups()){
            List<Float> absenceList =new ArrayList<>();
            for (GroupSubject groupSubject:groupSubjectService.getSubjectsByGroupId(group.getId())) {
                absenceList.add(absenceService.hoursCountByGroupAndSubject(group.getId(),groupSubject.getSubject().getId()));
                Map<Float, Float> maxMin = new HashMap<>();
                maxMin.put(Collections.max(absenceList),Collections.min(absenceList));
                grpAbs.put(group.getName(),maxMin);
            }
        }
        return grpAbs;
    }

}
