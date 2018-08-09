package com.glis.input;

import com.glis.output.MessageSender;

/**
 * @author Glis
 */
public interface InputSender extends MessageSender {
    /**
     * @return The identifier for the sender of the input.
     */
    String getIdentifier();
}
