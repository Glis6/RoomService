package com.glis.output;

import com.glis.DomainController;
import com.glis.output.handlers.CurrentSongOutputHandler;
import com.glis.output.handlers.OutputHandler;
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
    public SmartInitializingSingleton registerOutputHandlers(final HandlerLibrary<OutputHandler, String> handlerLibrary, final DomainController domainController) {
        return () -> handlerLibrary.registerHandlers(new OutputHandler[]{
                new CurrentSongOutputHandler(domainController)
        });
    }
}
