package de.daxu.swamp.docker.mapper;

import de.daxu.swamp.core.container.EnvironmentVariable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentVariableMapper implements Converter<EnvironmentVariable, String> {

    @Override
    public String convert( EnvironmentVariable environmentVariable ) {
        return environmentVariable.toString();
    }
}
