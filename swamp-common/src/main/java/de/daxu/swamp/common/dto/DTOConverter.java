package de.daxu.swamp.common.dto;

public interface DTOConverter<DOMAIN, DTO> {

    DTO toDTO(DOMAIN domain);
}
