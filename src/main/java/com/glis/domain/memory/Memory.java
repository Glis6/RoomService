package com.glis.domain.memory;

/**
 * @author Glis
 */
public interface Memory<K, V> {
    /**
     * @return The {@link SharedMemory} that is stored.
     */
    SharedMemory<K> getSharedMemory();

    /**
     * @return The {@link SharedObservableMemory} that is stored.
     */
    SharedObservableMemory<V> getSharedObservableMemory();
}
