package com.glis;

import com.glis.firebase.FirebaseBootstrap;
import com.glis.util.ShutdownHook;

/**
 * @author Glis
 */
public class StartUp {
    /**
     * The starting point of the application.
     */
    public static void main(String[] args) throws Exception {
        new FirebaseBootstrap().bind();
        new Bootstrap().bind();
        //Adds all shutdown hooks that the application has.
        ApplicationContextProvider.getApplicationContext().getBeansOfType(ShutdownHook.class)
                .values()
                .stream()
                .filter(shutdownHook -> shutdownHook instanceof Thread)
                .map(shutdownHook -> (Thread)shutdownHook)
                .forEach(shutdownHook -> Runtime.getRuntime().addShutdownHook(shutdownHook));
    }
}
