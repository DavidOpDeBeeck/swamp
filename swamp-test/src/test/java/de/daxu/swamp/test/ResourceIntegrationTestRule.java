package de.daxu.swamp.test;

import com.fasterxml.jackson.databind.JavaType;
import de.daxu.swamp.common.jackson.SwampObjectMapper;
import de.daxu.swamp.test.response.Response;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ResourceIntegrationTestRule extends IntegrationTestRule {

    private final String baseUrl;
    private final RestTemplate restTemplate;
    private final SwampObjectMapper objectMapper;

    public ResourceIntegrationTestRule( SpringRule spring ) {
        super( spring );
        this.baseUrl = String.format( "http://localhost:%s", spring.getProperty( "server.port" ) );
        this.restTemplate = new RestTemplate();
        this.objectMapper = new SwampObjectMapper();
    }

    public <T> T get( String path, Class<T> returnType ) {
        JavaType type = objectMapper.getTypeFactory()
                .constructType( returnType );
        return convertResponse( get( path ), type );
    }

    public <T> List<T> getList( String path, Class<T> returnType ) {
        JavaType type = objectMapper.getTypeFactory()
                .constructCollectionType( List.class, returnType );
        return convertResponse( get( path ), type );
    }

    private Response get( String url ) {
        return restTemplate.getForObject( this.baseUrl + url, Response.class );
    }

    private <T> T convertResponse( Response response, JavaType type ) {
        return objectMapper.convertValue( response.getData(), type );
    }
}
