package de.daxu.swamp.test.overrides;

import java.util.HashMap;
import java.util.Map;

public class BeanOverrides {

    public static BeanOverrides empty() {
        return new BeanOverrides.Builder().build();
    }

    private final Map<String, Object> beanOverrides;

    private BeanOverrides(Map<String, Object> beanOverrides) {
        this.beanOverrides = beanOverrides;
    }

    boolean containsBean(String beanName) {
        return beanOverrides.containsKey(beanName);
    }

    Object getOrDefault(String beanName, Object beanDefault) {
        return beanOverrides.getOrDefault(beanName, beanDefault);
    }

    public static class Builder {

        private final Map<String, Object> beanOverrides = new HashMap<>();

        public Builder withOverride(String beanName, Object beanOverride) {
            beanOverrides.put(beanName, beanOverride);
            return this;
        }

        public BeanOverrides build() {
            return new BeanOverrides(beanOverrides);
        }
    }
}
