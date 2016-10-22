package de.daxu.swamp.core.scheduler.command.container;

import com.github.dockerjava.api.command.SyncDockerCmd;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.core.scheduler.ContainerInstance;

public interface ContainerCommand {

    SyncDockerCmd<?> execute( Container container );

}
