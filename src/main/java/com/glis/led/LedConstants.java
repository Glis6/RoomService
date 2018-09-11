package com.glis.led;

/**
 * @author Glis
 */
class LedConstants {
    /**
     * A prefix that is added to everything to do with the LEDs.
     */
    private final static String LED_PREFIX = "led_";

    /**
     * The key for the rgn values.
     */
    final static String LED_SETTINGS_KEY = LED_PREFIX + "settings";

    /**
     * The key for the last rgb settings.
     */
    final static String LAST_LED_SETTINGS_KEY = LED_PREFIX + "lastLedSettings";

    /**
     * The key for whether or not the led's are enabled..
     */
    final static String LED_ENABLED = LED_PREFIX + "enabled";

    /**
     * The key for listening to led color changes.
     */
    final static String LED_COLOR_CHANGE_REQUEST_STRING = LED_PREFIX + "change";
}
