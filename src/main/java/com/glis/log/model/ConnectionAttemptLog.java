package com.glis.log.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Glis
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ConnectionAttemptLog extends Log {
    /**
     * The host address of the connecting client.
     */
    private final String remoteAddress;

    /**
     * The local address of the server.
     */
    private final String localAddress;
}
