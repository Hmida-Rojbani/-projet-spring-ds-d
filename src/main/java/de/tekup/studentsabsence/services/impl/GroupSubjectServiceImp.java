package de.tekup.studentsabsence.services.impl;
import de.tekup.studentsabsence.entities.Group;
import de.tekup.studentsabsence.entities.GroupSubject;
import de.tekup.studentsabsence.entities.GroupSubjectKey;
import de.tekup.studentsabsence.entities.Subject;
import de.tekup.studentsabsence.repositories.GroupSubjectRepository;
import de.tekup.studentsabsence.services.AbsenceService;
import de.tekup.studentsabsence.services.GroupService;
import de.tekup.studentsabsence.services.GroupSubjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GroupSubjectServiceImp implements GroupSubjectService {
    private final GroupSubjectRepository groupSubjectRepository;
    private final GroupService groupService;
    private  final AbsenceService absenceService;

    @Override
    public void addSubjectToGroup(Group group, Subject subject, float hours) {
        groupSubjectRepository.save(new GroupSubject(
                new GroupSubjectKey(group.getId(),subject.getId()),
                group,
                subject,
                hours
        ));
    }
    @Override
    public List<GroupSubject> getSubjectsByGroupId(Long id) {
        Group group = groupService.getGroupById(id);
        return new ArrayList<>(groupSubjectRepository.findAllByGroup(group));
    }
    @Override
    public void deleteSubjectFromGroup(Long gid, Long sid) {
        //TODO find a groupSubject by Group Id and Subject Id
        GroupSubject groupSubject = groupSubjectRepository.findGroupSubjectByGroup_IdAndSubject_Id(gid,sid);

        groupSubjectRepository.delete(groupSubject);
    }

    //Question 1
    public Subject getMaxAbsenceSubject(List<GroupSubject> groupSubjects){
        Subject maxsubject = null;
        float old=0;    float max=0;
        for (GroupSubject gs:groupSubjects) {
            old=absenceService.hoursCountByGroupAndSubject(gs.getGroup().getId(), gs.getSubject().getId());
            if(old>max){
                max=old;
                maxsubject=gs.getSubject();
            }
        }
        return maxsubject;
    }
    public Subject getMinAbsenceSubject(List<GroupSubject> groupSubjects){
        Subject minsubject = null;
        float old=0;    float min=10000;
        for (GroupSubject gs:groupSubjects) {
            old=absenceService.hoursCountByGroupAndSubject(gs.getGroup().getId(), gs.getSubject().getId());
            if(old<min){
                min=old;
                minsubject=gs.getSubject();
            }
        }
        return minsubject;
    }
    //Question 2
    public List<GroupSubject> getSubjectsGroupBySubjectId(Long sid){
        List<GroupSubject> groupSubjects=new ArrayList<>();
        groupSubjectRepository.findGroupSubjectBySubject_Id(sid).forEach(groupSubjects::add);
        return groupSubjects;
    }
    public GroupSubject getSubjectsGroupBySubjectIdAndGroupId(Long sid,Long gid){
        GroupSubject groupSubject= groupSubjectRepository.findGroupSubjectBySubject_IdAndGroup_Id(sid,gid);
        return groupSubject;
    }

}