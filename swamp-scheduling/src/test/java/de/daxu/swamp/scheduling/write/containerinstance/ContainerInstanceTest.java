package de.daxu.swamp.scheduling.write.containerinstance;

import de.daxu.swamp.scheduling.write.containerinstance.command.CreateContainerInstanceCommand;
import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceCreated;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

public class ContainerInstanceTest {

    private FixtureConfiguration fixture;

    private static final ContainerInstanceId CONTAINER_INSTANCE_ID = ContainerInstanceId.random();
    private static final String NAME = "centos";

    @Before
    public void setUp() {
        fixture = Fixtures.newGivenWhenThenFixture( ContainerInstance.class );
    }

    @Test
    public void onCreate() {
        fixture.given()
                .when( new CreateContainerInstanceCommand( CONTAINER_INSTANCE_ID, NAME ) )
                .expectEvents( new ContainerInstanceCreated( CONTAINER_INSTANCE_ID, NAME ) );
    }
}