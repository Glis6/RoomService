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
     * The description of what the client is.
     */
    private String description;

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
}
