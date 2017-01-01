package de.daxu.swamp.core.credentials;

import static de.daxu.swamp.core.credentials.UsernamePasswordCredentials.Builder;

public class UsernamePasswordCredentialsTestBuilder {

    private String username = "a test username";
    private String password = "a test password";

    public static UsernamePasswordCredentialsTestBuilder anUsernamePasswordCredentials() {
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
        return Builder.anUsernamePasswordCredentials()
                .withUsername( username )
                .withPassword( password )
                .build();
    }
}