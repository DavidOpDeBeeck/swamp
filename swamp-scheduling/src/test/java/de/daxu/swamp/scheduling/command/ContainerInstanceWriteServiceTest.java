package de.daxu.swamp.scheduling.command;

//@RunWith( MockitoJUnitRunner.class )
public class ContainerInstanceWriteServiceTest {
/*
    @Mock
    private CommandGateway commandGateway;

    @Mock
    private ContainerInstanceCommandFactory containerInstanceCommandFactory;

    @InjectMocks
    private ContainerInstanceWriteService containerInstanceWriteService;

    @Test
    public void schedule() throws Exception {
        when( containerInstanceCommandFactory.createProjectCommand( serverInstanceId, CONTAINER, dateScheduled ) )
                .thenReturn( SCHEDULE_COMMAND );

        containerInstanceWriteService.schedule( CONTAINER );

        verify( containerInstanceCommandFactory ).createProjectCommand( serverInstanceId, CONTAINER, dateScheduled );
        verify( commandGateway ).send( SCHEDULE_COMMAND );
    }

    @Test
    public void action() throws Exception {
        when( containerInstanceCommandFactory.createCreateCommand( CONTAINER_INSTANCE_ID, INTERNAL_CONTAINER_ID, INTERNAL_CONTAINER_NAME ) )
                .thenReturn( CREATE_COMMAND );

        containerInstanceWriteService.action( CONTAINER_INSTANCE_ID, INTERNAL_CONTAINER_ID, INTERNAL_CONTAINER_NAME );

        verify( containerInstanceCommandFactory ).createCreateCommand( CONTAINER_INSTANCE_ID, INTERNAL_CONTAINER_ID, INTERNAL_CONTAINER_NAME );
        verify( commandGateway ).send( CREATE_COMMAND );
    }

    @Test
    public void start() throws Exception {
        when( containerInstanceCommandFactory.startCommand( CONTAINER_INSTANCE_ID ) )
                .thenReturn( START_COMMAND );

        containerInstanceWriteService.start( CONTAINER_INSTANCE_ID );

        verify( containerInstanceCommandFactory ).startCommand( CONTAINER_INSTANCE_ID );
        verify( commandGateway ).send( START_COMMAND );
    }

    @Test
    public void stop() throws Exception {
        when( containerInstanceCommandFactory.stopCommand( CONTAINER_INSTANCE_ID ) )
                .thenReturn( STOP_COMMAND );

        containerInstanceWriteService.stop( CONTAINER_INSTANCE_ID );

        verify( containerInstanceCommandFactory ).stopCommand( CONTAINER_INSTANCE_ID );
        verify( commandGateway ).send( STOP_COMMAND );
    }

    @Test
    public void remove() throws Exception {
        when( containerInstanceCommandFactory.removeCommand( CONTAINER_INSTANCE_ID ) )
                .thenReturn( REMOVE_COMMAND );

        containerInstanceWriteService.remove( CONTAINER_INSTANCE_ID );

        verify( containerInstanceCommandFactory ).removeCommand( CONTAINER_INSTANCE_ID );
        verify( commandGateway ).send( REMOVE_COMMAND );
    }

    @Test
    public void startLogging() throws Exception {
        when( containerInstanceCommandFactory.startLoggingCommand( CONTAINER_INSTANCE_ID, logCallback ) )
                .thenReturn( START_LOGGING_COMMAND );

        containerInstanceWriteService.startLogging( CONTAINER_INSTANCE_ID, logCallback( instanceId ) );

        verify( containerInstanceCommandFactory ).startLoggingCommand( CONTAINER_INSTANCE_ID, logCallback );
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
    */
}