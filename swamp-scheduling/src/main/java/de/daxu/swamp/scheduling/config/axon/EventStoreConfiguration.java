package de.daxu.swamp.scheduling.config.axon;

import org.axonframework.common.jpa.ContainerManagedEntityManagerProvider;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.eventstore.jpa.JpaEventStore;
import org.axonframework.unitofwork.SpringTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class EventStoreConfiguration {

    private final PlatformTransactionManager transactionManager;

    @Autowired
    public EventStoreConfiguration(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Bean
    public JpaEventStore eventStore() {
        return new JpaEventStore(entityManagerProvider());
    }

    @Bean
    public EntityManagerProvider entityManagerProvider() {
        return new ContainerManagedEntityManagerProvider();
    }

    @Bean
    public SpringTransactionManager springTransactionManager() {
        return new SpringTransactionManager(transactionManager);
    }
}