package de.daxu.swamp.common.axon;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE;

@Aspect
@Component
public class AggregateAspect {

    private final AutowireCapableBeanFactory beanFactory;

    @Autowired
    public AggregateAspect(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @AfterReturning(value = "target(org.axonframework.eventsourcing.EventSourcingRepository)", returning = "returnValue")
    public Object autowireDependenciesIntoAggregate(JoinPoint joinPoint, Object returnValue) throws Throwable {
        if (joinPoint.getSignature().getName().equals("load")) {
            beanFactory.autowireBeanProperties(returnValue, AUTOWIRE_BY_TYPE, false);
        }
        return returnValue;
    }
}
