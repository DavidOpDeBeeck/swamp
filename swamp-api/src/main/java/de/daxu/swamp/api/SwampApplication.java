package de.daxu.swamp.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EntityScan( { "de.daxu.swamp", "org.axonframework.eventstore.jpa" } )
@EnableJpaRepositories( basePackages = { "de.daxu.swamp" } )
@SpringBootApplication( scanBasePackages = "de.daxu.swamp" )
public class SwampApplication {

    public static void main( String[] args ) {
        SpringApplication.run( SwampApplication.class, args );
    }

}