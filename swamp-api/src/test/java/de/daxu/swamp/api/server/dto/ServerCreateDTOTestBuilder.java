package de.daxu.swamp.api.server.dto;

import static de.daxu.swamp.core.LocationTestConstants.Servers.*;

public class ServerCreateDTOTestBuilder {

    private String name = SERVER_NAME;
    private String ip = SERVER_IP;
    private String CACertificate = SERVER_CA_CERTIFICATE;
    private String certificate = SERVER_CERTIFICATE;
    private String key = SERVER_KEY;

    public static ServerCreateDTOTestBuilder aServerCreateDTO() {
        return new ServerCreateDTOTestBuilder();
    }

    public static ServerCreateDTOTestBuilder anotherServerCreateDTO() {
        return new ServerCreateDTOTestBuilder()
                .withName(ANOTHER_SERVER_NAME)
                .withIp(ANOTHER_SERVER_IP)
                .withKey(ANOTHER_SERVER_KEY)
                .withCertificate(ANOTHER_SERVER_CERTIFICATE)
                .withCACertificate(ANOTHER_SERVER_CA_CERTIFICATE);
    }

    public ServerCreateDTOTestBuilder withName( String name ) {
        this.name = name;
        return this;
    }

    public ServerCreateDTOTestBuilder withIp( String ip ) {
        this.ip = ip;
        return this;
    }

    public ServerCreateDTOTestBuilder withCACertificate( String CACertificate ) {
        this.CACertificate = CACertificate;
        return this;
    }

    public ServerCreateDTOTestBuilder withCertificate( String certificate ) {
        this.certificate = certificate;
        return this;
    }

    public ServerCreateDTOTestBuilder withKey( String key ) {
        this.key = key;
        return this;
    }

    public ServerCreateDTO build() {
        ServerCreateDTO dto = new ServerCreateDTO();
        dto.name = name;
        dto.ip = ip;
        dto.key = key;
        dto.certificate = certificate;
        dto.CACertificate = CACertificate;
        return dto;
    }
}