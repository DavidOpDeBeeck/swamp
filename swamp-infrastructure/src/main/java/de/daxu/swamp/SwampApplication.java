package de.daxu.swamp;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
public class SwampApplication extends SwampConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(SwampApplication.class, args);
    }

}