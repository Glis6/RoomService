package com.glis.io.network.networktype;

import com.glis.io.network.input.library.MessageLibrary;

/**
 * @author Glis
 */
public final class ServerDownstream extends Downstream {
    /**
     * @param messageLibrary The {@link MessageLibrary} that holds the types of messages that can arrive.
     * @param customNetworkTypeHandler The {@link CustomNetworkTypeHandler} that handles the custom handling.
     */
    public ServerDownstream(MessageLibrary messageLibrary, CustomNetworkTypeHandler customNetworkTypeHandler) {
        super(customNetworkTypeHandler, messageLibrary);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTypeIdentifier() {
        return 1;
    }
}
