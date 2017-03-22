package de.daxu.swamp.api.server.converter;

import de.daxu.swamp.api.server.dto.ServerDTO;
import de.daxu.swamp.common.dto.DTOConverter;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.server.Server;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.server.Server.Builder.aServer;

@Component
public class ServerConverter implements DTOConverter<Server, ServerDTO>, DomainConverter<ServerDTO, Server> {

    @Override
    public ServerDTO toDTO(Server server) {
        return new ServerDTO.Builder()
                .withId(server.getId())
                .withName(server.getName())
                .withIp(server.getIp())
                .build();
    }

    @Override
    public Server toDomain(ServerDTO dto) {
        return aServer()
                .withId(dto.getId())
                .withName(dto.getName())
                .withIp(dto.getIp())
                .build();
    }
}
