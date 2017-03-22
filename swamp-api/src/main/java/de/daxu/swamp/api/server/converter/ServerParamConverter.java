package de.daxu.swamp.api.server.converter;

import de.daxu.swamp.common.web.exception.NotFoundException;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.core.server.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ServerParamConverter implements Converter<String, Server> {

    private final ServerService serverService;

    @Autowired
    public ServerParamConverter(ServerService serverService) {
        this.serverService = serverService;
    }

    @Override
    public Server convert(String source) {
        Server server = serverService.getServer(source);

        if (server == null)
            throw new NotFoundException("Server was not found!");

        return server;
    }
}
