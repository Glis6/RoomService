package com.glis;

import com.glis.domain.DomainController;
import com.glis.io.firebase.FirebaseMemory;
import com.glis.io.firebase.FirebaseRepositoryManager;
import com.glis.io.network.InputHandlerConfiguration;
import com.glis.io.network.input.InputHandlerLibrary;
import com.glis.io.network.input.dispatcher.InputDispatcher;
import com.glis.io.network.input.dispatcher.PriorityInputDispatcher;
import com.glis.io.network.input.handlers.InputHandler;
import com.glis.domain.memory.Memory;
import com.glis.domain.memory.StringMemory;
import com.glis.io.network.NetworkConfiguration;
import com.glis.io.network.OutputHandlerConfiguration;
import com.glis.io.network.output.OutputHandlerLibrary;
import com.glis.io.network.output.dispatcher.OutputDispatcher;
import com.glis.io.network.output.dispatcher.SimpleOutputDispatcher;
import com.glis.io.network.output.handlers.OutputHandler;
import com.glis.io.repository.RepositoryManager;
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
    public DomainController domainController(final InputDispatcher inputDispatcher, final OutputDispatcher outputDispatcher, final Memory<String, String> memory, RepositoryManager repositoryManager) throws Exception {
        return new DomainController(inputDispatcher, outputDispatcher, memory, repositoryManager);
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

    @Bean
    public RepositoryManager repositoryManager() {
        return new FirebaseRepositoryManager();
    }
}
