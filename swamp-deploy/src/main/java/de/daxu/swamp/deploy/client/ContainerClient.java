package de.daxu.swamp.deploy.client;

import de.daxu.swamp.deploy.callback.ProgressCallback;
import de.daxu.swamp.deploy.container.ContainerConfiguration;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.result.ContainerResult;

public interface ContainerClient extends DeployClient {

    ContainerResult create(ContainerConfiguration config, ProgressCallback<String> createCallback);

    ContainerResult start(ContainerId containerId);

    ContainerResult stop(ContainerId containerId);

    ContainerResult remove(ContainerId containerId);

    ContainerResult log(ContainerId containerId, ProgressCallback<String> logCallback);

    boolean exists(ContainerId containerId);

    boolean isRunning(ContainerId containerId);
}
