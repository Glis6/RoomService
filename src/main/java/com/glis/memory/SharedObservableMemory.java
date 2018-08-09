package com.glis.memory;

import io.reactivex.Observable;
import io.reactivex.subjects.Subject;

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
     * Gets or creates a subject for the given key.
     *
     * @param key The key that we're looking for.
     * @param <T> The type of the object.
     * @return The current state of the object.
     */
    <T> Subject<T> getSubject(K key, Class<T> clazz) throws Exception;

    /**
     * Deletes the observable and unlinks everything listening to it.
     *
     * @param key The key that is being deleted.
     */
    void delete(K key) throws Exception;
}
