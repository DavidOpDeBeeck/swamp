package de.daxu.swamp.common.jackson;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ofPattern;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> implements ContextualSerializer {

    private static final String DEFAULT_TIME_ZONE = "GMT";
    private static final String ISO8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    private BeanProperty property;

    @Override
    public void serialize( LocalDateTime dateTime, JsonGenerator gen, SerializerProvider serializers ) throws IOException, JsonProcessingException {
        ZonedDateTime zonedDateTime = dateTime.atZone( ZoneId.of( DEFAULT_TIME_ZONE ) );

        gen.writeNumber( zonedDateTime.toInstant().toEpochMilli() );
        gen.writeStringField( format( "%sISO8601", property.getName() ), zonedDateTime.format( ofPattern( ISO8601_FORMAT ) ) );
    }

    @Override
    public JsonSerializer<?> createContextual( SerializerProvider prov, BeanProperty property ) throws JsonMappingException {
        this.property = property;
        return this;
    }
}
