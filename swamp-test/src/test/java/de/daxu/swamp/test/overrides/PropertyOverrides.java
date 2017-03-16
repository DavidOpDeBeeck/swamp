package de.daxu.swamp.test.overrides;

import java.util.HashMap;
import java.util.Map;

public class PropertyOverrides {

    public static PropertyOverrides empty() {
        return new PropertyOverrides.Builder().build();
    }

    private final Map<String, Object> propertyOverrides;

    private PropertyOverrides(Map<String, Object> propertyOverrides) {
        this.propertyOverrides = propertyOverrides;
    }

    public Map<String, Object> getPropertyOverrides() {
        return propertyOverrides;
    }

    public static class Builder {

        private final Map<String, Object> propertyOverrides = new HashMap<>();

        public Builder withOverride(String beanName, Object beanOverride) {
            propertyOverrides.put(beanName, beanOverride);
            return this;
        }

        public PropertyOverrides build() {
            return new PropertyOverrides(propertyOverrides);
        }
    }
}
