package de.daxu.swamp.core.scheduler.command.instance;

import com.github.dockerjava.api.command.SyncDockerCmd;
import de.daxu.swamp.core.scheduler.ContainerInstance;

public interface ContainerInstanceCommand {

    SyncDockerCmd<?> execute( ContainerInstance instance );

}
