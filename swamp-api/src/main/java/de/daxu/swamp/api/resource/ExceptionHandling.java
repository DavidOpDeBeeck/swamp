package de.daxu.swamp.api.resource;

import de.daxu.swamp.common.response.Response;
import de.daxu.swamp.common.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class ExceptionHandling {

    private final ResponseFactory responseFactory;

    @Autowired
    public ExceptionHandling( ResponseFactory responseFactory ) {
        this.responseFactory = responseFactory;
    }

    @ExceptionHandler( Exception.class )
    public ResponseEntity<Response> handle( Exception exception ) {
        Logger.getLogger( ExceptionHandling.class.getSimpleName() ).log( Level.SEVERE, exception.toString() );
        Logger.getLogger( ExceptionHandling.class.getSimpleName() ).log( Level.SEVERE, exception.getMessage() );
        return new ResponseEntity<>( responseFactory.serverError( exception.getMessage() ), HttpStatus.OK );
    }
}
