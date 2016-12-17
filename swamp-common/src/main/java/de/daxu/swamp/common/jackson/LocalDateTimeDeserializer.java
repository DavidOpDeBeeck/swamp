package de.daxu.swamp.common.jackson;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private static final String DEFAULT_TIME_ZONE = "GMT";

    @Override
    public LocalDateTime deserialize( JsonParser parser, DeserializationContext context ) throws IOException, JsonProcessingException {
        JsonNode dateNode = parser.getCodec().readTree( parser );
        return Instant
                .ofEpochMilli( dateNode.longValue() )
                .atZone( ZoneId.of( DEFAULT_TIME_ZONE ) )
                .toLocalDateTime();
    }
}
