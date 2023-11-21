package me.chris.bear.data;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author Christopher
 * @Date 2023/11/8
 **/
@Setter
@Getter
@Accessors(chain = true)
@ConfigurationProperties(prefix = "bear.jpa")
public class DataAuditorProperties {

    boolean auditor = true;
}
