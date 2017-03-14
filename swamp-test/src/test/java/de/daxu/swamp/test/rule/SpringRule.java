package de.daxu.swamp.test.rule;

import de.daxu.swamp.test.SwampTestApplication;
import de.daxu.swamp.test.bean.OverridingBeanFactoryPostProcessor;
import org.junit.rules.ExternalResource;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class SpringRule extends ExternalResource {

    private static Class SPRING_APPLICATION_CLASS = SwampTestApplication.class;

    public static SpringRule spring() {
        return new SpringRule.Builder().build();
    }

    public static SpringRule spring(Map<String, Object> beanOverrides) {
        return new SpringRule.Builder()
                .withBeanOverrides(beanOverrides).build();
    }

    public static SpringRule spring(Map<String, Object> beanOverrides,
                                    Map<String, Object> propertyOverrides) {
        return new SpringRule.Builder()
                .withBeanOverrides(beanOverrides)
                .withPropertyOverrides(propertyOverrides).build();
    }

    private SpringApplication application;
    private ConfigurableApplicationContext context;
    private final DateRule dateRule = DateRule.now();

    private SpringRule(Map<String, Object> beanOverrides,
                       Map<String, Object> propertyOverrides,
                       String[] profiles) {
        this.application = configure(beanOverrides, propertyOverrides, profiles);
    }

    private SpringApplication configure(Map<String, Object> beanOverrides,
                                        Map<String, Object> propertyOverrides,
                                        String[] profiles) {
        SpringApplication application = new SpringApplication(SPRING_APPLICATION_CLASS);
        application.setAdditionalProfiles(profiles);
        application.setDefaultProperties(propertyOverrides);
        application.addInitializers(context -> context.addBeanFactoryPostProcessor(new OverridingBeanFactoryPostProcessor(beanOverrides)));
        return application;
    }

    @Override
    protected void before() throws Exception {
        dateRule.before();
        context = application.run();
    }

    @Override
    protected void after() {
        context.close();
    }

    public String getProperty(String name) {
        return context.getEnvironment()
                .getProperty(name);
    }

    public <T> T getInstance(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    public static class Builder {

        private Map<String, Object> beanOverrides = new HashMap<>();
        private Map<String, Object> propertyOverrides = new HashMap<>();
        private String[] profiles = {"test"};

        public static SpringRule.Builder springRule() {
            return new SpringRule.Builder();
        }

        public Builder withBeanOverrides(Map<String, Object> beanOverrides) {
            this.beanOverrides = beanOverrides;
            return this;
        }

        public Builder withPropertyOverrides(Map<String, Object> propertyOverrides) {
            this.propertyOverrides = propertyOverrides;
            return this;
        }

        public Builder withProfiles(String[] profiles) {
            this.profiles = profiles;
            return this;
        }

        public SpringRule build() {
            return new SpringRule(beanOverrides, propertyOverrides, profiles);
        }
    }
}
