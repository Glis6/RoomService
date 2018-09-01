package com.glis.util;

import com.glis.domain.DomainController;
import com.glis.domain.memory.Memory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author Glis
 */
public class ServerMonitor {
    /**
     * The delay between writing the threads to the memory.
     */
    private final static int WRITE_THREADS_DELAY = 5;

    /**
     * The logger that logs all actions.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * The {@link java.util.concurrent.Executor} that will run the slave.
     */
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    /**
     * The {@link Memory} to write the data to.
     */
    private final DomainController domainController;

    /**
     * @param domainController The {@link DomainController} for this instance.
     */
    public ServerMonitor(final DomainController domainController) {
        this.domainController = domainController;
    }

    /**
     * Starts the {@link ServerMonitor}.
     */
    public void start() {
        logger.info("Server status is being monitored.");
        executor.scheduleAtFixedRate(new MonitorSlave(), 0, WRITE_THREADS_DELAY, TimeUnit.SECONDS);
    }

    /**
     * The slave that writes the threads to the memory.
     */
    class MonitorSlave implements Runnable {
        /**
         * Logs the current CPU usage.
         */
        @Override
        public void run() {
            domainController.writeServerMonitoring();
        }
    }
}
