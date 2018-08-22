package com.glis.io.network.networktype;

/**
 * @author Glis
 */
public final class ServerUpstream extends Upstream {
    /**
     * @param customNetworkTypeHandler The {@link CustomNetworkTypeHandler} that handles the custom handling.
     */
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
