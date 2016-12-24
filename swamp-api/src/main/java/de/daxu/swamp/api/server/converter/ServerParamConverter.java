package de.daxu.swamp.api.server.converter;

import de.daxu.swamp.common.web.exception.NotFoundException;
import de.daxu.swamp.core.location.LocationService;
import de.daxu.swamp.core.server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ServerParamConverter implements Converter<String, Server> {

    private final LocationService locationService;

    @Autowired
    public ServerParamConverter( LocationService locationService ) {
        this.locationService = locationService;
    }

    @Override
    public Server convert( String source ) {
        Server server = locationService.getServer( source );

        if( server == null )
            throw new NotFoundException( "Server was not found!" );

        return server;
    }
}
