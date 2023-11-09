package me.chris.bear.web;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Christopher
 * @date 2023/11/8
 **/
@Setter
@Getter
@Accessors(chain = true)
@ConfigurationProperties(prefix = "bear.web")
public class BearWebProperties {

    boolean requestLogging = true;
}
