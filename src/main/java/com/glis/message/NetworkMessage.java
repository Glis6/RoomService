package com.glis.message;

/**
 * @author Glis
 */
public interface NetworkMessage {
    /**
     * The opcode for the network message.
     */
    default String getTypeIdentifier() {
        return getClass().getSimpleName();
    }
}
