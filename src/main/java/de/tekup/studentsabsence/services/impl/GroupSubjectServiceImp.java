package de.tekup.studentsabsence.services.impl;

import de.tekup.studentsabsence.entities.Group;
import de.tekup.studentsabsence.entities.GroupSubject;
import de.tekup.studentsabsence.entities.GroupSubjectKey;
import de.tekup.studentsabsence.entities.Subject;
import de.tekup.studentsabsence.repositories.GroupSubjectRepository;
import de.tekup.studentsabsence.services.GroupService;
import de.tekup.studentsabsence.services.GroupSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupSubjectServiceImp implements GroupSubjectService {
    private final GroupSubjectRepository groupSubjectRepository;
    private final GroupService groupService;

    @Autowired
    public GroupSubjectServiceImp(GroupSubjectRepository groupSubjectRepository,
                                  GroupService groupService) {

        this.groupSubjectRepository = groupSubjectRepository;
        this.groupService = groupService;
    }

    @Override
    public void addSubjectToGroup(Group group, Subject subject, float hours) {
        groupSubjectRepository.save(new GroupSubject(
                new GroupSubjectKey(group.getId(), subject.getId()),
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
        var groupSubject = groupSubjectRepository.findByGroup_IdAndSubject_Id(gid, sid);

        groupSubjectRepository.delete(groupSubject);
    }

}
