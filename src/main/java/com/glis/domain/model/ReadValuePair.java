package com.glis.domain.model;

import lombok.Data;

/**
 * @author Glis
 */
@Data
public class ReadValuePair {
    /**
     * The memory usage monitored.
     */
    private final double memoryUsage;

    /**
     * The CPU usage monitored.
     */
    private final double cpuUsage;
}
