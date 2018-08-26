package com.glis.led;

import com.glis.domain.memory.Memory;
import io.reactivex.Observable;
import lombok.NonNull;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.glis.led.LedConstants.RGB_VALUES_KEY;

/**
 * @author Glis
 */
public final class LedController {
    /**
     * The string that is used to split the command in multiple parts.
     */
    private final static String COMMAND_SPLIT_STRING = ";";

    /**
     * The {@link Logger} to use for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * The {@link Memory} of the application.
     */
    private final Memory<String, String> memory;

    /**
     * @param memory The {@link Memory} of the application.
     */
    public LedController(Memory<String, String> memory) {
        this.memory = memory;
    }

    /**
     * @param rgbValues The {@link RgbValues} to set.
     */
    public void setRgbSettings(final @NonNull RgbValues rgbValues) {
        try {
            logger.info("Setting rgb values to '" + rgbValues.toString() + "'.");
            memory.getSharedObservableMemory().setValue(RGB_VALUES_KEY, String.format("%s;%s;%s", rgbValues.getRed(), rgbValues.getGreen(), rgbValues.getBlue()));
        } catch(ClassCastException e) {
            logger.log(Level.WARNING, "'" + RGB_VALUES_KEY + "' is saved in a wrong state.", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong setting the new rgb settings.", e);
        }
    }

    /**
     * @return The current settings for the RGB's.
     */
    public Observable<Optional<RgbValues>> getRgbSettingsObservable() throws Exception {
        return memory.getSharedObservableMemory().getObservable(RGB_VALUES_KEY, String.class).map(optionalString -> {
            if (!optionalString.isPresent()) {
                return Optional.empty();
            }
            final String string = optionalString.get();
            final String[] parts = string.split(COMMAND_SPLIT_STRING);
            int red = 0;
            int green = 0;
            int blue = 0;
            for (int i = 0; i < parts.length; i++) {
                try {
                    final int value = Integer.parseInt(parts[i]);
                    switch (i) {
                        case 0:
                            red = value;
                            break;
                        case 1:
                            green = value;
                            break;
                        case 2:
                            blue = value;
                            break;
                    }
                } catch (Exception ignored) {
                }
            }
            return Optional.of(new RgbValues(red, green, blue));
        });
    }
}
