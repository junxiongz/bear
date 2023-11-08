package me.chris.bear.web;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Christopher
 * @date 2023/11/8
 **/
@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "me.chris.bear.web")
public class BearWebProperties {

    boolean requestLogging = true;
}
