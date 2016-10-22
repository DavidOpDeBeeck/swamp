package de.daxu.swamp.core.scheduler.command.instance;

import com.github.dockerjava.api.command.SyncDockerCmd;
import de.daxu.swamp.core.scheduler.ContainerInstance;

import static de.daxu.swamp.core.scheduler.client.DockerClientFactory.createClient;

public class RemoveCommand implements ContainerInstanceCommand {

    @Override
    public SyncDockerCmd<?> execute( ContainerInstance instance ) {
        return createClient( instance.getServer() ).removeContainerCmd( instance.getInternalContainerId() );
    }
}
