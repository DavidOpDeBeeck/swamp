package de.daxu.swamp.scheduling.notify.containerinstance;

import de.daxu.swamp.scheduling.read.containerinstance.ContainerInstanceViewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import static de.daxu.swamp.scheduling.ContainerInstanceTestConstants.Events.SCHEDULED_EVENT;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith( MockitoJUnitRunner.class )
public class ContainerInstanceNotifyEventHandlerTest {

    @Mock
    private SimpMessageSendingOperations messagingTemplate;

    @Mock
    private ContainerInstanceViewRepository containerInstanceViewRepository;

    @InjectMocks
    private ContainerInstanceNotifyEventHandler containerInstanceNotifyEventHandler;

    @Test
    public void onContainerInstanceEvent() throws Exception {
        containerInstanceNotifyEventHandler.on( SCHEDULED_EVENT );

        verify( messagingTemplate )
                .convertAndSend( eq( "/topic/container-updates" ), any( ContainerInstanceNotification.class ) );
    }
}