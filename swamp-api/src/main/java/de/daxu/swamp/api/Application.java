package de.daxu.swamp.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan( { "de.daxu.swamp.core" } )
@ComponentScan( basePackages = { "de.daxu.swamp" } )
@EnableJpaRepositories( basePackages = { "de.daxu.swamp" } )
@SpringBootApplication( scanBasePackages = "de.daxu.swamp.api" )
public class Application {

    public static void main( String[] args ) {
        SpringApplication.run( Application.class, args );
    }

}