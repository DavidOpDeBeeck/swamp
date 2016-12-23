package de.daxu.swamp.test.rule;

import de.daxu.swamp.common.rest.RestClient;

import static de.daxu.swamp.common.rest.RestClient.aRestClient;
import static java.lang.String.format;


public class ResourceIntegrationTestRule extends IntegrationTestRule {

    private final String url;

    public ResourceIntegrationTestRule( SpringRule spring ) {
        super( spring );
        this.url = format( "http://%s:%s", "localhost", spring.getProperty( "server.port" ) );
    }

    public RestClient.Resource webClient() {
        return aRestClient().resource( url );
    }
}
