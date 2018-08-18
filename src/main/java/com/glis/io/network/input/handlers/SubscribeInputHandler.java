package com.glis.io.network.input.handlers;

import com.glis.domain.DomainController;
import com.glis.exceptions.UnknownHandlerException;
import com.glis.io.network.input.MetaData;
import com.glis.message.SubscribeMessage;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * @author Glis
 */
@Component
public class SubscribeInputHandler extends DomainControllerRequiredInputHandler<SubscribeMessage> {
    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * @param domainController The {@link DomainController} for this instance.
     */
    public SubscribeInputHandler(DomainController domainController) {
        super(domainController);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canHandle(Object o) {
        return o instanceof SubscribeMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubscribeMessage convert(Object o) {
        return (SubscribeMessage)o;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object handleInput(SubscribeMessage input, MetaData metaData) {
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
