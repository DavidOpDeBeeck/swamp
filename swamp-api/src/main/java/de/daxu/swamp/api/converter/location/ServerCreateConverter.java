package de.daxu.swamp.api.converter.location;

import de.daxu.swamp.api.converter.DomainConverter;
import de.daxu.swamp.api.dto.location.ServerCreateDTO;
import de.daxu.swamp.core.location.Server;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.location.Server.ServerBuilder.serverBuilder;

@Component
public class ServerCreateConverter implements DomainConverter<ServerCreateDTO, Server> {

    @Override
    public Server toDomain( ServerCreateDTO dto ) {
        return serverBuilder()
                .withName( dto.name )
                .withIp( dto.ip )
                .withCACertificate( dto.CAcertificate )
                .withCertificate( dto.certificate )
                .withKey( dto.key )
                .build();
    }
}
