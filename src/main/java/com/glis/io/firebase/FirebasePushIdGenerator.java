package com.glis.io.firebase;

/**
 * @author Glis
 */
public interface FirebasePushIdGenerator {
    /**
     * @return A newly generated push id.
     */
    String generate();
}
