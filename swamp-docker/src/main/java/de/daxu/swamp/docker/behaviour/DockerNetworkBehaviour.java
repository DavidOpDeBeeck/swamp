package de.daxu.swamp.docker.behaviour;

import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.group.GroupId;

public interface DockerNetworkBehaviour {

    void createNetwork(GroupId groupId);

    void connectContainerToNetwork(GroupId groupId, ContainerId containerId);

}
