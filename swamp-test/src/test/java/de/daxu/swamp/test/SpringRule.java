package de.daxu.swamp.test;

import org.junit.rules.ExternalResource;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringRule extends ExternalResource {

    private ConfigurableApplicationContext applicationContext;

    public static SpringRule spring() {
        return new SpringRule();
    }

    @Override
    protected void before() throws Exception {
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

    public <T> T getInstance( Class<T> beanClass ) {
        return applicationContext.getBean( beanClass );
    }
}
