package de.daxu.swamp.scheduling.write.containerinstance;

import de.daxu.swamp.scheduling.write.containerinstance.command.CreateContainerInstanceCommand;
import de.daxu.swamp.scheduling.write.containerinstance.command.StartContainerInstanceCommand;
import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceCreatedEvent;
import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceStartedEvent;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class ContainerInstanceTest {

    private FixtureConfiguration fixture;

    private static final ContainerInstanceId CONTAINER_INSTANCE_ID = ContainerInstanceId.random();
    private static final String NAME = "centos";
    private static final Date DATE = new Date();

    @Before
    public void setUp() {
        fixture = Fixtures.newGivenWhenThenFixture( ContainerInstance.class );
    }

    @Test
    public void onCreate() {
        fixture.given()
                .when( new CreateContainerInstanceCommand( CONTAINER_INSTANCE_ID, NAME ) )
                .expectEvents( new ContainerInstanceCreatedEvent( CONTAINER_INSTANCE_ID, NAME ) );
    }

    @Test
    public void onStart() {
        fixture.given( new ContainerInstanceCreatedEvent( CONTAINER_INSTANCE_ID, NAME ) )
                .when( new StartContainerInstanceCommand( CONTAINER_INSTANCE_ID, DATE ) )
                .expectEvents( new ContainerInstanceStartedEvent( CONTAINER_INSTANCE_ID, DATE ) );
    }
}