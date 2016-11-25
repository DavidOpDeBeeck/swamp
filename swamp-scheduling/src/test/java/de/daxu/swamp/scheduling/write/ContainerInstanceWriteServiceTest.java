package de.daxu.swamp.scheduling.write;

import de.daxu.swamp.scheduling.write.containerinstance.command.ContainerInstanceCommandFactory;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static de.daxu.swamp.scheduling.ContainerInstanceTestConstants.*;
import static de.daxu.swamp.scheduling.ContainerInstanceTestConstants.Commands.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith( MockitoJUnitRunner.class )
public class ContainerInstanceWriteServiceTest {

    @Mock
    private CommandGateway commandGateway;

    @Mock
    private ContainerInstanceCommandFactory containerInstanceCommandFactory;

    @InjectMocks
    private ContainerInstanceWriteService containerInstanceWriteService;

    @Test
    public void scheduleContainerInstance() throws Exception {
        when( containerInstanceCommandFactory.createScheduleCommand( CONTAINER, SERVER ) )
                .thenReturn( SCHEDULE_COMMAND );

        containerInstanceWriteService.scheduleContainerInstance( CONTAINER, SERVER );

        verify( containerInstanceCommandFactory ).createScheduleCommand( CONTAINER, SERVER );
        verify( commandGateway ).send( SCHEDULE_COMMAND );
    }

    @Test
    public void createContainerInstance() throws Exception {
        when( containerInstanceCommandFactory.createCreateCommand( CONTAINER_INSTANCE_ID, INTERNAL_CONTAINER_ID, INTERNAL_CONTAINER_NAME ) )
                .thenReturn( CREATE_COMMAND );

        containerInstanceWriteService.createContainerInstance( CONTAINER_INSTANCE_ID, INTERNAL_CONTAINER_ID, INTERNAL_CONTAINER_NAME );

        verify( containerInstanceCommandFactory ).createCreateCommand( CONTAINER_INSTANCE_ID, INTERNAL_CONTAINER_ID, INTERNAL_CONTAINER_NAME );
        verify( commandGateway ).send( CREATE_COMMAND );
    }

    @Test
    public void startContainerInstance() throws Exception {
        when( containerInstanceCommandFactory.createStartCommand( CONTAINER_INSTANCE_ID ) )
                .thenReturn( START_COMMAND );

        containerInstanceWriteService.startContainerInstance( CONTAINER_INSTANCE_ID );

        verify( containerInstanceCommandFactory ).createStartCommand( CONTAINER_INSTANCE_ID );
        verify( commandGateway ).send( START_COMMAND );
    }
}