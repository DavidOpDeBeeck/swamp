package de.daxu.swamp.api.server.converter;

import de.daxu.swamp.api.server.dto.ServerCreateDTO;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.location.server.Server;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.location.server.Server.ServerBuilder.aServerBuilder;

@Component
public class ServerCreateConverter implements DomainConverter<ServerCreateDTO, Server> {

    @Override
    public Server toDomain( ServerCreateDTO dto ) {
        return aServerBuilder()
                .withName( dto.name )
                .withIp( dto.ip )
                .withCACertificate( dto.CAcertificate )
                .withCertificate( dto.certificate )
                .withKey( dto.key )
                .build();
    }
}
