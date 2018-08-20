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
     * The host address of the connecting host.
     */
    private final String host;

    /**
     * The port that the host is connecting to.
     */
    private final String port;
}
