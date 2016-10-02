package de.daxu.swamp.core.location;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
@Table( name = "server" )
@DiscriminatorValue( "server" )
public class Server extends Location {

    @NotBlank( message = "{NotBlank.Server.ip}" )
    @Column( name = "ip", unique = true )
    private String ip;

    @NotBlank( message = "{NotBlank.Server.CACertificate}" )
    @Lob
    @Column( name = "CACertificate" )
    private String CACertificate;

    @NotBlank( message = "{NotBlank.Server.certificate}" )
    @Lob
    @Column( name = "certificate" )
    private String certificate;

    @NotBlank( message = "{NotBlank.Server.key}" )
    @Lob
    @Column( name = "`key`" )
    private String key;

    public Server() {
    }

    public Server( String id, String name, String ip, String CACertificate, String certificate, String key ) {
        super( id, name );
        this.ip = ip;
        this.CACertificate = CACertificate;
        this.certificate = certificate;
        this.key = key;
    }

    public String getIp() {
        return ip;
    }

    public String getCACertificate() {
        return CACertificate;
    }

    public String getCertificate() {
        return certificate;
    }

    public String getKey() {
        return key;
    }

    @Override
    public LocationType getType() {
        return LocationType.SERVER;
    }

    public static class ServerBuilder extends LocationBuilder<ServerBuilder> {

        private String ip;
        private String CACertificate;
        private String certificate;
        private String key;

        public static ServerBuilder serverBuilder() {
            return new ServerBuilder();
        }

        public ServerBuilder withIp( String ip ) {
            this.ip = ip;
            return this;
        }

        public ServerBuilder withCACertificate( String CACertificate ) {
            this.CACertificate = CACertificate;
            return this;
        }

        public ServerBuilder withCertificate( String certificate ) {
            this.certificate = certificate;
            return this;
        }

        public ServerBuilder withKey( String key ) {
            this.key = key;
            return this;
        }

        public Server build() {
            return new Server( id, name, ip, CACertificate, certificate, key );
        }
    }
}
