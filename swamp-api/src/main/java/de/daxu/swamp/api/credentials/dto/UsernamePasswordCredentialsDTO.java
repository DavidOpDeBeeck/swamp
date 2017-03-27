package de.daxu.swamp.api.credentials.dto;

import de.daxu.swamp.core.credentials.CredentialsType;
import org.hibernate.validator.constraints.NotBlank;

public class UsernamePasswordCredentialsDTO extends CredentialsDTO {

    @NotBlank(message = "{NotBlank.UsernamePasswordCredentialsDTO.username}")
    private String username;
    @NotBlank(message = "{NotBlank.UsernamePasswordCredentialsDTO.password}")
    private String password;

    @SuppressWarnings("unused")
    private UsernamePasswordCredentialsDTO() {
        super(CredentialsType.USERNAME_PASSWORD);
    }

    private UsernamePasswordCredentialsDTO(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static class Builder {

        private String username;
        private String password;

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UsernamePasswordCredentialsDTO build() {
            return new UsernamePasswordCredentialsDTO(username, password);
        }
    }
}
