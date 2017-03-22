package de.daxu.swamp.test.rule;

import de.daxu.swamp.test.SwampTestApplication;
import de.daxu.swamp.test.overrides.BeanOverrides;
import de.daxu.swamp.test.overrides.OverridingBeanFactoryPostProcessor;
import de.daxu.swamp.test.overrides.PropertyOverrides;
import org.junit.rules.ExternalResource;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;


public class SpringRule extends ExternalResource {

    private static Class APPLICATION_CLASS = SwampTestApplication.class;
    private static BeanOverrides DEFAULT_BEAN_OVERRIDES = BeanOverrides.empty();
    private static PropertyOverrides DEFAULT_PROPERTY_OVERRIDES = PropertyOverrides.empty();
    private static String[] DEFAULT_PROFILES = {"test"};

    public static SpringRule spring() {
        return new SpringRule.Builder()
                .withBeanOverrides(DEFAULT_BEAN_OVERRIDES)
                .withPropertyOverrides(DEFAULT_PROPERTY_OVERRIDES)
                .withProfiles(DEFAULT_PROFILES)
                .build();
    }

    public static SpringRule spring(BeanOverrides beanOverrides) {
        return new SpringRule.Builder()
                .withBeanOverrides(beanOverrides)
                .withPropertyOverrides(DEFAULT_PROPERTY_OVERRIDES)
                .withProfiles(DEFAULT_PROFILES)
                .build();
    }

    public static SpringRule spring(PropertyOverrides propertyOverrides) {
        return new SpringRule.Builder()
                .withBeanOverrides(DEFAULT_BEAN_OVERRIDES)
                .withPropertyOverrides(propertyOverrides)
                .withProfiles(DEFAULT_PROFILES)
                .build();
    }

    private final DateRule dateRule = DateRule.now();

    private SpringApplication application;
    private ConfigurableApplicationContext context;

    private SpringRule(BeanOverrides beanOverrides,
                       PropertyOverrides propertyOverrides,
                       String[] profiles) {
        this.application = configure(beanOverrides, propertyOverrides, profiles);
    }

    private SpringApplication configure(BeanOverrides beanOverrides,
                                        PropertyOverrides propertyOverrides,
                                        String[] profiles) {
        SpringApplication application = new SpringApplication(APPLICATION_CLASS);
        application.setAdditionalProfiles(profiles);
        application.setDefaultProperties(propertyOverrides.getPropertyOverrides());
        application.addInitializers(context -> addBeanFactoryPostProcessor(beanOverrides, context));
        return application;
    }

    private void addBeanFactoryPostProcessor(BeanOverrides beanOverrides, ConfigurableApplicationContext context) {
        context.addBeanFactoryPostProcessor(new OverridingBeanFactoryPostProcessor(beanOverrides));
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

        private BeanOverrides beanOverrides = BeanOverrides.empty();
        private PropertyOverrides propertyOverrides = PropertyOverrides.empty();
        private String[] profiles = {"test"};

        Builder withBeanOverrides(BeanOverrides beanOverrides) {
            this.beanOverrides = beanOverrides;
            return this;
        }

        Builder withPropertyOverrides(PropertyOverrides propertyOverrides) {
            this.propertyOverrides = propertyOverrides;
            return this;
        }

        Builder withProfiles(String[] profiles) {
            this.profiles = profiles;
            return this;
        }

        public SpringRule build() {
            return new SpringRule(beanOverrides, propertyOverrides, profiles);
        }
    }
}
