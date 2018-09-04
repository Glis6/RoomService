package com.glis.io.network.input.handlers;

import com.glis.domain.DomainController;
import com.glis.io.network.input.MetaData;
import com.glis.message.ProfileMessage;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * @author Glis
 */
@Component
public class ProfileInputHandler extends DomainControllerRequiredInputHandler<ProfileMessage> {
    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * @param domainController The {@link DomainController} to use to set the profile.
     */
    public ProfileInputHandler(DomainController domainController) {
        super(domainController);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canHandle(Object o) {
        return o instanceof ProfileMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileMessage convert(Object o) {
        return (ProfileMessage)o;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object handleInput(ProfileMessage input, MetaData metaData) {
        logger.info("Attempting to set profile to '" + input.getIdentifier() + "'.");
        domainController.pickProfile(input.getIdentifier());
        return null;
    }
}
