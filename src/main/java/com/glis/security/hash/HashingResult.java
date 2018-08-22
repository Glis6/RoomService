package com.glis.security.hash;

/**
 * @author Glis
 */
public interface HashingResult {
    /**
     * @return The result from hashing the original string.
     */
    String getHashedString();

    /**
     * @return The salt used to hash to the {@link HashingResult#getHashedString()}.
     */
    String getSalt();
}
