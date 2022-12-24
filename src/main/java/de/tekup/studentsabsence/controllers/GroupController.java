package de.tekup.studentsabsence.controllers;


import de.tekup.studentsabsence.entities.*;
import de.tekup.studentsabsence.enums.LevelEnum;
import de.tekup.studentsabsence.enums.SpecialityEnum;
import de.tekup.studentsabsence.holders.GroupSubjectHolder;
import de.tekup.studentsabsence.services.AbsenceService;
import de.tekup.studentsabsence.services.StudentService;
import de.tekup.studentsabsence.services.GroupService;
import de.tekup.studentsabsence.services.GroupSubjectService;
import de.tekup.studentsabsence.services.SubjectService;
import de.tekup.studentsabsence.utils.GenericSubject;
import de.tekup.studentsabsence.utils.SendInBlue;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final SubjectService subjectService;
    private final GroupSubjectService groupSubjectService;
    private final AbsenceService absenceService;
    private final StudentService studentService;

    @GetMapping({"", "/"})
    public String index(Model model) {
        List<Group> groups = groupService.getAllGroups();
        model.addAttribute("groups", groups);
        return "groups/index";
    }

    @GetMapping("/add")
    public String addView(Model model) {
        model.addAttribute("levels", LevelEnum.values());
        model.addAttribute("specialities", SpecialityEnum.values());
        model.addAttribute("group", new Group());
        return "groups/add";
    }

    @PostMapping("/add")
    public String add(@Valid Group group, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("levels", LevelEnum.values());
            model.addAttribute("specialities", SpecialityEnum.values());
            return "groups/add";
        }

        groupService.addGroup(group);
        return "redirect:/groups";
    }

    @GetMapping("/{id}/update")
    public String updateView(@PathVariable long id,  Model model) {
        model.addAttribute("levels", LevelEnum.values());
        model.addAttribute("specialities", SpecialityEnum.values());
        model.addAttribute("group", groupService.getGroupById(id));
        return "groups/update";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable long id, @Valid Group group, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("levels", LevelEnum.values());
            model.addAttribute("specialities", SpecialityEnum.values());
            return "groups/update";
        }
        groupService.updateGroup(group);
        return "redirect:/groups";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable long id) {
        groupService.deleteGroup(id);
        return "redirect:/groups";
    }

    @GetMapping("/{gid}/subject/{id}/student/{sid}/send")
    public String sendEmailsToEliminatedStudents(@PathVariable Long gid, @PathVariable Long id, @PathVariable Long sid){

        Student student = studentService.getStudentBySid(sid);
        Group group = groupService.getGroupById(gid);
        Subject subject = subjectService.getSubjectById(id);

        if(student != null && group != null && subject != null){
            SendInBlue sendInBlue = new SendInBlue(
                    student.getEmail(),
                    student.getFirstName()+" "+student.getLastName(),
                    "Tek-Up : Elimination Subject",
                    "Hello <strong>"+ student.getFirstName()+" "+student.getLastName() +"</strong>"+", you are eliminated from the subject <strong>"+subject.getName()+"</strong> for passing the limit of absences by more than 33%."
            );

            sendInBlue.sendEmail();
        }

        return "redirect:http://localhost:8080/groups/"+gid+"/subject/"+id+"/eliminated";
    }

    @GetMapping("/{id}/subject/{sid}/eliminated")
    public String showEliminatedStudents(@PathVariable long id, Model model, @PathVariable Long sid) {
        Group group = groupService.getGroupById(id);
        Subject subject = subjectService.getSubjectById(sid);
        GroupSubject groupSubject = groupSubjectService.getGroupSubjectBySubjectIdAndGroupId(id,sid);

        ArrayList<Student> students = new ArrayList<>();
        if(group.getStudents().size() > 0 && subject != null && group != null) {
            for (Student student : group.getStudents()) {
                if(absenceService.hoursCountByStudentAndSubject(student.getSid(),subject.getId()) > (groupSubject.getHours()*0.33)){
                    students.add(student);
                }
            }
        }

        model.addAttribute("group", group);
        model.addAttribute("subject",subject);
        model.addAttribute("students",students);

        return "groups/eliminated";
    }

    @GetMapping("/{id}/subjects")
    public String showSubjects(@PathVariable long id, Model model) {
        Group group = groupService.getGroupById(id);

        model.addAttribute("group", group);
        ArrayList<Subject> subjects = new ArrayList<>();
        if(groupSubjectService.getSubjectsByGroupId(id).size() > 0) {
            for (GroupSubject groupSubject : groupSubjectService.getSubjectsByGroupId(id)) {
                subjects.add(groupSubject.getSubject());
            }
        }

        model.addAttribute("subjects",subjects);
        return "groups/subjects";
    }

    @GetMapping("/{id}/show")
    public String show(@PathVariable long id, Model model) {
        Group group = groupService.getGroupById(id);

        model.addAttribute("group", group);
        model.addAttribute("groupSubjects",groupSubjectService.getSubjectsByGroupId(id));
        model.addAttribute("students",group.getStudents());
        model.addAttribute("absenceService", absenceService);

        if(groupSubjectService.getSubjectsByGroupId(id).size() > 0) {
            ArrayList<GenericSubject> subjects = new ArrayList<>();
            for (GroupSubject groupSubject : groupSubjectService.getSubjectsByGroupId(id)) {
                float hours = absenceService.hoursCountByGroupAndSubject(id, groupSubject.getSubject().getId());
                subjects.add(new GenericSubject(groupSubject.getSubject().getName(), hours));
            }

            List<GenericSubject> sortedSubjects = subjects.stream()
                    .sorted(Comparator.comparing(GenericSubject::getHours))
                    .collect(Collectors.toList());

            model.addAttribute("minSubject",sortedSubjects.get(0));
            model.addAttribute("maxSubject",sortedSubjects.get(sortedSubjects.size() - 1));
        }else {
            model.addAttribute("minSubject",new GenericSubject("",0));
            model.addAttribute("maxSubject",new GenericSubject("",0));
        }


        return "groups/show";
    }

    @GetMapping("/{id}/add-subject")
    public String addSubjectView(Model model , @PathVariable Long id){
        model.addAttribute("groupSubjectHolder", new GroupSubjectHolder());
        model.addAttribute("group",groupService.getGroupById(id));
        model.addAttribute("subjects",subjectService.getAllSubjects());
        return "groups/add-subject";

    }

    @PostMapping("/{id}/add-subject")
    public String addSubject(@PathVariable Long id, @Valid GroupSubjectHolder groupSubjectHolder, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            model.addAttribute("group",groupService.getGroupById(id));
            model.addAttribute("subjects",subjectService.getAllSubjects());
            return "groups/add-subject";
        }

        Group group = groupService.getGroupById(id);
        groupSubjectService.addSubjectToGroup(group, groupSubjectHolder.getSubject(), groupSubjectHolder.getHours());
        return "redirect:/groups/"+id+"/add-subject";
    }

    @GetMapping("/{gid}/subject/{sid}/delete")
    public String deleteSubject(@PathVariable Long gid, @PathVariable Long sid){
        groupSubjectService.deleteSubjectFromGroup(gid, sid);
        return "redirect:/groups/"+gid+"/show";
    }

    @GetMapping("/{id}/add-absences")
    public String addAbsenceView(@PathVariable long id, Model model) {
        Group group = groupService.getGroupById(id);

        model.addAttribute("group", group);
        model.addAttribute("absence", new Absence());
        model.addAttribute("groupSubjects", groupSubjectService.getSubjectsByGroupId(id));
        model.addAttribute("students", group.getStudents());

        return "groups/add-absences";
    }

    @PostMapping("/{id}/add-absences")
    public String addAbsence(@PathVariable long id, @Valid Absence absence, BindingResult bindingResult, @RequestParam(value = "students", required = false) List<Student> students, Model model) {
        //*TODO Complete the body of this method
        System.out.println(bindingResult);
        System.out.println(students);
        if(bindingResult.hasErrors() || students == null) {
            Group group = groupService.getGroupById(id);
            model.addAttribute("group", group);
            model.addAttribute("absence", absence);
            model.addAttribute("groupSubjects", groupSubjectService.getSubjectsByGroupId(id));
            model.addAttribute("students", group.getStudents());
            return "groups/add-absences";
        }

        for(Student student : students){
            absence.setStudent(student);
            absenceService.addAbsence(absence);
        }
        return "redirect:/groups/"+id+"/add-absences";
    }

}
