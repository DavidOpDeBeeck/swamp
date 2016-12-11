package de.daxu.swamp.api.resource;

import de.daxu.swamp.common.response.Response;
import de.daxu.swamp.common.response.ResponseFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandling {

    private final ResponseFactory response;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger( ExceptionHandling.class );

    @Autowired
    public ExceptionHandling( ResponseFactory responseFactory ) {
        this.response = responseFactory;
    }

    @ResponseBody
    @ExceptionHandler( Exception.class )
    public Response handle( Exception exception ) {
        logger.warn( exception.toString() );
        return response.serverError( exception.getMessage() );
    }
}
