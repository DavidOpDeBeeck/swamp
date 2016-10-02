package de.daxu.swamp.api.converter;

public interface DTOConverter<DOMAIN, DTO> {

    DTO toDTO( DOMAIN domain );

}
