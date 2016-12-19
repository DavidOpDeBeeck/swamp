package de.daxu.swamp.api.server.converter;

import de.daxu.swamp.api.server.dto.ServerDTO;
import de.daxu.swamp.common.dto.DTOConverter;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.server.Server;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.server.Server.ServerBuilder.aServerBuilder;

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
        return aServerBuilder()
                .withId( dto.id )
                .withName( dto.name )
                .withIp( dto.ip )
                .build();
    }
}
