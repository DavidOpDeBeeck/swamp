package de.daxu.swamp.scheduling.config.axon;

import de.daxu.swamp.common.axon.EventListenerClusterSelector;
import de.daxu.swamp.common.axon.LoggingCluster;
import org.axonframework.eventhandling.Cluster;
import org.axonframework.eventhandling.ClusteringEventBus;
import org.axonframework.eventhandling.CompositeClusterSelector;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.replay.BackloggingIncomingMessageHandler;
import org.axonframework.eventhandling.replay.ReplayingCluster;
import org.axonframework.eventstore.jpa.JpaEventStore;
import org.axonframework.unitofwork.SpringTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@Import(EventStoreConfiguration.class)
public class ClusterConfiguration {

    private static final int COMMIT_THRESHOLD = 50;

    private final JpaEventStore eventStore;
    private final SpringTransactionManager transactionManager;

    @Autowired
    public ClusterConfiguration(JpaEventStore eventStore, SpringTransactionManager transactionManager) {
        this.eventStore = eventStore;
        this.transactionManager = transactionManager;
    }

    @Bean
    public EventBus eventBus() {
        return new ClusteringEventBus(
                new CompositeClusterSelector(newArrayList(
                        new EventListenerClusterSelector(replayableCluster(), true),
                        new EventListenerClusterSelector(notReplayableCluster(), false)
                ))
        );
    }

    @Bean
    public Cluster notReplayableCluster() {
        return new LoggingCluster("NotReplayableCluster");
    }

    @Bean
    public Cluster replayableCluster() {
        return new ReplayingCluster(
                new LoggingCluster("ReplayableCluster"),
                eventStore,
                transactionManager,
                COMMIT_THRESHOLD,
                new BackloggingIncomingMessageHandler());
    }
}