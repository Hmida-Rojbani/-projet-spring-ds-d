package de.tekup.studentsabsence.services.impl;

import de.tekup.studentsabsence.entities.Group;
import de.tekup.studentsabsence.repositories.GroupRepository;
import de.tekup.studentsabsence.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GroupServiceImp implements GroupService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImp(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<>();
        groupRepository.findAll().forEach(groups::add);
        return groups;
    }

    @Override
    public Group getGroupById(Long id) {
        return groupRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No Group With ID: " + id));
    }

    @Override
    public Group addGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group updateGroup(Group group) {
        if (!groupRepository.existsById(group.getId())) {
            throw new NoSuchElementException("No Group With ID: " + group.getId());
        }
        return groupRepository.save(group);
    }

    @Override
    public Group deleteGroup(Long id) {
        Group group = getGroupById(id);
        groupRepository.delete(group);
        return group;
    }
}
