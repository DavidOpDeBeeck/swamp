package de.daxu.swamp.scheduling.view;

import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceCreated;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.scheduling.view.ContainerInstanceView.ContainerInstanceViewBuilder.aContainerInstanceView;

@Component
public class ContainerInstanceViewEventHandler {

    @Autowired
    private ContainerInstanceViewRepository containerInstanceViewRepository;

    @EventHandler
    void on( ContainerInstanceCreated event ) {
        ContainerInstanceView view = aContainerInstanceView()
                .withContainerInstanceId( event.getAggregateId() )
                .withName( event.getName() )
                .build();
        containerInstanceViewRepository.save( view );
    }
}
