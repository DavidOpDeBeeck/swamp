package de.daxu.swamp.common.axon;

import org.axonframework.eventhandling.AbstractClusterSelector;
import org.axonframework.eventhandling.Cluster;
import org.axonframework.eventhandling.EventListener;

import static org.springframework.core.annotation.AnnotatedElementUtils.isAnnotated;
import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

public class EventListenerClusterSelector extends AbstractClusterSelector {

    private static final Class<de.daxu.swamp.common.axon.EventListener> EVENT_LISTENER_CLASS
            = de.daxu.swamp.common.axon.EventListener.class;

    private final Cluster cluster;
    private final boolean replayable;

    public EventListenerClusterSelector(Cluster cluster, boolean replayable) {
        this.cluster = cluster;
        this.replayable = replayable;
    }

    @Override
    protected Cluster doSelectCluster(EventListener eventListener, Class<?> listenerType) {
        return annotationPresent(listenerType)
                && (replayable == isReplayable(listenerType)) ? cluster : null;
    }

    private boolean isReplayable(Class<?> listenerType) {
        return findAnnotation(listenerType, EVENT_LISTENER_CLASS).replayable();
    }

    private boolean annotationPresent(Class<?> listenerType) {
        return annotationPresentOnClass(listenerType) || annotationPresentOnSuperClass(listenerType);
    }

    private boolean annotationPresentOnClass(Class<?> listenerType) {
        return listenerType.isAnnotationPresent(EVENT_LISTENER_CLASS) || isAnnotated(listenerType, EVENT_LISTENER_CLASS);
    }

    private boolean annotationPresentOnSuperClass(Class<?> listenerType) {
        return listenerType.getSuperclass() != null && annotationPresent(listenerType.getSuperclass());
    }
}
