package de.daxu.swamp.test.overrides;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class OverridingBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private final BeanOverrides beanOverrides;

    public OverridingBeanFactoryPostProcessor(BeanOverrides beanOverrides) {
        this.beanOverrides = beanOverrides;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        beanFactory.addBeanPostProcessor(new OverridingBeanPostProcessor(beanOverrides));
    }

    private static class OverridingBeanPostProcessor implements BeanPostProcessor {

        private final Logger logger = LoggerFactory.getLogger(OverridingBeanPostProcessor.class);
        private final BeanOverrides beanOverrides;

        private OverridingBeanPostProcessor(BeanOverrides beanOverrides) {
            this.beanOverrides = beanOverrides;
        }

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            if (beanOverrides.containsBean(beanName)) {
                logger.debug("Overriding bean {} with class {}", beanName, bean.getClass().getCanonicalName());
            }
            return beanOverrides.getOrDefault(beanName, bean);
        }
    }
}