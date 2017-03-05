package de.daxu.swamp.docker.client;

import de.daxu.swamp.deploy.container.ContainerConfiguration;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.notifier.Notifier;
import de.daxu.swamp.deploy.notifier.ProgressNotifier;

import java.io.File;

public interface DockerContainerBehaviour {

    ContainerId createContainer(ContainerConfiguration configuration, Notifier<String> notifier);

    void startContainer(ContainerId containerId);

    void stopContainer(ContainerId containerId);

    void removeContainer(ContainerId containerId);

    void logContainer(ContainerId containerId, ProgressNotifier<String> notifier);

    void buildContainer(File directory, String tag, Notifier<String> notifier);

    boolean containerExists(ContainerId containerId);

    boolean isContainerRunning(ContainerId containerId);
}
