package com.glis.io.network.input.handlers;

import com.glis.domain.DomainController;
import com.glis.io.network.input.MetaData;
import com.glis.message.ShutdownMessage;
import org.springframework.stereotype.Component;

/**
 * @author Glis
 */
@Component
public class ShutdownInputHandler extends DomainControllerRequiredInputHandler<ShutdownMessage> {
    /**
     * @param domainController The {@link DomainController} to use to shut everything down.
     */
    public ShutdownInputHandler(DomainController domainController) {
        super(domainController);
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
