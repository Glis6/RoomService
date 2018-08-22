package com.glis.log.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Glis
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorizationResponseLog extends Log {
    /**
     * The authorization response provided.
     */
    private final String authorizationResponse;

    /**
     * The client id provided in the message.
     */
    private final String clientId;

    /**
     * The hashed client secret received in the message.
     */
    private final String clientSecret;

    /**
     * The remote that is attempting to connect.
     */
    private final String remoteAddress;

    /**
     * The local address that is being connected to.
     */
    private final String localAddress;
}
