package de.daxu.swamp.scheduler.command.container;

import com.github.dockerjava.api.command.SyncDockerCmd;
import de.daxu.swamp.core.container.Container;

public interface ContainerCommand {

    SyncDockerCmd<?> execute( Container container );

}
