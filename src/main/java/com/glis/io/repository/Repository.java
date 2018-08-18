package com.glis.io.repository;

import io.reactivex.Observable;

import java.util.Optional;

/**
 * @author Glis
 */
public interface Repository<T> {
    /**
     * @param key The key to look up.
     * @return An {@link Observable} created from looking up the key.
     */
    Observable<Optional<T>> get(String key) throws Exception;

    /**
     * @param object The object to insert into the database.
     */
    void insert(T object) throws Exception;
}
