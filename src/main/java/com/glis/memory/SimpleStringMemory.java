package com.glis.memory;

import lombok.Data;

/**
 * @author Glis
 */
@Data
public final class SimpleStringMemory implements Memory<String, String> {
    /**
     * The {@link SharedMemory} that we're using.
     */
    private final SharedMemory<String> sharedMemory = new MappedSharedMemory<>();

    /**
     * The {@link SharedObservableMemory} that we're using.
     */
    private final SharedObservableMemory<String> sharedObservableMemory = new MappedSharedObservableMemory<>();
}
