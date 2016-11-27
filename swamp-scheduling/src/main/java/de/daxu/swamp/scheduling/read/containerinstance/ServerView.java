package de.daxu.swamp.scheduling.read.containerinstance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.daxu.swamp.common.jpa.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@SuppressWarnings( "unused" )
public class ServerView extends Identifiable {

    @Column( name = "name" )
    private String name;

    @Column( name = "ip" )
    private String ip;

    @Lob
    @Column( name = "ca_certificate" )
    private String CACertificate;

    @Lob
    @Column( name = "certificate" )
    private String certificate;

    @Lob
    @Column( name = "`key`" )
    private String key;

    private ServerView() {
    }

    private ServerView( String name,
                        String ip,
                        String CACertificate,
                        String certificate,
                        String key ) {
        this.name = name;
        this.ip = ip;
        this.CACertificate = CACertificate;
        this.certificate = certificate;
        this.key = key;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public void setIp( String ip ) {
        this.ip = ip;
    }

    public void setCACertificate( String CACertificate ) {
        this.CACertificate = CACertificate;
    }

    public void setCertificate( String certificate ) {
        this.certificate = certificate;
    }

    public void setKey( String key ) {
        this.key = key;
    }

    public String getName() {
        return name;
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

    public static class ServerViewBuilder {

        private String name;
        private String ip;
        private String CACertificate;
        private String certificate;
        private String key;

        public static ServerViewBuilder aServerView() {
            return new ServerViewBuilder();
        }

        public ServerViewBuilder withName( String name ) {
            this.name = name;
            return this;
        }

        public ServerViewBuilder withIp( String ip ) {
            this.ip = ip;
            return this;
        }

        public ServerViewBuilder withCACertificate( String CACertificate ) {
            this.CACertificate = CACertificate;
            return this;
        }

        public ServerViewBuilder withCertificate( String certificate ) {
            this.certificate = certificate;
            return this;
        }

        public ServerViewBuilder withKey( String key ) {
            this.key = key;
            return this;
        }

        public ServerView build() {
            return new ServerView( name, ip, CACertificate, certificate, key );
        }
    }
}
