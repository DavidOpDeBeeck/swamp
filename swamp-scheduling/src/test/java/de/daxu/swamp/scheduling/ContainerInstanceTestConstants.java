package de.daxu.swamp.scheduling;

public class ContainerInstanceTestConstants {
/*
    public static final ContainerInstanceId CONTAINER_INSTANCE_ID = ContainerInstanceId.random();
    public static final ContainerInstanceId ANOTHER_CONTAINER_INSTANCE_ID = ContainerInstanceId.random();

    public static final String INTERNAL_CONTAINER_NAME = "name";
    public static final String INTERNAL_CONTAINER_ID = "id";
    public static final String LOG = "log";

    public static final ProjectInstanceView CONTAINER_INSTANCE_VIEW = aContainerInstanceView()
            .withContainerInstanceId( CONTAINER_INSTANCE_ID )
            .withStatus( ContainerInstanceStatus.STARTED )
            .build();
    public static final ProjectInstanceView ANOTHER_CONTAINER_INSTANCE_VIEW = aContainerInstanceView()
            .withContainerInstanceId( ANOTHER_CONTAINER_INSTANCE_ID )
            .withStatus( ContainerInstanceStatus.STARTED )
            .build();

    public static final String NAME = "name";
    public static final RunConfiguration IMAGE_CONFIGURATION = anImageConfigurationBuilder().build();
    public static final Set<PortMapping> PORT_MAPPINGS = new HashSet<>();

    public static final Set<EnvironmentVariable> ENVIRONMENT_VARIABLES = new HashSet<>();

    public static final Container CONTAINER = aContainerBuilder()
            .withName( NAME )
            .withRunConfiguration( IMAGE_CONFIGURATION )
            .withPortMappings( PORT_MAPPINGS )
            .withEnvironmentVariables( ENVIRONMENT_VARIABLES )
            .build();
    public static final Server SERVER = aServerBuilder().build();

    public static class Commands {

        public static final InitializeContainerInstanceCommand SCHEDULE_COMMAND = new InitializeContainerInstanceCommand( CONTAINER_INSTANCE_ID, CONTAINER, SERVER, DATE );
        public static final CreateContainerInstanceCommand CREATE_COMMAND = new CreateContainerInstanceCommand( CONTAINER_INSTANCE_ID, INTERNAL_CONTAINER_ID, INTERNAL_CONTAINER_NAME, DATE );
        public static final StartContainerInstanceCommand START_COMMAND = new StartContainerInstanceCommand( CONTAINER_INSTANCE_ID, DATE );
        public static final StartContainerInstanceLoggingCommand START_LOGGING_COMMAND = new StartContainerInstanceLoggingCommand( CONTAINER_INSTANCE_ID );
        public static final ReceiveContainerInstanceLogCommand RECEIVE_LOG_COMMAND = new ReceiveContainerInstanceLogCommand( CONTAINER_INSTANCE_ID, LOG, DATE );
        public static final StopContainerInstanceCommand STOP_COMMAND = new StopContainerInstanceCommand( CONTAINER_INSTANCE_ID, DATE );
        public static final RemoveContainerInstanceCommand REMOVE_COMMAND = new RemoveContainerInstanceCommand( CONTAINER_INSTANCE_ID, DATE );
    }

    public static class Events {

        public static final ContainerInstanceInitializedEvent SCHEDULED_EVENT = new ContainerInstanceInitializedEvent( CONTAINER_INSTANCE_ID, NAME, IMAGE_CONFIGURATION, PORT_MAPPINGS, ENVIRONMENT_VARIABLES, SERVER, DATE, configuration );
        public static final ContainerInstanceCreatedEvent CREATED_EVENT = new ContainerInstanceCreatedEvent( CONTAINER_INSTANCE_ID, INTERNAL_CONTAINER_ID, INTERNAL_CONTAINER_NAME, DATE );
        public static final ContainerInstanceStartedEvent STARTED_EVENT = new ContainerInstanceStartedEvent( CONTAINER_INSTANCE_ID, DATE );
        public static final ContainerInstanceLoggingStartedEvent LOGGING_STARTED_EVENT = new ContainerInstanceLoggingStartedEvent( CONTAINER_INSTANCE_ID, DATE );
        public static final ContainerInstanceLogReceivedEvent LOG_RECEIVED_EVENT = new ContainerInstanceLogReceivedEvent( CONTAINER_INSTANCE_ID, LOG, DATE );
        public static final ContainerInstanceStoppedEvent STOPPED_EVENT = new ContainerInstanceStoppedEvent( CONTAINER_INSTANCE_ID, DATE );
        public static final ContainerInstanceRemovedEvent REMOVED_EVENT = new ContainerInstanceRemovedEvent( CONTAINER_INSTANCE_ID, DATE );
    }

    public static class Utils {

        public static final Date DATE = new Date();
    }
    */
}
