package de.daxu.swamp.scheduling;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.EnvironmentVariable;
import de.daxu.swamp.core.container.PortMapping;
import de.daxu.swamp.core.container.configuration.RunConfiguration;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.write.containerinstance.command.*;
import de.daxu.swamp.scheduling.write.containerinstance.event.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static de.daxu.swamp.core.container.Container.ContainerBuilder.aContainer;
import static de.daxu.swamp.core.container.configuration.ImageConfiguration.ImageConfigurationBuilder.anImageConfiguration;
import static de.daxu.swamp.core.location.Server.ServerBuilder.aServer;
import static de.daxu.swamp.scheduling.ContainerInstanceTestConstants.Utils.DATE;

public class ContainerInstanceTestConstants {

    public static final ContainerInstanceId CONTAINER_INSTANCE_ID = ContainerInstanceId.random();
    public static final String INTERNAL_CONTAINER_NAME = "name";
    public static final String INTERNAL_CONTAINER_ID = "id";
    public static final String LOG = "log";

    public static final String NAME = "name";
    public static final RunConfiguration IMAGE_CONFIGURATION = anImageConfiguration().build();
    public static final Set<PortMapping> PORT_MAPPINGS = new HashSet<>();
    public static final Set<EnvironmentVariable> ENVIRONMENT_VARIABLES = new HashSet<>();

    public static final Container CONTAINER = aContainer()
            .withName( NAME )
            .withRunConfiguration( IMAGE_CONFIGURATION )
            .withPortMappings( PORT_MAPPINGS )
            .withEnvironmentVariables( ENVIRONMENT_VARIABLES )
            .build();
    public static final Server SERVER = aServer().build();

    public static class Commands {

        public static final ScheduleContainerInstanceCommand SCHEDULE_COMMAND = new ScheduleContainerInstanceCommand( CONTAINER_INSTANCE_ID, CONTAINER, SERVER, DATE );
        public static final CreateContainerInstanceCommand CREATE_COMMAND = new CreateContainerInstanceCommand( CONTAINER_INSTANCE_ID, INTERNAL_CONTAINER_ID, INTERNAL_CONTAINER_NAME, DATE );
        public static final StartContainerInstanceCommand START_COMMAND = new StartContainerInstanceCommand( CONTAINER_INSTANCE_ID, DATE );
        public static final StartContainerInstanceLoggingCommand START_LOGGING_COMMAND = new StartContainerInstanceLoggingCommand( CONTAINER_INSTANCE_ID, DATE );
        public static final ReceiveContainerInstanceLogCommand RECEIVE_LOG_COMMAND = new ReceiveContainerInstanceLogCommand( CONTAINER_INSTANCE_ID, LOG, DATE );
    }

    public static class Events {

        public static final ContainerInstanceScheduledEvent SCHEDULED_EVENT = new ContainerInstanceScheduledEvent( CONTAINER_INSTANCE_ID, NAME, IMAGE_CONFIGURATION, PORT_MAPPINGS, ENVIRONMENT_VARIABLES, SERVER, DATE );
        public static final ContainerInstanceCreatedEvent CREATED_EVENT = new ContainerInstanceCreatedEvent( CONTAINER_INSTANCE_ID, INTERNAL_CONTAINER_ID, INTERNAL_CONTAINER_NAME, DATE );
        public static final ContainerInstanceStartedEvent STARTED_EVENT = new ContainerInstanceStartedEvent( CONTAINER_INSTANCE_ID, DATE );
        public static final ContainerInstanceLoggingStartedEvent LOGGING_STARTED_EVENT = new ContainerInstanceLoggingStartedEvent( CONTAINER_INSTANCE_ID, DATE );
        public static final ContainerInstanceLogReceivedEvent LOG_RECEIVED_EVENT = new ContainerInstanceLogReceivedEvent( CONTAINER_INSTANCE_ID, LOG, DATE );
    }

    public static class Utils {

        public static final Date DATE = new Date();
    }
}
