package de.daxu.swamp.test.rule;

import de.daxu.swamp.common.web.WebClient;

import static java.lang.String.format;


public class ResourceIntegrationTestRule extends IntegrationTestRule {

    private final String url;

    public ResourceIntegrationTestRule( SpringRule spring ) {
        super( spring );
        this.url = format( "http://%s:%s", "localhost", spring.getProperty( "server.port" ) );
    }

    public WebClient.Resource webClient() {
        return WebClient.resource( url );
    }
}
