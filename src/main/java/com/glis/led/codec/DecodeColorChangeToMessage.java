package com.glis.led.codec;

import com.glis.message.LedStripColorChangeMessage;
import com.glis.message.Message;
import org.springframework.stereotype.Component;

/**
 * @author Glis
 */
@Component
public class DecodeColorChangeToMessage implements DecodeLedChangeOutputToMessage {
    /**
     * The amount of parts that we require.
     */
    private final static int PARTS_REQUIRED = 3;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return "solidColor";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Message decode(String[] parts) throws IllegalArgumentException {
        if (parts.length < PARTS_REQUIRED) {
            throw new IllegalArgumentException(getClass().getSimpleName() + " requires " + PARTS_REQUIRED + " parts. Got " + parts.length + " parts.");
        }
        return new LedStripColorChangeMessage(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
    }
}
