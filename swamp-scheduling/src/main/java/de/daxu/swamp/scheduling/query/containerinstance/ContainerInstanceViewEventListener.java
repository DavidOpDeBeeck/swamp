package de.daxu.swamp.scheduling.query.containerinstance;

import de.daxu.swamp.common.axon.EventListener;
import de.daxu.swamp.scheduling.command.containerinstance.event.*;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;

import static de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus.*;
import static de.daxu.swamp.scheduling.query.containerinstance.ContainerInstanceView.Builder.aContainerInstanceView;
import static de.daxu.swamp.scheduling.query.containerinstance.ServerView.Builder.aServerView;

@EventListener
@SuppressWarnings("unused")
public class ContainerInstanceViewEventListener {

    private final ContainerInstanceViewRepository containerInstanceViewRepository;

    @Autowired
    public ContainerInstanceViewEventListener(ContainerInstanceViewRepository containerInstanceViewRepository) {
        this.containerInstanceViewRepository = containerInstanceViewRepository;
    }

    @EventHandler
    void on(ContainerInstanceInitializedEvent event) {
        ContainerInstanceView view = aContainerInstanceView()
                .withContainerInstanceId(event.getContainerInstanceId())
                .withInitializedAt(event.getEventMetaData().getCreatedAt())
                .withServer(aServerView()
                        .withName(event.getServer().getName())
                        .withIp(event.getServer().getIp())
                        .build())
                .withStatus(INITIALIZED)
                .build();
        containerInstanceViewRepository.save(view);
    }

    @EventHandler
    void on(ContainerInstanceCreatedEvent event) {
        ContainerInstanceView view = getByContainerInstanceId(event);

        view.setCreatedAt(event.getEventMetaData().getCreatedAt());
        view.setStatus(CREATED);
        view.setContainerId(event.getContainerId());

        containerInstanceViewRepository.save(view);
    }

    @EventHandler
    void on(ContainerInstanceCreatedFailedEvent event) {
        ContainerInstanceView view = getByContainerInstanceId(event);

        view.setCreatedAt(event.getEventMetaData().getCreatedAt());
        view.setStatus(CREATED);
        view.setContainerId(event.getContainerId());
        view.addWarnings(event.getErrors());

        containerInstanceViewRepository.save(view);
    }

    @EventHandler
    void on(ContainerInstanceStartedEvent event) {
        ContainerInstanceView view = getByContainerInstanceId(event);

        view.setStartedAt(event.getEventMetaData().getCreatedAt());
        view.setStatus(STARTED);

        containerInstanceViewRepository.save(view);
    }

    @EventHandler
    void on(ContainerInstanceStartedFailedEvent event) {
        ContainerInstanceView view = getByContainerInstanceId(event);

        view.setStartedAt(event.getEventMetaData().getCreatedAt());
        view.setStatus(STARTED);
        view.addWarnings(event.getErrors());

        containerInstanceViewRepository.save(view);
    }

    @EventHandler
    void on(ContainerInstanceStoppedEvent event) {
        ContainerInstanceView view = getByContainerInstanceId(event);

        view.setStoppedAt(event.getEventMetaData().getCreatedAt());
        view.setStopReason(event.getReason());
        view.setStatus(STOPPED);

        containerInstanceViewRepository.save(view);
    }

    @EventHandler
    void on(ContainerInstanceStoppedFailedEvent event) {
        ContainerInstanceView view = getByContainerInstanceId(event);

        view.setStoppedAt(event.getEventMetaData().getCreatedAt());
        view.setStopReason(event.getReason());
        view.setStatus(STOPPED);
        view.addWarnings(event.getErrors());

        containerInstanceViewRepository.save(view);
    }

    @EventHandler
    void on(ContainerInstanceRemovedEvent event) {
        ContainerInstanceView view = getByContainerInstanceId(event);

        view.setRemovedAt(event.getEventMetaData().getCreatedAt());
        view.setRemoveReason(event.getReason());
        view.setStatus(REMOVED);

        containerInstanceViewRepository.save(view);
    }

    @EventHandler()
    void on(ContainerInstanceRemovedFailedEvent event) {
        ContainerInstanceView view = getByContainerInstanceId(event);

        view.setRemovedAt(event.getEventMetaData().getCreatedAt());
        view.setRemoveReason(event.getReason());
        view.setStatus(REMOVED);
        view.addWarnings(event.getErrors());

        containerInstanceViewRepository.save(view);
    }

    @EventHandler
    void on(ContainerInstanceCreationLogReceivedEvent event) {
        ContainerInstanceView view = getByContainerInstanceId(event);
        view.addCreationLog(event.getLog());
        containerInstanceViewRepository.save(view);
    }

    @EventHandler
    void on(ContainerInstanceRunningLogReceivedEvent event) {
        ContainerInstanceView view = getByContainerInstanceId(event);
        view.addRunningLog(event.getLog());
        containerInstanceViewRepository.save(view);
    }

    private ContainerInstanceView getByContainerInstanceId(ContainerInstanceEvent event) {
        return containerInstanceViewRepository.getByContainerInstanceId(event.getContainerInstanceId());
    }
}
