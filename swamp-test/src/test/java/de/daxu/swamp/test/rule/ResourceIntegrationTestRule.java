package de.daxu.swamp.test.rule;

import com.fasterxml.jackson.databind.JavaType;
import de.daxu.swamp.common.jackson.SwampObjectMapper;
import de.daxu.swamp.test.response.Response;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

public class ResourceIntegrationTestRule extends IntegrationTestRule {

    private final String baseUrl;
    private final RestTemplate restTemplate;
    private final SwampObjectMapper objectMapper;

    private String pathPrefixes = "";

    public ResourceIntegrationTestRule( SpringRule spring ) {
        super( spring );
        this.baseUrl = format( "http://localhost:%s", spring.getProperty( "server.port" ) );
        this.restTemplate = new RestTemplate();
        this.objectMapper = new SwampObjectMapper();
    }

    public <T> T get( String path, Class<T> returnType ) {
        JavaType type = objectMapper.getTypeFactory()
                .constructType( returnType );
        return convertResponseData( getRequest( path ), type );
    }

    public <T> List<T> getList( String path, Class<T> returnType ) {
        JavaType type = objectMapper.getTypeFactory()
                .constructCollectionType( List.class, returnType );
        return convertResponseData( getRequest( path ), type );
    }

    public String post( String path, Object body ) {
        Response response = postRequest( path, body );
        String location = response.getMeta().getLocation();
        return location.substring( location.lastIndexOf( "/" ) + 1, location.length() );
    }

    public void put( String path, Object body ) {
        restTemplate.put( url( path ), body, Response.class );
    }

    public void delete( String path ) {
        restTemplate.delete( url( path ), Response.class );
    }

    public void withPathPrefix( String... prefixes ) {
        StringBuilder builder = new StringBuilder( prefixes.length );
        Arrays.stream( prefixes )
                .forEach( builder::append );
        this.pathPrefixes = builder.toString();
    }

    private Response getRequest( String path ) {
        return restTemplate.getForObject( url( path ), Response.class );
    }

    private Response postRequest( String path, Object body ) {
        return restTemplate.postForObject( url( path ), body, Response.class );
    }

    private String url( String path ) {
        return format( "%s%s%s", baseUrl, pathPrefixes, path );
    }

    private <T> T convertResponseData( Response response, JavaType type ) {
        return objectMapper.convertValue( response.getData(), type );
    }
}
