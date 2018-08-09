package com.glis.input.handlers;

import com.glis.DomainController;
import com.glis.exceptions.UnknownHandlerException;
import com.glis.input.MetaData;
import com.glis.message.SubscribeNetworkMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * @author Glis
 */
@Component
public class SubscribeInputHandler implements InputHandler<SubscribeNetworkMessage> {
    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * The {@link DomainController} for this instance.
     */
    private final DomainController domainController;

    /**
     * @param domainController The {@link DomainController} for this instance.
     */
    @Autowired
    public SubscribeInputHandler(DomainController domainController) {
        this.domainController = domainController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canHandle(Object o) {
        return o instanceof SubscribeNetworkMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubscribeNetworkMessage convert(Object o) {
        return (SubscribeNetworkMessage)o;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object handleInput(SubscribeNetworkMessage input, MetaData metaData) {
        for (String subscribeParameter : input.getSubscribeParameters()) {
            try {
                domainController.handleOutput(subscribeParameter, metaData.getInputSender());
            } catch (UnknownHandlerException e) {
                logger.warning("There is no handler available for the parameter '" + subscribeParameter + "'.");
            }
        }
        return null;
    }
}
