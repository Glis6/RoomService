package com.glis.io.network.networktype;

import com.glis.io.network.input.library.MessageLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Glis
 */
@Component
public final class ServerDownstream extends Downstream {
    /**
     * @param messageLibrary The {@link MessageLibrary} that holds the types of messages that can arrive.
     * @param customNetworkTypeHandler The {@link CustomNetworkTypeHandler} that handles the custom handling.
     */
    @Autowired
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
