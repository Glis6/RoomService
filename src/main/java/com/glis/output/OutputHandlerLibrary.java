package com.glis.output;

import com.glis.output.handlers.OutputHandler;
import com.glis.util.HandlerLibrary;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Glis
 */
public class OutputHandlerLibrary implements HandlerLibrary<OutputHandler, String> {
    /**
     * All handlers that are registered.
     */
    private final Set<OutputHandler> outputHandlers = new HashSet<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerHandlers(OutputHandler[] handlers) {
        outputHandlers.addAll(Arrays.asList(handlers));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deregisterHandlers(OutputHandler[] handlers) {
        outputHandlers.removeAll(Arrays.asList(handlers));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<OutputHandler> getHandlersForObject(String object) {
        return outputHandlers.stream().filter(handler -> handler.canHandle(object)).collect(Collectors.toSet());
    }
}
