package com.glis.io.network;

import com.glis.domain.DomainController;
import com.glis.io.network.codec.SubscribeMessageDecoder;
import com.glis.io.network.input.library.MappedMessageLibrary;
import com.glis.io.network.input.library.MessageLibrary;
import com.glis.io.network.networktype.*;
import com.glis.io.network.server.networktype.ServerBoth;
import com.glis.io.network.server.networktype.ServerDownstream;
import com.glis.io.network.server.networktype.ServerUpstream;
import com.glis.message.Message;
import com.glis.message.ProfileMessage;
import com.glis.message.ShutdownMessage;
import com.glis.message.SubscribeMessage;
import io.netty.channel.ChannelHandlerContext;
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
                new ProfileMessage()
        };
    }

    @Bean
    public MessageLibrary messageLibrary(Message... messages) {
        return new MappedMessageLibrary(messages);
    }

    @Bean
    public Upstream upstream(final DomainController domainController) {
        return new ServerUpstream(new ServerHandlerCustomNetworkTypeHandler(domainController) {
            @Override
            public void doCustom(ChannelHandlerContext channelHandlerContext, LinkData linkData) {
                super.doCustom(channelHandlerContext, linkData);
                channelHandlerContext.pipeline().addFirst(new SubscribeMessageDecoder());
            }
        });
    }

    @Bean
    public Downstream downstream(final DomainController domainController, final MessageLibrary messageLibrary) {
        return new ServerDownstream(messageLibrary, new ServerHandlerCustomNetworkTypeHandler(domainController));
    }

    @Bean
    public Both both(final Upstream upstream, final Downstream downstream) {
        return new ServerBoth(upstream, downstream);
    }
}
