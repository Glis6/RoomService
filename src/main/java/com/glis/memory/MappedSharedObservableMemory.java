package com.glis.memory;

import com.glis.exceptions.KeyTypeException;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Glis
 */
public class MappedSharedObservableMemory<K> implements SharedObservableMemory<K> {
    /**
     * The map that all the observables are stored in.
     */
    private final Map<K, Observable> map = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Observable<T> getObservable(K key, Class<T> clazz) throws Exception {
        if(key == null) {
            throw new KeyTypeException("Key cannot be null.");
        }
        if(!map.containsKey(key)) {
            map.put(key, BehaviorSubject.create());
        }
        return (Observable<T>)map.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Subject<T> getSubject(K key, Class<T> clazz) throws Exception {
        if(key == null) {
            throw new KeyTypeException("Key cannot be null.");
        }
        if(!map.containsKey(key)) {
            map.put(key, BehaviorSubject.create());
        }
        return (Subject<T>)map.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(K key) throws Exception {
        if(!map.containsKey(key)) {
            return;
        }
        map.remove(key);
    }
}
