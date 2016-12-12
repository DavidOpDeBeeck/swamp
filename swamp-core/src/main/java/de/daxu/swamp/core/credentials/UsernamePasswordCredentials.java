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

    UsernamePasswordCredentials( String username, String password ) {
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

    public static class UsernamePasswordCredentialsBuilder {

        private String username;
        private String password;

        public static UsernamePasswordCredentialsBuilder anUsernamePasswordCredentialsBuilder() {
            return new UsernamePasswordCredentialsBuilder();
        }

        public UsernamePasswordCredentialsBuilder withUsername( String username ) {
            this.username = username;
            return this;
        }

        public UsernamePasswordCredentialsBuilder withPassword( String password ) {
            this.password = password;
            return this;
        }

        public UsernamePasswordCredentials build() {
            return new UsernamePasswordCredentials( username, password );
        }
    }
}
