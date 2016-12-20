package de.daxu.swamp.core.server;

public class ServerBuilderTestBuilder {

    private String id;
    private String name  = "a server name";
    private String ip = "an ip";
    private String CACertificate = "a CACertificate";
    private String certificate = "a certificate";
    private String key = "a key";

    public static ServerBuilderTestBuilder aServerBuilderTestBuilder() {
        return new ServerBuilderTestBuilder();
    }

    public static ServerBuilderTestBuilder anotherServerBuilderTestBuilder() {
        return new ServerBuilderTestBuilder()
                .withName( "another server name" );
    }

    public ServerBuilderTestBuilder withId( String id ) {
        this.id = id;
        return this;
    }

    public ServerBuilderTestBuilder withName( String name ) {
        this.name = name;
        return this;
    }

    public ServerBuilderTestBuilder withIp( String ip ) {
        this.ip = ip;
        return this;
    }

    public ServerBuilderTestBuilder withCACertificate( String CACertificate ) {
        this.CACertificate = CACertificate;
        return this;
    }

    public ServerBuilderTestBuilder withCertificate( String certificate ) {
        this.certificate = certificate;
        return this;
    }

    public ServerBuilderTestBuilder withKey( String key ) {
        this.key = key;
        return this;
    }

    public Server build() {
        return new Server( id, name, ip, CACertificate, certificate, key );
    }
}