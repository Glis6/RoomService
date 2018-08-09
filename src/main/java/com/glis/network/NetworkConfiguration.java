package com.glis.network;

import com.glis.message.Message;
import com.glis.message.PlaySongMessage;
import com.glis.message.ShutdownMessage;
import com.glis.message.SubscribeMessage;
import com.glis.message.library.MappedMessageLibrary;
import com.glis.message.library.MessageLibrary;
import com.glis.network.networktype.Both;
import com.glis.network.networktype.Downstream;
import com.glis.network.networktype.Upstream;
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
                new SubscribeMessage(),
                new PlaySongMessage()
        };
    }

    @Bean
    public MessageLibrary messageLibrary(Message... messages) {
        return new MappedMessageLibrary(messages);
    }

    @Bean
    public Upstream upstream() {
        return new Upstream();
    }

    @Bean
    public Downstream downstream(final MessageLibrary messageLibrary) {
        return new Downstream(messageLibrary);
    }

    @Bean
    public Both both(final Upstream upstream, final Downstream downstream) {
        return new Both(upstream, downstream);
    }
}
