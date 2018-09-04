package com.glis.io.network.networktype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Glis
 */
@Component
public final class ServerUpstream extends Upstream {
    /**
     * @param customNetworkTypeHandler The {@link CustomNetworkTypeHandler} that handles the custom handling.
     */
    @Autowired
    public ServerUpstream(CustomNetworkTypeHandler customNetworkTypeHandler) {
        super(customNetworkTypeHandler);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTypeIdentifier() {
        return 0;
    }
}
