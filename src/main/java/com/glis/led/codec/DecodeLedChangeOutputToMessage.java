package com.glis.led.codec;

import com.glis.message.Message;

/**
 * @author Glis
 */
public interface DecodeLedChangeOutputToMessage {
    /**
     * @return The type that this class decodes.
     */
    String getType();

    /**
     * Decodes the string to the message.
     *
     * @param parts The parts of the string to decode.
     * @return The decoded message.
     */
    Message decode(final String[] parts) throws Exception;
}
