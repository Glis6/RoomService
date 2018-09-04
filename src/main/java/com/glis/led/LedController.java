package com.glis.led;

import com.glis.domain.memory.Memory;
import com.glis.exceptions.InvalidKeyException;
import io.reactivex.Observable;
import lombok.NonNull;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.glis.led.LedConstants.LAST_LED_SETTINGS_KEY;
import static com.glis.led.LedConstants.LED_ENABLED;
import static com.glis.led.LedConstants.RGB_VALUES_KEY;

/**
 * @author Glis
 */
public final class LedController {
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
                                        .getState(LAST_LED_SETTINGS_KEY, RgbValues.class);
                                changeLedSettings("");

                                //We overwrite the memory that holds the nothing with the last state, so it resumes this after.
                                memory.getSharedMemory().setState(LAST_LED_SETTINGS_KEY, lastRgbValues);
                            } catch (InvalidKeyException ignored) {
                            } catch (Exception e) {
                                logger.log(Level.SEVERE, "Failed to set the led's to nothing and save the last state.", e);
                            }
                            ledEnabled = false;
                            return;
                        }
                        try {
                            ledEnabled = true;
                            changeLedSettings(memory.getSharedMemory()
                                    .getState(LAST_LED_SETTINGS_KEY, String.class));
                        } catch (InvalidKeyException ignored) {
                        } catch (Exception e) {
                            logger.log(Level.SEVERE, "Could not load the '" + LAST_LED_SETTINGS_KEY + "'.", e);
                        }
                    }));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not load the '" + LED_ENABLED + "' state.", e);
        }
    }

    /**
     * @param ledSettings The string that translates into the led settings.
     */
    public void changeLedSettings(final @NonNull String ledSettings) {
        //Save the requested state to the memory for possible later use.
        try {
            memory.getSharedMemory().setState(LAST_LED_SETTINGS_KEY, ledSettings);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong saving the last led settings.", e);
        }

        //If we don't want the led's to be enabled then we'll just ignore setting it.
        if(!ledEnabled) {
            logger.info("Not sending led settings because it is disabled.");
            return;
        }
        logger.info("Setting led settings to '" + ledSettings + "'.");
        try {
            memory.getSharedObservableMemory().setValue(RGB_VALUES_KEY, ledSettings);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong setting the new led settings.", e);
        }
    }

    /**
     * @return The current settings for the RGB's.
     */
    Observable<Optional<String>> getRgbSettingsObservable() throws Exception {
        return memory.getSharedObservableMemory().getObservable(RGB_VALUES_KEY, String.class);
    }
}
