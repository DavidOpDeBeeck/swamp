package de.daxu.swamp.api.server.dto;

import de.daxu.swamp.api.location.dto.LocationCreateDTO;
import org.hibernate.validator.constraints.NotBlank;

public class ServerCreateDTO extends LocationCreateDTO {

    @NotBlank(message = "{NotBlank.ServerCreateDTO.ip}")
    private String ip;
    private String caCertificate;
    private String certificate;
    private String key;

    @SuppressWarnings("unused")
    private ServerCreateDTO() {
        super();
    }

    private ServerCreateDTO(String name, String ip, String caCertificate, String certificate, String key) {
        super(name);
        this.ip = ip;
        this.caCertificate = caCertificate;
        this.certificate = certificate;
        this.key = key;
    }

    public String getIp() {
        return ip;
    }

    public String getCaCertificate() {
        return caCertificate;
    }

    public String getCertificate() {
        return certificate;
    }

    public String getKey() {
        return key;
    }

    public static class Builder extends LocationCreateDTO.Builder<Builder> {

        private String ip;
        private String CACertificate;
        private String certificate;
        private String key;

        public Builder withIp(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder withCaCertificate(String CACertificate) {
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

        public ServerCreateDTO build() {
            return new ServerCreateDTO(name, ip, CACertificate, certificate, key);
        }
    }
}
