package com.glis.output;

import com.glis.message.NetworkMessage;

/**
 * @author Glis
 */
public interface MessageSender {
    /**
     * Enables to send a message on the network.
     * @param networkMessage A message to send on the network.
     */
    void send(NetworkMessage networkMessage);

    /**
     * Adds a {@link Runnable} to execute on closing.
     *
     * @param runnable The {@link Runnable} to execute on closing.
     */
    void onClose(Runnable runnable);
}
