package com.glis.output;

import com.glis.message.Message;

/**
 * @author Glis
 */
public interface MessageSender {
    /**
     * Enables to send a message on the network.
     * @param message A message to send on the network.
     */
    void send(Message message);

    /**
     * Adds a {@link Runnable} to execute on closing.
     *
     * @param runnable The {@link Runnable} to execute on closing.
     */
    void onClose(Runnable runnable);
}
