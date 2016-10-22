package de.daxu.swamp.core.scheduler.command.instance;

import com.github.dockerjava.api.command.SyncDockerCmd;
import de.daxu.swamp.core.scheduler.ContainerInstance;

public class RestartCommand implements ContainerInstanceCommand {

    @Override
    public SyncDockerCmd<?> execute( ContainerInstance instance ) {
        // TODO
        return null;
    }
}
