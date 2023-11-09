package me.chris.bear.web;

import lombok.extern.slf4j.Slf4j;
import me.chris.bear.web.filter.RequestLoggingFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Christopher
 * @date 2023/11/8
 **/
@Configuration
@Slf4j
@EnableConfigurationProperties(BearWebProperties.class)
public class BearWebAutoConfiguration {

    @ConditionalOnProperty(
            prefix = "bear.web",
            name = "request-logging",
            havingValue = "true")
    @Bean
    public RequestLoggingFilter requestLoggingFilter() {
        log.info("RequestLoggingFilter start.");
        return new RequestLoggingFilter();
    }

}
