package de.daxu.swamp.deploy.group;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class GroupManagerImpl implements GroupManager {

    final Set<Group> groups = newHashSet();

    @Override
    public Group getOrCreate(GroupId groupId) {
        if(exists(groupId)) {
            return get(groupId);
        } else {
            Group group = Group.withGroupId(groupId);
            groups.add(group);
            return group;
        }
    }

    @Override
    public boolean exists(GroupId groupId) {
        return groups.stream()
                .anyMatch(group -> group.getGroupId().equals(groupId));
    }

    private Group get(GroupId groupId) {
        return groups.stream()
                .filter(group -> group.getGroupId().equals(groupId))
                .findFirst()
                .orElse(null);
    }
}
