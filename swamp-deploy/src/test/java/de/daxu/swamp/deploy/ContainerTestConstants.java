package de.daxu.swamp.deploy;

import de.daxu.swamp.deploy.container.ContainerId;

import java.util.UUID;

public class ContainerTestConstants {

    public static ContainerId A_CONTAINER_ID = ContainerId.of(UUID.randomUUID().toString());
    public static ContainerId ANOTHER_CONTAINER_ID = ContainerId.of(UUID.randomUUID().toString());

}
