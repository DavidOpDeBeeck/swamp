package de.daxu.swamp.api.server.dto;

import de.daxu.swamp.api.location.dto.LocationCreateDTO;

public class ServerCreateDTO extends LocationCreateDTO {

    public String ip;
    public String CAcertificate;
    public String certificate;
    public String key;

}