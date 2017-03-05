package de.daxu.swamp.deploy.client;

import de.daxu.swamp.deploy.container.ContainerConfiguration;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.notifier.Notifier;
import de.daxu.swamp.deploy.notifier.ProgressNotifier;
import de.daxu.swamp.deploy.result.DeployResult;

public interface ContainerClient extends DeployClient {

    DeployResult<ContainerId> create(ContainerConfiguration config, Notifier<String> createNotifier);

    DeployResult<?> start(ContainerId containerId);

    DeployResult<?> stop(ContainerId containerId);

    DeployResult<?> remove(ContainerId containerId);

    DeployResult<?> log(ContainerId containerId, ProgressNotifier<String> logNotifier);

    DeployResult<Boolean> exists(ContainerId containerId);

    DeployResult<Boolean> isRunning(ContainerId containerId);
}
