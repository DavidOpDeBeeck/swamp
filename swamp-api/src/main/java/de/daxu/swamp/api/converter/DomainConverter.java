package de.daxu.swamp.api.converter;

public interface DomainConverter<DTO, DOMAIN> {

    DOMAIN toDomain( DTO dto );

}
