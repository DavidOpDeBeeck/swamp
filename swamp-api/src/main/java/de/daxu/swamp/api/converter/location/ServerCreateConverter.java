package de.daxu.swamp.api.converter.location;

import de.daxu.swamp.api.converter.DomainConverter;
import de.daxu.swamp.api.dto.location.ServerCreateDTO;
import de.daxu.swamp.core.location.server.Server;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.location.server.Server.ServerBuilder.aServer;

@Component
public class ServerCreateConverter implements DomainConverter<ServerCreateDTO, Server> {

    @Override
    public Server toDomain( ServerCreateDTO dto ) {
        return aServer()
                .withName( dto.name )
                .withIp( dto.ip )
                .withCACertificate( dto.CAcertificate )
                .withCertificate( dto.certificate )
                .withKey( dto.key )
                .build();
    }
}
