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
    final static String RGB_VALUES_KEY = LED_PREFIX + "rgbValues";

    /**
     * The key for listening to led color changes.
     */
    final static String LED_COLOR_CHANGE_REQUEST_STRING = LED_PREFIX + "change";
}
