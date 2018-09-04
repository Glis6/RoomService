package com.glis.io.network;

import com.glis.domain.DomainController;
import com.glis.io.network.output.handlers.OutputHandler;
import com.glis.util.HandlerLibrary;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Glis
 */
@Configuration
public class OutputHandlerConfiguration {

    @Bean
    public SmartInitializingSingleton registerOutputHandlers(final HandlerLibrary<OutputHandler, String> handlerLibrary, final DomainController domainController, final OutputHandler[] outputHandlers) {
        return () -> handlerLibrary.registerHandlers(outputHandlers);
    }
}
