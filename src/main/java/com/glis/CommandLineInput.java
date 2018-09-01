package com.glis;

import com.glis.domain.DomainController;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Glis
 */
public class CommandLineInput extends Thread {
    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * The scanner that we're using to read the input.
     */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * The {@link DomainController} for this instance.
     */
    private final DomainController domainController;

    /**
     * @param domainController The {@link DomainController} for this instance.
     */
    public CommandLineInput(DomainController domainController) {
        super(CommandLineInput.class.getSimpleName());
        this.domainController = domainController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void start() {
        logger.info("Command line is now available.");
        super.start();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            while (true) {
                if (scanner.hasNextLine()) {
                    try {
                        final String input = scanner.nextLine();
                        logger.info("Input received as '" + input + "'.");
                        final String[] parts = input.split(" ");
                        switch (parts[0]) {
                            case "setprofile":
                                domainController.pickProfile(parts[1]);
                                break;
                        }
                    } catch (Exception e) {
                        logger.log(Level.WARNING, "An error occurred in the command line input.", e);
                    }
                }
                sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
