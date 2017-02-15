package de.daxu.swamp.scheduling.config.axon;

import de.daxu.swamp.common.axon.CommandExceptionInterceptor;
import de.daxu.swamp.scheduling.command.build.Build;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstance;
import de.daxu.swamp.scheduling.command.serverinstance.ServerInstance;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.annotation.AnnotationCommandTargetResolver;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.commandhandling.interceptors.BeanValidationInterceptor;
import org.axonframework.common.annotation.ParameterResolverFactory;
import org.axonframework.common.annotation.SpringBeanParameterResolverFactory;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventsourcing.EventSourcedAggregateRoot;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.unitofwork.SpringTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

@Configuration
@Import({EventStoreConfiguration.class, ClusterConfiguration.class})
public class CommandBusConfiguration {

    private final EventBus eventBus;
    private final EventStore eventStore;
    private final SpringTransactionManager springTransactionManager;

    @Autowired
    public CommandBusConfiguration(EventBus eventBus,
                                   EventStore eventStore,
                                   SpringTransactionManager springTransactionManager) {
        this.eventBus = eventBus;
        this.eventStore = eventStore;
        this.springTransactionManager = springTransactionManager;
    }

    @Bean
    public AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor() {
        AnnotationEventListenerBeanPostProcessor processor = new AnnotationEventListenerBeanPostProcessor();
        processor.setEventBus(eventBus);
        return processor;
    }

    @Bean
    public AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor() {
        AnnotationCommandHandlerBeanPostProcessor processor = new AnnotationCommandHandlerBeanPostProcessor();
        processor.setCommandBus(commandBus());
        return processor;
    }

    @Bean
    public CommandBus commandBus() {
        SimpleCommandBus commandBus = new SimpleCommandBus();
        commandBus.setHandlerInterceptors(newArrayList(new BeanValidationInterceptor(), new CommandExceptionInterceptor()));
        commandBus.setTransactionManager(springTransactionManager);
        return commandBus;
    }

    @Bean
    public CommandGatewayFactoryBean<CommandGateway> commandGatewayFactoryBean() {
        CommandGatewayFactoryBean<CommandGateway> factory = new CommandGatewayFactoryBean<>();
        factory.setCommandBus(commandBus());
        return factory;
    }

    @Bean
    public ParameterResolverFactory springBeanParameterResolverFactory() {
        return new SpringBeanParameterResolverFactory();
    }

    @Bean
    public AnnotationCommandTargetResolver annotationCommandTargetResolver() {
        return new AnnotationCommandTargetResolver();
    }

    @Bean
    public Set<AggregateAnnotationCommandHandler> commandHandler() {
        return newHashSet(
                createAggregateCommandHandler(ContainerInstance.class),
                createAggregateCommandHandler(Build.class),
                createAggregateCommandHandler(ServerInstance.class)
        );
    }

    private <T extends EventSourcedAggregateRoot> AggregateAnnotationCommandHandler<T> createAggregateCommandHandler(Class<T> aggregateClass) {
        AggregateAnnotationCommandHandler<T> commandHandler = new AggregateAnnotationCommandHandler<>(
                aggregateClass,
                repository(aggregateClass),
                annotationCommandTargetResolver(),
                springBeanParameterResolverFactory()
        );
        commandHandler.supportedCommands().forEach(command -> commandBus().subscribe(command, commandHandler));
        return commandHandler;
    }

    private <T extends EventSourcedAggregateRoot> EventSourcingRepository<T> repository(Class<T> clazz) {
        EventSourcingRepository<T> repository = new EventSourcingRepository<>(clazz, eventStore);
        repository.setEventBus(eventBus);
        return repository;
    }

}