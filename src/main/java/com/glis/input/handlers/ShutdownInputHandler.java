package com.glis.input.handlers;

import com.glis.DomainController;
import com.glis.input.MetaData;
import com.glis.input.handlers.InputHandler;
import com.glis.message.ShutdownNetworkMessage;
import org.springframework.stereotype.Component;

/**
 * @author Glis
 */
@Component
public class ShutdownInputHandler implements InputHandler<ShutdownNetworkMessage> {
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
        return o instanceof ShutdownNetworkMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShutdownNetworkMessage convert(Object o) {
        return (ShutdownNetworkMessage)o;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object handleInput(ShutdownNetworkMessage input, MetaData metaData) {
        domainController.shutdown(input.getReason());
        return null;
    }
}
