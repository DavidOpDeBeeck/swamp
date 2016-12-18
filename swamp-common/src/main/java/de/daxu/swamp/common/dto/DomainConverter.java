package de.daxu.swamp.common.dto;

public interface DomainConverter<DTO, DOMAIN> {

    DOMAIN toDomain( DTO dto );

}
