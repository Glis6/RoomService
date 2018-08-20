package com.glis.log;

import com.glis.domain.DomainController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Glis
 */
@Configuration
public class LogConfiguration {
    @Bean
    public RepositoryExceptionLogHandler repositoryExceptionLogHandler(final DomainController domainController) {
        return new RepositoryExceptionLogHandler(domainController);
    }
}
