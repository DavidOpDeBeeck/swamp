package de.daxu.swamp.common.axon;

import de.daxu.swamp.common.cqrs.ValidationException;
import org.axonframework.commandhandling.CommandHandlerInterceptor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.InterceptorChain;
import org.axonframework.unitofwork.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandExceptionInterceptor implements CommandHandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(CommandExceptionInterceptor.class);

    @Override
    public Object handle(CommandMessage<?> commandMessage, UnitOfWork unitOfWork, InterceptorChain interceptorChain) throws Throwable {
        try {
            return interceptorChain.proceed(commandMessage);
        } catch(ValidationException exception) {
            logger.info("Validation exception: {}", exception.getMessage());
        } catch(Exception exception) {
            logger.error("Exception: {}", exception.getMessage());
        }
        return null;
    }
}
