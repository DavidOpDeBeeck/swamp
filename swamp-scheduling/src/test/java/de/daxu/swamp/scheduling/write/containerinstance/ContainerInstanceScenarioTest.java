package de.daxu.swamp.scheduling.write.containerinstance;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import static de.daxu.swamp.scheduling.ContainerInstanceTestConstants.Commands.*;
import static de.daxu.swamp.scheduling.ContainerInstanceTestConstants.Events.*;

public class ContainerInstanceScenarioTest {

    private FixtureConfiguration fixture;

    @Before
    public void setUp() {
        fixture = Fixtures.newGivenWhenThenFixture( ContainerInstance.class );
    }

    @Test
    public void onSchedule() {
        fixture.given()
                .when( SCHEDULE_COMMAND )
                .expectEvents( SCHEDULED_EVENT );
    }

    @Test
    public void onCreate() {
        fixture.given( SCHEDULED_EVENT )
                .when( CREATE_COMMAND )
                .expectEvents( CREATED_EVENT );
    }

    @Test
    public void onStart() {
        fixture.given( SCHEDULED_EVENT, CREATED_EVENT )
                .when( START_COMMAND )
                .expectEvents( STARTED_EVENT );
    }

    @Test
    public void onStartLogging() {
        fixture.given( SCHEDULED_EVENT, CREATED_EVENT, START_COMMAND )
                .when( START_LOGGING_COMMAND )
                .expectEvents( LOGGING_STARTED_EVENT );
    }

    @Test
    public void onLogReceived() {
        fixture.given( SCHEDULED_EVENT, CREATED_EVENT, START_COMMAND, START_LOGGING_COMMAND )
                .when( RECEIVE_LOG_COMMAND )
                .expectEvents( LOG_RECEIVED_EVENT );
    }
}