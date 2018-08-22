package com.glis.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Glis
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ClientIdentity extends Model {
    /**
     * The name that is used to display the client publicly.
     */
    private String displayName;

    /**
     * What type of network the client is.
     */
    private int networkType;

    /**
     * The salt used to has the client secret.
     */
    private String salt;

    /**
     * The hashed client secret.
     */
    private String clientSecret;

    /**
     * A constructor with all parameters.
     *
     * @param displayName  The display name of the network.
     * @param networkType  The type of network connection.
     * @param salt         The salt used to hash.
     * @param clientSecret The client secret, hashed.
     */
    public ClientIdentity(String displayName, int networkType, String salt, String clientSecret) {
        this.displayName = displayName;
        this.networkType = networkType;
        this.salt = salt;
        this.clientSecret = clientSecret;
    }

    /**
     * A default constructor.
     */
    public ClientIdentity() {
    }
}
