package com.glis.configuration;

import com.glis.domain.memory.Memory;
import com.glis.domain.memory.StringMemory;
import com.glis.io.firebase.FirebaseMemory;
import com.glis.io.firebase.FirebasePushIdGenerator;
import com.glis.io.firebase.FirebaseRepositoryManager;
import com.glis.io.firebase.SwftvsnFirebasePushIdGenerator;
import com.glis.io.network.input.InputHandlerLibrary;
import com.glis.io.network.input.dispatcher.InputDispatcher;
import com.glis.io.network.input.dispatcher.PriorityInputDispatcher;
import com.glis.io.network.input.handlers.InputHandler;
import com.glis.io.network.output.OutputHandlerLibrary;
import com.glis.io.network.output.dispatcher.OutputDispatcher;
import com.glis.io.network.output.dispatcher.SimpleOutputDispatcher;
import com.glis.io.network.output.handlers.OutputHandler;
import com.glis.io.repository.RepositoryManager;
import com.glis.security.encryption.EncryptionStandard;
import com.glis.security.encryption.RsaEncryption;
import com.glis.security.hash.HashingStandard;
import com.glis.security.hash.Sha512Hashing;
import com.glis.util.HandlerLibrary;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Glis
 */
@Configuration
@ComponentScan(basePackages = {"com.glis"})
public class SpringConfiguration {
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
    public RepositoryManager repositoryManager(final FirebasePushIdGenerator firebasePushIdGenerator) {
        return new FirebaseRepositoryManager(firebasePushIdGenerator);
    }

    @Bean
    public FirebasePushIdGenerator firebasePushIdGenerator() {
        return new SwftvsnFirebasePushIdGenerator();
    }

    @Bean
    public HashingStandard hashingStandard() {
        return new Sha512Hashing();
    }

    @Bean
    public EncryptionStandard encryptionStandard() throws Exception {
        return new RsaEncryption();
    }
}
