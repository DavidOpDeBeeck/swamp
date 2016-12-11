package de.daxu.swamp.docker.mapper;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports.Binding;
import de.daxu.swamp.common.util.Converter;
import de.daxu.swamp.core.container.PortMapping;
import org.springframework.stereotype.Component;

import static com.github.dockerjava.api.model.ExposedPort.*;
import static com.github.dockerjava.api.model.Ports.Binding.*;

@Component
public class PortMappingMapper implements Converter<PortMapping, PortBinding> {

    @Override
    public PortBinding convert( PortMapping portMapping ) {
        return createPortBinding( portMapping.getInternal(), portMapping.getExternal() );
    }

    private PortBinding createPortBinding( int internalPort, int externalPort ) {
        ExposedPort internal = tcp( internalPort );
        Binding external = bindPort( externalPort );
        return new PortBinding( external, internal );
    }
}
