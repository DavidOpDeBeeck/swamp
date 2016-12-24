package de.daxu.swamp.api.container.converter;

import de.daxu.swamp.common.web.exception.NotFoundException;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ContainerParamConverter implements Converter<String, Container> {

    private final ContainerService containerService;

    @Autowired
    public ContainerParamConverter( ContainerService containerService ) {
        this.containerService = containerService;
    }

    @Override
    public Container convert( String source ) {
        Container container = containerService.getContainer( source );

        if( container == null )
            throw new NotFoundException( "Container was not found!" );

        return container;
    }
}
