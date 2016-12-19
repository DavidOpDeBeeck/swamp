package de.daxu.swamp.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Map;

public class BeanOverridingProcessor implements BeanFactoryPostProcessor {

    private final Map<String, Object> beanOverrides;

    public BeanOverridingProcessor( Map<String, Object> beanOverrides ) {
        this.beanOverrides = beanOverrides;
    }

    @Override
    public void postProcessBeanFactory( ConfigurableListableBeanFactory beanFactory ) throws BeansException {
        beanFactory.addBeanPostProcessor(
                new BeanPostProcessor() {
                    @Override
                    public Object postProcessBeforeInitialization( Object bean, String beanName ) throws BeansException {
                        return bean;
                    }

                    @Override
                    public Object postProcessAfterInitialization( Object bean, String beanName ) throws BeansException {
                        return beanOverrides.getOrDefault( beanName, bean );
                    }
                }
        );
    }
}