package de.daxu.swamp.scheduling.view.containerinstance;

import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceCreatedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.scheduling.view.containerinstance.ContainerInstanceView.ContainerInstanceViewBuilder.aContainerInstanceView;

@Component
public class ContainerInstanceViewEventHandler {

    @Autowired
    private ContainerInstanceViewRepository containerInstanceViewRepository;

    @EventHandler
    void on( ContainerInstanceCreatedEvent event ) {
        ContainerInstanceView view = aContainerInstanceView()
                .withContainerInstanceId( event.getContainerInstanceId() )
                .withName( event.getName() )
                .build();
        containerInstanceViewRepository.save( view );
    }
}
