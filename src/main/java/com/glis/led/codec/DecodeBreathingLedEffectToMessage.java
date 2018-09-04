package com.glis.led.codec;

import com.glis.domain.Color;
import com.glis.message.LedStripBreathingEffectMessage;
import com.glis.message.Message;
import org.springframework.stereotype.Component;

/**
 * @author Glis
 */
@Component
public class DecodeBreathingLedEffectToMessage implements DecodeLedChangeOutputToMessage {
    /**
     * The amount of parts we expect for this message.
     */
    private final static int PARTS_REQUIRED = 6;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return "breathingEffect";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Message decode(String[] parts) throws IllegalArgumentException {
        if (parts.length < PARTS_REQUIRED) {
            throw new IllegalArgumentException(getClass().getSimpleName() + " requires " + PARTS_REQUIRED + " parts. Got " + parts.length + " parts.");
        }
        return new LedStripBreathingEffectMessage(new Color(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3])), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
    }
}
