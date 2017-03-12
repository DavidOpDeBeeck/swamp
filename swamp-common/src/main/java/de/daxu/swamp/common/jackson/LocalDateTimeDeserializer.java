package de.daxu.swamp.common.jackson;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import de.daxu.swamp.common.time.Dates;

import java.io.IOException;
import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ofPattern;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode dateNode = parser.getCodec().readTree(parser);
        return LocalDateTime
                .parse(dateNode.asText(), ofPattern(Dates.DATE_TIME_FORMAT));
    }
}
