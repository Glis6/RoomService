package com.glis.message;

import lombok.Data;

/**
 * @author Glis
 */
@Data
public final class ShutdownNetworkMessage implements NetworkMessage {
    /**
     * The reason for shutting down the network.
     */
    private String reason;

    /**
     * @param reason The reason for shutting down the network.
     */
    public ShutdownNetworkMessage(String reason) {
        this.reason = reason;
    }

    /**
     * A default constructor.
     */
    public ShutdownNetworkMessage() {
    }
}
