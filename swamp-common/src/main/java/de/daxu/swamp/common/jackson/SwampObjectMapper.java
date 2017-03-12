package de.daxu.swamp.common.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.time.LocalDateTime;

import static java.lang.String.format;

public class SwampObjectMapper extends ObjectMapper {

    public SwampObjectMapper() {
        configuration();
        registerModules();
    }

    public String toJSON(Object payload) {
        try {
            return writerWithDefaultPrettyPrinter().writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            return format("Failed to convert %s to JSON", payload);
        }
    }

    private void configuration() {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private void registerModules() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        registerModule(module);
    }
}
