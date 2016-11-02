package de.daxu.swamp.scheduling.config;

import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstance;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.commandhandling.interceptors.BeanValidationInterceptor;
import org.axonframework.common.jpa.ContainerManagedEntityManagerProvider;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.jpa.JpaEventStore;
import org.axonframework.unitofwork.SpringTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;

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
    public EventBus eventBus() {
        return new SimpleEventBus();
    }

    @Bean
    public CommandGatewayFactoryBean<CommandGateway> commandGatewayFactoryBean() {
        CommandGatewayFactoryBean<CommandGateway> factory = new CommandGatewayFactoryBean<>();
        factory.setCommandBus( commandBus() );
        return factory;
    }

    @Bean
    public EventSourcingRepository<ContainerInstance> containerInstanceRepository() {
        JpaEventStore eventStore = new JpaEventStore( entityManagerProvider() );
        EventSourcingRepository<ContainerInstance> repository = new EventSourcingRepository<>( ContainerInstance.class, eventStore );
        repository.setEventBus( eventBus() );
        return repository;
    }

    @Bean
    public EntityManagerProvider entityManagerProvider() {
        return new ContainerManagedEntityManagerProvider();
    }

    @Bean
    public AggregateAnnotationCommandHandler containerInstanceCommandHandler() {
        return AggregateAnnotationCommandHandler.subscribe( ContainerInstance.class, containerInstanceRepository(), commandBus() );
    }
}