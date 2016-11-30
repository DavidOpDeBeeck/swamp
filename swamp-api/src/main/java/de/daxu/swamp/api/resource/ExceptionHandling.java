package de.daxu.swamp.api.resource;

import de.daxu.swamp.common.response.Response;
import de.daxu.swamp.common.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class ExceptionHandling {

    private final ResponseFactory response;

    @Autowired
    public ExceptionHandling( ResponseFactory responseFactory ) {
        this.response = responseFactory;
    }

    @ExceptionHandler( Exception.class )
    public Response handle( Exception exception ) {
        Logger.getLogger( ExceptionHandling.class.getSimpleName() ).log( Level.SEVERE, exception.toString() );
        return response.serverError( exception.getMessage() );
    }
}
