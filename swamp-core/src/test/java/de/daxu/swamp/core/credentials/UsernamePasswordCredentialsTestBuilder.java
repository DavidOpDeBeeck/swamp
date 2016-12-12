package de.daxu.swamp.core.credentials;

public class UsernamePasswordCredentialsTestBuilder {

    private String username;
    private String password;

    public static UsernamePasswordCredentialsTestBuilder anUsernamePasswordCredentialsTestBuilder() {
        return new UsernamePasswordCredentialsTestBuilder();
    }

    public UsernamePasswordCredentialsTestBuilder withUsername( String username ) {
        this.username = username;
        return this;
    }

    public UsernamePasswordCredentialsTestBuilder withPassword( String password ) {
        this.password = password;
        return this;
    }

    public UsernamePasswordCredentials build() {
        return new UsernamePasswordCredentials( username, password );
    }
}