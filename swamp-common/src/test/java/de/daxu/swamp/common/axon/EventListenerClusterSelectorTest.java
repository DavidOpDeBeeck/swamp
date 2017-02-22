package de.daxu.swamp.common.axon;

import org.apache.commons.lang.NotImplementedException;
import org.axonframework.domain.EventMessage;
import org.axonframework.eventhandling.Cluster;
import org.axonframework.eventhandling.ClusterSelector;
import org.axonframework.eventhandling.SimpleCluster;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventListenerClusterSelectorTest {

    private static final SimpleCluster CLUSTER = new SimpleCluster("cluster");

    @Test
    public void givenReplayableEventListenerClusterSelector_whenAnnotationPresentAndReplayable_ReturnsCluster() throws Exception {
        ClusterSelector selector = new EventListenerClusterSelector(CLUSTER, true);

        Cluster cluster = selector.selectCluster(new ReplayableEventListener());

        assertThat(cluster).isEqualTo(CLUSTER);
    }

    @Test
    public void givenReplayableEventListenerClusterSelector_whenAnnotationNotPresent_ReturnsNull() throws Exception {
        ClusterSelector selector = new EventListenerClusterSelector(CLUSTER, true);

        Cluster cluster = selector.selectCluster(new TestEventListener());

        assertThat(cluster).isNull();
    }

    @Test
    public void givenReplayableEventListenerClusterSelector_whenAnnotationPresentAndNotReplayable_ReturnsNull() throws Exception {
        ClusterSelector selector = new EventListenerClusterSelector(CLUSTER, true);

        Cluster cluster = selector.selectCluster(new NotReplayableEventListener());

        assertThat(cluster).isNull();
    }

    @Test
    public void givenNotReplayableEventListenerClusterSelector_whenAnnotationPresentAndReplayable_ReturnsNull() throws Exception {
        ClusterSelector selector = new EventListenerClusterSelector(CLUSTER, false);

        Cluster cluster = selector.selectCluster(new ReplayableEventListener());

        assertThat(cluster).isNull();
    }

    @Test
    public void givenNotReplayableEventListenerClusterSelector_whenAnnotationNotPresent_ReturnsNull() throws Exception {
        ClusterSelector selector = new EventListenerClusterSelector(CLUSTER, false);

        Cluster cluster = selector.selectCluster(new TestEventListener());

        assertThat(cluster).isNull();
    }

    @Test
    public void givenNotReplayableEventListenerClusterSelector_whenAnnotationPresentAndNotReplayable_ReturnsCluster() throws Exception {
        ClusterSelector selector = new EventListenerClusterSelector(CLUSTER, false);

        Cluster cluster = selector.selectCluster(new NotReplayableEventListener());

        assertThat(cluster).isEqualTo(CLUSTER);
    }

    @EventListener
    private static class ReplayableEventListener extends TestEventListener {}

    @EventListener(replayable = false)
    private static class NotReplayableEventListener extends TestEventListener {}

    private static class TestEventListener implements org.axonframework.eventhandling.EventListener {

        @Override
        public void handle(EventMessage event) {
            throw new NotImplementedException();
        }
    }
}