package com.glis.input.dispatcher;

import com.glis.exceptions.UnknownHandlerException;
import com.glis.input.MetaData;

/**
 * @author Glis
 */
public interface InputDispatcher {
    /**
     * @param o The object that is being dispatched.
     * @param metaData The {@link MetaData} to the message.
     * @throws UnknownHandlerException Thrown if there is no handler available for the object.
     */
    void dispatchToHandler(Object o, MetaData metaData) throws UnknownHandlerException;
}
