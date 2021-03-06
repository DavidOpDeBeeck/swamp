package de.daxu.swamp.scheduling.query.build;

import de.daxu.swamp.common.axon.EventListener;
import de.daxu.swamp.scheduling.command.build.event.BuildContainerInstanceScheduledEvent;
import de.daxu.swamp.scheduling.command.build.event.BuildContainerInstanceStatusChangedEvent;
import de.daxu.swamp.scheduling.command.build.event.BuildEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@EventListener
@Transactional
@SuppressWarnings("unused")
public class BuildViewEventListener {

    private final BuildViewRepository buildViewRepository;

    @Autowired
    public BuildViewEventListener(BuildViewRepository buildViewRepository) {
        this.buildViewRepository = buildViewRepository;
    }

    @EventHandler
    void on(BuildContainerInstanceScheduledEvent event) {
        getBuild(event).addContainerInstance(event.getContainerInstanceId());
    }

    @EventHandler
    void on(BuildContainerInstanceStatusChangedEvent event) {
        getBuild(event).setContainerInstanceStatus(event.getContainerInstanceId(), event.getContainerInstanceStatus());
    }

    private BuildView getBuild(BuildEvent event) {
        return buildViewRepository.getByBuildId(event.getBuildId());
    }
}
