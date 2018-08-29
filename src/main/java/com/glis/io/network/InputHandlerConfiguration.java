package com.glis.io.network;

import com.glis.domain.DomainController;
import com.glis.io.network.input.handlers.*;
import com.glis.log.input.MessageLogInputHandler;
import com.glis.util.HandlerLibrary;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Glis
 */
@Configuration
public class InputHandlerConfiguration {

    @Bean
    public SmartInitializingSingleton registerInputHandlers(final HandlerLibrary<InputHandler, Object> handlerLibrary, final DomainController domainController) {
        return () -> handlerLibrary.registerHandlers(new InputHandler[]{
                new CommandLineEchoInputHandler(),
                new ShutdownInputHandler(domainController),
                new ProfileInputHandler(domainController),
                new MessageLogInputHandler(domainController)
        });
    }
}
