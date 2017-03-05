package de.daxu.swamp.docker.adapter.command;

import de.daxu.swamp.deploy.container.ContainerConfiguration;
import de.daxu.swamp.deploy.notifier.Notifier;
import de.daxu.swamp.docker.client.DockerClient;
import de.daxu.swamp.docker.configurator.DockerRunConfigurator;
import de.daxu.swamp.docker.configurator.DockerRunConfiguratorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DockerCreateContainerCommandAdapterFactory {

    private final DockerRunConfiguratorFactory runConfiguratorFactory;

    @Autowired
    public DockerCreateContainerCommandAdapterFactory(DockerRunConfiguratorFactory runConfiguratorFactory) {
        this.runConfiguratorFactory = runConfiguratorFactory;
    }

    public DockerCreateContainerCommandAdapter create(DockerClient client,
                                                      ContainerConfiguration config,
                                                      Notifier<String> createNotifier) {

        DockerRunConfigurator configurator
                = runConfiguratorFactory.create(client, createNotifier);

        String tag = config.getRunConfiguration()
                .configure(configurator);

        return new DockerCreateContainerCommandAdapter()
                .withTag(tag)
                .withGroup(config.getGroupId())
                .withAliases(config.getAliases())
                .withPortMappings(config.getPortMappings())
                .withEnvironmentVariables(config.getEnvironmentVariables());
    }
}
