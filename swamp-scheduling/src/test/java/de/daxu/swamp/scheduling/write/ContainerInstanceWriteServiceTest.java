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
    public void schedule() throws Exception {
        when( containerInstanceCommandFactory.createInitializeCommand( serverInstanceId, CONTAINER, dateScheduled ) )
                .thenReturn( SCHEDULE_COMMAND );

        containerInstanceWriteService.schedule( CONTAINER );

        verify( containerInstanceCommandFactory ).createInitializeCommand( serverInstanceId, CONTAINER, dateScheduled );
        verify( commandGateway ).send( SCHEDULE_COMMAND );
    }

    @Test
    public void create() throws Exception {
        when( containerInstanceCommandFactory.createCreateCommand( CONTAINER_INSTANCE_ID, INTERNAL_CONTAINER_ID, INTERNAL_CONTAINER_NAME ) )
                .thenReturn( CREATE_COMMAND );

        containerInstanceWriteService.create( CONTAINER_INSTANCE_ID, INTERNAL_CONTAINER_ID, INTERNAL_CONTAINER_NAME );

        verify( containerInstanceCommandFactory ).createCreateCommand( CONTAINER_INSTANCE_ID, INTERNAL_CONTAINER_ID, INTERNAL_CONTAINER_NAME );
        verify( commandGateway ).send( CREATE_COMMAND );
    }

    @Test
    public void start() throws Exception {
        when( containerInstanceCommandFactory.createStartCommand( CONTAINER_INSTANCE_ID ) )
                .thenReturn( START_COMMAND );

        containerInstanceWriteService.start( CONTAINER_INSTANCE_ID );

        verify( containerInstanceCommandFactory ).createStartCommand( CONTAINER_INSTANCE_ID );
        verify( commandGateway ).send( START_COMMAND );
    }

    @Test
    public void stop() throws Exception {
        when( containerInstanceCommandFactory.createStopCommand( CONTAINER_INSTANCE_ID ) )
                .thenReturn( STOP_COMMAND );

        containerInstanceWriteService.stop( CONTAINER_INSTANCE_ID );

        verify( containerInstanceCommandFactory ).createStopCommand( CONTAINER_INSTANCE_ID );
        verify( commandGateway ).send( STOP_COMMAND );
    }

    @Test
    public void remove() throws Exception {
        when( containerInstanceCommandFactory.createRemoveCommand( CONTAINER_INSTANCE_ID ) )
                .thenReturn( REMOVE_COMMAND );

        containerInstanceWriteService.remove( CONTAINER_INSTANCE_ID );

        verify( containerInstanceCommandFactory ).createRemoveCommand( CONTAINER_INSTANCE_ID );
        verify( commandGateway ).send( REMOVE_COMMAND );
    }

    @Test
    public void startLogging() throws Exception {
        when( containerInstanceCommandFactory.createStartLoggingCommand( CONTAINER_INSTANCE_ID, logCallback ) )
                .thenReturn( START_LOGGING_COMMAND );

        containerInstanceWriteService.startLogging( CONTAINER_INSTANCE_ID, logCallback( instanceId ) );

        verify( containerInstanceCommandFactory ).createStartLoggingCommand( CONTAINER_INSTANCE_ID, logCallback );
        verify( commandGateway ).send( START_LOGGING_COMMAND );
    }

    @Test
    public void logReceived() throws Exception {
        when( containerInstanceCommandFactory.createReceiveLogCommand( CONTAINER_INSTANCE_ID, LOG ) )
                .thenReturn( RECEIVE_LOG_COMMAND );

        containerInstanceWriteService.receiveLog( CONTAINER_INSTANCE_ID, LOG );

        verify( containerInstanceCommandFactory ).createReceiveLogCommand( CONTAINER_INSTANCE_ID, LOG );
        verify( commandGateway ).send( RECEIVE_LOG_COMMAND );
    }
}