package com.glis.output.dispatcher;

import com.glis.exceptions.UnknownHandlerException;
import com.glis.output.MessageSender;
import com.glis.output.handlers.OutputHandler;
import com.glis.util.HandlerLibrary;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Glis
 */
@Service
public final class SimpleOutputDispatcher implements OutputDispatcher {
    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * The {@link HandlerLibrary} used to lookup the handlers.
     */
    private final HandlerLibrary<OutputHandler, String> handlerLibrary;

    /**
     * @param handlerLibrary The {@link HandlerLibrary} used to lookup the handlers.
     */
    public SimpleOutputDispatcher(HandlerLibrary<OutputHandler, String> handlerLibrary) {
        this.handlerLibrary = handlerLibrary;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispatchToHandler(String identifier, MessageSender messageSender) throws UnknownHandlerException {
        Collection<OutputHandler> outputHandlers = handlerLibrary.getHandlersForObject(identifier);
        if(outputHandlers.isEmpty()) {
            throw new UnknownHandlerException("There is no handler that accepts '" + identifier + "' as identifier.");
        }
        outputHandlers.forEach(outputHandler -> {
            try {
                outputHandler.handle(messageSender);
            } catch(Exception e) {
                logger.log(Level.SEVERE, "Something went wrong while dispatching to handler.", e);
            }
        });
    }
}
