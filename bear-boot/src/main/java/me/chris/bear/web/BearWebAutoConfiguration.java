package me.chris.bear.web;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import me.chris.bear.web.filter.RequestLoggingFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Christopher
 * @date 2023/11/8
 **/
@Configuration
@Slf4j
@EnableConfigurationProperties(BearWebProperties.class)
public class BearWebAutoConfiguration {

    @PostConstruct
    void init(){
        log.info("BearWebAutoConfiguration starting...");
    }

    @ConditionalOnProperty(
            prefix = "bear.web",
            name = "request-logging",
            havingValue = "true",
            matchIfMissing = true)
    public RequestLoggingFilter requestLoggingFilter() {
        return new RequestLoggingFilter();
    }

}
