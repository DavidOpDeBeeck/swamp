package de.daxu.swamp.scheduling.query.build;

import de.daxu.swamp.common.axon.EventListener;
import de.daxu.swamp.scheduling.command.containerinstance.event.*;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;

import static de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus.*;

@EventListener
@SuppressWarnings("unused")
public class BuildViewEventListener {

    private final BuildViewRepository buildViewRepository;

    @Autowired
    public BuildViewEventListener(BuildViewRepository buildViewRepository) {
        this.buildViewRepository = buildViewRepository;
    }

    @EventHandler
    void on(ContainerInstanceInitializedEvent event) {
        getBuild(event).setContainerInstanceStatus(event.getContainerInstanceId(), INITIALIZED);
    }

    @EventHandler
    void on(ContainerInstanceCreatedEvent event) {
        getBuild(event).setContainerInstanceStatus(event.getContainerInstanceId(), CREATED);
    }

    @EventHandler
    void on(ContainerInstanceStartedEvent event) {
        getBuild(event).setContainerInstanceStatus(event.getContainerInstanceId(), STARTED);
    }

    @EventHandler
    void on(ContainerInstanceStoppedEvent event) {
        getBuild(event).setContainerInstanceStatus(event.getContainerInstanceId(), STOPPED);
    }

    @EventHandler
    void on(ContainerInstanceRemovedEvent event) {
        getBuild(event).setContainerInstanceStatus(event.getContainerInstanceId(), REMOVED);
    }

    private BuildView getBuild(ContainerInstanceEvent event) {
        return buildViewRepository.getByBuildId(event.getBuildId());
    }
}
