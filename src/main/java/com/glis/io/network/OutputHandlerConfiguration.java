package com.glis.io.network;

import com.glis.domain.DomainController;
import com.glis.spotify.output.PlaybackOutputHandler;
import com.glis.io.network.output.handlers.OutputHandler;
import com.glis.spotify.output.SpotifyAccessTokenOutputHandler;
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
                new PlaybackOutputHandler(domainController),
                new SpotifyAccessTokenOutputHandler(domainController)
        });
    }
}
