package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.util.Set;

public class ContainerInstanceCreatedFailedEvent
        extends AbstractContainerInstanceDeployEvent
        implements ContainerInstanceCreatedEvent, ContainerInstanceDeployFailedEvent {

    private final Set<String> errors;

    public ContainerInstanceCreatedFailedEvent(ContainerInstanceId containerInstanceId,
                                               BuildId buildId,
                                               EventMetaData eventMetaData,
                                               ContainerId containerId,
                                               Set<String> errors) {
        super(containerInstanceId, buildId, eventMetaData, containerId);
        this.errors = errors;
    }

    @Override
    public Set<String> getErrors() {
        return errors;
    }
}
