package com.glis.io.network.input.handlers;

import com.glis.io.network.input.MetaData;
import org.springframework.stereotype.Component;

/**
 * @author Glis
 */
@Component
@HandlerPriority(HandlerPriority.Priority.MONITOR)
public class CommandLineEchoInputHandler implements InputHandler<Object> {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canHandle(Object o) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object convert(Object o) {
        return o;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object handleInput(Object input, MetaData metaData) {
        System.out.println("Input: " + input + " - Sender: " + metaData.getInputSender().getIdentifier());
        return null;
    }
}
