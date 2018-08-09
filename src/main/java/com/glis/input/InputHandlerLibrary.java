package com.glis.input;

import com.glis.input.handlers.InputHandler;
import com.glis.util.HandlerLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Glis
 */
@Service
public final class InputHandlerLibrary implements HandlerLibrary<InputHandler, Object> {
    /**
     * A {@link List} of all the {@link InputHandler}s.
     */
    private final Set<InputHandler> inputHandlers;

    /**
     * @param inputHandlers All inputHandlers that are registered.
     */
    @Autowired
    public InputHandlerLibrary(InputHandler... inputHandlers) {
        this.inputHandlers = Arrays.stream(inputHandlers).collect(Collectors.toSet());
    }

    /**
     * A default constructor.
     */
    public InputHandlerLibrary() {
        inputHandlers = new LinkedHashSet<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerHandlers(InputHandler... inputHandler) {
        inputHandlers.addAll(Arrays.asList(inputHandler));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deregisterHandlers(InputHandler... inputHandler) {
        inputHandlers.removeAll(Arrays.asList(inputHandler));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<InputHandler> getHandlersForObject(Object object) {
        return inputHandlers
                .stream()
                .filter(inputHandler -> inputHandler.canHandle(object))
                .collect(Collectors.toSet());
    }
}
