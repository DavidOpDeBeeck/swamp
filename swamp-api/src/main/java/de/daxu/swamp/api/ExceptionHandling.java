package de.daxu.swamp.api;

import de.daxu.swamp.common.web.exception.NotFoundException;
import de.daxu.swamp.common.web.response.Response;
import de.daxu.swamp.common.web.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandling {

    private final ResponseFactory response;

    @Autowired
    public ExceptionHandling( ResponseFactory responseFactory ) {
        this.response = responseFactory;
    }

    @ResponseBody
    @ExceptionHandler( Exception.class )
    public Response handle( Exception exception ) {
        return response.serverError( exception.getMessage() );
    }

    @ResponseBody
    @ExceptionHandler( NotFoundException.class )
    public Response handle( NotFoundException exception ) {
        return response.notFound( exception.getMessage() );
    }
}
