package de.daxu.swamp.core.server;

import de.daxu.swamp.core.location.Location;
import de.daxu.swamp.core.location.LocationType;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

@Entity
@Table(name = "server")
@DiscriminatorValue("server")
@SuppressWarnings("unused")
public class Server extends Location {

    @NotBlank(message = "{NotBlank.Server.ip}")
    @Column(name = "ip", unique = true)
    private String ip;

    @NotBlank(message = "{NotBlank.Server.CACertificate}")
    @Lob
    @Column(name = "ca_certificate")
    private String CACertificate;

    @NotBlank(message = "{NotBlank.Server.certificate}")
    @Lob
    @Column(name = "certificate")
    private String certificate;

    @NotBlank(message = "{NotBlank.Server.key}")
    @Lob
    @Column(name = "`key`")
    private String key;

    private Server() {
    }

    private Server(String id, String name, String ip, String CACertificate, String certificate, String key) {
        super(id, name);
        this.ip = ip;
        this.CACertificate = CACertificate;
        this.certificate = certificate;
        this.key = key;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setCACertificate(String CACertificate) {
        this.CACertificate = CACertificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public void setKey(String key) {
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

    @Override
    public Set<Server> getServers() {
        return newHashSet(this);
    }

    public static class Builder extends Location.Builder<Builder> {

        private String ip;
        private String CACertificate;
        private String certificate;
        private String key;

        public static Builder aServer() {
            return new Builder();
        }

        public Builder withIp(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder withCACertificate(String CACertificate) {
            this.CACertificate = CACertificate;
            return this;
        }

        public Builder withCertificate(String certificate) {
            this.certificate = certificate;
            return this;
        }

        public Builder withKey(String key) {
            this.key = key;
            return this;
        }

        public Server build() {
            return new Server(id, name, ip, CACertificate, certificate, key);
        }
    }
}
