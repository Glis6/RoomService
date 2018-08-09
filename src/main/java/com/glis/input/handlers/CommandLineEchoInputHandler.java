package com.glis.input.handlers;

import com.glis.input.MetaData;

/**
 * @author Glis
 */
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
