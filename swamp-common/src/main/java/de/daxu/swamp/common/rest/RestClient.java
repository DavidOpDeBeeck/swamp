package de.daxu.swamp.common.rest;

import com.fasterxml.jackson.databind.JavaType;
import de.daxu.swamp.common.jackson.SwampObjectMapper;
import de.daxu.swamp.common.rest.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.lang.String.format;

public class RestClient {

    private static final SwampObjectMapper OBJECT_MAPPER = new SwampObjectMapper();

    public static RestClient aRestClient() {
        return new RestClient();
    }

    public static JavaType type( Class returnType ) {
        return OBJECT_MAPPER.getTypeFactory()
                .constructType( returnType );
    }

    public static JavaType list( Class returnType ) {
        return OBJECT_MAPPER.getTypeFactory()
                .constructCollectionType( List.class, returnType );
    }

    private RestClient() {
    }

    public Resource resource( String resource ) {
        return new Resource( resource );
    }

    public static class Resource {

        private final StringBuffer url;
        private final RestTemplate restTemplate;
        private final Logger logger = LoggerFactory.getLogger( Resource.class );

        private JavaType returnType;

        private Resource( String baseUrl ) {
            this.url = new StringBuffer( baseUrl );
            this.restTemplate = new RestTemplate();
        }

        public Resource path( String path ) {
            this.url.append( format( "/%s", path ) );
            return this;
        }

        public Resource type( JavaType returnType ) {
            this.returnType = returnType;
            return this;
        }

        public <T> T get() {
            String url = this.url.toString();
            logger.info( "[GET] {}", url );
            Response response = restTemplate.getForObject( url, Response.class );
            logger.info( "[RESPONSE] {}", OBJECT_MAPPER.toJSON( response ) );
            return convertResponseData( response );
        }

        public String post( Object payload ) {
            String url = this.url.toString();
            logger.info( "[POST] {}", url );
            logger.info( "[PAYLOAD] {}", OBJECT_MAPPER.toJSON( payload ) );
            Response response = restTemplate.postForObject( url, payload, Response.class );
            logger.info( "[RESPONSE] {}", OBJECT_MAPPER.toJSON( response ) );
            String location = response.getMeta().getLocation();
            return location.substring( location.lastIndexOf( "/" ) + 1, location.length() );
        }

        public void put( Object payload ) {
            String url = this.url.toString();
            logger.info( "[PUT] {}", url );
            logger.info( "[PAYLOAD] {}", OBJECT_MAPPER.toJSON( payload ) );
            restTemplate.put( url, payload, Response.class );
        }

        public void delete() {
            String url = this.url.toString();
            logger.info( "[DELETE] {}", url );
            restTemplate.delete( url, Response.class );
        }

        private <S> S convertResponseData( Response response ) {
            return OBJECT_MAPPER.convertValue( response.getData(), returnType );
        }
    }
}
