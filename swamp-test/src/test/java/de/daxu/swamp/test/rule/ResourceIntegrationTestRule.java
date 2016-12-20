package de.daxu.swamp.test.rule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import de.daxu.swamp.common.jackson.SwampObjectMapper;
import de.daxu.swamp.test.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

public class ResourceIntegrationTestRule extends IntegrationTestRule {

    private final Logger logger = LoggerFactory.getLogger( ResourceIntegrationTestRule.class );

    private final String baseUrl;
    private final RestTemplate restTemplate;
    private final SwampObjectMapper objectMapper;

    private String pathPrefixes;

    public ResourceIntegrationTestRule( SpringRule spring ) {
        super( spring );
        this.baseUrl = format( "http://localhost:%s", spring.getProperty( "server.port" ) );
        this.restTemplate = new RestTemplate();
        this.objectMapper = new SwampObjectMapper();
        this.pathPrefixes = "";
    }

    public void setPathPrefixes( String... pathPrefixes ) {
        this.pathPrefixes = Arrays.stream( pathPrefixes ).reduce( String::concat ).orElse( "" );
    }

    public <T> T get( Class<T> returnType, String... path ) {
        JavaType type = objectMapper.getTypeFactory()
                .constructType( returnType );
        return convertResponseData( getRequest( path ), type );
    }

    public <T> List<T> getList( Class<T> returnType, String... path ) {
        JavaType type = objectMapper.getTypeFactory()
                .constructCollectionType( List.class, returnType );
        return convertResponseData( getRequest( path ), type );
    }

    public String post( Object body, String... path ) {
        Response response = postRequest( body, path );
        String location = response.getMeta().getLocation();
        return location.substring( location.lastIndexOf( "/" ) + 1, location.length() );
    }

    public void put( Object body, String... path ) {
        restTemplate.put( url( path ), body, Response.class );
    }

    public void delete( String... path ) {
        restTemplate.delete( url( path ), Response.class );
    }

    private Response getRequest( String... path ) {
        logger.info("GET {}", url( path ));
        return restTemplate.getForObject( url( path ), Response.class );
    }

    private Response postRequest( Object body, String... path ) {
        return restTemplate.postForObject( url( path ), body, Response.class );
    }

    private String url( String... path ) {
        return format( "%s%s%s", baseUrl, pathPrefixes, Arrays.stream( path ).reduce( String::concat ).orElse( "" ) );
    }

    private <T> T convertResponseData( Response response, JavaType type ) {
        try {
            logger.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString( response ));
        } catch( JsonProcessingException e ) {
            e.printStackTrace();
        }
        return objectMapper.convertValue( response.getData(), type );
    }
}
