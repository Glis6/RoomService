package com.glis.domain.memory;

import lombok.Data;

/**
 * @author Glis
 */
@Data
public final class StringMemory implements Memory<String, String> {
    /**
     * The {@link SharedMemory} that we're using.
     */
    private final SharedMemory<String> sharedMemory;

    /**
     * The {@link SharedObservableMemory} that we're using.
     */
    private final SharedObservableMemory<String> sharedObservableMemory;

    /**
     * @param sharedObservableMemory The {@link SharedObservableMemory} that we're using.
     */
    public StringMemory(final SharedObservableMemory<String> sharedObservableMemory) {
        this.sharedMemory = new MappedSharedMemory<>();
        this.sharedObservableMemory = sharedObservableMemory;
    }

    /**
     * @param sharedMemory The {@link SharedMemory} that we're using.
     * @param sharedObservableMemory The {@link SharedObservableMemory} that we're using.
     */
    public StringMemory(final SharedMemory<String> sharedMemory, final SharedObservableMemory<String> sharedObservableMemory) {
        this.sharedMemory = sharedMemory;
        this.sharedObservableMemory = sharedObservableMemory;
    }
}
