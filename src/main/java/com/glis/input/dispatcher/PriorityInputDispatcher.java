package com.glis.input.dispatcher;

import com.glis.exceptions.UnknownHandlerException;
import com.glis.input.MetaData;
import com.glis.util.HandlerLibrary;
import com.glis.input.handlers.HandlerPriority;
import com.glis.input.handlers.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Glis
 */
@Service
public class PriorityInputDispatcher implements InputDispatcher {
    /**
     * The {@link HandlerLibrary} that is being used to lookup all the handlers.
     */
    private final HandlerLibrary<InputHandler, Object> handlerLibrary;

    /**
     * @param handlerLibrary The {@link HandlerLibrary} that is being used to lookup all the handlers.
     */
    @Autowired
    public PriorityInputDispatcher(HandlerLibrary<InputHandler, Object> handlerLibrary) {
        this.handlerLibrary = handlerLibrary;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("all")
    public void dispatchToHandler(Object o, MetaData metaData) throws UnknownHandlerException {
        if(o == null) {
            throw new UnknownHandlerException("Null cannot be given as a parameter.");
        }
        final Collection<InputHandler> handlers = handlerLibrary.getHandlersForObject(o);
        if(handlers.isEmpty()) {
            throw new UnknownHandlerException("There is no handler available for an object of type " + o.getClass().getSimpleName() + ".");
        }
        Collection<InputHandler> inputHandlers = handlers.stream().sorted((o1, o2) -> {
            final HandlerPriority.Priority priority1 = o1.getClass().isAnnotationPresent(HandlerPriority.class) ? o1.getClass().getAnnotation(HandlerPriority.class).value() : HandlerPriority.Priority.MEDIUM;
            final HandlerPriority.Priority priority2 = o2.getClass().isAnnotationPresent(HandlerPriority.class) ? o2.getClass().getAnnotation(HandlerPriority.class).value() : HandlerPriority.Priority.MEDIUM;
            return -Integer.compare(priority1.ordinal(), priority2.ordinal());
        }).collect(Collectors.toSet());
        for (InputHandler inputHandler : inputHandlers) {
            final Object handlerResult = inputHandler.handleInput(inputHandler.convert(o), metaData);
            if(handlerResult != null) {
                dispatchToHandler(handlerResult, metaData);
            }
        }
    }
}
