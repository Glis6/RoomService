package com.glis.input.handlers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Glis
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HandlerPriority {
    /**
     * @return The priority value the handler has.
     */
    Priority value();

    /**
     * An enum of all priorities.
     */
    enum Priority {
        /**
         * Monitoring, mainly used to do logging.
         */
        MONITOR,

        /**
         * Highest priority. Gets handled first.
         */
        HIGH,

        /**
         * Medium priority, gets handled right after the {@link Priority#HIGH} priority.
         */
        MEDIUM,

        /**
         * Lowest priority, gets handled last.
         */
        LOW
    }
}
