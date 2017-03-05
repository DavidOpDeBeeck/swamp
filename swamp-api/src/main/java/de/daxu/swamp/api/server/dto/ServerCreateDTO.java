package de.daxu.swamp.api.server.dto;

import de.daxu.swamp.api.location.dto.LocationCreateDTO;
import org.hibernate.validator.constraints.NotBlank;

public class ServerCreateDTO extends LocationCreateDTO {

    @NotBlank(message = "{NotBlank.ServerCreateDTO.ip}")
    public String ip;
    public String CACertificate;
    public String certificate;
    public String key;

}
