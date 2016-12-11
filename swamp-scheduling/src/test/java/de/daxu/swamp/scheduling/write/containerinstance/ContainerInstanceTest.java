package de.daxu.swamp.scheduling.write.containerinstance;

import org.junit.Before;
import org.junit.Test;

import static de.daxu.swamp.scheduling.ContainerInstanceTestConstants.CONTAINER_INSTANCE_ID;
import static de.daxu.swamp.scheduling.ContainerInstanceTestConstants.Events.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ContainerInstanceTest {

    private ContainerInstance containerInstance;

    @Before
    public void init() {
        this.containerInstance = new ContainerInstance();
    }

    @Test
    public void onScheduledEvent() throws Exception {
        containerInstance.on( SCHEDULED_EVENT );

        assertThat( containerInstance.getContainerInstanceId() ).isEqualTo( CONTAINER_INSTANCE_ID );
        assertThat( containerInstance.getStatus() ).isEqualTo( ContainerInstanceStatus.INITIALIZED );
    }

    @Test
    public void onCreatedEvent() throws Exception {
        containerInstance.on( CREATED_EVENT );

        assertThat( containerInstance.getStatus() ).isEqualTo( ContainerInstanceStatus.CREATED );
    }

    @Test
    public void onStartedEvent() throws Exception {
        containerInstance.on( STARTED_EVENT );

        assertThat( containerInstance.getStatus() ).isEqualTo( ContainerInstanceStatus.STARTED );
    }
}