package de.daxu.swamp.deploy.group;

import de.daxu.swamp.deploy.container.ContainerId;

public interface GroupManager {

    void addContainerToGroup( GroupId groupId, ContainerId containerId );

    boolean exists( GroupId groupId );

}
