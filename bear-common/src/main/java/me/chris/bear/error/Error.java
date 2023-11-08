package me.chris.bear.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.WithBy;

import java.io.Serializable;

/**
 * @author Christopher
 * @date 2023/11/8
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
@Value
@AllArgsConstructor
@Builder
public class Error implements Serializable, ErrorDetail {

    @WithBy private final int code;
    @WithBy private final int status;
    @WithBy private final String message;
    @WithBy private final Object data;
    @WithBy private final String path;

    /** 0 for uninitialized */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @WithBy private final long timestamp;
}
