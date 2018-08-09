package com.glis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Glis
 */
public class ApplicationContextProvider {
    /**
     * The current application context.
     */
    private final static ApplicationContext APPLICATION_CONTEXT = new AnnotationConfigApplicationContext(SpringConfiguration.class);

    /**
     * @return The {@link ApplicationContext} for the current instance.
     */
    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }
}
