package de.daxu.swamp.deploy.group;

import com.google.common.annotations.VisibleForTesting;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static com.google.common.collect.Sets.newHashSet;

public class GroupDAO {

    @VisibleForTesting
    final Set<Group> groups = newHashSet();

    public Group create(Group group) {
        groups.add(group);
        return group;
    }

    public void remove(GroupId groupId) {
        groups.removeIf(equalsGroup(groupId));
    }

    public Optional<Group> get(GroupId groupId) {
        return groups.stream()
                .filter(equalsGroup(groupId))
                .findFirst();
    }

    public boolean exists(GroupId groupId) {
        return groups.stream()
                .anyMatch(equalsGroup(groupId));
    }

    private Predicate<Group> equalsGroup(GroupId groupId) {
        return group -> group.getGroupId().equals(groupId);
    }
}
