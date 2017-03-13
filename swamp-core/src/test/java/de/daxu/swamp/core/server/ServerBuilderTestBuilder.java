package de.daxu.swamp.core.server;

import de.daxu.swamp.core.server.Server.Builder;

import static de.daxu.swamp.core.LocationTestConstants.Servers.*;

public class ServerBuilderTestBuilder {

    private String id;
    private String name = SERVER_NAME;
    private String ip = SERVER_IP;
    private String CACertificate = SERVER_CA_CERTIFICATE;
    private String certificate = SERVER_CERTIFICATE;
    private String key = SERVER_KEY;

    public static ServerBuilderTestBuilder aServer() {
        return new ServerBuilderTestBuilder();
    }

    public static ServerBuilderTestBuilder anotherServer() {
        return new ServerBuilderTestBuilder()
                .withName(ANOTHER_SERVER_NAME)
                .withIp(ANOTHER_SERVER_IP)
                .withKey(ANOTHER_SERVER_KEY)
                .withCertificate(ANOTHER_SERVER_CERTIFICATE)
                .withCACertificate(ANOTHER_SERVER_CA_CERTIFICATE);
    }

    public ServerBuilderTestBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public ServerBuilderTestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ServerBuilderTestBuilder withIp(String ip) {
        this.ip = ip;
        return this;
    }

    public ServerBuilderTestBuilder withCACertificate(String CACertificate) {
        this.CACertificate = CACertificate;
        return this;
    }

    public ServerBuilderTestBuilder withCertificate(String certificate) {
        this.certificate = certificate;
        return this;
    }

    public ServerBuilderTestBuilder withKey(String key) {
        this.key = key;
        return this;
    }

    public Server build() {
        return Builder.aServer()
                .withId(id)
                .withName(name)
                .withIp(ip)
                .withKey(key)
                .withCertificate(certificate)
                .withCACertificate(CACertificate)
                .build();
    }
}