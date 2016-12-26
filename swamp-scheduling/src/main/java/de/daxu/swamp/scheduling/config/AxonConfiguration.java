package de.daxu.swamp.scheduling.config;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstance;
import de.daxu.swamp.scheduling.command.projectinstance.ProjectInstance;
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
import org.axonframework.common.jpa.ContainerManagedEntityManagerProvider;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventsourcing.EventSourcedAggregateRoot;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.jpa.JpaEventStore;
import org.axonframework.unitofwork.SpringTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

@Configuration
public class AxonConfiguration {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor() {
        AnnotationEventListenerBeanPostProcessor processor = new AnnotationEventListenerBeanPostProcessor();
        processor.setEventBus( eventBus() );
        return processor;
    }

    @Bean
    public AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor() {
        AnnotationCommandHandlerBeanPostProcessor processor = new AnnotationCommandHandlerBeanPostProcessor();
        processor.setCommandBus( commandBus() );
        return processor;
    }

    @Bean
    public CommandBus commandBus() {
        SimpleCommandBus commandBus = new SimpleCommandBus();
        commandBus.setHandlerInterceptors( Collections.singletonList( new BeanValidationInterceptor() ) );
        commandBus.setTransactionManager( new SpringTransactionManager( transactionManager ) );
        return commandBus;
    }

    @Bean
    public CommandGatewayFactoryBean<CommandGateway> commandGatewayFactoryBean() {
        CommandGatewayFactoryBean<CommandGateway> factory = new CommandGatewayFactoryBean<>();
        factory.setCommandBus( commandBus() );
        return factory;
    }

    @Bean
    public EventBus eventBus() {
        return new SimpleEventBus();
    }

    @Bean
    public JpaEventStore eventStore() {
        return new JpaEventStore( entityManagerProvider() );
    }

    @Bean
    public EntityManagerProvider entityManagerProvider() {
        return new ContainerManagedEntityManagerProvider();
    }

    @Bean
    public Set<AggregateAnnotationCommandHandler> commandHandler() {
        return newHashSet(
                createCommandHandler( ContainerInstance.class ),
                createCommandHandler( ProjectInstance.class ),
                createCommandHandler( ServerInstance.class )
        );
    }

    private <T extends EventSourcedAggregateRoot> AggregateAnnotationCommandHandler<T> createCommandHandler( Class<T> clazz ) {
        AggregateAnnotationCommandHandler<T> handler
                = new AggregateAnnotationCommandHandler<>( clazz, repository( clazz ), annotationCommandTargetResolver(), springBeanParameterResolverFactory() );
        handler.supportedCommands().forEach( command -> commandBus().subscribe( command, handler ) );
        return handler;
    }

    public <T extends EventSourcedAggregateRoot> EventSourcingRepository<T> repository( Class<T> clazz ) {
        EventSourcingRepository<T> repository = new EventSourcingRepository<>( clazz, eventStore() );
        repository.setEventBus( eventBus() );
        return repository;
    }

    @Bean
    public ParameterResolverFactory springBeanParameterResolverFactory() {
        return new SpringBeanParameterResolverFactory();
    }

    @Bean
    public AnnotationCommandTargetResolver annotationCommandTargetResolver() {
        return new AnnotationCommandTargetResolver();
    }
}