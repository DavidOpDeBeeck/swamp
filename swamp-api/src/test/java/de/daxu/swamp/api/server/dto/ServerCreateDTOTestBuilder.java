package de.daxu.swamp.api.server.dto;

public class ServerCreateDTOTestBuilder {

    private String name = "a server name";
    private String ip = "a server ip";
    private String CACertificate = "a server CACertificate";
    private String certificate = "a server certificate";
    private String key = "a server key";

    public static ServerCreateDTOTestBuilder aServerCreateDTOTestBuilder() {
        return new ServerCreateDTOTestBuilder();
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