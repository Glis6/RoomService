package com.glis.memory;

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
     * Creates A {@link StringMemory} with basic memory mapping.
     */
    public StringMemory() {
        this.sharedMemory = new MappedSharedMemory<>();
        this.sharedObservableMemory = new MappedSharedObservableMemory<>();
    }

    /**
     * @param sharedMemory The {@link SharedMemory} that we're using.
     */
    public StringMemory(final SharedMemory<String> sharedMemory) {
        this.sharedMemory = sharedMemory;
        this.sharedObservableMemory = new MappedSharedObservableMemory<>();
    }

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
