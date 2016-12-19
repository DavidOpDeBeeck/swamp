package de.daxu.swamp.test.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Map;

public class OverridingBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private final Map<String, Object> beanOverrides;

    public OverridingBeanFactoryPostProcessor( Map<String, Object> beanOverrides ) {
        this.beanOverrides = beanOverrides;
    }

    @Override
    public void postProcessBeanFactory( ConfigurableListableBeanFactory beanFactory ) throws BeansException {
        beanFactory.addBeanPostProcessor( new OverridingBeanPostProcessor( beanOverrides ) );
    }

    private static class OverridingBeanPostProcessor implements BeanPostProcessor {

        private final Map<String, Object> beanOverrides;

        private OverridingBeanPostProcessor( Map<String, Object> beanOverrides ) {
            this.beanOverrides = beanOverrides;
        }

        @Override
        public Object postProcessBeforeInitialization( Object bean, String beanName ) throws BeansException {
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization( Object bean, String beanName ) throws BeansException {
            return beanOverrides.getOrDefault( beanName, bean );
        }
    }
}