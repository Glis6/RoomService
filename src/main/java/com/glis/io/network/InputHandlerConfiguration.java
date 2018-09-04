package com.glis.io.network;

import com.glis.domain.DomainController;
import com.glis.io.network.input.handlers.InputHandler;
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
    public SmartInitializingSingleton registerInputHandlers(final HandlerLibrary<InputHandler, Object> handlerLibrary, final DomainController domainController, final InputHandler[] inputHandlers) {
        return () -> handlerLibrary.registerHandlers(inputHandlers);
    }
}
