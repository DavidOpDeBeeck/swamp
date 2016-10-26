package de.daxu.swamp.api.resource;

import de.daxu.swamp.common.response.Response;
import de.daxu.swamp.common.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandling {

    @Autowired
    ResponseFactory responseFactory;

    @ExceptionHandler( Exception.class )
    public ResponseEntity<Response> handle( Exception exception ) {
        return new ResponseEntity<>( responseFactory.serverError( exception.getMessage() ), HttpStatus.OK );
    }
}
