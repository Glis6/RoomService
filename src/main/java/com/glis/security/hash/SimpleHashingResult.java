package com.glis.security.hash;

import lombok.Data;

/**
 * @author Glis
 */
@Data
public class SimpleHashingResult implements HashingResult {
    /**
     * The result from hashing the original string.
     */
    private final String hashedString;

    /**
     * The salt used to hash to the {@link HashingResult#getHashedString()}.
     */
    private final String salt;
}
