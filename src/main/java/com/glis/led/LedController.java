package com.glis.led;

import com.glis.domain.memory.Memory;
import com.glis.exceptions.InvalidKeyException;
import io.reactivex.Observable;
import lombok.NonNull;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.glis.led.LedConstants.LAST_RGB_SETTINGS_KEY;
import static com.glis.led.LedConstants.LED_ENABLED;
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
     * Whether or not the led's are enabled. We'll start with the assumption that they're not.
     */
    private boolean ledEnabled = false;

    /**
     * @param memory The {@link Memory} of the application.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public LedController(Memory<String, String> memory) {
        this.memory = memory;
        try {
            this.memory.getSharedObservableMemory()
                    .getObservable(LED_ENABLED, Boolean.class)
                    .subscribe(optionalEnabled -> optionalEnabled.ifPresent(enabled -> {
                        if (!enabled) {
                            try {
                                //We get the current state before we set it to nothing.
                                final RgbValues lastRgbValues = memory.getSharedMemory()
                                        .getState(LAST_RGB_SETTINGS_KEY, RgbValues.class);
                                setRgbSettings(new RgbValues(0, 0, 0));

                                //We overwrite the memory that holds the nothing with the last state, so it resumes this after.
                                memory.getSharedMemory().setState(LAST_RGB_SETTINGS_KEY, lastRgbValues);
                            } catch (InvalidKeyException ignored) {
                            } catch (Exception e) {
                                logger.log(Level.SEVERE, "Failed to set the led's to nothing and save the last state.", e);
                            }
                            ledEnabled = false;
                            return;
                        }
                        try {
                            ledEnabled = true;
                            setRgbSettings(memory.getSharedMemory()
                                    .getState(LAST_RGB_SETTINGS_KEY, RgbValues.class));
                        } catch (InvalidKeyException ignored) {
                        } catch (Exception e) {
                            logger.log(Level.SEVERE, "Could not load the '" + LAST_RGB_SETTINGS_KEY + "'.", e);
                        }
                    }));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not load the '" + LED_ENABLED + "' state.", e);
        }
    }

    /**
     * @param rgbValues The {@link RgbValues} to set.
     */
    public void setRgbSettings(final @NonNull RgbValues rgbValues) {
        //Save the requested state to the memory for possible later use.
        try {
            memory.getSharedMemory().setState(LAST_RGB_SETTINGS_KEY, rgbValues);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong saving the last rgb settings.", e);
        }

        //If we don't want the led's to be enabled then we'll just ignore setting it.
        if(!ledEnabled) {
            logger.info("Not sending led rgb values because it is disabled.");
            return;
        }
        logger.info("Setting rgb values to '" + rgbValues.toString() + "'.");
        try {
            memory.getSharedObservableMemory().setValue(RGB_VALUES_KEY, String.format("%s;%s;%s", rgbValues.getRed(), rgbValues.getGreen(), rgbValues.getBlue()));
        } catch (ClassCastException e) {
            logger.log(Level.WARNING, "'" + RGB_VALUES_KEY + "' is saved in a wrong state.", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong setting the new rgb settings.", e);
        }
    }

    /**
     * @return The current settings for the RGB's.
     */
    Observable<Optional<RgbValues>> getRgbSettingsObservable() throws Exception {
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
