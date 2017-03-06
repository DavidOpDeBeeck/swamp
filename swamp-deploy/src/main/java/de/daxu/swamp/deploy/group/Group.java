package de.daxu.swamp.deploy.group;

import de.daxu.swamp.deploy.container.ContainerId;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class Group {

    public static Group withGroupId(GroupId groupId) {
        return new Group.Builder()
                .withGroupId(groupId)
                .build();
    }

    private final GroupId groupId;
    private final Set<ContainerId> containers;

    private Group(GroupId groupId, Set<ContainerId> containers) {
        this.groupId = groupId;
        this.containers = containers;
    }

    public GroupId id() {
        return groupId;
    }

    public GroupId getGroupId() {
        return groupId;
    }

    public void addContainer(ContainerId containerId) {
        this.containers.add(containerId);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        return groupId.equals(group.groupId);
    }

    @Override
    public int hashCode() {
        return groupId.hashCode();
    }

    protected static class Builder {

        private GroupId groupId;
        private Set<ContainerId> containers = newHashSet();

        public Builder withGroupId(GroupId groupId) {
            this.groupId = groupId;
            return this;
        }

        public Builder withContainers(Set<ContainerId> containers) {
            this.containers = containers;
            return this;
        }

        public Group build() {
            return new Group(groupId, containers);
        }
    }
}
