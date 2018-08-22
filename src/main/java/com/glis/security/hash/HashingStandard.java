package com.glis.security.hash;

/**
 * @author Glis
 */
public interface HashingStandard {
    /**
     * @param stringToHash The string to be hashed.
     * @return The {@link HashingResult} containing the salt and hashed string.
     */
    HashingResult hash(String stringToHash) throws Exception;

    /**
     * @param stringToHash The string to be hashed.
     * @param salt         The salt to use while hashing.
     * @return The  hashed string.
     */
    String hash(String stringToHash, String salt) throws Exception;
}
