package com.glis.message;

import lombok.Data;

/**
 * @author Glis
 */
@Data
public final class SubscribeNetworkMessage implements NetworkMessage {
    /**
     * An array of types to subscribe to.
     */
    private String[] subscribeParameters;

    /**
     * @param subscribeParameters An array of types to subscribe to.
     */
    public SubscribeNetworkMessage(String[] subscribeParameters) {
        this.subscribeParameters = subscribeParameters;
    }

    /**
     * A default constructor.
     */
    public SubscribeNetworkMessage() {
    }
}
