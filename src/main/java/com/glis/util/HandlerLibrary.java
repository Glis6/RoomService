package com.glis.util;

import java.util.Collection;

/**
 * @author Glis
 */
public interface HandlerLibrary<T, K> {
    /**
     * @param handlers The handlers to be added if it they don't exist yet.
     */
    void registerHandlers(T[] handlers);

    /**
     * @param handlers The handlers to be removed if they exists.
     */
    void deregisterHandlers(T[] handlers);

    /**
     * @param object The object that we're checking.
     * @return All handlers that handle with the object.
     */
    Collection<T> getHandlersForObject(K object);
}
