package com.glis.network;

import lombok.Data;

/**
 * @author Glis
 */
@Data
public class AuthorizationMessage {
    /**
     * The networkType that is requested to link to.
     */
    private int networkType;

    /**
     * The name of the network that is connecting.
     */
    private String networkName;

    /**
     * @param networkType The networkType that is requested to link to.
     * @param networkName The name of the network that is connecting.
     */
    public AuthorizationMessage(int networkType, String networkName) {
        this.networkType = networkType;
        this.networkName = networkName;
    }

    /**
     * A default constructor.
     */
    public AuthorizationMessage() {
    }
}
