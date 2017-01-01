package de.daxu.swamp;

import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
public class SwampApplication extends SwampConfiguration {

    public static void main( String[] args ) {
        SpringApplication.run( SwampApplication.class, args );
    }

}