package com.glis.io.network;

import com.glis.io.network.input.library.MappedMessageLibrary;
import com.glis.io.network.input.library.MessageLibrary;
import com.glis.message.Message;
import com.glis.message.ProfileMessage;
import com.glis.message.ShutdownMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Glis
 */
@Configuration
public class NetworkConfiguration {
    @Bean
    public Message[] networkMessages() {
        return new Message[]{
                new ShutdownMessage(),
                new ProfileMessage()
        };
    }

    @Bean
    public MessageLibrary messageLibrary(Message... messages) {
        return new MappedMessageLibrary(messages);
    }
}
