package de.daxu.swamp.core;

import de.daxu.swamp.core.credentials.UsernamePasswordCredentials;

public class CredentialsTestConstants {

    public static class UsernamePassword {

        public final static String USERNAME = "username";
        public final static String ANOTHER_USERNAME = "anotherUsername";
        public final static String PASSWORD = "password";
        public final static String ANOTHER_PASSWORD = "anotherPassword";

        public static UsernamePasswordCredentials anUsernamePasswordCredentials() {
            return new UsernamePasswordCredentials.Builder()
                    .withUsername(USERNAME)
                    .withPassword(PASSWORD)
                    .build();
        }

        public static UsernamePasswordCredentials anotherUsernamePasswordCredentials() {
            return new UsernamePasswordCredentials.Builder()
                    .withUsername(ANOTHER_USERNAME)
                    .withPassword(ANOTHER_PASSWORD)
                    .build();
        }
    }

}
