package com.glis.memory;

import io.reactivex.Observable;

/**
 * @author Glis
 */
public interface SharedObservableMemory<K> {
    /**
     * Gets or creates an observable for the given key.
     *
     * @param key The key that we're looking for.
     * @param <T> The type of the object.
     * @return The current state of the object.
     */
    <T> Observable<T> getObservable(K key, Class<T> clazz) throws Exception;
    /**
     * Puts a value in the memory.
     *
     * @param key The key that we're looking for.
     * @param value The value that we're putting in.
     * @param <T> The type of the object.
     */
    <T> void setValue(K key, T value) throws Exception;

    /**
     * Deletes the observable and unlinks everything listening to it.
     *
     * @param key The key that is being deleted.
     */
    void delete(K key) throws Exception;
}
