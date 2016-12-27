package de.daxu.swamp.deploy.group;

import de.daxu.swamp.deploy.container.ContainerId;

import java.util.Set;

public interface GroupManager {

    void addGroup( GroupId groupId );

    void addContainer( GroupId groupId, ContainerId containerId );

    Set<GroupId> getGroups( ContainerId containerId );

    boolean exists( GroupId groupId );

    Set<GroupId> getAllGroups();
}
