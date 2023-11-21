package me.chris.bear.data;

import lombok.extern.slf4j.Slf4j;
import me.chris.bear.data.auditor.RequestHeaderAuditorAware;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

/**
 * @Author Christopher
 * @Since 2023/11/21 11:12 PM
 */
@Configuration
@Slf4j
@EnableConfigurationProperties(DataAuditorProperties.class)
public class DataAuditorAutoConfiguration {

    @ConditionalOnProperty(
            prefix = "bear.jpa",
            name = "auditor",
            havingValue = "true")
    @Bean
    public AuditorAware<Long> auditorProvider() {
        log.info("jpa auditor starting ...");
        return new RequestHeaderAuditorAware();
    }

}