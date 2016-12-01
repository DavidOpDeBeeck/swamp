package de.daxu.swamp.docker.mapper;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import de.daxu.swamp.core.container.PortMapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PortMappingMapper implements Converter<PortMapping, PortBinding> {

    @Override
    public PortBinding convert( PortMapping portMapping ) {
        return createPortBinding( portMapping.getInternal(), portMapping.getExternal() );
    }

    private PortBinding createPortBinding( int internalPort, int externalPort ) {
        ExposedPort internal = ExposedPort.tcp( internalPort );
        Ports.Binding external = Ports.Binding.bindPort( externalPort );
        return new PortBinding( external, internal );
    }
}
