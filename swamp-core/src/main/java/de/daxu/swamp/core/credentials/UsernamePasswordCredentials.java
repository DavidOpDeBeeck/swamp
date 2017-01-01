package de.daxu.swamp.core.credentials;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "username_password_credentials" )
@SuppressWarnings( "unused" )
public class UsernamePasswordCredentials extends Credentials {

    private String username;
    private String password;

    private UsernamePasswordCredentials() {
    }

    private UsernamePasswordCredentials( String username, String password ) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public CredentialsType getType() {
        return CredentialsType.USERNAME_PASSWORD;
    }

    public static class Builder {

        private String username;
        private String password;

        public static Builder anUsernamePasswordCredentials() {
            return new Builder();
        }

        public Builder withUsername( String username ) {
            this.username = username;
            return this;
        }

        public Builder withPassword( String password ) {
            this.password = password;
            return this;
        }

        public UsernamePasswordCredentials build() {
            return new UsernamePasswordCredentials( username, password );
        }
    }
}
