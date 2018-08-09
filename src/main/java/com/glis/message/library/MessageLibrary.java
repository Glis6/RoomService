package com.glis.message.library;

import com.glis.exceptions.InvalidTypeException;
import com.glis.message.NetworkMessage;
import lombok.NonNull;

/**
 * @author Glis
 */
public interface MessageLibrary {
    /**
     * @param identifier The identifier that we're looking for.
     * @return The class that the identifier is valid for.
     */
    Class<? extends NetworkMessage> getClassForIdentifier(@NonNull String identifier) throws InvalidTypeException;
}
