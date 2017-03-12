package de.daxu.swamp.api;

import de.daxu.swamp.common.web.exception.BadRequestException;
import de.daxu.swamp.common.web.exception.NotFoundException;
import de.daxu.swamp.common.web.response.Response;
import de.daxu.swamp.common.web.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionInterceptor {

    private final ResponseFactory response;

    @Autowired
    public ExceptionInterceptor(ResponseFactory responseFactory) {
        this.response = responseFactory;
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Response handle(Exception exception) {
        return response.serverError(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    public Response handle(NotFoundException exception) {
        return response.notFound(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    public Response handle(BadRequestException exception) {
        return response.badRequest(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handle(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();

        String[] errors = result.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .toArray(String[]::new);

        return response.badRequest(errors);
    }

    @ResponseBody
    @ExceptionHandler(TransactionSystemException.class)
    public Response handle(TransactionSystemException exception) {
        Throwable cause = exception.getCause();
        return cause instanceof RollbackException
                ? handle((RollbackException) cause)
                : handle(exception);
    }

    @ResponseBody
    @ExceptionHandler(RollbackException.class)
    public Response handle(RollbackException exception) {
        Throwable cause = exception.getCause();
        return cause instanceof ConstraintViolationException
                ? handle((ConstraintViolationException) cause)
                : handle(exception);
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public Response handle(ConstraintViolationException exception) {
        return response.badRequest(exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toArray(String[]::new));
    }
}
