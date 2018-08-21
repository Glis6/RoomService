package com.glis.log.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Glis
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceLog extends Log {
    /**
     * The load on the memory.
     */
    private final double memoryLoad;

    /**
     * The load on the CPU.
     */
    private final double cpuLoad;

    /**
     * We will set this one to seen to not give constant updates.
     */
    private final boolean seen = true;
}
