package de.daxu.swamp.common.axon;

import de.daxu.swamp.common.validator.ValidationException;
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
            logger.error("Command: {}, validation exception: {}", commandMessage.getCommandName(), exception.getMessage());
        } catch(Exception exception) {
            exception.printStackTrace();
            logger.error("Command: {}, exception: {}", commandMessage.getCommandName(), exception.getMessage());
        }
        return null;
    }
}
