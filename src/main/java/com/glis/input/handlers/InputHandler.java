package com.glis.input.handlers;

import com.glis.input.MetaData;
import kotlin.Metadata;

/**
 * @author Glis
 */
public interface InputHandler<T> {
    /**
     * Whether or not this handler can handle the given object.
     *
     * @param o The object to check.
     * @return Whether or not the handler can handle the object.
     */
    boolean canHandle(Object o);

    /**
     * @param o The object to convert.
     * @return The object converted to T.
     */
    T convert(Object o);

    /**
     * @param input The input to check.
     * @param metaData The {@link MetaData} to the message.
     * @return Whether or not the object has been fully handled and can be destroyed. Enter {@code null} as value
     *         to stop iterating.
     */
    Object handleInput(T input, MetaData metaData);
}
