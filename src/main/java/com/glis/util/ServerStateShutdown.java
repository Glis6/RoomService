package com.glis.util;

import com.glis.domain.memory.Memory;
import com.glis.domain.memory.SharedObservableMemory;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Glis
 */
@Component
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
    @Autowired
    public ServerStateShutdown(@NonNull final Memory<String, String> memory) {
        super(ServerStateShutdown.class.getSimpleName());
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
            memory.setValue("server_connections", "");
            memory.setValue("system_thread_activity", "");
            memory.setValue("system_resources_usage", "");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong saving the server state as offline.", e);
        }
        logger.info("Server state set to 'offline'.");
    }
}
