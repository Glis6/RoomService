package com.glis.network;

import com.glis.message.NetworkMessage;
import com.glis.message.PlaySongNetworkMessage;
import com.glis.message.ShutdownNetworkMessage;
import com.glis.message.SubscribeNetworkMessage;
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
    public NetworkMessage[] networkMessages() {
        return new NetworkMessage[]{
                new ShutdownNetworkMessage(),
                new SubscribeNetworkMessage(),
                new PlaySongNetworkMessage()
        };
    }

    @Bean
    public MessageLibrary messageLibrary(NetworkMessage... networkMessages) {
        return new MappedMessageLibrary(networkMessages);
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
