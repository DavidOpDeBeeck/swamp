package de.daxu.swamp.common.axon;

import de.daxu.swamp.common.util.SpringUtils;
import org.axonframework.domain.EventMessage;
import org.axonframework.eventhandling.Cluster;
import org.axonframework.eventhandling.ClusterMetaData;
import org.axonframework.eventhandling.EventListener;
import org.axonframework.eventhandling.EventProcessingMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Set;

public class LoggingCluster implements Cluster {

    private final Logger logger = LoggerFactory.getLogger(LoggingCluster.class);

    private final Cluster delegate;

    public LoggingCluster(Cluster delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public void publish(EventMessage... events) {
        Arrays.stream(events)
                .forEach(event -> logger.info("[{}] replaying event with id : {}", getName(), event.getIdentifier()));
        delegate.publish(events);
    }

    @Override
    public void subscribe(EventListener eventListener) {
        logger.info("[{}] {} subscribed", getName(), SpringUtils.getTargetClassName(eventListener));
        delegate.subscribe(eventListener);
    }

    @Override
    public void unsubscribe(EventListener eventListener) {
        delegate.unsubscribe(eventListener);
    }

    @Override
    public Set<EventListener> getMembers() {
        return delegate.getMembers();
    }

    @Override
    public ClusterMetaData getMetaData() {
        return delegate.getMetaData();
    }

    @Override
    public void subscribeEventProcessingMonitor(EventProcessingMonitor monitor) {
        delegate.subscribeEventProcessingMonitor(monitor);
    }

    @Override
    public void unsubscribeEventProcessingMonitor(EventProcessingMonitor monitor) {
        delegate.unsubscribeEventProcessingMonitor(monitor);
    }
}
