package com.glis.log.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Glis
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DisconnectionLog extends Log {
    /**
     * The remote that is connected.
     */
    private final String remoteAddress;

    /**
     * The local address that is being connected to.
     */
    private final String localAddress;

    /**
     * The name of the network that is connecting.
     */
    private final String networkName;
}
