package com.glis.input.handlers;

import com.glis.DomainController;
import com.glis.input.MetaData;
import com.glis.message.ShutdownMessage;
import org.springframework.stereotype.Component;

/**
 * @author Glis
 */
@Component
public class ShutdownInputHandler implements InputHandler<ShutdownMessage> {
    /**
     * The {@link DomainController} to use to shut everything down.
     */
    private final DomainController domainController;

    /**
     * @param domainController The {@link DomainController} to use to shut everything down.
     */
    public ShutdownInputHandler(DomainController domainController) {
        this.domainController = domainController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canHandle(Object o) {
        return o instanceof ShutdownMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShutdownMessage convert(Object o) {
        return (ShutdownMessage)o;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object handleInput(ShutdownMessage input, MetaData metaData) {
        domainController.shutdown(input.getReason());
        return null;
    }
}
