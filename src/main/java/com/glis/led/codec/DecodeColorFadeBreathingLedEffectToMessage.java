package com.glis.led.codec;

import com.glis.domain.Color;
import com.glis.message.LedStripColorFadeBreathingEffectMessage;
import com.glis.message.Message;
import org.springframework.stereotype.Component;

/**
 * @author Glis
 */
@Component
public class DecodeColorFadeBreathingLedEffectToMessage implements DecodeLedChangeOutputToMessage {
    /**
     * The amount of parts we expect for this message.
     */
    private final static int MINIMUM_PARTS_REQUIRED = 6;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return "colorFadeBreathingEffect";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Message decode(String[] parts) throws Exception {
        if (parts.length < MINIMUM_PARTS_REQUIRED) {
            throw new IllegalArgumentException(getClass().getSimpleName() + " requires " + MINIMUM_PARTS_REQUIRED + " at least parts. Got " + parts.length + " parts.");
        }
        if((parts.length - MINIMUM_PARTS_REQUIRED) % 3 != 0) {
            throw new IllegalArgumentException(getClass().getSimpleName() + " requires a format of '" + getType() + ";colorLength;fadeLength;r;g;b;r;b;b;...'");
        }
        final Color[] colors = new Color[(parts.length - 2) / 3];
        //Decode all the colors one by one.
        for(int i = 0; i < colors.length; i++) {
            colors[i] = new Color(
                    Integer.parseInt(parts[3 + (i * 3)]),
                    Integer.parseInt(parts[4 + (i * 3)]),
                    Integer.parseInt(parts[5 + (i * 3)])
            );
        }
        return new LedStripColorFadeBreathingEffectMessage(colors, Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }
}
