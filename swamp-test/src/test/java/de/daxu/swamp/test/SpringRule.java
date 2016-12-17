package de.daxu.swamp.test;

import org.junit.rules.ExternalResource;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.springframework.core.env.AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME;

public class SpringRule extends ExternalResource {

    private ConfigurableApplicationContext applicationContext;

    public static SpringRule spring() {
        return new SpringRule();
    }

    @Override
    protected void before() throws Exception {
        System.setProperty( ACTIVE_PROFILES_PROPERTY_NAME, "test" );
        applicationContext = SpringApplication.run( SwampTestApplication.class );
    }

    @Override
    protected void after() {
        applicationContext.close();
    }

    public String getProperty( String name ) {
        return applicationContext.getEnvironment()
                .getProperty( name );
    }

    public <T> T getInstance( Class<T> beanClass ) {
        return applicationContext.getBean( beanClass );
    }
}
