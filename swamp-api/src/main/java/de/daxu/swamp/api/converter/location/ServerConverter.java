package de.daxu.swamp.api.converter.location;

import de.daxu.swamp.api.converter.DTOConverter;
import de.daxu.swamp.api.converter.DomainConverter;
import de.daxu.swamp.api.dto.location.ServerDTO;
import de.daxu.swamp.core.location.Server;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.location.Server.ServerBuilder.serverBuilder;

@Component
public class ServerConverter implements DTOConverter<Server, ServerDTO>, DomainConverter<ServerDTO, Server> {

    @Override
    public ServerDTO toDTO( Server server ) {
        ServerDTO dto = new ServerDTO();
        dto.id = server.getId();
        dto.ip = server.getIp();
        dto.name = server.getName();
        dto.type = server.getType();
        return dto;
    }

    @Override
    public Server toDomain( ServerDTO dto ) {
        return serverBuilder()
                .withId( dto.id )
                .withName( dto.name )
                .withIp( dto.ip )
                .build();
    }
}
