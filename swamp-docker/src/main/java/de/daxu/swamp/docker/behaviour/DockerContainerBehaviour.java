package de.daxu.swamp.docker.behaviour;

import de.daxu.swamp.deploy.DeployNotifier;
import de.daxu.swamp.deploy.container.ContainerConfiguration;
import de.daxu.swamp.deploy.container.ContainerId;

import java.io.File;

public interface DockerContainerBehaviour {

    ContainerId createContainer(ContainerConfiguration configuration, DeployNotifier<String> notifier);

    void startContainer(ContainerId containerId);

    void stopContainer(ContainerId containerId);

    void removeContainer(ContainerId containerId);

    void logContainer(ContainerId containerId, DeployNotifier<String> notifier);

    void buildContainer(File directory, String tag, DeployNotifier<String> notifier);

    boolean containerExists(ContainerId containerId);

    boolean isContainerRunning(ContainerId containerId);
}
