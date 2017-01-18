package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.util.Set;

public class ContainerInstanceDeployEvent extends ContainerInstanceEvent {

    private final Set<String> warnings;

    public ContainerInstanceDeployEvent( ContainerInstanceId containerInstanceId, Set<String> warnings ) {
        super( containerInstanceId );
        this.warnings = warnings;
    }

    public Set<String> getWarnings() {
        return warnings;
    }
}
