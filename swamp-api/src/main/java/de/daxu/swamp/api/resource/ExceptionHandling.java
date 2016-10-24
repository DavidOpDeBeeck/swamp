package de.daxu.swamp.api.resource;

import de.daxu.swamp.common.response.Meta;
import de.daxu.swamp.common.response.Response;
import de.daxu.swamp.common.response.Response.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static de.daxu.swamp.common.response.Response.Builder.aResponse;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler( Exception.class )
    public ResponseEntity<Response> handle( Exception exception ) {
        Builder response = aResponse();

        response.withMeta( Meta.serverError() );
        response.withErrors( exception.getMessage() );

        return new ResponseEntity<>( response.build(), HttpStatus.OK );
    }
}
