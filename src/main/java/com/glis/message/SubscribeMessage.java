package com.glis.message;

import lombok.Data;

/**
 * @author Glis
 */
@Data
public final class SubscribeMessage implements Message {
    /**
     * An array of types to subscribe to.
     */
    private String[] subscribeParameters;

    /**
     * @param subscribeParameters An array of types to subscribe to.
     */
    public SubscribeMessage(String[] subscribeParameters) {
        this.subscribeParameters = subscribeParameters;
    }

    /**
     * A default constructor.
     */
    public SubscribeMessage() {
    }
}
