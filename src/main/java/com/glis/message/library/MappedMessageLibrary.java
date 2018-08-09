package com.glis.message.library;

import com.glis.exceptions.InvalidTypeException;
import com.glis.message.Message;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Glis
 */
public final class MappedMessageLibrary implements MessageLibrary {
    /**
     * A {@link Map} that holds all possible network message types.
     * All types are mapped by the {@link Message#getTypeIdentifier()}.
     */
    private final Map<String, Class<? extends Message>> messageTypes = new HashMap<>();

    /**
     * @param messages All network messages that need to be initialized.
     */
    public MappedMessageLibrary(Message... messages) {
        for (Message message : messages) {
            messageTypes.put(message.getTypeIdentifier(), message.getClass());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<? extends Message> getClassForIdentifier(final @NonNull String identifier) throws InvalidTypeException {
        if(identifier == null) {
            throw new InvalidTypeException("The identifier cannot be null.");
        }
        if(!messageTypes.containsKey(identifier)) {
            throw new InvalidTypeException("The identifier '" + identifier + "' does not seem to have an associated type.");
        }
        return messageTypes.get(identifier);
    }
}
