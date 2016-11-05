package de.daxu.swamp.scheduling.write.containerinstance;

import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.scheduling.write.containerinstance.command.CreateContainerInstanceCommand;
import de.daxu.swamp.scheduling.write.containerinstance.command.StartContainerInstanceCommand;
import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceCreatedEvent;
import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceStartedEvent;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static de.daxu.swamp.core.location.Server.ServerBuilder.aServer;

public class ContainerInstanceTest {

    private FixtureConfiguration fixture;

    private static final ContainerInstanceId CONTAINER_INSTANCE_ID = ContainerInstanceId.random();
    private static final String INTERNAL_CONTAINER_NAME = "name";
    private static final String INTERNAL_CONTAINER_ID = "id";
    private static final Date DATE = new Date();
    private static final Server SERVER = aServer().build();

    @Before
    public void setUp() {
        fixture = Fixtures.newGivenWhenThenFixture( ContainerInstance.class );
    }

    @Test
    public void onCreate() {
        fixture.given()
                .when( new CreateContainerInstanceCommand( CONTAINER_INSTANCE_ID, INTERNAL_CONTAINER_ID, INTERNAL_CONTAINER_NAME, DATE, SERVER ) )
                .expectEvents( new ContainerInstanceCreatedEvent( CONTAINER_INSTANCE_ID, INTERNAL_CONTAINER_ID, INTERNAL_CONTAINER_NAME, DATE, SERVER ) );
    }

    @Test
    public void onStart() {
        fixture.given( new ContainerInstanceCreatedEvent( CONTAINER_INSTANCE_ID, INTERNAL_CONTAINER_ID, INTERNAL_CONTAINER_NAME, DATE, SERVER ) )
                .when( new StartContainerInstanceCommand( CONTAINER_INSTANCE_ID, DATE ) )
                .expectEvents( new ContainerInstanceStartedEvent( CONTAINER_INSTANCE_ID, DATE ) );
    }
}