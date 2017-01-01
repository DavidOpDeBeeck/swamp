package de.daxu.swamp.common.jackson;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import de.daxu.swamp.common.time.Dates;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static de.daxu.swamp.common.time.Dates.ZONE_ID;
import static java.time.format.DateTimeFormatter.ofPattern;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize( LocalDateTime dateTime, JsonGenerator gen, SerializerProvider serializers ) throws IOException, JsonProcessingException {
        ZonedDateTime zonedDateTime = dateTime.atZone( ZONE_ID );
        gen.writeString( zonedDateTime.format( ofPattern( Dates.DATE_TIME_FORMAT ) ) );
    }
}
