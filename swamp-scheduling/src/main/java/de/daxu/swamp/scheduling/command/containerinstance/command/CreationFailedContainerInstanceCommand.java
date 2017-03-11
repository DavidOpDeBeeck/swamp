package de.daxu.swamp.scheduling.command.containerinstance.command;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.util.Set;

public class CreationFailedContainerInstanceCommand extends ContainerInstanceCommand {

    private Set<String> warnings;

    public CreationFailedContainerInstanceCommand(ContainerInstanceId containerInstanceId, Set<String> warnings) {
        super( containerInstanceId );
        this.warnings = warnings;
    }

    public Set<String> getWarnings() {
        return warnings;
    }
}
