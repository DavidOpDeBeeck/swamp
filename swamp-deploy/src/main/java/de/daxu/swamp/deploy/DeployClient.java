package de.daxu.swamp.deploy;

import de.daxu.swamp.deploy.container.ContainerConfiguration;
import de.daxu.swamp.deploy.container.ContainerId;

public interface DeployClient {

    DeployResult<ContainerId> createContainer(ContainerConfiguration config, DeployNotifier<String> notifier);

    DeployResult<?> startContainer(ContainerId containerId);

    DeployResult<?> stopContainer(ContainerId containerId);

    DeployResult<?> removeContainer(ContainerId containerId);

    DeployResult<?> logContainer(ContainerId containerId, DeployNotifier<String> notifier);

    DeployResult<Boolean> containerExists(ContainerId containerId);

    DeployResult<Boolean> isContainerRunning(ContainerId containerId);

}
