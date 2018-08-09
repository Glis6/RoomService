package com.glis.memory;

import com.glis.exceptions.InvalidKeyException;
import com.glis.exceptions.KeyTypeException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Glis
 */
public final class MappedSharedMemory<K> implements SharedMemory<K> {
    /**
     * The map that we're storing all the data in.
     */
    private final Map<K, Object> map;

    /**
     * A default constructor.
     */
    public MappedSharedMemory() {
        this.map =  new HashMap<>();
    }

    /**
     * A constructor for testing purposes only.
     *
     * @param map The map to inject.
     */
    MappedSharedMemory(Map<K, Object> map) {
        this.map = map;
    }

    /**
     * @param key The key that is being checked.
     * @return The key as a String.
     */
    private String keyCheck(String key) throws KeyTypeException {
        return key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T getState(K key, Class<T> clazz) throws KeyTypeException, InvalidKeyException, ClassCastException {
        if(key == null) {
            throw new KeyTypeException("Key cannot be null.");
        }
        if(clazz == null) {
            throw new ClassCastException("Class cannot be null.");
        }
        if(!map.containsKey(key)) {
            throw new InvalidKeyException("The map doesn't contain the given key.");
        }
        Object mapObject =  map.get(key);
        if(!clazz.isInstance(mapObject)) {
            throw new ClassCastException("The object is of the wrong class. Type: " + mapObject.getClass().getTypeName() + "; Required type: " + clazz.getTypeName());
        }
        return (T)mapObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void setState(K key, T state) throws Exception {
        if(key == null) {
            throw new KeyTypeException("Key cannot be null.");
        }
        if(state == null) {
            throw new Exception("Please use the SharedMemory#delete method to remove keys.");
        }
        map.put(key, state);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(K key) throws KeyTypeException {
        if(key == null) {
            throw new KeyTypeException("Key cannot be null.");
        }
        map.remove(key);
    }
}
