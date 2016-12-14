package de.daxu.swamp.test;

import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringRule extends ExternalResource {

    private Logger logger = LoggerFactory.getLogger( SpringRule.class );
    private ConfigurableApplicationContext applicationContext;

    public static SpringRule spring() {
        return new SpringRule();
    }

    @Override
    protected void before() throws Exception {
        logger.debug( "Starting SpringApplication" );
        applicationContext = createSpringApplication().run();
    }

    @Override
    protected void after() {
        applicationContext.close();
    }

    private SpringApplication createSpringApplication() {
        SpringApplication application = new SpringApplication( locations() );
        application.setAdditionalProfiles( profiles() );
        return application;
    }

    private String[] locations() {
        return new String[]{ "classpath:applicationContext.xml" };
    }

    private String[] profiles() {
        return new String[]{ "test" };
    }

    ApplicationContext context() {
        return applicationContext;
    }
}
