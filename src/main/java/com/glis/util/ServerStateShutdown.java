package com.glis.util;

import com.glis.domain.memory.Memory;
import com.glis.domain.memory.SharedObservableMemory;
import lombok.NonNull;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Glis
 */
public final class ServerStateShutdown extends Thread implements ShutdownHook {
    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * The {@link SharedObservableMemory} to save the state to.
     */
    private final SharedObservableMemory<String> memory;

    /**
     * @param memory The {@link Memory} to store the offline state in.
     */
    public ServerStateShutdown(@NonNull final Memory<String, String> memory) {
        this.memory = memory.getSharedObservableMemory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        logger.info("Settings server state to 'offline'.");
        try {
            memory.setValue("server_state", "offline");
            memory.setValue("server_connections", "0");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong saving the server state as offline.", e);
        }
        logger.info("Server state set to 'offline'.");
    }
}
