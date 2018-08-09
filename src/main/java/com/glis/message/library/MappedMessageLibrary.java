package com.glis.message.library;

import com.glis.exceptions.InvalidTypeException;
import com.glis.message.NetworkMessage;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Glis
 */
public final class MappedMessageLibrary implements MessageLibrary {
    /**
     * A {@link Map} that holds all possible network message types.
     * All types are mapped by the {@link NetworkMessage#getTypeIdentifier()}.
     */
    private final Map<String, Class<? extends NetworkMessage>> messageTypes = new HashMap<>();

    /**
     * @param networkMessages All network messages that need to be initialized.
     */
    public MappedMessageLibrary(NetworkMessage... networkMessages) {
        for (NetworkMessage networkMessage : networkMessages) {
            messageTypes.put(networkMessage.getTypeIdentifier(), networkMessage.getClass());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<? extends NetworkMessage> getClassForIdentifier(final @NonNull String identifier) throws InvalidTypeException {
        if(identifier == null) {
            throw new InvalidTypeException("The identifier cannot be null.");
        }
        if(!messageTypes.containsKey(identifier)) {
            throw new InvalidTypeException("The identifier '" + identifier + "' does not seem to have an associated type.");
        }
        return messageTypes.get(identifier);
    }
}
