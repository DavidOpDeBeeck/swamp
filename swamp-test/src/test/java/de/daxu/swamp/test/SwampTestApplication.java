package de.daxu.swamp.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootTest
@EntityScan( { "de.daxu.swamp", "org.axonframework.eventstore.jpa" } )
@EnableJpaRepositories( basePackages = { "de.daxu.swamp" } )
@SpringBootApplication( scanBasePackages = "de.daxu.swamp" )
public class SwampTestApplication {

    public static void main( String[] args ) {
        SpringApplication.run( SwampTestApplication.class, args );
    }

}