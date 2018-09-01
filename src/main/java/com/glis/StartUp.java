package com.glis;

import com.glis.domain.DomainController;
import com.glis.io.firebase.FirebaseBootstrap;
import com.glis.util.ShutdownHook;
import com.glis.util.ServerMonitor;

import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author Glis
 */
public class StartUp {
    /**
     * The starting point of the application.
     */
    public static void main(String[] args) throws Exception {
        Logger logger = LogManager.getLogManager().getLogger("");
        logger.info("Binding Firebase...");
        new FirebaseBootstrap().bind();

        //Adds all log handlers.
        logger.info("Adding all " + Handler.class.getSimpleName() + "s...");
        ApplicationContextProvider.getApplicationContext().getBeansOfType(Handler.class)
                .forEach((s, handler) -> logger.addHandler(handler));
        logger.info("Added all " + Handler.class.getSimpleName() + "s.");


        //Adds all shutdown hooks that the application has.
        logger.info("Adding all " + ShutdownHook.class.getSimpleName() + "s...");
        ApplicationContextProvider.getApplicationContext().getBeansOfType(ShutdownHook.class)
                .values()
                .stream()
                .filter(shutdownHook -> shutdownHook instanceof Thread)
                .map(shutdownHook -> (Thread)shutdownHook)
                .forEach(shutdownHook -> Runtime.getRuntime().addShutdownHook(shutdownHook));
        logger.info("Added all " + ShutdownHook.class.getSimpleName() + "s.");

        final DomainController domainController = ApplicationContextProvider.getApplicationContext().getBean(DomainController.class);
        new ServerMonitor(domainController).start();
        new CommandLineInput(domainController).start();
        new Bootstrap(domainController).bind();
    }
}
