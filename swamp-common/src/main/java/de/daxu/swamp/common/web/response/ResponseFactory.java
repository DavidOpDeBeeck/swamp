package de.daxu.swamp.common.web.response;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;

import static de.daxu.swamp.common.web.response.Response.Builder.aResponse;

@Component
public class ResponseFactory {

    private final MetaFactory metaFactory;

    @Autowired
    public ResponseFactory( MetaFactory metaFactory ) {
        this.metaFactory = metaFactory;
    }

    public Response notFound( String error ) {
        return aResponse()
                .withMeta( metaFactory.notFound() )
                .withErrors( error )
                .build();
    }

    public Response badRequest( String error ) {
        return aResponse()
                .withMeta( metaFactory.badRequest() )
                .withErrors( error )
                .build();
    }

    public Response serverError( String error ) {
        return aResponse()
                .withMeta( metaFactory.serverError() )
                .withErrors( error )
                .build();
    }

    public Response created( URI location ) {
        return aResponse()
                .withMeta( metaFactory.created( location ) )
                .build();
    }

    public Response success() {
        return success( null );
    }

    public Response success( Object data ) {
        return aResponse()
                .withMeta( metaFactory.success() )
                .withData( data )
                .build();
    }
}
