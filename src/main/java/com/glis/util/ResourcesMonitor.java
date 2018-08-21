package com.glis.util;

import com.glis.domain.DomainController;
import com.glis.log.ChannelLogController;
import com.sun.management.OperatingSystemMXBean;
import lombok.Data;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Glis
 */
public class ResourcesMonitor {
    /**
     * The delay between monitoring the resources usage.
     */
    private final static int RESOURCE_MONITOR_DELAY = 1;

    /**
     * The delay between calculating and writing the resources usage.
     */
    private final static int WRITE_RESOURCE_DELAY = 10;

    /**
     * The logger that logs all actions.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * The {@link java.util.concurrent.Executor} that will run the slave.
     */
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    /**
     * The values that have been logged since the last calculation.
     */
    private final List<ReadValuePair> readValues = new ArrayList<>();

    /**
     * The {@link ChannelLogController} to write the logs to.
     */
    private final ChannelLogController channelLogController;

    /**
     * @param domainController The {@link DomainController} for this instance.
     */
    public ResourcesMonitor(final DomainController domainController) {
        this.channelLogController = domainController.getChannelLogController();
    }

    /**
     * Starts the {@link ResourcesMonitor}.
     */
    public void start() {
        executor.scheduleAtFixedRate(new ResourcesMonitorSlave(readValues), 0, RESOURCE_MONITOR_DELAY, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(new WriteResourcesSlave(readValues), 0, WRITE_RESOURCE_DELAY, TimeUnit.SECONDS);
    }

    @Data
    private class ReadValuePair {
        /**
         * The memory usage monitored.
         */
        private final double memoryUsage;

        /**
         * The CPU usage monitored.
         */
        private final double cpuUsage;
    }

    /**
     * Monitors the current resources and writes them to an array.
     */
    @Data
    class ResourcesMonitorSlave implements Runnable {
        /**
         * The {@link List} to write the values to.
         */
        private final List<ReadValuePair> readValues;

        /**
         * Logs the current CPU usage.
         */
        @Override
        public void run() {
            try {
                OperatingSystemMXBean osMBean = ManagementFactory.newPlatformMXBeanProxy(ManagementFactory.getPlatformMBeanServer(), ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
                final double totalMemory = osMBean.getTotalPhysicalMemorySize();
                final double usedMemory = totalMemory - osMBean.getFreePhysicalMemorySize();
                final double percentageUsed = (usedMemory / totalMemory) * 100;
                readValues.add(new ReadValuePair(percentageUsed, osMBean.getSystemCpuLoad() * 100));
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Something went wrong gathering resource data.", e);
            }
        }
    }

    /**
     * Writes the used resources to the database.
     */
    @Data
    class WriteResourcesSlave implements Runnable {
        /**
         * The {@link List} to write the values to.
         */
        private final List<ReadValuePair> readValues;

        /**
         * Logs the current CPU usage.
         */
        @Override
        public void run() {
            try {
                final List<ReadValuePair> hardcopy = new ArrayList<>(readValues);
                readValues.clear();
                final OptionalDouble averageCpuUsage = hardcopy.stream().mapToDouble(ReadValuePair::getCpuUsage).average();
                final OptionalDouble averageMemoryUsage = hardcopy.stream().mapToDouble(ReadValuePair::getMemoryUsage).average();
                channelLogController.resourceUsage(averageMemoryUsage.orElse(0.0), averageCpuUsage.orElse(0.0));
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Something went wrong writing resource data.", e);
            }
        }
    }
}
