package com.glis;

import com.glis.firebase.FirebaseMemory;
import com.glis.input.InputHandlerConfiguration;
import com.glis.input.InputHandlerLibrary;
import com.glis.input.dispatcher.InputDispatcher;
import com.glis.input.dispatcher.PriorityInputDispatcher;
import com.glis.input.handlers.InputHandler;
import com.glis.memory.Memory;
import com.glis.memory.StringMemory;
import com.glis.network.NetworkConfiguration;
import com.glis.output.OutputHandlerConfiguration;
import com.glis.output.OutputHandlerLibrary;
import com.glis.output.dispatcher.OutputDispatcher;
import com.glis.output.dispatcher.SimpleOutputDispatcher;
import com.glis.output.handlers.OutputHandler;
import com.glis.util.HandlerLibrary;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Glis
 */
@Configuration
@Import({
        InputHandlerConfiguration.class,
        OutputHandlerConfiguration.class,
        NetworkConfiguration.class
})
public class SpringConfiguration {

    @Bean
    public DomainController domainController(final InputDispatcher inputDispatcher, final OutputDispatcher outputDispatcher, final Memory<String, String> memory) {
        return new DomainController(inputDispatcher, outputDispatcher, memory);
    }

    @Bean
    public InputDispatcher inputDispatcher(final HandlerLibrary<InputHandler, Object> handlerLibrary) {
        return new PriorityInputDispatcher(handlerLibrary);
    }

    @Bean
    public OutputDispatcher outputDispatcher(final HandlerLibrary<OutputHandler, String> handlerLibrary) {
        return new SimpleOutputDispatcher(handlerLibrary);
    }

    @Bean
    public HandlerLibrary<InputHandler, Object> inputHandlerLibrary() {
        return new InputHandlerLibrary();
    }

    @Bean
    public HandlerLibrary<OutputHandler, String> outputHandlerLibrary() {
        return new OutputHandlerLibrary();
    }

    @Bean
    public Memory<String, String> memory() {
        return new StringMemory(new FirebaseMemory(FirebaseDatabase.getInstance()));
    }
}
