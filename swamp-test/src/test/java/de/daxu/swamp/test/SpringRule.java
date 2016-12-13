package de.daxu.swamp.test;

import org.junit.rules.ExternalResource;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.MergedContextConfiguration;

public class SpringRule extends ExternalResource {

    private ApplicationContext applicationContext;

    @Override
    protected void before() throws Exception {
        SpringBootContextLoader contextLoader = new SpringBootContextLoader();
        MergedContextConfiguration configuration = new MergedContextConfiguration(
                HibernateRule.class,
                null,
                application(),
                null,
                profiles(), locations(),
                null,
                null,
                contextLoader,
                null,
                null
        );
        applicationContext = contextLoader.loadContext( configuration );
    }

    @Override
    protected void after() {
        ( ( AbstractApplicationContext ) applicationContext ).registerShutdownHook();
    }

    private String[] profiles() {
        return new String[]{ "test" };
    }

    private Class[] application() {
        return new Class[]{ SwampTestApplication.class };
    }

    private String[] locations() {
        return new String[]{ "classpath:test.properties" };
    }

    ApplicationContext context() {
        return applicationContext;
    }
}
