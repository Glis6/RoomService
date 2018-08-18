package com.glis.domain.memory;

/**
 * A simple class that holds current states of what objects should be like.
 *
 * @author Glis
 */
public interface SharedMemory<K> {
    /**
     * @param key The key that we're checking for.
     * @param <T> The type of the object.
     * @return The current state of the object.
     */
    <T> T getState(K key, Class<T> clazz) throws Exception;

    /**
     * @param key The key that we're storing.
     * @param state The state that we're storing.
     * @param <T> The type of the object.
     */
    <T> void setState(K key, T state) throws Exception;

    /**
     * @param key The key that is being deleted.
     */
    void delete(K key) throws Exception;
}
