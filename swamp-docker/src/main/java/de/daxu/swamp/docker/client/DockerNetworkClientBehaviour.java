package de.daxu.swamp.docker.client;

import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.group.GroupId;

public interface DockerNetworkClientBehaviour {

    void createNetwork(GroupId groupId);

    void connectContainerToNetwork(GroupId groupId, ContainerId containerId);

}
