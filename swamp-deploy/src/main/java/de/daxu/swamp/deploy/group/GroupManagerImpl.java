package de.daxu.swamp.deploy.group;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class GroupManagerImpl implements GroupManager {

    final Set<Group> groups = newHashSet();

    @Override
    public Group create(GroupId groupId) {
        Group group = Group.withGroupId(groupId);
        groups.add(group);
        return group;
    }

    @Override
    public Group get(GroupId groupId) {
        return groups.stream()
                .filter(group -> group.getGroupId().equals(groupId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean exists(GroupId groupId) {
        return groups.stream()
                .anyMatch(group -> group.getGroupId().equals(groupId));
    }
}
