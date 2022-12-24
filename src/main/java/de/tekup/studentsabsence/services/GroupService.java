package de.tekup.studentsabsence.services;

import de.tekup.studentsabsence.entities.Group;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface GroupService {
    List<Group> getAllGroups();

    Group getGroupById(Long id);

    Group addGroup(Group group);

    Group updateGroup(Group group);

    Group deleteGroup(Long id);
}
